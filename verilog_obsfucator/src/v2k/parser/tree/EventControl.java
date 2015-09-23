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
public class EventControl {
    private enum EType {eHierEvent, eStar, eEventExpr};

    public EventControl(boolean isStar) {
        m_item = new AStar(isStar);
    }
    public EventControl(HierEventIdent hei) {
        m_item = new AHier(hei);
    }
    public EventControl(EventExpression ee) {
        m_item = new AEvent(ee);
    }
    
    private A   m_item;
    
    private static abstract class A {
        protected A(EType e) {
            m_type = e;
        }
        final EType m_type;    
    }
    
    private static class AStar extends A {
        private AStar(boolean isStar) {
            super(EType.eStar);
			m_isStar = isStar;
        }
        private boolean m_isStar;
    }    
    private static class AHier extends A {
        private AHier(HierEventIdent hei) {
            super(EType.eHierEvent);
            m_event = hei;
        }
        private HierEventIdent  m_event;
    }    
    private static class AEvent extends A {
        private AEvent(EventExpression ee) {
            super(EType.eEventExpr);
            m_event = ee;
        }
        private EventExpression	m_event;
	}
}
