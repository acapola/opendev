package sample;

import java.util.Arrays;

/**
 * Created by seb on 6/22/14.
 */
public class lfsr1 {

    public static int[] runTest(int[] array) {
        final int N = array.length;
        int[] b = new int[N];
        int[] c = new int[N];
        int[] t = new int[N];
        b[0] = 1;
        c[0] = 1;
        int l = 0;
        int m = -1;
        for (int n = 0; n < N; n++) {
            int d = 0;
            for (int i = 0; i <= l; i++) {
                d ^= c[i] * array[n - i];
            }
            if (d == 1) {
                System.arraycopy(c, 0, t, 0, N);
                int N_M = n - m;
                for (int j = 0; j < N - N_M; j++) {
                    c[N_M + j] ^= b[j];
                }
                if (l <= n / 2) {
                    l = n + 1 - l;
                    m = n;
                    System.arraycopy(t, 0, b, 0, N);
                }
            }
        }
        System.out.println(l);
        return Arrays.copyOfRange(c,0,l+1);
    }
    public static int stepLfsr(int []state, int[] taps){
        int newBit = 0;
        int out = state[0];
        for(int i=1;i<state.length;i++){
            newBit ^= state[i] & taps[i];
        }
        for(int i=state.length-1;i>0;i--){
            state[i]=state[i-1];
        }
        state[0] = newBit;
        return out;
    }
    public static int[] lfsrSeq(int[] taps){
        int len = taps.length;
        int []initState = new int[len];
        Arrays.fill(initState,1);
        int []nullState = new int[len];
        int []state = Arrays.copyOf(initState,len);
        int []seq = new int[1<<len];
        int i=0;
        do {
            seq[i++]=stepLfsr(state,taps);
            //System.out.println(Arrays.toString(state));
        }while((!Arrays.equals(state,initState))&&(!Arrays.equals(state,nullState)));
        if(Arrays.equals(state,nullState)){
            System.out.println("WARNING: null state reached");
        }
        return Arrays.copyOfRange(seq,0,i);
    }
    public static void main(String[] args){
        int []refTaps = {1,0,1,1};//1+x2+x3
        //int []in = {0,1,1,0,1,0,0,1,0,1,0,0,1,1};
        int []in = lfsrSeq(refTaps);
        System.out.println(Arrays.toString(in));
        int []out=runTest(in);
        System.out.println(Arrays.toString(out));
        System.out.println(Arrays.toString(lfsrSeq(out)));
    }
}
