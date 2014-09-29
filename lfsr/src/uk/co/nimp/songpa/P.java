package uk.co.nimp.songpa;

/**
 * Created by seb on 9/28/14.
 */
public interface P {
    int getWidth();

    int getUnitWidth();

    int getnUnits();

    int getKeySize();

    boolean [] apply(boolean[] in, boolean[] key);
    boolean [] unapply(boolean[] in, boolean[] key);
}
