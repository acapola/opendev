package uk.co.nimp.boolean_expression;

/**
 * Created by seb on 1/18/16.
 */
public class NandBooleanOperator extends BinaryBooleanOperator {
    protected NandBooleanOperator() {
        super(false, "nand");
    }

    public boolean apply(boolean[] inputs) {
        if (inputs.length != 2) throw new RuntimeException();
        return !(inputs[0] & inputs[1]);
    }
}
