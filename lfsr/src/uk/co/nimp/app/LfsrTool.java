package uk.co.nimp.app;

import uk.co.nimp.Lfsr;
import uk.co.nimp.Z2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static uk.co.nimp.app.MainHelpers.*;
/**
 * Created by seb on 6/22/14.
 */
public class LfsrTool {
    static String helpMessage(){
        String msg="LFSR tool help\n";
        msg+="command line: java -cp z2.jar uk.co.nimp.app.LfsrTool [arguments...]\n";
        msg+="Arguments:\n";
        msg+=argDescription(ARG_HELP,null,"Display this help and exit");
        msg+=argDescription(ARG_DISP_SEQ,null,"Display the sequence generated by LFSRs");
        msg+=argDescription(ARG_DISP_SEQ_STATES,null,"Display the sequence generated by LFSRs and the successive states");
        msg+=argDescription(ARG_LFSR_BINSTR,"binaryString","Load a reference LFSR from its binary string representation");
        msg+=argDescription(ARG_LFSR_POLY,"polynomial","Load a reference LFSR from its polynomial representation");
        msg+=argDescription(ARG_SEQ_BINFILE,"file, offset, length","Load a reference sequence from a binary file");
        msg+=argDescription(ARG_SEQ_BINSTRFILE,"file","Load a reference sequence from a binary string file");
        msg+=argDescription(ARG_SEQ_BINSTR,"binaryString","Load a reference sequence from a binary string");

        msg+="\n";
        msg+="Behavior:\n";
        msg+="If a reference sequence is given, the minimum length LFSR which generates it is computed.\n";
        msg+="If a reference LFSR is given, its properties are computed (maximum length or not, sequence length....\n";
        msg+="If a reference LFSR is given and a reference sequence as well:\n";
        msg+=tab+"- The reference LFSR is compared with the one computed from the sequence.\n";
        msg+=tab+"- The reference sequence is search inside the sequence generated by the reference LFSR\n";
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
        System.out.println("---------------------------------------------------");
        runDemo(new String[]{ARG_SEQ_BINSTR, "001101110"}, "Find the LFSR generating a sequence given as a binary string", i++);
        runDemo(new String[]{ARG_SEQ_BINSTR,"001010011100101110111",ARG_DISP_SEQ},"Find the LFSR generating a sequence given as a binary string and display generated sequence",i++);
        runDemo(new String[]{ARG_LFSR_POLY,"1 + x3 + x5",ARG_DISP_SEQ},"Display the properties of an LFSR given as a polynomial,\n"+tab+"display also the generated sequence",i++);
        runDemo(new String[]{ARG_LFSR_BINSTR,"100101"},"Display the properties of an LFSR given as a binary string",i++);
        Lfsr l= Lfsr.fromPolynomial("1+x3+x5");
        boolean[] seq = Z2.cloneRange(l.sequence(),0,10);
        System.out.println("Create a file containing the binary sequence "+Z2.toBinaryString(seq));
        File tmp = File.createTempFile("LFSR_demo", ".dat");
        Z2.toBinaryFile(tmp,seq);
        runDemo(new String[]{ARG_SEQ_BINFILE, tmp.getCanonicalPath(),"0","0"}, "Find the LFSR generating a sequence given in a binary file", i++);
        l= Lfsr.fromPolynomial("1+x+x7");
        seq = Z2.cloneRange(l.sequence(),0,24);
        System.out.println("Create a text file containing the binary sequence "+Z2.toBinaryString(seq));
        tmp = File.createTempFile("LFSR_demo", ".dat");
        Z2.toBinaryStringFile(tmp,seq);
        runDemo(new String[]{ARG_SEQ_BINSTRFILE, tmp.getCanonicalPath()}, "Find the LFSR generating a sequence given in a binary string file (a text file containing 0 and 1)", i++);
        runDemo(new String[]{ARG_SEQ_BINSTR,"100000011111110101010011"},"Display the properties of an LFSR given as a binary string",i++);

        runDemo(new String[]{ARG_SEQ_BINSTR,"0001101110",ARG_LFSR_POLY,"1 + x3 + x5"},"Compare a sequence with an LFSR",i++);
        runDemo(new String[]{ARG_SEQ_BINSTR,"101110",ARG_LFSR_POLY,"1 + x3 + x5",ARG_DISP_SEQ},"Compare a short sequence with an LFSR and display sequences",i++);

        runDemo(new String[]{ARG_LFSR_POLY,"1+x+x5",ARG_DISP_SEQ_STATES},"Display the properties of an LFSR given as a polynomial,\n"+tab+"display also the generated sequence and associated the states",i++);
        runDemo(new String[]{ARG_LFSR_POLY,"1+x3+x12",ARG_DISP_SEQ},"Display the properties of an LFSR given as a polynomial,\n"+tab+"display also the generated sequence without the states",i++);
        //runDemo(new String[]{ARG_SEQ_BINSTR,"110011010010101010111000101111010010010011010101110011011111010100101010"},"debug",i++);
        //runDemo(new String[]{ARG_LFSR_POLY,"1+x42+x167"},"debug",i++);
    }
    static void describeLfsr(Lfsr lfsr){
        describeLfsr(lfsr,false,false);
    }
    static void describeLfsr(Lfsr lfsr,boolean dispSeq, boolean dispSeqStates){
        System.out.println(tab+"Polynomial:     "+lfsr.getPolynomial());
        System.out.println(tab+"Fibonacci taps: "+lfsr.getTapsString());
        System.out.println(tab+"Initial state:  "+lfsr.getStateString());
        String properties = "maximum length LFSR (sequence length is ";
        //long maxLength = ((1L<<lfsr.getWidth())-1L);
        BigInteger maxLength = BigInteger.ONE.shiftLeft(lfsr.getWidth()).subtract(BigInteger.ONE);
        if(!lfsr.isMaximumLength()) properties = "non "+properties + "smaller than ";
        properties += maxLength+")";
        if(!lfsr.isMaximumLength()){
            if(lfsr.isPolynomialIrreducible()) properties = "irreducible but not primitive, "+properties;
            else properties = "reducible, "+properties;
        }

        if(lfsr.isSingular()) properties = "a singular, "+properties;
        else properties = "a non singular, "+properties;
        System.out.println(tab+"It is "+properties);
        if(dispSeq){
            if(lfsr.isMaximumLength()) System.out.println("Generated sequence:\n"+Z2.toBinaryString(lfsr.sequence()));
            else {
                Map<boolean[],boolean[][]> sequences = lfsr.sequences(dispSeqStates);
                System.out.println(sequences.size()+" generated sequences:");
                for(boolean[] seq: sequences.keySet()){
                    System.out.println(tab+padTo(""+seq.length,4)+" outputs: "+Z2.toBinaryString(seq));
                    boolean[][] states = sequences.get(seq);
                    if(dispSeqStates) {
                        for (int i = 0; i < states.length; i++)
                            System.out.println(tab + tab + Z2.toBinaryString(states[i]));
                        System.out.println();
                    }
                }
            }
        }
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
        boolean[] refSequence=null;
        boolean dispSeq=false;
        boolean dispSeqStates=false;
        Lfsr refLfsr=null;
        for(int i=0;i<args.length;i++) {
            if (args[i].equals(ARG_HELP)) {
                System.out.println(helpMessage());
                System.exit(0);
            }
            if (args[i].equals(ARG_DISP_SEQ)) {
                dispSeq=true;
                continue;
            }
            if (args[i].equals(ARG_DISP_SEQ_STATES)) {
                dispSeq=true;
                dispSeqStates=true;
                continue;
            }
            if (args[i].equals(ARG_SEQ_BINSTR)) {
                refSequence = Z2.toBooleans(args[++i]);
                continue;
            }
            if (args[i].equals(ARG_SEQ_BINFILE)) {
                File inputFile = new File(args[++i]);
                int offset = Integer.decode(args[++i]);
                int length = Integer.decode(args[++i]);
                if(length==0) length = (int) inputFile.length();

                byte[] data = new byte[length];
                FileInputStream fis = new FileInputStream(inputFile);
                fis.read(data, offset, length);
                fis.close();
                BigInteger seqBi = new BigInteger(data);
                refSequence = Z2.toBooleans(seqBi);
                continue;
            }
            if (args[i].equals(ARG_SEQ_BINSTRFILE)) {
                File inputFile = new File(args[++i]);
                refSequence = Z2.binaryStringFileToBooleans(inputFile);
                continue;
            }
            if (args[i].equals(ARG_LFSR_POLY)) {
                refLfsr = Lfsr.fromPolynomial(args[++i]);
                continue;
            }
            if (args[i].equals(ARG_LFSR_BINSTR)) {
                refLfsr = Lfsr.fromBinaryString(args[++i]);
                continue;
            }
            System.out.println("Unknown argument found: "+args[i]);
            System.out.println(helpMessage());
            System.exit(1);
        }
        if(null!=refSequence){
            if(null!=refLfsr) {
                System.out.println("Reference LFSR:");
                describeLfsr(refLfsr,dispSeq,dispSeqStates);
                if(refLfsr.getWidth()*2<=refSequence.length){
                    System.out.println("Reference sequence is long enough to unambiguously retrieve the reference LFSR");
                } else {
                    System.out.println("WARNING: Reference sequence is too short to unambiguously retrieve the reference LFSR");
                }
            }
            Lfsr lfsr = Lfsr.fromSequence(refSequence);
            System.out.println("Reference sequence can be generated by following LFSR:");
            describeLfsr(lfsr,dispSeq,dispSeqStates);
            if(null!=refLfsr){
                if(refLfsr.isGeneratingSequence(refSequence)){
                    System.out.println("The reference LFSR generates the reference sequence\nInitial state: "+refLfsr.getStateString());
                }else{
                    System.out.println("The reference LFSR does not generates the reference sequence");
                }
            }
        }else{
            //Display the properties of the LFSR
            describeLfsr(refLfsr,dispSeq,dispSeqStates);
        }
    }
}
