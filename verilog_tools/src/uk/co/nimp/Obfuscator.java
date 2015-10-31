package uk.co.nimp;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static uk.co.nimp.Verilog2001Parser.*;
public class Obfuscator extends Verilog2001BaseListener {
    ParseTreeProperty<Scope> scopes = new ParseTreeProperty<Scope>();
    FileScope modules;
    Scope currentScope; // define symbols in this scope
    enum Phase {DEFINE_IDS, REPLACE_IDS}
    Phase phase;
    public void enterSource_text(Verilog2001Parser.Source_textContext ctx) {

        if(phase==Phase.DEFINE_IDS) {
            modules = new FileScope(null);
            currentScope = modules;
        }else{
            //modules = currentScope;
        }

    }

    public void exitSource_text(Verilog2001Parser.Source_textContext ctx) {
        System.out.println(modules);
    }

    public void enterModule_declaration(Verilog2001Parser.Module_declarationContext ctx) {
        String name = ctx.module_identifier().identifier().Simple_identifier().getSymbol().getText();
        if(phase==Phase.DEFINE_IDS) {
            // push new scope by making new one that points to enclosing scope
            ModuleSymbol module = new ModuleSymbol(name, currentScope);
            currentScope.define(module); // Define function in current scope
            saveScope(ctx, module);      // Push: set function's parent to current
            currentScope = module;       // Current scope is now function scope
        } else {
            currentScope = scopes.get(ctx);
        }
    }

    void saveScope(ParserRuleContext ctx, Scope s) { scopes.put(ctx, s); }

