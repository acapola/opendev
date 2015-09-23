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
public class HierIdent {
	public HierIdent(Ident id) {
		m_id = id;
	}
    public HierIdent(Id2 id2) {
        m_id2 = id2;
    }
    protected HierIdent(HierIdent hi) {
        m_id = hi.m_id;
        m_id2 = hi.m_id2;
    }
    /**A union: i.e., only one is used.*/
    private Ident   m_id;
    private Id2     m_id2;
    
    public static class Id2 {
        Id2(Ident id, ConstExpression cexp) {
           m_id = id;
           m_cexpr = cexp;
        }
        void add(HierIdent hi) {
            m_sfxs.add(hi);
        }
        private Ident           m_id;
        private ConstExpression m_cexpr;
        private List<HierIdent> m_sfxs = new LinkedList<HierIdent>();
    }

    @Override
    public String toString() {
        if(null!=m_id2) {
            return m_id2.toString();
        } else {
            return m_id.toString();
        }
    }
}
