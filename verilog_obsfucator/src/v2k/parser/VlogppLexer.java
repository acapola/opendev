// $ANTLR 2.7.7 (20080509): "Vlogpp.g" -> "VlogppLexer.java"$

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

import java.io.InputStream;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.TokenStreamRecognitionException;
import antlr.CharStreamException;
import antlr.CharStreamIOException;
import antlr.ANTLRException;
import java.io.Reader;
import java.util.Hashtable;
import antlr.CharScanner;
import antlr.InputBuffer;
import antlr.ByteBuffer;
import antlr.CharBuffer;
import antlr.Token;
import antlr.CommonToken;
import antlr.RecognitionException;
import antlr.NoViableAltForCharException;
import antlr.MismatchedCharException;
import antlr.TokenStream;
import antlr.ANTLRHashString;
import antlr.LexerSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.SemanticException;

public class VlogppLexer extends antlr.CharScanner implements VlogppLexerTokenTypes, TokenStream
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
public VlogppLexer(InputStream in) {
	this(new ByteBuffer(in));
}
public VlogppLexer(Reader in) {
	this(new CharBuffer(in));
}
public VlogppLexer(InputBuffer ib) {
	this(new LexerSharedInputState(ib));
}
public VlogppLexer(LexerSharedInputState state) {
	super(state);
	caseSensitiveLiterals = true;
	setCaseSensitive(true);
	literals = new Hashtable();
}

