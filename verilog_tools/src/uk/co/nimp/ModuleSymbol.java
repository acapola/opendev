package uk.co.nimp;
/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
***/
import java.util.LinkedHashMap;
import java.util.Map;

public class ModuleSymbol extends BaseScope implements Symbol {
    Map<String, Symbol> arguments = new LinkedHashMap<String, Symbol>();
    String name;
    public ModuleSymbol(String name,Scope enclosingScope) {
        super(enclosingScope);
        this.name = name;
    }

    public String getScopeName() { return name; }

    public String toString() { return "module "+super.toString()+":"+arguments.values(); }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setScope(Scope scope) {
        enclosingScope=scope;
    }
}
