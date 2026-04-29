package prev26lang.phase.livean;

import java.util.*;

import prev26lang.phase.asmgen.*;
import prev26lang.phase.memory.*;

/**
 * Liveness-analysis data structures.
 */
public class LIV {

	/**
	 * Liveness information of one assembly-code chunk.
	 */
	public static class CodeChunkAnal {

		/** Instructions of the analyzed code chunk. */
		private final Vector<ASM.Instruction> instructions;

		/** Temporaries used by each instruction. */
		private final Vector<HashSet<MEM.Temp>> use;

		/** Temporaries defined by each instruction. */
		private final Vector<HashSet<MEM.Temp>> def;

		/** Temporaries live immediately before each instruction. */
		private final Vector<HashSet<MEM.Temp>> in;

		/** Temporaries live immediately after each instruction. */
		private final Vector<HashSet<MEM.Temp>> out;

		/**
		 * Constructs liveness information of one code chunk.
		 */
		public CodeChunkAnal(
			final Vector<ASM.Instruction> instructions,
			final Vector<HashSet<MEM.Temp>> use,
			final Vector<HashSet<MEM.Temp>> def,
			final Vector<HashSet<MEM.Temp>> in,
			final Vector<HashSet<MEM.Temp>> out
		) {
			this.instructions = new Vector<ASM.Instruction>(instructions);
			this.use = copySets(use);
			this.def = copySets(def);
			this.in = copySets(in);
			this.out = copySets(out);
		}

		/**
		 * Returns a defensive copy of all instructions.
		 */
		public Vector<ASM.Instruction> instructions() {
			return new Vector<ASM.Instruction>(instructions);
		}

		/**
		 * Returns a defensive copy of all use sets.
		 */
		public Vector<HashSet<MEM.Temp>> use() {
			return copySets(use);
		}

		/**
		 * Returns a defensive copy of all def sets.
		 */
		public Vector<HashSet<MEM.Temp>> def() {
			return copySets(def);
		}

		/**
		 * Returns a defensive copy of all in sets.
		 */
		public Vector<HashSet<MEM.Temp>> in() {
			return copySets(in);
		}

		/**
		 * Returns a defensive copy of all out sets.
		 */
		public Vector<HashSet<MEM.Temp>> out() {
			return copySets(out);
		}

		/**
		 * Creates a defensive copy of a vector of sets.
		 */
		private static Vector<HashSet<MEM.Temp>> copySets(final Vector<HashSet<MEM.Temp>> sets) {
			final Vector<HashSet<MEM.Temp>> copy = new Vector<HashSet<MEM.Temp>>();

			for (final HashSet<MEM.Temp> set : sets)
				copy.add(new HashSet<MEM.Temp>(set));

			return copy;
		}

	}

}
