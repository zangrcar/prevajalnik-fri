package prev26lang.phase.seman;

import java.util.*;

import prev26lang.common.report.*;
import prev26lang.phase.abstr.*;

public class TypeConstructor implements AST.FullVisitor<Object, TypeConstructor.Phase> {

    enum Phase {
        DECLARE_TYPES,
        DEFINE_TYPES,
        RESOLVE_VARS,
        DECLARE_FUNS,
        RESOLVE_FUN_BODIES
    }

    public TypeConstructor() {
    }

    /**
     * Side structure for record components.
     * TYP.StrType / TYP.UniType store only component types, not names.
     */
    public static final Map<TYP.Type, LinkedHashMap<String, AST.CompDefn>> recComps = new HashMap<TYP.Type, LinkedHashMap<String, AST.CompDefn>>();

    // ===== HELPERS =====

    private TYP.NameType getDeclaredNameType(AST.TypDefn typDefn) {
        final TYP.Type type = SemAn.isTypeAttr.get(typDefn);
		if (!(type instanceof TYP.NameType nameType))
			throw new Report.InternalError();
		return nameType;
   	}

	private void registerRecordComps(TYP.Type recType, AST.Nodes<AST.CompDefn> comps) {
		final LinkedHashMap<String, AST.CompDefn> byName = new LinkedHashMap<>();

		for (AST.CompDefn compDefn : comps) {
			if (byName.containsKey(compDefn.name)) {
				throw new Report.Error(
					compDefn,
					"Duplicate component name '" + compDefn.name + "'."
				);
			}

			final TYP.Type compType = constructType(compDefn.type);
			SemAn.ofTypeAttr.put(compDefn, compType);
			byName.put(compDefn.name, compDefn);
		}

		recComps.put(recType, byName);
	}

	private void ensureAcyclic(TYP.Type type, HashSet<TYP.NameType> visiting, HashSet<TYP.NameType> checked, AST.Node blame) {
		if (type instanceof TYP.NameType nameType) {
			if (checked.contains(nameType))
				return;

			if (visiting.contains(nameType)) {
				throw new Report.Error(
					blame,
					"Cyclic type definition involving '" + nameType.name + "'."
				);
			}

			visiting.add(nameType);
			ensureAcyclic(nameType.type(), visiting, checked, blame);
			visiting.remove(nameType);
			checked.add(nameType);
			return;
		}

		if (type instanceof TYP.ArrType arrType) {
			ensureAcyclic(arrType.elemType, visiting, checked, blame);
			return;
		}

		if (type instanceof TYP.PtrType ptrType) {
			ensureAcyclic(ptrType.baseType, visiting, checked, blame);
			return;
		}

		if (type instanceof TYP.StrType strType) {
			for (TYP.Type compType : strType.compTypes)
				ensureAcyclic(compType, visiting, checked, blame);
			return;
		}

		if (type instanceof TYP.UniType uniType) {
			for (TYP.Type compType : uniType.compTypes)
				ensureAcyclic(compType, visiting, checked, blame);
			return;
		}

		if (type instanceof TYP.FunType funType) {
			for (TYP.Type parType : funType.parTypes)
				ensureAcyclic(parType, visiting, checked, blame);
			ensureAcyclic(funType.resType, visiting, checked, blame);
			return;
		}

		// atom types: nothing to do
	}

	private void checkTypeCycles(AST.Nodes<? extends AST.Defn> defns) {
		final HashSet<TYP.NameType> checked = new HashSet<>();

		for (AST.Defn defn : defns) {
			if (defn instanceof AST.TypDefn typDefn) {
				ensureAcyclic(
					getDeclaredNameType(typDefn),
					new HashSet<>(),
					checked,
					typDefn
				);
			}
		}
	}

