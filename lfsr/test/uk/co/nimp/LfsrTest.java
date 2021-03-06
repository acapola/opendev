package uk.co.nimp;

import org.junit.Test;

import java.math.BigInteger;
import java.util.*;

public class LfsrTest {

    @org.junit.Test
    public void testFromSequence() throws Exception {
        boolean debug = false;
        Random rng = new Random(0);
        int cornerCasesCnt = 0;
        int maxTest = 10;
        for (int i = 0; i < maxTest; i++) {
            boolean[] refSeq = new boolean[1 + rng.nextInt(400)];
            for (int j = 0; j < refSeq.length; j++) refSeq[j] = rng.nextBoolean();

            //System.out.println(toBinaryString(refSeq));
            Lfsr test = Lfsr.fromSequence(refSeq);
            boolean[] seq = test.sequence(refSeq.length);
            if (!Arrays.equals(refSeq, seq)) {
                System.out.println(test.describe(false, false));
                throw new RuntimeException("test fail for refSeq=\n" + Z2.toBinaryString(refSeq) + "\n" + Z2.toBinaryString(seq) + "\n");
            }
            if (test.getWidth() == seq.length) {
                cornerCasesCnt++;
                if (debug) System.out.println("corner case: " + Z2.toBinaryString(refSeq));
            }
        }
        if (debug) System.out.println(cornerCasesCnt + " corner cases out of " + maxTest + " cases.");
        switch (maxTest) {
            case 10000:
                assert (cornerCasesCnt == 42);
                break;
        }
/*
        Lfsr ref = Lfsr.fromPolynomial("1+x+x4");
        boolean[] refSeq = ref.sequence();
        boolean[] refSeqComplemented = Z2.complement(refSeq);
        Lfsr test = Lfsr.fromSequence(refSeqComplemented);
        //System.out.println(ref.getPolynomial()+": complemented sequence generated by "+test.getPolynomial());
        System.out.println(ref.describe(true, true));
        //System.out.println(test.describe(true, true) + "\n");
        ref.setMode(Lfsr.LfsrStepMode.MODMUL);
        System.out.println(ref.describe(true, true));
        //ref.setMode(Lfsr.LfsrStepMode.GALOIS);
        ref=ref.reversedSequenceLfsr().setMode(Lfsr.LfsrStepMode.GALOIS_XNOR);
        System.out.println(ref.describe(true, true));
*/
    }

    @org.junit.Test
    public void testGetTapsString() throws Exception {
        String refTaps = "11001";
        Lfsr dut = Lfsr.fromBinaryString(refTaps);
        assert (dut.getTapsString().equals(refTaps));
    }

    @org.junit.Test
    public void testGetTaps() throws Exception {
        String refTaps = "1100110110001010101000101";
        Lfsr dut = Lfsr.fromBinaryString(refTaps);
        assert (Z2.toBinaryString(dut.getTaps()).equals(refTaps));
    }

    @org.junit.Test
    public void testFromTaps() throws Exception {
        Lfsr dut;
        dut = Lfsr.fromTaps(new boolean[]{true, true, false, false, true});
        assert (dut.getTapsString().equals("11001"));
        dut = Lfsr.fromTaps(new boolean[]{true, true, false, false, true, false});
        assert (dut.getTapsString().equals("110010"));
        dut = Lfsr.fromTaps(new boolean[]{true, false, true});
        assert (dut.getTapsString().equals("101"));
    }

    @Test
    public void testToPolynomial() throws Exception {
        assert (Z2.toPolynomial(Z2.toBooleans("1")).equals("1"));
        assert (Z2.toPolynomial(Z2.toBooleans("01")).equals("x"));
        assert (Z2.toPolynomial(Z2.toBooleans("11")).equals("1 + x"));
        assert (Z2.toPolynomial(Z2.toBooleans("111")).equals("1 + x + x2"));
        assert (Z2.toPolynomial(Z2.toBooleans("1111")).equals("1 + x + x2 + x3"));
        assert (Z2.toPolynomial(Z2.toBooleans("1010")).equals("1 + x2"));
    }

    @Test
    public void testGetPolynomial() throws Exception {
        //assert(Lfsr.fromBinaryString("1").getPolynomial().equals("1"));
        //assert(Lfsr.fromBinaryString("01").getPolynomial().equals("x"));
        assert (Lfsr.fromBinaryString("11").getPolynomial().equals("1 + x"));
        assert (Lfsr.fromBinaryString("111").getPolynomial().equals("1 + x + x2"));
        assert (Lfsr.fromBinaryString("1111").getPolynomial().equals("1 + x + x2 + x3"));
        assert (Lfsr.fromBinaryString("1010").getPolynomial().equals("1 + x2"));
        assert (Lfsr.fromTapsPositions(new int[]{9, 11}).getPolynomial().equals("1 + x9 + x11"));
    }

