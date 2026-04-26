package prev26lang.phase.asmgen;

import java.util.Vector;

import prev26lang.common.report.*;
import prev26lang.phase.imrgen.*;
import prev26lang.phase.imrlin.*;
import prev26lang.phase.memory.*;

/**
 * Generator of RV64IM assembly instructions from linearized intermediate code.
 */
public class AsmGenerator {

	/** Generated assembly data chunks. */
	private final Vector<ASM.DataChunk> dataChunks;

	/** Generated assembly code chunks. */
	private final Vector<ASM.CodeChunk> codeChunks;

	/** Instructions of the function currently being translated. */
	private Vector<ASM.Instruction> instructions;

	public AsmGenerator() {
		dataChunks = new Vector<ASM.DataChunk>();
		codeChunks = new Vector<ASM.CodeChunk>();
	}

	/**
	 * Generates assembly-code chunks from linearized intermediate-code chunks.
	 */
	public void generate(final Vector<LIN.DataChunk> dataChunks, final Vector<LIN.CodeChunk> codeChunks) {
		this.dataChunks.clear();
		this.codeChunks.clear();

		for (final LIN.DataChunk dataChunk : dataChunks) {
			this.dataChunks.add(generate(dataChunk));
			if (dataChunk.init != null)
				this.dataChunks.add(new ASM.DataChunk(8));
		}

		for (final LIN.CodeChunk codeChunk : codeChunks)
			this.codeChunks.add(generate(codeChunk));
	}

	/**
	 * Returns a defensive copy of all data chunks.
	 */
	public Vector<ASM.DataChunk> dataChunks() {
		return new Vector<ASM.DataChunk>(dataChunks);
	}

	/**
	 * Generates an assembly data chunk.
	 */
	private ASM.DataChunk generate(final LIN.DataChunk dataChunk) {
		if (dataChunk.init == null)
			return new ASM.DataChunk(dataChunk.label, dataChunk.size);

		final Vector<Long> bytes = decodeString(dataChunk.init);
		if (bytes.size() != dataChunk.size)
			throw new Report.InternalError();
		return new ASM.DataChunk(dataChunk.label, dataChunk.size, bytes);
	}

	/**
	 * Decodes a string literal into bytes.
	 */
	private Vector<Long> decodeString(final String init) {
		if (init == null || init.length() < 2)
			throw new Report.InternalError();
		if (init.charAt(0) != '"' || init.charAt(init.length() - 1) != '"')
			throw new Report.InternalError();

		final Vector<Long> bytes = new Vector<Long>();
		int i = 1;
		final int end = init.length() - 1;

		while (i < end) {
			final char c = init.charAt(i);
			if (c != '\\') {
				bytes.add((long) c);
				i++;
				continue;
			}

			if (i + 1 >= end)
				throw new Report.InternalError();

			final char esc = init.charAt(i + 1);
			switch (esc) {
			case '"', '\\' -> {
				bytes.add((long) esc);
				i += 2;
			}
			case 'x' -> {
				if (i + 3 >= end)
					throw new Report.InternalError();
				bytes.add((long) (hexValue(init.charAt(i + 2)) * 16 + hexValue(init.charAt(i + 3))));
				i += 4;
			}
			default -> throw new Report.InternalError();
			}
		}

		return bytes;
	}

	/**
	 * Converts one hexadecimal digit to its numeric value.
	 */
	private int hexValue(final char c) {
		if ('0' <= c && c <= '9')
			return c - '0';
		if ('A' <= c && c <= 'F')
			return 10 + c - 'A';
		if ('a' <= c && c <= 'f')
			return 10 + c - 'a';
		throw new Report.InternalError();
	}

	/**
	 * Returns a defensive copy of all generated code chunks.
	 */
	public Vector<ASM.CodeChunk> codeChunks() {
		return new Vector<ASM.CodeChunk>(codeChunks);
	}

	/**
	 * Generates the instruction stream for one function body.
	 */
	private ASM.CodeChunk generate(final LIN.CodeChunk codeChunk) {
		instructions = new Vector<ASM.Instruction>();

		for (final IMR.Stmt stmt : codeChunk.stmts())
			munchStmt(stmt);

		return new ASM.CodeChunk(codeChunk.frame, instructions);
	}

	/**
	 * Creates a temporary vector.
	 */
	private Vector<MEM.Temp> temps(final MEM.Temp... temps) {
		final Vector<MEM.Temp> vector = new Vector<MEM.Temp>();
		for (final MEM.Temp temp : temps)
			vector.add(temp);
		return vector;
	}