    private TYP.Type constructType(final AST.Type type) {
		final TYP.Type cached = SemAn.isTypeAttr.get(type);
		if (cached != null)
			return cached;

		final TYP.Type semType;

		if (type instanceof AST.AtomType atomType) {
			semType = switch (atomType.type) {
				case INT -> TYP.IntType.type;
				case CHAR -> TYP.CharType.type;
				case BOOL -> TYP.BoolType.type;
				case VOID -> TYP.VoidType.type;
			};
		}

		else if (type instanceof AST.NameType nameType) {
			final AST.Defn defn = SemAn.defAtAttr.get(nameType);
			if (!(defn instanceof AST.TypDefn typDefn))
				throw new Report.InternalError();

			final TYP.Type declared = SemAn.isTypeAttr.get(typDefn);
			if (!(declared instanceof TYP.NameType))
				throw new Report.InternalError();

			semType = declared;
		}

		else if (type instanceof AST.ArrType arrType) {
			final TYP.Type elemType = constructType(arrType.elemType);

			final long numElems;
			try {
				numElems = Long.parseLong(arrType.numElems);
			} catch (NumberFormatException ex) {
				throw new Report.Error(arrType, "Invalid array size.");
			}

			// Optional here, or later in TypeChecker:
			if (numElems <= 0)
			throw new Report.Error(arrType, "Array size must be positive.");

			semType = new TYP.ArrType(elemType, numElems);
		}

		else if (type instanceof AST.PtrType ptrType) {
			final TYP.Type baseType = constructType(ptrType.baseType);
			semType = new TYP.PtrType(baseType);
		}

		else if (type instanceof AST.StrType strType) {
			final ArrayList<TYP.Type> compTypes = new ArrayList<>(strType.comps.size());

			for (AST.CompDefn compDefn : strType.comps) {
				final TYP.Type compType = constructType(compDefn.type);
				compTypes.add(compType);
			}

			semType = new TYP.StrType(compTypes);
			registerRecordComps(semType, strType.comps);
		}

		else if (type instanceof AST.UniType uniType) {
			final ArrayList<TYP.Type> compTypes = new ArrayList<>(uniType.comps.size());

			for (AST.CompDefn compDefn : uniType.comps) {
				final TYP.Type compType = constructType(compDefn.type);
				compTypes.add(compType);
			}

			semType = new TYP.UniType(compTypes);
			registerRecordComps(semType, uniType.comps);
		}

		else if (type instanceof AST.FunType funType) {
			final ArrayList<TYP.Type> parTypes = new ArrayList<>(funType.parTypes.size());

			for (AST.Type parType : funType.parTypes) {
				parTypes.add(constructType(parType));
			}

			final TYP.Type resType = constructType(funType.resType);
			semType = new TYP.FunType(parTypes, resType);
		}

		else {
			throw new Report.InternalError();
		}

		SemAn.isTypeAttr.put(type, semType);
		return semType;
	}

    private TYP.FunType constructFunType(AST.Nodes<AST.ParDefn> pars, AST.Type resultType) {
		final ArrayList<TYP.Type> parTypes = new ArrayList<>(pars.size());

		for (AST.ParDefn par : pars) {
			final TYP.Type parType = constructType(par.type);
			parTypes.add(parType);
		}

		final TYP.Type resType = constructType(resultType);
        return new TYP.FunType(parTypes, resType);
    }

    private void resolveVarDefn(AST.VarDefn varDefn) {
        final TYP.Type varType = constructType(varDefn.type);
		SemAn.ofTypeAttr.put(varDefn, varType);
    }

    private void declareFunDefn(AST.FunDefn funDefn) {
       	final TYP.FunType funType = constructFunType(funDefn.pars, funDefn.type);
		SemAn.ofTypeAttr.put(funDefn, funType);

		for (AST.ParDefn parDefn : funDefn.pars) {
			SemAn.ofTypeAttr.put(parDefn, constructType(parDefn.type));
		}
    }

    private void resolveFunBody(AST.DefFunDefn defFunDefn) {
        defFunDefn.expr.accept(this, Phase.RESOLVE_FUN_BODIES);
    }

    /**
     * Optional helper for nested let-blocks:
     * run the same 5 local passes on let definitions.
     */
    private void resolveLocalDefs(AST.Nodes<? extends AST.Defn> defns) {
        defns.accept(this, Phase.DECLARE_TYPES);
		defns.accept(this, Phase.DEFINE_TYPES);
		checkTypeCycles(defns);
		defns.accept(this, Phase.RESOLVE_VARS);
		defns.accept(this, Phase.DECLARE_FUNS);
		defns.accept(this, Phase.RESOLVE_FUN_BODIES);

    }

    // ===== ROOT =====

    @Override
    public Object visit(AST.Nodes<? extends AST.Node> nodes, Phase phase) {
        if (phase == null) {
			@SuppressWarnings("unchecked")
			final AST.Nodes<? extends AST.Defn> defns = (AST.Nodes<? extends AST.Defn>) nodes;

            nodes.accept(this, Phase.DECLARE_TYPES);
            nodes.accept(this, Phase.DEFINE_TYPES);
			checkTypeCycles(defns);
            nodes.accept(this, Phase.RESOLVE_VARS);
            nodes.accept(this, Phase.DECLARE_FUNS);
            nodes.accept(this, Phase.RESOLVE_FUN_BODIES);
            return null;
        }

        for (AST.Node node : nodes) {
            if (node != null)
                node.accept(this, phase);
        }
        return null;
    }

