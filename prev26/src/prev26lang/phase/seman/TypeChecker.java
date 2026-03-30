package prev26lang.phase.seman;

import java.util.*;

import prev26lang.common.report.*;
import prev26lang.phase.abstr.*;

public class TypeChecker implements AST.FullVisitor<Object, Object> {

    /** Have we already checked the program root for function main? */
    private boolean checkedProgramRoot = false;

    public TypeChecker() {
    }

    // ===== HELPERS =====

    /**
     * Returns the actual type, unwrapping named types.
     */
    private TYP.Type actualType(final TYP.Type type) {
        if (type == null)
			throw new Report.InternalError();
		return type.actualType();

    }

    /**
     * Structural type equivalence.
     */
	private boolean equiv(final TYP.Type fstType, final TYP.Type sndType) {
		return equiv(fstType, sndType, new HashSet<String>());
	}

    private boolean equiv(final TYP.Type fstType, final TYP.Type sndType,
                    	  final Set<String> comparedNames) {
		if (fstType == null || sndType == null)
			return false;

		if (fstType == sndType)
			return true;

		if (fstType instanceof TYP.NameType fstName) {
			String key = fstName.id + ":" + sndType.id;
			if (comparedNames.contains(key))
				return true;
			comparedNames.add(key);
			return equiv(fstName.actualType(), sndType, comparedNames);
		}

		if (sndType instanceof TYP.NameType sndName) {
			String key = fstType.id + ":" + sndName.id;
			if (comparedNames.contains(key))
				return true;
			comparedNames.add(key);
			return equiv(fstType, sndName.actualType(), comparedNames);
		}

		if (fstType.getClass() != sndType.getClass())
			return false;

		if (fstType instanceof TYP.IntType) return true;
		if (fstType instanceof TYP.CharType) return true;
		if (fstType instanceof TYP.BoolType) return true;
		if (fstType instanceof TYP.VoidType) return true;

		if (fstType instanceof TYP.ArrType fstArr && sndType instanceof TYP.ArrType sndArr) {
			return fstArr.numElems.equals(sndArr.numElems)
				&& equiv(fstArr.elemType, sndArr.elemType, comparedNames);
		}

		if (fstType instanceof TYP.PtrType fstPtr && sndType instanceof TYP.PtrType sndPtr) {
			return equiv(fstPtr.baseType, sndPtr.baseType, comparedNames);
		}

		if (fstType instanceof TYP.StrType fstStr && sndType instanceof TYP.StrType sndStr) {
			if (fstStr.compTypes.size() != sndStr.compTypes.size())
				return false;
			for (int i = 0; i < fstStr.compTypes.size(); i++) {
				if (!equiv(fstStr.compTypes.get(i), sndStr.compTypes.get(i), comparedNames))
					return false;
			}
			return true;
		}

		if (fstType instanceof TYP.UniType fstUni && sndType instanceof TYP.UniType sndUni) {
			if (fstUni.compTypes.size() != sndUni.compTypes.size())
				return false;
			for (int i = 0; i < fstUni.compTypes.size(); i++) {
				if (!equiv(fstUni.compTypes.get(i), sndUni.compTypes.get(i), comparedNames))
					return false;
			}
			return true;
		}

		if (fstType instanceof TYP.FunType fstFun && sndType instanceof TYP.FunType sndFun) {
			if (fstFun.parTypes.size() != sndFun.parTypes.size())
				return false;
			for (int i = 0; i < fstFun.parTypes.size(); i++) {
				if (!equiv(fstFun.parTypes.get(i), sndFun.parTypes.get(i), comparedNames))
					return false;
			}
			return equiv(fstFun.resType, sndFun.resType, comparedNames);
		}

		return false;
	}

	/**
	 * if I am going to need to allow nil instead of pointers and none instead of everything else
	 */

	private boolean isNilLiteral(final AST.Expr expr) {
		return (expr instanceof AST.AtomExpr atomExpr) &&
			(atomExpr.type == AST.AtomExpr.Type.PTR);
	}

