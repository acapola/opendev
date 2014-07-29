package uk.co.nimp;

import org.junit.Test;

import java.io.File;
import java.math.BigInteger;
import java.util.Random;

public class Z2Test {

    @Test
    public void testRotateToMinValue() throws Exception {
        assert(Z2.equal(Z2.rotateToMinValue(Z2.toBooleans("0")),Z2.toBooleans("0")));
        assert(Z2.equal(Z2.rotateToMinValue(Z2.toBooleans("1")),Z2.toBooleans("1")));
        assert(Z2.equal(Z2.rotateToMinValue(Z2.toBooleans("10")),Z2.toBooleans("10")));
        assert(Z2.equal(Z2.rotateToMinValue(Z2.toBooleans("01")),Z2.toBooleans("10")));
        assert(Z2.equal(Z2.rotateToMinValue(Z2.toBooleans("100")),Z2.toBooleans("100")));
        assert(Z2.equal(Z2.rotateToMinValue(Z2.toBooleans("010")),Z2.toBooleans("100")));
        assert(Z2.equal(Z2.rotateToMinValue(Z2.toBooleans("001")),Z2.toBooleans("100")));
        assert(Z2.equal(Z2.rotateToMinValue(Z2.toBooleans("110")),Z2.toBooleans("110")));
        assert(Z2.equal(Z2.rotateToMinValue(Z2.toBooleans("011")),Z2.toBooleans("110")));
        assert(Z2.equal(Z2.rotateToMinValue(Z2.toBooleans("101")),Z2.toBooleans("110")));
        assert(Z2.equal(Z2.rotateToMinValue(Z2.toBooleans("1100")),Z2.toBooleans("1100")));
        assert(Z2.equal(Z2.rotateToMinValue(Z2.toBooleans("0110")),Z2.toBooleans("1100")));
        assert(Z2.equal(Z2.rotateToMinValue(Z2.toBooleans("0011")),Z2.toBooleans("1100")));
        assert(Z2.equal(Z2.rotateToMinValue(Z2.toBooleans("1001")),Z2.toBooleans("1100")));
        assert(Z2.equal(Z2.rotateToMinValue(Z2.toBooleans("1110101110000")),Z2.toBooleans("1110101110000")));
        assert(Z2.equal(Z2.rotateToMinValue(Z2.toBooleans("1101011100001")),Z2.toBooleans("1110101110000")));
        assert(Z2.equal(Z2.rotateToMinValue(Z2.toBooleans("1010111000011")),Z2.toBooleans("1110101110000")));

    }

    @Test
    public void testDerivative() throws Exception {
        //System.out.println(Z2.toPolynomial(Z2.derivative(Z2.polynomialToBooleans("x5+x4+1"))));
        assert(Z2.equal(Z2.derivative(Z2.polynomialToBooleans("x4+x2+1")),Z2.polynomialToBooleans("0")));
        assert(Z2.equal(Z2.derivative(Z2.polynomialToBooleans("x4+x2+x+1")),Z2.polynomialToBooleans("1")));
        assert(Z2.equal(Z2.derivative(Z2.polynomialToBooleans("x5+x4+1")),Z2.polynomialToBooleans("x4")));
        assert(Z2.equal(Z2.derivative(Z2.polynomialToBooleans("x5+x4+x2+1")),Z2.polynomialToBooleans("x4")));
        assert(Z2.equal(Z2.derivative(Z2.polynomialToBooleans("x5+x4+x2+x+1")),Z2.polynomialToBooleans("x4+1")));
    }

    static boolean checkSquareRoot(boolean[] in){
        boolean[] square = Z2.mul(in,in);
        boolean[] sqrt = Z2.squareRoot(square);
        //System.out.println("square="+Z2.toPolynomial(square)+", sqrt="+Z2.toPolynomial(sqrt));
        return Z2.equal(sqrt,in);
    }

    @Test
    public void testSquareRoot() throws Exception {
        assert(checkSquareRoot(Z2.polynomialToBooleans("0")));
        assert(checkSquareRoot(Z2.polynomialToBooleans("1")));
        assert(checkSquareRoot(Z2.polynomialToBooleans("x")));
        assert(checkSquareRoot(Z2.polynomialToBooleans("x+1")));
        assert(checkSquareRoot(Z2.polynomialToBooleans("x2")));
        assert(checkSquareRoot(Z2.polynomialToBooleans("x2+1")));
        assert(checkSquareRoot(Z2.polynomialToBooleans("x2+x+1")));
        assert(checkSquareRoot(Z2.polynomialToBooleans("x3")));
        assert(checkSquareRoot(Z2.polynomialToBooleans("x3+1")));
        assert(checkSquareRoot(Z2.polynomialToBooleans("x3+x+1")));
        assert(checkSquareRoot(Z2.polynomialToBooleans("x3+x2+x+1")));
    }

