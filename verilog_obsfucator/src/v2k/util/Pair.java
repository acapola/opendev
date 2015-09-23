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
package v2k.util;

import	java.util.StringTokenizer;
import	java.util.LinkedList;
import	java.util.List;

/**
 *
 * @author karl
 */
public class Pair<T1,T2> {
	/**Create list of Pair parsing key(=val)?
	 */
	public static List<Pair<String,String> > factory(
		List<String> keyVals, String delim) {
		List<Pair<String,String> > rval = 
			new LinkedList<Pair<String,String> >();
		for (String kv : keyVals) {
			StringTokenizer toks = new StringTokenizer(kv, delim);
			String key = toks.nextToken();
			String val = toks.hasMoreTokens() ? toks.nextToken() : null;
			rval.add(new Pair<String,String>(key,val));
		}
		return rval;
	}

	public static List<Pair<String,String> > factory(List<String> keyVals) {
		return factory(keyVals, "=");
	}

    public Pair(T1 v1, T2 v2) {
        this.v1 = v1;
        this.v2 = v2;
    }
    public T1   v1;
    public T2   v2;
}
