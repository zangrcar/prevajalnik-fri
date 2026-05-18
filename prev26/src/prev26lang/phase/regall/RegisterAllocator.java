package prev26lang.phase.regall;

import java.util.*;

import prev26lang.common.report.*;
import prev26lang.phase.asmgen.*;
import prev26lang.phase.livean.*;
import prev26lang.phase.memory.*;

/**
 * Graph-coloring register allocator with move coalescing and spill rewriting.
 */
public class RegisterAllocator {

	/** Number of available allocatable physical registers. */
	private final int numRegs;

	/** Next spill slot index for the code chunk currently being allocated. */
	private int nextSpillSlot;

	/** Original frame of the code chunk currently being allocated. */
	private MEM.Frame baseFrame;

	/**
	 * Constructs a register allocator.
	 * 
	 * @param numRegs Number of allocatable physical registers.
	 */
	public RegisterAllocator(final int numRegs) {
		this.numRegs = numRegs;
		this.nextSpillSlot = 0;
		this.baseFrame = null;
	}

	/**
	 * Allocates registers for one code chunk, rewriting spills until coloring
	 * succeeds.
	 * 
	 * @param codeChunk Code chunk whose temporaries should be allocated.
	 * @return Allocated code chunk and temporary-to-register-color mapping.
	 */
	public Result allocate(final ASM.CodeChunk codeChunk) {
		ASM.CodeChunk currentCodeChunk = codeChunk;
		nextSpillSlot = 0;
		baseFrame = codeChunk.frame;

		for (int attempt = 0; attempt < 100; attempt++) {
			final LIV.CodeChunkAnal analysis = analyze(currentCodeChunk);
			final Coloring coloring = color(InterferenceGraph.from(analysis));

			if (coloring.spilledTemps.isEmpty())
				return new Result(currentCodeChunk, colorsToRegisters(coloring.colors));

			currentCodeChunk = rewriteSpills(currentCodeChunk, coloring.spilledTemps);
		}

		throw new Report.Error("Register allocation did not converge after spill rewriting.");
	}

	/**
	 * Runs liveness analysis for one code chunk.
	 */
	private LIV.CodeChunkAnal analyze(final ASM.CodeChunk codeChunk) {
		final FlowGraph flowGraph = new FlowGraph(codeChunk);
		final LiveAnalyzer analyzer = new LiveAnalyzer(flowGraph.instructions());
		return analyzer.analyze(flowGraph);
	}

	/**
	 * Colors an interference graph or reports real spills.
	 */
	private Coloring color(final InterferenceGraph graph) {
		final Stack<StackEntry> stack = reduce(graph);
		final LinkedHashMap<InterferenceGraph.Node, Integer> colors = new LinkedHashMap<InterferenceGraph.Node, Integer>();
		final LinkedHashSet<MEM.Temp> spilledTemps = new LinkedHashSet<MEM.Temp>();

		while (!stack.empty()) {
			final StackEntry entry = stack.pop();
			final Integer color = chooseColor(entry, colors, graph);

			if (color == null) {
				spilledTemps.addAll(entry.node.temps());
				continue;
			}

			colors.put(entry.node, color);
		}

		return new Coloring(colors, spilledTemps);
	}

	/**
	 * Reduces the graph by simplification, coalescing, freezing, and spill
	 * selection.
	 */
	private Stack<StackEntry> reduce(final InterferenceGraph graph) {
		final Stack<StackEntry> stack = new Stack<StackEntry>();

		while (!graph.isEmpty()) {
			if (simplify(graph, stack))
				continue;

			final InterferenceGraph.NodePair move = graph.coalescableMove(numRegs);
			if (move != null) {
				graph.coalesce(move.first, move.second);
				continue;
			}

			final InterferenceGraph.Node moveNode = graph.moveRelatedNode();
			if (moveNode != null) {
				graph.freezeMoves(moveNode);
				continue;
			}

			selectPotentialSpill(graph, stack);
		}

		return stack;
	}

	/**
	 * Removes every currently simplifiable non-move node.
	 */
	private boolean simplify(final InterferenceGraph graph, final Stack<StackEntry> stack) {
		boolean changed = false;

		while (true) {
			final InterferenceGraph.Node node = graph.simplifiableNode(numRegs);
			if (node == null)
				break;

			stack.push(new StackEntry(node, node.neighbours(), false));
			graph.remove(node);
			changed = true;
		}

		return changed;
	}

