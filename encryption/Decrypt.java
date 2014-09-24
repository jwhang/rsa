package encryption;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Decrypt {

	/**
	 * crackKey() takes in a BigInteger n that will return two prime factors that
	 * multiply to n in an array of BigInteger of size 2. If no such two prime factors
	 * exist, then crackKey() will return null.
	 * source:
	 * http://www.vogella.com/tutorials/JavaAlgorithmsPrimeFactorization/article.html
	 * @param n is a BigInteger
	 * @return an array of BigInteger of size 2.
	 */
	private static BigInteger[] crackKey(BigInteger n) {
		BigInteger[] bigR = new BigInteger[2];
		for (BigInteger b = new BigInteger("2"); 
				b.compareTo(n.divide(b)) <= 0; b = b.add(new BigInteger("1"))) {
			System.out.println(b);
			if(n.mod(b).equals(new BigInteger("0")) & b.isProbablePrime(100) &
				(n.divide(b)).isProbablePrime(100)) {
				bigR[0] = b;
				bigR[1] = n.divide(b);
				return bigR;
			}
		}
		return null;
	}

	/**
	 * crackMessage() takes in an array of BigInteger and cracks the message using the
	 * public key e (exponent) and n (mod)
	 * @param b BigInteger array the represents a string.
	 * @param e exponent should be a relative prime of (p-1)(q-1)
	 * @param n is the product of two primes, p and q.
	 * @return a String
	 */
	public static BigInteger[] crackMessage(BigInteger[] b, BigInteger e, BigInteger n) {
		BigInteger p = crackKey(n)[0];
		BigInteger q = crackKey(n)[1];
		return rsaDecrypt(b, decryptKey(e, p, q), p, q);
	}

	/**
	 * decryptKey() takes in the exponent e, and two prime numbers p and 1
	 * @param e exponent should be a relative prime of (p-1)(q-1)
	 * @param p BigInteger p is some prime number 
	 * @param q BigInteger q is some prime number
	 * @return BigInteger d is the inverse of e mod (p-1)(q-1)
	 */
	public static BigInteger decryptKey(BigInteger e, BigInteger p, BigInteger q) {
		BigInteger one = new BigInteger("1");
		BigInteger returnVal = e.modInverse((p.subtract(one)).multiply(q.subtract(one)));
		return returnVal;
	}
		
	/**
	 * rsaDecrypt() takes in a Byte Array, a BigInteger decrypt key d, and BigIntegers prime 
	 * numbers p and q.
	 * @param b Array of Byte
	 * @param d BigInteger decrypt key
	 * @param p BigInteger p is some prime number
	 * @param q BigInteger q is some prime number
	 * @return
	 */
	public static BigInteger[] rsaDecrypt(BigInteger[] b, BigInteger d, BigInteger p, BigInteger q) {
		for (int i = 0; i < b.length; i++){
			b[i] = b[i].modPow(d, p.multiply(q));
		}
		return b;
	}
	
	public static BigInteger[] rsaDecrypt(BigInteger[] b, BigInteger d, BigInteger N) {
		for (int i = 0; i < b.length; i++){
			b[i] = b[i].modPow(d, N);
		}
		return b;
	}

}
	
