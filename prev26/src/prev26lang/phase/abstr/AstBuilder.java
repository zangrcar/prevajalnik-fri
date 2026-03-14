package prev26lang.phase.abstr;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import prev26lang.common.report.Locatable;
import prev26lang.common.report.Location;
import prev26lang.common.report.Report;
import prev26lang.phase.abstr.AST;
import prev26lang.phase.abstr.Abstr;
import prev26lang.phase.lexan.LexAn;
import prev26lang.phase.synan.*;

public class AstBuilder extends Prev26ParserBaseVisitor<AST.Node> {

    private Location loc(Token tok) {
        return new Location((LexAn.LocLogToken) tok);
    }

    private Location loc(Locatable loc) {
        return new Location(loc);
    }

    private Location loc(Token first, Token last) {
        return new Location((LexAn.LocLogToken) first, (LexAn.LocLogToken) last);
    }

    private Location loc(Token first, Locatable last) {
        return new Location((LexAn.LocLogToken) first, last);
    }

    private Location loc(Locatable first, Token last) {
        return new Location(first, (LexAn.LocLogToken) last);
    }

    private Location loc(Locatable first, Locatable last) {
        return new Location(first, last);
    }

    private Location loc(ParserRuleContext ctx) {
        return loc(ctx.start, ctx.stop);
    }

    private <N extends AST.Node> N bind(N node, ParserRuleContext ctx) {
        Abstr.locAttr.put(node, loc(ctx));
        return node;
    }

    private <N extends AST.Node> N bind(N node, Locatable loc) {
        Abstr.locAttr.put(node, loc(loc));
        return node;
    }

    private <N extends AST.Node> N bind(N node, Token first, Token last) {
        Abstr.locAttr.put(node, loc(first, last));
        return node;
    }

    private <N extends AST.Node> N bind(N node, Token first, Locatable last) {
        Abstr.locAttr.put(node, loc(first, last));
        return node;
    }

    private <N extends AST.Node> N bind(N node, Locatable first, Token last) {
        Abstr.locAttr.put(node, loc(first, last));
        return node;
    }

    private <N extends AST.Node> N bind(N node, Locatable first, Locatable last) {
        Abstr.locAttr.put(node, loc(first, last));
        return node;
    }

    @Override
    public AST.Nodes<AST.FullDefn> visitSource(Prev26Parser.SourceContext ctx) {
        return visitProg(ctx.prog());
    }

    @Override
    public AST.Nodes<AST.FullDefn> visitProg(Prev26Parser.ProgContext ctx) {
        List<AST.FullDefn> defs = new ArrayList<>();

        defs.add(visitD(ctx.d()));
        defs.addAll(collectProgTail(ctx.prog_tail()));

        return bind(new AST.Nodes<>(defs), ctx);
    }

    private List<AST.FullDefn> collectProgTail(Prev26Parser.Prog_tailContext ctx) {
        List<AST.FullDefn> defs = new ArrayList<>();

        if (ctx == null || ctx.d() == null) {
            return defs;
        }

        defs.add(visitD(ctx.d()));
        defs.addAll(collectProgTail(ctx.prog_tail()));

        return defs;
    }

    @Override
    public AST.FullDefn visitD(Prev26Parser.DContext ctx) {
        if (ctx.TYP() != null) {
            AST.Type type = visitT(ctx.t());
            return bind(new AST.TypDefn(ctx.NAME().getText(), type), ctx);
        }

        if (ctx.VAR() != null) {
            AST.Type type = visitT(ctx.t());
            return bind(new AST.VarDefn(ctx.NAME().getText(), type), ctx);
        }

        if (ctx.FUN() != null) {
            String name = ctx.NAME().getText();
            AST.Nodes<AST.ParDefn> pars = visitParams(ctx.params());
            AST.Type type = visitT(ctx.t());

            if (ctx.fun_tail() != null && ctx.fun_tail().expr_list() != null) {
                AST.Expr body = visitExpr_list(ctx.fun_tail().expr_list());
                return bind(new AST.DefFunDefn(name, pars, type, body), ctx);
            }

            return bind(new AST.ExtFunDefn(name, pars, type), ctx);
        }

        throw new Report.InternalError();
    }

