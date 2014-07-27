package uk.co.nimp;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by seb on 6/22/14.
 */
public class Lfsr {
    //follow conventions from handbook of applied crypto by menezes,oorschot and vanstone, figure 6.4
    final boolean[] taps;//cL,...,c2,c1 (1 is implicit)
    final int l;//the width of the ARG_LFSR (max period is therefore 2^l - 1)
    boolean [] nullState;
    boolean[] state;//stage0, stage1, ...,stage L-1.
    boolean invertOutput;
    enum LfsrStepMode {
        FIBONACCI,
        FIBONACCI_XNOR,
        GALOIS,
        GALOIS_XNOR,
        MODMUL
    }

    LfsrStepMode mode;
    public Lfsr setMode(LfsrStepMode mode){
        this.mode=mode;
        switch(mode){
            case FIBONACCI_XNOR:
            case GALOIS_XNOR:
                Arrays.fill(nullState, true);
                break;
            default:
                Arrays.fill(nullState,false);
        }
        if(Z2.equal(nullState,state)){
            state=Z2.complement(state);
        }
        return this;
    }
    public Lfsr setInvertOutput(boolean invertOutput){
        this.invertOutput=invertOutput;
        return this;
    }

    /**
     * Gives the width in bits of the state of the LFSR.
     * This is also the degree of the polynomial
     * @return the width in bits of the state of the LFSR
     */
    public int getWidth() {
        return l;
    }
    public Lfsr(boolean []taps,int len){
        if(!taps[0]) throw new RuntimeException("LSFR polynomial must always contain 1 -> this is the lsb of taps.");
        this.taps = Z2.reverse(taps,1,len);
        l=len-1;
        nullState = new boolean[l];
        state = new boolean[l];
        state[0]=true;
        mode=LfsrStepMode.FIBONACCI;
        invertOutput=false;
    }
    public boolean step(){
        boolean out = false;
        switch(mode){
            case FIBONACCI:out=stepXor();break;
            case FIBONACCI_XNOR:out=stepXnor();break;
            case MODMUL:out=stepModMul();break;
            case GALOIS:out=stepGalois();break;
            case GALOIS_XNOR:out=stepGaloisXnor();
        }
        out = out ^ invertOutput;
        return out;
    }
    public boolean stepXor(){
        boolean out = state[0];
        boolean sj=taps[0] & state[0];
        for(int i=1;i<l;i++){
            sj ^= (taps[i] & state[i]);
            state[i-1]=state[i];
        }
        state[l-1]=sj;
        return out;
    }
    public boolean stepXnor(){
        boolean out = state[0];
        boolean sj=taps[0] & state[0];
        for(int i=1;i<l;i++){
            sj ^= !(taps[i] & state[i]);
            state[i-1]=state[i];
        }
        state[l-1]=sj;
        return out;
    }
    public boolean stepGalois(){
        boolean out = state[0];
        boolean s0= state[0];
        for(int i=1;i<l;i++){
            state[i-1]=state[i] ^ (taps[i] & s0);
        }
        state[l-1]=s0;
        return out;
    }
    public boolean stepGaloisXnor(){
        boolean out = state[0];
        boolean s0= state[0];
        for(int i=1;i<l;i++){
            state[i-1]=state[i] ^ (taps[i] & !s0);
        }
        state[l-1]=s0;
        return out;
    }
    public boolean stepModMul(){
        boolean out = state[0];
        boolean[] poly=getTaps();
        state = Z2.cloneAndPad(Z2.mod(Z2.minimumLengthCopy(Z2.shiftLeft(state, 1)), poly),l);
        return out;
    }

    /**
     * An LFSR which generate the same sequence but in reversed order
     * Does not work for singular LFSRs
     * @return
     */
    public Lfsr reversedSequenceLfsr(){
        boolean[] nonSingularTaps = Z2.minimumLengthCopy(getTaps());
        return new Lfsr(Z2.reverse(nonSingularTaps),nonSingularTaps.length);
    }


