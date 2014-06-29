package uk.co.nimp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class ScramblerTest {
    Scrambler dut;
    @Before
    public void setUp() throws Exception {
        //dut = new OffsetScrambler(13*16);
        dut = new DpeScrambler();
    }
    static void assertTrue(String message, boolean predicate){
        assertEquals(message,true,predicate);
    }
    static void assertFalse(String message, boolean predicate){
        assertEquals(message,false,predicate);
    }
    static void assertEquals(String message,boolean expected, boolean actual){
        int e=Z2.toInt(expected);
        int a=Z2.toInt(actual);
        Assert.assertEquals(message, e, a);
    }
    @Test
    public void testScramble() throws Exception {
        int inputRange = dut.getInputRange();
        int keyWidth = dut.getKeyWidth();
        int inputWidth = dut.getInputWidth();
        assert(inputWidth <= 32);
        Random rng = new Random(0);
        int keyRange = 1<<keyWidth;
        for(int i=0;i<keyRange;i++) {
            //boolean[] key = Z2.toBooleans(rng.nextInt(), keyWidth);
            boolean[] key = Z2.toBooleans(i, keyWidth);
            boolean[] done = new boolean[inputRange];
            int fixPointsCnt=0;
            for(int in=0;in<inputRange;in++){
                boolean[] inBits = Z2.toBooleans(in,inputWidth);
                boolean[] outBits = dut.scramble(inBits,key);
                int out = Z2.booleansToInt(outBits);
                assertFalse("Collision detected for input " + Z2.toBinaryString(inBits) + " and key " + Z2.toBinaryString(key), done[out]);
                done[out]=true;
                if(in==out) fixPointsCnt++;
            }
            System.out.println(fixPointsCnt+" fix points for key "+Z2.toBinaryString(key));
        }
    }
}