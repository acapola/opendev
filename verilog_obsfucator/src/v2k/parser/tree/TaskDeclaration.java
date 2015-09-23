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
import	java.util.*;

/**
 *
 * @author karl
 */
public class TaskDeclaration {

    public TaskDeclaration(boolean isAuto, TaskIdent id) {
        m_isAuto = isAuto;
        m_id = id;
		TreeSymbol.add(id, this);
    }
    public void add(TaskItemDecl tid) {
        m_taskItems.add(tid);
    }
    public void add(ListOf<TfPortDeclaration> lop) {
        m_ports = lop;
    }
    public void add(BlockItemDecl bid) {
        m_blkItems.add(bid);
    }
    public void add(Statement st) {
        m_stmt = st;
    }

    private boolean     		m_isAuto;
    private TaskIdent   		m_id;
	private List<TaskItemDecl>	m_taskItems = new LinkedList<TaskItemDecl>();
	private List<BlockItemDecl>	m_blkItems  = new LinkedList<BlockItemDecl>();
	//
	private List<TfPortDeclaration>	m_ports;
	private Statement				m_stmt;

}
