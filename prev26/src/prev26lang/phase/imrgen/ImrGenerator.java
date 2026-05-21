package prev26lang.phase.imrgen;

import java.util.*;
import prev26lang.common.report.*;
import prev26lang.phase.abstr.*;
import prev26lang.phase.memory.*;
import prev26lang.phase.seman.*;

public class ImrGenerator implements AST.FullVisitor<Object, Object> {

	private static final long ADDRESS_SIZE = 8;
	private static final long ALIGNMENT = 8;

	private MEM.Frame currentFrame = null;

	// --------------------------------------------------------------------
	// Generic helpers
	// --------------------------------------------------------------------

	private long align8(final long size) {
		if (size < 0)
			throw new Report.InternalError();
		return ((size + ALIGNMENT - 1) / ALIGNMENT) * ALIGNMENT;
	}

	private TYP.Type actualType(final TYP.Type type) {
		if (type == null)
			throw new Report.InternalError();
		return type.actualType();
	}

	private TYP.Type requireExprType(final AST.Expr expr) {
		final TYP.Type type = SemAn.ofTypeAttr.get(expr);
		if (type == null)
			throw new Report.InternalError();
		return type;
	}

	private TYP.Type requireType(final AST.Type type) {
		final TYP.Type semType = SemAn.isTypeAttr.get(type);
		if (semType == null)
			throw new Report.InternalError();
		return semType;
	}

	private IMR.Expr requireExprIR(final AST.Expr expr) {
		final IMR.Expr imr = ImrGen.genExprIMRAttr.get(expr);
		if (imr == null)
			throw new Report.InternalError();
		return imr;
	}

	private void putExprIR(final AST.Expr expr, final IMR.Expr imr) {
		if (expr == null || imr == null)
			throw new Report.InternalError();
		ImrGen.genExprIMRAttr.put(expr, imr);
	}

	private void putStmtIR(final AST.DefFunDefn defFunDefn, final IMR.Stmt imr) {
		if (defFunDefn == null || imr == null)
			throw new Report.InternalError();
		ImrGen.genStmtIMRAttr.put(defFunDefn, imr);
	}

	private MEM.Frame requireFrame(final AST.DefFunDefn defFunDefn) {
		final MEM.Frame frame = Memory.frameAttr.get(defFunDefn);
		if (frame == null)
			throw new Report.InternalError();
		return frame;
	}

	// --------------------------------------------------------------------
	// Size and memory-access helpers
	// --------------------------------------------------------------------

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

		if (actual instanceof TYP.ArrType arrType)
			return arrType.numElems * rawSizeOf(arrType.elemType);

		if (actual instanceof TYP.StrType strType) {
			long size = 0;
			for (final TYP.Type compType : strType.compTypes)
				size += slotSizeOf(compType);
			return size;
		}

		if (actual instanceof TYP.UniType uniType) {
			long size = 0;
			for (final TYP.Type compType : uniType.compTypes)
				size = Math.max(size, slotSizeOf(compType));
			return size;
		}

