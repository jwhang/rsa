package chat;
import java.io.IOException;

import netgame.common.*;

public class ChatHub extends Hub{

	protected static final int MAX_PLAYERS = 100;
	Player[] players;
	public ChatHub(int port) throws IOException {
		super(port);
		players = new Player[MAX_PLAYERS];
		setAutoreset(true);
	}
	protected void messageReceived(int playerID, Object obj) {
		if(obj instanceof ChatMessage) {
			ChatMessage message = (ChatMessage) obj;
			if(playerID != message.getSource()) {
				return;
			}
			else {
				super.messageReceived(playerID, obj);
			}
		}
		else if(obj instanceof KeyMessage) {
			KeyMessage message = (KeyMessage) obj;
			players[message.playerID] = new Player(message.N, message.e, message.playerID, message.name);
			sendToAll(new PlayersMessage(players));
		}
	}
	public Player[] getPlayers() {
		return players;
	}
	protected void playerConnected(int playerID) {
		sendToAll(new PlayersMessage(players));
	}
	protected void playerDisconnected(int playerID) {
		System.out.println("disconnect");
		players[playerID] = null;
		sendToAll(new PlayersMessage(players));
	}
}
