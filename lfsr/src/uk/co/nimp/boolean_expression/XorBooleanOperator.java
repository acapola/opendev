package uk.co.nimp.boolean_expression;

/**
 * Created by seb on 1/18/16.
 */
public class XorBooleanOperator extends BinaryBooleanOperator {
    protected XorBooleanOperator() {
        super(true, "xor");
    }

    public boolean apply(boolean[] inputs) {
        if (inputs.length != 2) throw new RuntimeException();
        return inputs[0] ^ inputs[1];
    }
}
