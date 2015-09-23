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
import  v2k.parser.tree.*;
import  java.util.*;

/**
 *
 * @author karl
 */
public class ParseTree extends ASTreeBase {
    
    public ParseTree() {
    }
    
    @Override
    public ModuleDeclaration moduleDeclaration(ModuleIdent mid, ListOf<ParameterDeclaration> nu1)
            throws TreeException {
		String nm = mid.toString();
        Module mod = new Module(nm);
        Module was = m_modulesByName.get(nm);
        if (null != was) {
            throw new TreeException('E', "DEFN-1", nm, was.getWhereDecl());
        }
        m_modulesByName.put(nm, mod);
        m_mod = mod;
        return null;
    }
    
    @Override
    public ListOf<ModuleInstance> moduleInstantiation(ListOf<ModuleInstance> nu1,
            Ident refNm, ParameterValueAssignment nu2, ModuleInstance inst) {
        inst.set(refNm, nu2);
        m_mod.addInstance(inst);
        return null;
    }
    
    @Override
	public ModuleIdent moduleIdentifier(Ident id) {
		return new ModuleIdent(id);
	}
    
    @Override
	public ModuleInstanceIdent moduleInstanceIdentifier(Ident id) {
		return new ModuleInstanceIdent(id);
	}
    
    @Override
    public ModuleInstance moduleInstance() {
        return new ModuleInstance();
    }
    @Override
    public void moduleInstance(ModuleInstance inst, ModuleInstanceIdent nm,
            Range nu1) {
        inst.set(nm, (Range)null);
    }

    /**Get current module.*/
    public Module getMod() {
        assert(null != m_mod);
        return m_mod;
    }
    
	public Map<String,Module> getModulesByName() {
		return m_modulesByName;
	} 

	/**Map of module name to Module.*/
	private Map<String,Module> m_modulesByName = new HashMap<String,Module>();
    /**Current module definition.*/
    private Module m_mod;

    @Override
    public String toString() {
        return "ParseTree{" +
                "m_modulesByName=" + m_modulesByName +
                ", m_mod=" + m_mod +
                '}';
    }
}
