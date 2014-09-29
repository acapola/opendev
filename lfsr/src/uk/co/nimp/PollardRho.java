package uk.co.nimp;

/*************************************************************************
 *  Compilation:  javac PollardRho.java
 *  Execution:    java PollardRho N
 *
 *  Factor N using the Pollard-Rho method.
 *
 *  % java PollardRho 44343535354351600000003434353
 *  149
 *  329569479697
 *  903019357561501
 *
 *************************************************************************/

import java.math.BigInteger;
import java.util.*;
//import java.security.SecureRandom;


class PollardRho {
    private final static BigInteger ZERO = new BigInteger("0");
    private final static BigInteger ONE  = new BigInteger("1");
    private final static BigInteger TWO  = new BigInteger("2");
    //private final static SecureRandom random = new SecureRandom();
    private final static Random random = new Random();

    public static BigInteger rho(BigInteger n) {
        BigInteger divisor;
        BigInteger c  = new BigInteger(n.bitLength(), random);
        BigInteger x  = new BigInteger(n.bitLength(), random);
        BigInteger xx = x;

        // check divisibility by 2
        if (n.mod(TWO).compareTo(ZERO) == 0) return TWO;

        do {
            x  =  x.multiply(x).mod(n).add(c).mod(n);
            xx = xx.multiply(xx).mod(n).add(c).mod(n);
            xx = xx.multiply(xx).mod(n).add(c).mod(n);
            divisor = x.subtract(xx).gcd(n);
        } while((divisor.compareTo(ONE)) == 0);

        return divisor;
    }

    public static void factor(BigInteger n, List<BigInteger> factors) {
        if (n.compareTo(ONE) == 0) return;
        if (n.isProbablePrime(20)) {
            //System.out.println(n);
            factors.add(n);
            return;
        }
        BigInteger divisor = rho(n);
        factor(divisor,factors);
        factor(n.divide(divisor),factors);
    }
    public static BigInteger[] factor(BigInteger n) {
        if(n.compareTo(BigInteger.ONE)<=0){
            //BigInteger []out = new BigInteger[1];
            //out[0] = BigInteger.ZERO;
            return new BigInteger[0];
        }
        ArrayList<BigInteger> factors = new ArrayList<BigInteger>();
        factor(n,factors);
        Collections.sort(factors);
        return factors.toArray(new BigInteger[factors.size()]);
    }
    public static Map<BigInteger,Integer> factorMap(BigInteger n){
        Map<BigInteger,Integer> factorsMap = new HashMap<BigInteger,Integer>();
        if(n.compareTo(BigInteger.ONE)<=0){
            //factorsMap.put(BigInteger.ZERO,0);
            return factorsMap;
        }
        ArrayList<BigInteger> factors = new ArrayList<BigInteger>();
        factor(n,factors);
        for(BigInteger f: factors){
            int power = 1;
            if(factorsMap.containsKey(f)) power += factorsMap.get(f);
            factorsMap.put(f,power);
        }
        return factorsMap;
    }

    /*public static Map<Integer,Integer> factorPowerOfTwoMinusOne(int powerOfTwoMinusOne){
        return intFactorsPowersOfTwoMinusOne.get(powerOfTwoMinusOne);
    }
    public static Map<Long,Integer> factorPowerOfTwoMinusOne(long powerOfTwoMinusOne){
        return longFactorsPowersOfTwoMinusOne.get(powerOfTwoMinusOne);
    }*/
    public static Map<BigInteger,Integer> factorPowerOfTwoMinusOne(BigInteger powerOfTwoMinusOne){
        if(bigIntegerFactorsPowersOfTwoMinusOne.containsKey(powerOfTwoMinusOne)) {
            return bigIntegerFactorsPowersOfTwoMinusOne.get(powerOfTwoMinusOne);
        }else{
            return factorMap(powerOfTwoMinusOne);//likely to take while!!!
        }
    }
    public static BigInteger[] factorPowerOfTwoMinusOne_Array(BigInteger powerOfTwoMinusOne){
        Map<BigInteger,Integer> factors = factorPowerOfTwoMinusOne(powerOfTwoMinusOne);
        int nFactors=0;
        for(BigInteger factor:factors.keySet()){
            nFactors+=factors.get(factor);
        }
        BigInteger[] out = new BigInteger[nFactors];
        int i=0;
        for(BigInteger factor:factors.keySet()){
            int n=factors.get(factor);
            for(;n>0;n--) out[i++] = factor;
        }
        return out;
    }

