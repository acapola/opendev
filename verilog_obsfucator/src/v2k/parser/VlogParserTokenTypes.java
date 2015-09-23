// $ANTLR 2.7.7 (20080509): "Vlog.g" -> "VlogLexer.java"$

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

public class VlogParserTokenTypes {
    public static final int NONE = -1;//seb

    public static final int EOF = 1;
	public static final int NULL_TREE_LOOKAHEAD = 3;
	public static final int SEMI = 4;
	public static final int LITERAL_endmodule = 5;
	public static final int LITERAL_module = 6;
	public static final int LITERAL_macromodule = 7;
	public static final int POUND = 8;
	public static final int LPAREN = 9;
	public static final int RPAREN = 10;
	public static final int COMMA = 11;
	public static final int DOT = 12;
	public static final int LCURLY = 13;
	public static final int RCURLY = 14;
	public static final int LBRACK = 15;
	public static final int RBRACK = 16;
	public static final int LITERAL_defparam = 17;
	public static final int LITERAL_localparam = 18;
	public static final int LITERAL_signed = 19;
	public static final int LITERAL_parameter = 20;
	public static final int LITERAL_specparam = 21;
	public static final int LITERAL_integer = 22;
	public static final int LITERAL_real = 23;
	public static final int LITERAL_realtime = 24;
	public static final int LITERAL_time = 25;
	public static final int LITERAL_inout = 26;
	public static final int LITERAL_input = 27;
	public static final int LITERAL_output = 28;
	public static final int LITERAL_reg = 29;
	public static final int LITERAL_event = 30;
	public static final int LITERAL_vectored = 31;
	public static final int LITERAL_scalared = 32;
	public static final int LITERAL_tri = 35;
	public static final int LITERAL_triand = 36;
	public static final int LITERAL_trior = 37;
	public static final int LITERAL_uwire = 40;
	public static final int LITERAL_wire = 41;
	public static final int LITERAL_wand = 42;
	public static final int LITERAL_wor = 43;
	public static final int ASSIGN = 44;
	public static final int COLON = 45;
	public static final int LITERAL_function = 46;
	public static final int LITERAL_automatic = 47;
	public static final int LITERAL_endfunction = 48;
	public static final int LITERAL_task = 49;
	public static final int LITERAL_endtask = 50;
	public static final int LITERAL_generate = 51;
	public static final int LITERAL_endgenerate = 52;
	public static final int LITERAL_genvar = 53;
	public static final int LITERAL_for = 54;
	public static final int QMARK = 55;
	public static final int LITERAL_if = 56;
	public static final int LITERAL_else = 57;
	public static final int LITERAL_case = 58;
	public static final int LITERAL_endcase = 59;
	public static final int LITERAL_default = 60;
	public static final int LITERAL_begin = 61;
	public static final int LITERAL_end = 62;
	public static final int LITERAL_assign = 63;
	public static final int LITERAL_initial = 64;
	public static final int LITERAL_always = 65;
	public static final int LE = 66;
	public static final int LITERAL_deassign = 67;
	public static final int LITERAL_force = 68;
	public static final int LITERAL_release = 69;
	public static final int LITERAL_fork = 70;
	public static final int LITERAL_join = 71;
	public static final int LITERAL_repeat = 72;
	public static final int LITERAL_disable = 73;
	public static final int AT = 74;
	public static final int STAR = 75;
	public static final int TRIGGER = 76;
	public static final int LITERAL_posedge = 77;
	public static final int LITERAL_negedge = 78;
	public static final int LITERAL_or = 79;
	public static final int LITERAL_wait = 80;
	public static final int LITERAL_casex = 81;
	public static final int LITERAL_casez = 82;
	public static final int LITERAL_forever = 83;
	public static final int LITERAL_while = 84;
	public static final int PLUS_COLON = 85;
	public static final int MINUS_COLON = 86;
	public static final int NUMBER = 87;
	public static final int STRING = 88;
	public static final int IDENT = 89;
	public static final int ESCAPED_IDENT = 90;
	public static final int SYSTEM_TASK_NAME = 91;
	public static final int LITERAL_and = 92;
	public static final int LITERAL_nand = 93;
	public static final int LITERAL_nor = 94;
	public static final int LITERAL_xor = 95;
	public static final int LITERAL_xnor = 96;
	public static final int MINUS = 97;
	public static final int PLUS = 98;
	public static final int LNOT = 99;
	public static final int BNOT = 100;
	public static final int BAND = 101;
	public static final int RNAND = 102;
	public static final int BOR = 103;
	public static final int RNOR = 104;
	public static final int BXOR = 105;
	public static final int RXNOR = 106;
	public static final int DIV = 107;
	public static final int MOD = 108;
	public static final int EQUAL = 109;
	public static final int NOT_EQ = 110;
	public static final int NOT_EQ_CASE = 111;
	public static final int EQ_CASE = 112;
	public static final int LAND = 113;
	public static final int LOR = 114;
	public static final int LT_ = 115;
	public static final int GT = 116;
	public static final int GE = 117;
	public static final int SR = 118;
	public static final int SL = 119;
	public static final int SR3 = 120;
	public static final int SL3 = 121;
	public static final int STAR2 = 122;
	public static final int PPATH = 123;
	public static final int FPATH = 124;
	public static final int TIC_DIRECTIVE = 125;
	public static final int TIC_LINE = 126;
	public static final int SIZED_NUMBER = 127;
	public static final int SIZE = 128;
	public static final int BASE = 129;
	public static final int SIZED_DIGIT = 130;
	public static final int UNSIZED_NUMBER = 131;
	public static final int DIGIT = 132;
	public static final int HEXDIGIT = 133;
	public static final int EXPONENT = 134;
	public static final int WS2 = 135;
	public static final int WS = 136;
	public static final int CNTRL = 137;
	public static final int SL_COMMENT = 138;
	public static final int ML_COMMENT = 139;

