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
    final boolean [] nullState;
    boolean[] state;//stage0, stage1, ...,stage L-1.

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
    }
    public boolean step(){
        //System.out.print(getTapsString()+" "+getStateString()+"->");
        boolean out = state[0];
        boolean sj=taps[0] & state[0];
        for(int i=1;i<l;i++){
            sj ^= taps[i] & state[i];
            state[i-1]=state[i];
        }
        state[l-1]=sj;
        //System.out.println(getStateString());
        return out;
    }
    /**
     * Build the ARG_LFSR which generate a given bit sequence. See "Berlekamp-Massey algorithm"
     * @param seq: a bit sequence with at least one bit set to 1
     * @return the minimal ARG_LFSR to generate the seq
     */
    public static Lfsr fromSequence(boolean[] seq){
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
        Lfsr out = new Lfsr(c,l+1);
        //System.out.println(out);
        //set the state so that the ARG_LFSR start to generate the desired sequence
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
        boolean[] fx = getTaps();
        return Z2.isPrimitive(fx);
    }
    public boolean isPolynomialIrreducible(){
        boolean[] fx = getTaps();
        return Z2.isIrreducible(fx);
    }
    public boolean isPolynomialPrimitive(){
        boolean[] fx = getTaps();
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
        String properties = "not maximum length)";
        if(isMaximumLength()) properties = "maximum length LFSR: period of "+((1<<l)-1)+")";
        else{
            if(isPolynomialIrreducible()) properties = "irreducible but not primitive --> "+properties;
            else properties = "reducible --> "+properties;
        }
        if(isSingular()) properties = "(Singular, "+properties;
        else properties = "(Non singular, "+properties;
        return "Lfsr{" +
                "taps=" + getTapsString() +
                ", state=" + getStateString() +
                ", "+properties+
                '}';
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
        for(int i=1;i<done.length;i++){
            if(done[i]) continue;
            boolean[] initState = Z2.toBooleans(i);
            done[i] = true;
            setState(initState);
            boolean[] trace = new boolean[1<<l];//used to detects loops
            int j=0;
            boolean[][] states = null;
            if(storeStates) {
                states = new boolean[maxLen][];
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
                    if(!Z2.equalValue(state,initState) && !Z2.isZero(state)) {//this sequence has a b shape: go straight and then loop
                        i=coveredState-1;//launch next computation to get the sequence with the loop only.
                        done[coveredState] = false;
                    }
                    break;
                }
                done[coveredState] = true;
                trace[coveredState] = true;
            }while((!Z2.equalValue(state, initState)) && (!Z2.isZero(state)));
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
