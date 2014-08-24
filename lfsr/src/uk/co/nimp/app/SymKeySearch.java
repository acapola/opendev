package uk.co.nimp.app;

import uk.co.nimp.ATimeUtilities;
import uk.co.nimp.Z2;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static uk.co.nimp.app.MainHelpers.*;
import static uk.co.nimp.app.MainHelpers.writeToFile;

/**
 * Created by seb on 8/23/14.
 */
public class SymKeySearch {
    static boolean demoMode = false;
    static String helpMessage(){
        String msg="AES key search tool help\n";
        msg+="command line: java -cp z2.jar uk.co.nimp.app.AesKeySearch [arguments...]\n";
        msg+="Arguments:\n";
        msg+=argDescription(ARG_HELP,null,"Display this help and exit");
        msg+=argDescription(ARG_HEXSTRFILE,"file plain cipher","Load candidate keys from a text file. Keys are expected in hexadecimal, one per line."+
        "plain text and cipher text also in hexadecimal.");
        msg+="\n"+credits;
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
        demoMode = true;
        System.out.println("---------------------------------------------------");
        /*boolean[][] dat = Z2.randomBooleansArray(5, 9, 14);
        System.out.println("Create a file containing "+dat.length+" vectors:");
        System.out.println(Z2.toBinaryString(dat));
        File tmp = File.createTempFile("HammingDistance_demo", ".dat");
        Z2.toBinaryStringFile(tmp,dat);*/
        runDemo(new String[]{ARG_HEXSTRFILE, "/home/seb/dev/dfa-aes-master/examples/keys-0.csv",
                "32_43_F6_A8_88_5A_30_8D_31_31_98_A2_E0_37_07_34","39_25_84_1D_02_DC_09_FB_DC_11_85_97_19_6A_0B_32"}, "Search the matching key in a file", i++);
        runDemo(new String[]{ARG_ENDIANNESS,ARG_HEXSTR, "AB_F7_15_88_09_CF_4F_3C_2B_7E_15_16_28_AE_D2_A6",
                "31_31_98_A2_E0_37_07_34_32_43_F6_A8_88_5A_30_8D","DC_11_85_97_19_6A_0B_32_39_25_84_1D_02_DC_09_FB"}, "Search the matching endianess", i++);
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
        if(args.length<4){
            System.out.println("\nNeed at least 4 arguments, found "+args.length);
            System.out.println(helpMessage());
            System.exit(1);
        }

        //parse arguments
        byte[] plain=null;
        byte[] cipher = null;
        List<byte[]> keyCandidates=new ArrayList<byte[]>();
        File inputFile=null;
        boolean endianess=false;
        for(int i=0;i<args.length;i++) {
            if (args[i].equals(ARG_HELP)) {
                System.out.println(helpMessage());
                System.exit(0);
            }
            if (args[i].equals(ARG_HEXSTRFILE)) {
                inputFile = new File(args[++i]);

                plain=Z2.hexBytesToBytes(args[++i]);
                cipher=Z2.hexBytesToBytes(args[++i]);
                continue;
            }
            if (args[i].equals(ARG_ENDIANNESS)) {
                endianess=true;
                continue;
            }
            if (args[i].equals(ARG_HEXSTR)) {
                endianess=true;
                keyCandidates.add(Z2.hexBytesToBytes(args[++i]));
                plain=Z2.hexBytesToBytes(args[++i]);
                cipher=Z2.hexBytesToBytes(args[++i]);
                continue;
            }
            System.out.println("Unknown argument found: "+args[i]);
            System.out.println(helpMessage());
            System.exit(1);
        }

        //Execution
        if(null!=inputFile)
            keyCandidates.addAll(Z2.hexStringFileToBytesList(inputFile));
        int i=0;
        int totalTests=0;
        for(;i<keyCandidates.size();i++){
            byte[] candidate=keyCandidates.get(i);
            if(check(candidate, plain, cipher)) return;
            if(endianess) {
                int maxWordSize = plain.length*8;
                for (int wordSize = 1; wordSize <= maxWordSize; wordSize = 2 * wordSize) {
                    if (plain.length*8 % wordSize != 0) {
                        System.out.println("Word size = " + wordSize + " skipped because it does not divides the block length (" + plain.length + ")");
                        continue;
                    }
                    if (candidate.length*8 % wordSize != 0) {
                        System.out.println("Word size = " + wordSize + " skipped because it does not divides the key length (" + candidate.length + ")");
                        continue;
                    }
                    for (int atomSize = 1; atomSize <= wordSize / 2; atomSize = 2 * atomSize) {
                        byte[] tweakedCandidate = Z2.switchEndianness(candidate, atomSize, wordSize);
                        byte[] tweakedIn = Z2.switchEndianness(plain, atomSize, wordSize);
                        byte[] tweakedExpected = Z2.switchEndianness(cipher, atomSize, wordSize);
                        totalTests++;
                        if (check(tweakedCandidate, tweakedIn, tweakedExpected)) {
                            System.out.println("Endianess changed: Atom size=" + atomSize + ", Word size = " + wordSize);
                            System.out.println("Input:  " + bytesToString(tweakedIn));
                            System.out.println("Output: " + bytesToString(tweakedExpected));
                            return;
                        }
                    }
                }
            }
        }
        System.out.println("No match found, "+i+" keys tested, "+totalTests+" tests done in total.");
    }
    static boolean check(byte[] key, byte[] in, byte[] expected){
        byte[] actualOut=encrypt(key,in);
        if(Arrays.equals(actualOut,expected)){
            System.out.println("Match found:\nKey:    "+bytesToString(key));
            return true;
        }
        return false;
    }

    static public byte[] encrypt(byte[] encKey, byte[] inputData) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding", "SunJCE");
            SecretKeySpec key = new SecretKeySpec(encKey, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(inputData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
