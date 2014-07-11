package uk.co.nimp;

import java.math.BigInteger;

/**
 * Created by seb on 6/29/14.
 */
public class Scrambler_5_64 implements Scrambler {
    final int inputRange;
    final int inputWidth;
    final int keyWidth;
    final BigInteger inputRangeBi;
    final boolean[] keyMask;
    public Scrambler_5_64() {
        this.inputRange = 32;
        inputWidth = Z2.bitWidth(inputRange);
        keyWidth=34*5;
        inputRangeBi = BigInteger.valueOf(inputRange);
        keyMask = Z2.randomBooleans(keyWidth,0);
    }

    @Override
    public int getInputRange() {
        return inputRange;
    }

    @Override
    public int getInputWidth() {
        return inputWidth;
    }

    @Override
    public int getKeyWidth() {
        return keyWidth;
    }

    @Override
    public boolean[] scramble(boolean[] in, boolean[] key) {
        assert(in.length==inputWidth);
        assert(key.length==keyWidth);
        BigInteger inBi = Z2.booleansToBigInteger(in);
        assert(inBi.compareTo(inputRangeBi)<=0);

        key = Z2.xor(key,keyMask);
        boolean[] key1 = Z2.cloneRange(key,32*5,5);
        boolean[] key2 = Z2.cloneRange(key,33*5,5);
        boolean[] addKey1 = Z2.xor(in,key1,5);
        int oneHotBitPos = Z2.booleansToInt(addKey1);

        boolean[] taken = new boolean[32];
        int oneHotBitPermutedPos=-1;
        for(int muxIndex=0;muxIndex<32;muxIndex++){
            boolean[] muxKey = Z2.cloneRange(key,muxIndex*5,5);
            int inSel = Z2.booleansToInt(muxKey);
            while(taken[inSel]) inSel=(inSel+13)%32;
            taken[inSel]=true;
            if(oneHotBitPos==inSel) oneHotBitPermutedPos = inSel;
        }
        boolean[] permOut = Z2.toBooleans(oneHotBitPermutedPos);
        boolean[] out = Z2.xor(permOut,key2);

        return out;
    }
}
