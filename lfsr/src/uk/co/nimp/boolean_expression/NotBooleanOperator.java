package uk.co.nimp.boolean_expression;

/**
 * Created by seb on 1/18/16.
 */
public class NotBooleanOperator extends BooleanOperator {
    protected NotBooleanOperator() {
        super(true, "not", 1);
    }

    @Override
    public boolean apply(boolean[] inputs) {
        if (inputs.length != 1) throw new RuntimeException();
        return !inputs[0];
    }
}
