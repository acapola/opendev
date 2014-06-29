package uk.co.nimp;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by seb on 6/22/14.
 */
public class Lfsr {
    //follow conventions from handbook of applied crypto by menezes,oorschot and vanstone, figure 6.4
    final boolean[] taps;//cL,...,c2,c1 (1 is implicit)
    final int l;//the width of the LFSR (max period is therefore 2^l - 1)
    final boolean [] nullState;
    boolean[] state;//stage0, stage1, ...,stage L-1.

    public int getWidth() {
        return l;
    }
    public Lfsr(boolean []taps,int len){
        if(!taps[0]) throw new RuntimeException("LSFR polynomial must always contain 1 -> this is the lsb of taps.");
        this.taps = Z2.reverse(taps,1,len);
        l=len-1;
        nullState = new boolean[l];
        state = new boolean[l];
        state[0]=true;
    }
    public boolean step(){
        //System.out.print(getTapsString()+" "+getStateString()+"->");
        boolean out = state[0];
        boolean sj=taps[0] & state[0];
        for(int i=1;i<l;i++){
            sj ^= taps[i] & state[i];
            state[i-1]=state[i];
        }
        state[l-1]=sj;
        //System.out.println(getStateString());
        return out;
    }
    /**
     * Build the LFSR which generate a given bit sequence. See "Berlekamp-Massey algorithm"
     * @param seq: a bit sequence with at least one bit set to 1
     * @return the minimal LFSR to generate the seq
     */
    public static Lfsr fromSequence(boolean[] seq){
        int len = seq.length;
        boolean[] c = new boolean[len+1];c[0]=true;
        boolean[] b = new boolean[len];b[0]=true;
        int l=0;
        int n=0;
        int m=-1;
        while(n<len){
            boolean sn=seq[n];
            boolean d=sn;
            assert(c.length>l);
            for(int i=1;i<l+1;i++) d^=c[i]&seq[n-i];
            //boolean[] t = Arrays.copyOf(c,len);
            if(d){
                boolean[] t = Arrays.copyOf(c,len);
                int shift=n-m;
                assert(shift>=0);
                //assert(l+shift<len);
                for(int i=shift;i<len;i++) c[i]^=b[i-shift];
                if(l<=n/2){
                    l=n+1-l;
                    m=n;
                    b=t;
                }
            }
            n++;
            //System.out.println("sn:"+toBinaryString(sn)+" d:"+toBinaryString(d)+" t: "+toBinaryString(t)+" c:"+toBinaryString(c)+" l:"+l+" m:"+m+" b:"+toBinaryString(b)+" n:"+n);
        }
        if(l==0) l=seq.length;//corner cases: we simply store the whole sequence
        Lfsr out = new Lfsr(c,l+1);
        //System.out.println(out);
        //set the state so that the LFSR start to generate the desired sequence
        /*for(int i=0;i<l-1;i++){
            out.state[i+1]=seq[i];
        }
        boolean sj=false;
        for(int i=1;i<l;i++){
            sj ^= out.taps[i] & out.state[i];
        }
        out.state[0]=sj^seq[l-1];*/
        out.setState(seq,l);
        return out;
    }
    public static Lfsr fromTaps(boolean[] taps){
        return new Lfsr(taps,taps.length);
    }

    public static Lfsr fromTapsPositions(int[] tapsPositions){
        int max=0;
        for(int i=0;i<tapsPositions.length;i++) max = Math.max(max,tapsPositions[i]);
        boolean []taps = new boolean[max+1];
        taps[0] = true;//required by the constructor
        for(int i=0;i<tapsPositions.length;i++) taps[tapsPositions[i]] = true;
        return new Lfsr(taps,taps.length);
    }
    public static Lfsr fromBinaryString(String tapsStr){
        return new Lfsr(Z2.toBooleans(tapsStr),tapsStr.length());
    }
    public static Lfsr fromPolynomial(String polynomial){
        boolean[] taps = Z2.polynomialToBooleans(polynomial);
        return new Lfsr(taps,taps.length);
    }

