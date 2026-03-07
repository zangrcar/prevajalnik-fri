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
		int lineStart = absIndex - 1;
		while (lineStart >= 0) {
			String ch = _input.getText(org.antlr.v4.runtime.misc.Interval.of(lineStart, lineStart));
			if ("\n".equals(ch)) break;
			lineStart--;
		}
		lineStart++;

		if (absIndex <= lineStart) return 0;

		String prefix = _input.getText(org.antlr.v4.runtime.misc.Interval.of(lineStart, absIndex - 1));

		return expandTabs(prefix, 8);
	}

	private int expandTabs(String s, int tabWidth) {
		int col = 0;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == '\t') {
				col += tabWidth - (col % tabWidth);
			} else {
				col++;
			}
		}

		return col;
	}

	private void lexErrorUnterminated(String msg) {
		int absEnd = _input.index();

		String t = getText();
		if (t.endsWith("\r\n")) absEnd -= 2;
		else if (t.endsWith("\n")) absEnd -= 1;

		int line = _tokenStartLine;

		int col = visualColumn(absEnd);

		throw new Report.Error(new Location(line, col + 1), msg);
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
SUB : '-';
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
INVALID_CHAR: '\'' ( BACKSLASH | QUOTE | HEXCHAR | NOQUOTE) ('\n' | EOF)
	{
		lexErrorUnterminated("missing closing apostrophe");
	};
CSTRING : '"' ( BACKSLASH | DQUOTE | HEXCHAR | NODQUOTE )* '"';
INVALID_STRING : '"' ( BACKSLASH | DQUOTE | HEXCHAR | NODQUOTE )* ('\n' | EOF)
	{
		lexErrorUnterminated("missing closing dictate");
	};
fragment BACKSLASH : '\\\\';
fragment QUOTE : '\\\'';
fragment DQUOTE : '\\"';
fragment HEXCHAR : '\\x'([0-9A-F][0-9A-F]);
fragment NOQUOTE : [ -&(-[\]-~];
fragment NODQUOTE : [ -!#-[\]-~];

ANY : 
	.
	{
		lexError("Unexpected character " + getText());
	}
;