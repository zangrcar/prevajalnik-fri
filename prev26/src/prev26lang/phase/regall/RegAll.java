package prev26lang.phase.regall;

import java.util.*;

import prev26lang.common.report.*;
import prev26lang.phase.*;
import prev26lang.phase.asmgen.*;
import prev26lang.phase.memory.*;

/**
 * Register-allocation phase.
 */
public class RegAll extends Phase {

	/** Allocatable RISC-V registers used for compiler temporaries. */
	private static final String[] ALLOCATABLE_REGISTERS = {
		"x5", "x6", "x7", "x9", "x10", "x11", "x12", "x13",
		"x14", "x15", "x16", "x17", "x18", "x19", "x20", "x21",
		"x22", "x23", "x24", "x25", "x26", "x27", "x28", "x29",
		"x30"
	};

	/** Code chunks after register allocation and possible spill rewriting. */
	private final Vector<ASM.CodeChunk> codeChunks;

	/** Register maps for code chunks in the same order as {@link #codeChunks}. */
	private final Vector<LinkedHashMap<MEM.Temp, String>> registers;

	/**
	 * Phase construction.
	 */
	public RegAll() {
		super("regall");
		codeChunks = new Vector<ASM.CodeChunk>();
		registers = new Vector<LinkedHashMap<MEM.Temp, String>>();
	}

	/**
	 * Allocates registers for all code chunks.
	 * 
	 * @param codeChunks Code chunks produced by assembly generation.
	 * @param numRegs Number of allocatable registers requested by the programmer.
	 */
	public void allocate(final Vector<ASM.CodeChunk> codeChunks, final int numRegs) {
		if ((numRegs < minRegisters()) || (numRegs > maxRegisters()))
			throw new Report.Error("Illegal number of registers: " + numRegs + ".");

		this.codeChunks.clear();
		this.registers.clear();

		final RegisterAllocator allocator = new RegisterAllocator(numRegs);
		for (final ASM.CodeChunk codeChunk : codeChunks) {
			final RegisterAllocator.Result result = allocator.allocate(codeChunk);
			this.codeChunks.add(result.codeChunk);
			this.registers.add(result.registers);
		}
	}

	/**
	 * Returns a defensive copy of allocated code chunks.
	 */
	public Vector<ASM.CodeChunk> codeChunks() {
		return new Vector<ASM.CodeChunk>(codeChunks);
	}

	/**
	 * Returns a defensive copy of all temporary-to-register maps.
	 */
	public Vector<LinkedHashMap<MEM.Temp, String>> registers() {
		final Vector<LinkedHashMap<MEM.Temp, String>> copy = new Vector<LinkedHashMap<MEM.Temp, String>>();

		for (final LinkedHashMap<MEM.Temp, String> registerMap : registers)
			copy.add(new LinkedHashMap<MEM.Temp, String>(registerMap));

		return copy;
	}

	/**
	 * Returns the minimum supported value of {@code --num-regs}.
	 */
	public static int minRegisters() {
		return 2;
	}

	/**
	 * Returns the maximum supported value of {@code --num-regs}.
	 */
	public static int maxRegisters() {
		return ALLOCATABLE_REGISTERS.length;
	}

	/**
	 * Returns the physical register represented by a color.
	 */
	public static String registerName(final int color) {
		if ((color < 0) || (color >= ALLOCATABLE_REGISTERS.length))
			throw new Report.InternalError();

		return ALLOCATABLE_REGISTERS[color];
	}

	/**
	 * Returns true if a temporary has a preassigned physical register.
	 */
	public static boolean isFixedRegister(final MEM.Temp temp) {
		return temp.equals(MEM.SP) || temp.equals(MEM.FP) || temp.equals(MEM.RA);
	}

	/**
	 * Returns the physical register of a preassigned temporary.
	 */
	public static String fixedRegisterName(final MEM.Temp temp) {
		if (temp.equals(MEM.SP))
			return "x2";
		if (temp.equals(MEM.FP))
			return "x8";
		if (temp.equals(MEM.RA))
			return "x1";

		throw new Report.InternalError();
	}

	/**
	 * Formats all allocated data and code chunks as final assembly text.
	 * 
	 * @param dataChunks Static data chunks from assembly generation.
	 * @return Lines of assembly with physical registers.
	 */
	public Vector<String> format(final Vector<ASM.DataChunk> dataChunks) {
		final Vector<String> lines = new Vector<String>();

		if (!dataChunks.isEmpty()) {
			lines.add(".data");
			for (final ASM.DataChunk dataChunk : dataChunks)
				lines.addAll(dataChunk.format());
		}

		lines.add(".text");
		for (int c = 0; c < codeChunks.size(); c++) {
			lines.add(codeChunks.get(c).frame.label.name + ":");
			for (final ASM.Instruction instruction : codeChunks.get(c).instructions()) {
				final String rendered = render(instruction, registers.get(c));
				if (rendered != null)
					lines.add("  " + rendered);
			}
		}

		return lines;
	}

	/**
	 * Renders one instruction and removes redundant physical-register moves.
	 */
	private String render(final ASM.Instruction instruction, final LinkedHashMap<MEM.Temp, String> registerMap) {
		if (isRedundantMove(instruction, registerMap))
			return null;

		final StringBuilder formatted = new StringBuilder();

		for (int i = 0; i < instruction.instruction.length(); i++) {
			if ((instruction.instruction.charAt(i) != '*') || (i + 2 >= instruction.instruction.length())) {
				formatted.append(instruction.instruction.charAt(i));
				continue;
			}

			final char kind = instruction.instruction.charAt(i + 1);
			if ((kind != 'd') && (kind != 's') && (kind != 'l')) {
				formatted.append(instruction.instruction.charAt(i));
				continue;
			}

			int j = i + 2;
			while ((j < instruction.instruction.length()) && Character.isDigit(instruction.instruction.charAt(j)))
				j++;
			if (j == i + 2) {
				formatted.append(instruction.instruction.charAt(i));
				continue;
			}

			final int index = Integer.parseInt(instruction.instruction.substring(i + 2, j));
			formatted.append(renderPlaceholder(kind, index, instruction, registerMap));
			i = j - 1;
		}

		return formatted.toString();
	}

	/**
	 * Checks whether a move copies a physical register to itself.
	 */
	private boolean isRedundantMove(final ASM.Instruction instruction, final LinkedHashMap<MEM.Temp, String> registerMap) {
		if (!instruction.isMove || (instruction.outputs().size() != 1) || (instruction.inputs().size() != 1))
			return false;

		return renderTemp(instruction.outputs().get(0), registerMap).equals(renderTemp(instruction.inputs().get(0), registerMap));
	}

	/**
	 * Renders one instruction-template placeholder.
	 */
	private String renderPlaceholder(
		final char kind,
		final int index,
		final ASM.Instruction instruction,
		final LinkedHashMap<MEM.Temp, String> registerMap
	) {
		return switch (kind) {
		case 'd' -> renderTemp(instruction.outputs().get(index), registerMap);
		case 's' -> renderTemp(instruction.inputs().get(index), registerMap);
		case 'l' -> instruction.labels().get(index).name;
		default -> throw new Report.InternalError();
		};
	}

	/**
	 * Renders one temporary as a physical register.
	 */
	private String renderTemp(final MEM.Temp temp, final LinkedHashMap<MEM.Temp, String> registerMap) {
		if (isFixedRegister(temp))
			return fixedRegisterName(temp);

		final String register = registerMap.get(temp);
		if (register == null)
			throw new Report.InternalError();

		return register;
	}
}
