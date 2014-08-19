package uk.co.nimp;

/**
 * Created by seb on 8/18/14.
 */
public class BooleanFunction {
    final boolean[] outputs;
    Boolean isBalanced=null;
    Integer numberOf0 = null;
    Integer numberOf1 = null;
    Double probOf0=null;
    Double probOf1=null;

    private BooleanFunction(boolean[] outputs){
        if(isPowerOf2(outputs.length)) throw new RuntimeException("Length must be a power of 2: got "+outputs.length);
        this.outputs = outputs.clone();
    }

    public boolean isBalanced() {
        if(null==isBalanced) {
            isBalanced=getNumberOf1()==outputs.length/2;
        }
        return isBalanced;
    }

    public Integer getNumberOf1() {
        if(null==numberOf1){
            numberOf1=numberOf1(outputs);
            numberOf0=outputs.length-numberOf1;
            probOf1=((double)numberOf1)/outputs.length;
            probOf0=1-probOf1;
        }
        return numberOf1;
    }

    public Integer getNumberOf0() {
        if(null==numberOf1) getNumberOf1();
        return numberOf0;
    }

    public double getProbOf1(){
        if(null==numberOf1) getNumberOf1();
        return probOf1;
    }

    public double getProbOf0(){
        if(null==numberOf1) getNumberOf1();
        return probOf0;
    }

    public static boolean isBalanced(boolean[] f){
        return numberOf1(f)==f.length/2;
    }

    public static int numberOf1(boolean[] f){
        int out=0;
        for(int i=0;i<f.length;i++){
            out+= f[i] ? 1 : 0;
        }
        return out;
    }

    public static int numberOf0(boolean[] f){
        return f.length-numberOf1(f);
    }

    public static boolean isPowerOf2(int length) {
        if(length<2) return false;
        int tmp=length-1;
        int x=tmp & length;
        return 0==x;
    }
}
