/**
 * Ariel
 * SieveDiagnosticAgent.java
 *
 * checks the sieve for errors in bulk
 *
 *
 * @author	nicholasink
 * @version	May 12, 2009
 */

package sieve;

import java.io.PrintStream;
import java.math.BigInteger;

import util.BigSquareRoot;
import util.BitBucketOutputStream;

public class SieveDiagnosticAgent {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for (int i = 100; i < 32768; i++) {
			BigInteger n = new BigInteger(new Integer(i).toString());

			boolean isSquare = false;
			BigSquareRoot sqrtFinder = new BigSquareRoot();
			BigInteger sqrt = sqrtFinder.get(n).toBigInteger();

			if (sqrt.pow(2).equals(n))
				isSquare = true;

			if (!n.isProbablePrime(8) && !isSquare) {
				long start = System.currentTimeMillis();
				try {
					int baseSize = ((int) Math.ceil(Math.sqrt(n.doubleValue()))) * 2;
					Sieve s = new Sieve(n, baseSize, 500000, 8,
							new PrintStream(new BitBucketOutputStream()));
					s.run();
					long end = System.currentTimeMillis();
					System.out.println("factors of " + n + ": "
							+ s.getFactor1() + ", " + s.getFactor2()
							+ "\t\t[time elapsed: " + (end - start)
							+ " ms, base size: " + baseSize + "]");
				} catch (Exception e) {
					System.err.println("error factoring " + i);
					System.err.println(e.getLocalizedMessage());
				}
			} else if (n.isProbablePrime(6))
				System.out.println(n + " is prime");
			else
				System.out.println(n + " is square");
		}
	}

}
