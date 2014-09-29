package uk.co.nimp.songpa;

import org.junit.Assert;
import org.junit.Test;
import uk.co.nimp.Z2;

import java.math.BigInteger;
import java.util.HashSet;

public class AesMixColumnTest {

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
    static void checkApply(AesMixColumns m, int in, int expected){
        boolean[] column = Z2.toBooleans(in,32);
        boolean[] newColumn = m.apply(column);
        int newColumnInt = Z2.booleansToInt(newColumn);
        assertEquals("mismatch",expected,newColumnInt);
    }
    @Test
    public void testApply() throws Exception {
        AesMixColumns m = new AesMixColumns();
        //test vectors from wikipedia
        checkApply(m,0x01010101,0x01010101);
        checkApply(m,0xc6c6c6c6,0xc6c6c6c6);
        checkApply(m,0xdb135345,0x8e4da1bc);
        checkApply(m,0xd4d4d4d5,0xd5d5d7d6);
        checkApply(m,0x2d26314c,0x4d7ebdf8);
        checkApply(m,0xf20a225c,0x9fdc589d);
    }
    static void checkUnapply(AesMixColumns m, int expected, int in){
        boolean[] column = Z2.toBooleans(in,32);
        boolean[] newColumn = m.unapply(column);
        int newColumnInt = Z2.booleansToInt(newColumn);
        assertEquals("mismatch",expected,newColumnInt);
    }
    @Test
    public void testUnapply() throws Exception {
        AesMixColumns m = new AesMixColumns();
        //test vectors from wikipedia
        checkUnapply(m,0x01010101,0x01010101);
        checkUnapply(m,0xc6c6c6c6,0xc6c6c6c6);
        checkUnapply(m,0xd4d4d4d5,0xd5d5d7d6);
        checkUnapply(m,0xdb135345,0x8e4da1bc);
        checkUnapply(m,0x2d26314c,0x4d7ebdf8);
        checkUnapply(m,0xf20a225c,0x9fdc589d);
    }
}