	private boolean isNoneLiteral(final AST.Expr expr) {
		return (expr instanceof AST.AtomExpr atomExpr) &&
			(atomExpr.type == AST.AtomExpr.Type.VOID);
	}

	private boolean isPointerLike(final TYP.Type type) {
		return actualType(type) instanceof TYP.PtrType;
	}

	private boolean isFunctionLike(final TYP.Type type) {
		return actualType(type) instanceof TYP.FunType;
	}

	private boolean compatible(final TYP.Type expectedType, final AST.Expr expr) {
		final TYP.Type actualExprType = requireExprType(expr);

		if (equiv(expectedType, actualExprType))
			return true;

		// Extension: nil is compatible with any pointer type.
		if (isNilLiteral(expr) && isPointerLike(expectedType))
			return true;

		return false;
	}

    /**
     * Checks that a type is not null and already constructed.
     */
    private TYP.Type requireType(final AST.Node node) {
        TYP.Type type = null;

		if (node instanceof AST.Type astType)
			type = SemAn.isTypeAttr.get(astType);
		else if (node instanceof AST.TypDefn typDefn)
			type = SemAn.isTypeAttr.get(typDefn);
		else if (node instanceof AST.Expr expr)
			type = SemAn.ofTypeAttr.get(expr);
		else if (node instanceof AST.Defn defn)
			type = SemAn.ofTypeAttr.get(defn);

		if (type == null)
			throw new Report.InternalError();

		return type;
    }

    /**
     * Checks that an expression has a type.
     */
    private TYP.Type requireExprType(final AST.Expr expr) {
        final TYP.Type type = SemAn.ofTypeAttr.get(expr);
		if (type == null)
			throw new Report.InternalError();
		return type;
    }

    /**
     * Returns the declared semantic type of a definition.
     */
    private TYP.Type declaredDefnType(final AST.Defn defn) {
        if (defn instanceof AST.TypDefn)
            return requireType(defn);

        if (defn instanceof AST.VarDefn varDefn)
            return requireType(varDefn.type);

        if (defn instanceof AST.FunDefn funDefn)
            return declaredFunType(funDefn);

        if (defn instanceof AST.ParDefn parDefn)
            return requireType(parDefn.type);

        if (defn instanceof AST.CompDefn compDefn)
            return requireType(compDefn.type);

        throw new Report.InternalError();
    }

    /**
     * Checks whether an expression is addressable.
     */
    private boolean isAddr(final AST.Expr expr) {
        final Boolean value = SemAn.isAddrAttr.get(expr);
		if (value == null)
			throw new Report.InternalError();
		return value;
    }

    /**
     * Checks whether an expression is constant.
     */
    private boolean isConst(final AST.Expr expr) {
        final Boolean value = SemAn.isConstAttr.get(expr);
		if (value == null)
			throw new Report.InternalError();
		return value;
    }

    /**
     * Require type int.
     */
	private void requireInt(final AST.Node node, final TYP.Type type) {
		if (!equiv(type, TYP.IntType.type))
			throw new Report.Error(node, "Expected type int.");
	}

    /**
     * Require type bool.
     */
    private void requireBool(final AST.Node node, final TYP.Type type) {
		if (!equiv(type, TYP.BoolType.type))
			throw new Report.Error(node, "Expected type bool.");
	}

    /**
     * Require type char.
     */
    private void requireChar(final AST.Node node, final TYP.Type type) {
		if (!equiv(type, TYP.CharType.type))
			throw new Report.Error(node, "Expected type char.");
	}

    /**
     * Require type void.
     */
    private void requireVoid(final AST.Node node, final TYP.Type type) {
		if (!equiv(type, TYP.VoidType.type))
			throw new Report.Error(node, "Expected type void.");
	}

    /**
     * Require non-void value type where needed.
     */
    private void requireNonVoid(final AST.Node node, final TYP.Type type) {
        if (equiv(type, TYP.VoidType.type))
			throw new Report.Error(node, "Expected type not void.");
    }

