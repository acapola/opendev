package uk.co.nimp;
/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
***/
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.*;

import java.io.FileInputStream;
import java.io.InputStream;

public class CheckSymbols {
    public static void error(Token t, String msg) {
        System.err.printf("line %d:%d %s\n", t.getLine(), t.getCharPositionInLine(),
                          msg);
    }

    public void process(String[] args) throws Exception {
        String inputFile = null;
        if ( args.length>0 ) inputFile = args[0];
        InputStream is = System.in;
        if ( inputFile!=null ) {
            is = new FileInputStream(inputFile);
        }
        ANTLRInputStream input = new ANTLRInputStream(is);
        Verilog2001Lexer lexer = new Verilog2001Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Verilog2001Parser parser = new Verilog2001Parser(tokens);
        parser.setBuildParseTree(true);
        ParseTree tree = parser.source_text();
        // show tree in text form
//        System.out.println(tree.toStringTree(parser));

        ParseTreeWalker walker = new ParseTreeWalker();
        DefPhase def = new DefPhase();
        walker.walk(def, tree);
        // create next phase and feed symbol table info from def to ref phase
        //RefPhase ref = new RefPhase(def.globals, def.scopes);
        //walker.walk(ref, tree);
    }

    public static void main(String[] args) throws Exception {
        new CheckSymbols().process(args);
    }
}