    public static Lfsr fromSequence(boolean[] seq){
        Lfsr out = fibonacciLfsrFromSequence(seq);
        boolean[]cseq = Z2.complement(seq);
        Lfsr fromCSeq = fibonacciLfsrFromSequence(cseq);
        if(fromCSeq.getWidth()<out.getWidth()){
            //fromCSeq.setMode(LfsrStepMode.FIBONACCI_XNOR);//works only for max length LSFR ?
            fromCSeq.setInvertOutput(true);
            out=fromCSeq;
        }
        return out;
    }
    /**
     * Build the LFSR which generate a given bit sequence. See "Berlekamp-Massey algorithm"
     * @param seq: a bit sequence with at least one bit set to 1
     * @return the minimal LFSR to generate seq
     */
    public static Lfsr fibonacciLfsrFromSequence(boolean[] seq){
        int len = seq.length;
        boolean[] c = new boolean[len+1];c[0]=true;
        boolean[] b = new boolean[len];b[0]=true;
        int l=0;
        int n=0;
        int m=-1;
        while(n<len){
            boolean sn=seq[n];
            boolean d=sn;
            assert(c.length>l);
            for(int i=1;i<l+1;i++) d^=c[i]&seq[n-i];
            //boolean[] t = Arrays.copyOf(c,len);
            if(d){
                boolean[] t = Arrays.copyOf(c,len);
                int shift=n-m;
                assert(shift>=0);
                //assert(l+shift<len);
                for(int i=shift;i<len;i++) c[i]^=b[i-shift];
                if(l<=n/2){
                    l=n+1-l;
                    m=n;
                    b=t;
                }
            }
            n++;
            //System.out.println("sn:"+toBinaryString(sn)+" d:"+toBinaryString(d)+" t: "+toBinaryString(t)+" c:"+toBinaryString(c)+" l:"+l+" m:"+m+" b:"+toBinaryString(b)+" n:"+n);
        }
        if(l==0) l=seq.length;//corner cases: we simply store the whole sequence
        c = Z2.cloneRange(c,0,l+1);
        //c = Z2.reverse(c);
        Lfsr out = fromTaps(c);
        //System.out.println(out);
        //set the state so that the LFSR start to generate the desired sequence
        /*for(int i=0;i<l-1;i++){
            out.state[i+1]=seq[i];
        }
        boolean sj=false;
        for(int i=1;i<l;i++){
            sj ^= out.taps[i] & out.state[i];
        }
        out.state[0]=sj^seq[l-1];*/
        out.setState(seq,l);
        return out;
    }
    public static Lfsr fromTaps(boolean[] taps){
        return new Lfsr(taps,taps.length);
    }

    public static Lfsr fromTapsPositions(int[] tapsPositions){
        int max=0;
        for(int i=0;i<tapsPositions.length;i++) max = Math.max(max,tapsPositions[i]);
        boolean []taps = new boolean[max+1];
        taps[0] = true;//required by the constructor
        for(int i=0;i<tapsPositions.length;i++) taps[tapsPositions[i]] = true;
        return new Lfsr(taps,taps.length);
    }
    public static Lfsr fromBinaryString(String tapsStr){
        return new Lfsr(Z2.toBooleans(tapsStr),tapsStr.length());
    }
    public static Lfsr fromPolynomial(String polynomial){
        boolean[] taps = Z2.polynomialToBooleans(polynomial);
        return new Lfsr(taps,taps.length);
    }

