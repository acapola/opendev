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
public class NonBlockingAssign {

    public NonBlockingAssign(Lvalue lv, DelayOrEventControl dec, Expression exp) {
        m_lval = lv;
        m_dec = dec;
        m_expr = exp;
    }
    private Lvalue              m_lval;
    private DelayOrEventControl m_dec;
    private Expression          m_expr;

    @Override
    public String toString() {
        String out = m_lval.toString() + " <= ";
        if(null!=m_dec) out += m_dec.toString();
        out += m_expr.toString() + ";";
        return out;
    }

    public Lvalue getM_lval() {
        return m_lval;
    }

    public void setM_lval(Lvalue m_lval) {
        this.m_lval = m_lval;
    }

    public Expression getM_expr() {
        return m_expr;
    }

    public void setM_expr(Expression m_expr) {
        this.m_expr = m_expr;
    }

    public DelayOrEventControl getM_dec() {
        return m_dec;
    }

    public void setM_dec(DelayOrEventControl m_dec) {
        this.m_dec = m_dec;
    }
}
