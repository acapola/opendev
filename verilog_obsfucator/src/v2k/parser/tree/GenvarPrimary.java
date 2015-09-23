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
public class GenvarPrimary {
	public GenvarPrimary(ConstPrimary ele) {
		m_item = new APrim(ele);
	}
	public GenvarPrimary(GenvarIdent ele) {
		m_item = new AId(ele);
	}
	private enum EType {
		ePrim,
		eId
	}

	private A   m_item;
    
	private static abstract class A {
		protected A(EType e) {
			m_type = e;
		}
		final EType m_type;    
	}


	private static class APrim extends A {
		private APrim(ConstPrimary ele) {
			super(EType.ePrim);
			m_ele = ele;
		}
		private ConstPrimary m_ele;
	}

	private static class AId extends A {
		private AId(GenvarIdent ele) {
			super(EType.eId);
			m_ele = ele;
		}
		private GenvarIdent m_ele;
	}
}
