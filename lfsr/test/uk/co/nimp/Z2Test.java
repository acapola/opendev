package uk.co.nimp;

import org.junit.Test;

import java.io.File;
import java.util.Random;

public class Z2Test {
    @Test
    public void testBitWidth() throws Exception {
        assert(Z2.bitWidth(1)==1);
        assert(Z2.bitWidth(2)==2);
        assert(Z2.bitWidth(3)==2);
        assert(Z2.bitWidth(4)==3);
        assert(Z2.bitWidth(5)==3);
        assert(Z2.bitWidth(6)==3);
        assert(Z2.bitWidth(7)==3);
        assert(Z2.bitWidth(8)==4);
        assert(Z2.bitWidth(31)==5);
        assert(Z2.bitWidth(32)==6);
        assert(Z2.bitWidth(0)==1);
    }

    @Test
    public void testAdd() throws Exception {
        boolean[] a = Z2.polynomialToBooleans("x2+x");
        boolean[] b = Z2.polynomialToBooleans("x3+x+1");
        boolean[] sum = Z2.polynomialToBooleans("x3+x2+1");
        assert(Z2.equal(sum,Z2.add(a,b)));
    }

    @Test
    public void testMul() throws Exception {
        boolean[] a = Z2.polynomialToBooleans("x2+x");
        boolean[] b = Z2.polynomialToBooleans("x3+x+1");
        boolean[] product = Z2.polynomialToBooleans("x5+x4+x3+x");
        assert(Z2.equal(product,Z2.mul(a,b)));
    }

    @Test
    public void testHammingWeight() throws Exception {
        assert(0==Z2.hammingWeight(Z2.ZERO));
        assert(1==Z2.hammingWeight(Z2.ONE));
        assert(1==Z2.hammingWeight(Z2.TWO));
        assert(4==Z2.hammingWeight(Z2.polynomialToBooleans("x5+x4+x3+x")));
        assert(5==Z2.hammingWeight(Z2.polynomialToBooleans("x5+x4+x3+x+x234")));
    }

    @Test
    public void testMinHammingWeight() throws Exception {
        boolean [][]test = new boolean[5][];
        test[0] = Z2.ZERO;
        test[1] = Z2.polynomialToBooleans("x4+x3+x");
        test[2] = Z2.polynomialToBooleans("x5+x4+x2");
        test[3] = Z2.polynomialToBooleans("x5+x3+1");
        test[4] = Z2.polynomialToBooleans("x654+x4+1");
        //System.out.println(Z2.minHammingDistance(test));
        assert(3==Z2.minHammingDistance(test));
        test[0] = Z2.ONE;
        //System.out.println(Z2.minHammingDistance(test));
        assert(2==Z2.minHammingDistance(test));
        test[0] = Z2.polynomialToBooleans("x65+x41+x23+x2");
        //System.out.println(Z2.minHammingDistance(test));
        assert(4==Z2.minHammingDistance(test));
    }

    @Test
    public void testBinaryStringFileToBooleansArray() throws Exception {
        int nTests = 100;
        int maxLen = 50;
        int minWidth = 0;
        int maxWidth = 100;
        Random rng = new Random();
        for(int i=0;i<nTests;i++){
            int len = rng.nextInt(maxLen);
            boolean[][] dat = Z2.randomBooleansArray(len,minWidth,maxWidth);
            File tmp = File.createTempFile("Z2Test","testBinaryStringFileToBooleansArray");
            Z2.toBinaryStringFile(tmp,dat);
            boolean[][] check = Z2.binaryStringFileToBooleansArray(tmp);
            assert(check.length==dat.length);
            for(int j=0;j<dat.length;j++) {
                assert(Z2.equal(dat[j],check[j]));
            }
        }
    }

        @Test
    public void testIsGreater() throws Exception {
        boolean[] a = Z2.polynomialToBooleans("x2+x");
        boolean[] b = Z2.polynomialToBooleans("x3+x+1");
        assert(!Z2.isGreater(a,a));
        assert(!Z2.isGreater(a,b));
        assert(Z2.isGreater(b,a));
    }
    @Test
    public void testIsGreaterOrEqual() throws Exception {
        boolean[] a = Z2.polynomialToBooleans("x2+x");
        boolean[] b = Z2.polynomialToBooleans("x3+x+1");
        assert(Z2.isGreaterOrEqual(a,a));
        assert(!Z2.isGreater(a,b));
        assert(Z2.isGreater(b,a));
    }
    @Test
    public void testDiv() throws Exception {
        boolean[] a = Z2.polynomialToBooleans("x2+x");
        boolean[] b = Z2.polynomialToBooleans("x3+x+1");
        boolean[] product = Z2.polynomialToBooleans("x5+x4+x3+x");
        assert(Z2.equal(b,Z2.div(product,a)));
        assert(Z2.equal(a,Z2.div(product,b)));
        boolean[] pa = Z2.add(a,product);
        assert(Z2.equal(a,Z2.div(pa,b)));
    }
    @Test
    public void testMod() throws Exception {
        boolean[] a = Z2.polynomialToBooleans("x2+x");
        boolean[] b = Z2.polynomialToBooleans("x3+x+1");
        boolean[] product = Z2.polynomialToBooleans("x5+x4+x3+x");
        assert(Z2.equal(Z2.ZERO,Z2.mod(product,a)));
        assert(Z2.equal(Z2.ZERO,Z2.mod(product,b)));
        boolean[] pa = Z2.add(a,product);
        assert(Z2.equal(a,Z2.mod(pa,b)));
    }

    @Test
    public void testGcd() throws Exception {
        boolean[] gx = Z2.polynomialToBooleans("x10+x9+x8+x6+x5+x4+1");
        boolean[] hx = Z2.polynomialToBooleans("x9+x6+x5+x3+x2+1");
        assert(Z2.equal(Z2.gcd(gx,hx),Z2.polynomialToBooleans("x3+x+1")));
        assert(Z2.equal(Z2.gcd(gx,hx),Z2.gcd(hx,gx)));
    }

    @Test
    public void testIsIrreducible() throws Exception {
        //reducible polynomials (since there is an even number of non zero terms, x+1 is a divisor):
        assert(!Z2.isIrreducible(Z2.polynomialToBooleans("1+x")));
        assert(!Z2.isIrreducible(Z2.polynomialToBooleans("1+x2")));
        assert(!Z2.isIrreducible(Z2.polynomialToBooleans("1+x2+x3+x7")));
        assert(!Z2.isIrreducible(Z2.polynomialToBooleans("1+x2+x4+x9")));

        //irreducible polynomials:
        assert(Z2.isIrreducible(Z2.polynomialToBooleans("1+x+x2")));
        assert(Z2.isIrreducible(Z2.polynomialToBooleans("1+x+x3")));
        assert(Z2.isIrreducible(Z2.polynomialToBooleans("1+x+x4")));
        assert(Z2.isIrreducible(Z2.polynomialToBooleans("1+x2+x5")));
        assert(Z2.isIrreducible(Z2.polynomialToBooleans("1+x+x6")));
        assert(Z2.isIrreducible(Z2.polynomialToBooleans("x13+x12+x11+x8+1")));
        assert(Z2.isIrreducible(Z2.polynomialToBooleans("x16+x14+x13+x11+1")));
        assert(Z2.isIrreducible(Z2.polynomialToBooleans("x19+x18+x17+x14+1")));
    }
}