    /**
     * Require that two types are equivalent.
     */
    private void requireEquiv(final AST.Node node, final TYP.Type fstType, final TYP.Type sndType) {
        if (!equiv(fstType, sndType))
			throw new Report.Error(node, "Type mismatch: " + fstType.toString() + ", " + sndType.toString());
    }

    /**
     * Require that a type is an array type.
     */
    private TYP.ArrType requireArrType(final AST.Node node, final TYP.Type type) {
        final TYP.Type actual = actualType(type);
		if (!(actual instanceof TYP.ArrType arrType))
			throw new Report.Error(node, "Expected an array type.");
		return arrType;
    }

    /**
     * Require that a type is a pointer type.
     */
    private TYP.PtrType requirePtrType(final AST.Node node, final TYP.Type type) {
        final TYP.Type actual = actualType(type);
		if (!(actual instanceof TYP.PtrType ptrType))
			throw new Report.Error(node, "Expected a pointer type.");
		return ptrType;
    }

    /**
     * Require that a type is a function type.
     */
    private TYP.FunType requireFunType(final AST.Node node, final TYP.Type type) {
        final TYP.Type actual = actualType(type);
		if (!(actual instanceof TYP.FunType funType))
			throw new Report.Error(node, "Expected a function type.");
		return funType;
    }

    /**
     * Require that a type is a struct or union type.
     */
    private TYP.RecType requireRecType(final AST.Node node, final TYP.Type type) {
        final TYP.Type actual = actualType(type);
		if (!(actual instanceof TYP.RecType recType))
			throw new Report.Error(node, "Expected a struct or union type.");
		return recType;
    }

    /**
     * Checks whether a type is a legal parameter/assignment/comparison type.
     */
    private boolean isLegalSimpleOrCallableType(final TYP.Type type) {
        final TYP.Type actual = actualType(type);
		return (actual instanceof TYP.IntType)
			|| (actual instanceof TYP.CharType)
			|| (actual instanceof TYP.BoolType)
			|| (actual instanceof TYP.PtrType)
			|| (actual instanceof TYP.FunType);
    }

    /**
     * Checks whether a type is a legal function result type.
     */
    private boolean isLegalFunResultType(final TYP.Type type) {
        final TYP.Type actual = actualType(type);
		return (actual instanceof TYP.VoidType) || isLegalSimpleOrCallableType(actual);
    }

    /**
     * Require a legal parameter/assignment/comparison type.
     */
    private void requireSimpleOrCallableType(final AST.Node node, final TYP.Type type) {
        if (!isLegalSimpleOrCallableType(type))
			throw new Report.Error(node, "Illegal type in this context.");
    }

    /**
     * Checks whether a type is a legal variable type.
     */
    private void checkVarType(final AST.Node node, final TYP.Type type) {
        requireNonVoid(node, type);
    }

    /**
     * Checks whether a type is a legal parameter type.
     */
    private void checkParType(final AST.Node node, final TYP.Type type) {
        if (!isLegalSimpleOrCallableType(type))
			throw new Report.Error(node, "Illegal parameter type.");
    }

    /**
     * Checks whether a type is a legal function result type.
     */
    private void checkFunResultType(final AST.Node node, final TYP.Type type) {
        if (!isLegalFunResultType(type))
			throw new Report.Error(node, "Illegal function result type.");
    }

    /**
     * Checks whether a type is a legal component type.
     */
    private void checkCompType(final AST.Node node, final TYP.Type type) {
        requireNonVoid(node, type);
    }

    private TYP.FunType declaredFunType(final AST.FunDefn funDefn) {
        final ArrayList<TYP.Type> parTypes = new ArrayList<>();
        for (AST.ParDefn parDefn : funDefn.pars)
            parTypes.add(requireType(parDefn.type));

        final TYP.Type resType = requireType(funDefn.type);
        return new TYP.FunType(parTypes, resType);
    }

