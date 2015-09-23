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
import  java.util.*;
import  java.util.regex.*;

public class MacroVal 
{
    public MacroVal(List<String> parms, String val) {
        m_nParms = parms.size();
        m_val = val;
        int i = 0;
        for (String p : parms) {
            replace(p, i++);
        }
        setWhere();
    }
    public MacroVal(String val) {
        m_val = val;
        setWhere();
    }
    public MacroVal(String val, Location loc) {
        m_val = val;
        if (null != loc) {
			m_decl = loc;
		} else {
			setWhere();
		}
    }
    private void setWhere() {
       //backup one line to acct for matched newline
       m_decl = new Location(Preproc.getTheOne().getLexerLoc(), -1);
    }
    public String getVal() {
        return m_val;
    }
	public boolean hasParams() {
		return (0 < m_nParms);
	}
    public String getVal(List<String> parms) {
        assert(m_nParms == parms.size());
        String rval = m_val;
        int i = 0;
        for (String p : parms)
        {
            rval = replace(rval, p, i++);
        }
        return rval;
    }
   	/**Get definition location.*/
	public Location getWhereDefn() {
		return m_decl;
	}
    private void replace(String pname, int ix) {
        String s = m_val;
        String repl = stDelim[0] + ix + stDelim[1];
        String regex = stPatt[0]+pname+stPatt[1];
        Pattern p = Pattern.compile(regex);
        for (Matcher m = p.matcher(s); m.find(); m = p.matcher(s))
        {
            int start = m.start();
            int end = m.end();
            s = s.substring(0, start+1) + repl + s.substring(end-1);
        }
        m_val = s;
    }
    private static String replace(String val, String pname, int ix) {
        String repl = stDelim[0] + ix + stDelim[1];
        return val.replace(repl, pname);
    }
    private int     m_nParms = 0;
    private String  m_val;
    /**Location of macro definition.*/
	private Location	m_decl;
  
    // {[0],[1]}={prefix,suffix}
    private static final String stPatt[] = 
            new String[]{"(^|[^a-zA-Z0-9_])","($|[^a-zA-Z0-9_])"};
    
    // {[0],[1]}={prefix,suffix}
    private static final String stDelim[] =
            new String[]{"</%","%/>"};
}
