package uk.co.nimp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BooleanFunctionTest {
    static Random rng=new Random();

    static final boolean slowTests=false;
    static void assertTrue(String message, boolean predicate){
        assertEquals(message,true,predicate);
    }
    static void assertFalse(String message, boolean predicate){
        assertEquals(message,false,predicate);
    }
    static void assertEquals(String message,boolean expected, boolean actual){
        int e=Z2.toInt(expected);
        int a=Z2.toInt(actual);
        Assert.assertEquals(message, e, a);
    }
    @Test
    public void testPowerOf2() throws Exception {
        Set<Integer> powersOf2 = new HashSet<Integer>();
        int t=1;
        for(int i=1;i<31;i++) {
            t=t*2;
            powersOf2.add(t);
        }
        if(slowTests) {
            for (int i = 0; i > -1; i++) {//exhaustive test, need 2 or 3 minutes
                assertEquals("mismatch for " + i + ": ", powersOf2.contains(i), BooleanFunction.isPowerOf2(i));
            }
        }else{
            Random rng = new Random();
            for (int i = 0; i<100000; i++) {
                int r=rng.nextInt();
                if(r<0) r=~r;
                assertEquals("mismatch for " + r + ": ", powersOf2.contains(r), BooleanFunction.isPowerOf2(r));
            }
        }
    }

    static boolean[] ti2VarToBooleans(int ti2Var){
        boolean[] out = new boolean[2];
        out[0]=1==(ti2Var&1);
        out[1]=1==((ti2Var>>1)&1);
        return out;
    }
    static int ti2VarToInt(boolean[] ti2Var){
        int out=0;
        if(ti2Var[0]) out=1;
        if(ti2Var[1]) out|=2;
        return out;
    }

    static boolean ti2VarToBoolean(int ti2Var){
        switch(ti2Var){
            case 0:
            case 3:return false;
            default:return true;
        }
    }

    static boolean[] ti3VarToBooleans(int ti3Var){
        boolean[] out = new boolean[3];
        out[0]=1==(ti3Var&1);
        out[1]=1==((ti3Var>>1)&1);
        out[2]=1==((ti3Var>>2)&1);
        return out;
    }
    static int ti3VarToInt(boolean[] ti3Var){
        int out=0;
        if(ti3Var[0]) out=1;
        if(ti3Var[1]) out|=2;
        if(ti3Var[2]) out|=4;
        return out;
    }

    static boolean ti3VarToBoolean(int ti3Var){
        switch(ti3Var){
            case 0:
            case 3:
            case 5:
            case 6: return false;
            default:return true;
        }
    }

    static boolean[] ti4VarToBooleans(int ti4Var){
        boolean[] out = new boolean[4];
        out[0]=1==(ti4Var&1);
        out[1]=1==((ti4Var>>1)&1);
        out[2]=1==((ti4Var>>2)&1);
        out[3]=1==((ti4Var>>3)&1);
        return out;
    }
    static int ti4VarToInt(boolean[] ti4Var){
        int out=0;
        if(ti4Var[0]) out=1;
        if(ti4Var[1]) out|=2;
        if(ti4Var[2]) out|=4;
        if(ti4Var[3]) out|=8;
        return out;
    }

    static boolean ti4VarToBoolean(int ti4Var){
        switch(ti4Var){
            case 0:
            case 3:
            case 5:
            case 6:
            case 9:
            case 0xA:
            case 0xC:
            case 0xF: return false;
            default:return true;
        }
    }

    static int ti2And(int aTi2, int bTi2){
        boolean remask=false;
        return ti2And(aTi2,bTi2,remask);
    }
    static int ti2And(int aTi2, int bTi2, boolean remask){
        boolean[] a=ti2VarToBooleans(aTi2);
        boolean[] b=ti2VarToBooleans(bTi2);
        boolean[] out=new boolean[2];
        boolean a0b0=!(a[0]&b[0]);
        boolean a0b1=!(a[0]&b[1]);
        boolean a1b0=!(a[1]&b[0]);
        boolean a1b1=!(a[1]&b[1]);
        if(remask){
            boolean r1=rng.nextBoolean();
            boolean r2=rng.nextBoolean();
            a0b0^=r1;
            a1b1^=r2;
            a1b0^=r1;
            a0b1^=r2;
        }
        out[0] = a1b1 ^ a0b0;
        out[1] = a1b0 ^ a0b1;

        return ti2VarToInt(out);
    }

    static int ti3And(int aTi3, int bTi3){
        boolean remask=false;
        return ti3And(aTi3,bTi3,remask);
    }
    static int ti3And(int aTi3, int bTi3, boolean remask){
        boolean[] a=ti3VarToBooleans(aTi3);
        boolean[] b=ti3VarToBooleans(bTi3);
        boolean[] out=new boolean[3];
        boolean a0b0=!(a[0]&b[0]);
        boolean a0b1=!(a[0]&b[1]);
        boolean a0b2=!(a[0]&b[2]);
        boolean a1b0=!(a[1]&b[0]);
        boolean a1b1=!(a[1]&b[1]);
        boolean a1b2=!(a[1]&b[2]);
        boolean a2b0=!(a[2]&b[0]);
        boolean a2b1=!(a[2]&b[1]);
        boolean a2b2=!(a[2]&b[2]);
        out[0] = a1b1 ^ a1b2 ^ a2b1 ^ true;
        out[1] = a2b2 ^ a0b2 ^ a2b0 ^ true;
        out[2] = a0b0 ^ a0b1 ^ a1b0 ^ true;
        if(remask){
            boolean r1=rng.nextBoolean();
            boolean r2=rng.nextBoolean();
            out[0]^=r1;
            out[1]^=r2;
            out[2]^=r1^r2;
        }
        return ti3VarToInt(out);
    }

    /*4 share AND:
    z1 = (x3 ⊕ x4)(y2 ⊕ y3) ⊕ y2 ⊕ y3 ⊕ y4 ⊕ x2 ⊕ x3 ⊕ x4
    z2 = (x1 ⊕ x3)(y1 ⊕ y4) ⊕ y1 ⊕ y3 ⊕ y4 ⊕ x1 ⊕ x3 ⊕ x4
    z3 = (x2 ⊕ x4)(y1 ⊕ y4) ⊕ y2 ⊕ x2
    z4 = (x1 ⊕ x2)(y2 ⊕ y3) ⊕ y1 ⊕ x1
    */
    static int ti4And(int aTi4, int bTi4){
        boolean[] a=ti4VarToBooleans(aTi4);
        boolean[] b=ti4VarToBooleans(bTi4);
        boolean[] out=new boolean[4];
        boolean x1 = a[0];
        boolean x2 = a[1];
        boolean x3 = a[2];
        boolean x4 = a[3];
        boolean y1 = b[0];
        boolean y2 = b[1];
        boolean y3 = b[2];
        boolean y4 = b[3];
        out[0] = ((x3 ^ x4)&(y2 ^ y3)) ^ y2 ^ y3 ^ y4 ^ x2 ^ x3 ^ x4;
        out[1] = ((x1 ^ x3)&(y1 ^ y4)) ^ y1 ^ y3 ^ y4 ^ x1 ^ x3 ^ x4;
        out[2] = ((x2 ^ x4)&(y1 ^ y4)) ^ y2 ^ x2;
        out[3] = ((x1 ^ x2)&(y2 ^ y3)) ^ y1 ^ x1;
        return ti4VarToInt(out);
    }

    @Test
    public void testTi2And() throws Exception {
        for(int a=0;a<4;a++) {
            for (int b = 0; b < 4; b++) {
                int actualInt = ti2And(a, b);
                int actualInt2 = ti2And(a, b, true);
                boolean actual = ti2VarToBoolean(actualInt);
                boolean actual2 = ti2VarToBoolean(actualInt2);
                boolean expected = ti2VarToBoolean(a)&ti2VarToBoolean(b);
                assert(actual==expected);
                assert(actual2==expected);
            }
        }
    }

    @Test
    public void testTi3And() throws Exception {
        for(int a=0;a<8;a++) {
            for (int b = 0; b < 8; b++) {
                int actualInt = ti3And(a, b);
                boolean actual = ti3VarToBoolean(actualInt);
                boolean expected = ti3VarToBoolean(a)&ti3VarToBoolean(b);
                assert(actual==expected);
            }
        }
    }

    void testTi2AndUniform(boolean testSecondInput, boolean remask) {
        String inputLabel = testSecondInput ? "b" : "a";
        int[][] outputValuesCountForA = new int[4][4];
        for (int k = 0; k < 10000; k++) {
            for (int a = 0; a < 4; a++) {
                for (int b = 0; b < 4; b++) {
                    int actualInt = testSecondInput ? ti2And(b, a,remask) : ti2And(a, b,remask);
                    outputValuesCountForA[a][actualInt]++;
                }
            }
        }

        System.out.print("      ");
        for (int a = 0; a < 4; a++) {
            System.out.print(String.format("%7d ", a));
        }
        System.out.println();
        for (int a = 0; a < 4; a++) {
            int sum = 0;
            System.out.print(String.format("%s=%2d: ",inputLabel, a));
            for (int b = 0; b < 4; b++) {
                System.out.print(String.format("%7d ", outputValuesCountForA[a][b]));
                sum += outputValuesCountForA[a][b];
            }
            System.out.println(" = " + sum);
        }
        System.out.print("      ");
        int minOnes = Integer.MAX_VALUE;
        int maxOnes = 0;
        int minZeroes = Integer.MAX_VALUE;
        int maxZeroes = 0;
        for (int a = 0; a < 4; a++) {
            int sum = 0;
            for (int b = 0; b < 4; b++) {
                sum += outputValuesCountForA[b][a];
            }
            if (ti2VarToBoolean(a)) {
                minOnes = Math.min(minOnes, sum);
                maxOnes = Math.max(maxOnes, sum);
            } else {
                minZeroes = Math.min(minZeroes, sum);
                maxZeroes = Math.max(maxZeroes, sum);
            }
            System.out.print(String.format("%7d ", sum));
        }
        System.out.print("\n -> ones = [" + minOnes + "," + maxOnes + "], zeroes = [" + minZeroes + "," + maxZeroes + "]");
        if ((minOnes == maxOnes) && (minZeroes == maxZeroes)) System.out.println(" -> Uniform :-)");
        else {
            double tolerance = 0.03;
            double onesRatio = (maxOnes * 1.0) / minOnes;
            double zeroesRatio = (maxZeroes * 1.0) / minZeroes;
            boolean onesPass = (onesRatio >= 1.0 - tolerance) && (onesRatio <= 1.0 + tolerance);
            boolean zeroesPass = (zeroesRatio >= 1.0 - tolerance) && (zeroesRatio <= 1.0 + tolerance);
            if (onesPass && zeroesPass) System.out.println(" -> quasi uniform!");
            else System.out.println(" -> NOT uniform :-(");
            System.out.println(" -> onesRatio=" + onesRatio);
            System.out.println(" -> zeroesRatio=" + zeroesRatio);
        }
        System.out.println();
    }


    @Test
    public void testTi2AndUniform() throws Exception {
        testTi2AndUniform(false,false);
        testTi2AndUniform(false,true);
        testTi2AndUniform(true,true);

    }

    void testTi3AndUniform(boolean testSecondInput, boolean remask) {
        String inputLabel = testSecondInput ? "b" : "a";
        int[][] outputValuesCountForA = new int[8][8];
        for (int k = 0; k < 10000; k++) {
            for (int a = 0; a < 8; a++) {
                for (int b = 0; b < 8; b++) {
                    int actualInt = testSecondInput ? ti3And(b, a,remask) : ti3And(a, b,remask);
                    outputValuesCountForA[a][actualInt]++;
                }
            }
        }

        System.out.print("      ");
        for (int a = 0; a < 8; a++) {
            System.out.print(String.format("%7d ", a));
        }
        System.out.println();
        for (int a = 0; a < 8; a++) {
            int sum = 0;
            System.out.print(String.format("%s=%2d: ",inputLabel, a));
            for (int b = 0; b < 8; b++) {
                System.out.print(String.format("%7d ", outputValuesCountForA[a][b]));
                sum += outputValuesCountForA[a][b];
            }
            System.out.println(" = " + sum);
        }
        System.out.print("      ");
        int minOnes = Integer.MAX_VALUE;
        int maxOnes = 0;
        int minZeroes = Integer.MAX_VALUE;
        int maxZeroes = 0;
        for (int a = 0; a < 8; a++) {
            int sum = 0;
            for (int b = 0; b < 8; b++) {
                sum += outputValuesCountForA[b][a];
            }
            if (ti3VarToBoolean(a)) {
                minOnes = Math.min(minOnes, sum);
                maxOnes = Math.max(maxOnes, sum);
            } else {
                minZeroes = Math.min(minZeroes, sum);
                maxZeroes = Math.max(maxZeroes, sum);
            }
            System.out.print(String.format("%7d ", sum));
        }
        System.out.print("\n -> ones = [" + minOnes + "," + maxOnes + "], zeroes = [" + minZeroes + "," + maxZeroes + "]");
        if ((minOnes == maxOnes) && (minZeroes == maxZeroes)) System.out.println(" -> Uniform :-)");
        else {
            double tolerance = 0.03;
            double onesRatio = (maxOnes * 1.0) / minOnes;
            double zeroesRatio = (maxZeroes * 1.0) / minZeroes;
            boolean onesPass = (onesRatio >= 1.0 - tolerance) && (onesRatio <= 1.0 + tolerance);
            boolean zeroesPass = (zeroesRatio >= 1.0 - tolerance) && (zeroesRatio <= 1.0 + tolerance);
            if (onesPass && zeroesPass) System.out.println(" -> quasi uniform!");
            else System.out.println(" -> NOT uniform :-(");
            System.out.println(" -> onesRatio=" + onesRatio);
            System.out.println(" -> zeroesRatio=" + zeroesRatio);
        }
        System.out.println();
    }

    @Test
    public void testTi3AndUniform() throws Exception {
        testTi3AndUniform(false,false);
        testTi3AndUniform(false,true);
        testTi3AndUniform(true,true);

    }

    @Test
    public void testTi4And() throws Exception {
        for(int a=0;a<16;a++) {
            for (int b = 0; b < 16; b++) {
                int actualInt = ti4And(a,b);
                boolean actual = ti4VarToBoolean(actualInt);
                boolean expected = ti4VarToBoolean(a)&ti4VarToBoolean(b);
                assert(actual==expected);
            }
        }
    }

    void testTi4AndUniformForInput(boolean testSecondInput){
        String inputLabel = testSecondInput ? "b" : "a";
        int [][]outputValuesCountForA=new int[16][16];
        for(int a=0;a<16;a++) {
            for (int b = 0; b < 16; b++) {
                int actualInt = testSecondInput ? ti4And(b,a) : ti4And(a,b);
                outputValuesCountForA[a][actualInt]++;
            }
        }
        System.out.print("     ");
        for(int a=0;a<16;a++) {
            System.out.print(String.format("%2d ",a));
        }
        System.out.println();
        for(int a=0;a<16;a++) {
            int sum=0;
            System.out.print(String.format("%s=%2d: ",inputLabel,a));
            for (int b = 0; b < 16; b++) {
                System.out.print(outputValuesCountForA[a][b] + "  ");
                sum+=outputValuesCountForA[a][b];
            }
            System.out.println(" = "+sum);
        }
        System.out.print("     ");
        int minOnes=32;
        int maxOnes=0;
        int minZeroes=32;
        int maxZeroes=0;
        for(int a=0;a<16;a++) {
            int sum=0;
            for (int b = 0; b < 16; b++) {
                sum+=outputValuesCountForA[b][a];
            }
            if(ti4VarToBoolean(a)){
                minOnes=Math.min(minOnes,sum);
                maxOnes=Math.max(maxOnes,sum);
            } else{
                minZeroes=Math.min(minZeroes,sum);
                maxZeroes=Math.max(maxZeroes,sum);
            }
            System.out.print(String.format("%2d ",sum));
        }
        System.out.print("\n -> ones = ["+minOnes+","+maxOnes+"], zeroes = ["+minZeroes+","+maxZeroes+"]");
        if((minOnes==maxOnes) && (minZeroes==maxZeroes)) System.out.println(" -> Uniform :-)");
        else System.out.println(" -> NOT uniform!");
        System.out.println();
    }

    @Test
    public void testTi4AndUniform() throws Exception {
        testTi4AndUniformForInput(false);
        testTi4AndUniformForInput(true);
    }
}