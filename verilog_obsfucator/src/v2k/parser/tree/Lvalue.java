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
public class Lvalue {
    public Lvalue(Lv2 lv2) {
        m_item = new ALv2(lv2);
    }
    public Lvalue(ListOf<Lvalue> lv2) {
        m_item = new ALvalue(lv2);
    }
    private static enum EType {
        eLv2, eLvalue
    }
	private A   m_item;

	private static abstract class A {
		protected A(EType e) {
			m_type = e;
		}
		final EType m_type;

        @Override
        public String toString() {
            return "A{" +
                    "m_type=" + m_type +
                    '}';
        }
    }

    private static class ALv2 extends A {
        private ALv2(Lv2 ele) {
            super(EType.eLv2);
            m_ele = ele;
        }
        private Lv2 m_ele;

        @Override
        public String toString() {
            return m_ele.toString();
        }

        public Lv2 getM_ele() {
            return m_ele;
        }

        public void setM_ele(Lv2 m_ele) {
            this.m_ele = m_ele;
        }
    }
    private static class ALvalue extends A {
        private ALvalue(LinkedList<Lvalue> list) {
            super(EType.eLvalue);
            m_eles = list;
        }
        private LinkedList<Lvalue> m_eles;

        @Override
        public String toString() {
            return "ALvalue{" +
                    "m_eles=" + m_eles +
                    '}';
        }
    }
    public static class Lv2 {
        public Lv2(HierIdent hi, PartSelect ps) {
            m_hident = hi;
            m_partSel = ps;
        }
        private HierIdent   m_hident;
        private PartSelect  m_partSel;

        @Override
        public String toString() {
            String out = m_hident.toString();
            if(null!=m_partSel){
                out+=m_partSel.toString();
            }
            return out;
        }

        public HierIdent getM_hident() {
            return m_hident;
        }

        public void setM_hident(HierIdent m_hident) {
            this.m_hident = m_hident;
        }

        public PartSelect getM_partSel() {
            return m_partSel;
        }

        public void setM_partSel(PartSelect m_partSel) {
            this.m_partSel = m_partSel;
        }
    }

    @Override
    public String toString() {
        return m_item.toString();
    }

    public A getM_item() {
        return m_item;
    }

    public void setM_item(A m_item) {
        this.m_item = m_item;
    }
}
