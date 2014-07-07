/**
 * Ariel
 * Polynomial.java
 *
 * provides abstraction of a polynomial
 * @version	Jan 31, 2009
 */

package util;

import java.math.BigInteger;
import java.util.Vector;

public class Polynomial {
	Vector<BigInteger> coefficients;

	public Polynomial(Vector<BigInteger> coefficients) {
		this.coefficients = coefficients;
	}

	public BigInteger get(BigInteger in) {
		BigInteger output = BigInteger.ZERO;

		for (int i = 0; i < coefficients.size(); i++) {
			output = output.add(coefficients.get(i).multiply(in.pow(i)));
		}

		return output;
	}

	public String toString() {
		String s = new String();

		int i = 0;
		for (BigInteger coefficient : coefficients) {
			s += coefficient + "x^" + i + " + ";
			i++;
		}

		return s;
	}
}
