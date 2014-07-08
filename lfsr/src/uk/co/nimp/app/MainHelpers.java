package uk.co.nimp.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by seb on 6/22/14.
 */
public class MainHelpers {
    public static String credits = "Written by Sebastien Riou, 20140708-221700";
    static String formatArg(String arg){
        return "-"+arg.substring(0,1).toLowerCase()+arg.substring(1);
    }
    public static final String BINSTR = "BinStr";
    public static final String BINSTRFILE = "BinStrFile";
    public static final String BINFILE = "BinFile";
    public static final String POLY = "Poly";
    public static final String SEQ = "Seq";
    public static final String STATES = "States";
    public static final String LFSR = "Lfsr";
    public static final String VECTORS = "Vectors";
    public static final String DISPLAY = "Disp";
    public static final String TCLOUT = "TclOut";
    public static final String TCLOUTFILE = "TclOutFile";

    public static final String ARG_BINSTRFILE = formatArg(BINSTRFILE);
    public static final String ARG_SEQ_BINSTR = formatArg(SEQ +BINSTR);
    public static final String ARG_SEQ_BINFILE = formatArg(SEQ +BINFILE);
    public static final String ARG_LFSR_BINSTR = formatArg(LFSR +BINSTR);
    public static final String ARG_LFSR_POLY = formatArg(LFSR +POLY);
    public static final String ARG_DISP_SEQ = formatArg(DISPLAY+SEQ);
    public static final String ARG_DISP_SEQ_STATES = formatArg(DISPLAY+SEQ+STATES);
    public static final String ARG_DISP_VECTORS = formatArg(DISPLAY+VECTORS);
    public static final String ARG_TCLOUT = formatArg(TCLOUT);
    public static final String ARG_TCLOUTFILE = formatArg(TCLOUTFILE);
    public static final String ARG_HELP = formatArg("Help");
    static final String tab = "   ";
    static String padTo(String in,int desiredLength){
        int pad = desiredLength-in.length();
        if(pad<=0){
            return in;
        }
        String out = in;
        for(int i=0;i<pad;i++){
            out+=" ";
        }
        return out;
    }
    static String limitLineLength(String in,int maxLineLength){
        int startPos=0;
        String out = in;
        int cutPoint = startPos+maxLineLength;
        while(cutPoint<out.length()){
            int newLinePos = out.indexOf("\n",startPos);
            if((-1==newLinePos)||(newLinePos>cutPoint)){
                //backtrack to beginning of the word
                int blankPos = out.lastIndexOf(" ",cutPoint);
                blankPos = Math.max(blankPos,out.lastIndexOf("\t",cutPoint));
                if((blankPos!=-1) && (blankPos>startPos)) cutPoint = blankPos+1;
                //copy the blank space from the beginning of the line to the new line as well to preserve alignment
                blankPos=startPos;
                while(Character.isWhitespace(out.charAt(blankPos))) blankPos++;
                out = out.substring(0,cutPoint)+"\n"+padTo("",blankPos-startPos)+out.substring(cutPoint);
                startPos = cutPoint+1;
            }else {
                startPos = newLinePos+1;
            }
            cutPoint = startPos+maxLineLength;
        }
        return out;
    }
    static String argDescription(String argName,String arg1,String description){
        String out = tab+ padTo(argName, 12)+tab;
        if(arg1!=null) out += padTo("[" + arg1 + "]", 6 * tab.length());
        else out+=tab+tab+tab+tab+tab+tab;
        out+=description+"\n";
        return out;
    }
    static void writeToFile(File file, String str)throws IOException {
        BufferedWriter writer = new BufferedWriter( new FileWriter(file));
        try {
            writer.write(str);
        } finally {
            writer.flush();
            writer.close();
        }
    }
}
