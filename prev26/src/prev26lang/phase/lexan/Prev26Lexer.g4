lexer grammar Prev26Lexer;

@header {
	package prev26lang.phase.lexan;

	import prev26lang.common.report.*;
}

@members {

    @Override
	public LexAn.LocLogToken nextToken() {
		return (LexAn.LocLogToken) super.nextToken();
	}

	// private void lexError(String msg) {
    //     throw new Report.Error(new Location(getLine(), getCharPositionInLine()), msg);
    // }

	private void lexError(String msg) {
        int abs = this._tokenStartCharIndex;
        int line = getLine();
        int col  = visualColumn(abs);

        throw new Report.Error(new Location(line, col+1), msg);
    }

	private int visualColumn(int absIndex) {
		// find line start (scan backwards for '\n')
		int lineStart = absIndex - 1;
		while (lineStart >= 0) {
			String ch = _input.getText(org.antlr.v4.runtime.misc.Interval.of(lineStart, lineStart));
			if ("\n".equals(ch)) break;
			lineStart--;
		}
		lineStart++;

		if (absIndex <= lineStart) return 0;

		// prefix of the line up to the error position
		String prefix = _input.getText(org.antlr.v4.runtime.misc.Interval.of(lineStart, absIndex - 1));

		// expand tabs to width 8 and return resulting length
		return expandTabs(prefix, 8).length();
	}

	private String expandTabs(String s, int tabWidth) {
		StringBuilder out = new StringBuilder(s.length());
		int col = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '\t') {
				int add = tabWidth - (col % tabWidth);
				for (int k = 0; k < add; k++) out.append(' ');
				col += add;
			} else {
				out.append(c);
				col++;
			}
		}
		return out.toString();
	}
}


AND : 'and';
AS : 'as';
BOOL : 'bool';
DO : 'do';
CHAR : 'char';
ELSE : 'else';
END : 'end';
FALSE : 'false';
FUN : 'fun';
IF : 'if';
INT : 'int';
IN : 'in';
LET : 'let';
NIL : 'nil';
NONE : 'none';
NOT : 'not';
OR : 'or';
SIZEOF : 'sizeof';
THEN : 'then';
TRUE : 'true';
TYP : 'typ';
VAR : 'var';
VOID : 'void';
WHILE : 'while';
POWER : '^';
LB : '{';
RB : '}';
LSB : '[';
RSB : ']';
LP : '(';
RP : ')';
LE: '<=';
GE : '>=';
NE : '!=';
EQ : '==';
LT : '<';
GT : '>';
MOD : '%';
DIV : '/';
MUL : '*';
MIN : '-';
ADD : '+';
IS : '=';
DD : ':';
C : ',';
D : '.';
COMMENT : '//' [\u0000-\u0009\u000B-\u000C\u000E-\u007F]* ('\n' | EOF) -> skip;
WS : [ \n\r\t]+ -> skip;
NAME : [A-Za-z_][A-Za-z_0-9]*;
CINT : [1-9][0-9]* | '0';
CCHAR : '\'' ( BACKSLASH | QUOTE | HEXCHAR | NOQUOTE) '\'';
CSTRING : '"' ( BACKSLASH | DQUOTE | HEXCHAR | NODQUOTE )* '"';
fragment BACKSLASH : '\\\\';
fragment QUOTE : '\\\'';
fragment DQUOTE : '\\"';
fragment HEXCHAR : '\\x'([0-9A-F][0-9A-F]);
fragment NOQUOTE : [ -&(-[\]-~];
fragment NODQUOTE : [ -!#-[\]-~];

ANY : 
	.
	{
		lexError("Invalid character " + getText());
	}
;