package uk.co.nimp.boolean_expression;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by seb on 1/18/16.
 */
public abstract class BooleanOperator {
    static Map<String, BooleanOperator> namesToOp = new HashMap<>();
    static BooleanOperator intToOp[] = new BooleanOperator[8];
    static int opCnt = 0;
    static boolean initialized;
    final boolean unary;
    final boolean linear;
    final String name;
    final int nOperands;
    final int id;

    protected BooleanOperator(boolean linear, String name, int nOperands) {
        this.unary = nOperands == 1;
        this.linear = linear;
        this.name = name;
        this.nOperands = nOperands;
        id = opCnt++;
    }

    protected static void addOp(BooleanOperator op) {
        namesToOp.put(op.name, op);
        intToOp[op.id] = op;
    }

    public static int getOpCnt(){
        if (!initialized) init();
        return opCnt;
    }

    protected static void init() {
        addOp(new BufBooleanOperator());
        addOp(new NotBooleanOperator());
        addOp(new XorBooleanOperator());
        addOp(new XnorBooleanOperator());
        addOp(new AndBooleanOperator());
        addOp(new NandBooleanOperator());
        addOp(new OrBooleanOperator());
        addOp(new NorBooleanOperator());
        initialized = true;
    }

    public static BooleanOperator get(String name) {
        if (!initialized) init();
        BooleanOperator existing = namesToOp.get(name);
        return existing;
    }

    public static BooleanOperator get(int id) {
        if (!initialized) init();
        BooleanOperator existing = intToOp[id];
        return existing;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getnOperands() {
        return nOperands;
    }

    public boolean isLinear() {
        return linear;
    }

    public boolean isUnary() {
        return unary;
    }

    public String describe() {
        return "BooleanOperator{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", unary=" + unary +
                ", linear=" + linear +
                ", nOperands=" + nOperands +
                '}';
    }

    public abstract boolean apply(boolean[] inputs);
    public boolean apply(List<Boolean> inputs){
        boolean[] b = new boolean[inputs.size()];
        for(int i=0;i<b.length;i++) b[i] = inputs.get(i);
        return apply(b);
    }

    public static boolean isBooleanOperator(String token) {
        return namesToOp.containsKey(token);
    }
}
