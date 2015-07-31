package qnd;

import uk.co.nimp.Lfsr;
import uk.co.nimp.Z2;

/**
 * Created by nxp56875 on 7/3/15.
 * try to recover the state of a multi step per cycle LFSR, knowing:
 * - the feedback structure
 * - the step size
 * - the output size
 * - an output stream
 */
public class LfsrMultiStepRecovery {
    public static void main(String[] args) {
        String polynomial = "x9+x5+1";
        int stepSize = 7;
        int outputSize = 4;
        boolean[] secretState = Z2.toBooleans("010010110");//that's what we want to recover

        //generate output data
        Lfsr lfsr = Lfsr.fromPolynomial(polynomial);
        lfsr.setState(secretState);
        int nOutputs = (((lfsr.getWidth()+outputSize-1)/outputSize)+2);//+2 to have some margin
        int nOutputBits = stepSize*nOutputs;
        boolean[] fullSeq = lfsr.sequence(nOutputBits);
        boolean[][] outputs = new boolean[nOutputs][outputSize];
        for(int i=0;i<nOutputs;i++){
            for(int j=0;j<outputSize;j++) outputs[i][j] = fullSeq[i*stepSize+j];
        }
        System.out.println(Z2.toBinaryString(outputs));
    }
}
