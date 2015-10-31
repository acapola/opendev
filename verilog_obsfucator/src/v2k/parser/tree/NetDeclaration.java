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
import  v2k.parser.tree.TreeSymbol.Id;

/**
 *
 * @author karl
 */
public class NetDeclaration {

    public NetDeclaration(NetType m_type, boolean m_isSigned, Range m_rng,
            Delay3 m_del3, List<NetIdentifiers> m_nets, List<NetDeclAssign> m_decls) {
        assert (false == ((null != m_nets) && (null != m_decls)));
        this.m_type = m_type;
        this.m_isSigned = m_isSigned;
        this.m_rng = m_rng;
        this.m_del3 = m_del3;
        this.m_nets = m_nets;
        this.m_decls = m_decls;
        updateSymbols();
    }

    private NetType                 m_type;
    private boolean                 m_isSigned;
    private Range                   m_rng;
    private Delay3                  m_del3;
    /**
     * Only 1 of m_nets or m_decls is used.
     */
    private List<NetIdentifiers>    m_nets;
    private List<NetDeclAssign>     m_decls;

    List<? extends Id> getIds() {
        return (null != m_nets) ? m_nets : m_decls;
    }
    private void updateSymbols() {
        TreeSymbol.updateNet(getIds(), this);
    }
}