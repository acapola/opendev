package uk.co.nimp.boolean_expression;

/**
 * Created by seb on 1/19/16.
 */
public class BufBooleanOperator extends BooleanOperator {
    protected BufBooleanOperator() {
        super(true, "buf", 1);
    }

    @Override
    public boolean apply(boolean[] inputs) {
        if (inputs.length != 1) throw new RuntimeException();
        return inputs[0];
    }
}