    @Test
    public void testPolynomialToBooleans() throws Exception {
        assert (Z2.toPolynomial(Z2.polynomialToBooleans("")).equals("0"));
        assert (Z2.toPolynomial(Z2.polynomialToBooleans("0")).equals("0"));
        assert (Z2.toPolynomial(Z2.polynomialToBooleans("0x")).equals("0"));
        assert (Z2.toPolynomial(Z2.polynomialToBooleans("0x1")).equals("0"));
        assert (Z2.toPolynomial(Z2.polynomialToBooleans("0x2")).equals("0"));
        assert (Z2.toPolynomial(Z2.polynomialToBooleans("0x1+0x2")).equals("0"));
        assert (Z2.toPolynomial(Z2.polynomialToBooleans("1")).equals("1"));
        assert (Z2.toPolynomial(Z2.polynomialToBooleans("x0")).equals("1"));
        assert (Z2.toPolynomial(Z2.polynomialToBooleans("1x0")).equals("1"));
        assert (Z2.toPolynomial(Z2.polynomialToBooleans("x")).equals("x"));
        assert (Z2.toPolynomial(Z2.polynomialToBooleans("1x")).equals("x"));
        assert (Z2.toPolynomial(Z2.polynomialToBooleans("x2")).equals("x2"));
        assert (Z2.toPolynomial(Z2.polynomialToBooleans("1x2")).equals("x2"));
        assert (Z2.toPolynomial(Z2.polynomialToBooleans("1x2+1+x")).equals("1 + x + x2"));
        assert (Z2.toPolynomial(Z2.polynomialToBooleans("0+x2+0+1+1x+0")).equals("1 + x + x2"));
        assert (Z2.toPolynomial(Z2.polynomialToBooleans("x+ 1x2+ 1+ x")).equals("1 + x2"));
        assert (Z2.toPolynomial(Z2.polynomialToBooleans(" 1x4+1x5 + 1x234+\t1 + x5  ")).equals("1 + x4 + x234"));
    }


    @Test
    public void testIsMaximumLength() throws Exception {
        //reducible polynomials (since there is an even number of non zero terms, x+1 is a divisor):
        assert (!Lfsr.fromPolynomial("1+x2").isMaximumLength());
        assert (!Lfsr.fromPolynomial("1+x2+x3+x7").isMaximumLength());
        assert (!Lfsr.fromPolynomial("1+x2+x4+x9").isMaximumLength());

        //irreducible polynomials:
        assert (Lfsr.fromPolynomial("1+x").isMaximumLength());
        assert (Lfsr.fromPolynomial("1+x+x2").isMaximumLength());
        assert (Lfsr.fromPolynomial("1+x+x3").isMaximumLength());
        assert (Lfsr.fromPolynomial("1+x+x4").isMaximumLength());
        assert (Lfsr.fromPolynomial("1+x2+x5").isMaximumLength());
        assert (Lfsr.fromPolynomial("1+x+x6").isMaximumLength());
    }

    @Test
    public void testIsPolynomialIrreducible() throws Exception {
        assert (!Lfsr.fromPolynomial("1 + x3 + x6 + x7 + x10 + x12 + x14 + x15 + x17 + x18 + x19 + x20 + x21 + x23 + x25 + x26 + x28 + x29 + x30 + x31 + x32").isPolynomialIrreducible());

    }

    @org.junit.Test
    public void testFromTapsPositions() throws Exception {
        Lfsr dut = Lfsr.fromTapsPositions(new int[]{9, 11});
        Lfsr ref = Lfsr.fromBinaryString("100000000101");
        assert (dut.getWidth() == ref.getWidth());
        assert (dut.getStateString().equals(ref.getStateString()));
        assert (dut.getTapsString().equals(ref.getTapsString()));
        assert (Z2.toBinaryString(dut.sequence()).equals(Z2.toBinaryString(ref.sequence())));
    }

    @org.junit.Test
    public void testSequence() throws Exception {
        Lfsr dut = Lfsr.fromBinaryString("11001");
        String seq = Z2.toBinaryString(dut.sequence());
        assert ("100011110101100".equals(seq));

        /*//only valid for galois ? -> at least not valid for fibonacci as multiply sometimes modifies more than the lsb or msb...
        dut = Lfsr.fromPolynomial("1+x+x2+x5+x6");
        int degree = dut.l;
        System.out.println(dut.getPolynomial());
        boolean[] dutSeq = dut.sequence(2*degree);
        Lfsr dutReversed = Lfsr.fromSequence(Z2.reverse(dutSeq));
        System.out.println(dutReversed.getPolynomial());

        boolean[] fx = Z2.polynomialToBooleans(dut.getPolynomial());
        boolean[] s = Z2.shiftLeft(Z2.ONE,0);
        dutReversed.setState(Z2.ONE);
        assert(Z2.equalValue(s,dutReversed.getState()));
        for(int i=0;i<30;i++) {
            System.out.println(String.format("%2d",i)+": "+Z2.toBinaryString(Z2.cloneAndPad(s,degree))+", "+Z2.toBinaryString(dutReversed.getState())+" --> "+String.format("%25s, %25s",Z2.toPolynomial(s),Z2.toPolynomial(dutReversed.getState())));
            dutReversed.step();
            s = Z2.mod(Z2.mul(s, Z2.X), fx);

            //assert (Z2.equalValue(s, Z2.reverse(dutReversed.getState())));
        }*/

    }


