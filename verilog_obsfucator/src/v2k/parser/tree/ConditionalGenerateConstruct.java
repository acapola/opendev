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
public class ConditionalGenerateConstruct {
	public ConditionalGenerateConstruct(IfGenerateConstruct ele) {
		m_item = new AIf(ele);
	}
	public ConditionalGenerateConstruct(CaseGenerateConstruct ele) {
		m_item = new ACase(ele);
	}
	private enum EType {
		eIf,
		eCase
	}

	private A   m_item;
    
	private static abstract class A {
		protected A(EType e) {
			m_type = e;
		}
		final EType m_type;    
	}


	private static class AIf extends A {
		private AIf(IfGenerateConstruct ele) {
			super(EType.eIf);
			m_ele = ele;
		}
		private IfGenerateConstruct m_ele;
	}

	private static class ACase extends A {
		private ACase(CaseGenerateConstruct ele) {
			super(EType.eCase);
			m_ele = ele;
		}
		private CaseGenerateConstruct m_ele;
	}
}