    public boolean[] getState() {
        return state;
    }
    public void setState(boolean[] state) {
        boolean[] paddedState = state;
        if(state.length<l){
            paddedState = new boolean[l];
            System.arraycopy(state,0,paddedState,0,state.length);
        }
        setState(paddedState,0,Math.min(l,paddedState.length));
    }
    public void setState(boolean[] state, int len) {
        setState(state,0,len);
    }
    public void setState(boolean[] state, int from, int to) {
        boolean [] newState = Arrays.copyOfRange(state,from,to);
        if(newState.length!=l) throw new RuntimeException("State length ("+newState.length+") does not match the LFSR size ("+l+")");
        //if(Arrays.equals(newState,nullState)) throw new RuntimeException("Can't set a state with only zeroes");
        this.state = newState;
    }
    public boolean[] getTaps() {
        boolean[] out = new boolean[l+1];
        out[0] = true;
        for(int i=1;i<=l;i++) out[i] = taps[l-i];
        return out;
    }
    public String getPolynomial(){
        return Z2.toPolynomial(getTaps());
    }
    public String getStateString(){
        return Z2.toBinaryString(state);
    }
    public String getTapsString(){
        return "1"+Z2.toBinaryString(Z2.reverse(taps));
    }
    public boolean isSingular(){
        return !taps[0];
    }
    public boolean isMaximumLength(){
        if(isSingular()) return false;
        return isPolynomialPrimitive();
    }
    public boolean isPolynomialIrreducible(){
        boolean[] fx = Z2.minimumLengthCopy(getTaps());
        return Z2.isIrreducible(fx);
    }
    public boolean isPolynomialPrimitive(){
        boolean[] fx = Z2.minimumLengthCopy(getTaps());
        return Z2.isPrimitive(fx);
    }

    /**
     * if the LFSR generates the sequence, its state is set to the initial state to generate the sequence.
     * @param sequence
     * @return
     */
    public boolean isGeneratingSequence(boolean[] sequence){
        setState(sequence);
        for(int i=0;i<sequence.length;i++){
            if(sequence[i]!=step()) return false;
        }
        setState(sequence);
        return true;
    }

    @Override
    public String toString() {
        /*String properties = "not maximum length)";
        if(isMaximumLength()){
            BigInteger maxLength = BigInteger.ONE.shiftLeft(l).subtract(BigInteger.ONE);
            properties = "maximum length LFSR: period of "+maxLength+")";
        }else{
            if(isPolynomialIrreducible()) properties = "irreducible but not primitive --> "+properties;
            else properties = "reducible --> "+properties;
        }
        if(isSingular()) properties = "(Singular, "+properties;
        else properties = "(Non singular, "+properties;*/
        return "Lfsr{" +
                "taps=" + getTapsString() +
                ", state=" + getStateString() +
                ", mode=" +mode+
                ", invertOutput=" + invertOutput +
                //", "+properties+
                '}';
    }

