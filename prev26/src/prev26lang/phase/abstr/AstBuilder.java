package prev26lang.phase.synan;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import prev26lang.common.report.Location;
import prev26lang.common.report.Report;
import prev26lang.phase.abstr.AST;
import prev26lang.phase.abstr.Abstr;
import prev26lang.phase.lexan.LexAn;

public class AstBuilder extends Prev26ParserBaseVisitor<AST.Node> {

    private Location loc(Token tok) {
        return new Location((LexAn.LocLogToken) tok);
    }

    private Location loc(Token first, Token last) {
        return new Location((LexAn.LocLogToken) first, (LexAn.LocLogToken) last);
    }

    private Location loc(ParserRuleContext ctx) {
        return loc(ctx.start, ctx.stop);
    }

    private <N extends AST.Node> N bind(N node, ParserRuleContext ctx) {
        Abstr.locAttr.put(node, loc(ctx));
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

    // TODO: next step
    public AST.Type visitT(Prev26Parser.TContext ctx) {
        throw new UnsupportedOperationException("visitT not implemented yet");
    }

    // TODO: next step
    public AST.Expr visitExpr_list(Prev26Parser.Expr_listContext ctx) {
        throw new UnsupportedOperationException("visitExpr_list not implemented yet");
    }
}