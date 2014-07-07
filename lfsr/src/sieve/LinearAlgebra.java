/**
 * Ariel
 * LinearAlgebraAgent.java
 *
 * assists with Linear Algebra tasks,
 * namely determining if a vector is
 * linearly independent from other
 * vectors
 *
 *
 * @author	nicholasink
 * @version	May 12, 2009
 */

package sieve;

import java.util.Vector;

import matrix.Matrix;

public class LinearAlgebra {
	
	public static boolean vectorLinearlyIndependent(Vector<Vector<Double>> priorVectors, Vector<Double> newVector) {
		// assemble the prior vectors into a matrix
		double[][] vectorsArray = new double[priorVectors.size() + 1][priorVectors.get(0).size()];
		for (int i = 0; i < priorVectors.size(); i++) {
			for (int j = 0; j < vectorsArray[0].length; j++)
				vectorsArray[i][j] = priorVectors.get(i).get(j);
		}
		for (int i = 0; i < newVector.size(); i++)
			vectorsArray[vectorsArray.length - 1][i] = newVector.get(i);
		
		Matrix A = new Matrix(vectorsArray);
		
		return A.getRowDimension() == A.rank();
	}
	
}