    // ===== DEFINITIONS =====

    @Override
    public Object visit(AST.TypDefn typDefn, Phase phase) {
        switch (phase) {
            case DECLARE_TYPES:
                SemAn.isTypeAttr.put(typDefn, new TYP.NameType(typDefn.name));
                break;

            case DEFINE_TYPES:
                TYP.NameType declared = getDeclaredNameType(typDefn);
				TYP.Type rhs = constructType(typDefn.type);
				declared.setActType(rhs);
                break;

            default:
                break;
        }
        return null;
    }

    @Override
    public Object visit(AST.VarDefn varDefn, Phase phase) {
        if (phase == Phase.RESOLVE_VARS) {
            resolveVarDefn(varDefn);
        }
        return null;
    }

    @Override
    public Object visit(AST.DefFunDefn defFunDefn, Phase phase) {
        switch (phase) {
            case DECLARE_FUNS:
                declareFunDefn(defFunDefn);
                break;

            case RESOLVE_FUN_BODIES:
                resolveFunBody(defFunDefn);
                break;

            default:
                break;
        }
        return null;
    }

    @Override
    public Object visit(AST.ExtFunDefn extFunDefn, Phase phase) {
        if (phase == Phase.DECLARE_FUNS) {
            declareFunDefn(extFunDefn);
        }
        return null;
    }

    // @Override
    // public Object visit(AST.ParDefn parDefn, Phase phase) {
    //     switch (phase) {
    //         case DECLARE_FUNS:
    //             SemAn.ofTypeAttr.put(parDefn, constructType(parDefn.type));
    //             break;

    //         default:
    //             break;
    //     }
    //     return null;
    // }

    @Override
    public Object visit(AST.CompDefn compDefn, Phase phase) {
        if (phase == Phase.DEFINE_TYPES) {
            SemAn.ofTypeAttr.put(compDefn, constructType(compDefn.type));
        }
        return null;
    }

    // ===== TYPE NODES =====

    @Override
    public Object visit(AST.AtomType atomType, Phase phase) {
        constructType(atomType);
        return null;
    }

    @Override
    public Object visit(AST.ArrType arrType, Phase phase) {
        constructType(arrType);
        return null;
    }

    @Override
    public Object visit(AST.PtrType ptrType, Phase phase) {
        constructType(ptrType);
        return null;
    }

    @Override
    public Object visit(AST.StrType strType, Phase phase) {
        constructType(strType);
        return null;
    }

    @Override
    public Object visit(AST.UniType uniType, Phase phase) {
        constructType(uniType);
        return null;
    }

    @Override
    public Object visit(AST.FunType funType, Phase phase) {
        constructType(funType);
        return null;
    }

    @Override
    public Object visit(AST.NameType nameType, Phase phase) {
        constructType(nameType);
        return null;
    }

    // ===== EXPRESSIONS =====

    @Override
    public Object visit(AST.AtomExpr atomExpr, Phase phase) {
        if (phase == Phase.RESOLVE_FUN_BODIES) {
            TYP.Type type = switch (atomExpr.type) {
				case INT -> TYP.IntType.type;
				case CHAR -> TYP.CharType.type;
				case BOOL -> TYP.BoolType.type;
				case VOID -> TYP.VoidType.type;
				case PTR -> TYP.PtrType.type;
				case STR -> new TYP.PtrType(TYP.CharType.type);
			};

			SemAn.ofTypeAttr.put(atomExpr, type);
			SemAn.isConstAttr.put(atomExpr, true);
			SemAn.isAddrAttr.put(atomExpr, false);
        }
        return null;
    }

    @Override
    public Object visit(AST.NameExpr nameExpr, Phase phase) {
        if (phase == Phase.RESOLVE_FUN_BODIES) {
            AST.Defn defn = SemAn.defAtAttr.get(nameExpr);
			TYP.Type type = SemAn.ofTypeAttr.get(defn);

			SemAn.ofTypeAttr.put(nameExpr, type);
			SemAn.isConstAttr.put(nameExpr, false);
			SemAn.isAddrAttr.put(nameExpr, (defn instanceof AST.VarDefn) || (defn instanceof AST.ParDefn));
        }
        return null;
    }

