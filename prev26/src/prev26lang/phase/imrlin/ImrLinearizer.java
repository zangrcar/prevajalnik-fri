package prev26lang.phase.imrlin;

import java.util.HashSet;
import java.util.Vector;

import prev26lang.common.report.*;
import prev26lang.phase.abstr.*;
import prev26lang.phase.imrgen.*;
import prev26lang.phase.memory.*;

public class ImrLinearizer implements AST.FullVisitor<Object, Object> {

	private final Vector<LIN.DataChunk> dataChunks;

	private final Vector<LIN.CodeChunk> codeChunks;

	private final HashSet<String> dataLabels;

	public ImrLinearizer() {
		dataChunks = new Vector<LIN.DataChunk>();
		codeChunks = new Vector<LIN.CodeChunk>();
		dataLabels = new HashSet<String>();
	}

	/** Returns a defensive copy of all linearized data chunks. */
	public Vector<LIN.DataChunk> dataChunks() {
		return new Vector<LIN.DataChunk>(dataChunks);
	}

	/** Returns a defensive copy of all linearized code chunks. */
	public Vector<LIN.CodeChunk> codeChunks() {
		return new Vector<LIN.CodeChunk>(codeChunks);
	}

	/** Returns a defensive copy of all data labels. */
	public HashSet<String> dataLabels() {
		return new HashSet<String>(dataLabels);
	}

	/** Stores statements that must be executed before a canonical expression. */
	private static class CanonExpr {
		public final Vector<IMR.Stmt> stmts;
		public final IMR.Expr expr;

		public CanonExpr(final Vector<IMR.Stmt> stmts, final IMR.Expr expr) {
			this.stmts = stmts;
			this.expr = expr;
		}
	}

	/** Stores statements that must be executed before a canonical call. */
	private static class CanonCall {
		public final Vector<IMR.Stmt> stmts;
		public final IMR.CALL call;

		public CanonCall(final Vector<IMR.Stmt> stmts, final IMR.CALL call) {
			this.stmts = stmts;
			this.call = call;
		}
	}

	/** Creates an empty statement vector. */
	private Vector<IMR.Stmt> stmts() {
		return new Vector<IMR.Stmt>();
	}

	/** Reads the generated IMR body of a function or reports an internal phase error. */
	private IMR.Stmt requireBodyIMR(final AST.DefFunDefn defFunDefn) {
		final IMR.Stmt body = ImrGen.genStmtIMRAttr.get(defFunDefn);
		if (body == null)
			throw new Report.InternalError();
		return body;
	}

	/** Reads the memory frame of a function or reports an internal phase error. */
	private MEM.Frame requireFrame(final AST.DefFunDefn defFunDefn) {
		final MEM.Frame frame = Memory.frameAttr.get(defFunDefn);
		if (frame == null)
			throw new Report.InternalError();
		return frame;
	}

	/** Reads the body entry label of a function or reports an internal phase error. */
	private MEM.Label requireEntryLabel(final AST.DefFunDefn defFunDefn) {
		final MEM.Label label = ImrGen.bodyEntryLabelAttr.get(defFunDefn);
		if (label == null)
			throw new Report.InternalError();
		return label;
	}

	/** Reads the body exit label of a function or reports an internal phase error. */
	private MEM.Label requireExitLabel(final AST.DefFunDefn defFunDefn) {
		final MEM.Label label = ImrGen.bodyExitLabelAttr.get(defFunDefn);
		if (label == null)
			throw new Report.InternalError();
		return label;
	}

	/** Adds a data chunk once, keyed by its label name. */
	private void addDataChunk(final MEM.AbsAccess access) {
		if (access == null)
			throw new Report.InternalError();
		if (dataLabels.add(access.label.name))
			dataChunks.add(new LIN.DataChunk(access));
	}

	/** Returns true if the expression is already canonical and has no side effects. */
	private boolean isSimpleExpr(final IMR.Expr expr) {
		return expr instanceof IMR.CONST || 
			   expr instanceof IMR.NAME || 
			   expr instanceof IMR.TEMP;
	}

	/** Builds a byte or octa memory expression of the same kind with a new address. */
	private IMR.Expr memWithAddr(final IMR.Expr mem, final IMR.Expr addr) {
		if (mem instanceof IMR.MEM1)
			return new IMR.MEM1(addr);
		if (mem instanceof IMR.MEM8)
			return new IMR.MEM8(addr);
		throw new Report.InternalError();
	}

