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
import  java.util.List;
import  v2k.parser.tree.symbol.*;
import static v2k.parser.Message.message;
import static v2k.parser.tree.symbol.Symbol.define;
import static v2k.util.Utils.invariant;


/**
 * Bridge class to tree.symbol.Symbol.
 * 
 * @author karl
 */
public class TreeSymbol {
    static void add(List<? extends Id> ids, Object obj) {
        for (Id ele : ids) {
            define(ele.getId(), obj);
        }
    }
    static void add(ListOf<? extends Id> ids, Object obj) {
        for (Id ele : ids) {
            define(ele.getId(), obj);
        }
    }
    static void add(Ident id, Object obj) {
        define(id, obj);
    }
    static void add(Ident id) {
        add(id, null);
    }
    /**
     * Find existing symbol definition and replace object
     * with this one.
     */
    static void updatePort(Ident id, Object newObj) {
        Symbol existing = find(id);
        if (null == existing) {
            message(id.getLocation(), 'E', "PORT-1", id);
        } else if (existing.getObj() instanceof v2k.parser.tree.PortDeclaration) {
            //No duplicate declarations.
            existing.redefined(id);
        } else {
            //Change a Port to a PortDeclaration
            existing.setObj(newObj);
        }
    }
    static void updatePort(List<? extends Id> ids, Object newObj) {
        Ident id = null;
        Symbol existing = null;
        for (Id ele : ids) {
            id = ele.getId();
            updatePort(id, newObj);
        }
    }
    static void updateNet(List<? extends Id> ids, Object newObj) {
        Ident id = null;
        Symbol existing = null;
        for (Id ele : ids) {
            id = ele.getId();
            existing = find(id.getId());
            boolean doRedefineRangeCheck = false;
            if (null == existing) {
                add(id, newObj);
            } else if (existing.getObj() instanceof v2k.parser.tree.PortDeclaration) {
                if (true == ((v2k.parser.tree.PortDeclaration)existing.getObj()).cannotRedefine()) {
                    existing.redefined(id);
                } else {
                    doRedefineRangeCheck = true;
                }
            } else {
                doRedefineRangeCheck = true;
            }
            if (doRedefineRangeCheck) {
                //TODO: can add wire to a port
                //Need to make sure range bounds identical
                existing.redefined(id);
            }
        }
    }
    static void updateReg(ListOf<? extends Id> ids, Object newObj) {
        Ident id = null;
        Symbol existing = null;
        for (Id ele : ids) {
            id = ele.getId();
            existing = find(id.getId());
            if (null == existing) {
                add(id, newObj);
            } else {
                existing.redefined(id);
            }
        }
    }
    static void update(Ident mid, Object obj) {
        Symbol existing = find(mid.getId());
        invariant(null != existing);
        existing.setObj(obj);
    }
    private static Symbol find(Ident id) {
        Symbol sym = Table.getTheOne().find(id.getName());
        return sym;
    }
    /**
     * Tree classes which are symbols implement methods.
     */
    interface Id {
        public Ident getId();
    }
}
