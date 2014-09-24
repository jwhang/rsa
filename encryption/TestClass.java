package encryption;

import java.math.BigInteger;

public class TestClass {

	public static void main(String args[]) {
		String a = "Hello World!";
		byte[] b = Convert.stringToBytesASCII(a);
		BigInteger e = new BigInteger("15485867");
		BigInteger p = new BigInteger("961748927");
		BigInteger q = new BigInteger("982451653");
		BigInteger[] big = Encrypt.rsaEncrypt(b, e, p.multiply(q));
//		for(int i = 0; i < big.length; i++) {
//			System.out.println(big[i]);
//		}
//		BigInteger d = Decrypt.decryptKey(e, p, q);
//		BigInteger[] decrypted = Decrypt.rsaDecrypt(big, d, p, q);
//		byte[] bite = Convert.bigIntegerToByte(big);
//		String goal = Convert.bytesToString(bite);
//		System.out.println(goal);
//		BigInteger ab = new BigInteger("3");
//		BigInteger bc = new BigInteger("2");
//		System.out.println(ab.divide(bc));
//		long as = System.currentTimeMillis();
//		BigInteger n[] = decrypt.primeFactors(p.multiply(q));
//		long bs = System.currentTimeMillis();
//		System.out.println(n[1] + " " + n[0]);
//		System.out.println(bs-as);
//		System.out.println(Math.random());

		
	}
}