	/**
	 * Removes one high-degree node and marks it as a possible real spill.
	 */
	private void selectPotentialSpill(final InterferenceGraph graph, final Stack<StackEntry> stack) {
		final InterferenceGraph.Node node = graph.potentialSpillNode();
		if (node == null)
			throw new Report.InternalError();

		stack.push(new StackEntry(node, node.neighbours(), true));
		graph.remove(node);
	}

	/**
	 * Chooses the first free color for a node being rebuilt.
	 */
	private Integer chooseColor(
		final StackEntry entry,
		final LinkedHashMap<InterferenceGraph.Node, Integer> colors,
		final InterferenceGraph graph
	) {
		final boolean[] used = new boolean[numRegs];

		for (final InterferenceGraph.Node neighbour : entry.neighbours) {
			final InterferenceGraph.Node representative = graph.representative(neighbour);
			if (representative == entry.node)
				continue;

			final Integer color = colors.get(representative);
			if (color != null)
				used[color] = true;
		}

		for (int color = 0; color < numRegs; color++)
			if (!used[color])
				return color;

		return null;
	}

	/**
	 * Converts node colors into a temporary-to-physical-register map.
	 */
	private LinkedHashMap<MEM.Temp, String> colorsToRegisters(
		final LinkedHashMap<InterferenceGraph.Node, Integer> colors
	) {
		final LinkedHashMap<MEM.Temp, String> registers = new LinkedHashMap<MEM.Temp, String>();

		for (final Map.Entry<InterferenceGraph.Node, Integer> entry : colors.entrySet()) {
			final String register = RegAll.registerName(entry.getValue());
			for (final MEM.Temp temp : entry.getKey().temps())
				registers.put(temp, register);
		}

		return registers;
	}

	/**
	 * Rewrites a code chunk so selected temporaries live in spill slots.
	 */
	private ASM.CodeChunk rewriteSpills(final ASM.CodeChunk codeChunk, final LinkedHashSet<MEM.Temp> spilledTemps) {
		final LinkedHashMap<MEM.Temp, Long> offsets = new LinkedHashMap<MEM.Temp, Long>();
		final Vector<ASM.Instruction> rewritten = new Vector<ASM.Instruction>();

		for (final ASM.Instruction instruction : codeChunk.instructions()) {
			final Rewrite rewrite = rewriteInstruction(instruction, spilledTemps, offsets);
			rewritten.addAll(rewrite.before);
			rewritten.add(rewrite.instruction);
			rewritten.addAll(rewrite.after);
		}

		return new ASM.CodeChunk(spillFrame(), codeChunk.entryLabel, codeChunk.exitLabel, rewritten);
	}

	/**
	 * Rewrites one instruction by loading spilled inputs before it and storing
	 * spilled outputs after it.
	 */
	private Rewrite rewriteInstruction(
		final ASM.Instruction instruction,
		final LinkedHashSet<MEM.Temp> spilledTemps,
		final LinkedHashMap<MEM.Temp, Long> offsets
	) {
		final Vector<ASM.Instruction> before = new Vector<ASM.Instruction>();
		final Vector<ASM.Instruction> after = new Vector<ASM.Instruction>();
		final LinkedHashMap<MEM.Temp, MEM.Temp> replacements = new LinkedHashMap<MEM.Temp, MEM.Temp>();

		for (final MEM.Temp temp : unique(instruction.inputs()))
			if (spilledTemps.contains(temp)) {
				final MEM.Temp replacement = replacementFor(temp, replacements);
				before.addAll(loadFromSpill(replacement, spillOffset(temp, offsets)));
			}

		for (final MEM.Temp temp : unique(instruction.outputs()))
			if (spilledTemps.contains(temp)) {
				final MEM.Temp replacement = replacementFor(temp, replacements);
				after.addAll(storeToSpill(replacement, spillOffset(temp, offsets)));
			}

		final ASM.Instruction rewritten = new ASM.Instruction(
			instruction.instruction,
			replaceTemps(instruction.outputs(), replacements),
			replaceTemps(instruction.inputs(), replacements),
			instruction.labels(),
			instruction.jumpTargets(),
			instruction.isMove,
			instruction.controlFlow
		);

		return new Rewrite(before, rewritten, after);
	}

	/**
	 * Returns temporaries in their first-seen order with duplicates removed.
	 */
	private LinkedHashSet<MEM.Temp> unique(final Vector<MEM.Temp> temps) {
		return new LinkedHashSet<MEM.Temp>(temps);
	}

