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
public class Expression {
    public Expression(Object e1, Object e2) {
        Object[] e1s = (Object[])e1;
        m_unopE1 = (UnaryOp)e1s[0];
        m_primE1 = (Primary)e1s[1];
        m_e2 = (null != e2) ? new Expr2(e2) : null;
    }
    protected Expression(Expression ex) {
        m_unopE1 = ex.m_unopE1;
        m_e2 = ex.m_e2;
        m_primE1 = ex.m_primE1;
    }
    private UnaryOp m_unopE1;
    /**Primary of expression_1.*/
    private Primary m_primE1;
    /**expression_2*/
    private Expr2   m_e2;

    private static class Expr2 {
        Expr2(Object e2) {
            Object[] e2s = (Object[])e2;
            m_isTernary = (Boolean)e2s[0];
            m_binopOrExpr = e2s[1];
            m_expr = (Expression)e2s[2];
            m_expr2 = (Expr2)e2s[3];
        }
        private boolean m_isTernary;
        /**Expression if m_isTernary==true; else BinaryOp.*/
        private Object  m_binopOrExpr;
        private Expression  m_expr;
        private Expr2       m_expr2;

        @Override
        public String toString() {
            if(m_isTernary){
                return m_binopOrExpr.toString()+" ? "+m_expr.toString()+" : "+m_expr2;
            } else {
                return m_binopOrExpr.toString()+m_expr.toString();
            }
        }
    }

    @Override
    public String toString() {
        String out = "";
        if(null!=m_unopE1) {
            out= m_unopE1.toString() + m_primE1.toString();
        }else{
            if(null!=m_primE1) out = m_primE1.toString();
            if(null!=m_e2) out+= m_e2.toString();
        }
        return out;
    }
}
