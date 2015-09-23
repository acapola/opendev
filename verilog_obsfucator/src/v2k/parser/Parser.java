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
import  v2k.message.ExceptionBase;
import  v2k.message.MessageMgr;
import  v2k.util.Utils;
import static v2k.util.Utils.getPropertyAsBool;
import  v2k.util.Pair;
import  v2k.parser.tree.*;
import  java.util.*;
import  antlr.TokenStreamException;

/**
 *
 * @author karl
 */
public class Parser {
    static class Tree extends ParseTree {
/**TODO
        @Override
        public void moduleDeclaration(ModuleDeclaration mdecl) {
            String fnm = mdecl.getModName() + ".v2k.xml";
            MessageMgr.message('I', "FILE-4", fnm);
            new ssi.XmlOutStream(fnm, mdecl);
        }
 */
    }
    public Parser() {
        this(getPropertyAsBool("USEPT") 
            ? new Tree() : new v2k.parser.tree.basic.ParseTree());
    }
    
    public Parser(ASTreeBase tree) {
        stPP = Preproc.getTheOne();
        m_tree = tree;
        setTheOne(this);
        addSearchPath(".");
    }

    private static Preproc stPP;
    
    static class ParseException extends ExceptionBase {
        public ParseException(char severity, String code, Object ... args) {
            super(severity, code, args);
        }
    }

    public void parse(String argv[]) {
        for (String ai : argv) {
            try {
                m_lexer = new Lexer(ai);
                m_parser = new VlogParser(m_lexer);
                if (false == ai.equals("<cmdline>")) {
                    Message.message('I', "FILE-3", ai);
                }
                m_parser.source_text();
            } catch (TokenStreamException ex) {
                if (null != ex.getMessage()) {
                    Message.message(ex);
                }
            } catch (Exception ex) {
                if (false == ExceptionBase.class.isInstance(ex)) {
                    Utils.abnormalExit(ex);
                }
            }
        }
    }
    public void parse(List<String> argv, List<String> inclDirs, 
            List<Pair<String,String> > defs) {
        addSearchPaths(inclDirs);
        addDefines(defs);
        parse(argv.toArray(new String[0]));
    }
    public void addDefines(List<Pair<String,String> > keyVals) {
        stPP.addDefines(keyVals);
    }
    public void addSearchPaths(List<String> paths) {
        stPP.addSearchPaths(paths);
    }
    private static void setTheOne(Parser x) {
        assert(null == stTheOne);
        stTheOne = x;
    }
    public static Parser getTheOne() {
        return stTheOne;
    }
    public static ASTreeBase getTheTree() {
        return getTheOne().m_tree;
    }
    public ASTreeBase getTree() {
        return m_tree;
    }
    public String getFilename() {
        return (null == m_lexer) ? "<cmdline>" : m_lexer.getFilename();
    }
    public int getLine() {
        return (null == m_lexer) ? 0 : m_lexer.getLine();
    }
    public boolean addSearchPath(String dir) {
        return stPP.addSearchPath(dir);
    }
    /**
     * Get include files, in no particular order.
     */
    public Map<String,Integer> getIncludeFiles() {
        return stPP.getIncludeFiles();
    }
    public static void setAbsPath(boolean v) {
        stUseAbsPaths = v;
    }
    public static void setDumpPP(boolean v) {
        stDumpPP = v;
    }
    public static void setKeepComments(boolean v) {
        stShowComments = v;
    }
    public static void setRedefinedCheck(int v) {
        stRedefinedCheck = v;
    }
    private VlogParser  m_parser;
    private Lexer       m_lexer;
    private ASTreeBase m_tree;
    private static Parser stTheOne = null;
    static final int stBufSz = 1<<20;
    /**Use absolute paths or not.*/
    static boolean stUseAbsPaths = false;
    /**Dump preprocessed files to file.E*/
    static boolean stDumpPP = false;
    /**Show define definitions in preprocessed output.*/
    static boolean stDumpDefn = false;
    /**Show comments during pp dump.*/
    static boolean stShowComments = true;
    /**Type of macro redefinition check: 1=value difference; 2=file location.*/
    static int stRedefinedCheck = 2;
 }