    static void checkSequencesLengthEqual(String polynomial, int[][] expected) {
        Lfsr lfsr = Lfsr.fromPolynomial(polynomial);
        Map<BigInteger, BigInteger> actual = lfsr.sequencesLength();
        System.out.println(polynomial + " -> " + actual);
        assert (actual.size() == expected.length);
        for (int i = 0; i < expected.length; i++) {
            BigInteger len = BigInteger.valueOf(expected[i][0]);
            assert (actual.containsKey(len));
            assert (actual.get(len).intValue() == expected[i][1]);
        }
    }

    static void checkSequencesLength(boolean[] polynomial) {
        checkSequencesLength(Z2.toPolynomial(polynomial));
    }

    static void checkSequencesLength(String polynomial) {
        Lfsr lfsr = Lfsr.fromPolynomial(polynomial);
        Set<boolean[]> sequences = lfsr.sequences();
        Map<BigInteger, Integer> expected = new HashMap<BigInteger, Integer>();
        for (boolean[] s : sequences) {
            BigInteger len = BigInteger.valueOf(s.length);
            if (expected.containsKey(len)) expected.put(len, expected.get(len) + 1);
            else expected.put(len, 1);
        }
        Map<BigInteger, BigInteger> actual = lfsr.sequencesLength();
        if (actual.size() != expected.size()) {
            System.out.println(polynomial + " -> expected:" + expected);
            System.out.println(polynomial + " -> actual:  " + actual);
            System.out.flush();

            throw new RuntimeException();
        }
        for (BigInteger len : expected.keySet()) {
            if (!actual.containsKey(len)) {
                System.out.println(polynomial + " -> expected:" + expected);
                System.out.println(polynomial + " -> actual:  " + actual);
                System.out.flush();
            }
            int actualNSeq = actual.get(len).intValue();
            int expectednSeq = expected.get(len);
            if (actualNSeq != expectednSeq) {
                System.out.println(polynomial + " -> expected:" + expected);
                System.out.println(polynomial + " -> actual:  " + actual);
                System.out.println(len + " -> " + actualNSeq + " != " + expectednSeq);
                System.out.flush();

                throw new RuntimeException();
            }
        }
    }

    static boolean hasSequenceLength(Lfsr lfsr, BigInteger len) {
        Map<BigInteger, BigInteger> actual = lfsr.sequencesLength();
        for (BigInteger actualLen:actual.keySet()) {
            if(actualLen.equals(len)) return true;
        }
        return false;
    }

    static void checkFromSequenceLength(BigInteger len) {
        Lfsr lfsr = Lfsr.fromSeqLength(len);
        if(null==lfsr){
            System.out.println("no LFSR found for length "+len);
            assert(false);
        }
        System.out.println(lfsr.describe(false,false));
        if(!hasSequenceLength(lfsr,len)){

            assert(false);
        }
    }



    public void testFromSeqLength() {
        //checkFromSequenceLength(BigInteger.valueOf(11));
        //checkFromSequenceLength(BigInteger.valueOf(2720));

        for(int i=3;i<53;i++){
            switch(i){
                case 11:
                case 13:
                case 19:
                case 22:
                case 25:
                case 26:
                case 27:
                case 29:
                case 32:
                case 37:
                case 38:
                case 41:
                case 43:
                case 47:
                case 49:
                case 50:
                case 52:
                    break;
                default:
                    checkFromSequenceLength(BigInteger.valueOf(i));
            }

        }
    }

    /*@Test
    public void logSeqLength() {
        int targetWidth = 3;
        int maxTargetWidth = targetWidth*2;
        while(targetWidth<=maxTargetWidth) {
            int targetHammingWeight = 2;
            boolean[] candidateCore = Z2.firstWithHammingWeight(targetHammingWeight-2, targetWidth-2);
            boolean[] candidate = new boolean[targetWidth];
            candidate[0]=true;
            candidate[targetWidth-1]=true;
            while (true) {
                System.arraycopy(candidateCore,0,candidate,1,targetWidth-2);
                BigInteger orderOfX = Z2.orderOfX(candidate);
                if(orderOfX.equals(length)){
                    Lfsr c = fromTaps(candidate);
                    return c;//TODO set initial value
                } else if(orderOfX.equals(BigInteger.ONE)) {//order of X = 1 tells us nothing, have to look in detail into that one
                    Lfsr c = fromTaps(candidate);
                    Map<BigInteger, BigInteger> lengths = c.sequencesLength();
                    if (lengths.containsKey(length)) {
                        return c;//TODO set initial value
                    }
                }
                candidateCore = Z2.nextWithSameHammingWeigth(candidateCore);
                if (null == candidateCore) {
                    targetHammingWeight++;
                    if (targetHammingWeight > targetWidth) {
                        //did not find any solution for that width
                        targetWidth++;
                        break;
                    }
                    candidateCore = Z2.firstWithHammingWeight(targetHammingWeight-2, targetWidth-2);
                }
            }
        }
    }*/


