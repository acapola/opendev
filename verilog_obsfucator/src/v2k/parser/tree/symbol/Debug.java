/*
*************************************************************************
*************************************************************************
**                                                                     **
**  V2KPARSE                                                           **
**  Copyright (C) 2008    Karl W. Pfalzer                              **
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

package v2k.parser.tree.symbol;
import  java.io.*;
import  java.util.*;

/**
 *
 * @author karl
 */
public class Debug {
    private static Map<Integer, TreeMap<String,Symbol>> m_stab;
    public static void dumpSymbols(PrintStream os) {
        m_stab = Table.getTheOne().getSymbolsByScope();
        Scope scope = ScopeManager.getTheOne().peek();
        dump(scope, 0, os);
    }
    private static void dump(Scope scope, int level, PrintStream os) {
       int scopeLvl = scope.getScopeLvl();
       Map<String,Symbol> syms = m_stab.get(scopeLvl);
       if (null != syms) {
           os.print(level+": "+scope.getId().getName()+": ("+scopeLvl+")");
           for (String key : syms.keySet()) {
               os.print(" "+key);
           }
           os.println();
       }
       if (null != scope.getChildren()) {
           for (Scope child : scope.getChildren()) {
               dump(child, level+1, os);
           }
       }
    }
}
