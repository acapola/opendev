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
 *
 * @author karl
 */
public class EventExpression {
    public EventExpression(Object e1, Object e2) {
        m_e1 = (E1)e1;
        m_e2 = (E2)e2;
    }
    private E1  m_e1;
    private E2  m_e2;
    
    static class E1 {
        E1(int tk, Expression ex) {
            m_tok = tk;
            m_expr = ex;
        }
        private final int m_tok;  //-1 for neither pos/negedge
        private Expression m_expr;
    }
    static class E2 {
        E2(boolean isOr, EventExpression ee, Object e2) {
            m_isOr = isOr;
            m_expr = ee;
            m_expr2 = (E2)e2;
        }
        private final boolean   m_isOr;
        private EventExpression m_expr;
        private E2              m_expr2;
    }
}
