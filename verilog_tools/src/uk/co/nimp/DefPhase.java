package uk.co.nimp;
/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
***/
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import static uk.co.nimp.Verilog2001Parser.*;
public class DefPhase extends Verilog2001BaseListener {
    ParseTreeProperty<Scope> scopes = new ParseTreeProperty<Scope>();
    FileScope modules;
    Scope currentScope; // define symbols in this scope
    public void enterSource_text(Verilog2001Parser.Source_textContext ctx) {
        modules = new FileScope(null);
        currentScope = modules;
    }
	
    public void exitSource_text(Verilog2001Parser.Source_textContext ctx) {
        System.out.println(modules);
    }

    public void enterModule_declaration(Verilog2001Parser.Module_declarationContext ctx) {
        String name = ctx.module_identifier().identifier().Simple_identifier().getSymbol().getText();

        // push new scope by making new one that points to enclosing scope
        ModuleSymbol module = new ModuleSymbol(name, currentScope);
        currentScope.define(module); // Define function in current scope
        saveScope(ctx, module);      // Push: set function's parent to current
        currentScope = module;       // Current scope is now function scope
    }

    void saveScope(ParserRuleContext ctx, Scope s) { scopes.put(ctx, s); }

    public void exitModule_declaration(Verilog2001Parser.Module_declarationContext ctx) {
        System.out.println(currentScope);
        currentScope = currentScope.getEnclosingScope(); // pop scope
    }



    public void exitNet_declaration(Verilog2001Parser.Net_declarationContext ctx) {
        List_of_net_identifiersContext netIdCtx = ctx.list_of_net_identifiers();
        if(null!=netIdCtx) {
            for (Net_identifierContext net : netIdCtx.net_identifier()) {
                Token tok = net.identifier().Simple_identifier().getSymbol();
                if (null == tok) {
                    tok = net.identifier().Escaped_identifier().getSymbol();
                    if (null == tok) throw new RuntimeException("net=" + net);
                }
                defineVar(tok);
            }
        }else{
            List_of_net_decl_assignmentsContext netAssignCtx = ctx.list_of_net_decl_assignments();
            if(null!=netAssignCtx) {
                for (Net_decl_assignmentContext net : netAssignCtx.net_decl_assignment()) {
                    Token tok = net.net_identifier().identifier().Simple_identifier().getSymbol();
                    if (null == tok) {
                        tok = net.net_identifier().identifier().Escaped_identifier().getSymbol();
                        if (null == tok) throw new RuntimeException("net=" + net);
                    }
                    defineVar(tok);
                }
            }else{
                throw new RuntimeException("netAssignCtx=" + netAssignCtx);
            }
        }
    }
    static Object test;
    void defineVar(Token nameToken) {
        VariableSymbol var = new VariableSymbol(nameToken.getText());
        //System.out.println("token index: "+nameToken.getTokenIndex()+" --> "+var);
        if("tmp".equals(nameToken.getText())) {
            test = nameToken;
            System.out.println("token object: " + test + " --> " + var);
        }
        currentScope.define(var); // Define symbol in current scope
    }


}