    static void writeMersenneFactors(){
        ArrayList<BigInteger> nums = new ArrayList<BigInteger>();
        nums.addAll(bigIntegerFactorsPowersOfTwoMinusOne.keySet());
        Collections.sort(nums);
        for(BigInteger num:nums){
            Map<BigInteger,Integer> factors = bigIntegerFactorsPowersOfTwoMinusOne.get(num);
            System.out.print(num.toString(16).toUpperCase());
            ArrayList<BigInteger> sortedFactors = new ArrayList<BigInteger>();
            sortedFactors.addAll(factors.keySet());
            Collections.sort(sortedFactors);
            for(BigInteger f:sortedFactors){
                System.out.print(" "+f.toString(16).toUpperCase()+" "+BigInteger.valueOf(factors.get(f)).toString(16).toUpperCase());
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        writeMersenneFactors();
        BigInteger n;
        if(args.length==0){
            Scanner sc = new Scanner(System.in);

            System.out.print("number to factor: ");
            n= new BigInteger(sc.nextLine());
        } else {
            n = new BigInteger(args[0]);
        }
        BigInteger[] factors = factor(n);
        for(BigInteger f:factors){
            System.out.println(f);
        }
    }/*
    static Map<Integer,Map<Integer,Integer>> intFactorsPowersOfTwoMinusOne = new HashMap<Integer,Map<Integer,Integer>>(31);
    static {
        Map<Integer,Integer> factorsMap = new HashMap<Integer,Integer>();
        factorsMap.put(1,1);
        intFactorsPowersOfTwoMinusOne.put(0x1,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(3,1);
        intFactorsPowersOfTwoMinusOne.put(0x3,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(7,1);
        intFactorsPowersOfTwoMinusOne.put(0x7,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(3,1);
        factorsMap.put(5,1);
        intFactorsPowersOfTwoMinusOne.put(0xf,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(31,1);
        intFactorsPowersOfTwoMinusOne.put(0x1f,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(3,2);
        factorsMap.put(7,1);
        intFactorsPowersOfTwoMinusOne.put(0x3f,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(127,1);
        intFactorsPowersOfTwoMinusOne.put(0x7f,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(17,1);
        factorsMap.put(3,1);
        factorsMap.put(5,1);
        intFactorsPowersOfTwoMinusOne.put(0xff,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(7,1);
        factorsMap.put(73,1);
        intFactorsPowersOfTwoMinusOne.put(0x1ff,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(3,1);
        factorsMap.put(11,1);
        factorsMap.put(31,1);
        intFactorsPowersOfTwoMinusOne.put(0x3ff,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(23,1);
        factorsMap.put(89,1);
        intFactorsPowersOfTwoMinusOne.put(0x7ff,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(3,2);
        factorsMap.put(5,1);
        factorsMap.put(7,1);
        factorsMap.put(13,1);
        intFactorsPowersOfTwoMinusOne.put(0xfff,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(8191,1);
        intFactorsPowersOfTwoMinusOne.put(0x1fff,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(3,1);
        factorsMap.put(127,1);
        factorsMap.put(43,1);
        intFactorsPowersOfTwoMinusOne.put(0x3fff,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(7,1);
        factorsMap.put(31,1);
        factorsMap.put(151,1);
        intFactorsPowersOfTwoMinusOne.put(0x7fff,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(17,1);
        factorsMap.put(257,1);
        factorsMap.put(3,1);
        factorsMap.put(5,1);
        intFactorsPowersOfTwoMinusOne.put(0xffff,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(131071,1);
        intFactorsPowersOfTwoMinusOne.put(0x1ffff,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(19,1);
        factorsMap.put(3,3);
        factorsMap.put(7,1);
        factorsMap.put(73,1);
        intFactorsPowersOfTwoMinusOne.put(0x3ffff,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(524287,1);
        intFactorsPowersOfTwoMinusOne.put(0x7ffff,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(3,1);
        factorsMap.put(5,2);
        factorsMap.put(11,1);
        factorsMap.put(41,1);
        factorsMap.put(31,1);
        intFactorsPowersOfTwoMinusOne.put(0xfffff,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(337,1);
        factorsMap.put(7,2);
        factorsMap.put(127,1);
        intFactorsPowersOfTwoMinusOne.put(0x1fffff,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(3,1);
        factorsMap.put(683,1);
        factorsMap.put(23,1);
        factorsMap.put(89,1);
        intFactorsPowersOfTwoMinusOne.put(0x3fffff,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(178481,1);
        factorsMap.put(47,1);
        intFactorsPowersOfTwoMinusOne.put(0x7fffff,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(17,1);
        factorsMap.put(3,2);
        factorsMap.put(5,1);
        factorsMap.put(7,1);
        factorsMap.put(13,1);
        factorsMap.put(241,1);
        intFactorsPowersOfTwoMinusOne.put(0xffffff,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(1801,1);
        factorsMap.put(601,1);
        factorsMap.put(31,1);
        intFactorsPowersOfTwoMinusOne.put(0x1ffffff,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(3,1);
        factorsMap.put(2731,1);
        factorsMap.put(8191,1);
        intFactorsPowersOfTwoMinusOne.put(0x3ffffff,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(262657,1);
        factorsMap.put(7,1);
        factorsMap.put(73,1);
        intFactorsPowersOfTwoMinusOne.put(0x7ffffff,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(3,1);
        factorsMap.put(5,1);
        factorsMap.put(113,1);
        factorsMap.put(127,1);
        factorsMap.put(43,1);
        factorsMap.put(29,1);
        intFactorsPowersOfTwoMinusOne.put(0xfffffff,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(1103,1);
        factorsMap.put(233,1);
        factorsMap.put(2089,1);
        intFactorsPowersOfTwoMinusOne.put(0x1fffffff,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(3,2);
        factorsMap.put(7,1);
        factorsMap.put(11,1);
        factorsMap.put(331,1);
        factorsMap.put(31,1);
        factorsMap.put(151,1);
        intFactorsPowersOfTwoMinusOne.put(0x3fffffff,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(2147483647,1);
        intFactorsPowersOfTwoMinusOne.put(0x7fffffff,factorsMap);
    }
    static Map<Long,Map<Long,Integer>> longFactorsPowersOfTwoMinusOne = new HashMap<Long,Map<Long,Integer>>(63);
    static {
        Map<Long,Integer> factorsMap = new HashMap<Long,Integer>();
        factorsMap.put(1L,1);
        longFactorsPowersOfTwoMinusOne.put(0x1L,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(3L,1);
        longFactorsPowersOfTwoMinusOne.put(0x3L,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(7L,1);
        longFactorsPowersOfTwoMinusOne.put(0x7L,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(3L,1);
        factorsMap.put(5L,1);
        longFactorsPowersOfTwoMinusOne.put(0xfL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(31L,1);
        longFactorsPowersOfTwoMinusOne.put(0x1fL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(3L,2);
        factorsMap.put(7L,1);
        longFactorsPowersOfTwoMinusOne.put(0x3fL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(127L,1);
        longFactorsPowersOfTwoMinusOne.put(0x7fL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(17L,1);
        factorsMap.put(3L,1);
        factorsMap.put(5L,1);
        longFactorsPowersOfTwoMinusOne.put(0xffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(7L,1);
        factorsMap.put(73L,1);
        longFactorsPowersOfTwoMinusOne.put(0x1ffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(3L,1);
        factorsMap.put(11L,1);
        factorsMap.put(31L,1);
        longFactorsPowersOfTwoMinusOne.put(0x3ffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(23L,1);
        factorsMap.put(89L,1);
        longFactorsPowersOfTwoMinusOne.put(0x7ffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(3L,2);
        factorsMap.put(5L,1);
        factorsMap.put(7L,1);
        factorsMap.put(13L,1);
        longFactorsPowersOfTwoMinusOne.put(0xfffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(8191L,1);
        longFactorsPowersOfTwoMinusOne.put(0x1fffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(3L,1);
        factorsMap.put(127L,1);
        factorsMap.put(43L,1);
        longFactorsPowersOfTwoMinusOne.put(0x3fffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(7L,1);
        factorsMap.put(31L,1);
        factorsMap.put(151L,1);
        longFactorsPowersOfTwoMinusOne.put(0x7fffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(17L,1);
        factorsMap.put(257L,1);
        factorsMap.put(3L,1);
        factorsMap.put(5L,1);
        longFactorsPowersOfTwoMinusOne.put(0xffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(131071L,1);
        longFactorsPowersOfTwoMinusOne.put(0x1ffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(19L,1);
        factorsMap.put(3L,3);
        factorsMap.put(7L,1);
        factorsMap.put(73L,1);
        longFactorsPowersOfTwoMinusOne.put(0x3ffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(524287L,1);
        longFactorsPowersOfTwoMinusOne.put(0x7ffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(3L,1);
        factorsMap.put(5L,2);
        factorsMap.put(41L,1);
        factorsMap.put(11L,1);
        factorsMap.put(31L,1);
        longFactorsPowersOfTwoMinusOne.put(0xfffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(337L,1);
        factorsMap.put(7L,2);
        factorsMap.put(127L,1);
        longFactorsPowersOfTwoMinusOne.put(0x1fffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(3L,1);
        factorsMap.put(683L,1);
        factorsMap.put(23L,1);
        factorsMap.put(89L,1);
        longFactorsPowersOfTwoMinusOne.put(0x3fffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(178481L,1);
        factorsMap.put(47L,1);
        longFactorsPowersOfTwoMinusOne.put(0x7fffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(17L,1);
        factorsMap.put(3L,2);
        factorsMap.put(5L,1);
        factorsMap.put(7L,1);
        factorsMap.put(13L,1);
        factorsMap.put(241L,1);
        longFactorsPowersOfTwoMinusOne.put(0xffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(1801L,1);
        factorsMap.put(601L,1);
        factorsMap.put(31L,1);
        longFactorsPowersOfTwoMinusOne.put(0x1ffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(3L,1);
        factorsMap.put(2731L,1);
        factorsMap.put(8191L,1);
        longFactorsPowersOfTwoMinusOne.put(0x3ffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(262657L,1);
        factorsMap.put(7L,1);
        factorsMap.put(73L,1);
        longFactorsPowersOfTwoMinusOne.put(0x7ffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(3L,1);
        factorsMap.put(5L,1);
        factorsMap.put(113L,1);
        factorsMap.put(127L,1);
        factorsMap.put(43L,1);
        factorsMap.put(29L,1);
        longFactorsPowersOfTwoMinusOne.put(0xfffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(1103L,1);
        factorsMap.put(233L,1);
        factorsMap.put(2089L,1);
        longFactorsPowersOfTwoMinusOne.put(0x1fffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(3L,2);
        factorsMap.put(7L,1);
        factorsMap.put(11L,1);
        factorsMap.put(331L,1);
        factorsMap.put(31L,1);
        factorsMap.put(151L,1);
        longFactorsPowersOfTwoMinusOne.put(0x3fffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(2147483647L,1);
        longFactorsPowersOfTwoMinusOne.put(0x7fffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(65537L,1);
        factorsMap.put(17L,1);
        factorsMap.put(257L,1);
        factorsMap.put(3L,1);
        factorsMap.put(5L,1);
        longFactorsPowersOfTwoMinusOne.put(0xffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(23L,1);
        factorsMap.put(7L,1);
        factorsMap.put(89L,1);
        factorsMap.put(599479L,1);
        longFactorsPowersOfTwoMinusOne.put(0x1ffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(131071L,1);
        factorsMap.put(3L,1);
        factorsMap.put(43691L,1);
        longFactorsPowersOfTwoMinusOne.put(0x3ffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(71L,1);
        factorsMap.put(122921L,1);
        factorsMap.put(127L,1);
        factorsMap.put(31L,1);
        longFactorsPowersOfTwoMinusOne.put(0x7ffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(19L,1);
        factorsMap.put(3L,3);
        factorsMap.put(5L,1);
        factorsMap.put(37L,1);
        factorsMap.put(7L,1);
        factorsMap.put(109L,1);
        factorsMap.put(73L,1);
        factorsMap.put(13L,1);
        longFactorsPowersOfTwoMinusOne.put(0xfffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(616318177L,1);
        factorsMap.put(223L,1);
        longFactorsPowersOfTwoMinusOne.put(0x1fffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(3L,1);
        factorsMap.put(524287L,1);
        factorsMap.put(174763L,1);
        longFactorsPowersOfTwoMinusOne.put(0x3fffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(121369L,1);
        factorsMap.put(7L,1);
        factorsMap.put(79L,1);
        factorsMap.put(8191L,1);
        longFactorsPowersOfTwoMinusOne.put(0x7fffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(61681L,1);
        factorsMap.put(17L,1);
        factorsMap.put(3L,1);
        factorsMap.put(5L,2);
        factorsMap.put(41L,1);
        factorsMap.put(11L,1);
        factorsMap.put(31L,1);
        longFactorsPowersOfTwoMinusOne.put(0xffffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(164511353L,1);
        factorsMap.put(13367L,1);
        longFactorsPowersOfTwoMinusOne.put(0x1ffffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(5419L,1);
        factorsMap.put(3L,2);
        factorsMap.put(337L,1);
        factorsMap.put(7L,2);
        factorsMap.put(127L,1);
        factorsMap.put(43L,1);
        longFactorsPowersOfTwoMinusOne.put(0x3ffffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(9719L,1);
        factorsMap.put(431L,1);
        factorsMap.put(2099863L,1);
        longFactorsPowersOfTwoMinusOne.put(0x7ffffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(3L,1);
        factorsMap.put(683L,1);
        factorsMap.put(2113L,1);
        factorsMap.put(5L,1);
        factorsMap.put(397L,1);
        factorsMap.put(23L,1);
        factorsMap.put(89L,1);
        longFactorsPowersOfTwoMinusOne.put(0xfffffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(631L,1);
        factorsMap.put(7L,1);
        factorsMap.put(23311L,1);
        factorsMap.put(73L,1);
        factorsMap.put(31L,1);
        factorsMap.put(151L,1);
        longFactorsPowersOfTwoMinusOne.put(0x1fffffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(2796203L,1);
        factorsMap.put(3L,1);
        factorsMap.put(178481L,1);
        factorsMap.put(47L,1);
        longFactorsPowersOfTwoMinusOne.put(0x3fffffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(4513L,1);
        factorsMap.put(13264529L,1);
        factorsMap.put(2351L,1);
        longFactorsPowersOfTwoMinusOne.put(0x7fffffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(17L,1);
        factorsMap.put(257L,1);
        factorsMap.put(3L,2);
        factorsMap.put(5L,1);
        factorsMap.put(97L,1);
        factorsMap.put(7L,1);
        factorsMap.put(13L,1);
        factorsMap.put(673L,1);
        factorsMap.put(241L,1);
        longFactorsPowersOfTwoMinusOne.put(0xffffffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(127L,1);
        factorsMap.put(4432676798593L,1);
        longFactorsPowersOfTwoMinusOne.put(0x1ffffffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(4051L,1);
        factorsMap.put(3L,1);
        factorsMap.put(251L,1);
        factorsMap.put(1801L,1);
        factorsMap.put(601L,1);
        factorsMap.put(11L,1);
        factorsMap.put(31L,1);
        longFactorsPowersOfTwoMinusOne.put(0x3ffffffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(131071L,1);
        factorsMap.put(103L,1);
        factorsMap.put(7L,1);
        factorsMap.put(2143L,1);
        factorsMap.put(11119L,1);
        longFactorsPowersOfTwoMinusOne.put(0x7ffffffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(3L,1);
        factorsMap.put(2731L,1);
        factorsMap.put(1613L,1);
        factorsMap.put(157L,1);
        factorsMap.put(5L,1);
        factorsMap.put(53L,1);
        factorsMap.put(8191L,1);
        longFactorsPowersOfTwoMinusOne.put(0xfffffffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(6361L,1);
        factorsMap.put(20394401L,1);
        factorsMap.put(69431L,1);
        longFactorsPowersOfTwoMinusOne.put(0x1fffffffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(262657L,1);
        factorsMap.put(19L,1);
        factorsMap.put(3L,4);
        factorsMap.put(7L,1);
        factorsMap.put(87211L,1);
        factorsMap.put(73L,1);
        longFactorsPowersOfTwoMinusOne.put(0x3fffffffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(881L,1);
        factorsMap.put(23L,1);
        factorsMap.put(3191L,1);
        factorsMap.put(201961L,1);
        factorsMap.put(89L,1);
        factorsMap.put(31L,1);
        longFactorsPowersOfTwoMinusOne.put(0x7fffffffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(17L,1);
        factorsMap.put(15790321L,1);
        factorsMap.put(3L,1);
        factorsMap.put(5L,1);
        factorsMap.put(113L,1);
        factorsMap.put(127L,1);
        factorsMap.put(43L,1);
        factorsMap.put(29L,1);
        longFactorsPowersOfTwoMinusOne.put(0xffffffffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(32377L,1);
        factorsMap.put(524287L,1);
        factorsMap.put(7L,1);
        factorsMap.put(1212847L,1);
        longFactorsPowersOfTwoMinusOne.put(0x1ffffffffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(1103L,1);
        factorsMap.put(3L,1);
        factorsMap.put(233L,1);
        factorsMap.put(59L,1);
        factorsMap.put(3033169L,1);
        factorsMap.put(2089L,1);
        longFactorsPowersOfTwoMinusOne.put(0x3ffffffffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(179951L,1);
        factorsMap.put(3203431780337L,1);
        longFactorsPowersOfTwoMinusOne.put(0x7ffffffffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(1321L,1);
        factorsMap.put(3L,2);
        factorsMap.put(5L,2);
        factorsMap.put(7L,1);
        factorsMap.put(11L,1);
        factorsMap.put(41L,1);
        factorsMap.put(331L,1);
        factorsMap.put(13L,1);
        factorsMap.put(61L,1);
        factorsMap.put(31L,1);
        factorsMap.put(151L,1);
        longFactorsPowersOfTwoMinusOne.put(0xfffffffffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(2305843009213693951L,1);
        longFactorsPowersOfTwoMinusOne.put(0x1fffffffffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(3L,1);
        factorsMap.put(715827883L,1);
        factorsMap.put(2147483647L,1);
        longFactorsPowersOfTwoMinusOne.put(0x3fffffffffffffffL,factorsMap);
        }     static {         Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(92737L,1);
        factorsMap.put(337L,1);
        factorsMap.put(649657L,1);
        factorsMap.put(7L,2);
        factorsMap.put(127L,1);
        factorsMap.put(73L,1);
        longFactorsPowersOfTwoMinusOne.put(0x7fffffffffffffffL,factorsMap);
    }*/
    static Map<BigInteger,Map<BigInteger,Integer>> bigIntegerFactorsPowersOfTwoMinusOne = new HashMap<BigInteger,Map<BigInteger,Integer>>(2000);
    static {
        Map<BigInteger, Integer> factorsMap = new HashMap<BigInteger, Integer>();
        factorsMap.put(new BigInteger("1", 16), 1);
        bigIntegerFactorsPowersOfTwoMinusOne.put(new BigInteger("1", 16), factorsMap);
    }

}