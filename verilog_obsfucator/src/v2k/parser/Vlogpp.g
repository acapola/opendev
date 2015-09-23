header {
/*
 *************************************************************************
 *************************************************************************
 **                                                                     **
 **  V2KPARSE                                                           **
 **  Copyright (C) 2008-2009    Karl W. Pfalzer                         **
 **                                                                     **
 **  This program is free software; you can redistribute it and/or      **
 **  modify it under the terms of the GNU General Public License        **
 **  as published by the Free Software Foundation; either version 2     **
 **  of the License, or (at your option) any later version.             **
 **                                                                     **
 **  This program is distributed in the hope that it will be useful,    **
 **  but WITHOUT ANY WARRANTY; without even the implied warranty of     **
 **  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the      **
 **  GNU General Public License for more details.                       **
 **                                                                     **
 **  You should have received a copy of the GNU General Public License  **
 **  along with this program; if not, write to the                      **
 **  Free Software Foundation, Inc.                                     **
 **  51 Franklin Street, Fifth Floor                                    **
 **  Boston, MA  02110-1301, USA.                                       **
 **                                                                     **
 *************************************************************************
 *************************************************************************
 */
package v2k.parser;
import	java.util.LinkedList;
}

class VlogppLexer extends Lexer;
options {
	k=2;
	charVocabulary='\u0000'..'\u00FF';
	testLiterals=false;
}
{
	//Embelish VlogppLexer class

	static void setPP(Preproc pp) {
		stPP = pp;
	}

	private static Preproc stPP = null;

	@Override
	public void uponEOF() throws TokenStreamException, CharStreamException {
		stPP.uponEOF();
	}

	private static String scrub2nl(String s) {
		String t = "";
		int i = 0;
		while (true) {
			i = s.indexOf("\n", i);
			if (0 <= i) {
				t += "\n";
				i++;
			} else {
				break; //while
			}
		}
		return t;
	}
}

TIC_DIRECTIVE
	:	'`' id:IDENT 
		(	{id.getText().equals("define")}? td:TIC_DEFINE 
				{
					String s = td.getText();
					if (Parser.stDumpDefn) {
						$setText("/*@DEFINE@ "+s+"*/");
					} else {
						String t = scrub2nl(s);
						$setText(t);
					}
				}
		|	{id.getText().equals("include")}? TIC_INCLUDE
		|	{id.getText().equals("timescale")}?  TIC_TIMESCALE	
				{$setType(Token.SKIP);}
		|	{id.getText().equals("ifdef")}? (WS)+ id5:IDENT	
				{stPP.ticIfdef(id5.getText()); $setType(Token.SKIP);}
		|	{id.getText().equals("ifndef")}? (WS)+ id7:IDENT	
				{stPP.ticIfndef(id7.getText()); $setType(Token.SKIP);}
		|	{id.getText().equals("elsif")}? (WS)+ id9:IDENT	
				{stPP.ticElsif(id9.getText()); $setType(Token.SKIP);}
		|	{id.getText().equals("else")}? 
				{stPP.ticElse(); $setType(Token.SKIP);}
		|	{id.getText().equals("endif")}?
				{stPP.ticEndif(); $setType(Token.SKIP);}
		|	{id.getText().equals("undef")}? (WS)+ id10:IDENT
				{stPP.undef(id10.getText()); $setType(Token.SKIP);}
		|	{id.getText().equals("celldefine")}? {$setType(Token.SKIP);}
		|	{id.getText().equals("endcelldefine")}? {$setType(Token.SKIP);}
		|	{id.getText().equals("default_nettype")}?  SKIP_DIRECTIVE
				{$setType(Token.SKIP);}
		|	{id.getText().equals("begin_keywords")}?  SKIP_DIRECTIVE
				{$setType(Token.SKIP);}
		|	{id.getText().equals("end_keywords")}?  SKIP_DIRECTIVE
				{$setType(Token.SKIP);}
		|	{id.getText().equals("line")}?  SKIP_DIRECTIVE
				{$setType(Token.SKIP);}
		|	{id.getText().equals("unconnected_drive")}?  SKIP_DIRECTIVE
				{$setType(Token.SKIP);}
		|	{id.getText().equals("nounconnected_drive")}?  SKIP_DIRECTIVE
				{$setType(Token.SKIP);}
		|	{id.getText().equals("pragma")}?  SKIP_DIRECTIVE
				{$setType(Token.SKIP);}
		|	{id.getText().equals("resetall")}?  SKIP_DIRECTIVE
				{$setType(Token.SKIP);}
		|	{id.getText().equals("default_decay_time")}?  SKIP_DIRECTIVE
				{$setType(Token.SKIP);}
		|	{id.getText().equals("default_trireg_strength")}?  SKIP_DIRECTIVE
				{$setType(Token.SKIP);}
		|	{id.getText().equals("delay_mode_distributed")}?  SKIP_DIRECTIVE
				{$setType(Token.SKIP);}
		|	{id.getText().equals("delay_mode_path")}?  SKIP_DIRECTIVE
				{$setType(Token.SKIP);}
		|	{id.getText().equals("delay_mode_unit")}?  SKIP_DIRECTIVE
				{$setType(Token.SKIP);}
		|	{id.getText().equals("delay_mode_zero")}?  SKIP_DIRECTIVE
				{$setType(Token.SKIP);}
		|	MACRO_EXPAND[id.getText()] {$setType(Token.SKIP);}
		)
	;

