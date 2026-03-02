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

	private void lexError(String msg) {
        throw new Report.Error(new Location(getLine(), getCharPositionInLine()), msg);
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