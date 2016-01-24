package uk.co.nimp;

import uk.co.nimp.boolean_expression.BooleanExpressionNode;
import uk.co.nimp.boolean_expression.BooleanOperator;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * General class for non linear feedback shift register. LFSR are covered by this class as well, this is just a corner case among many possible feedback functions.
 */
public class Gfsr {

    final int stateWidth;//number of bits to store the state
    final List<BooleanExpressionNode> taps;
    boolean state[];
    final boolean [] nullState;

    protected Gfsr(List<BooleanExpressionNode> taps) {
        this.taps = taps.stream().map(t -> t.copy()).collect(Collectors.toList());
        stateWidth = taps.size();
        state = new boolean[stateWidth];
        state[0] = true;
        nullState = new boolean[stateWidth];
        Arrays.fill(nullState, false);
    }

    static public Gfsr copy(Gfsr n){
        Gfsr out = new Gfsr(n.taps);
        out.setState(n.getState());
        return out;
    }

    static public Gfsr fromTaps(List<BooleanExpressionNode> taps){
        return new Gfsr(taps);
    }

    static public Gfsr fromTaps(String tapsStr){
        String[] tapsStrs = tapsStr.split(";");
        List<BooleanExpressionNode> taps = Arrays.asList(tapsStrs).stream().map(t -> BooleanExpressionNode.toExpression(t)).collect(Collectors.toList());
        return new Gfsr(taps);
    }

    public boolean step(){
        boolean[]s = state.clone();
        for(int i=0;i<stateWidth;i++) state[i]= taps.get(i).apply(s);
        return state[0];
    }

    @Override
    public String toString() {
        return "Nlfsr{" +
                "stateWidth=" + stateWidth +
                ", taps=" + getTapsString() +
                ", state=" + Arrays.toString(state) +
                '}';
    }

    public Map<boolean[],boolean[][]> sequences(boolean excludeP) {
        boolean[] done = new boolean[1<<stateWidth];
        boolean[] startStateToDo = new boolean[1<<stateWidth];
        HashMap<boolean[],boolean[][]> out = new HashMap<boolean[],boolean[][]>();

        int maxLen= (1<<stateWidth)-1;
        boolean[] seq = new boolean[maxLen];
        int startState=0;
        for(int i=startState;i<done.length;i++){
            if(done[i] & !startStateToDo[i]) continue;
            boolean[] initState = Z2.toBooleans(i);
            done[i] = true;
            setState(initState);
            boolean[] trace = new boolean[1<<stateWidth];//used to detects loops
            int j=0;
            boolean[][] states = null;

            states = new boolean[maxLen+1][];
            states[j] = new boolean[stateWidth];
            Z2.copy(state, states[j]);
            boolean notP = true;
            do{
                boolean newBit = step();
                seq[j++]=newBit;

                states[j] = new boolean[stateWidth];
                Z2.copy(state,states[j]);

                int coveredState = Z2.booleansToInt(getState());
                if(trace[coveredState]) {
                    if(!Z2.equalValue(state,initState) ) {//this sequence has a P shape: go straight and then loop
                        notP=false;
                        if( !done[coveredState] ) {//if it has not been covered already in a sequence
                            //mark that state as a start state to do to get the sequence with the loop only.
                            startStateToDo[coveredState] = true;
                        }
                    }
                    break;
                }

                trace[coveredState] = true;
            }while((!Z2.equalValue(state, initState)));
            for(int k=0;k<trace.length;k++) done[k] |= trace[k];
            if(notP | !excludeP) {
                out.put(Arrays.copyOfRange(seq, 0, j), Arrays.copyOfRange(states, 0, j));
            }
        }
        return out;
    }

    /**
     *
     * @return true if the FSR is has length of at least (1<<stateWidth)-1 (max length of LFSR)
     */
    private boolean isMaxLength() {
        boolean[] done = new boolean[1<<stateWidth];

        int maxLen= (1<<stateWidth)-1;

        boolean[] initState = Z2.toBooleans(1);
        done[1] = true;
        setState(initState);
        boolean[] trace = new boolean[1<<stateWidth];//used to detects loops
        int j=0;
        do{
            boolean newBit = step();
            j++;

            int coveredState = Z2.booleansToInt(getState());
            if(trace[coveredState]) {
                if(Z2.equalValue(state,initState) ) return true;
                else {//this sequence has a P shape: go straight and then loop
                    if(j>=maxLen) return true;
                    else return false;
                }
            }
            trace[coveredState] = true;
        }while((!Z2.equalValue(state, initState)));
        if(j>=maxLen) return true;
        return false;
    }



