package uk.co.nimp;

import java.util.*;

/**
 * General class for non linear feedback shift register. LFSR are covered by this class as well, this is just a corner case among many possible feedback functions.
 */
public class Nlfsr {

    static public enum Operator {
            SHIFT,
            NOT,
            AND,
            NAND,
            OR,
            NOR,
            XOR,
            XNOR;
            boolean isUnary(){
                if(this.equals(SHIFT)||this.equals(NOT)) return true;
                return false;
            }
            static Map<Operator,String> opToString = new HashMap<Operator, String>();
            static Map<Operator,String> opToLongString = new HashMap<Operator, String>();
            static Map<String,Operator> longStringToOp = new HashMap<String,Operator>();
            static Operator[] intToOp;
            static {
                opToString.put(SHIFT, ">");
                opToString.put(NOT, "~");
                opToString.put(AND, "&");
                opToString.put(NAND, "!&");
                opToString.put(OR, "|");
                opToString.put(NOR, "!|");
                opToString.put(XOR, "^");
                opToString.put(XNOR, "!^");
                intToOp = new Operator[opToString.size()];
                int i=0;
                for(Operator op:opToString.keySet()){
                    //intToOp[i++] = op;
                    String s = opToString.get(op);
                    if(!s.startsWith("!")) s=" "+s;
                    opToLongString.put(op,s);
                    longStringToOp.put(s,op);
                }
                //force unary operators in lowest numbers
                intToOp[i++] = SHIFT;
                intToOp[i++] = NOT;
                intToOp[i++] = AND;
                intToOp[i++] = NAND;
                intToOp[i++] = OR;
                intToOp[i++] = NOR;
                intToOp[i++] = XOR;
                intToOp[i++] = XNOR;
            }
            static int opCnt(){
                return intToOp.length;
            }
            static private final int UNARY_OP_CNT = 2;
            static int unaryOpCnt(){
                return UNARY_OP_CNT;
            }
            static int binaryOpCnt(){
                return intToOp.length-UNARY_OP_CNT;
            }

            static Operator toOperator(int val) {
                return intToOp[val];
            }
            static Operator[] toOperator(int val[]) {
                Operator[] out = new Operator[val.length];
                for(int i=0;i<val.length;i++) out[i] = intToOp[val[i]];
                return out;
            }
            static Operator[] toOperator(List<Integer>val) {
                Operator[] out = new Operator[val.size()];
                for(int i=0;i<val.size();i++) out[i] = intToOp[val.get(i)];
                return out;
            }
            static Operator[] toOperator(String str){
                str = str.replace(" ","");
                str = str.replace("\t","");
                str = str.replace("\r","");
                str = str.replace("\n","");
                String s="";
                for(int i=0;i<str.length();i++) {
                    String opStr = str.substring(i, i + 1);
                    if (opStr.equals("!")){
                        s += str.substring(i, i + 2);
                        i++;
                    }
                    else s += " " + opStr;
                }
                Operator out[]=new Operator[s.length()/2];
                for(int i=0;i<out.length;i++) out[i] = longStringToOp.get(s.substring(2*i,2*(i+1)));
                return out;
            }
            public String toString(){
                return opToLongString.get(this);
            }
        }


    final int stateWidth;//number of bits to store the state
    final Operator[] taps;
    boolean state[];
    final boolean [] nullState;

    protected Nlfsr(Operator[] taps) {
        if(!taps[0].isUnary()) throw new RuntimeException("first tap must have unary operator");
        this.taps = taps.clone();
        stateWidth = taps.length;
        state = new boolean[stateWidth];
        state[0] = true;
        nullState = new boolean[stateWidth];
        Arrays.fill(nullState,false);
    }

    static public Nlfsr fromTaps(Operator[] taps){
        return new Nlfsr(taps);
    }

    static public Nlfsr fromTaps(int[] taps){
        return new Nlfsr(Operator.toOperator(taps));
    }

    static public Nlfsr fromTaps(List<Integer> taps){
        return new Nlfsr(Operator.toOperator(taps));
    }

    static public Nlfsr fromTaps(String tapsStr){
        Operator[] taps = Operator.toOperator(tapsStr);
        return new Nlfsr(taps);
    }

    public boolean step(){
        boolean out = state[0];
        boolean s0= state[0];
        for(int i=1;i<stateWidth;i++){
            switch(taps[i]){
                case SHIFT: state[i-1]=   state[i];break;
                case NOT:   state[i-1]=  !state[i];break;
                case AND:   state[i-1]=   state[i] & s0;break;
                case NAND:  state[i-1]=!( state[i] & s0);break;
                case OR:    state[i-1]= ( state[i] | s0);break;
                case NOR:   state[i-1]=!( state[i] | s0);break;
                case XOR:   state[i-1]= ( state[i] ^ s0);break;
                case XNOR:  state[i-1]=!( state[i] ^ s0);break;
            }
        }
        switch(taps[0]){
            case SHIFT: state[stateWidth-1]= s0;break;
            case NOT:   state[stateWidth-1]=!s0;break;
        }
        return out;
    }

    @Override
    public String toString() {
        return "Nlfsr{" +
                "stateWidth=" + stateWidth +
                ", taps=" + Arrays.toString(taps) +
                ", state=" + Arrays.toString(state) +
                '}';
    }

    private Map<boolean[],boolean[][]> sequences(boolean excludeP) {
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
        return Arrays.toString(taps);
    }
    public String getStateString(){
        return Z2.toBinaryString(state);
    }

    /**
     * Explore what sequence length can be optained with Nlfsr of a given width
     * Ignore sequences below minLength
     * @param stateWidth
     * @return map of sequence length to Nlfsr
     */
    static public Map<Integer,List<Nlfsr>> explore(int stateWidth, int minLength){
        Map<Integer,List<Nlfsr>> out = new HashMap<Integer, List<Nlfsr>>();

        List<Integer> operatorBounds = new ArrayList<Integer>();
        operatorBounds.add(Operator.unaryOpCnt());//first operator must be unary
        for(int i=1;i<stateWidth;i++) operatorBounds.add(Operator.opCnt());
        GenericCounter cnt = new GenericCounter(operatorBounds);
        do{
            List<Integer> operatorVals = cnt.getCount();
            //filter out pure shift registers
            if(operatorVals.stream().noneMatch(opVal -> opVal >= Operator.unaryOpCnt())) continue;
            //build the NLFSR
            Nlfsr n = Nlfsr.fromTaps(operatorVals);
            //System.out.println(n);
            //generate the sequences
            Map<boolean[],boolean[][]> sequences = n.sequences(true);
            //add each one to the output
            for(boolean[] seq: sequences.keySet()){
                int len = seq.length;
                if(len<minLength) continue;
                if(!out.containsKey(len)) out.put(len,new ArrayList<Nlfsr>());
                List<Nlfsr> l = out.get(len);
                l.add(n);
                //System.out.println("\t"+len);
            }
        }while (cnt.next());
        //show frequencies

        for(Integer len:out.keySet()){
            System.out.println(len+"; "+out.get(len).size());
        }
        return out;
    }
}
