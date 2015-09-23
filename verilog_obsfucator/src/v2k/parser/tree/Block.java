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
public abstract class Block {

    protected Block(BlockIdent bid) {
        m_bid = bid;
        if (null == m_bid) {
            String nm = "$b"+m_unnamedCnt++;
            Ident id = new Ident(nm, Ident.EType.eBlock);
            TreeSymbol.add(id, this);
        }
    }
  
    public void add(BlockItemDecl bd) {
        if (null == m_decls) {
            m_decls = new LinkedList<BlockItemDecl>();
        }
        m_decls.add(bd);
    }

    public void add(Statement st) {
        if (null == m_stmts) {
            m_stmts = new LinkedList<Statement>();
        }
        m_stmts.add(st);
    }


    protected BlockIdent            m_bid;
    protected List<BlockItemDecl>   m_decls;
    protected List<Statement>       m_stmts;
    /**
     * If unnamed block, then assign and update.
     */
    private static int m_unnamedCnt = 0;
}
