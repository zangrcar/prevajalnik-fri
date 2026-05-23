// Generated from Prev26Parser.g4 by ANTLR 4.13.2


    package prev26lang.phase.synan;
    
    import java.util.*;
    import prev26lang.common.report.*;
    import prev26lang.phase.lexan.*;
    import prev26lang.phase.abstr.*;


import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link Prev26Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface Prev26ParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#source}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSource(Prev26Parser.SourceContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(Prev26Parser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#prog_tail}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg_tail(Prev26Parser.Prog_tailContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#d}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitD(Prev26Parser.DContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#fun_tail}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFun_tail(Prev26Parser.Fun_tailContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#params}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParams(Prev26Parser.ParamsContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#param_tail}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam_tail(Prev26Parser.Param_tailContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(Prev26Parser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#t}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitT(Prev26Parser.TContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#par_t}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPar_t(Prev26Parser.Par_tContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#par_t_parse_id}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPar_t_parse_id(Prev26Parser.Par_t_parse_idContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#par_after_id}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPar_after_id(Prev26Parser.Par_after_idContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#non_id_t}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNon_id_t(Prev26Parser.Non_id_tContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#basic_t}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBasic_t(Prev26Parser.Basic_tContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#array_t}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray_t(Prev26Parser.Array_tContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#pointer_t}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPointer_t(Prev26Parser.Pointer_tContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#object_t}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObject_t(Prev26Parser.Object_tContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#comps}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComps(Prev26Parser.CompsContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#comp_tail}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComp_tail(Prev26Parser.Comp_tailContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#comp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComp(Prev26Parser.CompContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#fun_t}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFun_t(Prev26Parser.Fun_tContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#t_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitT_list(Prev26Parser.T_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#t_list_tail}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitT_list_tail(Prev26Parser.T_list_tailContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#expr_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_list(Prev26Parser.Expr_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#e_tail}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitE_tail(Prev26Parser.E_tailContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitE(Prev26Parser.EContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#e_assign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitE_assign(Prev26Parser.E_assignContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#e_as}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitE_as(Prev26Parser.E_asContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#as_tail}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAs_tail(Prev26Parser.As_tailContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#e_or}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitE_or(Prev26Parser.E_orContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#or_tail}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOr_tail(Prev26Parser.Or_tailContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#e_and}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitE_and(Prev26Parser.E_andContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#and_tail}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd_tail(Prev26Parser.And_tailContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#e_compare}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitE_compare(Prev26Parser.E_compareContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#compare_tail}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompare_tail(Prev26Parser.Compare_tailContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#compare_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompare_op(Prev26Parser.Compare_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#e_add}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitE_add(Prev26Parser.E_addContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#add_tail}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdd_tail(Prev26Parser.Add_tailContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#add_comp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdd_comp(Prev26Parser.Add_compContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#e_mul}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitE_mul(Prev26Parser.E_mulContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#mul_tail}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMul_tail(Prev26Parser.Mul_tailContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#mul_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMul_op(Prev26Parser.Mul_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#e_prefix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitE_prefix(Prev26Parser.E_prefixContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#prefix_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefix_op(Prev26Parser.Prefix_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#e_postfix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitE_postfix(Prev26Parser.E_postfixContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#postfix_tail}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfix_tail(Prev26Parser.Postfix_tailContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#empty_expr_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmpty_expr_list(Prev26Parser.Empty_expr_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#e_basic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitE_basic(Prev26Parser.E_basicContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#e_const}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitE_const(Prev26Parser.E_constContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#bool_const}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBool_const(Prev26Parser.Bool_constContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#void_const}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVoid_const(Prev26Parser.Void_constContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#ptr_const}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPtr_const(Prev26Parser.Ptr_constContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#e_sizeof}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitE_sizeof(Prev26Parser.E_sizeofContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#e_let}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitE_let(Prev26Parser.E_letContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#e_while}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitE_while(Prev26Parser.E_whileContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#e_until}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitE_until(Prev26Parser.E_untilContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#e_if}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitE_if(Prev26Parser.E_ifContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#if_tail}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_tail(Prev26Parser.If_tailContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#c_int}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitC_int(Prev26Parser.C_intContext ctx);
	/**
	 * Visit a parse tree produced by {@link Prev26Parser#opt_add_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpt_add_op(Prev26Parser.Opt_add_opContext ctx);
}