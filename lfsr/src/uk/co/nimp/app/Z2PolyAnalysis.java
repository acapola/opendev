package uk.co.nimp.app;

import uk.co.nimp.Z2;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Map;

import static uk.co.nimp.app.MainHelpers.*;
import static uk.co.nimp.app.MainHelpers.pauseProg;

/**
 * Created by seb on 8/31/14.
 */
public class Z2PolyAnalysis {
    static String helpMessage(){
        String msg="LFSR tool help\n";
        msg+="command line: java -cp z2.jar uk.co.nimp.app.Z2PolyAnalysis [arguments...]\n";
        msg+="Arguments:\n";
        msg+=argDescription(ARG_HELP,null,"Display this help and exit");
        msg+=argDescription(ARG_PROFILING,null,"Ask for user interaction before and after the processing.");

        msg+="\n";
        msg+="Behavior:\n";
        msg+="Compute the factorization and the order of X of a polynomial given as argument.\n";
        msg+="\n"+credits;
        //msg+="\n0123456789abcdefghij0123456789abcdefghij0123456789abcdefghij0123456789abcdefghijtt";
        msg = limitLineLength(msg,lineLengthLimit);
        return msg;
    }

    static void runDemo(String []args,String description,int index) {
        System.out.println("Run "+index+" "+description);
        try {
            main(args);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("---------------------------------------------------");
    }
    public static void demo() throws IOException {
        int i=0;
        System.out.println("---------------------------------------------------");
        runDemo(new String[]{"x2+1"}, "Analyze a polynomial in Z2", i++);
        runDemo(new String[]{"x4+x2+1"}, "Analyze a polynomial in Z2", i++);
        runDemo(new String[]{"x123+x2+1"}, "Analyze a polynomial in Z2", i++);


        //runDemo(new String[]{ARG_SEQ_HEXSTR,"60 C0 83 19"},"debug",i++);
        //runDemo(new String[]{ARG_LFSR_POLY,Z2.toPolynomial(Z2.pow(Z2.polynomialToBooleans("1+x+x3"),2)),ARG_DISP_SEQ_STATES},"debug",i++);
        //runDemo(new String[]{ARG_LFSR_POLY,Z2.toPolynomial(Z2.pow(Z2.polynomialToBooleans("1+x+x2+x3+x4"),6)),ARG_DISP_SEQ_STATES},"debug",i++);
        //runDemo(new String[]{ARG_LFSR_POLY,Z2.toPolynomial(Z2.pow(Z2.polynomialToBooleans("1+x+x2+x3+x4"),7)),ARG_DISP_SEQ_STATES},"debug",i++);
        //runDemo(new String[]{ARG_LFSR_POLY,Z2.toPolynomial(Z2.mul(Z2.pow(Z2.polynomialToBooleans("1+x+x3"),2),Z2.polynomialToBooleans("1+x"))),ARG_DISP_SEQ_STATES},"debug",i++);
        //runDemo(new String[]{ARG_LFSR_POLY,Z2.toPolynomial(Z2.mul(Z2.pow(Z2.polynomialToBooleans("1+x"),2),Z2.polynomialToBooleans("1+x+x3"))),ARG_DISP_SEQ_STATES},"debug",i++);

    }

    public static void main(String[] args) throws IOException {
        int len=-1;
        if(args.length==0) {
            System.out.println("No argument found, launch demo");
            try{
                demo();
            }catch(Exception e){
                e.printStackTrace();
            }
            System.out.println(helpMessage());
            System.exit(1);
        }
        System.out.println("Arguments:");
        for(int i = 0;i<args.length;i++){
            System.out.println(i+tab+args[i]);
        }
        boolean profiling=false;
        boolean factorize = true;
        boolean orderOfX = true;
        ArrayList<boolean[]> polys = new ArrayList<boolean[]>();
        for(int i=0;i<args.length;i++) {
            if (args[i].equals(ARG_HELP)) {
                System.out.println(helpMessage());
                System.exit(0);
            }
            if(args[i].equals(ARG_PROFILING)){
                profiling = true;
                continue;
            }
            //assume the argument is a polynomial
            try {
                boolean[] p = Z2.polynomialToBooleans(args[i]);
                polys.add(p);
            }catch (Throwable e) {
                System.out.println("Unknown argument found: " + args[i]);
                System.out.println(helpMessage());
                System.exit(1);
            }
        }
        if(profiling){
            pauseProg("Profiling: hit enter to start the processing");
        }
        if(factorize){
            System.out.println("Factors of "+Z2.toPolynomial(polys.get(0)));
            Map<BigInteger,Integer> factors = Z2.factorPolynomialMap(polys.get(0));
            for(BigInteger f:factors.keySet()){
                int power = factors.get(f);
                if(power>1) System.out.println("("+Z2.bigIntegerToPolynomial(f)+") ^ "+power);
                else System.out.println(" "+Z2.bigIntegerToPolynomial(f));
            }
        }
        if(orderOfX){
            System.out.println(Z2.orderOfX(polys.get(0)));
        }
        if(profiling){
            pauseProg("Profiling: hit enter to exit");
        }
    }
}
