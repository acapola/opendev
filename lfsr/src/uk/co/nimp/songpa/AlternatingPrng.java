package uk.co.nimp.songpa;

import uk.co.nimp.Lfsr;
import uk.co.nimp.Z2;

import java.util.Random;

/**
 * Created by seb on 9/28/14.
 */
public class AlternatingPrng implements K {

    final int stepSize;

    Lfsr r1;
    Lfsr r2;
    Lfsr r3;

    public AlternatingPrng(int stepSize) {
        this.stepSize = stepSize;
        r1=Lfsr.fromTapsPositions(new int[]{64,  4,  3, 1, 0});
        r2=Lfsr.fromTapsPositions(new int[]{61, 16, 15, 1, 0});
        r3=Lfsr.fromTapsPositions(new int[]{63,         1, 0});
    }

    @Override
    public void setSeed(boolean[] key) {
        int expFactor = (getSeedWidth()+key.length-1)/key.length;
        int expandedSize = key.length*expFactor;
        boolean[] expandedKey = new boolean[expandedSize];
        for(int i = 0;i<expFactor;i++) {
            System.arraycopy(key,0,expandedKey,i*key.length,key.length);
        }
        boolean[] k1 = new boolean[r1.getWidth()];
        System.arraycopy(expandedKey,0,k1,0,k1.length);
        boolean[] k2 = new boolean[r2.getWidth()];
        System.arraycopy(expandedKey,k1.length,k2,0,k2.length);
        boolean[] k3 = new boolean[r3.getWidth()];
        System.arraycopy(expandedKey,k1.length+k2.length,k3,0,k3.length);
        r1.setState(Z2.xor(k1,Z2.randomBooleans(r1.getWidth(),0)));
        r2.setState(Z2.xor(k2,Z2.randomBooleans(r2.getWidth(),1)));
        r3.setState(Z2.xor(k3,Z2.randomBooleans(r3.getWidth(),2)));
    }

    @Override
    public boolean[] step() {
        boolean[] out = new boolean[stepSize];
        for(int i= 0;i<stepSize;i++){
            if(r1.step()) r2.step();
            else r3.step();
            out[i] = r2.getState()[0] ^ r3.getState()[0];
        }
        return out;
    }

    @Override
    public int getStepSize() {
        return stepSize;
    }

    @Override
    public int getSeedWidth() {
        return r1.getWidth()+r2.getWidth()+r3.getWidth();
    }
}
