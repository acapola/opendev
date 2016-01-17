package uk.co.nimp.app;

import uk.co.nimp.Nlfsr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Created by seb on 1/17/16.
 */
public class NlfsrExplorer {

    public static void dumpToCsvFile(File dest, Map<Integer,List<Nlfsr>> map) throws FileNotFoundException {
        Predicate<Nlfsr> acceptAll = new Predicate<Nlfsr>() {
            @Override
            public boolean test(Nlfsr nlfsr) {
                return true;
            }
        };
        dumpToCsvFile(dest,map,acceptAll);
    }
    public static void dumpToCsvFile(File dest, Map<Integer,List<Nlfsr>> map, final Predicate<Nlfsr> filter) throws FileNotFoundException {
        PrintStream ps = new PrintStream(new FileOutputStream(dest));
        for(Integer len:map.keySet()){
            ps.printf("%06d,", len);
            //for(Nlfsr n:map.get(len)) if(filter(n)) ps.print(n.getTapsString()+",");
            map.get(len).stream().filter(filter).forEach(n -> ps.print(n.getTapsString()+","));
            ps.println();
        }
        ps.close();
    }

    public static void main(String args[]) throws FileNotFoundException {
        for(int stateWidth = 7;stateWidth<20;stateWidth++) {
            int minLen = (1 << (stateWidth - 2)) - 1;
            Map<Integer, List<Nlfsr>> map = Nlfsr.explore(stateWidth, minLen);
            dumpToCsvFile(new File("NlfsrDb" + stateWidth + ".csv"), map);
            Predicate<Nlfsr> onlyNonLinear = new Predicate<Nlfsr>() {
                @Override
                public boolean test(Nlfsr nlfsr) {
                    return !nlfsr.isLinear();
                }
            };
            Predicate<Nlfsr> onlyCommonCells = new Predicate<Nlfsr>() {
                @Override
                public boolean test(Nlfsr nlfsr) {
                    return nlfsr.isCommonCell();
                }
            };
            dumpToCsvFile(new File("NlfsrDb" + stateWidth + "_NL.csv"), map, onlyNonLinear);
            dumpToCsvFile(new File("NlfsrDb" + stateWidth + "_commonCells.csv"), map, onlyCommonCells);
            //show frequencies
            for (Integer len : map.keySet()) {
                System.out.println(len + "; " + map.get(len).size());
            }
            for (int i = minLen; i < (1 << stateWidth); i++) {
                if (!map.containsKey(i)) {
                    System.out.println("No Nlfsr of width " + stateWidth + " with sequence of length " + i);
                }
            }
            List<Nlfsr> maxLength = map.get((1 << stateWidth) - 1);
            int nlMaxlenCnt = 0;
            for (Nlfsr n : maxLength) {
                if (!n.isLinear()) {
                    //System.out.println("max length NLFSR found: "+n);
                    nlMaxlenCnt++;
                }
            }
            System.out.println(nlMaxlenCnt + " max length NLFSR found");
        }
    }
}
