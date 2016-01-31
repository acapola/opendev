package uk.co.nimp;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * General class for non linear feedback shift register. LFSR are covered by this class as well, this is just a corner case among many possible feedback functions.
 */
public class Nlfsr  {

    static public enum Operator {
            SHIFT,
            NOT,
            AND,
            NAND,
            OR,
            NOR,
            OA,
            OX,
            OX2,
            XOR,
            XNOR;
            boolean isUnary(){
                if(this.equals(SHIFT)||this.equals(NOT)) return true;
                return false;
            }
            static Map<Operator,String> opToString = new HashMap<Operator, String>();
            static Map<Operator,Integer> opToInt = new HashMap<Operator, Integer>();
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
                opToString.put(OA, "OA");
                opToString.put(OX, "OX");
                opToString.put(OX2, "O2");
                opToString.put(XOR, "^");
                opToString.put(XNOR, "!^");
                intToOp = new Operator[opToString.size()];
                int i=0;
                for(Operator op:opToString.keySet()){
                    //intToOp[i++] = op;
                    String s = opToString.get(op);
                    if(s.length()<2) s=" "+s;
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
                intToOp[i++] = OA;
                intToOp[i++] = OX;
                intToOp[i++] = OX2;
                intToOp[i++] = XOR;
                intToOp[i++] = XNOR;
                for(i=0;i<intToOp.length;i++) opToInt.put(intToOp[i],i);
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
            static String getNext(String str, int offset){
                String out = str.substring(offset,offset+1);
                if(out.equals("!")) return str.substring(offset,offset+2);
                if(out.equals("O")) return str.substring(offset,offset+2);
                /*if(out.equals("O")){
                    String s = str.substring(offset,offset+2);
                    char next = s.charAt(offset+1);
                    if((next>='2') || (next<='5')) return s;
                }*/
                return out;
            }
            static Operator[] toOperator(String str){
                str = str.replace(" ","");
                str = str.replace("\t","");
                str = str.replace("\r","");
                str = str.replace("\n","");
                String s="";
                for(int i=0;i<str.length();i++) {
                    String opStr = getNext(str,i);
                    if(opStr.length()==2) i++;
                    else opStr=" "+opStr;
                    s+=opStr;
                }
                Operator out[]=new Operator[s.length()/2];
                for(int i=0;i<out.length;i++) out[i] = longStringToOp.get(s.substring(2*i,2*(i+1)));
                return out;
            }
            public String toString(){
                return opToLongString.get(this);
            }
            public Integer toInt() {return opToInt.get(this);}

        public boolean isLinear() {
            switch(this){
                case SHIFT:
                case NOT:
                case XOR:
                case XNOR:
                    return true;
                default: return false;
            }
        }
        public boolean isCommonCell() {
            switch(this){
                case OX:
                case OX2:
                    return false;
                default: return true;
            }
        }
    }


    final int stateWidth;//number of bits to store the state
    final Operator[] taps;
    boolean state[];
    final boolean [] nullState;

    protected Nlfsr(Operator[] taps) {
        //if(!taps[0].isUnary()) throw new RuntimeException("first tap must have unary operator");
        this.taps = taps.clone();
        stateWidth = taps.length;
        state = new boolean[stateWidth];
        state[0] = true;
        nullState = new boolean[stateWidth];
        Arrays.fill(nullState,false);
    }

