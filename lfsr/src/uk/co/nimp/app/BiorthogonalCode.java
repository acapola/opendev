package uk.co.nimp.app;

import uk.co.nimp.Z2;

/**
 * Created by seb on 10/28/15.
 */
public class BiorthogonalCode {


    public static void main(String[] args) {
        int m = 3;
        int n = 1<<m;
        int k = m+1;
        int d = 1<<(m-1);
        System.out.println("Biorthogonal code for m="+m);
        System.out.println("n="+n);
        System.out.println("k="+k);
        System.out.println("d="+d);
        //compute G the generator matrix
        boolean [][] G=new boolean[k][n];
        for(int row = 0;row<k;row++){
            int nz = (1<<row)>>1;
            for(int col = 0;col<n;col++){
                boolean v = true;
                if(0!=nz){
                    v = ((col/nz) & 1) != 1;
                }
                G[row][col] = v;
            }
        }
        System.out.println("Generator matrix:\n"+Z2.toBinaryString(G)+"\n");
        //put it in row echelon form
        G = Z2.reducedRowEchelonMatrix(G);
        System.out.println("Generator matrix in row echelon form:\n"+Z2.toBinaryString(G)+"\n");
        G = Z2.reorderRowsToStandardForm(G);
        System.out.println("Generator matrix in std row echelon form:\n"+Z2.toBinaryString(G)+"\n");
        boolean[][]P = Z2.copySubMatrix(G,0,k);
        boolean[][]H = Z2.complement(Z2.transpose(P));
        System.out.println("Parity check matrix:\n"+Z2.toBinaryString(H) + "\n");

        for(int i=0;i<1<<k;i++){
            boolean [][]u = Z2.toRowMatrix(i,k);
            boolean [][]c = Z2.matrixMul(u,G);
            System.out.println("data = "+Z2.toBinaryString(u)+" -> code = " +Z2.toBinaryString(c));
        }


    }
}
