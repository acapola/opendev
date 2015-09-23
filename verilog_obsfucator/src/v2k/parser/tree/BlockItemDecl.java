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
import static v2k.parser.VlogParserTokenTypes.*;

public class BlockItemDecl {
	public BlockItemDecl(boolean isSigned, Range rng, ListOf<BlockVariableType> ele) {
		m_item = new AReg(isSigned, rng, ele);
	}
	public BlockItemDecl(int la1, ListOf<BlockVariableType> ele) {
		switch (la1) {
            case LITERAL_time:
                m_item = new ATime(ele);
                break;
            case LITERAL_integer:
                m_item = new AInt(ele);
                break;
            default:
                assert false;
        }
	}
	public BlockItemDecl(int la1, ListOf<BlockRealType> ele, boolean nil) {
		switch (la1) {
            case LITERAL_real:
                m_item = new AReal(ele);
                break;
            case LITERAL_realtime:
                m_item = new ARealTime(ele);
                break;
            default:
                assert false;
        }
	}
	public BlockItemDecl(EventDecl ele) {
		m_item = new AEvent(ele);
	}
	public BlockItemDecl(LocalParameterDecl ele) {
		m_item = new ALocalParm(ele);
	}
	public BlockItemDecl(ParameterDeclaration ele) {
		m_item = new AParm(ele);
	}
	private enum EType {
		eReg,
		eInt,
		eTime,
		eReal,
		eRealTime,
		eEvent,
		eLocalParm,
		eParm
	}

	private A   m_item;
    
	private static abstract class A {
		protected A(EType e) {
			m_type = e;
		}
		final EType m_type;    
	}


	private static class AReg extends A {
		private AReg(boolean isSigned, Range rng, ListOf<BlockVariableType> ele) {
			super(EType.eReg);
			m_isSigned = isSigned;
            m_range = rng;
            m_ele = ele;
		}
        private boolean                   m_isSigned;
        private Range                     m_range;
		private ListOf<BlockVariableType> m_ele;
	}

	private static class AInt extends A {
		private AInt(ListOf<BlockVariableType> ele) {
			super(EType.eInt);
			m_ele = ele;
		}
		private ListOf<BlockVariableType> m_ele;
	}

	private static class ATime extends A {
		private ATime(ListOf<BlockVariableType> ele) {
			super(EType.eTime);
			m_ele = ele;
		}
		private ListOf<BlockVariableType> m_ele;
	}

	private static class AReal extends A {
		private AReal(ListOf<BlockRealType> ele) {
			super(EType.eReal);
			m_ele = ele;
		}
		private ListOf<BlockRealType> m_ele;
	}

	private static class ARealTime extends A {
		private ARealTime(ListOf<BlockRealType> ele) {
			super(EType.eRealTime);
			m_ele = ele;
		}
		private ListOf<BlockRealType> m_ele;
	}

	private static class AEvent extends A {
		private AEvent(EventDecl ele) {
			super(EType.eEvent);
			m_ele = ele;
		}
		private EventDecl m_ele;
	}

	private static class ALocalParm extends A {
		private ALocalParm(LocalParameterDecl ele) {
			super(EType.eLocalParm);
			m_ele = ele;
		}
		private LocalParameterDecl m_ele;
	}

	private static class AParm extends A {
		private AParm(ParameterDeclaration ele) {
			super(EType.eParm);
			m_ele = ele;
		}
		private ParameterDeclaration m_ele;
	}
}
