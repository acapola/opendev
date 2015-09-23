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

import v2k.parser.VlogParserTokenTypes;

/**
 *
 * @author karl
 */
public class RangeExpression {
    public RangeExpression(Expression lhs, int rangeOp, ConstExpression rhs) {
        m_lhs = lhs;
        m_rangeOp = rangeOp;
        m_rhs = rhs;
    }
    /**In case of rangeOp==COLON, then lhs is actually ConstExpression.*/
    private Expression  m_lhs;
    /**One of VlogParserTokenTypes or -1 if just (base) expression used.*/
    private int m_rangeOp;
    private ConstExpression  m_rhs;

    @Override
    public String toString() {
        String out=m_lhs.toString();
        out+="{"+m_rangeOp+"}";
        switch(m_rangeOp){
            case VlogParserTokenTypes.NUMBER:
            case VlogParserTokenTypes.SIZED_NUMBER:
            case VlogParserTokenTypes.IDENT:
                out+="{"+m_rangeOp+"}";
                break;
            default:
                out+=VlogParserTokenTypes.toString(m_rangeOp);
        }
        if(null!=m_rhs) out+=m_rhs;
        return out;
    }
}
