package uk.co.nimp;

public class SymbolClass implements Symbol { // A generic programming language symbol

    String name;      // All symbols at least have a name
    Scope scope;      // All symbols know what scope contains them.

    public SymbolClass(String name) { this.name = name; }
    @Override
    public String getName() { return name; }

    @Override
    public void setScope(Scope scope) {
        this.scope=scope;
    }

    public String toString() {
        return getName();
    }
}
