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
public class GenerateBlock {

    public GenerateBlock(GenerateBlockIdent id) {
        m_id = id;
    }
    public GenerateBlock(ModuleOrGenerateItem item) {
        add(item);
    }
    public void add(ModuleOrGenerateItem item) {
        m_items.add(item);
    }
    private GenerateBlockIdent          m_id;
    private List<ModuleOrGenerateItem>  m_items = 
        new LinkedList<ModuleOrGenerateItem>();
}