    public String describe(boolean outputSeq, boolean outputStates){
        String out = "";
        out+="Polynomial:      "+getPolynomial()+"\n";
        out+="Inverted output: "+invertOutput+"\n";
        out+="Stepping mode:   "+mode+"\n";
        out+="Taps:            "+getTapsString()+"\n";
        out+="Initial state:   "+getStateString()+"\n";
        String properties = "maximum length LFSR \n"+"Sequence length is ";
        BigInteger maxLength = BigInteger.ONE.shiftLeft(getWidth()).subtract(BigInteger.ONE);
        String tab="   ";
        if(isMaximumLength()) {
            properties += maxLength;
        }else{
            properties = "non "+properties;
            if(isPolynomialIrreducible()) {
                properties = "irreducible but not primitive, "+properties;
                Map<BigInteger,Integer> seqlength=sequencesLength();
                BigInteger len=null;
                for(BigInteger l:seqlength.keySet()){
                    len=l;
                }
                properties = properties+len+", "+seqlength.get(len)+" different sequences of that length";
            } else {
                try{
                    Map<BigInteger,Integer> seqlength=sequencesLength();
                    properties+="dependant on the initial value. It can be:\n";
                    for(BigInteger len:seqlength.keySet()){
                        properties+=tab+len+" ("+seqlength.get(len)+" different sequences of that length)\n";
                    }
                    properties=properties.substring(0,properties.length()-1);//remove the last new line character
                }catch(Throwable e) {//we don't know how to compute the length of sequences for this polynomial.
                    properties += "smaller than " + maxLength;
                }
            }
        }

        if(!isPolynomialIrreducible()) {
            properties = "reducible, " + properties;
            boolean[][] factors = Z2.factorPolynomial(Z2.minimumLengthCopy(getTaps()));
            properties += "\n"+"Factors:\n" +tab+ Z2.join(Z2.toPolynomials(factors), "\n"+tab);
        }

        if(isSingular()) properties = "a singular, "+properties;
        else properties = "a non singular, "+properties;
        out+="It is "+properties+"\n";
        out+="Reverse sequence polynomial: "+reversedSequenceLfsr().getPolynomial()+"\n";

        if(outputSeq){
            if(isMaximumLength()){
                Map<boolean[],boolean[][]> seqAndStates = sequencesAndStates();
                boolean[] seq=null;
                boolean[][]states=null;
                for(boolean[] s:seqAndStates.keySet()){
                    seq=s;
                    states=seqAndStates.get(s);
                }

                out+="Generated sequence:\n"+Z2.toBinaryString(seq)+"\n";
                if (outputStates) {
                    for (int i = 0; i < states.length; i++)
                        out+=tab + Z2.toBinaryString(states[i])+"\n";
                    //out+="\n";
                }
            } else {
                Map<boolean[],boolean[][]> sequences = sequences(outputStates);
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
                out+=sum+" states in total\n";
                //if(sum<200) {//for debug
                for (boolean[] seq : sequences.keySet()) {
                    out+=String.format("%6d",seq.length) + " outputs: " + Z2.toBinaryString(seq)+"\n";
                    if (outputStates) {
                        boolean[][] states = sequences.get(seq);
                        for (int i = 0; i < states.length; i++)
                            out+=tab + Z2.toBinaryString(states[i])+"\n";
                        //out+="\n";
                    }
                }
                //}
            }
        }
        return out;
    }

