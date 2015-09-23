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
public class ModuleInstance {
    public ModuleInstance() {
    }
    public void add(ListOf<PortConnection> lopc) {
        m_conns = lopc;
    }
    public void set(ModuleInstanceIdent nm, Range rng) {
        m_instNm = nm;
        m_rng = rng;
    }
    public void set(Ident refNm, ParameterValueAssignment parms) {
        m_refName = refNm;
        m_parms = parms;
    }
    public String getRefName() {
        return m_refName.toString();
    }
    
    private Ident                       m_refName;
    private ParameterValueAssignment    m_parms;
    private ModuleInstanceIdent         m_instNm;
    private Range                       m_rng;
    private ListOf<PortConnection>      m_conns;
}