    /**
     * Check main function shape.
     */
    private void checkMain(final AST.Nodes<? extends AST.Node> nodes) {
		for (AST.Node node : nodes) {
			if (node instanceof AST.DefFunDefn funDefn && funDefn.name.equals("main")) {
				TYP.FunType mainType = declaredFunType(funDefn);
				if (mainType.parTypes.size() != 0 || !equiv(mainType.resType, TYP.IntType.type))
					throw new Report.Error(funDefn, "Function main must have type fun(() -> int).");
				return;
			}
		}
		throw new Report.Error(nodes, "Program must define function main.");
    }

    // ===== ROOT =====

    @Override
    public Object visit(AST.Nodes<? extends AST.Node> nodes, Object arg) {
        final boolean isProgramRoot = (!checkedProgramRoot)
			&& (nodes.size() > 0)
			&& (nodes.first() instanceof AST.FullDefn);

		if (isProgramRoot)
			checkedProgramRoot = true;

        for (AST.Node node : nodes) {
			if (node != null)
				node.accept(this, arg);
		}

		if (isProgramRoot)
			checkMain(nodes);
        return null;
    }

    // ===== DEFINITIONS =====

    @Override
    public Object visit(AST.TypDefn typDefn, Object arg) {
        typDefn.type.accept(this, arg);
        return null;
    }

    @Override
    public Object visit(AST.VarDefn varDefn, Object arg) {
        varDefn.type.accept(this, arg);
		TYP.Type varType = requireType(varDefn.type);
    	checkVarType(varDefn, varType);
        return null;
    }

    @Override
    public Object visit(AST.DefFunDefn defFunDefn, Object arg) {
        for (AST.ParDefn parDefn : defFunDefn.pars)
			parDefn.accept(this, arg);

		defFunDefn.type.accept(this, arg);
		defFunDefn.expr.accept(this, arg);

		TYP.FunType funType = declaredFunType(defFunDefn);
		checkFunResultType(defFunDefn, funType.resType);

		TYP.Type bodyType = requireExprType(defFunDefn.expr);
		requireEquiv(defFunDefn, bodyType, funType.resType);
        return null;
    }

    @Override
    public Object visit(AST.ExtFunDefn extFunDefn, Object arg) {
        for (AST.ParDefn parDefn : extFunDefn.pars)
			parDefn.accept(this, arg);

		extFunDefn.type.accept(this, arg);

		TYP.FunType funType = declaredFunType(extFunDefn);
		checkFunResultType(extFunDefn, funType.resType);
        return null;
    }

    @Override
    public Object visit(AST.ParDefn parDefn, Object arg) {
        parDefn.type.accept(this, arg);

		TYP.Type parType = requireType(parDefn.type);
		checkParType(parDefn, parType);
        return null;
    }

    @Override
    public Object visit(AST.CompDefn compDefn, Object arg) {
        compDefn.type.accept(this, arg);

		TYP.Type compType = requireType(compDefn.type);
		checkCompType(compDefn, compType);
        return null;
    }

    // ===== TYPE NODES =====

    @Override
    public Object visit(AST.AtomType atomType, Object arg) {
        // often nothing, type already constructed
        return null;
    }

    @Override
    public Object visit(AST.ArrType arrType, Object arg) {
        arrType.elemType.accept(this, arg);
		requireNonVoid(arrType.elemType, requireType(arrType.elemType));
        return null;
    }

    @Override
    public Object visit(AST.PtrType ptrType, Object arg) {
        ptrType.baseType.accept(this, arg);
		requireNonVoid(ptrType.baseType, requireType(ptrType.baseType));
        return null;
    }

    @Override
    public Object visit(AST.StrType strType, Object arg) {
        for (AST.CompDefn compDefn : strType.comps)
        	compDefn.accept(this, arg);
        return null;
    }

    @Override
    public Object visit(AST.UniType uniType, Object arg) {
        for (AST.CompDefn compDefn : uniType.comps)
        	compDefn.accept(this, arg);
        return null;
    }

