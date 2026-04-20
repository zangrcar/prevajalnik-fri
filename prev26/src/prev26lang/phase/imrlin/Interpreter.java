package prev26lang.phase.imrlin;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import prev26lang.common.report.Report;
import prev26lang.phase.memory.*;
import prev26lang.phase.imrgen.*;

/**
 * Interpreter - for testing purposes only.
 */
public class Interpreter {

	private Scanner scanner = new Scanner(System.in);

	private boolean debug = false;

	private Random random;

	private HashMap<Long, Byte> memory;

	private HashMap<MEM.Temp, Long> temps;

	private HashMap<MEM.Label, Long> dataMemLabels;

	private HashMap<String, Long> dataMemLabelsByName;

	private HashMap<MEM.Label, Long> codeMemLabels;

	private HashMap<String, Long> codeMemLabelsByName;

	private HashMap<Long, MEM.Label> codeAddrLabels;

	private HashMap<MEM.Label, Integer> jumpMemLabels;

	private HashMap<MEM.Label, LIN.CodeChunk> callMemLabels;

	private HashMap<String, LIN.CodeChunk> callMemLabelsByName;

	private long nextCodeAddress;

	private MEM.Temp SP;

	private MEM.Temp FP;

	private MEM.Temp RV;

	private MEM.Temp HP;

	public Interpreter(Vector<LIN.DataChunk> dataChunks, Vector<LIN.CodeChunk> codeChunks) {
		random = new Random();

		this.memory = new HashMap<Long, Byte>();
		this.temps = new HashMap<MEM.Temp, Long>();

		SP = new MEM.Temp();
		tempST(SP, 0x7FFFFFFFFFFFFFF8l);
		HP = new MEM.Temp();
		tempST(HP, 0x2000000000000000l);

		this.dataMemLabels = new HashMap<MEM.Label, Long>();
		this.dataMemLabelsByName = new HashMap<String, Long>();
		for (LIN.DataChunk dataChunk : dataChunks) {
			if (debug) {
				System.out.printf("### %s @ %d\n", dataChunk.label.name, tempLD(HP, false));
			}
			this.dataMemLabels.put(dataChunk.label, tempLD(HP, false));
			this.dataMemLabelsByName.put(dataChunk.label.name, tempLD(HP, false));
			if (dataChunk.init != null)
				storeStringLiteral(dataChunk);
			tempST(HP, tempLD(HP, false) + dataChunk.size, debug);
		}
		if (debug)
			System.out.printf("###\n");

		this.jumpMemLabels = new HashMap<MEM.Label, Integer>();
		this.callMemLabels = new HashMap<MEM.Label, LIN.CodeChunk>();
		this.callMemLabelsByName = new HashMap<String, LIN.CodeChunk>();
		this.codeMemLabels = new HashMap<MEM.Label, Long>();
		this.codeMemLabelsByName = new HashMap<String, Long>();
		this.codeAddrLabels = new HashMap<Long, MEM.Label>();
		this.nextCodeAddress = 0x1000000000000000l;
		for (LIN.CodeChunk codeChunk : codeChunks) {
			this.callMemLabels.put(codeChunk.frame.label, codeChunk);
			this.callMemLabelsByName.put(codeChunk.frame.label.name, codeChunk);
			codeAddress(codeChunk.frame.label);
			Vector<IMR.Stmt> stmts = codeChunk.stmts();
			for (int stmtOffset = 0; stmtOffset < stmts.size(); stmtOffset++) {
				if (stmts.get(stmtOffset) instanceof IMR.LABEL)
					jumpMemLabels.put(((IMR.LABEL) stmts.get(stmtOffset)).label, stmtOffset);
			}
		}
	}

