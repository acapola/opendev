package uk.co.nimp;

import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;
import static uk.co.nimp.PollardRho.*;
public class PollardRhoTest {
    static final HashMap<String,String[]> testcases;
    static {
        testcases = new HashMap<String, String[]>();
        testcases.put("1790407",new String[]{"71","151","167"});
        testcases.put("44343535354351600000003434353",new String[]{"149","329569479697","903019357561501"});
        testcases.put("187072209578355573530071658587684226515959365500927",new String[]{"2349023","79638304766856507377778616296087448490695649"});
    }
    @Test
    public void testFactor() throws Exception {
        for(String nStr:testcases.keySet()) {
            String[] factors = testcases.get(nStr);
            BigInteger[] expected = new BigInteger[factors.length];
            for(int i=0;i<factors.length;i++) expected[i] = new BigInteger(factors[i]);
            System.out.println(nStr);
            assertArrayEquals(factor(new BigInteger(nStr)), expected);
        }
    }
}