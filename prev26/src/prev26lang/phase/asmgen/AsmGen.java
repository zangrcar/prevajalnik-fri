package prev26lang.phase.asmgen;


import prev26lang.common.logger.*;
import prev26lang.phase.*;
import prev26lang.phase.memory.*;

/**
 * Assembly code generation phase.
 */
public class AsmGen extends Phase {
	/**
	 * Phase construction.
	 */
	public AsmGen() {
		super("asmgen");
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

	}

}