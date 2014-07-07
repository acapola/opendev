/**
 * Ariel
 * Sieve.java
 *
 * quadratic sieve implementation
 * @version	Jan 31, 2009
 */

package sieve;

import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Vector;

import matrix.Matrix;
import util.BigSquareRoot;
import util.Polynomial;

public class Sieve implements Runnable {
	private long[] firstPrimes = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97 };
	private Vector<Long> primeBase;
	private Vector<Vector<Integer>> factorizationsVector;
	private BigInteger n;
	private BigInteger sqrt;
	private Polynomial q;
	private Vector<BigInteger> xVector;
	private Vector<BigInteger> yVector;
	private Matrix factorizationsMatrix;
	private final int PRIMEBASE_SIZE;
	private final long MAX_CONGRUENCES;
	private long totalCongruences;
	private BigInteger xValue;
	private BigInteger yValue;
	private long factor1;
	private long factor2;
	private PrintStream out;
	private long startTime;

	Sieve(BigInteger n, int baseSize, long maxCongruences, int primalityConfidence, PrintStream out) {
		startTime = System.currentTimeMillis();
		PRIMEBASE_SIZE = baseSize;
		MAX_CONGRUENCES = maxCongruences;
		this.out = out;
		out.println("Factoring " + n);
		constructSieve(primalityConfidence, n);
	}

	private void collectSieveData() {
		for (totalCongruences = 0; factorizationsVector.size() < primeBase
				.size() + 1; totalCongruences++) {
			BigInteger y = q
					.get(new BigInteger(Long.toString(totalCongruences)));
			BigInteger reduced = y.add(BigInteger.ZERO);

			Vector<Integer> factorizationVector = new Vector<Integer>(primeBase
					.size());

			// attempt to factor completely with primes in the base
			for (int i = 0; i < primeBase.size(); i++) {
				long prime = primeBase.get(i);
				BigInteger remainder = BigInteger.ZERO;
				int j;
				for (j = 0; remainder.equals(BigInteger.ZERO); j++) {
					BigInteger[] division = reduced
							.divideAndRemainder(new BigInteger(Long
									.toString(prime)));
					BigInteger quotient = division[0];
					remainder = division[1];
					if (remainder.equals(BigInteger.ZERO))
						reduced = quotient;
				}
				factorizationVector.add(i, j - 1);
			}

			if (reduced.equals(BigInteger.ONE)) {
				xVector.add(new BigInteger(new Long(totalCongruences).toString()));
				yVector.add(q.get(new BigInteger(Long
						.toString(totalCongruences))));
				factorizationsVector.add(factorizationVector);
			}

			if (totalCongruences == MAX_CONGRUENCES) {
				System.err.println(xVector.size() + " smooth quadrics, "
						+ (primeBase.size() + 1) + " needed");
				throw new IllegalArgumentException(
						"Chunk size exceeded (MAX_CONGRUENCES:"
								+ MAX_CONGRUENCES + " reached)");
			}
		}
	}

	private void constructSieve(int primalityConfidence, BigInteger n) {
		this.n = n;

		if (this.n.isProbablePrime(primalityConfidence))
			throw new IllegalArgumentException("Input is probably prime ("
					+ this.n + ")");

		BigSquareRoot sqrtFinder = new BigSquareRoot();
		this.sqrt = sqrtFinder.get(n).toBigInteger();

		if (this.sqrt.pow(2).equals(n))
			throw new IllegalArgumentException("Input is square (" + this.sqrt
					+ "^2)");

		// determine the polynomial to use and cache it for later
		Vector<BigInteger> coefficients = new Vector<BigInteger>();
		coefficients.add((this.n.multiply(BigInteger.ZERO
				.subtract(BigInteger.ONE))).add(this.sqrt.pow(2)));
		coefficients.add(this.sqrt.multiply(new BigInteger("2")));
		coefficients.add(BigInteger.ONE);
		this.q = new Polynomial(coefficients);

		xVector = new Vector<BigInteger>();
		yVector = new Vector<BigInteger>();

		factorizationsVector = new Vector<Vector<Integer>>();

		totalCongruences = 0;
	}

	private void constructFactorizationsMatrix() {
		factorizationsMatrix = new Matrix(factorizationsVector.size(),
				primeBase.size());
		for (int i = 0; i < factorizationsVector.size(); i++) {
			for (int j = 0; j < primeBase.size(); j++)
				factorizationsMatrix.set(i, j, (factorizationsVector.get(i)
						.get(j)) % 2);
		}
	}

	private void constructPrimeBase(long numPrimes) {
		primeBase = new Vector<Long>();

		for (int i = 0; i < firstPrimes.length && primeBase.size() < numPrimes; i++) {
			if (firstPrimes[i] > n.longValue())
				break;
			if (isQuadraticResidue(firstPrimes[i], n.longValue()))
				primeBase.add(firstPrimes[i]);
		}

		// search for larger primes beyond those in the base
		long prime = firstPrimes[firstPrimes.length - 1];
		for (long i = prime; primeBase.size() < numPrimes; i++) {
			prime += 1;
			if (prime > n.longValue())
				break;
			if (new BigInteger(Long.toString(prime)).isProbablePrime(10)
					&& prime <= n.longValue())
				primeBase.add(prime);
		}
	}

	private static long gcd(long a, long b) {
		if (b == 0)
			return a;
		return gcd(b, a % b);
	}

	/**
	 * @return Returns the factor1.
	 */
	public long getFactor1() {
		return factor1;
	}

	/**
	 * @return Returns the factor2.
	 */
	public long getFactor2() {
		return factor2;
	}

	public Matrix getFactorizationsMatrix() {
		return factorizationsMatrix;
	}

	public Vector<Vector<Integer>> getFactorizationsVector() {
		return factorizationsVector;
	}

	/**
	 * @return Returns the n.
	 */
	public BigInteger getModulus() {
		return n;
	}

	public Vector<Long> getPrimeBase() {
		return primeBase;
	}

	/**
	 * @return Returns the sqrt.
	 */
	public BigInteger getSqrt() {
		return sqrt;
	}

	public BigInteger getXValue() {
		return xValue;
	}

	public Vector<BigInteger> getXVector() {
		return xVector;
	}

	public BigInteger getYValue() {
		return yValue;
	}

	public Vector<BigInteger> getYVector() {
		return yVector;
	}

	public static boolean isQuadraticResidue(long q, long n) {
		return legendre(n, (q - 1) / 2, 1, q) == 1;
	}

	private boolean isZeroVector(double[] vector) {
		for (double x : vector)
			if (x != 0)
				return false;
		return true;
	}

	public static int legendre(long a, long q, long l, long n) {
		long x = power(q, l);
		long z = 1;

		a = a % n;

		if (x == 0)
			return (int) z;

		while (x != 0) {
			if (x % 2 == 0) {
				a = power(a, 2) % n;
				x /= 2;
			} else {
				x--;
				z = (z * a) % n;
			}
		}

		return (int) z;
	}

	public static long power(long a, long b) {
		return (int) Math.pow((double) a, (double) b);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		constructPrimeBase(PRIMEBASE_SIZE);

		out.println("prime base: " + primeBase);

		collectSieveData();
		constructFactorizationsMatrix();

		out.println("total congruences: " + totalCongruences + " ("
				+ (primeBase.size() + 1) + " smooth congruences)");

		out.println();

		out.println("x\tq(x)\tfactorization (mod 2)");
		for (int i = 0; i < xVector.size(); i++)
			out.println(xVector.get(i) + "\t" + yVector.get(i) + "\t"
					+ factorizationsMatrix.getRowAsVector(i));

		out.println();

		Vector<Integer> zeroRows = new Vector<Integer>();
		for (int i = 0; i < factorizationsMatrix.getRowDimension(); i++) {
			if (isZeroVector(factorizationsMatrix.getArray()[i]))
				zeroRows.add(i);
		}

		// create a new transposed matrix from only the linearly independent
		// rows, and store the indices of those rows in basisVectors
		Vector<Vector<Double>> basisVectors = new Vector<Vector<Double>>(
				factorizationsMatrix.getRowDimension());
		Vector<Integer> basisIndices = new Vector<Integer>(factorizationsMatrix
				.getRowDimension());
		Vector<Integer> dependentIndices = new Vector<Integer>(
				factorizationsMatrix.getRowDimension());

		// construct the basis vectors out of the linearly independent rows
		// begin by finding the first nonzero row in the matrix
		int row = 0;
		while (zeroRows.contains(row)
				&& row < factorizationsMatrix.getRowDimension()) {
			dependentIndices.add(row);
			row++;
		}

		basisVectors.add(factorizationsMatrix.getRowAsVector(row));
		basisIndices.add(row);
		row++;

		while (row < factorizationsMatrix.getRowDimension()) {
			Vector v = factorizationsMatrix.getRowAsVector(row);
			if (LinearAlgebra.vectorLinearlyIndependent(basisVectors, v)) {
				basisVectors.add(v);
				basisIndices.add(row);
			} else
				dependentIndices.add(row);

			row++;
		}

		// create a double array from the vector matrix
		double[][] A = new double[basisVectors.size()][factorizationsMatrix
				.getColumnDimension()];
		for (int i = 0; i < basisVectors.size(); i++) {
			Vector<Double> rowVector = basisVectors.get(i);
			for (int j = 0; j < factorizationsMatrix.getColumnDimension(); j++)
				A[i][j] = rowVector.get(j);
		}
		Matrix basisMatrix = new Matrix(A);

		/*
		 * out.println("factorizations matrix (mod 2)");
		 * out.println(factorizationsMatrix);
		 */
		out.println("linearly dependent rows: " + dependentIndices);
		out.println("basis matrix [" + basisMatrix.getRowDimension() + "x" + basisMatrix.getColumnDimension() + "]");
		//out.println(basisMatrix);
		out.println();

		factor1 = 1;
		factor2 = 1;

		for (int i = 0; i < dependentIndices.size() && factor1 == 1
				&& factor2 == 1; i++) {
			int combinationRow = dependentIndices.get(i);

			out.print("combination row: ");
			out.println(combinationRow);

			Matrix b = new Matrix(factorizationsMatrix.getColumnDimension(), 1);
			for (int j = 0; j < factorizationsMatrix.getColumnDimension(); j++)
				b.set(j, 0, factorizationsMatrix.get(combinationRow, j));

			Vector<Double> combinationVectorSolution = basisMatrix.transpose()
					.solve(b).transpose().getRowAsVector(0);

			// sometimes the vector isn't completely exact
			// round to integers accordingly (also convert)
			// to absolute value)
			for (int j = 0; j < combinationVectorSolution.size(); j++) {
				double d = combinationVectorSolution.get(j).doubleValue();
				combinationVectorSolution.set(j, (double) Math.round(Math
						.abs(d)));
			}

			out.print("combination vector: ");
			out.println(b.transpose().getRowAsVector(0));
			out.print("combination vector solution: ");
			out.println(combinationVectorSolution);

			out.println();

			xValue = xVector.get(combinationRow);
			for (int x = 0; x < combinationVectorSolution.size(); x++) {
				int exponent = combinationVectorSolution.get(x).intValue();
				BigInteger xFactor = BigInteger.ONE;

				while (exponent > 0) {
					xFactor.multiply(xVector.get(basisIndices.get(x)));
					exponent--;
				}

				xValue.multiply(xFactor);
			}

			xValue = xValue.mod(n);
			yValue = q.get(xValue);

			out.println("x: " + xVector.get(combinationRow) + " > " + xValue);
			out.println("y: " + yValue);

			if (yValue.compareTo(BigInteger.ZERO) > 0) {
				BigSquareRoot sqrtFinder = new BigSquareRoot();
				BigInteger ySquareRoot = sqrtFinder.get(yValue).toBigInteger();
	
				factor1 = gcd(xValue.add(sqrt.add(ySquareRoot)).longValue(), n
						.longValue());
				factor2 = gcd(xValue.add(sqrt.subtract(ySquareRoot)).longValue(), n
						.longValue());
	
				if (factor1 == 1 && factor2 == 1)
					out.println("trivial solution");
	
				out.println();
			}
		}
		
		out.println((System.currentTimeMillis() - startTime) + "ms elapsed for factorization");

		if (factor1 == 1 && factor2 == 1) {
			throw new IllegalArgumentException(
					"Could not find nontrivial solution");
		}
	}

}
