package uk.co.nimp;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by seb on 6/22/14.
 */
public class Z2 {
    public static final boolean[] ZERO = new boolean[1];
    public static final boolean[] ONE;
    public static final boolean[] TWO;
    static {
        ONE = new boolean[1];
        ONE[0]=true;
        TWO = new boolean[2];
        TWO[1]=true;
    }
    public static final boolean[] X = TWO;
    public static boolean[] toBooleans(String in){
        in = in.replace(" ","");
        in = in.replace("\t","");
        in = in.replace("\n","");
        in = in.replace("_","");
        boolean[] out = new boolean[in.length()];
        for(int i=0;i<in.length();i++){
            if(in.charAt(i)=='1') out[i]=true;
        }
        return out;
    }
    public static String toBinaryString(boolean []in){
        return toBinaryString(in,0,in.length);
    }
    public static String toBinaryString(boolean []in, int offset, int length){
        String out="";
        for(int i=offset;i<length;i++){
            if(in[i]) out +="1";
            else out += "0";
        }
        return out;
    }
    public static String toPolynomial(boolean []in){
        String out="";
        String separator = " + ";
        if(in.length>0 && in[0]) out +="1"+separator;
        if(in.length>1 && in[1]) out +="x"+separator;
        for(int i=2;i<in.length;i++){
            if(in[i]) out +="x"+i+separator;
        }
        if(out.isEmpty()) out = "0";
        else out = out.substring(0,out.length()-separator.length());
        return out;
    }
    public static boolean[] polynomialToBooleans(String in){
        String s = in.toLowerCase();
        String[] termsStr = s.split("\\+");
        boolean[] coefs = new boolean[termsStr.length];
        int[] degrees = new int[termsStr.length];
        int maxDegree = 0;
        for(int i=0;i<termsStr.length;i++){
            termsStr[i] = termsStr[i].trim();
            String t = termsStr[i];
            if(t.isEmpty()) continue;
            boolean coef = true;
            if(t.startsWith("0")) {
                coef=false;
                t = t.substring(1);
            } else {
                if(t.startsWith("1")) {
                    coef=true;
                    t = t.substring(1);
                } else if(!t.startsWith("x")) throw new RuntimeException("Expected '0', '1' or 'x', got '"+t+"'");
            }
            if(t.startsWith("x")){
                t=t.substring(1);
                if(t.isEmpty()) degrees[i] = 1;
                else degrees[i] = Integer.decode(t);
                maxDegree = Math.max(maxDegree,degrees[i]);
            }
            coefs[i] = coef;
        }
        boolean[] out = new boolean[maxDegree+1];
        for(int i=0;i<coefs.length;i++){
            out[degrees[i]]^=coefs[i];
        }
        return out;
    }
    /**
     * compute a = a ^ (b<<bShift)
     * @param a
     * @param b
     * @param bShift
     */
    static void selfAdd(boolean[] a, boolean[] b, int bShift){
        for(int i=0;i<b.length;i++){
            a[i+bShift] ^= b[i];
        }
    }
    static void copy(boolean[] src,boolean[] dst){System.arraycopy(src,0,dst,0,src.length);}
    static boolean[] clone(boolean[] src) {
        return cloneRange(src,0,src.length);
    }
    public static boolean[] cloneRange(boolean[] src, int offset, int length){
        boolean[] out = new boolean[length];
        System.arraycopy(src,offset,out,0,length);
        return out;
    }

    static int msbIndex(boolean[] in){
        int i = in.length-1;
        while(i>0 && !in[i]) i--;
        return i;
    }
    static boolean[] substitute(boolean[] in, boolean[][] sbox){
        int input = booleansToInt(in);
        return sbox[input].clone();
    }

