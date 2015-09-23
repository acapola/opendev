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

import java.util.List;

import static uk.co.nimp.Verilog2001Parser.*;
public class DefPhase extends Verilog2001BaseListener {
    ParseTreeProperty<Scope> scopes = new ParseTreeProperty<Scope>();
    GlobalScope globals;
    Scope currentScope; // define symbols in this scope
    public void enterSource_text(Verilog2001Parser.Source_textContext ctx) {
        globals = new GlobalScope(null);
        currentScope = globals;
    }
	
    public void exitSource_text(Verilog2001Parser.Source_textContext ctx) {
        System.out.println(globals);
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
        /*for(Verilog2001Parser net:ctx.list_of_net_identifiers().net_identifier()){
            defineVar(net.
        }*/
        for(Net_identifierContext net : ctx.list_of_net_identifiers().net_identifier()){
            defineVar(net.identifier().Simple_identifier().getSymbol());
        }
        //System.out.println(ctx.list_of_net_identifiers().net_identifier());
    }

    void defineVar(Token nameToken) {
        VariableSymbol var = new VariableSymbol(nameToken.getText());
        currentScope.define(var); // Define symbol in current scope
    }
}