    @Override
    public Object visit(AST.FunType funType, Object arg) {
        for (AST.Type parType : funType.parTypes) {
			parType.accept(this, arg);
			checkParType(parType, requireType(parType));
		}

		funType.resType.accept(this, arg);
		checkFunResultType(funType.resType, requireType(funType.resType));
        return null;
    }

    @Override
    public Object visit(AST.NameType nameType, Object arg) {
        // often nothing, already resolved by constructor
        return null;
    }

    // ===== EXPRESSIONS =====

    @Override
    public Object visit(AST.AtomExpr atomExpr, Object arg) {
        requireExprType(atomExpr);
        return null;
    }

    @Override
    public Object visit(AST.NameExpr nameExpr, Object arg) {
        requireExprType(nameExpr);
        return null;
    }

    @Override
    public Object visit(AST.ArrExpr arrExpr, Object arg) {
        arrExpr.arrExpr.accept(this, arg);
		arrExpr.idx.accept(this, arg);

		TYP.Type arrType = requireExprType(arrExpr.arrExpr);
		TYP.Type idxType = requireExprType(arrExpr.idx);

		requireArrType(arrExpr.arrExpr, arrType);
		requireInt(arrExpr.idx, idxType);
		if (!isAddr(arrExpr.arrExpr))
			throw new Report.Error(arrExpr.arrExpr, "Expression is not addressable.");
        return null;
    }

    @Override
    public Object visit(AST.AsgnExpr asgnExpr, Object arg) {
        asgnExpr.fstExpr.accept(this, arg);
		asgnExpr.sndExpr.accept(this, arg);

		TYP.Type fstType = requireExprType(asgnExpr.fstExpr);
		TYP.Type sndType = requireExprType(asgnExpr.sndExpr);

		if (!isAddr(asgnExpr.fstExpr))
			throw new Report.Error(asgnExpr.fstExpr, "Left-hand side is not addressable.");

		requireEquiv(asgnExpr, fstType, sndType);
		requireSimpleOrCallableType(asgnExpr, fstType);
        return null;
    }

    @Override
    public Object visit(AST.BinExpr binExpr, Object arg) {
        binExpr.fstExpr.accept(this, arg);
		binExpr.sndExpr.accept(this, arg);

		TYP.Type fstType = requireExprType(binExpr.fstExpr);
		TYP.Type sndType = requireExprType(binExpr.sndExpr);

		switch (binExpr.oper) {
			case ADD, SUB, MUL, DIV, MOD -> {
				requireInt(binExpr.fstExpr, fstType);
				requireInt(binExpr.sndExpr, sndType);
			}
			case AND, OR -> {
				requireBool(binExpr.fstExpr, fstType);
				requireBool(binExpr.sndExpr, sndType);
			}
			case EQU, NEQ, LTH, GTH, LEQ, GEQ -> {
				requireEquiv(binExpr, fstType, sndType);
				requireSimpleOrCallableType(binExpr.fstExpr, fstType);
			}
		}
        return null;
    }

    @Override
    public Object visit(AST.CallExpr callExpr, Object arg) {
        callExpr.funExpr.accept(this, arg);
		callExpr.argExprs.accept(this, arg);

		TYP.FunType funType = requireFunType(callExpr.funExpr, requireExprType(callExpr.funExpr));

		if (funType.parTypes.size() != callExpr.argExprs.size())
			throw new Report.Error(callExpr, "Wrong number of arguments.");

		for (int i = 0; i < callExpr.argExprs.size(); i++) {
			TYP.Type argType = requireExprType(callExpr.argExprs.get(i));
			TYP.Type parType = funType.parTypes.get(i);
			requireEquiv(callExpr.argExprs.get(i), argType, parType);
		}
        return null;
    }