    static public Nlfsr copy(Nlfsr n){
        Nlfsr out = new Nlfsr(n.taps);
        out.setState(n.getState());
        return out;
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
        boolean s1= state[1];
        boolean s2= state[2];
        boolean[] stateBu = state.clone();
        for(int i=1;i<=stateWidth;i++){
            int safeI = i%stateWidth;
            switch(taps[safeI]){
                case SHIFT: state[i-1]=   stateBu[safeI];break;
                case NOT:   state[i-1]=  !stateBu[safeI];break;
                case AND:   state[i-1]=   stateBu[safeI] & s0;break;
                case NAND:  state[i-1]=!( stateBu[safeI] & s0);break;
                case OR:    state[i-1]= ( stateBu[safeI] | s0);break;
                case OA:    state[i-1]= ( stateBu[safeI] & (s0 | s1));break;
                case OX:    state[i-1]= ( stateBu[safeI] ^ (s0 | s1));break;
                case OX2:   state[i-1]= ( stateBu[safeI] ^ (s0 | s2));break;
                case NOR:   state[i-1]=!( stateBu[safeI] | s0);break;
                case XOR:   state[i-1]= ( stateBu[safeI] ^ s0);break;
                case XNOR:  state[i-1]=!( stateBu[safeI] ^ s0);break;
            }
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

    public boolean isMaxLength_Storage() {
        final int maxLen= (1<<stateWidth)-1;
        int startState=0;
        boolean[] initState = Z2.toBooleans(startState);
        setState(initState);
        boolean[] trace = new boolean[1<<stateWidth];//used to detects loops
        trace[startState] = true;
        int j=0;
        do{
            boolean newBit = step();
            j++;
            int coveredState = Z2.booleansToInt(getState());
            if(trace[coveredState]) {
                if(Z2.equalValue(state,initState) ) {
                    if((j==1) && (startState==0)){//single state cycle, maybe all other states are in a max length seq., try it
                        startState=1;
                        initState = Z2.toBooleans(startState);
                        setState(initState);
                        trace[startState] = true;
                        j=0;
                        continue;
                    }
                    if(maxLen<=j) return true;
                    else return false;
                } else {//this sequence has a P shape: go straight and then loop
                    return false;
                }
            }
            trace[coveredState] = true;
        }while(maxLen+1>j);
        return false;
    }


    public boolean isMaxLength() {
        if(taps[0]==Operator.OA) {
            int xnorPos=-1;
            for(int i = 1;i<stateWidth;i++){//check there is only 1 xnor and find out its position
                if(taps[i]==Operator.XNOR)
                    xnorPos=i;
                else if(taps[i]!=Operator.SHIFT){
                    xnorPos=-1;
                    break;
                }
            }
            if(-1!=xnorPos)
                return isMaxLengthOAXN(xnorPos);
        }
        final long maxLen= (1L<<stateWidth)-1;
        int startState=0;
        boolean[] initState = Z2.toBooleans(startState);
        setState(initState);
        long j=0;
        do{
            boolean newBit = step();
            j++;

            if(Z2.equalValue(state,initState) ) {
                if((j==1) && (startState==0)){//single state cycle, maybe all other states are in a max length seq., try it
                    startState=1;
                    initState = Z2.toBooleans(startState);
                    setState(initState);
                    j=0;
                    continue;
                }
                if(maxLen<=j) return true;
                else return false;
            }
        }while(maxLen+1>j);
        return false;
    }
    public boolean isMaxLengthOAXN(int xnorPos) {
        //System.out.println(this.describe(true,true));
        final long maxLen= (1L<<stateWidth)-1;
        long startState=0;
        long lState = startState;
        long j=0;

        long tapsMask = (Long.MIN_VALUE-1) & ~(1L<<(stateWidth-1)) & ~(1L<<(xnorPos-1));//0 where we have to compute something

        do{
            long shiftedState = (lState>>1)  & tapsMask;
            long lastTap = lState>>1;
            lastTap = ((lState & (lastTap | lState)) & 1)<<(stateWidth-1);
            long tapXn =(((lState>>xnorPos) ^ ~lState) & 1) << (xnorPos-1);
            lState = shiftedState | lastTap | tapXn;
            j++;

            if(lState==startState ) {
                if((j==1) && (startState==0)){//single state cycle, maybe all other states are in a max length seq., try it
                    startState=1;
                    lState = startState;
                    j=0;
                    continue;
                }
                if(maxLen<=j) return true;
                else return false;
            }
        }while(maxLen+1>j);
        return false;
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
        String out = "";
        for(Operator tap:taps){
            out+=tap;
        }
        return out;
    }

    public String getStateString(){
        return Z2.toBinaryString(state);
    }

    public static List<Nlfsr> findMaxLengthNlfsr(int stateWidth, int maxResults) {
        List<Nlfsr> out = new ArrayList<>();
        List<Integer> operatorBounds = new ArrayList<Integer>();
        operatorBounds.add(Operator.unaryOpCnt());//first operator must be unary
        for(int i=1;i<stateWidth;i++) operatorBounds.add(Operator.opCnt());
        GenericCounter cnt = new GenericCounter(operatorBounds);
        int j=0;
        Predicate<Integer> rejected = new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                switch(Operator.toOperator(integer)){
                    case OX2:
                    case SHIFT:
                    case OX:
                    case XNOR:
                        return true;
                }
                return false;
            }
        };
        do{
            List<Integer> operatorVals = cnt.getCount();
            if(operatorVals.stream().anyMatch(rejected)) continue;
            //filter out pure shift registers
            if(operatorVals.stream().noneMatch(opVal -> opVal >= Operator.unaryOpCnt())) continue;
            //build the NLFSR
            Nlfsr n = Nlfsr.fromTaps(operatorVals);
            //System.out.println(n);
            //check
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

    public static List<Nlfsr> findMaxLengthNlfsrNOAX(int stateWidth, int maxResults) {
        List<Nlfsr> out = new ArrayList<>();
        int operatorBounds = 0;
        if(stateWidth>=32) throw new RuntimeException();
        List<Integer> base = new ArrayList<>();
        int notId = Operator.NOT.toInt();
        int xorId = Operator.XOR.toInt();
        base.add(notId);
        base.add(Operator.OA.toInt());
        int cnt=0;
        int cntLimit = 1<<(stateWidth-2);
        do{
            List<Integer> operatorVals = new ArrayList<>(32);
            operatorVals.addAll(base);
            for(int i=0;i<stateWidth-2;i++){
                if((1&(cnt>>i))==0) operatorVals.add(notId);
                else operatorVals.add(xorId);
            }
            cnt++;
            //build the NLFSR
            Nlfsr n = Nlfsr.fromTaps(operatorVals);
            //System.out.println(n);
            //check
            if(n.isLinear()) continue;
            if(cnt % 0x10000 == 0) System.out.println(n.getTapsString());
            //check
            if(n.isMaxLength()){
                out.add(n);
                System.out.println("max length: " + n.getTapsString());
                if(out.size()==maxResults) return out;
            }
        }while (cnt<cntLimit);
        return out;
    }

    public static List<Nlfsr> findMaxLengthNlfsrNOAX1(int stateWidth, int maxResults) {
        List<Nlfsr> out = new ArrayList<>();
        List<Integer> base = new ArrayList<>();
        int notId = Operator.NOT.toInt();
        int xorId = Operator.XOR.toInt();
        base.add(notId);
        base.add(Operator.OA.toInt());
        for(int i = 0;i<stateWidth-3;i++) base.add(notId);
        int cnt=0;
        int cntLimit = stateWidth-2;
        do{
            List<Integer> operatorVals = new LinkedList<>();
            operatorVals.addAll(base);
            operatorVals.add(2 + cnt, xorId);
            cnt++;
            //build the NLFSR
            Nlfsr n = Nlfsr.fromTaps(operatorVals);
            //System.out.println(n);
            //check
            if(n.isLinear()) continue;
            if(cnt % 0x10000 == 0) System.out.println(n.getTapsString());
            //check
            if(n.isMaxLength()){
                out.add(n);
                System.out.println("max length: " + n.getTapsString());
                if(out.size()==maxResults) return out;
            }
        }while (cnt<cntLimit);
        return out;
    }

    public static List<Nlfsr> findMaxLengthNlfsrOAX(int stateWidth, int maxResults) {
        List<Nlfsr> out = new ArrayList<>();
        List<Integer> base = new ArrayList<>();
        int notId = Operator.SHIFT.toInt();
        int xorId = Operator.XOR.toInt();
        int oaId = Operator.SHIFT.toInt();
        //int oaId = Operator.NOT.toInt();
        base.add(oaId);
        for(int i = 1;i<stateWidth-1;i++) base.add(notId);
        //base.add(xorId);
        int cnt=0;
        int cntLimit = stateWidth-1;
        do{
            List<Integer> operatorVals = new LinkedList<>();
            operatorVals.addAll(base);
            operatorVals.add(1+cnt, xorId);
            cnt++;
            //build the NLFSR
            Nlfsr n = Nlfsr.fromTaps(operatorVals);
            //System.out.println(n);
            //check
            //if(n.isLinear()) continue;
            if(cnt % 0x10000 == 0) System.out.println(n.getTapsString());
            //check
            if(n.isMaxLength()){
                out.add(n);
                System.out.println("max length: " + n.getTapsString());
                //System.out.println(n.describe(true,true));
                if(out.size()==maxResults) return out;
            }
        }while (cnt<cntLimit);
        return out;
    }


    /**
     * Explore what sequence length can be optained with Nlfsr of a given width
     * Ignore sequences below minLength
     * @param stateWidth
     * @return map of sequence length to Nlfsr
     */
    static public Map<Integer,List<Nlfsr>> explore(int stateWidth, int minLength) {
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
                Nlfsr nForSeq = Nlfsr.copy(n);
                nForSeq.setState(sequences.get(seq)[0]);
                l.add(nForSeq);
                //System.out.println("\t"+len);
            }
        }while (cnt.next());
        return out;
    }
    public boolean isLinear(){
        for(int i=0;i<taps.length;i++){
            if(!taps[i].isLinear()) return false;
        }
        return true;
    }
    public boolean isCommonCell(){
        for(int i=0;i<taps.length;i++){
            if(!taps[i].isCommonCell()) return false;
        }
        return true;
    }
    public boolean isPureShiftingRegister(){
        for(int i=0;i<taps.length;i++){
            if(!taps[i].isUnary()) return false;
        }
        return true;
    }
}
