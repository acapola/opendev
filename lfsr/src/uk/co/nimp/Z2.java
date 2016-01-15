package uk.co.nimp;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

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
    static String cleanUp(String in){
        in = in.replace(" ", "");
        in = in.replace("\t","");
        in = in.replace("\n","");
        in = in.replace("_","");
        return in;
    }
    public static boolean[] toBooleans(String in){
        in=cleanUp(in);
        boolean[] out = new boolean[in.length()];
        for(int i=0;i<in.length();i++){
            if(in.charAt(i)=='1') out[i]=true;
        }
        return out;
    }
    public static boolean[] hexBytesToPaddedBooleans(String in) {
        in=cleanUp(in);
        if(in.length()%2!=0) throw new RuntimeException("Can't convert an hex bytes string if it has an odd number of digits: "+in.length()+", '"+in+"'");
        int len = in.length()*4;
        boolean[] out = new boolean[len];
        for(int i=0;i<in.length();i+=2){
            int nibbles = Integer.parseInt(in.substring(i,i+2),16);
            boolean[] bits = Z2.toBooleans(nibbles);
            for(int j=0;j<bits.length;j++) out[i*4+j] = bits[j];
        }
        return out;
    }
    public static byte[] hexBytesToBytes(String in) {
        in=cleanUp(in);
        if(in.length()%2!=0) throw new RuntimeException("Can't convert an hex bytes string if it has an odd number of digits: "+in.length()+", '"+in+"'");
        int len = in.length()/2;
        byte[] out = new byte[len];
        for(int i=0;i<in.length();i+=2){
            int nibbles = Integer.parseInt(in.substring(i,i+2),16);
            byte bits = (byte)(nibbles);
            out[i/2] = bits;
        }
        return out;
    }
    public static boolean[] hexBytesToBooleans(String in){
        return Z2.minimumLengthCopy(hexBytesToPaddedBooleans(in));
    }
    public static boolean[] hexBytesToBooleans(String in, int length){
        boolean[] fullLength = hexBytesToPaddedBooleans(in);
        boolean[] out = new boolean[length];
        System.arraycopy(fullLength,0,out,0,length);
        return out;
    }
    public static String toBinaryString(boolean []in){
        return toBinaryString(in,0,in.length);
    }
    public static String toBinaryString(boolean []in, int offset, int length){
        String out="";
        for(int i=offset;i<offset+length;i++){
            if(in[i]) out +="1";
            else out += "0";
        }
        return out;
    }
    public static String toHexByteString(boolean[] in){ return toHexByteString(in, 0, in.length);}

    public static String toHexByteString(boolean[] in, int offset, int length){
        String out="";
        for(int i=0;i<length;i+=8){
            int digit = Z2.booleansToInt(in,offset+i,8);
            out+=String.format("%02X",digit);
        }
        return out;
    }

    public static String toPolynomial(boolean []in){
        String out="";
        String separator = "+";
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
    static void selfAdd(boolean[] a, final boolean[] b, final int bShift){
        assert(bShift>=0);
        for(int i=0;i<b.length;i++){
            a[i+bShift] ^= b[i];
        }
    }
    static void copy(boolean[] src,boolean[] dst){System.arraycopy(src,0,dst,0,src.length);}
    /*static boolean[] clone(boolean[] src) {
        return cloneRange(src,0,src.length);
    }*/

    public static boolean[] cloneRange(boolean[] src, int offset, int length){
        boolean[] out = new boolean[length];
        System.arraycopy(src,offset,out,0,length);
        return out;
    }
    public static boolean[] cloneAndPad(boolean[] src, int length){
        boolean[] out = new boolean[length];
        System.arraycopy(src,0,out,0,src.length);
        return out;
    }

    public static int msbIndex(boolean[] in){
        int i = in.length-1;
        while(i>0 && !in[i]) i--;
        return i;
    }
    public static int lsbSetIndex(boolean[] in){
        if(Z2.isZero(in)) return -1;
        int i = 0;
        while(i<in.length && !in[i]) i++;
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
        int len = Math.max(a.length, b.length);
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
        //assert(a[a.length-1] || equal(a,Z2.ZERO));assert(b[b.length-1] || equal(b,Z2.ZERO));
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
    public static boolean[] mul(boolean[] a, boolean[] b){
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
    static boolean[] firstWithHammingWeight(int targetHammingWeight, int targetWidth){
        boolean[] out = new boolean[targetWidth];
        for(int i=0;i<targetHammingWeight;i++) out[i]=true;
        return out;
    }
    static List<Integer> valToOnesIndexes(boolean[] in){
        List<Integer> out = new ArrayList<Integer>();
        for(int i=0;i<in.length;i++) if(in[i]) out.add(i);
        return out;
    }
    static boolean[] onesIndexesToBooleans(List<Integer> ones, int width) {
        boolean[] out = new boolean[width];
        for(int i:ones) out[i]=true;
        return out;
    }

    static boolean[] nextWithSameHammingWeigth(boolean[] in){
        List<Integer> ones = valToOnesIndexes(in);
        int targetHammingWeight = ones.size();
        for(int bitIndex=0;bitIndex<targetHammingWeight;bitIndex++){
            //bitIndex refers to ones
            //bitPos refers to the binary representation
            int bitPos = ones.get(bitIndex);
            int nextBitPos = bitPos+1;
            int nextBitIndex = bitIndex+1;
            if(     ((nextBitIndex <  targetHammingWeight) && (nextBitPos<ones.get(nextBitIndex))) ||
                    ((nextBitIndex == targetHammingWeight) && (nextBitPos<in.length))){
                //move the bit by one position left
                ones.set(bitIndex,nextBitPos);
                if(bitIndex>0){
                    //reset all lower bits
                    int cnt=0;
                    for(int i=0;i<bitIndex;i++) ones.set(i,cnt++);
                }
                return onesIndexesToBooleans(ones, in.length);
            }
            //this bit cannot be moved further, try the next one
        }
        //all numbers enumerated
        return null;
    }
    public static byte[] toByteArray(boolean[]in){
        int byteLen = (in.length+7)/8;
        byte[] out = new byte[byteLen];
        for(int i=0;i<in.length/8;i++){
            for(int j=7;j>=0;j--){
                out[i]=(byte)((out[i]<<1) | ((in[i*8+j]) ? 1 : 0));
            }
        }
        int remaining = in.length %8;
        int last = byteLen-1;
        for(int j=remaining-1;j>=0;j--){
            int bit  = ((in[last*8+j]) ? 1 : 0);
            out[last]=(byte)((out[last]<<1) | bit);
        }
        return out;
    }
    public static boolean[] toBooleans(byte[] in) {
        return toBooleans(in,0,in.length);
    }
    public static boolean[] toBooleans(byte[] in, int byteOffset,int byteLength){
        boolean[] out = new boolean[byteLength*8];
        for(int i=0;i<byteLength;i++){
            int b=in[byteOffset+i];
            boolean[] bBits = Z2.toBooleans(b,8);
            System.arraycopy(bBits,0,out,i*8,8);
        }
        return out;
    }
    public static byte[] booleansToBytes(boolean[] in){
        int outLength = (in.length+7)/8;
        byte[] out = new byte[outLength];
        for(int i=0;i<outLength;i++){
            for(int j=0;j<8;j++){
                int bitIndex = 8*i+j;
                if(bitIndex>in.length) break;
                if(in[bitIndex]) out[i]|=(byte)(1<<j);
            }
        }
        return out;
    }
    public static void toBinaryFile(File file,boolean[] in) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        byte[] data = Z2.toByteArray(in);
        fos.write(data);
        fos.flush();
        fos.close();
    }
    public static byte[] binaryFileToBytes(File file, int byteOffset, int byteLength) throws IOException {
        if(byteLength==-1) byteLength = (int) file.length();
        byte[] data = new byte[byteLength];
        FileInputStream fis = new FileInputStream(file);
        int remaining = byteOffset;
        while(remaining>0) {
            remaining-=fis.skip(remaining);
        }
        fis.read(data, 0, byteLength);
        fis.close();
        return data;
    }
    public static boolean[] binaryFileToBooleans(File file, int byteOffset, int byteLength) throws IOException {
        byte[] data = binaryFileToBytes(file,byteOffset,byteLength);
        return Z2.toBooleans(data);
    }
    public static boolean[] binaryFileToBooleans(File file) throws IOException {
        return binaryFileToBooleans(file,0,-1);
    }


    /*public static void toBinaryFile(File file,BigInteger in) throws IOException {
        byte []data = in.toByteArray();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(data);
        fos.flush();
        fos.close();
    }*/
    /*public static BigInteger binaryFileToBigInteger(File file) throws IOException {
        byte[] data = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(data);
        fis.close();
        return new BigInteger(data);
    }

    public static BigInteger binaryFileToBigInteger(File file, int offset, int length) throws IOException {
        if(length==-1) length = (int) file.length();
        byte[] data = new byte[length];
        FileInputStream fis = new FileInputStream(file);
        int remaining = offset;
        while(remaining>0) {
            remaining-=fis.skip(remaining);
        }
        fis.read(data, 0, length);
        fis.close();
        BigInteger seqBi = new BigInteger(data);
        return seqBi;
    }

    public static boolean[] binaryFileToBooleans(File file, int offset, int length) throws IOException {
        if(length==-1) length = (int) file.length();
        BigInteger seqBi = binaryFileToBigInteger(file,offset,length);
        return Z2.toBooleans(seqBi,length*8);
    }*/

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
    static byte[] toBytes(ArrayList<Byte> list){
        byte[] out = new byte[list.size()];
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
    public static List<byte[]> hexStringFileToBytesList(File file) throws IOException {
        ArrayList<byte[]> tmp = new ArrayList<byte[]>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            String line = br.readLine();
            while (line != null) {
                tmp.add(hexBytesToBytes(line));
                line = br.readLine();
            }
        } finally {
            br.close();
        }
        return tmp;
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
    /*public static boolean[] toBooleans(int in) {
        boolean[] out = new boolean[Integer.bitCount(in)];
        for(int i=0;i<32;i++){
            out[i] = Z2.toBoolean(in & (1<<i));
        }
        return out;
    }*/
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
    public static boolean isGreaterOrEqual(boolean[] a, boolean[] b){
        return !isGreater(b,a);
    }
    public static boolean[] div(boolean[] in, boolean[] divider){
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
        boolean[] out = reverse(quotient, 0, i);
        assert(out[out.length-1] || equal(out,Z2.ZERO));
        return out;
    }
    public static boolean[] mod(boolean[] in, boolean[] moduli){
        final boolean[] divider = moduli;
        final int dividerMsbPos = divider.length-1;
        assert(in[in.length-1] || equal(in,Z2.ZERO));
        assert(divider[dividerMsbPos]);
        if(in.length<moduli.length) return in.clone();
        if(Z2.isOne(moduli)) return Z2.ZERO.clone();
        boolean[] reminder = in.clone();
        int reminderMsbIndex = reminder.length-1;
        while(reminderMsbIndex>=dividerMsbPos){
            int shift = reminderMsbIndex-dividerMsbPos;
            selfAdd(reminder, divider, shift);
            while(reminderMsbIndex>=dividerMsbPos && !reminder[--reminderMsbIndex]);
        }
        boolean[] out = minimumLengthCopy(reminder);
        assert(out[out.length-1] || equal(out,Z2.ZERO));
        return out;
    }
    static boolean[] modSlow(boolean[] in, boolean[] moduli){
        boolean[] divider = moduli;
        assert(in[in.length-1] || equal(in,Z2.ZERO));
        assert(divider[divider.length-1]);
        if(in.length<moduli.length) return in.clone();
        if(Z2.isOne(moduli)) return Z2.ZERO.clone();
        boolean[] quotient = new boolean[in.length];
        boolean[] reminder = in.clone();
        int reminderMsbIndex = reminder.length-1;
        int i=0;
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
    public static boolean[] pow(boolean[] in, int exp){//TODO: test
        boolean[] out = in;
        for(int i=1;i<exp;i++) out = Z2.mul(out,in);//TODO: optimize
        return out;
    }
    public static boolean[] shiftLeft(boolean[]in,int shift){
        boolean[]out = new boolean[in.length+shift];
        for(int i=0;i<in.length;i++) out[i+shift]=in[i];
        return out;
    }
    public static boolean[] rotateLeft(boolean[]in,int shift){
        boolean[]out = new boolean[in.length];
        for(int i=0;i<in.length;i++) out[(i+shift)%in.length]=in[i];
        return out;
    }
    public static boolean[] rotateToMinValue(boolean[]in){
        boolean[] minValue = in.clone();
        boolean[] candidate = in;
        for(int i=0;i<in.length;i++){
            candidate=Z2.rotateLeft(candidate,1);
            if(Z2.isGreater(minValue,candidate)){
                minValue=candidate;
            }
        }
        return minValue;
    }
    public static Set<boolean[]> rotateToMinValue(Set<boolean[]>in){
        HashSet<boolean[]> out = new HashSet<boolean[]>(in.size());
        for(boolean[] i:in){
            out.add(Z2.rotateToMinValue(i));
        }
        return out;
    }
    public static Set<boolean[]> reverse(Set<boolean[]>in){
        HashSet<boolean[]> out = new HashSet<boolean[]>(in.size());
        for(boolean[] i:in){
            out.add(Z2.reverse(i));
        }
        return out;
    }


    /**
     * LCM of polynomials using formulae lcm(a,b) = product(pi_max(ei,fi)) where pi^ei is factors of a and pi^fi factors of b
     * @param a
     * @param b
     * @return
     */
    static boolean[] lcm(boolean[] a, boolean[] b){//TODO: test, consider using formulae: lcm(a,b) = (a / gcd(a,b)) * b
        if(Z2.isZero(a)||Z2.isZero(b)) return Z2.ZERO.clone();
        if(Z2.isOne(a)) return b.clone();
        if(Z2.isOne(b)) return a.clone();
        List<boolean[]> aFactors = Z2.factorPolynomialList(a);
        List<boolean[]> bFactors = Z2.factorPolynomialList(b);
        Map<BigInteger,Integer> aFactorsMap = new HashMap<BigInteger,Integer>();
        for(boolean[] f: aFactors){
            BigInteger fBi = Z2.booleansToBigInteger(f);
            if(aFactorsMap.containsKey(fBi)) aFactorsMap.put(fBi,aFactorsMap.get(fBi));
            else aFactorsMap.put(fBi,1);
        }
        Map<BigInteger,Integer> bFactorsMap = new HashMap<BigInteger,Integer>();
        for(boolean[] f: bFactors){
            BigInteger fBi = Z2.booleansToBigInteger(f);
            if(bFactorsMap.containsKey(fBi)) bFactorsMap.put(fBi,bFactorsMap.get(fBi));
            else bFactorsMap.put(fBi,1);
        }
        boolean[] out=Z2.ONE;
        for(BigInteger fBi: aFactorsMap.keySet()){
            int pow = aFactorsMap.get(fBi);
            if(bFactorsMap.containsKey(fBi)){
                int bPow = bFactorsMap.get(fBi);
                bFactorsMap.remove(fBi);
                if(bPow>pow) pow = bPow;
            }
            boolean[]f=Z2.toBooleans(fBi);
            out = Z2.mul(out,Z2.pow(f,pow));
        }
        for(BigInteger fBi: bFactorsMap.keySet()){
            int pow = bFactorsMap.get(fBi);
            boolean[]f=Z2.toBooleans(fBi);
            out = Z2.mul(out,Z2.pow(f,pow));
        }
        return out;
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
     * compute the least common multiple of two numbers a and b
     * @param a
     * @param b
     * @return LCM(a,b)
     */
    public static BigInteger lcmBi(BigInteger a, BigInteger b){
        return b.multiply(a.divide(a.gcd(b)));
    }

    /**
     * Compute r, the order of x, r being the smallest integer such that x^r mod polynomial = 1
     * @param polynomial
     * @return r
     */
    public static BigInteger orderOfX(boolean[] polynomial) {
        if(!polynomial[0]) return BigInteger.ZERO;//order of X is not defined if polynomial(0)=0
        if(polynomial[polynomial.length-1]==false) polynomial=Z2.minimumLengthCopy(polynomial);
        if(polynomial.length<=1) return BigInteger.ZERO;
        if(polynomial.length==2) return BigInteger.ONE;//polynomial is x+1

        if(Z2.isIrreducible(polynomial)){
            BigInteger qmMinus1=BigInteger.ONE.shiftLeft(polynomial.length-1).subtract(BigInteger.ONE);
            if(Z2.isPrimitive(polynomial)) return qmMinus1;//TODO: optimize further. Here we do the factorization of qmMinus1 twice is polynomial turn out to NOT be primitive
            /*According to Jyrki Lahtonen (StackExchange):
            If p(x) is irreducible of degree n, then the order is always a factor of 2n−1, i.e. a factor of what you would get with a primitive p(x).
            Furthermore, in that case the order will not be a factor of 2ℓ−1 for any ℓ∣n (ℓ which divides n), which allows you to eliminate some cases.
             */
            boolean lahtonenOpt=true;
            Set<BigInteger> blackList = new HashSet<BigInteger>();
            if(lahtonenOpt){
                BigInteger two = BigInteger.valueOf(2);
                int n=polynomial.length-1;//the degree of our polynomial
                BigInteger[] factorsOfN = PollardRho.factor(BigInteger.valueOf(n));//TODO: use int routine
                for(BigInteger l:factorsOfN){
                    BigInteger twoLMinusOne = l.multiply(two).subtract(BigInteger.ONE);
                    Map<BigInteger,Integer> toExclude = PollardRho.factorPowerOfTwoMinusOne(twoLMinusOne);
                    for(BigInteger f:toExclude.keySet()) blackList.add(f);
                }
            }

            //LIDL
            Set<BigInteger> factorsOfOrderOfX = new HashSet<BigInteger>();
            Map<BigInteger,Integer> factorsMap = PollardRho.factorPowerOfTwoMinusOne(qmMinus1);//PollardRho.factorMap(qmMinus1);
            for(BigInteger pj:factorsMap.keySet()){
                int r = factorsMap.get(pj);
                BigInteger divider = pj;
                while(r>0) {//TODO: binary search ?
                    BigInteger exp = qmMinus1.divide(divider);
                    if (!Z2.isOne(Z2.modExp(Z2.X, exp, polynomial))) {//order of x is a multiple of pj^rj
                        factorsOfOrderOfX.add(pj.pow(r));
                        break;
                    }
                    r--;
                    divider = divider.multiply(pj);
                }
            }
            BigInteger out = BigInteger.ONE;
            for(BigInteger f:factorsOfOrderOfX) out = out.multiply(f);
            return out;
/*

            BigInteger[] factors = PollardRho.factor(qmMinus1);
            long nCombination = 1<<factors.length;
            for(long i=1;i<nCombination;i++) {
                BigInteger candidate = BigInteger.ONE;
                boolean[] selection = Z2.toBooleans(BigInteger.valueOf(i));
                for (int j = 0; j < selection.length; j++) {
                    if (selection[j]) candidate = candidate.multiply(factors[j]);
                }
                if(lahtonenOpt && blackList.contains(candidate)) continue;
                boolean[] checker = Z2.modExp(Z2.X, candidate, polynomial);
                if (Z2.isOne(checker)) {
                    return candidate;
                }
            }
            throw new RuntimeException("could not find the order of X for polynomial "+Z2.toPolynomial(polynomial));*/
        }
        // Theorem 3.11 of "Finite fields by Lidl & Niederreiter
        //f = f1^b1 * ... * fk^bk
        //ord(f)=e * 2^t with e=lcm(ord(f1),...,ord(fk)) and t smallest integer such that 2^t>=max(b1,...bk)
        Map<BigInteger,Integer> factorsMap = Z2.factorPolynomialMap(polynomial);
        BigInteger e = BigInteger.ONE;
        int maxPow=1;
        for(BigInteger fx:factorsMap.keySet()){
            int pow = factorsMap.get(fx);
            maxPow = Math.max(maxPow,pow);
            BigInteger orderOfX = Z2.orderOfX(Z2.toBooleans(fx));//recursion will go to the irreducible case
            e = Z2.lcmBi(e, orderOfX);
        }
        if(maxPow>1) {
            assert(!Z2.isOne(Z2.modExp(Z2.X, e, polynomial)));//check that we need that multiplication indeed
            BigInteger pt = BigInteger.valueOf(2).pow(Z2.bitWidth(maxPow - 1));
            e = e.multiply(pt);
        }
        assert(Z2.isOne(Z2.modExp(Z2.X, e, polynomial)));

        return e;
    }

    public static int orderOfX_callCnt=0;

    /**
     * Compute the order of x of a power of an irreducible polynomial
     * match Theorem 3.8 in "Finite Fields" by Lidl and Niederreiter
     * In our case we have only polynomials of characteristic 2
     * @param irreduciblePolynomial
     * @param power
     * @return order of x of irreduciblePolynomial^power
     */
    public static BigInteger orderOfX(boolean[] irreduciblePolynomial, int power) {
        assert(power>=1);
        BigInteger candidate = Z2.orderOfX(irreduciblePolynomial);
        boolean[] px = Z2.pow(irreduciblePolynomial, power);

        //power : 1   2   3   4   5   6   7   8   9
        //        0   1   2   2   3   3   3   3   4
        //base:   1   2   4   4   8   8   8   8   16
        BigInteger base = power==1 ? BigInteger.ONE : BigInteger.valueOf(2).pow(Z2.bitWidth(power-1));
        candidate = candidate.multiply(base);

        assert(Z2.isOne(Z2.modExp(Z2.X, candidate, px)));

        return candidate;
    }

    /**
     * Compute r, the order of x, r being the smallest integer such that x^r mod polynomial = 1
     * @param polynomial
     * @return r
     */
    public static BigInteger orderOfX_seb(boolean[] polynomial){
        orderOfX_callCnt++;
        if(!polynomial[0]) return BigInteger.ZERO;//order of X is not defined if polynomial(0)=0
        if(polynomial[polynomial.length-1]==false) polynomial=Z2.minimumLengthCopy(polynomial);
        if(polynomial.length<=1) return BigInteger.ZERO;
        if(polynomial.length==2) return BigInteger.ONE;//polynomial is x+1

        BigInteger maxLength=BigInteger.ONE.shiftLeft(polynomial.length-1).subtract(BigInteger.ONE);
        if(!Z2.isIrreducible(polynomial)){
            Map<BigInteger,Integer> factorsMap = Z2.factorPolynomialMap(polynomial);
            BigInteger out = BigInteger.ONE;

            for(BigInteger fx:factorsMap.keySet()){
                int pow = factorsMap.get(fx);
                BigInteger orderOfX = Z2.orderOfX(Z2.toBooleans(fx),pow);
                out = Z2.lcmBi(out,orderOfX);
            }
            //if(!out.equals(BigInteger.ONE))return out;
            return out;
            /*List<boolean[]> polyFactors = Z2.factorPolynomialList(polynomial);
            List<BigInteger> factors = new ArrayList<BigInteger>();
            for(boolean[] term : polyFactors){
                BigInteger termMaxLength=BigInteger.ONE.shiftLeft(term.length-1).subtract(BigInteger.ONE);
                if(termMaxLength.compareTo(BigInteger.ONE)>0) {
                    PollardRho.factor(termMaxLength,factors);
                }
            }
            if(factors.size()==0){
                factors.add(BigInteger.ONE);//needed for cases like (1+x)^2: all termMaxLength are one
            }
            Collections.sort(factors);//sort to get the smallest factors first (there are several solution to the congruence, we want the smallest)

            //Map<BigInteger,Integer> polyFactorsMap = Z2.booleansListToCountMap(polyFactors);
            //int maxPow = Collections.max(polyFactorsMap.values());
            BigInteger base = BigInteger.ONE;
            do {
                long nCombination = 1 << factors.size();
                for (long i = 1; i < nCombination; i++) {//TODO: replace long by BigInteger
                    BigInteger candidate = base;
                    boolean[] selection = Z2.toBooleans(BigInteger.valueOf(i));
                    for (int j = 0; j < selection.length; j++) {
                        if (selection[j]) candidate = candidate.multiply(factors.get(j));
                    }
                    boolean[] checker = Z2.modExp(Z2.X, candidate, polynomial);
                    if (Z2.isOne(checker)) {
                        return candidate;
                    }
                }
                base = base.multiply(BigInteger.valueOf(2));
            }while(base.compareTo(BigInteger.valueOf(2).pow(1000))<0);//TODO: remove, this is a workaround, not the real solution, we need to find the right base with a direct method.
            throw new RuntimeException(Z2.toPolynomial(polynomial));*/
        }
        BigInteger[] factors = PollardRho.factorPowerOfTwoMinusOne_Array(maxLength);
        long nCombination = 1<<factors.length;
        for(long i=1;i<nCombination;i++) {
            BigInteger candidate = BigInteger.ONE;
            boolean[] selection = Z2.toBooleans(BigInteger.valueOf(i));
            for (int j = 0; j < selection.length; j++) {
                if (selection[j]) candidate = candidate.multiply(factors[j]);
            }
            boolean[] checker = Z2.modExp(Z2.X, candidate, polynomial);
            if (Z2.isOne(checker)) {
                return candidate;
            }
        }
        throw new RuntimeException("could not find the order of X for polynomial "+Z2.toPolynomial(polynomial));
    }

    /**
     * Compute r, the order of x, r being the smallest integer such that x^r mod polynomial = 1
     * @param polynomial
     * @return r
     */
    public static BigInteger orderOfX_testedX16(boolean[] polynomial){
        if(Z2.equal(Z2.X,polynomial)) return BigInteger.ZERO;
        BigInteger maxLength=BigInteger.ONE.shiftLeft(polynomial.length-1).subtract(BigInteger.ONE);
        if(maxLength.compareTo(BigInteger.ZERO)<=0) return maxLength;
        if(maxLength.equals(BigInteger.ONE)) return BigInteger.ONE;
        if(!Z2.isIrreducible(polynomial)){
            List<boolean[]> polyFactors = Z2.factorPolynomialList(polynomial);
            List<BigInteger> factors = new ArrayList<BigInteger>();
            for(boolean[] term : polyFactors){
                BigInteger termMaxLength=BigInteger.ONE.shiftLeft(term.length-1).subtract(BigInteger.ONE);
                if(termMaxLength.compareTo(BigInteger.ONE)>0) {
                    PollardRho.factor(termMaxLength,factors);
                }
            }
            if(factors.size()==0){
                factors.add(BigInteger.ONE);//needed for cases like (1+x)^2: all termMaxLength are one
            }
            Collections.sort(factors);//sort to get the smallest factors first (there are several solution to the congruence, we want the smallest)
            Map<BigInteger,Integer> polyFactorsMap = Z2.booleansListToCountMap(polyFactors);
            int maxPow = Collections.max(polyFactorsMap.values());
            //maxPow: 1   2   3   4   5   6   7   8   9
            //        0   1   2   2   3   3   3   3   4
            //base:   1   2   4   4   8   8   8   8   16
            BigInteger base = maxPow==1 ? BigInteger.ONE : BigInteger.valueOf(2).pow(Z2.bitWidth(maxPow-1));
            /*if(!base.equals(BigInteger.ONE)){
                System.out.println("base="+base+", maxPow="+maxPow+", px=("+Z2.join(Z2.toPolynomials(polyFactors),")*(")+")");
            }*/
            base = BigInteger.ONE;
            do {
                long nCombination = 1 << factors.size();
                for (long i = 1; i < nCombination; i++) {//TODO: replace long by BigInteger
                    BigInteger candidate = base;
                    boolean[] selection = Z2.toBooleans(BigInteger.valueOf(i));
                    for (int j = 0; j < selection.length; j++) {
                        if (selection[j]) candidate = candidate.multiply(factors.get(j));
                    }
                    boolean[] checker = Z2.modExp(Z2.X, candidate, polynomial);
                    if (Z2.isOne(checker)) {
                        return candidate;
                    }
                }
                base = base.multiply(BigInteger.valueOf(2));
            }while(base.compareTo(BigInteger.valueOf(2).pow(1000))<0);//TODO: remove, this is a workaround, not the real solution, we need to find the right base with a direct method.
            throw new RuntimeException(Z2.toPolynomial(polynomial));
        }
        BigInteger[] factors = PollardRho.factor(maxLength);
        long nCombination = 1<<factors.length;
        for(long i=1;i<nCombination;i++) {
            BigInteger candidate = BigInteger.ONE;
            boolean[] selection = Z2.toBooleans(BigInteger.valueOf(i));
            for (int j = 0; j < selection.length; j++) {
                if (selection[j]) candidate = candidate.multiply(factors[j]);
            }
            boolean[] checker = Z2.modExp(Z2.X, candidate, polynomial);
            if (Z2.isOne(checker)) {
                return candidate;
            }
        }
        throw new RuntimeException("could not find the order of X for polynomial "+Z2.toPolynomial(polynomial));
    }


    /**
     * Algorithm 4.69 Testing a polynomial for irreducibility
     * @param fx
     * @return true if fx is irreducible over Z2
     */
    static final boolean[] X_1 = new boolean[]{true,true};
    static boolean isIrreducible(boolean[] fx){
        assert(fx[fx.length-1] || equal(fx,Z2.ZERO));
        if(!fx[0]) return false;//divisible by x
        if(Z2.equalValue(X_1,fx)) return true;
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
        if(Z2.equalValue(X_1,fx)) return true;
        if(!isIrreducible(fx)) return false;//fx must be irreducible to have a chance to be primitive
        //1 is alsways a factor, so we check it now:
        //if(!isPrimitiveCore(1,fx)) return false;//never going to return false as x is always smaller than fx except in extreme cases fx=1 or fx=x...
        BigInteger p = BigInteger.valueOf(2);//computing in Z2
        int m = fx.length-1;
        BigInteger product = p.pow(m).subtract(BigInteger.ONE);
        Map<BigInteger,Integer> factors = PollardRho.factorPowerOfTwoMinusOne(product);
        for(BigInteger ri:factors.keySet()){
            BigInteger exp = product.divide(ri);
            if(!isPrimitiveCore(exp,fx)) return false;
        }
        return true;
    }
    static boolean isPrimitiveCore(BigInteger exp,boolean[] fx){
        boolean[] lx = Z2.modExp(Z2.X, exp, fx);
        if(Z2.equal(lx,Z2.ONE)) return false;
        return true;
    }
    static boolean isPrimitiveCore(int expInt,boolean[] fx){
        assert(expInt>=0);
        boolean[] x_exp = new boolean[expInt+1];
        x_exp[expInt]=true;
        boolean[] lx = Z2.mod(x_exp, fx);
        if(Z2.equal(lx,Z2.ONE)) return false;
        return true;
    }
    public static boolean[] modExp(boolean[] gx,long exp, boolean[] moduli){
        return modExp(gx, BigInteger.valueOf(exp), moduli);
    }

    /**
     * Algorithm 2.227 Repeated square and multiply for exponentiation in Fp^m
     * @param gx
     * @param exp positive integer <= p^m - 1
     * @param moduli irreducible polynomial of degree m over Z2
     * @return (gx^exp) mod moduli in Z2
     */
    public static boolean[] modExp(boolean[] gx,BigInteger exp, boolean[] moduli){
        assert(exp.signum()>=0);
        BigInteger k = exp;
        boolean[] fx = moduli;
        boolean[] sx = Z2.ONE.clone();
        if(k==BigInteger.ZERO) return sx;
        boolean [] Gx = gx;
        if(k.testBit(0)) {
            sx = gx.clone();
            if(k.bitLength()==1){
                return Z2.mod(sx,fx);
            }
        }
        for(int i=1;i<k.bitLength();i++){
            //Gx = Z2.mod(Gx,fx);//was added for speed (reduce the width of the square and therefore speedup the next mod operation) --> does not speed up :-S
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
        return booleansToInt(in,0,in.length);
    }
    public static int booleansToInt(boolean[] in, int offset, int length){
        int out = 0;
        if(length>32) throw new RuntimeException("boolean array to big to fit in 32 bits integer");
        for(int i = 0;i<length;i++){
            if(i+offset==in.length) break;
            if(in[i+offset]) out |= 1<<i;
        }
        return out;
    }
    public static byte booleansToByte(boolean[] in){
        int out = 0;
        if(in.length>8) throw new RuntimeException("boolean array to big to fit in 8 bits integer");
        for(int i = 0;i<in.length;i++){
            if(in[i]) out |= 1<<i;
        }
        return (byte)out;
    }
    public static byte binaryStringToByte(String binaryString) {
        return booleansToByte(toBooleans(binaryString));
    }
    public static boolean[] toBooleans(BigInteger in) {
        return toBooleans(in, in.bitLength());
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
    public static boolean[] toBooleans(long in) {
        return toBooleans(in, bitWidth(in));
    }
    public static boolean[][] toRowMatrix(int in, int rowWidth){
        boolean [][] out = new boolean[1][rowWidth];
        boolean [] bits = toBooleans(in,rowWidth);
        for(int i=0;i<rowWidth;i++) out[0][i] = bits[i];
        return out;
    }
    public static boolean[] toBooleans(long in, int outputLength) {
        boolean[] out = new boolean[outputLength];
        int iMax = Math.min(bitWidth(in),outputLength);
        for(int i=0;i<iMax;i++){
            if((in & (1L<<i)) != 0) out[i] = true;
        }
        return out;
    }

    public static int bitWidth(long in){
        int i;
        if(in<0) return 64;
        for(i=1;i<63;i++){
            if(in<(1L<<i)) return i;
        }
        return i;
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
    public static boolean []complement(boolean []in) {
        return complement(in, 0, in.length);
    }
    public static boolean []complement(boolean []in,int from, int to){
        boolean []out = new boolean[to-from];
        for(int i=0;i<out.length;i++) out[i] = !in[i];
        return out;
    }
    public static boolean[][] complement(boolean[][] in) {
        int rows = in.length;
        int cols = in[0].length;
        boolean [][]out = new boolean[rows][cols];
        for(int r=0;r<rows;r++){
            for(int c=0;c<cols;c++) out[r][c] = !in[r][c];
        }
        return out;
    }
    static boolean []reverse(boolean []in) {
        return reverse(in, 0, in.length);
    }
    static boolean []reverse(boolean []in,int from, int to){
        boolean []out = new boolean[to-from];
        for(int i=0;i<out.length;i++) out[i] = in[to-1-i];
        return out;
    }

    /**
     * change the order of atoms inside words
     * @param in
     * @param atomSize
     * @param wordSize
     * @return the input with the opposite endianness
     */
    public static boolean []switchEndianness(boolean[] in, int atomSize, int wordSize){
        if(in.length%wordSize!=0) throw new RuntimeException("input length must be a multiple of the word size. Got "+in.length+" and "+wordSize);
        if(wordSize%atomSize!=0) throw new RuntimeException("Word size must be a multiple of the atom size. Got "+wordSize+" and "+atomSize);
        boolean[] out = new boolean[in.length];
        int nWords = in.length/wordSize;
        int nAtoms = wordSize/atomSize;
        for(int wordIndex=0;wordIndex<nWords;wordIndex++){
            for(int i=0;i<nAtoms;i++){
                int outAtomIndex = nAtoms-1-i;
                int offset = wordIndex*wordSize+i*atomSize;
                int outOffset = wordIndex*wordSize+outAtomIndex*atomSize;
                System.arraycopy(in,offset,out,outOffset,atomSize);
            }
        }
        return out;
    }
    public static byte []switchEndianness(byte[] in, int atomSize, int wordSize) {
        boolean[] bits = toBooleans(in);
        bits = switchEndianness(bits,atomSize,wordSize);
        byte[] out = booleansToBytes(bits);
        return out;
    }
    public static String toBinaryString(boolean in){
        return in ? "1" : "0";
    }
    public static int toInt(boolean in) {return in ? 1 : 0;}
    static Random rng = new Random(System.nanoTime());
    public static boolean[] randomBooleans(int len) {
        return randomBooleans(len,rng.nextLong());
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
        return out.substring(0, out.length() - 1);
    }

    public static boolean[][] reducedRowEchelonMatrix(boolean[][]in) {
        return reducedRowEchelonMatrix(in, in[0].length);
    }

    /**
     * reoder the columns of a matrix to put identity matrix on the left
     * @param in
     * @return
     */
    public static boolean[][] reorderRowsToStandardForm(boolean[][]in) {
        int rows = in.length;
        int cols = in[0].length;
        boolean [][]out = in.clone();
        for(int r=0;r<rows;r++){//iterate for each row
            //search the matching column
            int c;
            for(c = r;c<cols;c++){
                int i;
                for(i=0;i<rows;i++){
                    if(out[i][c]^(i==r)) break;//not the column we want
                }
                if(i==rows){//found the right column
                    if(r!=c) swapColumns(out,r,c);
                    break;
                }
            }
            if(c==cols) throw new RuntimeException("Could not find an identity matrix by reordering columns in\n"+Z2.toBinaryString(in));
        }
        return out;
    }

    /*
    function ToReducedRowEchelonForm(Matrix M) is
    lead := 0
    rowCount := the number of rows in M
    columnCount := the number of columns in M
    for 0 ≤ r < rowCount do
        if columnCount ≤ lead then
            stop
        end if
        i = r
        while M[i, lead] = 0 do
            i = i + 1
            if rowCount = i then
                i = r
                lead = lead + 1
                if columnCount = lead then
                    stop
                end if
            end if
        end while
        Swap rows i and r
        If M[r, lead] is not 0 divide row r by M[r, lead]
        for 0 ≤ i < rowCount do
            if i ≠ r do
                Subtract M[i, lead] multiplied by row r from row i
            end if
        end for
        lead = lead + 1
    end for
end function
     */
    public static boolean[][] reducedRowEchelonMatrix(boolean[][]in, int maxColumn){
        int lead=0;
        final int rowCount = in.length;
        final int columnCount = maxColumn;
        boolean[][]out = in.clone();
        for(int r=0;r<rowCount;r++){
            //System.out.println("r="+r+", lead="+lead);
            //System.out.println(Z2.toBinaryString(out)+"\n");
            if(columnCount<=lead) return out;
            int i=r;
            while(!out[i][lead]){
                i++;
                if(rowCount==i) {
                    i = r;
                    lead++;
                    if(columnCount==lead) return out;
                }
            }
            Z2.swapRows(out,i,r);
            //if out[r][lead]=0 we do nothing, if =1, we divide row r by 1, so we do nothing as well actually :-)
            for(i=0;i<rowCount;i++){
                if((i!=r) && out[i][lead]) selfAdd(out[i],out[r],0);
            }
            lead++;
        }
        return out;
    }
    public static void swapRows(boolean[][]in,int i,int j){
        boolean[] tmp = in[i];
        in[i] = in[j];
        in[j] = tmp;
    }
    public static void swapColumns(boolean[][]in,int i,int j){
        boolean[] tmp = new boolean[in.length];
        for(int r=0;r<in.length;r++) tmp[r] = in[r][i];
        for(int r=0;r<in.length;r++) in[r][i] = in[r][j];
        for(int r=0;r<in.length;r++) in[r][j] = tmp[r];
    }

    public static boolean[][] columnEchelonMatrix(boolean[][] in) {
        return Z2.columnEchelonMatrix(in, in[0].length);
    }
    public static boolean[][] columnEchelonMatrix(boolean[][] in,int maxRow) {
        boolean[][]out = Z2.transpose(in);
        out = Z2.reducedRowEchelonMatrix(out, maxRow);
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
        return Z2.rowAugment(in, identity);
    }
    public static boolean[][] columnAugmentIdentity(boolean[][] in){
        int nRows = in.length;
        int nColumns = in[0].length;
        if(nRows!=nColumns) throw new RuntimeException("Square matrix expected");
        boolean[][] identity = Z2.identityMatrix(nRows);
        return Z2.columnAugment(in, identity);
    }
    /**
     * kernel basis given as columns
     * @param in
     * @return a matrix with each kernel basis in its column
     */
    public static boolean[][] matrixKernelBasisAsColumns(boolean[][]in){
        int maxRow = in.length;
        boolean[][] tmp = Z2.rowAugmentIdentity(in);
        tmp = Z2.columnEchelonMatrix(tmp, maxRow);
        int colOffset=0;
        for(int i=0;i<maxRow;i++){
            if(firstOneIndex(in[i])==-1) {
                colOffset = i;
                break;
            }
        }
        boolean[][]out = copySubMatrix(tmp, maxRow, colOffset);
        //System.out.println(Z2.toBinaryString(out));
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

        tmp = Z2.reducedRowEchelonMatrix(tmp, maxColumn);
        //System.out.println(Z2.toBinaryString(tmp));

        int rowOffset=0;
        for(int i=0;i<maxColumn;i++){
            if(firstOneIndexInColumn(in, i)==-1) {
                rowOffset = i;
                break;
            }
        }
        boolean[][]out = copySubMatrix(tmp,rowOffset,maxColumn);
        //System.out.println(Z2.toBinaryString(out));
        for(int i=0;i<out.length;i++){
            out[i] = Z2.reverse(out[i]);
        }
        return out;
    }
    static boolean[] mul(Collection<boolean[]> in){
        boolean[] out=Z2.ONE;
        for(boolean[] term:in){
            out = Z2.mul(out, term);
        }
        return out;
    }
    public static String[] toPolynomials(Collection<boolean[]> in){
        String[] out=new String[in.size()];
        int i=0;
        for(boolean[] term:in){
            out[i++] = Z2.toPolynomial(term);
        }
        return out;
    }
    public static String[] toPolynomials(boolean[][] in){
        String[] out=new String[in.length];
        int i=0;
        for(boolean[] term:in){
            out[i++] = Z2.toPolynomial(term);
        }
        return out;
    }
    static boolean[] mulBi(Collection<BigInteger> in){
        boolean[] out=Z2.ONE;
        for(BigInteger term:in){
            out = Z2.mul(out, Z2.toBooleans(term));
        }
        return out;
    }
    public static String bigIntegerToPolynomial(BigInteger in){
        String out = Z2.toPolynomial(Z2.toBooleans(in));
        return out;
    }
    public static String[] bigIntegersToPolynomials(Collection<BigInteger> in){
        String[] out=new String[in.size()];
        int i=0;
        for(BigInteger term:in){
            out[i++] = Z2.toPolynomial(Z2.toBooleans(term));
        }
        return out;
    }
    public static String join(String[] terms,String separator){
        String out="";
        if(terms.length==0) return "";
        for(int i=0;i<terms.length;i++){
            out+=terms[i]+separator;
        }
        return out.substring(0,out.length()-separator.length());
    }

    public static boolean[] derivative(boolean[] in){
        int len = in.length-1;
        if(len<=0) return Z2.ZERO.clone();
        boolean[] out = new boolean[len];
        for(int i=0;i<len;i++){
            out[i]=in[i+1] & toBoolean((i+1)%2);
        }
        out=minimumLengthCopy(out);
        return out;
    }
    public static Map<BigInteger,Integer> booleansListToCountMap(List<boolean[]> in) {
        List<BigInteger> bi = new ArrayList<BigInteger>();
        for (boolean[] f : in) {
            BigInteger fBi = Z2.booleansToBigInteger(f);
            bi.add(fBi);
        }
        return listToCountMap(bi);
    }
    public static Map<BigInteger,Integer> listToCountMap(List<BigInteger> in){
        Map<BigInteger,Integer> factorsMap = new HashMap<BigInteger,Integer>();
        for(BigInteger fBi: in){
            int power = 1;
            if(factorsMap.containsKey(fBi)) power += factorsMap.get(fBi);
            factorsMap.put(fBi, power);
        }
        return factorsMap;
    }
    public static Map<BigInteger,Integer> factorPolynomialMap(boolean[] in){
        List<boolean[]>factors = Z2.factorPolynomialList(in);
        Map<BigInteger,Integer> factorsMap = new HashMap<BigInteger,Integer>();
        for(boolean[] f: factors){
            BigInteger fBi = Z2.booleansToBigInteger(f);
            int power = 1;
            if(factorsMap.containsKey(fBi)) power += factorsMap.get(fBi);
            factorsMap.put(fBi,power);
        }
        return factorsMap;
    }
    public static boolean[][] factorPolynomial(boolean[] in) {
        List<boolean[]> F=null;
        //try {
            F = factorPolynomialList(in);
        //}catch(Throwable e){
        //    throw new RuntimeException(e.getCause()+ " happened during factorization of polynomial "+Z2.toPolynomial(in)+"\n"+e.toString());
        //}
        boolean[][] out = new boolean[F.size()][];
        F.toArray(out);
        //System.out.println("factors");
        //System.out.println(Z2.toBinaryString(out));
        return out;
    }
    public static List<boolean[]> factorPolynomialList(boolean[] in) {
        List<boolean[]> out = new ArrayList<boolean[]>();
        if(Z2.isOne(in)) {
            out.add(Z2.ONE.clone());
            return out;
        }
        List<boolean[]> squareFreeFactors = factorToSquareFreePolynomialList(in);

        for(boolean[] factor:squareFreeFactors){
            List<boolean[]> factors = Z2.factorSquareFreePolynomialList(factor);
            out.addAll(factors);
        }

        Collections.sort(out, comparator);
        return out;
    }

    public static boolean[][] factorToSquareFreePolynomial(boolean[] in) {
        if(Z2.isOne(in)) {
            boolean[][]out = new boolean[1][];
            out[0]=Z2.ONE.clone();
            return out;
        }
        List<boolean[]> F=factorToSquareFreePolynomialList(in);
        boolean[][] out = new boolean[F.size()][];
        F.toArray(out);
        return out;
    }
    public static List<boolean[]> factorToSquareFreePolynomialList(boolean[] in) {
        ArrayList<boolean[]> F = new ArrayList<boolean[]>();
        int i=1;
        boolean[] f=in;
        boolean[] fd = Z2.derivative(f);
        if(Z2.isZero(fd)){
            f=Z2.squareRoot(f);
            List<boolean[]> sqrtFactors = factorToSquareFreePolynomialList(f);
            F.addAll(sqrtFactors);
            F.addAll(sqrtFactors);
        } else {
            boolean[]gx=Z2.gcd(f,fd);
            boolean[]hx=Z2.div(f,gx);
            while(!Z2.isOne(hx)){
                boolean[]hb=Z2.gcd(hx,gx);
                boolean[]lx=Z2.div(hx,hb);
                if(!Z2.isOne(lx))
                    for(int j=0;j<i;j++) F.add(lx);
                i++;
                hx=hb;
                gx=Z2.div(gx,hb);
            }
            if(!Z2.isOne(gx)){
                gx=Z2.squareRoot(gx);
                List<boolean[]> sqrtFactors = factorToSquareFreePolynomialList(gx);
                F.addAll(sqrtFactors);
                F.addAll(sqrtFactors);
            }
        }
        Collections.sort(F, comparator);
        return F;
    }

    public static boolean[] square(boolean[]in) {
        /*int len = in.length;

        boolean[] out = new boolean[in.length*2];
        for(int i=0;i<in.length;i++){
            out[2*i] = in[i];
        }*/
        //TODO

        return Z2.mul(in,in);
    }
    public static boolean[] squareRoot(boolean[]in){
        assert(Z2.isZero(Z2.derivative(in)));
        boolean[] out = new boolean[(in.length+1)/2];
        for(int i=0;i<in.length;i+=2){
            out[i/2] = in[i];
        }
        return out;
    }

    public static boolean[][] factorSquareFreePolynomial(boolean[] in) {
        List<boolean[]> F=factorSquareFreePolynomialList(in);
        boolean[][] out = new boolean[F.size()][];
        F.toArray(out);
        //System.out.println("factors");
        //System.out.println(Z2.toBinaryString(out));
        return out;
    }

        /**
         * Algorithm 3.111: Berlekamp's Q-matrix algorithm
         * @param in a square free monic polynomial of degree n in Fq[x]
         * @return the factorization of in into monic irreducible polynomials
         */
    public static List<boolean[]> factorSquareFreePolynomialList(boolean[] in){
        final int q=2;
        int n = in.length-1;
        boolean[][]Q = new boolean[n][];
        for(int i=0;i<n;i++) Q[i] = new boolean[n];
        for(int i=0;i<n;i++){
            boolean[] p=Z2.modExp(Z2.X,i*q,in);
            for(int j=0;j<p.length;j++) Q[n-1-j][n-1-i] = p[j];
        }
        //System.out.println("Q matrix");
        //System.out.println(Z2.toBinaryString(Q));
        boolean[][]qMinusI = Z2.xor(Q,Z2.identityMatrix(n));
        boolean[][]qMinusI_echelon = Z2.reducedRowEchelonMatrix(qMinusI);
        //System.out.println("Q minus I matrix in row echelon form:");
        //System.out.println(Z2.toBinaryString(qMinusI_echelon));
        boolean[][]basis = matrixKernelBasis(qMinusI_echelon);
        //System.out.println("basis");
        //System.out.println(Z2.toBinaryString(basis));
        Set<BigInteger> F = Z2.factorWithBasis(in,basis);
        ArrayList<boolean[]> tmpList = new ArrayList<boolean[]>();
        for(BigInteger f:F){
            tmpList.add(Z2.toBooleans(f));
        }
        Collections.sort(tmpList,comparator);
        return tmpList;
    }
    //static String dbgLevel="";
    static Set<BigInteger> factorWithBasis(boolean[] hx,boolean[][]basis){
        //dbgLevel+="\t";
        //use BigInteger instead of boolean[] because boolean[] are added to the set even if they contain the same value (hash=reference)
        HashSet<BigInteger> out = new HashSet<BigInteger>();
        boolean replaced=false;
        for(int i=0;i<basis.length;i++){
            //System.out.println(dbgLevel+"vi: "+Z2.toPolynomial(basis[i]));
            if(Z2.isOne(basis[i])) continue;
            if(Z2.msbIndex(hx)>1) { //deg hx > 1
                for (int alpha = 0; alpha < 2; alpha++) {
                    boolean[] viMinusAlpha = Z2.sub(basis[i], Z2.toBooleans(alpha));
                    boolean[] gcd = Z2.gcd(hx, viMinusAlpha);
                    if ((Z2.msbIndex(gcd) >= 1) && !Z2.equalValue(gcd,hx)){
                        //System.out.println(dbgLevel+Z2.toPolynomial(hx)+" factored by "+Z2.toPolynomial(gcd)+" (alpha="+alpha+")");
                        replaced = true;
                        out.addAll(factorWithBasis(gcd,basis));
                    }
                }
            }
        }
        if(!replaced) {
            //System.out.println(dbgLevel+Z2.toPolynomial(hx)+" kept");
            out.add(Z2.booleansToBigInteger(hx));
        }
        boolean[] product = Z2.mulBi(out);
        //System.out.println(dbgLevel+"product="+Z2.toPolynomial(product)+", factors: "+Z2.join(Z2.bigIntegersToPolynomials(out),","));dbgLevel=dbgLevel.substring(0,dbgLevel.length()-1);
        assert(Z2.equalValue(product,hx));
        return out;
    }

    public static boolean[][] matrixMul(boolean[] u, boolean[][] g) {
        boolean [][] out = new boolean[u.length][g[0].length];
        //if(g.length!=out.length) throw new RuntimeException("Cannot multiply matrices due to dimensions mismatch");
        for(int i=0;i<u.length;i++){
            for(int j=0;j<g.length;j++){
                out[i][j]=u[i]&g[j][i];
            }
        }
        return out;
    }

    public static boolean[][] matrixMul(boolean[][] u, boolean[][] g) {
        boolean [][] out = new boolean[u.length][g[0].length];
        if(g.length!=u[0].length) throw new RuntimeException("Cannot multiply matrices due to dimensions mismatch");
        for(int i=0;i<u.length;i++){
            for(int j=0;j<g[0].length;j++){
                for(int k=0;k<g.length;k++) {
                    out[i][j] ^= u[i][k] & g[k][j];
                }
            }
        }
        return out;
    }

    static class Z2Comparator implements Comparator<boolean[]>{
        @Override
        public int compare(boolean[] a, boolean[] b) {
            if(Z2.equalValue(a,b)) return 0;
            if(Z2.isGreater(a,b)) return 1;
            return -1;
        }
    }
    static Z2Comparator comparator = new Z2Comparator();

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
