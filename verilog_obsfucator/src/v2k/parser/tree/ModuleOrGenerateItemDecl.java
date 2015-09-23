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
public class ModuleOrGenerateItemDecl {
	public ModuleOrGenerateItemDecl(NetDeclaration ele) {
		m_item = new ANet(ele);
	}
	public ModuleOrGenerateItemDecl(RegDecl ele) {
		m_item = new AReg(ele);
	}
	public ModuleOrGenerateItemDecl(IntegerDecl ele) {
		m_item = new AInteger(ele);
	}
	public ModuleOrGenerateItemDecl(RealDecl ele) {
		m_item = new AReal(ele);
	}
	public ModuleOrGenerateItemDecl(TimeDecl ele) {
		m_item = new ATime(ele);
	}
	public ModuleOrGenerateItemDecl(RealtimeDecl ele) {
		m_item = new ARealtime(ele);
	}
	public ModuleOrGenerateItemDecl(EventDecl ele) {
		m_item = new AEvent(ele);
	}
	public ModuleOrGenerateItemDecl(ListOf<GenvarIdent> ele) {
		m_item = new AGenvar(ele);
	}
	public ModuleOrGenerateItemDecl(TaskDeclaration ele) {
		m_item = new ATask(ele);
	}
	public ModuleOrGenerateItemDecl(FuncDecl ele) {
		m_item = new AFunc(ele);
	}
	private enum EType {
		eNet,
		eReg,
		eInteger,
		eReal,
		eTime,
		eRealtime,
		eEvent,
		eGenvar,
		eTask,
		eFunc
	}

	private A   m_item;
    
	private static abstract class A {
		protected A(EType e) {
			m_type = e;
		}
		final EType m_type;    
	}


	private static class ANet extends A {
		private ANet(NetDeclaration ele) {
			super(EType.eNet);
			m_ele = ele;
		}
		private NetDeclaration m_ele;
	}

	private static class AReg extends A {
		private AReg(RegDecl ele) {
			super(EType.eReg);
			m_ele = ele;
		}
		private RegDecl m_ele;
	}

	private static class AInteger extends A {
		private AInteger(IntegerDecl ele) {
			super(EType.eInteger);
			m_ele = ele;
		}
		private IntegerDecl m_ele;
	}

	private static class AReal extends A {
		private AReal(RealDecl ele) {
			super(EType.eReal);
			m_ele = ele;
		}
		private RealDecl m_ele;
	}

	private static class ATime extends A {
		private ATime(TimeDecl ele) {
			super(EType.eTime);
			m_ele = ele;
		}
		private TimeDecl m_ele;
	}

	private static class ARealtime extends A {
		private ARealtime(RealtimeDecl ele) {
			super(EType.eRealtime);
			m_ele = ele;
		}
		private RealtimeDecl m_ele;
	}

	private static class AEvent extends A {
		private AEvent(EventDecl ele) {
			super(EType.eEvent);
			m_ele = ele;
		}
		private EventDecl m_ele;
	}

	private static class AGenvar extends A {
		private AGenvar(ListOf<GenvarIdent> ele) {
			super(EType.eGenvar);
			m_ele = ele;
		}
		private ListOf<GenvarIdent> m_ele;
	}

	private static class ATask extends A {
		private ATask(TaskDeclaration ele) {
			super(EType.eTask);
			m_ele = ele;
		}
		private TaskDeclaration m_ele;
	}

	private static class AFunc extends A {
		private AFunc(FuncDecl ele) {
			super(EType.eFunc);
			m_ele = ele;
		}
		private FuncDecl m_ele;
	}
}
