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


package v2k.parser.tree.symbol;
import  java.util.*;

/**
 *
 * @author karl
 */
public class Table {
    public static Table getTheOne() {
        return m_theOne;
    }
    /**
     * Add symbol to table.
     * Asserts that symbol does not already exist; so expects find() to
     * be done beforehand.
     * @param sym symbol to add.
     */
    public void add(Symbol sym) {
        assert null != sym.getScope();  //we only add new ones.
        final String key = sym.getName();
        Entry entry = null;
        if (false == m_symTab.containsKey(key)) {
            entry = new Entry();
            entry.add(sym);
            m_symTab.put(key, entry);
        } else {
            assert null == find(sym.getName(), sym.getScope());
            entry = m_symTab.get(key);
            entry.add(sym);
        }
    }
    /**
     * Find symbol starting with current scope. 
     * @param key key to search for.
     * @param scope enclosing scope to start search.
     * @return defined Symbol withing scope or null if not found.
     */
    public Symbol find(String key, Scope scope) {
        Entry entries = m_symTab.get(key);
        if (null == entries) {
            return null;
        }
        int scopeLvl = -1;
        /**
         * Look in symbol table for match to current scope ID.
         * If not found, then repeat for scope parent, up scope tree.
         */
        for (Scope currScope : scope) {
            scopeLvl = currScope.getScopeLvl();
            for (Symbol ele : entries) {
                if (scopeLvl == ele.getScope().getScopeLvl()) {
                    return ele;
                }
            }
        }
        return null;
    }
    public Symbol find(String key) {
        return find(key, ScopeManager.getTheOne().peek());
    }
    private Map<String,Entry>    m_symTab = new Hashtable<String,Entry>();
    private static Table m_theOne = new Table();
    
    /**
     * Wrap around entry.
     */
    private static class Entry extends LinkedList<Symbol> {
        //nada
    }
    /**
     * Return map of symbols by scope level.
     */
    public Map<Integer, TreeMap<String,Symbol>> getSymbolsByScope() {
        Map<Integer, TreeMap<String,Symbol>> rval =
                new Hashtable<Integer, TreeMap<String,Symbol>>();
        for (String key : m_symTab.keySet()) {
            Entry entry = m_symTab.get(key);
            for (Symbol symbol : entry) {
                Integer level = symbol.getScope().getScopeLvl();
                if (false == rval.containsKey(level)) {
                    rval.put(level, new TreeMap<String,Symbol>());
                }
                rval.get(level).put(key, symbol);
            }
        }
        return rval;
    }
}
