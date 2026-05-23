package prev26lang.phase.seman;

import java.util.*;

import prev26lang.common.report.*;
import prev26lang.phase.abstr.*;


/**
 * Name resolver.
 * 
 * The name resolver connects each node of a abstract syntax tree where a name
 * is used with the node where it is defined. The only exceptions are struct and
 * union component names which are connected with their definitions by the type
 * resolver. The results of the name resolver are stored in
 * {@link prev26lang.phase.seman.SemAn#defAtAttr}.
 * 
 * @author bostjan.slivnik@fri.uni-lj.si
 */
public class NameResolver implements AST.FullVisitor<Object, NameResolver.Phase> {

	enum Phase { DECLARE, RESOLVE }

	/** Constructs a new name resolver. */
	public NameResolver() {
	}

	/** The symbol table. */
	private SymbTable symbTable = new SymbTable();

	private void insertDefn(AST.Defn defn) {
		// not handled by name resolver
		if (defn instanceof AST.CompDefn)
			return;

		try {
			symbTable.ins(defn.name, defn);
		} catch (SymbTable.CannotInsNameException ex) {
			throw new Report.Error(
				defn,
				"Name '" + defn.name + "' is already defined in this scope."
			);
		}
	}

	private void resolveTypeName(AST.NameType node) {
		final AST.Defn defn;

		try {
			defn = symbTable.fnd(node.name);
		} catch (SymbTable.CannotFndNameException ex) {
			throw new Report.Error(
				node,
				"Undefined type name '" + node.name + "'."
			);
		}

		if (!(defn instanceof AST.TypDefn)) {
			throw new Report.Error(
				node,
				"'" + node.name + "' is not a type name."
			);
		}

		SemAn.defAtAttr.put(node, defn);
	}

	private void resolveExprName(AST.NameExpr node) {
		final AST.Defn defn;

		try {
			defn = symbTable.fnd(node.name);
		} catch (SymbTable.CannotFndNameException ex) {
			throw new Report.Error(
				node,
				"Undefined value/function name '" + node.name + "'."
			);
		}

		if(defn instanceof AST.TypDefn) {
			throw new Report.Error(
				node,
				"'" + node.name + "' is a type name, not value/function name."
			);
		}

		SemAn.defAtAttr.put(node, defn);
	}

	private void declareDefs(AST.Nodes<? extends AST.Defn> defns) {
		for (AST.Defn defn : defns) {
			insertDefn(defn);
		}
	}

	private void resolveDefs(AST.Nodes<? extends AST.Defn> defns) {
		for (AST.Defn defn : defns) {
			defn.accept(this, Phase.RESOLVE);
		}
	}

	@Override
	public Object visit(AST.Nodes<? extends AST.Node> nodes, Phase phase) {
		// A sequence of full definitions = one scope's declaration block
		// (program-level definitions; let-definitions are handled in visit(LetExpr)).
		if (nodes.size() > 0 && nodes.first() instanceof AST.FullDefn) {
			@SuppressWarnings("unchecked")
			AST.Nodes<? extends AST.Defn> defns = (AST.Nodes<? extends AST.Defn>) nodes;
			declareDefs(defns);
			resolveDefs(defns);
			return null;
		}

		// Default traversal for all other node sequences.
		for (AST.Node node : nodes) {
			if (node != null)
				node.accept(this, phase);
		}
		return null;
	}

	@Override
	public Object visit(AST.DefFunDefn defFunDefn, Phase phase) {
		// Parameter types and result type are resolved OUTSIDE the function scope.
		defFunDefn.pars.accept(this, phase);
		defFunDefn.type.accept(this, phase);

		// Parameters and body are INSIDE the function scope.
		symbTable.newScope();
		declareDefs(defFunDefn.pars);
		defFunDefn.expr.accept(this, phase);
		symbTable.oldScope();

		return null;
	}

	@Override
	public Object visit(AST.ExtFunDefn extFunDefn, Phase phase) {
		// Same rule as for normal functions: parameter types/result type are outside.
		extFunDefn.pars.accept(this, phase);
		extFunDefn.type.accept(this, phase);

		// Parameters still live in the function scope (also checks duplicate params).
		symbTable.newScope();
		declareDefs(extFunDefn.pars);
		symbTable.oldScope();

		return null;
	}

	@Override
	public Object visit(AST.LetExpr letExpr, Phase phase) {
		boolean closeScope = false;
		if (!symbTable.letScopes.getFirst()) {
			closeScope = true;
			symbTable.newScope(true);
		}
		declareDefs(letExpr.defns);
		resolveDefs(letExpr.defns);
		letExpr.expr.accept(this, phase);
		if (closeScope) {
			symbTable.oldScope();
		}

		return null;
	}

	@Override
	public Object visit(AST.NameType nameType, Phase phase) {
		resolveTypeName(nameType);
		return null;
	}

	@Override
	public Object visit(AST.NameExpr nameExpr, Phase phase) {
		resolveExprName(nameExpr);
		return null;
	}


	// ===== SYMBOL TABLE =====

	/**
	 * A symbol table.
	 */
	public class SymbTable {

		/**
		 * A symbol table record denoting a definition of a name within a certain scope.
		 */
		private class ScopedDefn {

			/** The depth of the scope the definition belongs to. */
			public final int depth;

