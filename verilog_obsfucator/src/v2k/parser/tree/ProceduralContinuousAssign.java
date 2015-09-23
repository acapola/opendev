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
public class ProceduralContinuousAssign {
    private enum EType {eAssign, eDeassign, eForce, eRelease};

    public ProceduralContinuousAssign(VariableAssignment vas, boolean isAssign) {
        m_item = (isAssign) ? new AAssign(vas) : new AForce(vas);
    }
    public ProceduralContinuousAssign(Lvalue stmt, boolean isDeassign) {
        m_item = (isDeassign) ? new ADeassign(stmt) : new ARelease(stmt);
    }
    
    private A   m_item;
    
    private static abstract class A {
        protected A(EType e) {
            m_type = e;
        }
        final EType m_type;    
    }
    
    private static class AAssign extends A {
        private AAssign(VariableAssignment a) {
            super(EType.eAssign);
            m_vas = a;
        }
        private VariableAssignment  m_vas;
    }    
    private static class AForce extends A {
        private AForce(VariableAssignment a) {
            super(EType.eForce);
            m_vas = a;
        }
        private VariableAssignment  m_vas;
    }    
    private static class ADeassign extends A {
        private ADeassign(Lvalue lv) {
            super(EType.eDeassign);
            m_lv = lv;
        }
        private Lvalue  m_lv;
    }    
    private static class ARelease extends A {
        private ARelease(Lvalue lv) {
            super(EType.eRelease);
            m_lv = lv;
        }
        private Lvalue  m_lv;
    }    

}
