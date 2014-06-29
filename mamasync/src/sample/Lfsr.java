package sample;

import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

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
        setState(state,0,Math.min(l,state.length));
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
    public boolean isMaximumLength(){
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
        String properties = "(not maximum length)";
        if(isMaximumLength()) properties = "(maximum length LFSR: period of "+((1<<l)-1)+")";
        return "Lfsr{" +
                "taps=" + getTapsString() +
                ", state=" + getStateString() +
                ", "+properties+
                '}';
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
    gfhgfh                                          vbc
    static String helpMessage(){
        String msg="LFSR tool help\n";
        msg+="command line: java -jar lfsr.jar [arguments...]\n";
        msg+="Arguments:\n";
        msg+=tab+ARG_HELP+tab+tab+tab+tab+tab+tab+"iDisplay this help and exit\n";
        msg+=tab+ARG_DISP_SEQ+tab+tab+tab+tab+tab+"Display the sequence generated by LFSRs\n";
        msg+=tab+ARG_LFSR_BINSTR+tab+"[binaryString]"+tab+"Load a reference LFSR from its binary string representation\n";
        msg+=tab+ARG_LFSR_POLY+tab+"[polynomial]"+tab+"Load a reference LFSR from its polynomial representation\n";
        msg+=tab+ARG_SEQ_BINFILE+tab+"[file]"+tab+tab+tab+"Load a reference sequence from a binary file\n";
        msg+=tab+ARG_SEQ_BINSTR+tab+"[binaryString]\tLoad a reference sequence from a binary string\n";
        msg+="\n";
        msg+="Behavior:\n";
        msg+="If a reference sequence is given, the minimum length LFSR which generates it is computed.\n";
        msg+="If a reference LFSR is given, its properties are computed (maximum length or not, sequence length....\n";
        msg+="If a reference LFSR is given and a reference sequence as well:\n";
        msg+=tab+"- The reference LFSR is compared with the one computed from the sequence.\n";
        msg+=tab+"- The reference sequence is search inside the sequence generated by the reference LFSR\n";
        msg+="\nWritten by Sebastien Riou, 20140629";
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
        runDemo(new String[]{ARG_SEQ_BINSTR,"001010011100101110111"},"Find the LFSR generating a sequence given as a binary string",i++);
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
    }
    static void describeLfsr(Lfsr lfsr){
        describeLfsr(lfsr,false);
    }
    static void describeLfsr(Lfsr lfsr,boolean dispSeq){
        System.out.println(tab+"Polynomial:     "+lfsr.getPolynomial());
        System.out.println(tab+"Fibonacci taps: "+lfsr.getTapsString());
        System.out.println(tab+"Initial state:  "+lfsr.getStateString());
        String property = "a maximum length LFSR (sequence length is ";
        long maxLength = ((1L<<lfsr.getWidth())-1L);
        if(!lfsr.isMaximumLength()) property = "not "+property + "smaller than ";
        property += maxLength+")";
        System.out.println(tab+"It is "+property);
        if(dispSeq){
            System.out.println("Generated sequence:\n"+Z2.toBinaryString(lfsr.sequence()));
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