protected
MACRO_EXPAND [String key]
options {ignore=WS;}
	:	{stPP.hasParams(key)}? MACRO_EXPAND_PARMS[key]
		|	//nothing follows
			{
				if (stPP.okToDefine(key)) {
					stPP.expandMacro(key);
				}
			}
	;

protected
MACRO_EXPAND_PARMS [String key]
options {ignore=WS;}
{
	LinkedList<String> parms = null;
}
	:	'(' parms=MACRO_EXPAND_PARMS2 ')'
		{
			if (stPP.okToDefine(key)) {
				stPP.expandMacro(key, parms);
			}
		}
	;


protected
MACRO_EXPAND_PARMS2 returns [LinkedList<String> parms]
options {ignore=WS;}
{
	parms = new LinkedList<String>();
	String s = null;
	StringBuffer sbuf = new StringBuffer();
}
	:	(s1:PAREN_CLOSURE2 
			{	s = s1.getText();
				if (',' == s.charAt(0)) {
					if (0 < sbuf.length()) {
						parms.add(sbuf.toString());
					}
					sbuf = new StringBuffer();
				} else {
					sbuf.append(s);
				}
			}
		)* 
		{	if (0 < sbuf.length()) {
				parms.add(sbuf.toString());
			}
		}
	;

//Some closure rules for matching w/in macros
protected
PAREN_CLOSURE
options {ignore=WS;}
	:	'(' (PAREN_CLOSURE2)* ')'
	;

protected
PAREN_CLOSURE2
options {ignore=WS;}
	:	('{')=> CURLY_CLOSURE
	|	('"')=> STRING
	|	PAREN_CLOSURE
	|	~')'
	;

protected
CURLY_CLOSURE
options {ignore=WS;}
	:	'{' (CURLY_CLOSURE2)* '}'
	;

protected
CURLY_CLOSURE2
options {ignore=WS;}
	:	('(')=> PAREN_CLOSURE
	|	('"')=> STRING
	|	CURLY_CLOSURE
	|	~'}'
	;

IDENT 
	:	 ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'$'|'0'..'9')*
	;

    // string literals
STRING :
        '"' (~('"'|'\n'))* '"'
        ;

protected
UNSIZED_NUMBER :
	DIGIT (DIGIT | '_')*
        ;

protected
DIGIT :
        ('0'..'9')
        ;

//TODO: q&d: need to check units, etc.
protected
TIC_TIMESCALE
	:	(WS)+ TIMESCALE_VALUE (WS)* '/' (WS)* TIMESCALE_VALUE
	;

protected
TIMESCALE_VALUE
	:	UNSIZED_NUMBER (WS)* IDENT	
	;

protected
TIC_DEFINE
	:	(WS)+ id:IDENT 
			(	('(')=> TIC_DEFINE_PARMS[id.getText()]
			|	def:DEFINE
				{	
					stPP.addDefine(id.getText(), def.getText());
				}
			)
	;

protected
TIC_DEFINE_PARMS [String id]
{
	LinkedList<String> parms = new LinkedList<String>();
}
	:	'(' (WS)*
			(id2:IDENT {parms.add(id2.getText());}
			((WS)* ',' id3:IDENT {parms.add(id3.getText());})*)? 
			')' def:DEFINE
		{
			stPP.addDefine(id, parms, def.getText());
		}
	;

protected
TIC_INCLUDE
	:	(WS)+ fn:STRING
		{	
			stPP.includeFile(fn.getText());
		}
	;

protected
DEFINE
	:	(	'\\' ('\r')? '\n' {newline();}
		|   ('"')=> STRING
		|	~('\\'|'\n'|'\r')
		)*
		('\r')? '\n' {newline();}
	;

WS  :  (CNTRL |' '|'\r'|'\t'|'\n' {newline();})
    ;

protected
CNTRL
	: '\u0000'..'\u0008'
	| '\u000B'..'\u000C'
	| '\u000E'..'\u001F'
	| '\u007F'..'\u00FF'
	;

// skip pre-processor to end of line
protected
SKIP_DIRECTIVE
    :	(~('\n'|'\r'))* ('\n'|'\r'('\n')?)?
        {newline();}
    ;

// Single-line comments
protected
SL_COMMENT
    :   "//"
        (~('\n'|'\r'))* ('\n'|'\r'('\n')?)?
        {newline();}
    ;

// multiple-line comments
protected
ML_COMMENT
    :   "/*"
        (   /*  '\r' '\n' can be matched in one alternative or by matching
                '\r' in one iteration and '\n' in another.  I am trying to
                handle any flavor of newline that comes in, but the language
                that allows both "\r\n" and "\r" and "\n" to all be valid
                newline is ambiguous.  Consequently, the resulting grammar
                must be ambiguous.  I'm shutting this warning off.
             */
            options {
                generateAmbigWarnings=false;
            }
        :
            { LA(2)!='/' }? '*'
        |   '\r' '\n'       {newline();}
        |   '\r'            {newline();}
        |   '\n'            {newline();}
        |   ~('*'|'\n'|'\r')
        )*
        "*/"
    ;

COMMENT
{ Token tok = null;}
	:	(id:SL_COMMENT {tok=id;} | id2:ML_COMMENT {tok=id2;})
		{
			if (false == Parser.stShowComments) {
				String s = tok.getText();
				String t = scrub2nl(s);
				$setText(t);
			}
		}
	;

//all other valid chars
DUMMY: . ;	
