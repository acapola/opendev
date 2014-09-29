package uk.co.nimp.songpa;

import uk.co.nimp.Z2;

import java.util.Arrays;

/**
 * Created by seb on 9/27/14.
 */
public class KeyedPermutation implements P {
    final int width;//the width of the input / output
    final int unitWidth;//the granularity of the permutation: 1 -> shuffle bits, 8 -> shuffle bytes...
    final int nUnits;
    final int[] indexesWidth;
    final int keySize;
    public KeyedPermutation(int width, int unitWidth) {
        this.unitWidth = unitWidth;
        this.width = width;
        nUnits = width/unitWidth;
        if(nUnits*unitWidth != width) throw new RuntimeException();
        indexesWidth = new int[nUnits];
        int remaining=nUnits-1;
        int kSize=0;
        for(int i = 0;i<nUnits-1;i++){
            indexesWidth[i] = Z2.bitWidth(remaining--);
            kSize+=indexesWidth[i];
        }
        indexesWidth[nUnits-1] = 0;//no choice to do for the the last unit
        keySize = kSize;
    }
    static public KeyedPermutation create(int width){
        return new KeyedPermutation(width,1);
    }
    static public KeyedPermutation create(int width, int unitWidth){
        return new KeyedPermutation(width,unitWidth);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getUnitWidth() {
        return unitWidth;
    }

    @Override
    public int getnUnits() {
        return nUnits;
    }

    @Override
    public int getKeySize() {
        return keySize;
    }

    static public int getNthZeroIndex(boolean[] in,int n){
        int zeroIndex=0;
        for(int i = 0;i<in.length;i++){
            if(false==in[i]){
                if(zeroIndex==n) return i;
                zeroIndex++;
            }
        }
        return -1;
    }
    @Override
    public boolean [] apply(boolean[] in, boolean[] key){
        boolean []out=new boolean[width];
        /*boolean []outDone=new boolean[nUnits];
        int pos=0;
        int remaining=nUnits;
        for(int i=0;i<nUnits;i++){
            int index = Z2.booleansToInt(key,pos,indexesWidth[i]) %remaining;
            //System.out.println(index);
            remaining--;
            pos+=indexesWidth[i];
            int outPos = getNthZeroIndex(outDone,index);
            outDone[outPos]=true;
            System.arraycopy(in,i*unitWidth,out,outPos*unitWidth,unitWidth);
        }*/
        int[] map = computeMap(key);
        for(int i=0;i<nUnits;i++){
            System.arraycopy(in,map[i]*unitWidth,out,i*unitWidth,unitWidth);
        }
        return out;
    }
    int[] computeMap(boolean[] key){
        boolean []outDone=new boolean[nUnits];
        int[] map = new int[nUnits];
        int pos=0;
        int remaining=nUnits;
        for(int i=0;i<nUnits;i++){
            int index = Z2.booleansToInt(key,pos,indexesWidth[i]) %remaining;
            remaining--;
            pos+=indexesWidth[i];
            int outPos = getNthZeroIndex(outDone,index);
            outDone[outPos]=true;
            map[outPos]=i;
        }
        return map;
    }
    @Override
    public boolean [] unapply(boolean[] in, boolean[] key){
        boolean []out=new boolean[width];
        /*
        boolean []outDone=new boolean[nUnits];
        int[] map = new int[nUnits];
        int pos=0;
        int remaining=nUnits;
        for(int i=0;i<nUnits;i++){
            int index = Z2.booleansToInt(key,pos,indexesWidth[i]) %remaining;
            //System.out.println(index);
            remaining--;
            pos+=indexesWidth[i];
            int outPos = getNthZeroIndex(outDone,index);
            outDone[outPos]=true;
            map[outPos]=i;
        }*/
        int[] map = computeMap(key);
        for(int i=0;i<nUnits;i++){
            System.arraycopy(in,i*unitWidth,out,map[i]*unitWidth,unitWidth);
        }
        return out;
    }
}
