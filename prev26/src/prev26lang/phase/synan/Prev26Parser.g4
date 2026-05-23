parser grammar Prev26Parser;

@header {

    package prev26lang.phase.synan;
    
    import java.util.*;
    import prev26lang.common.report.*;
    import prev26lang.phase.lexan.*;
    import prev26lang.phase.abstr.*;

}

@members {

    private Location loc(final Token tok) { return new Location((LexAn.LocLogToken)tok); }
    private Location loc(final Locatable loc) { return new Location(loc); }
    private Location loc(final Token tok1, final Token tok2) { return new Location((LexAn.LocLogToken)tok1, (LexAn.LocLogToken)tok2); }
    private Location loc(final Token tok1, final Locatable loc2) { if (loc2 == null) return null; return new Location((LexAn.LocLogToken)tok1, loc2); }
    private Location loc(final Locatable loc1, final Token tok2) { if (loc1 == null) return null; return new Location(loc1, (LexAn.LocLogToken)tok2); }
    private Location loc(final Locatable loc1, final Locatable loc2) { if ((loc1 == null) || (loc2 == null)) return null; return new Location(loc1, loc2); }
    private Location loc(final ParserRuleContext ctx) {
        return loc(ctx.start, (ctx.stop == null) ? _input.LT(-1) : ctx.stop);
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

    private AST.Expr packExprs(List<AST.Expr> exprs, ParserRuleContext ctx) {
        if (exprs.size() == 1)
            return bind(exprs.get(0), ctx);

        AST.Nodes<AST.Expr> exprNodes = bind(new AST.Nodes<>(exprs), ctx);
        return bind(new AST.Exprs(exprNodes), ctx);
    }

}

options{
    tokenVocab=Prev26Lexer;
}

source returns [AST.Nodes<AST.FullDefn> ast]
    : prog EOF { $ast = $prog.ast; };

prog returns [AST.Nodes<AST.FullDefn> ast]
@init { List<AST.FullDefn> defs = new ArrayList<>(); }
    : d prog_tail
      {
          defs.add($d.ast);
          defs.addAll($prog_tail.asts);
          $ast = bind(new AST.Nodes<>(defs), $ctx);
      };

prog_tail returns [List<AST.FullDefn> asts]
@init { $asts = new ArrayList<>(); }
    : d prog_tail
      {
          $asts.add($d.ast);
          $asts.addAll($prog_tail.asts);
      }
    |;

d returns [AST.FullDefn ast]
    : TYP n=NAME IS t
      { $ast = bind(new AST.TypDefn($n.getText(), $t.ast), $ctx); }
    | VAR n=NAME DD t
      { $ast = bind(new AST.VarDefn($n.getText(), $t.ast), $ctx); }
    | FUN n=NAME LP params RP DD t fun_tail
      {
          if ($fun_tail.body != null)
              $ast = bind(new AST.DefFunDefn($n.getText(), $params.ast, $t.ast, $fun_tail.body), $ctx);
          else
              $ast = bind(new AST.ExtFunDefn($n.getText(), $params.ast, $t.ast), $ctx);
      };

fun_tail returns [AST.Expr body]
    : IS expr_list { $body = packExprs($expr_list.exprs, $expr_list.ctx); }
    | { $body = null; };

params returns [AST.Nodes<AST.ParDefn> ast]
@init { List<AST.ParDefn> pars = new ArrayList<>(); }
    : param[false] param_tail[false]
      {
          pars.add($param.ast);
          pars.addAll($param_tail.pars);
          $ast = bind(new AST.Nodes<>(pars), $ctx);
      }
    | { $ast = bind(new AST.Nodes<>(pars), $ctx); };

param_tail[boolean asComp] returns [List<AST.ParDefn> pars, List<AST.CompDefn> compDefs]
@init { $pars = new ArrayList<>(); $compDefs = new ArrayList<>(); }
    : C param[$asComp] param_tail[$asComp]
      {
          if ($asComp) {
              $compDefs.add($param.compDef);
              $compDefs.addAll($param_tail.compDefs);
          } else {
              $pars.add($param.ast);
              $pars.addAll($param_tail.pars);
          }
      }
    |;

param[boolean asComp] returns [AST.ParDefn ast, AST.CompDefn compDef]
    : n=NAME DD t
      {
          if ($asComp)
              $compDef = bind(new AST.CompDefn($n.getText(), $t.ast), $n, $t.ast);
          else
              $ast = bind(new AST.ParDefn($n.getText(), $t.ast), $ctx);
      };

t returns [AST.Type ast]
    : basic_t { $ast = $basic_t.ast; }
    | n=NAME { $ast = bind(new AST.NameType($n.getText()), $ctx); }
    | array_t { $ast = $array_t.ast; }
    | pointer_t { $ast = $pointer_t.ast; }
    | object_t { $ast = $object_t.ast; }
    | LP par_t RP { $ast = bind($par_t.ast, $ctx); };

par_t returns [AST.Type ast]
    : par_t_parse_id { $ast = $par_t_parse_id.ast; }
    | fun_t { $ast = $fun_t.ast; };

par_t_parse_id returns [AST.Type ast]
@init { List<AST.CompDefn> comps = new ArrayList<>(); }
    : n=NAME par_after_id
      {
          if ($par_after_id.type != null) {
              AST.CompDefn firstComp = bind(new AST.CompDefn($n.getText(), $par_after_id.type), $n, $par_after_id.type);
              comps.add(firstComp);
              comps.addAll($par_after_id.compDefs);
              AST.Nodes<AST.CompDefn> compNodes = bind(new AST.Nodes<>(comps), $ctx);
              $ast = bind(new AST.StrType(compNodes), $ctx);
          } else {
              $ast = bind(new AST.NameType($n.getText()), $ctx);
          }
      }
    | non_id_t { $ast = bind($non_id_t.ast, $ctx); };

par_after_id returns [AST.Type type, List<AST.CompDefn> compDefs]
@init { $compDefs = new ArrayList<>(); }
    : DD t param_tail[true]
      {
          $type = $t.ast;
          $compDefs.addAll($param_tail.compDefs);
      }
    | { $type = null; };

non_id_t returns [AST.Type ast]
    : basic_t { $ast = $basic_t.ast; }
    | array_t { $ast = $array_t.ast; }
    | pointer_t { $ast = $pointer_t.ast; }
    | object_t { $ast = $object_t.ast; }
    | LP par_t RP { $ast = bind($par_t.ast, $ctx); };

basic_t returns [AST.AtomType ast]
    : INT { $ast = bind(new AST.AtomType(AST.AtomType.Type.INT), $ctx); }
    | CHAR { $ast = bind(new AST.AtomType(AST.AtomType.Type.CHAR), $ctx); }
    | BOOL { $ast = bind(new AST.AtomType(AST.AtomType.Type.BOOL), $ctx); }
    | VOID { $ast = bind(new AST.AtomType(AST.AtomType.Type.VOID), $ctx); };

array_t returns [AST.ArrType ast]
    : LSB c_int RSB t
      { $ast = bind(new AST.ArrType($t.ast, $c_int.ctx.getText()), $ctx); };

pointer_t returns [AST.PtrType ast]
    : POWER t { $ast = bind(new AST.PtrType($t.ast), $ctx); };

object_t returns [AST.RecType ast]
    : LB comps RB { $ast = bind(new AST.UniType($comps.ast), $ctx); };

comps returns [AST.Nodes<AST.CompDefn> ast]
@init { List<AST.CompDefn> comps = new ArrayList<>(); }
    : comp comp_tail
      {
          comps.add($comp.ast);
          comps.addAll($comp_tail.asts);
          $ast = bind(new AST.Nodes<>(comps), $ctx);
      };

comp_tail returns [List<AST.CompDefn> asts]
@init { $asts = new ArrayList<>(); }
    : C comp comp_tail
      {
          $asts.add($comp.ast);
          $asts.addAll($comp_tail.asts);
      }
    |;

comp returns [AST.CompDefn ast]
    : n=NAME DD t { $ast = bind(new AST.CompDefn($n.getText(), $t.ast), $ctx); };

fun_t returns [AST.FunType ast]
    : DD t_list DD t { $ast = bind(new AST.FunType($t_list.ast, $t.ast), $ctx); };

t_list returns [AST.Nodes<AST.Type> ast]
@init { List<AST.Type> types = new ArrayList<>(); }
    : t t_list_tail
      {
          types.add($t.ast);
          types.addAll($t_list_tail.asts);
          $ast = bind(new AST.Nodes<>(types), $ctx);
      }
    | { $ast = bind(new AST.Nodes<>(types), $ctx); };

t_list_tail returns [List<AST.Type> asts]
@init { $asts = new ArrayList<>(); }
    : C t t_list_tail
      {
          $asts.add($t.ast);
          $asts.addAll($t_list_tail.asts);
      }
    |;

expr_list returns [List<AST.Expr> exprs]
@init { $exprs = new ArrayList<>(); }
    : e e_tail
      {
          $exprs.add($e.ast);
          $exprs.addAll($e_tail.exprs);
      };

e_tail returns [List<AST.Expr> exprs]
@init { $exprs = new ArrayList<>(); }
    : C e e_tail
      {
          $exprs.add($e.ast);
          $exprs.addAll($e_tail.exprs);
      }
    |;

e returns [AST.Expr ast]
    : e_as e_assign
      {
          if ($e_assign.rhs != null)
              $ast = bind(new AST.AsgnExpr($e_as.ast, $e_assign.rhs), $e_as.ast, $e_assign.rhs);
          else
              $ast = bind($e_as.ast, $ctx);
      };

e_assign returns [AST.Expr rhs]
    : IS e_as { $rhs = $e_as.ast; }
    | { $rhs = null; };

e_as returns [AST.Expr ast]
    : e_or as_tail[$e_or.ast] { $ast = bind($as_tail.ast, $ctx); };

as_tail[AST.Expr expr] returns [AST.Expr ast]
locals [AST.Expr next]
    : AS t
      {
          $next = bind(new AST.CastExpr($t.ast, $expr), $expr, $t.ast);
      }
      as_tail[$next] { $ast = $as_tail.ast; }
    | { $ast = $expr; };

e_or returns [AST.Expr ast]
    : e_and or_tail[$e_and.ast] { $ast = bind($or_tail.ast, $ctx); };

or_tail[AST.Expr expr] returns [AST.Expr ast]
locals [AST.Expr next]
    : OR e_and
      {
          $next = bind(new AST.BinExpr(AST.BinExpr.Oper.OR, $expr, $e_and.ast), $expr, $e_and.ast);
      }
      or_tail[$next] { $ast = $or_tail.ast; }
    | { $ast = $expr; };

e_and returns [AST.Expr ast]
    : e_compare and_tail[$e_compare.ast] { $ast = bind($and_tail.ast, $ctx); };

and_tail[AST.Expr expr] returns [AST.Expr ast]
locals [AST.Expr next]
    : AND e_compare
      {
          $next = bind(new AST.BinExpr(AST.BinExpr.Oper.AND, $expr, $e_compare.ast), $expr, $e_compare.ast);
      }
      and_tail[$next] { $ast = $and_tail.ast; }
    | { $ast = $expr; };

e_compare returns [AST.Expr ast]
    : e_add compare_tail
      {
          if ($compare_tail.rhs != null)
              $ast = bind(new AST.BinExpr($compare_tail.oper, $e_add.ast, $compare_tail.rhs), $e_add.ast, $compare_tail.rhs);
          else
              $ast = bind($e_add.ast, $ctx);
      };

compare_tail returns [AST.BinExpr.Oper oper, AST.Expr rhs]
    : compare_op e_add
      {
          $oper = $compare_op.oper;
          $rhs = $e_add.ast;
      }
    | { $rhs = null; };

compare_op returns [AST.BinExpr.Oper oper]
    : EQ { $oper = AST.BinExpr.Oper.EQU; }
    | NE { $oper = AST.BinExpr.Oper.NEQ; }
    | LT { $oper = AST.BinExpr.Oper.LTH; }
    | GT { $oper = AST.BinExpr.Oper.GTH; }
    | LE { $oper = AST.BinExpr.Oper.LEQ; }
    | GE { $oper = AST.BinExpr.Oper.GEQ; };

e_add returns [AST.Expr ast]
    : e_mul add_tail[$e_mul.ast] { $ast = bind($add_tail.ast, $ctx); };

add_tail[AST.Expr expr] returns [AST.Expr ast]
locals [AST.Expr next]
    : add_comp e_mul
      {
          $next = bind(new AST.BinExpr($add_comp.oper, $expr, $e_mul.ast), $expr, $e_mul.ast);
      }
      add_tail[$next] { $ast = $add_tail.ast; }
    | { $ast = $expr; };

add_comp returns [AST.BinExpr.Oper oper]
    : ADD { $oper = AST.BinExpr.Oper.ADD; }
    | SUB { $oper = AST.BinExpr.Oper.SUB; };

e_mul returns [AST.Expr ast]
    : e_prefix mul_tail[$e_prefix.ast] { $ast = bind($mul_tail.ast, $ctx); };

mul_tail[AST.Expr expr] returns [AST.Expr ast]
locals [AST.Expr next]
    : mul_op e_prefix
      {
          $next = bind(new AST.BinExpr($mul_op.oper, $expr, $e_prefix.ast), $expr, $e_prefix.ast);
      }
      mul_tail[$next] { $ast = $mul_tail.ast; }
    | { $ast = $expr; };

mul_op returns [AST.BinExpr.Oper oper]
    : MUL { $oper = AST.BinExpr.Oper.MUL; }
    | DIV { $oper = AST.BinExpr.Oper.DIV; }
    | MOD { $oper = AST.BinExpr.Oper.MOD; };

e_prefix returns [AST.Expr ast]
    : prefix_op e_prefix
      { $ast = bind(new AST.PfxExpr($prefix_op.oper, $e_prefix.ast), $ctx); }
    | e_postfix { $ast = bind($e_postfix.ast, $ctx); };

prefix_op returns [AST.PfxExpr.Oper oper]
    : NOT { $oper = AST.PfxExpr.Oper.NOT; }
    | ADD { $oper = AST.PfxExpr.Oper.ADD; }
    | SUB { $oper = AST.PfxExpr.Oper.SUB; }
    | POWER { $oper = AST.PfxExpr.Oper.PTR; };

e_postfix returns [AST.Expr ast]
    : e_basic postfix_tail[$e_basic.ast] { $ast = bind($postfix_tail.ast, $ctx); };

postfix_tail[AST.Expr expr] returns [AST.Expr ast]
locals [AST.Expr next]
    : D n=NAME
      {
          $next = bind(new AST.CompExpr($expr, $n.getText()), $expr, $n);
      }
      postfix_tail[$next] { $ast = $postfix_tail.ast; }
    | p=POWER
      {
          $next = bind(new AST.SfxExpr(AST.SfxExpr.Oper.PTR, $expr), $expr, $p);
      }
      postfix_tail[$next] { $ast = $postfix_tail.ast; }
    | LSB e r=RSB
      {
          $next = bind(new AST.ArrExpr($expr, $e.ast), $expr, $r);
      }
      postfix_tail[$next] { $ast = $postfix_tail.ast; }
    | LP empty_expr_list r=RP
      {
          $next = bind(new AST.CallExpr($expr, $empty_expr_list.ast), $expr, $r);
      }
      postfix_tail[$next] { $ast = $postfix_tail.ast; }
    | { $ast = $expr; };

empty_expr_list returns [AST.Nodes<AST.Expr> ast]
    : expr_list { $ast = bind(new AST.Nodes<>($expr_list.exprs), $expr_list.ctx); }
    | { $ast = bind(new AST.Nodes<>(new ArrayList<AST.Expr>()), $ctx); };

e_basic returns [AST.Expr ast]
    : n=NAME { $ast = bind(new AST.NameExpr($n.getText()), $ctx); }
    | e_const { $ast = $e_const.ast; }
    | e_sizeof { $ast = $e_sizeof.ast; }
    | e_if { $ast = $e_if.ast; }
    | e_while { $ast = $e_while.ast; }
	| e_until { $ast = $e_until.ast; }
    | e_let { $ast = $e_let.ast; }
    | LP expr_list RP { $ast = bind(packExprs($expr_list.exprs, $expr_list.ctx), $ctx); };

e_const returns [AST.AtomExpr ast]
    : c=CINT { $ast = bind(new AST.AtomExpr(AST.AtomExpr.Type.INT, $c.getText()), $ctx); }
    | bool_const { $ast = $bool_const.ast; }
    | c=CCHAR { $ast = bind(new AST.AtomExpr(AST.AtomExpr.Type.CHAR, $c.getText()), $ctx); }
    | c=CSTRING { $ast = bind(new AST.AtomExpr(AST.AtomExpr.Type.STR, $c.getText()), $ctx); }
    | void_const { $ast = $void_const.ast; }
    | ptr_const { $ast = $ptr_const.ast; };

bool_const returns [AST.AtomExpr ast]
    : TRUE { $ast = bind(new AST.AtomExpr(AST.AtomExpr.Type.BOOL, $TRUE.getText()), $ctx); }
    | FALSE { $ast = bind(new AST.AtomExpr(AST.AtomExpr.Type.BOOL, $FALSE.getText()), $ctx); };

void_const returns [AST.AtomExpr ast]
    : NONE { $ast = bind(new AST.AtomExpr(AST.AtomExpr.Type.VOID, $NONE.getText()), $ctx); };

ptr_const returns [AST.AtomExpr ast]
    : NIL { $ast = bind(new AST.AtomExpr(AST.AtomExpr.Type.PTR, $NIL.getText()), $ctx); };

e_sizeof returns [AST.SizeExpr ast]
    : SIZEOF t { $ast = bind(new AST.SizeExpr($t.ast), $ctx); };

e_let returns [AST.LetExpr ast]
    : LET prog IN expr_list END
      { $ast = bind(new AST.LetExpr($prog.ast, packExprs($expr_list.exprs, $expr_list.ctx)), $ctx); };

e_while returns [AST.WhileExpr ast]
    : WHILE e DO expr_list END
      { $ast = bind(new AST.WhileExpr($e.ast, packExprs($expr_list.exprs, $expr_list.ctx), false), $ctx); };

e_until returns [AST.WhileExpr ast]
    : UNTIL e DO expr_list END
      { $ast = bind(new AST.WhileExpr($e.ast, packExprs($expr_list.exprs, $expr_list.ctx), true), $ctx); };

e_if returns [AST.Expr ast]
    : IF e THEN expr_list if_tail END
      {
          AST.Expr thenExpr = packExprs($expr_list.exprs, $expr_list.ctx);
          if ($if_tail.ast != null)
              $ast = bind(new AST.IfThenElseExpr($e.ast, thenExpr, $if_tail.ast), $ctx);
          else
              $ast = bind(new AST.IfThenExpr($e.ast, thenExpr), $ctx);
      };

if_tail returns [AST.Expr ast]
    : ELSE expr_list { $ast = packExprs($expr_list.exprs, $expr_list.ctx); }
    | { $ast = null; };

c_int: opt_add_op CINT;
opt_add_op: add_comp |;
