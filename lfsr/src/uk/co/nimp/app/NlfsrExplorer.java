package uk.co.nimp.app;

import uk.co.nimp.Nlfsr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
            ps.printf("len,%06d,", len);
            map.get(len).stream().filter(filter).forEach(n -> ps.print(n.getTapsString()+","+n.getStateString()+","));
            ps.println();
        }
        ps.close();
    }

    public static void main(String args[]) throws FileNotFoundException {
        for(int stateWidth = 5;stateWidth<20;stateWidth++) {
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
            /*int nlMaxlenCnt = 0;
            for (Nlfsr n : maxLength) {
                if (!n.isLinear()) {
                    //System.out.println("max length NLFSR found: "+n);
                    nlMaxlenCnt++;
                }
            }*/
            long nlMaxlenCnt=maxLength.stream().filter(onlyNonLinear).count();
            System.out.println(nlMaxlenCnt + " max length NLFSR found");
            List<Nlfsr> nlCommonCells = maxLength.stream().filter(onlyNonLinear).filter(onlyCommonCells).collect(Collectors.toList());
            int nlCommonCellMaxlenCnt=nlCommonCells.size();
            System.out.println(nlCommonCellMaxlenCnt + " max length NLFSR found using only common cells");
            nlCommonCells.forEach(n -> System.out.println(n.getTapsString()));
        }
    }
}