    @Override
    public AST.Nodes<AST.ParDefn> visitParams(Prev26Parser.ParamsContext ctx) {
        List<AST.ParDefn> pars = new ArrayList<>();

        if (ctx == null || ctx.param() == null) {
            return bind(new AST.Nodes<>(pars), ctx);
        }

        pars.add(visitParam(ctx.param()));
        pars.addAll(collectParamTail(ctx.param_tail()));

        return bind(new AST.Nodes<>(pars), ctx);
    }

    private List<AST.ParDefn> collectParamTail(Prev26Parser.Param_tailContext ctx) {
        List<AST.ParDefn> pars = new ArrayList<>();

        if (ctx == null || ctx.param() == null) {
            return pars;
        }

        pars.add(visitParam(ctx.param()));
        pars.addAll(collectParamTail(ctx.param_tail()));

        return pars;
    }

    @Override
    public AST.ParDefn visitParam(Prev26Parser.ParamContext ctx) {
        AST.Type type = visitT(ctx.t());
        return bind(new AST.ParDefn(ctx.NAME().getText(), type), ctx);
    }

    @Override
    public AST.Type visitT(Prev26Parser.TContext ctx) {
        if (ctx.basic_t() != null) {
            return visitBasic_t(ctx.basic_t());
        }

        if (ctx.NAME() != null) {
            return bind(new AST.NameType(ctx.NAME().getText()), ctx);
        }

        if (ctx.array_t() != null) {
            return visitArray_t(ctx.array_t());
        }

        if (ctx.pointer_t() != null) {
            return visitPointer_t(ctx.pointer_t());
        }

        if (ctx.object_t() != null) {
            return visitObject_t(ctx.object_t());
        }

        if (ctx.par_t() != null) {
            return bind(visitPar_t(ctx.par_t()), ctx);
        }

        throw new Report.InternalError();
    }

    @Override
    public AST.AtomType visitBasic_t(Prev26Parser.Basic_tContext ctx) {
        if (ctx.INT() != null) {
            return bind(new AST.AtomType(AST.AtomType.Type.INT), ctx);
        }
        if (ctx.CHAR() != null) {
            return bind(new AST.AtomType(AST.AtomType.Type.CHAR), ctx);
        }
        if (ctx.BOOL() != null) {
            return bind(new AST.AtomType(AST.AtomType.Type.BOOL), ctx);
        }
        if (ctx.VOID() != null) {
            return bind(new AST.AtomType(AST.AtomType.Type.VOID), ctx);
        }

        throw new Report.InternalError();
    }

    @Override
    public AST.ArrType visitArray_t(Prev26Parser.Array_tContext ctx) {
        AST.Type elemType = visitT(ctx.t());
        String numElems = ctx.c_int().getText();
        return bind(new AST.ArrType(elemType, numElems), ctx);
    }

    @Override
    public AST.PtrType visitPointer_t(Prev26Parser.Pointer_tContext ctx) {
        AST.Type baseType = visitT(ctx.t());
        return bind(new AST.PtrType(baseType), ctx);
    }

    @Override
    public AST.RecType visitObject_t(Prev26Parser.Object_tContext ctx) {
        AST.Nodes<AST.CompDefn> comps = visitComps(ctx.comps());
        return bind(new AST.UniType(comps), ctx);
    }

    @Override
    public AST.Type visitPar_t(Prev26Parser.Par_tContext ctx) {
        if (ctx.fun_t() != null) {
            return visitFun_t(ctx.fun_t());
        }

        if (ctx.par_t_parse_id() != null) {
            return visitPar_t_parse_id(ctx.par_t_parse_id());
        }

        throw new Report.InternalError();
    }

