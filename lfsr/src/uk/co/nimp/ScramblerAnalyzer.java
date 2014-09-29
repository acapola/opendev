package uk.co.nimp;

import java.math.BigInteger;
import java.util.HashSet;

/**
 * Created by seb on 6/29/14.
 */
public class ScramblerAnalyzer {

    final Scrambler dut;

    public ScramblerAnalyzer(Scrambler dut) {
        this.dut = dut;
    }
    public String inputBitsToOutputBitsAnalysis(){
        impactedBits = new int[dut.getInputWidth()];
        minBits = dut.getInputWidth();
        maxBits = 0;
        return inputBitsToOutputBitsAnalysis(new boolean[dut.getKeyWidth()],new boolean[dut.getInputWidth()]);
    }
    public String inputBitsToOutputBitsAnalysis(int trials){
        impactedBits = new int[dut.getInputWidth()];
        impactMap = new boolean[dut.getInputWidth()][dut.getInputWidth()];
        minBits = dut.getInputWidth();
        maxBits = 0;
        //String stats="";
        for(int i=0;i<trials;i++) {
            inputBitsToOutputBitsAnalysis(Z2.randomBooleans(dut.getKeyWidth()), Z2.randomBooleans(dut.getInputWidth()));
        }
        String out="Input to outputbit analysis over "+trials+" trials:\n";
        out+="Minimum number of impacted bits: "+minBits+"\n";
        out+="Maximum number of impacted bits: "+maxBits+"\nbit:           ";
        int warning=-1;
        for(int i=0;i<dut.getInputWidth();i++){
            out+=String.format("%4d",i);
        }
        out+="\ntotal impact:  ";
        for(int i=0;i<dut.getInputWidth();i++){
            int impacted=0;
            for(int j=0;j<dut.getInputWidth();j++){
                if(impactMap[i][j]) impacted++;
            }
            if(impacted!=dut.getInputWidth()) warning=i;
            out+=String.format("%4d",impacted);
        }
        if(warning!=-1) out+="\nWARNING at index "+warning;
        warning=-1;
        out+="\naverage impact:";
        for(int i=0;i<dut.getInputWidth();i++){
            int impact = (impactedBits[i]+(trials/2))/trials;
            if(impact!=dut.getInputWidth()/2) warning=i;
            out+=String.format("%4d",impact);
        }
        if(warning!=-1) out+="\nWARNING at index "+warning;
        out+="\n";

        return out;
    }
    int[] impactedBits;
    boolean [][] impactMap;
    int minBits;
    int maxBits;
    public String inputBitsToOutputBitsAnalysis(boolean[] key, boolean[] input){
        BigInteger inputBi = Z2.booleansToBigInteger(input);
        boolean[] refOut = dut.scramble(Z2.toBooleans(inputBi,dut.getInputWidth()),key);
        BigInteger refOutBi = Z2.booleansToBigInteger(refOut);
        BigInteger mask = BigInteger.ONE;
        for(int i=0;i<dut.getInputWidth();i++){
            BigInteger in = inputBi.xor(mask);
            boolean[] out = dut.scramble(Z2.toBooleans(in,dut.getInputWidth()),key);
            BigInteger outBi = Z2.booleansToBigInteger(out);
            BigInteger delta = outBi.xor(refOutBi);
            int nBits = delta.bitCount();
            impactedBits[i]+=nBits;
            for(int j=0;j<dut.getInputWidth();j++){
                if(delta.testBit(j)) impactMap[i][j]=true;
            }
            minBits = Math.min(minBits,nBits);
            maxBits = Math.max(maxBits,nBits);
            mask = mask.shiftLeft(1);
        }
        String out="Input to output bit analysis for:\n";
        out+="key   "+Z2.toBinaryString(key)+"\n";
        out+="input "+Z2.toBinaryString(input)+"\n";
        out+="Minimum number of impacted bits: "+minBits+"\n";
        out+="Maximum number of impacted bits: "+maxBits+"\nbit:   ";
        for(int i=0;i<dut.getInputWidth();i++){
            out+=String.format("%4d",i);
        }
        out+="\nimpact:";
        for(int i=0;i<dut.getInputWidth();i++){
            out+=String.format("%4d",impactedBits[i]);
        }
        out+="\n";
        return out;
    }
    int[] keyOutput(boolean[] key){
        if(key.length!=dut.getKeyWidth()) throw new RuntimeException("Wrong length for key");
        //int maxKeyRange = 1<<dut.getKeyWidth();
        int inputWidth = dut.getInputWidth();
        int[] out = new int[inputWidth];
        for(int in=0;in<inputWidth;in++){
            boolean[] inBits = Z2.toBooleans(in,inputWidth);
            boolean[] outBits = dut.scramble(inBits,key);
            out[in]=Z2.booleansToInt(outBits);
        }
        return out;
    }
    boolean equals(int[] a,int[]b){
        for(int i=0;i<a.length;i++){
            if(a[i]!=b[i]) return false;
        }
        return true;
    }
    public String effectiveKeyRangeAnalysis(){
        HashSet<int[]> uniqueMappings = new HashSet<int[]>();
        int keyWidth = dut.getKeyWidth();
        int maxKey = 1<<keyWidth;
        for(int key=0;key<maxKey;key++){
            int[] map = keyOutput(Z2.toBooleans(key,keyWidth));
            boolean exists = false;
            for(int[] existing: uniqueMappings){
                if(equals(map,existing)) {
                    exists = true;
                    break;
                }
            }
            if(!exists) uniqueMappings.add(map);
        }
        return uniqueMappings.size()+" effective keys";
    }

    public String effectiveKeyRangeEstimationAnalysis(int maxTrials){
        HashSet<int[]> uniqueMappings = new HashSet<int[]>();
        int keyWidth = dut.getKeyWidth();
        int maxKey = 1<<keyWidth;
        if(keyWidth>31) maxKey = maxTrials;
        int collisionCnt=0;
        for(int key=0;key<maxKey;key++){
            int[] map = keyOutput(Z2.randomBooleans(keyWidth,key));
            boolean exists = false;
            for(int[] existing: uniqueMappings){
                if(equals(map,existing)) {
                    exists = true;
                    collisionCnt++;
                    System.out.println(collisionCnt+" collision found so far out of "+key+" trials");
                    break;
                }
            }
            if(!exists) {
                uniqueMappings.add(map);
                if((key%1000)==0) System.out.println(collisionCnt+" collision found so far out of "+key+" trials");
            }
        }
        return uniqueMappings.size()+" effective keys out of "+maxKey+": effectiveness ratio is "+(uniqueMappings.size()*100)/maxKey+"%";
    }

    public static void main(String[] args){
        ScramblerAnalyzer scramblerAnalyzer = new ScramblerAnalyzer(new Scrambler_5_64());
        System.out.println(scramblerAnalyzer.inputBitsToOutputBitsAnalysis());
        //System.out.println(scramblerAnalyzer.effectiveKeyRangeAnalysis());
        System.out.println(scramblerAnalyzer.effectiveKeyRangeEstimationAnalysis(1<<16));
    }
}
