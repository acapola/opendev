package uk.co.nimp.songpa;

/**
 * Created by seb on 9/28/14.
 */
public interface K {
    public void setSeed(boolean[] key);
    public boolean[] step();
    public int getStepSize();
    public int getSeedWidth();
}
