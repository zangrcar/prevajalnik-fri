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

}

options{
    tokenVocab=Prev26Lexer;
}

source returns [AST.Nodes<AST.FullDefn> ast]
    : prog EOF;

prog: d prog_tail;
prog_tail: d prog_tail |;

d: 
    TYP NAME IS t | 
    VAR NAME DD t | 
    FUN NAME LP params RP DD t fun_tail;
fun_tail: IS expr_list |;
params: param param_tail |;
param_tail: C param param_tail |;
param: NAME DD t;

t: 
    basic_t | 
    NAME | 
    array_t | 
    pointer_t |
    object_t |
    LP par_t RP;

par_t: par_t_parse_id | fun_t;
par_t_parse_id: 
    NAME  par_after_id |
    non_id_t;
par_after_id: DD t param_tail |;
non_id_t: 
    basic_t |
    array_t |
    pointer_t |
    object_t |
    LP par_t RP;



basic_t: INT | CHAR | BOOL | VOID;
array_t: LSB c_int RSB t;
pointer_t: POWER t;
object_t: LB comps RB;
comps: comp comp_tail;
comp_tail: C comp comp_tail |;
comp: NAME DD t;
fun_t: DD t_list DD t;
t_list: t t_list_tail |;
t_list_tail: C t t_list_tail |;

expr_list: e e_tail;
e_tail: C e e_tail |;

e: e_as e_assign;
e_assign: IS e_as |;
e_as: e_or as_tail;
as_tail: AS t as_tail |;
e_or: e_and or_tail;
or_tail: OR e_and or_tail |;
e_and: e_compare and_tail;
and_tail: AND e_compare and_tail |;
e_compare: e_add compare_tail;
compare_tail: compare_op e_add |;
compare_op: EQ | NE | LT | GT | LE | GE;
e_add: e_mul add_tail;
add_tail: add_comp e_mul add_tail |;
add_comp: ADD | SUB;
e_mul: e_prefix mul_tail;
mul_tail: mul_op e_prefix mul_tail |;
mul_op: MUL | DIV | MOD;
e_prefix: prefix_op e_prefix | e_postfix;
prefix_op: NOT | ADD | SUB | POWER;
e_postfix: e_basic postfix_tail;
postfix_tail: 
    D NAME postfix_tail |
    POWER postfix_tail | 
    LSB e RSB postfix_tail |
    LP empty_expr_list RP postfix_tail |;
empty_expr_list: expr_list | ;

e_basic: 
    NAME | 
    e_const | 
    e_sizeof |
    e_if | 
    e_while | 
    e_let | 
    LP expr_list RP;
e_const: CINT | bool_const | CCHAR | CSTRING | void_const | ptr_const;
bool_const: TRUE | FALSE;
void_const: NONE;
ptr_const: NIL;
e_sizeof: SIZEOF t;
e_let: LET prog IN expr_list END;
e_while: WHILE e DO expr_list END;
e_if: IF e THEN expr_list if_tail END;
if_tail: ELSE expr_list |;
c_int: opt_add_op CINT;
opt_add_op: add_comp |;





