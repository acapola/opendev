package uk.co.nimp.app;

import uk.co.nimp.Gfsr;
import uk.co.nimp.Nlfsr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Created by seb on 1/22/16.
 */
public class MaxLengthNlfsr {
    public static void dumpToCsvFileGfsr(File dest, List<Gfsr> fsrs, final Predicate<Gfsr> filter) throws FileNotFoundException {
        PrintStream ps = new PrintStream(new FileOutputStream(dest));
        fsrs.stream().filter(filter).forEach(n -> ps.println(n.getTapsString()));
        ps.close();
    }
    public static void dumpToCsvFile(File dest, List<Nlfsr> fsrs, final Predicate<Nlfsr> filter) throws FileNotFoundException {
        PrintStream ps = new PrintStream(new FileOutputStream(dest));
        fsrs.stream().filter(filter).forEach(n -> ps.println(n.getTapsString()));
        ps.close();
    }

    public static void mainGfsr(String args[]) throws FileNotFoundException {
        int stateWidth = 5;
        List<Gfsr> out = Gfsr.findMaxLengthNlfsr(stateWidth,10);
        System.out.println(out.size() + " max length NLFSR found for stateWidth = " + stateWidth);
        Predicate<Gfsr> acceptAll = new Predicate<Gfsr>() {
            @Override
            public boolean test(Gfsr fsr) {
                return true;
            }
        };

        //dumpToCsvFile(new File("test.txt"),out,acceptAll);
    }

    public static void main(String args[]) throws FileNotFoundException {
        for(int stateWidth = 4;stateWidth<20;stateWidth++) {
            List<Nlfsr> out = Nlfsr.findMaxLengthNlfsrOAX(stateWidth, Integer.MAX_VALUE);
            System.out.println(out.size() + " max length NLFSR found for stateWidth = " + stateWidth);
            Predicate<Nlfsr> acceptAll = new Predicate<Nlfsr>() {
                @Override
                public boolean test(Nlfsr fsr) {
                    return true;
                }
            };
            //System.out.println(out.get(0).describe(true,true));
            //dumpToCsvFile(new File("MaxLenNlfsrOAXDb" + stateWidth + "_NL.csv"), out, acceptAll);
        }
    }
}