    @Override
    public AST.Type visitPar_t_parse_id(Prev26Parser.Par_t_parse_idContext ctx) {
        if (ctx.NAME() != null) {
            if (ctx.par_after_id() != null && ctx.par_after_id().t() != null) {
                List<AST.CompDefn> comps = new ArrayList<>();
                AST.Type firstType = visitT(ctx.par_after_id().t());
                AST.CompDefn firstComp = bind(new AST.CompDefn(ctx.NAME().getText(), firstType), ctx.NAME().getSymbol(), firstType);

                comps.add(firstComp);
                comps.addAll(collectCompParamTail(ctx.par_after_id().param_tail()));

                AST.Nodes<AST.CompDefn> compNodes = bind(new AST.Nodes<>(comps), ctx);
                return bind(new AST.StrType(compNodes), ctx);
            }

            return bind(new AST.NameType(ctx.NAME().getText()), ctx);
        }

        if (ctx.non_id_t() != null) {
            return bind(visitNon_id_t(ctx.non_id_t()), ctx);
        }

        throw new Report.InternalError();
    }

    @Override
    public AST.Type visitNon_id_t(Prev26Parser.Non_id_tContext ctx) {
        if (ctx.basic_t() != null) {
            return visitBasic_t(ctx.basic_t());
        }

        if (ctx.array_t() != null) {
            return visitArray_t(ctx.array_t());
        }

        if (ctx.pointer_t() != null) {
            return visitPointer_t(ctx.pointer_t());
        }

        if (ctx.object_t() != null) {
            return visitObject_t(ctx.object_t());
        }

        if (ctx.par_t() != null) {
            return bind(visitPar_t(ctx.par_t()), ctx);
        }

        throw new Report.InternalError();
    }

    @Override
    public AST.Nodes<AST.CompDefn> visitComps(Prev26Parser.CompsContext ctx) {
        List<AST.CompDefn> comps = new ArrayList<>();

        if (ctx == null || ctx.comp() == null) {
            return bind(new AST.Nodes<>(comps), ctx);
        }

        comps.add(visitComp(ctx.comp()));
        comps.addAll(collectCompTail(ctx.comp_tail()));

        return bind(new AST.Nodes<>(comps), ctx);
    }

    private List<AST.CompDefn> collectCompTail(Prev26Parser.Comp_tailContext ctx) {
        List<AST.CompDefn> comps = new ArrayList<>();

        if (ctx == null || ctx.comp() == null) {
            return comps;
        }

        comps.add(visitComp(ctx.comp()));
        comps.addAll(collectCompTail(ctx.comp_tail()));

        return comps;
    }

    private List<AST.CompDefn> collectCompParamTail(Prev26Parser.Param_tailContext ctx) {
        List<AST.CompDefn> comps = new ArrayList<>();

        if (ctx == null || ctx.param() == null) {
            return comps;
        }

        AST.Type type = visitT(ctx.param().t());
        AST.CompDefn comp = bind(new AST.CompDefn(ctx.param().NAME().getText(), type), ctx.param().NAME().getSymbol(), type);

        comps.add(comp);
        comps.addAll(collectCompParamTail(ctx.param_tail()));

        return comps;
    }

    @Override
    public AST.CompDefn visitComp(Prev26Parser.CompContext ctx) {
        AST.Type type = visitT(ctx.t());
        return bind(new AST.CompDefn(ctx.NAME().getText(), type), ctx);
    }

    @Override
    public AST.FunType visitFun_t(Prev26Parser.Fun_tContext ctx) {
        AST.Nodes<AST.Type> parTypes = visitT_list(ctx.t_list());
        AST.Type resType = visitT(ctx.t());
        return bind(new AST.FunType(parTypes, resType), ctx);
    }

