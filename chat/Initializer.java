package chat;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 * Main method for RSA Chat application.
 * Allows user to join or host a server which others can join.
 * Easy to use if everyone is on same network, otherwise port forwarding
 * must be set up.
 * 
 * Join "localhost" in IP spot with corresponding host to connect to own computer.
 * @author Jared
 *
 */
public class Initializer extends JFrame implements ActionListener {

	public final static String DEFAULT_PORT = "33333";
	public final static String JOIN_IP_MESSAGE = "Put IP here";
	public final static String JOIN_PORT_MESSAGE = "Put Port here";
	public final static String TITLE = "RSA Encryption Demonstrator";

	ChatHub hub;
	ChatWindow window;

	JCheckBox host, join;
	JTextField hostPort, joinPort, joinIP, name;
	JButton start;
	JLabel error;

	public static void main(String[] args) {

		Initializer window = new Initializer();

		window.setVisible(true);
	}


	public Initializer() {
		setSize(400, 200);
		setTitle(TITLE);

		JPanel borderHolder = new JPanel();//for formatting
		JPanel innerHolder = new JPanel();

		host = new JCheckBox("Host");
		join = new JCheckBox("Join");
		hostPort = new JTextField(DEFAULT_PORT, 5);
		joinPort = new JTextField(JOIN_PORT_MESSAGE, 15);
		joinIP = new JTextField(JOIN_IP_MESSAGE, 15);
		start = new JButton("Start");
		error = new JLabel("");
		name = new JTextField("Your name");

		host.addActionListener(this);
		join.addActionListener(this);
		start.addActionListener(this);

		innerHolder.setLayout(new GridLayout(2,3));
		innerHolder.add(join);

		innerHolder.add(joinIP);
		innerHolder.add(joinPort);
		innerHolder.add(host);
		innerHolder.add(hostPort);
		innerHolder.add(error);


		borderHolder.setLayout(new BorderLayout());
		borderHolder.add(innerHolder, BorderLayout.CENTER);
		borderHolder.add(start, BorderLayout.SOUTH);
		borderHolder.add(name, BorderLayout.NORTH);
		add(borderHolder);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Handles:
	 * -Start button
	 * -Join box
	 * -Host box
	 */
	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();
		if(source == host) {
			setEnabledJoin(!host.isSelected());
		}
		else if(source == join) {
			setEnabledHost(!join.isSelected());
		}
		else if(source == start) {
			if(host.isSelected()) {
				try {
					int port = Integer.parseInt(hostPort.getText());
					hub = new ChatHub(Integer.parseInt(hostPort.getText()));//make server

					window = new ChatWindow("localhost",
							Integer.parseInt(hostPort.getText()), name.getText());
				} catch (IllegalArgumentException e1) {
					hub = null;
					error.setText("Invalid port");
					revalidate();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if(join.isSelected()) {
				try {
					window = new ChatWindow(joinIP.getText(), 
							Integer.parseInt(joinPort.getText()), name.getText());
				} catch (NumberFormatException e1) {
					error.setText("Invalid port: "+joinPort.getText());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

	}

	/**
	 * If Host checkbox is checked or unchecked, enables or disables
	 * Join fields accordingly.
	 * @param b
	 */
	private void setEnabledJoin(boolean b) {
		join.setEnabled(b);
		joinPort.setEnabled(b);
		joinIP.setEnabled(b);
	}

	/**
	 * if Join checkbox is checked or unchecked, enables or disables
	 * Host fields accordingly.
	 * @param b
	 */
	private void setEnabledHost(boolean b) {
		host.setEnabled(b);
		hostPort.setEnabled(b);
	}
}