	/**
	 * Creates a label vector.
	 */
	private Vector<MEM.Label> labels(final MEM.Label... labels) {
		final Vector<MEM.Label> vector = new Vector<MEM.Label>();
		for (final MEM.Label label : labels)
			vector.add(label);
		return vector;
	}

	/**
	 * Emits one non-move instruction into the current function body.
	 */
	private void emit(
		final String instruction, 
		final Vector<MEM.Temp> output, 
		final Vector<MEM.Temp> input,
		final Vector<MEM.Label> label
	) {
		emit(new ASM.Instruction(instruction, output, input, label));
	}

	/**
	 * Emits one instruction into the current function body.
	 */
	private void emit(final ASM.Instruction instruction) {
		if (instructions == null)
			throw new Report.InternalError();
		instructions.add(instruction);
	}

	/**
	 * Returns true if a value fits into a signed 12-bit immediate field.
	 */
	private boolean isImm12(final long value) {
		return -2048 <= value && value <= 2047;
	}

	/**
	 * Reads a label from an intermediate-code name expression.
	 */
	private MEM.Label labelOf(final IMR.Expr expr) {
		if (expr instanceof IMR.NAME name)
			return name.label;
		throw new Report.InternalError();
	}

	/**
	 * Translates one canonical intermediate-code statement.
	 */
	private void munchStmt(final IMR.Stmt stmt) {
		if (stmt instanceof IMR.LABEL label) {
			emit(ASM.label(label.label));
			return;
		}

		if (stmt instanceof IMR.MOVE move) {
			munchMove(move);
			return;
		}

		if (stmt instanceof IMR.ESTMT eStmt) {
			munchExprAsStatement(eStmt.expr);
			return;
		}

		if (stmt instanceof IMR.JUMP jump) {
			munchJump(jump);
			return;
		}

		if (stmt instanceof IMR.CJUMP cjump) {
			munchCJump(cjump);
			return;
		}

		throw new Report.InternalError();
	}

	/**
	 * Translates a move statement.
	 */
	private void munchMove(final IMR.MOVE move) {
		if (move.dst instanceof IMR.TEMP dst) {
			munchExprInto(dst.temp, move.src);
			return;
		}

		if (move.dst instanceof IMR.MEM1 mem) {
			final MEM.Temp addr = munchExpr(mem.addr);
			final MEM.Temp src = munchExpr(move.src);
			emit("SB *s0, 0(*s1)", new Vector<MEM.Temp>(), temps(src, addr), new Vector<MEM.Label>());
			return;
		}

		if (move.dst instanceof IMR.MEM8 mem) {
			final MEM.Temp addr = munchExpr(mem.addr);
			final MEM.Temp src = munchExpr(move.src);
			emit("SD *s0, 0(*s1)", new Vector<MEM.Temp>(), temps(src, addr), new Vector<MEM.Label>());
			return;
		}

		throw new Report.InternalError();
	}

	/**
	 * Translates an unconditional jump.
	 */
	private void munchJump(final IMR.JUMP jump) {
		if (jump.addr instanceof IMR.NAME name) {
			emit("JAL x0, *l0", new Vector<MEM.Temp>(), new Vector<MEM.Temp>(), labels(name.label));
			return;
		}

		final MEM.Temp addr = munchExpr(jump.addr);
		emit("JALR x0, 0(*s0)", new Vector<MEM.Temp>(), temps(addr), new Vector<MEM.Label>());
	}

	/**
	 * Translates a conditional jump.
	 */
	private void munchCJump(final IMR.CJUMP cjump) {
		final MEM.Temp cond = munchExpr(cjump.cond);
		final MEM.Label posLabel = labelOf(cjump.posAddr);
		final MEM.Label negLabel = labelOf(cjump.negAddr);

		emit("BNE *s0, x0, *l0", new Vector<MEM.Temp>(), temps(cond), labels(posLabel, negLabel));
		emit("JAL x0, *l0", new Vector<MEM.Temp>(), new Vector<MEM.Temp>(), labels(negLabel));
	}

	/**
	 * Translates an expression used only for side effects.
	 */
	private void munchExprAsStatement(final IMR.Expr expr) {
		if (expr instanceof IMR.CALL call) {
			munchCall(call, null);
			return;
		}

		if (expr instanceof IMR.CONST || expr instanceof IMR.NAME || expr instanceof IMR.TEMP)
			return;

		munchExpr(expr);
	}