    @Override
    public Object visit(AST.ArrExpr arrExpr, Phase phase) {
        if (phase == Phase.RESOLVE_FUN_BODIES) {
            arrExpr.arrExpr.accept(this, phase);
			arrExpr.idx.accept(this, phase);

			TYP.Type arrType = SemAn.ofTypeAttr.get(arrExpr.arrExpr);
			TYP.Type actual = (arrType == null ? null : arrType.actualType());
			TYP.Type elemType = null;

			if (actual instanceof TYP.ArrType at)
				elemType = at.elemType;

			SemAn.ofTypeAttr.put(arrExpr, elemType);
			SemAn.isConstAttr.put(arrExpr, false);
			SemAn.isAddrAttr.put(arrExpr, true);
        }
        return null;
    }

    @Override
    public Object visit(AST.AsgnExpr asgnExpr, Phase phase) {
        if (phase == Phase.RESOLVE_FUN_BODIES) {
            asgnExpr.fstExpr.accept(this, phase);
			asgnExpr.sndExpr.accept(this, phase);

			SemAn.ofTypeAttr.put(asgnExpr, TYP.VoidType.type);
			SemAn.isConstAttr.put(asgnExpr, false);
			SemAn.isAddrAttr.put(asgnExpr, false);
        }
        return null;
    }

    @Override
    public Object visit(AST.BinExpr binExpr, Phase phase) {
        if (phase == Phase.RESOLVE_FUN_BODIES) {
            binExpr.fstExpr.accept(this, phase);
			binExpr.sndExpr.accept(this, phase);

			TYP.Type type = switch (binExpr.oper) {
				case ADD, SUB, MUL, DIV, MOD -> TYP.IntType.type;
				case AND, OR, EQU, NEQ, LTH, GTH, LEQ, GEQ -> TYP.BoolType.type;
			};

			SemAn.ofTypeAttr.put(binExpr, type);
			SemAn.isConstAttr.put(binExpr,
				Boolean.TRUE.equals(SemAn.isConstAttr.get(binExpr.fstExpr)) &&
				Boolean.TRUE.equals(SemAn.isConstAttr.get(binExpr.sndExpr)));
			SemAn.isAddrAttr.put(binExpr, false);
        }
        return null;
    }

    @Override
    public Object visit(AST.CallExpr callExpr, Phase phase) {
        if (phase == Phase.RESOLVE_FUN_BODIES) {
            callExpr.argExprs.accept(this, phase);
			callExpr.funExpr.accept(this, phase);
			
			TYP.Type funType = SemAn.ofTypeAttr.get(callExpr.funExpr);
			TYP.Type actual = (funType == null ? null : funType.actualType());
			TYP.Type resultType = null;

			if (actual instanceof TYP.FunType ft)
				resultType = ft.resType;

			SemAn.ofTypeAttr.put(callExpr, resultType);
			SemAn.isConstAttr.put(callExpr, false);
			SemAn.isAddrAttr.put(callExpr, false);
        }
        return null;
    }

    @Override
    public Object visit(AST.CastExpr castExpr, Phase phase) {
        if (phase == Phase.RESOLVE_FUN_BODIES) {
            castExpr.expr.accept(this, phase);
			TYP.Type targetType = constructType(castExpr.type);

    		SemAn.ofTypeAttr.put(castExpr, targetType);
			SemAn.isConstAttr.put(castExpr, false);
			SemAn.isAddrAttr.put(castExpr, false);
        }
        return null;
    }

    @Override
    public Object visit(AST.CompExpr compExpr, Phase phase) {
        if (phase == Phase.RESOLVE_FUN_BODIES) {
            compExpr.recExpr.accept(this, phase);
			TYP.Type recType = SemAn.ofTypeAttr.get(compExpr.recExpr);
			TYP.Type actual = (recType == null ? null : recType.actualType());

			TYP.Type compType = null;

			if (actual instanceof TYP.StrType || actual instanceof TYP.UniType) {
				final LinkedHashMap<String, AST.CompDefn> comps = recComps.get(actual);

				if (comps != null) {
					final AST.CompDefn compDefn = comps.get(compExpr.name);
					if (compDefn != null) {
						compType = SemAn.ofTypeAttr.get(compDefn);
					}
				}
			}

			SemAn.ofTypeAttr.put(compExpr, compType);
			SemAn.isConstAttr.put(compExpr, false);
			SemAn.isAddrAttr.put(compExpr,
				Boolean.TRUE.equals(SemAn.isAddrAttr.get(compExpr.recExpr)));
        }
        return null;
    }

