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
import static v2k.parser.Message.message;
import  v2k.parser.tree.*;

/**
 *
 * @author karl
 */
public class Symbol {
    /**
     * Add new id to symbol table.
     * If id type implies starting new scope, one is created.
     * An error message is generated if id already defined; in which case,
     * symbol is not added to table.
     * @param id ident to add to symbol table.
     * @param obj id's object instance.
     */
    public static void define(Ident id, Object obj) {
        String key = id.getName();
        Table symtab = Table.getTheOne();
        Symbol symbol = symtab.find(key);
        if (null != symbol) {
            error(symbol, id);
            return;
        }
        ScopeManager smgr = ScopeManager.getTheOne();
        Scope scope = null;
        if (id.definesNewScope()) {
            scope = new Scope(id);
            smgr.push(scope);
        } else {
            scope = smgr.peek();
        }
        symtab.add(new Symbol(id, scope, obj));
    }
    /**
     * Print error message for duplicate symbol defn.
     */
    public static void error(Symbol was, Ident now) {
        String loc = was.m_id.getLocation().toString();
        message(now.getLocation(), 'E', "DEFN-2", now.getId(), loc);
    }
    public void redefined(Ident now) {
        error(this, now);
    }
    private Symbol(Ident id, Scope scope, Object obj) {
        m_id = id;
        m_scope = scope;
        m_obj = obj;
    }
    public String getName() {
        return m_id.getName();
    }
    private void setScope(Scope scope){
        m_scope = scope;
    }
    public Scope getScope() {
        return m_scope;
    }
    public void setObj(Object obj) {
        m_obj = obj;
    }
    public Ident getId() {
        return m_id;
    }
    public Object getObj() {
        return m_obj;
    }
    private Ident   m_id;
    /**
     * Scope of symbol definition.
     */
    private Scope   m_scope;
    /**
     * Type-specific object.
     */
    private Object  m_obj;
}
