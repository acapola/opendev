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
package v2k.parser;
import  v2k.message.ExceptionBase;
import  v2k.util.Utils;
import  v2k.util.Pair;
import  java.util.*;
import  java.io.*;
import  antlr.*;

/**
 *
 * @author karl
 */
public class Preproc {
	public static Preproc getTheOne() {
		return stTheOne;
	}

    public Preproc() {
        addSearchPath(".");
        assert stTheOne == null;
        stTheOne = this;
        VlogppLexer.setPP(getTheOne());
    }
    
    /**Not really usable, since we have static init using above constructor.*/
    public Preproc(List<String> inclDirs, List<Pair<String,String> > defs) {
        this();
        addSearchPaths(inclDirs);
        addDefines(defs);
    }

    private static final Location stCmdLineLoc = new Location("<cmdline>", 0);
    
    Location getLexerLoc() {
        return (null != m_preproc) ? m_preproc.getLocation() : stCmdLineLoc;
    }
    void setPreproc(IPreproc pp) {
        m_preproc = pp;
    }
    
    public void addDefines(List<Pair<String,String> > keyVals) {
        for (Pair<String,String> kv : keyVals) {
            addDefine(kv.v1, kv.v2);
        }
    }
    public void addSearchPaths(List<String> paths) {
        for (String p : paths) {
            addSearchPath(p);
        }
    }
    public boolean addSearchPath(String dir) {
        boolean rval = false;
        File d = new File(dir);
        if (d.isDirectory()) {
            if (Parser.stUseAbsPaths) {
                d = d.getAbsoluteFile();
            }
            if (false == m_searchPath.contains(d)) {
                m_searchPath.add(d);
            }
            rval = true;
        }
        return rval;
    }
    public void uponEOF() throws TokenStreamException, CharStreamException {
        m_preproc.pop();
    }
    /**
     * Get include files, in no particular order.
     */
    public Map<String,Integer> getIncludeFiles() {
        return m_includes;
    }

 /*Allow to redefine
    public boolean okToDefine(String key) throws RecognitionException {
        boolean rval = !passToken() || isDefined(key);
        if (false == rval) {
            throw new ParseException('E', "MACRO-1", key);
        }
        return rval;
    }
  */
    public boolean okToDefine(String key) throws RecognitionException {
        boolean rval = !passToken() || isDefined(key);
        if (false == rval) {
            Message.message(getLexerLoc(), 'E', "MACRO-1", key);
        }
        return true;    //allow to redefine
    }
   
    // Return true if tok found; else tok==return val
    public boolean isDefined(String key) {
        return m_defines.containsKey(key);
    }
    public void expandMacro(String key) throws TokenStreamRetryException {
        if (passToken()) {
            if (false == isDefined(key)) {
                return;
            }
            String v = m_defines.get(key).getVal();
            m_preproc.push(v);
        }
    }
    public void expandMacro(String key, List<String> parms) 
        throws TokenStreamRetryException {
        if (passToken()) {
            assert(isDefined(key));
            String v = m_defines.get(key).getVal(parms);
            m_preproc.push(v);
        }
    }
    public boolean hasParams(String key) {
        boolean has = m_defines.containsKey(key);
        if (has) {
            has = m_defines.get(key).hasParams();
        }
        return has;
    }
    private static boolean isRedefined(MacroVal has, MacroVal check) {
        boolean rval = false;
        if (null != has) {
            switch (Parser.stRedefinedCheck) {
                case 1:
                    rval = !has.getVal().equals(check.getVal());
                    break;
                default:
                    rval = !has.getWhereDefn().equals(check.getWhereDefn());
            }
        }
        return rval;
    }
    /**
     * Process macro value before saving to remove comments.
     * @param val macro value
     * @return processes macro value
     */
    private static String process(String val) {
        //remove line comments
        int start, end;
        while (true) {
            start = val.indexOf("//");
            if (0 > start) {
                break;  //while
            }
            String ns = val.substring(0, start);
            end = val.indexOf("\\", start);
            if (0 < end) {
                ns += val.substring(end);
            }
            val = ns;
        }
        val = val.replace("\\\n"," ").replace("\\\r"," ")
            .replace('\n', ' ').replace('\r',' ').trim();
        return val;
    }
    
