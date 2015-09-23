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

public class ParameterAssignment {
	public ParameterAssignment(Expression ele) {
		m_item = new AExpr(ele);
	}
	public ParameterAssignment(NamedParamAssignment ele) {
		m_item = new ANamed(ele);
	}
	private enum EType {
		eExpr,
		eNamed
	}

	private A   m_item;
    
	private static abstract class A {
		protected A(EType e) {
			m_type = e;
		}
		final EType m_type;    
	}


	private static class AExpr extends A {
		private AExpr(Expression ele) {
			super(EType.eExpr);
			m_ele = ele;
		}
		private Expression m_ele;
	}

	private static class ANamed extends A {
		private ANamed(NamedParamAssignment ele) {
			super(EType.eNamed);
			m_ele = ele;
		}
		private NamedParamAssignment m_ele;
	}
}
