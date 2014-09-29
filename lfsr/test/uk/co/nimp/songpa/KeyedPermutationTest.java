package uk.co.nimp.songpa;

import org.junit.Assert;
import org.junit.Test;
import uk.co.nimp.Z2;

import java.math.BigInteger;
import java.util.HashSet;

public class KeyedPermutationTest {

    static void assertTrue(String message, boolean predicate) {
        assertEquals(message, true, predicate);
    }

    static void assertFalse(String message, boolean predicate) {
        assertEquals(message, false, predicate);
    }

    static void assertEquals(String message, boolean expected, boolean actual) {
        int e = Z2.toInt(expected);
        int a = Z2.toInt(actual);
        Assert.assertEquals(message, e, a);
    }

    static long factorial(long in) {
        if (in == 1) return in;
        return in * factorial(in - 1);
    }

    @Test
    public void testPermute() throws Exception {
        for (int nUnit = 3; nUnit < 7; nUnit++) {
            int unit = 4;
            int width = nUnit * unit;
            KeyedPermutation p = KeyedPermutation.create(width, unit);
            int expectedNumberOfPermutation = (int) factorial(nUnit);
            int keySize = p.getKeySize();
            long kMax = 1L << keySize;
            boolean[] in = new boolean[width];
            for (int i = 0; i < nUnit; i++) {
                boolean[] idx = Z2.toBooleans(i, unit);
                System.arraycopy(idx, 0, in, i * unit, unit);
            }
            System.out.println(Z2.toHexByteString(in));
            HashSet<BigInteger> outputs = new HashSet<BigInteger>();
            for (long k = 0; k < kMax; k++) {
                boolean[] key = Z2.toBooleans(k, keySize);
                boolean[] out = p.apply(in, key);
                outputs.add(Z2.booleansToBigInteger(out));
                System.out.println(Z2.toHexByteString(out));
                boolean[] recovered = p.unapply(out, key);
                assert (Z2.equal(in, recovered));
            }
            assert (outputs.size() == expectedNumberOfPermutation);
        }
    }

    @Test
    public void testPermuteDbg() throws Exception {
        KeyedPermutation p = KeyedPermutation.create(32, 1);
        boolean[] in = Z2.hexBytesToBooleans("B471B51B",32);
        boolean[] key = Z2.hexBytesToBooleans("A9A57E3D1C39EACDFB97969CD6AD687701",129);
        boolean[] out = p.apply(in,key);
        boolean[] expectedOut = Z2.hexBytesToBooleans("F604577C",32);
        assert(Z2.equal(expectedOut,out));
        boolean[] plain = p.unapply(out,key);
        assert(Z2.equal(plain,in));
    }
}