    @Override
    public AST.Nodes<AST.Type> visitT_list(Prev26Parser.T_listContext ctx) {
        List<AST.Type> types = new ArrayList<>();

        if (ctx == null || ctx.t() == null) {
            return bind(new AST.Nodes<>(types), ctx);
        }

        types.add(visitT(ctx.t()));
        types.addAll(collectTListTail(ctx.t_list_tail()));

        return bind(new AST.Nodes<>(types), ctx);
    }

    private List<AST.Type> collectTListTail(Prev26Parser.T_list_tailContext ctx) {
        List<AST.Type> types = new ArrayList<>();

        if (ctx == null || ctx.t() == null) {
            return types;
        }

        types.add(visitT(ctx.t()));
        types.addAll(collectTListTail(ctx.t_list_tail()));

        return types;
    }

    @Override
    public AST.Expr visitExpr_list(Prev26Parser.Expr_listContext ctx) {
        return packExprs(collectExprs(ctx), ctx);
    }

    private AST.Nodes<AST.Expr> exprNodes(Prev26Parser.Expr_listContext ctx) {
        return bind(new AST.Nodes<>(collectExprs(ctx)), ctx);
    }

    private List<AST.Expr> collectExprs(Prev26Parser.Expr_listContext ctx) {
        List<AST.Expr> exprs = new ArrayList<>();

        if (ctx == null || ctx.e() == null) {
            return exprs;
        }

        exprs.add(visitE(ctx.e()));
        exprs.addAll(collectETail(ctx.e_tail()));

        return exprs;
    }

    private List<AST.Expr> collectETail(Prev26Parser.E_tailContext ctx) {
        List<AST.Expr> exprs = new ArrayList<>();

        if (ctx == null || ctx.e() == null) {
            return exprs;
        }

        exprs.add(visitE(ctx.e()));
        exprs.addAll(collectETail(ctx.e_tail()));

        return exprs;
    }

    private AST.Expr packExprs(List<AST.Expr> exprs, ParserRuleContext ctx) {
        if (exprs.size() == 1) {
            return bind(exprs.get(0), ctx);
        }

        AST.Nodes<AST.Expr> exprNodes = bind(new AST.Nodes<>(exprs), ctx);
        return bind(new AST.Exprs(exprNodes), ctx);
    }

    @Override
    public AST.Expr visitE(Prev26Parser.EContext ctx) {
        AST.Expr expr = visitE_as(ctx.e_as());

        if (ctx.e_assign() != null && ctx.e_assign().e_as() != null) {
            AST.Expr rhs = visitE_as(ctx.e_assign().e_as());
            return bind(new AST.AsgnExpr(expr, rhs), expr, rhs);
        }

        return bind(expr, ctx);
    }

    @Override
    public AST.Expr visitE_as(Prev26Parser.E_asContext ctx) {
        AST.Expr expr = visitE_or(ctx.e_or());
        return bind(applyAsTail(expr, ctx.as_tail()), ctx);
    }

    private AST.Expr applyAsTail(AST.Expr expr, Prev26Parser.As_tailContext ctx) {
        if (ctx == null || ctx.t() == null) {
            return expr;
        }

        AST.Type type = visitT(ctx.t());
        AST.Expr cast = bind(new AST.CastExpr(type, expr), expr, type);

        return applyAsTail(cast, ctx.as_tail());
    }

    @Override
    public AST.Expr visitE_or(Prev26Parser.E_orContext ctx) {
        AST.Expr expr = visitE_and(ctx.e_and());
        return bind(applyOrTail(expr, ctx.or_tail()), ctx);
    }

    private AST.Expr applyOrTail(AST.Expr expr, Prev26Parser.Or_tailContext ctx) {
        if (ctx == null || ctx.e_and() == null) {
            return expr;
        }

        AST.Expr rhs = visitE_and(ctx.e_and());
        AST.Expr binExpr = bind(new AST.BinExpr(AST.BinExpr.Oper.OR, expr, rhs), expr, rhs);

        return applyOrTail(binExpr, ctx.or_tail());
    }