	/**
	 * Translates an expression and returns the temporary containing its value.
	 */
	private MEM.Temp munchExpr(final IMR.Expr expr) {
		if (expr instanceof IMR.TEMP temp)
			return temp.temp;

		final MEM.Temp dst = new MEM.Temp();
		munchExprInto(dst, expr);
		return dst;
	}

	/**
	 * Translates an expression into a requested destination temporary.
	 */
	private void munchExprInto(final MEM.Temp dst, final IMR.Expr expr) {
		if (expr instanceof IMR.CONST constant) {
			loadConst(dst, constant.value);
			return;
		}

		if (expr instanceof IMR.NAME name) {
			loadLabel(dst, name.label);
			return;
		}

		if (expr instanceof IMR.TEMP temp) {
			if (dst != temp.temp)
				emit(ASM.move("ADDI *d0, *s0, 0", dst, temp.temp));
			return;
		}

		if (expr instanceof IMR.BINOP binOp) {
			munchBinop(dst, binOp);
			return;
		}

		if (expr instanceof IMR.UNOP unOp) {
			munchUnop(dst, unOp);
			return;
		}

		if (expr instanceof IMR.MEM1 mem) {
			final MEM.Temp addr = munchExpr(mem.addr);
			emit("LBU *d0, 0(*s0)", temps(dst), temps(addr), new Vector<MEM.Label>());
			return;
		}

		if (expr instanceof IMR.MEM8 mem) {
			final MEM.Temp addr = munchExpr(mem.addr);
			emit("LD *d0, 0(*s0)", temps(dst), temps(addr), new Vector<MEM.Label>());
			return;
		}

		if (expr instanceof IMR.CALL call) {
			munchCall(call, dst);
			return;
		}

		throw new Report.InternalError();
	}

	/**
	 * Loads a 64-bit constant using only RV64I instructions.
	 */
	private void loadConst(final MEM.Temp dst, final long value) {
		if (isImm12(value)) {
			emit("ADDI *d0, x0, " + value, temps(dst), new Vector<MEM.Temp>(), new Vector<MEM.Label>());
			return;
		}

		boolean started = false;
		for (int shift = 56; shift >= 0; shift -= 8) {
			final long byteValue = (value >>> shift) & 0xFFL;
			if (!started) {
				if (byteValue == 0 && shift > 0)
					continue;
				emit("ADDI *d0, x0, " + byteValue, temps(dst), new Vector<MEM.Temp>(), new Vector<MEM.Label>());
				started = true;
				continue;
			}

			emit("SLLI *d0, *s0, 8", temps(dst), temps(dst), new Vector<MEM.Label>());
			if (byteValue != 0)
				emit("ADDI *d0, *s0, " + byteValue, temps(dst), temps(dst), new Vector<MEM.Label>());
		}

		if (!started)
			emit("ADDI *d0, x0, 0", temps(dst), new Vector<MEM.Temp>(), new Vector<MEM.Label>());
	}

	/**
	 * Loads a label address using RV64 relocation operands.
	 */
	private void loadLabel(final MEM.Temp dst, final MEM.Label label) {
		emit("LUI *d0, %hi(*l0)", temps(dst), new Vector<MEM.Temp>(), labels(label));
		emit("ADDI *d0, *s0, %lo(*l0)", temps(dst), temps(dst), labels(label));
	}