    public void exitModule_declaration(Verilog2001Parser.Module_declarationContext ctx) {
        System.out.println(currentScope);
        currentScope = currentScope.getEnclosingScope(); // pop scope
    }
/*
    public void exitNet_declaration(Verilog2001Parser.Net_declarationContext ctx) {
        if(phase!=Phase.DEFINE_IDS) return;
        Verilog2001Parser.List_of_net_identifiersContext netIdCtx = ctx.list_of_net_identifiers();
        if(null!=netIdCtx) {
            for (Verilog2001Parser.Net_identifierContext net : netIdCtx.net_identifier()) {
                Token tok = net.identifier().Simple_identifier().getSymbol();
                if (null == tok) {
                    tok = net.identifier().Escaped_identifier().getSymbol();
                    if (null == tok) throw new RuntimeException("net=" + net);
                }
                currentScope.defineVar(tok);
            }
        }else{
            Verilog2001Parser.List_of_net_decl_assignmentsContext netAssignCtx = ctx.list_of_net_decl_assignments();
            if(null!=netAssignCtx) {
                for (Verilog2001Parser.Net_decl_assignmentContext net : netAssignCtx.net_decl_assignment()) {
                    Token tok = net.net_identifier().identifier().Simple_identifier().getSymbol();
                    if (null == tok) {
                        tok = net.net_identifier().identifier().Escaped_identifier().getSymbol();
                        if (null == tok) throw new RuntimeException("net=" + net);
                    }
                    currentScope.defineVar(tok);
                }
            }else{
                throw new RuntimeException("netAssignCtx=" + netAssignCtx);
            }
        }
    }
*/
    /*@Override
    public void exitReg_declaration(Reg_declarationContext ctx) {
        //super.exitReg_declaration(ctx);
        if(phase!=Phase.DEFINE_IDS) return;
        Verilog2001Parser.List_of_variable_identifiersContext regIdCtx = ctx.list_of_variable_identifiers();
        for (Verilog2001Parser.Variable_identifierContext net : regIdCtx.) {
            Token tok = net.identifier().Simple_identifier().getSymbol();
            if (null == tok) {
                tok = net.identifier().Escaped_identifier().getSymbol();
                if (null == tok) throw new RuntimeException("net=" + net);
            }
            currentScope.defineVar(tok);
        }
    }*/
/*
    @Override
    public void exitEscaped_hierarchical_identifier(Escaped_hierarchical_identifierContext ctx) {
        super.exitEscaped_hierarchical_identifier(ctx);
    }
*/
    void checkIdentifier(Simple_hierarchical_identifierContext ctx){
        //if(phase!=Phase.REPLACE_IDS) return;
        Token tok;
        Simple_hierarchical_branchContext c =ctx.simple_hierarchical_branch();
        List<TerminalNode> nodes;
        if(null!=c){
            nodes = c.Simple_identifier();
        }else {
            nodes = new ArrayList<TerminalNode>();
            nodes.add(ctx.Escaped_identifier());
        }
        for(TerminalNode node:nodes) {
            tok = node.getSymbol();
            if(phase==Phase.DEFINE_IDS) currentScope.defineVar(tok);
            else currentScope.checkIdentifier(tok);
        }
    }
    @Override
    public void exitHierarchical_identifier(Hierarchical_identifierContext ctx) {
        checkIdentifier(ctx.simple_hierarchical_identifier());
        //if(phase!=Phase.REPLACE_IDS) return;
        //Token tok = ctx.simple_hierarchical_identifier().Escaped_identifier().getSymbol();
        //currentScope.checkIdentifier(tok);
        //super.exitHierarchical_identifier(ctx);
    }
/*
    @Override
    public void exitHierarchical_net_identifier(Hierarchical_net_identifierContext ctx) {
        super.exitHierarchical_net_identifier(ctx);
    }

    @Override
    public void exitHierarchical_variable_identifier(Hierarchical_variable_identifierContext ctx) {
        super.exitHierarchical_variable_identifier(ctx);
    }
*/
    @Override
    public void exitIdentifier(IdentifierContext ctx) {
        Token tok = ctx.Simple_identifier().getSymbol();
        if(null==tok){
            tok=ctx.Escaped_identifier().getSymbol();
        }
        if(phase==Phase.DEFINE_IDS) currentScope.defineVar(tok);
        else currentScope.checkIdentifier(tok);
    }
/*
    @Override
    public void exitNet_identifier(Net_identifierContext ctx) {
        super.exitNet_identifier(ctx);
    }

    @Override
    public void exitSimple_hierarchical_identifier(Simple_hierarchical_identifierContext ctx) {
        checkIdentifier(ctx);
        //super.exitSimple_hierarchical_identifier(ctx);
    }

    @Override
    public void exitVariable_identifier(Variable_identifierContext ctx) {
        if(phase!=Phase.REPLACE_IDS) return;
        TerminalNode node = ctx.identifier().Simple_identifier();
        if(null==node) node = ctx.identifier().Escaped_identifier();
        currentScope.checkIdentifier(node.getSymbol());
        //super.exitVariable_identifier(ctx);
    }
    */
    void obfuscate(String filePath) throws IOException {
        InputStream is = new FileInputStream(filePath);
        ANTLRInputStream input = new ANTLRInputStream(is);
        Verilog2001Lexer lexer = new Verilog2001Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Verilog2001Parser parser = new Verilog2001Parser(tokens);
        parser.setBuildParseTree(true);
        ParseTree tree = parser.source_text();
        phase = Phase.DEFINE_IDS;
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(this, tree);
        phase = Phase.REPLACE_IDS;
        walker.walk(this,tree);
        printTree(tree);
    }

    void printTree(ParseTree t){
        if(null != t ){
            int children = t.getChildCount();
            for (int i = 0; i < children; i++ ){
                ParseTree child = t.getChild(i);
                if(0==child.getChildCount()) {
                    String tokenText = child.getText();
                    if("<EOF>".compareTo(tokenText)!=0) {
                        Object o = child.getPayload();
                        if(BaseScope.tokenToOutId.containsKey(o))
                            tokenText = BaseScope.tokenToOutId.get(o);
                        System.out.print(tokenText + " ");
                    }
                }else {
                    printTree(child);
                }
            }
        }
    }


    public static void main(String[] args) throws IOException {
        Obfuscator o=new Obfuscator();
        o.obfuscate(args[0]);

    }
}
