package prev26lang.phase.imrlin;


import prev26lang.*;
import prev26lang.common.logger.*;
import prev26lang.common.report.*;
import prev26lang.phase.*;
import prev26lang.phase.abstr.*;
import prev26lang.phase.imrgen.*;
import prev26lang.phase.memory.*;

/**
 * Intermediate representation linarization phase.
 * 
 * @author bostjan.slivnik@fri.uni-lj.si
 */
public class ImrLin extends Phase {
	/**
	 * Phase construction.
	 */
	public ImrLin() {
		super("imrlin");
	}

	// ===== LOGGER =====

	/**
	 * Logger of the intermediate representation.
	 */
	public static class Logger extends Memory.Logger {

		/**
		 * Constructs a new intermediate code (generated) visitor.
		 * 
		 * @param xmlLogger The underlying XML logger.
		 */
		public Logger(final XMLLogger xmlLogger) {
			super(xmlLogger);
		}

		/**
		 * Logs all attributes of a node.
		 * 
		 * @param node The node.
		 */
		@Override
		public void logAttrs(final XMLLogger xmlLogger, final AST.Node node) {
			super.logAttrs(xmlLogger, node);
			if (node instanceof AST.DefFunDefn) {
				xmlLogger.begElement("genimr");
				xmlLogger.begElement("entrylabel");
				switch (ImrGen.bodyEntryLabelAttr.get(node)) {
				case null -> {
					if (!Compiler.devMode()) {
						throw new Report.InternalError();
					}
				}
				case final MEM.Label label -> {
					xmlLogger.addAttribute("label", label.name);
				}
				}
				xmlLogger.endElement();
				xmlLogger.endElement();
			}
			if (node instanceof AST.DefFunDefn) {
				xmlLogger.begElement("genimr");
				xmlLogger.begElement("exitlabel");
				switch (ImrGen.bodyExitLabelAttr.get(node)) {
				case null -> {
					if (!Compiler.devMode()) {
						throw new Report.InternalError();
					}
				}
				case final MEM.Label label -> {
					xmlLogger.addAttribute("label", label.name);
				}
				}
				xmlLogger.endElement();
				xmlLogger.endElement();
			}
			if (node instanceof AST.Expr) {
				switch (ImrGen.genExprIMRAttr.get(node)) {
				case null -> {
					if (Compiler.devMode()) {
						xmlLogger.begElement("genimr");
						xmlLogger.addAttribute("none", "");
						xmlLogger.endElement();
					} else
						throw new Report.InternalError();
				}
				case final IMR.Expr genIMR -> {
					genIMR.log(xmlLogger);
				}
				}
			}
			if (node instanceof AST.DefFunDefn) {
				switch (ImrGen.genStmtIMRAttr.get(node)) {
				case null -> {
					if (Compiler.devMode()) {
						xmlLogger.begElement("genimr");
						xmlLogger.addAttribute("none", "");
						xmlLogger.endElement();
					} else
						throw new Report.InternalError();
				}
				case final IMR.Stmt genIMR -> {
					genIMR.log(xmlLogger);
				}
				}
			}
		}

	}

}