    @Test
    public void testSequencesLength() throws Exception {
        /*Lfsr c = Lfsr.fromPolynomial("1+x4+x5");
        System.out.println(c);
        System.out.println(c.describe(false,false));
        checkSequencesLengthEqual("1+x4+x5", new int[][]{{3, 1}, {7, 1}, {21, 1}});*/


        checkSequencesLengthEqual("1+x5", new int[][]{{1, 1}, {5, 6}});
        checkSequencesLengthEqual("1+x11", new int[][]{{1, 1}, {11, 186}});

        checkSequencesLengthEqual("1+x", new int[][]{{1, 1}});//primitive
        checkSequencesLengthEqual("1+x+x3", new int[][]{{7, 1}});//primitive
        checkSequencesLengthEqual("1+x2+x3", new int[][]{{7, 1}});//primitive
        checkSequencesLengthEqual("1+x+x4", new int[][]{{15, 1}});//primitive
        checkSequencesLengthEqual("1+x3+x4", new int[][]{{15, 1}});//primitive
        //checkSequencesLengthEqual("1+x161+x167",new int[][]{{2^167-1,1}});//primitive
        checkSequencesLengthEqual("1+x+x2+x3+x4", new int[][]{{5, 3}});//irreducible
        checkSequencesLengthEqual("1+x3+x12", new int[][]{{45, 91}});//irreducible

        //square free polynomials
        checkSequencesLengthEqual("1+x3", new int[][]{{1, 1}, {3, 2}});             //(1+x)(1+x+x2)
        checkSequencesLengthEqual("1+x+x2+x4", new int[][]{{1, 1}, {7, 2}});             //(1+x)(1+x+x3)
        checkSequencesLengthEqual("1+x4+x5", new int[][]{{3, 1}, {7, 1}, {21, 1}});      //(1+x+x2)(1+x+x3)
        checkSequencesLengthEqual("1+x+x4+x6", new int[][]{{1, 1}, {3, 2}, {7, 2}, {21, 2}});//(1+x)(1+x+x2)(1+x+x3)
        checkSequencesLengthEqual("1+x+x6+x8+x9", new int[][]{{3, 1}, {7, 1}, {15, 4}, {21, 1}, {105, 4}});//(1+x+x2)(1+x+x3)(1+x+x4)
        checkSequencesLengthEqual("1+x+x2+x3+x5+x9+x10+x13+x14", new int[][]{{3, 1}, {7, 1}, {15, 4}, {21, 1}, {31, 1}, {93, 1}, {105, 4}, {217, 1}, {465, 4}, {651, 1}, {3255, 4}});//(1+x+x2)(1+x+x3)(1+x+x4)(1+x2+x5)

        //powers of primitive
        checkSequencesLengthEqual("1+x+x2", new int[][]{{3, 1}});//primitive
        checkSequencesLengthEqual("1+x2+x4", new int[][]{{3, 1}, {6, 2}});//(1+x+x2)^2
        checkSequencesLengthEqual("1+x+x3+x5+x6", new int[][]{{3, 1}, {6, 2}, {12, 4}});//(1+x+x2)^3
        checkSequencesLengthEqual("1+x4+x8", new int[][]{{3, 1}, {6, 2}, {12, 20}});//(1+x+x2)^4
        checkSequencesLengthEqual("1 + x + x2 + x4 + x5 + x6 + x8 + x9 + x10", new int[][]{{3, 1}, {6, 2}, {12, 20}, {24, 32}});//(1+x+x2)^5
        checkSequencesLengthEqual("1 + x2 + x6 + x10 + x12", new int[][]{{3, 1}, {6, 2}, {12, 20}, {24, 160}});//(1+x+x2)^6
        checkSequencesLengthEqual("1 + x + x3 + x4 + x6 + x7 + x8 + x10 + x11 + x13 + x14", new int[][]{{3, 1}, {6, 2}, {12, 20}, {24, 672}});//(1+x+x2)^7
        checkSequencesLengthEqual("1 + x8 + x16", new int[][]{{3, 1}, {6, 2}, {12, 20}, {24, 2720}});//(1+x+x2)^8
        checkSequencesLengthEqual("1 + x + x2 + x8 + x9 + x10 + x16 + x17 + x18", new int[][]{{3, 1}, {6, 2}, {12, 20}, {24, 2720}, {48, 4096}});//(1+x+x2)^9

        checkSequencesLengthEqual("1+x ", new int[][]{{1, 1}});//primitive
        checkSequencesLengthEqual("1+x2", new int[][]{{1, 1}, {2, 1}});//(1+x)^2
        checkSequencesLengthEqual("1+x+x2+x3", new int[][]{{1, 1}, {2, 1}, {4, 1}});//(1+x)^3
        checkSequencesLengthEqual("1+x4", new int[][]{{1, 1}, {2, 1}, {4, 3}});//(1+x)^4
        checkSequencesLengthEqual("1+x+x4+x5", new int[][]{{1, 1}, {2, 1}, {4, 3}, {8, 2}});//(1+x)^5
        checkSequencesLengthEqual("1+x2+x4+x6", new int[][]{{1, 1}, {2, 1}, {4, 3}, {8, 6}});//(1+x)^6
        checkSequencesLengthEqual("1+x+x2+x3+x4+x5+x6+x7", new int[][]{{1, 1}, {2, 1}, {4, 3}, {8, 14}});//(1+x)^7
        checkSequencesLengthEqual("1 + x8", new int[][]{{1, 1}, {2, 1}, {4, 3}, {8, 30}});//(1+x)^8
        checkSequencesLengthEqual("1 + x + x8 + x9", new int[][]{{1, 1}, {2, 1}, {4, 3}, {8, 30}, {16, 16}});//(1+x)^9
        checkSequencesLengthEqual("1 + x2 + x8 + x10", new int[][]{{1, 1}, {2, 1}, {4, 3}, {8, 30}, {16, 48}});//(1+x)^10
        //checkSequencesLengthEqual("1 + x2 + x8 + x10",    new int[][]{{1,1},{2,1},{4,3},{8,30},{16,48}});//(1+x)^11
        //checkSequencesLengthEqual("1 + x2 + x8 + x10",    new int[][]{{1,1},{2,1},{4,3},{8,30},{16,48}});//(1+x)^12
        checkSequencesLengthEqual("1+x+x4+x5+x8+x9+x12+x13", new int[][]{{1, 1}, {2, 1}, {4, 3}, {8, 30}, {16, 496}});//(1+x)^13
        //checkSequencesLengthEqual("1 + x2 + x8 + x10",    new int[][]{{1,1},{2,1},{4,3},{8,30},{16,48}});//(1+x)^14
        //checkSequencesLengthEqual("1 + x2 + x8 + x10",    new int[][]{{1,1},{2,1},{4,3},{8,30},{16,48}});//(1+x)^15
        checkSequencesLengthEqual("1 + x16", new int[][]{{1, 1}, {2, 1}, {4, 3}, {8, 30}, {16, 4080}});//(1+x)^16
        checkSequencesLengthEqual("1 + x + x16 + x17", new int[][]{{1, 1}, {2, 1}, {4, 3}, {8, 30}, {16, 4080}, {32, 2048}});//(1+x)^16

        checkSequencesLengthEqual("1+x3+x4", new int[][]{{15, 1}});//primitive
        checkSequencesLengthEqual("1 + x6 + x8", new int[][]{{15, 1}, {30, 8}});//(1+x3+x4)^2
        checkSequencesLengthEqual("1 + x3 + x4 + x6 + x8 + x9 + x10 + x11 + x12", new int[][]{{15, 1}, {30, 8}, {60, 64}});//(1+x3+x4)^3
        checkSequencesLengthEqual("1 + x12 + x16", new int[][]{{15, 1}, {30, 8}, {60, 1088}});//(1+x3+x4)^4


        //powers of irreducibles
        checkSequencesLengthEqual("1+x+x2+x3+x4", new int[][]{{5, 3}});//irreducible
        checkSequencesLengthEqual("1+x2+x4+x6+x8", new int[][]{{5, 3}, {10, 24}});//(1+x+x2+x3+x4)^2
        checkSequencesLengthEqual("1 + x + x4 + x6 + x8 + x11 + x12", new int[][]{{5, 3}, {10, 24}, {20, 192}});//(1+x+x2+x3+x4)^3
        checkSequencesLengthEqual("1 + x4 + x8 + x12 + x16", new int[][]{{5, 3}, {10, 24}, {20, 3264}});//(1+x+x2+x3+x4)^4
        checkSequencesLengthEqual("1 + x + x2 + x3 + x5 + x6 + x7 + x9 + x10 + x11 + x13 + x14 + x15 + x17 + x18 + x19 + x20",
                new int[][]{{5, 3}, {10, 24}, {20, 3264}, {40, 24576}});//(1+x+x2+x3+x4)^5

        checkSequencesLengthEqual("1+x ", new int[][]{{1, 1}});//primitive
        checkSequencesLengthEqual("1+x2", new int[][]{{1, 1}, {2, 1}});//(1+x)^2
        checkSequencesLengthEqual("1+x+x3", new int[][]{{7, 1}});//primitive
        checkSequencesLengthEqual("1+x2+x6", new int[][]{{7, 1}, {14, 4}});//(1+x+x3)^2
        checkSequencesLengthEqual("1+x+x2+x4", new int[][]{{1, 1}, {7, 2}});//(1+x)(1+x+x3)
        checkSequencesLengthEqual("1+x3", new int[][]{{1, 1}, {3, 2}});//(1+x)(1+x+x2). orderOfX: 2, 3  maxLength: 1, 3

        checkSequencesLengthEqual("1+x+x2+x3+x5+x7+x8+x9+x13", new int[][]{{3, 1}, {7, 1}, {15, 4}, {21, 1}, {30, 32}, {105, 4}, {210, 32}});//(1+x+x2)(1+x2+x3)(1+x+x4)^2

        checkSequencesLengthEqual("1 + x + x2 + x5", new int[][]{{1, 1}, {2, 1}, {7, 2}, {14, 1}});//(1+x)^2*(1+x+x3) orderOfX: 2, 2, 7 maxLength: 1, 1, 7
        checkSequencesLengthEqual("1 + x + x2 + x3 + x6 + x7", new int[][]{{1, 1}, {7, 2}, {14, 8}});//(1+x)(1+x+x3)^2 orderOfX: 2, 7, 7  maxLength: 1, 7, 7
        checkSequencesLengthEqual("1+x2+x3+x7", new int[][]{{1, 1}, {2, 1}, {31, 2}, {62, 1}});//(1+x)^2*(1+x3+x5) orderOfX: 2, 2, 31


        checkSequencesLengthEqual("1+x+x3+x4", new int[][]{{1, 1}, {2, 1}, {3, 2}, {6, 1}});//(1+x)^2 * (1+x+x2) orderOfX: 2, 2, 3

        checkSequencesLength("1+x+x2+x3+x5+x7+x8+x9+x13");
        for (int i = 2; i < 1 << 12; i++) {
            boolean[] poly = Z2.toBooleans(i);
            poly[0] = true;
            checkSequencesLength(poly);
            if (i % 1000 == 0) System.out.println(i);
        }

    }