    @Override
    public AST.Expr visitE_and(Prev26Parser.E_andContext ctx) {
        AST.Expr expr = visitE_compare(ctx.e_compare());
        return bind(applyAndTail(expr, ctx.and_tail()), ctx);
    }

    private AST.Expr applyAndTail(AST.Expr expr, Prev26Parser.And_tailContext ctx) {
        if (ctx == null || ctx.e_compare() == null) {
            return expr;
        }

        AST.Expr rhs = visitE_compare(ctx.e_compare());
        AST.Expr binExpr = bind(new AST.BinExpr(AST.BinExpr.Oper.AND, expr, rhs), expr, rhs);

        return applyAndTail(binExpr, ctx.and_tail());
    }

    @Override
    public AST.Expr visitE_compare(Prev26Parser.E_compareContext ctx) {
        AST.Expr expr = visitE_add(ctx.e_add());

        if (ctx.compare_tail() != null && ctx.compare_tail().compare_op() != null) {
            AST.Expr rhs = visitE_add(ctx.compare_tail().e_add());
            AST.BinExpr.Oper oper = compareOper(ctx.compare_tail().compare_op());
            return bind(new AST.BinExpr(oper, expr, rhs), expr, rhs);
        }

        return bind(expr, ctx);
    }

    private AST.BinExpr.Oper compareOper(Prev26Parser.Compare_opContext ctx) {
        if (ctx.EQ() != null) {
            return AST.BinExpr.Oper.EQU;
        }
        if (ctx.NE() != null) {
            return AST.BinExpr.Oper.NEQ;
        }
        if (ctx.LT() != null) {
            return AST.BinExpr.Oper.LTH;
        }
        if (ctx.GT() != null) {
            return AST.BinExpr.Oper.GTH;
        }
        if (ctx.LE() != null) {
            return AST.BinExpr.Oper.LEQ;
        }
        if (ctx.GE() != null) {
            return AST.BinExpr.Oper.GEQ;
        }

        throw new Report.InternalError();
    }

    @Override
    public AST.Expr visitE_add(Prev26Parser.E_addContext ctx) {
        AST.Expr expr = visitE_mul(ctx.e_mul());
        return bind(applyAddTail(expr, ctx.add_tail()), ctx);
    }

    private AST.Expr applyAddTail(AST.Expr expr, Prev26Parser.Add_tailContext ctx) {
        if (ctx == null || ctx.e_mul() == null) {
            return expr;
        }

        AST.Expr rhs = visitE_mul(ctx.e_mul());
        AST.BinExpr.Oper oper = addOper(ctx.add_comp());
        AST.Expr binExpr = bind(new AST.BinExpr(oper, expr, rhs), expr, rhs);

        return applyAddTail(binExpr, ctx.add_tail());
    }

    private AST.BinExpr.Oper addOper(Prev26Parser.Add_compContext ctx) {
        if (ctx.ADD() != null) {
            return AST.BinExpr.Oper.ADD;
        }
        if (ctx.SUB() != null) {
            return AST.BinExpr.Oper.SUB;
        }

        throw new Report.InternalError();
    }

    @Override
    public AST.Expr visitE_mul(Prev26Parser.E_mulContext ctx) {
        AST.Expr expr = visitE_prefix(ctx.e_prefix());
        return bind(applyMulTail(expr, ctx.mul_tail()), ctx);
    }

    private AST.Expr applyMulTail(AST.Expr expr, Prev26Parser.Mul_tailContext ctx) {
        if (ctx == null || ctx.e_prefix() == null) {
            return expr;
        }

        AST.Expr rhs = visitE_prefix(ctx.e_prefix());
        AST.BinExpr.Oper oper = mulOper(ctx.mul_op());
        AST.Expr binExpr = bind(new AST.BinExpr(oper, expr, rhs), expr, rhs);

        return applyMulTail(binExpr, ctx.mul_tail());
    }