	/** Canonicalizes a call target and arguments while preserving left-to-right order. */
	private CanonCall canonCall(final IMR.CALL call) {
		final Vector<IMR.Stmt> prefix = stmts();
		final IMR.Expr callAddr;
		if (call.addr instanceof IMR.NAME) {
			callAddr = call.addr;
		} else {
			final CanonExpr addr = canonExpr(call.addr);
			prefix.addAll(addr.stmts);
			final MEM.Temp addrTemp = new MEM.Temp();
			prefix.add(new IMR.MOVE(new IMR.TEMP(addrTemp), addr.expr));
			callAddr = new IMR.TEMP(addrTemp);
		}

		final Vector<IMR.Expr> args = new Vector<IMR.Expr>();
		for (final IMR.Expr arg : call.args) {
			final CanonExpr canonArg = canonExpr(arg);
			prefix.addAll(canonArg.stmts);
			final MEM.Temp argTemp = new MEM.Temp();
			prefix.add(new IMR.MOVE(new IMR.TEMP(argTemp), canonArg.expr));
			args.add(new IMR.TEMP(argTemp));
		}

		return new CanonCall(prefix, new IMR.CALL(callAddr, call.offs, args));
	}

	/** Canonicalizes an expression and returns prefix statements plus the pure expression. */
	private CanonExpr canonExpr(final IMR.Expr expr) {
		if (expr == null)
			throw new Report.InternalError();

		if (isSimpleExpr(expr))
			return new CanonExpr(stmts(), expr);

		if (expr instanceof IMR.BINOP binOp) {
			final CanonExpr fstExpr = canonExpr(binOp.fstExpr);
			final CanonExpr sndExpr = canonExpr(binOp.sndExpr);
			final Vector<IMR.Stmt> prefix = stmts();
			prefix.addAll(fstExpr.stmts);
			final MEM.Temp fstTemp = new MEM.Temp();
			prefix.add(new IMR.MOVE(new IMR.TEMP(fstTemp), fstExpr.expr));
			prefix.addAll(sndExpr.stmts);
			final MEM.Temp sndTemp = new MEM.Temp();
			prefix.add(new IMR.MOVE(new IMR.TEMP(sndTemp), sndExpr.expr));
			return new CanonExpr(prefix, new IMR.BINOP(binOp.oper, new IMR.TEMP(fstTemp), new IMR.TEMP(sndTemp)));
		}

		if (expr instanceof IMR.UNOP unOp) {
			final CanonExpr subExpr = canonExpr(unOp.subExpr);
			return new CanonExpr(subExpr.stmts, new IMR.UNOP(unOp.oper, subExpr.expr, unOp.isInt));
		}

		if (expr instanceof IMR.MEM1 || expr instanceof IMR.MEM8) {
			final IMR.Expr addr = (expr instanceof IMR.MEM1 mem) ? mem.addr : ((IMR.MEM8) expr).addr;
			final CanonExpr canonAddr = canonExpr(addr);
			return new CanonExpr(canonAddr.stmts, memWithAddr(expr, canonAddr.expr));
		}

		if (expr instanceof IMR.SEXPR sExpr) {
			final Vector<IMR.Stmt> prefix = stmts();
			canonStmt(sExpr.stmt, prefix);
			final CanonExpr canonExpr = canonExpr(sExpr.expr);
			prefix.addAll(canonExpr.stmts);
			return new CanonExpr(prefix, canonExpr.expr);
		}

		if (expr instanceof IMR.CALL call) {
			final CanonCall canonCall = canonCall(call);
			final MEM.Temp temp = new MEM.Temp();
			final Vector<IMR.Stmt> prefix = stmts();
			prefix.addAll(canonCall.stmts);
			prefix.add(new IMR.MOVE(new IMR.TEMP(temp), canonCall.call));
			return new CanonExpr(prefix, new IMR.TEMP(temp));
		}

		throw new Report.InternalError();
	}

	/** Canonicalizes a statement and appends zero or more canonical statements to the output. */
	private void canonStmt(final IMR.Stmt stmt, final Vector<IMR.Stmt> output) {
		if (stmt == null || output == null)
			throw new Report.InternalError();

		if (stmt instanceof IMR.STMTS stmts) {
			for (final IMR.Stmt subStmt : stmts.stmts)
				canonStmt(subStmt, output);
			return;
		}

		if (stmt instanceof IMR.LABEL || 
			stmt instanceof IMR.JUMP || 
			stmt instanceof IMR.CJUMP || 
			stmt instanceof IMR.ESTMT || 
			stmt instanceof IMR.MOVE
		) {
			stmt.accept(new StmtCanonizer(output), null);
			return;
		}

		throw new Report.InternalError();
	}

	/** Converts statement nodes into canonical statements using the enclosing linearizer helpers. */
	private class StmtCanonizer implements IMR.Visitor<Object, Object> {
		private final Vector<IMR.Stmt> output;

		public StmtCanonizer(final Vector<IMR.Stmt> output) {
			this.output = output;
		}

