package prev26lang.phase.regall;

import java.util.*;

import prev26lang.phase.asmgen.*;
import prev26lang.phase.livean.*;
import prev26lang.phase.memory.*;

/**
 * Interference graph used by register allocation.
 */
public class InterferenceGraph {

	/** Graph nodes currently present in the graph. */
	private final LinkedHashSet<Node> nodes;

	/** Maps every original temporary to its current graph node. */
	private final LinkedHashMap<MEM.Temp, Node> tempToNode;

	/**
	 * Constructs an empty interference graph.
	 */
	public InterferenceGraph() {
		nodes = new LinkedHashSet<Node>();
		tempToNode = new LinkedHashMap<MEM.Temp, Node>();
	}

	/**
	 * Builds an interference graph from one liveness-analysis result.
	 * 
	 * @param analysis Liveness information for one code chunk.
	 * @return A graph with normal interference edges and move-preference edges.
	 */
	public static InterferenceGraph from(final LIV.CodeChunkAnal analysis) {
		final InterferenceGraph graph = new InterferenceGraph();
		final Vector<ASM.Instruction> instructions = analysis.instructions();
		final Vector<HashSet<MEM.Temp>> in = analysis.in();
		final Vector<HashSet<MEM.Temp>> out = analysis.out();
		final Vector<HashSet<MEM.Temp>> def = analysis.def();

		for (int i = 0; i < instructions.size(); i++) {
			graph.ensureAll(instructions.get(i).inputs());
			graph.ensureAll(instructions.get(i).outputs());
			graph.ensureAll(in.get(i));
			graph.ensureAll(out.get(i));

			graph.addClique(in.get(i));
			graph.addClique(out.get(i));
			graph.addDefOutEdges(def.get(i), out.get(i));
		}

		for (final ASM.Instruction instruction : instructions)
			if (instruction.isMove)
				graph.addMoveEdges(instruction.outputs(), instruction.inputs());

		return graph;
	}

	/**
	 * Returns a copy of the current graph nodes.
	 */
	public LinkedHashSet<Node> nodes() {
		return new LinkedHashSet<Node>(nodes);
	}

	/**
	 * Returns true when all graph nodes have been removed.
	 */
	public boolean isEmpty() {
		return nodes.isEmpty();
	}

	/**
	 * Finds a non-move-related node whose degree is smaller than the register count.
	 * 
	 * @param numRegs Number of available physical registers.
	 * @return A simplifiable node, or {@code null} if none exists.
	 */
	public Node simplifiableNode(final int numRegs) {
		for (final Node node : nodes)
			if (!node.isMoveRelated() && (node.degree() < numRegs))
				return node;

		return null;
	}

	/**
	 * Finds a pair of move-related nodes that can be coalesced.
	 * 
	 * @param numRegs Number of available physical registers.
	 * @return A pair of coalescable nodes, or {@code null} if none exists.
	 */
	public NodePair coalescableMove(final int numRegs) {
		for (final Node first : nodes) {
			for (final Node second : first.moveNeighbours()) {
				if (!nodes.contains(second))
					continue;
				if (canCoalesce(first, second, numRegs) || canCoalesce(second, first, numRegs))
					return new NodePair(first, second);
			}
		}

		return null;
	}

	/**
	 * Finds one move-related node whose move edges can be frozen.
	 */
	public Node moveRelatedNode() {
		Node best = null;

		for (final Node node : nodes) {
			if (!node.isMoveRelated())
				continue;
			if ((best == null) || (node.degree() < best.degree()))
				best = node;
		}

		return best;
	}

	/**
	 * Finds a node that should be treated as a potential spill.
	 */
	public Node potentialSpillNode() {
		Node best = null;

		for (final Node node : nodes)
			if ((best == null) || (node.degree() > best.degree()))
				best = node;

		return best;
	}

	/**
	 * Removes one graph node and all edges pointing to it.
	 * 
	 * @param node Node to remove from the current graph.
	 */
	public void remove(final Node node) {
		nodes.remove(node);

		for (final Node neighbour : node.neighbours())
			neighbour.removeNeighbour(node);
		for (final Node moveNeighbour : node.moveNeighbours())
			moveNeighbour.removeMoveNeighbour(node);

		node.clearEdges();
	}

