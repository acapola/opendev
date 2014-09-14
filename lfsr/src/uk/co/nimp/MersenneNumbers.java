package uk.co.nimp;

import java.io.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by seb on 9/14/14.
 */
public class MersenneNumbers {
    /*static Map<BigInteger, Map<BigInteger, Integer>> bigIntegerFactorsPowersOfTwoMinusOne = new HashMap<BigInteger, Map<BigInteger, Integer>>(2000);

    static {

        Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(new BigInteger("1", 16), 1);
        bigIntegerFactorsPowersOfTwoMinusOne.put(new BigInteger("1", 16), factorsMap);
        factorsMap.clear();

    }*/
    static Integer maxPower = null;
    static BigInteger max=null;
    public static int getMaxPower() {
        if (null == maxPower) {
            getMax();
        }
        return maxPower;
    }
    public static BigInteger getMax(){
        if(null==max) {
            InputStream stream = MersenneNumbers.class.getResourceAsStream("/uk/co/nimp/MersenneNumbersFactors.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(stream));
            String line = null;
            String lastLine = null;
            int i = 0;
            try {
                while ((line = in.readLine()) != null) {
                    if(line.isEmpty()) break;
                    lastLine = line;
                    i++;
                }
                String[] numbers = lastLine.split(" ");
                BigInteger num = new BigInteger(numbers[0], 16);
                BigInteger expected = BigInteger.valueOf(2).pow(i).subtract(BigInteger.ONE);
                if (expected == num)
                    throw new RuntimeException("INTERNAL ERROR, data file corrupted: num=" + num + ", expected=" + expected);
                max = num;
                maxPower = i;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return max;
    }
    public static int getExponent(BigInteger powerOfTwoMinusOne) {
        int power = powerOfTwoMinusOne.bitLength();
        return power;
    }
    public static BigInteger getMersenneNumber(int exponent){
        return BigInteger.valueOf(2).pow(exponent).subtract(BigInteger.ONE);
    }
    public static Map<BigInteger,Integer> factorPowerOfTwoMinusOne(BigInteger powerOfTwoMinusOne) {
        if(powerOfTwoMinusOne.compareTo(getMax())>0) return PollardRho.factorMap(powerOfTwoMinusOne);
        return factorPowerOfTwoMinusOne(getExponent(powerOfTwoMinusOne));
    }
    public static Map<BigInteger,Integer> factorPowerOfTwoMinusOne(int power) {
        if(power>getMaxPower()) return PollardRho.factorMap(getMersenneNumber(power));
        Map<BigInteger, Integer> out = new HashMap<BigInteger, Integer>();
        InputStream stream = MersenneNumbers.class.getResourceAsStream("/uk/co/nimp/MersenneNumbersFactors.txt");
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String line = null;
        int i=0;
        try {
            do{
                line = in.readLine();
                i++;
            }while(i!=power);
            String[] numbers = line.split(" ");
            assert(getMersenneNumber(power)== new BigInteger(numbers[0],16));
            for(int j=1;j<numbers.length;j+=2){
                out.put(new BigInteger(numbers[j],16),Integer.parseInt(numbers[j+1],16));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return out;
    }
    static void testFromExponent(int in){
        System.out.println("input = "+in+" -> number = "+getMersenneNumber(in));
        Map<BigInteger,Integer> factors = factorPowerOfTwoMinusOne(in);
        BigInteger check=BigInteger.ONE;
        for(BigInteger f:factors.keySet()){
            System.out.println(f+" ^ "+factors.get(f));
            check = check.multiply(f.pow(factors.get(f)));
        }
        if(check.equals(getMersenneNumber(in))){
            System.out.println("PASS: Factors equal input.");
        } else {
            System.out.println("ERROR: factors equal "+check);
        }
    }
    static void testFromNumber(BigInteger in){
        System.out.println("input = "+in+" -> exponent = "+getExponent(in));
        Map<BigInteger,Integer> factors = factorPowerOfTwoMinusOne(in);
        BigInteger check=BigInteger.ONE;
        for(BigInteger f:factors.keySet()){
            System.out.println(f+" ^ "+factors.get(f));
            check = check.multiply(f.pow(factors.get(f)));
        }
        if(check.equals(in)){
            System.out.println("PASS: Factors equal input.");
        } else {
            System.out.println("ERROR: factors equal "+check);
        }
    }
    static void testConverters(int in){
        BigInteger num = getMersenneNumber(in);
        int exp = getExponent(num);
        System.out.println("in="+in+", num="+num+", exp="+exp);
        if(exp==in){
            System.out.println("PASS: exponent to number and number to exponent consistent");
        } else {
            System.out.println("ERROR: exponent to number and number to exponent not consistent");
        }
    }
    public static void main(String[] args) throws Exception {
        System.out.println(getMax()+", "+getMaxPower());
        int in = 298;
        testConverters(in);
        testFromExponent(in);
        testFromNumber(getMersenneNumber(in));
    }
}