	/**
	 * Returns the replacement temporary used for one spilled temporary around one
	 * instruction.
	 */
	private MEM.Temp replacementFor(
		final MEM.Temp temp,
		final LinkedHashMap<MEM.Temp, MEM.Temp> replacements
	) {
		MEM.Temp replacement = replacements.get(temp);
		if (replacement == null) {
			replacement = new MEM.Temp();
			replacements.put(temp, replacement);
		}
		return replacement;
	}

	/**
	 * Replaces spilled temporaries in an operand list with fresh temporaries.
	 */
	private Vector<MEM.Temp> replaceTemps(
		final Vector<MEM.Temp> temps,
		final LinkedHashMap<MEM.Temp, MEM.Temp> replacements
	) {
		final Vector<MEM.Temp> replaced = new Vector<MEM.Temp>();

		for (final MEM.Temp temp : temps) {
			final MEM.Temp replacement = replacements.get(temp);
			replaced.add(replacement == null ? temp : replacement);
		}

		return replaced;
	}

	/**
	 * Returns the stack offset assigned to one spilled temporary.
	 */
	private long spillOffset(final MEM.Temp temp, final LinkedHashMap<MEM.Temp, Long> offsets) {
		Long offset = offsets.get(temp);
		if (offset != null)
			return offset;

		if (baseFrame == null)
			throw new Report.InternalError();

		// Skip the outgoing arguments and the fixed old-FP/RA slots.
		offset = baseFrame.argsSize + 16 + 8L * nextSpillSlot;
		nextSpillSlot++;
		offsets.put(temp, offset);
		return offset;
	}

	/**
	 * Returns the current frame enlarged by all spill slots allocated so far.
	 */
	private MEM.Frame spillFrame() {
		if (baseFrame == null)
			throw new Report.InternalError();

		return new MEM.Frame(baseFrame, baseFrame.size + 8L * nextSpillSlot);
	}

	/**
	 * Builds instructions that load a spilled value into a fresh temporary.
	 */
	private Vector<ASM.Instruction> loadFromSpill(final MEM.Temp dst, final long offset) {
		final Vector<ASM.Instruction> instructions = new Vector<ASM.Instruction>();

		if (isImm12(offset)) {
			instructions.add(new ASM.Instruction("ld *d0, " + offset + "(*s0)", temps(dst), temps(MEM.SP), new Vector<MEM.Label>()));
			return instructions;
		}

		final MEM.Temp addr = spillAddress(offset, instructions);
		instructions.add(new ASM.Instruction("ld *d0, 0(*s0)", temps(dst), temps(addr), new Vector<MEM.Label>()));
		return instructions;
	}

	/**
	 * Builds instructions that store a fresh temporary back into a spill slot.
	 */
	private Vector<ASM.Instruction> storeToSpill(final MEM.Temp src, final long offset) {
		final Vector<ASM.Instruction> instructions = new Vector<ASM.Instruction>();

		if (isImm12(offset)) {
			instructions.add(new ASM.Instruction("sd *s0, " + offset + "(*s1)", new Vector<MEM.Temp>(), temps(src, MEM.SP), new Vector<MEM.Label>()));
			return instructions;
		}

		final MEM.Temp addr = spillAddress(offset, instructions);
		instructions.add(new ASM.Instruction("sd *s0, 0(*s1)", new Vector<MEM.Temp>(), temps(src, addr), new Vector<MEM.Label>()));
		return instructions;
	}

	/**
	 * Builds instructions that compute SP plus a large spill-slot offset.
	 */
	private MEM.Temp spillAddress(final long offset, final Vector<ASM.Instruction> instructions) {
		final MEM.Temp offsetTemp = new MEM.Temp();
		final MEM.Temp addr = new MEM.Temp();

		instructions.addAll(loadConst(offsetTemp, offset));
		instructions.add(new ASM.Instruction("add *d0, *s0, *s1", temps(addr), temps(MEM.SP, offsetTemp), new Vector<MEM.Label>()));
		return addr;
	}

