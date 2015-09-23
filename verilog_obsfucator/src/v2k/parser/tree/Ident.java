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
import  antlr.Token;
import  antlr.CommonToken;
import  v2k.parser.Location;
import  v2k.parser.tree.symbol.Scope;
/**
 *
 * @author karl
 */
public class Ident implements TreeSymbol.Id {
    public static enum EType {
        eUnknown,
        eVariable, ePort, eSystemFunc, eFunc, eModule,
        eGenerateBlock, eParameter, eGenvar, eReal,
        eEvent, eBlock, eSystemTask, eModuleInst, eTask,
        eNet, eSpecParam
    }
    public Ident(Token tok, EType type) {
        m_id = tok.getText();
        m_type = type;
        setLocation(tok);
    }
    public Ident(Token tok) {
        this(tok, EType.eUnknown);
    }
    public Ident(String txt, EType type) {
        this(new CommonToken(txt),type);
    }
    private void setLocation(Token tok) {
        CommonToken ctok = (CommonToken)tok;
        //TODO: major kludge; BUT: location factory gets advanced during LA().
        m_loc = new Location(Location.factory().getFilename(), ctok.getLine());
    }
	protected Ident(Ident id, EType type) {
		m_id = id.m_id;
        m_type = type;
        m_loc = id.m_loc;
	}
    public Ident getId() {
        return this;
    }
    public String getName() {
        return m_id;
    }
    public static boolean definesNewScope(EType type) {
        switch(type) {
            case eModule:
            case eGenerateBlock:
            case eBlock:
            case eFunc:
            case eTask:
                return true;
            default:
                return false;
        }
    }
    public boolean definesNewScope() {
        return definesNewScope(m_type);
    }
    public Location getLocation() {
        return m_loc;
    }
    public Scope.EType asScopeType() {
        switch(m_type) {
            case eModule:
                return Scope.EType.eModule;
            case eGenerateBlock:
                return Scope.EType.eGenerateBlk;
            case eBlock:
                return Scope.EType.eNamedBlk;
            case eFunc:
                return Scope.EType.eFunc;
            case eTask:
                return Scope.EType.eTask;
        }
        return null;
    }
    @Override
    public String toString() {
        return getName();
    }
    public EType getType() {
        return m_type;
    }
    private String      m_id;
    private EType       m_type = EType.eUnknown;
    private Location    m_loc;
}
