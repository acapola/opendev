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


package v2k.parser;
import  java.io.File;

/**
 *
 * @author karl
 */
public class Location {
	public static Location factory() {
		return new Location(0);
	}
	public static Location factory(int offset) {
		return new Location(offset);
	}
	/*Create at current lexer location.*/
	public Location(int offset) {
		int lnum = Parser.getTheOne().getLine() + offset;
		if (0 > lnum) {
			lnum = 0;
		}
		setLoc(Parser.getTheOne().getFilename(), lnum);
	}
	/*Create using specified location.*/
	public Location(String fn, int lnum) {
		setLoc(fn, lnum);
	}
    /*Create location by adding offset to specified location.*/
    public Location(Location loc, int offset) {
        m_fileName = loc.getFilename();
        m_lineno = loc.getLineno() + offset;
    }
	/**Get String representation.*/
    @Override
	public String toString() {
		return getFilename()+":"+getLineno();
	}
	/**Get filename.*/
	public String getFilename() {
		return m_fileName;
	}
	/**Get line number.*/
	public int getLineno() {
		return m_lineno;
	}
    /**Check if objects point to same location.*/
    public boolean equals(Location obj) {
        return new File(getFilename()).equals(new File(obj.getFilename()))
            && (getLineno() == obj.getLineno());
    }
	private void setLoc(String fn, int lnum) {
		m_fileName = fn;
		m_lineno = lnum;
	}
	/**Filename.*/
	private String	m_fileName;
	/**Line number.*/
	private	int		m_lineno;
}
