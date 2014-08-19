package uk.co.nimp;

import java.math.BigInteger;

/**
 * Created by seb on 8/17/14.
 */
public class Sbox4 {

    public static BigInteger factorial(BigInteger in){
        BigInteger i = BigInteger.valueOf(2);
        BigInteger out = BigInteger.ONE;
        while(i.compareTo(in)<0){
            out = out.multiply(i);
            i=i.add(BigInteger.ONE);
        }
        return out;
    }

    public static int combinations(int groupSize, int sampleSize){
        BigInteger nFact = factorial(BigInteger.valueOf(groupSize));
        BigInteger kFact = factorial(BigInteger.valueOf(sampleSize));
        BigInteger nMinusKFact = factorial(BigInteger.valueOf(groupSize-sampleSize));
        return nFact.divide(kFact).divide(nMinusKFact).intValue();
    }

    public static void countFromInputs(){
        final String[] gatesTypes = {"and","andna","andnb","or","orna","ornb","xor"};
        final int nGateTypes = gatesTypes.length;
        int inputs= 4;
        int crossPoints;
        int outputs;
        int gates=0;
        int maxLevel=2;
        for(int level=0;level<maxLevel;level++){
            crossPoints=combinations(inputs,2);
            outputs=crossPoints*nGateTypes;
            gates+=outputs;
            System.out.println("level "+level+": "+outputs+" new nets, "+gates+" gates in total");
            inputs+=outputs;
        }
    }

    public static int countGatesFromOutput(int maxLevel){
        int inputs=0;

        int outputs=1;
        int gates=0;
        for(int level=0;level<maxLevel;level++){
            int newGates=outputs;
            int newInputs = newGates*2;
            gates+=newGates;
            System.out.println("level "+level+": "+newInputs+" inputs, "+newGates+" gates, "+gates+" gates in total");
            outputs = newInputs;
        }
        return gates;
    }

    public static long countCiruits(int nGates){
        final String[] gatesTypes = {"null","a","b","na","nb","and","andna","andnb","nand","or","orna","ornb","nor","xor","xnor"};
        final int nGateTypes = gatesTypes.length;
        BigInteger nCases = BigInteger.valueOf(nGates).pow(nGateTypes);
        System.out.println("Number of possible circuits with "+nGates+" gates: "+nCases+" ("+nCases.bitLength()+" bits search space)");
        return nCases.longValue();
    }

    public static void main(String []args){
        int nGates = countGatesFromOutput(4);
        long nCases = countCiruits(nGates);

    }
}
