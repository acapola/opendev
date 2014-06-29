package uk.co.nimp;

import java.math.BigInteger;

/**
 * Created by seb on 6/29/14.
 */
public class OffsetScrambler implements Scrambler {
    final int inputRange;
    final int inputWidth;
    final int keyWidth;
    final BigInteger inputRangeBi;

    public OffsetScrambler(int inputRange) {
        this.inputRange = inputRange;
        inputWidth = Z2.bitWidth(inputRange);
        keyWidth=inputWidth;
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
        BigInteger keyBi = Z2.booleansToBigInteger(key);
        BigInteger out = inBi.add(keyBi).mod(inputRangeBi);
        return Z2.toBooleans(out,keyWidth);
    }
}
