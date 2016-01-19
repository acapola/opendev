package uk.co.nimp.boolean_expression;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by seb on 1/18/16.
 */
public class BooleanExpressionNode implements Operand {
    final BooleanOperator operator;
    final List<Operand> operands;

    public BooleanExpressionNode(BooleanOperator operator, List<Operand> operands) {
        this.operator = operator;
        this.operands = operands;
    }
    public static BooleanExpressionNode toExpression(String tokens){
        return toExpression(0,tokens.split(" "));
    }
    public static BooleanExpressionNode toExpression(String ... tokens){
        return toExpression(0,tokens);
    }
    static BooleanExpressionNode toExpression (int offset, String ... tokens){
        String token = tokens[offset];
        BooleanOperator op = BooleanOperator.get(token);
        List<Operand> operands = new ArrayList<>();
        int operandsDepth=1;//1 for the operator we just consumed
        for(int j=0;j<op.nOperands;j++) {
            int operandOffset = offset+operandsDepth+j;
            token = tokens[operandOffset];
            if (BooleanOperator.isBooleanOperator(token)) {
                Operand operand = toExpression(operandOffset, tokens);
                operands.add(operand);
                operandsDepth+=operand.depth();
            } else {
                int inputPosition = Integer.parseInt(token);
                operands.add(new BooleanExpressionInputNode(inputPosition));
                if((0==offset) && (op.nOperands-1==j) && (operandOffset!=tokens.length-1)) throw new RuntimeException("Not all tokens have been consumed");
            }
        }
        return new BooleanExpressionNode(op,operands);
    }

    public static class BooleanExpressionInputNode implements Operand {
        final int position;

        public BooleanExpressionInputNode(int position) {
            this.position = position;
        }

        @Override
        public boolean isInput() {
            return true;
        }

        @Override
        public boolean apply(boolean[] inputs) {
            return inputs[position];
        }

        @Override
        public int getMinimumInputWidth() {
            return position+1;
        }

        @Override
        public int getNumberOfInputs() {
            return 1;
        }

        @Override
        public int depth() {
            return 0;
        }

        @Override
        public boolean isLinear() {
            return true;
        }

        @Override
        public String toString() {
            return Integer.toString(position);
        }
    }


    @Override
    public boolean isInput() {
        return false;
    }

    @Override
    public boolean apply(boolean[] inputs) {
        List<Boolean> operandsValue = operands.stream().map(o -> o.apply(inputs)).collect(Collectors.toList());
        return operator.apply(operandsValue);
    }

    @Override
    public int getMinimumInputWidth() {
        return operands.stream().map(o -> o.getMinimumInputWidth()).max(Comparator.<Integer>naturalOrder()).get();
    }

    @Override
    public int getNumberOfInputs() {
        return operands.stream().filter(o -> o.isInput()).map(o -> o.getNumberOfInputs() - 1).collect(Collectors.toSet()).size();
    }

    @Override
    public int depth() {
        return operands.stream().mapToInt(o -> o.depth() + 1).sum();
    }

    @Override
    public String toString() {
        String out = operator+" ";
        out+=operands.stream().map(o -> o.toString()).collect(Collectors.joining(" "));
        return out;
    }

    @Override
    public boolean isLinear(){
        if(!operator.isLinear()) return false;
        return operands.stream().allMatch(o -> o.isLinear());
    }


}