    @Override
    public Object visit(AST.CastExpr castExpr, Object arg) {
        castExpr.expr.accept(this, arg);
		castExpr.type.accept(this, arg);

		TYP.Type exprType = requireExprType(castExpr.expr);
		TYP.Type targetType = requireType(castExpr.type);

		requireNonVoid(castExpr.expr, exprType);
		requireNonVoid(castExpr.type, targetType);
        return null;
    }

    @Override
    public Object visit(AST.CompExpr compExpr, Object arg) {
        compExpr.recExpr.accept(this, arg);

		TYP.Type recType = requireExprType(compExpr.recExpr);
		TYP.RecType recActual = requireRecType(compExpr, recType);

		if (!isAddr(compExpr.recExpr))
			throw new Report.Error(compExpr.recExpr, "Expression is not addressable.");

		LinkedHashMap<String, AST.CompDefn> comps = TypeConstructor.recComps.get(recActual);
		if (comps == null)
			throw new Report.InternalError();

		AST.CompDefn compDefn = comps.get(compExpr.name);
		if (compDefn == null)
			throw new Report.Error(compExpr, "No such component '" + compExpr.name + "'.");

		return null;
    }

    @Override
    public Object visit(AST.PfxExpr pfxExpr, Object arg) {
        pfxExpr.subExpr.accept(this, arg);

		TYP.Type subType = requireExprType(pfxExpr.subExpr);

		switch (pfxExpr.oper) {
			case NOT -> requireBool(pfxExpr.subExpr, subType);
			case ADD, SUB -> requireInt(pfxExpr.subExpr, subType);
			case PTR -> {
				if (!isAddr(pfxExpr.subExpr))
					throw new Report.Error(pfxExpr.subExpr, "Expression is not addressable.");
				requireNonVoid(pfxExpr.subExpr, subType);
			}
		}
        return null;
    }

    @Override
    public Object visit(AST.SfxExpr sfxExpr, Object arg) {
        sfxExpr.subExpr.accept(this, arg);

		TYP.PtrType ptrType = requirePtrType(sfxExpr.subExpr, requireExprType(sfxExpr.subExpr));
		requireNonVoid(sfxExpr, ptrType.baseType);
        return null;
    }

    @Override
    public Object visit(AST.SizeExpr sizeExpr, Object arg) {
        sizeExpr.type.accept(this, arg);

		TYP.Type type = requireType(sizeExpr.type);
		requireNonVoid(sizeExpr, type);
        return null;
    }

    @Override
    public Object visit(AST.Exprs exprs, Object arg) {
        exprs.exprs.accept(this, arg);
		for (AST.Expr expr : exprs.exprs)
			requireExprType(expr);
        return null;
    }

    @Override
    public Object visit(AST.IfThenExpr ifThenExpr, Object arg) {
        ifThenExpr.condExpr.accept(this, arg);
		ifThenExpr.thenExpr.accept(this, arg);

		requireBool(ifThenExpr.condExpr, requireExprType(ifThenExpr.condExpr));
		requireExprType(ifThenExpr.thenExpr);
        return null;
    }

    @Override
    public Object visit(AST.IfThenElseExpr ifThenElseExpr, Object arg) {
        ifThenElseExpr.condExpr.accept(this, arg);
		ifThenElseExpr.thenExpr.accept(this, arg);
		ifThenElseExpr.elseExpr.accept(this, arg);

		requireBool(ifThenElseExpr.condExpr, requireExprType(ifThenElseExpr.condExpr));
		requireExprType(ifThenElseExpr.thenExpr);
		requireExprType(ifThenElseExpr.elseExpr);
        return null;
    }

    @Override
    public Object visit(AST.WhileExpr whileExpr, Object arg) {
        whileExpr.condExpr.accept(this, arg);
		whileExpr.expr.accept(this, arg);

		requireBool(whileExpr.condExpr, requireExprType(whileExpr.condExpr));
		requireExprType(whileExpr.expr);
        return null;
    }

    @Override
    public Object visit(AST.LetExpr letExpr, Object arg) {
        letExpr.defns.accept(this, arg);
    	letExpr.expr.accept(this, arg);
        return null;
    }
}
