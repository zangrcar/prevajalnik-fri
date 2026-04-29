package prev26lang.phase.livean;

import java.util.*;

import prev26lang.common.report.*;
import prev26lang.phase.asmgen.*;
import prev26lang.phase.memory.*;

/**
 * Analyzer of live temporary variables.
 */
public class LiveAnalyzer {

	/** Temporaries live immediately before each instruction. */
	private Vector<HashSet<MEM.Temp>> in;

	/** Temporaries live immediately after each instruction. */
	private Vector<HashSet<MEM.Temp>> out;

	/** Temporaries used by each instruction. */
	private final Vector<HashSet<MEM.Temp>> use;

	/** Temporaries defined by each instruction. */
	private final Vector<HashSet<MEM.Temp>> def;

	/**
	 * Constructs a liveness analyzer.
	 * 
	 * @param instructions The instructions that will be analyzed.
	 */
	public LiveAnalyzer(final Vector<ASM.Instruction> instructions) {
		in = emptySets(instructions.size());
		out = emptySets(instructions.size());
		use = new Vector<HashSet<MEM.Temp>>();
		def = new Vector<HashSet<MEM.Temp>>();

		for (final ASM.Instruction instruction : instructions) {
			use.add(new HashSet<MEM.Temp>(instruction.inputs()));
			def.add(new HashSet<MEM.Temp>(instruction.outputs()));
		}
	}

	/**
	 * Computes liveness information for all instructions in a flow graph.
	 * 
	 * @param flowGraph The control-flow graph being analyzed.
	 * @return The computed liveness information.
	 */
	public LIV.CodeChunkAnal analyze(final FlowGraph flowGraph) {
		final Vector<ASM.Instruction> instructions = flowGraph.instructions();
		final Vector<HashSet<Integer>> successors = flowGraph.successors();

		if ((instructions.size() != use.size()) || (successors.size() != use.size()))
			throw new Report.InternalError();

		reset(instructions.size());

		while (true) {
			final Vector<HashSet<MEM.Temp>> oldIn = copySets(in);
			final Vector<HashSet<MEM.Temp>> oldOut = copySets(out);

			for (int n = instructions.size() - 1; n >= 0; n--)
				analyzeInstruction(n, successors.get(n));

			if (setsEqual(in, oldIn) && setsEqual(out, oldOut))
				break;
		}

		return new LIV.CodeChunkAnal(instructions, use, def, in, out);
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
	 * Resets the computed liveness sets.
	 */
	private void reset(final int size) {
		in = emptySets(size);
		out = emptySets(size);
	}

	/**
	 * Computes one iteration step for one instruction.
	 */
	private void analyzeInstruction(final int index, final HashSet<Integer> successors) {
		final HashSet<MEM.Temp> newOut = outSet(successors);
		final HashSet<MEM.Temp> newIn = inSet(index, newOut);

		out.set(index, newOut);
		in.set(index, newIn);
	}

	/**
	 * Computes the out set from successor in sets.
	 */
	private HashSet<MEM.Temp> outSet(final HashSet<Integer> successors) {
		final HashSet<MEM.Temp> outSet = new HashSet<MEM.Temp>();

		for (final Integer successor : successors)
			outSet.addAll(in.get(successor));

		return outSet;
	}

	/**
	 * Computes the in set from use, def, and out sets.
	 */
	private HashSet<MEM.Temp> inSet(final int index, final HashSet<MEM.Temp> outSet) {
		final HashSet<MEM.Temp> outMinusDef = new HashSet<MEM.Temp>(outSet);
		outMinusDef.removeAll(def.get(index));

		final HashSet<MEM.Temp> inSet = new HashSet<MEM.Temp>(use.get(index));
		inSet.addAll(outMinusDef);
		return inSet;
	}

	/**
	 * Compares two vectors of sets by their contents.
	 */
	private boolean setsEqual(final Vector<HashSet<MEM.Temp>> fst, final Vector<HashSet<MEM.Temp>> snd) {
		return fst.equals(snd);
	}

	/**
	 * Creates a defensive copy of a vector of sets.
	 */
	private Vector<HashSet<MEM.Temp>> copySets(final Vector<HashSet<MEM.Temp>> sets) {
		final Vector<HashSet<MEM.Temp>> copy = new Vector<HashSet<MEM.Temp>>();

		for (final HashSet<MEM.Temp> set : sets)
			copy.add(new HashSet<MEM.Temp>(set));

		return copy;
	}

	/**
	 * Creates a vector of empty temporary sets.
	 */
	private Vector<HashSet<MEM.Temp>> emptySets(final int size) {
		final Vector<HashSet<MEM.Temp>> sets = new Vector<HashSet<MEM.Temp>>();

		for (int i = 0; i < size; i++)
			sets.add(new HashSet<MEM.Temp>());

		return sets;
	}

}