    /**
     * return a copy of input with the minimum length to preserve all bits to one
     * the msb of the output is therefore always one. The single exception to that is when the input is all 0, then
     * output contain a single bit set to 0.
     * @param in
     * @return
     */
    public static boolean[] minimumLengthCopy(boolean[] in){
        int i = msbIndex(in);
        if(0==i){
            if(in[i]) return ONE.clone();
            else return ZERO.clone();
        }
        boolean[] out = new boolean[i+1];
        System.arraycopy(in,0,out,0,i+1);
        assert(out[out.length-1]);
        return out;
    }
    public static boolean equalValue(boolean[] a, boolean[] b){
        boolean []bigger;
        boolean []smaller;
        if(a.length>b.length){
            bigger = a;
            smaller = b;
        }else{
            bigger = b;
            smaller = a;
        }
        for(int i=0;i<smaller.length;i++){
            if(bigger[i]!=smaller[i]) return false;
        }
        //check the msb of bigger array: if they are all 0, then we have equality
        for(int i=smaller.length;i<bigger.length;i++){
            if(bigger[i]) return false;
        }
        return true;
    }
    public static boolean equal(boolean[] a, boolean[] b){
        if(a.length!=b.length) return false;
        for(int i=0;i<a.length;i++){
            if(a[i]!=b[i]) return false;
        }
        return true;
    }
    public static boolean equal(boolean[][] a, boolean[][] b){
        if(a.length!=b.length) return false;
        for(int i=0;i<a.length;i++) {
            if (!Z2.equal(a[i], b[i])) return false;
        }
        return true;
    }
    public static boolean[] xor(boolean[] a, boolean[] b){
        int len = Math.max(a.length,b.length);
        boolean[] out = new boolean[len];
        copy(a, out);
        for(int i=0;i<b.length;i++){
            out[i] ^= b[i];
        }
        return out;
    }
    public static boolean[] xor(boolean[] a, boolean[] b,int outputLength){
        boolean[] out = new boolean[outputLength];
        System.arraycopy(a, 0, out, 0, Math.min(outputLength, a.length));
        for(int i=0;i<Math.min(outputLength,b.length);i++){
            out[i] ^= b[i];
        }
        return out;
    }
    public static boolean[] add(boolean[] a, boolean[] b){
        return add(a,b,0);
    }
    public static boolean[] sub(boolean[] a, boolean[] b){
        return add(a,b,0);
    }
    public static boolean[] add(boolean[] a, boolean[] b, int bShift){
        assert(a[a.length-1] || equal(a,Z2.ZERO));assert(b[b.length-1] || equal(b,Z2.ZERO));
        int len = Math.max(a.length,b.length+bShift);
        boolean[] out = new boolean[len];
        copy(a, out);
        for(int i=0;i<b.length;i++){
            out[i+bShift] ^= b[i];
        }
        if(isZero(out)) return Z2.ZERO.clone();
        //assert(out[out.length-1]);
        if(!out[out.length-1])
            out = Z2.minimumLengthCopy(out);
        return out;
    }
    static boolean[] mul(boolean[] a, boolean[] b){
        assert(a[a.length-1] || equal(a,Z2.ZERO));assert(b[b.length-1] || equal(b,Z2.ZERO));
        boolean[] out = new boolean[a.length+b.length-1];
        for(int i=0;i<a.length;i++){
            if(a[i]) selfAdd(out,b,i);
        }
        assert(out[out.length-1] || equal(out,Z2.ZERO));
        return out;
    }
    static int hammingWeight(boolean []in){
        return booleansToBigInteger(in).bitCount();
    }
    static int hammingDistance(boolean []a, boolean []b){
        BigInteger aBi = booleansToBigInteger(a);
        BigInteger bBi = booleansToBigInteger(b);
        return hammingDistance(aBi, bBi);
    }
    static int hammingDistance(BigInteger a, BigInteger b){
        return a.xor(b).bitCount();
    }
    static int minHammingDistance(BigInteger [] in){
        if(in.length<2) throw new RuntimeException("Need at least two element to compute hamming distance, got "+in.length);
        int out = Integer.MAX_VALUE;
        for(int i=0;i<in.length;i++){
            for(int j=i+1;j<in.length;j++){
                out = Math.min(out,hammingDistance(in[i],in[j]));
                if(0==out) return out;
            }
        }
        return out;
    }
    public static int minHammingDistance(boolean [][] in) {
        BigInteger [] inBi = booleansArrayToBigIntegers(in);
        return minHammingDistance(inBi);
    }
    static BigInteger[] booleansArrayToBigIntegers(boolean [][] in){
        BigInteger []out= new BigInteger[in.length];
        for(int i=0;i<in.length;i++){
            out[i] = booleansToBigInteger(in[i]);
        }
        return out;
    }
    public static void toBinaryFile(File file,boolean[] in) throws IOException {
        BigInteger inBi = Z2.booleansToBigInteger(in);
        toBinaryFile(file,inBi);
    }
    public static void toBinaryFile(File file,BigInteger in) throws IOException {
        byte []data = in.toByteArray();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(data);
        fos.flush();
        fos.close();
    }
    public static BigInteger binaryFileToBigInteger(File file) throws IOException {
        byte[] data = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(data);
        fis.close();
        return new BigInteger(data);
    }
    static void toBinaryStringFile(File file, BigInteger in) throws IOException {
        BufferedWriter writer = new BufferedWriter( new FileWriter(file));
        try {
            for(int i=0;i<in.bitLength();i++) {
                if(in.testBit(i)) writer.write("1");
                else writer.write("0");
                if((i%(1<<16)==0) && (i!=0)) writer.newLine();
            }
        } finally {
            writer.flush();
            writer.close();
        }
    }
    static BigInteger binaryStringFileToBigInteger(File file) throws IOException {
        BigInteger out = BigInteger.ZERO;
        BufferedReader br = new BufferedReader(new FileReader(file));
        int bitLength=0;
        try {
            String line = br.readLine();
            while (line != null) {
                for(int i=0;i<line.length();i++){
                    char c = line.charAt(i);
                    if(Character.isWhitespace(c)) continue;
                    if(c=='_') continue;
                    if(c=='-') continue;
                    if(c=='0') {bitLength++;continue;};
                    if(c=='1') {out=out.setBit(bitLength++);continue;};
                }
                line = br.readLine();
            }
        } finally {
            br.close();
        }
        return out;
    }
    public static void toBinaryStringFile(File file, boolean[] in) throws IOException {
        BigInteger inBi = booleansToBigInteger(in);
        toBinaryStringFile(file,inBi);
    }
    public static boolean[] binaryStringFileToBooleans(File file) throws IOException {
        BigInteger bi=binaryStringFileToBigInteger(file);
        return toBooleans(bi);
    }
    public static void toBinaryStringFile(File file, boolean[][] in) throws IOException {
        BufferedWriter writer = new BufferedWriter( new FileWriter(file));
        try {
            for(int i=0;i<in.length;i++) {
                writer.write(toBinaryString(in[i]));
                writer.newLine();
            }
        } finally {
            writer.flush();
            writer.close();
        }
    }
    static BigInteger[] binaryStringFileToBigIntegers(File file) throws IOException {
        ArrayList<BigInteger> out = new ArrayList<BigInteger>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            String line = br.readLine();
            while (line != null) {
                int bitLength=0;
                BigInteger bi = BigInteger.ZERO;
                for(int i=0;i<line.length();i++){
                    char c = line.charAt(i);
                    if(Character.isWhitespace(c)) continue;
                    if(c=='_') continue;
                    if(c=='-') continue;
                    if(c=='0') {bitLength++;continue;};
                    if(c=='1') {bi=bi.setBit(bitLength++);continue;};
                }
                out.add(bi);
                line = br.readLine();
            }
        } finally {
            br.close();
        }
        return (BigInteger[]) out.toArray();
    }
    static boolean[] toBooleans(ArrayList<Boolean> list){
        boolean[] out = new boolean[list.size()];
        for(int i=0;i<list.size();i++) out[i] = list.get(i);
        return out;
    }
    public static boolean[][] binaryStringFileToBooleansArray(File file) throws IOException {
        ArrayList<boolean[]> tmp = new ArrayList<boolean[]>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            String line = br.readLine();
            while (line != null) {
                ArrayList<Boolean> b = new ArrayList<Boolean>();
                for(int i=0;i<line.length();i++){
                    char c = line.charAt(i);
                    if(Character.isWhitespace(c)) continue;
                    if(c=='_') continue;
                    if(c=='-') continue;
                    if(c=='0') {b.add(false);continue;};
                    if(c=='1') {b.add(true);continue;};
                }
                tmp.add(toBooleans(b));
                line = br.readLine();
            }
        } finally {
            br.close();
        }
        boolean [][]out = new boolean[tmp.size()][];
        for(int i=0;i<tmp.size();i++) out[i]=tmp.get(i);
        return out;
    }
    public static boolean[][] toBooleansArray(String in) {
        String []lines = in.split("\n");
        boolean[][] out = new boolean[lines.length][];
        for(int line=0;line<lines.length;line++){
            out[line]=Z2.toBooleans(lines[line]);
        }
        return out;
    }
    public static boolean toBoolean(int in){return in!=0;}
    public static boolean[] toBooleans(int[] in) {
        boolean[] out = new boolean[in.length];
        for(int i=0;i<in.length;i++){
            out[i] = Z2.toBoolean(in[i]);
        }
        return out;
    }
    public static boolean[][] toBooleansArray(int[][] in) {
        boolean[][] out = new boolean[in.length][];
        for(int i=0;i<in.length;i++){
            out[i] = Z2.toBooleans(in[i]);
        }
        return out;
    }
    public static boolean isGreater(boolean[] a, boolean[] b){
        int aMsbIndex = msbIndex(a);
        int bMsbIndex = msbIndex(b);
        if(aMsbIndex>bMsbIndex) return true;
        if(aMsbIndex<bMsbIndex) return false;
        int i = bMsbIndex;
        while(i>0 && a[i]==b[i]) i--;
        return a[i] & !b[i];
    }
    static boolean isGreaterOrEqual(boolean[] a, boolean[] b){
        return !isGreater(b,a);
    }
    static boolean[] div(boolean[] in, boolean[] divider){
        assert(in[in.length-1] || equal(in,Z2.ZERO));assert(divider[divider.length-1]);
        boolean[] quotient = new boolean[in.length];
        boolean[] reminder = in.clone();
        int reminderMsbIndex = reminder.length-1;
        int i=0;
        while(isGreaterOrEqual(reminder,divider)){
            quotient[i++]=true;
            int shift = reminderMsbIndex-(divider.length-1);
            selfAdd(reminder, divider, shift);
            while(reminderMsbIndex>divider.length-1 && !reminder[--reminderMsbIndex]){
                i++;
            }
        }
        boolean[] out = reverse(quotient,0,i);
        assert(out[out.length-1] || equal(out,Z2.ZERO));
        return out;
    }
    static boolean[] mod(boolean[] in, boolean[] moduli){
        boolean[] divider = moduli;
        assert(in[in.length-1] || equal(in,Z2.ZERO));assert(divider[divider.length-1]);
        if(in.length<moduli.length) return in.clone();
        if(Z2.isOne(moduli)) return Z2.ZERO.clone();
        boolean[] quotient = new boolean[in.length];
        boolean[] reminder = in.clone();
        int reminderMsbIndex = reminder.length-1;
        int i=0;
        //while(isGreaterOrEqual(reminder, divider)){
        while(reminder.length>=divider.length){
            quotient[i++]=true;
            int shift = reminderMsbIndex-(divider.length-1);
            selfAdd(reminder, divider, shift);
            while(reminderMsbIndex>=divider.length-1 && !reminder[--reminderMsbIndex]){
                i++;
            }
            reminder = minimumLengthCopy(reminder);
        }
        boolean[] out = minimumLengthCopy(reminder);
        assert(out[out.length-1] || equal(out,Z2.ZERO));
        return out;
    }
    static boolean isZero(boolean[] in){
        for(int i=0;i<in.length;i++) if(in[i]) return false;
        return true;
    }
    static boolean isOne(boolean[] in){
        if(in.length==0) return false;
        for(int i=1;i<in.length;i++) if(in[i]) return false;
        return in[0];
    }
    static boolean[] gcd(boolean[] a, boolean[] b){
        assert(a[a.length-1] || equal(a,Z2.ZERO));assert(b[b.length-1] || equal(b,Z2.ZERO));
        if(Z2.isOne(a)||Z2.isOne(b)) return Z2.ONE.clone();
        boolean[] gx = a.clone();
        boolean[] hx = b.clone();
        while(!isZero(hx)){
            boolean[] rx = mod(gx,hx);
            gx = hx;
            hx = rx;
        }
        boolean[] out = gx;
        assert(out[out.length-1] || equal(out,Z2.ZERO));
        return out;
    }

    /**
     * Algorithm 4.69 Testing a polynomial for irreducibility
     * @param fx
     * @return true if fx is irreducible over Z2
     */
    static boolean isIrreducible(boolean[] fx){
        assert(fx[fx.length-1] || equal(fx,Z2.ZERO));
        int m = fx.length-1;
        boolean[] ux = X;
        for(int i=1;i<=(m+1)/2;i++){
            ux = mod(mul(ux,ux),fx);
            boolean[] delta = sub(ux,X);
            boolean[] dx = gcd(delta,fx);
            if(!isOne(dx)) return false;
        }
        return true;
    }

    /**
     * Algorithm 4.77 Testing a polynomial for primitivity
     * @param fx
     * @return true if fx is primitive over Z2
     */
    static boolean isPrimitive(boolean[] fx){
        assert(fx[fx.length-1] || equal(fx,Z2.ZERO));
        if(!isIrreducible(fx)) return false;//fx must be irreducible to have a chance to be primitive
        //1 is alsways a factor, so we check it now:
        //if(!isPrimitiveCore(1,fx)) return false;//never going to return false as x is always smaller than fx except in extreme cases fx=1 or fx=x...
        BigInteger p = BigInteger.valueOf(2);//computing in Z2
        int m = fx.length-1;
        BigInteger product = p.pow(m).subtract(BigInteger.ONE);
        BigInteger[] factors = PollardRho.factor(product);
        for(int i=0;i<factors.length;i++){
            BigInteger ri = factors[i];
            BigInteger exp = product.divide(ri);
            if(!isPrimitiveCore(exp,fx)) return false;
        }
        return true;
    }
    static boolean isPrimitiveCore(BigInteger exp,boolean[] fx){
        boolean[] lx = Z2.modExp(Z2.X,exp,fx);
        if(Z2.equal(lx,Z2.ONE)) return false;
        return true;
    }
    static boolean isPrimitiveCore(int expInt,boolean[] fx){
        assert(expInt>=0);
        boolean[] x_exp = new boolean[expInt+1];
        x_exp[expInt]=true;
        boolean[] lx = Z2.mod(x_exp,fx);
        if(Z2.equal(lx,Z2.ONE)) return false;
        return true;
    }
    static boolean[] modExp(boolean[] gx,long exp, boolean[] moduli){
        return modExp(gx,BigInteger.valueOf(exp),moduli);
    }

    /**
     * Algorithm 2.227 Repeated square and multiply for exponentiation in Fp^m
     * @param gx
     * @param exp positive integer <= p^m - 1
     * @param moduli irreducible polynomial of degree m over Z2
     * @return (gx^exp) mod moduli in Z2
     */
    static boolean[] modExp(boolean[] gx,BigInteger exp, boolean[] moduli){
        assert(exp.signum()>=0);
        BigInteger k = exp;
        boolean[] fx = moduli;
        boolean[] sx = Z2.ONE.clone();
        if(k==BigInteger.ZERO) return sx;
        boolean [] Gx = gx;
        if(k.testBit(0)) sx = gx.clone();
        for(int i=1;i<k.bitLength();i++){
            Gx = Z2.mul(Gx,Gx);
            Gx = Z2.mod(Gx,fx);
            if(k.testBit(i)){
                sx = Z2.mul(Gx,sx);
                sx = Z2.mod(sx,fx);
            }
        }
        return sx;
    }

    public static BigInteger booleansToBigInteger(boolean[] in){
        BigInteger out = BigInteger.ZERO;
        for(int i = 0;i<in.length;i++){
            if(in[i]) out = out.setBit(i);
        }
        return out;
    }
    public static int booleansToInt(boolean[] in){
        int out = 0;
        if(in.length>32) throw new RuntimeException("boolean array to big to fit in 32 bits integer");
        for(int i = 0;i<in.length;i++){
            if(in[i]) out |= 1<<i;
        }
        return out;
    }
    public static boolean[] toBooleans(BigInteger in) {
        return toBooleans(in,in.bitLength());
    }
    public static boolean[] toBooleans(BigInteger in, int outputLength) {
        boolean[] out = new boolean[outputLength];
        int iMax = Math.min(in.bitLength(),outputLength);
        for(int i=0;i<iMax;i++){
            if(in.testBit(i)) out[i] = true;
        }
        return out;
    }

    public static boolean[] toBooleans(int in) {
        return toBooleans(in, bitWidth(in));
    }
    public static boolean[] toBooleans(int in, int outputLength) {
        boolean[] out = new boolean[outputLength];
        int iMax = Math.min(bitWidth(in),outputLength);
        for(int i=0;i<iMax;i++){
            if((in & (1<<i)) != 0) out[i] = true;
        }
        return out;
    }
    public static int bitWidth(int in){
        //return (int) Math.ceil(Math.log(in)/Math.log(2));
        int i;
        if(in<0) return 32;
        for(i=1;i<31;i++){
            if(in<(1<<i)) return i;
        }
        return i;
    }
    static boolean []reverse(boolean []in) {
        return reverse(in, 0, in.length);
    }
    static boolean []reverse(boolean []in,int from, int to){
        boolean []out = new boolean[to-from];
        for(int i=0;i<out.length;i++) out[i] = in[to-1-i];
        return out;
    }
    public static String toBinaryString(boolean in){
        return in ? "1" : "0";
    }
    public static int toInt(boolean in) {return in ? 1 : 0;}

    public static boolean[] randomBooleans(int len) {
        return randomBooleans(len,System.currentTimeMillis());
    }
    public static boolean[] randomBooleans(int len,long seed) {
        boolean[] out = new boolean[len];
        Random rng = new Random(seed);
        for(int i = 0;i<len;i++) out[i] = rng.nextBoolean();
        return out;
    }
    public static boolean[][] randomBooleansArray(int len, int width){
        return randomBooleansArray(len,width,width);
    }
    public static boolean[][] randomBooleansArray(int len, int minWidth, int maxWidth){
        int delta = maxWidth-minWidth+1;
        Random rng = new Random();
        boolean[][] dat = new boolean[len][];
        for(int j=0;j<len;j++) {
            dat[j] = Z2.randomBooleans(minWidth+rng.nextInt(delta));
        }
        return dat;
    }
    public static String toBinaryString(boolean[][] in){
        String out="";
        for(int i=0;i<in.length;i++) out+=Z2.toBinaryString(in[i])+"\n";
        if(out.isEmpty()) return "";
        return out.substring(0,out.length()-1);
    }

    public static boolean[][] rowEchelonMatrix(boolean[][] in){
        return Z2.rowEchelonMatrix(in,in.length);
    }
    /**
     * also called gaussian elimination
     * pseudo code
     * for k = 1 ... m:
         Find pivot for column k:
         i_max  := argmax (i = k ... m, abs(A[i, k]))
         if A[i_max, k] = 0
            error "Matrix is singular!"
         swap rows(k, i_max)
         Do for all rows below pivot:
         for i = k + 1 ... m:
             Do for all remaining elements in current row:
             for j = k + 1 ... n:
                A[i, j]  := A[i, j] - A[k, j] * (A[i, k] / A[k, k])
             Fill lower triangular matrix with zeros:
                A[i, k]  := 0
     * @param in
     * @return
     */
    public static boolean[][] rowEchelonMatrix(boolean[][] in, int maxColumn){
        boolean[][] out = new boolean[in.length][];
        int nRows = in.length;
        int nColumns = in[0].length;
        for(int i=0;i<nRows;i++){
            out[i] = new boolean[nColumns];
            for(int j=0;j<nColumns;j++){
                out[i][j]=in[i][j];
            }
        }
        int row=0;
        for(int k=0;k<maxColumn;k++){
            //System.out.println(Z2.toBinaryString(out)+"\n");
            int iMax = -1;
            for(int i = row;i<nRows;i++){
                if(out[i][k]) {
                    iMax = i;
                    break;//stop the loop here, as 1 is the maximum in Z2...
                }
            }
            if(iMax==-1) continue;
            //if(!out[iMax][k]) continue;//throw new RuntimeException("Singular Matrix");
            if(iMax>k) {//swap
                boolean[] tmp = out[iMax];
                out[iMax] = out[k];
                out[k] = tmp;
            }
            //nullify other rows at column k
            for(int i=row;i<nRows;i++){
                if(i==row) continue;
                if(out[i][k]){
                    selfAdd(out[i],out[k],0);
                }
            }
            row++;
        }
        return out;
    }
    public static boolean[][] columnEchelonMatrix(boolean[][] in) {
        return Z2.columnEchelonMatrix(in,in[0].length);
    }
    public static boolean[][] columnEchelonMatrix(boolean[][] in,int maxRow) {
        boolean[][]out = Z2.transpose(in);
        out = Z2.rowEchelonMatrix(out,maxRow);
        return Z2.transpose(out);
    }
    public static boolean isColumnEchelonMatrix(boolean[][] in){
        boolean[][] t = Z2.transpose(in);
        return Z2.isRowEchelonMatrix(t);
    }

    public static boolean isRowEchelonMatrix(boolean[][] in){
        boolean[][] out = new boolean[in.length][];
        int nRows = in.length;
        int nColumns = in[0].length;
        int msColumn = 0;
        for(int i=0;i<nRows;i++){
            if(in[i].length!=nColumns) throw new RuntimeException("Malformed matrix: row "+i+" has length "+in[i].length+", expected "+nColumns);
            for(int j=0;j<msColumn;j++){
                if(in[i][j]) return false;
            }
            while((msColumn!=nColumns) && !in[i][msColumn]) {
                msColumn++;
            }
        }

        return true;
    }
    public static boolean[][] transpose(boolean[][]in){
        int nRows = in.length;
        int nColumns = in[0].length;
        boolean[][] out = new boolean[nColumns][];
        for(int j=0;j<nColumns;j++) out[j] = new boolean[nRows];
        for(int i=0;i<nRows;i++){
            for(int j=0;j<nColumns;j++){
                out[j][i] = in[i][j];
            }
        }
        return out;
    }
    public static boolean[][] rowAugment(boolean[][] hi,boolean[][]lo){
        int nRows = hi.length+lo.length;
        int nColumns = hi[0].length;
        if(lo[0].length!=nColumns) throw new RuntimeException("Can't row augment a matrix with another one of different column dimension");
        boolean[][]out = new boolean[nRows][];
        for(int i=0;i<nRows;i++) out[i] = new boolean[nColumns];
        for(int i=0;i<hi.length;i++){
            for(int j=0;j<nColumns;j++){
                out[i][j] = hi[i][j];
            }
        }
        for(int i=0;i<lo.length;i++){
            for(int j=0;j<nColumns;j++){
                out[i+hi.length][j] = lo[i][j];
            }
        }
        return out;
    }
    public static boolean[][] columnAugment(boolean[][] left,boolean[][]right){
        int nRows = left.length;
        int nColumns = left[0].length+right[0].length;
        int leftColumns = left[0].length;
        if(right.length!=nRows) throw new RuntimeException("Can't column augment a matrix with another one of different row dimension");
        boolean[][]out = new boolean[nRows][];
        for(int i=0;i<nRows;i++){
            out[i] = new boolean[nColumns];
            for(int j=0;j<leftColumns;j++){
                out[i][j] = left[i][j];
            }
            for(int j=leftColumns;j<nColumns;j++){
                out[i][j] = right[i][j-leftColumns];
            }
        }
        return out;
    }
    public static boolean[][] identityMatrix(int dim){
        boolean[][] out = new boolean[dim][];
        for(int i = 0;i<dim;i++){
            out[i] = new boolean[dim];
            out[i][i] = true;
        }
        return out;
    }
    public static boolean[][] rowAugmentIdentity(boolean[][] in){
        int nRows = in.length;
        int nColumns = in[0].length;
        if(nRows!=nColumns) throw new RuntimeException("Square matrix expected");
        boolean[][] identity = Z2.identityMatrix(nRows);
        return Z2.rowAugment(in,identity);
    }
    public static boolean[][] columnAugmentIdentity(boolean[][] in){
        int nRows = in.length;
        int nColumns = in[0].length;
        if(nRows!=nColumns) throw new RuntimeException("Square matrix expected");
        boolean[][] identity = Z2.identityMatrix(nRows);
        return Z2.columnAugment(in,identity);
    }
    /**
     * kernel basis given as columns
     * @param in
     * @return a matrix with each kernel basis in its column
     */
    public static boolean[][] matrixKernelBasisAsColumns(boolean[][]in){
        int maxRow = in.length;
        boolean[][] tmp = Z2.rowAugmentIdentity(in);
        tmp = Z2.columnEchelonMatrix(tmp,maxRow);
        int colOffset=0;
        for(int i=0;i<maxRow;i++){
            if(firstOneIndex(in[i])==-1) {
                colOffset = i;
                break;
            }
        }
        boolean[][]out = copySubMatrix(tmp,maxRow,colOffset);
        System.out.println(Z2.toBinaryString(out));
        return out;
    }

    /**
     * kernel basis given as rows
     * @param in
     * @return a matrix with each kernel basis in its row
     */
    public static boolean[][] matrixKernelBasis(boolean[][]in){
        int maxColumn = in[0].length;
        boolean[][] tmp = Z2.transpose(in);
        tmp=Z2.columnAugmentIdentity(tmp);
        //System.out.println(Z2.toBinaryString(tmp));

        tmp = Z2.rowEchelonMatrix(tmp,maxColumn);
        //System.out.println(Z2.toBinaryString(tmp));

        int rowOffset=0;
        for(int i=0;i<maxColumn;i++){
            if(firstOneIndexInColumn(in,i)==-1) {
                rowOffset = i;
                break;
            }
        }
        boolean[][]out = copySubMatrix(tmp,rowOffset,maxColumn);
        //System.out.println(Z2.toBinaryString(out));
        return out;
    }

    /**
     * Algorithm 3.111: Berlekamp's Q-matrix algorithm
     * @param in a square free monic polynomial of degree n in Fq[x]
     * @return the factorization of in into monic irreducible polynomials
     */
    public static boolean[][] factorPolynomial(boolean[] in){
        final int q=2;
        int n = in.length-1;
        boolean[][]Q = new boolean[n][];
        for(int i=0;i<n;i++) Q[i] = new boolean[n];
        for(int i=0;i<n;i++){
            boolean[] p=Z2.modExp(Z2.X,i*q,in);
            for(int j=0;j<p.length;j++) Q[n-1-j][n-1-i] = p[j];
        }
        System.out.println("Q matrix");
        System.out.println(Z2.toBinaryString(Q));
        boolean[][]qMinusI = Z2.xor(Q,Z2.identityMatrix(n));
        boolean[][]qMinusI_echelon = Z2.rowEchelonMatrix(qMinusI);
        System.out.println("Q minus I matrix in row echelon form:");
        System.out.println(Z2.toBinaryString(qMinusI_echelon));
        boolean[][]basis = matrixKernelBasis(qMinusI_echelon);
        System.out.println("basis");
        System.out.println(Z2.toBinaryString(basis));

        return null;//TODO
    }
    public static boolean[][] xor(boolean[][] a,boolean[][] b){
        final int nRows = a.length;
        assert(nRows==b.length);
        if(0==nRows) return new boolean[0][];
        final int nColumns = a[0].length;
        boolean [][]out = new boolean[nRows][];
        for(int i=0;i<nRows;i++){
            out[i]=new boolean[nColumns];
            for(int j=0;j<nColumns;j++){
                out[i][j] = a[i][j]^b[i][j];
            }
        }
        return out;
    }

    public static boolean[][] copySubMatrix(boolean[][]in,int rowOffset,int colOffset) {
        int nRows = in.length;
        if(0==nRows) return new boolean[0][];
        return copySubMatrix(in,rowOffset,colOffset,nRows-rowOffset,in[0].length-colOffset);
    }
    public static boolean[][] copySubMatrix(boolean[][]in,int rowOffset,int colOffset,int nRows) {
        if(0==nRows) return new boolean[0][];
        return copySubMatrix(in,rowOffset,colOffset,nRows,in[0].length-colOffset);
    }
    public static boolean[][] copySubMatrix(boolean[][]in,int rowOffset,int colOffset,int nRows, int nCols){
        boolean[][] out = new boolean[nRows][];
        for(int i = 0;i<nRows;i++){
            out[i]=new boolean[nCols];
            for(int j=0;j<nCols;j++){
                out[i][j] = in[rowOffset+i][colOffset+j];
            }
        }
        return out;
    }

    /**
     * return the index of the least significant bit set to 1
     * @param in
     * @return index of the least significant bit set to 1
     */
    public static int firstOneIndex(boolean[] in){
        for(int i = 0;i<in.length;i++) if(in[i]) return i;
        return -1;
    }

    /**
     * return the index of the least significant bit set to 1 in a column
     * @param in
     * @return index of the least significant bit set to 1 in column
     */
    public static int firstOneIndexInColumn(boolean[][] in,int column){
        for(int i = 0;i<in[column].length;i++) if(in[column][i]) return i;
        return -1;
    }
    public static boolean determinant2x2(boolean[][] in){
        int nRows = in.length;
        int nColumns = in[0].length;
        assert(nRows==2);
        assert(nColumns==2);
        return (in[0][0]&in[1][1]) ^ (in[1][0]&in[0][1]);
    }
}
