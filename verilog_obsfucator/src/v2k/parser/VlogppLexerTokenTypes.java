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

public interface VlogppLexerTokenTypes {
	int EOF = 1;
	int NULL_TREE_LOOKAHEAD = 3;
	int TIC_DIRECTIVE = 4;
	int MACRO_EXPAND = 5;
	int MACRO_EXPAND_PARMS = 6;
	int MACRO_EXPAND_PARMS2 = 7;
	int PAREN_CLOSURE = 8;
	int PAREN_CLOSURE2 = 9;
	int CURLY_CLOSURE = 10;
	int CURLY_CLOSURE2 = 11;
	int IDENT = 12;
	int STRING = 13;
	int UNSIZED_NUMBER = 14;
	int DIGIT = 15;
	int TIC_TIMESCALE = 16;
	int TIMESCALE_VALUE = 17;
	int TIC_DEFINE = 18;
	int TIC_DEFINE_PARMS = 19;
	int TIC_INCLUDE = 20;
	int DEFINE = 21;
	int WS = 22;
	int CNTRL = 23;
	int SKIP_DIRECTIVE = 24;
	int SL_COMMENT = 25;
	int ML_COMMENT = 26;
	int COMMENT = 27;
	int DUMMY = 28;
}
