package uk.co.nimp;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;

/**
 * Created by seb on 8/22/14.
 */
public class WbAesEp {
    final byte []keyPlain;
    public WbAesEp(byte []key){
        keyPlain = key.clone();
    }
    public byte[] encryptRef(byte[] inputData) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding", "SunJCE");
            SecretKeySpec key = new SecretKeySpec(keyPlain, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(inputData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public byte[] encrypt(byte[] inputData){
        return null;
    }

    static public String bytesToString(byte[] dat){
        String out = "";
        if(0==dat.length) return out;
        for(int i=0;i<dat.length;i++){
            int b = 0x0FF & dat[i];
            out+=String.format("%02X ",b);
        }
        return out.substring(0,out.length()-1);
    }
    static public String reverseBytesToString(byte[] dat){
        byte []rdat = reverse(dat);
        return bytesToString(rdat);
    }
    static void print(byte[] dat){
        System.out.println(bytesToString(dat));
    }
    static public byte[] reverse(byte[] in){
        byte[] out = new byte[in.length];
        for(int i=0;i<in.length;i++) out[i] = in[in.length-1-i];
        return out;
    }
    static void printReverseBytes(byte[] dat){
        System.out.println(reverseBytesToString(dat));
    }
    static public void main(String[] args){
        byte []key = {0x2b,0x7e,0x15,0x16,0x28,(byte)0xae,(byte)0xd2,(byte)0xa6,(byte)0xab,(byte)0xf7,0x15,(byte)0x88,0x09,(byte)0xcf,0x4f,0x3c};
        byte []plain = {0x32,0x43,(byte)0xf6,(byte)0xa8,(byte)0x88,0x5a,0x30,(byte)0x8d,0x31,0x31,(byte)0x98,(byte)0xa2,(byte)0xe0,0x37,0x07,0x34};
        byte []cipher = null;
        byte []expectedCipher = null;
        WbAesEp aes = new WbAesEp(key);
        expectedCipher = aes.encryptRef(plain);
        cipher = aes.encrypt(plain);
        print(key);
        print(plain);
        print(expectedCipher);
    }
}
