package uk.co.nimp.boolean_expression;

/**
 * Created by seb on 1/18/16.
 */
public interface Operand {
    boolean isInput();
    boolean apply(boolean[] inputs);
    int getMinimumInputWidth();//minimum required input width
    int getNumberOfInputs();//number of actual inputs
    int depth();//number of nodes under this operand

    boolean isLinear();
}