    public boolean[] getState() {
        return state;
    }

    public void setState(boolean[] state) {
        boolean[] paddedState = state;
        if(state.length<stateWidth){
            paddedState = new boolean[stateWidth];
            System.arraycopy(state,0,paddedState,0,state.length);
        }
        setState(paddedState,0,Math.min(stateWidth,paddedState.length));
    }
    public void setState(boolean[] state, int len) {
        setState(state,0,len);
    }
    public void setState(boolean[] state, int from, int to) {
        boolean [] newState = Arrays.copyOfRange(state,from,to);
        if(newState.length!=stateWidth) throw new RuntimeException("State length ("+newState.length+") does not match the state width ("+stateWidth+")");
        this.state = newState;
    }

    public String describe(boolean outputSeq, boolean outputStates){
        String out = "";
        out+="Taps:            "+getTapsString()+"\n";
        out+="Initial state:   "+getStateString()+"\n";
        String tab="   ";

        if(outputSeq){
            Map<boolean[],boolean[][]> sequences = sequences(true);
            out+=sequences.size()+" generated sequences:"+"\n";
            TreeMap<Integer,Integer> counts = new TreeMap <Integer,Integer>();
            for(boolean[] seq: sequences.keySet()){
                int len = seq.length;
                if(counts.containsKey(len)) counts.put(len,counts.get(len)+1);
                else counts.put(len,1);
            }
            int sum=0;
            for(int len:counts.keySet()){
                sum+=len*counts.get(len);
                out+=String.format("%10d",len)+" bits sequences: "+counts.get(len)+" occurences"+"\n";
            }
            if(sum==Lfsr.polynomialDegreeToMaximumLength(stateWidth).intValue()) out+="All sequences are mutually exclusive\n";
            else out +="Some sequences have part in common\n";
            List<boolean[]> sequencesLength=new ArrayList<boolean[]>();
            sequencesLength.addAll(sequences.keySet());
            Collections.sort(sequencesLength,Z2.comparator);
            for (boolean[] seq : sequencesLength) {
                out+=String.format("%5d",seq.length) + " bits sequence: " + Z2.toBinaryString(seq)+"\n";
                if (outputStates) {
                    boolean[][] states = sequences.get(seq);
                    for (int i = 0; i < states.length; i++)
                        out+=tab+tab+tab + Z2.toBinaryString(states[i])+"\n";
                }
            }
        }
        return out;
    }

    public String getTapsString() {
        return taps.stream().map(t -> t.toString()).collect(Collectors.joining(";"));
    }

    public String getStateString(){
        return Z2.toBinaryString(state);
    }

    /**
     * Explore what sequence length can be optained with Gfsr of a given width
     * Ignore sequences below minLength
     * @param stateWidth
     * @return map of sequence length to Gfsr
     */
    /*static public Map<Integer,List<Gfsr>> explore(int stateWidth, int minLength) {
        Map<Integer,List<Gfsr>> out = new HashMap<Integer, List<Gfsr>>();

        List<Integer> operatorBounds = new ArrayList<Integer>();
        for(int i=1;i<stateWidth;i++) operatorBounds.add(BooleanOperator.getOpCnt());
        GenericCounter cnt = new GenericCounter(operatorBounds);
        do{
            List<Integer> operatorVals = cnt.getCount();
            //build the FSR
            Gfsr n = Gfsr.fromTaps(operatorVals);
            //filter out pure shift registers
            if(n.isPureShiftingRegister()) continue;
            //System.out.println(n);
            //generate the sequences
            Map<boolean[],boolean[][]> sequences = n.sequences(true);
            //add each one to the output
            for(boolean[] seq: sequences.keySet()){
                int len = seq.length;
                if(len<minLength) continue;
                if(!out.containsKey(len)) out.put(len,new ArrayList<Gfsr>());
                List<Gfsr> l = out.get(len);
                Gfsr nForSeq = Gfsr.copy(n);
                nForSeq.setState(sequences.get(seq)[0]);
                l.add(nForSeq);
                //System.out.println("\t"+len);
            }
        }while (cnt.next());
        return out;
    }*/

