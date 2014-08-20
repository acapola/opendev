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


    public static void main(String[] args) {
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
    }
}