	/**
	 * Combines two move-related nodes into one node.
	 * 
	 * @param kept Node that remains in the graph.
	 * @param removed Node that is merged into {@code kept}.
	 */
	public void coalesce(final Node kept, final Node removed) {
		kept.absorbTemps(removed);

		for (final MEM.Temp temp : removed.temps())
			tempToNode.put(temp, kept);

		for (final Node neighbour : removed.neighbours()) {
			neighbour.replaceNeighbour(removed, kept);
			kept.addNeighbour(neighbour);
		}

		for (final Node moveNeighbour : removed.moveNeighbours()) {
			moveNeighbour.replaceMoveNeighbour(removed, kept);
			kept.addMoveNeighbour(moveNeighbour);
		}

		nodes.remove(removed);
		kept.removeNeighbour(kept);
		kept.removeMoveNeighbour(kept);
		kept.removeMoveNeighbour(removed);
		removed.clearEdges();
		removeMoveEdgesHiddenByInterference(kept);
	}

	/**
	 * Freezes all move preferences touching one node.
	 * 
	 * @param node Node whose move edges are no longer considered coalescing
	 *             opportunities.
	 */
	public void freezeMoves(final Node node) {
		for (final Node moveNeighbour : node.moveNeighbours())
			moveNeighbour.removeMoveNeighbour(node);

		node.clearMoveNeighbours();
	}

	/**
	 * Checks George's coalescing condition in one direction.
	 */
	private boolean canCoalesce(final Node first, final Node second, final int numRegs) {
		for (final Node neighbour : first.neighbours())
			if ((neighbour != second) && !second.hasNeighbour(neighbour) && (neighbour.degree() >= numRegs))
				return false;

		return true;
	}

	/**
	 * Ensures all non-fixed temporaries from a collection have graph nodes.
	 */
	private void ensureAll(final Collection<MEM.Temp> temps) {
		for (final MEM.Temp temp : temps)
			ensure(temp);
	}

	/**
	 * Ensures one non-fixed temporary has a graph node.
	 */
	private Node ensure(final MEM.Temp temp) {
		if (RegAll.isFixedRegister(temp))
			return null;

		Node node = tempToNode.get(temp);
		if (node != null)
			return node;

		node = new Node(temp);
		nodes.add(node);
		tempToNode.put(temp, node);
		return node;
	}

	/**
	 * Adds normal interference edges between all temporaries in a live set.
	 */
	private void addClique(final Collection<MEM.Temp> temps) {
		final Vector<MEM.Temp> vector = new Vector<MEM.Temp>(temps);

		for (int i = 0; i < vector.size(); i++)
			for (int j = i + 1; j < vector.size(); j++)
				addNormalEdge(vector.get(i), vector.get(j));
	}

	/**
	 * Adds normal interference between definitions and live-out temporaries.
	 */
	private void addDefOutEdges(final Collection<MEM.Temp> defs, final Collection<MEM.Temp> liveOut) {
		for (final MEM.Temp def : defs)
			for (final MEM.Temp out : liveOut)
				addNormalEdge(def, out);
	}

	/**
	 * Adds move-preference edges for source/destination pairs of a move instruction.
	 */
	private void addMoveEdges(final Collection<MEM.Temp> outputs, final Collection<MEM.Temp> inputs) {
		for (final MEM.Temp output : outputs)
			for (final MEM.Temp input : inputs)
				addMoveEdge(output, input);
	}

	/**
	 * Adds one normal interference edge if both temporaries are allocatable.
	 */
	private void addNormalEdge(final MEM.Temp fst, final MEM.Temp snd) {
		if (fst.equals(snd))
			return;

		final Node fstNode = ensure(fst);
		final Node sndNode = ensure(snd);
		if ((fstNode == null) || (sndNode == null))
			return;

		fstNode.addNeighbour(sndNode);
		sndNode.addNeighbour(fstNode);
		fstNode.removeMoveNeighbour(sndNode);
		sndNode.removeMoveNeighbour(fstNode);
	}

	/**
	 * Adds one move-preference edge when no normal interference already exists.
	 */
	private void addMoveEdge(final MEM.Temp fst, final MEM.Temp snd) {
		if (fst.equals(snd))
			return;

		final Node fstNode = ensure(fst);
		final Node sndNode = ensure(snd);
		if ((fstNode == null) || (sndNode == null) || fstNode.hasNeighbour(sndNode))
			return;

		fstNode.addMoveNeighbour(sndNode);
		sndNode.addMoveNeighbour(fstNode);
	}

