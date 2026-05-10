package prev26lang.phase.asmgen;

import java.util.Vector;

import prev26lang.phase.memory.*;

/**
 * Assembly-code data structures.
 */
public class ASM {

	/**
	 * A chunk of static data.
	 */
	public static class DataChunk {

		/** Alignment directive emitted by this chunk, or 0 if this is not one. */
		private final long alignment;

		/** The label where data is placed, or null for an alignment directive. */
		public final MEM.Label label;

		/** The size of data in bytes. */
		public final long size;

		/** The initialized bytes, or an empty vector for zero-initialized storage. */
		private final Vector<Long> bytes;

		/**
		 * Constructs an alignment directive chunk.
		 */
		public DataChunk(final long alignment) {
			this(alignment, null, 0, new Vector<Long>());
		}

		/**
		 * Constructs an uninitialized data chunk.
		 */
		public DataChunk(final MEM.Label label, final long size) {
			this(0, label, size, new Vector<Long>());
		}

		/**
		 * Constructs an initialized data chunk.
		 */
		public DataChunk(final MEM.Label label, final long size, final Vector<Long> bytes) {
			this(0, label, size, bytes);
		}

		/**
		 * Constructs a data chunk.
		 */
		private DataChunk(final long alignment, final MEM.Label label, final long size, final Vector<Long> bytes) {
			this.alignment = alignment;
			this.label = label;
			this.size = size;
			this.bytes = new Vector<Long>(bytes);
		}

		/**
		 * Formats this data chunk as Ripes-compatible assembler directives.
		 */
		public Vector<String> format() {
			final Vector<String> lines = new Vector<String>();
			if (label == null) {
				lines.add(".align " + log2(alignment));
				return lines;
			}
			lines.add(label.name + ":");

			if (bytes.isEmpty()) {
				lines.add("  .space " + size);
				return lines;
			}

			final StringBuilder line = new StringBuilder("  .byte ");
			for (int i = 0; i < bytes.size(); i++) {
				if (i > 0)
					line.append(", ");
				line.append(bytes.get(i));
			}
			lines.add(line.toString());
			return lines;
		}

		/**
		 * Returns the exponent of a power-of-two alignment.
		 */
		private long log2(final long value) {
			long exponent = 0;
			long current = value;
			while (current > 1) {
				current /= 2;
				exponent++;
			}
			return exponent;
		}
	}

	/**
	 * A chunk of assembly code belonging to one function.
	 */
	public static class CodeChunk {

		/** The frame of the function this code belongs to. */
		public final MEM.Frame frame;

		/** Label at which the function body starts. */
		public final MEM.Label entryLabel;

		/** Label at which the function body exits into the epilogue. */
		public final MEM.Label exitLabel;

		/** The generated instructions. */
		private final Vector<Instruction> instructions;

		/**
		 * Constructs a code chunk.
		 */
		public CodeChunk(final MEM.Frame frame, final Vector<Instruction> instructions) {
			this(frame, null, null, instructions);
		}