	/**
	 * Translates a binary operation.
	 */
	private void munchBinop(final MEM.Temp dst, final IMR.BINOP binOp) {
		final MEM.Temp fst = munchExpr(binOp.fstExpr);
		final MEM.Temp snd = munchExpr(binOp.sndExpr);

		switch (binOp.oper) {
		case ADD -> emit("ADD *d0, *s0, *s1", temps(dst), temps(fst, snd), new Vector<MEM.Label>());
		case SUB -> emit("SUB *d0, *s0, *s1", temps(dst), temps(fst, snd), new Vector<MEM.Label>());
		case MUL -> emit("MUL *d0, *s0, *s1", temps(dst), temps(fst, snd), new Vector<MEM.Label>());
		case DIV -> emit("DIV *d0, *s0, *s1", temps(dst), temps(fst, snd), new Vector<MEM.Label>());
		case MOD -> emit("REM *d0, *s0, *s1", temps(dst), temps(fst, snd), new Vector<MEM.Label>());
		case LTH -> emit("SLT *d0, *s0, *s1", temps(dst), temps(fst, snd), new Vector<MEM.Label>());
		case GTH -> emit("SLT *d0, *s1, *s0", temps(dst), temps(fst, snd), new Vector<MEM.Label>());
		case LEQ -> {
			final MEM.Temp tmp = new MEM.Temp();
			emit("SLT *d0, *s1, *s0", temps(tmp), temps(fst, snd), new Vector<MEM.Label>());
			emit("XORI *d0, *s0, 1", temps(dst), temps(tmp), new Vector<MEM.Label>());
		}
		case GEQ -> {
			final MEM.Temp tmp = new MEM.Temp();
			emit("SLT *d0, *s0, *s1", temps(tmp), temps(fst, snd), new Vector<MEM.Label>());
			emit("XORI *d0, *s0, 1", temps(dst), temps(tmp), new Vector<MEM.Label>());
		}
		case EQU -> {
			final MEM.Temp tmp = new MEM.Temp();
			emit("XOR *d0, *s0, *s1", temps(tmp), temps(fst, snd), new Vector<MEM.Label>());
			emit("SLTIU *d0, *s0, 1", temps(dst), temps(tmp), new Vector<MEM.Label>());
		}
		case NEQ -> {
			final MEM.Temp tmp = new MEM.Temp();
			emit("XOR *d0, *s0, *s1", temps(tmp), temps(fst, snd), new Vector<MEM.Label>());
			emit("SLTU *d0, x0, *s0", temps(dst), temps(tmp), new Vector<MEM.Label>());
		}
		case AND -> {
			final MEM.Temp leftBool = boolValue(fst);
			final MEM.Temp rightBool = boolValue(snd);
			emit("AND *d0, *s0, *s1", temps(dst), temps(leftBool, rightBool), new Vector<MEM.Label>());
		}
		case OR -> {
			final MEM.Temp leftBool = boolValue(fst);
			final MEM.Temp rightBool = boolValue(snd);
			emit("OR *d0, *s0, *s1", temps(dst), temps(leftBool, rightBool), new Vector<MEM.Label>());
		}
		}
	}

	/**
	 * Converts a value into 0 or 1.
	 */
	private MEM.Temp boolValue(final MEM.Temp value) {
		final MEM.Temp bool = new MEM.Temp();
		emit("SLTU *d0, x0, *s0", temps(bool), temps(value), new Vector<MEM.Label>());
		return bool;
	}

	/**
	 * Translates a unary operation.
	 */
	private void munchUnop(final MEM.Temp dst, final IMR.UNOP unOp) {
		final MEM.Temp sub = munchExpr(unOp.subExpr);

		switch (unOp.oper) {
		case NEG -> emit("SUB *d0, x0, *s0", temps(dst), temps(sub), new Vector<MEM.Label>());
		case NOT -> emit("SLTIU *d0, *s0, 1", temps(dst), temps(sub), new Vector<MEM.Label>());
		}
	}

	/**
	 * Translates a function call and optionally loads its return value.
	 */
	private void munchCall(final IMR.CALL call, final MEM.Temp dst) {
		for (int i = 0; i < call.args.size(); i++) {
			final MEM.Temp arg = munchExpr(call.args.get(i));
			storeToStack(arg, call.offs.get(i));
		}

		if (call.addr instanceof IMR.NAME name)
			emit("JAL *d0, *l0", temps(MEM.RA), new Vector<MEM.Temp>(), labels(name.label));
		else {
			final MEM.Temp addr = munchExpr(call.addr);
			emit("JALR *d0, 0(*s0)", temps(MEM.RA), temps(addr), new Vector<MEM.Label>());
		}

		if (dst != null)
			emit("LD *d0, 0(*s0)", temps(dst), temps(MEM.SP), new Vector<MEM.Label>());
	}

	/**
	 * Stores a temporary to the outgoing-argument area at SP + offset.
	 */
	private void storeToStack(final MEM.Temp src, final long offset) {
		if (isImm12(offset)) {
			emit("SD *s0, " + offset + "(*s1)", new Vector<MEM.Temp>(), temps(src, MEM.SP), new Vector<MEM.Label>());
			return;
		}

		final MEM.Temp offsetTemp = new MEM.Temp();
		final MEM.Temp addr = new MEM.Temp();
		loadConst(offsetTemp, offset);
		emit("ADD *d0, *s0, *s1", temps(addr), temps(MEM.SP, offsetTemp), new Vector<MEM.Label>());
		emit("SD *s0, 0(*s1)", new Vector<MEM.Temp>(), temps(src, addr), new Vector<MEM.Label>());
	}
}
