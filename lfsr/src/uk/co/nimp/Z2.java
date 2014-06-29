package uk.co.nimp;

import java.math.BigInteger;
import java.util.Arrays;

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

    static int msbIndex(boolean[] in){
        int i = in.length-1;
        while(i>0 && !in[i]) i--;
        return i;
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
    public static boolean equal(boolean[] a, boolean[] b){
        if(a.length!=b.length) return false;
        for(int i=0;i<a.length;i++){
            if(a[i]!=b[i]) return false;
        }
        return true;
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
        assert(out[out.length-1]);
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
    static boolean isGreater(boolean[] a, boolean[] b){
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
        boolean[] quotient = new boolean[in.length];
        boolean[] reminder = in.clone();
        int reminderMsbIndex = reminder.length-1;
        int i=0;
        while(isGreaterOrEqual(reminder, divider)){
            quotient[i++]=true;
            int shift = reminderMsbIndex-(divider.length-1);
            selfAdd(reminder, divider, shift);
            while(reminderMsbIndex>divider.length-1 && !reminder[--reminderMsbIndex]){
                i++;
            }
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
     * Algorithm 4.69 Testing a poolynomial for irreducibility
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
        boolean[] out = new boolean[in.bitLength()];
        for(int i=0;i<out.length;i++){
            if(in.testBit(i)) out[i] = true;
        }
        return out;
    }
    public static int bitWidth(int in){
        //return (int) Math.ceil(Math.log(in)/Math.log(2));
        int i;
        for(i=1;i<32;i++){
            if(in<(1<<i)) return i;
        }
        return i;
    }
    public static boolean[] toBooleans(int in) {
        boolean[] out = new boolean[Integer.highestOneBit(in)];
        for(int i=0;i<out.length;i++){
            if((in & (1<<i)) != 0) out[i] = true;
        }
        return out;
    }
    static boolean isIrreducible(BigInteger fx){
        int m = fx.bitLength();
        BigInteger two  = new BigInteger("2");
        BigInteger x = two;
        BigInteger ux = x;
        for(int i=1;i<=m/2;i++){
            ux = ux.modPow(two,fx);
            BigInteger delta = ux.xor(x);//u(x) - x in Z2 is bitwise xor
            BigInteger dx = fx.gcd(delta);
            if(!dx.equals(BigInteger.ONE)) return false;
        }
        return true;
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
}