    void checkReversedSequenceLfsr(String polynomial) {
        Lfsr lfsr = Lfsr.fromPolynomial(polynomial);
        Lfsr reversed = lfsr.reversedSequenceLfsr();
        Set<boolean[]> seqs = Z2.rotateToMinValue(Z2.reverse(lfsr.sequences()));
        Set<boolean[]> rseqs = Z2.rotateToMinValue(reversed.sequences());
        //System.out.println("\n"+polynomial);
        //System.out.println(lfsr.getPolynomial()+": "+lfsr.isPolynomialPrimitive()+", "+lfsr.isPolynomialIrreducible());
        //System.out.println(reversed.getPolynomial() + ": " + reversed.isPolynomialPrimitive() + ", " + reversed.isPolynomialIrreducible());
        assert (seqs.size() == rseqs.size());

        boolean match = false;
        for (boolean[] seq : seqs) {
            match = false;
            for (boolean[] rseq : rseqs) {
                if (Z2.equal(rseq, seq)) {
                    match = true;
                    break;
                } else {
                    //System.out.println("\t"+Z2.toBinaryString(rseq)+ " does not match");
                }
            }
            if (!match) {
                System.out.println("\t" + Z2.toBinaryString(seq) + " cannot be found");
            }
        }
        System.out.flush();
        assert (match);
    }

