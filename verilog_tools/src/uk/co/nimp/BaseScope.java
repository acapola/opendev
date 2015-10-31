package uk.co.nimp;
/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
***/
import org.antlr.v4.runtime.Token;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BaseScope implements Scope {
    static HashMap<Object,String> tokenToOutId = new HashMap<Object, String>();
    HashMap<String,Object> idToToken;
    long idCnt=0;
    @Override
    public void defineVar(Token nameToken) {
        String id = nameToken.getText();
        if(id.isEmpty()) return;
        if(null!=resolve(id)) return;
        VariableSymbol var = new VariableSymbol(id);
        if(!tokenToOutId.containsKey(nameToken)) {
            tokenToOutId.put(nameToken, "id" + idCnt++);
            idToToken.put(id, nameToken);
        }
        define(var); // Define symbol in current scope
    }
    @Override
    public void checkIdentifier(Token token){
        String id = token.getText();
        if(idToToken.containsKey(id)){
            Object declarationToken = idToToken.get(id);
            String outId = tokenToOutId.get(declarationToken);
            tokenToOutId.put(token,outId);
        }
    }

    Scope enclosingScope; // null if global (outermost) scope
    Map<String, Symbol> symbols = new LinkedHashMap<String, Symbol>();

    public BaseScope(Scope enclosingScope) {
        //tokenToOutId = new HashMap<Object, java.lang.String>();
        idToToken = new HashMap<java.lang.String, Object>();

        this.enclosingScope = enclosingScope;
    }

    public Symbol resolve(String name) {
        Symbol s = symbols.get(name);
        if ( s!=null ) return s;
        // if not here, check any enclosing scope
        if ( enclosingScope != null ) return enclosingScope.resolve(name);
        return null; // not found
    }

    public void define(Symbol sym) {
        symbols.put(sym.getName(), sym);
        sym.setScope(this); // track the scope in each symbol
    }

    public Scope getEnclosingScope() { return enclosingScope; }

    public String toString() { return getScopeName()+":"+symbols.keySet().toString(); }
}
