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
COMMENT : '//' [^\n]* -> skip;
WS : [ \n\r\t]+ -> skip;
NAME : [A-Za-z_][A-Za-z_0-9]*;
// CINT : [+-]?[1-9][0-9]*;
CINT : [1-9][0-9]* | '0';
CCHAR : '\'' ( '\\\\' | '\\\'' | '\\x'([0-9A-F][0-9A-F]) | [ -&(-[\]-~] ) '\'';
CSTRING : '"' ( '\\\\' | '\\"' | '\\x'([0-9A-F][0-9A-F]) | [ -!#-[\]-~] )* '"';