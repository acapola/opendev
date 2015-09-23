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
import  static v2k.util.Utils.invariant;

/**
 *
 * @author karl
 */
public class PortExpression {

    public PortExpression(PortReference pref) {
        add(pref);
    }

    void add(PortReference  pref) {
        m_exprs.add(pref);
    }
    public String getName() {
        return getId().getName();
    }
    public PortIdent getId() {
        invariant(1 == m_exprs.size()); //TODO: could be port {...}
        return m_exprs.get(0).getId();
    }
    private List<PortReference> m_exprs = new LinkedList<PortReference>();
}
