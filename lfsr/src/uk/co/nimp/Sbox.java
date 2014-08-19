package uk.co.nimp;


/**
 * Created by seb on 8/18/14.
 */
public class Sbox {
    BooleanFunction []booleanFunctions=null;
    final int []outputs;
    final int width;
    final int length;
    int []outputValuesCounts=null;

    protected Sbox(int []outputs, int width){
        if(width>31) throw new RuntimeException("Maximal width is 31, got "+width);
        if(BooleanFunction.isPowerOf2(outputs.length)) throw new RuntimeException("Length must be a power of 2: got "+outputs.length);
        this.outputs = outputs.clone();
        length = outputs.length;
        this.width=width;
    }

    public int[] getOutputValuesCounts() {
        if(null==outputValuesCounts){
            outputValuesCounts = new int[length];
            for(int i=0;i<length;i++) outputValuesCounts[outputs[i]]++;//this limits the supported width to 31
        }
        return outputValuesCounts;
    }

    public int getOutputValueCount(int outputValue){
        if(null==outputValuesCounts){getOutputValuesCounts();}
        return outputValuesCounts[outputValue];
    }



}