    static public List<Gfsr> findMaxLengthNlfsr(int stateWidth, int maxResults) {
        List<Gfsr> out = new ArrayList<Gfsr>();

        List<Integer> bounds = new ArrayList<Integer>();
        IntStream.rangeClosed(0, stateWidth).forEach(n -> bounds.add(stateWidth));
        IntStream.rangeClosed(0, stateWidth).forEach(n -> bounds.add(BooleanOperator.getOpCnt()));
        GenericCounter cnt = new GenericCounter(bounds);
        System.out.println(cnt.upperBounds);
        Function<Integer,Integer> input1 = i ->  (stateWidth-1+i)%stateWidth;
        int j=0;
        String lastTrace="";
        do{
            List<Integer> vals = cnt.getCount();
            Collections.reverse(vals);
            //System.out.println(vals);
            List<BooleanExpressionNode> taps = new ArrayList<>();
            String trace="";
            for(int i = 0;i<stateWidth-1;i++){
                Integer opId = vals.get(stateWidth + i);
                trace+=opId;
                int nOperands = BooleanOperator.get(opId).getnOperands();
                List<Integer> operands = new ArrayList<>();
                int op1 = input1.apply(i);
                trace+=op1;
                operands.add(op1);
                if(nOperands>1) {
                    int op2 = vals.get(i);
                    trace+=op2;
                    //check input values are such that input1==input2
                    if(op1==op2) {
                        taps = null;
                        break;
                    }
                    operands.add(op2);
                }
                BooleanExpressionNode tap = BooleanExpressionNode.toExpression(opId, operands);
                //System.out.print(tap);
                taps.add(tap);
            }
            if(null==taps) continue;
            {//last tap is special: depth = 1
                int i = stateWidth-1;
                Integer op1Id = vals.get(stateWidth + i);
                trace+=op1Id;
                BooleanOperator operator1 = BooleanOperator.get(op1Id);
                String tapStr = operator1.toString()+" ";
                int nOperands = operator1.getnOperands();

                Integer op2Id = vals.get(stateWidth + i+1);
                trace+=op2Id;
                BooleanOperator operator2 = BooleanOperator.get(op2Id);
                tapStr += operator2.toString()+" ";
                nOperands = nOperands-1+operator2.getnOperands();

                int op1 = input1.apply(i);
                tapStr += op1+" ";
                trace+=op1;
                if(nOperands!=3) continue;

                int op2 = vals.get(i);
                trace+=op2;
                //check input values are such that input1==input2
                if(op1==op2) continue;
                tapStr += op2 + " ";

                int op3 = vals.get(i + 1);
                trace += op3;
                //check input values are such that input3==input2
                if (op3 == op2) continue;
                tapStr += op3 + " ";

                BooleanExpressionNode tap = BooleanExpressionNode.toExpression(tapStr);
                //System.out.print(tap);
                taps.add(tap);
            }
            if(trace.contentEquals(lastTrace)) continue;
            lastTrace=trace;

            //System.out.println();
            //build the NLFSR
            Gfsr n = Gfsr.fromTaps(taps);
            //filter LFSRs
            if(n.isLinear()) continue;
            if(j++ % 0x10000 == 0) System.out.println(n.getTapsString());
            //check
            if(n.isMaxLength()){
                out.add(n);
                System.out.println("max length: " + n.getTapsString());
                if(out.size()==maxResults) return out;
            }
        }while (cnt.next());
        return out;
    }

    public boolean isLinear(){
        return taps.stream().allMatch(t -> t.isLinear());
    }
    public boolean isCommonCell(){
        return true;
    }
    public boolean isPureShiftingRegister(){
        return taps.stream().allMatch(t -> 1==t.getNumberOfInputs());
    }
}
