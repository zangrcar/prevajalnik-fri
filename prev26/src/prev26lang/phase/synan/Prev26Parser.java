// Generated from Prev26Parser.g4 by ANTLR 4.13.2


    package prev26lang.phase.synan;
    
    import java.util.*;
    import prev26lang.common.report.*;
    import prev26lang.phase.lexan.*;
    import prev26lang.phase.abstr.*;


import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"doclint:missing", "all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class Prev26Parser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		AND=1, AS=2, BOOL=3, CONST=4, DO=5, CHAR=6, ELSE=7, END=8, FALSE=9, FUN=10, 
		IF=11, INT=12, IN=13, LET=14, NIL=15, NONE=16, NOT=17, OR=18, SIZEOF=19, 
		THEN=20, TRUE=21, TYP=22, VAR=23, VOID=24, WHILE=25, POWER=26, LB=27, 
		RB=28, LSB=29, RSB=30, LP=31, RP=32, LE=33, GE=34, NE=35, EQ=36, LT=37, 
		GT=38, MOD=39, DIV=40, MUL=41, SUB=42, ADD=43, IS=44, DD=45, C=46, D=47, 
		COMMENT=48, WS=49, NAME=50, CINT=51, CCHAR=52, INVALID_CHAR=53, CSTRING=54, 
		INVALID_STRING=55, ANY=56;
	public static final int
		RULE_source = 0, RULE_prog = 1, RULE_prog_tail = 2, RULE_d = 3, RULE_fun_tail = 4, 
		RULE_params = 5, RULE_param_tail = 6, RULE_param = 7, RULE_t = 8, RULE_par_t = 9, 
		RULE_par_t_parse_id = 10, RULE_par_after_id = 11, RULE_non_id_t = 12, 
		RULE_basic_t = 13, RULE_array_t = 14, RULE_pointer_t = 15, RULE_object_t = 16, 
		RULE_comps = 17, RULE_comp_tail = 18, RULE_comp = 19, RULE_fun_t = 20, 
		RULE_t_list = 21, RULE_t_list_tail = 22, RULE_expr_list = 23, RULE_e_tail = 24, 
		RULE_e = 25, RULE_e_assign = 26, RULE_e_as = 27, RULE_as_tail = 28, RULE_e_or = 29, 
		RULE_or_tail = 30, RULE_e_and = 31, RULE_and_tail = 32, RULE_e_compare = 33, 
		RULE_compare_tail = 34, RULE_compare_op = 35, RULE_e_add = 36, RULE_add_tail = 37, 
		RULE_add_comp = 38, RULE_e_mul = 39, RULE_mul_tail = 40, RULE_mul_op = 41, 
		RULE_e_prefix = 42, RULE_prefix_op = 43, RULE_e_postfix = 44, RULE_postfix_tail = 45, 
		RULE_empty_expr_list = 46, RULE_e_basic = 47, RULE_e_const = 48, RULE_bool_const = 49, 
		RULE_void_const = 50, RULE_ptr_const = 51, RULE_e_sizeof = 52, RULE_e_let = 53, 
		RULE_e_while = 54, RULE_e_if = 55, RULE_if_tail = 56, RULE_c_int = 57, 
		RULE_opt_add_op = 58;
	private static String[] makeRuleNames() {
		return new String[] {
			"source", "prog", "prog_tail", "d", "fun_tail", "params", "param_tail", 
			"param", "t", "par_t", "par_t_parse_id", "par_after_id", "non_id_t", 
			"basic_t", "array_t", "pointer_t", "object_t", "comps", "comp_tail", 
			"comp", "fun_t", "t_list", "t_list_tail", "expr_list", "e_tail", "e", 
			"e_assign", "e_as", "as_tail", "e_or", "or_tail", "e_and", "and_tail", 
			"e_compare", "compare_tail", "compare_op", "e_add", "add_tail", "add_comp", 
			"e_mul", "mul_tail", "mul_op", "e_prefix", "prefix_op", "e_postfix", 
			"postfix_tail", "empty_expr_list", "e_basic", "e_const", "bool_const", 
			"void_const", "ptr_const", "e_sizeof", "e_let", "e_while", "e_if", "if_tail", 
			"c_int", "opt_add_op"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'and'", "'as'", "'bool'", "'const'", "'do'", "'char'", "'else'", 
			"'end'", "'false'", "'fun'", "'if'", "'int'", "'in'", "'let'", "'nil'", 
			"'none'", "'not'", "'or'", "'sizeof'", "'then'", "'true'", "'typ'", "'var'", 
			"'void'", "'while'", "'^'", "'{'", "'}'", "'['", "']'", "'('", "')'", 
			"'<='", "'>='", "'!='", "'=='", "'<'", "'>'", "'%'", "'/'", "'*'", "'-'", 
			"'+'", "'='", "':'", "','", "'.'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "AND", "AS", "BOOL", "CONST", "DO", "CHAR", "ELSE", "END", "FALSE", 
			"FUN", "IF", "INT", "IN", "LET", "NIL", "NONE", "NOT", "OR", "SIZEOF", 
			"THEN", "TRUE", "TYP", "VAR", "VOID", "WHILE", "POWER", "LB", "RB", "LSB", 
			"RSB", "LP", "RP", "LE", "GE", "NE", "EQ", "LT", "GT", "MOD", "DIV", 
			"MUL", "SUB", "ADD", "IS", "DD", "C", "D", "COMMENT", "WS", "NAME", "CINT", 
			"CCHAR", "INVALID_CHAR", "CSTRING", "INVALID_STRING", "ANY"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Prev26Parser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }



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


	public Prev26Parser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SourceContext extends ParserRuleContext {
		public AST.Nodes<AST.FullDefn> ast;
		public ProgContext prog;
		public ProgContext prog() {
			return getRuleContext(ProgContext.class,0);
		}
		public TerminalNode EOF() { return getToken(Prev26Parser.EOF, 0); }
		public SourceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_source; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitSource(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SourceContext source() throws RecognitionException {
		SourceContext _localctx = new SourceContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_source);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
			((SourceContext)_localctx).prog = prog();
			setState(119);
			match(EOF);
			 ((SourceContext)_localctx).ast =  ((SourceContext)_localctx).prog.ast; 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgContext extends ParserRuleContext {
		public AST.Nodes<AST.FullDefn> ast;
		public DContext d;
		public Prog_tailContext prog_tail;
		public DContext d() {
			return getRuleContext(DContext.class,0);
		}
		public Prog_tailContext prog_tail() {
			return getRuleContext(Prog_tailContext.class,0);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_prog);
		 List<AST.FullDefn> defs = new ArrayList<>(); 
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			((ProgContext)_localctx).d = d();
			setState(123);
			((ProgContext)_localctx).prog_tail = prog_tail();

			          defs.add(((ProgContext)_localctx).d.ast);
			          defs.addAll(((ProgContext)_localctx).prog_tail.asts);
			          ((ProgContext)_localctx).ast =  bind(new AST.Nodes<>(defs), _localctx);
			      
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Prog_tailContext extends ParserRuleContext {
		public List<AST.FullDefn> asts;
		public DContext d;
		public Prog_tailContext prog_tail;
		public DContext d() {
			return getRuleContext(DContext.class,0);
		}
		public Prog_tailContext prog_tail() {
			return getRuleContext(Prog_tailContext.class,0);
		}
		public Prog_tailContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog_tail; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitProg_tail(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Prog_tailContext prog_tail() throws RecognitionException {
		Prog_tailContext _localctx = new Prog_tailContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_prog_tail);
		 ((Prog_tailContext)_localctx).asts =  new ArrayList<>(); 
		try {
			setState(131);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FUN:
			case TYP:
			case VAR:
				enterOuterAlt(_localctx, 1);
				{
				setState(126);
				((Prog_tailContext)_localctx).d = d();
				setState(127);
				((Prog_tailContext)_localctx).prog_tail = prog_tail();

				          _localctx.asts.add(((Prog_tailContext)_localctx).d.ast);
				          _localctx.asts.addAll(((Prog_tailContext)_localctx).prog_tail.asts);
				      
				}
				break;
			case EOF:
			case IN:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DContext extends ParserRuleContext {
		public AST.FullDefn ast;
		public Token n;
		public TContext t;
		public ParamsContext params;
		public Fun_tailContext fun_tail;
		public TerminalNode TYP() { return getToken(Prev26Parser.TYP, 0); }
		public TerminalNode IS() { return getToken(Prev26Parser.IS, 0); }
		public TContext t() {
			return getRuleContext(TContext.class,0);
		}
		public TerminalNode NAME() { return getToken(Prev26Parser.NAME, 0); }
		public TerminalNode VAR() { return getToken(Prev26Parser.VAR, 0); }
		public TerminalNode DD() { return getToken(Prev26Parser.DD, 0); }
		public TerminalNode FUN() { return getToken(Prev26Parser.FUN, 0); }
		public TerminalNode LP() { return getToken(Prev26Parser.LP, 0); }
		public ParamsContext params() {
			return getRuleContext(ParamsContext.class,0);
		}
		public TerminalNode RP() { return getToken(Prev26Parser.RP, 0); }
		public Fun_tailContext fun_tail() {
			return getRuleContext(Fun_tailContext.class,0);
		}
		public DContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_d; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitD(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DContext d() throws RecognitionException {
		DContext _localctx = new DContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_d);
		try {
			setState(155);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TYP:
				enterOuterAlt(_localctx, 1);
				{
				setState(133);
				match(TYP);
				setState(134);
				((DContext)_localctx).n = match(NAME);
				setState(135);
				match(IS);
				setState(136);
				((DContext)_localctx).t = t();
				 ((DContext)_localctx).ast =  bind(new AST.TypDefn(((DContext)_localctx).n.getText(), ((DContext)_localctx).t.ast), _localctx); 
				}
				break;
			case VAR:
				enterOuterAlt(_localctx, 2);
				{
				setState(139);
				match(VAR);
				setState(140);
				((DContext)_localctx).n = match(NAME);
				setState(141);
				match(DD);
				setState(142);
				((DContext)_localctx).t = t();
				 ((DContext)_localctx).ast =  bind(new AST.VarDefn(((DContext)_localctx).n.getText(), ((DContext)_localctx).t.ast), _localctx); 
				}
				break;
			case FUN:
				enterOuterAlt(_localctx, 3);
				{
				setState(145);
				match(FUN);
				setState(146);
				((DContext)_localctx).n = match(NAME);
				setState(147);
				match(LP);
				setState(148);
				((DContext)_localctx).params = params();
				setState(149);
				match(RP);
				setState(150);
				match(DD);
				setState(151);
				((DContext)_localctx).t = t();
				setState(152);
				((DContext)_localctx).fun_tail = fun_tail();

				          if (((DContext)_localctx).fun_tail.body != null)
				              ((DContext)_localctx).ast =  bind(new AST.DefFunDefn(((DContext)_localctx).n.getText(), ((DContext)_localctx).params.ast, ((DContext)_localctx).t.ast, ((DContext)_localctx).fun_tail.body), _localctx);
				          else
				              ((DContext)_localctx).ast =  bind(new AST.ExtFunDefn(((DContext)_localctx).n.getText(), ((DContext)_localctx).params.ast, ((DContext)_localctx).t.ast), _localctx);
				      
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Fun_tailContext extends ParserRuleContext {
		public AST.Expr body;
		public Expr_listContext expr_list;
		public TerminalNode IS() { return getToken(Prev26Parser.IS, 0); }
		public Expr_listContext expr_list() {
			return getRuleContext(Expr_listContext.class,0);
		}
		public Fun_tailContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fun_tail; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitFun_tail(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Fun_tailContext fun_tail() throws RecognitionException {
		Fun_tailContext _localctx = new Fun_tailContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_fun_tail);
		try {
			setState(162);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IS:
				enterOuterAlt(_localctx, 1);
				{
				setState(157);
				match(IS);
				setState(158);
				((Fun_tailContext)_localctx).expr_list = expr_list();
				 ((Fun_tailContext)_localctx).body =  packExprs(((Fun_tailContext)_localctx).expr_list.exprs, ((Fun_tailContext)_localctx).expr_list); 
				}
				break;
			case EOF:
			case FUN:
			case IN:
			case TYP:
			case VAR:
				enterOuterAlt(_localctx, 2);
				{
				 ((Fun_tailContext)_localctx).body =  null; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParamsContext extends ParserRuleContext {
		public AST.Nodes<AST.ParDefn> ast;
		public ParamContext param;
		public Param_tailContext param_tail;
		public ParamContext param() {
			return getRuleContext(ParamContext.class,0);
		}
		public Param_tailContext param_tail() {
			return getRuleContext(Param_tailContext.class,0);
		}
		public ParamsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_params; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitParams(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamsContext params() throws RecognitionException {
		ParamsContext _localctx = new ParamsContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_params);
		 List<AST.ParDefn> pars = new ArrayList<>(); 
		try {
			setState(169);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(164);
				((ParamsContext)_localctx).param = param(false);
				setState(165);
				((ParamsContext)_localctx).param_tail = param_tail(false);

				          pars.add(((ParamsContext)_localctx).param.ast);
				          pars.addAll(((ParamsContext)_localctx).param_tail.pars);
				          ((ParamsContext)_localctx).ast =  bind(new AST.Nodes<>(pars), _localctx);
				      
				}
				break;
			case RP:
				enterOuterAlt(_localctx, 2);
				{
				 ((ParamsContext)_localctx).ast =  bind(new AST.Nodes<>(pars), _localctx); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Param_tailContext extends ParserRuleContext {
		public boolean asComp;
		public List<AST.ParDefn> pars;
		public List<AST.CompDefn> compDefs;
		public ParamContext param;
		public Param_tailContext param_tail;
		public TerminalNode C() { return getToken(Prev26Parser.C, 0); }
		public ParamContext param() {
			return getRuleContext(ParamContext.class,0);
		}
		public Param_tailContext param_tail() {
			return getRuleContext(Param_tailContext.class,0);
		}
		public Param_tailContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public Param_tailContext(ParserRuleContext parent, int invokingState, boolean asComp) {
			super(parent, invokingState);
			this.asComp = asComp;
		}
		@Override public int getRuleIndex() { return RULE_param_tail; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitParam_tail(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Param_tailContext param_tail(boolean asComp) throws RecognitionException {
		Param_tailContext _localctx = new Param_tailContext(_ctx, getState(), asComp);
		enterRule(_localctx, 12, RULE_param_tail);
		 ((Param_tailContext)_localctx).pars =  new ArrayList<>(); ((Param_tailContext)_localctx).compDefs =  new ArrayList<>(); 
		try {
			setState(177);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case C:
				enterOuterAlt(_localctx, 1);
				{
				setState(171);
				match(C);
				setState(172);
				((Param_tailContext)_localctx).param = param(_localctx.asComp);
				setState(173);
				((Param_tailContext)_localctx).param_tail = param_tail(_localctx.asComp);

				          if (_localctx.asComp) {
				              _localctx.compDefs.add(((Param_tailContext)_localctx).param.compDef);
				              _localctx.compDefs.addAll(((Param_tailContext)_localctx).param_tail.compDefs);
				          } else {
				              _localctx.pars.add(((Param_tailContext)_localctx).param.ast);
				              _localctx.pars.addAll(((Param_tailContext)_localctx).param_tail.pars);
				          }
				      
				}
				break;
			case RP:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParamContext extends ParserRuleContext {
		public boolean asComp;
		public AST.ParDefn ast;
		public AST.CompDefn compDef;
		public Token n;
		public TContext t;
		public TerminalNode DD() { return getToken(Prev26Parser.DD, 0); }
		public TContext t() {
			return getRuleContext(TContext.class,0);
		}
		public TerminalNode NAME() { return getToken(Prev26Parser.NAME, 0); }
		public ParamContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public ParamContext(ParserRuleContext parent, int invokingState, boolean asComp) {
			super(parent, invokingState);
			this.asComp = asComp;
		}
		@Override public int getRuleIndex() { return RULE_param; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamContext param(boolean asComp) throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState(), asComp);
		enterRule(_localctx, 14, RULE_param);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(179);
			((ParamContext)_localctx).n = match(NAME);
			setState(180);
			match(DD);
			setState(181);
			((ParamContext)_localctx).t = t();

			          if (_localctx.asComp)
			              ((ParamContext)_localctx).compDef =  bind(new AST.CompDefn(((ParamContext)_localctx).n.getText(), ((ParamContext)_localctx).t.ast), ((ParamContext)_localctx).n, ((ParamContext)_localctx).t.ast);
			          else
			              ((ParamContext)_localctx).ast =  bind(new AST.ParDefn(((ParamContext)_localctx).n.getText(), ((ParamContext)_localctx).t.ast), _localctx);
			      
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TContext extends ParserRuleContext {
		public AST.Type ast;
		public Basic_tContext basic_t;
		public Token n;
		public Array_tContext array_t;
		public Pointer_tContext pointer_t;
		public Object_tContext object_t;
		public Par_tContext par_t;
		public Basic_tContext basic_t() {
			return getRuleContext(Basic_tContext.class,0);
		}
		public TerminalNode NAME() { return getToken(Prev26Parser.NAME, 0); }
		public Array_tContext array_t() {
			return getRuleContext(Array_tContext.class,0);
		}
		public Pointer_tContext pointer_t() {
			return getRuleContext(Pointer_tContext.class,0);
		}
		public Object_tContext object_t() {
			return getRuleContext(Object_tContext.class,0);
		}
		public TerminalNode LP() { return getToken(Prev26Parser.LP, 0); }
		public Par_tContext par_t() {
			return getRuleContext(Par_tContext.class,0);
		}
		public TerminalNode RP() { return getToken(Prev26Parser.RP, 0); }
		public TContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_t; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitT(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TContext t() throws RecognitionException {
		TContext _localctx = new TContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_t);
		try {
			setState(203);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BOOL:
			case CHAR:
			case INT:
			case VOID:
				enterOuterAlt(_localctx, 1);
				{
				setState(184);
				((TContext)_localctx).basic_t = basic_t();
				 ((TContext)_localctx).ast =  ((TContext)_localctx).basic_t.ast; 
				}
				break;
			case NAME:
				enterOuterAlt(_localctx, 2);
				{
				setState(187);
				((TContext)_localctx).n = match(NAME);
				 ((TContext)_localctx).ast =  bind(new AST.NameType(((TContext)_localctx).n.getText()), _localctx); 
				}
				break;
			case LSB:
				enterOuterAlt(_localctx, 3);
				{
				setState(189);
				((TContext)_localctx).array_t = array_t();
				 ((TContext)_localctx).ast =  ((TContext)_localctx).array_t.ast; 
				}
				break;
			case POWER:
				enterOuterAlt(_localctx, 4);
				{
				setState(192);
				((TContext)_localctx).pointer_t = pointer_t();
				 ((TContext)_localctx).ast =  ((TContext)_localctx).pointer_t.ast; 
				}
				break;
			case LB:
				enterOuterAlt(_localctx, 5);
				{
				setState(195);
				((TContext)_localctx).object_t = object_t();
				 ((TContext)_localctx).ast =  ((TContext)_localctx).object_t.ast; 
				}
				break;
			case LP:
				enterOuterAlt(_localctx, 6);
				{
				setState(198);
				match(LP);
				setState(199);
				((TContext)_localctx).par_t = par_t();
				setState(200);
				match(RP);
				 ((TContext)_localctx).ast =  bind(((TContext)_localctx).par_t.ast, _localctx); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Par_tContext extends ParserRuleContext {
		public AST.Type ast;
		public Par_t_parse_idContext par_t_parse_id;
		public Fun_tContext fun_t;
		public Par_t_parse_idContext par_t_parse_id() {
			return getRuleContext(Par_t_parse_idContext.class,0);
		}
		public Fun_tContext fun_t() {
			return getRuleContext(Fun_tContext.class,0);
		}
		public Par_tContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_par_t; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitPar_t(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Par_tContext par_t() throws RecognitionException {
		Par_tContext _localctx = new Par_tContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_par_t);
		try {
			setState(211);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BOOL:
			case CHAR:
			case INT:
			case VOID:
			case POWER:
			case LB:
			case LSB:
			case LP:
			case NAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(205);
				((Par_tContext)_localctx).par_t_parse_id = par_t_parse_id();
				 ((Par_tContext)_localctx).ast =  ((Par_tContext)_localctx).par_t_parse_id.ast; 
				}
				break;
			case DD:
				enterOuterAlt(_localctx, 2);
				{
				setState(208);
				((Par_tContext)_localctx).fun_t = fun_t();
				 ((Par_tContext)_localctx).ast =  ((Par_tContext)_localctx).fun_t.ast; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Par_t_parse_idContext extends ParserRuleContext {
		public AST.Type ast;
		public Token n;
		public Par_after_idContext par_after_id;
		public Non_id_tContext non_id_t;
		public Par_after_idContext par_after_id() {
			return getRuleContext(Par_after_idContext.class,0);
		}
		public TerminalNode NAME() { return getToken(Prev26Parser.NAME, 0); }
		public Non_id_tContext non_id_t() {
			return getRuleContext(Non_id_tContext.class,0);
		}
		public Par_t_parse_idContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_par_t_parse_id; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitPar_t_parse_id(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Par_t_parse_idContext par_t_parse_id() throws RecognitionException {
		Par_t_parse_idContext _localctx = new Par_t_parse_idContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_par_t_parse_id);
		 List<AST.CompDefn> comps = new ArrayList<>(); 
		try {
			setState(220);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(213);
				((Par_t_parse_idContext)_localctx).n = match(NAME);
				setState(214);
				((Par_t_parse_idContext)_localctx).par_after_id = par_after_id();

				          if (((Par_t_parse_idContext)_localctx).par_after_id.type != null) {
				              AST.CompDefn firstComp = bind(new AST.CompDefn(((Par_t_parse_idContext)_localctx).n.getText(), ((Par_t_parse_idContext)_localctx).par_after_id.type), ((Par_t_parse_idContext)_localctx).n, ((Par_t_parse_idContext)_localctx).par_after_id.type);
				              comps.add(firstComp);
				              comps.addAll(((Par_t_parse_idContext)_localctx).par_after_id.compDefs);
				              AST.Nodes<AST.CompDefn> compNodes = bind(new AST.Nodes<>(comps), _localctx);
				              ((Par_t_parse_idContext)_localctx).ast =  bind(new AST.StrType(compNodes), _localctx);
				          } else {
				              ((Par_t_parse_idContext)_localctx).ast =  bind(new AST.NameType(((Par_t_parse_idContext)_localctx).n.getText()), _localctx);
				          }
				      
				}
				break;
			case BOOL:
			case CHAR:
			case INT:
			case VOID:
			case POWER:
			case LB:
			case LSB:
			case LP:
				enterOuterAlt(_localctx, 2);
				{
				setState(217);
				((Par_t_parse_idContext)_localctx).non_id_t = non_id_t();
				 ((Par_t_parse_idContext)_localctx).ast =  bind(((Par_t_parse_idContext)_localctx).non_id_t.ast, _localctx); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Par_after_idContext extends ParserRuleContext {
		public AST.Type type;
		public List<AST.CompDefn> compDefs;
		public TContext t;
		public Param_tailContext param_tail;
		public TerminalNode DD() { return getToken(Prev26Parser.DD, 0); }
		public TContext t() {
			return getRuleContext(TContext.class,0);
		}
		public Param_tailContext param_tail() {
			return getRuleContext(Param_tailContext.class,0);
		}
		public Par_after_idContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_par_after_id; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitPar_after_id(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Par_after_idContext par_after_id() throws RecognitionException {
		Par_after_idContext _localctx = new Par_after_idContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_par_after_id);
		 ((Par_after_idContext)_localctx).compDefs =  new ArrayList<>(); 
		try {
			setState(228);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DD:
				enterOuterAlt(_localctx, 1);
				{
				setState(222);
				match(DD);
				setState(223);
				((Par_after_idContext)_localctx).t = t();
				setState(224);
				((Par_after_idContext)_localctx).param_tail = param_tail(true);

				          ((Par_after_idContext)_localctx).type =  ((Par_after_idContext)_localctx).t.ast;
				          _localctx.compDefs.addAll(((Par_after_idContext)_localctx).param_tail.compDefs);
				      
				}
				break;
			case RP:
				enterOuterAlt(_localctx, 2);
				{
				 ((Par_after_idContext)_localctx).type =  null; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Non_id_tContext extends ParserRuleContext {
		public AST.Type ast;
		public Basic_tContext basic_t;
		public Array_tContext array_t;
		public Pointer_tContext pointer_t;
		public Object_tContext object_t;
		public Par_tContext par_t;
		public Basic_tContext basic_t() {
			return getRuleContext(Basic_tContext.class,0);
		}
		public Array_tContext array_t() {
			return getRuleContext(Array_tContext.class,0);
		}
		public Pointer_tContext pointer_t() {
			return getRuleContext(Pointer_tContext.class,0);
		}
		public Object_tContext object_t() {
			return getRuleContext(Object_tContext.class,0);
		}
		public TerminalNode LP() { return getToken(Prev26Parser.LP, 0); }
		public Par_tContext par_t() {
			return getRuleContext(Par_tContext.class,0);
		}
		public TerminalNode RP() { return getToken(Prev26Parser.RP, 0); }
		public Non_id_tContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_non_id_t; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitNon_id_t(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Non_id_tContext non_id_t() throws RecognitionException {
		Non_id_tContext _localctx = new Non_id_tContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_non_id_t);
		try {
			setState(247);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BOOL:
			case CHAR:
			case INT:
			case VOID:
				enterOuterAlt(_localctx, 1);
				{
				setState(230);
				((Non_id_tContext)_localctx).basic_t = basic_t();
				 ((Non_id_tContext)_localctx).ast =  ((Non_id_tContext)_localctx).basic_t.ast; 
				}
				break;
			case LSB:
				enterOuterAlt(_localctx, 2);
				{
				setState(233);
				((Non_id_tContext)_localctx).array_t = array_t();
				 ((Non_id_tContext)_localctx).ast =  ((Non_id_tContext)_localctx).array_t.ast; 
				}
				break;
			case POWER:
				enterOuterAlt(_localctx, 3);
				{
				setState(236);
				((Non_id_tContext)_localctx).pointer_t = pointer_t();
				 ((Non_id_tContext)_localctx).ast =  ((Non_id_tContext)_localctx).pointer_t.ast; 
				}
				break;
			case LB:
				enterOuterAlt(_localctx, 4);
				{
				setState(239);
				((Non_id_tContext)_localctx).object_t = object_t();
				 ((Non_id_tContext)_localctx).ast =  ((Non_id_tContext)_localctx).object_t.ast; 
				}
				break;
			case LP:
				enterOuterAlt(_localctx, 5);
				{
				setState(242);
				match(LP);
				setState(243);
				((Non_id_tContext)_localctx).par_t = par_t();
				setState(244);
				match(RP);
				 ((Non_id_tContext)_localctx).ast =  bind(((Non_id_tContext)_localctx).par_t.ast, _localctx); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Basic_tContext extends ParserRuleContext {
		public AST.AtomType ast;
		public TerminalNode INT() { return getToken(Prev26Parser.INT, 0); }
		public TerminalNode CHAR() { return getToken(Prev26Parser.CHAR, 0); }
		public TerminalNode BOOL() { return getToken(Prev26Parser.BOOL, 0); }
		public TerminalNode VOID() { return getToken(Prev26Parser.VOID, 0); }
		public Basic_tContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_basic_t; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitBasic_t(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Basic_tContext basic_t() throws RecognitionException {
		Basic_tContext _localctx = new Basic_tContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_basic_t);
		try {
			setState(257);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(249);
				match(INT);
				 ((Basic_tContext)_localctx).ast =  bind(new AST.AtomType(AST.AtomType.Type.INT), _localctx); 
				}
				break;
			case CHAR:
				enterOuterAlt(_localctx, 2);
				{
				setState(251);
				match(CHAR);
				 ((Basic_tContext)_localctx).ast =  bind(new AST.AtomType(AST.AtomType.Type.CHAR), _localctx); 
				}
				break;
			case BOOL:
				enterOuterAlt(_localctx, 3);
				{
				setState(253);
				match(BOOL);
				 ((Basic_tContext)_localctx).ast =  bind(new AST.AtomType(AST.AtomType.Type.BOOL), _localctx); 
				}
				break;
			case VOID:
				enterOuterAlt(_localctx, 4);
				{
				setState(255);
				match(VOID);
				 ((Basic_tContext)_localctx).ast =  bind(new AST.AtomType(AST.AtomType.Type.VOID), _localctx); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Array_tContext extends ParserRuleContext {
		public AST.ArrType ast;
		public C_intContext c_int;
		public TContext t;
		public TerminalNode LSB() { return getToken(Prev26Parser.LSB, 0); }
		public C_intContext c_int() {
			return getRuleContext(C_intContext.class,0);
		}
		public TerminalNode RSB() { return getToken(Prev26Parser.RSB, 0); }
		public TContext t() {
			return getRuleContext(TContext.class,0);
		}
		public Array_tContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_array_t; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitArray_t(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Array_tContext array_t() throws RecognitionException {
		Array_tContext _localctx = new Array_tContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_array_t);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(259);
			match(LSB);
			setState(260);
			((Array_tContext)_localctx).c_int = c_int();
			setState(261);
			match(RSB);
			setState(262);
			((Array_tContext)_localctx).t = t();
			 ((Array_tContext)_localctx).ast =  bind(new AST.ArrType(((Array_tContext)_localctx).t.ast, ((Array_tContext)_localctx).c_int.getText()), _localctx); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Pointer_tContext extends ParserRuleContext {
		public AST.PtrType ast;
		public TContext t;
		public TerminalNode POWER() { return getToken(Prev26Parser.POWER, 0); }
		public TContext t() {
			return getRuleContext(TContext.class,0);
		}
		public Pointer_tContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pointer_t; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitPointer_t(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Pointer_tContext pointer_t() throws RecognitionException {
		Pointer_tContext _localctx = new Pointer_tContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_pointer_t);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(265);
			match(POWER);
			setState(266);
			((Pointer_tContext)_localctx).t = t();
			 ((Pointer_tContext)_localctx).ast =  bind(new AST.PtrType(((Pointer_tContext)_localctx).t.ast), _localctx); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Object_tContext extends ParserRuleContext {
		public AST.RecType ast;
		public CompsContext comps;
		public TerminalNode LB() { return getToken(Prev26Parser.LB, 0); }
		public CompsContext comps() {
			return getRuleContext(CompsContext.class,0);
		}
		public TerminalNode RB() { return getToken(Prev26Parser.RB, 0); }
		public Object_tContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_object_t; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitObject_t(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Object_tContext object_t() throws RecognitionException {
		Object_tContext _localctx = new Object_tContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_object_t);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(269);
			match(LB);
			setState(270);
			((Object_tContext)_localctx).comps = comps();
			setState(271);
			match(RB);
			 ((Object_tContext)_localctx).ast =  bind(new AST.UniType(((Object_tContext)_localctx).comps.ast), _localctx); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CompsContext extends ParserRuleContext {
		public AST.Nodes<AST.CompDefn> ast;
		public CompContext comp;
		public Comp_tailContext comp_tail;
		public CompContext comp() {
			return getRuleContext(CompContext.class,0);
		}
		public Comp_tailContext comp_tail() {
			return getRuleContext(Comp_tailContext.class,0);
		}
		public CompsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comps; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitComps(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompsContext comps() throws RecognitionException {
		CompsContext _localctx = new CompsContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_comps);
		 List<AST.CompDefn> comps = new ArrayList<>(); 
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(274);
			((CompsContext)_localctx).comp = comp();
			setState(275);
			((CompsContext)_localctx).comp_tail = comp_tail();

			          comps.add(((CompsContext)_localctx).comp.ast);
			          comps.addAll(((CompsContext)_localctx).comp_tail.asts);
			          ((CompsContext)_localctx).ast =  bind(new AST.Nodes<>(comps), _localctx);
			      
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Comp_tailContext extends ParserRuleContext {
		public List<AST.CompDefn> asts;
		public CompContext comp;
		public Comp_tailContext comp_tail;
		public TerminalNode C() { return getToken(Prev26Parser.C, 0); }
		public CompContext comp() {
			return getRuleContext(CompContext.class,0);
		}
		public Comp_tailContext comp_tail() {
			return getRuleContext(Comp_tailContext.class,0);
		}
		public Comp_tailContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comp_tail; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitComp_tail(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Comp_tailContext comp_tail() throws RecognitionException {
		Comp_tailContext _localctx = new Comp_tailContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_comp_tail);
		 ((Comp_tailContext)_localctx).asts =  new ArrayList<>(); 
		try {
			setState(284);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case C:
				enterOuterAlt(_localctx, 1);
				{
				setState(278);
				match(C);
				setState(279);
				((Comp_tailContext)_localctx).comp = comp();
				setState(280);
				((Comp_tailContext)_localctx).comp_tail = comp_tail();

				          _localctx.asts.add(((Comp_tailContext)_localctx).comp.ast);
				          _localctx.asts.addAll(((Comp_tailContext)_localctx).comp_tail.asts);
				      
				}
				break;
			case RB:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CompContext extends ParserRuleContext {
		public AST.CompDefn ast;
		public Token n;
		public TContext t;
		public TerminalNode DD() { return getToken(Prev26Parser.DD, 0); }
		public TContext t() {
			return getRuleContext(TContext.class,0);
		}
		public TerminalNode NAME() { return getToken(Prev26Parser.NAME, 0); }
		public CompContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitComp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompContext comp() throws RecognitionException {
		CompContext _localctx = new CompContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_comp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(286);
			((CompContext)_localctx).n = match(NAME);
			setState(287);
			match(DD);
			setState(288);
			((CompContext)_localctx).t = t();
			 ((CompContext)_localctx).ast =  bind(new AST.CompDefn(((CompContext)_localctx).n.getText(), ((CompContext)_localctx).t.ast), _localctx); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Fun_tContext extends ParserRuleContext {
		public AST.FunType ast;
		public T_listContext t_list;
		public TContext t;
		public List<TerminalNode> DD() { return getTokens(Prev26Parser.DD); }
		public TerminalNode DD(int i) {
			return getToken(Prev26Parser.DD, i);
		}
		public T_listContext t_list() {
			return getRuleContext(T_listContext.class,0);
		}
		public TContext t() {
			return getRuleContext(TContext.class,0);
		}
		public Fun_tContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fun_t; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitFun_t(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Fun_tContext fun_t() throws RecognitionException {
		Fun_tContext _localctx = new Fun_tContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_fun_t);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(291);
			match(DD);
			setState(292);
			((Fun_tContext)_localctx).t_list = t_list();
			setState(293);
			match(DD);
			setState(294);
			((Fun_tContext)_localctx).t = t();
			 ((Fun_tContext)_localctx).ast =  bind(new AST.FunType(((Fun_tContext)_localctx).t_list.ast, ((Fun_tContext)_localctx).t.ast), _localctx); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class T_listContext extends ParserRuleContext {
		public AST.Nodes<AST.Type> ast;
		public TContext t;
		public T_list_tailContext t_list_tail;
		public TContext t() {
			return getRuleContext(TContext.class,0);
		}
		public T_list_tailContext t_list_tail() {
			return getRuleContext(T_list_tailContext.class,0);
		}
		public T_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_t_list; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitT_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final T_listContext t_list() throws RecognitionException {
		T_listContext _localctx = new T_listContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_t_list);
		 List<AST.Type> types = new ArrayList<>(); 
		try {
			setState(302);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BOOL:
			case CHAR:
			case INT:
			case VOID:
			case POWER:
			case LB:
			case LSB:
			case LP:
			case NAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(297);
				((T_listContext)_localctx).t = t();
				setState(298);
				((T_listContext)_localctx).t_list_tail = t_list_tail();

				          types.add(((T_listContext)_localctx).t.ast);
				          types.addAll(((T_listContext)_localctx).t_list_tail.asts);
				          ((T_listContext)_localctx).ast =  bind(new AST.Nodes<>(types), _localctx);
				      
				}
				break;
			case DD:
				enterOuterAlt(_localctx, 2);
				{
				 ((T_listContext)_localctx).ast =  bind(new AST.Nodes<>(types), _localctx); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class T_list_tailContext extends ParserRuleContext {
		public List<AST.Type> asts;
		public TContext t;
		public T_list_tailContext t_list_tail;
		public TerminalNode C() { return getToken(Prev26Parser.C, 0); }
		public TContext t() {
			return getRuleContext(TContext.class,0);
		}
		public T_list_tailContext t_list_tail() {
			return getRuleContext(T_list_tailContext.class,0);
		}
		public T_list_tailContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_t_list_tail; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitT_list_tail(this);
			else return visitor.visitChildren(this);
		}
	}

	public final T_list_tailContext t_list_tail() throws RecognitionException {
		T_list_tailContext _localctx = new T_list_tailContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_t_list_tail);
		 ((T_list_tailContext)_localctx).asts =  new ArrayList<>(); 
		try {
			setState(310);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case C:
				enterOuterAlt(_localctx, 1);
				{
				setState(304);
				match(C);
				setState(305);
				((T_list_tailContext)_localctx).t = t();
				setState(306);
				((T_list_tailContext)_localctx).t_list_tail = t_list_tail();

				          _localctx.asts.add(((T_list_tailContext)_localctx).t.ast);
				          _localctx.asts.addAll(((T_list_tailContext)_localctx).t_list_tail.asts);
				      
				}
				break;
			case DD:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Expr_listContext extends ParserRuleContext {
		public List<AST.Expr> exprs;
		public EContext e;
		public E_tailContext e_tail;
		public EContext e() {
			return getRuleContext(EContext.class,0);
		}
		public E_tailContext e_tail() {
			return getRuleContext(E_tailContext.class,0);
		}
		public Expr_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr_list; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitExpr_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expr_listContext expr_list() throws RecognitionException {
		Expr_listContext _localctx = new Expr_listContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_expr_list);
		 ((Expr_listContext)_localctx).exprs =  new ArrayList<>(); 
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(312);
			((Expr_listContext)_localctx).e = e();
			setState(313);
			((Expr_listContext)_localctx).e_tail = e_tail();

			          _localctx.exprs.add(((Expr_listContext)_localctx).e.ast);
			          _localctx.exprs.addAll(((Expr_listContext)_localctx).e_tail.exprs);
			      
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class E_tailContext extends ParserRuleContext {
		public List<AST.Expr> exprs;
		public EContext e;
		public E_tailContext e_tail;
		public TerminalNode C() { return getToken(Prev26Parser.C, 0); }
		public EContext e() {
			return getRuleContext(EContext.class,0);
		}
		public E_tailContext e_tail() {
			return getRuleContext(E_tailContext.class,0);
		}
		public E_tailContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_e_tail; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitE_tail(this);
			else return visitor.visitChildren(this);
		}
	}

	public final E_tailContext e_tail() throws RecognitionException {
		E_tailContext _localctx = new E_tailContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_e_tail);
		 ((E_tailContext)_localctx).exprs =  new ArrayList<>(); 
		try {
			setState(322);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case C:
				enterOuterAlt(_localctx, 1);
				{
				setState(316);
				match(C);
				setState(317);
				((E_tailContext)_localctx).e = e();
				setState(318);
				((E_tailContext)_localctx).e_tail = e_tail();

				          _localctx.exprs.add(((E_tailContext)_localctx).e.ast);
				          _localctx.exprs.addAll(((E_tailContext)_localctx).e_tail.exprs);
				      
				}
				break;
			case EOF:
			case ELSE:
			case END:
			case FUN:
			case IN:
			case TYP:
			case VAR:
			case RP:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EContext extends ParserRuleContext {
		public AST.Expr ast;
		public E_asContext e_as;
		public E_assignContext e_assign;
		public E_asContext e_as() {
			return getRuleContext(E_asContext.class,0);
		}
		public E_assignContext e_assign() {
			return getRuleContext(E_assignContext.class,0);
		}
		public EContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_e; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitE(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EContext e() throws RecognitionException {
		EContext _localctx = new EContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_e);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(324);
			((EContext)_localctx).e_as = e_as();
			setState(325);
			((EContext)_localctx).e_assign = e_assign();

			          if (((EContext)_localctx).e_assign.rhs != null)
			              ((EContext)_localctx).ast =  bind(new AST.AsgnExpr(((EContext)_localctx).e_as.ast, ((EContext)_localctx).e_assign.rhs), ((EContext)_localctx).e_as.ast, ((EContext)_localctx).e_assign.rhs);
			          else
			              ((EContext)_localctx).ast =  bind(((EContext)_localctx).e_as.ast, _localctx);
			      
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class E_assignContext extends ParserRuleContext {
		public AST.Expr rhs;
		public E_asContext e_as;
		public TerminalNode IS() { return getToken(Prev26Parser.IS, 0); }
		public E_asContext e_as() {
			return getRuleContext(E_asContext.class,0);
		}
		public E_assignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_e_assign; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitE_assign(this);
			else return visitor.visitChildren(this);
		}
	}

	public final E_assignContext e_assign() throws RecognitionException {
		E_assignContext _localctx = new E_assignContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_e_assign);
		try {
			setState(333);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IS:
				enterOuterAlt(_localctx, 1);
				{
				setState(328);
				match(IS);
				setState(329);
				((E_assignContext)_localctx).e_as = e_as();
				 ((E_assignContext)_localctx).rhs =  ((E_assignContext)_localctx).e_as.ast; 
				}
				break;
			case EOF:
			case DO:
			case ELSE:
			case END:
			case FUN:
			case IN:
			case THEN:
			case TYP:
			case VAR:
			case RSB:
			case RP:
			case C:
				enterOuterAlt(_localctx, 2);
				{
				 ((E_assignContext)_localctx).rhs =  null; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class E_asContext extends ParserRuleContext {
		public AST.Expr ast;
		public E_orContext e_or;
		public As_tailContext as_tail;
		public E_orContext e_or() {
			return getRuleContext(E_orContext.class,0);
		}
		public As_tailContext as_tail() {
			return getRuleContext(As_tailContext.class,0);
		}
		public E_asContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_e_as; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitE_as(this);
			else return visitor.visitChildren(this);
		}
	}

	public final E_asContext e_as() throws RecognitionException {
		E_asContext _localctx = new E_asContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_e_as);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(335);
			((E_asContext)_localctx).e_or = e_or();
			setState(336);
			((E_asContext)_localctx).as_tail = as_tail(((E_asContext)_localctx).e_or.ast);
			 ((E_asContext)_localctx).ast =  bind(((E_asContext)_localctx).as_tail.ast, _localctx); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class As_tailContext extends ParserRuleContext {
		public AST.Expr expr;
		public AST.Expr ast;
		public AST.Expr next;
		public TContext t;
		public As_tailContext as_tail;
		public TerminalNode AS() { return getToken(Prev26Parser.AS, 0); }
		public TContext t() {
			return getRuleContext(TContext.class,0);
		}
		public As_tailContext as_tail() {
			return getRuleContext(As_tailContext.class,0);
		}
		public As_tailContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public As_tailContext(ParserRuleContext parent, int invokingState, AST.Expr expr) {
			super(parent, invokingState);
			this.expr = expr;
		}
		@Override public int getRuleIndex() { return RULE_as_tail; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitAs_tail(this);
			else return visitor.visitChildren(this);
		}
	}

	public final As_tailContext as_tail(AST.Expr expr) throws RecognitionException {
		As_tailContext _localctx = new As_tailContext(_ctx, getState(), expr);
		enterRule(_localctx, 56, RULE_as_tail);
		try {
			setState(346);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AS:
				enterOuterAlt(_localctx, 1);
				{
				setState(339);
				match(AS);
				setState(340);
				((As_tailContext)_localctx).t = t();

				          ((As_tailContext)_localctx).next =  bind(new AST.CastExpr(((As_tailContext)_localctx).t.ast, _localctx.expr), _localctx.expr, ((As_tailContext)_localctx).t.ast);
				      
				setState(342);
				((As_tailContext)_localctx).as_tail = as_tail(_localctx.next);
				 ((As_tailContext)_localctx).ast =  ((As_tailContext)_localctx).as_tail.ast; 
				}
				break;
			case EOF:
			case DO:
			case ELSE:
			case END:
			case FUN:
			case IN:
			case THEN:
			case TYP:
			case VAR:
			case RSB:
			case RP:
			case IS:
			case C:
				enterOuterAlt(_localctx, 2);
				{
				 ((As_tailContext)_localctx).ast =  _localctx.expr; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class E_orContext extends ParserRuleContext {
		public AST.Expr ast;
		public E_andContext e_and;
		public Or_tailContext or_tail;
		public E_andContext e_and() {
			return getRuleContext(E_andContext.class,0);
		}
		public Or_tailContext or_tail() {
			return getRuleContext(Or_tailContext.class,0);
		}
		public E_orContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_e_or; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitE_or(this);
			else return visitor.visitChildren(this);
		}
	}

	public final E_orContext e_or() throws RecognitionException {
		E_orContext _localctx = new E_orContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_e_or);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(348);
			((E_orContext)_localctx).e_and = e_and();
			setState(349);
			((E_orContext)_localctx).or_tail = or_tail(((E_orContext)_localctx).e_and.ast);
			 ((E_orContext)_localctx).ast =  bind(((E_orContext)_localctx).or_tail.ast, _localctx); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Or_tailContext extends ParserRuleContext {
		public AST.Expr expr;
		public AST.Expr ast;
		public AST.Expr next;
		public E_andContext e_and;
		public Or_tailContext or_tail;
		public TerminalNode OR() { return getToken(Prev26Parser.OR, 0); }
		public E_andContext e_and() {
			return getRuleContext(E_andContext.class,0);
		}
		public Or_tailContext or_tail() {
			return getRuleContext(Or_tailContext.class,0);
		}
		public Or_tailContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public Or_tailContext(ParserRuleContext parent, int invokingState, AST.Expr expr) {
			super(parent, invokingState);
			this.expr = expr;
		}
		@Override public int getRuleIndex() { return RULE_or_tail; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitOr_tail(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Or_tailContext or_tail(AST.Expr expr) throws RecognitionException {
		Or_tailContext _localctx = new Or_tailContext(_ctx, getState(), expr);
		enterRule(_localctx, 60, RULE_or_tail);
		try {
			setState(359);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OR:
				enterOuterAlt(_localctx, 1);
				{
				setState(352);
				match(OR);
				setState(353);
				((Or_tailContext)_localctx).e_and = e_and();

				          ((Or_tailContext)_localctx).next =  bind(new AST.BinExpr(AST.BinExpr.Oper.OR, _localctx.expr, ((Or_tailContext)_localctx).e_and.ast), _localctx.expr, ((Or_tailContext)_localctx).e_and.ast);
				      
				setState(355);
				((Or_tailContext)_localctx).or_tail = or_tail(_localctx.next);
				 ((Or_tailContext)_localctx).ast =  ((Or_tailContext)_localctx).or_tail.ast; 
				}
				break;
			case EOF:
			case AS:
			case DO:
			case ELSE:
			case END:
			case FUN:
			case IN:
			case THEN:
			case TYP:
			case VAR:
			case RSB:
			case RP:
			case IS:
			case C:
				enterOuterAlt(_localctx, 2);
				{
				 ((Or_tailContext)_localctx).ast =  _localctx.expr; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class E_andContext extends ParserRuleContext {
		public AST.Expr ast;
		public E_compareContext e_compare;
		public And_tailContext and_tail;
		public E_compareContext e_compare() {
			return getRuleContext(E_compareContext.class,0);
		}
		public And_tailContext and_tail() {
			return getRuleContext(And_tailContext.class,0);
		}
		public E_andContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_e_and; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitE_and(this);
			else return visitor.visitChildren(this);
		}
	}

	public final E_andContext e_and() throws RecognitionException {
		E_andContext _localctx = new E_andContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_e_and);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(361);
			((E_andContext)_localctx).e_compare = e_compare();
			setState(362);
			((E_andContext)_localctx).and_tail = and_tail(((E_andContext)_localctx).e_compare.ast);
			 ((E_andContext)_localctx).ast =  bind(((E_andContext)_localctx).and_tail.ast, _localctx); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class And_tailContext extends ParserRuleContext {
		public AST.Expr expr;
		public AST.Expr ast;
		public AST.Expr next;
		public E_compareContext e_compare;
		public And_tailContext and_tail;
		public TerminalNode AND() { return getToken(Prev26Parser.AND, 0); }
		public E_compareContext e_compare() {
			return getRuleContext(E_compareContext.class,0);
		}
		public And_tailContext and_tail() {
			return getRuleContext(And_tailContext.class,0);
		}
		public And_tailContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public And_tailContext(ParserRuleContext parent, int invokingState, AST.Expr expr) {
			super(parent, invokingState);
			this.expr = expr;
		}
		@Override public int getRuleIndex() { return RULE_and_tail; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitAnd_tail(this);
			else return visitor.visitChildren(this);
		}
	}

	public final And_tailContext and_tail(AST.Expr expr) throws RecognitionException {
		And_tailContext _localctx = new And_tailContext(_ctx, getState(), expr);
		enterRule(_localctx, 64, RULE_and_tail);
		try {
			setState(372);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AND:
				enterOuterAlt(_localctx, 1);
				{
				setState(365);
				match(AND);
				setState(366);
				((And_tailContext)_localctx).e_compare = e_compare();

				          ((And_tailContext)_localctx).next =  bind(new AST.BinExpr(AST.BinExpr.Oper.AND, _localctx.expr, ((And_tailContext)_localctx).e_compare.ast), _localctx.expr, ((And_tailContext)_localctx).e_compare.ast);
				      
				setState(368);
				((And_tailContext)_localctx).and_tail = and_tail(_localctx.next);
				 ((And_tailContext)_localctx).ast =  ((And_tailContext)_localctx).and_tail.ast; 
				}
				break;
			case EOF:
			case AS:
			case DO:
			case ELSE:
			case END:
			case FUN:
			case IN:
			case OR:
			case THEN:
			case TYP:
			case VAR:
			case RSB:
			case RP:
			case IS:
			case C:
				enterOuterAlt(_localctx, 2);
				{
				 ((And_tailContext)_localctx).ast =  _localctx.expr; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class E_compareContext extends ParserRuleContext {
		public AST.Expr ast;
		public E_addContext e_add;
		public Compare_tailContext compare_tail;
		public E_addContext e_add() {
			return getRuleContext(E_addContext.class,0);
		}
		public Compare_tailContext compare_tail() {
			return getRuleContext(Compare_tailContext.class,0);
		}
		public E_compareContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_e_compare; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitE_compare(this);
			else return visitor.visitChildren(this);
		}
	}

	public final E_compareContext e_compare() throws RecognitionException {
		E_compareContext _localctx = new E_compareContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_e_compare);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(374);
			((E_compareContext)_localctx).e_add = e_add();
			setState(375);
			((E_compareContext)_localctx).compare_tail = compare_tail();

			          if (((E_compareContext)_localctx).compare_tail.rhs != null)
			              ((E_compareContext)_localctx).ast =  bind(new AST.BinExpr(((E_compareContext)_localctx).compare_tail.oper, ((E_compareContext)_localctx).e_add.ast, ((E_compareContext)_localctx).compare_tail.rhs), ((E_compareContext)_localctx).e_add.ast, ((E_compareContext)_localctx).compare_tail.rhs);
			          else
			              ((E_compareContext)_localctx).ast =  bind(((E_compareContext)_localctx).e_add.ast, _localctx);
			      
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Compare_tailContext extends ParserRuleContext {
		public AST.BinExpr.Oper oper;
		public AST.Expr rhs;
		public Compare_opContext compare_op;
		public E_addContext e_add;
		public Compare_opContext compare_op() {
			return getRuleContext(Compare_opContext.class,0);
		}
		public E_addContext e_add() {
			return getRuleContext(E_addContext.class,0);
		}
		public Compare_tailContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compare_tail; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitCompare_tail(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Compare_tailContext compare_tail() throws RecognitionException {
		Compare_tailContext _localctx = new Compare_tailContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_compare_tail);
		try {
			setState(383);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LE:
			case GE:
			case NE:
			case EQ:
			case LT:
			case GT:
				enterOuterAlt(_localctx, 1);
				{
				setState(378);
				((Compare_tailContext)_localctx).compare_op = compare_op();
				setState(379);
				((Compare_tailContext)_localctx).e_add = e_add();

				          ((Compare_tailContext)_localctx).oper =  ((Compare_tailContext)_localctx).compare_op.oper;
				          ((Compare_tailContext)_localctx).rhs =  ((Compare_tailContext)_localctx).e_add.ast;
				      
				}
				break;
			case EOF:
			case AND:
			case AS:
			case DO:
			case ELSE:
			case END:
			case FUN:
			case IN:
			case OR:
			case THEN:
			case TYP:
			case VAR:
			case RSB:
			case RP:
			case IS:
			case C:
				enterOuterAlt(_localctx, 2);
				{
				 ((Compare_tailContext)_localctx).rhs =  null; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Compare_opContext extends ParserRuleContext {
		public AST.BinExpr.Oper oper;
		public TerminalNode EQ() { return getToken(Prev26Parser.EQ, 0); }
		public TerminalNode NE() { return getToken(Prev26Parser.NE, 0); }
		public TerminalNode LT() { return getToken(Prev26Parser.LT, 0); }
		public TerminalNode GT() { return getToken(Prev26Parser.GT, 0); }
		public TerminalNode LE() { return getToken(Prev26Parser.LE, 0); }
		public TerminalNode GE() { return getToken(Prev26Parser.GE, 0); }
		public Compare_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compare_op; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitCompare_op(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Compare_opContext compare_op() throws RecognitionException {
		Compare_opContext _localctx = new Compare_opContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_compare_op);
		try {
			setState(397);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case EQ:
				enterOuterAlt(_localctx, 1);
				{
				setState(385);
				match(EQ);
				 ((Compare_opContext)_localctx).oper =  AST.BinExpr.Oper.EQU; 
				}
				break;
			case NE:
				enterOuterAlt(_localctx, 2);
				{
				setState(387);
				match(NE);
				 ((Compare_opContext)_localctx).oper =  AST.BinExpr.Oper.NEQ; 
				}
				break;
			case LT:
				enterOuterAlt(_localctx, 3);
				{
				setState(389);
				match(LT);
				 ((Compare_opContext)_localctx).oper =  AST.BinExpr.Oper.LTH; 
				}
				break;
			case GT:
				enterOuterAlt(_localctx, 4);
				{
				setState(391);
				match(GT);
				 ((Compare_opContext)_localctx).oper =  AST.BinExpr.Oper.GTH; 
				}
				break;
			case LE:
				enterOuterAlt(_localctx, 5);
				{
				setState(393);
				match(LE);
				 ((Compare_opContext)_localctx).oper =  AST.BinExpr.Oper.LEQ; 
				}
				break;
			case GE:
				enterOuterAlt(_localctx, 6);
				{
				setState(395);
				match(GE);
				 ((Compare_opContext)_localctx).oper =  AST.BinExpr.Oper.GEQ; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class E_addContext extends ParserRuleContext {
		public AST.Expr ast;
		public E_mulContext e_mul;
		public Add_tailContext add_tail;
		public E_mulContext e_mul() {
			return getRuleContext(E_mulContext.class,0);
		}
		public Add_tailContext add_tail() {
			return getRuleContext(Add_tailContext.class,0);
		}
		public E_addContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_e_add; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitE_add(this);
			else return visitor.visitChildren(this);
		}
	}

	public final E_addContext e_add() throws RecognitionException {
		E_addContext _localctx = new E_addContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_e_add);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(399);
			((E_addContext)_localctx).e_mul = e_mul();
			setState(400);
			((E_addContext)_localctx).add_tail = add_tail(((E_addContext)_localctx).e_mul.ast);
			 ((E_addContext)_localctx).ast =  bind(((E_addContext)_localctx).add_tail.ast, _localctx); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Add_tailContext extends ParserRuleContext {
		public AST.Expr expr;
		public AST.Expr ast;
		public AST.Expr next;
		public Add_compContext add_comp;
		public E_mulContext e_mul;
		public Add_tailContext add_tail;
		public Add_compContext add_comp() {
			return getRuleContext(Add_compContext.class,0);
		}
		public E_mulContext e_mul() {
			return getRuleContext(E_mulContext.class,0);
		}
		public Add_tailContext add_tail() {
			return getRuleContext(Add_tailContext.class,0);
		}
		public Add_tailContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public Add_tailContext(ParserRuleContext parent, int invokingState, AST.Expr expr) {
			super(parent, invokingState);
			this.expr = expr;
		}
		@Override public int getRuleIndex() { return RULE_add_tail; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitAdd_tail(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Add_tailContext add_tail(AST.Expr expr) throws RecognitionException {
		Add_tailContext _localctx = new Add_tailContext(_ctx, getState(), expr);
		enterRule(_localctx, 74, RULE_add_tail);
		try {
			setState(410);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SUB:
			case ADD:
				enterOuterAlt(_localctx, 1);
				{
				setState(403);
				((Add_tailContext)_localctx).add_comp = add_comp();
				setState(404);
				((Add_tailContext)_localctx).e_mul = e_mul();

				          ((Add_tailContext)_localctx).next =  bind(new AST.BinExpr(((Add_tailContext)_localctx).add_comp.oper, _localctx.expr, ((Add_tailContext)_localctx).e_mul.ast), _localctx.expr, ((Add_tailContext)_localctx).e_mul.ast);
				      
				setState(406);
				((Add_tailContext)_localctx).add_tail = add_tail(_localctx.next);
				 ((Add_tailContext)_localctx).ast =  ((Add_tailContext)_localctx).add_tail.ast; 
				}
				break;
			case EOF:
			case AND:
			case AS:
			case DO:
			case ELSE:
			case END:
			case FUN:
			case IN:
			case OR:
			case THEN:
			case TYP:
			case VAR:
			case RSB:
			case RP:
			case LE:
			case GE:
			case NE:
			case EQ:
			case LT:
			case GT:
			case IS:
			case C:
				enterOuterAlt(_localctx, 2);
				{
				 ((Add_tailContext)_localctx).ast =  _localctx.expr; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Add_compContext extends ParserRuleContext {
		public AST.BinExpr.Oper oper;
		public TerminalNode ADD() { return getToken(Prev26Parser.ADD, 0); }
		public TerminalNode SUB() { return getToken(Prev26Parser.SUB, 0); }
		public Add_compContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_add_comp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitAdd_comp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Add_compContext add_comp() throws RecognitionException {
		Add_compContext _localctx = new Add_compContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_add_comp);
		try {
			setState(416);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ADD:
				enterOuterAlt(_localctx, 1);
				{
				setState(412);
				match(ADD);
				 ((Add_compContext)_localctx).oper =  AST.BinExpr.Oper.ADD; 
				}
				break;
			case SUB:
				enterOuterAlt(_localctx, 2);
				{
				setState(414);
				match(SUB);
				 ((Add_compContext)_localctx).oper =  AST.BinExpr.Oper.SUB; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class E_mulContext extends ParserRuleContext {
		public AST.Expr ast;
		public E_prefixContext e_prefix;
		public Mul_tailContext mul_tail;
		public E_prefixContext e_prefix() {
			return getRuleContext(E_prefixContext.class,0);
		}
		public Mul_tailContext mul_tail() {
			return getRuleContext(Mul_tailContext.class,0);
		}
		public E_mulContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_e_mul; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitE_mul(this);
			else return visitor.visitChildren(this);
		}
	}

	public final E_mulContext e_mul() throws RecognitionException {
		E_mulContext _localctx = new E_mulContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_e_mul);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(418);
			((E_mulContext)_localctx).e_prefix = e_prefix();
			setState(419);
			((E_mulContext)_localctx).mul_tail = mul_tail(((E_mulContext)_localctx).e_prefix.ast);
			 ((E_mulContext)_localctx).ast =  bind(((E_mulContext)_localctx).mul_tail.ast, _localctx); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Mul_tailContext extends ParserRuleContext {
		public AST.Expr expr;
		public AST.Expr ast;
		public AST.Expr next;
		public Mul_opContext mul_op;
		public E_prefixContext e_prefix;
		public Mul_tailContext mul_tail;
		public Mul_opContext mul_op() {
			return getRuleContext(Mul_opContext.class,0);
		}
		public E_prefixContext e_prefix() {
			return getRuleContext(E_prefixContext.class,0);
		}
		public Mul_tailContext mul_tail() {
			return getRuleContext(Mul_tailContext.class,0);
		}
		public Mul_tailContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public Mul_tailContext(ParserRuleContext parent, int invokingState, AST.Expr expr) {
			super(parent, invokingState);
			this.expr = expr;
		}
		@Override public int getRuleIndex() { return RULE_mul_tail; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitMul_tail(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Mul_tailContext mul_tail(AST.Expr expr) throws RecognitionException {
		Mul_tailContext _localctx = new Mul_tailContext(_ctx, getState(), expr);
		enterRule(_localctx, 80, RULE_mul_tail);
		try {
			setState(429);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case MOD:
			case DIV:
			case MUL:
				enterOuterAlt(_localctx, 1);
				{
				setState(422);
				((Mul_tailContext)_localctx).mul_op = mul_op();
				setState(423);
				((Mul_tailContext)_localctx).e_prefix = e_prefix();

				          ((Mul_tailContext)_localctx).next =  bind(new AST.BinExpr(((Mul_tailContext)_localctx).mul_op.oper, _localctx.expr, ((Mul_tailContext)_localctx).e_prefix.ast), _localctx.expr, ((Mul_tailContext)_localctx).e_prefix.ast);
				      
				setState(425);
				((Mul_tailContext)_localctx).mul_tail = mul_tail(_localctx.next);
				 ((Mul_tailContext)_localctx).ast =  ((Mul_tailContext)_localctx).mul_tail.ast; 
				}
				break;
			case EOF:
			case AND:
			case AS:
			case DO:
			case ELSE:
			case END:
			case FUN:
			case IN:
			case OR:
			case THEN:
			case TYP:
			case VAR:
			case RSB:
			case RP:
			case LE:
			case GE:
			case NE:
			case EQ:
			case LT:
			case GT:
			case SUB:
			case ADD:
			case IS:
			case C:
				enterOuterAlt(_localctx, 2);
				{
				 ((Mul_tailContext)_localctx).ast =  _localctx.expr; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Mul_opContext extends ParserRuleContext {
		public AST.BinExpr.Oper oper;
		public TerminalNode MUL() { return getToken(Prev26Parser.MUL, 0); }
		public TerminalNode DIV() { return getToken(Prev26Parser.DIV, 0); }
		public TerminalNode MOD() { return getToken(Prev26Parser.MOD, 0); }
		public Mul_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mul_op; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitMul_op(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Mul_opContext mul_op() throws RecognitionException {
		Mul_opContext _localctx = new Mul_opContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_mul_op);
		try {
			setState(437);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case MUL:
				enterOuterAlt(_localctx, 1);
				{
				setState(431);
				match(MUL);
				 ((Mul_opContext)_localctx).oper =  AST.BinExpr.Oper.MUL; 
				}
				break;
			case DIV:
				enterOuterAlt(_localctx, 2);
				{
				setState(433);
				match(DIV);
				 ((Mul_opContext)_localctx).oper =  AST.BinExpr.Oper.DIV; 
				}
				break;
			case MOD:
				enterOuterAlt(_localctx, 3);
				{
				setState(435);
				match(MOD);
				 ((Mul_opContext)_localctx).oper =  AST.BinExpr.Oper.MOD; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class E_prefixContext extends ParserRuleContext {
		public AST.Expr ast;
		public Prefix_opContext prefix_op;
		public E_prefixContext e_prefix;
		public E_postfixContext e_postfix;
		public Prefix_opContext prefix_op() {
			return getRuleContext(Prefix_opContext.class,0);
		}
		public E_prefixContext e_prefix() {
			return getRuleContext(E_prefixContext.class,0);
		}
		public E_postfixContext e_postfix() {
			return getRuleContext(E_postfixContext.class,0);
		}
		public E_prefixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_e_prefix; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitE_prefix(this);
			else return visitor.visitChildren(this);
		}
	}

	public final E_prefixContext e_prefix() throws RecognitionException {
		E_prefixContext _localctx = new E_prefixContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_e_prefix);
		try {
			setState(446);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CONST:
			case NOT:
			case POWER:
			case SUB:
			case ADD:
				enterOuterAlt(_localctx, 1);
				{
				setState(439);
				((E_prefixContext)_localctx).prefix_op = prefix_op();
				setState(440);
				((E_prefixContext)_localctx).e_prefix = e_prefix();
				 ((E_prefixContext)_localctx).ast =  bind(new AST.PfxExpr(((E_prefixContext)_localctx).prefix_op.oper, ((E_prefixContext)_localctx).e_prefix.ast), _localctx); 
				}
				break;
			case FALSE:
			case IF:
			case LET:
			case NIL:
			case NONE:
			case SIZEOF:
			case TRUE:
			case WHILE:
			case LP:
			case NAME:
			case CINT:
			case CCHAR:
			case CSTRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(443);
				((E_prefixContext)_localctx).e_postfix = e_postfix();
				 ((E_prefixContext)_localctx).ast =  bind(((E_prefixContext)_localctx).e_postfix.ast, _localctx); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Prefix_opContext extends ParserRuleContext {
		public AST.PfxExpr.Oper oper;
		public TerminalNode NOT() { return getToken(Prev26Parser.NOT, 0); }
		public TerminalNode ADD() { return getToken(Prev26Parser.ADD, 0); }
		public TerminalNode SUB() { return getToken(Prev26Parser.SUB, 0); }
		public TerminalNode POWER() { return getToken(Prev26Parser.POWER, 0); }
		public TerminalNode CONST() { return getToken(Prev26Parser.CONST, 0); }
		public Prefix_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prefix_op; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitPrefix_op(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Prefix_opContext prefix_op() throws RecognitionException {
		Prefix_opContext _localctx = new Prefix_opContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_prefix_op);
		try {
			setState(458);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NOT:
				enterOuterAlt(_localctx, 1);
				{
				setState(448);
				match(NOT);
				 ((Prefix_opContext)_localctx).oper =  AST.PfxExpr.Oper.NOT; 
				}
				break;
			case ADD:
				enterOuterAlt(_localctx, 2);
				{
				setState(450);
				match(ADD);
				 ((Prefix_opContext)_localctx).oper =  AST.PfxExpr.Oper.ADD; 
				}
				break;
			case SUB:
				enterOuterAlt(_localctx, 3);
				{
				setState(452);
				match(SUB);
				 ((Prefix_opContext)_localctx).oper =  AST.PfxExpr.Oper.SUB; 
				}
				break;
			case POWER:
				enterOuterAlt(_localctx, 4);
				{
				setState(454);
				match(POWER);
				 ((Prefix_opContext)_localctx).oper =  AST.PfxExpr.Oper.PTR; 
				}
				break;
			case CONST:
				enterOuterAlt(_localctx, 5);
				{
				setState(456);
				match(CONST);
				 ((Prefix_opContext)_localctx).oper =  AST.PfxExpr.Oper.CONST; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class E_postfixContext extends ParserRuleContext {
		public AST.Expr ast;
		public E_basicContext e_basic;
		public Postfix_tailContext postfix_tail;
		public E_basicContext e_basic() {
			return getRuleContext(E_basicContext.class,0);
		}
		public Postfix_tailContext postfix_tail() {
			return getRuleContext(Postfix_tailContext.class,0);
		}
		public E_postfixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_e_postfix; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitE_postfix(this);
			else return visitor.visitChildren(this);
		}
	}

	public final E_postfixContext e_postfix() throws RecognitionException {
		E_postfixContext _localctx = new E_postfixContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_e_postfix);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(460);
			((E_postfixContext)_localctx).e_basic = e_basic();
			setState(461);
			((E_postfixContext)_localctx).postfix_tail = postfix_tail(((E_postfixContext)_localctx).e_basic.ast);
			 ((E_postfixContext)_localctx).ast =  bind(((E_postfixContext)_localctx).postfix_tail.ast, _localctx); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Postfix_tailContext extends ParserRuleContext {
		public AST.Expr expr;
		public AST.Expr ast;
		public AST.Expr next;
		public Token n;
		public Postfix_tailContext postfix_tail;
		public Token p;
		public EContext e;
		public Token r;
		public Empty_expr_listContext empty_expr_list;
		public TerminalNode D() { return getToken(Prev26Parser.D, 0); }
		public Postfix_tailContext postfix_tail() {
			return getRuleContext(Postfix_tailContext.class,0);
		}
		public TerminalNode NAME() { return getToken(Prev26Parser.NAME, 0); }
		public TerminalNode POWER() { return getToken(Prev26Parser.POWER, 0); }
		public TerminalNode LSB() { return getToken(Prev26Parser.LSB, 0); }
		public EContext e() {
			return getRuleContext(EContext.class,0);
		}
		public TerminalNode RSB() { return getToken(Prev26Parser.RSB, 0); }
		public TerminalNode LP() { return getToken(Prev26Parser.LP, 0); }
		public Empty_expr_listContext empty_expr_list() {
			return getRuleContext(Empty_expr_listContext.class,0);
		}
		public TerminalNode RP() { return getToken(Prev26Parser.RP, 0); }
		public Postfix_tailContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public Postfix_tailContext(ParserRuleContext parent, int invokingState, AST.Expr expr) {
			super(parent, invokingState);
			this.expr = expr;
		}
		@Override public int getRuleIndex() { return RULE_postfix_tail; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitPostfix_tail(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Postfix_tailContext postfix_tail(AST.Expr expr) throws RecognitionException {
		Postfix_tailContext _localctx = new Postfix_tailContext(_ctx, getState(), expr);
		enterRule(_localctx, 90, RULE_postfix_tail);
		try {
			setState(490);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case D:
				enterOuterAlt(_localctx, 1);
				{
				setState(464);
				match(D);
				setState(465);
				((Postfix_tailContext)_localctx).n = match(NAME);

				          ((Postfix_tailContext)_localctx).next =  bind(new AST.CompExpr(_localctx.expr, ((Postfix_tailContext)_localctx).n.getText()), _localctx.expr, ((Postfix_tailContext)_localctx).n);
				      
				setState(467);
				((Postfix_tailContext)_localctx).postfix_tail = postfix_tail(_localctx.next);
				 ((Postfix_tailContext)_localctx).ast =  ((Postfix_tailContext)_localctx).postfix_tail.ast; 
				}
				break;
			case POWER:
				enterOuterAlt(_localctx, 2);
				{
				setState(470);
				((Postfix_tailContext)_localctx).p = match(POWER);

				          ((Postfix_tailContext)_localctx).next =  bind(new AST.SfxExpr(AST.SfxExpr.Oper.PTR, _localctx.expr), _localctx.expr, ((Postfix_tailContext)_localctx).p);
				      
				setState(472);
				((Postfix_tailContext)_localctx).postfix_tail = postfix_tail(_localctx.next);
				 ((Postfix_tailContext)_localctx).ast =  ((Postfix_tailContext)_localctx).postfix_tail.ast; 
				}
				break;
			case LSB:
				enterOuterAlt(_localctx, 3);
				{
				setState(475);
				match(LSB);
				setState(476);
				((Postfix_tailContext)_localctx).e = e();
				setState(477);
				((Postfix_tailContext)_localctx).r = match(RSB);

				          ((Postfix_tailContext)_localctx).next =  bind(new AST.ArrExpr(_localctx.expr, ((Postfix_tailContext)_localctx).e.ast), _localctx.expr, ((Postfix_tailContext)_localctx).r);
				      
				setState(479);
				((Postfix_tailContext)_localctx).postfix_tail = postfix_tail(_localctx.next);
				 ((Postfix_tailContext)_localctx).ast =  ((Postfix_tailContext)_localctx).postfix_tail.ast; 
				}
				break;
			case LP:
				enterOuterAlt(_localctx, 4);
				{
				setState(482);
				match(LP);
				setState(483);
				((Postfix_tailContext)_localctx).empty_expr_list = empty_expr_list();
				setState(484);
				((Postfix_tailContext)_localctx).r = match(RP);

				          ((Postfix_tailContext)_localctx).next =  bind(new AST.CallExpr(_localctx.expr, ((Postfix_tailContext)_localctx).empty_expr_list.ast), _localctx.expr, ((Postfix_tailContext)_localctx).r);
				      
				setState(486);
				((Postfix_tailContext)_localctx).postfix_tail = postfix_tail(_localctx.next);
				 ((Postfix_tailContext)_localctx).ast =  ((Postfix_tailContext)_localctx).postfix_tail.ast; 
				}
				break;
			case EOF:
			case AND:
			case AS:
			case DO:
			case ELSE:
			case END:
			case FUN:
			case IN:
			case OR:
			case THEN:
			case TYP:
			case VAR:
			case RSB:
			case RP:
			case LE:
			case GE:
			case NE:
			case EQ:
			case LT:
			case GT:
			case MOD:
			case DIV:
			case MUL:
			case SUB:
			case ADD:
			case IS:
			case C:
				enterOuterAlt(_localctx, 5);
				{
				 ((Postfix_tailContext)_localctx).ast =  _localctx.expr; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Empty_expr_listContext extends ParserRuleContext {
		public AST.Nodes<AST.Expr> ast;
		public Expr_listContext expr_list;
		public Expr_listContext expr_list() {
			return getRuleContext(Expr_listContext.class,0);
		}
		public Empty_expr_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_empty_expr_list; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitEmpty_expr_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Empty_expr_listContext empty_expr_list() throws RecognitionException {
		Empty_expr_listContext _localctx = new Empty_expr_listContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_empty_expr_list);
		try {
			setState(496);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CONST:
			case FALSE:
			case IF:
			case LET:
			case NIL:
			case NONE:
			case NOT:
			case SIZEOF:
			case TRUE:
			case WHILE:
			case POWER:
			case LP:
			case SUB:
			case ADD:
			case NAME:
			case CINT:
			case CCHAR:
			case CSTRING:
				enterOuterAlt(_localctx, 1);
				{
				setState(492);
				((Empty_expr_listContext)_localctx).expr_list = expr_list();
				 ((Empty_expr_listContext)_localctx).ast =  bind(new AST.Nodes<>(((Empty_expr_listContext)_localctx).expr_list.exprs), ((Empty_expr_listContext)_localctx).expr_list); 
				}
				break;
			case RP:
				enterOuterAlt(_localctx, 2);
				{
				 ((Empty_expr_listContext)_localctx).ast =  bind(new AST.Nodes<>(new ArrayList<AST.Expr>()), _localctx); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class E_basicContext extends ParserRuleContext {
		public AST.Expr ast;
		public Token n;
		public E_constContext e_const;
		public E_sizeofContext e_sizeof;
		public E_ifContext e_if;
		public E_whileContext e_while;
		public E_letContext e_let;
		public Expr_listContext expr_list;
		public TerminalNode NAME() { return getToken(Prev26Parser.NAME, 0); }
		public E_constContext e_const() {
			return getRuleContext(E_constContext.class,0);
		}
		public E_sizeofContext e_sizeof() {
			return getRuleContext(E_sizeofContext.class,0);
		}
		public E_ifContext e_if() {
			return getRuleContext(E_ifContext.class,0);
		}
		public E_whileContext e_while() {
			return getRuleContext(E_whileContext.class,0);
		}
		public E_letContext e_let() {
			return getRuleContext(E_letContext.class,0);
		}
		public TerminalNode LP() { return getToken(Prev26Parser.LP, 0); }
		public Expr_listContext expr_list() {
			return getRuleContext(Expr_listContext.class,0);
		}
		public TerminalNode RP() { return getToken(Prev26Parser.RP, 0); }
		public E_basicContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_e_basic; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitE_basic(this);
			else return visitor.visitChildren(this);
		}
	}

	public final E_basicContext e_basic() throws RecognitionException {
		E_basicContext _localctx = new E_basicContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_e_basic);
		try {
			setState(520);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(498);
				((E_basicContext)_localctx).n = match(NAME);
				 ((E_basicContext)_localctx).ast =  bind(new AST.NameExpr(((E_basicContext)_localctx).n.getText()), _localctx); 
				}
				break;
			case FALSE:
			case NIL:
			case NONE:
			case TRUE:
			case CINT:
			case CCHAR:
			case CSTRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(500);
				((E_basicContext)_localctx).e_const = e_const();
				 ((E_basicContext)_localctx).ast =  ((E_basicContext)_localctx).e_const.ast; 
				}
				break;
			case SIZEOF:
				enterOuterAlt(_localctx, 3);
				{
				setState(503);
				((E_basicContext)_localctx).e_sizeof = e_sizeof();
				 ((E_basicContext)_localctx).ast =  ((E_basicContext)_localctx).e_sizeof.ast; 
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 4);
				{
				setState(506);
				((E_basicContext)_localctx).e_if = e_if();
				 ((E_basicContext)_localctx).ast =  ((E_basicContext)_localctx).e_if.ast; 
				}
				break;
			case WHILE:
				enterOuterAlt(_localctx, 5);
				{
				setState(509);
				((E_basicContext)_localctx).e_while = e_while();
				 ((E_basicContext)_localctx).ast =  ((E_basicContext)_localctx).e_while.ast; 
				}
				break;
			case LET:
				enterOuterAlt(_localctx, 6);
				{
				setState(512);
				((E_basicContext)_localctx).e_let = e_let();
				 ((E_basicContext)_localctx).ast =  ((E_basicContext)_localctx).e_let.ast; 
				}
				break;
			case LP:
				enterOuterAlt(_localctx, 7);
				{
				setState(515);
				match(LP);
				setState(516);
				((E_basicContext)_localctx).expr_list = expr_list();
				setState(517);
				match(RP);
				 ((E_basicContext)_localctx).ast =  bind(packExprs(((E_basicContext)_localctx).expr_list.exprs, ((E_basicContext)_localctx).expr_list), _localctx); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class E_constContext extends ParserRuleContext {
		public AST.AtomExpr ast;
		public Token c;
		public Bool_constContext bool_const;
		public Void_constContext void_const;
		public Ptr_constContext ptr_const;
		public TerminalNode CINT() { return getToken(Prev26Parser.CINT, 0); }
		public Bool_constContext bool_const() {
			return getRuleContext(Bool_constContext.class,0);
		}
		public TerminalNode CCHAR() { return getToken(Prev26Parser.CCHAR, 0); }
		public TerminalNode CSTRING() { return getToken(Prev26Parser.CSTRING, 0); }
		public Void_constContext void_const() {
			return getRuleContext(Void_constContext.class,0);
		}
		public Ptr_constContext ptr_const() {
			return getRuleContext(Ptr_constContext.class,0);
		}
		public E_constContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_e_const; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitE_const(this);
			else return visitor.visitChildren(this);
		}
	}

	public final E_constContext e_const() throws RecognitionException {
		E_constContext _localctx = new E_constContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_e_const);
		try {
			setState(537);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CINT:
				enterOuterAlt(_localctx, 1);
				{
				setState(522);
				((E_constContext)_localctx).c = match(CINT);
				 ((E_constContext)_localctx).ast =  bind(new AST.AtomExpr(AST.AtomExpr.Type.INT, ((E_constContext)_localctx).c.getText()), _localctx); 
				}
				break;
			case FALSE:
			case TRUE:
				enterOuterAlt(_localctx, 2);
				{
				setState(524);
				((E_constContext)_localctx).bool_const = bool_const();
				 ((E_constContext)_localctx).ast =  ((E_constContext)_localctx).bool_const.ast; 
				}
				break;
			case CCHAR:
				enterOuterAlt(_localctx, 3);
				{
				setState(527);
				((E_constContext)_localctx).c = match(CCHAR);
				 ((E_constContext)_localctx).ast =  bind(new AST.AtomExpr(AST.AtomExpr.Type.CHAR, ((E_constContext)_localctx).c.getText()), _localctx); 
				}
				break;
			case CSTRING:
				enterOuterAlt(_localctx, 4);
				{
				setState(529);
				((E_constContext)_localctx).c = match(CSTRING);
				 ((E_constContext)_localctx).ast =  bind(new AST.AtomExpr(AST.AtomExpr.Type.STR, ((E_constContext)_localctx).c.getText()), _localctx); 
				}
				break;
			case NONE:
				enterOuterAlt(_localctx, 5);
				{
				setState(531);
				((E_constContext)_localctx).void_const = void_const();
				 ((E_constContext)_localctx).ast =  ((E_constContext)_localctx).void_const.ast; 
				}
				break;
			case NIL:
				enterOuterAlt(_localctx, 6);
				{
				setState(534);
				((E_constContext)_localctx).ptr_const = ptr_const();
				 ((E_constContext)_localctx).ast =  ((E_constContext)_localctx).ptr_const.ast; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Bool_constContext extends ParserRuleContext {
		public AST.AtomExpr ast;
		public Token TRUE;
		public Token FALSE;
		public TerminalNode TRUE() { return getToken(Prev26Parser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(Prev26Parser.FALSE, 0); }
		public Bool_constContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bool_const; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitBool_const(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Bool_constContext bool_const() throws RecognitionException {
		Bool_constContext _localctx = new Bool_constContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_bool_const);
		try {
			setState(543);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TRUE:
				enterOuterAlt(_localctx, 1);
				{
				setState(539);
				((Bool_constContext)_localctx).TRUE = match(TRUE);
				 ((Bool_constContext)_localctx).ast =  bind(new AST.AtomExpr(AST.AtomExpr.Type.BOOL, ((Bool_constContext)_localctx).TRUE.getText()), _localctx); 
				}
				break;
			case FALSE:
				enterOuterAlt(_localctx, 2);
				{
				setState(541);
				((Bool_constContext)_localctx).FALSE = match(FALSE);
				 ((Bool_constContext)_localctx).ast =  bind(new AST.AtomExpr(AST.AtomExpr.Type.BOOL, ((Bool_constContext)_localctx).FALSE.getText()), _localctx); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Void_constContext extends ParserRuleContext {
		public AST.AtomExpr ast;
		public Token NONE;
		public TerminalNode NONE() { return getToken(Prev26Parser.NONE, 0); }
		public Void_constContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_void_const; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitVoid_const(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Void_constContext void_const() throws RecognitionException {
		Void_constContext _localctx = new Void_constContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_void_const);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(545);
			((Void_constContext)_localctx).NONE = match(NONE);
			 ((Void_constContext)_localctx).ast =  bind(new AST.AtomExpr(AST.AtomExpr.Type.VOID, ((Void_constContext)_localctx).NONE.getText()), _localctx); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Ptr_constContext extends ParserRuleContext {
		public AST.AtomExpr ast;
		public Token NIL;
		public TerminalNode NIL() { return getToken(Prev26Parser.NIL, 0); }
		public Ptr_constContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ptr_const; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitPtr_const(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Ptr_constContext ptr_const() throws RecognitionException {
		Ptr_constContext _localctx = new Ptr_constContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_ptr_const);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(548);
			((Ptr_constContext)_localctx).NIL = match(NIL);
			 ((Ptr_constContext)_localctx).ast =  bind(new AST.AtomExpr(AST.AtomExpr.Type.PTR, ((Ptr_constContext)_localctx).NIL.getText()), _localctx); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class E_sizeofContext extends ParserRuleContext {
		public AST.SizeExpr ast;
		public TContext t;
		public TerminalNode SIZEOF() { return getToken(Prev26Parser.SIZEOF, 0); }
		public TContext t() {
			return getRuleContext(TContext.class,0);
		}
		public E_sizeofContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_e_sizeof; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitE_sizeof(this);
			else return visitor.visitChildren(this);
		}
	}

	public final E_sizeofContext e_sizeof() throws RecognitionException {
		E_sizeofContext _localctx = new E_sizeofContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_e_sizeof);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(551);
			match(SIZEOF);
			setState(552);
			((E_sizeofContext)_localctx).t = t();
			 ((E_sizeofContext)_localctx).ast =  bind(new AST.SizeExpr(((E_sizeofContext)_localctx).t.ast), _localctx); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class E_letContext extends ParserRuleContext {
		public AST.LetExpr ast;
		public ProgContext prog;
		public Expr_listContext expr_list;
		public TerminalNode LET() { return getToken(Prev26Parser.LET, 0); }
		public ProgContext prog() {
			return getRuleContext(ProgContext.class,0);
		}
		public TerminalNode IN() { return getToken(Prev26Parser.IN, 0); }
		public Expr_listContext expr_list() {
			return getRuleContext(Expr_listContext.class,0);
		}
		public TerminalNode END() { return getToken(Prev26Parser.END, 0); }
		public E_letContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_e_let; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitE_let(this);
			else return visitor.visitChildren(this);
		}
	}

	public final E_letContext e_let() throws RecognitionException {
		E_letContext _localctx = new E_letContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_e_let);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(555);
			match(LET);
			setState(556);
			((E_letContext)_localctx).prog = prog();
			setState(557);
			match(IN);
			setState(558);
			((E_letContext)_localctx).expr_list = expr_list();
			setState(559);
			match(END);
			 ((E_letContext)_localctx).ast =  bind(new AST.LetExpr(((E_letContext)_localctx).prog.ast, packExprs(((E_letContext)_localctx).expr_list.exprs, ((E_letContext)_localctx).expr_list)), _localctx); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class E_whileContext extends ParserRuleContext {
		public AST.WhileExpr ast;
		public EContext e;
		public Expr_listContext expr_list;
		public TerminalNode WHILE() { return getToken(Prev26Parser.WHILE, 0); }
		public EContext e() {
			return getRuleContext(EContext.class,0);
		}
		public TerminalNode DO() { return getToken(Prev26Parser.DO, 0); }
		public Expr_listContext expr_list() {
			return getRuleContext(Expr_listContext.class,0);
		}
		public TerminalNode END() { return getToken(Prev26Parser.END, 0); }
		public E_whileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_e_while; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitE_while(this);
			else return visitor.visitChildren(this);
		}
	}

	public final E_whileContext e_while() throws RecognitionException {
		E_whileContext _localctx = new E_whileContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_e_while);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(562);
			match(WHILE);
			setState(563);
			((E_whileContext)_localctx).e = e();
			setState(564);
			match(DO);
			setState(565);
			((E_whileContext)_localctx).expr_list = expr_list();
			setState(566);
			match(END);
			 ((E_whileContext)_localctx).ast =  bind(new AST.WhileExpr(((E_whileContext)_localctx).e.ast, packExprs(((E_whileContext)_localctx).expr_list.exprs, ((E_whileContext)_localctx).expr_list)), _localctx); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class E_ifContext extends ParserRuleContext {
		public AST.Expr ast;
		public EContext e;
		public Expr_listContext expr_list;
		public If_tailContext if_tail;
		public TerminalNode IF() { return getToken(Prev26Parser.IF, 0); }
		public EContext e() {
			return getRuleContext(EContext.class,0);
		}
		public TerminalNode THEN() { return getToken(Prev26Parser.THEN, 0); }
		public Expr_listContext expr_list() {
			return getRuleContext(Expr_listContext.class,0);
		}
		public If_tailContext if_tail() {
			return getRuleContext(If_tailContext.class,0);
		}
		public TerminalNode END() { return getToken(Prev26Parser.END, 0); }
		public E_ifContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_e_if; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitE_if(this);
			else return visitor.visitChildren(this);
		}
	}

	public final E_ifContext e_if() throws RecognitionException {
		E_ifContext _localctx = new E_ifContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_e_if);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(569);
			match(IF);
			setState(570);
			((E_ifContext)_localctx).e = e();
			setState(571);
			match(THEN);
			setState(572);
			((E_ifContext)_localctx).expr_list = expr_list();
			setState(573);
			((E_ifContext)_localctx).if_tail = if_tail();
			setState(574);
			match(END);

			          AST.Expr thenExpr = packExprs(((E_ifContext)_localctx).expr_list.exprs, ((E_ifContext)_localctx).expr_list);
			          if (((E_ifContext)_localctx).if_tail.ast != null)
			              ((E_ifContext)_localctx).ast =  bind(new AST.IfThenElseExpr(((E_ifContext)_localctx).e.ast, thenExpr, ((E_ifContext)_localctx).if_tail.ast), _localctx);
			          else
			              ((E_ifContext)_localctx).ast =  bind(new AST.IfThenExpr(((E_ifContext)_localctx).e.ast, thenExpr), _localctx);
			      
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class If_tailContext extends ParserRuleContext {
		public AST.Expr ast;
		public Expr_listContext expr_list;
		public TerminalNode ELSE() { return getToken(Prev26Parser.ELSE, 0); }
		public Expr_listContext expr_list() {
			return getRuleContext(Expr_listContext.class,0);
		}
		public If_tailContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_if_tail; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitIf_tail(this);
			else return visitor.visitChildren(this);
		}
	}

	public final If_tailContext if_tail() throws RecognitionException {
		If_tailContext _localctx = new If_tailContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_if_tail);
		try {
			setState(582);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ELSE:
				enterOuterAlt(_localctx, 1);
				{
				setState(577);
				match(ELSE);
				setState(578);
				((If_tailContext)_localctx).expr_list = expr_list();
				 ((If_tailContext)_localctx).ast =  packExprs(((If_tailContext)_localctx).expr_list.exprs, ((If_tailContext)_localctx).expr_list); 
				}
				break;
			case END:
				enterOuterAlt(_localctx, 2);
				{
				 ((If_tailContext)_localctx).ast =  null; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class C_intContext extends ParserRuleContext {
		public Opt_add_opContext opt_add_op() {
			return getRuleContext(Opt_add_opContext.class,0);
		}
		public TerminalNode CINT() { return getToken(Prev26Parser.CINT, 0); }
		public C_intContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_c_int; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitC_int(this);
			else return visitor.visitChildren(this);
		}
	}

	public final C_intContext c_int() throws RecognitionException {
		C_intContext _localctx = new C_intContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_c_int);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(584);
			opt_add_op();
			setState(585);
			match(CINT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Opt_add_opContext extends ParserRuleContext {
		public Add_compContext add_comp() {
			return getRuleContext(Add_compContext.class,0);
		}
		public Opt_add_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_opt_add_op; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Prev26ParserVisitor ) return ((Prev26ParserVisitor<? extends T>)visitor).visitOpt_add_op(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Opt_add_opContext opt_add_op() throws RecognitionException {
		Opt_add_opContext _localctx = new Opt_add_opContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_opt_add_op);
		try {
			setState(589);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SUB:
			case ADD:
				enterOuterAlt(_localctx, 1);
				{
				setState(587);
				add_comp();
				}
				break;
			case CINT:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u00018\u0250\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"+
		"\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007!\u0002\"\u0007\"\u0002"+
		"#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007&\u0002\'\u0007\'\u0002"+
		"(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007+\u0002,\u0007,\u0002"+
		"-\u0007-\u0002.\u0007.\u0002/\u0007/\u00020\u00070\u00021\u00071\u0002"+
		"2\u00072\u00023\u00073\u00024\u00074\u00025\u00075\u00026\u00076\u0002"+
		"7\u00077\u00028\u00078\u00029\u00079\u0002:\u0007:\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002"+
		"\u0084\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003"+
		"\u009c\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0003\u0004\u00a3\b\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0003\u0005\u00aa\b\u0005\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006\u00b2\b\u0006\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0003\b\u00cc"+
		"\b\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0003\t\u00d4\b\t"+
		"\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0003\n\u00dd"+
		"\b\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0003\u000b\u00e5\b\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0003\f\u00f8\b\f\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0003\r\u0102\b\r\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0003"+
		"\u0012\u011d\b\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001"+
		"\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0003"+
		"\u0015\u012f\b\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001"+
		"\u0016\u0001\u0016\u0003\u0016\u0137\b\u0016\u0001\u0017\u0001\u0017\u0001"+
		"\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001"+
		"\u0018\u0001\u0018\u0003\u0018\u0143\b\u0018\u0001\u0019\u0001\u0019\u0001"+
		"\u0019\u0001\u0019\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001"+
		"\u001a\u0003\u001a\u014e\b\u001a\u0001\u001b\u0001\u001b\u0001\u001b\u0001"+
		"\u001b\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001"+
		"\u001c\u0001\u001c\u0003\u001c\u015b\b\u001c\u0001\u001d\u0001\u001d\u0001"+
		"\u001d\u0001\u001d\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001"+
		"\u001e\u0001\u001e\u0001\u001e\u0003\u001e\u0168\b\u001e\u0001\u001f\u0001"+
		"\u001f\u0001\u001f\u0001\u001f\u0001 \u0001 \u0001 \u0001 \u0001 \u0001"+
		" \u0001 \u0003 \u0175\b \u0001!\u0001!\u0001!\u0001!\u0001\"\u0001\"\u0001"+
		"\"\u0001\"\u0001\"\u0003\"\u0180\b\"\u0001#\u0001#\u0001#\u0001#\u0001"+
		"#\u0001#\u0001#\u0001#\u0001#\u0001#\u0001#\u0001#\u0003#\u018e\b#\u0001"+
		"$\u0001$\u0001$\u0001$\u0001%\u0001%\u0001%\u0001%\u0001%\u0001%\u0001"+
		"%\u0003%\u019b\b%\u0001&\u0001&\u0001&\u0001&\u0003&\u01a1\b&\u0001\'"+
		"\u0001\'\u0001\'\u0001\'\u0001(\u0001(\u0001(\u0001(\u0001(\u0001(\u0001"+
		"(\u0003(\u01ae\b(\u0001)\u0001)\u0001)\u0001)\u0001)\u0001)\u0003)\u01b6"+
		"\b)\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0003*\u01bf\b*\u0001"+
		"+\u0001+\u0001+\u0001+\u0001+\u0001+\u0001+\u0001+\u0001+\u0001+\u0003"+
		"+\u01cb\b+\u0001,\u0001,\u0001,\u0001,\u0001-\u0001-\u0001-\u0001-\u0001"+
		"-\u0001-\u0001-\u0001-\u0001-\u0001-\u0001-\u0001-\u0001-\u0001-\u0001"+
		"-\u0001-\u0001-\u0001-\u0001-\u0001-\u0001-\u0001-\u0001-\u0001-\u0001"+
		"-\u0001-\u0003-\u01eb\b-\u0001.\u0001.\u0001.\u0001.\u0003.\u01f1\b.\u0001"+
		"/\u0001/\u0001/\u0001/\u0001/\u0001/\u0001/\u0001/\u0001/\u0001/\u0001"+
		"/\u0001/\u0001/\u0001/\u0001/\u0001/\u0001/\u0001/\u0001/\u0001/\u0001"+
		"/\u0001/\u0003/\u0209\b/\u00010\u00010\u00010\u00010\u00010\u00010\u0001"+
		"0\u00010\u00010\u00010\u00010\u00010\u00010\u00010\u00010\u00030\u021a"+
		"\b0\u00011\u00011\u00011\u00011\u00031\u0220\b1\u00012\u00012\u00012\u0001"+
		"3\u00013\u00013\u00014\u00014\u00014\u00014\u00015\u00015\u00015\u0001"+
		"5\u00015\u00015\u00015\u00016\u00016\u00016\u00016\u00016\u00016\u0001"+
		"6\u00017\u00017\u00017\u00017\u00017\u00017\u00017\u00017\u00018\u0001"+
		"8\u00018\u00018\u00018\u00038\u0247\b8\u00019\u00019\u00019\u0001:\u0001"+
		":\u0003:\u024e\b:\u0001:\u0000\u0000;\u0000\u0002\u0004\u0006\b\n\f\u000e"+
		"\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,.02468:<>@BDF"+
		"HJLNPRTVXZ\\^`bdfhjlnprt\u0000\u0000\u0254\u0000v\u0001\u0000\u0000\u0000"+
		"\u0002z\u0001\u0000\u0000\u0000\u0004\u0083\u0001\u0000\u0000\u0000\u0006"+
		"\u009b\u0001\u0000\u0000\u0000\b\u00a2\u0001\u0000\u0000\u0000\n\u00a9"+
		"\u0001\u0000\u0000\u0000\f\u00b1\u0001\u0000\u0000\u0000\u000e\u00b3\u0001"+
		"\u0000\u0000\u0000\u0010\u00cb\u0001\u0000\u0000\u0000\u0012\u00d3\u0001"+
		"\u0000\u0000\u0000\u0014\u00dc\u0001\u0000\u0000\u0000\u0016\u00e4\u0001"+
		"\u0000\u0000\u0000\u0018\u00f7\u0001\u0000\u0000\u0000\u001a\u0101\u0001"+
		"\u0000\u0000\u0000\u001c\u0103\u0001\u0000\u0000\u0000\u001e\u0109\u0001"+
		"\u0000\u0000\u0000 \u010d\u0001\u0000\u0000\u0000\"\u0112\u0001\u0000"+
		"\u0000\u0000$\u011c\u0001\u0000\u0000\u0000&\u011e\u0001\u0000\u0000\u0000"+
		"(\u0123\u0001\u0000\u0000\u0000*\u012e\u0001\u0000\u0000\u0000,\u0136"+
		"\u0001\u0000\u0000\u0000.\u0138\u0001\u0000\u0000\u00000\u0142\u0001\u0000"+
		"\u0000\u00002\u0144\u0001\u0000\u0000\u00004\u014d\u0001\u0000\u0000\u0000"+
		"6\u014f\u0001\u0000\u0000\u00008\u015a\u0001\u0000\u0000\u0000:\u015c"+
		"\u0001\u0000\u0000\u0000<\u0167\u0001\u0000\u0000\u0000>\u0169\u0001\u0000"+
		"\u0000\u0000@\u0174\u0001\u0000\u0000\u0000B\u0176\u0001\u0000\u0000\u0000"+
		"D\u017f\u0001\u0000\u0000\u0000F\u018d\u0001\u0000\u0000\u0000H\u018f"+
		"\u0001\u0000\u0000\u0000J\u019a\u0001\u0000\u0000\u0000L\u01a0\u0001\u0000"+
		"\u0000\u0000N\u01a2\u0001\u0000\u0000\u0000P\u01ad\u0001\u0000\u0000\u0000"+
		"R\u01b5\u0001\u0000\u0000\u0000T\u01be\u0001\u0000\u0000\u0000V\u01ca"+
		"\u0001\u0000\u0000\u0000X\u01cc\u0001\u0000\u0000\u0000Z\u01ea\u0001\u0000"+
		"\u0000\u0000\\\u01f0\u0001\u0000\u0000\u0000^\u0208\u0001\u0000\u0000"+
		"\u0000`\u0219\u0001\u0000\u0000\u0000b\u021f\u0001\u0000\u0000\u0000d"+
		"\u0221\u0001\u0000\u0000\u0000f\u0224\u0001\u0000\u0000\u0000h\u0227\u0001"+
		"\u0000\u0000\u0000j\u022b\u0001\u0000\u0000\u0000l\u0232\u0001\u0000\u0000"+
		"\u0000n\u0239\u0001\u0000\u0000\u0000p\u0246\u0001\u0000\u0000\u0000r"+
		"\u0248\u0001\u0000\u0000\u0000t\u024d\u0001\u0000\u0000\u0000vw\u0003"+
		"\u0002\u0001\u0000wx\u0005\u0000\u0000\u0001xy\u0006\u0000\uffff\uffff"+
		"\u0000y\u0001\u0001\u0000\u0000\u0000z{\u0003\u0006\u0003\u0000{|\u0003"+
		"\u0004\u0002\u0000|}\u0006\u0001\uffff\uffff\u0000}\u0003\u0001\u0000"+
		"\u0000\u0000~\u007f\u0003\u0006\u0003\u0000\u007f\u0080\u0003\u0004\u0002"+
		"\u0000\u0080\u0081\u0006\u0002\uffff\uffff\u0000\u0081\u0084\u0001\u0000"+
		"\u0000\u0000\u0082\u0084\u0001\u0000\u0000\u0000\u0083~\u0001\u0000\u0000"+
		"\u0000\u0083\u0082\u0001\u0000\u0000\u0000\u0084\u0005\u0001\u0000\u0000"+
		"\u0000\u0085\u0086\u0005\u0016\u0000\u0000\u0086\u0087\u00052\u0000\u0000"+
		"\u0087\u0088\u0005,\u0000\u0000\u0088\u0089\u0003\u0010\b\u0000\u0089"+
		"\u008a\u0006\u0003\uffff\uffff\u0000\u008a\u009c\u0001\u0000\u0000\u0000"+
		"\u008b\u008c\u0005\u0017\u0000\u0000\u008c\u008d\u00052\u0000\u0000\u008d"+
		"\u008e\u0005-\u0000\u0000\u008e\u008f\u0003\u0010\b\u0000\u008f\u0090"+
		"\u0006\u0003\uffff\uffff\u0000\u0090\u009c\u0001\u0000\u0000\u0000\u0091"+
		"\u0092\u0005\n\u0000\u0000\u0092\u0093\u00052\u0000\u0000\u0093\u0094"+
		"\u0005\u001f\u0000\u0000\u0094\u0095\u0003\n\u0005\u0000\u0095\u0096\u0005"+
		" \u0000\u0000\u0096\u0097\u0005-\u0000\u0000\u0097\u0098\u0003\u0010\b"+
		"\u0000\u0098\u0099\u0003\b\u0004\u0000\u0099\u009a\u0006\u0003\uffff\uffff"+
		"\u0000\u009a\u009c\u0001\u0000\u0000\u0000\u009b\u0085\u0001\u0000\u0000"+
		"\u0000\u009b\u008b\u0001\u0000\u0000\u0000\u009b\u0091\u0001\u0000\u0000"+
		"\u0000\u009c\u0007\u0001\u0000\u0000\u0000\u009d\u009e\u0005,\u0000\u0000"+
		"\u009e\u009f\u0003.\u0017\u0000\u009f\u00a0\u0006\u0004\uffff\uffff\u0000"+
		"\u00a0\u00a3\u0001\u0000\u0000\u0000\u00a1\u00a3\u0006\u0004\uffff\uffff"+
		"\u0000\u00a2\u009d\u0001\u0000\u0000\u0000\u00a2\u00a1\u0001\u0000\u0000"+
		"\u0000\u00a3\t\u0001\u0000\u0000\u0000\u00a4\u00a5\u0003\u000e\u0007\u0000"+
		"\u00a5\u00a6\u0003\f\u0006\u0000\u00a6\u00a7\u0006\u0005\uffff\uffff\u0000"+
		"\u00a7\u00aa\u0001\u0000\u0000\u0000\u00a8\u00aa\u0006\u0005\uffff\uffff"+
		"\u0000\u00a9\u00a4\u0001\u0000\u0000\u0000\u00a9\u00a8\u0001\u0000\u0000"+
		"\u0000\u00aa\u000b\u0001\u0000\u0000\u0000\u00ab\u00ac\u0005.\u0000\u0000"+
		"\u00ac\u00ad\u0003\u000e\u0007\u0000\u00ad\u00ae\u0003\f\u0006\u0000\u00ae"+
		"\u00af\u0006\u0006\uffff\uffff\u0000\u00af\u00b2\u0001\u0000\u0000\u0000"+
		"\u00b0\u00b2\u0001\u0000\u0000\u0000\u00b1\u00ab\u0001\u0000\u0000\u0000"+
		"\u00b1\u00b0\u0001\u0000\u0000\u0000\u00b2\r\u0001\u0000\u0000\u0000\u00b3"+
		"\u00b4\u00052\u0000\u0000\u00b4\u00b5\u0005-\u0000\u0000\u00b5\u00b6\u0003"+
		"\u0010\b\u0000\u00b6\u00b7\u0006\u0007\uffff\uffff\u0000\u00b7\u000f\u0001"+
		"\u0000\u0000\u0000\u00b8\u00b9\u0003\u001a\r\u0000\u00b9\u00ba\u0006\b"+
		"\uffff\uffff\u0000\u00ba\u00cc\u0001\u0000\u0000\u0000\u00bb\u00bc\u0005"+
		"2\u0000\u0000\u00bc\u00cc\u0006\b\uffff\uffff\u0000\u00bd\u00be\u0003"+
		"\u001c\u000e\u0000\u00be\u00bf\u0006\b\uffff\uffff\u0000\u00bf\u00cc\u0001"+
		"\u0000\u0000\u0000\u00c0\u00c1\u0003\u001e\u000f\u0000\u00c1\u00c2\u0006"+
		"\b\uffff\uffff\u0000\u00c2\u00cc\u0001\u0000\u0000\u0000\u00c3\u00c4\u0003"+
		" \u0010\u0000\u00c4\u00c5\u0006\b\uffff\uffff\u0000\u00c5\u00cc\u0001"+
		"\u0000\u0000\u0000\u00c6\u00c7\u0005\u001f\u0000\u0000\u00c7\u00c8\u0003"+
		"\u0012\t\u0000\u00c8\u00c9\u0005 \u0000\u0000\u00c9\u00ca\u0006\b\uffff"+
		"\uffff\u0000\u00ca\u00cc\u0001\u0000\u0000\u0000\u00cb\u00b8\u0001\u0000"+
		"\u0000\u0000\u00cb\u00bb\u0001\u0000\u0000\u0000\u00cb\u00bd\u0001\u0000"+
		"\u0000\u0000\u00cb\u00c0\u0001\u0000\u0000\u0000\u00cb\u00c3\u0001\u0000"+
		"\u0000\u0000\u00cb\u00c6\u0001\u0000\u0000\u0000\u00cc\u0011\u0001\u0000"+
		"\u0000\u0000\u00cd\u00ce\u0003\u0014\n\u0000\u00ce\u00cf\u0006\t\uffff"+
		"\uffff\u0000\u00cf\u00d4\u0001\u0000\u0000\u0000\u00d0\u00d1\u0003(\u0014"+
		"\u0000\u00d1\u00d2\u0006\t\uffff\uffff\u0000\u00d2\u00d4\u0001\u0000\u0000"+
		"\u0000\u00d3\u00cd\u0001\u0000\u0000\u0000\u00d3\u00d0\u0001\u0000\u0000"+
		"\u0000\u00d4\u0013\u0001\u0000\u0000\u0000\u00d5\u00d6\u00052\u0000\u0000"+
		"\u00d6\u00d7\u0003\u0016\u000b\u0000\u00d7\u00d8\u0006\n\uffff\uffff\u0000"+
		"\u00d8\u00dd\u0001\u0000\u0000\u0000\u00d9\u00da\u0003\u0018\f\u0000\u00da"+
		"\u00db\u0006\n\uffff\uffff\u0000\u00db\u00dd\u0001\u0000\u0000\u0000\u00dc"+
		"\u00d5\u0001\u0000\u0000\u0000\u00dc\u00d9\u0001\u0000\u0000\u0000\u00dd"+
		"\u0015\u0001\u0000\u0000\u0000\u00de\u00df\u0005-\u0000\u0000\u00df\u00e0"+
		"\u0003\u0010\b\u0000\u00e0\u00e1\u0003\f\u0006\u0000\u00e1\u00e2\u0006"+
		"\u000b\uffff\uffff\u0000\u00e2\u00e5\u0001\u0000\u0000\u0000\u00e3\u00e5"+
		"\u0006\u000b\uffff\uffff\u0000\u00e4\u00de\u0001\u0000\u0000\u0000\u00e4"+
		"\u00e3\u0001\u0000\u0000\u0000\u00e5\u0017\u0001\u0000\u0000\u0000\u00e6"+
		"\u00e7\u0003\u001a\r\u0000\u00e7\u00e8\u0006\f\uffff\uffff\u0000\u00e8"+
		"\u00f8\u0001\u0000\u0000\u0000\u00e9\u00ea\u0003\u001c\u000e\u0000\u00ea"+
		"\u00eb\u0006\f\uffff\uffff\u0000\u00eb\u00f8\u0001\u0000\u0000\u0000\u00ec"+
		"\u00ed\u0003\u001e\u000f\u0000\u00ed\u00ee\u0006\f\uffff\uffff\u0000\u00ee"+
		"\u00f8\u0001\u0000\u0000\u0000\u00ef\u00f0\u0003 \u0010\u0000\u00f0\u00f1"+
		"\u0006\f\uffff\uffff\u0000\u00f1\u00f8\u0001\u0000\u0000\u0000\u00f2\u00f3"+
		"\u0005\u001f\u0000\u0000\u00f3\u00f4\u0003\u0012\t\u0000\u00f4\u00f5\u0005"+
		" \u0000\u0000\u00f5\u00f6\u0006\f\uffff\uffff\u0000\u00f6\u00f8\u0001"+
		"\u0000\u0000\u0000\u00f7\u00e6\u0001\u0000\u0000\u0000\u00f7\u00e9\u0001"+
		"\u0000\u0000\u0000\u00f7\u00ec\u0001\u0000\u0000\u0000\u00f7\u00ef\u0001"+
		"\u0000\u0000\u0000\u00f7\u00f2\u0001\u0000\u0000\u0000\u00f8\u0019\u0001"+
		"\u0000\u0000\u0000\u00f9\u00fa\u0005\f\u0000\u0000\u00fa\u0102\u0006\r"+
		"\uffff\uffff\u0000\u00fb\u00fc\u0005\u0006\u0000\u0000\u00fc\u0102\u0006"+
		"\r\uffff\uffff\u0000\u00fd\u00fe\u0005\u0003\u0000\u0000\u00fe\u0102\u0006"+
		"\r\uffff\uffff\u0000\u00ff\u0100\u0005\u0018\u0000\u0000\u0100\u0102\u0006"+
		"\r\uffff\uffff\u0000\u0101\u00f9\u0001\u0000\u0000\u0000\u0101\u00fb\u0001"+
		"\u0000\u0000\u0000\u0101\u00fd\u0001\u0000\u0000\u0000\u0101\u00ff\u0001"+
		"\u0000\u0000\u0000\u0102\u001b\u0001\u0000\u0000\u0000\u0103\u0104\u0005"+
		"\u001d\u0000\u0000\u0104\u0105\u0003r9\u0000\u0105\u0106\u0005\u001e\u0000"+
		"\u0000\u0106\u0107\u0003\u0010\b\u0000\u0107\u0108\u0006\u000e\uffff\uffff"+
		"\u0000\u0108\u001d\u0001\u0000\u0000\u0000\u0109\u010a\u0005\u001a\u0000"+
		"\u0000\u010a\u010b\u0003\u0010\b\u0000\u010b\u010c\u0006\u000f\uffff\uffff"+
		"\u0000\u010c\u001f\u0001\u0000\u0000\u0000\u010d\u010e\u0005\u001b\u0000"+
		"\u0000\u010e\u010f\u0003\"\u0011\u0000\u010f\u0110\u0005\u001c\u0000\u0000"+
		"\u0110\u0111\u0006\u0010\uffff\uffff\u0000\u0111!\u0001\u0000\u0000\u0000"+
		"\u0112\u0113\u0003&\u0013\u0000\u0113\u0114\u0003$\u0012\u0000\u0114\u0115"+
		"\u0006\u0011\uffff\uffff\u0000\u0115#\u0001\u0000\u0000\u0000\u0116\u0117"+
		"\u0005.\u0000\u0000\u0117\u0118\u0003&\u0013\u0000\u0118\u0119\u0003$"+
		"\u0012\u0000\u0119\u011a\u0006\u0012\uffff\uffff\u0000\u011a\u011d\u0001"+
		"\u0000\u0000\u0000\u011b\u011d\u0001\u0000\u0000\u0000\u011c\u0116\u0001"+
		"\u0000\u0000\u0000\u011c\u011b\u0001\u0000\u0000\u0000\u011d%\u0001\u0000"+
		"\u0000\u0000\u011e\u011f\u00052\u0000\u0000\u011f\u0120\u0005-\u0000\u0000"+
		"\u0120\u0121\u0003\u0010\b\u0000\u0121\u0122\u0006\u0013\uffff\uffff\u0000"+
		"\u0122\'\u0001\u0000\u0000\u0000\u0123\u0124\u0005-\u0000\u0000\u0124"+
		"\u0125\u0003*\u0015\u0000\u0125\u0126\u0005-\u0000\u0000\u0126\u0127\u0003"+
		"\u0010\b\u0000\u0127\u0128\u0006\u0014\uffff\uffff\u0000\u0128)\u0001"+
		"\u0000\u0000\u0000\u0129\u012a\u0003\u0010\b\u0000\u012a\u012b\u0003,"+
		"\u0016\u0000\u012b\u012c\u0006\u0015\uffff\uffff\u0000\u012c\u012f\u0001"+
		"\u0000\u0000\u0000\u012d\u012f\u0006\u0015\uffff\uffff\u0000\u012e\u0129"+
		"\u0001\u0000\u0000\u0000\u012e\u012d\u0001\u0000\u0000\u0000\u012f+\u0001"+
		"\u0000\u0000\u0000\u0130\u0131\u0005.\u0000\u0000\u0131\u0132\u0003\u0010"+
		"\b\u0000\u0132\u0133\u0003,\u0016\u0000\u0133\u0134\u0006\u0016\uffff"+
		"\uffff\u0000\u0134\u0137\u0001\u0000\u0000\u0000\u0135\u0137\u0001\u0000"+
		"\u0000\u0000\u0136\u0130\u0001\u0000\u0000\u0000\u0136\u0135\u0001\u0000"+
		"\u0000\u0000\u0137-\u0001\u0000\u0000\u0000\u0138\u0139\u00032\u0019\u0000"+
		"\u0139\u013a\u00030\u0018\u0000\u013a\u013b\u0006\u0017\uffff\uffff\u0000"+
		"\u013b/\u0001\u0000\u0000\u0000\u013c\u013d\u0005.\u0000\u0000\u013d\u013e"+
		"\u00032\u0019\u0000\u013e\u013f\u00030\u0018\u0000\u013f\u0140\u0006\u0018"+
		"\uffff\uffff\u0000\u0140\u0143\u0001\u0000\u0000\u0000\u0141\u0143\u0001"+
		"\u0000\u0000\u0000\u0142\u013c\u0001\u0000\u0000\u0000\u0142\u0141\u0001"+
		"\u0000\u0000\u0000\u01431\u0001\u0000\u0000\u0000\u0144\u0145\u00036\u001b"+
		"\u0000\u0145\u0146\u00034\u001a\u0000\u0146\u0147\u0006\u0019\uffff\uffff"+
		"\u0000\u01473\u0001\u0000\u0000\u0000\u0148\u0149\u0005,\u0000\u0000\u0149"+
		"\u014a\u00036\u001b\u0000\u014a\u014b\u0006\u001a\uffff\uffff\u0000\u014b"+
		"\u014e\u0001\u0000\u0000\u0000\u014c\u014e\u0006\u001a\uffff\uffff\u0000"+
		"\u014d\u0148\u0001\u0000\u0000\u0000\u014d\u014c\u0001\u0000\u0000\u0000"+
		"\u014e5\u0001\u0000\u0000\u0000\u014f\u0150\u0003:\u001d\u0000\u0150\u0151"+
		"\u00038\u001c\u0000\u0151\u0152\u0006\u001b\uffff\uffff\u0000\u01527\u0001"+
		"\u0000\u0000\u0000\u0153\u0154\u0005\u0002\u0000\u0000\u0154\u0155\u0003"+
		"\u0010\b\u0000\u0155\u0156\u0006\u001c\uffff\uffff\u0000\u0156\u0157\u0003"+
		"8\u001c\u0000\u0157\u0158\u0006\u001c\uffff\uffff\u0000\u0158\u015b\u0001"+
		"\u0000\u0000\u0000\u0159\u015b\u0006\u001c\uffff\uffff\u0000\u015a\u0153"+
		"\u0001\u0000\u0000\u0000\u015a\u0159\u0001\u0000\u0000\u0000\u015b9\u0001"+
		"\u0000\u0000\u0000\u015c\u015d\u0003>\u001f\u0000\u015d\u015e\u0003<\u001e"+
		"\u0000\u015e\u015f\u0006\u001d\uffff\uffff\u0000\u015f;\u0001\u0000\u0000"+
		"\u0000\u0160\u0161\u0005\u0012\u0000\u0000\u0161\u0162\u0003>\u001f\u0000"+
		"\u0162\u0163\u0006\u001e\uffff\uffff\u0000\u0163\u0164\u0003<\u001e\u0000"+
		"\u0164\u0165\u0006\u001e\uffff\uffff\u0000\u0165\u0168\u0001\u0000\u0000"+
		"\u0000\u0166\u0168\u0006\u001e\uffff\uffff\u0000\u0167\u0160\u0001\u0000"+
		"\u0000\u0000\u0167\u0166\u0001\u0000\u0000\u0000\u0168=\u0001\u0000\u0000"+
		"\u0000\u0169\u016a\u0003B!\u0000\u016a\u016b\u0003@ \u0000\u016b\u016c"+
		"\u0006\u001f\uffff\uffff\u0000\u016c?\u0001\u0000\u0000\u0000\u016d\u016e"+
		"\u0005\u0001\u0000\u0000\u016e\u016f\u0003B!\u0000\u016f\u0170\u0006 "+
		"\uffff\uffff\u0000\u0170\u0171\u0003@ \u0000\u0171\u0172\u0006 \uffff"+
		"\uffff\u0000\u0172\u0175\u0001\u0000\u0000\u0000\u0173\u0175\u0006 \uffff"+
		"\uffff\u0000\u0174\u016d\u0001\u0000\u0000\u0000\u0174\u0173\u0001\u0000"+
		"\u0000\u0000\u0175A\u0001\u0000\u0000\u0000\u0176\u0177\u0003H$\u0000"+
		"\u0177\u0178\u0003D\"\u0000\u0178\u0179\u0006!\uffff\uffff\u0000\u0179"+
		"C\u0001\u0000\u0000\u0000\u017a\u017b\u0003F#\u0000\u017b\u017c\u0003"+
		"H$\u0000\u017c\u017d\u0006\"\uffff\uffff\u0000\u017d\u0180\u0001\u0000"+
		"\u0000\u0000\u017e\u0180\u0006\"\uffff\uffff\u0000\u017f\u017a\u0001\u0000"+
		"\u0000\u0000\u017f\u017e\u0001\u0000\u0000\u0000\u0180E\u0001\u0000\u0000"+
		"\u0000\u0181\u0182\u0005$\u0000\u0000\u0182\u018e\u0006#\uffff\uffff\u0000"+
		"\u0183\u0184\u0005#\u0000\u0000\u0184\u018e\u0006#\uffff\uffff\u0000\u0185"+
		"\u0186\u0005%\u0000\u0000\u0186\u018e\u0006#\uffff\uffff\u0000\u0187\u0188"+
		"\u0005&\u0000\u0000\u0188\u018e\u0006#\uffff\uffff\u0000\u0189\u018a\u0005"+
		"!\u0000\u0000\u018a\u018e\u0006#\uffff\uffff\u0000\u018b\u018c\u0005\""+
		"\u0000\u0000\u018c\u018e\u0006#\uffff\uffff\u0000\u018d\u0181\u0001\u0000"+
		"\u0000\u0000\u018d\u0183\u0001\u0000\u0000\u0000\u018d\u0185\u0001\u0000"+
		"\u0000\u0000\u018d\u0187\u0001\u0000\u0000\u0000\u018d\u0189\u0001\u0000"+
		"\u0000\u0000\u018d\u018b\u0001\u0000\u0000\u0000\u018eG\u0001\u0000\u0000"+
		"\u0000\u018f\u0190\u0003N\'\u0000\u0190\u0191\u0003J%\u0000\u0191\u0192"+
		"\u0006$\uffff\uffff\u0000\u0192I\u0001\u0000\u0000\u0000\u0193\u0194\u0003"+
		"L&\u0000\u0194\u0195\u0003N\'\u0000\u0195\u0196\u0006%\uffff\uffff\u0000"+
		"\u0196\u0197\u0003J%\u0000\u0197\u0198\u0006%\uffff\uffff\u0000\u0198"+
		"\u019b\u0001\u0000\u0000\u0000\u0199\u019b\u0006%\uffff\uffff\u0000\u019a"+
		"\u0193\u0001\u0000\u0000\u0000\u019a\u0199\u0001\u0000\u0000\u0000\u019b"+
		"K\u0001\u0000\u0000\u0000\u019c\u019d\u0005+\u0000\u0000\u019d\u01a1\u0006"+
		"&\uffff\uffff\u0000\u019e\u019f\u0005*\u0000\u0000\u019f\u01a1\u0006&"+
		"\uffff\uffff\u0000\u01a0\u019c\u0001\u0000\u0000\u0000\u01a0\u019e\u0001"+
		"\u0000\u0000\u0000\u01a1M\u0001\u0000\u0000\u0000\u01a2\u01a3\u0003T*"+
		"\u0000\u01a3\u01a4\u0003P(\u0000\u01a4\u01a5\u0006\'\uffff\uffff\u0000"+
		"\u01a5O\u0001\u0000\u0000\u0000\u01a6\u01a7\u0003R)\u0000\u01a7\u01a8"+
		"\u0003T*\u0000\u01a8\u01a9\u0006(\uffff\uffff\u0000\u01a9\u01aa\u0003"+
		"P(\u0000\u01aa\u01ab\u0006(\uffff\uffff\u0000\u01ab\u01ae\u0001\u0000"+
		"\u0000\u0000\u01ac\u01ae\u0006(\uffff\uffff\u0000\u01ad\u01a6\u0001\u0000"+
		"\u0000\u0000\u01ad\u01ac\u0001\u0000\u0000\u0000\u01aeQ\u0001\u0000\u0000"+
		"\u0000\u01af\u01b0\u0005)\u0000\u0000\u01b0\u01b6\u0006)\uffff\uffff\u0000"+
		"\u01b1\u01b2\u0005(\u0000\u0000\u01b2\u01b6\u0006)\uffff\uffff\u0000\u01b3"+
		"\u01b4\u0005\'\u0000\u0000\u01b4\u01b6\u0006)\uffff\uffff\u0000\u01b5"+
		"\u01af\u0001\u0000\u0000\u0000\u01b5\u01b1\u0001\u0000\u0000\u0000\u01b5"+
		"\u01b3\u0001\u0000\u0000\u0000\u01b6S\u0001\u0000\u0000\u0000\u01b7\u01b8"+
		"\u0003V+\u0000\u01b8\u01b9\u0003T*\u0000\u01b9\u01ba\u0006*\uffff\uffff"+
		"\u0000\u01ba\u01bf\u0001\u0000\u0000\u0000\u01bb\u01bc\u0003X,\u0000\u01bc"+
		"\u01bd\u0006*\uffff\uffff\u0000\u01bd\u01bf\u0001\u0000\u0000\u0000\u01be"+
		"\u01b7\u0001\u0000\u0000\u0000\u01be\u01bb\u0001\u0000\u0000\u0000\u01bf"+
		"U\u0001\u0000\u0000\u0000\u01c0\u01c1\u0005\u0011\u0000\u0000\u01c1\u01cb"+
		"\u0006+\uffff\uffff\u0000\u01c2\u01c3\u0005+\u0000\u0000\u01c3\u01cb\u0006"+
		"+\uffff\uffff\u0000\u01c4\u01c5\u0005*\u0000\u0000\u01c5\u01cb\u0006+"+
		"\uffff\uffff\u0000\u01c6\u01c7\u0005\u001a\u0000\u0000\u01c7\u01cb\u0006"+
		"+\uffff\uffff\u0000\u01c8\u01c9\u0005\u0004\u0000\u0000\u01c9\u01cb\u0006"+
		"+\uffff\uffff\u0000\u01ca\u01c0\u0001\u0000\u0000\u0000\u01ca\u01c2\u0001"+
		"\u0000\u0000\u0000\u01ca\u01c4\u0001\u0000\u0000\u0000\u01ca\u01c6\u0001"+
		"\u0000\u0000\u0000\u01ca\u01c8\u0001\u0000\u0000\u0000\u01cbW\u0001\u0000"+
		"\u0000\u0000\u01cc\u01cd\u0003^/\u0000\u01cd\u01ce\u0003Z-\u0000\u01ce"+
		"\u01cf\u0006,\uffff\uffff\u0000\u01cfY\u0001\u0000\u0000\u0000\u01d0\u01d1"+
		"\u0005/\u0000\u0000\u01d1\u01d2\u00052\u0000\u0000\u01d2\u01d3\u0006-"+
		"\uffff\uffff\u0000\u01d3\u01d4\u0003Z-\u0000\u01d4\u01d5\u0006-\uffff"+
		"\uffff\u0000\u01d5\u01eb\u0001\u0000\u0000\u0000\u01d6\u01d7\u0005\u001a"+
		"\u0000\u0000\u01d7\u01d8\u0006-\uffff\uffff\u0000\u01d8\u01d9\u0003Z-"+
		"\u0000\u01d9\u01da\u0006-\uffff\uffff\u0000\u01da\u01eb\u0001\u0000\u0000"+
		"\u0000\u01db\u01dc\u0005\u001d\u0000\u0000\u01dc\u01dd\u00032\u0019\u0000"+
		"\u01dd\u01de\u0005\u001e\u0000\u0000\u01de\u01df\u0006-\uffff\uffff\u0000"+
		"\u01df\u01e0\u0003Z-\u0000\u01e0\u01e1\u0006-\uffff\uffff\u0000\u01e1"+
		"\u01eb\u0001\u0000\u0000\u0000\u01e2\u01e3\u0005\u001f\u0000\u0000\u01e3"+
		"\u01e4\u0003\\.\u0000\u01e4\u01e5\u0005 \u0000\u0000\u01e5\u01e6\u0006"+
		"-\uffff\uffff\u0000\u01e6\u01e7\u0003Z-\u0000\u01e7\u01e8\u0006-\uffff"+
		"\uffff\u0000\u01e8\u01eb\u0001\u0000\u0000\u0000\u01e9\u01eb\u0006-\uffff"+
		"\uffff\u0000\u01ea\u01d0\u0001\u0000\u0000\u0000\u01ea\u01d6\u0001\u0000"+
		"\u0000\u0000\u01ea\u01db\u0001\u0000\u0000\u0000\u01ea\u01e2\u0001\u0000"+
		"\u0000\u0000\u01ea\u01e9\u0001\u0000\u0000\u0000\u01eb[\u0001\u0000\u0000"+
		"\u0000\u01ec\u01ed\u0003.\u0017\u0000\u01ed\u01ee\u0006.\uffff\uffff\u0000"+
		"\u01ee\u01f1\u0001\u0000\u0000\u0000\u01ef\u01f1\u0006.\uffff\uffff\u0000"+
		"\u01f0\u01ec\u0001\u0000\u0000\u0000\u01f0\u01ef\u0001\u0000\u0000\u0000"+
		"\u01f1]\u0001\u0000\u0000\u0000\u01f2\u01f3\u00052\u0000\u0000\u01f3\u0209"+
		"\u0006/\uffff\uffff\u0000\u01f4\u01f5\u0003`0\u0000\u01f5\u01f6\u0006"+
		"/\uffff\uffff\u0000\u01f6\u0209\u0001\u0000\u0000\u0000\u01f7\u01f8\u0003"+
		"h4\u0000\u01f8\u01f9\u0006/\uffff\uffff\u0000\u01f9\u0209\u0001\u0000"+
		"\u0000\u0000\u01fa\u01fb\u0003n7\u0000\u01fb\u01fc\u0006/\uffff\uffff"+
		"\u0000\u01fc\u0209\u0001\u0000\u0000\u0000\u01fd\u01fe\u0003l6\u0000\u01fe"+
		"\u01ff\u0006/\uffff\uffff\u0000\u01ff\u0209\u0001\u0000\u0000\u0000\u0200"+
		"\u0201\u0003j5\u0000\u0201\u0202\u0006/\uffff\uffff\u0000\u0202\u0209"+
		"\u0001\u0000\u0000\u0000\u0203\u0204\u0005\u001f\u0000\u0000\u0204\u0205"+
		"\u0003.\u0017\u0000\u0205\u0206\u0005 \u0000\u0000\u0206\u0207\u0006/"+
		"\uffff\uffff\u0000\u0207\u0209\u0001\u0000\u0000\u0000\u0208\u01f2\u0001"+
		"\u0000\u0000\u0000\u0208\u01f4\u0001\u0000\u0000\u0000\u0208\u01f7\u0001"+
		"\u0000\u0000\u0000\u0208\u01fa\u0001\u0000\u0000\u0000\u0208\u01fd\u0001"+
		"\u0000\u0000\u0000\u0208\u0200\u0001\u0000\u0000\u0000\u0208\u0203\u0001"+
		"\u0000\u0000\u0000\u0209_\u0001\u0000\u0000\u0000\u020a\u020b\u00053\u0000"+
		"\u0000\u020b\u021a\u00060\uffff\uffff\u0000\u020c\u020d\u0003b1\u0000"+
		"\u020d\u020e\u00060\uffff\uffff\u0000\u020e\u021a\u0001\u0000\u0000\u0000"+
		"\u020f\u0210\u00054\u0000\u0000\u0210\u021a\u00060\uffff\uffff\u0000\u0211"+
		"\u0212\u00056\u0000\u0000\u0212\u021a\u00060\uffff\uffff\u0000\u0213\u0214"+
		"\u0003d2\u0000\u0214\u0215\u00060\uffff\uffff\u0000\u0215\u021a\u0001"+
		"\u0000\u0000\u0000\u0216\u0217\u0003f3\u0000\u0217\u0218\u00060\uffff"+
		"\uffff\u0000\u0218\u021a\u0001\u0000\u0000\u0000\u0219\u020a\u0001\u0000"+
		"\u0000\u0000\u0219\u020c\u0001\u0000\u0000\u0000\u0219\u020f\u0001\u0000"+
		"\u0000\u0000\u0219\u0211\u0001\u0000\u0000\u0000\u0219\u0213\u0001\u0000"+
		"\u0000\u0000\u0219\u0216\u0001\u0000\u0000\u0000\u021aa\u0001\u0000\u0000"+
		"\u0000\u021b\u021c\u0005\u0015\u0000\u0000\u021c\u0220\u00061\uffff\uffff"+
		"\u0000\u021d\u021e\u0005\t\u0000\u0000\u021e\u0220\u00061\uffff\uffff"+
		"\u0000\u021f\u021b\u0001\u0000\u0000\u0000\u021f\u021d\u0001\u0000\u0000"+
		"\u0000\u0220c\u0001\u0000\u0000\u0000\u0221\u0222\u0005\u0010\u0000\u0000"+
		"\u0222\u0223\u00062\uffff\uffff\u0000\u0223e\u0001\u0000\u0000\u0000\u0224"+
		"\u0225\u0005\u000f\u0000\u0000\u0225\u0226\u00063\uffff\uffff\u0000\u0226"+
		"g\u0001\u0000\u0000\u0000\u0227\u0228\u0005\u0013\u0000\u0000\u0228\u0229"+
		"\u0003\u0010\b\u0000\u0229\u022a\u00064\uffff\uffff\u0000\u022ai\u0001"+
		"\u0000\u0000\u0000\u022b\u022c\u0005\u000e\u0000\u0000\u022c\u022d\u0003"+
		"\u0002\u0001\u0000\u022d\u022e\u0005\r\u0000\u0000\u022e\u022f\u0003."+
		"\u0017\u0000\u022f\u0230\u0005\b\u0000\u0000\u0230\u0231\u00065\uffff"+
		"\uffff\u0000\u0231k\u0001\u0000\u0000\u0000\u0232\u0233\u0005\u0019\u0000"+
		"\u0000\u0233\u0234\u00032\u0019\u0000\u0234\u0235\u0005\u0005\u0000\u0000"+
		"\u0235\u0236\u0003.\u0017\u0000\u0236\u0237\u0005\b\u0000\u0000\u0237"+
		"\u0238\u00066\uffff\uffff\u0000\u0238m\u0001\u0000\u0000\u0000\u0239\u023a"+
		"\u0005\u000b\u0000\u0000\u023a\u023b\u00032\u0019\u0000\u023b\u023c\u0005"+
		"\u0014\u0000\u0000\u023c\u023d\u0003.\u0017\u0000\u023d\u023e\u0003p8"+
		"\u0000\u023e\u023f\u0005\b\u0000\u0000\u023f\u0240\u00067\uffff\uffff"+
		"\u0000\u0240o\u0001\u0000\u0000\u0000\u0241\u0242\u0005\u0007\u0000\u0000"+
		"\u0242\u0243\u0003.\u0017\u0000\u0243\u0244\u00068\uffff\uffff\u0000\u0244"+
		"\u0247\u0001\u0000\u0000\u0000\u0245\u0247\u00068\uffff\uffff\u0000\u0246"+
		"\u0241\u0001\u0000\u0000\u0000\u0246\u0245\u0001\u0000\u0000\u0000\u0247"+
		"q\u0001\u0000\u0000\u0000\u0248\u0249\u0003t:\u0000\u0249\u024a\u0005"+
		"3\u0000\u0000\u024as\u0001\u0000\u0000\u0000\u024b\u024e\u0003L&\u0000"+
		"\u024c\u024e\u0001\u0000\u0000\u0000\u024d\u024b\u0001\u0000\u0000\u0000"+
		"\u024d\u024c\u0001\u0000\u0000\u0000\u024eu\u0001\u0000\u0000\u0000\""+
		"\u0083\u009b\u00a2\u00a9\u00b1\u00cb\u00d3\u00dc\u00e4\u00f7\u0101\u011c"+
		"\u012e\u0136\u0142\u014d\u015a\u0167\u0174\u017f\u018d\u019a\u01a0\u01ad"+
		"\u01b5\u01be\u01ca\u01ea\u01f0\u0208\u0219\u021f\u0246\u024d";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}