		throw new Report.InternalError();
	}

	private long slotSizeOf(final TYP.Type type) {
		return align8(rawSizeOf(type));
	}

	private long sizeOf(final AST.Type type) {
		return rawSizeOf(requireType(type));
	}

	private IMR.Expr memAt(final IMR.Expr addr, final TYP.Type type) {
		if (addr == null || type == null)
			throw new Report.InternalError();

		final long size = rawSizeOf(type);
		if (size == 1)
			return new IMR.MEM1(addr);
		if (size > 1)
			return new IMR.MEM8(addr);

		throw new Report.InternalError();
	}

	private IMR.Expr addrOf(final IMR.Expr expr) {
		if (expr instanceof IMR.MEM1 mem)
			return mem.addr;
		if (expr instanceof IMR.MEM8 mem)
			return mem.addr;
		if (expr instanceof IMR.SEXPR sExpr)
			return new IMR.SEXPR(sExpr.stmt, addrOf(sExpr.expr));
		if (expr instanceof IMR.BINOP binOp && binOp.oper == IMR.BINOP.Oper.MOD && binOp.sndExpr instanceof IMR.CONST)
			return addrOf(binOp.fstExpr);

		throw new Report.InternalError();
	}

	private IMR.Expr addrOf(final AST.Expr expr) {
		return addrOf(requireExprIR(expr));
	}

	private IMR.Expr addressWithOffset(final IMR.Expr baseAddr, final long offset) {
		if (baseAddr == null)
			throw new Report.InternalError();
		if (offset == 0)
			return baseAddr;
		return new IMR.BINOP(IMR.BINOP.Oper.ADD, baseAddr, new IMR.CONST(offset));
	}

	// --------------------------------------------------------------------
	// Frame, static-link, and definition helpers
	// --------------------------------------------------------------------

	private MEM.Access requireAccess(final AST.Defn defn) {
		if (!(defn instanceof AST.VarDefn) && !(defn instanceof AST.ParDefn) && !(defn instanceof AST.CompDefn))
			throw new Report.InternalError();

		final MEM.Access access = Memory.accessAttr.get(defn);
		if (access == null)
			throw new Report.InternalError();
		return access;
	}

	private long depthOf(final AST.Defn defn) {
		if (defn instanceof AST.DefFunDefn defFunDefn)
			return requireFrame(defFunDefn).depth;

		if (defn instanceof AST.ExtFunDefn extFunDefn) {
			if (extFunDefn.pars.size() == 0)
				return 0;

			final MEM.Access access = requireAccess(extFunDefn.pars.first());
			if (access instanceof MEM.RelAccess relAccess)
				return relAccess.depth;

			throw new Report.InternalError();
		}

		final MEM.Access access = requireAccess(defn);
		if (access instanceof MEM.RelAccess relAccess)
			return relAccess.depth;
		if (access instanceof MEM.AbsAccess)
			return 0;

		throw new Report.InternalError();
	}

	private IMR.Expr staticLinkAt(final IMR.Expr framePointer) {
		if (framePointer == null)
			throw new Report.InternalError();
		return new IMR.MEM8(framePointer);
	}

	private IMR.Expr staticChainTo(final long targetDepth) {
		if (currentFrame == null)
			throw new Report.InternalError();
		if (targetDepth < 0 || targetDepth > currentFrame.depth)
			throw new Report.InternalError();

		IMR.Expr framePointer = new IMR.TEMP(currentFrame.FP);
		for (long depth = currentFrame.depth; depth > targetDepth; depth--)
			framePointer = staticLinkAt(framePointer);
		return framePointer;
	}

	private IMR.Expr addressOfAccess(final MEM.Access access) {
		if (access instanceof MEM.AbsAccess absAccess)
			return new IMR.NAME(absAccess.label);

		if (access instanceof MEM.RelAccess relAccess) {
			if (relAccess.depth == -1)
				return new IMR.CONST(relAccess.offset);

			return addressWithOffset(staticChainTo(relAccess.depth), relAccess.offset);
		}

		throw new Report.InternalError();
	}

	private IMR.Expr addressOfDefinition(final AST.Defn defn) {
		if (defn instanceof AST.FunDefn funDefn)
			return addressOfFunction(funDefn);

		return addressOfAccess(requireAccess(defn));
	}

	private MEM.Label labelOf(final AST.FunDefn funDefn) {
		if (funDefn instanceof AST.DefFunDefn defFunDefn)
			return requireFrame(defFunDefn).label;

		if (funDefn instanceof AST.ExtFunDefn)
			return new MEM.Label(funDefn.name);

		throw new Report.InternalError();
	}

	private IMR.Expr addressOfFunction(final AST.FunDefn funDefn) {
		return new IMR.NAME(labelOf(funDefn));
	}

	private IMR.Expr callStaticLink(final AST.FunDefn callee) {
		final long calleeDepth = depthOf(callee);
		if (calleeDepth < 0)
			throw new Report.InternalError();
		if (calleeDepth == 0)
			return new IMR.CONST(0xEEEEEEEEL);

		return staticChainTo(calleeDepth - 1);
	}

	private IMR.Expr indirectCallStaticLink() {
		// Function values are represented as code pointers, so no closure link is stored.
		return new IMR.CONST(0xEEEEEEEEL);
	}

	// --------------------------------------------------------------------
	// Operator and literal helpers
	// --------------------------------------------------------------------

	private IMR.BINOP.Oper binOper(final AST.BinExpr.Oper oper) {
		if (oper == null)
			throw new Report.InternalError();

		return switch (oper) {
		case OR -> IMR.BINOP.Oper.OR;
		case AND -> IMR.BINOP.Oper.AND;
		case EQU -> IMR.BINOP.Oper.EQU;
		case NEQ -> IMR.BINOP.Oper.NEQ;
		case LTH -> IMR.BINOP.Oper.LTH;
		case GTH -> IMR.BINOP.Oper.GTH;
		case LEQ -> IMR.BINOP.Oper.LEQ;
		case GEQ -> IMR.BINOP.Oper.GEQ;
		case ADD -> IMR.BINOP.Oper.ADD;
		case SUB -> IMR.BINOP.Oper.SUB;
		case MUL -> IMR.BINOP.Oper.MUL;
		case DIV -> IMR.BINOP.Oper.DIV;
		case MOD -> IMR.BINOP.Oper.MOD;
		};
	}

	private IMR.UNOP.Oper unOper(final AST.PfxExpr.Oper oper) {
		if (oper == null)
			throw new Report.InternalError();

		return switch (oper) {
		case NOT -> IMR.UNOP.Oper.NOT;
		case SUB -> IMR.UNOP.Oper.NEG;
		case ADD, PTR -> throw new Report.InternalError();
		};
	}

	private long valueOfInt(final String lexeme) {
		try {
			return Long.parseLong(lexeme);
		} catch (final NumberFormatException __) {
			throw new Report.InternalError();
		}
	}

	private long valueOfBool(final String lexeme) {
		if ("true".equals(lexeme))
			return 1;
		if ("false".equals(lexeme))
			return 0;
		throw new Report.InternalError();
	}

	private long valueOfChar(final String lexeme) {
		if (lexeme == null || lexeme.length() < 3)
			throw new Report.InternalError();
		if (lexeme.charAt(0) != '\'' || lexeme.charAt(lexeme.length() - 1) != '\'')
			throw new Report.InternalError();

		if (lexeme.charAt(1) == '\\') {
			if (1 + escapeWidth(lexeme, 1) != lexeme.length() - 1)
				throw new Report.InternalError();
			return valueOfEscape(lexeme, 1);
		}

		if (lexeme.length() != 3)
			throw new Report.InternalError();
		return lexeme.charAt(1);
	}

	private long valueOfEscape(final String lexeme, final int index) {
		if (lexeme == null || index < 0 || index + 1 >= lexeme.length())
			throw new Report.InternalError();
		if (lexeme.charAt(index) != '\\')
			throw new Report.InternalError();

		final char escaped = lexeme.charAt(index + 1);
		return switch (escaped) {
		case '\\' -> '\\';
		case '\'' -> '\'';
		case '"' -> '"';
		case 'x' -> {
			if (index + 3 >= lexeme.length())
				throw new Report.InternalError();

			final int hi = Character.digit(lexeme.charAt(index + 2), 16);
			final int lo = Character.digit(lexeme.charAt(index + 3), 16);
			if (hi < 0 || lo < 0)
				throw new Report.InternalError();

			yield 16 * hi + lo;
		}
		default -> throw new Report.InternalError();
		};
	}

	private int escapeWidth(final String lexeme, final int index) {
		if (lexeme == null || index < 0 || index + 1 >= lexeme.length())
			throw new Report.InternalError();
		if (lexeme.charAt(index) != '\\')
			throw new Report.InternalError();

		return switch (lexeme.charAt(index + 1)) {
		case '\\', '\'', '"' -> 2;
		case 'x' -> {
			valueOfEscape(lexeme, index);
			yield 4;
		}
		default -> throw new Report.InternalError();
		};
	}

	private IMR.Expr valueOfAtom(final AST.AtomExpr atomExpr) {
		if (atomExpr == null)
			throw new Report.InternalError();

		return switch (atomExpr.type) {
		case INT -> new IMR.CONST(valueOfInt(atomExpr.value));
		case CHAR -> new IMR.CONST(valueOfChar(atomExpr.value));
		case BOOL -> new IMR.CONST(valueOfBool(atomExpr.value));
		case VOID, PTR -> new IMR.CONST(0);
		case STR -> {
			final MEM.AbsAccess access = Memory.stringAttr.get(atomExpr);
			if (access == null)
				throw new Report.InternalError();
			yield new IMR.NAME(access.label);
		}
		};
	}

	private IMR.Expr castTo(final IMR.Expr expr, final TYP.Type type) {
		if (expr == null || type == null)
			throw new Report.InternalError();

		final TYP.Type actual = actualType(type);
		if (actual instanceof TYP.BoolType)
			return new IMR.BINOP(IMR.BINOP.Oper.MOD, expr, new IMR.CONST(2));
		if (actual instanceof TYP.CharType)
			return new IMR.BINOP(IMR.BINOP.Oper.MOD, expr, new IMR.CONST(256));
		return expr;
	}

	// --------------------------------------------------------------------
	// Compound-expression helpers
	// --------------------------------------------------------------------

	private AST.CompDefn componentDefinition(final AST.CompExpr compExpr) {
		if (compExpr == null)
			throw new Report.InternalError();

		final TYP.Type recType = actualType(requireExprType(compExpr.recExpr));
		if (!(recType instanceof TYP.RecType))
			throw new Report.InternalError();

		final LinkedHashMap<String, AST.CompDefn> comps = TypeConstructor.recComps.get(recType);
		if (comps == null)
			throw new Report.InternalError();

		final AST.CompDefn compDefn = comps.get(compExpr.name);
		if (compDefn == null)
			throw new Report.InternalError();
		return compDefn;
	}

	private TYP.Type arrayElementType(final AST.ArrExpr arrExpr) {
		if (arrExpr == null)
			throw new Report.InternalError();

		final TYP.Type arrType = actualType(requireExprType(arrExpr.arrExpr));
		if (arrType instanceof TYP.ArrType semArrType)
			return semArrType.elemType;

		throw new Report.InternalError();
	}

	private Vector<IMR.Stmt> stmts() {
		return new Vector<IMR.Stmt>();
	}

	private IMR.Stmt stmts(final Vector<IMR.Stmt> stmts) {
		if (stmts == null)
			throw new Report.InternalError();
		return new IMR.STMTS(stmts);
	}

	private IMR.Expr sexpr(final Vector<IMR.Stmt> stmts, final IMR.Expr expr) {
		if (stmts == null || expr == null)
			throw new Report.InternalError();
		if (stmts.isEmpty())
			return expr;
		return new IMR.SEXPR(stmts(stmts), expr);
	}

	private AST.FunDefn directCallee(final AST.CallExpr callExpr) {
		if (callExpr == null)
			throw new Report.InternalError();

		if (callExpr.funExpr instanceof AST.NameExpr nameExpr) {
			final AST.Defn defn = SemAn.defAtAttr.get(nameExpr);
			if (defn instanceof AST.FunDefn funDefn)
				return funDefn;
		}

		return null;
	}

	private Vector<Long> callArgOffsets(final AST.CallExpr callExpr) {
		if (callExpr == null)
			throw new Report.InternalError();

		final Vector<Long> offsets = new Vector<Long>();
		long offset = 0;

		offsets.add(offset);
		offset += ADDRESS_SIZE;

		for (final AST.Expr argExpr : callExpr.argExprs) {
			offsets.add(offset);
			offset += slotSizeOf(requireExprType(argExpr));
		}

		return offsets;
	}

	private Vector<IMR.Expr> callArgs(final AST.CallExpr callExpr) {
		if (callExpr == null)
			throw new Report.InternalError();

		final AST.FunDefn callee = directCallee(callExpr);

		final Vector<IMR.Expr> args = new Vector<IMR.Expr>();
		args.add(callee == null ? indirectCallStaticLink() : callStaticLink(callee));

		for (final AST.Expr argExpr : callExpr.argExprs)
			args.add(requireExprIR(argExpr));

		return args;
	}

	// --------------------------------------------------------------------
	// Definitions
	// --------------------------------------------------------------------

	@Override
	public Object visit(final AST.DefFunDefn defFunDefn, final Object arg) {
		final MEM.Frame oldFrame = currentFrame;
		final MEM.Frame frame = requireFrame(defFunDefn);
		final MEM.Label entryLabel = new MEM.Label();
		final MEM.Label exitLabel = new MEM.Label();

		ImrGen.bodyEntryLabelAttr.put(defFunDefn, entryLabel);
		ImrGen.bodyExitLabelAttr.put(defFunDefn, exitLabel);

		currentFrame = frame;
		try {
			defFunDefn.expr.accept(this, arg);

			final Vector<IMR.Stmt> body = stmts();
			body.add(new IMR.LABEL(entryLabel));
			body.add(new IMR.MOVE(new IMR.TEMP(frame.RV), requireExprIR(defFunDefn.expr)));
			body.add(new IMR.JUMP(new IMR.NAME(exitLabel)));
			body.add(new IMR.LABEL(exitLabel));

			putStmtIR(defFunDefn, stmts(body));
		} finally {
			currentFrame = oldFrame;
		}
		return null;
	}

	@Override
	public Object visit(final AST.ExtFunDefn extFunDefn, final Object arg) {
		return null;
	}

	// --------------------------------------------------------------------
	// Expressions
	// --------------------------------------------------------------------

	@Override
	public Object visit(final AST.ArrExpr arrExpr, final Object arg) {
		arrExpr.arrExpr.accept(this, arg);
		arrExpr.idx.accept(this, arg);

		final TYP.Type elemType = arrayElementType(arrExpr);
		final IMR.Expr offset = new IMR.BINOP(IMR.BINOP.Oper.MUL, requireExprIR(arrExpr.idx),
				new IMR.CONST(rawSizeOf(elemType)));
		final IMR.Expr addr = new IMR.BINOP(IMR.BINOP.Oper.ADD, addrOf(arrExpr.arrExpr), offset);

		putExprIR(arrExpr, memAt(addr, elemType));
		return null;
	}

	@Override
	public Object visit(final AST.AsgnExpr asgnExpr, final Object arg) {
		asgnExpr.fstExpr.accept(this, arg);
		asgnExpr.sndExpr.accept(this, arg);

		final Vector<IMR.Stmt> body = stmts();
		body.add(new IMR.MOVE(memAt(addrOf(asgnExpr.fstExpr), requireExprType(asgnExpr.fstExpr)),
				requireExprIR(asgnExpr.sndExpr)));

		putExprIR(asgnExpr, sexpr(body, new IMR.CONST(0)));
		return null;
	}

	@Override
	public Object visit(final AST.AtomExpr atomExpr, final Object arg) {
		putExprIR(atomExpr, valueOfAtom(atomExpr));
		return null;
	}

	@Override
	public Object visit(final AST.BinExpr binExpr, final Object arg) {
		binExpr.fstExpr.accept(this, arg);
		binExpr.sndExpr.accept(this, arg);

		putExprIR(binExpr, new IMR.BINOP(
			binOper(binExpr.oper), 
			requireExprIR(binExpr.fstExpr),
			requireExprIR(binExpr.sndExpr)
		));
		return null;
	}

	@Override
	public Object visit(final AST.CallExpr callExpr, final Object arg) {
		callExpr.funExpr.accept(this, arg);
		callExpr.argExprs.accept(this, arg);

		putExprIR(
			callExpr, 
			new IMR.CALL(
				requireExprIR(callExpr.funExpr), 
				callArgOffsets(callExpr), 
				callArgs(callExpr)
			)
		);
		return null;
	}

	@Override
	public Object visit(final AST.CastExpr castExpr, final Object arg) {
		castExpr.expr.accept(this, arg);
		putExprIR(castExpr, castTo(
			requireExprIR(castExpr.expr), 
			requireType(castExpr.type)
		));
		return null;
	}

	@Override
	public Object visit(final AST.CompExpr compExpr, final Object arg) {
		compExpr.recExpr.accept(this, arg);

		final AST.CompDefn compDefn = componentDefinition(compExpr);
		final MEM.Access access = requireAccess(compDefn);
		if (!(access instanceof MEM.RelAccess relAccess) || relAccess.depth != -1)
			throw new Report.InternalError();

		final IMR.Expr addr = addressWithOffset(addrOf(compExpr.recExpr), relAccess.offset);
		putExprIR(compExpr, memAt(addr, requireExprType(compExpr)));
		return null;
	}

	@Override
	public Object visit(final AST.NameExpr nameExpr, final Object arg) {
		final AST.Defn defn = SemAn.defAtAttr.get(nameExpr);
		if (defn == null)
			throw new Report.InternalError();

		if (defn instanceof AST.FunDefn funDefn)
			putExprIR(nameExpr, addressOfFunction(funDefn));
		else
			putExprIR(nameExpr, memAt(addressOfDefinition(defn), requireExprType(nameExpr)));

		return null;
	}

	@Override
	public Object visit(final AST.PfxExpr pfxExpr, final Object arg) {
		pfxExpr.subExpr.accept(this, arg);

		final IMR.Expr subExpr = requireExprIR(pfxExpr.subExpr);
		switch (pfxExpr.oper) {
		case ADD -> putExprIR(pfxExpr, subExpr);
		case SUB, NOT -> putExprIR(pfxExpr, new IMR.UNOP(unOper(pfxExpr.oper), subExpr));
		case PTR -> putExprIR(pfxExpr, addrOf(subExpr));
		}
		return null;
	}

	@Override
	public Object visit(final AST.SfxExpr sfxExpr, final Object arg) {
		sfxExpr.subExpr.accept(this, arg);
		putExprIR(sfxExpr, memAt(requireExprIR(sfxExpr.subExpr), requireExprType(sfxExpr)));
		return null;
	}

	@Override
	public Object visit(final AST.SizeExpr sizeExpr, final Object arg) {
		putExprIR(sizeExpr, new IMR.CONST(sizeOf(sizeExpr.type)));
		return null;
	}

	@Override
	public Object visit(final AST.Exprs exprs, final Object arg) {
		exprs.exprs.accept(this, arg);
		if (exprs.exprs.size() == 0)
			throw new Report.InternalError();

		final Vector<IMR.Stmt> body = stmts();
		for (int e = 0; e < exprs.exprs.size() - 1; e++)
			body.add(new IMR.ESTMT(requireExprIR(exprs.exprs.get(e))));

		putExprIR(exprs, sexpr(body, requireExprIR(exprs.exprs.last())));
		return null;
	}

	@Override
	public Object visit(final AST.IfThenExpr ifThenExpr, final Object arg) {
		ifThenExpr.condExpr.accept(this, arg);
		ifThenExpr.thenExpr.accept(this, arg);

		final MEM.Label thenLabel = new MEM.Label();
		final MEM.Label endLabel = new MEM.Label();

		final Vector<IMR.Stmt> body = stmts();
		body.add(new IMR.CJUMP(requireExprIR(ifThenExpr.condExpr), new IMR.NAME(thenLabel), new IMR.NAME(endLabel)));
		body.add(new IMR.LABEL(thenLabel));
		body.add(new IMR.ESTMT(requireExprIR(ifThenExpr.thenExpr)));
		body.add(new IMR.LABEL(endLabel));

		putExprIR(ifThenExpr, sexpr(body, new IMR.CONST(0)));
		return null;
	}

	@Override
	public Object visit(final AST.IfThenElseExpr ifThenElseExpr, final Object arg) {
		ifThenElseExpr.condExpr.accept(this, arg);
		ifThenElseExpr.thenExpr.accept(this, arg);
		ifThenElseExpr.elseExpr.accept(this, arg);

		final MEM.Label thenLabel = new MEM.Label();
		final MEM.Label elseLabel = new MEM.Label();
		final MEM.Label endLabel = new MEM.Label();

		final Vector<IMR.Stmt> body = stmts();
		body.add(new IMR.CJUMP(requireExprIR(ifThenElseExpr.condExpr), new IMR.NAME(thenLabel), new IMR.NAME(elseLabel)));
		body.add(new IMR.LABEL(thenLabel));
		body.add(new IMR.ESTMT(requireExprIR(ifThenElseExpr.thenExpr)));
		body.add(new IMR.JUMP(new IMR.NAME(endLabel)));
		body.add(new IMR.LABEL(elseLabel));
		body.add(new IMR.ESTMT(requireExprIR(ifThenElseExpr.elseExpr)));
		body.add(new IMR.LABEL(endLabel));

		putExprIR(ifThenElseExpr, sexpr(body, new IMR.CONST(0)));
		return null;
	}

	@Override
	public Object visit(final AST.WhileExpr whileExpr, final Object arg) {
		whileExpr.condExpr.accept(this, arg);
		whileExpr.expr.accept(this, arg);

		final MEM.Label condLabel = new MEM.Label();
		final MEM.Label bodyLabel = new MEM.Label();
		final MEM.Label endLabel = new MEM.Label();

		final Vector<IMR.Stmt> body = stmts();
		body.add(new IMR.LABEL(condLabel));
		body.add(new IMR.CJUMP(requireExprIR(whileExpr.condExpr), new IMR.NAME(bodyLabel), new IMR.NAME(endLabel)));
		body.add(new IMR.LABEL(bodyLabel));
		body.add(new IMR.ESTMT(requireExprIR(whileExpr.expr)));
		body.add(new IMR.JUMP(new IMR.NAME(condLabel)));
		body.add(new IMR.LABEL(endLabel));

		putExprIR(whileExpr, sexpr(body, new IMR.CONST(0)));
		return null;
	}

	@Override
	public Object visit(final AST.LetExpr letExpr, final Object arg) {
		letExpr.defns.accept(this, arg);
		letExpr.expr.accept(this, arg);
		putExprIR(letExpr, requireExprIR(letExpr.expr));
		return null;
	}

}
