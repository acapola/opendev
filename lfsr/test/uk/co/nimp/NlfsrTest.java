package uk.co.nimp;

import org.junit.Test;

import java.math.BigInteger;
import java.util.*;


public class NlfsrTest {


    @Test
    public void testFromTaps() throws Exception {
        Nlfsr n = Nlfsr.fromTaps("~!&^");
        System.out.println(n.describe(true,true));
        //Nlfsr.explore(8,3);
    }

    static Nlfsr buildMaxLengthEven1(int stateWidth){//fail for 8 (~OA > ... > ^ ,  >OA > > >!^ , ~OA > > >!^ as well)
        Nlfsr.Operator[] taps = new Nlfsr.Operator[stateWidth];
        //build following the pattern >OA > ... > ^
        taps[0]= Nlfsr.Operator.SHIFT;
        taps[1]= Nlfsr.Operator.OA;
        for(int i=2;i<taps.length-1;i++) taps[i] = Nlfsr.Operator.SHIFT;
        taps[stateWidth-1] = Nlfsr.Operator.XOR;
        Nlfsr n = Nlfsr.fromTaps(taps);
        return n;
    }

    static Nlfsr buildMaxLengthEven2(int stateWidth){//fails for 8 (~OA ~ > > ^ , >OA ~ > >!^ , ~OA ~ > >!^ as well)
        Nlfsr.Operator[] taps = new Nlfsr.Operator[stateWidth];
        //build following the pattern  >OA ~ > > ^
        taps[0]= Nlfsr.Operator.SHIFT;
        taps[1]= Nlfsr.Operator.OA;
        taps[2]= Nlfsr.Operator.NOT;
        for(int i=3;i<taps.length-1;i++) taps[i] = Nlfsr.Operator.SHIFT;
        taps[stateWidth-1] = Nlfsr.Operator.XOR;
        Nlfsr n = Nlfsr.fromTaps(taps);
        return n;
    }

    static Nlfsr buildMaxLengthEven3(int stateWidth){//fails for 8 (~OA ~ ~ ~!^ as well)
        Nlfsr.Operator[] taps = new Nlfsr.Operator[stateWidth];
        //build following the pattern    >OA ~ ~ ~!^
        taps[0]= Nlfsr.Operator.SHIFT;
        taps[1]= Nlfsr.Operator.OA;
        for(int i=2;i<taps.length-1;i++) taps[i] = Nlfsr.Operator.NOT;
        taps[stateWidth-1] = Nlfsr.Operator.XNOR;
        Nlfsr n = Nlfsr.fromTaps(taps);
        return n;
    }

    static Nlfsr buildMaxLengthEven4(int stateWidth){//fails for 8 (~OA > ~ ~!^ as well)
        Nlfsr.Operator[] taps = new Nlfsr.Operator[stateWidth];
        //build following the pattern      >OA > ~ ~!^
        taps[0]= Nlfsr.Operator.SHIFT;
        taps[1]= Nlfsr.Operator.OA;
        taps[2]= Nlfsr.Operator.SHIFT;
        for(int i=3;i<taps.length-1;i++) taps[i] = Nlfsr.Operator.NOT;
        taps[stateWidth-1] = Nlfsr.Operator.XNOR;
        Nlfsr n = Nlfsr.fromTaps(taps);
        return n;
    }

    static Nlfsr buildMaxLengthEven5(int stateWidth){//fails for 8 (~OA!^!^ ~!^ as well)
        Nlfsr.Operator[] taps = new Nlfsr.Operator[stateWidth];
        //build following the pattern        >OA!^!^ ~!^
        taps[0]= Nlfsr.Operator.SHIFT;
        taps[1]= Nlfsr.Operator.OA;
        for(int i=2;i<taps.length-2;i++) taps[i] = Nlfsr.Operator.XNOR;
        taps[stateWidth-2]= Nlfsr.Operator.NOT;
        taps[stateWidth-1] = Nlfsr.Operator.XNOR;
        Nlfsr n = Nlfsr.fromTaps(taps);
        return n;
    }

    static Nlfsr buildMaxLengthEven(int stateWidth){//fails for 8
        Nlfsr.Operator[] taps = new Nlfsr.Operator[stateWidth];
        //build following the pattern        ~OA!^!^ ~!^
        taps[0]= Nlfsr.Operator.NOT;
        taps[1]= Nlfsr.Operator.OA;
        for(int i=2;i<taps.length-2;i++) taps[i] = Nlfsr.Operator.XNOR;
        taps[stateWidth-2]= Nlfsr.Operator.NOT;
        taps[stateWidth-1] = Nlfsr.Operator.XNOR;
        Nlfsr n = Nlfsr.fromTaps(taps);
        return n;
    }

    @Test
    public void testEvenMaxLength() throws Exception {
        for(int i=4;i<20;i+=2){
            Nlfsr n = buildMaxLengthEven(i);
            System.out.println(i+", "+n);
            Map<boolean[],boolean[][]> sequences = n.sequences(true);
            int maxLength = (1<<i)-1;
            assert(!(sequences.keySet().stream().noneMatch( s -> s.length==maxLength)));

        }
    }


    static Nlfsr buildMaxLengthOdd(int stateWidth){//fails for 9
        Nlfsr.Operator[] taps = new Nlfsr.Operator[stateWidth];
        //build following the pattern        ~OA!^!^!^!^!^
        taps[0]= Nlfsr.Operator.NOT;
        taps[1]= Nlfsr.Operator.OA;
        for(int i=2;i<taps.length;i++) taps[i] = Nlfsr.Operator.XNOR;
        Nlfsr n = Nlfsr.fromTaps(taps);
        return n;
    }

    @Test
    public void testOddMaxLength() throws Exception {
        for(int i=5;i<20;i+=2){
            Nlfsr n = buildMaxLengthOdd(i);
            System.out.println(i+", "+n);
            Map<boolean[],boolean[][]> sequences = n.sequences(true);
            int maxLength = (1<<i)-1;
            assert(!(sequences.keySet().stream().noneMatch( s -> s.length==maxLength)));

        }
    }
}
