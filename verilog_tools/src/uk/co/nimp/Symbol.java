package uk.co.nimp;

public class Symbol { // A generic programming language symbol

    String name;      // All symbols at least have a name
    Scope scope;      // All symbols know what scope contains them.

    public Symbol(String name) { this.name = name; }
    public String getName() { return name; }

    public String toString() {
        return getName();
    }
}
