package uk.co.nimp;

import org.junit.Test;
import uk.co.nimp.boolean_expression.BooleanExpressionNode;
import uk.co.nimp.boolean_expression.BooleanOperator;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Created by seb on 1/18/16.
 */
public class BooleanExpressionTest {

    @Test
    public void testGet() throws Exception {
        System.out.println(BooleanOperator.get("not").describe());
        System.out.println(BooleanOperator.get("xor").describe());
        System.out.println(BooleanOperator.get("or").describe());
    }

    void checkToExpression(String expression){
        BooleanExpressionNode expr = BooleanExpressionNode.toExpression(expression);
        assert(expr.toString().contentEquals(expression));
        assert(expr.depth()+1==expression.split(" ").length);
    }

    @Test
    public void testToExpression() throws Exception {
        checkToExpression("not 1");
        checkToExpression("and 3 0");
        checkToExpression("nand 1 xor 1 2");
        checkToExpression("or xnor 1 2 0");
        checkToExpression("nor not 2 nand 1 2");
    }

    @Test
    public void testIsLinear() throws Exception {
        assert(true==BooleanExpressionNode.toExpression("not 3").isLinear());
        assert(true==BooleanExpressionNode.toExpression("buf 3").isLinear());
        assert(true==BooleanExpressionNode.toExpression("xor 2 3").isLinear());
        assert(true==BooleanExpressionNode.toExpression("xnor 3 0").isLinear());
        assert(true==BooleanExpressionNode.toExpression("xnor not 1 xor 2 buf 8").isLinear());

        assert(false==BooleanExpressionNode.toExpression("xnor not 1 or 2 buf 8").isLinear());
        assert(false==BooleanExpressionNode.toExpression("xnor not 1 and 2 buf 8").isLinear());
        assert(false==BooleanExpressionNode.toExpression("xnor not 1 nor 2 buf 8").isLinear());
        assert(false==BooleanExpressionNode.toExpression("xnor not 1 nand 2 buf 8").isLinear());
        assert(false==BooleanExpressionNode.toExpression("or not 1 xor 2 buf 8").isLinear());
        assert(false==BooleanExpressionNode.toExpression("and not 1 xor 2 buf 8").isLinear());
        assert(false==BooleanExpressionNode.toExpression("nor not 1 xor 2 buf 8").isLinear());
        assert(false==BooleanExpressionNode.toExpression("nand not 1 xor 2 buf 8").isLinear());
    }

    void checkApply(String exprStr, Function<boolean[], Boolean> model) {
        BooleanExpressionNode expr = BooleanExpressionNode.toExpression(exprStr);
        checkApply(expr, model);
    }
    void checkApply(BooleanExpressionNode expr, Function<boolean[], Boolean> model){
        int width = expr.getMinimumInputWidth();
        int max = (1<<width)-1;
        assert(IntStream.rangeClosed(0, max)
                .mapToObj(i -> Z2.toBooleans(i, 3))
                .map(i -> expr.apply(i) == model.apply(i))
                .allMatch(b -> b == true));
    }

    @Test
    public void testApply() throws Exception {
        checkApply("or and 0 1 or and 0 2 and 1 2",i -> ((i[0] & i[1]) | (i[0] & i[2]) | (i[1] & i[2])));//majority function of 3 inputs
        checkApply("or and 1 2 and 0 not 2",i -> i[2] ? i[1] : i[0]);//mux2
        checkApply("xor xor 0 1 2",i -> i[2] ^ i[1] ^ i[0]);//parity
        checkApply("xnor xor 0 1 2",i -> !i[2] ^ i[1] ^ i[0]);//inverted parity
        checkApply("nand nand 0 nand 0 1 nand 1 nand 0 1",i -> i[1] ^ i[0]);//decomposed xor
        checkApply("nor nor 0 nor 0 1 nor 1 nor 0 1",i -> !i[1] ^ i[0]);//decomposed xnor
        checkApply("not nor nor buf 0 nor buf 0 buf 1 nor buf 1 nor buf 0 buf 1",i -> i[1] ^ i[0]);//decomposed xor with not and buf

    }
}
