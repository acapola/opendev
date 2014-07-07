/**
 * Ariel
 * ConsoleInterface.java
 *
 * provides a console interface to Ariel
 *
 *
 * @author	nicholasink
 * @version	Mar 2, 2009
 */

package sieve;

import java.math.BigInteger;
import java.util.Scanner;

public class ConsoleInterface {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("number to factor: ");
		BigInteger n = new BigInteger(sc.nextLine());
		System.out.print("size of prime base: ");
		Integer r = new Integer(sc.nextLine());
		System.out.print("max congruences: ");
		Long m = new Long(sc.nextLine());
		
		Sieve s = new Sieve(n, r, m, 12, System.out);
		s.run();
		System.out.println("factors: " + s.getFactor1() + ", " + s.getFactor2());
	}

}
