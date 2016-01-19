package uk.co.nimp.boolean_expression;

/**
 * Created by seb on 1/18/16.
 */
public class AndBooleanOperator extends BinaryBooleanOperator {
    protected AndBooleanOperator() {
        super(false, "and");
    }

    public boolean apply(boolean[] inputs) {
        if (inputs.length != 2) throw new RuntimeException();
        return inputs[0] & inputs[1];
    }
}
