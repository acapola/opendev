package uk.co.nimp;

import java.math.BigInteger;

/**
 * Created by seb on 6/29/14.
 */
public class DpeScrambler implements Scrambler {
    final int inputRange;
    final int inputWidth;
    final int keyWidth;
    final BigInteger inputRangeBi;

    public DpeScrambler() {
        this.inputRange = 13*16;
        inputWidth = Z2.bitWidth(inputRange);
        keyWidth=12;
        inputRangeBi = BigInteger.valueOf(inputRange);
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

        boolean[] key1 = Z2.cloneRange(key,0,4);
        boolean[] key2 = Z2.cloneRange(key,4,4);
        boolean[] addKey1 = Z2.xor(in,key1,4);
        boolean[] sub1 = Z2.substitute(addKey1,sbox1);
        boolean[] d = Z2.cloneRange(in,3,5);

        d[0] = sub1[3];
        int oneHotBitPos = Z2.booleansToInt(d);
        int permuted = (oneHotBitPos + Z2.booleansToInt(key2)) % (13*2);
        boolean[] e = Z2.toBooleans(permuted,5);
        boolean[] out = new boolean[inputWidth];
        System.arraycopy(sub1,0,out,0,3);
        System.arraycopy(e,0,out,3,5);
        return out;
    }
    static final boolean[][] sbox1;
    static {
        sbox1 = new boolean[16][4];
        for(int i=0;i<16;i++){
            sbox1[i] = Z2.toBooleans((47-i)%16,4);
        }
    }
}
