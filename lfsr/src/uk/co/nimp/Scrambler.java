package uk.co.nimp;

/**
 * Created by seb on 6/29/14.
 */
public interface Scrambler {
    public int getInputRange();
    public int getInputWidth();
    public int getKeyWidth();
    public boolean[] scramble(boolean[] in, boolean[] key);
}