    public boolean[] getState() {
        return state;
    }
    public void setState(boolean[] state) {
        boolean[] paddedState = state;
        if(state.length<l){
            paddedState = new boolean[l];
            System.arraycopy(state,0,paddedState,0,state.length);
        }
        setState(paddedState,0,Math.min(l,paddedState.length));
    }
    public void setState(boolean[] state, int len) {
        setState(state,0,len);
    }
    public void setState(boolean[] state, int from, int to) {
        boolean [] newState = Arrays.copyOfRange(state,from,to);
        if(newState.length!=l) throw new RuntimeException("State length ("+newState.length+") does not match the LFSR size ("+l+")");
        //if(Arrays.equals(newState,nullState)) throw new RuntimeException("Can't set a state with only zeroes");
        this.state = newState;
    }
    public boolean[] getTaps() {
        boolean[] out = new boolean[l+1];
        out[0] = true;
        for(int i=1;i<=l;i++) out[i] = taps[l-i];
        return out;
    }
    public String getPolynomial(){
        return Z2.toPolynomial(getTaps());
    }
    public String getStateString(){
        return Z2.toBinaryString(state);
    }
    public String getTapsString(){
        return "1"+Z2.toBinaryString(Z2.reverse(taps));
    }
    public boolean isSingular(){
        return taps[l-1];
    }
    public boolean isMaximumLength(){
        if(isSingular()) return false;
        boolean[] fx = getTaps();
        return Z2.isIrreducible(fx);
    }

    /**
     * if the LFSR generates the sequence, its state is set to the initial state to generate the sequence.
     * @param sequence
     * @return
     */
    public boolean isGeneratingSequence(boolean[] sequence){
        setState(sequence);
        for(int i=0;i<sequence.length;i++){
            if(sequence[i]!=step()) return false;
        }
        setState(sequence);
        return true;
    }

    @Override
    public String toString() {
        String properties = "not maximum length)";
        if(isMaximumLength()) properties = "maximum length LFSR: period of "+((1<<l)-1)+")";
        if(isSingular()) properties = "(Singular, "+properties;
        else properties = "(Non singular, "+properties;
        return "Lfsr{" +
                "taps=" + getTapsString() +
                ", state=" + getStateString() +
                ", "+properties+
                '}';
    }
    public Set<boolean[]> sequences() {
        boolean[] done = new boolean[1<<l];
        HashSet<boolean[]> out = new HashSet<boolean[]>();
        int maxLen= (1<<l)-1;
        boolean[] seq = new boolean[maxLen];
        for(int i=1;i<done.length;i++){
            if(done[i]) continue;
            boolean[] initState = Z2.toBooleans(i);
            setState(initState);
            boolean[] trace = new boolean[1<<l];//used to detects loops
            int j=0;
            do{
                seq[j++]=step();
                int coveredState = Z2.booleansToInt(getState());
                done[coveredState] = true;
                if(trace[coveredState]) break;
                trace[coveredState] = true;
            }while((!Arrays.equals(state,initState)) && (!Arrays.equals(state,nullState)));
            out.add(Arrays.copyOfRange(seq,0,j));
        }
        return out;
    }
    public boolean []sequence() {
        return sequence(-1);
    }
    public boolean []sequence(int len){
        int maxLen= 1<<l;
        if(len!=-1) maxLen = len;
        boolean[] seq = new boolean[maxLen];
        boolean[] initState = Arrays.copyOf(state,l);
        int i=0;
        do {
            seq[i++]=step();
            if(-1==len){
                if(Arrays.equals(state,initState)) break;
                if(Arrays.equals(state,nullState)) {
                    System.out.println("WARNING: null state reached");
                    break;
                }
            }else if(i==len) break;
        }while(true);
        return Arrays.copyOfRange(seq,0,i);
    }

////////////////////////////////////////////////////////////
//test stuff
    public static final String BINSTR = "BinStr";
    public static final String BINFILE = "BinFile";
    public static final String POLY = "Poly";
    public static final String SEQ = "-seq";
    public static final String LFSR = "-lfsr";