    public boolean addDefine(String key, String val) {
        // Strip location (if present)
        Location loc = null;
        int ix = key.indexOf("@@"); //fname:lnum@@...
        if (0 < ix) {
            int ix2 = key.indexOf(":");
            if (0 < ix2) {
                String fn = key.substring(0, ix2);
                int ln = Integer.parseInt(key.substring(ix2+1, ix));
                loc = new Location(fn, ln);
                key = key.substring(ix+2);
            }
        }
        MacroVal has = m_defines.get(key);
        if (passToken()) {
            if (null == val) {
                val = "";
            }
            val = process(val);
            MacroVal m = new MacroVal(val, loc);
            if (isRedefined(has, m)) {
                Message.message(m.getWhereDefn(), 'W', "MACRO-3", key, 
                                has.getWhereDefn());
            } else {
                m_defines.put(key, m);
            }
        }
        return has != null;
    }
    public boolean addDefine(String key, List<String> parms, String val) {
        MacroVal has = m_defines.get(key);
        if (passToken()) {
           val = process(val);
           m_defines.put(key, new MacroVal(parms, val));
            MacroVal m = new MacroVal(parms, val);
            if (isRedefined(has, m)) {
                Message.message(m.getWhereDefn(), 'W', "MACRO-3", key, 
                                has.getWhereDefn());
            } else {
                m_defines.put(key, m);
            }
        }
        return has != null;
    }

    public void includeFile(String fn) /*"fn"*/ 
            throws TokenStreamRetryException,
                   RecognitionException {
        if (false == passToken()) {
            return;
        }
        String tfn = fn.replace('"', ' ').trim();
        File ifile = Utils.findFile(m_searchPath, tfn, Parser.stUseAbsPaths);
        if (null == ifile) {
            throw new ParseException('E', "INCL-2", tfn);
        }
        tfn = Parser.stUseAbsPaths ? ifile.getAbsolutePath() : ifile.getPath();
        if (false == m_includes.containsKey(tfn)) {
            m_includes.put(tfn, new Integer(1));
        } else {
            m_includes.put(tfn, m_includes.get(tfn)+1);
        }
        m_preproc.push(ifile);
    }
    /**Alter next state if previous is block*/
    private IfdefState next(IfdefState dflt) {
        IfdefState ns = dflt;
        if (!m_ifdefStack.empty() && 
                (IfdefState.eDone != m_ifdefStack.peek())) {
            ns = IfdefState.eBlockDone;
        }
        return ns;
    }
    public void undef(String key) {
        if (null == m_defines.remove(key)) {
            Message.message(getLexerLoc(), 'W', "MACRO-2", key);
        }
    }
    public void ticIfdef(String key) {
        boolean rval = isDefined(key);
        m_ifdefStack.push(next(rval ? IfdefState.eDone 
                : IfdefState.eNotDone));
    }
    public void ticIfndef(String key) {
        boolean rval = !isDefined(key);
        m_ifdefStack.push(next(rval ? IfdefState.eDone 
                : IfdefState.eNotDone));
    }
    public void ticElse() {
        IfdefState was = m_ifdefStack.pop();
        m_ifdefStack.push(next((was==IfdefState.eDone) 
                ? IfdefState.eBlockDone : IfdefState.eDone));
    }
    public void ticElsif(String key) {
        IfdefState was = m_ifdefStack.pop();
        IfdefState nxt = (was==IfdefState.eDone) ? IfdefState.eBlockDone
                : (isDefined(key) ? IfdefState.eDone : IfdefState.eNotDone);
        m_ifdefStack.push(next(nxt));
    }
    public void ticEndif() {
        m_ifdefStack.pop();
    }
    /**Return true to parser if can pass token.
     * This way we can block tokens while processing ifdef blocks.
     */
    public boolean passToken() {
        return (m_ifdefStack.isEmpty()) ? true : m_ifdefStack.peek().pass();
    }

    static class ParseException extends ExceptionBase {
        public ParseException(char severity, String code, Object ... args) {
            super(severity, code, args);
        }
    }
    
    Stack<LexState> getStack() {
        return m_lexStack;
    }

    static interface IPreproc {
        public void push(File f) throws TokenStreamRetryException;
        public void push(String txt) throws TokenStreamRetryException;
        public void pop() throws TokenStreamRetryException;
        public Location getLocation();
    }
    
    private static enum IfdefState {
        eDone, eNotDone, eBlockDone;
                 
        public boolean pass() {
            return (this==eDone);
        }
    }

    private IPreproc    m_preproc;
    
    static class LexState {
        LexState(LexerSharedInputState lis, boolean doLine) {
            m_state = lis;
            m_doLine = doLine;
        }
        final LexerSharedInputState   m_state;
        final boolean                 m_doLine;
    }
    
    /**Stack of lexer states*/
    private Stack<LexState> m_lexStack = new Stack<LexState>();

    /**Stack of ifdef states*/
    private Stack<IfdefState> m_ifdefStack = new Stack<IfdefState>();
    private Map<String,MacroVal> m_defines = new HashMap<String,MacroVal>();
    private List<File>  m_searchPath = new LinkedList<File>();
    /**
     * Keep track of any include files processed (in no particular order).
     */
    private Map<String,Integer> m_includes = new HashMap<String,Integer>();

    private static Preproc  stTheOne = new Preproc();
}
