package uk.co.nimp.boolean_expression;

/**
 * Created by seb on 1/18/16.
 */
public abstract class BinaryBooleanOperator extends BooleanOperator {
    protected BinaryBooleanOperator(boolean linear, String name) {
        super(linear, name, 2);
    }

    public boolean apply(boolean a, boolean b) {
        return apply(new boolean[]{a, b});
    }
}
