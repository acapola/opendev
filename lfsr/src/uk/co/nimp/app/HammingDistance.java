package uk.co.nimp.app;

import uk.co.nimp.ATimeUtilities;
import uk.co.nimp.Z2;

import java.io.File;
import java.io.IOException;
import static uk.co.nimp.app.MainHelpers.*;

/**
 * Created by seb on 7/5/14.
 */
public class HammingDistance {
    static boolean demoMode = false;
    static String helpMessage(){
        String msg="Hamming distance tool help\n";
        msg+="command line: java -cp z2.jar uk.co.nimp.app.HammingDistance [arguments...]\n";
        msg+="Arguments:\n";
        msg+=argDescription(ARG_HELP,null,"Display this help and exit");
        msg+=argDescription(ARG_BINSTRFILE,"file","Load vectors from a binary string file (text file where each line "+
                        "contain a vector represented by 0 and 1 characters)");
        msg+=argDescription(ARG_DISP_VECTORS,null,"Display the loaded vectors");
        msg+=argDescription(ARG_TCLOUTFILE,"file","Write output to a TCL file");
        msg+=argDescription(ARG_TCLOUT,null,"Display only TCL output, overrides other display options");
        msg+="\n"+credits;
        //msg+="\n0123456789abcdefghij0123456789abcdefghij0123456789abcdefghij0123456789abcdefghijtt";
        msg = limitLineLength(msg,78);
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
        demoMode = true;
        System.out.println("---------------------------------------------------");
        boolean[][] dat = Z2.randomBooleansArray(5,9,14);
        System.out.println("Create a file containing "+dat.length+" vectors:");
        System.out.println(Z2.toBinaryString(dat));
        File tmp = File.createTempFile("HammingDistance_demo", ".dat");
        Z2.toBinaryStringFile(tmp,dat);
        runDemo(new String[]{ARG_BINSTRFILE, tmp.getCanonicalPath()}, "Find the minimum hamming distance of vectors given in a binary string file", i++);
        runDemo(new String[]{ARG_BINSTRFILE, tmp.getCanonicalPath(),ARG_DISP_VECTORS}, "Same as above, but display the loaded vectors in addition", i++);
        runDemo(new String[]{ARG_BINSTRFILE, tmp.getCanonicalPath(),ARG_TCLOUT}, "Display output as TCL code", i++);
    }
    public static void main(String[] args) throws IOException {
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
        if(demoMode) {
            System.out.println("Arguments:");
            for (int i = 0; i < args.length; i++) {
                System.out.println(i + tab + args[i]);
            }
        }
        if(args.length<2){
            System.out.println("\nNeed at least two arguments, found "+args.length);
            System.out.println(helpMessage());
            System.exit(1);
        }

        //parse arguments
        boolean[][] dat = null;
        boolean dispVectors=false;
        boolean tclOut = false;
        File tclOutFile=null;
        for(int i=0;i<args.length;i++) {
            if (args[i].equals(ARG_HELP)) {
                System.out.println(helpMessage());
                System.exit(0);
            }
            if (args[i].equals(ARG_DISP_VECTORS)) {
                dispVectors=true;
                continue;
            }
            if (args[i].equals(ARG_BINSTRFILE)) {
                File inputFile = new File(args[++i]);
                dat = Z2.binaryStringFileToBooleansArray(inputFile);
                continue;
            }
            if (args[i].equals(ARG_TCLOUTFILE)) {
                tclOutFile = new File(args[++i]);
                continue;
            }
            if (args[i].equals(ARG_TCLOUT)) {
                tclOut = true;
                continue;
            }
            System.out.println("Unknown argument found: "+args[i]);
            System.out.println(helpMessage());
            System.exit(1);
        }
        if(tclOut){
            dispVectors=false;
        }


        //Execution
        if(dispVectors) System.out.println("Loaded "+dat.length+" vectors:\n"+Z2.toBinaryString(dat));
        int hammingDistance = Z2.minHammingDistance(dat);
        String tclOutStr = "set hammingDistance "+hammingDistance;
        if(tclOut){
            System.out.println(tclOutStr);
        }else {
            System.out.println("Minimum hamming distance over " + dat.length + " vectors: " + hammingDistance);
        }
        if(tclOutFile!=null) {
            String comment = "# File generated on "+ ATimeUtilities.getTimeForUi()+"\n";
            writeToFile(tclOutFile, comment+tclOutStr+"\n");
        }
    }
}