			/** The definition. */
			public final AST.Defn defn;

			/**
			 * Constructs a new record denoting a definition of a name within a certain
			 * scope.
			 * 
			 * @param depth The depth of the scope the definition belongs to.
			 * @param defn  The definition.
			 */
			public ScopedDefn(int depth, AST.Defn defn) {
				this.depth = depth;
				this.defn = defn;
			}

		}

		/**
		 * A mapping of names into lists of records denoting definitions at different
		 * scopes. At each moment during the lifetime of a symbol table, the definition
		 * list corresponding to a particular name contains all definitions that name
		 * within currently active scopes: the definition at the inner most scope is the
		 * first in the list and is visible, the other definitions are hidden.
		 */
		private final HashMap<String, LinkedList<ScopedDefn>> allDefnsOfAllNames;

		/**
		 * The list of scopes. Each scope is represented by a list of names defined
		 * within it.
		 */
		private final LinkedList<LinkedList<String>> scopes;

		/** The depth of the currently active scope. */
		private int currDepth;

		/** Whether the symbol table can no longer be modified or not. */
		private boolean lock;

		private LinkedList<Boolean> letScopes;

		/**
		 * Constructs a new symbol table.
		 */
		public SymbTable() {
			allDefnsOfAllNames = new HashMap<String, LinkedList<ScopedDefn>>();
			scopes = new LinkedList<LinkedList<String>>();
			currDepth = 0;
			lock = false;
			letScopes = new LinkedList<Boolean>();
			newScope();
		}

		/**
		 * Returns the depth of the currently active scope.
		 * 
		 * @return The depth of the currently active scope.
		 */
		public int currDepth() {
			return currDepth;
		}

		/**
		 * Inserts a new definition of a name within the currently active scope or
		 * throws an exception if this name has already been defined within this scope.
		 * Once the symbol table is locked, any attempt to insert further definitions
		 * results in an internal error.
		 * 
		 * @param name The name.
		 * @param defn The definition.
		 * @throws CannotInsNameException Thrown if this name has already been defined
		 *                                within the currently active scope.
		 */
		public void ins(String name, AST.Defn defn) throws CannotInsNameException {
			if (lock)
				throw new Report.InternalError();

			LinkedList<ScopedDefn> allDefnsOfName = allDefnsOfAllNames.get(name);
			if (allDefnsOfName == null) {
				allDefnsOfName = new LinkedList<ScopedDefn>();
				allDefnsOfAllNames.put(name, allDefnsOfName);
			}

			if (!allDefnsOfName.isEmpty()) {
				ScopedDefn defnOfName = allDefnsOfName.getFirst();
				if (defnOfName.depth == currDepth)
					throw new CannotInsNameException();
			}

			allDefnsOfName.addFirst(new ScopedDefn(currDepth, defn));
			scopes.getFirst().addFirst(name);
		}

		/**
		 * Returns the currently visible definition of the specified name. If no
		 * definition of the name exists within these scopes, an exception is thrown.
		 * 
		 * @param name The name.
		 * @return The definition.
		 * @throws CannotFndNameException Thrown if the name is not defined within the
		 *                                currently active scope or any scope enclosing
		 *                                it.
		 */
		public AST.Defn fnd(String name) throws CannotFndNameException {
			LinkedList<ScopedDefn> allDefnsOfName = allDefnsOfAllNames.get(name);
			if (allDefnsOfName == null)
				throw new CannotFndNameException();

			if (allDefnsOfName.isEmpty())
				throw new CannotFndNameException();

			return allDefnsOfName.getFirst().defn;
		}

		/** Used for selecting the range of scopes. */
		public enum XScopeSelector {
			/** All live scopes. */
			ALL,
			/** Currently active scope. */
			ACT,
		}

		/**
		 * Constructs a new scope within the currently active scope. The newly
		 * constructed scope becomes the currently active scope.
		 */
		public void newScope() {
			newScope(false);
		}

		public void newScope(boolean fromLetExpr) {
			if (lock)
				throw new Report.InternalError();

			currDepth++;
			scopes.addFirst(new LinkedList<String>());
			letScopes.addFirst(fromLetExpr);
		}

		/**
		 * Destroys the currently active scope by removing all definitions belonging to
		 * it from the symbol table. Makes the enclosing scope the currently active
		 * scope.
		 */
		public void oldScope() {
			if (lock)
				throw new Report.InternalError();

			if (currDepth == 0)
				throw new Report.InternalError();

			for (String name : scopes.getFirst()) {
				allDefnsOfAllNames.get(name).removeFirst();
			}
			scopes.removeFirst();
			letScopes.removeFirst();
			currDepth--;
		}

		/**
		 * Prevents further modifications of this symbol table.
		 */
		public void lock() {
			lock = true;
		}

		/**
		 * An exception thrown when the name cannot be inserted into a symbol table.
		 */
		@SuppressWarnings("serial")
		public class CannotInsNameException extends Exception {

			/**
			 * Constructs a new exception.
			 */
			private CannotInsNameException() {
			}

		}

		/**
		 * An exception thrown when the name cannot be found in the symbol table.
		 */
		@SuppressWarnings("serial")
		public class CannotFndNameException extends Exception {

			/**
			 * Constructs a new exception.
			 */
			private CannotFndNameException() {
			}

		}

	}

}
