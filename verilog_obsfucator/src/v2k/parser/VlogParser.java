// $ANTLR 2.7.7 (20080509): "Vlog.g" -> "VlogParser.java"$

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
import  v2k.parser.tree.*;

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;
import static v2k.parser.VlogParserTokenTypes.*;
public class VlogParser extends antlr.LLkParser       /*implements VlogParserTokenTypes*/
 {

    /**Print message unless it was already handled.*/
    public void reportError(RecognitionException ex) {
        if (false == v2k.message.ExceptionBase.class.isInstance(ex)) {
            Message.message(ex);
        }
    }

    public void reportError(String s) {
        Message.syntaxError(s);
    }

    public void reportWarning(String s) {
		Message.syntaxWarning(s);
    }

	static Parser getParser() {
		return Parser.getTheOne();
	}

	private static ASTreeBase getTree() {
		return Parser.getTheTree();
	}


protected VlogParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public VlogParser(TokenBuffer tokenBuf) {
  this(tokenBuf,2);
}

protected VlogParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public VlogParser(TokenStream lexer) {
  this(lexer,2);
}

public VlogParser(ParserSharedInputState state) {
  super(state,2);
  tokenNames = _tokenNames;
}

	public final void source_text() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			_loop3:
			do {
				if ((LA(1)==LITERAL_module||LA(1)==LITERAL_macromodule||LA(1)==LPAREN)) {
					description();
				}
				else {
					break _loop3;
				}
				
			} while (true);
			}
			match(Token.EOF_TYPE);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
	}
	
	public final void description() throws RecognitionException, TokenStreamException {
		
		ModuleDeclaration mdecl=null;
		
		try {      // for error handling
			mdecl=module_declaration();
			if ( inputState.guessing==0 ) {
				getTree().moduleDeclaration(mdecl);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
	}
	
	public final ModuleDeclaration  module_declaration() throws RecognitionException, TokenStreamException {
		ModuleDeclaration mdecl;
		
			mdecl=null; ModuleIdent mid=null; ListOf<ParameterDeclaration> lopd=null;
			ListOf<PortDeclaration> lports=null; 
			NonPortModuleItem npmi=null; ListOf<Port> lop=null;
			ModuleItem mi=null;
			boolean isAnsi=false;
		
		
		try {      // for error handling
			{
			_loop7:
			do {
				if ((LA(1)==LPAREN)) {
					attribute_instance();
				}
				else {
					break _loop7;
				}
				
			} while (true);
			}
			module_keyword();
			mid=module_identifier();
			if ( inputState.guessing==0 ) {
				getTree().addSymbol(mid);
			}
			{
			switch ( LA(1)) {
			case POUND:
			{
				lopd=module_parameter_port_list();
				break;
			}
			case SEMI:
			case LPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				mdecl = getTree().moduleDeclaration(mid,lopd);
			}
			{
			boolean synPredMatched12 = false;
			if (((LA(1)==SEMI||LA(1)==LPAREN) && (_tokenSet_2.member(LA(2))))) {
				int _m12 = mark();
				synPredMatched12 = true;
				inputState.guessing++;
				try {
					{
					{
					if ((LA(1)==LPAREN)) {
						list_of_port_declarations();
					}
					else {
					}
					
					}
					}
				}
				catch (RecognitionException pe) {
					synPredMatched12 = false;
				}
				rewind(_m12);
inputState.guessing--;
			}
			if ( synPredMatched12 ) {
				{
				switch ( LA(1)) {
				case LPAREN:
				{
					lports=list_of_port_declarations();
					break;
				}
				case SEMI:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				match(SEMI);
				if ( inputState.guessing==0 ) {
					isAnsi=true;
				}
				if ( inputState.guessing==0 ) {
					getTree().moduleDeclaration(mdecl,isAnsi,lports);
				}
				{
				_loop15:
				do {
					if ((_tokenSet_3.member(LA(1)))) {
						npmi=non_port_module_item();
						if ( inputState.guessing==0 ) {
							getTree().moduleDeclaration(mdecl,npmi);
						}
					}
					else {
						break _loop15;
					}
					
				} while (true);
				}
			}
			else if ((LA(1)==LPAREN) && (_tokenSet_4.member(LA(2)))) {
				lop=list_of_ports();
				match(SEMI);
				if ( inputState.guessing==0 ) {
					getTree().moduleDeclaration(mdecl,lop);
				}
				{
				_loop17:
				do {
					if ((_tokenSet_5.member(LA(1)))) {
						mi=module_item();
						if ( inputState.guessing==0 ) {
							getTree().moduleDeclaration(mdecl,mi);
						}
					}
					else {
						break _loop17;
					}
					
				} while (true);
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			match(LITERAL_endmodule);
			if ( inputState.guessing==0 ) {
				getTree().popScope();
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		return mdecl;
	}
	
	public final void attribute_instance() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LPAREN);
			match(STAR);
			attr_spec();
			{
			_loop463:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					attr_spec();
				}
				else {
					break _loop463;
				}
				
			} while (true);
			}
			match(STAR);
			match(RPAREN);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_6);
			} else {
			  throw ex;
			}
		}
	}
	
	public final void module_keyword() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_module:
			{
				match(LITERAL_module);
				break;
			}
			case LITERAL_macromodule:
			{
				match(LITERAL_macromodule);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_7);
			} else {
			  throw ex;
			}
		}
	}
	
	public final ModuleIdent  module_identifier() throws RecognitionException, TokenStreamException {
		ModuleIdent rid;
		
		rid=null; Ident id=null; Token la1 = LT(1);
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_or:
			case LITERAL_and:
			case LITERAL_nand:
			case LITERAL_nor:
			case LITERAL_xor:
			case LITERAL_xnor:
			{
				{
				switch ( LA(1)) {
				case LITERAL_and:
				{
					match(LITERAL_and);
					break;
				}
				case LITERAL_nand:
				{
					match(LITERAL_nand);
					break;
				}
				case LITERAL_nor:
				{
					match(LITERAL_nor);
					break;
				}
				case LITERAL_or:
				{
					match(LITERAL_or);
					break;
				}
				case LITERAL_xor:
				{
					match(LITERAL_xor);
					break;
				}
				case LITERAL_xnor:
				{
					match(LITERAL_xnor);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				if ( inputState.guessing==0 ) {
					la1.setType(IDENT); id = new Ident(la1);		
				}
				break;
			}
			case IDENT:
			case ESCAPED_IDENT:
			{
				id=identifier();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				rid = getTree().moduleIdentifier(id);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		return rid;
	}
	
	public final ListOf<ParameterDeclaration>  module_parameter_port_list() throws RecognitionException, TokenStreamException {
		ListOf<ParameterDeclaration> lopd;
		
		lopd=null;
		
		try {      // for error handling
			match(POUND);
			match(LPAREN);
			lopd=parameter_declarations();
			match(RPAREN);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		return lopd;
	}
	
	public final ListOf<PortDeclaration>  list_of_port_declarations() throws RecognitionException, TokenStreamException {
		ListOf<PortDeclaration> lopd;
		
		lopd=null; PortDeclaration pd=null;
		
		try {      // for error handling
			match(LPAREN);
			{
			switch ( LA(1)) {
			case LPAREN:
			case LITERAL_inout:
			case LITERAL_input:
			case LITERAL_output:
			{
				pd=port_declaration(true);
				if ( inputState.guessing==0 ) {
					lopd = getTree().addToList(lopd,pd);
				}
				{
				_loop30:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						pd=port_declaration(true);
						if ( inputState.guessing==0 ) {
							lopd = getTree().addToList(lopd,pd);
						}
					}
					else {
						break _loop30;
					}
					
				} while (true);
				}
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(RPAREN);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		return lopd;
	}
	
	public final NonPortModuleItem  non_port_module_item() throws RecognitionException, TokenStreamException {
		NonPortModuleItem npmi;
		
		npmi=null; Object o1=null;
		
		try {      // for error handling
			boolean synPredMatched55 = false;
			if (((_tokenSet_11.member(LA(1))) && (_tokenSet_12.member(LA(2))))) {
				int _m55 = mark();
				synPredMatched55 = true;
				inputState.guessing++;
				try {
					{
					module_or_generate_item();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched55 = false;
				}
				rewind(_m55);
inputState.guessing--;
			}
			if ( synPredMatched55 ) {
				o1=module_or_generate_item();
				if ( inputState.guessing==0 ) {
					npmi = getTree().nonPortModuleItem((ModuleOrGenerateItem)o1);
				}
			}
			else if ((LA(1)==LITERAL_generate)) {
				o1=generate_region();
				if ( inputState.guessing==0 ) {
					npmi = getTree().nonPortModuleItem((ListOf<ModuleOrGenerateItem>)o1);
				}
			}
			else if ((LA(1)==LPAREN||LA(1)==LITERAL_parameter||LA(1)==LITERAL_specparam) && (_tokenSet_13.member(LA(2)))) {
				{
				_loop57:
				do {
					if ((LA(1)==LPAREN)) {
						attribute_instance();
					}
					else {
						break _loop57;
					}
					
				} while (true);
				}
				{
				switch ( LA(1)) {
				case LITERAL_parameter:
				{
					o1=parameter_declaration();
					match(SEMI);
					if ( inputState.guessing==0 ) {
						npmi = getTree().nonPortModuleItem((ParameterDeclaration)o1);
					}
					break;
				}
				case LITERAL_specparam:
				{
					o1=specparam_declaration();
					if ( inputState.guessing==0 ) {
						npmi = getTree().nonPortModuleItem((SpecparamDecl)o1);
					}
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_14);
			} else {
			  throw ex;
			}
		}
		return npmi;
	}
	
/**
list_of_ports
:	(LPAREN port)=> LPAREN port (COMMA port)* RPAREN
|	LPAREN RPAREN
;
**/
	public final ListOf<Port>  list_of_ports() throws RecognitionException, TokenStreamException {
		ListOf<Port> lop;
		
		lop=null; Port port=null;
		
		try {      // for error handling
			match(LPAREN);
			{
			if ((_tokenSet_4.member(LA(1))) && (_tokenSet_15.member(LA(2)))) {
				port=port();
				if ( inputState.guessing==0 ) {
					lop=getTree().addToList(lop,port);
				}
				{
				_loop26:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						port=port();
						if ( inputState.guessing==0 ) {
							lop=getTree().addToList(lop,port);
						}
					}
					else {
						break _loop26;
					}
					
				} while (true);
				}
			}
			else if ((LA(1)==RPAREN) && (LA(2)==SEMI)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			match(RPAREN);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		return lop;
	}
	
	public final ModuleItem  module_item() throws RecognitionException, TokenStreamException {
		ModuleItem mi;
		
		mi=null; PortDeclaration pd=null; NonPortModuleItem npi=null;
		
		try {      // for error handling
			boolean synPredMatched47 = false;
			if (((_tokenSet_16.member(LA(1))) && (_tokenSet_17.member(LA(2))))) {
				int _m47 = mark();
				synPredMatched47 = true;
				inputState.guessing++;
				try {
					{
					port_declaration(false);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched47 = false;
				}
				rewind(_m47);
inputState.guessing--;
			}
			if ( synPredMatched47 ) {
				pd=port_declaration(false);
				match(SEMI);
				if ( inputState.guessing==0 ) {
					mi = getTree().moduleItem(pd);
				}
			}
			else if ((_tokenSet_3.member(LA(1))) && (_tokenSet_18.member(LA(2)))) {
				npi=non_port_module_item();
				if ( inputState.guessing==0 ) {
					mi = getTree().moduleItem(npi);
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_14);
			} else {
			  throw ex;
			}
		}
		return mi;
	}
	
	public final ListOf<ParameterDeclaration>  parameter_declarations() throws RecognitionException, TokenStreamException {
		ListOf<ParameterDeclaration> lopd;
		
		lopd=null; ParameterDeclaration pd=null;
		
		try {      // for error handling
			pd=parameter_declaration();
			if ( inputState.guessing==0 ) {
				lopd=getTree().addToList(lopd,pd);
			}
			{
			_loop22:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					pd=parameter_declaration();
					if ( inputState.guessing==0 ) {
						lopd=getTree().addToList(lopd,pd);
					}
				}
				else {
					break _loop22;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_19);
			} else {
			  throw ex;
			}
		}
		return lopd;
	}
	
	public final ParameterDeclaration  parameter_declaration() throws RecognitionException, TokenStreamException {
		ParameterDeclaration pd;
		
		pd=null; boolean isSigned=false; Range rng=null; 
		ListOf<ParamAssign> lopa=null; ParameterType pt=null;
		
		
		try {      // for error handling
			{
			if ((LA(1)==LITERAL_parameter) && (_tokenSet_20.member(LA(2)))) {
				match(LITERAL_parameter);
				{
				switch ( LA(1)) {
				case LITERAL_signed:
				{
					match(LITERAL_signed);
					if ( inputState.guessing==0 ) {
						isSigned=true;
					}
					break;
				}
				case LBRACK:
				case IDENT:
				case ESCAPED_IDENT:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				{
				switch ( LA(1)) {
				case LBRACK:
				{
					rng=range();
					break;
				}
				case IDENT:
				case ESCAPED_IDENT:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				lopa=list_of_param_assignments();
			}
			else if ((LA(1)==LITERAL_parameter) && ((LA(2) >= LITERAL_integer && LA(2) <= LITERAL_time))) {
				match(LITERAL_parameter);
				pt=parameter_type();
				lopa=list_of_param_assignments();
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
				pd = getTree().parameterDecl(isSigned,rng,pt,lopa);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		return pd;
	}
	
	public final Port  port() throws RecognitionException, TokenStreamException {
		Port port;
		
		port=null; PortExpression pexpr=null; PortIdent pid=null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case RPAREN:
			case COMMA:
			case LCURLY:
			case IDENT:
			case ESCAPED_IDENT:
			{
				{
				switch ( LA(1)) {
				case LCURLY:
				case IDENT:
				case ESCAPED_IDENT:
				{
					pexpr=port_expression();
					break;
				}
				case RPAREN:
				case COMMA:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				break;
			}
			case DOT:
			{
				match(DOT);
				pid=port_identifier();
				match(LPAREN);
				{
				switch ( LA(1)) {
				case LCURLY:
				case IDENT:
				case ESCAPED_IDENT:
				{
					pexpr=port_expression();
					break;
				}
				case RPAREN:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				match(RPAREN);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				port = getTree().port(pid,pexpr);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_22);
			} else {
			  throw ex;
			}
		}
		return port;
	}
	
	public final PortDeclaration  port_declaration(
		boolean isOnlyDefn
	) throws RecognitionException, TokenStreamException {
		PortDeclaration pd;
		
		pd=null;
		
		try {      // for error handling
			{
			_loop43:
			do {
				if ((LA(1)==LPAREN)) {
					attribute_instance();
				}
				else {
					break _loop43;
				}
				
			} while (true);
			}
			{
			switch ( LA(1)) {
			case LITERAL_inout:
			{
				pd=inout_declaration(isOnlyDefn);
				break;
			}
			case LITERAL_input:
			{
				pd=input_declaration(isOnlyDefn);
				break;
			}
			case LITERAL_output:
			{
				pd=output_declaration(isOnlyDefn);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		return pd;
	}
	
	public final PortExpression  port_expression() throws RecognitionException, TokenStreamException {
		PortExpression pexpr;
		
		pexpr=null; PortReference pref=null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case IDENT:
			case ESCAPED_IDENT:
			{
				pref=port_reference();
				if ( inputState.guessing==0 ) {
					pexpr = getTree().portExpression(null,pref);
				}
				break;
			}
			case LCURLY:
			{
				match(LCURLY);
				pref=port_reference();
				if ( inputState.guessing==0 ) {
					pexpr = getTree().portExpression(null,pref);
				}
				{
				_loop37:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						pref=port_reference();
						if ( inputState.guessing==0 ) {
							getTree().portExpression(pexpr,pref);
						}
					}
					else {
						break _loop37;
					}
					
				} while (true);
				}
				match(RCURLY);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_22);
			} else {
			  throw ex;
			}
		}
		return pexpr;
	}
	
	public final PortIdent  port_identifier() throws RecognitionException, TokenStreamException {
		PortIdent rid;
		
		rid=null; Ident id=null;
		
		try {      // for error handling
			id=identifier();
			if ( inputState.guessing==0 ) {
				rid = getTree().portIdentifier(id);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_23);
			} else {
			  throw ex;
			}
		}
		return rid;
	}
	
	public final PortReference  port_reference() throws RecognitionException, TokenStreamException {
		PortReference pref;
		
		pref=null; PortIdent pid=null; ConstRangeExpression rexpr=null;
		
		try {      // for error handling
			pid=port_identifier();
			{
			switch ( LA(1)) {
			case LBRACK:
			{
				match(LBRACK);
				{
				switch ( LA(1)) {
				case LPAREN:
				case LCURLY:
				case NUMBER:
				case STRING:
				case IDENT:
				case ESCAPED_IDENT:
				case SYSTEM_TASK_NAME:
				case MINUS:
				case PLUS:
				case LNOT:
				case BNOT:
				case BAND:
				case RNAND:
				case BOR:
				case RNOR:
				case BXOR:
				case RXNOR:
				{
					rexpr=constant_range_expression();
					break;
				}
				case RBRACK:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				match(RBRACK);
				break;
			}
			case RPAREN:
			case COMMA:
			case RCURLY:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				pref = getTree().portReference(pid,rexpr);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_24);
			} else {
			  throw ex;
			}
		}
		return pref;
	}
	
	public final ConstRangeExpression  constant_range_expression() throws RecognitionException, TokenStreamException {
		ConstRangeExpression cre;
		
		cre=null; ConstExpression ce1=null, ce2=null; int op=-1;
		
		try {      // for error handling
			ce1=constant_expression();
			{
			switch ( LA(1)) {
			case COLON:
			case PLUS_COLON:
			case MINUS_COLON:
			{
				if ( inputState.guessing==0 ) {
					op=LA(1);
				}
				{
				switch ( LA(1)) {
				case PLUS_COLON:
				{
					match(PLUS_COLON);
					break;
				}
				case MINUS_COLON:
				{
					match(MINUS_COLON);
					break;
				}
				case COLON:
				{
					match(COLON);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				ce2=constant_expression();
				break;
			}
			case RBRACK:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				cre = getTree().constantRangeExpression(ce1,op,ce2);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		return cre;
	}
	
	public final PortDeclaration  inout_declaration(
		boolean isOnlyDefn
	) throws RecognitionException, TokenStreamException {
		PortDeclaration pd;
		
		pd=null; int dir=LA(1); NetType type=null; Range rng=null;
		boolean isSigned=false;
		ListOf<? extends PortIdent> ids = null;
		
		
		try {      // for error handling
			match(LITERAL_inout);
			{
			switch ( LA(1)) {
			case 33:
			case 34:
			case LITERAL_tri:
			case LITERAL_triand:
			case LITERAL_trior:
			case 38:
			case 39:
			case LITERAL_uwire:
			case LITERAL_wire:
			case LITERAL_wand:
			case LITERAL_wor:
			{
				type=net_type();
				break;
			}
			case LBRACK:
			case LITERAL_signed:
			case IDENT:
			case ESCAPED_IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case LITERAL_signed:
			{
				match(LITERAL_signed);
				if ( inputState.guessing==0 ) {
					isSigned=true;
				}
				break;
			}
			case LBRACK:
			case IDENT:
			case ESCAPED_IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case LBRACK:
			{
				rng=range();
				break;
			}
			case IDENT:
			case ESCAPED_IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			ids=list_of_port_identifiers();
			if ( inputState.guessing==0 ) {
				pd = getTree().portDecl(isOnlyDefn,dir,type,rng,null,false,isSigned,ids);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		return pd;
	}
	
	public final PortDeclaration  input_declaration(
		boolean isOnlyDefn
	) throws RecognitionException, TokenStreamException {
		PortDeclaration pd;
		
		pd=null; int dir=LA(1); NetType type=null; Range rng=null;
		boolean isSigned=false;
		ListOf<? extends PortIdent> ids = null;
		
		
		try {      // for error handling
			match(LITERAL_input);
			{
			switch ( LA(1)) {
			case 33:
			case 34:
			case LITERAL_tri:
			case LITERAL_triand:
			case LITERAL_trior:
			case 38:
			case 39:
			case LITERAL_uwire:
			case LITERAL_wire:
			case LITERAL_wand:
			case LITERAL_wor:
			{
				type=net_type();
				break;
			}
			case LBRACK:
			case LITERAL_signed:
			case IDENT:
			case ESCAPED_IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case LITERAL_signed:
			{
				match(LITERAL_signed);
				if ( inputState.guessing==0 ) {
					isSigned=true;
				}
				break;
			}
			case LBRACK:
			case IDENT:
			case ESCAPED_IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case LBRACK:
			{
				rng=range();
				break;
			}
			case IDENT:
			case ESCAPED_IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			ids=list_of_port_identifiers();
			if ( inputState.guessing==0 ) {
				pd = getTree().portDecl(isOnlyDefn,dir,type,rng,null,false,isSigned,ids);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		return pd;
	}
	
	public final PortDeclaration  output_declaration(
		boolean isOnlyDefn
	) throws RecognitionException, TokenStreamException {
		PortDeclaration pd;
		
		pd=null; int dir=LA(1); NetType type=null; Range rng=null;
		boolean isReg=false; boolean isSigned=false;
		ListOf<? extends PortIdent> ids = null; OutputVarType ovt=null;
		
		
		try {      // for error handling
			{
			boolean synPredMatched82 = false;
			if (((LA(1)==LITERAL_output) && (LA(2)==LITERAL_integer||LA(2)==LITERAL_time))) {
				int _m82 = mark();
				synPredMatched82 = true;
				inputState.guessing++;
				try {
					{
					match(LITERAL_output);
					output_variable_type();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched82 = false;
				}
				rewind(_m82);
inputState.guessing--;
			}
			if ( synPredMatched82 ) {
				match(LITERAL_output);
				ovt=output_variable_type();
				ids=list_of_variable_port_identifiers();
			}
			else {
				boolean synPredMatched87 = false;
				if (((LA(1)==LITERAL_output) && (_tokenSet_26.member(LA(2))))) {
					int _m87 = mark();
					synPredMatched87 = true;
					inputState.guessing++;
					try {
						{
						match(LITERAL_output);
						{
						switch ( LA(1)) {
						case LITERAL_reg:
						{
							match(LITERAL_reg);
							break;
						}
						case LBRACK:
						case LITERAL_signed:
						case IDENT:
						case ESCAPED_IDENT:
						{
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
						{
						switch ( LA(1)) {
						case LITERAL_signed:
						{
							match(LITERAL_signed);
							break;
						}
						case LBRACK:
						case IDENT:
						case ESCAPED_IDENT:
						{
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
						{
						switch ( LA(1)) {
						case LBRACK:
						{
							range();
							break;
						}
						case IDENT:
						case ESCAPED_IDENT:
						{
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
						list_of_variable_port_identifiers();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched87 = false;
					}
					rewind(_m87);
inputState.guessing--;
				}
				if ( synPredMatched87 ) {
					match(LITERAL_output);
					{
					switch ( LA(1)) {
					case LITERAL_reg:
					{
						match(LITERAL_reg);
						if ( inputState.guessing==0 ) {
							isReg=true;
						}
						break;
					}
					case LBRACK:
					case LITERAL_signed:
					case IDENT:
					case ESCAPED_IDENT:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					{
					switch ( LA(1)) {
					case LITERAL_signed:
					{
						match(LITERAL_signed);
						if ( inputState.guessing==0 ) {
							isSigned=true;
						}
						break;
					}
					case LBRACK:
					case IDENT:
					case ESCAPED_IDENT:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					{
					switch ( LA(1)) {
					case LBRACK:
					{
						rng=range();
						break;
					}
					case IDENT:
					case ESCAPED_IDENT:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					ids=list_of_variable_port_identifiers();
				}
				else if ((LA(1)==LITERAL_output) && (_tokenSet_27.member(LA(2)))) {
					match(LITERAL_output);
					{
					switch ( LA(1)) {
					case 33:
					case 34:
					case LITERAL_tri:
					case LITERAL_triand:
					case LITERAL_trior:
					case 38:
					case 39:
					case LITERAL_uwire:
					case LITERAL_wire:
					case LITERAL_wand:
					case LITERAL_wor:
					{
						type=net_type();
						break;
					}
					case LBRACK:
					case LITERAL_signed:
					case IDENT:
					case ESCAPED_IDENT:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					{
					switch ( LA(1)) {
					case LITERAL_signed:
					{
						match(LITERAL_signed);
						if ( inputState.guessing==0 ) {
							isSigned=true;
						}
						break;
					}
					case LBRACK:
					case IDENT:
					case ESCAPED_IDENT:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					{
					switch ( LA(1)) {
					case LBRACK:
					{
						rng=range();
						break;
					}
					case IDENT:
					case ESCAPED_IDENT:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					ids=list_of_port_identifiers();
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				if ( inputState.guessing==0 ) {
					pd = getTree().portDecl(isOnlyDefn,dir,type,rng,ovt,isReg,isSigned,ids);
				}
			}
			catch (RecognitionException ex) {
				if (inputState.guessing==0) {
					reportError(ex);
					recover(ex,_tokenSet_21);
				} else {
				  throw ex;
				}
			}
			return pd;
		}
		
	public final ModuleOrGenerateItem  module_or_generate_item() throws RecognitionException, TokenStreamException {
		ModuleOrGenerateItem mi;
		
		mi=null; Object o1=null;
		
		try {      // for error handling
			{
			_loop50:
			do {
				if ((LA(1)==LPAREN)) {
					attribute_instance();
				}
				else {
					break _loop50;
				}
				
			} while (true);
			}
			{
			switch ( LA(1)) {
			case LITERAL_integer:
			case LITERAL_real:
			case LITERAL_realtime:
			case LITERAL_time:
			case LITERAL_reg:
			case LITERAL_event:
			case 33:
			case 34:
			case LITERAL_tri:
			case LITERAL_triand:
			case LITERAL_trior:
			case 38:
			case 39:
			case LITERAL_uwire:
			case LITERAL_wire:
			case LITERAL_wand:
			case LITERAL_wor:
			case LITERAL_function:
			case LITERAL_task:
			case LITERAL_genvar:
			{
				o1=module_or_generate_item_declaration();
				if ( inputState.guessing==0 ) {
					mi = getTree().moduleOrGenItem((ModuleOrGenerateItemDecl)o1);
				}
				break;
			}
			case LITERAL_localparam:
			{
				o1=local_parameter_declaration();
				match(SEMI);
				if ( inputState.guessing==0 ) {
					mi = getTree().moduleOrGenItem((LocalParameterDecl)o1);
				}
				break;
			}
			case LITERAL_defparam:
			{
				o1=parameter_override();
				if ( inputState.guessing==0 ) {
					mi = getTree().moduleOrGenItem((ListOf<DefparamAssign>)o1, true);
				}
				break;
			}
			case LITERAL_assign:
			{
				o1=continuous_assign();
				if ( inputState.guessing==0 ) {
					mi = getTree().moduleOrGenItem((ContinuousAssign)o1);
				}
				break;
			}
			case LITERAL_or:
			case IDENT:
			case ESCAPED_IDENT:
			case LITERAL_and:
			case LITERAL_nand:
			case LITERAL_nor:
			case LITERAL_xor:
			case LITERAL_xnor:
			{
				o1=module_instantiation();
				if ( inputState.guessing==0 ) {
					mi = getTree().moduleOrGenItem((ListOf<ModuleInstance>)o1);
				}
				break;
			}
			case LITERAL_initial:
			{
				o1=initial_construct();
				if ( inputState.guessing==0 ) {
					mi = getTree().moduleOrGenItem((InitialConstruct)o1);
				}
				break;
			}
			case LITERAL_always:
			{
				o1=always_construct();
				if ( inputState.guessing==0 ) {
					mi = getTree().moduleOrGenItem((AlwaysConstruct)o1);
				}
				break;
			}
			case LITERAL_for:
			{
				o1=loop_generate_construct();
				if ( inputState.guessing==0 ) {
					mi = getTree().moduleOrGenItem((LoopGenerateConstruct)o1);
				}
				break;
			}
			case LITERAL_if:
			case LITERAL_case:
			{
				o1=conditional_generate_construct();
				if ( inputState.guessing==0 ) {
					mi = getTree().moduleOrGenItem((ConditionalGenerateConstruct)o1);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		return mi;
	}
	
	public final ModuleOrGenerateItemDecl  module_or_generate_item_declaration() throws RecognitionException, TokenStreamException {
		ModuleOrGenerateItemDecl mogd;
		
		mogd=null; Object o1=null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case 33:
			case 34:
			case LITERAL_tri:
			case LITERAL_triand:
			case LITERAL_trior:
			case 38:
			case 39:
			case LITERAL_uwire:
			case LITERAL_wire:
			case LITERAL_wand:
			case LITERAL_wor:
			{
				o1=net_declaration();
				if ( inputState.guessing==0 ) {
					mogd = getTree().moduleOrGenItemDecl((NetDeclaration)o1);
				}
				break;
			}
			case LITERAL_reg:
			{
				o1=reg_declaration();
				if ( inputState.guessing==0 ) {
					mogd = getTree().moduleOrGenItemDecl((RegDecl)o1);
				}
				break;
			}
			case LITERAL_integer:
			{
				o1=integer_declaration();
				if ( inputState.guessing==0 ) {
					mogd = getTree().moduleOrGenItemDecl((IntegerDecl)o1);
				}
				break;
			}
			case LITERAL_real:
			{
				o1=real_declaration();
				if ( inputState.guessing==0 ) {
					mogd = getTree().moduleOrGenItemDecl((RealDecl)o1);
				}
				break;
			}
			case LITERAL_time:
			{
				o1=time_declaration();
				if ( inputState.guessing==0 ) {
					mogd = getTree().moduleOrGenItemDecl((TimeDecl)o1);
				}
				break;
			}
			case LITERAL_realtime:
			{
				o1=realtime_declaration();
				if ( inputState.guessing==0 ) {
					mogd = getTree().moduleOrGenItemDecl((RealtimeDecl)o1);
				}
				break;
			}
			case LITERAL_event:
			{
				o1=event_declaration();
				if ( inputState.guessing==0 ) {
					mogd = getTree().moduleOrGenItemDecl((EventDecl)o1);
				}
				break;
			}
			case LITERAL_genvar:
			{
				o1=genvar_declaration();
				if ( inputState.guessing==0 ) {
					mogd = getTree().moduleOrGenItemDecl((ListOf<GenvarIdent>)o1);
				}
				break;
			}
			case LITERAL_task:
			{
				o1=task_declaration();
				if ( inputState.guessing==0 ) {
					mogd = getTree().moduleOrGenItemDecl((TaskDeclaration)o1);
				}
				break;
			}
			case LITERAL_function:
			{
				o1=function_declaration();
				if ( inputState.guessing==0 ) {
					mogd = getTree().moduleOrGenItemDecl((FuncDecl)o1);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		return mogd;
	}
	
	public final LocalParameterDecl  local_parameter_declaration() throws RecognitionException, TokenStreamException {
		LocalParameterDecl lpd;
		
		lpd=null; boolean isSigned=false; Range rng=null; 
		ListOf<ParamAssign> lopa=null; ParameterType pt=null;
		
		
		try {      // for error handling
			{
			if ((LA(1)==LITERAL_localparam) && (_tokenSet_20.member(LA(2)))) {
				match(LITERAL_localparam);
				{
				switch ( LA(1)) {
				case LITERAL_signed:
				{
					match(LITERAL_signed);
					if ( inputState.guessing==0 ) {
						isSigned=true;
					}
					break;
				}
				case LBRACK:
				case IDENT:
				case ESCAPED_IDENT:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				{
				switch ( LA(1)) {
				case LBRACK:
				{
					rng=range();
					break;
				}
				case IDENT:
				case ESCAPED_IDENT:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				lopa=list_of_param_assignments();
			}
			else if ((LA(1)==LITERAL_localparam) && ((LA(2) >= LITERAL_integer && LA(2) <= LITERAL_time))) {
				match(LITERAL_localparam);
				pt=parameter_type();
				lopa=list_of_param_assignments();
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
				lpd = getTree().localParameterDecl(isSigned,rng,pt,lopa);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		return lpd;
	}
	
	public final ListOf<DefparamAssign>  parameter_override() throws RecognitionException, TokenStreamException {
		ListOf<DefparamAssign> lopa;
		
		lopa=null;
		
		try {      // for error handling
			match(LITERAL_defparam);
			lopa=list_of_defparam_assignments();
			match(SEMI);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		return lopa;
	}
	
	public final ContinuousAssign  continuous_assign() throws RecognitionException, TokenStreamException {
		ContinuousAssign ca;
		
		ca=null; Object d3=null, na=null;
		
		try {      // for error handling
			match(LITERAL_assign);
			{
			switch ( LA(1)) {
			case POUND:
			{
				d3=delay3();
				break;
			}
			case LCURLY:
			case IDENT:
			case ESCAPED_IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			na=list_of_net_assignments();
			match(SEMI);
			if ( inputState.guessing==0 ) {
				ca = getTree().continuousAssign(d3, na);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		return ca;
	}
	
	public final ListOf<ModuleInstance>  module_instantiation() throws RecognitionException, TokenStreamException {
		ListOf<ModuleInstance> lom;
		
			lom=null; Ident refNm = null; ParameterValueAssignment pva=null;
			ModuleInstance mi=null;
		
		
		try {      // for error handling
			refNm=module_identifier();
			{
			boolean synPredMatched246 = false;
			if (((LA(1)==POUND) && (LA(2)==NUMBER||LA(2)==IDENT||LA(2)==ESCAPED_IDENT))) {
				int _m246 = mark();
				synPredMatched246 = true;
				inputState.guessing++;
				try {
					{
					match(POUND);
					delay_value();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched246 = false;
				}
				rewind(_m246);
inputState.guessing--;
			}
			if ( synPredMatched246 ) {
				match(POUND);
				delay_value();
			}
			else if ((LA(1)==POUND) && (LA(2)==LPAREN)) {
				pva=parameter_value_assignment();
			}
			else if ((LA(1)==LPAREN||LA(1)==IDENT||LA(1)==ESCAPED_IDENT)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			mi=module_instance();
			if ( inputState.guessing==0 ) {
				lom = getTree().moduleInstantiation(lom,refNm,pva,mi);
			}
			{
			_loop248:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					mi=module_instance();
					if ( inputState.guessing==0 ) {
						lom = getTree().moduleInstantiation(lom,refNm,pva,mi);
					}
				}
				else {
					break _loop248;
				}
				
			} while (true);
			}
			match(SEMI);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		return lom;
	}
	
	public final InitialConstruct  initial_construct() throws RecognitionException, TokenStreamException {
		InitialConstruct inc;
		
		inc=null; Statement st=null;
		
		try {      // for error handling
			match(LITERAL_initial);
			st=statement();
			if ( inputState.guessing==0 ) {
				inc = getTree().initialConstruct(st);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		return inc;
	}
	
	public final AlwaysConstruct  always_construct() throws RecognitionException, TokenStreamException {
		AlwaysConstruct awc;
		
		awc=null; Statement st=null;
		
		try {      // for error handling
			match(LITERAL_always);
			st=statement();
			if ( inputState.guessing==0 ) {
				awc = getTree().alwaysConstruct(st);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		return awc;
	}
	
	public final LoopGenerateConstruct  loop_generate_construct() throws RecognitionException, TokenStreamException {
		LoopGenerateConstruct lgc;
		
		lgc=null; GenvarInit init=null; GenvarExpression test=null;
		GenvarIteration iter=null; GenerateBlock blk=null;
		
		try {      // for error handling
			match(LITERAL_for);
			match(LPAREN);
			init=genvar_initialization();
			match(SEMI);
			test=genvar_expression();
			match(SEMI);
			iter=genvar_iteration();
			match(RPAREN);
			blk=generate_block();
			if ( inputState.guessing==0 ) {
				lgc = getTree().loopGenerateConstruct(init,test,iter,blk);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		return lgc;
	}
	
	public final ConditionalGenerateConstruct  conditional_generate_construct() throws RecognitionException, TokenStreamException {
		ConditionalGenerateConstruct cgc;
		
		cgc=null; IfGenerateConstruct igc=null; CaseGenerateConstruct cg=null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_if:
			{
				igc=if_generate_construct();
				break;
			}
			case LITERAL_case:
			{
				cg=case_generate_construct();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				cgc = getTree().conditionalGenerateConstruct(igc,cg);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		return cgc;
	}
	
	public final NetDeclaration  net_declaration() throws RecognitionException, TokenStreamException {
		NetDeclaration nd;
		
		nd=null; NetType type=null; boolean isSigned=false; Range rng=null;
		Delay3 del3=null; ListOf<NetIdentifiers> nets=null; 
		ListOf<NetDeclAssign> decls=null;
		
		
		try {      // for error handling
			type=net_type();
			{
			switch ( LA(1)) {
			case LITERAL_vectored:
			{
				match(LITERAL_vectored);
				break;
			}
			case LITERAL_scalared:
			{
				match(LITERAL_scalared);
				break;
			}
			case POUND:
			case LBRACK:
			case LITERAL_signed:
			case IDENT:
			case ESCAPED_IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case LITERAL_signed:
			{
				match(LITERAL_signed);
				if ( inputState.guessing==0 ) {
					isSigned=true;
				}
				break;
			}
			case POUND:
			case LBRACK:
			case IDENT:
			case ESCAPED_IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case LBRACK:
			{
				rng=range();
				break;
			}
			case POUND:
			case IDENT:
			case ESCAPED_IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case POUND:
			{
				del3=delay3();
				break;
			}
			case IDENT:
			case ESCAPED_IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			if ((LA(1)==IDENT||LA(1)==ESCAPED_IDENT) && (LA(2)==SEMI||LA(2)==COMMA||LA(2)==LBRACK)) {
				nets=list_of_net_identifiers();
			}
			else if ((LA(1)==IDENT||LA(1)==ESCAPED_IDENT) && (LA(2)==ASSIGN)) {
				decls=list_of_net_decl_assignments();
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			match(SEMI);
			if ( inputState.guessing==0 ) {
				nd=getTree().netDeclaration(type,isSigned,rng,del3,nets,decls);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		return nd;
	}
	
	public final RegDecl  reg_declaration() throws RecognitionException, TokenStreamException {
		RegDecl rd;
		
		rd=null; boolean isSigned=false; Range rng=null; 
		ListOf<VariableType> vid=null;
		
		try {      // for error handling
			match(LITERAL_reg);
			{
			switch ( LA(1)) {
			case LITERAL_signed:
			{
				match(LITERAL_signed);
				if ( inputState.guessing==0 ) {
					isSigned=true;
				}
				break;
			}
			case LBRACK:
			case IDENT:
			case ESCAPED_IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case LBRACK:
			{
				rng=range();
				break;
			}
			case IDENT:
			case ESCAPED_IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			vid=list_of_variable_identifiers();
			match(SEMI);
			if ( inputState.guessing==0 ) {
				rd=getTree().regDecl(isSigned,rng,vid);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		return rd;
	}
	
	public final IntegerDecl  integer_declaration() throws RecognitionException, TokenStreamException {
		IntegerDecl intd;
		
		intd=null; ListOf<VariableType> lovt=null;
		
		try {      // for error handling
			match(LITERAL_integer);
			lovt=list_of_variable_identifiers();
			match(SEMI);
			if ( inputState.guessing==0 ) {
				intd = getTree().integerDecl(lovt);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		return intd;
	}
	
	public final RealDecl  real_declaration() throws RecognitionException, TokenStreamException {
		RealDecl rd;
		
		rd=null; ListOf<RealType> lort=null;
		
		try {      // for error handling
			match(LITERAL_real);
			lort=list_of_real_identifiers();
			match(SEMI);
			if ( inputState.guessing==0 ) {
				rd=getTree().realDecl(lort);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		return rd;
	}
	
	public final TimeDecl  time_declaration() throws RecognitionException, TokenStreamException {
		TimeDecl td;
		
		td=null; ListOf<VariableType> vid=null;
		
		try {      // for error handling
			match(LITERAL_time);
			vid=list_of_variable_identifiers();
			match(SEMI);
			if ( inputState.guessing==0 ) {
				td = getTree().timeDecl(vid);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		return td;
	}
	
	public final RealtimeDecl  realtime_declaration() throws RecognitionException, TokenStreamException {
		RealtimeDecl rtd;
		
		rtd=null; ListOf<RealType> lort=null;
		
		try {      // for error handling
			match(LITERAL_realtime);
			lort=list_of_real_identifiers();
			match(SEMI);
			if ( inputState.guessing==0 ) {
				rtd=getTree().realtimeDecl(lort);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		return rtd;
	}
	
	public final EventDecl  event_declaration() throws RecognitionException, TokenStreamException {
		EventDecl ed;
		
		ed=null; ListOf<EventIdentifiers> loei=null;
		
		try {      // for error handling
			match(LITERAL_event);
			loei=list_of_event_identifiers();
			match(SEMI);
			if ( inputState.guessing==0 ) {
				ed = getTree().eventDecl(loei);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_29);
			} else {
			  throw ex;
			}
		}
		return ed;
	}
	
	public final ListOf<GenvarIdent>  genvar_declaration() throws RecognitionException, TokenStreamException {
		ListOf<GenvarIdent> loi;
		
		loi=null;
		
		try {      // for error handling
			match(LITERAL_genvar);
			loi=list_of_genvar_identifiers();
			match(SEMI);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		return loi;
	}
	
	public final TaskDeclaration  task_declaration() throws RecognitionException, TokenStreamException {
		TaskDeclaration td;
		
		td=null; TaskIdent id=null; boolean isAuto=false;
		TaskItemDecl tid=null; BlockItemDecl bid=null;
		ListOf<TfPortDeclaration> lop=null; Statement st=null;
		
		
		try {      // for error handling
			match(LITERAL_task);
			{
			switch ( LA(1)) {
			case LITERAL_automatic:
			{
				match(LITERAL_automatic);
				if ( inputState.guessing==0 ) {
					isAuto=true;
				}
				break;
			}
			case IDENT:
			case ESCAPED_IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			id=task_identifier();
			if ( inputState.guessing==0 ) {
				td = getTree().taskDecl(isAuto, id);
			}
			{
			switch ( LA(1)) {
			case SEMI:
			{
				match(SEMI);
				{
				_loop200:
				do {
					if ((_tokenSet_30.member(LA(1))) && (_tokenSet_31.member(LA(2)))) {
						tid=task_item_declaration();
						if ( inputState.guessing==0 ) {
							getTree().taskDecl(td, tid);
						}
					}
					else {
						break _loop200;
					}
					
				} while (true);
				}
				break;
			}
			case LPAREN:
			{
				match(LPAREN);
				{
				switch ( LA(1)) {
				case LPAREN:
				case LITERAL_inout:
				case LITERAL_input:
				case LITERAL_output:
				{
					lop=task_port_list();
					if ( inputState.guessing==0 ) {
						getTree().taskDecl(td,lop);
					}
					break;
				}
				case RPAREN:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				match(RPAREN);
				match(SEMI);
				{
				_loop203:
				do {
					if ((_tokenSet_32.member(LA(1))) && (_tokenSet_13.member(LA(2)))) {
						bid=block_item_declaration();
						if ( inputState.guessing==0 ) {
							getTree().taskDecl(td, bid);
						}
					}
					else {
						break _loop203;
					}
					
				} while (true);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			st=statement_or_null();
			if ( inputState.guessing==0 ) {
				getTree().taskDecl(td, st);
			}
			match(LITERAL_endtask);
			if ( inputState.guessing==0 ) {
				getTree().popScope();
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		return td;
	}
	
	public final FuncDecl  function_declaration() throws RecognitionException, TokenStreamException {
		FuncDecl fd;
		
		fd=null; boolean isAuto=false; FuncType ft=null; FuncIdent id=null;
			FuncItemDecl fid=null; ListOf<FuncItemDecl> lofid=null;
			ListOf<TfPortDeclaration> lop=null; 
			BlockItemDecl bid=null; ListOf<BlockItemDecl> lobid=null;
			FuncStatement stmt=null;
		
		
		try {      // for error handling
			match(LITERAL_function);
			{
			switch ( LA(1)) {
			case LITERAL_automatic:
			{
				match(LITERAL_automatic);
				if ( inputState.guessing==0 ) {
					isAuto=true;
				}
				break;
			}
			case LBRACK:
			case LITERAL_signed:
			case LITERAL_integer:
			case LITERAL_real:
			case LITERAL_realtime:
			case LITERAL_time:
			case IDENT:
			case ESCAPED_IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			if ((_tokenSet_33.member(LA(1))) && (_tokenSet_34.member(LA(2)))) {
				ft=function_range_or_type();
			}
			else if ((LA(1)==IDENT||LA(1)==ESCAPED_IDENT) && (LA(2)==SEMI||LA(2)==LPAREN)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			id=function_identifier();
			{
			switch ( LA(1)) {
			case SEMI:
			{
				match(SEMI);
				{
				int _cnt174=0;
				_loop174:
				do {
					if ((_tokenSet_35.member(LA(1))) && (_tokenSet_31.member(LA(2)))) {
						fid=function_item_declaration();
						if ( inputState.guessing==0 ) {
							lofid=getTree().addToList(lofid,fid);
						}
					}
					else {
						if ( _cnt174>=1 ) { break _loop174; } else {throw new NoViableAltException(LT(1), getFilename());}
					}
					
					_cnt174++;
				} while (true);
				}
				break;
			}
			case LPAREN:
			{
				match(LPAREN);
				lop=function_port_list();
				match(RPAREN);
				match(SEMI);
				{
				_loop176:
				do {
					if ((_tokenSet_32.member(LA(1))) && (_tokenSet_13.member(LA(2)))) {
						bid=block_item_declaration();
						if ( inputState.guessing==0 ) {
							lobid=getTree().addToList(lobid,bid);
						}
					}
					else {
						break _loop176;
					}
					
				} while (true);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			stmt=function_statement();
			match(LITERAL_endfunction);
			if ( inputState.guessing==0 ) {
					fd = getTree().funcDecl(isAuto,ft,id,lofid,lop,lobid,stmt);
							 	getTree().popScope();
							
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		return fd;
	}
	
	public final ListOf<ModuleOrGenerateItem>  generate_region() throws RecognitionException, TokenStreamException {
		ListOf<ModuleOrGenerateItem> loi;
		
		loi=null; ModuleOrGenerateItem mi=null;
		
		try {      // for error handling
			match(LITERAL_generate);
			{
			_loop281:
			do {
				if ((_tokenSet_11.member(LA(1)))) {
					mi=module_or_generate_item();
					if ( inputState.guessing==0 ) {
						loi = getTree().addToList(loi, mi);
					}
				}
				else {
					break _loop281;
				}
				
			} while (true);
			}
			match(LITERAL_endgenerate);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_14);
			} else {
			  throw ex;
			}
		}
		return loi;
	}
	
	public final SpecparamDecl  specparam_declaration() throws RecognitionException, TokenStreamException {
		SpecparamDecl spd;
		
		spd=null; Range rng=null; ListOf<SpecparamAssign> spa=null;
		
		try {      // for error handling
			match(LITERAL_specparam);
			{
			switch ( LA(1)) {
			case LBRACK:
			{
				rng=range();
				break;
			}
			case IDENT:
			case ESCAPED_IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			spa=list_of_specparam_assignments();
			match(SEMI);
			if ( inputState.guessing==0 ) {
				spd = getTree().specparamDecl(rng,spa);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_14);
			} else {
			  throw ex;
			}
		}
		return spd;
	}
	
	public final ListOf<DefparamAssign>  list_of_defparam_assignments() throws RecognitionException, TokenStreamException {
		ListOf<DefparamAssign> loda;
		
		loda=null; DefparamAssign da=null;
		
		try {      // for error handling
			da=defparam_assignment();
			if ( inputState.guessing==0 ) {
				loda=getTree().addToList(loda,da);
			}
			{
			_loop127:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					da=defparam_assignment();
					if ( inputState.guessing==0 ) {
						loda=getTree().addToList(loda,da);
					}
				}
				else {
					break _loop127;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		return loda;
	}
	
	public final Range  range() throws RecognitionException, TokenStreamException {
		Range rng;
		
		rng=null; ConstExpression msb=null, lsb=null;
		
		try {      // for error handling
			match(LBRACK);
			msb=msb_constant_expression();
			match(COLON);
			lsb=lsb_constant_expression();
			match(RBRACK);
			if ( inputState.guessing==0 ) {
				rng = getTree().range(msb,lsb);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_36);
			} else {
			  throw ex;
			}
		}
		return rng;
	}
	
	public final ListOf<ParamAssign>  list_of_param_assignments() throws RecognitionException, TokenStreamException {
		ListOf<ParamAssign> lopa;
		
		lopa=null; ParamAssign pa=null;
		
		try {      // for error handling
			pa=param_assignment();
			if ( inputState.guessing==0 ) {
				lopa=getTree().addToList(lopa,pa);
			}
			{
			_loop145:
			do {
				if ((LA(1)==COMMA) && (LA(2)==IDENT||LA(2)==ESCAPED_IDENT)) {
					match(COMMA);
					pa=param_assignment();
					if ( inputState.guessing==0 ) {
						lopa=getTree().addToList(lopa,pa);
					}
				}
				else {
					break _loop145;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		return lopa;
	}
	
	public final ParameterType  parameter_type() throws RecognitionException, TokenStreamException {
		ParameterType pt;
		
		pt = getTree().parameterType(LA(1));
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_integer:
			{
				match(LITERAL_integer);
				break;
			}
			case LITERAL_real:
			{
				match(LITERAL_real);
				break;
			}
			case LITERAL_realtime:
			{
				match(LITERAL_realtime);
				break;
			}
			case LITERAL_time:
			{
				match(LITERAL_time);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_37);
			} else {
			  throw ex;
			}
		}
		return pt;
	}
	
	public final ListOf<SpecparamAssign>  list_of_specparam_assignments() throws RecognitionException, TokenStreamException {
		ListOf<SpecparamAssign> spa;
		
		spa=null; SpecparamAssign sa=null;
		
		try {      // for error handling
			sa=specparam_assignment();
			if ( inputState.guessing==0 ) {
				spa=getTree().addToList(spa,sa);
			}
			{
			_loop154:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					sa=specparam_assignment();
					if ( inputState.guessing==0 ) {
						spa=getTree().addToList(spa,sa);
					}
				}
				else {
					break _loop154;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		return spa;
	}
	
	public final NetType  net_type() throws RecognitionException, TokenStreamException {
		NetType nt;
		
		nt=null; int type=LA(1); nt = getTree().netType(type);
		
		try {      // for error handling
			switch ( LA(1)) {
			case 33:
			{
				match(33);
				break;
			}
			case 34:
			{
				match(34);
				break;
			}
			case LITERAL_tri:
			{
				match(LITERAL_tri);
				break;
			}
			case LITERAL_triand:
			{
				match(LITERAL_triand);
				break;
			}
			case LITERAL_trior:
			{
				match(LITERAL_trior);
				break;
			}
			case 38:
			{
				match(38);
				break;
			}
			case 39:
			{
				match(39);
				break;
			}
			case LITERAL_uwire:
			{
				match(LITERAL_uwire);
				break;
			}
			case LITERAL_wire:
			{
				match(LITERAL_wire);
				break;
			}
			case LITERAL_wand:
			{
				match(LITERAL_wand);
				break;
			}
			case LITERAL_wor:
			{
				match(LITERAL_wor);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_38);
			} else {
			  throw ex;
			}
		}
		return nt;
	}
	
	public final ListOf<PortIdent>  list_of_port_identifiers() throws RecognitionException, TokenStreamException {
		ListOf<PortIdent> lop;
		
		lop=null; PortIdent pi=null;
		
		try {      // for error handling
			pi=port_identifier();
			if ( inputState.guessing==0 ) {
				lop = getTree().addToList(lop,pi);
			}
			{
			_loop148:
			do {
				if ((LA(1)==COMMA) && (LA(2)==IDENT||LA(2)==ESCAPED_IDENT)) {
					match(COMMA);
					pi=port_identifier();
					if ( inputState.guessing==0 ) {
						lop = getTree().addToList(lop,pi);
					}
				}
				else {
					break _loop148;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		return lop;
	}
	
	public final OutputVarType  output_variable_type() throws RecognitionException, TokenStreamException {
		OutputVarType ovt;
		
		ovt=null; int type=LA(1); ovt = getTree().outputVarType(type);
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_integer:
			{
				match(LITERAL_integer);
				break;
			}
			case LITERAL_time:
			{
				match(LITERAL_time);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_37);
			} else {
			  throw ex;
			}
		}
		return ovt;
	}
	
	public final ListOf<PortIdents>  list_of_variable_port_identifiers() throws RecognitionException, TokenStreamException {
		ListOf<PortIdents> lopi;
		
		lopi=null; PortIdents pis=null;
		
		try {      // for error handling
			pis=port_identifiers();
			if ( inputState.guessing==0 ) {
				lopi=getTree().addToList(lopi,pis);
			}
			{
			_loop160:
			do {
				if ((LA(1)==COMMA) && (LA(2)==IDENT||LA(2)==ESCAPED_IDENT)) {
					match(COMMA);
					pis=port_identifiers();
					if ( inputState.guessing==0 ) {
						lopi=getTree().addToList(lopi,pis);
					}
				}
				else {
					break _loop160;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		return lopi;
	}
	
	public final ListOf<EventIdentifiers>  list_of_event_identifiers() throws RecognitionException, TokenStreamException {
		ListOf<EventIdentifiers> loei;
		
		loei=null; EventIdentifiers ei=null;
		
		try {      // for error handling
			ei=event_identifiers();
			if ( inputState.guessing==0 ) {
				loei=getTree().addToList(loei,ei);
			}
			{
			_loop130:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					ei=event_identifiers();
					if ( inputState.guessing==0 ) {
						loei=getTree().addToList(loei,ei);
					}
				}
				else {
					break _loop130;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		return loei;
	}
	
	public final ListOf<VariableType>  list_of_variable_identifiers() throws RecognitionException, TokenStreamException {
		ListOf<VariableType> lovt;
		
		lovt=null; VariableType vt=null;
		
		try {      // for error handling
			vt=variable_type();
			if ( inputState.guessing==0 ) {
				lovt = getTree().addToList(lovt,vt);
			}
			{
			_loop157:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					vt=variable_type();
					if ( inputState.guessing==0 ) {
						lovt = getTree().addToList(lovt,vt);
					}
				}
				else {
					break _loop157;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		return lovt;
	}
	
	public final Delay3  delay3() throws RecognitionException, TokenStreamException {
		Delay3 d3;
		
		d3=null; ListOf<DelayValue> lo=null; DelayValue dv=null;
		
		try {      // for error handling
			{
			if ((LA(1)==POUND) && (LA(2)==NUMBER||LA(2)==IDENT||LA(2)==ESCAPED_IDENT)) {
				match(POUND);
				dv=delay_value();
				if ( inputState.guessing==0 ) {
					lo = getTree().addToList(lo, dv);
				}
			}
			else if ((LA(1)==POUND) && (LA(2)==LPAREN)) {
				match(POUND);
				match(LPAREN);
				{
				dv=delay_value();
				if ( inputState.guessing==0 ) {
					lo = getTree().addToList(lo, dv);
				}
				{
				_loop122:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						dv=delay_value();
						if ( inputState.guessing==0 ) {
							getTree().addToList(lo, dv);
						}
					}
					else {
						break _loop122;
					}
					
				} while (true);
				}
				}
				match(RPAREN);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
				d3 = getTree().delay3(lo);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_39);
			} else {
			  throw ex;
			}
		}
		return d3;
	}
	
	public final ListOf<NetIdentifiers>  list_of_net_identifiers() throws RecognitionException, TokenStreamException {
		ListOf<NetIdentifiers> loni;
		
		loni=null; NetIdentifiers ni=null;
		
		try {      // for error handling
			ni=net_identifiers();
			if ( inputState.guessing==0 ) {
				loni=getTree().addToList(loni,ni);
			}
			{
			_loop139:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					ni=net_identifiers();
					if ( inputState.guessing==0 ) {
						loni=getTree().addToList(loni,ni);
					}
				}
				else {
					break _loop139;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		return loni;
	}
	
	public final ListOf<NetDeclAssign>	 list_of_net_decl_assignments() throws RecognitionException, TokenStreamException {
		ListOf<NetDeclAssign>	loda;
		
		loda=null; NetDeclAssign nda=null;
		
		try {      // for error handling
			nda=net_decl_assignment();
			if ( inputState.guessing==0 ) {
				loda=getTree().addToList(loda,nda);
			}
			{
			_loop136:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					nda=net_decl_assignment();
					if ( inputState.guessing==0 ) {
						loda=getTree().addToList(loda,nda);
					}
				}
				else {
					break _loop136;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		return loda;
	}
	
	public final ListOf<RealType>  list_of_real_identifiers() throws RecognitionException, TokenStreamException {
		ListOf<RealType> lort;
		
		lort=null; RealType rt=null;
		
		try {      // for error handling
			rt=real_type();
			if ( inputState.guessing==0 ) {
				lort=getTree().addToList(lort,rt);
			}
			{
			_loop151:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					rt=real_type();
					if ( inputState.guessing==0 ) {
						lort=getTree().addToList(lort,rt);
					}
				}
				else {
					break _loop151;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		return lort;
	}
	
	public final RealType  real_type() throws RecognitionException, TokenStreamException {
		RealType rt;
		
		rt=null; RealIdent id=null; 
		Dimension dim=null; ListOf<Dimension> lod=null; 
		ConstExpression expr=null;
		
		try {      // for error handling
			{
			if ((LA(1)==IDENT||LA(1)==ESCAPED_IDENT) && (LA(2)==SEMI||LA(2)==COMMA||LA(2)==LBRACK)) {
				id=real_identifier();
				{
				_loop113:
				do {
					if ((LA(1)==LBRACK)) {
						dim=dimension();
						if ( inputState.guessing==0 ) {
							lod = getTree().addToList(lod, dim);
						}
					}
					else {
						break _loop113;
					}
					
				} while (true);
				}
			}
			else if ((LA(1)==IDENT||LA(1)==ESCAPED_IDENT) && (LA(2)==ASSIGN)) {
				id=real_identifier();
				match(ASSIGN);
				expr=constant_expression();
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
				rt = getTree().realType(id,lod,expr);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_40);
			} else {
			  throw ex;
			}
		}
		return rt;
	}
	
	public final RealIdent  real_identifier() throws RecognitionException, TokenStreamException {
		RealIdent rid;
		
		rid=null; Ident id=null;
		
		try {      // for error handling
			id=identifier();
			if ( inputState.guessing==0 ) {
				rid = getTree().realIdentifier(id);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_41);
			} else {
			  throw ex;
			}
		}
		return rid;
	}
	
	public final Dimension  dimension() throws RecognitionException, TokenStreamException {
		Dimension dim;
		
		dim=null; ConstExpression msb=null, lsb=null;
		
		try {      // for error handling
			match(LBRACK);
			msb=dimension_constant_expression();
			match(COLON);
			lsb=dimension_constant_expression();
			match(RBRACK);
			if ( inputState.guessing==0 ) {
				dim = getTree().dimension(msb,lsb);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_42);
			} else {
			  throw ex;
			}
		}
		return dim;
	}
	
	public final ConstExpression  constant_expression() throws RecognitionException, TokenStreamException {
		ConstExpression ce;
		
		ce=null; Expression exp=null;
		
		try {      // for error handling
			exp=expression();
			if ( inputState.guessing==0 ) {
				ce = getTree().constantExpression(exp);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_43);
			} else {
			  throw ex;
			}
		}
		return ce;
	}
	
	public final VariableType  variable_type() throws RecognitionException, TokenStreamException {
		VariableType vt;
		
		vt=null; VariableIdent id=null; 
		Dimension dim=null; ListOf<Dimension> lod=null; 
		ConstExpression expr=null;
		
		try {      // for error handling
			{
			if ((LA(1)==IDENT||LA(1)==ESCAPED_IDENT) && (LA(2)==SEMI||LA(2)==COMMA||LA(2)==LBRACK)) {
				id=variable_identifier();
				{
				_loop117:
				do {
					if ((LA(1)==LBRACK)) {
						dim=dimension();
						if ( inputState.guessing==0 ) {
							lod = getTree().addToList(lod, dim);
						}
					}
					else {
						break _loop117;
					}
					
				} while (true);
				}
			}
			else if ((LA(1)==IDENT||LA(1)==ESCAPED_IDENT) && (LA(2)==ASSIGN)) {
				id=variable_identifier();
				match(ASSIGN);
				expr=constant_expression();
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
				vt = getTree().varType(id,lod,expr);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_40);
			} else {
			  throw ex;
			}
		}
		return vt;
	}
	
	public final VariableIdent  variable_identifier() throws RecognitionException, TokenStreamException {
		VariableIdent rid;
		
		rid=null; Ident id=null;
		
		try {      // for error handling
			id=identifier();
			if ( inputState.guessing==0 ) {
				rid = getTree().variableIdentifier(id);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_41);
			} else {
			  throw ex;
			}
		}
		return rid;
	}
	
	public final DelayValue  delay_value() throws RecognitionException, TokenStreamException {
		DelayValue dv;
		
		dv=null; Vnumber n=null; Ident id=null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case NUMBER:
			{
				n=number();
				if ( inputState.guessing==0 ) {
					dv = getTree().delayValue(n);
				}
				break;
			}
			case IDENT:
			case ESCAPED_IDENT:
			{
				id=identifier();
				if ( inputState.guessing==0 ) {
					dv = getTree().delayValue(id);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_44);
			} else {
			  throw ex;
			}
		}
		return dv;
	}
	
	public final Delay2  delay2() throws RecognitionException, TokenStreamException {
		Delay2 d2;
		
		d2=null; Delay3 d3=null;
		
		try {      // for error handling
			d3=delay3();
			if ( inputState.guessing==0 ) {
				d2 = getTree().delay2(d3);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		return d2;
	}
	
	public final Vnumber  number() throws RecognitionException, TokenStreamException {
		Vnumber n;
		
		Token  tk = null;
		n = null;
		
		try {      // for error handling
			tk = LT(1);
			match(NUMBER);
			if ( inputState.guessing==0 ) {
				n = getTree().number(tk);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_45);
			} else {
			  throw ex;
			}
		}
		return n;
	}
	
	public final Ident  identifier() throws RecognitionException, TokenStreamException {
		Ident id;
		
		Token  tk = null;
		Token  tk2 = null;
			id=null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case IDENT:
			{
				tk = LT(1);
				match(IDENT);
				if ( inputState.guessing==0 ) {
					id = new Ident(tk);
				}
				break;
			}
			case ESCAPED_IDENT:
			{
				tk2 = LT(1);
				match(ESCAPED_IDENT);
				if ( inputState.guessing==0 ) {
					id = new Ident(tk2);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_46);
			} else {
			  throw ex;
			}
		}
		return id;
	}
	
	public final DefparamAssign  defparam_assignment() throws RecognitionException, TokenStreamException {
		DefparamAssign dpa;
		
		dpa=null; HierParameterIdent hpi=null; ConstMinTypMaxExpression mtm=null;
		
		try {      // for error handling
			hpi=hierarchical_parameter_identifier();
			match(ASSIGN);
			mtm=constant_mintypmax_expression();
			if ( inputState.guessing==0 ) {
				dpa = getTree().defparamAssign(hpi,mtm);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_40);
			} else {
			  throw ex;
			}
		}
		return dpa;
	}
	
	public final EventIdentifiers  event_identifiers() throws RecognitionException, TokenStreamException {
		EventIdentifiers eis;
		
		eis=null; EventIdent ei=null; Dimension dim=null;
		
		try {      // for error handling
			ei=event_identifier();
			if ( inputState.guessing==0 ) {
				eis=getTree().eventIdentifiers(null,ei,null);
			}
			{
			_loop133:
			do {
				if ((LA(1)==LBRACK)) {
					dim=dimension();
					if ( inputState.guessing==0 ) {
						eis=getTree().eventIdentifiers(eis,null,dim);
					}
				}
				else {
					break _loop133;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_40);
			} else {
			  throw ex;
			}
		}
		return eis;
	}
	
	public final EventIdent  event_identifier() throws RecognitionException, TokenStreamException {
		EventIdent rid;
		
		rid=null; Ident id=null;
		
		try {      // for error handling
			id=identifier();
			if ( inputState.guessing==0 ) {
				rid = getTree().eventIdentifier(id);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_42);
			} else {
			  throw ex;
			}
		}
		return rid;
	}
	
	public final NetDeclAssign  net_decl_assignment() throws RecognitionException, TokenStreamException {
		NetDeclAssign nda;
		
		nda=null; NetIdent id=null; Expression exp=null;
		
		try {      // for error handling
			id=net_identifier();
			match(ASSIGN);
			exp=expression();
			if ( inputState.guessing==0 ) {
				nda = getTree().netDeclAssign(id,exp);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_40);
			} else {
			  throw ex;
			}
		}
		return nda;
	}
	
	public final NetIdentifiers  net_identifiers() throws RecognitionException, TokenStreamException {
		NetIdentifiers nis;
		
		nis=null; NetIdent ni=null; Dimension dim=null;
		
		try {      // for error handling
			ni=net_identifier();
			if ( inputState.guessing==0 ) {
				nis=getTree().netIdentifiers(null,ni,null);
			}
			{
			_loop142:
			do {
				if ((LA(1)==LBRACK)) {
					dim=dimension();
					if ( inputState.guessing==0 ) {
						nis=getTree().netIdentifiers(nis,null,dim);
					}
				}
				else {
					break _loop142;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_40);
			} else {
			  throw ex;
			}
		}
		return nis;
	}
	
	public final NetIdent  net_identifier() throws RecognitionException, TokenStreamException {
		NetIdent rid;
		
		rid=null; Ident id=null;
		
		try {      // for error handling
			id=identifier();
			if ( inputState.guessing==0 ) {
				rid = getTree().netIdentifier(id);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_41);
			} else {
			  throw ex;
			}
		}
		return rid;
	}
	
	public final ParamAssign  param_assignment() throws RecognitionException, TokenStreamException {
		ParamAssign pa;
		
		pa=null; ParameterIdent id=null; ConstMinTypMaxExpression mtm=null;
		
		try {      // for error handling
			id=parameter_identifier();
			match(ASSIGN);
			mtm=constant_mintypmax_expression();
			if ( inputState.guessing==0 ) {
				pa = getTree().paramAssign(id,mtm);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		return pa;
	}
	
	public final SpecparamAssign  specparam_assignment() throws RecognitionException, TokenStreamException {
		SpecparamAssign spa;
		
		spa=null; SpecparamIdent id=null; ConstMinTypMaxExpression mtm=null;
		
		try {      // for error handling
			id=specparam_identifier();
			match(ASSIGN);
			mtm=constant_mintypmax_expression();
			if ( inputState.guessing==0 ) {
				spa = getTree().specparamAssign(id,mtm);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_40);
			} else {
			  throw ex;
			}
		}
		return spa;
	}
	
	public final PortIdents  port_identifiers() throws RecognitionException, TokenStreamException {
		PortIdents pis;
		
		pis=null; PortIdent id=null; ConstExpression expr=null;
		
		try {      // for error handling
			id=port_identifier();
			{
			switch ( LA(1)) {
			case ASSIGN:
			{
				match(ASSIGN);
				expr=constant_expression();
				break;
			}
			case SEMI:
			case RPAREN:
			case COMMA:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				pis = getTree().portIdents(id, expr);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		return pis;
	}
	
	public final HierParameterIdent  hierarchical_parameter_identifier() throws RecognitionException, TokenStreamException {
		HierParameterIdent rid;
		
		rid=null; HierIdent id=null;
		
		try {      // for error handling
			id=hierarchical_identifier();
			if ( inputState.guessing==0 ) {
				rid = getTree().hierarchicalParameterIdentifier(id);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_47);
			} else {
			  throw ex;
			}
		}
		return rid;
	}
	
	public final ConstMinTypMaxExpression  constant_mintypmax_expression() throws RecognitionException, TokenStreamException {
		ConstMinTypMaxExpression cmtm;
		
		cmtm=null; ConstExpression c0=null,c1=null,c2=null;
		
		try {      // for error handling
			c0=constant_expression();
			{
			switch ( LA(1)) {
			case COLON:
			{
				match(COLON);
				c1=constant_expression();
				match(COLON);
				c2=constant_expression();
				break;
			}
			case SEMI:
			case RPAREN:
			case COMMA:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				cmtm = getTree().constantMinTypeMaxExpression(c0,c1,c2);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		return cmtm;
	}
	
/**REMOVE LEFT RECURSION
expression
:	primary
| 	unary_operator (attribute_instance)* primary
|	expression binary_operator (attribute_instance)* expression
|	expression QMARK (attribute_instance)* expression COLON expression
;
**/
	public final Expression  expression() throws RecognitionException, TokenStreamException {
		Expression expr;
		
		expr=null; Object e1=null, e2=null;
		
		try {      // for error handling
			e1=expression_1();
			e2=expression_2();
			if ( inputState.guessing==0 ) {
				expr = getTree().expression(e1, e2);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_48);
			} else {
			  throw ex;
			}
		}
		return expr;
	}
	
	public final ParameterIdent  parameter_identifier() throws RecognitionException, TokenStreamException {
		ParameterIdent rid;
		
		rid=null; Ident id=null;
		
		try {      // for error handling
			id=identifier();
			if ( inputState.guessing==0 ) {
				rid = getTree().parameterIdentifier(id);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_49);
			} else {
			  throw ex;
			}
		}
		return rid;
	}
	
	public final SpecparamIdent  specparam_identifier() throws RecognitionException, TokenStreamException {
		SpecparamIdent rid;
		
		rid=null; Ident id=null;
		
		try {      // for error handling
			id=identifier();
			if ( inputState.guessing==0 ) {
				rid = getTree().specparamIdentifier(id);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_47);
			} else {
			  throw ex;
			}
		}
		return rid;
	}
	
	public final ConstExpression  dimension_constant_expression() throws RecognitionException, TokenStreamException {
		ConstExpression ce;
		
		ce=null;
		
		try {      // for error handling
			ce=constant_expression();
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_50);
			} else {
			  throw ex;
			}
		}
		return ce;
	}
	
	public final ConstExpression  msb_constant_expression() throws RecognitionException, TokenStreamException {
		ConstExpression cexp;
		
		cexp=null;
		
		try {      // for error handling
			cexp=constant_expression();
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_51);
			} else {
			  throw ex;
			}
		}
		return cexp;
	}
	
	public final ConstExpression  lsb_constant_expression() throws RecognitionException, TokenStreamException {
		ConstExpression cexp;
		
		cexp=null;
		
		try {      // for error handling
			cexp=constant_expression();
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		return cexp;
	}
	
	public final FuncType  function_range_or_type() throws RecognitionException, TokenStreamException {
		FuncType ft;
		
		ft=null; int tok=LA(1); Range rng=null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LBRACK:
			case LITERAL_signed:
			case IDENT:
			case ESCAPED_IDENT:
			{
				{
				switch ( LA(1)) {
				case LITERAL_signed:
				{
					match(LITERAL_signed);
					break;
				}
				case LBRACK:
				case IDENT:
				case ESCAPED_IDENT:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				if ( inputState.guessing==0 ) {
					tok=-1;
				}
				{
				switch ( LA(1)) {
				case LBRACK:
				{
					rng=range();
					break;
				}
				case IDENT:
				case ESCAPED_IDENT:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				break;
			}
			case LITERAL_integer:
			{
				match(LITERAL_integer);
				break;
			}
			case LITERAL_real:
			{
				match(LITERAL_real);
				break;
			}
			case LITERAL_realtime:
			{
				match(LITERAL_realtime);
				break;
			}
			case LITERAL_time:
			{
				match(LITERAL_time);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				ft = getTree().funcType(tok, rng);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_37);
			} else {
			  throw ex;
			}
		}
		return ft;
	}
	
	public final FuncIdent  function_identifier() throws RecognitionException, TokenStreamException {
		FuncIdent rid;
		
		rid=null; Ident id=null;
		
		try {      // for error handling
			id=identifier();
			if ( inputState.guessing==0 ) {
					rid = getTree().funcIdentifier(id);
							getTree().addSymbol(rid);
						
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		return rid;
	}
	
	public final FuncItemDecl  function_item_declaration() throws RecognitionException, TokenStreamException {
		FuncItemDecl fid;
		
		fid=null; TfPortDeclaration tpd=null; BlockItemDecl bid=null;
		
		try {      // for error handling
			{
			boolean synPredMatched182 = false;
			if (((LA(1)==LPAREN||LA(1)==LITERAL_input) && (_tokenSet_31.member(LA(2))))) {
				int _m182 = mark();
				synPredMatched182 = true;
				inputState.guessing++;
				try {
					{
					{
					_loop181:
					do {
						if ((LA(1)==LPAREN)) {
							attribute_instance();
						}
						else {
							break _loop181;
						}
						
					} while (true);
					}
					tf_input_declaration();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched182 = false;
				}
				rewind(_m182);
inputState.guessing--;
			}
			if ( synPredMatched182 ) {
				{
				_loop184:
				do {
					if ((LA(1)==LPAREN)) {
						attribute_instance();
					}
					else {
						break _loop184;
					}
					
				} while (true);
				}
				tpd=tf_input_declaration();
				match(SEMI);
			}
			else if ((_tokenSet_32.member(LA(1))) && (_tokenSet_13.member(LA(2)))) {
				bid=block_item_declaration();
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
				fid = getTree().funcItemDecl(tpd,bid);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_52);
			} else {
			  throw ex;
			}
		}
		return fid;
	}
	
	public final ListOf<TfPortDeclaration>  function_port_list() throws RecognitionException, TokenStreamException {
		ListOf<TfPortDeclaration> lop;
		
		lop=null; TfPortDeclaration tpd=null;
		
		try {      // for error handling
			{
			_loop187:
			do {
				if ((LA(1)==LPAREN)) {
					attribute_instance();
				}
				else {
					break _loop187;
				}
				
			} while (true);
			}
			tpd=tf_input_declaration();
			if ( inputState.guessing==0 ) {
				lop = getTree().addToList(lop,tpd);
			}
			{
			_loop191:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					{
					_loop190:
					do {
						if ((LA(1)==LPAREN)) {
							attribute_instance();
						}
						else {
							break _loop190;
						}
						
					} while (true);
					}
					tpd=tf_input_declaration();
					if ( inputState.guessing==0 ) {
						getTree().addToList(lop,tpd);
					}
				}
				else {
					break _loop191;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_19);
			} else {
			  throw ex;
			}
		}
		return lop;
	}
	
	public final BlockItemDecl  block_item_declaration() throws RecognitionException, TokenStreamException {
		BlockItemDecl bid;
		
		bid=null; int la=LA(1); boolean isSigned=false; Range rng=null;
		ListOf<BlockVariableType> lov=null; ListOf<BlockRealType> lor=null;
		EventDecl ed=null; LocalParameterDecl lpd=null; ParameterDeclaration pd=null;
		
		
		try {      // for error handling
			{
			_loop227:
			do {
				if ((LA(1)==LPAREN)) {
					attribute_instance();
				}
				else {
					break _loop227;
				}
				
			} while (true);
			}
			{
			switch ( LA(1)) {
			case LITERAL_reg:
			{
				match(LITERAL_reg);
				{
				switch ( LA(1)) {
				case LITERAL_signed:
				{
					match(LITERAL_signed);
					if ( inputState.guessing==0 ) {
						isSigned=true;
					}
					break;
				}
				case LBRACK:
				case IDENT:
				case ESCAPED_IDENT:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				{
				switch ( LA(1)) {
				case LBRACK:
				{
					rng=range();
					break;
				}
				case IDENT:
				case ESCAPED_IDENT:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				lov=list_of_block_variable_identifiers();
				match(SEMI);
				if ( inputState.guessing==0 ) {
					bid = getTree().blockItemDecl(isSigned, rng, lov);
				}
				break;
			}
			case LITERAL_integer:
			{
				match(LITERAL_integer);
				lov=list_of_block_variable_identifiers();
				match(SEMI);
				if ( inputState.guessing==0 ) {
					bid = getTree().blockItemDecl(la, lov);
				}
				break;
			}
			case LITERAL_time:
			{
				match(LITERAL_time);
				lov=list_of_block_variable_identifiers();
				match(SEMI);
				if ( inputState.guessing==0 ) {
					bid = getTree().blockItemDecl(la, lov);
				}
				break;
			}
			case LITERAL_real:
			{
				match(LITERAL_real);
				lor=list_of_block_real_identifiers();
				match(SEMI);
				if ( inputState.guessing==0 ) {
					bid = getTree().blockItemDecl(la, lor, true);
				}
				break;
			}
			case LITERAL_realtime:
			{
				match(LITERAL_realtime);
				lor=list_of_block_real_identifiers();
				match(SEMI);
				if ( inputState.guessing==0 ) {
					bid = getTree().blockItemDecl(la, lor, true);
				}
				break;
			}
			case LITERAL_event:
			{
				ed=event_declaration();
				if ( inputState.guessing==0 ) {
					bid = getTree().blockItemDecl(ed);
				}
				break;
			}
			case LITERAL_localparam:
			{
				lpd=local_parameter_declaration();
				match(SEMI);
				if ( inputState.guessing==0 ) {
					bid = getTree().blockItemDecl(lpd);
				}
				break;
			}
			case LITERAL_parameter:
			{
				pd=parameter_declaration();
				match(SEMI);
				if ( inputState.guessing==0 ) {
					bid = getTree().blockItemDecl(pd);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_53);
			} else {
			  throw ex;
			}
		}
		return bid;
	}
	
	public final FuncStatement  function_statement() throws RecognitionException, TokenStreamException {
		FuncStatement fc;
		
		fc=null; Statement st=null;
		
		try {      // for error handling
			st=statement();
			if ( inputState.guessing==0 ) {
				fc = getTree().functionStatement(st);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_54);
			} else {
			  throw ex;
			}
		}
		return fc;
	}
	
	public final TfPortDeclaration  tf_input_declaration() throws RecognitionException, TokenStreamException {
		TfPortDeclaration tfp;
		
		tfp=null; int dir=LA(1); TaskPortType tpt=null; ListOf<PortIdent> lop=null;
		
		try {      // for error handling
			match(LITERAL_input);
			tpt=task_port_type();
			lop=list_of_port_identifiers();
			if ( inputState.guessing==0 ) {
				tfp = getTree().tfPortDecl(dir, tpt, lop);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		return tfp;
	}
	
	public final TaskIdent  task_identifier() throws RecognitionException, TokenStreamException {
		TaskIdent rid;
		
		rid=null; Ident id=null;
		
		try {      // for error handling
			id=identifier();
			if ( inputState.guessing==0 ) {
				rid = getTree().taskIdentifier(id);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		return rid;
	}
	
	public final TaskItemDecl  task_item_declaration() throws RecognitionException, TokenStreamException {
		TaskItemDecl tid;
		
		tid=null; BlockItemDecl bid=null; TfPortDeclaration tpd=null;
		
		try {      // for error handling
			boolean synPredMatched206 = false;
			if (((_tokenSet_32.member(LA(1))) && (_tokenSet_13.member(LA(2))))) {
				int _m206 = mark();
				synPredMatched206 = true;
				inputState.guessing++;
				try {
					{
					block_item_declaration();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched206 = false;
				}
				rewind(_m206);
inputState.guessing--;
			}
			if ( synPredMatched206 ) {
				bid=block_item_declaration();
				if ( inputState.guessing==0 ) {
					tid = getTree().taskItemDecl(bid,null);
				}
			}
			else if ((_tokenSet_16.member(LA(1))) && (_tokenSet_31.member(LA(2)))) {
				{
				_loop208:
				do {
					if ((LA(1)==LPAREN)) {
						attribute_instance();
					}
					else {
						break _loop208;
					}
					
				} while (true);
				}
				{
				switch ( LA(1)) {
				case LITERAL_input:
				{
					tpd=tf_input_declaration();
					match(SEMI);
					break;
				}
				case LITERAL_output:
				{
					tpd=tf_output_declaration();
					match(SEMI);
					break;
				}
				case LITERAL_inout:
				{
					tpd=tf_inout_declaration();
					match(SEMI);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				if ( inputState.guessing==0 ) {
					tid = getTree().taskItemDecl(null,tpd);
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_55);
			} else {
			  throw ex;
			}
		}
		return tid;
	}
	
	public final ListOf<TfPortDeclaration>  task_port_list() throws RecognitionException, TokenStreamException {
		ListOf<TfPortDeclaration> lop;
		
		lop=null; TfPortDeclaration tpd=null;
		
		try {      // for error handling
			tpd=task_port_item();
			if ( inputState.guessing==0 ) {
				lop = getTree().addToList(lop,tpd);
			}
			{
			_loop212:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					tpd=task_port_item();
					if ( inputState.guessing==0 ) {
						lop = getTree().addToList(lop,tpd);
					}
				}
				else {
					break _loop212;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_19);
			} else {
			  throw ex;
			}
		}
		return lop;
	}
	
	public final Statement  statement_or_null() throws RecognitionException, TokenStreamException {
		Statement stmt;
		
		stmt=null;
		
		try {      // for error handling
			boolean synPredMatched356 = false;
			if (((LA(1)==SEMI||LA(1)==LPAREN) && (_tokenSet_56.member(LA(2))))) {
				int _m356 = mark();
				synPredMatched356 = true;
				inputState.guessing++;
				try {
					{
					{
					_loop355:
					do {
						if ((LA(1)==LPAREN)) {
							attribute_instance();
						}
						else {
							break _loop355;
						}
						
					} while (true);
					}
					match(SEMI);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched356 = false;
				}
				rewind(_m356);
inputState.guessing--;
			}
			if ( synPredMatched356 ) {
				{
				_loop358:
				do {
					if ((LA(1)==LPAREN)) {
						attribute_instance();
					}
					else {
						break _loop358;
					}
					
				} while (true);
				}
				match(SEMI);
			}
			else if ((_tokenSet_57.member(LA(1))) && (_tokenSet_58.member(LA(2)))) {
				stmt=statement();
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_59);
			} else {
			  throw ex;
			}
		}
		return stmt;
	}
	
	public final TfPortDeclaration  tf_output_declaration() throws RecognitionException, TokenStreamException {
		TfPortDeclaration tfp;
		
		tfp=null; int dir=LA(1); TaskPortType tpt=null; ListOf<PortIdent> lop=null;
		
		try {      // for error handling
			match(LITERAL_output);
			tpt=task_port_type();
			lop=list_of_port_identifiers();
			if ( inputState.guessing==0 ) {
				tfp = getTree().tfPortDecl(dir, tpt, lop);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		return tfp;
	}
	
	public final TfPortDeclaration  tf_inout_declaration() throws RecognitionException, TokenStreamException {
		TfPortDeclaration tfp;
		
		tfp=null; int dir=LA(1); TaskPortType tpt=null; ListOf<PortIdent> lop=null;
		
		try {      // for error handling
			match(LITERAL_inout);
			tpt=task_port_type();
			lop=list_of_port_identifiers();
			if ( inputState.guessing==0 ) {
				tfp = getTree().tfPortDecl(dir, tpt, lop);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_21);
			} else {
			  throw ex;
			}
		}
		return tfp;
	}
	
	public final TfPortDeclaration  task_port_item() throws RecognitionException, TokenStreamException {
		TfPortDeclaration tpd;
		
		tpd=null;
		
		try {      // for error handling
			{
			_loop215:
			do {
				if ((LA(1)==LPAREN)) {
					attribute_instance();
				}
				else {
					break _loop215;
				}
				
			} while (true);
			}
			{
			switch ( LA(1)) {
			case LITERAL_input:
			{
				tpd=tf_input_declaration();
				break;
			}
			case LITERAL_output:
			{
				tpd=tf_output_declaration();
				break;
			}
			case LITERAL_inout:
			{
				tpd=tf_inout_declaration();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_22);
			} else {
			  throw ex;
			}
		}
		return tpd;
	}
	
	public final TaskPortType  task_port_type() throws RecognitionException, TokenStreamException {
		TaskPortType tpt;
		
		tpt=null; int la=LA(1); boolean isReg=false;
		boolean isSigned=false; Range rng=null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_integer:
			case LITERAL_real:
			case LITERAL_realtime:
			case LITERAL_time:
			{
				{
				switch ( LA(1)) {
				case LITERAL_integer:
				{
					match(LITERAL_integer);
					break;
				}
				case LITERAL_real:
				{
					match(LITERAL_real);
					break;
				}
				case LITERAL_realtime:
				{
					match(LITERAL_realtime);
					break;
				}
				case LITERAL_time:
				{
					match(LITERAL_time);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				if ( inputState.guessing==0 ) {
					tpt = getTree().taskPortType(la);
				}
				break;
			}
			case LBRACK:
			case LITERAL_signed:
			case LITERAL_reg:
			case IDENT:
			case ESCAPED_IDENT:
			{
				{
				switch ( LA(1)) {
				case LITERAL_reg:
				{
					match(LITERAL_reg);
					if ( inputState.guessing==0 ) {
						isReg=true;
					}
					break;
				}
				case LBRACK:
				case LITERAL_signed:
				case IDENT:
				case ESCAPED_IDENT:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				{
				switch ( LA(1)) {
				case LITERAL_signed:
				{
					match(LITERAL_signed);
					if ( inputState.guessing==0 ) {
						isSigned=true;
					}
					break;
				}
				case LBRACK:
				case IDENT:
				case ESCAPED_IDENT:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				{
				switch ( LA(1)) {
				case LBRACK:
				{
					rng=range();
					break;
				}
				case IDENT:
				case ESCAPED_IDENT:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				if ( inputState.guessing==0 ) {
					tpt = getTree().taskPortType(isReg, isSigned, rng);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_37);
			} else {
			  throw ex;
			}
		}
		return tpt;
	}
	
	public final ListOf<BlockVariableType>  list_of_block_variable_identifiers() throws RecognitionException, TokenStreamException {
		ListOf<BlockVariableType> lob;
		
		lob=null; BlockVariableType bvt=null;
		
		try {      // for error handling
			bvt=block_variable_type();
			if ( inputState.guessing==0 ) {
				lob = getTree().addToList(lob,bvt);
			}
			{
			_loop233:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					bvt=block_variable_type();
					if ( inputState.guessing==0 ) {
						lob = getTree().addToList(lob,bvt);
					}
				}
				else {
					break _loop233;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		return lob;
	}
	
	public final ListOf<BlockRealType>  list_of_block_real_identifiers() throws RecognitionException, TokenStreamException {
		ListOf<BlockRealType> lob;
		
		lob=null; BlockRealType brt=null;
		
		try {      // for error handling
			brt=block_real_type();
			if ( inputState.guessing==0 ) {
				lob = getTree().addToList(lob,brt);
			}
			{
			_loop236:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					brt=block_real_type();
					if ( inputState.guessing==0 ) {
						lob = getTree().addToList(lob,brt);
					}
				}
				else {
					break _loop236;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		return lob;
	}
	
	public final BlockVariableType  block_variable_type() throws RecognitionException, TokenStreamException {
		BlockVariableType bvt;
		
		bvt=null; VariableIdent id=null; Dimension dim=null; 
			ListOf<Dimension> lod=null;
		
		
		try {      // for error handling
			id=variable_identifier();
			{
			_loop239:
			do {
				if ((LA(1)==LBRACK)) {
					dim=dimension();
					if ( inputState.guessing==0 ) {
						lod=getTree().addToList(lod,dim);
					}
				}
				else {
					break _loop239;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				bvt = getTree().blockVariableType(id,lod);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_40);
			} else {
			  throw ex;
			}
		}
		return bvt;
	}
	
	public final BlockRealType  block_real_type() throws RecognitionException, TokenStreamException {
		BlockRealType brt;
		
		brt=null; RealIdent id=null; Dimension dim=null; ListOf<Dimension> lod=null;
		
		try {      // for error handling
			id=real_identifier();
			{
			_loop242:
			do {
				if ((LA(1)==LBRACK)) {
					dim=dimension();
					if ( inputState.guessing==0 ) {
						lod=getTree().addToList(lod,dim);
					}
				}
				else {
					break _loop242;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				brt = getTree().blockRealType(id,lod);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_40);
			} else {
			  throw ex;
			}
		}
		return brt;
	}
	
	public final ParameterValueAssignment  parameter_value_assignment() throws RecognitionException, TokenStreamException {
		ParameterValueAssignment pva;
		
		pva=null; ListOf<ParameterAssignment> lopa=null;
		
		try {      // for error handling
			match(POUND);
			match(LPAREN);
			lopa=list_of_parameter_assignments();
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				pva = getTree().parameterValueAssignment(lopa);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_60);
			} else {
			  throw ex;
			}
		}
		return pva;
	}
	
	public final ModuleInstance  module_instance() throws RecognitionException, TokenStreamException {
		ModuleInstance inst;
		
		inst=null; ListOf<PortConnection> lopc=null;
		
		try {      // for error handling
			if ( inputState.guessing==0 ) {
				inst = getTree().moduleInstance();
			}
			name_of_module_instance(inst);
			match(LPAREN);
			{
			if ((_tokenSet_61.member(LA(1))) && (_tokenSet_62.member(LA(2)))) {
				lopc=list_of_port_connections();
			}
			else if ((LA(1)==RPAREN) && (LA(2)==SEMI||LA(2)==COMMA)) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				getTree().moduleInstance(inst,lopc);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_40);
			} else {
			  throw ex;
			}
		}
		return inst;
	}
	
	public final ListOf<ParameterAssignment>  list_of_parameter_assignments() throws RecognitionException, TokenStreamException {
		ListOf<ParameterAssignment> lopa;
		
		lopa=null; Expression exp=null; NamedParamAssignment npa=null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LPAREN:
			case LCURLY:
			case NUMBER:
			case STRING:
			case IDENT:
			case ESCAPED_IDENT:
			case SYSTEM_TASK_NAME:
			case MINUS:
			case PLUS:
			case LNOT:
			case BNOT:
			case BAND:
			case RNAND:
			case BOR:
			case RNOR:
			case BXOR:
			case RXNOR:
			{
				exp=ordered_parameter_assignment();
				if ( inputState.guessing==0 ) {
					lopa = getTree().listOfParamAssigns(lopa,exp,null);
				}
				{
				_loop252:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						exp=ordered_parameter_assignment();
						if ( inputState.guessing==0 ) {
							getTree().listOfParamAssigns(lopa,exp,null);
						}
					}
					else {
						break _loop252;
					}
					
				} while (true);
				}
				break;
			}
			case DOT:
			{
				npa=named_parameter_assignment();
				if ( inputState.guessing==0 ) {
					lopa = getTree().listOfParamAssigns(lopa,null,npa);
				}
				{
				_loop254:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						npa=named_parameter_assignment();
						if ( inputState.guessing==0 ) {
							getTree().listOfParamAssigns(lopa,null,npa);
						}
					}
					else {
						break _loop254;
					}
					
				} while (true);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_19);
			} else {
			  throw ex;
			}
		}
		return lopa;
	}
	
	public final Expression  ordered_parameter_assignment() throws RecognitionException, TokenStreamException {
		Expression exp;
		
		exp=null;
		
		try {      // for error handling
			exp=expression();
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_22);
			} else {
			  throw ex;
			}
		}
		return exp;
	}
	
	public final NamedParamAssignment  named_parameter_assignment() throws RecognitionException, TokenStreamException {
		NamedParamAssignment npa;
		
		npa=null; ParameterIdent id=null; MinTypMaxExpression exp=null;
		
		try {      // for error handling
			match(DOT);
			id=parameter_identifier();
			match(LPAREN);
			{
			switch ( LA(1)) {
			case LPAREN:
			case LCURLY:
			case NUMBER:
			case STRING:
			case IDENT:
			case ESCAPED_IDENT:
			case SYSTEM_TASK_NAME:
			case MINUS:
			case PLUS:
			case LNOT:
			case BNOT:
			case BAND:
			case RNAND:
			case BOR:
			case RNOR:
			case BXOR:
			case RXNOR:
			{
				exp=mintypmax_expression();
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				npa = getTree().namedParameterAssignment(id,exp);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_22);
			} else {
			  throw ex;
			}
		}
		return npa;
	}
	
	public final MinTypMaxExpression  mintypmax_expression() throws RecognitionException, TokenStreamException {
		MinTypMaxExpression mtm;
		
		mtm=null; Expression mtms[] = {null,null,null}; Expression exp=null;
		
		try {      // for error handling
			exp=expression();
			if ( inputState.guessing==0 ) {
				mtms[0]=exp;
			}
			{
			switch ( LA(1)) {
			case COLON:
			{
				match(COLON);
				exp=expression();
				if ( inputState.guessing==0 ) {
					mtms[1]=exp;
				}
				match(COLON);
				exp=expression();
				if ( inputState.guessing==0 ) {
					mtms[2]=exp;
				}
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				mtm=getTree().minTypMaxExpression(mtms);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_19);
			} else {
			  throw ex;
			}
		}
		return mtm;
	}
	
	public final void name_of_module_instance(
		ModuleInstance inst
	) throws RecognitionException, TokenStreamException {
		
			ModuleInstanceIdent nm=null; Range rng=null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case IDENT:
			case ESCAPED_IDENT:
			{
				nm=module_instance_identifier();
				{
				switch ( LA(1)) {
				case LBRACK:
				{
					rng=range();
					break;
				}
				case LPAREN:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				break;
			}
			case LPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				getTree().moduleInstance(inst, nm, rng);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_63);
			} else {
			  throw ex;
			}
		}
	}
	
	public final ListOf<PortConnection>  list_of_port_connections() throws RecognitionException, TokenStreamException {
		ListOf<PortConnection> lopc;
		
		lopc=null; Expression exp=null; NamedPortConnection npc=null;
		
		try {      // for error handling
			boolean synPredMatched265 = false;
			if (((_tokenSet_64.member(LA(1))) && (_tokenSet_62.member(LA(2))))) {
				int _m265 = mark();
				synPredMatched265 = true;
				inputState.guessing++;
				try {
					{
					ordered_port_connection();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched265 = false;
				}
				rewind(_m265);
inputState.guessing--;
			}
			if ( synPredMatched265 ) {
				exp=ordered_port_connection();
				if ( inputState.guessing==0 ) {
					lopc = getTree().listOfPortConnections(lopc, exp, null);
				}
				{
				_loop267:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						exp=ordered_port_connection();
						if ( inputState.guessing==0 ) {
							lopc = getTree().listOfPortConnections(lopc, exp, null);
						}
					}
					else {
						break _loop267;
					}
					
				} while (true);
				}
			}
			else if ((LA(1)==LPAREN||LA(1)==DOT) && (LA(2)==STAR||LA(2)==IDENT||LA(2)==ESCAPED_IDENT)) {
				npc=named_port_connection();
				if ( inputState.guessing==0 ) {
					lopc = getTree().listOfPortConnections(lopc, null, npc);
				}
				{
				_loop269:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						npc=named_port_connection();
						if ( inputState.guessing==0 ) {
							lopc = getTree().listOfPortConnections(lopc, null, npc);
						}
					}
					else {
						break _loop269;
					}
					
				} while (true);
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_19);
			} else {
			  throw ex;
			}
		}
		return lopc;
	}
	
	public final ModuleInstanceIdent  module_instance_identifier() throws RecognitionException, TokenStreamException {
		ModuleInstanceIdent rid;
		
		rid=null; Ident id=null;
		
		try {      // for error handling
			id=identifier();
			if ( inputState.guessing==0 ) {
				rid = getTree().moduleInstanceIdentifier(id);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_65);
			} else {
			  throw ex;
			}
		}
		return rid;
	}
	
	public final Expression  ordered_port_connection() throws RecognitionException, TokenStreamException {
		Expression exp;
		
		exp=null;
		
		try {      // for error handling
			{
			_loop272:
			do {
				if ((LA(1)==LPAREN) && (LA(2)==STAR)) {
					attribute_instance();
				}
				else {
					break _loop272;
				}
				
			} while (true);
			}
			{
			switch ( LA(1)) {
			case LPAREN:
			case LCURLY:
			case NUMBER:
			case STRING:
			case IDENT:
			case ESCAPED_IDENT:
			case SYSTEM_TASK_NAME:
			case MINUS:
			case PLUS:
			case LNOT:
			case BNOT:
			case BAND:
			case RNAND:
			case BOR:
			case RNOR:
			case BXOR:
			case RXNOR:
			{
				exp=expression();
				break;
			}
			case RPAREN:
			case COMMA:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_22);
			} else {
			  throw ex;
			}
		}
		return exp;
	}
	
	public final NamedPortConnection  named_port_connection() throws RecognitionException, TokenStreamException {
		NamedPortConnection npc;
		
		npc=null; PortIdent pi=null; Expression exp=null;
		
		try {      // for error handling
			{
			_loop276:
			do {
				if ((LA(1)==LPAREN)) {
					attribute_instance();
				}
				else {
					break _loop276;
				}
				
			} while (true);
			}
			match(DOT);
			pi=port_identifier();
			{
			switch ( LA(1)) {
			case LPAREN:
			{
				match(LPAREN);
				{
				switch ( LA(1)) {
				case LPAREN:
				case LCURLY:
				case NUMBER:
				case STRING:
				case IDENT:
				case ESCAPED_IDENT:
				case SYSTEM_TASK_NAME:
				case MINUS:
				case PLUS:
				case LNOT:
				case BNOT:
				case BAND:
				case RNAND:
				case BOR:
				case RNOR:
				case BXOR:
				case RXNOR:
				{
					exp=expression();
					break;
				}
				case RPAREN:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				match(RPAREN);
				break;
			}
			case RPAREN:
			case COMMA:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				npc = getTree().namedPortConnection(pi, exp);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_22);
			} else {
			  throw ex;
			}
		}
		return npc;
	}
	
	public final ListOf<GenvarIdent>  list_of_genvar_identifiers() throws RecognitionException, TokenStreamException {
		ListOf<GenvarIdent> loi;
		
		loi=null; GenvarIdent id=null;
		
		try {      // for error handling
			id=genvar_identifier();
			if ( inputState.guessing==0 ) {
				loi = getTree().addToList(loi, id);
			}
			{
			_loop285:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					id=genvar_identifier();
					if ( inputState.guessing==0 ) {
						getTree().addToList(loi, id);
					}
				}
				else {
					break _loop285;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		return loi;
	}
	
	public final GenvarIdent  genvar_identifier() throws RecognitionException, TokenStreamException {
		GenvarIdent rid;
		
		rid=null; Ident id=null;
		
		try {      // for error handling
			id=identifier();
			if ( inputState.guessing==0 ) {
				rid = getTree().genvarIdentifier(id);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_66);
			} else {
			  throw ex;
			}
		}
		return rid;
	}
	
	public final GenvarInit  genvar_initialization() throws RecognitionException, TokenStreamException {
		GenvarInit gi;
		
		gi=null; GenvarIdent lhs=null; ConstExpression rhs=null;
		
		try {      // for error handling
			lhs=genvar_identifier();
			match(ASSIGN);
			rhs=constant_expression();
			if ( inputState.guessing==0 ) {
				gi = getTree().genvarInit(lhs,rhs);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		return gi;
	}
	
/**REMOVE LEFT RECURSION
genvar_expression
:	genvar_primary
| 	unary_operator (attribute_instance)* genvar_primary
| 	genvar_expression binary_operator (attribute_instance)* genvar_expression
| 	genvar_expression QMARK (attribute_instance)* 
		genvar_expression COLON genvar_expression
;
**/
	public final GenvarExpression  genvar_expression() throws RecognitionException, TokenStreamException {
		GenvarExpression ge;
		
		ge=null; Object e1=null, e2=null;
		
		try {      // for error handling
			e1=genvar_expression_1();
			e2=genvar_expression_2();
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_67);
			} else {
			  throw ex;
			}
		}
		return ge;
	}
	
	public final GenvarIteration  genvar_iteration() throws RecognitionException, TokenStreamException {
		GenvarIteration gi;
		
		gi=null; GenvarIdent gv=null; GenvarExpression ge=null;
		
		try {      // for error handling
			gv=genvar_identifier();
			match(ASSIGN);
			ge=genvar_expression();
			if ( inputState.guessing==0 ) {
				gi = getTree().genvarIteration(gv,ge);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_19);
			} else {
			  throw ex;
			}
		}
		return gi;
	}
	
	public final GenerateBlock  generate_block() throws RecognitionException, TokenStreamException {
		GenerateBlock gb;
		
		gb=null; ModuleOrGenerateItem mi=null; GenerateBlockIdent bi=null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LPAREN:
			case LITERAL_defparam:
			case LITERAL_localparam:
			case LITERAL_integer:
			case LITERAL_real:
			case LITERAL_realtime:
			case LITERAL_time:
			case LITERAL_reg:
			case LITERAL_event:
			case 33:
			case 34:
			case LITERAL_tri:
			case LITERAL_triand:
			case LITERAL_trior:
			case 38:
			case 39:
			case LITERAL_uwire:
			case LITERAL_wire:
			case LITERAL_wand:
			case LITERAL_wor:
			case LITERAL_function:
			case LITERAL_task:
			case LITERAL_genvar:
			case LITERAL_for:
			case LITERAL_if:
			case LITERAL_case:
			case LITERAL_assign:
			case LITERAL_initial:
			case LITERAL_always:
			case LITERAL_or:
			case IDENT:
			case ESCAPED_IDENT:
			case LITERAL_and:
			case LITERAL_nand:
			case LITERAL_nor:
			case LITERAL_xor:
			case LITERAL_xnor:
			{
				mi=module_or_generate_item();
				if ( inputState.guessing==0 ) {
					gb = getTree().generateBlock(bi,mi);
				}
				break;
			}
			case LITERAL_begin:
			{
				match(LITERAL_begin);
				{
				switch ( LA(1)) {
				case COLON:
				{
					match(COLON);
					bi=generate_block_identifier();
					break;
				}
				case LPAREN:
				case LITERAL_defparam:
				case LITERAL_localparam:
				case LITERAL_integer:
				case LITERAL_real:
				case LITERAL_realtime:
				case LITERAL_time:
				case LITERAL_reg:
				case LITERAL_event:
				case 33:
				case 34:
				case LITERAL_tri:
				case LITERAL_triand:
				case LITERAL_trior:
				case 38:
				case 39:
				case LITERAL_uwire:
				case LITERAL_wire:
				case LITERAL_wand:
				case LITERAL_wor:
				case LITERAL_function:
				case LITERAL_task:
				case LITERAL_genvar:
				case LITERAL_for:
				case LITERAL_if:
				case LITERAL_case:
				case LITERAL_end:
				case LITERAL_assign:
				case LITERAL_initial:
				case LITERAL_always:
				case LITERAL_or:
				case IDENT:
				case ESCAPED_IDENT:
				case LITERAL_and:
				case LITERAL_nand:
				case LITERAL_nor:
				case LITERAL_xor:
				case LITERAL_xnor:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				if ( inputState.guessing==0 ) {
					gb = getTree().generateBlock(bi,mi);
				}
				{
				_loop318:
				do {
					if ((_tokenSet_11.member(LA(1)))) {
						mi=module_or_generate_item();
						if ( inputState.guessing==0 ) {
							getTree().generateBlock(gb,mi);
						}
					}
					else {
						break _loop318;
					}
					
				} while (true);
				}
				match(LITERAL_end);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		return gb;
	}
	
	public final Object  genvar_expression_1() throws RecognitionException, TokenStreamException {
		Object e1;
		
		e1=null; GenvarPrimary gp=null; UnaryOp uop=null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LPAREN:
			case LCURLY:
			case NUMBER:
			case STRING:
			case IDENT:
			case ESCAPED_IDENT:
			case SYSTEM_TASK_NAME:
			{
				gp=genvar_primary();
				break;
			}
			case MINUS:
			case PLUS:
			case LNOT:
			case BNOT:
			case BAND:
			case RNAND:
			case BOR:
			case RNOR:
			case BXOR:
			case RXNOR:
			{
				uop=unary_operator();
				{
				_loop292:
				do {
					if ((LA(1)==LPAREN) && (LA(2)==STAR)) {
						attribute_instance();
					}
					else {
						break _loop292;
					}
					
				} while (true);
				}
				gp=genvar_primary();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				e1 = getTree().genvarExpression(uop,gp);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_67);
			} else {
			  throw ex;
			}
		}
		return e1;
	}
	
	public final Object  genvar_expression_2() throws RecognitionException, TokenStreamException {
		Object ge2;
		
		ge2=null; BinaryOp bop=null; GenvarExpression gv1=null, gv2=null;
		Object e2=null; boolean isNull = false;
		
		
		try {      // for error handling
			{
			if ((_tokenSet_68.member(LA(1))) && (_tokenSet_69.member(LA(2)))) {
				bop=binary_operator();
				{
				_loop296:
				do {
					if ((LA(1)==LPAREN) && (LA(2)==STAR)) {
						attribute_instance();
					}
					else {
						break _loop296;
					}
					
				} while (true);
				}
				gv1=genvar_expression();
				e2=genvar_expression_2();
			}
			else if ((LA(1)==QMARK) && (_tokenSet_69.member(LA(2)))) {
				match(QMARK);
				{
				_loop298:
				do {
					if ((LA(1)==LPAREN) && (LA(2)==STAR)) {
						attribute_instance();
					}
					else {
						break _loop298;
					}
					
				} while (true);
				}
				gv1=genvar_expression();
				match(COLON);
				gv2=genvar_expression();
				e2=genvar_expression_2();
			}
			else if ((_tokenSet_67.member(LA(1))) && (_tokenSet_70.member(LA(2)))) {
				if ( inputState.guessing==0 ) {
					isNull=true;
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
				ge2 = (isNull) ? null : getTree().genvarExpression(bop,gv1,gv2,e2);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_67);
			} else {
			  throw ex;
			}
		}
		return ge2;
	}
	
	public final GenvarPrimary  genvar_primary() throws RecognitionException, TokenStreamException {
		GenvarPrimary gp;
		
		gp=null; ConstPrimary cp=null; GenvarIdent gi=null;
		
		try {      // for error handling
			{
			boolean synPredMatched303 = false;
			if (((_tokenSet_71.member(LA(1))) && (_tokenSet_72.member(LA(2))))) {
				int _m303 = mark();
				synPredMatched303 = true;
				inputState.guessing++;
				try {
					{
					constant_primary();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched303 = false;
				}
				rewind(_m303);
inputState.guessing--;
			}
			if ( synPredMatched303 ) {
				cp=constant_primary();
			}
			else if ((LA(1)==IDENT||LA(1)==ESCAPED_IDENT) && (_tokenSet_67.member(LA(2)))) {
				gi=genvar_identifier();
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
				gp = getTree().genvarPrimary(cp,gi);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_67);
			} else {
			  throw ex;
			}
		}
		return gp;
	}
	
	public final UnaryOp  unary_operator() throws RecognitionException, TokenStreamException {
		UnaryOp rop;
		
			rop=null; int op=LA(1);
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case MINUS:
			{
				match(MINUS);
				break;
			}
			case PLUS:
			{
				match(PLUS);
				break;
			}
			case LNOT:
			{
				match(LNOT);
				break;
			}
			case BNOT:
			{
				match(BNOT);
				break;
			}
			case BAND:
			{
				match(BAND);
				break;
			}
			case RNAND:
			{
				match(RNAND);
				break;
			}
			case BOR:
			{
				match(BOR);
				break;
			}
			case RNOR:
			{
				match(RNOR);
				break;
			}
			case BXOR:
			{
				match(BXOR);
				break;
			}
			case RXNOR:
			{
				match(RXNOR);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				rop = getTree().unaryOp(op);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_71);
			} else {
			  throw ex;
			}
		}
		return rop;
	}
	
	public final BinaryOp  binary_operator() throws RecognitionException, TokenStreamException {
		BinaryOp rop;
		
			rop=null; int op=LA(1);
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case MINUS:
			{
				match(MINUS);
				break;
			}
			case PLUS:
			{
				match(PLUS);
				break;
			}
			case BAND:
			{
				match(BAND);
				break;
			}
			case BOR:
			{
				match(BOR);
				break;
			}
			case BXOR:
			{
				match(BXOR);
				break;
			}
			case RXNOR:
			{
				match(RXNOR);
				break;
			}
			case STAR:
			{
				match(STAR);
				break;
			}
			case DIV:
			{
				match(DIV);
				break;
			}
			case MOD:
			{
				match(MOD);
				break;
			}
			case EQUAL:
			{
				match(EQUAL);
				break;
			}
			case NOT_EQ:
			{
				match(NOT_EQ);
				break;
			}
			case NOT_EQ_CASE:
			{
				match(NOT_EQ_CASE);
				break;
			}
			case EQ_CASE:
			{
				match(EQ_CASE);
				break;
			}
			case LAND:
			{
				match(LAND);
				break;
			}
			case LOR:
			{
				match(LOR);
				break;
			}
			case LT_:
			{
				match(LT_);
				break;
			}
			case LE:
			{
				match(LE);
				break;
			}
			case GT:
			{
				match(GT);
				break;
			}
			case GE:
			{
				match(GE);
				break;
			}
			case SR:
			{
				match(SR);
				break;
			}
			case SL:
			{
				match(SL);
				break;
			}
			case SR3:
			{
				match(SR3);
				break;
			}
			case SL3:
			{
				match(SL3);
				break;
			}
			case STAR2:
			{
				match(STAR2);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				rop = getTree().binaryOp(op);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_69);
			} else {
			  throw ex;
			}
		}
		return rop;
	}
	
	public final ConstPrimary  constant_primary() throws RecognitionException, TokenStreamException {
		ConstPrimary cp;
		
		cp=null; Primary p=null;
		
		try {      // for error handling
			p=primary();
			if ( inputState.guessing==0 ) {
				cp = getTree().constantPrimary(p);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_67);
			} else {
			  throw ex;
			}
		}
		return cp;
	}
	
	public final IfGenerateConstruct  if_generate_construct() throws RecognitionException, TokenStreamException {
		IfGenerateConstruct igs;
		
		igs=null; ConstExpression ifExpr=null; GenerateBlock ifb=null, elb=null;
		
		try {      // for error handling
			match(LITERAL_if);
			match(LPAREN);
			ifExpr=constant_expression();
			match(RPAREN);
			ifb=generate_block_or_null();
			{
			if ((LA(1)==LITERAL_else) && (_tokenSet_73.member(LA(2)))) {
				match(LITERAL_else);
				elb=generate_block_or_null();
			}
			else if ((_tokenSet_28.member(LA(1))) && (_tokenSet_74.member(LA(2)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
				igs = getTree().ifGenerateConstruct(ifExpr, ifb, elb);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		return igs;
	}
	
	public final CaseGenerateConstruct  case_generate_construct() throws RecognitionException, TokenStreamException {
		CaseGenerateConstruct cgc;
		
		cgc=null; ConstExpression ce=null; CaseGenerateItem cgi=null;
		
		try {      // for error handling
			match(LITERAL_case);
			match(LPAREN);
			ce=constant_expression();
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				cgc = getTree().caseGenerateConstruct(cgc,ce,null);
			}
			cgi=case_generate_item();
			if ( inputState.guessing==0 ) {
				getTree().caseGenerateConstruct(cgc,null,cgi);
			}
			{
			_loop310:
			do {
				if ((_tokenSet_75.member(LA(1)))) {
					cgi=case_generate_item();
					if ( inputState.guessing==0 ) {
						getTree().caseGenerateConstruct(cgc,null,cgi);
					}
				}
				else {
					break _loop310;
				}
				
			} while (true);
			}
			match(LITERAL_endcase);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		return cgc;
	}
	
	public final GenerateBlock  generate_block_or_null() throws RecognitionException, TokenStreamException {
		GenerateBlock gb;
		
		gb=null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LPAREN:
			case LITERAL_defparam:
			case LITERAL_localparam:
			case LITERAL_integer:
			case LITERAL_real:
			case LITERAL_realtime:
			case LITERAL_time:
			case LITERAL_reg:
			case LITERAL_event:
			case 33:
			case 34:
			case LITERAL_tri:
			case LITERAL_triand:
			case LITERAL_trior:
			case 38:
			case 39:
			case LITERAL_uwire:
			case LITERAL_wire:
			case LITERAL_wand:
			case LITERAL_wor:
			case LITERAL_function:
			case LITERAL_task:
			case LITERAL_genvar:
			case LITERAL_for:
			case LITERAL_if:
			case LITERAL_case:
			case LITERAL_begin:
			case LITERAL_assign:
			case LITERAL_initial:
			case LITERAL_always:
			case LITERAL_or:
			case IDENT:
			case ESCAPED_IDENT:
			case LITERAL_and:
			case LITERAL_nand:
			case LITERAL_nor:
			case LITERAL_xor:
			case LITERAL_xnor:
			{
				gb=generate_block();
				break;
			}
			case SEMI:
			{
				match(SEMI);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_28);
			} else {
			  throw ex;
			}
		}
		return gb;
	}
	
	public final CaseGenerateItem  case_generate_item() throws RecognitionException, TokenStreamException {
		CaseGenerateItem cgi;
		
		cgi=null; ConstExpression ce=null; GenerateBlock gb=null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LPAREN:
			case LCURLY:
			case NUMBER:
			case STRING:
			case IDENT:
			case ESCAPED_IDENT:
			case SYSTEM_TASK_NAME:
			case MINUS:
			case PLUS:
			case LNOT:
			case BNOT:
			case BAND:
			case RNAND:
			case BOR:
			case RNOR:
			case BXOR:
			case RXNOR:
			{
				ce=constant_expression();
				if ( inputState.guessing==0 ) {
					cgi = getTree().caseGenerateItem(null,ce,null);
				}
				{
				_loop313:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						ce=constant_expression();
						if ( inputState.guessing==0 ) {
							getTree().caseGenerateItem(cgi,ce,null);
						}
					}
					else {
						break _loop313;
					}
					
				} while (true);
				}
				match(COLON);
				gb=generate_block_or_null();
				if ( inputState.guessing==0 ) {
					getTree().caseGenerateItem(cgi,null,gb);
				}
				break;
			}
			case LITERAL_default:
			{
				match(LITERAL_default);
				{
				switch ( LA(1)) {
				case COLON:
				{
					match(COLON);
					break;
				}
				case SEMI:
				case LPAREN:
				case LITERAL_defparam:
				case LITERAL_localparam:
				case LITERAL_integer:
				case LITERAL_real:
				case LITERAL_realtime:
				case LITERAL_time:
				case LITERAL_reg:
				case LITERAL_event:
				case 33:
				case 34:
				case LITERAL_tri:
				case LITERAL_triand:
				case LITERAL_trior:
				case 38:
				case 39:
				case LITERAL_uwire:
				case LITERAL_wire:
				case LITERAL_wand:
				case LITERAL_wor:
				case LITERAL_function:
				case LITERAL_task:
				case LITERAL_genvar:
				case LITERAL_for:
				case LITERAL_if:
				case LITERAL_case:
				case LITERAL_begin:
				case LITERAL_assign:
				case LITERAL_initial:
				case LITERAL_always:
				case LITERAL_or:
				case IDENT:
				case ESCAPED_IDENT:
				case LITERAL_and:
				case LITERAL_nand:
				case LITERAL_nor:
				case LITERAL_xor:
				case LITERAL_xnor:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				gb=generate_block_or_null();
				if ( inputState.guessing==0 ) {
					cgi = getTree().caseGenerateItem(null,null,gb);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_76);
			} else {
			  throw ex;
			}
		}
		return cgi;
	}
	
	public final GenerateBlockIdent  generate_block_identifier() throws RecognitionException, TokenStreamException {
		GenerateBlockIdent rid;
		
		rid=null; Ident id=null;
		
		try {      // for error handling
			id=identifier();
			if ( inputState.guessing==0 ) {
				rid = getTree().generateBlockIdentifier(id);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_77);
			} else {
			  throw ex;
			}
		}
		return rid;
	}
	
	public final ListOf<NetAssign>  list_of_net_assignments() throws RecognitionException, TokenStreamException {
		ListOf<NetAssign> lo;
		
		lo=null; NetAssign na=null;
		
		try {      // for error handling
			na=net_assignment();
			if ( inputState.guessing==0 ) {
				lo = getTree().addToList(lo, na);
			}
			{
			_loop324:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					na=net_assignment();
					if ( inputState.guessing==0 ) {
						getTree().addToList(lo, na);
					}
				}
				else {
					break _loop324;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		return lo;
	}
	
	public final NetAssign  net_assignment() throws RecognitionException, TokenStreamException {
		NetAssign na;
		
		na=null; Lvalue lv=null;  Expression exp=null;
		
		try {      // for error handling
			lv=lvalue();
			match(ASSIGN);
			exp=expression();
			if ( inputState.guessing==0 ) {
				na = getTree().netAssignment(lv,exp);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_40);
			} else {
			  throw ex;
			}
		}
		return na;
	}
	
	public final Lvalue  lvalue() throws RecognitionException, TokenStreamException {
		Lvalue lv;
		
		lv=null; Object o1=null; Lvalue olv=null; ListOf<Lvalue> lof=null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case IDENT:
			case ESCAPED_IDENT:
			{
				o1=lvalue2();
				if ( inputState.guessing==0 ) {
					lv  = getTree().lvalue(o1);
				}
				break;
			}
			case LCURLY:
			{
				match(LCURLY);
				olv=lvalue();
				if ( inputState.guessing==0 ) {
					lof = getTree().lvalue(lof, olv);
				}
				{
				_loop455:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						olv=lvalue();
						if ( inputState.guessing==0 ) {
							getTree().lvalue(lof, olv);
						}
					}
					else {
						break _loop455;
					}
					
				} while (true);
				}
				match(RCURLY);
				if ( inputState.guessing==0 ) {
					lv = getTree().lvalue(lof);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_78);
			} else {
			  throw ex;
			}
		}
		return lv;
	}
	
	public final Statement  statement() throws RecognitionException, TokenStreamException {
		Statement stmt;
		
		stmt=null; Object o1=null;
		
		try {      // for error handling
			{
			_loop348:
			do {
				if ((LA(1)==LPAREN)) {
					attribute_instance();
				}
				else {
					break _loop348;
				}
				
			} while (true);
			}
			{
			switch ( LA(1)) {
			case LITERAL_case:
			case LITERAL_casex:
			case LITERAL_casez:
			{
				o1=case_statement();
				if ( inputState.guessing==0 ) {
					stmt = getTree().statement((CaseStatement)o1);
				}
				break;
			}
			case LITERAL_if:
			{
				o1=conditional_statement();
				if ( inputState.guessing==0 ) {
					stmt = getTree().statement((ConditionalStatement)o1);
				}
				break;
			}
			case LITERAL_disable:
			{
				o1=disable_statement();
				if ( inputState.guessing==0 ) {
					stmt = getTree().statement((DisableStmt)o1);
				}
				break;
			}
			case TRIGGER:
			{
				o1=event_trigger();
				if ( inputState.guessing==0 ) {
					stmt = getTree().statement((EventTrigger)o1);
				}
				break;
			}
			case LITERAL_for:
			case LITERAL_repeat:
			case LITERAL_forever:
			case LITERAL_while:
			{
				o1=loop_statement();
				if ( inputState.guessing==0 ) {
					stmt = getTree().statement((LoopStatement)o1);
				}
				break;
			}
			case LITERAL_fork:
			{
				o1=par_block();
				if ( inputState.guessing==0 ) {
					stmt = getTree().statement((ParBlock)o1);
				}
				break;
			}
			case LITERAL_assign:
			case LITERAL_deassign:
			case LITERAL_force:
			case LITERAL_release:
			{
				o1=procedural_continuous_assignments();
				match(SEMI);
				if ( inputState.guessing==0 ) {
					stmt = getTree().statement((ProceduralContinuousAssign)o1);
				}
				break;
			}
			case POUND:
			case AT:
			{
				o1=procedural_timing_control_statement();
				if ( inputState.guessing==0 ) {
					stmt = getTree().statement((ProceduralTimingControlStatement)o1);
				}
				break;
			}
			case LITERAL_begin:
			{
				o1=seq_block();
				if ( inputState.guessing==0 ) {
					stmt = getTree().statement((SeqBlock)o1);
				}
				break;
			}
			case SYSTEM_TASK_NAME:
			{
				o1=system_task_enable();
				if ( inputState.guessing==0 ) {
					stmt = getTree().statement((SystemTaskEnable)o1);
				}
				break;
			}
			case LITERAL_wait:
			{
				o1=wait_statement();
				if ( inputState.guessing==0 ) {
					stmt = getTree().statement((WaitStatement)o1);
				}
				break;
			}
			default:
				boolean synPredMatched351 = false;
				if (((LA(1)==LCURLY||LA(1)==IDENT||LA(1)==ESCAPED_IDENT) && (_tokenSet_79.member(LA(2))))) {
					int _m351 = mark();
					synPredMatched351 = true;
					inputState.guessing++;
					try {
						{
						blocking_assignment();
						match(SEMI);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched351 = false;
					}
					rewind(_m351);
inputState.guessing--;
				}
				if ( synPredMatched351 ) {
					o1=blocking_assignment();
					match(SEMI);
					if ( inputState.guessing==0 ) {
						stmt = getTree().statement((BlockingAssign)o1);
					}
				}
				else if ((LA(1)==LCURLY||LA(1)==IDENT||LA(1)==ESCAPED_IDENT) && (_tokenSet_80.member(LA(2)))) {
					o1=nonblocking_assignment();
					match(SEMI);
					if ( inputState.guessing==0 ) {
						stmt = getTree().statement((NonBlockingAssign)o1);
					}
				}
				else if ((LA(1)==IDENT||LA(1)==ESCAPED_IDENT) && (_tokenSet_81.member(LA(2)))) {
					o1=task_enable();
					if ( inputState.guessing==0 ) {
						stmt = getTree().statement((TaskEnable)o1);
					}
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_59);
			} else {
			  throw ex;
			}
		}
		return stmt;
	}
	
	public final BlockingAssign  blocking_assignment() throws RecognitionException, TokenStreamException {
		BlockingAssign ba;
		
		ba=null; Lvalue lv=null; DelayOrEventControl dec=null; Expression exp=null;
		
		try {      // for error handling
			lv=lvalue();
			match(ASSIGN);
			{
			switch ( LA(1)) {
			case POUND:
			case LITERAL_repeat:
			case AT:
			{
				dec=delay_or_event_control();
				break;
			}
			case LPAREN:
			case LCURLY:
			case NUMBER:
			case STRING:
			case IDENT:
			case ESCAPED_IDENT:
			case SYSTEM_TASK_NAME:
			case MINUS:
			case PLUS:
			case LNOT:
			case BNOT:
			case BAND:
			case RNAND:
			case BOR:
			case RNOR:
			case BXOR:
			case RXNOR:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			exp=expression();
			if ( inputState.guessing==0 ) {
				ba = getTree().blockingAssignment(lv,dec,exp);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		return ba;
	}
	
	public final DelayOrEventControl  delay_or_event_control() throws RecognitionException, TokenStreamException {
		DelayOrEventControl dec;
		
		dec=null; DelayControl dc=null; EventControl ec=null; Expression exp=null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case POUND:
			{
				dc=delay_control();
				if ( inputState.guessing==0 ) {
					dec = getTree().delayOrEventControl(dc);
				}
				break;
			}
			case AT:
			{
				ec=event_control();
				if ( inputState.guessing==0 ) {
					dec = getTree().delayOrEventControl(ec);
				}
				break;
			}
			case LITERAL_repeat:
			{
				match(LITERAL_repeat);
				match(LPAREN);
				exp=expression();
				match(RPAREN);
				ec=event_control();
				if ( inputState.guessing==0 ) {
					dec = getTree().delayOrEventControl(exp,ec);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_69);
			} else {
			  throw ex;
			}
		}
		return dec;
	}
	
	public final NonBlockingAssign  nonblocking_assignment() throws RecognitionException, TokenStreamException {
		NonBlockingAssign nba;
		
		nba=null; Lvalue lv=null; DelayOrEventControl dec=null; Expression exp=null;
		
		try {      // for error handling
			lv=lvalue();
			match(LE);
			{
			switch ( LA(1)) {
			case POUND:
			case LITERAL_repeat:
			case AT:
			{
				dec=delay_or_event_control();
				break;
			}
			case LPAREN:
			case LCURLY:
			case NUMBER:
			case STRING:
			case IDENT:
			case ESCAPED_IDENT:
			case SYSTEM_TASK_NAME:
			case MINUS:
			case PLUS:
			case LNOT:
			case BNOT:
			case BAND:
			case RNAND:
			case BOR:
			case RNOR:
			case BXOR:
			case RXNOR:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			exp=expression();
			if ( inputState.guessing==0 ) {
				nba = getTree().nonBlockingAssignment(lv,dec,exp);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		return nba;
	}
	
	public final ProceduralContinuousAssign  procedural_continuous_assignments() throws RecognitionException, TokenStreamException {
		ProceduralContinuousAssign pca;
		
		pca=null; VariableAssignment vas=null; Lvalue lv=null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_assign:
			{
				match(LITERAL_assign);
				vas=variable_assignment();
				if ( inputState.guessing==0 ) {
					pca = getTree().procContAssign(vas, true);
				}
				break;
			}
			case LITERAL_deassign:
			{
				match(LITERAL_deassign);
				lv=lvalue();
				if ( inputState.guessing==0 ) {
					pca = getTree().procContAssign(lv, true);
				}
				break;
			}
			case LITERAL_force:
			{
				match(LITERAL_force);
				vas=variable_assignment();
				if ( inputState.guessing==0 ) {
					pca = getTree().procContAssign(vas, false);
				}
				break;
			}
			case LITERAL_release:
			{
				match(LITERAL_release);
				lv=lvalue();
				if ( inputState.guessing==0 ) {
					pca = getTree().procContAssign(lv, false);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_10);
			} else {
			  throw ex;
			}
		}
		return pca;
	}
	
	public final VariableAssignment  variable_assignment() throws RecognitionException, TokenStreamException {
		VariableAssignment va;
		
		va=null; Lvalue lv=null; Expression exp=null;
		
		try {      // for error handling
			lv=lvalue();
			match(ASSIGN);
			exp=expression();
			if ( inputState.guessing==0 ) {
				va = getTree().variableAssignment(lv,exp);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_82);
			} else {
			  throw ex;
			}
		}
		return va;
	}
	
	public final ParBlock  par_block() throws RecognitionException, TokenStreamException {
		ParBlock pb;
		
		pb=null; BlockIdent bid=null; BlockItemDecl decl=null; Statement st=null;
		
		try {      // for error handling
			match(LITERAL_fork);
			{
			switch ( LA(1)) {
			case COLON:
			{
				match(COLON);
				bid=block_identifier();
				{
				_loop337:
				do {
					if ((_tokenSet_32.member(LA(1))) && (_tokenSet_13.member(LA(2)))) {
						decl=block_item_declaration();
						if ( inputState.guessing==0 ) {
							pb = getTree().parBlock(pb,bid,decl);
						}
					}
					else {
						break _loop337;
					}
					
				} while (true);
				}
				break;
			}
			case POUND:
			case LPAREN:
			case LCURLY:
			case LITERAL_for:
			case LITERAL_if:
			case LITERAL_case:
			case LITERAL_begin:
			case LITERAL_assign:
			case LITERAL_deassign:
			case LITERAL_force:
			case LITERAL_release:
			case LITERAL_fork:
			case LITERAL_join:
			case LITERAL_repeat:
			case LITERAL_disable:
			case AT:
			case TRIGGER:
			case LITERAL_wait:
			case LITERAL_casex:
			case LITERAL_casez:
			case LITERAL_forever:
			case LITERAL_while:
			case IDENT:
			case ESCAPED_IDENT:
			case SYSTEM_TASK_NAME:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			_loop339:
			do {
				if ((_tokenSet_57.member(LA(1)))) {
					st=statement();
					if ( inputState.guessing==0 ) {
						pb = getTree().parBlock(pb,bid,st);
					}
				}
				else {
					break _loop339;
				}
				
			} while (true);
			}
			match(LITERAL_join);
			if ( inputState.guessing==0 ) {
				
							pb = getTree().parBlock(pb,bid);
							getTree().popScope();
						
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_59);
			} else {
			  throw ex;
			}
		}
		return pb;
	}
	
	public final BlockIdent  block_identifier() throws RecognitionException, TokenStreamException {
		BlockIdent rid;
		
		rid=null; Ident id=null;
		
		try {      // for error handling
			id=identifier();
			if ( inputState.guessing==0 ) {
					rid = getTree().blockIdentifier(id);
							getTree().addSymbol(rid);
						
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_83);
			} else {
			  throw ex;
			}
		}
		return rid;
	}
	
	public final SeqBlock  seq_block() throws RecognitionException, TokenStreamException {
		SeqBlock sb;
		
		sb=null; BlockIdent bid=null; BlockItemDecl decl=null; Statement st=null;
		
		try {      // for error handling
			match(LITERAL_begin);
			{
			switch ( LA(1)) {
			case COLON:
			{
				match(COLON);
				bid=block_identifier();
				{
				_loop343:
				do {
					if ((_tokenSet_32.member(LA(1))) && (_tokenSet_13.member(LA(2)))) {
						decl=block_item_declaration();
						if ( inputState.guessing==0 ) {
							sb = getTree().seqBlock(sb,bid,decl);
						}
					}
					else {
						break _loop343;
					}
					
				} while (true);
				}
				break;
			}
			case POUND:
			case LPAREN:
			case LCURLY:
			case LITERAL_for:
			case LITERAL_if:
			case LITERAL_case:
			case LITERAL_begin:
			case LITERAL_end:
			case LITERAL_assign:
			case LITERAL_deassign:
			case LITERAL_force:
			case LITERAL_release:
			case LITERAL_fork:
			case LITERAL_repeat:
			case LITERAL_disable:
			case AT:
			case TRIGGER:
			case LITERAL_wait:
			case LITERAL_casex:
			case LITERAL_casez:
			case LITERAL_forever:
			case LITERAL_while:
			case IDENT:
			case ESCAPED_IDENT:
			case SYSTEM_TASK_NAME:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			_loop345:
			do {
				if ((_tokenSet_57.member(LA(1)))) {
					st=statement();
					if ( inputState.guessing==0 ) {
						sb = getTree().seqBlock(sb,bid,st);
					}
				}
				else {
					break _loop345;
				}
				
			} while (true);
			}
			match(LITERAL_end);
			if ( inputState.guessing==0 ) {
				
							sb = getTree().seqBlock(sb,bid);
							getTree().popScope();
						
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_59);
			} else {
			  throw ex;
			}
		}
		return sb;
	}
	
	public final CaseStatement  case_statement() throws RecognitionException, TokenStreamException {
		CaseStatement cs;
		
		cs=null; int tk=LA(1); Expression exp=null; CaseItem ci=null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_case:
			{
				match(LITERAL_case);
				break;
			}
			case LITERAL_casex:
			{
				match(LITERAL_casex);
				break;
			}
			case LITERAL_casez:
			{
				match(LITERAL_casez);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(LPAREN);
			exp=expression();
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				cs = getTree().caseStatement(tk, exp);
			}
			ci=case_item();
			if ( inputState.guessing==0 ) {
				getTree().caseStatement(cs, ci);
			}
			{
			_loop381:
			do {
				if ((_tokenSet_75.member(LA(1)))) {
					ci=case_item();
					if ( inputState.guessing==0 ) {
						getTree().caseStatement(cs, ci);
					}
				}
				else {
					break _loop381;
				}
				
			} while (true);
			}
			match(LITERAL_endcase);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_59);
			} else {
			  throw ex;
			}
		}
		return cs;
	}
	
	public final ConditionalStatement  conditional_statement() throws RecognitionException, TokenStreamException {
		ConditionalStatement cs;
		
		cs=null; Expression exp=null; Statement s1=null, s2=null;
		
		try {      // for error handling
			match(LITERAL_if);
			match(LPAREN);
			exp=expression();
			match(RPAREN);
			s1=statement_or_null();
			{
			if ((LA(1)==LITERAL_else) && (_tokenSet_84.member(LA(2)))) {
				match(LITERAL_else);
				s2=statement_or_null();
			}
			else if ((_tokenSet_59.member(LA(1))) && (_tokenSet_85.member(LA(2)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
				cs = getTree().conditionalStatement(exp, s1, s2);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_59);
			} else {
			  throw ex;
			}
		}
		return cs;
	}
	
	public final DisableStmt  disable_statement() throws RecognitionException, TokenStreamException {
		DisableStmt ds;
		
		ds=null; HierIdent hi=null;
		
		try {      // for error handling
			match(LITERAL_disable);
			hi=hierarchical_identifier();
			match(SEMI);
			if ( inputState.guessing==0 ) {
				ds = getTree().disableStatement(hi);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_59);
			} else {
			  throw ex;
			}
		}
		return ds;
	}
	
	public final EventTrigger  event_trigger() throws RecognitionException, TokenStreamException {
		EventTrigger et;
		
		et=null; HierEventIdent hei=null; Expression exp=null;
		
		try {      // for error handling
			match(TRIGGER);
			hei=hierarchical_event_identifier();
			if ( inputState.guessing==0 ) {
				et = getTree().eventTrigger(hei);
			}
			{
			_loop367:
			do {
				if ((LA(1)==LBRACK)) {
					match(LBRACK);
					exp=expression();
					match(RBRACK);
					if ( inputState.guessing==0 ) {
						getTree().eventTrigger(et, exp);
					}
				}
				else {
					break _loop367;
				}
				
			} while (true);
			}
			match(SEMI);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_59);
			} else {
			  throw ex;
			}
		}
		return et;
	}
	
	public final LoopStatement  loop_statement() throws RecognitionException, TokenStreamException {
		LoopStatement lst;
		
		lst=null; Statement st=null; Expression exp=null; 
		VariableAssignment va1=null,va2=null; int tk=LA(1);
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_forever:
			{
				match(LITERAL_forever);
				st=statement();
				if ( inputState.guessing==0 ) {
					lst = getTree().loopStatement(st);
				}
				break;
			}
			case LITERAL_repeat:
			{
				match(LITERAL_repeat);
				match(LPAREN);
				exp=expression();
				match(RPAREN);
				st=statement();
				if ( inputState.guessing==0 ) {
					lst = getTree().loopStatement(tk, exp, st);
				}
				break;
			}
			case LITERAL_while:
			{
				match(LITERAL_while);
				match(LPAREN);
				exp=expression();
				match(RPAREN);
				st=statement();
				if ( inputState.guessing==0 ) {
					lst = getTree().loopStatement(tk, exp, st);
				}
				break;
			}
			case LITERAL_for:
			{
				match(LITERAL_for);
				match(LPAREN);
				va1=variable_assignment();
				match(SEMI);
				exp=expression();
				match(SEMI);
				va2=variable_assignment();
				match(RPAREN);
				st=statement();
				if ( inputState.guessing==0 ) {
					lst = getTree().loopStatement(va1, exp, va2, st);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_59);
			} else {
			  throw ex;
			}
		}
		return lst;
	}
	
	public final ProceduralTimingControlStatement  procedural_timing_control_statement() throws RecognitionException, TokenStreamException {
		ProceduralTimingControlStatement pcs;
		
		pcs=null; ProceduralTimingControl pt=null; Statement st=null;
		
		try {      // for error handling
			pt=procedural_timing_control();
			st=statement_or_null();
			if ( inputState.guessing==0 ) {
				pcs = getTree().procTimingControlStmt(pt, st);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_59);
			} else {
			  throw ex;
			}
		}
		return pcs;
	}
	
	public final SystemTaskEnable  system_task_enable() throws RecognitionException, TokenStreamException {
		SystemTaskEnable ste;
		
		ste=null; SystemTaskIdent sti=null; Expression exp=null;
		
		try {      // for error handling
			sti=system_task_identifier();
			if ( inputState.guessing==0 ) {
				ste = getTree().systemTaskEnable(sti);
			}
			{
			switch ( LA(1)) {
			case LPAREN:
			{
				match(LPAREN);
				{
				switch ( LA(1)) {
				case LPAREN:
				case LCURLY:
				case NUMBER:
				case STRING:
				case IDENT:
				case ESCAPED_IDENT:
				case SYSTEM_TASK_NAME:
				case MINUS:
				case PLUS:
				case LNOT:
				case BNOT:
				case BAND:
				case RNAND:
				case BOR:
				case RNOR:
				case BXOR:
				case RXNOR:
				{
					exp=expression();
					break;
				}
				case RPAREN:
				case COMMA:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				if ( inputState.guessing==0 ) {
					getTree().systemTaskEnable(ste,exp); exp=null;
				}
				{
				_loop392:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						{
						switch ( LA(1)) {
						case LPAREN:
						case LCURLY:
						case NUMBER:
						case STRING:
						case IDENT:
						case ESCAPED_IDENT:
						case SYSTEM_TASK_NAME:
						case MINUS:
						case PLUS:
						case LNOT:
						case BNOT:
						case BAND:
						case RNAND:
						case BOR:
						case RNOR:
						case BXOR:
						case RXNOR:
						{
							exp=expression();
							break;
						}
						case RPAREN:
						case COMMA:
						{
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
						}
						if ( inputState.guessing==0 ) {
							getTree().systemTaskEnable(ste,exp); exp=null;
						}
					}
					else {
						break _loop392;
					}
					
				} while (true);
				}
				match(RPAREN);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(SEMI);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_59);
			} else {
			  throw ex;
			}
		}
		return ste;
	}
	
	public final TaskEnable  task_enable() throws RecognitionException, TokenStreamException {
		TaskEnable te;
		
		te=null; HierTaskIdent hti=null; Expression exp=null;
		
		try {      // for error handling
			hti=hierarchical_task_identifier();
			if ( inputState.guessing==0 ) {
				te = getTree().taskEnable(hti);
			}
			{
			switch ( LA(1)) {
			case LPAREN:
			{
				match(LPAREN);
				exp=expression();
				if ( inputState.guessing==0 ) {
					getTree().taskEnable(te,exp);
				}
				{
				_loop396:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						exp=expression();
						if ( inputState.guessing==0 ) {
							getTree().taskEnable(te,exp);
						}
					}
					else {
						break _loop396;
					}
					
				} while (true);
				}
				match(RPAREN);
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(SEMI);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_59);
			} else {
			  throw ex;
			}
		}
		return te;
	}
	
	public final WaitStatement  wait_statement() throws RecognitionException, TokenStreamException {
		WaitStatement ws;
		
		ws=null; Expression exp=null; Statement st=null;
		
		try {      // for error handling
			match(LITERAL_wait);
			match(LPAREN);
			exp=expression();
			match(RPAREN);
			st=statement_or_null();
			if ( inputState.guessing==0 ) {
				ws = getTree().waitStatement(exp, st);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_59);
			} else {
			  throw ex;
			}
		}
		return ws;
	}
	
	public final DelayControl  delay_control() throws RecognitionException, TokenStreamException {
		DelayControl dc;
		
		dc=null; DelayValue dv=null; MinTypMaxExpression mtm=null;
		
		try {      // for error handling
			if ((LA(1)==POUND) && (LA(2)==NUMBER||LA(2)==IDENT||LA(2)==ESCAPED_IDENT)) {
				match(POUND);
				dv=delay_value();
				if ( inputState.guessing==0 ) {
					dc = getTree().delayControl(dv);
				}
			}
			else if ((LA(1)==POUND) && (LA(2)==LPAREN)) {
				match(POUND);
				match(LPAREN);
				mtm=mintypmax_expression();
				match(RPAREN);
				if ( inputState.guessing==0 ) {
					dc = getTree().delayControl(mtm);
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_86);
			} else {
			  throw ex;
			}
		}
		return dc;
	}
	
	public final EventControl  event_control() throws RecognitionException, TokenStreamException {
		EventControl ec;
		
		ec=null; HierEventIdent hei=null; EventExpression ee=null;
		
		try {      // for error handling
			if ((LA(1)==AT) && (LA(2)==IDENT||LA(2)==ESCAPED_IDENT)) {
				match(AT);
				hei=hierarchical_event_identifier();
				if ( inputState.guessing==0 ) {
					ec = getTree().eventControl(hei);
				}
			}
			else if ((LA(1)==AT) && (LA(2)==LPAREN)) {
				match(AT);
				match(LPAREN);
				{
				switch ( LA(1)) {
				case STAR:
				{
					match(STAR);
					if ( inputState.guessing==0 ) {
						ec = getTree().eventControl(true);
					}
					break;
				}
				case LPAREN:
				case LCURLY:
				case LITERAL_posedge:
				case LITERAL_negedge:
				case NUMBER:
				case STRING:
				case IDENT:
				case ESCAPED_IDENT:
				case SYSTEM_TASK_NAME:
				case MINUS:
				case PLUS:
				case LNOT:
				case BNOT:
				case BAND:
				case RNAND:
				case BOR:
				case RNOR:
				case BXOR:
				case RXNOR:
				{
					ee=event_expression();
					if ( inputState.guessing==0 ) {
						ec = getTree().eventControl(ee);
					}
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				match(RPAREN);
			}
			else if ((LA(1)==AT) && (LA(2)==STAR)) {
				match(AT);
				match(STAR);
				if ( inputState.guessing==0 ) {
					ec = getTree().eventControl(true);
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_86);
			} else {
			  throw ex;
			}
		}
		return ec;
	}
	
	public final HierIdent  hierarchical_identifier() throws RecognitionException, TokenStreamException {
		HierIdent hid;
		
		hid = null; Object hi=null; Ident id=null;
		
		try {      // for error handling
			boolean synPredMatched473 = false;
			if (((LA(1)==IDENT||LA(1)==ESCAPED_IDENT) && (LA(2)==DOT||LA(2)==LBRACK))) {
				int _m473 = mark();
				synPredMatched473 = true;
				inputState.guessing++;
				try {
					{
					identifier();
					{
					switch ( LA(1)) {
					case LBRACK:
					{
						match(LBRACK);
						constant_expression();
						match(RBRACK);
						break;
					}
					case DOT:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					match(DOT);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched473 = false;
				}
				rewind(_m473);
inputState.guessing--;
			}
			if ( synPredMatched473 ) {
				hi=hierarchical_identifier2();
				if ( inputState.guessing==0 ) {
					hid = getTree().hierarchicalIdentifier(hi);
				}
			}
			else if ((LA(1)==IDENT||LA(1)==ESCAPED_IDENT) && (_tokenSet_87.member(LA(2)))) {
				id=identifier();
				if ( inputState.guessing==0 ) {
					hid = getTree().hierarchicalIdentifier(id);
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_87);
			} else {
			  throw ex;
			}
		}
		return hid;
	}
	
	public final HierEventIdent  hierarchical_event_identifier() throws RecognitionException, TokenStreamException {
		HierEventIdent rid;
		
		rid=null; HierIdent id=null;
		
		try {      // for error handling
			id=hierarchical_identifier();
			if ( inputState.guessing==0 ) {
				rid = getTree().hierarchicalEventIdentifier(id);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_88);
			} else {
			  throw ex;
			}
		}
		return rid;
	}
	
/**REMOVE LEFT RECURSION
event_expression
:	expression
|	"posedge" expression
|	"negedge" expression
|	event_expression "or" event_expression
|	event_expression COMMA event_expression
;
**/
	public final EventExpression  event_expression() throws RecognitionException, TokenStreamException {
		EventExpression ee;
		
		ee=null; Object e1=null, e2=null;
		
		try {      // for error handling
			e1=event_expression_1();
			e2=event_expression_2();
			if ( inputState.guessing==0 ) {
				ee = getTree().eventExpression(e1,e2);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_89);
			} else {
			  throw ex;
			}
		}
		return ee;
	}
	
	public final Object  event_expression_1() throws RecognitionException, TokenStreamException {
		Object e1;
		
		e1=null; Expression exp=null; int tk=LA(1);
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LPAREN:
			case LCURLY:
			case NUMBER:
			case STRING:
			case IDENT:
			case ESCAPED_IDENT:
			case SYSTEM_TASK_NAME:
			case MINUS:
			case PLUS:
			case LNOT:
			case BNOT:
			case BAND:
			case RNAND:
			case BOR:
			case RNOR:
			case BXOR:
			case RXNOR:
			{
				exp=expression();
				if ( inputState.guessing==0 ) {
					tk=-1;
				}
				break;
			}
			case LITERAL_posedge:
			{
				match(LITERAL_posedge);
				exp=expression();
				break;
			}
			case LITERAL_negedge:
			{
				match(LITERAL_negedge);
				exp=expression();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				e1 = getTree().event_expression_1(tk, exp);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_89);
			} else {
			  throw ex;
			}
		}
		return e1;
	}
	
	public final Object  event_expression_2() throws RecognitionException, TokenStreamException {
		Object e2;
		
		e2=null; EventExpression ee=null; Object ee2=null;
		
		try {      // for error handling
			{
			if ((LA(1)==LITERAL_or) && (_tokenSet_90.member(LA(2)))) {
				match(LITERAL_or);
				ee=event_expression();
				ee2=event_expression_2();
				if ( inputState.guessing==0 ) {
					e2 = getTree().event_expression_2(true, ee, ee2);
				}
			}
			else if ((LA(1)==COMMA) && (_tokenSet_90.member(LA(2)))) {
				match(COMMA);
				ee=event_expression();
				ee2=event_expression_2();
				if ( inputState.guessing==0 ) {
					e2 = getTree().event_expression_2(false, ee, ee2);
				}
			}
			else if ((LA(1)==RPAREN||LA(1)==COMMA||LA(1)==LITERAL_or) && (_tokenSet_91.member(LA(2)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_89);
			} else {
			  throw ex;
			}
		}
		return e2;
	}
	
	public final ProceduralTimingControl  procedural_timing_control() throws RecognitionException, TokenStreamException {
		ProceduralTimingControl ptc;
		
		ptc=null; DelayControl dc=null; EventControl ec=null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case POUND:
			{
				dc=delay_control();
				if ( inputState.guessing==0 ) {
					ptc = getTree().proceduralTimingControl(dc);
				}
				break;
			}
			case AT:
			{
				ec=event_control();
				if ( inputState.guessing==0 ) {
					ptc = getTree().proceduralTimingControl(ec);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_84);
			} else {
			  throw ex;
			}
		}
		return ptc;
	}
	
	public final CaseItem  case_item() throws RecognitionException, TokenStreamException {
		CaseItem ci;
		
		ci=null; Expression exp=null; Statement stmt=null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LPAREN:
			case LCURLY:
			case NUMBER:
			case STRING:
			case IDENT:
			case ESCAPED_IDENT:
			case SYSTEM_TASK_NAME:
			case MINUS:
			case PLUS:
			case LNOT:
			case BNOT:
			case BAND:
			case RNAND:
			case BOR:
			case RNOR:
			case BXOR:
			case RXNOR:
			{
				exp=expression();
				if ( inputState.guessing==0 ) {
					ci = getTree().caseItem(exp);
				}
				{
				_loop384:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						exp=expression();
						if ( inputState.guessing==0 ) {
							getTree().caseItem(ci, exp);
						}
					}
					else {
						break _loop384;
					}
					
				} while (true);
				}
				match(COLON);
				stmt=statement_or_null();
				if ( inputState.guessing==0 ) {
					getTree().caseItem(ci, stmt);
				}
				break;
			}
			case LITERAL_default:
			{
				match(LITERAL_default);
				{
				switch ( LA(1)) {
				case COLON:
				{
					match(COLON);
					break;
				}
				case SEMI:
				case POUND:
				case LPAREN:
				case LCURLY:
				case LITERAL_for:
				case LITERAL_if:
				case LITERAL_case:
				case LITERAL_begin:
				case LITERAL_assign:
				case LITERAL_deassign:
				case LITERAL_force:
				case LITERAL_release:
				case LITERAL_fork:
				case LITERAL_repeat:
				case LITERAL_disable:
				case AT:
				case TRIGGER:
				case LITERAL_wait:
				case LITERAL_casex:
				case LITERAL_casez:
				case LITERAL_forever:
				case LITERAL_while:
				case IDENT:
				case ESCAPED_IDENT:
				case SYSTEM_TASK_NAME:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				stmt=statement_or_null();
				if ( inputState.guessing==0 ) {
					ci = getTree().caseItem(stmt);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_76);
			} else {
			  throw ex;
			}
		}
		return ci;
	}
	
	public final SystemTaskIdent  system_task_identifier() throws RecognitionException, TokenStreamException {
		SystemTaskIdent rid;
		
		Token  tk = null;
		rid=null;
		
		try {      // for error handling
			tk = LT(1);
			match(SYSTEM_TASK_NAME);
			if ( inputState.guessing==0 ) {
				rid = getTree().systemTaskIdentifier(tk);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		return rid;
	}
	
	public final HierTaskIdent  hierarchical_task_identifier() throws RecognitionException, TokenStreamException {
		HierTaskIdent rid;
		
		rid=null; HierIdent id=null;
		
		try {      // for error handling
			id=hierarchical_identifier();
			if ( inputState.guessing==0 ) {
				rid = getTree().hierarchicalTaskIdentifier(id);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		return rid;
	}
	
	public final Concatenation  concatenation() throws RecognitionException, TokenStreamException {
		Concatenation cc;
		
		cc=null; Expression exp=null;
		
		try {      // for error handling
			match(LCURLY);
			exp=expression();
			if ( inputState.guessing==0 ) {
				cc=getTree().concatenation(null, exp);
			}
			{
			_loop399:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					exp=expression();
					if ( inputState.guessing==0 ) {
						getTree().concatenation(cc, exp);
					}
				}
				else {
					break _loop399;
				}
				
			} while (true);
			}
			match(RCURLY);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_48);
			} else {
			  throw ex;
			}
		}
		return cc;
	}
	
	public final MultConcatenation  multiple_concatenation() throws RecognitionException, TokenStreamException {
		MultConcatenation mc;
		
		mc=null; ConstExpression ce=null; Concatenation cc=null;
		
		try {      // for error handling
			match(LCURLY);
			ce=constant_expression();
			cc=concatenation();
			match(RCURLY);
			if ( inputState.guessing==0 ) {
				mc = getTree().multipleConcatenation(ce, cc);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_48);
			} else {
			  throw ex;
			}
		}
		return mc;
	}
	
	public final FunctionCall  function_call() throws RecognitionException, TokenStreamException {
		FunctionCall fc;
		
		fc=null; HierFunctionIdent hfi=null; Expression exp=null;
		
		try {      // for error handling
			hfi=hierarchical_function_identifier();
			{
			_loop403:
			do {
				if ((LA(1)==LPAREN) && (LA(2)==STAR)) {
					attribute_instance();
				}
				else {
					break _loop403;
				}
				
			} while (true);
			}
			match(LPAREN);
			exp=expression();
			if ( inputState.guessing==0 ) {
				fc = getTree().functionCall(hfi, exp);
			}
			{
			_loop405:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					exp=expression();
					if ( inputState.guessing==0 ) {
						getTree().functionCall(fc, exp);
					}
				}
				else {
					break _loop405;
				}
				
			} while (true);
			}
			match(RPAREN);
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_48);
			} else {
			  throw ex;
			}
		}
		return fc;
	}
	
	public final HierFunctionIdent  hierarchical_function_identifier() throws RecognitionException, TokenStreamException {
		HierFunctionIdent rid;
		
		rid=null; HierIdent id=null;
		
		try {      // for error handling
			id=hierarchical_identifier();
			if ( inputState.guessing==0 ) {
				rid = getTree().hierarchicalFunctionIdentifier(id);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_63);
			} else {
			  throw ex;
			}
		}
		return rid;
	}
	
	public final SystemFunctionCall  system_function_call() throws RecognitionException, TokenStreamException {
		SystemFunctionCall sfc;
		
		sfc=null; SystemFunctionIdent sfi=null; Expression exp=null;
		
		try {      // for error handling
			sfi=system_function_identifier();
			if ( inputState.guessing==0 ) {
				sfc = getTree().systemFunctionCall(sfi);
			}
			{
			switch ( LA(1)) {
			case LPAREN:
			{
				match(LPAREN);
				exp=expression();
				if ( inputState.guessing==0 ) {
					getTree().systemFunctionCall(sfc, exp);
				}
				{
				_loop409:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						exp=expression();
						if ( inputState.guessing==0 ) {
							getTree().systemFunctionCall(sfc, exp);
						}
					}
					else {
						break _loop409;
					}
					
				} while (true);
				}
				match(RPAREN);
				break;
			}
			case EOF:
			case SEMI:
			case RPAREN:
			case COMMA:
			case LCURLY:
			case RCURLY:
			case RBRACK:
			case COLON:
			case QMARK:
			case LE:
			case STAR:
			case LITERAL_or:
			case PLUS_COLON:
			case MINUS_COLON:
			case MINUS:
			case PLUS:
			case BAND:
			case BOR:
			case BXOR:
			case RXNOR:
			case DIV:
			case MOD:
			case EQUAL:
			case NOT_EQ:
			case NOT_EQ_CASE:
			case EQ_CASE:
			case LAND:
			case LOR:
			case LT_:
			case GT:
			case GE:
			case SR:
			case SL:
			case SR3:
			case SL3:
			case STAR2:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_48);
			} else {
			  throw ex;
			}
		}
		return sfc;
	}
	
	public final SystemFunctionIdent  system_function_identifier() throws RecognitionException, TokenStreamException {
		SystemFunctionIdent rid;
		
		Token  tk = null;
		rid=null;
		
		try {      // for error handling
			tk = LT(1);
			match(SYSTEM_TASK_NAME);
			if ( inputState.guessing==0 ) {
				rid = getTree().systemFunctionIdentifier(tk);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_92);
			} else {
			  throw ex;
			}
		}
		return rid;
	}
	
	public final Expression  base_expression() throws RecognitionException, TokenStreamException {
		Expression exp;
		
		exp=null;
		
		try {      // for error handling
			exp=expression();
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_93);
			} else {
			  throw ex;
			}
		}
		return exp;
	}
	
	public final ConstExpression  constant_base_expression() throws RecognitionException, TokenStreamException {
		ConstExpression cexp;
		
		cexp=null;
		
		try {      // for error handling
			cexp=constant_expression();
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		return cexp;
	}
	
	public final Object  expression_1() throws RecognitionException, TokenStreamException {
		Object expr;
		
			expr=null; Object uop=null, prim=null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LPAREN:
			case LCURLY:
			case NUMBER:
			case STRING:
			case IDENT:
			case ESCAPED_IDENT:
			case SYSTEM_TASK_NAME:
			{
				prim=primary();
				break;
			}
			case MINUS:
			case PLUS:
			case LNOT:
			case BNOT:
			case BAND:
			case RNAND:
			case BOR:
			case RNOR:
			case BXOR:
			case RXNOR:
			{
				uop=unary_operator();
				{
				_loop423:
				do {
					if ((LA(1)==LPAREN) && (LA(2)==STAR)) {
						attribute_instance();
					}
					else {
						break _loop423;
					}
					
				} while (true);
				}
				prim=primary();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				expr = getTree().expression_1(uop, prim);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_48);
			} else {
			  throw ex;
			}
		}
		return expr;
	}
	
	public final Object  expression_2() throws RecognitionException, TokenStreamException {
		Object expr;
		
		expr=null; Object e1=null, e2=null, e3=null; boolean isTernary=false;
		
		try {      // for error handling
			{
			if ((_tokenSet_68.member(LA(1))) && (_tokenSet_69.member(LA(2)))) {
				e1=binary_operator();
				{
				_loop427:
				do {
					if ((LA(1)==LPAREN) && (LA(2)==STAR)) {
						attribute_instance();
					}
					else {
						break _loop427;
					}
					
				} while (true);
				}
				e2=expression();
				e3=expression_2();
			}
			else if ((LA(1)==QMARK) && (_tokenSet_69.member(LA(2)))) {
				match(QMARK);
				{
				_loop429:
				do {
					if ((LA(1)==LPAREN) && (LA(2)==STAR)) {
						attribute_instance();
					}
					else {
						break _loop429;
					}
					
				} while (true);
				}
				e1=expression();
				match(COLON);
				e2=expression();
				e3=expression_2();
				if ( inputState.guessing==0 ) {
					isTernary=true;
				}
			}
			else if ((_tokenSet_48.member(LA(1))) && (_tokenSet_94.member(LA(2)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
				expr = getTree().expression_2(isTernary, e1, e2, e3);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_48);
			} else {
			  throw ex;
			}
		}
		return expr;
	}
	
	public final Primary  primary() throws RecognitionException, TokenStreamException {
		Primary prim;
		
		prim=null; Object o1=null; HierIdent id=null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case NUMBER:
			{
				o1=number();
				if ( inputState.guessing==0 ) {
					prim = getTree().primary((Vnumber)o1);
				}
				break;
			}
			case SYSTEM_TASK_NAME:
			{
				o1=system_function_call();
				if ( inputState.guessing==0 ) {
					prim = getTree().primary((SystemFunctionCall)o1);
				}
				break;
			}
			case LPAREN:
			{
				match(LPAREN);
				o1=mintypmax_expression();
				match(RPAREN);
				if ( inputState.guessing==0 ) {
					prim = getTree().primary((MinTypMaxExpression)o1);
				}
				break;
			}
			case STRING:
			{
				o1=string();
				if ( inputState.guessing==0 ) {
					prim = getTree().primary((Vstring)o1);
				}
				break;
			}
			default:
				if ((LA(1)==IDENT||LA(1)==ESCAPED_IDENT) && (_tokenSet_95.member(LA(2)))) {
					id=hierarchical_identifier();
					{
					switch ( LA(1)) {
					case LBRACK:
					{
						o1=part_select();
						break;
					}
					case EOF:
					case SEMI:
					case RPAREN:
					case COMMA:
					case LCURLY:
					case RCURLY:
					case RBRACK:
					case COLON:
					case QMARK:
					case LE:
					case STAR:
					case LITERAL_or:
					case PLUS_COLON:
					case MINUS_COLON:
					case MINUS:
					case PLUS:
					case BAND:
					case BOR:
					case BXOR:
					case RXNOR:
					case DIV:
					case MOD:
					case EQUAL:
					case NOT_EQ:
					case NOT_EQ_CASE:
					case EQ_CASE:
					case LAND:
					case LOR:
					case LT_:
					case GT:
					case GE:
					case SR:
					case SL:
					case SR3:
					case SL3:
					case STAR2:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					if ( inputState.guessing==0 ) {
						prim = getTree().primary(id, (PartSelect)o1);
					}
				}
				else {
					boolean synPredMatched450 = false;
					if (((LA(1)==IDENT||LA(1)==ESCAPED_IDENT) && (LA(2)==LPAREN||LA(2)==DOT||LA(2)==LBRACK))) {
						int _m450 = mark();
						synPredMatched450 = true;
						inputState.guessing++;
						try {
							{
							function_call();
							}
						}
						catch (RecognitionException pe) {
							synPredMatched450 = false;
						}
						rewind(_m450);
inputState.guessing--;
					}
					if ( synPredMatched450 ) {
						o1=function_call();
						if ( inputState.guessing==0 ) {
							prim = getTree().primary((FunctionCall)o1);
						}
					}
					else {
						boolean synPredMatched452 = false;
						if (((LA(1)==LCURLY) && (_tokenSet_69.member(LA(2))))) {
							int _m452 = mark();
							synPredMatched452 = true;
							inputState.guessing++;
							try {
								{
								multiple_concatenation();
								}
							}
							catch (RecognitionException pe) {
								synPredMatched452 = false;
							}
							rewind(_m452);
inputState.guessing--;
						}
						if ( synPredMatched452 ) {
							o1=multiple_concatenation();
							if ( inputState.guessing==0 ) {
								prim = getTree().primary((MultConcatenation)o1);
							}
						}
						else if ((LA(1)==LCURLY) && (_tokenSet_69.member(LA(2)))) {
							o1=concatenation();
							if ( inputState.guessing==0 ) {
								prim = getTree().primary((Concatenation)o1);
							}
						}
					else {
						throw new NoViableAltException(LT(1), getFilename());
					}
					}}}
				}
				catch (RecognitionException ex) {
					if (inputState.guessing==0) {
						reportError(ex);
						recover(ex,_tokenSet_48);
					} else {
					  throw ex;
					}
				}
				return prim;
			}
			
	public final RangeExpression  range_expression() throws RecognitionException, TokenStreamException {
		RangeExpression rexp;
		
		rexp=null; Expression lhs=null; ConstExpression rhs=null; int tk=LA(2);
		
		try {      // for error handling
			{
			boolean synPredMatched437 = false;
			if (((_tokenSet_69.member(LA(1))) && (_tokenSet_96.member(LA(2))))) {
				int _m437 = mark();
				synPredMatched437 = true;
				inputState.guessing++;
				try {
					{
					msb_constant_expression();
					match(COLON);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched437 = false;
				}
				rewind(_m437);
inputState.guessing--;
			}
			if ( synPredMatched437 ) {
				lhs=msb_constant_expression();
				match(COLON);
				rhs=lsb_constant_expression();
			}
			else {
				boolean synPredMatched439 = false;
				if (((_tokenSet_69.member(LA(1))) && (_tokenSet_97.member(LA(2))))) {
					int _m439 = mark();
					synPredMatched439 = true;
					inputState.guessing++;
					try {
						{
						base_expression();
						match(PLUS_COLON);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched439 = false;
					}
					rewind(_m439);
inputState.guessing--;
				}
				if ( synPredMatched439 ) {
					lhs=base_expression();
					match(PLUS_COLON);
					rhs=width_constant_expression();
				}
				else {
					boolean synPredMatched441 = false;
					if (((_tokenSet_69.member(LA(1))) && (_tokenSet_98.member(LA(2))))) {
						int _m441 = mark();
						synPredMatched441 = true;
						inputState.guessing++;
						try {
							{
							base_expression();
							match(MINUS_COLON);
							}
						}
						catch (RecognitionException pe) {
							synPredMatched441 = false;
						}
						rewind(_m441);
inputState.guessing--;
					}
					if ( synPredMatched441 ) {
						lhs=base_expression();
						match(MINUS_COLON);
						rhs=width_constant_expression();
					}
					else if ((_tokenSet_69.member(LA(1))) && (_tokenSet_99.member(LA(2)))) {
						lhs=expression();
						if ( inputState.guessing==0 ) {
							tk=-1;
						}
					}
					else {
						throw new NoViableAltException(LT(1), getFilename());
					}
					}}
					}
					if ( inputState.guessing==0 ) {
						rexp = getTree().rangeExpression(lhs, tk, rhs);
					}
				}
				catch (RecognitionException ex) {
					if (inputState.guessing==0) {
						reportError(ex);
						recover(ex,_tokenSet_25);
					} else {
					  throw ex;
					}
				}
				return rexp;
			}
			
	public final ConstExpression  width_constant_expression() throws RecognitionException, TokenStreamException {
		ConstExpression cexp;
		
		cexp=null;
		
		try {      // for error handling
			cexp=constant_expression();
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_25);
			} else {
			  throw ex;
			}
		}
		return cexp;
	}
	
	public final PartSelect  part_select() throws RecognitionException, TokenStreamException {
		PartSelect ps;
		
		ps=null; RangeExpression rexp=null;
		
		try {      // for error handling
			{
			int _cnt446=0;
			_loop446:
			do {
				if ((LA(1)==LBRACK)) {
					match(LBRACK);
					rexp=range_expression();
					if ( inputState.guessing==0 ) {
						ps = getTree().partSelect(ps, rexp);
					}
					match(RBRACK);
				}
				else {
					if ( _cnt446>=1 ) { break _loop446; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt446++;
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_100);
			} else {
			  throw ex;
			}
		}
		return ps;
	}
	
	public final Vstring  string() throws RecognitionException, TokenStreamException {
		Vstring v;
		
		Token  tk = null;
		v = null;
		
		try {      // for error handling
			tk = LT(1);
			match(STRING);
			if ( inputState.guessing==0 ) {
				v = getTree().string(tk);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_48);
			} else {
			  throw ex;
			}
		}
		return v;
	}
	
	public final Object  lvalue2() throws RecognitionException, TokenStreamException {
		Object o1;
		
		o1=null; HierIdent hi=null; PartSelect ps=null;
		
		try {      // for error handling
			hi=hierarchical_identifier();
			{
			switch ( LA(1)) {
			case LBRACK:
			{
				ps=part_select();
				break;
			}
			case SEMI:
			case COMMA:
			case RCURLY:
			case ASSIGN:
			case LE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				o1 = getTree().lvalue2(hi,ps);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_78);
			} else {
			  throw ex;
			}
		}
		return o1;
	}
	
	public final void attr_spec() throws RecognitionException, TokenStreamException {
		
		ConstExpression unused=null;
		
		try {      // for error handling
			attr_name();
			{
			switch ( LA(1)) {
			case ASSIGN:
			{
				match(ASSIGN);
				unused=constant_expression();
				break;
			}
			case COMMA:
			case STAR:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_101);
			} else {
			  throw ex;
			}
		}
	}
	
	public final void attr_name() throws RecognitionException, TokenStreamException {
		
		Ident unused=null;
		
		try {      // for error handling
			unused=identifier();
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_102);
			} else {
			  throw ex;
			}
		}
	}
	
	public final Object  hierarchical_identifier2() throws RecognitionException, TokenStreamException {
		Object hid;
		
		hid=null; Ident id=null; ConstExpression cex=null; HierIdent hid2=null;
		
		try {      // for error handling
			id=identifier();
			{
			switch ( LA(1)) {
			case LBRACK:
			{
				match(LBRACK);
				cex=constant_expression();
				match(RBRACK);
				break;
			}
			case DOT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				hid = getTree().hierarchicalIdentifier2(id, cex);
			}
			match(DOT);
			{
			int _cnt477=0;
			_loop477:
			do {
				if ((LA(1)==IDENT||LA(1)==ESCAPED_IDENT) && (_tokenSet_103.member(LA(2)))) {
					hid2=hierarchical_identifier();
					if ( inputState.guessing==0 ) {
						getTree().hierarchicalIdentifier2(hid, hid2);
					}
				}
				else {
					if ( _cnt477>=1 ) { break _loop477; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt477++;
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_87);
			} else {
			  throw ex;
			}
		}
		return hid;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"SEMI",
		"\"endmodule\"",
		"\"module\"",
		"\"macromodule\"",
		"POUND",
		"LPAREN",
		"RPAREN",
		"COMMA",
		"DOT",
		"LCURLY",
		"RCURLY",
		"LBRACK",
		"RBRACK",
		"\"defparam\"",
		"\"localparam\"",
		"\"signed\"",
		"\"parameter\"",
		"\"specparam\"",
		"\"integer\"",
		"\"real\"",
		"\"realtime\"",
		"\"time\"",
		"\"inout\"",
		"\"input\"",
		"\"output\"",
		"\"reg\"",
		"\"event\"",
		"\"vectored\"",
		"\"scalared\"",
		"\"supply0\"",
		"\"supply1\"",
		"\"tri\"",
		"\"triand\"",
		"\"trior\"",
		"\"tri0\"",
		"\"tri1\"",
		"\"uwire\"",
		"\"wire\"",
		"\"wand\"",
		"\"wor\"",
		"ASSIGN",
		"COLON",
		"\"function\"",
		"\"automatic\"",
		"\"endfunction\"",
		"\"task\"",
		"\"endtask\"",
		"\"generate\"",
		"\"endgenerate\"",
		"\"genvar\"",
		"\"for\"",
		"QMARK",
		"\"if\"",
		"\"else\"",
		"\"case\"",
		"\"endcase\"",
		"\"default\"",
		"\"begin\"",
		"\"end\"",
		"\"assign\"",
		"\"initial\"",
		"\"always\"",
		"LE",
		"\"deassign\"",
		"\"force\"",
		"\"release\"",
		"\"fork\"",
		"\"join\"",
		"\"repeat\"",
		"\"disable\"",
		"AT",
		"STAR",
		"TRIGGER",
		"\"posedge\"",
		"\"negedge\"",
		"\"or\"",
		"\"wait\"",
		"\"casex\"",
		"\"casez\"",
		"\"forever\"",
		"\"while\"",
		"PLUS_COLON",
		"MINUS_COLON",
		"NUMBER",
		"STRING",
		"IDENT",
		"ESCAPED_IDENT",
		"SYSTEM_TASK_NAME",
		"\"and\"",
		"\"nand\"",
		"\"nor\"",
		"\"xor\"",
		"\"xnor\"",
		"MINUS",
		"PLUS",
		"LNOT",
		"BNOT",
		"BAND",
		"RNAND",
		"BOR",
		"RNOR",
		"BXOR",
		"RXNOR",
		"DIV",
		"MOD",
		"EQUAL",
		"NOT_EQ",
		"NOT_EQ_CASE",
		"EQ_CASE",
		"LAND",
		"LOR",
		"LT_",
		"GT",
		"GE",
		"SR",
		"SL",
		"SR3",
		"SL3",
		"STAR2",
		"PPATH",
		"FPATH",
		"TIC_DIRECTIVE",
		"TIC_LINE",
		"SIZED_NUMBER",
		"SIZE",
		"BASE",
		"SIZED_DIGIT",
		"UNSIZED_NUMBER",
		"DIGIT",
		"HEXDIGIT",
		"EXPONENT",
		"WS2",
		"WS",
		"CNTRL",
		"SL_COMMENT",
		"ML_COMMENT"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 2L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 706L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { -8833159764646689248L, 8422195203L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { -8833159765116452352L, 8422195203L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 15360L, 100663296L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = { -8833159764646690304L, 8422195203L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	private static final long[] mk_tokenSet_6() {
		long[] data = { -6529568555246665776L, 8796086703995L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	private static final long[] mk_tokenSet_7() {
		long[] data = { 0L, 8422195200L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
	private static final long[] mk_tokenSet_8() {
		long[] data = { 784L, 100663296L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
	private static final long[] mk_tokenSet_9() {
		long[] data = { 528L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
	private static final long[] mk_tokenSet_10() {
		long[] data = { 16L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
	private static final long[] mk_tokenSet_11() {
		long[] data = { -8835411564933283328L, 8422195203L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
	private static final long[] mk_tokenSet_12() {
		long[] data = { -6539085914947673344L, 236920696L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
	private static final long[] mk_tokenSet_13() {
		long[] data = { 63471616L, 100665344L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
	private static final long[] mk_tokenSet_14() {
		long[] data = { -8833159764646690272L, 8422195203L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
	private static final long[] mk_tokenSet_15() {
		long[] data = { 48144L, 100663296L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
	private static final long[] mk_tokenSet_16() {
		long[] data = { 469762560L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
	private static final long[] mk_tokenSet_17() {
		long[] data = { 17584171286528L, 100665344L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
	private static final long[] mk_tokenSet_18() {
		long[] data = { -6524924212160847104L, 8558452603L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
	private static final long[] mk_tokenSet_19() {
		long[] data = { 1024L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
	private static final long[] mk_tokenSet_20() {
		long[] data = { 557056L, 100663296L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
	private static final long[] mk_tokenSet_21() {
		long[] data = { 3088L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_21 = new BitSet(mk_tokenSet_21());
	private static final long[] mk_tokenSet_22() {
		long[] data = { 3072L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_22 = new BitSet(mk_tokenSet_22());
	private static final long[] mk_tokenSet_23() {
		long[] data = { 17592186097168L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_23 = new BitSet(mk_tokenSet_23());
	private static final long[] mk_tokenSet_24() {
		long[] data = { 19456L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_24 = new BitSet(mk_tokenSet_24());
	private static final long[] mk_tokenSet_25() {
		long[] data = { 65536L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_25 = new BitSet(mk_tokenSet_25());
	private static final long[] mk_tokenSet_26() {
		long[] data = { 537427968L, 100663296L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_26 = new BitSet(mk_tokenSet_26());
	private static final long[] mk_tokenSet_27() {
		long[] data = { 17583596666880L, 100663296L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_27 = new BitSet(mk_tokenSet_27());
	private static final long[] mk_tokenSet_28() {
		long[] data = { -2343472701605797344L, 8796084666371L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_28 = new BitSet(mk_tokenSet_28());
	private static final long[] mk_tokenSet_29() {
		long[] data = { -37629692392103120L, 8796086704123L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_29 = new BitSet(mk_tokenSet_29());
	private static final long[] mk_tokenSet_30() {
		long[] data = { 2144600576L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_30 = new BitSet(mk_tokenSet_30());
	private static final long[] mk_tokenSet_31() {
		long[] data = { 600342528L, 100665344L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_31 = new BitSet(mk_tokenSet_31());
	private static final long[] mk_tokenSet_32() {
		long[] data = { 1674838528L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_32 = new BitSet(mk_tokenSet_32());
	private static final long[] mk_tokenSet_33() {
		long[] data = { 63471616L, 100663296L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_33 = new BitSet(mk_tokenSet_33());
	private static final long[] mk_tokenSet_34() {
		long[] data = { 41488L, 8787763134464L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_34 = new BitSet(mk_tokenSet_34());
	private static final long[] mk_tokenSet_35() {
		long[] data = { 1809056256L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_35 = new BitSet(mk_tokenSet_35());
	private static final long[] mk_tokenSet_36() {
		long[] data = { 768L, 100663296L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_36 = new BitSet(mk_tokenSet_36());
	private static final long[] mk_tokenSet_37() {
		long[] data = { 0L, 100663296L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_37 = new BitSet(mk_tokenSet_37());
	private static final long[] mk_tokenSet_38() {
		long[] data = { 6443008256L, 100663296L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_38 = new BitSet(mk_tokenSet_38());
	private static final long[] mk_tokenSet_39() {
		long[] data = { 8194L, 100663296L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_39 = new BitSet(mk_tokenSet_39());
	private static final long[] mk_tokenSet_40() {
		long[] data = { 2064L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_40 = new BitSet(mk_tokenSet_40());
	private static final long[] mk_tokenSet_41() {
		long[] data = { 17592186079248L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_41 = new BitSet(mk_tokenSet_41());
	private static final long[] mk_tokenSet_42() {
		long[] data = { 34832L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_42 = new BitSet(mk_tokenSet_42());
	private static final long[] mk_tokenSet_43() {
		long[] data = { 35184372165650L, 6293504L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_43 = new BitSet(mk_tokenSet_43());
	private static final long[] mk_tokenSet_44() {
		long[] data = { -6539226658941948142L, 8787765172088L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_44 = new BitSet(mk_tokenSet_44());
	private static final long[] mk_tokenSet_45() {
		long[] data = { -6503162677550813422L, 576460743981899644L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_45 = new BitSet(mk_tokenSet_45());
	private static final long[] mk_tokenSet_46() {
		long[] data = { -1881800963713925358L, 576460752303398911L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_46 = new BitSet(mk_tokenSet_46());
	private static final long[] mk_tokenSet_47() {
		long[] data = { 17592186044416L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_47 = new BitSet(mk_tokenSet_47());
	private static final long[] mk_tokenSet_48() {
		long[] data = { 36063981391146002L, 576459266251065348L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_48 = new BitSet(mk_tokenSet_48());
	private static final long[] mk_tokenSet_49() {
		long[] data = { 17592186044928L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_49 = new BitSet(mk_tokenSet_49());
	private static final long[] mk_tokenSet_50() {
		long[] data = { 35184372154368L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_50 = new BitSet(mk_tokenSet_50());
	private static final long[] mk_tokenSet_51() {
		long[] data = { 35184372088832L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_51 = new BitSet(mk_tokenSet_51());
	private static final long[] mk_tokenSet_52() {
		long[] data = { -6539226657132895488L, 236918648L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_52 = new BitSet(mk_tokenSet_52());
	private static final long[] mk_tokenSet_53() {
		long[] data = { -1927540638369963248L, 236918776L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_53 = new BitSet(mk_tokenSet_53());
	private static final long[] mk_tokenSet_54() {
		long[] data = { 281474976710656L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_54 = new BitSet(mk_tokenSet_54());
	private static final long[] mk_tokenSet_55() {
		long[] data = { -6539226656797351152L, 236918648L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_55 = new BitSet(mk_tokenSet_55());
	private static final long[] mk_tokenSet_56() {
		long[] data = { -36222317508549856L, 8796086706171L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_56 = new BitSet(mk_tokenSet_56());
	private static final long[] mk_tokenSet_57() {
		long[] data = { -6539226658941951232L, 236918648L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_57 = new BitSet(mk_tokenSet_57());
	private static final long[] mk_tokenSet_58() {
		long[] data = { -1927487863956393200L, 245309436L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_58 = new BitSet(mk_tokenSet_58());
	private static final long[] mk_tokenSet_59() {
		long[] data = { -36222317508549856L, 8796086704123L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_59 = new BitSet(mk_tokenSet_59());
	private static final long[] mk_tokenSet_60() {
		long[] data = { 512L, 100663296L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_60 = new BitSet(mk_tokenSet_60());
	private static final long[] mk_tokenSet_61() {
		long[] data = { 15872L, 8787763134464L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_61 = new BitSet(mk_tokenSet_61());
	private static final long[] mk_tokenSet_62() {
		long[] data = { 36028797019012624L, 576460743973537796L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_62 = new BitSet(mk_tokenSet_62());
	private static final long[] mk_tokenSet_63() {
		long[] data = { 512L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_63 = new BitSet(mk_tokenSet_63());
	private static final long[] mk_tokenSet_64() {
		long[] data = { 11776L, 8787763134464L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_64 = new BitSet(mk_tokenSet_64());
	private static final long[] mk_tokenSet_65() {
		long[] data = { 33280L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_65 = new BitSet(mk_tokenSet_65());
	private static final long[] mk_tokenSet_66() {
		long[] data = { 36081573577100304L, 576459266244741124L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_66 = new BitSet(mk_tokenSet_66());
	private static final long[] mk_tokenSet_67() {
		long[] data = { 36063981391053840L, 576459266244741124L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_67 = new BitSet(mk_tokenSet_67());
	private static final long[] mk_tokenSet_68() {
		long[] data = { 0L, 576459266244741124L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_68 = new BitSet(mk_tokenSet_68());
	private static final long[] mk_tokenSet_69() {
		long[] data = { 8704L, 8787763134464L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_69 = new BitSet(mk_tokenSet_69());
	private static final long[] mk_tokenSet_70() {
		long[] data = { -6529568555719581184L, 8796084666371L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_70 = new BitSet(mk_tokenSet_70());
	private static final long[] mk_tokenSet_71() {
		long[] data = { 8704L, 260046848L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_71 = new BitSet(mk_tokenSet_71());
	private static final long[] mk_tokenSet_72() {
		long[] data = { 36063981391099408L, 576460743973537796L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_72 = new BitSet(mk_tokenSet_72());
	private static final long[] mk_tokenSet_73() {
		long[] data = { -6529568555719589360L, 8422195203L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_73 = new BitSet(mk_tokenSet_73());
	private static final long[] mk_tokenSet_74() {
		long[] data = { -1424967069680654L, 576460752297107327L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_74 = new BitSet(mk_tokenSet_74());
	private static final long[] mk_tokenSet_75() {
		long[] data = { 1152921504606855680L, 8787763134464L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_75 = new BitSet(mk_tokenSet_75());
	private static final long[] mk_tokenSet_76() {
		long[] data = { 1729382256910279168L, 8787763134464L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_76 = new BitSet(mk_tokenSet_76());
	private static final long[] mk_tokenSet_77() {
		long[] data = { -4223725546505895424L, 8422195203L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_77 = new BitSet(mk_tokenSet_77());
	private static final long[] mk_tokenSet_78() {
		long[] data = { 17592186062864L, 4L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_78 = new BitSet(mk_tokenSet_78());
	private static final long[] mk_tokenSet_79() {
		long[] data = { 17592186089472L, 100663296L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_79 = new BitSet(mk_tokenSet_79());
	private static final long[] mk_tokenSet_80() {
		long[] data = { 45056L, 100663300L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_80 = new BitSet(mk_tokenSet_80());
	private static final long[] mk_tokenSet_81() {
		long[] data = { 37392L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_81 = new BitSet(mk_tokenSet_81());
	private static final long[] mk_tokenSet_82() {
		long[] data = { 1040L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_82 = new BitSet(mk_tokenSet_82());
	private static final long[] mk_tokenSet_83() {
		long[] data = { -1927540638839725312L, 236918776L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_83 = new BitSet(mk_tokenSet_83());
	private static final long[] mk_tokenSet_84() {
		long[] data = { -6539226658941951216L, 236918648L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_84 = new BitSet(mk_tokenSet_84());
	private static final long[] mk_tokenSet_85() {
		long[] data = { -82958L, 576460752297107455L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_85 = new BitSet(mk_tokenSet_85());
	private static final long[] mk_tokenSet_86() {
		long[] data = { -6539226658941951216L, 8787765172088L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_86 = new BitSet(mk_tokenSet_86());
	private static final long[] mk_tokenSet_87() {
		long[] data = { -6503145085364736238L, 576460743981899644L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_87 = new BitSet(mk_tokenSet_87());
	private static final long[] mk_tokenSet_88() {
		long[] data = { -6539226658941918448L, 8787765172088L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_88 = new BitSet(mk_tokenSet_88());
	private static final long[] mk_tokenSet_89() {
		long[] data = { 3072L, 32768L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_89 = new BitSet(mk_tokenSet_89());
	private static final long[] mk_tokenSet_90() {
		long[] data = { 8704L, 8787763159040L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_90 = new BitSet(mk_tokenSet_90());
	private static final long[] mk_tokenSet_91() {
		long[] data = { -6539226658941951216L, 8787765196664L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_91 = new BitSet(mk_tokenSet_91());
	private static final long[] mk_tokenSet_92() {
		long[] data = { 36063981391146514L, 576459266251065348L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_92 = new BitSet(mk_tokenSet_92());
	private static final long[] mk_tokenSet_93() {
		long[] data = { 0L, 6291456L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_93 = new BitSet(mk_tokenSet_93());
	private static final long[] mk_tokenSet_94() {
		long[] data = { -140743931330766L, 576460752303423487L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_94 = new BitSet(mk_tokenSet_94());
	private static final long[] mk_tokenSet_95() {
		long[] data = { 36063981391182866L, 576459266251065348L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_95 = new BitSet(mk_tokenSet_95());
	private static final long[] mk_tokenSet_96() {
		long[] data = { 36063981391098368L, 576460743973537796L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_96 = new BitSet(mk_tokenSet_96());
	private static final long[] mk_tokenSet_97() {
		long[] data = { 36028797019009536L, 576460743975634948L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_97 = new BitSet(mk_tokenSet_97());
	private static final long[] mk_tokenSet_98() {
		long[] data = { 36028797019009536L, 576460743977732100L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_98 = new BitSet(mk_tokenSet_98());
	private static final long[] mk_tokenSet_99() {
		long[] data = { 36028797019075072L, 576460743973537796L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_99 = new BitSet(mk_tokenSet_99());
	private static final long[] mk_tokenSet_100() {
		long[] data = { 36081573577190418L, 576459266251065348L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_100 = new BitSet(mk_tokenSet_100());
	private static final long[] mk_tokenSet_101() {
		long[] data = { 2048L, 2048L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_101 = new BitSet(mk_tokenSet_101());
	private static final long[] mk_tokenSet_102() {
		long[] data = { 17592186046464L, 2048L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_102 = new BitSet(mk_tokenSet_102());
	private static final long[] mk_tokenSet_103() {
		long[] data = { -6503145085364732142L, 576460743981899644L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_103 = new BitSet(mk_tokenSet_103());
	
	}
