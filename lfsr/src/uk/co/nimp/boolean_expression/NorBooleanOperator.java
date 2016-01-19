package uk.co.nimp.boolean_expression;

/**
 * Created by seb on 1/18/16.
 */
public class NorBooleanOperator extends BinaryBooleanOperator {
    protected NorBooleanOperator() {
        super(false, "nor");
    }

    public boolean apply(boolean[] inputs) {
        if (inputs.length != 2) throw new RuntimeException();
        return !(inputs[0] | inputs[1]);
    }
}
