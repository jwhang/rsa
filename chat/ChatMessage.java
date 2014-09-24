package chat;

import java.io.Serializable;
import java.math.BigInteger;

import encryption.Convert;
import encryption.Encrypt;
/**
 * Type of message for sending message to other users.
 * Sent to ChatHub, which then sends it to all users.
 * @author Jared
 *
 */
public class ChatMessage implements Serializable {
	
	BigInteger N, e;
	int source;//sender of message
	private byte[] byteMessage;
	private BigInteger[] encodedMessage;
	
	public ChatMessage(String message, BigInteger N, BigInteger e, int source) {
		byteMessage = Convert.stringToBytesASCII(message);
		encodedMessage = Encrypt.rsaEncrypt(byteMessage, e, N) ;
		this.source = source;
	}
	
	public BigInteger[] getEncodedMessage() {
		return encodedMessage;
	}
	public int getSource() {
		return source;
	}
}