	/**
	 * Stores a string literal as consecutive bytes.
	 */
	private void storeStringLiteral(LIN.DataChunk dataChunk) {
		final String init = dataChunk.init;

		if (init == null || init.length() < 2)
			throw new Report.InternalError();
		if (init.charAt(0) != '"' || init.charAt(init.length() - 1) != '"')
			throw new Report.InternalError();

		long offset = 0;
		int i = 1;
		final int end = init.length() - 1;

		while (i < end) {
			final char c = init.charAt(i);
			if (c != '\\') {
				memST1(tempLD(HP, false) + offset, (long) c, false);
				offset++;
				i++;
				continue;
			}

			if (i + 1 >= end)
				throw new Report.InternalError();

			final char esc = init.charAt(i + 1);
			switch (esc) {
			case '"', '\\' -> {
				memST1(tempLD(HP, false) + offset, (long) esc, false);
				offset++;
				i += 2;
			}
			case 'x' -> {
				if (i + 3 >= end)
					throw new Report.InternalError();
				final int value = hexValue(init.charAt(i + 2)) * 16 + hexValue(init.charAt(i + 3));
				memST1(tempLD(HP, false) + offset, (long) value, false);
				offset++;
				i += 4;
			}
			default -> throw new Report.InternalError();
			}
		}

		if (offset != dataChunk.size)
			throw new Report.InternalError();
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

	private Long codeAddress(MEM.Label label) {
		Long address = codeMemLabelsByName.get(label.name);
		if (address == null) {
			address = nextCodeAddress;
			nextCodeAddress += 8;
			codeMemLabelsByName.put(label.name, address);
			codeAddrLabels.put(address, label);
		}
		codeMemLabels.put(label, address);
		return address;
	}

	private Long labelAddress(MEM.Label label) {
		Long address = dataMemLabels.get(label);
		if (address != null)
			return address;
		address = dataMemLabelsByName.get(label.name);
		if (address != null)
			return address;
		address = codeMemLabels.get(label);
		if (address != null)
			return address;
		address = codeMemLabelsByName.get(label.name);
		if (address != null)
			return address;
		return codeAddress(label);
	}

	private MEM.Label labelOf(IMR.Expr addr) {
		if (addr instanceof IMR.NAME name)
			return name.label;
		Long address = addr.accept(new ExprInterpreter(), null);
		MEM.Label label = codeAddrLabels.get(address);
		if (label == null)
			throw new Report.InternalError();
		return label;
	}

	private LIN.CodeChunk codeChunk(MEM.Label label) {
		LIN.CodeChunk chunk = callMemLabels.get(label);
		if (chunk == null)
			chunk = callMemLabelsByName.get(label.name);
		if (chunk == null)
			throw new Report.InternalError();
		return chunk;
	}

	private Long callArgLD(IMR.CALL call, int argIndex) {
		if (argIndex < 0 || argIndex >= call.offs.size())
			throw new Report.InternalError();
		return memLD(tempLD(SP, false) + call.offs.get(argIndex), false);
	}

	private void memST(Long address, Long value) {
		memST(address, value, debug);
	}

	private void memST(Long address, Long value, boolean debug) {
		if (debug)
			System.out.printf("### [%d] <- %d\n", address, value);
		for (int b = 0; b <= 7; b++) {
			long longval = value % 0x100;
			byte byteval = (byte) longval;
			memory.put(address + b, byteval);
			value = value >> 8;
		}
	}

	private void memST1(Long address, Long value) {
		memST1(address, value, debug);
	}

	private void memST1(Long address, Long value, boolean debug) {
		Long byteValue = value & 0xFFL;
		if (debug)
			System.out.printf("### [%d] <- %d\n", address, byteValue);
		memory.put(address, (byte) (long) byteValue);
	}

	private Long memLD(Long address) {
		return memLD(address, debug);
	}

	private Long memLD(Long address, boolean debug) {
		Long value = 0L;
		for (int b = 7; b >= 0; b--) {
			Byte byteval = memory.get(address + b);
			if (byteval == null) {
				byteval = (byte) (random.nextLong() / 0x100);
				// throw new Report.Error("INTERPRETER: Uninitialized memory location " +
				// (address + b) + ".");
			}
			long longval = (long) byteval;
			value = (value * 0x100) + (longval < 0 ? longval + 0x100 : longval);
		}
		if (debug)
			System.out.printf("### %d <- [%d]\n", value, address);
		return value;
	}

	private Long memLD1(Long address) {
		return memLD1(address, debug);
	}

	private Long memLD1(Long address, boolean debug) {
		Byte byteval = memory.get(address);
		if (byteval == null)
			byteval = (byte) (random.nextLong() / 0x100);
		long value = (long) byteval;
		value = value < 0 ? value + 0x100 : value;
		if (debug)
			System.out.printf("### %d <- [%d]\n", value, address);
		return value;
	}

	private void tempST(MEM.Temp temp, Long value) {
		tempST(temp, value, debug);
	}

	private void tempST(MEM.Temp temp, Long value, boolean debug) {
		temps.put(temp, value);
		if (debug) {
			if (temp == SP) {
				System.out.printf("### SP <- %d\n", value);
				return;
			}
			if (temp == FP) {
				System.out.printf("### FP <- %d\n", value);
				return;
			}
			if (temp == RV) {
				System.out.printf("### RV <- %d\n", value);
				return;
			}
			if (temp == HP) {
				System.out.printf("### HP <- %d\n", value);
				return;
			}
			System.out.printf("### T%d <- %d\n", temp.temp, value);
			return;
		}
	}

	private Long tempLD(MEM.Temp temp) {
		return tempLD(temp, debug);
	}

	private Long tempLD(MEM.Temp temp, boolean debug) {
		Long value = temps.get(temp);
		if (value == null) {
			value = random.nextLong();
			throw new Report.Error("Uninitialized temporary variable T" + temp.temp + ".");
		}
		if (debug) {
			if (temp == SP) {
				System.out.printf("### %d <- SP\n", value);
				return value;
			}
			if (temp == FP) {
				System.out.printf("### %d <- FP\n", value);
				return value;
			}
			if (temp == RV) {
				System.out.printf("### %d <- RV\n", value);
				return value;
			}
			if (temp == HP) {
				System.out.printf("### %d <- HP\n", value);
				return value;
			}
			System.out.printf("### %d <- T%d\n", value, temp.temp);
			return value;
		}
		return value;
	}

	private class ExprInterpreter implements IMR.Visitor<Long, Object> {

		@Override
		public Long visit(IMR.BINOP imrBinop, Object arg) {
			Long fstExpr = imrBinop.fstExpr.accept(this, null);
			Long sndExpr = imrBinop.sndExpr.accept(this, null);
			switch (imrBinop.oper) {
			case OR:
				return (fstExpr != 0) | (sndExpr != 0) ? 1L : 0L;
			case AND:
				return (fstExpr != 0) & (sndExpr != 0) ? 1L : 0L;
			case EQU:
				return (fstExpr == sndExpr) ? 1L : 0L;
			case NEQ:
				return (fstExpr != sndExpr) ? 1L : 0L;
			case LEQ:
				return (fstExpr <= sndExpr) ? 1L : 0L;
			case GEQ:
				return (fstExpr >= sndExpr) ? 1L : 0L;
			case LTH:
				return (fstExpr < sndExpr) ? 1L : 0L;
			case GTH:
				return (fstExpr > sndExpr) ? 1L : 0L;
			case ADD:
				return fstExpr + sndExpr;
			case SUB:
				return fstExpr - sndExpr;
			case MUL:
				return fstExpr * sndExpr;
			case DIV:
				return fstExpr / sndExpr;
			case MOD:
				return fstExpr % sndExpr;
			}
			throw new Report.InternalError();
		}

		@Override
		public Long visit(IMR.CALL imrCall, Object arg) {
			throw new Report.InternalError();
		}

		@Override
		public Long visit(IMR.CONST imrConst, Object arg) {
			return imrConst.value;
		}

		@Override
		public Long visit(IMR.MEM1 imrMem, Object arg) {
			return memLD1(imrMem.addr.accept(this, null));
		}

		@Override
		public Long visit(IMR.MEM8 imrMem, Object arg) {
			return memLD(imrMem.addr.accept(this, null));
		}

		@Override
		public Long visit(IMR.NAME imrName, Object arg) {
			return labelAddress(imrName.label);
		}

		@Override
		public Long visit(IMR.SEXPR imrSExpr, Object arg) {
			throw new Report.InternalError();
		}

		@Override
		public Long visit(IMR.TEMP imrMemTemp, Object arg) {
			return tempLD(imrMemTemp.temp);
		}

		@Override
		public Long visit(IMR.UNOP imrUnop, Object arg) {
			Long subExpr = imrUnop.subExpr.accept(this, null);
			switch (imrUnop.oper) {
			case NOT:
				return (subExpr == 0) ? 1L : 0L;
			case NEG:
				return -subExpr;
			}
			throw new Report.InternalError();
		}

	}

	private class StmtInterpreter implements IMR.Visitor<MEM.Label, Object> {

		@Override
		public MEM.Label visit(IMR.CJUMP imrCJump, Object arg) {
			if (debug)
				System.out.println(imrCJump);
			Long cond = imrCJump.cond.accept(new ExprInterpreter(), null);
			return labelOf((cond != 0) ? imrCJump.posAddr : imrCJump.negAddr);
		}

		@Override
		public MEM.Label visit(IMR.ESTMT imrEStmt, Object arg) {
			if (debug)
				System.out.println(imrEStmt);
			if (imrEStmt.expr instanceof IMR.CALL) {
				call((IMR.CALL) imrEStmt.expr);
				return null;
			}
			imrEStmt.expr.accept(new ExprInterpreter(), null);
			return null;
		}

		@Override
		public MEM.Label visit(IMR.JUMP imrJump, Object arg) {
			if (debug)
				System.out.println(imrJump);
			return labelOf(imrJump.addr);
		}

		@Override
		public MEM.Label visit(IMR.LABEL imrMemLabel, Object arg) {
			if (debug)
				System.out.println(imrMemLabel);
			return null;
		}

		@Override
		public MEM.Label visit(IMR.MOVE imrMove, Object arg) {
			if (debug)
				System.out.println(imrMove);
			if (imrMove.dst instanceof IMR.MEM1) {
				Long dst = ((IMR.MEM1) (imrMove.dst)).addr.accept(new ExprInterpreter(), null);
				Long src;
				if (imrMove.src instanceof IMR.CALL) {
					call((IMR.CALL) imrMove.src);
					src = memLD(tempLD(SP));
				} else
					src = imrMove.src.accept(new ExprInterpreter(), null);
				memST1(dst, src);
				return null;
			}
			if (imrMove.dst instanceof IMR.MEM8) {
				Long dst = ((IMR.MEM8) (imrMove.dst)).addr.accept(new ExprInterpreter(), null);
				Long src;
				if (imrMove.src instanceof IMR.CALL) {
					call((IMR.CALL) imrMove.src);
					src = memLD(tempLD(SP));
				} else
					src = imrMove.src.accept(new ExprInterpreter(), null);
				memST(dst, src);
				return null;
			}
			if (imrMove.dst instanceof IMR.TEMP) {
				IMR.TEMP dst = (IMR.TEMP) (imrMove.dst);
				Long src;
				if (imrMove.src instanceof IMR.CALL) {
					call((IMR.CALL) imrMove.src);
					src = memLD(tempLD(SP));
				} else
					src = imrMove.src.accept(new ExprInterpreter(), null);
				tempST(dst.temp, src);
				return null;
			}
			throw new Report.InternalError();
		}

		@Override
		public MEM.Label visit(IMR.STMTS imrStmts, Object arg) {
			if (debug)
				System.out.println(imrStmts);
			throw new Report.InternalError();
		}

		private void call(IMR.CALL imrCall) {
			if (imrCall.args.size() != imrCall.offs.size())
				throw new Report.InternalError();
			for (int a = 0; a < imrCall.args.size(); a++) {
				IMR.Expr callArg = imrCall.args.get(a);
				Long callValue = callArg.accept(new ExprInterpreter(), null);
				memST(tempLD(SP) + imrCall.offs.get(a), callValue);
			}
			MEM.Label callLabel = labelOf(imrCall.addr);
			if (callLabel.name.equals("_new")) {
				Long size = callArgLD(imrCall, 1);
				Long addr = tempLD(HP);
				tempST(HP, addr + size);
				memST(tempLD(SP), addr, false);
				return;
			}
			if (callLabel.name.equals("_del")) {
				return;
			}
			if (callLabel.name.equals("_exit")) {
				System.exit(1);
			}
			if (callLabel.name.equals("_putint")) {
				Long c = callArgLD(imrCall, 1);
				System.out.printf("%d", c);
				return;
			}
			if (callLabel.name.equals("_getint")) {
				Long l = scanner.nextLong();
				memST(tempLD(SP), (long) l, false);
				return;
			}
			if (callLabel.name.equals("_putchar")) {
				Long c = callArgLD(imrCall, 1);
				System.out.printf("%c", (char) ((long) c) % 0x100);
				return;
			}
			if (callLabel.name.equals("_getchar")) {
				char c = '\n';
				try {
					c = (char) System.in.read();
				} catch (Exception __) {
				}
				memST(tempLD(SP), (long) c, false);
				return;
			}
			funCall(callLabel);
		}

	}

	public void funCall(MEM.Label entryMemLabel) {

		HashMap<MEM.Temp, Long> storedMemTemps;
		MEM.Temp storedFP = FP;
		MEM.Temp storedRV = RV;

		LIN.CodeChunk chunk = codeChunk(entryMemLabel);
		MEM.Frame frame = chunk.frame;
		Vector<IMR.Stmt> stmts = chunk.stmts();
		int stmtOffset;

		/* PROLOGUE */
		{
			if (debug)
				System.out.printf("###\n### CALL: %s\n", entryMemLabel.name);

			// Store registers and FP.
			storedMemTemps = temps;
			temps = new HashMap<MEM.Temp, Long>(temps);
			// Store RA.
			// Create a stack frame.
			FP = frame.FP;
			RV = frame.RV;
			tempST(frame.FP, tempLD(SP));
			tempST(SP, tempLD(SP) - frame.size);
			// Jump to the body.
			stmtOffset = jumpMemLabels.get(chunk.entryLabel);
		}

		/* BODY */
		{
			int pc = 0;
			MEM.Label label = null;

			while (label != chunk.exitLabel) {
				if (debug) {
					pc++;
					System.out.printf("### %s (%d):\n", chunk.frame.label.name, pc);
					if (pc == 1000000)
						break;
				}

				if (label != null) {
					Integer offset = jumpMemLabels.get(label);
					if (offset == null) {
						throw new Report.InternalError();
					}
					stmtOffset = offset;
				}

				label = stmts.get(stmtOffset).accept(new StmtInterpreter(), null);

				stmtOffset += 1;
			}
		}

		/* EPILOGUE */
		{
			// Store the result.
			memST(tempLD(frame.FP), tempLD(frame.RV));
			// Destroy a stack frame.
			tempST(SP, tempLD(SP) + frame.size);
			// Restore registers and FP.
			FP = storedFP;
			RV = storedRV;
			Long hp = tempLD(HP);
			temps = storedMemTemps;
			tempST(HP, hp);
			// Restore RA.
			// Return.

			if (debug)
				System.out.printf("### RETURN: %s\n###\n", entryMemLabel.name);
		}

	}

	public long run(String entryMemLabel) {
		for (MEM.Label label : callMemLabels.keySet()) {
			if (label.name.equals(entryMemLabel)) {
				funCall(label);
				return memLD(tempLD(SP));
			}
		}
		throw new Report.InternalError();
	}

}
