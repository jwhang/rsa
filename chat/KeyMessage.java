package chat;

import java.io.Serializable;
import java.math.BigInteger;
/**
 * Type of message for informing server of public keys N and e.
 * @author Jared
 *
 */
public class KeyMessage implements Serializable {
	
	BigInteger N, e;
	int playerID;
	String name;
	public KeyMessage(BigInteger e, BigInteger N, int playerID, String name) {
		this.N = N;
		this.e = e;
		this.playerID = playerID;
		this.name = name;
	}
}