    @Test
    public void testReducedRowEchelonMatrix() throws Exception {
        assert(Z2.equal(Z2.reducedRowEchelonMatrix(Z2.toBooleansArray(new int[][]{
                        {1,0,1,0,0,0,0,0,0,0},
                        {0,1,1,0,0,1,0,0,0,0},
                        {0,0,1,1,0,0,0,0,0,0},
                        {1,0,0,0,0,0,1,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,1,1,0,1,1,0,1,0,0},
                        {1,0,0,0,0,0,1,0,0,0},
                        {1,0,0,1,0,0,0,1,1,0},
                        {0,1,0,0,0,0,0,0,1,0},
                        {0,1,0,0,1,0,0,0,0,0}
                }
        )),Z2.toBooleansArray(new int[][]{
                        {1,0,0,0,0,0,1,0,0,0},
                        {0,1,0,0,0,0,0,0,1,0},
                        {0,0,1,0,0,0,1,0,0,0},
                        {0,0,0,1,0,0,1,0,0,0},
                        {0,0,0,0,1,0,0,0,1,0},
                        {0,0,0,0,0,1,1,0,1,0},
                        {0,0,0,0,0,0,0,1,1,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0}
                }
        )));

        boolean [][]in = Z2.toBooleansArray(
                "01100\n"+
                        "11000\n"+
                        "10110\n"+
                        "11010\n"+
                        "11000"
        );
        //System.out.println(Z2.toBinaryString(in)+"\n");

        boolean[][] out = Z2.reducedRowEchelonMatrix(in);
        //System.out.println(Z2.toBinaryString(in)+"\n");
        //System.out.println(Z2.toBinaryString(expected)+"\n");
        //System.out.println(Z2.toBinaryString(out)+"\n");
        assert(Z2.isRowEchelonMatrix(out));


        in = Z2.toBooleansArray(
                "10101\n"+
                        "11011\n"+
                        "01111\n"+
                        "01001\n"+
                        "10111"
        );

        out = Z2.reducedRowEchelonMatrix(in);
        //System.out.println(Z2.toBinaryString(in)+"\n");
        //System.out.println(Z2.toBinaryString(expected)+"\n");
        //System.out.println(Z2.toBinaryString(out)+"\n");
        assert(Z2.isRowEchelonMatrix(out));


        in = Z2.toBooleansArray(
                "00000\n"+
                        "01100\n"+
                        "00111\n"+
                        "11011\n"+
                        "11110"
        );
        out = Z2.reducedRowEchelonMatrix(in);
        //System.out.println(Z2.toBinaryString(in)+"\n");
        //System.out.println(Z2.toBinaryString(out)+"\n");
        assert(Z2.isRowEchelonMatrix(out));

        in = Z2.toBooleansArray(
                "01111\n"+
                        "11011\n"+
                        "10100\n"+
                        "00110\n"+
                        "00000"
        );
        out = Z2.reducedRowEchelonMatrix(in);
        //System.out.println(Z2.toBinaryString(in)+"\n");
        //System.out.println(Z2.toBinaryString(out)+"\n");
        assert(Z2.isRowEchelonMatrix(out));

    }

        @Test
    public void testMatrixKernelBasis() throws Exception {


        assert (Z2.equal(Z2.matrixKernelBasisAsColumns(Z2.toBooleansArray(new int[][]{
                        {1, 0, 1, 0, 0},
                        {0, 1, 1, 0, 0},
                        {0, 0, 0, 1, 0},
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0}
                }
        )), Z2.toBooleansArray(new int[][]{
                        {1, 0},
                        {1, 0},
                        {1, 0},
                        {0, 0},
                        {0, 1}
                }
        )));


        /*assert (Z2.equal(Z2.matrixKernelBasis(Z2.toBooleansArray(new int[][]{
                        {0, 0, 1, 0, 1},
                        {0, 0, 1, 1, 0},
                        {0, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0}
                }
        )),Z2.toBooleansArray(new int[][]{
                        {0, 0, 1, 1, 1},
                        {1, 0, 0, 0, 0}
                }
        )));*/


