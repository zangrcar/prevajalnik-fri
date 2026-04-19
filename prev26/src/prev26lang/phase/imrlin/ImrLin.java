package prev26lang.phase.imrlin;


import prev26lang.common.logger.*;
import prev26lang.phase.*;
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

	}

}
