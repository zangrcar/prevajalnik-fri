package prev26lang.phase.memory;

import java.util.ArrayDeque;
import java.util.Deque;
import prev26lang.common.report.*;
import prev26lang.phase.abstr.*;
import prev26lang.phase.seman.*;

/**
 * Memory layout visitor.
 *
 * What this visitor computes:
 *  - Memory.frameAttr on AST.DefFunDefn
 *  - Memory.accessAttr on AST.VarDefn / AST.ParDefn / AST.CompDefn
 *  - Memory.stringAttr on string literals (AST.AtomExpr of type STR)
 *
 * High-level idea:
 *  - globals -> MEM.AbsAccess
 *  - locals  -> MEM.RelAccess with negative offsets
 *  - params  -> MEM.RelAccess with positive offsets
 *  - record components -> MEM.RelAccess with depth = -1
 *  - string literals -> MEM.AbsAccess with anonymous label and init text
 */
public class Layouter implements AST.FullVisitor<Object, Object> {

    // --------------------------------------------------------------------
    //  Calling-record / alignment constants
    // --------------------------------------------------------------------

    /** Width of an address in bytes, given directly by the assignment. */
    private static final long ADDRESS_SIZE = 8;

    /** Required alignment in bytes, given directly by the assignment. */
    private static final long ALIGNMENT = 8;

    /**
     * Fixed part of a frame that is always present, excluding locals and
     * outgoing-argument area.
     */
    private static final long FIXED_FRAME_SIZE = 2*ADDRESS_SIZE;

    /**
     * Size reserved in every outgoing call for the static link.
     */
    private static final long STATIC_LINK_SIZE = ADDRESS_SIZE;

	/**
     * Offset of the first incoming parameter relative to FP.
     */
    private static final long FIRST_PARAM_OFFSET = STATIC_LINK_SIZE;

    /**
     * Whether a call reserves space for the callee result in the outgoing area.
     */
    private static final boolean RESULT_IN_CALL_RECORD = true;

    // --------------------------------------------------------------------
    //  Context objects
    // --------------------------------------------------------------------

    /**
     * Per-function layout context.
     *
     * We push one context when entering a function and pop it when leaving.
     * The context stores everything needed to compute:
     *  - parameter accesses
     *  - local accesses
     *  - maximum outgoing argument area
     *  - final frame
     */
    private static final class FunCtx {
        final AST.FunDefn funDefn;
        final long depth;
        final MEM.Label label;
        final boolean hasFrame;   // true only for DefFunDefn, false for ExtFunDefn

        long nextParOffset;
        long nextLocalOffset;     // grows negatively
        long locsSize;
        long maxArgsSize;

        FunCtx(AST.FunDefn funDefn, long depth, MEM.Label label, boolean hasFrame) {
            this.funDefn = funDefn;
            this.depth = depth;
            this.label = label;
            this.hasFrame = hasFrame;

            this.nextParOffset = FIRST_PARAM_OFFSET;
            this.nextLocalOffset = 0;
            this.locsSize = 0;
            this.maxArgsSize = 0;
        }
    }

    /**
     * Per-record layout context.
     *
     * We push one context when entering a struct or union type and use it while
     * visiting its components.
     */
    private static final class RecCtx {
        final boolean isStruct;

        long nextOffset;   // used for struct fields
        long maxSize;      // used for union size

        RecCtx(boolean isStruct) {
            this.isStruct = isStruct;
            this.nextOffset = 0;
            this.maxSize = 0;
        }
    }

    /** Stack of function contexts for nested functions. */
    private final Deque<FunCtx> funStack = new ArrayDeque<>();

    /** Stack of record contexts for nested record types. */
    private final Deque<RecCtx> recStack = new ArrayDeque<>();

    /** Constructs the layouter. */
    public Layouter() {
    }

    // --------------------------------------------------------------------
    //  Small generic helpers
    // --------------------------------------------------------------------

    /**
     * Rounds a size up to the next multiple of 8.
     *
     * Use this whenever something must be placed in memory as a separately
     * aligned object/slot.
     */
    private long align8(long size) {
        if (size < 0)
            throw new Report.InternalError();
        return ((size + ALIGNMENT - 1) / ALIGNMENT) * ALIGNMENT;
    }

    /**
     * Returns the current function context or null if we are not inside any
     * function.
     */
    private FunCtx currentFun() {
        return funStack.peek();
    }

    /**
     * Returns the current record context or null if we are not inside any
     * struct/union type.
     */
    private RecCtx currentRec() {
        return recStack.peek();
    }

    /**
     * True iff we are currently inside a function body / parameter list.
     */
    private boolean insideFunction() {
        return currentFun() != null;
    }