	/**
	 * Loads a 64-bit constant into a temporary.
	 */
	private Vector<ASM.Instruction> loadConst(final MEM.Temp dst, final long value) {
		final Vector<ASM.Instruction> instructions = new Vector<ASM.Instruction>();

		if (isImm12(value)) {
			instructions.add(new ASM.Instruction("addi *d0, x0, " + value, temps(dst), new Vector<MEM.Temp>(), new Vector<MEM.Label>()));
			return instructions;
		}

		boolean started = false;
		for (int shift = 56; shift >= 0; shift -= 8) {
			final long byteValue = (value >>> shift) & 0xFFL;
			if (!started) {
				if ((byteValue == 0) && (shift > 0))
					continue;
				instructions.add(new ASM.Instruction("addi *d0, x0, " + byteValue, temps(dst), new Vector<MEM.Temp>(), new Vector<MEM.Label>()));
				started = true;
				continue;
			}

			instructions.add(new ASM.Instruction("slli *d0, *s0, 8", temps(dst), temps(dst), new Vector<MEM.Label>()));
			if (byteValue != 0)
				instructions.add(new ASM.Instruction("addi *d0, *s0, " + byteValue, temps(dst), temps(dst), new Vector<MEM.Label>()));
		}

		if (!started)
			instructions.add(new ASM.Instruction("addi *d0, x0, 0", temps(dst), new Vector<MEM.Temp>(), new Vector<MEM.Label>()));

		return instructions;
	}

	/**
	 * Returns true if a value fits into a signed 12-bit immediate field.
	 */
	private boolean isImm12(final long value) {
		return -2048 <= value && value <= 2047;
	}

	/**
	 * Creates a temporary vector.
	 */
	private Vector<MEM.Temp> temps(final MEM.Temp... temps) {
		final Vector<MEM.Temp> vector = new Vector<MEM.Temp>();
		for (final MEM.Temp temp : temps)
			vector.add(temp);
		return vector;
	}

	/**
	 * One entry stored on the simplification stack.
	 */
	private static class StackEntry {

		/** Removed node. */
		final InterferenceGraph.Node node;

		/** Normal neighbours present at removal time. */
		final LinkedHashSet<InterferenceGraph.Node> neighbours;

		/** True if this node was selected as a possible spill. */
		@SuppressWarnings("unused")
		final boolean potentialSpill;

		/**
		 * Constructs one simplification-stack entry.
		 */
		StackEntry(
			final InterferenceGraph.Node node,
			final LinkedHashSet<InterferenceGraph.Node> neighbours,
			final boolean potentialSpill
		) {
			this.node = node;
			this.neighbours = new LinkedHashSet<InterferenceGraph.Node>(neighbours);
			this.potentialSpill = potentialSpill;
		}
	}

	/**
	 * Coloring result before register names are attached.
	 */
	private static class Coloring {

		/** Color of every successfully colored graph node. */
		final LinkedHashMap<InterferenceGraph.Node, Integer> colors;

		/** Original temporaries that could not be colored. */
		final LinkedHashSet<MEM.Temp> spilledTemps;

		/**
		 * Constructs a coloring result.
		 */
		Coloring(
			final LinkedHashMap<InterferenceGraph.Node, Integer> colors,
			final LinkedHashSet<MEM.Temp> spilledTemps
		) {
			this.colors = colors;
			this.spilledTemps = spilledTemps;
		}
	}

	/**
	 * Instruction rewrite result.
	 */
	private static class Rewrite {

		/** Instructions inserted before the rewritten instruction. */
		final Vector<ASM.Instruction> before;

		/** The rewritten original instruction. */
		final ASM.Instruction instruction;

		/** Instructions inserted after the rewritten instruction. */
		final Vector<ASM.Instruction> after;

		/**
		 * Constructs an instruction rewrite result.
		 */
		Rewrite(
			final Vector<ASM.Instruction> before,
			final ASM.Instruction instruction,
			final Vector<ASM.Instruction> after
		) {
			this.before = before;
			this.instruction = instruction;
			this.after = after;
		}
	}

	/**
	 * Final allocation result for one code chunk.
	 */
	public static class Result {

		/** Code chunk after possible spill rewriting. */
		public final ASM.CodeChunk codeChunk;

		/** Physical register selected for every remaining temporary. */
		public final LinkedHashMap<MEM.Temp, String> registers;

		/**
		 * Constructs a final allocation result.
		 */
		public Result(final ASM.CodeChunk codeChunk, final LinkedHashMap<MEM.Temp, String> registers) {
			this.codeChunk = codeChunk;
			this.registers = new LinkedHashMap<MEM.Temp, String>(registers);
		}
	}
}
