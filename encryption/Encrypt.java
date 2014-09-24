package encryption;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.Random;

public class Encrypt {
	
	public static BigInteger[] prime;
	
	/**
	 * encrypt() takes in a BigInteger x and encrypts it with public keys e and n.
	 * encrypted return value is x^e Mod (n)
	 * @param x BigInteger x represents a character
	 * @param e BigInteger e is the public key exponent
	 * @param n BigInteger n is the public key mod
	 * @return a BigInteger that is the encrypted value of x.
	 */
	private static BigInteger encrypt(BigInteger x, BigInteger e, BigInteger n) {
		return x.modPow(e, n);
	}
	
	/**
	 * rsaEncrypte() takes in a Byte Array and public keys e and n and returns a
	 * BigInteger array of encrypted values.
	 * @param b Byte array represents a string
	 * @param e BigInteger e is the public key exponent
	 * @param n BigInteger n is the public key mod
	 * @return
	 */
	public static BigInteger[] rsaEncrypt(byte[] b, BigInteger e, BigInteger n) {
		BigInteger[] bigB = new BigInteger[b.length];
		for (int i = 0; i < bigB.length; i++) {
			BigInteger x = new BigInteger(Byte.toString(b[i]));
			bigB[i] = encrypt(x, e, n);
		}
		return bigB;
	}
	
	
	/**
	 * Reads the file at the specified location and creates an BigInteger array of the
	 * numbers found in the txt file.
	 */
	public static void encryptBytes() {
		BigInteger[] rArray = new BigInteger[1000000];
		try {
			URL url = Encrypt.class.getResource("PrimeBank.txt");
			BufferedReader reader = new BufferedReader(new FileReader(
					url.getPath()));
			String line = null;
			for(int i = 0; i < rArray.length; i++) {
				line = reader.readLine();
				rArray[i] = new BigInteger((String) line);
				
			}
			prime = rArray;
			
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Probably no file found?");
			
		} 
	}
	
	/**
	 * Picks a random prime integer from .txt file. Must be called after encryptBytes() is called.
	 * @return a prime BigInteger.
	 */
	public static BigInteger pickPrime() {
		Random r = new Random();
		return prime[r.nextInt(prime.length)];
	}

	public static void main(String args[]) {
			encryptBytes();
			pickPrime();
			
		}
	}

