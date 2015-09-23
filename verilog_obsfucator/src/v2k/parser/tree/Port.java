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


package v2k.parser.tree;

/**
 * Port object declared in list_of_ports, as in:
 *    module a(p1, p2,, p3); //p1,2,3 are Port
 *
 * @author karl
 */
public class Port {
    /**
     * Create port object.
     * @param m_pid rarely used form of "module x (.ID(EXPR))".
     * @param m_expr more common form "module x(EXPR, ...)"
     */
    public Port(PortIdent m_pid, PortExpression m_expr) {
        this.m_pid = m_pid;
        this.m_expr = m_expr;
        TreeSymbol.add(getId(), this);
    }
    public String getName() {
        return getId().getName();
    }
    public PortIdent getId() {
        return (null != m_pid) ? m_pid : m_expr.getId();
    }
    private PortIdent       m_pid;
    private PortExpression  m_expr;
}