    private AST.BinExpr.Oper mulOper(Prev26Parser.Mul_opContext ctx) {
        if (ctx.MUL() != null) {
            return AST.BinExpr.Oper.MUL;
        }
        if (ctx.DIV() != null) {
            return AST.BinExpr.Oper.DIV;
        }
        if (ctx.MOD() != null) {
            return AST.BinExpr.Oper.MOD;
        }

        throw new Report.InternalError();
    }

    @Override
    public AST.Expr visitE_prefix(Prev26Parser.E_prefixContext ctx) {
        if (ctx.e_postfix() != null) {
            return bind(visitE_postfix(ctx.e_postfix()), ctx);
        }

        if (ctx.prefix_op() != null && ctx.e_prefix() != null) {
            AST.Expr expr = visitE_prefix(ctx.e_prefix());
            AST.PfxExpr.Oper oper = prefixOper(ctx.prefix_op());
            return bind(new AST.PfxExpr(oper, expr), ctx);
        }

        throw new Report.InternalError();
    }

    private AST.PfxExpr.Oper prefixOper(Prev26Parser.Prefix_opContext ctx) {
        if (ctx.NOT() != null) {
            return AST.PfxExpr.Oper.NOT;
        }
        if (ctx.ADD() != null) {
            return AST.PfxExpr.Oper.ADD;
        }
        if (ctx.SUB() != null) {
            return AST.PfxExpr.Oper.SUB;
        }
        if (ctx.POWER() != null) {
            return AST.PfxExpr.Oper.PTR;
        }

        throw new Report.InternalError();
    }

    @Override
    public AST.Expr visitE_postfix(Prev26Parser.E_postfixContext ctx) {
        AST.Expr expr = visitE_basic(ctx.e_basic());
        return bind(applyPostfixTail(expr, ctx.postfix_tail()), ctx);
    }

    private AST.Expr applyPostfixTail(AST.Expr expr, Prev26Parser.Postfix_tailContext ctx) {
        if (ctx == null) {
            return expr;
        }

        if (ctx.NAME() != null) {
            AST.Expr compExpr = bind(new AST.CompExpr(expr, ctx.NAME().getText()), expr, ctx.NAME().getSymbol());
            return applyPostfixTail(compExpr, ctx.postfix_tail());
        }

        if (ctx.POWER() != null) {
            AST.Expr sfxExpr = bind(new AST.SfxExpr(AST.SfxExpr.Oper.PTR, expr), expr, ctx.POWER().getSymbol());
            return applyPostfixTail(sfxExpr, ctx.postfix_tail());
        }

        if (ctx.e() != null) {
            AST.Expr idx = visitE(ctx.e());
            AST.Expr arrExpr = bind(new AST.ArrExpr(expr, idx), expr, ctx.RSB().getSymbol());
            return applyPostfixTail(arrExpr, ctx.postfix_tail());
        }

        if (ctx.empty_expr_list() != null) {
            AST.Nodes<AST.Expr> args = visitEmpty_expr_list(ctx.empty_expr_list());
            AST.Expr callExpr = bind(new AST.CallExpr(expr, args), expr, ctx.RP().getSymbol());
            return applyPostfixTail(callExpr, ctx.postfix_tail());
        }

        return expr;
    }

	@Override
	public AST.Nodes<AST.Expr> visitEmpty_expr_list(Prev26Parser.Empty_expr_listContext ctx) {
        List<AST.Expr> exprs = new ArrayList<>();

        if (ctx == null || ctx.expr_list() == null) {
			return bind(new AST.Nodes<>(exprs), ctx);
        }

        return exprNodes(ctx.expr_list());
    }

    @Override
    public AST.Expr visitE_basic(Prev26Parser.E_basicContext ctx) {
        if (ctx.NAME() != null) {
            return bind(new AST.NameExpr(ctx.NAME().getText()), ctx);
        }

        if (ctx.e_const() != null) {
            return visitE_const(ctx.e_const());
        }

        if (ctx.e_sizeof() != null) {
            return visitE_sizeof(ctx.e_sizeof());
        }

        if (ctx.e_if() != null) {
            return visitE_if(ctx.e_if());
        }

        if (ctx.e_while() != null) {
            return visitE_while(ctx.e_while());
        }

        if (ctx.e_let() != null) {
            return visitE_let(ctx.e_let());
        }

        if (ctx.expr_list() != null) {
            return bind(visitExpr_list(ctx.expr_list()), ctx);
        }

        throw new Report.InternalError();
    }