	/**
	 * Removes move edges that became impossible after adding normal interference.
	 */
	private void removeMoveEdgesHiddenByInterference(final Node node) {
		for (final Node moveNeighbour : node.moveNeighbours())
			if (node.hasNeighbour(moveNeighbour)) {
				node.removeMoveNeighbour(moveNeighbour);
				moveNeighbour.removeMoveNeighbour(node);
			}
	}

	/**
	 * A pair of graph nodes.
	 */
	public static class NodePair {

		/** First node of the pair. */
		public final Node first;

		/** Second node of the pair. */
		public final Node second;

		/**
		 * Constructs a node pair.
		 */
		public NodePair(final Node first, final Node second) {
			this.first = first;
			this.second = second;
		}
	}

	/**
	 * One node of the interference graph.
	 */
	public static class Node {

		/** Original temporaries represented by this possibly coalesced node. */
		private final LinkedHashSet<MEM.Temp> temps;

		/** Normal interference neighbours. */
		private final LinkedHashSet<Node> neighbours;

		/** Move-preference neighbours. */
		private final LinkedHashSet<Node> moveNeighbours;

		/**
		 * Constructs a graph node for one temporary.
		 */
		public Node(final MEM.Temp temp) {
			temps = new LinkedHashSet<MEM.Temp>();
			neighbours = new LinkedHashSet<Node>();
			moveNeighbours = new LinkedHashSet<Node>();
			temps.add(temp);
		}

		/**
		 * Returns temporaries represented by this node.
		 */
		public LinkedHashSet<MEM.Temp> temps() {
			return new LinkedHashSet<MEM.Temp>(temps);
		}

		/**
		 * Returns normal interference neighbours.
		 */
		public LinkedHashSet<Node> neighbours() {
			return new LinkedHashSet<Node>(neighbours);
		}

		/**
		 * Returns move-preference neighbours.
		 */
		public LinkedHashSet<Node> moveNeighbours() {
			return new LinkedHashSet<Node>(moveNeighbours);
		}

		/**
		 * Returns the number of normal interference neighbours.
		 */
		public int degree() {
			return neighbours.size();
		}

		/**
		 * Returns true if this node still has move-preference edges.
		 */
		public boolean isMoveRelated() {
			return !moveNeighbours.isEmpty();
		}

		/**
		 * Returns true if another node is a normal interference neighbour.
		 */
		public boolean hasNeighbour(final Node node) {
			return neighbours.contains(node);
		}

		/**
		 * Adds a normal interference neighbour.
		 */
		private void addNeighbour(final Node node) {
			if (node != this)
				neighbours.add(node);
		}

		/**
		 * Adds a move-preference neighbour.
		 */
		private void addMoveNeighbour(final Node node) {
			if (node != this)
				moveNeighbours.add(node);
		}

		/**
		 * Removes a normal interference neighbour.
		 */
		private void removeNeighbour(final Node node) {
			neighbours.remove(node);
		}

		/**
		 * Removes a move-preference neighbour.
		 */
		private void removeMoveNeighbour(final Node node) {
			moveNeighbours.remove(node);
		}

		/**
		 * Replaces one normal neighbour with another.
		 */
		private void replaceNeighbour(final Node oldNode, final Node newNode) {
			if (neighbours.remove(oldNode) && (newNode != this))
				neighbours.add(newNode);
		}

		/**
		 * Replaces one move neighbour with another.
		 */
		private void replaceMoveNeighbour(final Node oldNode, final Node newNode) {
			if (moveNeighbours.remove(oldNode) && (newNode != this))
				moveNeighbours.add(newNode);
		}

		/**
		 * Adds all temporaries from another node into this node.
		 */
		private void absorbTemps(final Node node) {
			temps.addAll(node.temps);
		}

		/**
		 * Removes all graph edges from this node.
		 */
		private void clearEdges() {
			neighbours.clear();
			moveNeighbours.clear();
		}

		/**
		 * Removes all move-preference edges from this node.
		 */
		private void clearMoveNeighbours() {
			moveNeighbours.clear();
		}

		@Override
		public String toString() {
			return temps.toString();
		}
	}
}