public Token nextToken() throws TokenStreamException {
	Token theRetToken=null;
tryAgain:
	for (;;) {
		Token _token = null;
		int _ttype = Token.INVALID_TYPE;
		resetText();
		try {   // for char stream error handling
			try {   // for lexical error handling
				if ((LA(1)=='`') && (_tokenSet_0.member(LA(2)))) {
					mTIC_DIRECTIVE(true);
					theRetToken=_returnToken;
				}
				else if ((LA(1)=='"') && (_tokenSet_1.member(LA(2)))) {
					mSTRING(true);
					theRetToken=_returnToken;
				}
				else if ((LA(1)=='/') && (LA(2)=='*'||LA(2)=='/')) {
					mCOMMENT(true);
					theRetToken=_returnToken;
				}
				else if ((_tokenSet_0.member(LA(1))) && (true)) {
					mIDENT(true);
					theRetToken=_returnToken;
				}
				else if ((_tokenSet_2.member(LA(1))) && (true)) {
					mWS(true);
					theRetToken=_returnToken;
				}
				else if (((LA(1) >= '\u0000' && LA(1) <= '\u00ff')) && (true)) {
					mDUMMY(true);
					theRetToken=_returnToken;
				}
				else {
					if (LA(1)==EOF_CHAR) {uponEOF(); _returnToken = makeToken(Token.EOF_TYPE);}
				else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
				}
				
				if ( _returnToken==null ) continue tryAgain; // found SKIP token
				_ttype = _returnToken.getType();
				_returnToken.setType(_ttype);
				return _returnToken;
			}
			catch (RecognitionException e) {
				throw new TokenStreamRecognitionException(e);
			}
		}
		catch (CharStreamException cse) {
			if ( cse instanceof CharStreamIOException ) {
				throw new TokenStreamIOException(((CharStreamIOException)cse).io);
			}
			else {
				throw new TokenStreamException(cse.getMessage());
			}
		}
	}
}

	public final void mTIC_DIRECTIVE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = TIC_DIRECTIVE;
		int _saveIndex;
		Token id=null;
		Token td=null;
		Token id5=null;
		Token id7=null;
		Token id9=null;
		Token id10=null;
		
		match('`');
		mIDENT(true);
		id=_returnToken;
		{
		if (((_tokenSet_2.member(LA(1))) && (_tokenSet_3.member(LA(2))))&&(id.getText().equals("define"))) {
			mTIC_DEFINE(true);
			td=_returnToken;
			if ( inputState.guessing==0 ) {
				
									String s = td.getText();
									if (Parser.stDumpDefn) {
										text.setLength(_begin); text.append("/*@DEFINE@ "+s+"*/");
									} else {
										String t = scrub2nl(s);
										text.setLength(_begin); text.append(t);
									}
								
			}
		}
		else if (((_tokenSet_2.member(LA(1))) && (_tokenSet_4.member(LA(2))))&&(id.getText().equals("include"))) {
			mTIC_INCLUDE(false);
		}
		else if (((_tokenSet_2.member(LA(1))) && (_tokenSet_5.member(LA(2))))&&(id.getText().equals("timescale"))) {
			mTIC_TIMESCALE(false);
			if ( inputState.guessing==0 ) {
				_ttype = Token.SKIP;
			}
		}
		else if (((_tokenSet_2.member(LA(1))) && (_tokenSet_3.member(LA(2))))&&(id.getText().equals("ifdef"))) {
			{
			int _cnt4=0;
			_loop4:
			do {
				if ((_tokenSet_2.member(LA(1)))) {
					mWS(false);
				}
				else {
					if ( _cnt4>=1 ) { break _loop4; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
				}
				
				_cnt4++;
			} while (true);
			}
			mIDENT(true);
			id5=_returnToken;
			if ( inputState.guessing==0 ) {
				stPP.ticIfdef(id5.getText()); _ttype = Token.SKIP;
			}
		}
		else if (((_tokenSet_2.member(LA(1))) && (_tokenSet_3.member(LA(2))))&&(id.getText().equals("ifndef"))) {
			{
			int _cnt6=0;
			_loop6:
			do {
				if ((_tokenSet_2.member(LA(1)))) {
					mWS(false);
				}
				else {
					if ( _cnt6>=1 ) { break _loop6; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
				}
				
				_cnt6++;
			} while (true);
			}
			mIDENT(true);
			id7=_returnToken;
			if ( inputState.guessing==0 ) {
				stPP.ticIfndef(id7.getText()); _ttype = Token.SKIP;
			}
		}
		else if (((_tokenSet_2.member(LA(1))) && (_tokenSet_3.member(LA(2))))&&(id.getText().equals("elsif"))) {
			{
			int _cnt8=0;
			_loop8:
			do {
				if ((_tokenSet_2.member(LA(1)))) {
					mWS(false);
				}
				else {
					if ( _cnt8>=1 ) { break _loop8; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
				}
				
				_cnt8++;
			} while (true);
			}
			mIDENT(true);
			id9=_returnToken;
			if ( inputState.guessing==0 ) {
				stPP.ticElsif(id9.getText()); _ttype = Token.SKIP;
			}
		}
		else if (((_tokenSet_2.member(LA(1))) && (_tokenSet_3.member(LA(2))))&&(id.getText().equals("undef"))) {
			{
			int _cnt10=0;
			_loop10:
			do {
				if ((_tokenSet_2.member(LA(1)))) {
					mWS(false);
				}
				else {
					if ( _cnt10>=1 ) { break _loop10; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
				}
				
				_cnt10++;
			} while (true);
			}
			mIDENT(true);
			id10=_returnToken;
			if ( inputState.guessing==0 ) {
				stPP.undef(id10.getText()); _ttype = Token.SKIP;
			}
		}
		else if (( true )&&(id.getText().equals("else"))) {
			if ( inputState.guessing==0 ) {
				stPP.ticElse(); _ttype = Token.SKIP;
			}
		}
		else if (( true )&&(id.getText().equals("endif"))) {
			if ( inputState.guessing==0 ) {
				stPP.ticEndif(); _ttype = Token.SKIP;
			}
		}
		else if (( true )&&(id.getText().equals("celldefine"))) {
			if ( inputState.guessing==0 ) {
				_ttype = Token.SKIP;
			}
		}
		else if (( true )&&(id.getText().equals("endcelldefine"))) {
			if ( inputState.guessing==0 ) {
				_ttype = Token.SKIP;
			}
		}
		else if (( true )&&(id.getText().equals("default_nettype"))) {
			mSKIP_DIRECTIVE(false);
			if ( inputState.guessing==0 ) {
				_ttype = Token.SKIP;
			}
		}
		else if (( true )&&(id.getText().equals("begin_keywords"))) {
			mSKIP_DIRECTIVE(false);
			if ( inputState.guessing==0 ) {
				_ttype = Token.SKIP;
			}
		}
		else if (( true )&&(id.getText().equals("end_keywords"))) {
			mSKIP_DIRECTIVE(false);
			if ( inputState.guessing==0 ) {
				_ttype = Token.SKIP;
			}
		}
		else if (( true )&&(id.getText().equals("line"))) {
			mSKIP_DIRECTIVE(false);
			if ( inputState.guessing==0 ) {
				_ttype = Token.SKIP;
			}
		}
		else if (( true )&&(id.getText().equals("unconnected_drive"))) {
			mSKIP_DIRECTIVE(false);
			if ( inputState.guessing==0 ) {
				_ttype = Token.SKIP;
			}
		}
		else if (( true )&&(id.getText().equals("nounconnected_drive"))) {
			mSKIP_DIRECTIVE(false);
			if ( inputState.guessing==0 ) {
				_ttype = Token.SKIP;
			}
		}
		else if (( true )&&(id.getText().equals("pragma"))) {
			mSKIP_DIRECTIVE(false);
			if ( inputState.guessing==0 ) {
				_ttype = Token.SKIP;
			}
		}
		else if (( true )&&(id.getText().equals("resetall"))) {
			mSKIP_DIRECTIVE(false);
			if ( inputState.guessing==0 ) {
				_ttype = Token.SKIP;
			}
		}
		else if (( true )&&(id.getText().equals("default_decay_time"))) {
			mSKIP_DIRECTIVE(false);
			if ( inputState.guessing==0 ) {
				_ttype = Token.SKIP;
			}
		}
		else if (( true )&&(id.getText().equals("default_trireg_strength"))) {
			mSKIP_DIRECTIVE(false);
			if ( inputState.guessing==0 ) {
				_ttype = Token.SKIP;
			}
		}
		else if (( true )&&(id.getText().equals("delay_mode_distributed"))) {
			mSKIP_DIRECTIVE(false);
			if ( inputState.guessing==0 ) {
				_ttype = Token.SKIP;
			}
		}
		else if (( true )&&(id.getText().equals("delay_mode_path"))) {
			mSKIP_DIRECTIVE(false);
			if ( inputState.guessing==0 ) {
				_ttype = Token.SKIP;
			}
		}
		else if (( true )&&(id.getText().equals("delay_mode_unit"))) {
			mSKIP_DIRECTIVE(false);
			if ( inputState.guessing==0 ) {
				_ttype = Token.SKIP;
			}
		}
		else if (( true )&&(id.getText().equals("delay_mode_zero"))) {
			mSKIP_DIRECTIVE(false);
			if ( inputState.guessing==0 ) {
				_ttype = Token.SKIP;
			}
		}
		else {
			mMACRO_EXPAND(false,id.getText());
			if ( inputState.guessing==0 ) {
				_ttype = Token.SKIP;
			}
		}
		
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mIDENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = IDENT;
		int _saveIndex;
		
		{
		switch ( LA(1)) {
		case 'a':  case 'b':  case 'c':  case 'd':
		case 'e':  case 'f':  case 'g':  case 'h':
		case 'i':  case 'j':  case 'k':  case 'l':
		case 'm':  case 'n':  case 'o':  case 'p':
		case 'q':  case 'r':  case 's':  case 't':
		case 'u':  case 'v':  case 'w':  case 'x':
		case 'y':  case 'z':
		{
			matchRange('a','z');
			break;
		}
		case 'A':  case 'B':  case 'C':  case 'D':
		case 'E':  case 'F':  case 'G':  case 'H':
		case 'I':  case 'J':  case 'K':  case 'L':
		case 'M':  case 'N':  case 'O':  case 'P':
		case 'Q':  case 'R':  case 'S':  case 'T':
		case 'U':  case 'V':  case 'W':  case 'X':
		case 'Y':  case 'Z':
		{
			matchRange('A','Z');
			break;
		}
		case '_':
		{
			match('_');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		{
		_loop47:
		do {
			if (((LA(1) >= 'a' && LA(1) <= 'z')) && (true)) {
				matchRange('a','z');
			}
			else if (((LA(1) >= 'A' && LA(1) <= 'Z')) && (true)) {
				matchRange('A','Z');
			}
			else if ((LA(1)=='_') && (true)) {
				match('_');
			}
			else if ((LA(1)=='$') && (true)) {
				match('$');
			}
			else if (((LA(1) >= '0' && LA(1) <= '9')) && (true)) {
				matchRange('0','9');
			}
			else {
				break _loop47;
			}
			
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mTIC_DEFINE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = TIC_DEFINE;
		int _saveIndex;
		Token id=null;
		Token def=null;
		
		{
		int _cnt69=0;
		_loop69:
		do {
			if ((_tokenSet_2.member(LA(1)))) {
				mWS(false);
			}
			else {
				if ( _cnt69>=1 ) { break _loop69; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
			}
			
			_cnt69++;
		} while (true);
		}
		mIDENT(true);
		id=_returnToken;
		{
		boolean synPredMatched72 = false;
		if (((LA(1)=='(') && (_tokenSet_6.member(LA(2))))) {
			int _m72 = mark();
			synPredMatched72 = true;
			inputState.guessing++;
			try {
				{
				match('(');
				}
			}
			catch (RecognitionException pe) {
				synPredMatched72 = false;
			}
			rewind(_m72);
inputState.guessing--;
		}
		if ( synPredMatched72 ) {
			mTIC_DEFINE_PARMS(false,id.getText());
		}
		else if (((LA(1) >= '\u0000' && LA(1) <= '\u00ff')) && (true)) {
			mDEFINE(true);
			def=_returnToken;
			if ( inputState.guessing==0 ) {
					
									stPP.addDefine(id.getText(), def.getText());
								
			}
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mTIC_INCLUDE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = TIC_INCLUDE;
		int _saveIndex;
		Token fn=null;
		
		{
		int _cnt83=0;
		_loop83:
		do {
			if ((_tokenSet_2.member(LA(1)))) {
				mWS(false);
			}
			else {
				if ( _cnt83>=1 ) { break _loop83; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
			}
			
			_cnt83++;
		} while (true);
		}
		mSTRING(true);
		fn=_returnToken;
		if ( inputState.guessing==0 ) {
				
						stPP.includeFile(fn.getText());
					
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mTIC_TIMESCALE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = TIC_TIMESCALE;
		int _saveIndex;
		
		{
		int _cnt59=0;
		_loop59:
		do {
			if ((_tokenSet_2.member(LA(1)))) {
				mWS(false);
			}
			else {
				if ( _cnt59>=1 ) { break _loop59; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
			}
			
			_cnt59++;
		} while (true);
		}
		mTIMESCALE_VALUE(false);
		{
		_loop61:
		do {
			if ((_tokenSet_2.member(LA(1)))) {
				mWS(false);
			}
			else {
				break _loop61;
			}
			
		} while (true);
		}
		match('/');
		{
		_loop63:
		do {
			if ((_tokenSet_2.member(LA(1)))) {
				mWS(false);
			}
			else {
				break _loop63;
			}
			
		} while (true);
		}
		mTIMESCALE_VALUE(false);
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mWS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = WS;
		int _saveIndex;
		
		{
		switch ( LA(1)) {
		case ' ':
		{
			match(' ');
			break;
		}
		case '\r':
		{
			match('\r');
			break;
		}
		case '\t':
		{
			match('\t');
			break;
		}
		case '\n':
		{
			match('\n');
			if ( inputState.guessing==0 ) {
				newline();
			}
			break;
		}
		default:
			if ((_tokenSet_7.member(LA(1)))) {
				mCNTRL(false);
			}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mSKIP_DIRECTIVE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SKIP_DIRECTIVE;
		int _saveIndex;
		
		{
		_loop98:
		do {
			if ((_tokenSet_8.member(LA(1)))) {
				{
				match(_tokenSet_8);
				}
			}
			else {
				break _loop98;
			}
			
		} while (true);
		}
		{
		switch ( LA(1)) {
		case '\n':
		{
			match('\n');
			break;
		}
		case '\r':
		{
			match('\r');
			{
			if ((LA(1)=='\n')) {
				match('\n');
			}
			else {
			}
			
			}
			break;
		}
		default:
			{
			}
		}
		}
		if ( inputState.guessing==0 ) {
			newline();
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mMACRO_EXPAND(boolean _createToken,
		String key
	) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = MACRO_EXPAND;
		int _saveIndex;
		
		if (((LA(1)=='('))&&(stPP.hasParams(key))) {
			mMACRO_EXPAND_PARMS(false,key);
		}
		else {
			if ( inputState.guessing==0 ) {
				
								if (stPP.okToDefine(key)) {
									stPP.expandMacro(key);
								}
							
			}
		}
		
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mMACRO_EXPAND_PARMS(boolean _createToken,
		String key
	) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = MACRO_EXPAND_PARMS;
		int _saveIndex;
		
			LinkedList<String> parms = null;
		
		
		match('(');
		{
		if ((_tokenSet_2.member(LA(1))) && ((LA(2) >= '\u0000' && LA(2) <= '\u00ff'))) {
			_saveIndex=text.length();
			mWS(false);
			text.setLength(_saveIndex);
		}
		else if (((LA(1) >= '\u0000' && LA(1) <= '\u00ff')) && (true)) {
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		
		}
		parms=mMACRO_EXPAND_PARMS2(false);
		{
		if ((_tokenSet_2.member(LA(1)))) {
			_saveIndex=text.length();
			mWS(false);
			text.setLength(_saveIndex);
		}
		else if ((LA(1)==')')) {
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		
		}
		match(')');
		{
		if ((_tokenSet_2.member(LA(1)))) {
			_saveIndex=text.length();
			mWS(false);
			text.setLength(_saveIndex);
		}
		else {
		}
		
		}
		if ( inputState.guessing==0 ) {
			
						if (stPP.okToDefine(key)) {
							stPP.expandMacro(key, parms);
						}
					
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final LinkedList<String>  mMACRO_EXPAND_PARMS2(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		LinkedList<String> parms;
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = MACRO_EXPAND_PARMS2;
		int _saveIndex;
		Token s1=null;
		
			parms = new LinkedList<String>();
			String s = null;
			StringBuffer sbuf = new StringBuffer();
		
		
		{
		_loop19:
		do {
			if ((_tokenSet_9.member(LA(1))) && ((LA(2) >= '\u0000' && LA(2) <= '\u00ff'))) {
				mPAREN_CLOSURE2(true);
				s1=_returnToken;
				{
				if ((_tokenSet_2.member(LA(1))) && ((LA(2) >= '\u0000' && LA(2) <= '\u00ff'))) {
					_saveIndex=text.length();
					mWS(false);
					text.setLength(_saveIndex);
				}
				else if (((LA(1) >= '\u0000' && LA(1) <= '\u00ff')) && (true)) {
				}
				else {
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
				}
				
				}
				if ( inputState.guessing==0 ) {
						s = s1.getText();
									if (',' == s.charAt(0)) {
										if (0 < sbuf.length()) {
											parms.add(sbuf.toString());
										}
										sbuf = new StringBuffer();
									} else {
										sbuf.append(s);
									}
								
				}
			}
			else {
				break _loop19;
			}
			
		} while (true);
		}
		if ( inputState.guessing==0 ) {
				if (0 < sbuf.length()) {
							parms.add(sbuf.toString());
						}
					
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
		return parms;
	}
	
	protected final void mPAREN_CLOSURE2(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = PAREN_CLOSURE2;
		int _saveIndex;
		
		switch ( LA(1)) {
		case '{':
		{
			mCURLY_CLOSURE(false);
			break;
		}
		case '"':
		{
			mSTRING(false);
			break;
		}
		case '(':
		{
			mPAREN_CLOSURE(false);
			break;
		}
		default:
			if ((_tokenSet_10.member(LA(1)))) {
				matchNot(')');
			}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mPAREN_CLOSURE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = PAREN_CLOSURE;
		int _saveIndex;
		
		match('(');
		{
		if ((_tokenSet_2.member(LA(1))) && ((LA(2) >= '\u0000' && LA(2) <= '\u00ff'))) {
			_saveIndex=text.length();
			mWS(false);
			text.setLength(_saveIndex);
		}
		else if (((LA(1) >= '\u0000' && LA(1) <= '\u00ff')) && ((LA(2) >= '\u0000' && LA(2) <= '\u00ff'))) {
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		
		}
		{
		_loop24:
		do {
			if ((_tokenSet_9.member(LA(1)))) {
				mPAREN_CLOSURE2(false);
				{
				if ((_tokenSet_2.member(LA(1))) && ((LA(2) >= '\u0000' && LA(2) <= '\u00ff'))) {
					_saveIndex=text.length();
					mWS(false);
					text.setLength(_saveIndex);
				}
				else if (((LA(1) >= '\u0000' && LA(1) <= '\u00ff')) && ((LA(2) >= '\u0000' && LA(2) <= '\u00ff'))) {
				}
				else {
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
				}
				
				}
			}
			else {
				break _loop24;
			}
			
		} while (true);
		}
		match(')');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mCURLY_CLOSURE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = CURLY_CLOSURE;
		int _saveIndex;
		
		match('{');
		{
		if ((_tokenSet_2.member(LA(1))) && ((LA(2) >= '\u0000' && LA(2) <= '\u00ff'))) {
			_saveIndex=text.length();
			mWS(false);
			text.setLength(_saveIndex);
		}
		else if (((LA(1) >= '\u0000' && LA(1) <= '\u00ff')) && ((LA(2) >= '\u0000' && LA(2) <= '\u00ff'))) {
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		
		}
		{
		_loop36:
		do {
			if ((_tokenSet_11.member(LA(1)))) {
				mCURLY_CLOSURE2(false);
				{
				if ((_tokenSet_2.member(LA(1))) && ((LA(2) >= '\u0000' && LA(2) <= '\u00ff'))) {
					_saveIndex=text.length();
					mWS(false);
					text.setLength(_saveIndex);
				}
				else if (((LA(1) >= '\u0000' && LA(1) <= '\u00ff')) && ((LA(2) >= '\u0000' && LA(2) <= '\u00ff'))) {
				}
				else {
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
				}
				
				}
			}
			else {
				break _loop36;
			}
			
		} while (true);
		}
		match('}');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mSTRING(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = STRING;
		int _saveIndex;
		
		match('"');
		{
		_loop51:
		do {
			if ((_tokenSet_12.member(LA(1)))) {
				{
				match(_tokenSet_12);
				}
			}
			else {
				break _loop51;
			}
			
		} while (true);
		}
		match('"');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mCURLY_CLOSURE2(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = CURLY_CLOSURE2;
		int _saveIndex;
		
		switch ( LA(1)) {
		case '(':
		{
			mPAREN_CLOSURE(false);
			break;
		}
		case '"':
		{
			mSTRING(false);
			break;
		}
		case '{':
		{
			mCURLY_CLOSURE(false);
			break;
		}
		default:
			if ((_tokenSet_13.member(LA(1)))) {
				matchNot('}');
			}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mUNSIZED_NUMBER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = UNSIZED_NUMBER;
		int _saveIndex;
		
		mDIGIT(false);
		{
		_loop54:
		do {
			if ((LA(1)=='_') && (_tokenSet_14.member(LA(2)))) {
				match('_');
			}
			else if (((LA(1) >= '0' && LA(1) <= '9'))) {
				mDIGIT(false);
			}
			else {
				break _loop54;
			}
			
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mDIGIT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DIGIT;
		int _saveIndex;
		
		{
		matchRange('0','9');
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mTIMESCALE_VALUE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = TIMESCALE_VALUE;
		int _saveIndex;
		
		mUNSIZED_NUMBER(false);
		{
		_loop66:
		do {
			if ((_tokenSet_2.member(LA(1)))) {
				mWS(false);
			}
			else {
				break _loop66;
			}
			
		} while (true);
		}
		mIDENT(false);
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mTIC_DEFINE_PARMS(boolean _createToken,
		String id
	) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = TIC_DEFINE_PARMS;
		int _saveIndex;
		Token id2=null;
		Token id3=null;
		Token def=null;
		
			LinkedList<String> parms = new LinkedList<String>();
		
		
		match('(');
		{
		_loop75:
		do {
			if ((_tokenSet_2.member(LA(1)))) {
				mWS(false);
			}
			else {
				break _loop75;
			}
			
		} while (true);
		}
		{
		switch ( LA(1)) {
		case 'A':  case 'B':  case 'C':  case 'D':
		case 'E':  case 'F':  case 'G':  case 'H':
		case 'I':  case 'J':  case 'K':  case 'L':
		case 'M':  case 'N':  case 'O':  case 'P':
		case 'Q':  case 'R':  case 'S':  case 'T':
		case 'U':  case 'V':  case 'W':  case 'X':
		case 'Y':  case 'Z':  case '_':  case 'a':
		case 'b':  case 'c':  case 'd':  case 'e':
		case 'f':  case 'g':  case 'h':  case 'i':
		case 'j':  case 'k':  case 'l':  case 'm':
		case 'n':  case 'o':  case 'p':  case 'q':
		case 'r':  case 's':  case 't':  case 'u':
		case 'v':  case 'w':  case 'x':  case 'y':
		case 'z':
		{
			mIDENT(true);
			id2=_returnToken;
			if ( inputState.guessing==0 ) {
				parms.add(id2.getText());
			}
			{
			_loop80:
			do {
				if ((_tokenSet_15.member(LA(1)))) {
					{
					_loop79:
					do {
						if ((_tokenSet_2.member(LA(1)))) {
							mWS(false);
						}
						else {
							break _loop79;
						}
						
					} while (true);
					}
					match(',');
					mIDENT(true);
					id3=_returnToken;
					if ( inputState.guessing==0 ) {
						parms.add(id3.getText());
					}
				}
				else {
					break _loop80;
				}
				
			} while (true);
			}
			break;
		}
		case ')':
		{
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		match(')');
		mDEFINE(true);
		def=_returnToken;
		if ( inputState.guessing==0 ) {
			
						stPP.addDefine(id, parms, def.getText());
					
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mDEFINE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DEFINE;
		int _saveIndex;
		
		{
		_loop90:
		do {
			boolean synPredMatched88 = false;
			if (((LA(1)=='"') && (_tokenSet_1.member(LA(2))))) {
				int _m88 = mark();
				synPredMatched88 = true;
				inputState.guessing++;
				try {
					{
					match('"');
					}
				}
				catch (RecognitionException pe) {
					synPredMatched88 = false;
				}
				rewind(_m88);
inputState.guessing--;
			}
			if ( synPredMatched88 ) {
				mSTRING(false);
			}
			else if ((_tokenSet_16.member(LA(1))) && ((LA(2) >= '\u0000' && LA(2) <= '\u00ff'))) {
				{
				match(_tokenSet_16);
				}
			}
			else if ((LA(1)=='\\')) {
				match('\\');
				{
				switch ( LA(1)) {
				case '\r':
				{
					match('\r');
					break;
				}
				case '\n':
				{
					break;
				}
				default:
				{
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
				}
				}
				}
				match('\n');
				if ( inputState.guessing==0 ) {
					newline();
				}
			}
			else {
				break _loop90;
			}
			
		} while (true);
		}
		{
		switch ( LA(1)) {
		case '\r':
		{
			match('\r');
			break;
		}
		case '\n':
		{
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		match('\n');
		if ( inputState.guessing==0 ) {
			newline();
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mCNTRL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = CNTRL;
		int _saveIndex;
		
		switch ( LA(1)) {
		case '\u0000':  case '\u0001':  case '\u0002':  case '\u0003':
		case '\u0004':  case '\u0005':  case '\u0006':  case '\u0007':
		case '\u0008':
		{
			matchRange('\u0000','\u0008');
			break;
		}
		case '\u000b':  case '\u000c':
		{
			matchRange('\u000B','\u000C');
			break;
		}
		case '\u000e':  case '\u000f':  case '\u0010':  case '\u0011':
		case '\u0012':  case '\u0013':  case '\u0014':  case '\u0015':
		case '\u0016':  case '\u0017':  case '\u0018':  case '\u0019':
		case '\u001a':  case '\u001b':  case '\u001c':  case '\u001d':
		case '\u001e':  case '\u001f':
		{
			matchRange('\u000E','\u001F');
			break;
		}
		default:
			if (((LA(1) >= '\u007f' && LA(1) <= '\u00ff'))) {
				matchRange('\u007F','\u00FF');
			}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mSL_COMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SL_COMMENT;
		int _saveIndex;
		
		match("//");
		{
		_loop104:
		do {
			if ((_tokenSet_8.member(LA(1)))) {
				{
				match(_tokenSet_8);
				}
			}
			else {
				break _loop104;
			}
			
		} while (true);
		}
		{
		switch ( LA(1)) {
		case '\n':
		{
			match('\n');
			break;
		}
		case '\r':
		{
			match('\r');
			{
			if ((LA(1)=='\n')) {
				match('\n');
			}
			else {
			}
			
			}
			break;
		}
		default:
			{
			}
		}
		}
		if ( inputState.guessing==0 ) {
			newline();
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mML_COMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ML_COMMENT;
		int _saveIndex;
		
		match("/*");
		{
		_loop110:
		do {
			if (((LA(1)=='*') && ((LA(2) >= '\u0000' && LA(2) <= '\u00ff')))&&( LA(2)!='/' )) {
				match('*');
			}
			else if ((LA(1)=='\r') && (LA(2)=='\n')) {
				match('\r');
				match('\n');
				if ( inputState.guessing==0 ) {
					newline();
				}
			}
			else if ((LA(1)=='\r') && ((LA(2) >= '\u0000' && LA(2) <= '\u00ff'))) {
				match('\r');
				if ( inputState.guessing==0 ) {
					newline();
				}
			}
			else if ((LA(1)=='\n')) {
				match('\n');
				if ( inputState.guessing==0 ) {
					newline();
				}
			}
			else if ((_tokenSet_17.member(LA(1)))) {
				{
				match(_tokenSet_17);
				}
			}
			else {
				break _loop110;
			}
			
		} while (true);
		}
		match("*/");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mCOMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = COMMENT;
		int _saveIndex;
		Token id=null;
		Token id2=null;
		Token tok = null;
		
		{
		if ((LA(1)=='/') && (LA(2)=='/')) {
			mSL_COMMENT(true);
			id=_returnToken;
			if ( inputState.guessing==0 ) {
				tok=id;
			}
		}
		else if ((LA(1)=='/') && (LA(2)=='*')) {
			mML_COMMENT(true);
			id2=_returnToken;
			if ( inputState.guessing==0 ) {
				tok=id2;
			}
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		
		}
		if ( inputState.guessing==0 ) {
			
						if (false == Parser.stShowComments) {
							String s = tok.getText();
							String t = scrub2nl(s);
							text.setLength(_begin); text.append(t);
						}
					
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mDUMMY(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DUMMY;
		int _saveIndex;
		
		matchNot(EOF_CHAR);
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 0L, 576460745995190270L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = new long[8];
		data[0]=-1025L;
		for (int i = 1; i<=3; i++) { data[i]=-1L; }
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = new long[8];
		data[0]=8589934591L;
		data[1]=-9223372036854775808L;
		for (int i = 2; i<=3; i++) { data[i]=-1L; }
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = new long[8];
		data[0]=8589934591L;
		data[1]=-8646911290859585538L;
		for (int i = 2; i<=3; i++) { data[i]=-1L; }
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = new long[8];
		data[0]=25769803775L;
		data[1]=-9223372036854775808L;
		for (int i = 2; i<=3; i++) { data[i]=-1L; }
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = new long[8];
		data[0]=287948909764935679L;
		data[1]=-9223372036854775808L;
		for (int i = 2; i<=3; i++) { data[i]=-1L; }
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	private static final long[] mk_tokenSet_6() {
		long[] data = new long[8];
		data[0]=2207613190143L;
		data[1]=-8646911290859585538L;
		for (int i = 2; i<=3; i++) { data[i]=-1L; }
		return data;
	}
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	private static final long[] mk_tokenSet_7() {
		long[] data = new long[8];
		data[0]=4294957567L;
		data[1]=-9223372036854775808L;
		for (int i = 2; i<=3; i++) { data[i]=-1L; }
		return data;
	}
	public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
	private static final long[] mk_tokenSet_8() {
		long[] data = new long[8];
		data[0]=-9217L;
		for (int i = 1; i<=3; i++) { data[i]=-1L; }
		return data;
	}
	public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
	private static final long[] mk_tokenSet_9() {
		long[] data = new long[8];
		data[0]=-2199023255553L;
		for (int i = 1; i<=3; i++) { data[i]=-1L; }
		return data;
	}
	public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
	private static final long[] mk_tokenSet_10() {
		long[] data = new long[8];
		data[0]=-3315714752513L;
		data[1]=-576460752303423489L;
		for (int i = 2; i<=3; i++) { data[i]=-1L; }
		return data;
	}
	public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
	private static final long[] mk_tokenSet_11() {
		long[] data = new long[8];
		data[0]=-1L;
		data[1]=-2305843009213693953L;
		for (int i = 2; i<=3; i++) { data[i]=-1L; }
		return data;
	}
	public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
	private static final long[] mk_tokenSet_12() {
		long[] data = new long[8];
		data[0]=-17179870209L;
		for (int i = 1; i<=3; i++) { data[i]=-1L; }
		return data;
	}
	public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
	private static final long[] mk_tokenSet_13() {
		long[] data = new long[8];
		data[0]=-1116691496961L;
		data[1]=-2882303761517117441L;
		for (int i = 2; i<=3; i++) { data[i]=-1L; }
		return data;
	}
	public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
	private static final long[] mk_tokenSet_14() {
		long[] data = new long[8];
		data[0]=287948909764935679L;
		data[1]=-8646911290859585538L;
		for (int i = 2; i<=3; i++) { data[i]=-1L; }
		return data;
	}
	public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
	private static final long[] mk_tokenSet_15() {
		long[] data = new long[8];
		data[0]=17600775979007L;
		data[1]=-9223372036854775808L;
		for (int i = 2; i<=3; i++) { data[i]=-1L; }
		return data;
	}
	public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
	private static final long[] mk_tokenSet_16() {
		long[] data = new long[8];
		data[0]=-9217L;
		data[1]=-268435457L;
		for (int i = 2; i<=3; i++) { data[i]=-1L; }
		return data;
	}
	public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
	private static final long[] mk_tokenSet_17() {
		long[] data = new long[8];
		data[0]=-4398046520321L;
		for (int i = 1; i<=3; i++) { data[i]=-1L; }
		return data;
	}
	public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
	
	}