    public static final String ARG_SEQ_BINSTR = SEQ+BINSTR;
    public static final String ARG_SEQ_BINFILE = SEQ+BINFILE;
    public static final String ARG_LFSR_BINSTR = LFSR+BINSTR;
    public static final String ARG_LFSR_POLY = LFSR+POLY;
    public static final String ARG_DISP_SEQ = "-dispSeq";
    public static final String ARG_HELP = "-help";
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
        String out = tab+ padTo(argName, 11)+tab;
        if(arg1!=null) out += padTo("[" + arg1 + "]", 6 * tab.length());
        else out+=tab+tab+tab+tab+tab+tab;
        out+=description+"\n";
        return out;
    }
    static String helpMessage(){
        String msg="LFSR tool help\n";
        msg+="command line: java -jar lfsr.jar [arguments...]\n";
        msg+="Arguments:\n";
        msg+=argDescription(ARG_HELP,null,"Display this help and exit");
        msg+=argDescription(ARG_DISP_SEQ,null,"Display the sequence generated by LFSRs");
        msg+=argDescription(ARG_LFSR_BINSTR,"binaryString","Load a reference LFSR from its binary string representation");
        msg+=argDescription(ARG_LFSR_POLY,"polynomial","Load a reference LFSR from its polynomial representation");
        msg+=argDescription(ARG_SEQ_BINFILE,"file","Load a reference sequence from a binary file");
        msg+=argDescription(ARG_SEQ_BINSTR,"binaryString","Load a reference sequence from a binary string");

        msg+="\n";
        msg+="Behavior:\n";
        msg+="If a reference sequence is given, the minimum length LFSR which generates it is computed.\n";
        msg+="If a reference LFSR is given, its properties are computed (maximum length or not, sequence length....\n";
        msg+="If a reference LFSR is given and a reference sequence as well:\n";
        msg+=tab+"- The reference LFSR is compared with the one computed from the sequence.\n";
        msg+=tab+"- The reference sequence is search inside the sequence generated by the reference LFSR\n";
        msg+="\nWritten by Sebastien Riou, 20140629-111100";
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
        Lfsr l=Lfsr.fromPolynomial("1+x3+x5");
        boolean[] seq = l.sequence();
        System.out.println("Create a file containing the binary sequence "+Z2.toBinaryString(seq,0,24));
        File tmp = File.createTempFile("LFSR_demo", ".dat");
        BigInteger seqBi = Z2.booleansToBigInteger(seq);
        byte []data = seqBi.toByteArray();
        FileOutputStream fos = new FileOutputStream(tmp);
        fos.write(data,0,3);
        fos.flush();
        fos.close();
        runDemo(new String[]{ARG_SEQ_BINFILE, tmp.getCanonicalPath()}, "Find the LFSR generating a sequence given in a binary file", i++);
        runDemo(new String[]{ARG_SEQ_BINSTR,"0001101110",ARG_LFSR_POLY,"1 + x3 + x5"},"Compare a sequence with an LFSR",i++);
        runDemo(new String[]{ARG_SEQ_BINSTR,"101110",ARG_LFSR_POLY,"1 + x3 + x5",ARG_DISP_SEQ},"Compare a short sequence with an LFSR and display sequences",i++);
        //runDemo(new String[]{ARG_SEQ_BINSTR,"001101001",ARG_DISP_SEQ},"debug",i++);
    }
    static void describeLfsr(Lfsr lfsr){
        describeLfsr(lfsr,false);
    }
    static void describeLfsr(Lfsr lfsr,boolean dispSeq){
        System.out.println(tab+"Polynomial:     "+lfsr.getPolynomial());
        System.out.println(tab+"Fibonacci taps: "+lfsr.getTapsString());
        System.out.println(tab+"Initial state:  "+lfsr.getStateString());
        String property = "maximum length LFSR (sequence length is ";
        long maxLength = ((1L<<lfsr.getWidth())-1L);
        if(!lfsr.isMaximumLength()) property = "non "+property + "smaller than ";
        property += maxLength+")";
        if(lfsr.isSingular()) property = "a singular, "+property;
        else property = "a non singular, "+property;
        System.out.println(tab+"It is "+property);
        if(dispSeq){
            if(lfsr.isMaximumLength()) System.out.println("Generated sequence:\n"+Z2.toBinaryString(lfsr.sequence()));
            else {
                Set<boolean[]> sequences = lfsr.sequences();
                System.out.println(sequences.size()+" generated sequences:");
                for(boolean[] seq: sequences){
                    System.out.println(tab+Z2.toBinaryString(seq));
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
            if (args[i].equals(ARG_SEQ_BINSTR)) {
                refSequence = Z2.toBooleans(args[++i]);
                continue;
            }
            if (args[i].equals(ARG_SEQ_BINFILE)) {
                File inputFile = new File(args[++i]);
                byte[] data = new byte[(int) inputFile.length()];
                FileInputStream fis = new FileInputStream(inputFile);
                fis.read(data, 0, data.length);
                fis.close();
                BigInteger seqBi = new BigInteger(data);
                refSequence = Z2.toBooleans(seqBi);
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
        }
        if(null!=refSequence){
            if(null!=refLfsr) {
                System.out.println("Reference LFSR:");
                describeLfsr(refLfsr,dispSeq);
                if(refLfsr.getWidth()*2<=refSequence.length){
                    System.out.println("Reference sequence is long enough to unambiguously retrieve the reference LFSR");
                } else {
                    System.out.println("WARNING: Reference sequence is too short to unambiguously retrieve the reference LFSR");
                }
            }
            Lfsr lfsr = Lfsr.fromSequence(refSequence);
            System.out.println("Reference sequence can be generated by following LFSR:");
            describeLfsr(lfsr,dispSeq);
            if(null!=refLfsr){
                if(refLfsr.isGeneratingSequence(refSequence)){
                    System.out.println("The reference LFSR generates the reference sequence\nInitial state: "+refLfsr.getStateString());
                }else{
                    System.out.println("The reference LFSR does not generates the reference sequence");
                }
            }
        }else{
            //Display the properties of the LFSR
            describeLfsr(refLfsr,dispSeq);
        }
    }
    static void testTapsPosition(){
        Lfsr ref = fromTapsPositions(new int[]{9,11});
        System.out.println(ref);
        boolean[] refSeq = ref.sequence();
        System.out.println(Z2.toBinaryString(refSeq));
        Lfsr test = fromSequence(refSeq);
        boolean[] seq = test.sequence();
        System.out.println(Z2.toBinaryString(seq));
        System.out.println(test);
    }
    static void debug(){
        {
            Lfsr ref = Lfsr.fromBinaryString("11001");
            System.out.println(ref);
            System.out.println(ref.getPolynomial());
            boolean[] refSeq = ref.sequence();
            System.out.println(Z2.toBinaryString(refSeq));
            Lfsr test = fromSequence(refSeq);
            boolean[] seq = test.sequence();
            System.out.println(Z2.toBinaryString(seq));
            System.out.println(test);

            test = fromSequence(Z2.toBooleans("001101110"));
            System.out.println(Z2.toBinaryString(test.sequence()));
            System.out.println(test);
            test = fromSequence(Z2.toBooleans("001010011100101110111"));
            System.out.println(Z2.toBinaryString(test.sequence()));
            System.out.println(test);
            test = fromSequence(Z2.toBooleans("110101100011010001000"));
            System.out.println(Z2.toBinaryString(test.sequence()));
            System.out.println(test);
        }
    }
}
