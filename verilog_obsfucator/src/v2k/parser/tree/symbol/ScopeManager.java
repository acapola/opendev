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

/**
 *
 * @author karl
 */
public class ScopeManager {
    public static ScopeManager getTheOne() {
        return m_theOne;
    }
    public Scope peek() {
        return m_stack.peek();
    }
    public void push(Scope scope) {
        Scope parent = peek();
        parent.addChild(scope);
        scope.setParent(parent);
        m_stack.push(scope);
    }
    public Scope pop() {
        return m_stack.pop();
    }
    private ScopeManager() {
        m_stack.push(new Scope(Scope.EType.eGlobal));
    }
    /**
     * Stack of scopes.
     */
    private Stack<Scope>    m_stack = new Stack<Scope>();
    private static ScopeManager m_theOne = new ScopeManager();
}
