/*
*************************************************************************
*************************************************************************
**                                                                     **
**  V2KPARSE                                                           **
**  Copyright (C) 2008    Karl W. Pfalzer                              **
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
package v2k.parser.tree.basic;
import  v2k.parser.Location;
import  v2k.parser.tree.*;
import  java.util.*;

/**
 *
 * @author karl
 */
public class Module {
	public Module(String nm) {
		m_name = nm;
        m_decl = Location.factory();
	}
    public List<ParameterDeclaration> getParmDecls() {
        return m_parmDecls;
    }

    /**
     * Get module name.
     */
    public String getName() {
        return m_name;
    }

    /**
     * Get declaration location.
     */
    public Location getWhereDecl() {
        return m_decl;
    }

    /**
     * Add module instance.
     */
    public ModuleInstance addInstance(ModuleInstance inst) {
        m_modInsts.add(inst);
        return inst;
    }

    /**
     * Get list of instances
     */
    public List<ModuleInstance> getInstances() {
        return m_modInsts;
    }

    /**
     * Name of module.
     */
    private final String m_name;
    /**
     * Location of module declaration.
     */
    private final Location m_decl;
    /**
     * List of module instances in order of declaration.
     */
    private List<ModuleInstance> m_modInsts = new LinkedList<ModuleInstance>();

    private List<ParameterDeclaration>  m_parmDecls;

}