		/**
		 * Constructs a code chunk.
		 */
		public CodeChunk(
			final MEM.Frame frame,
			final MEM.Label entryLabel,
			final MEM.Label exitLabel,
			final Vector<Instruction> instructions
		) {
			this.frame = frame;
			this.entryLabel = entryLabel;
			this.exitLabel = exitLabel;
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
	 * Control-flow behavior of an assembly instruction.
	 */
	public enum ControlFlow {
		/** Instruction continues with the following instruction. */
		NONE,

		/** Instruction jumps unconditionally. */
		JUMP,

		/** Instruction jumps conditionally or continues with the following instruction. */
		CJUMP,

		/** Instruction calls a function and then continues after the call returns. */
		CALL,

		/** Instruction leaves the current function. */
		RETURN
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

		/** Labels used as operands in this instruction's template. */
		public final Vector<MEM.Label> label;

		/** Labels this instruction can jump to. */
		public final Vector<MEM.Label> jumpTargets;

		/** True if this instruction only copies one temporary to another. */
		public final boolean isMove;

		/** Control-flow behavior of this instruction. */
		public final ControlFlow controlFlow;

		/**
		 * Constructs an instruction.
		 */
		public Instruction(
			final String instruction,
			final Vector<MEM.Temp> output,
			final Vector<MEM.Temp> input,
			final Vector<MEM.Label> label,
			final Vector<MEM.Label> jumpTargets,
			final boolean isMove
		) {
			this(instruction, output, input, label, jumpTargets, isMove, ControlFlow.NONE);
		}

		/**
		 * Constructs an instruction.
		 */
		public Instruction(
			final String instruction,
			final Vector<MEM.Temp> output,
			final Vector<MEM.Temp> input,
			final Vector<MEM.Label> label,
			final Vector<MEM.Label> jumpTargets,
			final boolean isMove,
			final ControlFlow controlFlow
		) {
			this.instruction = instruction;
			this.output = new Vector<MEM.Temp>(output);
			this.input = new Vector<MEM.Temp>(input);
			this.label = new Vector<MEM.Label>(label);
			this.jumpTargets = new Vector<MEM.Label>(jumpTargets);
			this.isMove = isMove;
			this.controlFlow = controlFlow;
		}

		/**
		 * Constructs a non-move instruction with no jump targets.
		 */
		public Instruction(
			final String instruction,
			final Vector<MEM.Temp> output,
			final Vector<MEM.Temp> input,
			final Vector<MEM.Label> label
		) {
			this(instruction, output, input, label, new Vector<MEM.Label>(), false);
		}

		/**
		 * Constructs a non-move instruction.
		 */
		public Instruction(
			final String instruction,
			final Vector<MEM.Temp> output,
			final Vector<MEM.Temp> input,
			final Vector<MEM.Label> label,
			final Vector<MEM.Label> jumpTargets
		) {
			this(instruction, output, input, label, jumpTargets, false);
		}

		/**
		 * Constructs a non-move instruction.
		 */
		public Instruction(
			final String instruction,
			final Vector<MEM.Temp> output,
			final Vector<MEM.Temp> input,
			final Vector<MEM.Label> label,
			final Vector<MEM.Label> jumpTargets,
			final ControlFlow controlFlow
		) {
			this(instruction, output, input, label, jumpTargets, false, controlFlow);
		}

		/** Returns a defensive copy of the used temporaries. */
		public Vector<MEM.Temp> inputs() {
			return new Vector<MEM.Temp>(input);
		}

		/** Returns a defensive copy of the defined temporaries. */
		public Vector<MEM.Temp> outputs() {
			return new Vector<MEM.Temp>(output);
		}

		/** Returns a defensive copy of the operand labels. */
		public Vector<MEM.Label> labels() {
			return new Vector<MEM.Label>(label);
		}

		/** Returns a defensive copy of the possible jump labels. */
		public Vector<MEM.Label> jumpTargets() {
			return new Vector<MEM.Label>(jumpTargets);
		}

		/**
		 * Formats this instruction with temporary names.
		 */
		public String format() {
			final String renderedInstruction = renderInstruction();
			return String.format(
				"%-32s %-18s %-18s %-18s %-18s %-5s",
				renderedInstruction,
				formatTemps(input),
				formatTemps(output),
				formatLabels(label),
				formatLabels(jumpTargets),
				Boolean.toString(isMove)
			);
		}

		/**
		 * Renders the instruction itself with placeholders resolved.
		 */
		private String renderInstruction() {
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
		 * Formats a temporary list as one set.
		 */
		private String formatTemps(final Vector<MEM.Temp> temps) {
			final StringBuilder formatted = new StringBuilder("{");
			for (int i = 0; i < temps.size(); i++) {
				if (i > 0)
					formatted.append(", ");
				formatted.append(temps.get(i));
			}
			formatted.append("}");
			return formatted.toString();
		}

		/**
		 * Formats a label list as one set.
		 */
		private String formatLabels(final Vector<MEM.Label> labels) {
			final StringBuilder formatted = new StringBuilder("{");
			for (int i = 0; i < labels.size(); i++) {
				if (i > 0)
					formatted.append(", ");
				formatted.append(labels.get(i).name);
			}
			formatted.append("}");
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
		return new Instruction(instruction, output, input, label);
	}

	/**
	 * Convenience constructor for pure temporary-copy instructions.
	 */
	public static Instruction move(final String instruction, final MEM.Temp dst, final MEM.Temp src) {
		final Vector<MEM.Temp> output = new Vector<MEM.Temp>();
		final Vector<MEM.Temp> input = new Vector<MEM.Temp>();
		output.add(dst);
		input.add(src);
		return new Instruction(instruction, output, input, new Vector<MEM.Label>(), new Vector<MEM.Label>(), true);
	}

	/**
	 * Convenience constructor for labels placed in the instruction stream.
	 */
	public static Instruction label(final MEM.Label label) {
		final Vector<MEM.Label> labels = new Vector<MEM.Label>();
		labels.add(label);
		return new Instruction("*l0:", new Vector<MEM.Temp>(), new Vector<MEM.Temp>(), labels);
	}
}
