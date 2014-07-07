package rsa;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

public class RSA {
	private final static BigInteger one = new BigInteger("1");
	private final static SecureRandom random = new SecureRandom();

	private BigInteger privateKey;
	private BigInteger publicKey;
	private BigInteger modulus;

	// generate an N-bit (roughly) public and private key
	RSA(int N) {
		BigInteger p = BigInteger.probablePrime(N / 2, random);
		BigInteger q = BigInteger.probablePrime(N / 2, random);
		BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

		modulus = p.multiply(q);
		publicKey = new BigInteger("65537"); // common value in practice = 2^16
												// + 1
		privateKey = publicKey.modInverse(phi);
	}

	BigInteger encrypt(BigInteger message) {
		return message.modPow(publicKey, modulus);
	}

	BigInteger decrypt(BigInteger encrypted) {
		return encrypted.modPow(privateKey, modulus);
	}

	public String toString() {
		String s = "";
		s += "public exponent\t\t= " + publicKey + "\n";
		s += "private exponent\t= " + privateKey + "\n";
		s += "modulus\t\t\t= " + modulus;
		return s;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Key size? ");
		int N = sc.nextInt();

		RSA key = new RSA(N);
		System.out.println(key);

		// create random message, encrypt and decrypt
		BigInteger message = new BigInteger(N - 1, random);

		// // create message by converting string to integer
		// String s = "test";
		// byte[] bytes = s.getBytes();
		// BigInteger message = new BigInteger(s);

		BigInteger encrypt = key.encrypt(message);
		BigInteger decrypt = key.decrypt(encrypt);
		System.out.println("message\t\t\t= " + message);
		System.out.println("encrypted\t\t= " + encrypt);
		System.out.println("decrypted\t\t= " + decrypt);
	}
}
