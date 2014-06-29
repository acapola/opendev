package uk.co.nimp;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class LfsrTest {

    @org.junit.Test
    public void testFromSequence() throws Exception {
        boolean debug=false;
        Random rng = new Random(0);
        int cornerCasesCnt=0;
        int maxTest=10;
        for(int i=0;i<maxTest;i++){
            boolean[] refSeq = new boolean[1+rng.nextInt(400)];
            for(int j=0;j<refSeq.length;j++) refSeq[j] = rng.nextBoolean();

            //System.out.println(toBinaryString(refSeq));
            Lfsr test = Lfsr.fromSequence(refSeq);
            boolean[] seq = test.sequence(refSeq.length);
            if(!Arrays.equals(refSeq, seq)) throw new RuntimeException("test fail for refSeq=\n"+Z2.toBinaryString(refSeq)+"\n"+Z2.toBinaryString(seq)+"\n");
            if(test.getWidth()==seq.length){
                cornerCasesCnt++;
                if(debug) System.out.println("corner case: "+Z2.toBinaryString(refSeq));
            }
        }
        if(debug) System.out.println(cornerCasesCnt+" corner cases out of "+maxTest+" cases.");
        switch(maxTest){
            case 10000: assert(cornerCasesCnt==42);break;
        }
    }

    @org.junit.Test
    public void testGetTapsString() throws Exception {
        String refTaps = "11001";
        Lfsr dut = Lfsr.fromBinaryString(refTaps);
        assert(dut.getTapsString().equals(refTaps));
    }

    @org.junit.Test
    public void testGetTaps() throws Exception {
        String refTaps = "1100110110001010101000101";
        Lfsr dut = Lfsr.fromBinaryString(refTaps);
        assert(Z2.toBinaryString(dut.getTaps()).equals(refTaps));
    }

    @org.junit.Test
    public void testFromTaps() throws Exception {
        Lfsr dut;
        dut = Lfsr.fromTaps(new boolean[]{true,true,false,false,true});
        assert(dut.getTapsString().equals("11001"));
        dut = Lfsr.fromTaps(new boolean[]{true,true,false,false,true,false});
        assert(dut.getTapsString().equals("110010"));
        dut = Lfsr.fromTaps(new boolean[]{true,false,true});
        assert(dut.getTapsString().equals("101"));
    }
    @Test
    public void testToPolynomial() throws Exception {
        assert(Z2.toPolynomial(Z2.toBooleans("1")).equals("1"));
        assert(Z2.toPolynomial(Z2.toBooleans("01")).equals("x"));
        assert(Z2.toPolynomial(Z2.toBooleans("11")).equals("1 + x"));
        assert(Z2.toPolynomial(Z2.toBooleans("111")).equals("1 + x + x2"));
        assert(Z2.toPolynomial(Z2.toBooleans("1111")).equals("1 + x + x2 + x3"));
        assert(Z2.toPolynomial(Z2.toBooleans("1010")).equals("1 + x2"));
    }

    @Test
    public void testGetPolynomial() throws Exception {
        //assert(Lfsr.fromBinaryString("1").getPolynomial().equals("1"));
        //assert(Lfsr.fromBinaryString("01").getPolynomial().equals("x"));
        assert(Lfsr.fromBinaryString("11").getPolynomial().equals("1 + x"));
        assert(Lfsr.fromBinaryString("111").getPolynomial().equals("1 + x + x2"));
        assert(Lfsr.fromBinaryString("1111").getPolynomial().equals("1 + x + x2 + x3"));
        assert(Lfsr.fromBinaryString("1010").getPolynomial().equals("1 + x2"));
        assert(Lfsr.fromTapsPositions(new int[]{9,11}).getPolynomial().equals("1 + x9 + x11"));
    }

    @Test
    public void testPolynomialToBooleans() throws Exception {
        assert(Z2.toPolynomial(Z2.polynomialToBooleans("")).equals("0"));
        assert(Z2.toPolynomial(Z2.polynomialToBooleans("0")).equals("0"));
        assert(Z2.toPolynomial(Z2.polynomialToBooleans("0x")).equals("0"));
        assert(Z2.toPolynomial(Z2.polynomialToBooleans("0x1")).equals("0"));
        assert(Z2.toPolynomial(Z2.polynomialToBooleans("0x2")).equals("0"));
        assert(Z2.toPolynomial(Z2.polynomialToBooleans("0x1+0x2")).equals("0"));
        assert(Z2.toPolynomial(Z2.polynomialToBooleans("1")).equals("1"));
        assert(Z2.toPolynomial(Z2.polynomialToBooleans("x0")).equals("1"));
        assert(Z2.toPolynomial(Z2.polynomialToBooleans("1x0")).equals("1"));
        assert(Z2.toPolynomial(Z2.polynomialToBooleans("x")).equals("x"));
        assert(Z2.toPolynomial(Z2.polynomialToBooleans("1x")).equals("x"));
        assert(Z2.toPolynomial(Z2.polynomialToBooleans("x2")).equals("x2"));
        assert(Z2.toPolynomial(Z2.polynomialToBooleans("1x2")).equals("x2"));
        assert(Z2.toPolynomial(Z2.polynomialToBooleans("1x2+1+x")).equals("1 + x + x2"));
        assert(Z2.toPolynomial(Z2.polynomialToBooleans("0+x2+0+1+1x+0")).equals("1 + x + x2"));
        assert(Z2.toPolynomial(Z2.polynomialToBooleans("x+ 1x2+ 1+ x")).equals("1 + x2"));
        assert(Z2.toPolynomial(Z2.polynomialToBooleans(" 1x4+1x5 + 1x234+\t1 + x5  ")).equals("1 + x4 + x234"));
    }



    @Test
    public void testIsMaximumLength() throws Exception {
        //reducible polynomials (since there is an even number of non zero terms, x+1 is a divisor):
        assert(!Lfsr.fromPolynomial("1+x").isMaximumLength());
        assert(!Lfsr.fromPolynomial("1+x2").isMaximumLength());
        assert(!Lfsr.fromPolynomial("1+x2+x3+x7").isMaximumLength());
        assert(!Lfsr.fromPolynomial("1+x2+x4+x9").isMaximumLength());

        //irreducible polynomials:
        assert(Lfsr.fromPolynomial("1+x+x2").isMaximumLength());
        assert(Lfsr.fromPolynomial("1+x+x3").isMaximumLength());
        assert(Lfsr.fromPolynomial("1+x+x4").isMaximumLength());
        assert(Lfsr.fromPolynomial("1+x2+x5").isMaximumLength());
        assert(Lfsr.fromPolynomial("1+x+x6").isMaximumLength());
    }

    @org.junit.Test
    public void testFromTapsPositions() throws Exception {
        Lfsr dut = Lfsr.fromTapsPositions(new int[]{9,11});
        Lfsr ref = Lfsr.fromBinaryString("100000000101");
        assert(dut.getWidth()==ref.getWidth());
        assert(dut.getStateString().equals(ref.getStateString()));
        assert(dut.getTapsString().equals(ref.getTapsString()));
        assert(Z2.toBinaryString(dut.sequence()).equals(Z2.toBinaryString(ref.sequence())));
    }

    @org.junit.Test
    public void testSequence() throws Exception {
        Lfsr dut = Lfsr.fromBinaryString("11001");
        String seq = Z2.toBinaryString(dut.sequence());
        assert("100011110101100".equals(seq));
    }
}