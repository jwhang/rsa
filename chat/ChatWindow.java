package chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.math.BigInteger;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import encryption.Convert;
import encryption.Decrypt;
import encryption.Encrypt;
import netgame.common.Client;
import netgame.common.ForwardedMessage;
/**
 * ChatWindow both creates a ChatClient for communication with a ChatHub
 * as well as displays messages from other users connected to the same hub.
 * 
 * It generates a p, q, e, and d for receives messages.
 * @author Jared
 *
 */
public class ChatWindow extends JFrame implements KeyListener {

	ChatClient connection; //client that will connect to hub
	JPanel inner, rightGrid, numContainer; //formatting
	JTextArea log; //chat log
	JTextField message, NSend, eSend, dText;
	JTextArea clients; //list of connnected users
	JScrollPane scroll;
	JLabel NLabel, eLabel, dLabel, idLabel, NSendLabel, eSendLabel;
	JCheckBox showEncoded, showBytes;

	private int myID;
	private String name;
	private BigInteger p, q, N, e, d;

	public ChatWindow(String hostName, int port, String name) throws IOException {
		super("RSA Chat");
		setSize(1000, 1000);
		
		clients = new JTextArea("Connected users:");
		clients.setLineWrap(true);
		
		connection = new ChatClient(hostName, port);
		
		this.name = name;

		do { //select parameters for encryption
			Encrypt.encryptBytes();
			p = Encrypt.pickPrime();
			q = Encrypt.pickPrime();
			e = Encrypt.pickPrime();
			N = p.multiply(q);
			d = Decrypt.decryptKey(e, p, q);
		} while(e.compareTo(N) >= 0);

		NLabel = new JLabel("Your N: "+N); // display your encryption params
		eLabel = new JLabel("Your e: "+e);
		dLabel = new JLabel("Decrypt with: ("+d+")");
		idLabel = new JLabel("Your Name: "+name);

		NSend = new JTextField(N.toString()); // changeable parameters
		eSend = new JTextField(e.toString());
		NSendLabel = new JLabel("Send mod N: ");
		eSendLabel = new JLabel("Send to power e: ");
		dText = new JTextField(d.toString());
		dText.setPreferredSize(new Dimension(1, 20));

		
		//check boxes for what you want displayed in chat log:
		showEncoded = new JCheckBox("Show Encoded Integers");
		showEncoded.setSelected(true);
		showBytes = new JCheckBox("Show Decoded Bytes");
		showBytes.setSelected(true);

		//container panel for formatting
		inner = new JPanel();
		inner.setSize(500, 500);
		inner.setLayout(new BorderLayout());

		//adding pretty borders
		log = new JTextArea("CHAT LOG", 5, 5);
		log.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
		log.setLineWrap(true);
		log.setEditable(false);
		
		message = new JTextField();
		message.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
		message.addKeyListener(this);

		clients.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
		clients.setEditable(false);
		scroll = new JScrollPane(log);
		inner.add(scroll, BorderLayout.CENTER);

		numContainer = new JPanel();//adding everything to container
		numContainer.setLayout(new GridLayout(0, 1));
		numContainer.add(idLabel);
		numContainer.add(NLabel);
		numContainer.add(eLabel);
		numContainer.add(dLabel);
		numContainer.add(dText);
		numContainer.add(NSendLabel);
		numContainer.add(NSend);
		numContainer.add(eSendLabel);
		numContainer.add(eSend);

		rightGrid = new JPanel();
		rightGrid.setLayout(new GridLayout(2, 1));
		rightGrid.add(clients);
		rightGrid.add(numContainer);
		rightGrid.add(showEncoded);
		rightGrid.add(showBytes);

		inner.add(message, BorderLayout.SOUTH);
		setLayout(new GridLayout(1, 2));
		add(inner);
		add(rightGrid);
		
		connection.send(new KeyMessage(e, N, myID, name));//inform server of keys
		
		setVisible(true);
	}
	/**
	 * This is how user communicates
	 * with ChatHub.
	 * @author Jared
	 *
	 */
	public class ChatClient extends Client{

		Player[] players; //list of other players
		
		public ChatClient(String hubHostName, int hubPort) throws IOException {
			super(hubHostName, hubPort);
			
			myID = getID();
			players = new Player[100];
		}

		/**
		 * If ChatMessage is received:
		 * 		Displays and decodes chat message using numbers in text fields.
		 * If PlayersMessage is received:
		 * 		Updates player list.
		 */
		protected void messageReceived(Object obj) {
			if(obj instanceof ForwardedMessage) {
				ChatMessage message = (ChatMessage) ((ForwardedMessage) obj).message;
				BigInteger[] encodedMessage = message.getEncodedMessage();
				log.setText(log.getText()+"\n"+"Message from "+players[message.getSource()].name+": ");
				if(showEncoded.isSelected()) {
					log.setText(log.getText()+"\n"+"Encoded: "+bigIntArrayToString(encodedMessage));
				}
				BigInteger[] decryptedMessage = Decrypt.rsaDecrypt(encodedMessage, new BigInteger(dText.getText()), N);
				if(showBytes.isSelected()) {
					log.setText(log.getText()+"\n" +"Decoded with key "+dText.getText()+": "+bigIntArrayToString(decryptedMessage));
				} try {
					log.setText(log.getText()+"\n" +"Message: "+Convert.bytesToString(Convert.bigIntegerToByte(decryptedMessage))+"\n");
				} catch(Exception e) {
					log.setText(log.getText()+"\n" + "Message: " + "Decoded message cannot be contained by integers. " + "\n");
				}
				revalidate();
			}
			else {
				PlayersMessage message = (PlayersMessage) obj;
				players = message.players;
				updatePlayers();
			}
		}
		/**
		 * Rewrites the player list based on new information
		 * from the ChatHub.
		 */
		private void updatePlayers() {
			String text = "Connected users:\n";
			for(int i=0;i<players.length;i++) {
				if(players[i] == null) {
					continue;
				}
				text+=players[i].name+" N: "+players[i].N.toString()+" e: "+
						players[i].e.toString()+"\n";
			}
			clients.setText(text);
			revalidate();
		}
	}
	
	/**
	 * For showing the encoded integers in a message
	 * @param array array of integers
	 * @return string list of integers
	 */
	private String bigIntArrayToString(BigInteger[] array) {
		String concat = "[ ";
		for(int i=0;i<array.length;i++) {
			concat += array[i]+", ";
		}
		concat += "]";
		return concat;

	}



	/**
	 * Sends message when Enter is pressed.
	 */
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			connection.send(new ChatMessage(message.getText(), new BigInteger(NSend.getText()),
					new BigInteger(eSend.getText()), myID));
			message.setText("");
		}

	}
	
	
	
	//----------------------not used---------------------------
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}


}
