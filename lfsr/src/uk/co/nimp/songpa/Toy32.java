package uk.co.nimp.songpa;

/**
 * Created by seb on 9/28/14.
 */
public class Toy32 extends Cipher {
    public Toy32(int outterRounds, int innerRounds){
        this.innerRounds=innerRounds;
        this.outterRounds=outterRounds;
        this.width=32;
        this.keySize=width;
        this.p = new KeyedPermutation(width,1);
        this.m = new AesMixColumns();
        this.x = new FullWidthXor(width);
        this.s = new AesSubBytes();
        this.k = new AlternatingPrng(p.getKeySize()+x.getKeySize());
    }
}