    /**
     * Computes the static depth of a newly entered function.
     *
     * The MEM.Frame documentation says that global functions are at depth 0.
     * Nested functions are therefore one level deeper than the enclosing one.
     */
    private long nextFunctionDepth() {
        final FunCtx parent = currentFun();
        return (parent == null ? 0 : parent.depth + 1);
    }

    /**
     * Creates a label for a function.
     *
     * For named functions, using the function name is usually the nicest choice.
     * Nested functions may also use named labels in this project.
     */
    private MEM.Label functionLabel(AST.FunDefn funDefn) {
		return (currentFun() == null)
			? new MEM.Label(funDefn.name)
			: new MEM.Label();
	}

    // --------------------------------------------------------------------
    //  Semantic-type helpers
    // --------------------------------------------------------------------

    /**
     * Unwraps named types to their actual type.
     */
    private TYP.Type actualType(final TYP.Type type) {
        if (type == null)
            throw new Report.InternalError();
        return type.actualType();
    }

    /**
     * Reads the semantic type attached to a syntax type node.
     */
    private TYP.Type requireType(final AST.Type typeNode) {
        final TYP.Type type = SemAn.isTypeAttr.get(typeNode);
        if (type == null)
            throw new Report.InternalError();
        return type;
    }

    /**
     * Reads the semantic type attached to an expression.
     *
     * Useful for computing outgoing call area size.
     */
    private TYP.Type requireExprType(final AST.Expr expr) {
        final TYP.Type type = SemAn.ofTypeAttr.get(expr);
        if (type == null)
            throw new Report.InternalError();
        return type;
    }

    /**
     * Returns the raw size of a type, before outer slot-alignment.
     *
     * Notes:
     *  - int, pointers, and function values are 8 bytes
     *  - char and bool are naturally 1 byte
     *  - arrays / structs / unions are built recursively
     *
     * If your course wants char/bool to also occupy 8 bytes everywhere, you can
     * simplify this and return 8 for them too.
     *
     * If your course wants arrays to be tightly packed without per-element
     * alignment, change the array case accordingly.
     */
    private long rawSizeOf(final TYP.Type type) {
        final TYP.Type actual = actualType(type);

        if (actual instanceof TYP.IntType)
            return 8;

        if (actual instanceof TYP.CharType)
            return 1;

        if (actual instanceof TYP.BoolType)
            return 1;

        if (actual instanceof TYP.VoidType)
            return 0;

        if (actual instanceof TYP.PtrType)
            return ADDRESS_SIZE;

        if (actual instanceof TYP.FunType)
            return ADDRESS_SIZE;

        if (actual instanceof TYP.ArrType arrType) {
            return arrType.numElems * rawSizeOf(arrType.elemType);
        }

        if (actual instanceof TYP.StrType strType) {
            long sum = 0;
            for (TYP.Type compType : strType.compTypes)
                sum += slotSizeOf(compType);
            return sum;
        }

        if (actual instanceof TYP.UniType uniType) {
            long max = 0;
            for (TYP.Type compType : uniType.compTypes)
                max = Math.max(max, slotSizeOf(compType));
            return max;
        }

        throw new Report.InternalError();
    }

    /**
     * Returns the size of a type when stored as an independently aligned object.
     *
     * This is usually the size you want for:
     *  - variables
     *  - parameters
     *  - record components
     *  - call-record slots
     */
    private long slotSizeOf(final TYP.Type type) {
        return align8(rawSizeOf(type));
    }

    /**
     * Convenience overload for syntax type nodes.
     */
    private long slotSizeOf(final AST.Type typeNode) {
        return slotSizeOf(requireType(typeNode));
    }

	/**
	 * Returns the raw payload size of a string literal in bytes.
	 *
	 * PREV'26 strings may contain:
	 *  - ordinary printable characters
	 *  - escaped quote: \"
	 *  - escaped backslash: \\
	 *  - hex escape: \xHH
	 *
	 * Each decoded character occupies one byte.
	 *
	 * This function returns the number of actual stored characters,
	 * without surrounding quotes and without any implicit '\0'.
	 */
	private long stringSize(final AST.AtomExpr atomExpr) {
		final String lexeme = atomExpr.value;

		if (lexeme == null || lexeme.length() < 2)
			throw new Report.InternalError();

		if (lexeme.charAt(0) != '"' || lexeme.charAt(lexeme.length() - 1) != '"')
			throw new Report.InternalError();

		long size = 0;

		// Skip opening and closing quote.
		int i = 1;
		final int end = lexeme.length() - 1;

		while (i < end) {
			final char c = lexeme.charAt(i);

			if (c != '\\') {
				// Ordinary character inside the string.
				size++;
				i++;
				continue;
			}

			// Escape sequence starts here.
			if (i + 1 >= end)
				throw new Report.InternalError();

			final char esc = lexeme.charAt(i + 1);

			switch (esc) {
				case '"', '\\' -> {
					// \" or \\
					size++;
					i += 2;
				}
				case 'x' -> {
					// \xHH
					if (i + 3 >= end)
						throw new Report.InternalError();
					
					final char h1 = lexeme.charAt(i + 2);
					final char h2 = lexeme.charAt(i + 3);
					
					if (!isHexDigit(h1) || !isHexDigit(h2))
						throw new Report.InternalError();
					
					size++;
					i += 4;
				}

				default -> // According to the PREV'26 spec, other escapes are not valid.
					throw new Report.InternalError();
			}
		}

		return size;
	}

