package prev26lang.phase.livean;

import java.util.*;

import prev26lang.common.report.*;
import prev26lang.phase.asmgen.*;
import prev26lang.phase.memory.*;

/**
 * Control-flow graph of one assembly-code chunk.
 */
public class FlowGraph {

	/** The code chunk represented by this flow graph. */
	private final ASM.CodeChunk codeChunk;

	/** Instructions represented by graph nodes. */
	private final Vector<ASM.Instruction> instructions;

	/** Maps each instruction to the set of possible successor instructions. */
	private final Vector<HashSet<Integer>> successors;

	/** Maps every label definition to the instruction that defines it. */
	private final HashMap<MEM.Label, Integer> labelToInstruction;

	/**
	 * Constructs a control-flow graph for the given code chunk.
	 * 
	 * @param codeChunk The code chunk whose instructions are graph nodes.
	 */
	public FlowGraph(final ASM.CodeChunk codeChunk) {
		this.codeChunk = codeChunk;
		instructions = codeChunk.instructions();
		successors = new Vector<HashSet<Integer>>();
		labelToInstruction = new HashMap<MEM.Label, Integer>();
		generate();
	}

	/**
	 * Returns the code chunk represented by this flow graph.
	 */
	public ASM.CodeChunk codeChunk() {
		return codeChunk;
	}

	/**
	 * Returns a defensive copy of all instructions.
	 */
	public Vector<ASM.Instruction> instructions() {
		return new Vector<ASM.Instruction>(instructions);
	}

	/**
	 * Returns a defensive copy of all successor sets.
	 */
	public Vector<HashSet<Integer>> successors() {
		return copySets(successors);
	}

	/**
	 * Generates all graph edges.
	 */
	private void generate() {
		labelToInstruction.clear();
		successors.clear();

		setLabelsToInstructions();
		generateSuccessors();
	}

	/**
	 * Finds instruction indices that define labels.
	 */
	private void setLabelsToInstructions() {
		for (int i = 0; i < instructions.size(); i++) {
			final ASM.Instruction instruction = instructions.get(i);
			if (!isLabelDefinition(instruction))
				continue;

			for (final MEM.Label label : instruction.labels())
				labelToInstruction.put(label, i);
		}
	}

	/**
	 * Generates successor sets for all instructions.
	 */
	private void generateSuccessors() {
		for (int i = 0; i < instructions.size(); i++)
			successors.add(successors(i));
	}

	/**
	 * Generates the successor set of one instruction.
	 */
	private HashSet<Integer> successors(final int index) {
		final ASM.Instruction instruction = instructions.get(index);
		final HashSet<Integer> successors = new HashSet<Integer>();

		if (mayFallThrough(instruction) && (index + 1 < instructions.size()))
			successors.add(index + 1);

		if (!isFunctionCall(instruction))
			addJumpTargets(successors, instruction);

		return successors;
	}

	/**
	 * Adds all explicitly known jump-target instructions to a successor set.
	 */
	private void addJumpTargets(final HashSet<Integer> successors, final ASM.Instruction instruction) {
		for (final MEM.Label target : instruction.jumpTargets()) {
			final Integer targetIndex = labelToInstruction.get(target);
			if (targetIndex == null)
				throw new Report.InternalError();
			successors.add(targetIndex);
		}
	}

	/**
	 * Returns true if this instruction defines a label in the instruction stream.
	 */
	private boolean isLabelDefinition(final ASM.Instruction instruction) {
		return instruction.instruction.endsWith(":");
	}

	/**
	 * Returns true if control can continue with the next instruction.
	 */
	private boolean mayFallThrough(final ASM.Instruction instruction) {
		return !isUnconditionalJump(instruction);
	}

	/**
	 * Returns true if this instruction is an unconditional jump.
	 */
	private boolean isUnconditionalJump(final ASM.Instruction instruction) {
		return instruction.instruction.startsWith("JAL x0") || instruction.instruction.startsWith("JALR x0");
	}

	/**
	 * Returns true if this instruction calls a function.
	 */
	private boolean isFunctionCall(final ASM.Instruction instruction) {
		return instruction.instruction.startsWith("JAL *d") || instruction.instruction.startsWith("JALR *d");
	}

	/**
	 * Creates a defensive copy of a vector of sets.
	 */
	private Vector<HashSet<Integer>> copySets(final Vector<HashSet<Integer>> sets) {
		final Vector<HashSet<Integer>> copy = new Vector<HashSet<Integer>>();

		for (final HashSet<Integer> set : sets)
			copy.add(new HashSet<Integer>(set));

		return copy;
	}

}