    @Override
    public Object visit(AST.PfxExpr pfxExpr, Phase phase) {
        if (phase == Phase.RESOLVE_FUN_BODIES) {

			pfxExpr.subExpr.accept(this, phase);
			TYP.Type subType = SemAn.ofTypeAttr.get(pfxExpr.subExpr);

			TYP.Type type = switch (pfxExpr.oper) {
				case NOT -> TYP.BoolType.type;
				case ADD, SUB -> TYP.IntType.type;
				case PTR -> new TYP.PtrType(subType);
			};

			SemAn.ofTypeAttr.put(pfxExpr, type);
			SemAn.isConstAttr.put(pfxExpr, false);
			SemAn.isAddrAttr.put(pfxExpr, false);
        }
        return null;
    }

    @Override
    public Object visit(AST.SfxExpr sfxExpr, Phase phase) {
        if (phase == Phase.RESOLVE_FUN_BODIES) {
            sfxExpr.subExpr.accept(this, phase);
			TYP.Type subType = SemAn.ofTypeAttr.get(sfxExpr.subExpr);
			TYP.Type actual = (subType == null ? null : subType.actualType());

			// constructor-style rough shape:
			TYP.Type result = null;
			if (actual instanceof TYP.PtrType ptrType) {
				result = ptrType.baseType;
			}

			SemAn.ofTypeAttr.put(sfxExpr, result);
			SemAn.isConstAttr.put(sfxExpr, false);
			SemAn.isAddrAttr.put(sfxExpr, true);
        }
        return null;
    }

    @Override
    public Object visit(AST.SizeExpr sizeExpr, Phase phase) {
        if (phase == Phase.RESOLVE_FUN_BODIES) {
            constructType(sizeExpr.type);

			SemAn.ofTypeAttr.put(sizeExpr, TYP.IntType.type);
			SemAn.isConstAttr.put(sizeExpr, true);
			SemAn.isAddrAttr.put(sizeExpr, false);
        }
        return null;
    }

    @Override
    public Object visit(AST.Exprs exprs, Phase phase) {
        if (phase == Phase.RESOLVE_FUN_BODIES) {
            exprs.exprs.accept(this, phase);

			TYP.Type type = SemAn.ofTypeAttr.get(exprs.exprs.last());
			SemAn.ofTypeAttr.put(exprs, type);
			SemAn.isConstAttr.put(exprs, false);
			SemAn.isAddrAttr.put(exprs, false);
        }
        return null;
    }

    @Override
    public Object visit(AST.IfThenExpr ifThenExpr, Phase phase) {
        if (phase == Phase.RESOLVE_FUN_BODIES) {
            ifThenExpr.condExpr.accept(this, phase);
			ifThenExpr.thenExpr.accept(this, phase);
			TYP.Type result = TYP.VoidType.type;
			SemAn.ofTypeAttr.put(ifThenExpr, result);
			SemAn.isConstAttr.put(ifThenExpr, false);
			SemAn.isAddrAttr.put(ifThenExpr, false);
        }
        return null;
    }

    @Override
    public Object visit(AST.IfThenElseExpr ifThenElseExpr, Phase phase) {
        if (phase == Phase.RESOLVE_FUN_BODIES) {
            ifThenElseExpr.condExpr.accept(this, phase);
			ifThenElseExpr.thenExpr.accept(this, phase);
			ifThenElseExpr.elseExpr.accept(this, phase);
			TYP.Type result = TYP.VoidType.type;
			SemAn.ofTypeAttr.put(ifThenElseExpr, result);
			SemAn.isConstAttr.put(ifThenElseExpr, false);
			SemAn.isAddrAttr.put(ifThenElseExpr, false);
        }
        return null;
    }

    @Override
    public Object visit(AST.WhileExpr whileExpr, Phase phase) {
        if (phase == Phase.RESOLVE_FUN_BODIES) {
            whileExpr.condExpr.accept(this, phase);
			whileExpr.expr.accept(this, phase);
			TYP.Type result = TYP.VoidType.type;
			SemAn.ofTypeAttr.put(whileExpr, result);
			SemAn.isConstAttr.put(whileExpr, false);
			SemAn.isAddrAttr.put(whileExpr, false);
        }
        return null;
    }

    @Override
    public Object visit(AST.LetExpr letExpr, Phase phase) {
        if (phase == Phase.RESOLVE_FUN_BODIES) {
            resolveLocalDefs(letExpr.defns);
			letExpr.expr.accept(this, phase);

			SemAn.ofTypeAttr.put(letExpr, SemAn.ofTypeAttr.get(letExpr.expr));
			SemAn.isConstAttr.put(letExpr, false);
			SemAn.isAddrAttr.put(letExpr, false);
        }
        return null;
    }
}