    @Override
    public AST.AtomExpr visitE_const(Prev26Parser.E_constContext ctx) {
        if (ctx.CINT() != null) {
            return bind(new AST.AtomExpr(AST.AtomExpr.Type.INT, ctx.CINT().getText()), ctx);
        }

        if (ctx.bool_const() != null) {
            return visitBool_const(ctx.bool_const());
        }

        if (ctx.CCHAR() != null) {
            return bind(new AST.AtomExpr(AST.AtomExpr.Type.CHAR, ctx.CCHAR().getText()), ctx);
        }

        if (ctx.CSTRING() != null) {
            return bind(new AST.AtomExpr(AST.AtomExpr.Type.STR, ctx.CSTRING().getText()), ctx);
        }

        if (ctx.void_const() != null) {
            return visitVoid_const(ctx.void_const());
        }

        if (ctx.ptr_const() != null) {
            return visitPtr_const(ctx.ptr_const());
        }

        throw new Report.InternalError();
    }

    @Override
    public AST.AtomExpr visitBool_const(Prev26Parser.Bool_constContext ctx) {
        if (ctx.TRUE() != null) {
            return bind(new AST.AtomExpr(AST.AtomExpr.Type.BOOL, ctx.TRUE().getText()), ctx);
        }

        if (ctx.FALSE() != null) {
            return bind(new AST.AtomExpr(AST.AtomExpr.Type.BOOL, ctx.FALSE().getText()), ctx);
        }

        throw new Report.InternalError();
    }

    @Override
    public AST.AtomExpr visitVoid_const(Prev26Parser.Void_constContext ctx) {
        return bind(new AST.AtomExpr(AST.AtomExpr.Type.VOID, ctx.NONE().getText()), ctx);
    }

    @Override
    public AST.AtomExpr visitPtr_const(Prev26Parser.Ptr_constContext ctx) {
        return bind(new AST.AtomExpr(AST.AtomExpr.Type.PTR, ctx.NIL().getText()), ctx);
    }

    @Override
    public AST.SizeExpr visitE_sizeof(Prev26Parser.E_sizeofContext ctx) {
        AST.Type type = visitT(ctx.t());
        return bind(new AST.SizeExpr(type), ctx);
    }

    @Override
    public AST.LetExpr visitE_let(Prev26Parser.E_letContext ctx) {
        AST.Nodes<AST.FullDefn> defns = visitProg(ctx.prog());
        AST.Expr expr = visitExpr_list(ctx.expr_list());
        return bind(new AST.LetExpr(defns, expr), ctx);
    }

    @Override
    public AST.WhileExpr visitE_while(Prev26Parser.E_whileContext ctx) {
        AST.Expr condExpr = visitE(ctx.e());
        AST.Expr bodyExpr = visitExpr_list(ctx.expr_list());
        return bind(new AST.WhileExpr(condExpr, bodyExpr), ctx);
    }

    @Override
    public AST.Expr visitE_if(Prev26Parser.E_ifContext ctx) {
        AST.Expr condExpr = visitE(ctx.e());
        AST.Expr thenExpr = visitExpr_list(ctx.expr_list());

        if (ctx.if_tail() != null && ctx.if_tail().expr_list() != null) {
            AST.Expr elseExpr = visitExpr_list(ctx.if_tail().expr_list());
            return bind(new AST.IfThenElseExpr(condExpr, thenExpr, elseExpr), ctx);
        }

        return bind(new AST.IfThenExpr(condExpr, thenExpr), ctx);
    }
}
