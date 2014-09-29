package uk.co.nimp.songpa;

import uk.co.nimp.Z2;

/**
 * Created by seb on 9/27/14.
 */
public class AesMixColumns implements M {

    static byte f2(byte x){
        byte toXor=0;
        if(((x >> 7) & 1)!=0) toXor = 0x1B;
        return (byte)((x << 1) ^ toXor);
    }
    static byte f3(byte x){
        return (byte)(f2(x) ^ x);
    }
    static byte f4(byte x){
        return (byte)(f2(f2(x)));
    }
    static byte f8(byte x){
        return (byte)(f2(f4(x)));
    }

    static byte f9(byte x){
        return (byte)(x ^ f8(x));
    }
    static byte fb(byte x){
        return (byte)(x ^ f2(x) ^ f8(x));
    }
    static byte fd(byte x){
        return (byte)(x ^ f4(x) ^ f8(x));
    }
    static byte fe(byte x){
        return (byte)(f2(x) ^ f4(x) ^ f8(x));
    }

    static int mixColumn(int column){
        byte[] bytes = new byte[4];
        for(int i=0;i<4;i++){
            bytes[3-i]=(byte)((column>>(i*8)) & 0xFF);
        }
        int out=0;
        for(int i=0;i<4;i++){
            int newByte = 0xFF & (f2(bytes[(i+0)%4])^f3(bytes[(i+1)%4])^bytes[(i+2)%4]^bytes[(i+3)%4]);
            out=(out<<8)+newByte;
        }
        return out;
    }
    static int invMixColumn(int column){
        byte[] bytes = new byte[4];
        for(int i=0;i<4;i++){
            bytes[3-i]=(byte)((column>>(i*8)) & 0xFF);
        }
        int out=0;
        for(int i=0;i<4;i++){
            int newByte = 0xFF & (fe(bytes[(i+0)%4])^fb(bytes[(i+1)%4])^fd(bytes[(i+2)%4])^f9(bytes[(i+3)%4]));
            out=(out<<8)+newByte;
        }
        return out;
    }
    public boolean[] apply(boolean[] in){
        boolean[] out = new boolean[in.length];
        for (int i = 0; i < in.length/32; i++) {
            int column = Z2.booleansToInt(in,i*32,32);
            int newColumnInt = mixColumn(column);
            boolean[] newColumn = Z2.toBooleans(newColumnInt,32);
            System.arraycopy(newColumn, 0, out, i * 32, 32);
        }
        return out;
    }
    public boolean[] unapply(boolean[] in){
        boolean[] out = new boolean[in.length];
        for (int i = 0; i < in.length/32; i++) {
            int column = Z2.booleansToInt(in,i*32,32);
            int newColumnInt = invMixColumn(column);
            boolean[] newColumn = Z2.toBooleans(newColumnInt,32);
            System.arraycopy(newColumn, 0, out, i * 32, 32);
        }
        return out;
    }
}