	/**
	 * Checks whether a character is a hexadecimal digit.
	 */
	private boolean isHexDigit(final char c) {
		return ('0' <= c && c <= '9')
			|| ('A' <= c && c <= 'F')
			|| ('a' <= c && c <= 'f');
	}

    // --------------------------------------------------------------------
    //  Access allocation helpers
    // --------------------------------------------------------------------

    /**
     * Allocates a global variable.
     *
     * Globals live at absolute addresses, so they use MEM.AbsAccess.
     */
    private void layoutGlobalVar(final AST.VarDefn varDefn) {
        final long size = slotSizeOf(varDefn.type);
        final MEM.AbsAccess access = new MEM.AbsAccess(size, new MEM.Label(varDefn.name));
        Memory.accessAttr.put(varDefn, access);
    }

    /**
     * Allocates a local variable inside the current function.
     *
     * Locals use negative offsets relative to the frame base and remember the
     * static depth of the function that owns them.
     */
    private void layoutLocalVar(final AST.VarDefn varDefn, final FunCtx ctx) {
        final long size = slotSizeOf(varDefn.type);

        ctx.nextLocalOffset -= size;
        ctx.locsSize += size;

        final MEM.RelAccess access = new MEM.RelAccess(size, ctx.nextLocalOffset, ctx.depth);
        Memory.accessAttr.put(varDefn, access);
    }

    /**
     * Allocates a formal parameter of the current function.
     *
     * Parameters use positive offsets relative to the frame base.
     */
    private void layoutParameter(final AST.ParDefn parDefn, final FunCtx ctx) {
        final long size = slotSizeOf(parDefn.type);
        final long offset = ctx.nextParOffset;

        Memory.accessAttr.put(parDefn, new MEM.RelAccess(size, offset, ctx.depth));
        ctx.nextParOffset += size;
    }

    /**
     * Allocates a record component.
     *
     * Record components always use MEM.RelAccess with depth = -1.
     *  - struct: offsets grow one after another
     *  - union:  all offsets are 0, size is max(component size)
     */
    private void layoutComponent(final AST.CompDefn compDefn, final RecCtx ctx) {
        final long size = slotSizeOf(compDefn.type);

        if (ctx.isStruct) {
            final long offset = ctx.nextOffset;
            Memory.accessAttr.put(compDefn, new MEM.RelAccess(size, offset, -1));
            ctx.nextOffset += size;
        } else {
            Memory.accessAttr.put(compDefn, new MEM.RelAccess(size, 0, -1));
            ctx.maxSize = Math.max(ctx.maxSize, size);
        }
    }

    /**
     * Stores a string literal into the string attribute.
     *
     * Strings are absolute objects with anonymous labels and preserved init text.
     */
    private void layoutStringLiteral(final AST.AtomExpr atomExpr) {
        final long size = stringSize(atomExpr);
        final MEM.AbsAccess access = new MEM.AbsAccess(size, new MEM.Label(), atomExpr.value);
        Memory.stringAttr.put(atomExpr, access);
    }

    // --------------------------------------------------------------------
    //  Function / frame helpers
    // --------------------------------------------------------------------

    /**
     * Enters a function by pushing a fresh context.
     *
     * This is used for both defined and external functions because external
     * function parameters still need accesses, even though external functions do
     * not get a frame in Memory.frameAttr.
     */
    private FunCtx enterFunction(final AST.FunDefn funDefn, final boolean hasFrame) {
        final FunCtx ctx = new FunCtx(funDefn, nextFunctionDepth(), functionLabel(funDefn), hasFrame);
        funStack.push(ctx);
        return ctx;
    }

    /**
     * Computes the size of the frame for a defined function 
	 * without saved registers and temporary variables.
     */
    private long frameSize(final FunCtx ctx) {
        return align8(FIXED_FRAME_SIZE + ctx.locsSize + ctx.maxArgsSize);
    }

