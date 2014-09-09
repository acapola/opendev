package uk.co.nimp;

import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    @Test
    public void testIntsTable() throws Exception{
        /*{
            System.out.println("static Map<Integer,Map<Integer,Integer>> intFactorsPowersOfTwoMinusOne = new HashMap<Integer,Map<Integer,Integer>>(31);");
            System.out.println("static {\nMap<Integer,Integer> factorsMap = new HashMap<Integer,Integer>();");
            int n = 1;
            for (int i = 0; i < 31; i++) {
                Map<BigInteger, Integer> factors = PollardRho.factorMap(BigInteger.valueOf(n));
                if (i > 0) System.out.println("factorsMap.clear();");
                if (i == 0) factors.put(BigInteger.ONE, 1);
                for (BigInteger f : factors.keySet()) {
                    System.out.println("factorsMap.put(" + f + "," + factors.get(f) + ");");
                }
                System.out.println("intFactorsPowersOfTwoMinusOne.put(0x" + Integer.toHexString(n) + ",factorsMap);");
                n = (n << 1) + 1;
            }
            System.out.println("}");
        }
        {
            System.out.println("static Map<Long,Map<Long,Integer>> longFactorsPowersOfTwoMinusOne = new HashMap<Long,Map<Long,Integer>>(63);");
            System.out.println("static {\nMap<Long,Integer> factorsMap = new HashMap<Long,Integer>();");
            long n = 1L;
            for (int i = 0; i < 63; i++) {
                Map<BigInteger, Integer> factors = PollardRho.factorMap(BigInteger.valueOf(n));
                if (i > 0) System.out.println("factorsMap.clear();");
                if (i == 0) factors.put(BigInteger.ONE, 1);
                for (BigInteger f : factors.keySet()) {
                    System.out.println("factorsMap.put(" + f + "L," + factors.get(f) + ");");
                }
                System.out.println("longFactorsPowersOfTwoMinusOne.put(0x" + Long.toHexString(n) + "L,factorsMap);");
                n = (n << 1) + 1;
            }
            System.out.println("}");
        }*/
        {
            int nBitsStart = 63;
            int nBits = 200;
            System.out.println("static Map<BigInteger,Map<BigInteger,Integer>> bigIntegersFactorsPowersOfTwoMinusOne = new HashMap<BigInteger,Map<BigInteger,Integer>>("+nBits+");");
            System.out.println("static {\nMap<BigInteger,Integer> factorsMap = new HashMap<BigInteger,Integer>();");
            BigInteger n = BigInteger.ONE.shiftLeft(nBitsStart).subtract(BigInteger.ONE);
            for (int i = nBitsStart; i < nBits; i++) {
                Map<BigInteger, Integer> factors = PollardRho.factorMap(n);
                if (i > 0) System.out.println("factorsMap.clear();");
                if (i == 0) factors.put(BigInteger.ONE, 1);
                for (BigInteger f : factors.keySet()) {
                    System.out.println("factorsMap.put(new BigInteger(\"" + f.toString(16) + "\",16)," + factors.get(f) + ");");
                }
                System.out.println("bigIntegerFactorsPowersOfTwoMinusOne.put(new BigInteger(\"" + n.toString(16) + "\",16),factorsMap);");
                n = n.shiftLeft(1).add(BigInteger.ONE);
            }
            System.out.println("}");
        }
    }
}