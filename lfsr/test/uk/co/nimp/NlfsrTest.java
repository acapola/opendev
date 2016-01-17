package uk.co.nimp;

import org.junit.Test;

import java.math.BigInteger;
import java.util.*;


public class NlfsrTest {


    @Test
    public void testFromTaps() throws Exception {
        Nlfsr n = Nlfsr.fromTaps("~!&^");
        System.out.println(n.describe(true,true));
        //Nlfsr.explore(8,3);
    }
}