    /**
     * Finishes a defined function by constructing and storing its frame.
     */
    private void leaveDefinedFunction(final AST.DefFunDefn defFunDefn, final FunCtx ctx) {
        final MEM.Frame frame = new MEM.Frame(
            ctx.label,
            ctx.depth,
            ctx.locsSize,
            ctx.maxArgsSize,
            frameSize(ctx)
        );
        Memory.frameAttr.put(defFunDefn, frame);
        funStack.pop();
    }

    /**
     * Finishes an external function.
     *
     * No frame is stored, but the temporary function context is still popped.
     */
    private void leaveExternalFunction() {
        funStack.pop();
    }

    /**
     * Computes how much outgoing call-record space is needed for one call.
     *
     * Skeleton policy:
     *  - reserve space for static link
     *  - reserve one slot per argument
     *  - reserve one result slot
     */
    private long callAreaSize(final AST.CallExpr callExpr) {
        long size = 0;

        size += STATIC_LINK_SIZE;

        // Reserve slots for actual arguments.
        for (AST.Expr argExpr : callExpr.argExprs) {
            size += slotSizeOf(requireExprType(argExpr));
        }

        return align8(size);
    }

    /**
     * Updates the current function's max outgoing-argument area with one call.
     */
    private void registerCall(final AST.CallExpr callExpr) {
        final FunCtx ctx = currentFun();
        if (ctx == null)
            return;

        ctx.maxArgsSize = Math.max(ctx.maxArgsSize, callAreaSize(callExpr));
    }

    // --------------------------------------------------------------------
    //  Visitor methods
    // --------------------------------------------------------------------

    /**
     * Variable definitions allocate either:
     *  - absolute storage if global
     *  - relative storage if local to a function
     */
    @Override
    public Object visit(AST.VarDefn varDefn, Object arg) {
        if (insideFunction())
            layoutLocalVar(varDefn, currentFun());
        else
            layoutGlobalVar(varDefn);
        return null;
    }

    /**
     * Defined functions:
     *  - create a function context
     *  - allocate parameters
     *  - traverse the body (this handles locals, nested functions, calls, strings)
     *  - build and store the final frame
     */
    @Override
    public Object visit(AST.DefFunDefn defFunDefn, Object arg) {
        final FunCtx ctx = enterFunction(defFunDefn, true);

        defFunDefn.pars.accept(this, arg);
        defFunDefn.type.accept(this, arg); // not strictly needed for layout, but harmless
        defFunDefn.expr.accept(this, arg);

        leaveDefinedFunction(defFunDefn, ctx);
        return null;
    }

    /**
     * External functions do not get frames in Memory.frameAttr, but their
     * parameters still need layout because ParDefn nodes are logged as accesses.
     */
    @Override
    public Object visit(AST.ExtFunDefn extFunDefn, Object arg) {
        enterFunction(extFunDefn, false);

        extFunDefn.pars.accept(this, arg);
        extFunDefn.type.accept(this, arg);

        leaveExternalFunction();
        return null;
    }

    /**
     * Formal parameter layout inside the current function.
     */
    @Override
    public Object visit(AST.ParDefn parDefn, Object arg) {
        final FunCtx ctx = currentFun();
        if (ctx == null)
            throw new Report.InternalError();

        layoutParameter(parDefn, ctx);
        return null;
    }

    /**
     * Record component layout inside the current struct/union.
     */
    @Override
    public Object visit(AST.CompDefn compDefn, Object arg) {
        final RecCtx ctx = currentRec();
        if (ctx == null)
            throw new Report.InternalError();

        layoutComponent(compDefn, ctx);
        return null;
    }

    /**
     * Struct layout:
     * push struct context, visit components, pop context.
     */
    @Override
    public Object visit(AST.StrType strType, Object arg) {
        recStack.push(new RecCtx(true));
        strType.comps.accept(this, arg);
        recStack.pop();
        return null;
    }

    /**
     * Union layout:
     * push union context, visit components, pop context.
     */
    @Override
    public Object visit(AST.UniType uniType, Object arg) {
        recStack.push(new RecCtx(false));
        uniType.comps.accept(this, arg);
        recStack.pop();
        return null;
    }

    /**
     * String literals are stored in global/static data.
     */
    @Override
    public Object visit(AST.AtomExpr atomExpr, Object arg) {
        if (atomExpr.type == AST.AtomExpr.Type.STR)
            layoutStringLiteral(atomExpr);
        return null;
    }

    /**
     * Function calls affect only the current function's outgoing-argument area.
     *
     * We still traverse the function expression and all arguments first.
     */
    @Override
    public Object visit(AST.CallExpr callExpr, Object arg) {
        callExpr.funExpr.accept(this, arg);
        callExpr.argExprs.accept(this, arg);

        registerCall(callExpr);
        return null;
    }
}