		@Override
		public Object visit(final IMR.LABEL label, final Object arg) {
			output.add(label);
			return null;
		}

		@Override
		public Object visit(final IMR.JUMP jump, final Object arg) {
			final CanonExpr addr = canonExpr(jump.addr);
			output.addAll(addr.stmts);
			output.add(new IMR.JUMP(addr.expr));
			return null;
		}

		@Override
		public Object visit(final IMR.CJUMP cjump, final Object arg) {
			final CanonExpr cond = canonExpr(cjump.cond);
			final CanonExpr posAddr = canonExpr(cjump.posAddr);
			final CanonExpr negAddr = canonExpr(cjump.negAddr);
			output.addAll(cond.stmts);
			output.addAll(posAddr.stmts);
			output.addAll(negAddr.stmts);
			output.add(new IMR.CJUMP(cond.expr, posAddr.expr, negAddr.expr));
			return null;
		}

		@Override
		public Object visit(final IMR.ESTMT eStmt, final Object arg) {
			if (eStmt.expr instanceof IMR.CALL call) {
				final CanonCall canonCall = canonCall(call);
				output.addAll(canonCall.stmts);
				output.add(new IMR.ESTMT(canonCall.call));
				return null;
			}

			final CanonExpr expr = canonExpr(eStmt.expr);
			output.addAll(expr.stmts);
			output.add(new IMR.ESTMT(expr.expr));
			return null;
		}

		@Override
		public Object visit(final IMR.MOVE move, final Object arg) {
			if (move.dst instanceof IMR.TEMP temp) {
				canonMoveToTemp(temp, move.src);
				return null;
			}

			if (move.dst instanceof IMR.MEM1 || move.dst instanceof IMR.MEM8) {
				canonMoveToMem(move.dst, move.src);
				return null;
			}

			throw new Report.InternalError();
		}

		/** Canonicalizes a move whose destination is a temporary variable. */
		private void canonMoveToTemp(final IMR.TEMP dst, final IMR.Expr src) {
			if (src instanceof IMR.CALL call) {
				final CanonCall canonCall = canonCall(call);
				output.addAll(canonCall.stmts);
				output.add(new IMR.MOVE(dst, canonCall.call));
				return;
			}

			final CanonExpr canonSrc = canonExpr(src);
			output.addAll(canonSrc.stmts);
			output.add(new IMR.MOVE(dst, canonSrc.expr));
		}

		/** Canonicalizes a move whose destination is a memory location. */
		private void canonMoveToMem(final IMR.Expr dst, final IMR.Expr src) {
			final IMR.Expr addr = (dst instanceof IMR.MEM1 mem) ? mem.addr : ((IMR.MEM8) dst).addr;
			final CanonExpr canonAddr = canonExpr(addr);
			output.addAll(canonAddr.stmts);
			final MEM.Temp addrTemp = new MEM.Temp();
			output.add(new IMR.MOVE(new IMR.TEMP(addrTemp), canonAddr.expr));
			final IMR.Expr canonDst = memWithAddr(dst, new IMR.TEMP(addrTemp));

			if (src instanceof IMR.CALL call) {
				final CanonCall canonCall = canonCall(call);
				output.addAll(canonCall.stmts);
				output.add(new IMR.MOVE(canonDst, canonCall.call));
				return;
			}

			final CanonExpr canonSrc = canonExpr(src);
			output.addAll(canonSrc.stmts);
			output.add(new IMR.MOVE(canonDst, canonSrc.expr));
		}
	}

	/** Builds and stores a code chunk for one defined function. */
	private void addCodeChunk(final AST.DefFunDefn defFunDefn) {
		final Vector<IMR.Stmt> body = stmts();
		canonStmt(requireBodyIMR(defFunDefn), body);
		codeChunks.add(
			new LIN.CodeChunk(
				requireFrame(defFunDefn), 
				body, 
				requireEntryLabel(defFunDefn),
				requireExitLabel(defFunDefn)
			)
		);
	}

	@Override
	public Object visit(final AST.VarDefn varDefn, final Object arg) {
		final MEM.Access access = Memory.accessAttr.get(varDefn);
		if (access instanceof MEM.AbsAccess absAccess)
			addDataChunk(absAccess);
		return null;
	}

	@Override
	public Object visit(final AST.DefFunDefn defFunDefn, final Object arg) {
		addCodeChunk(defFunDefn);
		defFunDefn.expr.accept(this, arg);
		return null;
	}

	@Override
	public Object visit(final AST.AtomExpr atomExpr, final Object arg) {
		if (atomExpr.type == AST.AtomExpr.Type.STR)
			addDataChunk(Memory.stringAttr.get(atomExpr));
		return null;
	}

}
