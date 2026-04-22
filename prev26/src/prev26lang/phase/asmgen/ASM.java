package prev26lang.phase.asmgen;

import java.util.Vector;

import prev26lang.phase.memory.*;

/**
 * Assembly-code data structures.
 */
public class ASM {

	/**
	 * A chunk of assembly code belonging to one function.
	 */
	public static class CodeChunk {

		/** The frame of the function this code belongs to. */
		public final MEM.Frame frame;

		/** The generated instructions. */
		private final Vector<Instruction> instructions;

		/**
		 * Constructs a code chunk.
		 */
		public CodeChunk(final MEM.Frame frame, final Vector<Instruction> instructions) {
			this.frame = frame;
			this.instructions = new Vector<Instruction>(instructions);
		}

		/**
		 * Returns a defensive copy of the instructions.
		 */
		public Vector<Instruction> instructions() {
			return new Vector<Instruction>(instructions);
		}
	}

	/**
	 * One assembly instruction with temporary-variable metadata.
	 */
	public static class Instruction {

		/**
		 * Instruction template. Output temps are written as *d0, *d1, ..., input temps
		 * as *s0, *s1, ..., and labels as *l0, *l1, ...
		 */
		public final String instruction;

		/** Temporaries read by this instruction. */
		public final Vector<MEM.Temp> input;

		/** Temporaries defined by this instruction. */
		public final Vector<MEM.Temp> output;

		/** Labels this instruction can jump to. */
		public final Vector<MEM.Label> label;

		/** True if this instruction only copies one temporary to another. */
		public final boolean isMove;

		/**
		 * Constructs an instruction.
		 */
		public Instruction(
			final String instruction,
			final Vector<MEM.Temp> output,
			final Vector<MEM.Temp> input,
			final Vector<MEM.Label> label,
			final boolean isMove
		) {
			this.instruction = instruction;
			this.output = new Vector<MEM.Temp>(output);
			this.input = new Vector<MEM.Temp>(input);
			this.label = new Vector<MEM.Label>(label);
			this.isMove = isMove;
		}

		/**
		 * Constructs a non-move instruction.
		 */
		public Instruction(
			final String instruction,
			final Vector<MEM.Temp> output,
			final Vector<MEM.Temp> input,
			final Vector<MEM.Label> label
		) {
			this(instruction, output, input, label, false);
		}

		/** Returns a defensive copy of the used temporaries. */
		public Vector<MEM.Temp> inputs() {
			return new Vector<MEM.Temp>(input);
		}

		/** Returns a defensive copy of the defined temporaries. */
		public Vector<MEM.Temp> outputs() {
			return new Vector<MEM.Temp>(output);
		}

		/** Returns a defensive copy of the possible jump labels. */
		public Vector<MEM.Label> labels() {
			return new Vector<MEM.Label>(label);
		}

		/**
		 * Formats this instruction with temporary names.
		 */
		public String format() {
			final StringBuilder formatted = new StringBuilder();

			for (int i = 0; i < instruction.length(); i++) {
				if ((instruction.charAt(i) != '*') || (i + 2 >= instruction.length())) {
					formatted.append(instruction.charAt(i));
					continue;
				}

				final char kind = instruction.charAt(i + 1);
				if ((kind != 'd') && (kind != 's') && (kind != 'l')) {
					formatted.append(instruction.charAt(i));
					continue;
				}

				int j = i + 2;
				while ((j < instruction.length()) && Character.isDigit(instruction.charAt(j)))
					j++;
				if (j == i + 2) {
					formatted.append(instruction.charAt(i));
					continue;
				}

				final int index = Integer.parseInt(instruction.substring(i + 2, j));
				formatted.append(formatPlaceholder(kind, index));
				i = j - 1;
			}

			return formatted.toString();
		}

		/**
		 * Formats one placeholder.
		 */
		private String formatPlaceholder(final char kind, final int index) {
			return switch (kind) {
			case 'd' -> formatTemp(output, index);
			case 's' -> formatTemp(input, index);
			case 'l' -> formatLabel(index);
			default -> throw new IllegalArgumentException();
			};
		}

		/**
		 * Formats one temporary.
		 */
		private String formatTemp(final Vector<MEM.Temp> temps, final int index) {
			if (index < 0 || index >= temps.size())
				return "*?" + index;

			return temps.get(index).toString();
		}

		/**
		 * Formats one label.
		 */
		private String formatLabel(final int index) {
			if (index < 0 || index >= label.size())
				return "*?" + index;
			return label.get(index).name;
		}

		@Override
		public String toString() {
			return format();
		}
	}

	/**
	 * Convenience constructor for ordinary non-move instructions.
	 */
	public static Instruction oper(
		final String instruction,
		final Vector<MEM.Temp> output,
		final Vector<MEM.Temp> input,
		final Vector<MEM.Label> label
	) {
		return new Instruction(instruction, output, input, label, false);
	}

	/**
	 * Convenience constructor for pure temporary-copy instructions.
	 */
	public static Instruction move(final String instruction, final MEM.Temp dst, final MEM.Temp src) {
		final Vector<MEM.Temp> output = new Vector<MEM.Temp>();
		final Vector<MEM.Temp> input = new Vector<MEM.Temp>();
		output.add(dst);
		input.add(src);
		return new Instruction(instruction, output, input, new Vector<MEM.Label>(), true);
	}

	/**
	 * Convenience constructor for labels placed in the instruction stream.
	 */
	public static Instruction label(final MEM.Label label) {
		final Vector<MEM.Label> labels = new Vector<MEM.Label>();
		labels.add(label);
		return new Instruction("*l0:", new Vector<MEM.Temp>(), new Vector<MEM.Temp>(), labels, false);
	}
}
