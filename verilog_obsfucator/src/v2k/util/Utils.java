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
import  java.util.*;
import  java.io.*;

/**
 *
 * @author karl
 */
public class Utils {

    /** Creates a new instance of Utils */
    public static String stripDoubleQuotes (final String s) {
        int len = s.length();
        String ns = s.substring(1, len-1);
        return ns;
    }

    public static void invariant(boolean c) {
        if (false == c) {
            Thread.dumpStack();
            System.exit(1);
        }
    }

    private final static String stDOT = ".";

    public static String getToolRoot() {
        String root = System.getProperty("tool.root");
        if (null == root) {
            root = stDOT;
        }
        return root;
    }

    public static void fatal(Exception ex) {
        PrintStream err = System.err;
        err.print(ex.getMessage());
        ex.printStackTrace(err);
        System.exit(1);
    }

    /**Lookup property value.
     *
     * @param prop  property name
     * @return true if property exists and set to "true" or else false.
     */
    public static boolean getPropertyAsBool(String prop) {
        String pv = System.getProperty(prop);
        boolean v = (null == pv) ? false : Boolean.parseBoolean(pv);
        return v;
    }

    public static void abnormalExit(Exception ex) {
        System.err.println(ex.getMessage());
        ex.printStackTrace(System.err);
        System.exit(1);
    }

    public static List<String> arrayToList(String s[]) {
        List<String> rval = new LinkedList<String>();
        for (String i : s) {
            rval.add(i);
        }
        return rval;
    }

    public static int streamCopy(BufferedInputStream from, BufferedOutputStream to) throws IOException {
        final int bufSz = 2048;
        byte buf[] = new byte[bufSz];
        int tlCnt = 0, cnt = 0;
        while (0 <= (cnt = from.read(buf, 0, bufSz))) {
            tlCnt += cnt;
            to.write(buf, 0, cnt);
        }
        to.flush();
        return tlCnt;
    }

    public static String nl() {
        return m_nl;
    }

    public static File findFile(List<File> dirs, String fname, boolean getCanon)
    {
        File rval = null;
        for (File d : dirs)
        {
            File f = new File(d, fname);
            if (f.exists())
            {
                try {
                    rval = (getCanon) ? f.getCanonicalFile() : f;
                    break; //for
                } catch (Exception ex) {
                    //do nothing
                }
            }
        }
        return rval;
    }

    public static File findFile(List<File> dirs, String fname)
    {
        return findFile(dirs, fname, true);
    }

    /** Return a null x as an empty collection. */
    public static <T> T asEmpty(T x, T empty) {
        return (null != x) ? x : empty;
    }

    private final static String m_nl = System.getProperty("line.separator");
}
