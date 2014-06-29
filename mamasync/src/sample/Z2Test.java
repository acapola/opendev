package sample;

import org.junit.Test;

import static org.junit.Assert.*;

public class Z2Test {

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