    @Test
    public void testReversedSequenceLfsr() throws Exception {
        checkReversedSequenceLfsr("1+x");//primitive
        checkReversedSequenceLfsr("1+x+x3");//primitive
        checkReversedSequenceLfsr("1+x2+x3");//primitive
        checkReversedSequenceLfsr("1+x+x4");//primitive
        checkReversedSequenceLfsr("1+x3+x4");//primitive
        checkReversedSequenceLfsr("1+x+x2+x3+x4");//irreducible
        checkReversedSequenceLfsr("1+x3+x12");//irreducible

        //square free polynomials
        checkReversedSequenceLfsr("1+x3");             //(1+x)(1+x+x2)
        checkReversedSequenceLfsr("1+x+x2+x4");             //(1+x)(1+x+x3)
        checkReversedSequenceLfsr("1+x4+x5");      //(1+x+x2)(1+x+x3)
        checkReversedSequenceLfsr("1+x+x4+x6");//(1+x)(1+x+x2)(1+x+x3)
        checkReversedSequenceLfsr("1+x+x6+x8+x9");//(1+x+x2)(1+x+x3)(1+x+x4)
        checkReversedSequenceLfsr("1+x+x2+x3+x5+x9+x10+x13+x14");//(1+x+x2)(1+x+x3)(1+x+x4)(1+x2+x5)

        //powers of primitive
        checkReversedSequenceLfsr("1+x+x2");//primitive
        checkReversedSequenceLfsr("1+x2+x4");//(1+x+x2)^2
        checkReversedSequenceLfsr("1+x+x3+x5+x6");//(1+x+x2)^3
        checkReversedSequenceLfsr("1+x4+x8");//(1+x+x2)^4
        checkReversedSequenceLfsr("1 + x + x2 + x4 + x5 + x6 + x8 + x9 + x10");//(1+x+x2)^5
        checkReversedSequenceLfsr("1 + x2 + x6 + x10 + x12");//(1+x+x2)^6
        checkReversedSequenceLfsr("1 + x + x3 + x4 + x6 + x7 + x8 + x10 + x11 + x13 + x14");//(1+x+x2)^7
        //checkReversedSequenceLfsr("1 + x8 + x16");//(1+x+x2)^8

        checkReversedSequenceLfsr("1+x ");//primitive
        checkReversedSequenceLfsr("1+x2");//(1+x)^2
        checkReversedSequenceLfsr("1+x+x2+x3");//(1+x)^3
        checkReversedSequenceLfsr("1+x4");//(1+x)^4
        checkReversedSequenceLfsr("1+x+x4+x5");//(1+x)^5
        checkReversedSequenceLfsr("1+x2+x4+x6");//(1+x)^6
        checkReversedSequenceLfsr("1+x+x2+x3+x4+x5+x6+x7");//(1+x)^7
        checkReversedSequenceLfsr("1 + x8");//(1+x)^8
        checkReversedSequenceLfsr("1 + x + x8 + x9");//(1+x)^9
        checkReversedSequenceLfsr("1 + x2 + x8 + x10");//(1+x)^10
        checkReversedSequenceLfsr("1+x+x4+x5+x8+x9+x12+x13");//(1+x)^13
        //checkReversedSequenceLfsr("1 + x16");//(1+x)^16
        //checkReversedSequenceLfsr("1 + x + x16 + x17");//(1+x)^16

        checkReversedSequenceLfsr("1+x3+x4");//primitive
        checkReversedSequenceLfsr("1 + x6 + x8");//(1+x3+x4)^2
        checkReversedSequenceLfsr("1 + x3 + x4 + x6 + x8 + x9 + x10 + x11 + x12");//(1+x3+x4)^3
        //checkReversedSequenceLfsr("1 + x12 + x16");//(1+x3+x4)^4


        //powers of irreducibles
        checkReversedSequenceLfsr("1+x+x2+x3+x4");//irreducible
        checkReversedSequenceLfsr("1+x2+x4+x6+x8");//(1+x+x2+x3+x4)^2
        checkReversedSequenceLfsr("1 + x + x4 + x6 + x8 + x11 + x12");//(1+x+x2+x3+x4)^3
        //checkReversedSequenceLfsr("1 + x4 + x8 + x12 + x16");//(1+x+x2+x3+x4)^4

        checkReversedSequenceLfsr("1+x ");//primitive
        checkReversedSequenceLfsr("1+x2");//(1+x)^2
        checkReversedSequenceLfsr("1+x+x3");//primitive
        checkReversedSequenceLfsr("1+x2+x6");//(1+x+x3)^2
        checkReversedSequenceLfsr("1+x+x2+x4");//(1+x)(1+x+x3)

        checkReversedSequenceLfsr("1 + x + x2 + x5");//(1+x)^2*(1+x+x3)
        checkReversedSequenceLfsr("1 + x + x2 + x3 + x6 + x7");//(1+x)(1+x+x3)^2

        checkReversedSequenceLfsr("1+x3");
        checkReversedSequenceLfsr("1+x+x3+x4");

    }

