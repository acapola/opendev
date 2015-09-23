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
public class FuncDecl {

    public FuncDecl(boolean isAuto, FuncType type, FuncIdent id,
            List<FuncItemDecl> items, List<TfPortDeclaration> ports,
            List<BlockItemDecl> blkItems, FuncStatement stmt) {
        m_isAuto = isAuto;
        m_type = type;
        m_id = id;
        m_items = items;
        m_ports = ports;
        m_blkItems = blkItems;
        m_stmt = stmt;
        TreeSymbol.update(id, this);
    }

    private boolean     m_isAuto;
    private FuncType    m_type;
    private FuncIdent   m_id;
    private List<FuncItemDecl>      m_items;
    private List<TfPortDeclaration> m_ports;
    private List<BlockItemDecl>     m_blkItems;
    private FuncStatement           m_stmt;
}
