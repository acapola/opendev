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
import static v2k.parser.VlogParserTokenTypes.LITERAL_repeat;

/**
 *
 * @author karl
 */
public class LoopStatement {
    private enum EType {eForever, eRepeat, eWhile, eFor};

    public LoopStatement(Statement st) {
       	m_stmt = new AForever(st); 
    }
    public LoopStatement(int tk, Expression ex, Statement st) {
        m_stmt = (tk == LITERAL_repeat) ?
            new ARepeat(ex, st) : new AWhile(ex, st);
    }
    public LoopStatement(VariableAssignment init, Expression test,
                        VariableAssignment iter, Statement stmt) {
        m_stmt = new AFor(init, test, iter, stmt);
    }

    private A   m_stmt;
    
    private static abstract class A {
        protected A(EType e) {
            m_type = e;
        }
        final EType m_type;    
    }
    
    private static class AForever extends A {
        private AForever(Statement st) {
            super(EType.eForever);
            m_stmt = st;
        }
        private final Statement	m_stmt;
    }    
    private static class ARepeat extends A {
        private ARepeat(Expression ex, Statement st) {
            super(EType.eRepeat);
            m_expr = ex;
			m_stmt = st;
        }
        private final Expression    m_expr;
        private final Statement     m_stmt;
    }    
    private static class AWhile extends A {
        private AWhile(Expression ex, Statement st) {
            super(EType.eWhile);
            m_expr = ex;
			m_stmt = st;
        }
        private final Expression	m_expr;
        private final Statement		m_stmt;
    }    
    private static class AFor extends A {
        private AFor(VariableAssignment init, Expression test,
		             VariableAssignment iter, Statement stmt) {
            super(EType.eFor);
            m_init = init;
			m_test = test;
			m_iter = iter;
			m_stmt = stmt;
        }
		private VariableAssignment	m_init, m_iter;
		private Expression			m_test;
		private Statement			m_stmt;
    }    
}
