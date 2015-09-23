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
public class CaseItem {
    private enum EType {eNonDflt, eDflt};

    public CaseItem(Expression ex) {
        m_item = new ANonDflt(ex);
    }
    public CaseItem(Statement stmt) {
        m_item = new ADflt(stmt);
    }
    public void add(Expression ex) {
        m_item.add(ex);
    }
    public void add(Statement st) {
        m_item.add(st);
    }
    
    private A   m_item;
    
    private static abstract class A {
        protected A(EType e) {
            m_type = e;
        }
        protected void add(Expression ex) {
            assert(false);
        }
        protected void add(Statement st) {
            assert(false);
        }
        
        final EType m_type;    
    }
    
    private static class ANonDflt extends A {
        private ANonDflt(Expression ex) {
            super(EType.eNonDflt);
            add(ex);
        }
        @Override
		protected void add(Expression ex) {
			m_expr.add(ex);
		}
        @Override
        protected void add(Statement stmt) {
            m_stmt = stmt;
        }
        private List<Expression>	m_expr = new LinkedList<Expression>();
        private Statement           m_stmt;
    }    
    private static class ADflt extends A {
        private ADflt(Statement stmt) {
            super(EType.eDflt);
            m_stmt = stmt;
        }
        private Statement   m_stmt;
    }    

}
