package uk.co.nimp.songpa;

import org.junit.Assert;
import org.junit.Test;
import uk.co.nimp.Scrambler;
import uk.co.nimp.ScramblerAnalyzer;
import uk.co.nimp.Z2;

public class Toy32Test {

    static void assertTrue(String message, boolean predicate){
        assertEquals(message,true,predicate);
    }
    static void assertFalse(String message, boolean predicate){
        assertEquals(message, false, predicate);
    }
    static void assertEquals(String message,boolean expected, boolean actual){
        int e=Z2.toInt(expected);
        int a=Z2.toInt(actual);
        Assert.assertEquals(message, e, a);
    }
    static void assertEquals(String message,int expected, int actual){
        if(expected!=actual){
            throw new RuntimeException(message+"\nexpected: 0x"+Integer.toHexString(expected)+"\nactual:   0x"+Integer.toHexString(actual));
        }
    }

    @Test
    public void testApply() throws Exception {
        Toy32 dut = new Toy32(1,0);
        dut.setSpy(System.out);
        int keySize = dut.getKeySize();
        boolean[] key = new boolean[keySize];//the 00 key
        dut.setKey(key);
        for(int i=0;i<1;i++){
            boolean[] in = Z2.randomBooleans(32,i);
            dut.printSeparator();
            boolean[] out = dut.encrypt(in);
            boolean[] decOut = dut.decrypt(out);
            System.out.println(Z2.toHexByteString(in)+" <-> "+Z2.toHexByteString(out));
            assert(Z2.equal(in,decOut));
        }
    }

    @Test
    public void testBasicAnalysis() throws Exception {
        ScramblerAnalyzer scramblerAnalyzer = new ScramblerAnalyzer(new Toy32Scrambler(new Toy32(1,0)));
        System.out.println(scramblerAnalyzer.inputBitsToOutputBitsAnalysis(1000));
        //System.out.println(scramblerAnalyzer.effectiveKeyRangeAnalysis());
        //System.out.println(scramblerAnalyzer.effectiveKeyRangeEstimationAnalysis(1<<16));
    }

    static class Toy32Scrambler implements Scrambler {

        final Toy32 impl;

        Toy32Scrambler(Toy32 impl) {
            this.impl = impl;
        }

        @Override
        public int getInputRange() {
            return 1<<impl.width;
        }

        @Override
        public int getInputWidth() {
            return impl.width;
        }

        @Override
        public int getKeyWidth() {
            return impl.getKeySize();
        }

        @Override
        public boolean[] scramble(boolean[] in, boolean[] key) {
            impl.setKey(key);
            return impl.encrypt(in);
        }
    }

}