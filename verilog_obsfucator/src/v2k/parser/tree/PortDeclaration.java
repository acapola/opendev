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
import  java.util.*;

/**
 *
 * @author karl
 */
public class PortDeclaration {

    public PortDeclaration(boolean isOnlyDefn, int m_dir, NetType m_type, Range m_rng,
            OutputVarType m_ovt, boolean m_isReg, boolean m_isSigned,
            List<? extends PortIdent> m_ids) {
        this.m_dir = m_dir;
        this.m_type = m_type;
        this.m_rng = m_rng;
        this.m_ovt = m_ovt;
        this.m_isReg = m_isReg;
        this.m_isSigned = m_isSigned;
        this.m_ids = m_ids;
        this.m_cannotRedefine = isOnlyDefn;
        updateSymbols(isOnlyDefn);
    }
    private final int                   m_dir; //LA(1) value
    private NetType                     m_type;
    private Range                       m_rng;
    private OutputVarType               m_ovt;
    private final boolean               m_isReg, m_isSigned;
    /**
     * Cannot redefine if declared using list_of_port_declarations.
     */
    private final boolean               m_cannotRedefine;
    public List<? extends PortIdent>   m_ids;

    public boolean cannotRedefine() {
        return m_cannotRedefine;
    }
    private void updateSymbols(boolean isOnlyDefn) {
        if (isOnlyDefn) {
            TreeSymbol.add(m_ids, this);
        } else {
            /**
             * TODO: handle case where port is multiply defined, as in:
             * case1) input p1; output p1;
             * case2) input [3:0] p2; input [4:0] p2;
             * case3) output p3; output reg p3;
             * ...
             */
            TreeSymbol.updatePort(m_ids, this);
        }
    }
}