    //public // "supply0" = 33
    //public // "supply1" = 34
    //public // "tri0" = 38
    //public // "tri1" = 39

    public static String toString(int tokenType){
        switch(tokenType){
            case NONE: return "";
            
            case SEMI: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_endmodule: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_module: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_macromodule: throw new RuntimeException("tokenType="+tokenType);
            case POUND: throw new RuntimeException("tokenType="+tokenType);
            case LPAREN: throw new RuntimeException("tokenType="+tokenType);
            case RPAREN: throw new RuntimeException("tokenType="+tokenType);
            case COMMA: return ",";
            case DOT: return ".";
            case LCURLY: return "{";
            case RCURLY: return "}";
            case LBRACK: return "[";
            case RBRACK: return "]";
            case LITERAL_defparam: return "defparam";
            case LITERAL_localparam: return "localparam";
            case LITERAL_signed: return "signed";
            case LITERAL_parameter: return "parameter";
            case LITERAL_specparam: return "specparam";
            case LITERAL_integer: return "integer";
            case LITERAL_real: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_realtime: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_time: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_inout: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_input: return "input";
            case LITERAL_output: return "output";
            case LITERAL_reg: return "reg";
            case LITERAL_event: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_vectored: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_scalared: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_tri: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_triand: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_trior: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_uwire: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_wire: return "";
            case LITERAL_wand: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_wor: throw new RuntimeException("tokenType="+tokenType);
            case ASSIGN: return "assign";
            case COLON: return ":";
            case LITERAL_function: return "function";
            case LITERAL_automatic: return "automatic";
            case LITERAL_endfunction: return "endfunction";
            case LITERAL_task: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_endtask: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_generate: return "generate";
            case LITERAL_endgenerate: return "endgenerate";
            case LITERAL_genvar: return "genvar";
            case LITERAL_for: return "for";
            case QMARK: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_if: return "if";
            case LITERAL_else: return "else";
            case LITERAL_case: return "case";
            case LITERAL_endcase: return "endcase";
            case LITERAL_default: return "default";
            case LITERAL_begin: return "begin";
            case LITERAL_end: return "end";
            case LITERAL_assign: return "assign";
            case LITERAL_initial: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_always: return "always";
            case LE: return "<=";
            case LITERAL_deassign: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_force: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_release: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_fork: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_join: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_repeat: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_disable: throw new RuntimeException("tokenType="+tokenType);
            case AT: return "@";
            case STAR: return "*";
            case TRIGGER: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_posedge: return "posedge";
            case LITERAL_negedge: return "negedge";
            case LITERAL_or: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_wait: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_casex: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_casez: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_forever: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_while: throw new RuntimeException("tokenType="+tokenType);
            case PLUS_COLON: return "+:";
            case MINUS_COLON: return "-:";
            case NUMBER: throw new RuntimeException("tokenType="+tokenType);
            case STRING: throw new RuntimeException("tokenType="+tokenType);
            case IDENT: return "";
            case ESCAPED_IDENT: throw new RuntimeException("tokenType="+tokenType);
            case SYSTEM_TASK_NAME: throw new RuntimeException("tokenType="+tokenType);
            case LITERAL_and: return "and";
            case LITERAL_nand: return "nand";
            case LITERAL_nor: return "nor";
            case LITERAL_xor: return "xor";
            case LITERAL_xnor: return "xnor";
            case MINUS: return "-";
            case PLUS: return "+";
            case LNOT: return "!";
            case BNOT: return "~";
            case BAND: return "&";
            case RNAND: throw new RuntimeException("tokenType="+tokenType);
            case BOR: return "|";
            case RNOR: throw new RuntimeException("tokenType="+tokenType);
            case BXOR: return "^";
            case RXNOR: throw new RuntimeException("tokenType="+tokenType);
            case DIV: return "/";
            case MOD: return "%";
            case EQUAL: return "==";
            case NOT_EQ: return "!=";
            case NOT_EQ_CASE: throw new RuntimeException("tokenType="+tokenType);
            case EQ_CASE: throw new RuntimeException("tokenType="+tokenType);
            case LAND: return "&&";
            case LOR: return "||";
            case LT_: return "<";
            case GT: return ">";
            case GE: return ">=";
            case SR: return ">>";
            case SL: return "<<";
            case SR3: return ">>>";
            case SL3: return "<<<";
            case STAR2: return "**";
            case PPATH: throw new RuntimeException("tokenType="+tokenType);
            case FPATH: throw new RuntimeException("tokenType="+tokenType);
            case TIC_DIRECTIVE: throw new RuntimeException("tokenType="+tokenType);
            case TIC_LINE: throw new RuntimeException("tokenType="+tokenType);
            case SIZED_NUMBER: throw new RuntimeException("tokenType="+tokenType);
            case SIZE: throw new RuntimeException("tokenType="+tokenType);
            case BASE: throw new RuntimeException("tokenType="+tokenType);
            case SIZED_DIGIT: throw new RuntimeException("tokenType="+tokenType);
            case UNSIZED_NUMBER: throw new RuntimeException("tokenType="+tokenType);
            case DIGIT: throw new RuntimeException("tokenType="+tokenType);
            case HEXDIGIT: throw new RuntimeException("tokenType="+tokenType);
            case EXPONENT: throw new RuntimeException("tokenType="+tokenType);
            case WS2: throw new RuntimeException("tokenType="+tokenType);
            case WS: throw new RuntimeException("tokenType="+tokenType);
            case CNTRL: throw new RuntimeException("tokenType="+tokenType);
            case SL_COMMENT: return "//";
            case ML_COMMENT: return "/**/";

            default: throw new RuntimeException("unsupported tokenType: "+tokenType);
        }
    }
}
