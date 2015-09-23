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
public class NonPortModuleItem {
	public NonPortModuleItem(ModuleOrGenerateItem ele) {
		m_item = new AModGenItem(ele);
	}
	public NonPortModuleItem(ListOf<ModuleOrGenerateItem> ele) {
		m_item = new AGenRegion(ele);
	}
	public NonPortModuleItem(ParameterDeclaration ele) {
		m_item = new AParm(ele);
	}
	public NonPortModuleItem(SpecparamDecl ele) {
		m_item = new ASpec(ele);
	}
	private enum EType {
		eModGenItem,
		eGenRegion,
		eParm,
		eSpec
	}

	private A   m_item;
    
	private static abstract class A {
		protected A(EType e) {
			m_type = e;
		}
		final EType m_type;    
	}


	private static class AModGenItem extends A {
		private AModGenItem(ModuleOrGenerateItem ele) {
			super(EType.eModGenItem);
			m_ele = ele;
		}
		private ModuleOrGenerateItem m_ele;
	}

	private static class AGenRegion extends A {
		private AGenRegion(ListOf<ModuleOrGenerateItem> ele) {
			super(EType.eGenRegion);
			m_ele = ele;
		}
		private ListOf<ModuleOrGenerateItem> m_ele;
	}

	private static class AParm extends A {
		private AParm(ParameterDeclaration ele) {
			super(EType.eParm);
			m_ele = ele;
		}
		private ParameterDeclaration m_ele;
	}

	private static class ASpec extends A {
		private ASpec(SpecparamDecl ele) {
			super(EType.eSpec);
			m_ele = ele;
		}
		private SpecparamDecl m_ele;
	}
}
