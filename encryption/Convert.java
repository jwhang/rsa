package encryption;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class Convert {

	/**
	 * stringToBytesASCII() takes in a string and returns an array of
	 * bytes, each byte representing a character.
	 * 
	 * src: http://examples.javacodegeeks.com/
	 * Byron Kiourtzoglou
	 * 
	 * @param str any String
	 * @return array of Bytes
	 */
	public static byte[] stringToBytesASCII(String str) {
		byte[] b = new byte[str.length()];
		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) str.charAt(i);
		}
		return b;
	}
	
	/**
	 * bigIntegerToByte() takes in an array of BigInteger and return an
	 * array of Bytes
	 * @param big Array of BigInteger
	 * @return array of Bytes
	 */
	public static byte[] bigIntegerToByte(BigInteger[] big) throws Exception {
		byte[] b = new byte[big.length];
		for(int i = 0; i < big.length; i++) {
			BigInteger bInt = new BigInteger("255"); 
			int hold = Integer.parseInt((big[i].mod(bInt)).toString());
			b[i] = (byte) hold;
		}
		return b;
	}
	
	/**
	 * bytesToString() takes in an array of Byte and returns an string
	 * 
	 * src: http://examples.javacodegeeks.com/
	 * Byron Kiourtzoglou
	 * 
	 * @param b array of bytes
	 * @return some String
	 */
	public static String bytesToString(byte[] b) {
		String value = "Error";
		try {
			value = new String(b, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	public static void main(String args[]) {
		String hello = "Hello World!";
		byte[] b = stringToBytesASCII(hello);
		String newhello = bytesToString(b);
		System.out.println(newhello);
	}
	
	
	

}
