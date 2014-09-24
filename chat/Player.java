package chat;

import java.io.Serializable;
import java.math.BigInteger;
/**
 * Class for storing information about a user.
 * @author Jared
 *
 */
public class Player implements Serializable{
	BigInteger N, e;
	int playerID;
	String name;
	public Player(BigInteger N, BigInteger e, int playerID, String name) {
		this.N = N;
		this.e = e;
		this.playerID = playerID;
		this.name = name;
	}
	
}
