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
import java.util.*;
import v2k.parser.tree.*;

/**
 *
 * @author karl
 */
public class Scope implements Iterable<Scope> {
    public static enum EType {eGlobal, eModule, eTask, eFunc, eNamedBlk, eGenerateBlk};

    public Scope(Scope parent, EType type) {
        m_parent = parent;
        m_type = type;
    }
    public Scope(EType type) {
        this(null, type);
    }
    public Scope(Ident id) {
        this(id.asScopeType());
        m_id = id;
    }
    /**
     * Iterate up scope.
     * @return iterator for use in "foreach".
     */
    public Iterator<Scope> iterator() {
        return new UpOrder(this);
    }
    public void addChild(Scope scope) {
        if (null == m_children) {
            m_children = new LinkedList<Scope>();
        }
        m_children.add(scope);
    }
    public void setParent(Scope parent) {
        assert null == m_parent;
        m_parent = parent;
    }
    public int getScopeLvl() {
        return m_scopeLvl;
    }
    public Scope getParent() {
        return m_parent;
    }
    public List<Scope> getChildren() {
        return m_children;
    }
    public EType getType() {
        return m_type;
    }
    public Ident getId() {
        return m_id;
    }
    private Scope   m_parent;
    private EType   m_type;
    private Ident   m_id;
    /**
     * Next level of nested scopes.
     */
    private List<Scope> m_children;
    /**
     * Each scope assigned a unique level id.
     * This is used to find definition point
     * during scope (backtrack) tracing.
     */
    private int m_scopeLvl = m_stScopeLvl++;
    private static int m_stScopeLvl = 0;

    /**
     * Traverse up scope tree.
     */
    private static class UpOrder implements Iterator<Scope> {
        public UpOrder(Scope start) {
            m_curr = start;
        }
        public Scope next() {
            Scope rval = m_curr;
            switch (m_curr.m_type) {
                case eModule: case eTask: case eFunc:  //toplevel scopes
                    m_curr = null;
                    break;
                default:
                    m_curr = m_curr.getParent();
            }
            return rval;
        }
        public boolean hasNext() {
            return (null != m_curr);
        }
        public void remove() {
            assert false;
        }
        private Scope   m_curr;
    }
}