    public static BigInteger polynomialDegreeToMaximumLength(int degree){
        BigInteger maxLength = BigInteger.ONE.shiftLeft(degree).subtract(BigInteger.ONE);
        return maxLength;
    }
    static BigInteger irreduciblePolynomialSequencesLength(boolean[] polynomial){
        //TreeMap<BigInteger,Integer> out = new TreeMap<BigInteger,Integer>();
        BigInteger maxLength=Lfsr.polynomialDegreeToMaximumLength(polynomial.length-1);
        BigInteger[] factors = PollardRho.factor(maxLength);
        int nCombination = 1<<factors.length;
        for(int i=1;i<nCombination;i++){
            BigInteger candidate = BigInteger.ONE;
            boolean[] selection = Z2.toBooleans(i);
            for (int j = 0; j < selection.length; j++) {
                if (selection[j]) candidate = candidate.multiply(factors[j]);
            }
            boolean[] checker = Z2.modExp(Z2.X,candidate,polynomial);
            if(Z2.isOne(checker)){
                //BigInteger nSeq = maxLength.divide(candidate);
                //out.put(candidate, nSeq.intValue());
                //return out;
                return candidate;
            }
                /*
                    //a very inefficient approach
                    int candidate = 1;
                    boolean[] selection = Z2.toBooleans(i);
                    for (int j = 0; j < selection.length; j++) {
                        if (selection[j]) candidate = candidate * factors[j].intValue();
                    }
                    BigInteger checker = BigInteger.ONE.shiftLeft(candidate);
                    checker = checker.add(BigInteger.ONE);
                    boolean[] gcd = Z2.gcd(poly, Z2.toBooleans(checker));
                    if (Z2.equal(gcd, poly)) {//gcd(x^k+1,poly)==poly ?
                        BigInteger nSeq = maxLength.divide(BigInteger.valueOf(candidate));
                        out.put(BigInteger.valueOf(candidate), nSeq.intValue());
                        return out;
                    }
                */
        }
        if(Z2.isIrreducible(polynomial)) throw new RuntimeException("fatal internal error");
        else throw new RuntimeException("polynomial is reducible");
    }
    /**
     * Compute the length of all sequences
     * @return a Map associating the length of sequence and the number of occurence.
     */
    public Map<BigInteger,Integer> sequencesLength(){
        TreeMap<BigInteger,Integer> out = new TreeMap<BigInteger, Integer>();
        BigInteger maxLength = polynomialDegreeToMaximumLength(l);
        if(isMaximumLength()){
            out.put(maxLength,1);
            return out;
        }
        boolean[] poly=Z2.minimumLengthCopy(getTaps());
        if(isPolynomialIrreducible()){
            BigInteger len=Lfsr.irreduciblePolynomialSequencesLength(poly);
            BigInteger nSeq = maxLength.divide(len);
            out.put(len, nSeq.intValue());
            return out;
        }else{
            boolean[][]factors = Z2.factorPolynomial(poly);
            Map<BigInteger,Integer> factorsMap = new HashMap<BigInteger,Integer>();
            int maxPower=0;
            for(boolean[] f: factors){
                BigInteger fBi = Z2.booleansToBigInteger(f);
                int power = 1;
                if(factorsMap.containsKey(fBi)) power += factorsMap.get(fBi);
                factorsMap.put(fBi,power);
                maxPower = Math.max(maxPower,power);
            }


            if(factorsMap.size()==1) {
                boolean[] root = factors[0];
                int power = maxPower;
                if(Z2.isPrimitive(root)) {
                    //power of primitive polynomials:
                    //nLength = 1+nBits(pow-1)
                    int nLengths = 1+Z2.bitWidth(power-1);
                    BigInteger length=Lfsr.polynomialDegreeToMaximumLength(root.length-1);
                    BigInteger sum=BigInteger.ZERO;
                    BigInteger two = BigInteger.valueOf(2);
                    for(int i=0;i<nLengths-1;i++){
                        //example for poly degree 2:
                        //i           0   1   2   3
                        //targetSum   1   3   15  255
                        BigInteger targetSum=Lfsr.polynomialDegreeToMaximumLength((root.length-1)*(1<<i));
                        BigInteger delta = targetSum.subtract(sum);
                        int occurences = delta.divide(length).intValue();
                        out.put(length,occurences);
                        sum=targetSum;
                        length=length.multiply(two);
                    }
                    BigInteger delta = maxLength.subtract(sum);
                    int occurences = delta.divide(length).intValue();

                    out.put(length,occurences);
                }else{
                    int nLengths = 1+Z2.bitWidth(power-1);

                    BigInteger length=Lfsr.irreduciblePolynomialSequencesLength(root);
                    BigInteger sum=BigInteger.ZERO;
                    BigInteger two = BigInteger.valueOf(2);
                    for(int i=0;i<nLengths-1;i++){
                        //example for poly degree 2:
                        //i           0   1   2   3
                        //targetSum   1   3   15  255
                        BigInteger targetSum=Lfsr.polynomialDegreeToMaximumLength((root.length-1)*(1<<i));
                        BigInteger delta = targetSum.subtract(sum);
                        int occurences = delta.divide(length).intValue();
                        out.put(length,occurences);
                        sum=targetSum;
                        length=length.multiply(two);
                    }
                    BigInteger delta = maxLength.subtract(sum);
                    int occurences = delta.divide(length).intValue();

                    out.put(length,occurences);
                    //throw new RuntimeException("sequencesLength called for a polynomial being a power of an irreducible polynomial, case not implemented yet");
                }
            } else {
                if(1==maxPower) {
                    //square free polynomials:
                    BigInteger[] primitiveSeqLengths = new BigInteger[factors.length];
                    for (int i = 0; i < factors.length; i++) {
                        primitiveSeqLengths[i] = polynomialDegreeToMaximumLength(factors[i].length - 1);
                    }
                    int nCombination = 1 << factors.length;
                    for (int i = 1; i < nCombination; i++) {
                        boolean[] selection = Z2.toBooleans(i);
                        int lsbIndex = Z2.lsbSetIndex(selection);
                        BigInteger len = primitiveSeqLengths[lsbIndex];
                        BigInteger product = len;
                        for (int j = lsbIndex + 1; j < selection.length; j++) {
                            if (selection[j]) {
                                len = lcm(len, primitiveSeqLengths[j]);
                                product = product.multiply(primitiveSeqLengths[j]);
                            }
                        }
                        int nSeq = product.divide(len).intValue();
                        if (out.containsKey(len)) out.put(len, out.get(len) + nSeq);
                        else out.put(len, nSeq);
                    }
                }else{//general case: mix of powers of irreducible polynomials
                    throw new RuntimeException("sequencesLength called for a polynomial being a mix of powers of irreducible polynomials, case not implemented yet");
                }
            }
        }
        return out;
    }
    static BigInteger lcm(BigInteger a, BigInteger b){
        return b.multiply(a.divide(a.gcd(b)));
    }

