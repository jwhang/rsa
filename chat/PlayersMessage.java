package chat;

import java.io.Serializable;

public class PlayersMessage implements Serializable {
	
	Player[] players;
	
	public PlayersMessage(Player[] players) {
		this.players = players;
	}

}
