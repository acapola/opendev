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
public class FuncItemDecl {
	public FuncItemDecl(BlockItemDecl ele) {
		m_item = new ABlk(ele);
	}
	public FuncItemDecl(TfPortDeclaration ele) {
		m_item = new APort(ele);
	}
	private enum EType {
		eBlk,
		ePort
	}

	private A   m_item;
    
	private static abstract class A {
		protected A(EType e) {
			m_type = e;
		}
		final EType m_type;    
	}


	private static class ABlk extends A {
		private ABlk(BlockItemDecl ele) {
			super(EType.eBlk);
			m_ele = ele;
		}
		private BlockItemDecl m_ele;
	}

	private static class APort extends A {
		private APort(TfPortDeclaration ele) {
			super(EType.ePort);
			m_ele = ele;
		}
		private TfPortDeclaration m_ele;
	}
}
