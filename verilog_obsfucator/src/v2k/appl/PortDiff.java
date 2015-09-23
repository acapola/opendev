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


package v2k.appl;
import  java.util.*;
import  v2k.parser.Main;
import  v2k.parser.tree.*;

/**
 *
 * @author karl
 */
public class PortDiff extends Main {
/*
    static class Tree extends ParseTree {
        @Override
        public void moduleDeclaration(ModuleDeclaration mdecl) {
            HashMap<String,Port> ports = mdecl.getPorts();
            m_ports.add(ports);
        }
    }
    static List<HashMap<String,Port>>  m_ports = new LinkedList<HashMap<String,Port>>();

    @Override
    protected ASTreeBase getTree() {
        return new Tree();
    }

    private PortDiff(String argv[]) {
        super(argv);
    }

    private void process() {
        assert 2==m_ports.size();
        HashMap<String,Port>
                before = m_ports.get(0),
                after =  m_ports.get(1);

        for (String n : after.keySet()) {
            if (false == before.containsKey(n)) {
                System.out.println("port \""+n+"\": added");
            }
        }
        for (String n : before.keySet()) {
            if (false == after.containsKey(n)) {
                System.out.println("port \""+n+"\": deleted");
            }
        }
    }

    public static void main(String argv[]) {
        (new PortDiff(argv)).process();
    }*/
}
