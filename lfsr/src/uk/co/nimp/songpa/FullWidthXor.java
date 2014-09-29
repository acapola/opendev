package uk.co.nimp.songpa;

import uk.co.nimp.Z2;

/**
 * Created by seb on 9/28/14.
 */
public class FullWidthXor implements X {
    final int keySize;

    public FullWidthXor(int keySize) {
        this.keySize = keySize;
    }

    @Override
    public boolean[] apply(boolean[] in, boolean[] key) {
        return Z2.xor(in, key);
    }

    @Override
    public int getKeySize() {
        return keySize;
    }
}
