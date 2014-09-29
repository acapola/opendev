package uk.co.nimp.songpa;

import uk.co.nimp.Z2;

import java.io.PrintStream;

/**
 * Created by seb on 9/28/14.
 */
public abstract class Cipher {
    int width;
    int innerRounds;
    int outterRounds;
    int keySize;
    K k;
    P p;
    M m;
    X x;
    S s;
    int lastP=0;//tweaking parameter: set to 1 to do same number of full outter rounds before and after the inner rounds.
    boolean printIntermediate=false;
    PrintStream spy=null;
    String indent="";
    public void setSpy(PrintStream spy) {
        this.spy = spy;
        printIntermediate= null!=spy;
    }
    void print(String msg){
        if(printIntermediate) spy.print(indent+msg);
    }
    void println(String msg){
        if(printIntermediate) spy.println(indent+msg);
    }
    void printSeparator(){
        println("----------------------------------------------------------------------------------------------------");
    }
    void incrIndent(){
        indent+="   ";
    }
    void decrIndent(){
        if(indent.length()>0)
            indent=indent.substring(3);
    }

    public boolean[] getKey() {
        return key;
    }

    public void setKey(boolean[] key) {
        this.key = key;
    }

    boolean[] key;
    boolean[][] keySchedule;
    Integer nRounds=null;

    public int getnRounds(){
        if(null==nRounds) nRounds = 2*outterRounds+innerRounds+lastP;
        return nRounds;
    }
    boolean[] outterRound(boolean[] in, boolean lastRound){
        incrIndent();
        boolean[] roundKey = k.step();
        println("roundKey: "+Z2.toHexByteString(roundKey));
        boolean[] pKey = new boolean[p.getKeySize()];
        System.arraycopy(roundKey,0,pKey,0,p.getKeySize());
        boolean[] xKey = new boolean[x.getKeySize()];
        System.arraycopy(roundKey, p.getKeySize(), xKey, 0, x.getKeySize());
        if(lastRound) println("pKey: "+Z2.toHexByteString(pKey));
        else println("pKey: "+Z2.toHexByteString(pKey)+", xKey: "+Z2.toHexByteString(xKey));
        boolean[] pOut = p.apply(in, pKey);
        println("pOut: "+Z2.toHexByteString(pOut));
        if(lastRound) {
            decrIndent();
            return pOut;
        }
        boolean[] mOut = m.apply(pOut);
        println("mOut: "+Z2.toHexByteString(mOut));
        boolean[] xOut = x.apply(mOut,xKey);
        println("xOut: "+Z2.toHexByteString(xOut));
        decrIndent();
        return xOut;
    }
    boolean[] innerRound(boolean[] in, boolean lastRound){
        boolean[] sIn = outterRound(in, lastRound);
        boolean[] sOut = s.apply(sIn);
        incrIndent();
        println("sOut: "+Z2.toHexByteString(sOut));
        decrIndent();
        return sOut;
    }
    public boolean[] encrypt(boolean[] in){
        printSeparator();
        println("Encrypt " + Z2.toHexByteString(in) + " with key " + Z2.toHexByteString(key));
        k.setSeed(key);
        boolean[] state=in.clone();
        int maxRounds = getnRounds()-1;
        int round=0;
        for(int i=0;i<outterRounds;i++){
            println("Round " + round);
            state = outterRound(state,false);
            round++;
        }
        for(int i=0;i<innerRounds;i++){
            println("Round "+round+ "(inner round)");
            state = innerRound(state, round == maxRounds);
            round++;
        }
        for(int i=0;i<outterRounds+lastP;i++){
            println("Round "+round);
            state = outterRound(state,round==maxRounds);
            round++;
        }
        println("Cipher text: "+Z2.toHexByteString(state));
        return state;
    }
    boolean[] decOutterRound(boolean[] in, int round){
        incrIndent();
        boolean[] roundKey = keySchedule[nRounds-round-1];
        println("roundKey: "+Z2.toHexByteString(roundKey));
        boolean[] pKey = new boolean[p.getKeySize()];
        System.arraycopy(roundKey,0,pKey,0,p.getKeySize());
        boolean[] mOut;
        if(round>0) {
            boolean[] xKey = new boolean[x.getKeySize()];
            System.arraycopy(roundKey, p.getKeySize(), xKey, 0, x.getKeySize());
            println("pKey: "+Z2.toHexByteString(pKey)+", xKey: "+Z2.toHexByteString(xKey));
            boolean[] xOut = x.apply(in, xKey);
            println("xOut: "+Z2.toHexByteString(xOut));
            mOut = m.unapply(xOut);
            println("mOut: "+Z2.toHexByteString(mOut));
        } else {
            println("pKey: "+Z2.toHexByteString(pKey));
            mOut=in;
        }
        boolean[] pOut = p.unapply(mOut, pKey);
        println("pOut: "+Z2.toHexByteString(pOut));
        decrIndent();
        return pOut;
    }
    boolean[] decInnerRound(boolean[] in, int round){
        boolean[] sOut;
        if(round>0) {
            sOut = s.unapply(in);
            incrIndent();
            println("sOut: "+Z2.toHexByteString(sOut));
            decrIndent();
        } else {
            sOut = in;
        }
        boolean[] out = decOutterRound(sOut, round);
        return out;
    }
    void computeKeySchedule(){
        k.setSeed(key);
        keySchedule = new boolean[getnRounds()][p.getKeySize()+x.getKeySize()];
        for(int i=0;i<nRounds;i++){
            keySchedule[i] = k.step();
        }
    }
    public boolean[] decrypt(boolean[] in){
        printSeparator();
        println("Decrypt "+Z2.toHexByteString(in)+" with key "+Z2.toHexByteString(key));
        boolean[] state=in.clone();
        computeKeySchedule();
        int round=0;
        for(int i=0;i<outterRounds+lastP;i++){
            println("Round "+round);
            state = decOutterRound(state, round);
            round++;
        }
        for(int i=0;i<innerRounds;i++){
            println("Round "+round+ "(inner round)");
            state = decInnerRound(state, round);
            round++;
        }
        for(int i=0;i<outterRounds;i++){
            println("Round "+round);
            state = decOutterRound(state, round);
            round++;
        }
        println("Cipher text: "+Z2.toHexByteString(state));
        return state;
    }
    public byte[] encrypt(byte[] in){
        boolean[] inB = Z2.toBooleans(in);
        boolean[] outB = encrypt(inB);
        return Z2.booleansToBytes(outB);
    }
    public byte[] decrypt(byte[] in){
        boolean[] inB = Z2.toBooleans(in);
        boolean[] outB = encrypt(inB);
        return Z2.booleansToBytes(outB);
    }
    public int getKeySize() {
        return keySize;
    }
}
