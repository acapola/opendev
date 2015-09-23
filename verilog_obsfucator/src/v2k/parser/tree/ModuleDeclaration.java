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
public class ModuleDeclaration {

    public ModuleDeclaration(ModuleIdent m_id, List<ParameterDeclaration> m_parms) {
        this.m_id = m_id;
        this.m_parms = m_parms;
        TreeSymbol.update(m_id, this);
    }
    public void setPorts(boolean isAnsi, List<?> ports) {
        this.m_portsAndItems = (isAnsi) ? new AnsiStyle(ports)
                : new NonAnsiStyle(ports);
    }
    public void addItem(Object item) {
        m_portsAndItems.add(item);
    }
    public String getModName() {
        return m_id.toString();
    }
    public HashMap<String,PortIdent> getPorts() {
        HashMap<String,PortIdent> rval = new HashMap<>();
        List<PortDeclaration> ports = m_portsAndItems.m_ports;
        String pnm;
        for (PortDeclaration pDecl : ports) {
            PortIdent p = pDecl.m_ids.get(0);
            pnm = p.getName();
            rval.put(pnm, p);
        }
        return rval;
    }
    private abstract class Style<TPort,TItem> {
        Style(List<TPort> ports) {
            m_ports = ports;
        }
        protected void add(Object item) {
            if (null == m_items) {
                m_items = new LinkedList<TItem>();
            }
            m_items.add((TItem)item);
        }
        protected List<TPort>   m_ports;
        protected List<TItem>   m_items;
    }
    private class AnsiStyle extends Style<PortDeclaration,NonPortModuleItem> {
        public AnsiStyle(List<?> ports) {
            super((List<PortDeclaration>)ports);
        }
    }
    private class NonAnsiStyle extends Style<Port,ModuleItem> {
        public NonAnsiStyle(List<?> ports) {
            super((List<Port>)ports);
        }
    }
    private ModuleIdent                 m_id;
    private List<ParameterDeclaration>  m_parms;
    private Style                       m_portsAndItems;
}
