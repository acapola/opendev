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
public class Statement {
	public Statement(BlockingAssign ele) {
		m_item = new ABlock(ele);
	}
	public Statement(NonBlockingAssign ele) {
		m_item = new ANonBlock(ele);
	}
	public Statement(CaseStatement ele) {
		m_item = new ACase(ele);
	}
	public Statement(ConditionalStatement ele) {
		m_item = new ACond(ele);
	}
	public Statement(DisableStmt ele) {
		m_item = new ADis(ele);
	}
	public Statement(EventTrigger ele) {
		m_item = new AEvent(ele);
	}
	public Statement(LoopStatement ele) {
		m_item = new ALoop(ele);
	}
	public Statement(SystemTaskEnable ele) {
		m_item = new ASysTask(ele);
	}
	public Statement(TaskEnable ele) {
		m_item = new ATask(ele);
	}
	public Statement(WaitStatement ele) {
		m_item = new AWait(ele);
	}
	public Statement(SeqBlock ele) {
		m_item = new ASeq(ele);
	}
	public Statement(ParBlock ele) {
		m_item = new APar(ele);
	}
	public Statement(ProceduralContinuousAssign ele) {
		m_item = new AContAssgn(ele);
	}
	public Statement(ProceduralTimingControlStatement ele) {
		m_item = new ACntl(ele);
	}
	private enum EType {
		eBlock,
		eNonBlock,
		eCase,
		eCond,
		eDis,
		eEvent,
		eLoop,
		eSysTask,
		eTask,
		eWait,
		eSeq,
		ePar,
		eContAssgn,
		eCntl
	}

	private A   m_item;
    
	private static abstract class A {
		protected A(EType e) {
			m_type = e;
		}
		final EType m_type;    
	}


	private static class ABlock extends A {
		private ABlock(BlockingAssign ele) {
			super(EType.eBlock);
			m_ele = ele;
		}
		private BlockingAssign m_ele;
	}

	private static class ANonBlock extends A {
		private ANonBlock(NonBlockingAssign ele) {
			super(EType.eNonBlock);
			m_ele = ele;
		}
		private NonBlockingAssign m_ele;
	}

	private static class ACase extends A {
		private ACase(CaseStatement ele) {
			super(EType.eCase);
			m_ele = ele;
		}
		private CaseStatement m_ele;
	}

	private static class ACond extends A {
		private ACond(ConditionalStatement ele) {
			super(EType.eCond);
			m_ele = ele;
		}
		private ConditionalStatement m_ele;
	}

	private static class ADis extends A {
		private ADis(DisableStmt ele) {
			super(EType.eDis);
			m_ele = ele;
		}
		private DisableStmt m_ele;
	}

	private static class AEvent extends A {
		private AEvent(EventTrigger ele) {
			super(EType.eEvent);
			m_ele = ele;
		}
		private EventTrigger m_ele;
	}

	private static class ALoop extends A {
		private ALoop(LoopStatement ele) {
			super(EType.eLoop);
			m_ele = ele;
		}
		private LoopStatement m_ele;
	}

	private static class ASysTask extends A {
		private ASysTask(SystemTaskEnable ele) {
			super(EType.eSysTask);
			m_ele = ele;
		}
		private SystemTaskEnable m_ele;
	}

	private static class ATask extends A {
		private ATask(TaskEnable ele) {
			super(EType.eTask);
			m_ele = ele;
		}
		private TaskEnable m_ele;
	}

	private static class AWait extends A {
		private AWait(WaitStatement ele) {
			super(EType.eWait);
			m_ele = ele;
		}
		private WaitStatement m_ele;
	}

	private static class ASeq extends A {
		private ASeq(SeqBlock ele) {
			super(EType.eSeq);
			m_ele = ele;
		}
		private SeqBlock m_ele;
	}

	private static class APar extends A {
		private APar(ParBlock ele) {
			super(EType.ePar);
			m_ele = ele;
		}
		private ParBlock m_ele;
	}

	private static class AContAssgn extends A {
		private AContAssgn(ProceduralContinuousAssign ele) {
			super(EType.eContAssgn);
			m_ele = ele;
		}
		private ProceduralContinuousAssign m_ele;
	}

	private static class ACntl extends A {
		private ACntl(ProceduralTimingControlStatement ele) {
			super(EType.eCntl);
			m_ele = ele;
		}
		private ProceduralTimingControlStatement m_ele;
	}
}