    static class MessedUpLfsr extends Lfsr {
        public MessedUpLfsr() {
            super(Z2.toBooleans("100000000101"), 12);
        }

        @Override
        public boolean step() {
            //for(i=BITS_PER_CYCLE;i<47;i=i+1) o_rnd[i] <= o_rnd[i-BITS_PER_CYCLE] | (deadlock & SEED[i]);
            //for(i=0;i<BITS_PER_CYCLE;i=i+1) o_rnd[i] <= o_rnd[46-i] ^ o_rnd[41-i] | (deadlock & SEED[i]);
            boolean out = state[0];
            boolean []next_state = new boolean[l];
            final int BITS_PER_CYCLE=8;
            final boolean[] SEED = Z2.toBooleans(0x123,l);
            for(int i=BITS_PER_CYCLE;i<l;i++){
                next_state[i]=state[i-BITS_PER_CYCLE];
            }
            for(int i=0;i<BITS_PER_CYCLE;i++){
                next_state[i]=state[10-i] ^ state[8-i];
            }
            state=next_state;
            if(Z2.isZero(next_state)){
                state = SEED;System.out.println("seed");
            }
            return out;
        }

        @Override
        public String describe(boolean outputSeq, boolean outputStates) {
            BigInteger orderOfX = Z2.orderOfX(getTaps());
            String out = "";
            out+="Polynomial:      "+getPolynomial()+"\n";
            out+="Order of x:      "+orderOfX+"\n";
            out+="Inverted output: "+invertOutput+"\n";
            out+="Stepping mode:   "+mode+"\n";
            out+="Taps:            "+getTapsString()+"\n";
            out+="Initial state:   "+getStateString()+"\n";
            String properties = "maximum length LFSR \n"+"Sequence length is ";
            BigInteger maxLength = BigInteger.ONE.shiftLeft(getWidth()).subtract(BigInteger.ONE);
            String tab="   ";
            if(isMaximumLength()) {
                properties += maxLength;
            }else{
                properties = "non "+properties;
                if(isPolynomialIrreducible()) {
                    properties = "irreducible but not primitive, "+properties;
                    Map<BigInteger,BigInteger> seqlength=sequencesLength();
                    BigInteger len=null;
                    for(BigInteger l:seqlength.keySet()){
                        len=l;
                    }
                    properties = properties+len+", "+seqlength.get(len)+" different sequences of that length";
                } else {
                    try{
                        Map<BigInteger,BigInteger> seqlength=sequencesLength();
                        properties+="dependant on the initial value. It can be:\n";
                        for(BigInteger len:seqlength.keySet()){
                            properties+=tab+len+" ("+seqlength.get(len)+" different sequences of that length)\n";
                        }
                        properties=properties.substring(0,properties.length()-1);//remove the last new line character
                    }catch(Throwable e) {//we don't know how to compute the length of sequences for this polynomial.
                        //properties += "smaller than " + maxLength;
                        properties += "at most "+orderOfX+" (depends on initial state)";
                    }
                }
            }

            if(!isPolynomialIrreducible()) {
                properties = "reducible, " + properties;
                boolean[][] factors = Z2.factorPolynomial(Z2.minimumLengthCopy(getTaps()));
                //properties += "\n"+"Factors:\n" +tab+ Z2.join(Z2.toPolynomials(factors), "\n"+tab);
                properties += "\n"+"Factors:\n";
                for(boolean[] f:factors) {
                    properties += tab+ Z2.toPolynomial(f)+ " (order of x: " + Z2.orderOfX(f)+")\n";
                }
                properties=properties.substring(0,properties.length()-1);//remove the last new line character
            }

            if(isSingular()) properties = "a singular, "+properties;
            else properties = "a non singular, "+properties;
            out+="It is "+properties+"\n";
            if(l>1 && isPolynomialIrreducible()) {
                out += "Reverse sequence polynomial: " + reversedSequenceLfsr().getPolynomial() + "\n";
            }
            if(outputSeq){
                                    Map<boolean[],boolean[][]> sequences = sequences(outputStates);
                    out+=sequences.size()+" generated sequences:"+"\n";
                    TreeMap<Integer,Integer> counts = new TreeMap <Integer,Integer>();
                    for(boolean[] seq: sequences.keySet()){
                        int len = seq.length;
                        if(counts.containsKey(len)) counts.put(len,counts.get(len)+1);
                        else counts.put(len,1);
                    }
                    int sum=0;
                    for(int len:counts.keySet()){
                        sum+=len*counts.get(len);
                        out+=String.format("%10d",len)+" bits sequences: "+counts.get(len)+" occurences"+"\n";
                    }
                    out+=sum+" states in total\n";
                    //if(sum<200) {//for debug
                    List<boolean[]> sequencesLength=new ArrayList<boolean[]>();
                    sequencesLength.addAll(sequences.keySet());
                    Collections.sort(sequencesLength,Z2.comparator);
                    for (boolean[] seq : sequencesLength) {
                        out+=String.format("%10d",seq.length) + " bits sequence: " + Z2.toBinaryString(seq)+"\n";
                        if (outputStates) {
                            boolean[][] states = sequences.get(seq);
                            for (int i = 0; i < states.length; i++)
                                out+=tab + Z2.toBinaryString(states[i])+"\n";
                            //out+="\n";
                        }
                    }
                    //}

            }
            return out;
        }
    }
    @Test
    public void testExperiment() throws Exception {
        MessedUpLfsr lfsr = new MessedUpLfsr();
        System.out.println(lfsr.describe(true,true));
        //System.out.println(Lfsr.fromPolynomial("1+x9+x11").describe(true,true));
    }
}