    public Set<boolean[]> sequences() {
        Set<boolean[]> out = sequences(false).keySet();
        return out;
    }
    public Map<boolean[],boolean[][]> sequencesAndStates() {
        return sequences(true);
    }
    public Map<boolean[],boolean[][]> sequences(boolean storeStates) {
        boolean[] done = new boolean[1<<l];
        HashMap<boolean[],boolean[][]> out = new HashMap<boolean[],boolean[][]>();
        int maxLen= (1<<l)-1;
        boolean[] seq = new boolean[maxLen];
        int startState=1;
        if((mode==LfsrStepMode.FIBONACCI_XNOR)||(mode==LfsrStepMode.GALOIS_XNOR)) startState=0;
        for(int i=startState;i<done.length;i++){
            if(done[i]) continue;
            boolean[] initState = Z2.toBooleans(i);
            done[i] = true;
            setState(initState);
            boolean[] trace = new boolean[1<<l];//used to detects loops
            int j=0;
            boolean[][] states = null;
            if(storeStates) {
                states = new boolean[maxLen+1][];
                states[j] = new boolean[l];
                Z2.copy(state, states[j]);
            }
            do{
                seq[j++]=step();
                if(storeStates) {
                    states[j] = new boolean[l];
                    Z2.copy(state,states[j]);
                }
                int coveredState = Z2.booleansToInt(getState());
                if(trace[coveredState]) {
                    if(!Z2.equalValue(state,initState) && !Z2.equal(nullState,state)) {//this sequence has a b shape: go straight and then loop
                        i=coveredState-1;//launch next computation to get the sequence with the loop only.
                        done[coveredState] = false;
                    }
                    break;
                }
                done[coveredState] = true;
                trace[coveredState] = true;
            }while((!Z2.equalValue(state, initState)) && (!Z2.equal(nullState,state)));
            if(storeStates) out.put(Arrays.copyOfRange(seq,0,j),Arrays.copyOfRange(states,0,j));
            else  out.put(Arrays.copyOfRange(seq,0,j),null);
        }
        return out;
    }
    public boolean []sequence() {
        return sequence(-1);
    }
    public boolean []sequence(int len){
        int maxLen= 1<<l;
        if(len!=-1) maxLen = len;
        boolean[] seq = new boolean[maxLen];
        boolean[] initState = Arrays.copyOf(state,l);
        int i=0;
        do {
            seq[i++]=step();
            if(-1==len){
                if(Arrays.equals(state,initState)) break;
                if(Arrays.equals(state,nullState)) {
                    System.out.println("WARNING: null state reached");
                    break;
                }
            }else if(i==len) break;
        }while(true);
        return Arrays.copyOfRange(seq,0,i);
    }
}
