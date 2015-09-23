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
import  v2k.message.MessageMgr;
import	antlr.ANTLRException;
import  antlr.RecognitionException;

/**
 *
 * @author karl
 */
public class Message {
	public static void message(ANTLRException ex) {
		//try to extract prefix <CODE/> or <svr:CODE/>
		String code = "ERR-1";
		String m = ex.toString().trim();
		char svr = 'E';
		if ('<' == m.charAt(0)) {
			int n = m.indexOf("/>");
			if (0 < n) {
				code = m.substring(1, n);
				if (':' == code.charAt(1)) {
					svr = code.charAt(0);
					code = code.substring(2);
				}
				m = m.substring(n+2);
			}
		}
		message(svr, code, m);
	}
	public static void message(RecognitionException ex) {
		String code = "ERR-1";
		String m = ex.toString().trim();
        Location loc = new Location(Parser.getTheOne().getFilename(), ex.getLine());
		message(loc, 'E', code, m);
	}
    public static void message(char severity, String code, Object ... args) {
   		String fn = Parser.getTheOne().getFilename();
       	Integer ln = Parser.getTheOne().getLine();
        Object nargs[] = new Object[args.length+2];
        nargs[0] = fn; nargs[1] = ln;
        System.arraycopy(args, 0, nargs, 2, args.length);
        MessageMgr.message(severity, code, nargs);
    }
    public static void message(Location loc, char severity, String code, 
                               Object ... args) {
   		String fn = loc.getFilename();
       	Integer ln = loc.getLineno();
        Object nargs[] = new Object[args.length+2];
        nargs[0] = fn; nargs[1] = ln;
        System.arraycopy(args, 0, nargs, 2, args.length);
        MessageMgr.message(severity, code, nargs);
    }
	/**Display message iff. doMsg==true.*/
	public static void message(boolean doMsg, char svr, String code, String msg) {
		if (doMsg) {
			String fn = Parser.getTheOne().getFilename();
			Integer ln = Parser.getTheOne().getLine();
			MessageMgr.message(svr, code, fn, ln, msg);
		}
	}
	/**Display message iff. doMsg==true.*/
	public static void message(boolean doMsg, Location loc, char svr, 
                               String code, Object ... args) {
		if (doMsg) {
			message(loc, svr, code, args);
		}
	}

	public static void message(char svr, String code, String msg) {
		String fn = Parser.getTheOne().getFilename();
		Integer ln = Parser.getTheOne().getLine();
		MessageMgr.message(svr, code, fn, ln, msg);
	}
	
	public static void syntaxError(String msg) {
		message('E', stStax, msg);
	}

	public static void syntaxWarning(String msg) {
		message('W', stStax, msg);
	}

	private final static String stStax = "PARSE-1";
	

}
