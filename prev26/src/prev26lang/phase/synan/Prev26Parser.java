// Generated from Prev26Parser.g4 by ANTLR 4.13.2


    package prev26lang.phase.synan;
    
    import java.util.*;
    import prev26lang.common.report.*;
    import prev26lang.phase.lexan.*;


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
		AND=1, AS=2, BOOL=3, DO=4, CHAR=5, ELSE=6, END=7, FALSE=8, FUN=9, IF=10, 
		INT=11, IN=12, LET=13, NIL=14, NONE=15, NOT=16, OR=17, SIZEOF=18, THEN=19, 
		TRUE=20, TYP=21, VAR=22, VOID=23, WHILE=24, POWER=25, LB=26, RB=27, LSB=28, 
		RSB=29, LP=30, RP=31, LE=32, GE=33, NE=34, EQ=35, LT=36, GT=37, MOD=38, 
		DIV=39, MUL=40, SUB=41, ADD=42, IS=43, DD=44, C=45, D=46, COMMENT=47, 
		WS=48, NAME=49, CINT=50, CCHAR=51, INVALID_CHAR=52, CSTRING=53, INVALID_STRING=54, 
		ANY=55;
	public static final int
		RULE_source = 0, RULE_prog = 1, RULE_prog_tail = 2, RULE_d = 3, RULE_fun_tail = 4, 
		RULE_params = 5, RULE_param_tail = 6, RULE_param = 7, RULE_t = 8, RULE_par_t = 9, 
		RULE_par_t_parse_NAME = 10, RULE_par_after_NAME = 11, RULE_nonNAME_t = 12, 
		RULE_basic_t = 13, RULE_array_t = 14, RULE_pointer_t = 15, RULE_object_t = 16, 
		RULE_fun_t = 17, RULE_t_list = 18, RULE_t_list_tail = 19, RULE_expr_list = 20, 
		RULE_e_tail = 21, RULE_e = 22, RULE_e_assign = 23, RULE_e_as = 24, RULE_as_tail = 25, 
		RULE_e_or = 26, RULE_or_tail = 27, RULE_e_and = 28, RULE_and_tail = 29, 
		RULE_e_comp = 30, RULE_comp_tail = 31, RULE_comp_op = 32, RULE_e_add = 33, 
		RULE_add_tail = 34, RULE_add_comp = 35, RULE_e_mul = 36, RULE_mul_tail = 37, 
		RULE_mul_op = 38, RULE_e_prefix = 39, RULE_prefix_op = 40, RULE_e_postfix = 41, 
		RULE_postfix_tail = 42, RULE_e_basic = 43, RULE_e_const = 44, RULE_bool_const = 45, 
		RULE_void_const = 46, RULE_ptr_const = 47, RULE_e_sizeof = 48, RULE_e_let = 49, 
		RULE_e_while = 50, RULE_e_if = 51, RULE_if_tail = 52;
	private static String[] makeRuleNames() {
		return new String[] {
			"source", "prog", "prog_tail", "d", "fun_tail", "params", "param_tail", 
			"param", "t", "par_t", "par_t_parse_NAME", "par_after_NAME", "nonNAME_t", 
			"basic_t", "array_t", "pointer_t", "object_t", "fun_t", "t_list", "t_list_tail", 
			"expr_list", "e_tail", "e", "e_assign", "e_as", "as_tail", "e_or", "or_tail", 
			"e_and", "and_tail", "e_comp", "comp_tail", "comp_op", "e_add", "add_tail", 
			"add_comp", "e_mul", "mul_tail", "mul_op", "e_prefix", "prefix_op", "e_postfix", 
			"postfix_tail", "e_basic", "e_const", "bool_const", "void_const", "ptr_const", 
			"e_sizeof", "e_let", "e_while", "e_if", "if_tail"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'and'", "'as'", "'bool'", "'do'", "'char'", "'else'", "'end'", 
			"'false'", "'fun'", "'if'", "'int'", "'in'", "'let'", "'nil'", "'none'", 
			"'not'", "'or'", "'sizeof'", "'then'", "'true'", "'typ'", "'var'", "'void'", 
			"'while'", "'^'", "'{'", "'}'", "'['", "']'", "'('", "')'", "'<='", "'>='", 
			"'!='", "'=='", "'<'", "'>'", "'%'", "'/'", "'*'", "'-'", "'+'", "'='", 
			"':'", "','", "'.'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "AND", "AS", "BOOL", "DO", "CHAR", "ELSE", "END", "FALSE", "FUN", 
			"IF", "INT", "IN", "LET", "NIL", "NONE", "NOT", "OR", "SIZEOF", "THEN", 
			"TRUE", "TYP", "VAR", "VOID", "WHILE", "POWER", "LB", "RB", "LSB", "RSB", 
			"LP", "RP", "LE", "GE", "NE", "EQ", "LT", "GT", "MOD", "DIV", "MUL", 
			"SUB", "ADD", "IS", "DD", "C", "D", "COMMENT", "WS", "NAME", "CINT", 
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


	public Prev26Parser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SourceContext extends ParserRuleContext {
		public ProgContext prog() {
			return getRuleContext(ProgContext.class,0);
		}
		public TerminalNode EOF() { return getToken(Prev26Parser.EOF, 0); }
		public SourceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_source; }
	}

	public final SourceContext source() throws RecognitionException {
		SourceContext _localctx = new SourceContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_source);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(106);
			prog();
			setState(107);
			match(EOF);
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
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_prog);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(109);
			d();
			setState(110);
			prog_tail();
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
	}

	public final Prog_tailContext prog_tail() throws RecognitionException {
		Prog_tailContext _localctx = new Prog_tailContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_prog_tail);
		try {
			setState(116);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FUN:
			case TYP:
			case VAR:
				enterOuterAlt(_localctx, 1);
				{
				setState(112);
				d();
				setState(113);
				prog_tail();
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
		public TerminalNode TYP() { return getToken(Prev26Parser.TYP, 0); }
		public TerminalNode NAME() { return getToken(Prev26Parser.NAME, 0); }
		public TerminalNode IS() { return getToken(Prev26Parser.IS, 0); }
		public TContext t() {
			return getRuleContext(TContext.class,0);
		}
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
	}

	public final DContext d() throws RecognitionException {
		DContext _localctx = new DContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_d);
		try {
			setState(135);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TYP:
				enterOuterAlt(_localctx, 1);
				{
				setState(118);
				match(TYP);
				setState(119);
				match(NAME);
				setState(120);
				match(IS);
				setState(121);
				t();
				}
				break;
			case VAR:
				enterOuterAlt(_localctx, 2);
				{
				setState(122);
				match(VAR);
				setState(123);
				match(NAME);
				setState(124);
				match(DD);
				setState(125);
				t();
				}
				break;
			case FUN:
				enterOuterAlt(_localctx, 3);
				{
				setState(126);
				match(FUN);
				setState(127);
				match(NAME);
				setState(128);
				match(LP);
				setState(129);
				params();
				setState(130);
				match(RP);
				setState(131);
				match(DD);
				setState(132);
				t();
				setState(133);
				fun_tail();
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
		public TerminalNode IS() { return getToken(Prev26Parser.IS, 0); }
		public Expr_listContext expr_list() {
			return getRuleContext(Expr_listContext.class,0);
		}
		public Fun_tailContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fun_tail; }
	}

	public final Fun_tailContext fun_tail() throws RecognitionException {
		Fun_tailContext _localctx = new Fun_tailContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_fun_tail);
		try {
			setState(140);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IS:
				enterOuterAlt(_localctx, 1);
				{
				setState(137);
				match(IS);
				setState(138);
				expr_list();
				}
				break;
			case EOF:
			case FUN:
			case IN:
			case TYP:
			case VAR:
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
	public static class ParamsContext extends ParserRuleContext {
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
	}

	public final ParamsContext params() throws RecognitionException {
		ParamsContext _localctx = new ParamsContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_params);
		try {
			setState(146);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(142);
				param();
				setState(143);
				param_tail();
				}
				break;
			case RB:
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
	public static class Param_tailContext extends ParserRuleContext {
		public TerminalNode C() { return getToken(Prev26Parser.C, 0); }
		public ParamContext param() {
			return getRuleContext(ParamContext.class,0);
		}
		public Param_tailContext param_tail() {
			return getRuleContext(Param_tailContext.class,0);
		}
		public Param_tailContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param_tail; }
	}

	public final Param_tailContext param_tail() throws RecognitionException {
		Param_tailContext _localctx = new Param_tailContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_param_tail);
		try {
			setState(153);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case C:
				enterOuterAlt(_localctx, 1);
				{
				setState(148);
				match(C);
				setState(149);
				param();
				setState(150);
				param_tail();
				}
				break;
			case RB:
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
		public TerminalNode NAME() { return getToken(Prev26Parser.NAME, 0); }
		public TerminalNode DD() { return getToken(Prev26Parser.DD, 0); }
		public TContext t() {
			return getRuleContext(TContext.class,0);
		}
		public ParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param; }
	}

	public final ParamContext param() throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_param);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(155);
			match(NAME);
			setState(156);
			match(DD);
			setState(157);
			t();
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
	}

	public final TContext t() throws RecognitionException {
		TContext _localctx = new TContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_t);
		try {
			setState(168);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BOOL:
			case CHAR:
			case INT:
			case VOID:
				enterOuterAlt(_localctx, 1);
				{
				setState(159);
				basic_t();
				}
				break;
			case NAME:
				enterOuterAlt(_localctx, 2);
				{
				setState(160);
				match(NAME);
				}
				break;
			case LSB:
				enterOuterAlt(_localctx, 3);
				{
				setState(161);
				array_t();
				}
				break;
			case POWER:
				enterOuterAlt(_localctx, 4);
				{
				setState(162);
				pointer_t();
				}
				break;
			case LB:
				enterOuterAlt(_localctx, 5);
				{
				setState(163);
				object_t();
				}
				break;
			case LP:
				enterOuterAlt(_localctx, 6);
				{
				setState(164);
				match(LP);
				setState(165);
				par_t();
				setState(166);
				match(RP);
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
		public Par_t_parse_NAMEContext par_t_parse_NAME() {
			return getRuleContext(Par_t_parse_NAMEContext.class,0);
		}
		public Fun_tContext fun_t() {
			return getRuleContext(Fun_tContext.class,0);
		}
		public Par_tContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_par_t; }
	}

	public final Par_tContext par_t() throws RecognitionException {
		Par_tContext _localctx = new Par_tContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_par_t);
		try {
			setState(172);
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
				setState(170);
				par_t_parse_NAME();
				}
				break;
			case DD:
				enterOuterAlt(_localctx, 2);
				{
				setState(171);
				fun_t();
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
	public static class Par_t_parse_NAMEContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(Prev26Parser.NAME, 0); }
		public Par_after_NAMEContext par_after_NAME() {
			return getRuleContext(Par_after_NAMEContext.class,0);
		}
		public NonNAME_tContext nonNAME_t() {
			return getRuleContext(NonNAME_tContext.class,0);
		}
		public Par_t_parse_NAMEContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_par_t_parse_NAME; }
	}

	public final Par_t_parse_NAMEContext par_t_parse_NAME() throws RecognitionException {
		Par_t_parse_NAMEContext _localctx = new Par_t_parse_NAMEContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_par_t_parse_NAME);
		try {
			setState(177);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(174);
				match(NAME);
				setState(175);
				par_after_NAME();
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
				setState(176);
				nonNAME_t();
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
	public static class Par_after_NAMEContext extends ParserRuleContext {
		public TerminalNode DD() { return getToken(Prev26Parser.DD, 0); }
		public TContext t() {
			return getRuleContext(TContext.class,0);
		}
		public Param_tailContext param_tail() {
			return getRuleContext(Param_tailContext.class,0);
		}
		public Par_after_NAMEContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_par_after_NAME; }
	}

	public final Par_after_NAMEContext par_after_NAME() throws RecognitionException {
		Par_after_NAMEContext _localctx = new Par_after_NAMEContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_par_after_NAME);
		try {
			setState(184);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DD:
				enterOuterAlt(_localctx, 1);
				{
				setState(179);
				match(DD);
				setState(180);
				t();
				setState(181);
				param_tail();
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
	public static class NonNAME_tContext extends ParserRuleContext {
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
		public NonNAME_tContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nonNAME_t; }
	}

	public final NonNAME_tContext nonNAME_t() throws RecognitionException {
		NonNAME_tContext _localctx = new NonNAME_tContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_nonNAME_t);
		try {
			setState(194);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BOOL:
			case CHAR:
			case INT:
			case VOID:
				enterOuterAlt(_localctx, 1);
				{
				setState(186);
				basic_t();
				}
				break;
			case LSB:
				enterOuterAlt(_localctx, 2);
				{
				setState(187);
				array_t();
				}
				break;
			case POWER:
				enterOuterAlt(_localctx, 3);
				{
				setState(188);
				pointer_t();
				}
				break;
			case LB:
				enterOuterAlt(_localctx, 4);
				{
				setState(189);
				object_t();
				}
				break;
			case LP:
				enterOuterAlt(_localctx, 5);
				{
				setState(190);
				match(LP);
				setState(191);
				par_t();
				setState(192);
				match(RP);
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
		public TerminalNode INT() { return getToken(Prev26Parser.INT, 0); }
		public TerminalNode CHAR() { return getToken(Prev26Parser.CHAR, 0); }
		public TerminalNode BOOL() { return getToken(Prev26Parser.BOOL, 0); }
		public TerminalNode VOID() { return getToken(Prev26Parser.VOID, 0); }
		public Basic_tContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_basic_t; }
	}

	public final Basic_tContext basic_t() throws RecognitionException {
		Basic_tContext _localctx = new Basic_tContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_basic_t);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(196);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 8390696L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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
		public TerminalNode LSB() { return getToken(Prev26Parser.LSB, 0); }
		public TerminalNode CINT() { return getToken(Prev26Parser.CINT, 0); }
		public TerminalNode RSB() { return getToken(Prev26Parser.RSB, 0); }
		public TContext t() {
			return getRuleContext(TContext.class,0);
		}
		public Array_tContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_array_t; }
	}

	public final Array_tContext array_t() throws RecognitionException {
		Array_tContext _localctx = new Array_tContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_array_t);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
			match(LSB);
			setState(199);
			match(CINT);
			setState(200);
			match(RSB);
			setState(201);
			t();
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
		public TerminalNode POWER() { return getToken(Prev26Parser.POWER, 0); }
		public TContext t() {
			return getRuleContext(TContext.class,0);
		}
		public Pointer_tContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pointer_t; }
	}

	public final Pointer_tContext pointer_t() throws RecognitionException {
		Pointer_tContext _localctx = new Pointer_tContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_pointer_t);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(203);
			match(POWER);
			setState(204);
			t();
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
		public TerminalNode LB() { return getToken(Prev26Parser.LB, 0); }
		public ParamsContext params() {
			return getRuleContext(ParamsContext.class,0);
		}
		public TerminalNode RB() { return getToken(Prev26Parser.RB, 0); }
		public Object_tContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_object_t; }
	}

	public final Object_tContext object_t() throws RecognitionException {
		Object_tContext _localctx = new Object_tContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_object_t);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			match(LB);
			setState(207);
			params();
			setState(208);
			match(RB);
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
	}

	public final Fun_tContext fun_t() throws RecognitionException {
		Fun_tContext _localctx = new Fun_tContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_fun_t);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(210);
			match(DD);
			setState(211);
			t_list();
			setState(212);
			match(DD);
			setState(213);
			t();
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
	}

	public final T_listContext t_list() throws RecognitionException {
		T_listContext _localctx = new T_listContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_t_list);
		try {
			setState(219);
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
				setState(215);
				t();
				setState(216);
				t_list_tail();
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
	public static class T_list_tailContext extends ParserRuleContext {
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
	}

	public final T_list_tailContext t_list_tail() throws RecognitionException {
		T_list_tailContext _localctx = new T_list_tailContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_t_list_tail);
		try {
			setState(226);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case C:
				enterOuterAlt(_localctx, 1);
				{
				setState(221);
				match(C);
				setState(222);
				t();
				setState(223);
				t_list_tail();
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
	}

	public final Expr_listContext expr_list() throws RecognitionException {
		Expr_listContext _localctx = new Expr_listContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_expr_list);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(228);
			e();
			setState(229);
			e_tail();
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
	}

	public final E_tailContext e_tail() throws RecognitionException {
		E_tailContext _localctx = new E_tailContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_e_tail);
		try {
			setState(236);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case C:
				enterOuterAlt(_localctx, 1);
				{
				setState(231);
				match(C);
				setState(232);
				e();
				setState(233);
				e_tail();
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
	}

	public final EContext e() throws RecognitionException {
		EContext _localctx = new EContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_e);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(238);
			e_as();
			setState(239);
			e_assign();
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
		public TerminalNode IS() { return getToken(Prev26Parser.IS, 0); }
		public E_asContext e_as() {
			return getRuleContext(E_asContext.class,0);
		}
		public E_assignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_e_assign; }
	}

	public final E_assignContext e_assign() throws RecognitionException {
		E_assignContext _localctx = new E_assignContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_e_assign);
		try {
			setState(244);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IS:
				enterOuterAlt(_localctx, 1);
				{
				setState(241);
				match(IS);
				setState(242);
				e_as();
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
	}

	public final E_asContext e_as() throws RecognitionException {
		E_asContext _localctx = new E_asContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_e_as);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(246);
			e_or();
			setState(247);
			as_tail();
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
		public TerminalNode AS() { return getToken(Prev26Parser.AS, 0); }
		public TContext t() {
			return getRuleContext(TContext.class,0);
		}
		public As_tailContext as_tail() {
			return getRuleContext(As_tailContext.class,0);
		}
		public As_tailContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_as_tail; }
	}

	public final As_tailContext as_tail() throws RecognitionException {
		As_tailContext _localctx = new As_tailContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_as_tail);
		try {
			setState(254);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AS:
				enterOuterAlt(_localctx, 1);
				{
				setState(249);
				match(AS);
				setState(250);
				t();
				setState(251);
				as_tail();
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
	}

	public final E_orContext e_or() throws RecognitionException {
		E_orContext _localctx = new E_orContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_e_or);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(256);
			e_and();
			setState(257);
			or_tail();
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
		public TerminalNode OR() { return getToken(Prev26Parser.OR, 0); }
		public E_andContext e_and() {
			return getRuleContext(E_andContext.class,0);
		}
		public Or_tailContext or_tail() {
			return getRuleContext(Or_tailContext.class,0);
		}
		public Or_tailContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_or_tail; }
	}

	public final Or_tailContext or_tail() throws RecognitionException {
		Or_tailContext _localctx = new Or_tailContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_or_tail);
		try {
			setState(264);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OR:
				enterOuterAlt(_localctx, 1);
				{
				setState(259);
				match(OR);
				setState(260);
				e_and();
				setState(261);
				or_tail();
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
		public E_compContext e_comp() {
			return getRuleContext(E_compContext.class,0);
		}
		public And_tailContext and_tail() {
			return getRuleContext(And_tailContext.class,0);
		}
		public E_andContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_e_and; }
	}

	public final E_andContext e_and() throws RecognitionException {
		E_andContext _localctx = new E_andContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_e_and);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(266);
			e_comp();
			setState(267);
			and_tail();
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
		public TerminalNode AND() { return getToken(Prev26Parser.AND, 0); }
		public E_compContext e_comp() {
			return getRuleContext(E_compContext.class,0);
		}
		public And_tailContext and_tail() {
			return getRuleContext(And_tailContext.class,0);
		}
		public And_tailContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_and_tail; }
	}

	public final And_tailContext and_tail() throws RecognitionException {
		And_tailContext _localctx = new And_tailContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_and_tail);
		try {
			setState(274);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AND:
				enterOuterAlt(_localctx, 1);
				{
				setState(269);
				match(AND);
				setState(270);
				e_comp();
				setState(271);
				and_tail();
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
	public static class E_compContext extends ParserRuleContext {
		public E_addContext e_add() {
			return getRuleContext(E_addContext.class,0);
		}
		public Comp_tailContext comp_tail() {
			return getRuleContext(Comp_tailContext.class,0);
		}
		public E_compContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_e_comp; }
	}

	public final E_compContext e_comp() throws RecognitionException {
		E_compContext _localctx = new E_compContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_e_comp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(276);
			e_add();
			setState(277);
			comp_tail();
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
		public Comp_opContext comp_op() {
			return getRuleContext(Comp_opContext.class,0);
		}
		public E_addContext e_add() {
			return getRuleContext(E_addContext.class,0);
		}
		public Comp_tailContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comp_tail; }
	}

	public final Comp_tailContext comp_tail() throws RecognitionException {
		Comp_tailContext _localctx = new Comp_tailContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_comp_tail);
		try {
			setState(283);
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
				setState(279);
				comp_op();
				setState(280);
				e_add();
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
	public static class Comp_opContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(Prev26Parser.EQ, 0); }
		public TerminalNode NE() { return getToken(Prev26Parser.NE, 0); }
		public TerminalNode LT() { return getToken(Prev26Parser.LT, 0); }
		public TerminalNode GT() { return getToken(Prev26Parser.GT, 0); }
		public TerminalNode LE() { return getToken(Prev26Parser.LE, 0); }
		public TerminalNode GE() { return getToken(Prev26Parser.GE, 0); }
		public Comp_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comp_op; }
	}

	public final Comp_opContext comp_op() throws RecognitionException {
		Comp_opContext _localctx = new Comp_opContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_comp_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(285);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 270582939648L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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
	}

	public final E_addContext e_add() throws RecognitionException {
		E_addContext _localctx = new E_addContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_e_add);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(287);
			e_mul();
			setState(288);
			add_tail();
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
		public Add_compContext add_comp() {
			return getRuleContext(Add_compContext.class,0);
		}
		public E_mulContext e_mul() {
			return getRuleContext(E_mulContext.class,0);
		}
		public Add_tailContext add_tail() {
			return getRuleContext(Add_tailContext.class,0);
		}
		public Add_tailContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_add_tail; }
	}

	public final Add_tailContext add_tail() throws RecognitionException {
		Add_tailContext _localctx = new Add_tailContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_add_tail);
		try {
			setState(295);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SUB:
			case ADD:
				enterOuterAlt(_localctx, 1);
				{
				setState(290);
				add_comp();
				setState(291);
				e_mul();
				setState(292);
				add_tail();
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
		public TerminalNode ADD() { return getToken(Prev26Parser.ADD, 0); }
		public TerminalNode SUB() { return getToken(Prev26Parser.SUB, 0); }
		public Add_compContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_add_comp; }
	}

	public final Add_compContext add_comp() throws RecognitionException {
		Add_compContext _localctx = new Add_compContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_add_comp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(297);
			_la = _input.LA(1);
			if ( !(_la==SUB || _la==ADD) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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
	}

	public final E_mulContext e_mul() throws RecognitionException {
		E_mulContext _localctx = new E_mulContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_e_mul);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(299);
			e_prefix();
			setState(300);
			mul_tail();
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
		public Mul_opContext mul_op() {
			return getRuleContext(Mul_opContext.class,0);
		}
		public E_prefixContext e_prefix() {
			return getRuleContext(E_prefixContext.class,0);
		}
		public Mul_tailContext mul_tail() {
			return getRuleContext(Mul_tailContext.class,0);
		}
		public Mul_tailContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mul_tail; }
	}

	public final Mul_tailContext mul_tail() throws RecognitionException {
		Mul_tailContext _localctx = new Mul_tailContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_mul_tail);
		try {
			setState(307);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case MOD:
			case DIV:
			case MUL:
				enterOuterAlt(_localctx, 1);
				{
				setState(302);
				mul_op();
				setState(303);
				e_prefix();
				setState(304);
				mul_tail();
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
		public TerminalNode MUL() { return getToken(Prev26Parser.MUL, 0); }
		public TerminalNode DIV() { return getToken(Prev26Parser.DIV, 0); }
		public TerminalNode MOD() { return getToken(Prev26Parser.MOD, 0); }
		public Mul_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mul_op; }
	}

	public final Mul_opContext mul_op() throws RecognitionException {
		Mul_opContext _localctx = new Mul_opContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_mul_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(309);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 1924145348608L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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
	}

	public final E_prefixContext e_prefix() throws RecognitionException {
		E_prefixContext _localctx = new E_prefixContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_e_prefix);
		try {
			setState(315);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NOT:
			case POWER:
			case SUB:
			case ADD:
				enterOuterAlt(_localctx, 1);
				{
				setState(311);
				prefix_op();
				setState(312);
				e_prefix();
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
				setState(314);
				e_postfix();
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
		public TerminalNode NOT() { return getToken(Prev26Parser.NOT, 0); }
		public TerminalNode ADD() { return getToken(Prev26Parser.ADD, 0); }
		public TerminalNode SUB() { return getToken(Prev26Parser.SUB, 0); }
		public TerminalNode POWER() { return getToken(Prev26Parser.POWER, 0); }
		public Prefix_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prefix_op; }
	}

	public final Prefix_opContext prefix_op() throws RecognitionException {
		Prefix_opContext _localctx = new Prefix_opContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_prefix_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(317);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 6597103386624L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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
	}

	public final E_postfixContext e_postfix() throws RecognitionException {
		E_postfixContext _localctx = new E_postfixContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_e_postfix);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(319);
			e_basic();
			setState(320);
			postfix_tail();
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
		public TerminalNode D() { return getToken(Prev26Parser.D, 0); }
		public TerminalNode NAME() { return getToken(Prev26Parser.NAME, 0); }
		public Postfix_tailContext postfix_tail() {
			return getRuleContext(Postfix_tailContext.class,0);
		}
		public TerminalNode POWER() { return getToken(Prev26Parser.POWER, 0); }
		public TerminalNode LSB() { return getToken(Prev26Parser.LSB, 0); }
		public EContext e() {
			return getRuleContext(EContext.class,0);
		}
		public TerminalNode RSB() { return getToken(Prev26Parser.RSB, 0); }
		public TerminalNode LP() { return getToken(Prev26Parser.LP, 0); }
		public Expr_listContext expr_list() {
			return getRuleContext(Expr_listContext.class,0);
		}
		public TerminalNode RP() { return getToken(Prev26Parser.RP, 0); }
		public Postfix_tailContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_postfix_tail; }
	}

	public final Postfix_tailContext postfix_tail() throws RecognitionException {
		Postfix_tailContext _localctx = new Postfix_tailContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_postfix_tail);
		try {
			setState(338);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case D:
				enterOuterAlt(_localctx, 1);
				{
				setState(322);
				match(D);
				setState(323);
				match(NAME);
				setState(324);
				postfix_tail();
				}
				break;
			case POWER:
				enterOuterAlt(_localctx, 2);
				{
				setState(325);
				match(POWER);
				setState(326);
				postfix_tail();
				}
				break;
			case LSB:
				enterOuterAlt(_localctx, 3);
				{
				setState(327);
				match(LSB);
				setState(328);
				e();
				setState(329);
				match(RSB);
				setState(330);
				postfix_tail();
				}
				break;
			case LP:
				enterOuterAlt(_localctx, 4);
				{
				setState(332);
				match(LP);
				setState(333);
				expr_list();
				setState(334);
				match(RP);
				setState(335);
				postfix_tail();
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
	}

	public final E_basicContext e_basic() throws RecognitionException {
		E_basicContext _localctx = new E_basicContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_e_basic);
		try {
			setState(350);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(340);
				match(NAME);
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
				setState(341);
				e_const();
				}
				break;
			case SIZEOF:
				enterOuterAlt(_localctx, 3);
				{
				setState(342);
				e_sizeof();
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 4);
				{
				setState(343);
				e_if();
				}
				break;
			case WHILE:
				enterOuterAlt(_localctx, 5);
				{
				setState(344);
				e_while();
				}
				break;
			case LET:
				enterOuterAlt(_localctx, 6);
				{
				setState(345);
				e_let();
				}
				break;
			case LP:
				enterOuterAlt(_localctx, 7);
				{
				setState(346);
				match(LP);
				setState(347);
				expr_list();
				setState(348);
				match(RP);
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
	}

	public final E_constContext e_const() throws RecognitionException {
		E_constContext _localctx = new E_constContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_e_const);
		try {
			setState(358);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CINT:
				enterOuterAlt(_localctx, 1);
				{
				setState(352);
				match(CINT);
				}
				break;
			case FALSE:
			case TRUE:
				enterOuterAlt(_localctx, 2);
				{
				setState(353);
				bool_const();
				}
				break;
			case CCHAR:
				enterOuterAlt(_localctx, 3);
				{
				setState(354);
				match(CCHAR);
				}
				break;
			case CSTRING:
				enterOuterAlt(_localctx, 4);
				{
				setState(355);
				match(CSTRING);
				}
				break;
			case NONE:
				enterOuterAlt(_localctx, 5);
				{
				setState(356);
				void_const();
				}
				break;
			case NIL:
				enterOuterAlt(_localctx, 6);
				{
				setState(357);
				ptr_const();
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
		public TerminalNode TRUE() { return getToken(Prev26Parser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(Prev26Parser.FALSE, 0); }
		public Bool_constContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bool_const; }
	}

	public final Bool_constContext bool_const() throws RecognitionException {
		Bool_constContext _localctx = new Bool_constContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_bool_const);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(360);
			_la = _input.LA(1);
			if ( !(_la==FALSE || _la==TRUE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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
		public TerminalNode NONE() { return getToken(Prev26Parser.NONE, 0); }
		public Void_constContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_void_const; }
	}

	public final Void_constContext void_const() throws RecognitionException {
		Void_constContext _localctx = new Void_constContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_void_const);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(362);
			match(NONE);
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
		public TerminalNode NIL() { return getToken(Prev26Parser.NIL, 0); }
		public Ptr_constContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ptr_const; }
	}

	public final Ptr_constContext ptr_const() throws RecognitionException {
		Ptr_constContext _localctx = new Ptr_constContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_ptr_const);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(364);
			match(NIL);
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
		public TerminalNode SIZEOF() { return getToken(Prev26Parser.SIZEOF, 0); }
		public TContext t() {
			return getRuleContext(TContext.class,0);
		}
		public E_sizeofContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_e_sizeof; }
	}

	public final E_sizeofContext e_sizeof() throws RecognitionException {
		E_sizeofContext _localctx = new E_sizeofContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_e_sizeof);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(366);
			match(SIZEOF);
			setState(367);
			t();
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
	}

	public final E_letContext e_let() throws RecognitionException {
		E_letContext _localctx = new E_letContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_e_let);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(369);
			match(LET);
			setState(370);
			prog();
			setState(371);
			match(IN);
			setState(372);
			expr_list();
			setState(373);
			match(END);
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
	}

	public final E_whileContext e_while() throws RecognitionException {
		E_whileContext _localctx = new E_whileContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_e_while);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(375);
			match(WHILE);
			setState(376);
			e();
			setState(377);
			match(DO);
			setState(378);
			expr_list();
			setState(379);
			match(END);
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
	}

	public final E_ifContext e_if() throws RecognitionException {
		E_ifContext _localctx = new E_ifContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_e_if);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(381);
			match(IF);
			setState(382);
			e();
			setState(383);
			match(THEN);
			setState(384);
			expr_list();
			setState(385);
			if_tail();
			setState(386);
			match(END);
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
		public TerminalNode ELSE() { return getToken(Prev26Parser.ELSE, 0); }
		public Expr_listContext expr_list() {
			return getRuleContext(Expr_listContext.class,0);
		}
		public If_tailContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_if_tail; }
	}

	public final If_tailContext if_tail() throws RecognitionException {
		If_tailContext _localctx = new If_tailContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_if_tail);
		try {
			setState(391);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ELSE:
				enterOuterAlt(_localctx, 1);
				{
				setState(388);
				match(ELSE);
				setState(389);
				expr_list();
				}
				break;
			case END:
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
		"\u0004\u00017\u018a\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
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
		"2\u00072\u00023\u00073\u00024\u00074\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0003\u0002u\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0003\u0003\u0088\b\u0003\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0003\u0004\u008d\b\u0004\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0003\u0005\u0093\b\u0005\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0003\u0006\u009a\b\u0006\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0003\b\u00a9\b\b\u0001\t\u0001\t\u0003\t\u00ad"+
		"\b\t\u0001\n\u0001\n\u0001\n\u0003\n\u00b2\b\n\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u00b9\b\u000b\u0001\f"+
		"\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0003\f\u00c3"+
		"\b\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001"+
		"\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0003\u0012\u00dc"+
		"\b\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0003"+
		"\u0013\u00e3\b\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0003\u0015\u00ed\b\u0015\u0001"+
		"\u0016\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0003"+
		"\u0017\u00f5\b\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0019\u0001"+
		"\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0003\u0019\u00ff\b\u0019\u0001"+
		"\u001a\u0001\u001a\u0001\u001a\u0001\u001b\u0001\u001b\u0001\u001b\u0001"+
		"\u001b\u0001\u001b\u0003\u001b\u0109\b\u001b\u0001\u001c\u0001\u001c\u0001"+
		"\u001c\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0003"+
		"\u001d\u0113\b\u001d\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001f\u0001"+
		"\u001f\u0001\u001f\u0001\u001f\u0003\u001f\u011c\b\u001f\u0001 \u0001"+
		" \u0001!\u0001!\u0001!\u0001\"\u0001\"\u0001\"\u0001\"\u0001\"\u0003\""+
		"\u0128\b\"\u0001#\u0001#\u0001$\u0001$\u0001$\u0001%\u0001%\u0001%\u0001"+
		"%\u0001%\u0003%\u0134\b%\u0001&\u0001&\u0001\'\u0001\'\u0001\'\u0001\'"+
		"\u0003\'\u013c\b\'\u0001(\u0001(\u0001)\u0001)\u0001)\u0001*\u0001*\u0001"+
		"*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001"+
		"*\u0001*\u0001*\u0001*\u0003*\u0153\b*\u0001+\u0001+\u0001+\u0001+\u0001"+
		"+\u0001+\u0001+\u0001+\u0001+\u0001+\u0003+\u015f\b+\u0001,\u0001,\u0001"+
		",\u0001,\u0001,\u0001,\u0003,\u0167\b,\u0001-\u0001-\u0001.\u0001.\u0001"+
		"/\u0001/\u00010\u00010\u00010\u00011\u00011\u00011\u00011\u00011\u0001"+
		"1\u00012\u00012\u00012\u00012\u00012\u00012\u00013\u00013\u00013\u0001"+
		"3\u00013\u00013\u00013\u00014\u00014\u00014\u00034\u0188\b4\u00014\u0000"+
		"\u00005\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018"+
		"\u001a\u001c\u001e \"$&(*,.02468:<>@BDFHJLNPRTVXZ\\^`bdfh\u0000\u0006"+
		"\u0004\u0000\u0003\u0003\u0005\u0005\u000b\u000b\u0017\u0017\u0001\u0000"+
		" %\u0001\u0000)*\u0001\u0000&(\u0003\u0000\u0010\u0010\u0019\u0019)*\u0002"+
		"\u0000\b\b\u0014\u0014\u0181\u0000j\u0001\u0000\u0000\u0000\u0002m\u0001"+
		"\u0000\u0000\u0000\u0004t\u0001\u0000\u0000\u0000\u0006\u0087\u0001\u0000"+
		"\u0000\u0000\b\u008c\u0001\u0000\u0000\u0000\n\u0092\u0001\u0000\u0000"+
		"\u0000\f\u0099\u0001\u0000\u0000\u0000\u000e\u009b\u0001\u0000\u0000\u0000"+
		"\u0010\u00a8\u0001\u0000\u0000\u0000\u0012\u00ac\u0001\u0000\u0000\u0000"+
		"\u0014\u00b1\u0001\u0000\u0000\u0000\u0016\u00b8\u0001\u0000\u0000\u0000"+
		"\u0018\u00c2\u0001\u0000\u0000\u0000\u001a\u00c4\u0001\u0000\u0000\u0000"+
		"\u001c\u00c6\u0001\u0000\u0000\u0000\u001e\u00cb\u0001\u0000\u0000\u0000"+
		" \u00ce\u0001\u0000\u0000\u0000\"\u00d2\u0001\u0000\u0000\u0000$\u00db"+
		"\u0001\u0000\u0000\u0000&\u00e2\u0001\u0000\u0000\u0000(\u00e4\u0001\u0000"+
		"\u0000\u0000*\u00ec\u0001\u0000\u0000\u0000,\u00ee\u0001\u0000\u0000\u0000"+
		".\u00f4\u0001\u0000\u0000\u00000\u00f6\u0001\u0000\u0000\u00002\u00fe"+
		"\u0001\u0000\u0000\u00004\u0100\u0001\u0000\u0000\u00006\u0108\u0001\u0000"+
		"\u0000\u00008\u010a\u0001\u0000\u0000\u0000:\u0112\u0001\u0000\u0000\u0000"+
		"<\u0114\u0001\u0000\u0000\u0000>\u011b\u0001\u0000\u0000\u0000@\u011d"+
		"\u0001\u0000\u0000\u0000B\u011f\u0001\u0000\u0000\u0000D\u0127\u0001\u0000"+
		"\u0000\u0000F\u0129\u0001\u0000\u0000\u0000H\u012b\u0001\u0000\u0000\u0000"+
		"J\u0133\u0001\u0000\u0000\u0000L\u0135\u0001\u0000\u0000\u0000N\u013b"+
		"\u0001\u0000\u0000\u0000P\u013d\u0001\u0000\u0000\u0000R\u013f\u0001\u0000"+
		"\u0000\u0000T\u0152\u0001\u0000\u0000\u0000V\u015e\u0001\u0000\u0000\u0000"+
		"X\u0166\u0001\u0000\u0000\u0000Z\u0168\u0001\u0000\u0000\u0000\\\u016a"+
		"\u0001\u0000\u0000\u0000^\u016c\u0001\u0000\u0000\u0000`\u016e\u0001\u0000"+
		"\u0000\u0000b\u0171\u0001\u0000\u0000\u0000d\u0177\u0001\u0000\u0000\u0000"+
		"f\u017d\u0001\u0000\u0000\u0000h\u0187\u0001\u0000\u0000\u0000jk\u0003"+
		"\u0002\u0001\u0000kl\u0005\u0000\u0000\u0001l\u0001\u0001\u0000\u0000"+
		"\u0000mn\u0003\u0006\u0003\u0000no\u0003\u0004\u0002\u0000o\u0003\u0001"+
		"\u0000\u0000\u0000pq\u0003\u0006\u0003\u0000qr\u0003\u0004\u0002\u0000"+
		"ru\u0001\u0000\u0000\u0000su\u0001\u0000\u0000\u0000tp\u0001\u0000\u0000"+
		"\u0000ts\u0001\u0000\u0000\u0000u\u0005\u0001\u0000\u0000\u0000vw\u0005"+
		"\u0015\u0000\u0000wx\u00051\u0000\u0000xy\u0005+\u0000\u0000y\u0088\u0003"+
		"\u0010\b\u0000z{\u0005\u0016\u0000\u0000{|\u00051\u0000\u0000|}\u0005"+
		",\u0000\u0000}\u0088\u0003\u0010\b\u0000~\u007f\u0005\t\u0000\u0000\u007f"+
		"\u0080\u00051\u0000\u0000\u0080\u0081\u0005\u001e\u0000\u0000\u0081\u0082"+
		"\u0003\n\u0005\u0000\u0082\u0083\u0005\u001f\u0000\u0000\u0083\u0084\u0005"+
		",\u0000\u0000\u0084\u0085\u0003\u0010\b\u0000\u0085\u0086\u0003\b\u0004"+
		"\u0000\u0086\u0088\u0001\u0000\u0000\u0000\u0087v\u0001\u0000\u0000\u0000"+
		"\u0087z\u0001\u0000\u0000\u0000\u0087~\u0001\u0000\u0000\u0000\u0088\u0007"+
		"\u0001\u0000\u0000\u0000\u0089\u008a\u0005+\u0000\u0000\u008a\u008d\u0003"+
		"(\u0014\u0000\u008b\u008d\u0001\u0000\u0000\u0000\u008c\u0089\u0001\u0000"+
		"\u0000\u0000\u008c\u008b\u0001\u0000\u0000\u0000\u008d\t\u0001\u0000\u0000"+
		"\u0000\u008e\u008f\u0003\u000e\u0007\u0000\u008f\u0090\u0003\f\u0006\u0000"+
		"\u0090\u0093\u0001\u0000\u0000\u0000\u0091\u0093\u0001\u0000\u0000\u0000"+
		"\u0092\u008e\u0001\u0000\u0000\u0000\u0092\u0091\u0001\u0000\u0000\u0000"+
		"\u0093\u000b\u0001\u0000\u0000\u0000\u0094\u0095\u0005-\u0000\u0000\u0095"+
		"\u0096\u0003\u000e\u0007\u0000\u0096\u0097\u0003\f\u0006\u0000\u0097\u009a"+
		"\u0001\u0000\u0000\u0000\u0098\u009a\u0001\u0000\u0000\u0000\u0099\u0094"+
		"\u0001\u0000\u0000\u0000\u0099\u0098\u0001\u0000\u0000\u0000\u009a\r\u0001"+
		"\u0000\u0000\u0000\u009b\u009c\u00051\u0000\u0000\u009c\u009d\u0005,\u0000"+
		"\u0000\u009d\u009e\u0003\u0010\b\u0000\u009e\u000f\u0001\u0000\u0000\u0000"+
		"\u009f\u00a9\u0003\u001a\r\u0000\u00a0\u00a9\u00051\u0000\u0000\u00a1"+
		"\u00a9\u0003\u001c\u000e\u0000\u00a2\u00a9\u0003\u001e\u000f\u0000\u00a3"+
		"\u00a9\u0003 \u0010\u0000\u00a4\u00a5\u0005\u001e\u0000\u0000\u00a5\u00a6"+
		"\u0003\u0012\t\u0000\u00a6\u00a7\u0005\u001f\u0000\u0000\u00a7\u00a9\u0001"+
		"\u0000\u0000\u0000\u00a8\u009f\u0001\u0000\u0000\u0000\u00a8\u00a0\u0001"+
		"\u0000\u0000\u0000\u00a8\u00a1\u0001\u0000\u0000\u0000\u00a8\u00a2\u0001"+
		"\u0000\u0000\u0000\u00a8\u00a3\u0001\u0000\u0000\u0000\u00a8\u00a4\u0001"+
		"\u0000\u0000\u0000\u00a9\u0011\u0001\u0000\u0000\u0000\u00aa\u00ad\u0003"+
		"\u0014\n\u0000\u00ab\u00ad\u0003\"\u0011\u0000\u00ac\u00aa\u0001\u0000"+
		"\u0000\u0000\u00ac\u00ab\u0001\u0000\u0000\u0000\u00ad\u0013\u0001\u0000"+
		"\u0000\u0000\u00ae\u00af\u00051\u0000\u0000\u00af\u00b2\u0003\u0016\u000b"+
		"\u0000\u00b0\u00b2\u0003\u0018\f\u0000\u00b1\u00ae\u0001\u0000\u0000\u0000"+
		"\u00b1\u00b0\u0001\u0000\u0000\u0000\u00b2\u0015\u0001\u0000\u0000\u0000"+
		"\u00b3\u00b4\u0005,\u0000\u0000\u00b4\u00b5\u0003\u0010\b\u0000\u00b5"+
		"\u00b6\u0003\f\u0006\u0000\u00b6\u00b9\u0001\u0000\u0000\u0000\u00b7\u00b9"+
		"\u0001\u0000\u0000\u0000\u00b8\u00b3\u0001\u0000\u0000\u0000\u00b8\u00b7"+
		"\u0001\u0000\u0000\u0000\u00b9\u0017\u0001\u0000\u0000\u0000\u00ba\u00c3"+
		"\u0003\u001a\r\u0000\u00bb\u00c3\u0003\u001c\u000e\u0000\u00bc\u00c3\u0003"+
		"\u001e\u000f\u0000\u00bd\u00c3\u0003 \u0010\u0000\u00be\u00bf\u0005\u001e"+
		"\u0000\u0000\u00bf\u00c0\u0003\u0012\t\u0000\u00c0\u00c1\u0005\u001f\u0000"+
		"\u0000\u00c1\u00c3\u0001\u0000\u0000\u0000\u00c2\u00ba\u0001\u0000\u0000"+
		"\u0000\u00c2\u00bb\u0001\u0000\u0000\u0000\u00c2\u00bc\u0001\u0000\u0000"+
		"\u0000\u00c2\u00bd\u0001\u0000\u0000\u0000\u00c2\u00be\u0001\u0000\u0000"+
		"\u0000\u00c3\u0019\u0001\u0000\u0000\u0000\u00c4\u00c5\u0007\u0000\u0000"+
		"\u0000\u00c5\u001b\u0001\u0000\u0000\u0000\u00c6\u00c7\u0005\u001c\u0000"+
		"\u0000\u00c7\u00c8\u00052\u0000\u0000\u00c8\u00c9\u0005\u001d\u0000\u0000"+
		"\u00c9\u00ca\u0003\u0010\b\u0000\u00ca\u001d\u0001\u0000\u0000\u0000\u00cb"+
		"\u00cc\u0005\u0019\u0000\u0000\u00cc\u00cd\u0003\u0010\b\u0000\u00cd\u001f"+
		"\u0001\u0000\u0000\u0000\u00ce\u00cf\u0005\u001a\u0000\u0000\u00cf\u00d0"+
		"\u0003\n\u0005\u0000\u00d0\u00d1\u0005\u001b\u0000\u0000\u00d1!\u0001"+
		"\u0000\u0000\u0000\u00d2\u00d3\u0005,\u0000\u0000\u00d3\u00d4\u0003$\u0012"+
		"\u0000\u00d4\u00d5\u0005,\u0000\u0000\u00d5\u00d6\u0003\u0010\b\u0000"+
		"\u00d6#\u0001\u0000\u0000\u0000\u00d7\u00d8\u0003\u0010\b\u0000\u00d8"+
		"\u00d9\u0003&\u0013\u0000\u00d9\u00dc\u0001\u0000\u0000\u0000\u00da\u00dc"+
		"\u0001\u0000\u0000\u0000\u00db\u00d7\u0001\u0000\u0000\u0000\u00db\u00da"+
		"\u0001\u0000\u0000\u0000\u00dc%\u0001\u0000\u0000\u0000\u00dd\u00de\u0005"+
		"-\u0000\u0000\u00de\u00df\u0003\u0010\b\u0000\u00df\u00e0\u0003&\u0013"+
		"\u0000\u00e0\u00e3\u0001\u0000\u0000\u0000\u00e1\u00e3\u0001\u0000\u0000"+
		"\u0000\u00e2\u00dd\u0001\u0000\u0000\u0000\u00e2\u00e1\u0001\u0000\u0000"+
		"\u0000\u00e3\'\u0001\u0000\u0000\u0000\u00e4\u00e5\u0003,\u0016\u0000"+
		"\u00e5\u00e6\u0003*\u0015\u0000\u00e6)\u0001\u0000\u0000\u0000\u00e7\u00e8"+
		"\u0005-\u0000\u0000\u00e8\u00e9\u0003,\u0016\u0000\u00e9\u00ea\u0003*"+
		"\u0015\u0000\u00ea\u00ed\u0001\u0000\u0000\u0000\u00eb\u00ed\u0001\u0000"+
		"\u0000\u0000\u00ec\u00e7\u0001\u0000\u0000\u0000\u00ec\u00eb\u0001\u0000"+
		"\u0000\u0000\u00ed+\u0001\u0000\u0000\u0000\u00ee\u00ef\u00030\u0018\u0000"+
		"\u00ef\u00f0\u0003.\u0017\u0000\u00f0-\u0001\u0000\u0000\u0000\u00f1\u00f2"+
		"\u0005+\u0000\u0000\u00f2\u00f5\u00030\u0018\u0000\u00f3\u00f5\u0001\u0000"+
		"\u0000\u0000\u00f4\u00f1\u0001\u0000\u0000\u0000\u00f4\u00f3\u0001\u0000"+
		"\u0000\u0000\u00f5/\u0001\u0000\u0000\u0000\u00f6\u00f7\u00034\u001a\u0000"+
		"\u00f7\u00f8\u00032\u0019\u0000\u00f81\u0001\u0000\u0000\u0000\u00f9\u00fa"+
		"\u0005\u0002\u0000\u0000\u00fa\u00fb\u0003\u0010\b\u0000\u00fb\u00fc\u0003"+
		"2\u0019\u0000\u00fc\u00ff\u0001\u0000\u0000\u0000\u00fd\u00ff\u0001\u0000"+
		"\u0000\u0000\u00fe\u00f9\u0001\u0000\u0000\u0000\u00fe\u00fd\u0001\u0000"+
		"\u0000\u0000\u00ff3\u0001\u0000\u0000\u0000\u0100\u0101\u00038\u001c\u0000"+
		"\u0101\u0102\u00036\u001b\u0000\u01025\u0001\u0000\u0000\u0000\u0103\u0104"+
		"\u0005\u0011\u0000\u0000\u0104\u0105\u00038\u001c\u0000\u0105\u0106\u0003"+
		"6\u001b\u0000\u0106\u0109\u0001\u0000\u0000\u0000\u0107\u0109\u0001\u0000"+
		"\u0000\u0000\u0108\u0103\u0001\u0000\u0000\u0000\u0108\u0107\u0001\u0000"+
		"\u0000\u0000\u01097\u0001\u0000\u0000\u0000\u010a\u010b\u0003<\u001e\u0000"+
		"\u010b\u010c\u0003:\u001d\u0000\u010c9\u0001\u0000\u0000\u0000\u010d\u010e"+
		"\u0005\u0001\u0000\u0000\u010e\u010f\u0003<\u001e\u0000\u010f\u0110\u0003"+
		":\u001d\u0000\u0110\u0113\u0001\u0000\u0000\u0000\u0111\u0113\u0001\u0000"+
		"\u0000\u0000\u0112\u010d\u0001\u0000\u0000\u0000\u0112\u0111\u0001\u0000"+
		"\u0000\u0000\u0113;\u0001\u0000\u0000\u0000\u0114\u0115\u0003B!\u0000"+
		"\u0115\u0116\u0003>\u001f\u0000\u0116=\u0001\u0000\u0000\u0000\u0117\u0118"+
		"\u0003@ \u0000\u0118\u0119\u0003B!\u0000\u0119\u011c\u0001\u0000\u0000"+
		"\u0000\u011a\u011c\u0001\u0000\u0000\u0000\u011b\u0117\u0001\u0000\u0000"+
		"\u0000\u011b\u011a\u0001\u0000\u0000\u0000\u011c?\u0001\u0000\u0000\u0000"+
		"\u011d\u011e\u0007\u0001\u0000\u0000\u011eA\u0001\u0000\u0000\u0000\u011f"+
		"\u0120\u0003H$\u0000\u0120\u0121\u0003D\"\u0000\u0121C\u0001\u0000\u0000"+
		"\u0000\u0122\u0123\u0003F#\u0000\u0123\u0124\u0003H$\u0000\u0124\u0125"+
		"\u0003D\"\u0000\u0125\u0128\u0001\u0000\u0000\u0000\u0126\u0128\u0001"+
		"\u0000\u0000\u0000\u0127\u0122\u0001\u0000\u0000\u0000\u0127\u0126\u0001"+
		"\u0000\u0000\u0000\u0128E\u0001\u0000\u0000\u0000\u0129\u012a\u0007\u0002"+
		"\u0000\u0000\u012aG\u0001\u0000\u0000\u0000\u012b\u012c\u0003N\'\u0000"+
		"\u012c\u012d\u0003J%\u0000\u012dI\u0001\u0000\u0000\u0000\u012e\u012f"+
		"\u0003L&\u0000\u012f\u0130\u0003N\'\u0000\u0130\u0131\u0003J%\u0000\u0131"+
		"\u0134\u0001\u0000\u0000\u0000\u0132\u0134\u0001\u0000\u0000\u0000\u0133"+
		"\u012e\u0001\u0000\u0000\u0000\u0133\u0132\u0001\u0000\u0000\u0000\u0134"+
		"K\u0001\u0000\u0000\u0000\u0135\u0136\u0007\u0003\u0000\u0000\u0136M\u0001"+
		"\u0000\u0000\u0000\u0137\u0138\u0003P(\u0000\u0138\u0139\u0003N\'\u0000"+
		"\u0139\u013c\u0001\u0000\u0000\u0000\u013a\u013c\u0003R)\u0000\u013b\u0137"+
		"\u0001\u0000\u0000\u0000\u013b\u013a\u0001\u0000\u0000\u0000\u013cO\u0001"+
		"\u0000\u0000\u0000\u013d\u013e\u0007\u0004\u0000\u0000\u013eQ\u0001\u0000"+
		"\u0000\u0000\u013f\u0140\u0003V+\u0000\u0140\u0141\u0003T*\u0000\u0141"+
		"S\u0001\u0000\u0000\u0000\u0142\u0143\u0005.\u0000\u0000\u0143\u0144\u0005"+
		"1\u0000\u0000\u0144\u0153\u0003T*\u0000\u0145\u0146\u0005\u0019\u0000"+
		"\u0000\u0146\u0153\u0003T*\u0000\u0147\u0148\u0005\u001c\u0000\u0000\u0148"+
		"\u0149\u0003,\u0016\u0000\u0149\u014a\u0005\u001d\u0000\u0000\u014a\u014b"+
		"\u0003T*\u0000\u014b\u0153\u0001\u0000\u0000\u0000\u014c\u014d\u0005\u001e"+
		"\u0000\u0000\u014d\u014e\u0003(\u0014\u0000\u014e\u014f\u0005\u001f\u0000"+
		"\u0000\u014f\u0150\u0003T*\u0000\u0150\u0153\u0001\u0000\u0000\u0000\u0151"+
		"\u0153\u0001\u0000\u0000\u0000\u0152\u0142\u0001\u0000\u0000\u0000\u0152"+
		"\u0145\u0001\u0000\u0000\u0000\u0152\u0147\u0001\u0000\u0000\u0000\u0152"+
		"\u014c\u0001\u0000\u0000\u0000\u0152\u0151\u0001\u0000\u0000\u0000\u0153"+
		"U\u0001\u0000\u0000\u0000\u0154\u015f\u00051\u0000\u0000\u0155\u015f\u0003"+
		"X,\u0000\u0156\u015f\u0003`0\u0000\u0157\u015f\u0003f3\u0000\u0158\u015f"+
		"\u0003d2\u0000\u0159\u015f\u0003b1\u0000\u015a\u015b\u0005\u001e\u0000"+
		"\u0000\u015b\u015c\u0003(\u0014\u0000\u015c\u015d\u0005\u001f\u0000\u0000"+
		"\u015d\u015f\u0001\u0000\u0000\u0000\u015e\u0154\u0001\u0000\u0000\u0000"+
		"\u015e\u0155\u0001\u0000\u0000\u0000\u015e\u0156\u0001\u0000\u0000\u0000"+
		"\u015e\u0157\u0001\u0000\u0000\u0000\u015e\u0158\u0001\u0000\u0000\u0000"+
		"\u015e\u0159\u0001\u0000\u0000\u0000\u015e\u015a\u0001\u0000\u0000\u0000"+
		"\u015fW\u0001\u0000\u0000\u0000\u0160\u0167\u00052\u0000\u0000\u0161\u0167"+
		"\u0003Z-\u0000\u0162\u0167\u00053\u0000\u0000\u0163\u0167\u00055\u0000"+
		"\u0000\u0164\u0167\u0003\\.\u0000\u0165\u0167\u0003^/\u0000\u0166\u0160"+
		"\u0001\u0000\u0000\u0000\u0166\u0161\u0001\u0000\u0000\u0000\u0166\u0162"+
		"\u0001\u0000\u0000\u0000\u0166\u0163\u0001\u0000\u0000\u0000\u0166\u0164"+
		"\u0001\u0000\u0000\u0000\u0166\u0165\u0001\u0000\u0000\u0000\u0167Y\u0001"+
		"\u0000\u0000\u0000\u0168\u0169\u0007\u0005\u0000\u0000\u0169[\u0001\u0000"+
		"\u0000\u0000\u016a\u016b\u0005\u000f\u0000\u0000\u016b]\u0001\u0000\u0000"+
		"\u0000\u016c\u016d\u0005\u000e\u0000\u0000\u016d_\u0001\u0000\u0000\u0000"+
		"\u016e\u016f\u0005\u0012\u0000\u0000\u016f\u0170\u0003\u0010\b\u0000\u0170"+
		"a\u0001\u0000\u0000\u0000\u0171\u0172\u0005\r\u0000\u0000\u0172\u0173"+
		"\u0003\u0002\u0001\u0000\u0173\u0174\u0005\f\u0000\u0000\u0174\u0175\u0003"+
		"(\u0014\u0000\u0175\u0176\u0005\u0007\u0000\u0000\u0176c\u0001\u0000\u0000"+
		"\u0000\u0177\u0178\u0005\u0018\u0000\u0000\u0178\u0179\u0003,\u0016\u0000"+
		"\u0179\u017a\u0005\u0004\u0000\u0000\u017a\u017b\u0003(\u0014\u0000\u017b"+
		"\u017c\u0005\u0007\u0000\u0000\u017ce\u0001\u0000\u0000\u0000\u017d\u017e"+
		"\u0005\n\u0000\u0000\u017e\u017f\u0003,\u0016\u0000\u017f\u0180\u0005"+
		"\u0013\u0000\u0000\u0180\u0181\u0003(\u0014\u0000\u0181\u0182\u0003h4"+
		"\u0000\u0182\u0183\u0005\u0007\u0000\u0000\u0183g\u0001\u0000\u0000\u0000"+
		"\u0184\u0185\u0005\u0006\u0000\u0000\u0185\u0188\u0003(\u0014\u0000\u0186"+
		"\u0188\u0001\u0000\u0000\u0000\u0187\u0184\u0001\u0000\u0000\u0000\u0187"+
		"\u0186\u0001\u0000\u0000\u0000\u0188i\u0001\u0000\u0000\u0000\u0019t\u0087"+
		"\u008c\u0092\u0099\u00a8\u00ac\u00b1\u00b8\u00c2\u00db\u00e2\u00ec\u00f4"+
		"\u00fe\u0108\u0112\u011b\u0127\u0133\u013b\u0152\u015e\u0166\u0187";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}