        assert (Z2.equal(Z2.matrixKernelBasis(Z2.toBooleansArray(new int[][]{
                        {1, 0, 1, 0, 0},
                        {0, 1, 1, 0, 0},
                        {0, 0, 0, 1, 0},
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0}
                }
        )),Z2.toBooleansArray(new int[][]{
                        {0, 0, 1, 1, 1},
                        {1, 0, 0, 0, 0}
                }
        )));

        boolean[][]dbg=Z2.matrixKernelBasis(Z2.toBooleansArray(new int[][]{
                        {1, 1, 0, 1, 1},
                        {0, 1, 1, 1, 1},
                        {0, 0, 1, 1, 0},
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0}
                }
        ));
        //System.out.println("dbg=\n"+Z2.toBinaryString(dbg));
    }


    @Test
    public void testFactorPolynomial() throws Exception {
        boolean[] a;
        boolean[][] factors;

        a = Z2.polynomialToBooleans("1");
        factors = Z2.factorPolynomial(a);
        //System.out.println(Z2.toPolynomial(a)+"=("+Z2.join(Z2.toPolynomials(factors),")*(")+")");
        assert (factors.length == 1);
        assert (Z2.equalValue(factors[0], Z2.polynomialToBooleans("1")));

        a = Z2.polynomialToBooleans("x");
        factors = Z2.factorPolynomial(a);
        //System.out.println(Z2.toPolynomial(a)+"=("+Z2.join(Z2.toPolynomials(factors),")*(")+")");
        assert (factors.length == 1);
        assert (Z2.equalValue(factors[0], Z2.polynomialToBooleans("x")));


        a = Z2.polynomialToBooleans("x2");
        factors = Z2.factorPolynomial(a);
        //System.out.println(Z2.toPolynomial(a)+"=("+Z2.join(Z2.toPolynomials(factors),")*(")+")");
        assert (factors.length == 2);
        assert (Z2.equalValue(factors[0], Z2.polynomialToBooleans("x")));
        assert (Z2.equalValue(factors[1], Z2.polynomialToBooleans("x")));

        a = Z2.polynomialToBooleans("x2+1");
        factors = Z2.factorPolynomial(a);
        //System.out.println(Z2.toPolynomial(a)+"=("+Z2.join(Z2.toPolynomials(factors),")*(")+")");
        assert (factors.length == 2);
        assert (Z2.equalValue(factors[0], Z2.polynomialToBooleans("x+1")));
        assert (Z2.equalValue(factors[1], Z2.polynomialToBooleans("x+1")));

        a = Z2.polynomialToBooleans("x3");
        factors = Z2.factorPolynomial(a);
        //System.out.println(Z2.toPolynomial(a)+"=("+Z2.join(Z2.toPolynomials(factors),")*(")+")");
        assert (factors.length == 3);
        assert (Z2.equalValue(factors[0], Z2.polynomialToBooleans("x")));
        assert (Z2.equalValue(factors[1], Z2.polynomialToBooleans("x")));
        assert (Z2.equalValue(factors[2], Z2.polynomialToBooleans("x")));

        a = Z2.polynomialToBooleans("x17+x8+x3+1");
        factors = Z2.factorPolynomial(a);
        //System.out.println(Z2.toPolynomial(a)+"=("+Z2.join(Z2.toPolynomials(factors),")*(")+")");
        assert (factors.length == 5);
        assert (Z2.equalValue(factors[0], Z2.polynomialToBooleans("x+1")));
        assert (Z2.equalValue(factors[1], Z2.polynomialToBooleans("x+1")));
        assert (Z2.equalValue(factors[2], Z2.polynomialToBooleans("x2+x+1")));
        assert (Z2.equalValue(factors[3], Z2.polynomialToBooleans("x6+x5+x3+x2+1")));
        assert (Z2.equalValue(factors[4], Z2.polynomialToBooleans("x7+x5+x3+x+1")));

        a = Z2.polynomialToBooleans("1 + x2 + x4 + x6 + x7 + x8 + x11");
        factors = Z2.factorPolynomial(a);
        System.out.println(Z2.toPolynomial(a)+"=("+Z2.join(Z2.toPolynomials(factors),")*(")+")");

    }

    @Test
    public void testFactorToSquareFreePolynomial() throws Exception {
        boolean[] a;
        boolean[][] factors;

        /*a = Z2.polynomialToBooleans("0");
        factors = Z2.factorToSquareFreePolynomial(a);
        //System.out.println(Z2.toPolynomial(a)+"=("+Z2.join(Z2.toPolynomials(factors),")*(")+")");
        assert (factors.length == 1);
        assert (Z2.equalValue(factors[0], Z2.polynomialToBooleans("0")));*/

        a = Z2.polynomialToBooleans("1");
        factors = Z2.factorToSquareFreePolynomial(a);
        //System.out.println(Z2.toPolynomial(a)+"=("+Z2.join(Z2.toPolynomials(factors),")*(")+")");
        assert (factors.length == 1);
        assert (Z2.equalValue(factors[0], Z2.polynomialToBooleans("1")));

        a = Z2.polynomialToBooleans("x");
        factors = Z2.factorToSquareFreePolynomial(a);
        //System.out.println(Z2.toPolynomial(a)+"=("+Z2.join(Z2.toPolynomials(factors),")*(")+")");
        assert (factors.length == 1);
        assert (Z2.equalValue(factors[0], Z2.polynomialToBooleans("x")));


        a = Z2.polynomialToBooleans("x2");
        factors = Z2.factorToSquareFreePolynomial(a);
        //System.out.println(Z2.toPolynomial(a)+"=("+Z2.join(Z2.toPolynomials(factors),")*(")+")");
        assert (factors.length == 2);
        assert (Z2.equalValue(factors[0], Z2.polynomialToBooleans("x")));
        assert (Z2.equalValue(factors[1], Z2.polynomialToBooleans("x")));

        a = Z2.polynomialToBooleans("x2+1");
        factors = Z2.factorToSquareFreePolynomial(a);
        //System.out.println(Z2.toPolynomial(a)+"=("+Z2.join(Z2.toPolynomials(factors),")*(")+")");
        assert (factors.length == 2);
        assert (Z2.equalValue(factors[0], Z2.polynomialToBooleans("x+1")));
        assert (Z2.equalValue(factors[1], Z2.polynomialToBooleans("x+1")));

        a = Z2.polynomialToBooleans("x3");
        factors = Z2.factorToSquareFreePolynomial(a);
        //System.out.println(Z2.toPolynomial(a)+"=("+Z2.join(Z2.toPolynomials(factors),")*(")+")");
        assert (factors.length == 3);
        assert (Z2.equalValue(factors[0], Z2.polynomialToBooleans("x")));
        assert (Z2.equalValue(factors[1], Z2.polynomialToBooleans("x")));
        assert (Z2.equalValue(factors[2], Z2.polynomialToBooleans("x")));

        a = Z2.polynomialToBooleans("x4+1");
        factors = Z2.factorToSquareFreePolynomial(a);
        //System.out.println(Z2.toPolynomial(a)+"=("+Z2.join(Z2.toPolynomials(factors),")*(")+")");
        assert (factors.length == 4);
        assert (Z2.equalValue(factors[0], Z2.polynomialToBooleans("x+1")));
        assert (Z2.equalValue(factors[1], Z2.polynomialToBooleans("x+1")));
        assert (Z2.equalValue(factors[2], Z2.polynomialToBooleans("x+1")));
        assert (Z2.equalValue(factors[3], Z2.polynomialToBooleans("x+1")));

        a = Z2.polynomialToBooleans("x17+1");
        factors = Z2.factorToSquareFreePolynomial(a);
        //System.out.println(Z2.toPolynomial(a)+"=("+Z2.join(Z2.toPolynomials(factors),")*(")+")");
        assert (factors.length == 1);
        assert (Z2.equalValue(factors[0], Z2.polynomialToBooleans("x17+1")));


        a = Z2.square(Z2.polynomialToBooleans("x17+1"));
        factors = Z2.factorToSquareFreePolynomial(a);
        //System.out.println(Z2.toPolynomial(a)+"=("+Z2.join(Z2.toPolynomials(factors),")*(")+")");
        assert (factors.length == 2);
        assert (Z2.equalValue(factors[0], Z2.polynomialToBooleans("x17+1")));
        assert (Z2.equalValue(factors[1], Z2.polynomialToBooleans("x17+1")));

        a = Z2.square(Z2.polynomialToBooleans("x17+x8+x3+1"));
        factors = Z2.factorToSquareFreePolynomial(a);
        //System.out.println(Z2.toPolynomial(a)+"=("+Z2.join(Z2.toPolynomials(factors),")*(")+")");
        assert (factors.length == 6);
        boolean[] bigF=(Z2.mul(Z2.mul(
                        Z2.polynomialToBooleans("x2+x+1"),
                        Z2.polynomialToBooleans("x6+x5+x3+x2+1")),
                Z2.polynomialToBooleans("x7+x5+x3+x+1")));
        //System.out.println(Z2.toPolynomial(bigF));
        assert (Z2.equalValue(factors[0], Z2.polynomialToBooleans("x+1")));
        assert (Z2.equalValue(factors[1], Z2.polynomialToBooleans("x+1")));
        assert (Z2.equalValue(factors[2], Z2.polynomialToBooleans("x+1")));
        assert (Z2.equalValue(factors[3], Z2.polynomialToBooleans("x+1")));
        assert (Z2.equalValue(factors[4], bigF));
        assert (Z2.equalValue(factors[5], bigF));

        a = Z2.polynomialToBooleans("x17+x5+x4+1");
        factors = Z2.factorPolynomial(a);
        assert(factors.length==8);
        assert(Z2.equalValue(factors[0],Z2.polynomialToBooleans("x+1")));
        assert(Z2.equalValue(factors[1],Z2.polynomialToBooleans("x+1")));
        assert(Z2.equalValue(factors[2],Z2.polynomialToBooleans("x+1")));
        assert(Z2.equalValue(factors[3],Z2.polynomialToBooleans("x+1")));
        assert(Z2.equalValue(factors[4],Z2.polynomialToBooleans("x+1")));
        assert(Z2.equalValue(factors[5],Z2.polynomialToBooleans("x3+x2+1")));
        assert(Z2.equalValue(factors[6],Z2.polynomialToBooleans("x4+x+1")));
        assert(Z2.equalValue(factors[7],Z2.polynomialToBooleans("x5+x3+1")));
    }

    @Test
    public void testFactorSquareFreePolynomial() throws Exception {
        boolean[] a;
        boolean[][] factors;

        a = Z2.polynomialToBooleans("x6+x5+x4+x3+1");
        factors = Z2.factorSquareFreePolynomial(a);
        assert(factors.length==2);
        assert(Z2.equalValue(factors[0],Z2.polynomialToBooleans("x2+x+1")));
        assert(Z2.equalValue(factors[1],Z2.polynomialToBooleans("x4+x+1")));



        /*
(14:14) gp > a=(x^5+x^4+1)*Mod(1,2)
%1 = Mod(1, 2)*x^5 + Mod(1, 2)*x^4 + Mod(1, 2)
(14:15) gp > factor(a)
%2 =
[Mod(1, 2)*x^2 + Mod(1, 2)*x + Mod(1, 2) 1]

[Mod(1, 2)*x^3 + Mod(1, 2)*x + Mod(1, 2) 1]
         */
        a = Z2.polynomialToBooleans("x5+x4+1");
        factors = Z2.factorSquareFreePolynomial(a);
        assert(factors.length==2);
        assert(Z2.equalValue(factors[0],Z2.polynomialToBooleans("x2+x+1")));
        assert(Z2.equalValue(factors[1],Z2.polynomialToBooleans("x3+x+1")));

/*
(23:37) gp > a=(x^5+x^3+1)*Mod(1,2)
%6 = Mod(1, 2)*x^5 + Mod(1, 2)*x^3 + Mod(1, 2)
(22:32) gp > factor(a)
%7 =
[Mod(1, 2)*x^5 + Mod(1, 2)*x^3 + Mod(1, 2) 1]
 */
        a = Z2.polynomialToBooleans("x5+x3+1");
        factors = Z2.factorSquareFreePolynomial(a);
        assert(factors.length==1);
        assert(Z2.equalValue(factors[0],Z2.polynomialToBooleans("x5+x3+1")));

/*
(22:35) gp > factor((x^5+x^1+1)*Mod(1,2))
%9 =
[  Mod(1, 2)*x^2 + Mod(1, 2)*x + Mod(1, 2) 1]

[Mod(1, 2)*x^3 + Mod(1, 2)*x^2 + Mod(1, 2) 1]
 */
        a = Z2.polynomialToBooleans("x5+x+1");
        factors = Z2.factorSquareFreePolynomial(a);
        assert(factors.length==2);
        assert(Z2.equalValue(factors[0],Z2.polynomialToBooleans("x2+x+1")));
        assert(Z2.equalValue(factors[1],Z2.polynomialToBooleans("x3+x2+1")));

/*
(22:38) gp > factor((x^6+x^5+x^4+1)*Mod(1,2))
%15 =
[                                                Mod(1, 2)*x + Mod(1, 2) 1]

[Mod(1, 2)*x^5 + Mod(1, 2)*x^3 + Mod(1, 2)*x^2 + Mod(1, 2)*x + Mod(1, 2) 1]
*/
        a = Z2.polynomialToBooleans("x6+x5+x4+1");
        factors = Z2.factorSquareFreePolynomial(a);
        assert(factors.length==2);
        assert(Z2.equalValue(factors[0],Z2.polynomialToBooleans("x+1")));
        assert(Z2.equalValue(factors[1],Z2.polynomialToBooleans("x5+x3+x2+x+1")));


/*        boolean[] hx = Z2.polynomialToBooleans("1 + x + x2 + x4 ");
        for(int i=0;i< 1<<10;i++){
            boolean[] vi=Z2.toBooleans(i);
            boolean[] gcd=Z2.gcd(hx,vi);
            if((Z2.msbIndex(gcd)>=2) && (!Z2.equalValue(gcd,hx))){
                System.out.println(Z2.toPolynomial(hx)+" factored by "+Z2.toPolynomial(gcd)+", found with basis "+Z2.toPolynomial(vi));
            }
        }
*/
/*
        (22:40) gp > factor((x^10+x^5+x^4+1)*Mod(1,2))
%17 =
[                                                Mod(1, 2)*x + Mod(1, 2) 1]

[                              Mod(1, 2)*x^3 + Mod(1, 2)*x^2 + Mod(1, 2) 1]

[Mod(1, 2)*x^6 + Mod(1, 2)*x^4 + Mod(1, 2)*x^3 + Mod(1, 2)*x + Mod(1, 2) 1]
*/
        a = Z2.polynomialToBooleans("x10+x5+x4+1");
/*        System.out.println("a="+Z2.toPolynomial(a));
        for(int i=0;i< 2*10;i=i+2){
            boolean[] xi=Z2.toBooleans(1<<i);
            boolean[] xiModa=Z2.mod(xi,a);
            System.out.println("x"+i+"="+Z2.toPolynomial(xi)+" mod a --> "+Z2.toPolynomial(xiModa));
        }
*/
        factors = Z2.factorSquareFreePolynomial(a);
        assert(factors.length==3);
        assert(Z2.equalValue(factors[0],Z2.polynomialToBooleans("x+1")));
        assert(Z2.equalValue(factors[1],Z2.polynomialToBooleans("x3+x2+1")));
        assert(Z2.equalValue(factors[2],Z2.polynomialToBooleans("x6+x4+x3+x+1")));

/*
(22:38) gp > factor((x^17+x^5+x^4+1)*Mod(1,2))
%16 =
[                  Mod(1, 2)*x + Mod(1, 2) 5]

[Mod(1, 2)*x^3 + Mod(1, 2)*x^2 + Mod(1, 2) 1]

[  Mod(1, 2)*x^4 + Mod(1, 2)*x + Mod(1, 2) 1]

[Mod(1, 2)*x^5 + Mod(1, 2)*x^3 + Mod(1, 2) 1]
 */

    }

    @Test
    public void testTranspose() throws Exception {
        assert (Z2.equal(Z2.transpose(Z2.toBooleansArray(
                        "01100\n" +
                        "11000\n" +
                        "10110\n" +
                        "11010\n" +
                        "11000"
        )),Z2.toBooleansArray(
                        "01111\n" +
                        "11011\n" +
                        "10100\n" +
                        "00110\n" +
                        "00000"
        )));
        assert (Z2.equal(Z2.transpose(Z2.toBooleansArray(new int[][]{
                        {1,0,0,0},
                        {1,1,0,0},
                        {0,1,1,0},
                        {1,0,0,1}
                }
        )),Z2.toBooleansArray(new int[][]{
                        {1,1,0,1},
                        {0,1,1,0},
                        {0,0,1,0},
                        {0,0,0,1}
        })));
    }
    @Test
    public void testIsRowEchellonMatrix() throws Exception {
        //non row echelon form matrix
        assert(!Z2.isRowEchelonMatrix(Z2.toBooleansArray(
                "01100\n"+
                "11000\n"+
                "10110\n"+
                "11010\n"+
                "11000"
        )));
        assert(!Z2.isRowEchelonMatrix(Z2.toBooleansArray(
                "10101\n"+
                "11011\n"+
                "01111\n"+
                "01001\n"+
                "10111"
        )));

        //row echelon form matrix
        assert(Z2.isRowEchelonMatrix(Z2.toBooleansArray(
                        "10100\n"+
                        "01100\n"+
                        "00010\n"+
                        "00000\n"+
                        "00000"
        )));
        assert(Z2.isRowEchelonMatrix(Z2.toBooleansArray(
                "10101\n"+
                "01110\n"+
                "00111\n"+
                "00010\n"+
                "00001"
        )));
    }
    @Test
    public void testIsColumnEchellonMatrix() throws Exception {
        //non column echelon form matrix
        assert(!Z2.isRowEchelonMatrix(Z2.toBooleansArray(new int[][]{
                        {1,0,0,0},
                        {1,1,0,0},
                        {0,1,0,0},
                        {1,0,0,1}
                }
        )));

        //column echelon form matrix
        assert(Z2.isColumnEchelonMatrix(Z2.toBooleansArray(new int[][]{
                        {1,0,0,0},
                        {1,1,0,0},
                        {0,1,1,0},
                        {1,0,0,1}
                }
        )));
    }
    @Test
    public void testRowAugmentIdentity() throws Exception {
        assert(Z2.equal(Z2.rowAugmentIdentity(Z2.toBooleansArray(new int[][]{
                        {1,0,0,0},
                        {1,1,0,0},
                        {0,1,0,0},
                        {1,0,0,1}
                }
        )),Z2.toBooleansArray(new int[][]{
                        {1,0,0,0},
                        {1,1,0,0},
                        {0,1,0,0},
                        {1,0,0,1},
                        {1,0,0,0},
                        {0,1,0,0},
                        {0,0,1,0},
                        {0,0,0,1}
                }
        )));
    }

    @Test
    public void testBitWidth() throws Exception {
        assert(Z2.bitWidth(1)==1);
        assert(Z2.bitWidth(2)==2);
        assert(Z2.bitWidth(3)==2);
        assert(Z2.bitWidth(4)==3);
        assert(Z2.bitWidth(5)==3);
        assert(Z2.bitWidth(6)==3);
        assert(Z2.bitWidth(7)==3);
        assert(Z2.bitWidth(8)==4);
        assert(Z2.bitWidth(31)==5);
        assert(Z2.bitWidth(32)==6);
        assert(Z2.bitWidth(0)==1);
        assert(Z2.bitWidth(Integer.MAX_VALUE)==31);
        assert(Z2.bitWidth(Integer.MIN_VALUE)==32);
        assert(Z2.bitWidth(-1)==32);
    }

    @Test
    public void testToBooleans() throws Exception {
        assert(Z2.equal(Z2.toBooleans(0x00000000),new boolean[]{false}));
        assert(Z2.equal(Z2.toBooleans(0x00000001),new boolean[]{true}));
        boolean[] expected = new boolean[32];
        expected[31]=true;
        assert(Z2.equal(Z2.toBooleans(0x80000000),expected));
        for(int i=0;i<31;i++) expected[i]=true;
        assert(Z2.equal(Z2.toBooleans(0xFFFFFFFF),expected));
    }
    @Test
    public void testHexBytesToBooleans() throws Exception {
        assert(Z2.equal(Z2.hexBytesToBooleans("00"),new boolean[]{false}));
        assert(Z2.equal(Z2.hexBytesToBooleans("01"),new boolean[]{true}));
        boolean[] expected = new boolean[32];
        expected[31]=true;
        assert(Z2.equal(Z2.hexBytesToBooleans("00 00 00 80"),expected));
        for(int i=0;i<31;i++) expected[i]=true;
        assert(Z2.equal(Z2.hexBytesToBooleans("FFFFFFFF"),expected));
    }

    @Test
    public void testAdd() throws Exception {
        boolean[] a = Z2.polynomialToBooleans("x2+x");
        boolean[] b = Z2.polynomialToBooleans("x3+x+1");
        boolean[] sum = Z2.polynomialToBooleans("x3+x2+1");
        assert(Z2.equal(sum,Z2.add(a,b)));
        a = Z2.polynomialToBooleans("x+1");
        b = Z2.X;
        sum=Z2.polynomialToBooleans("1");
        assert(Z2.equal(sum,Z2.add(a,b)));
    }

    @Test
    public void testMul() throws Exception {
        boolean[] a = Z2.polynomialToBooleans("x2+x");
        boolean[] b = Z2.polynomialToBooleans("x3+x+1");
        boolean[] product = Z2.polynomialToBooleans("x5+x4+x3+x");
        assert(Z2.equal(product,Z2.mul(a,b)));
    }

    @Test
    public void testHammingWeight() throws Exception {
        assert(0==Z2.hammingWeight(Z2.ZERO));
        assert(1==Z2.hammingWeight(Z2.ONE));
        assert(1==Z2.hammingWeight(Z2.TWO));
        assert(4==Z2.hammingWeight(Z2.polynomialToBooleans("x5+x4+x3+x")));
        assert(5==Z2.hammingWeight(Z2.polynomialToBooleans("x5+x4+x3+x+x234")));
    }

    @Test
    public void testMinHammingWeight() throws Exception {
        boolean [][]test = new boolean[5][];
        test[0] = Z2.ZERO;
        test[1] = Z2.polynomialToBooleans("x4+x3+x");
        test[2] = Z2.polynomialToBooleans("x5+x4+x2");
        test[3] = Z2.polynomialToBooleans("x5+x3+1");
        test[4] = Z2.polynomialToBooleans("x654+x4+1");
        //System.out.println(Z2.minHammingDistance(test));
        assert(3==Z2.minHammingDistance(test));
        test[0] = Z2.ONE;
        //System.out.println(Z2.minHammingDistance(test));
        assert(2==Z2.minHammingDistance(test));
        test[0] = Z2.polynomialToBooleans("x65+x41+x23+x2");
        //System.out.println(Z2.minHammingDistance(test));
        assert(4==Z2.minHammingDistance(test));
    }

    @Test
    public void testBinaryStringFileToBooleansArray() throws Exception {
        int nTests = 100;
        int maxLen = 50;
        int minWidth = 0;
        int maxWidth = 100;
        Random rng = new Random();
        for(int i=0;i<nTests;i++){
            int len = rng.nextInt(maxLen);
            boolean[][] dat = Z2.randomBooleansArray(len,minWidth,maxWidth);
            File tmp = File.createTempFile("Z2Test","testBinaryStringFileToBooleansArray");
            Z2.toBinaryStringFile(tmp,dat);
            boolean[][] check = Z2.binaryStringFileToBooleansArray(tmp);
            assert(check.length==dat.length);
            for(int j=0;j<dat.length;j++) {
                assert(Z2.equal(dat[j],check[j]));
            }
        }
    }

    @Test
    public void testBinaryFileToBooleans() throws Exception {
        int nTests = 100;
        int maxLen = 50;
        Random rng = new Random();
        for(int i=0;i<nTests;i++){
            int len = rng.nextInt(maxLen);
            boolean[] dat = Z2.randomBooleans(len);
            File tmp = File.createTempFile("Z2Test","testBinaryStringFileToBooleansArray");
            Z2.toBinaryFile(tmp,dat);
            boolean[] check = Z2.binaryFileToBooleans(tmp);
            //System.out.println(Z2.toBinaryString(dat));
            //System.out.println(Z2.toBinaryString(check));
            //System.out.println();
            assert(Z2.equalValue(dat,check));
            if(len>=24) {
                assert (Z2.equal(Z2.cloneRange(dat, 0,8),Z2.binaryFileToBooleans(tmp,0,1)));
                assert (Z2.equal(Z2.cloneRange(dat, 8,8),Z2.binaryFileToBooleans(tmp,1,1)));
                assert (Z2.equal(Z2.cloneRange(dat, 8,16),Z2.binaryFileToBooleans(tmp,1,2)));
            }
        }
    }

        @Test
    public void testIsGreater() throws Exception {
        boolean[] a = Z2.polynomialToBooleans("x2+x");
        boolean[] b = Z2.polynomialToBooleans("x3+x+1");
        assert(!Z2.isGreater(a,a));
        assert(!Z2.isGreater(a,b));
        assert(Z2.isGreater(b,a));
    }
    @Test
    public void testIsGreaterOrEqual() throws Exception {
        boolean[] a = Z2.polynomialToBooleans("x2+x");
        boolean[] b = Z2.polynomialToBooleans("x3+x+1");
        assert(Z2.isGreaterOrEqual(a,a));
        assert(!Z2.isGreater(a,b));
        assert(Z2.isGreater(b,a));
    }
    @Test
    public void testDiv() throws Exception {
        boolean[] a = Z2.polynomialToBooleans("x2+x");
        boolean[] b = Z2.polynomialToBooleans("x3+x+1");
        boolean[] product = Z2.polynomialToBooleans("x5+x4+x3+x");
        assert(Z2.equal(b,Z2.div(product,a)));
        assert(Z2.equal(a,Z2.div(product,b)));
        boolean[] pa = Z2.add(a,product);
        assert(Z2.equal(a,Z2.div(pa,b)));
    }
    @Test
    public void testMod() throws Exception {
        boolean[] a = Z2.polynomialToBooleans("x2+x");
        boolean[] b = Z2.polynomialToBooleans("x3+x+1");
        boolean[] product = Z2.polynomialToBooleans("x5+x4+x3+x");
        assert(Z2.equal(Z2.ZERO,Z2.mod(product,a)));
        assert(Z2.equal(Z2.ZERO,Z2.mod(product,b)));
        boolean[] pa = Z2.add(a,product);
        assert(Z2.equal(a,Z2.mod(pa,b)));
        a = Z2.polynomialToBooleans("x12");
        b = Z2.polynomialToBooleans("x12+x3+1");
        assert(Z2.equal(Z2.mod(a,b),Z2.polynomialToBooleans("x3+1")));
        a = Z2.polynomialToBooleans("x12+x3+1");
        b = Z2.polynomialToBooleans("x2+x");
        assert(Z2.equal(Z2.mod(a, b), Z2.ONE));
        a = Z2.polynomialToBooleans("x13");
        b = Z2.polynomialToBooleans("x12+x3+1");
        assert(Z2.equal(Z2.mod(a, b),Z2.polynomialToBooleans("x4+x")));
        a = Z2.polynomialToBooleans("x315");
        b = Z2.polynomialToBooleans("x12+x3+1");
        assert(Z2.equal(Z2.mod(a,b),Z2.ONE));
        a = Z2.polynomialToBooleans("x12+x3+1");
        b = Z2.polynomialToBooleans("1");
        assert(Z2.equal(Z2.mod(a,b),Z2.ZERO));

    }

    @Test
    public void testGcd() throws Exception {
        boolean[] gx = Z2.polynomialToBooleans("x10+x9+x8+x6+x5+x4+1");
        boolean[] hx = Z2.polynomialToBooleans("x9+x6+x5+x3+x2+1");
        assert(Z2.equal(Z2.gcd(gx,hx),Z2.polynomialToBooleans("x3+x+1")));
        assert(Z2.equal(Z2.gcd(gx,hx),Z2.gcd(hx,gx)));
        gx = Z2.polynomialToBooleans("x12+x3+1");
        hx = Z2.polynomialToBooleans("x2+x");
        assert(Z2.equal(Z2.gcd(gx,hx),Z2.ONE));
        gx = Z2.polynomialToBooleans("x2");
        assert(Z2.equal(Z2.gcd(gx,hx),Z2.X));
        gx = Z2.polynomialToBooleans("x3+x+1");
        hx = Z2.polynomialToBooleans("x2");
        assert(Z2.equal(Z2.gcd(gx,hx),Z2.ONE));

    }

    @Test
    public void testOrderOfX() throws Exception{
        assert(Z2.orderOfX(Z2.polynomialToBooleans("0")).equals(BigInteger.ZERO));
        assert(Z2.orderOfX(Z2.polynomialToBooleans("1")).equals(BigInteger.ZERO));
        assert(Z2.orderOfX(Z2.polynomialToBooleans("x")).equals(BigInteger.ONE));
        assert(Z2.orderOfX(Z2.polynomialToBooleans("x+1")).equals(BigInteger.ONE));
        assert(Z2.orderOfX(Z2.polynomialToBooleans("x8+x4+x3+x+1")).equals(BigInteger.valueOf(51)));//irreducible
        assert(Z2.orderOfX(Z2.polynomialToBooleans("1+x2+x5")).equals(BigInteger.valueOf(31)));//primitive
        assert(Z2.orderOfX(Z2.polynomialToBooleans("1+x+x6")).equals(BigInteger.valueOf(63)));//primitive

        assert(Z2.orderOfX(Z2.polynomialToBooleans("1+x+x2")).equals(BigInteger.valueOf(3)));//primitive
        assert(Z2.orderOfX(Z2.polynomialToBooleans("1+x+x3")).equals(BigInteger.valueOf(7)));//primitive

        boolean[] px = Z2.polynomialToBooleans("1 + x2 + x4 + x5 + x6 + x7 + x8 + x9 + x11");
        System.out.println("px="+Z2.toPolynomial(px));

        for(int i=1;i< 1<<px.length;i++){
            boolean[] test = Z2.modExp(Z2.X,i,px);
            if(Z2.isOne(test))
                System.out.println("i="+i+", x^i mod px=1");
        }

        assert(Z2.orderOfX(Z2.polynomialToBooleans("1+x3")).equals(BigInteger.valueOf(3)));//(1+x)(1+x+x2)
        assert(Z2.orderOfX(Z2.polynomialToBooleans("1+x+x2+x4")).equals(BigInteger.valueOf(7)));//(1+x)(1+x+x3)
        assert(Z2.orderOfX(Z2.polynomialToBooleans("1+x4+x5")).equals(BigInteger.valueOf(21)));//(1+x+x2)(1+x+x3)
        assert(Z2.orderOfX(Z2.polynomialToBooleans("1+x+x4+x6")).equals(BigInteger.valueOf(21)));//(1+x)(1+x+x2)(1+x+x3)

        assert(Z2.orderOfX(Z2.polynomialToBooleans("1 + x2 + x4 + x5 + x6 + x7 + x8 + x9 + x11")).equals(BigInteger.valueOf(357)));//(1+x+x3)(x8+x4+x3+x+1) -->primitive * irreducible


        assert(Z2.orderOfX(Z2.polynomialToBooleans("1 + x + x2 + x5")).equals(BigInteger.valueOf(7)));//(1+x)^2*(1+x+x3)
        assert(Z2.orderOfX(Z2.polynomialToBooleans("1 + x + x2 + x3 + x6 + x7")).equals(BigInteger.valueOf(7)));//(1+x)(1+x+x3)^2
        assert(Z2.orderOfX(Z2.polynomialToBooleans("1 + x + x2 + x3 + x4 + x5 + x6 + x8 + x9 + x11")).equals(BigInteger.valueOf(3*7*31)));//(1+x)(1+x+x2)(1+x+x3)(1+x2+x5)
        assert(Z2.orderOfX(Z2.polynomialToBooleans("1 + x2 + x4 + x5 + x10 + x12")).equals(BigInteger.valueOf(63)));//(1+x)(1+x+x2)(1+x+x3)(1+x+x6)
        assert(Z2.orderOfX(Z2.polynomialToBooleans("1 + x + x4 + x10 + x11")).equals(BigInteger.valueOf(63)));//(1+x+x2)(1+x+x3)(1+x+x6)
        assert(Z2.orderOfX(Z2.polynomialToBooleans("1 + x2 + x3 + x5 + x7 + x10 + x12 + x13 + x14")).equals(BigInteger.valueOf(126)));//(1+x+x2)(1+x+x3)^2(1+x+x6)


    }

    @Test
    public void testIsIrreducible() throws Exception {
        //reducible polynomials (since there is an even number of non zero terms, x+1 is a divisor):
        assert(!Z2.isIrreducible(Z2.polynomialToBooleans("1+x2")));
        assert(!Z2.isIrreducible(Z2.polynomialToBooleans("1+x2+x3+x7")));
        assert(!Z2.isIrreducible(Z2.polynomialToBooleans("1+x2+x4+x9")));
        assert(!Z2.isIrreducible(Z2.polynomialToBooleans("1 + x3 + x6 + x7 + x10 + x12 + x14 + x15 + x17 + x18 + x19 + x20 + x21 + x23 + x25 + x26 + x28 + x29 + x30 + x31 + x32")));
        //assert(!Z2.isIrreducible(Z2.toBooleans("1001001100101011011111010110111110000")));

        //irreducible polynomials:
        assert(Z2.isIrreducible(Z2.polynomialToBooleans("1+x")));
        assert(Z2.isIrreducible(Z2.polynomialToBooleans("1+x+x2")));
        assert(Z2.isIrreducible(Z2.polynomialToBooleans("1+x+x3")));
        assert(Z2.isIrreducible(Z2.polynomialToBooleans("1+x+x4")));
        assert(Z2.isIrreducible(Z2.polynomialToBooleans("1+x2+x5")));
        assert(Z2.isIrreducible(Z2.polynomialToBooleans("1+x+x6")));
        assert(Z2.isIrreducible(Z2.polynomialToBooleans("1+x3+x12")));
        assert(Z2.isIrreducible(Z2.polynomialToBooleans("x13+x12+x11+x8+1")));
        assert(Z2.isIrreducible(Z2.polynomialToBooleans("x16+x14+x13+x11+1")));
        assert(Z2.isIrreducible(Z2.polynomialToBooleans("x19+x18+x17+x14+1")));
    }

    @Test
    public void testIsPrimitive() throws Exception {
        //reducible polynomials (since there is an even number of non zero terms, x+1 is a divisor):
        assert(!Z2.isPrimitive(Z2.polynomialToBooleans("1+x2")));
        assert(!Z2.isPrimitive(Z2.polynomialToBooleans("1+x2+x3+x7")));
        assert(!Z2.isPrimitive(Z2.polynomialToBooleans("1+x2+x4+x9")));

        //irreducible but not primitive
        assert(!Z2.isPrimitive(Z2.polynomialToBooleans("1+x3+x12")));

        //primitive polynomials:
        assert(Z2.isPrimitive(Z2.polynomialToBooleans("1+x")));
        assert(Z2.isPrimitive(Z2.polynomialToBooleans("1+x+x2")));
        assert(Z2.isPrimitive(Z2.polynomialToBooleans("1+x+x3")));
        assert(Z2.isPrimitive(Z2.polynomialToBooleans("1+x+x4")));
        assert(Z2.isPrimitive(Z2.polynomialToBooleans("1+x2+x5")));
        assert(Z2.isPrimitive(Z2.polynomialToBooleans("1+x+x6")));
        assert(Z2.isPrimitive(Z2.polynomialToBooleans("1+x+x7")));
        assert(Z2.isPrimitive(Z2.polynomialToBooleans("1+x+x5+x6+x8")));
        assert(Z2.isPrimitive(Z2.polynomialToBooleans("1+x4+x9")));
        assert(Z2.isPrimitive(Z2.polynomialToBooleans("1+x3+x4+x7+x12")));
        assert(Z2.isPrimitive(Z2.polynomialToBooleans("1+x42+x47")));
    }
}