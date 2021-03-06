package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import clientServer.VPN;
import authentication.Driver;

/*
 * Author: Group 8
 * Last Modified: October 15, 2014
 * Course: EECE 412, Assignment 3
 * Purpose: Implements GUI and initiates Client-Server connection.
 */

public class Gui {
	private JFrame mainFrame;
	private JPanel controlPanel;
	private JPanel userTypePanel;
	private String clientPortNum;
	private String serverPortNum;
	private String ipAdd;

	private String serverMsg;
	private String clientMsg;
	private String sharedKeyClient;
	private String sharedKeyServer;

	private ArrayList<String> logMsg_client;
	private ArrayList<String> logMsg_server;
	private int counter_client = -1;
	private int counter_server = -1;

	private boolean isServerConnected; // to check if message can be sent over
	private boolean isClientConnected; // to check if message can be sent over

	private JTextField displayMsgFieldOnServer;
	private JTextField displayMsgFieldOnClient;

	private static JTextArea displayLogServer;
	private static JTextArea displayLogClient;
	
	private static VPN myVPN = new VPN();
	private static Driver an = new Driver();
	
    char[] charArrS ={ 't', 'r', 'o', 'p', 'i', 'c', 'a', 'l' }; // testing
    char[] charArrC ={ 'i', 's', 'l', 'a', 'n', 'd' }; // testing
    
    //private boolean DoneClientAuthentication = false; // true is done with authenticating, false is not
    //private boolean DoneServerAuthentication = false; // true is done with authenticating, false is not

	public Gui() {
		prepareGUI();
	}

	private void prepareGUI() {
		mainFrame = new JFrame("VPN EECE 412");
		mainFrame.setSize(600, 800);
		mainFrame.setLayout(new FlowLayout());

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});

		controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(2, 1));

		userTypePanel = new JPanel();
		userTypePanel.setLayout(new FlowLayout());

		mainFrame.add(userTypePanel);
		mainFrame.add(controlPanel);
		mainFrame.setVisible(true);
	}

	private void showLayout() {

		final JPanel panel = new JPanel();
		panel.setBackground(Color.CYAN);
		panel.setSize(400, 500);

		CardLayout layout = new CardLayout();
		panel.setLayout(layout);

		// ------------------ step panel --------------------------------//

		final JPanel stepPanel = new JPanel();
		GroupLayout stepLayout = new GroupLayout(stepPanel);
		stepLayout.setAutoCreateGaps(true);
		stepLayout.setAutoCreateContainerGaps(true);
				

		stepLayout.setHorizontalGroup(stepLayout.createSequentialGroup()
				.addGroup(
						stepLayout
								.createParallelGroup(
										GroupLayout.Alignment.LEADING)));

		stepLayout.setVerticalGroup(stepLayout.createSequentialGroup());

		stepPanel.setLayout(stepLayout);


		// ---------------- message box
		// ---------------------------------------------//

		// for the server
		final JPanel serverMsgPanel = new JPanel();
		final JLabel serverHeaderLabel = new JLabel("MESSAGE FROM THE SERVER");
		final JLabel serverMsgLabel = new JLabel("Enter your message here");
		final JTextField serverMsgTextField = new JTextField(40);
		final JLabel serverDisplayMsgLabel = new JLabel("Received message");
		
		displayLogServer = new JTextArea(10, 10);
		displayLogServer.setLineWrap(true);
		
		JScrollPane scrollpane_server = new JScrollPane(displayLogServer);
		
		serverMsgPanel.setLayout(new GridLayout(7, 1));
		displayMsgFieldOnServer = new JTextField(40);
		displayMsgFieldOnServer
				.setText("Waiting for a message...");
		displayMsgFieldOnServer.setOpaque(false);
		serverMsgPanel.add(serverHeaderLabel);
		serverMsgPanel.add(serverMsgLabel);
		serverMsgPanel.add(serverMsgTextField);
		serverMsgPanel.add(serverDisplayMsgLabel);
		serverMsgPanel.add(displayMsgFieldOnServer);

		// for the client
		final JPanel clientMsgPanel = new JPanel();
		final JLabel clientHeaderLabel = new JLabel("MESSAGE FROM THE CLIENT");
		final JLabel clientMsgLabel = new JLabel("Enter a message here");
		final JTextField clientMsgTextField = new JTextField(40);
		final JLabel clientDisplayMsgLabel = new JLabel("Received message");

		clientMsgPanel.setLayout(new GridLayout(7, 1));
		displayMsgFieldOnClient = new JTextField(40);
		displayMsgFieldOnClient
				.setText("Waiting for a message...");
		displayMsgFieldOnClient.setOpaque(false);
		clientMsgPanel.add(clientHeaderLabel);
		clientMsgPanel.add(clientMsgLabel);
		clientMsgPanel.add(clientMsgTextField);
		clientMsgPanel.add(clientDisplayMsgLabel);
		clientMsgPanel.add(displayMsgFieldOnClient);

		// ----------- SERVER -------------------------------------//

		JPanel serverPanel = new JPanel();
		GroupLayout serverLayout = new GroupLayout(serverPanel);
		serverLayout.setAutoCreateGaps(true);
		serverLayout.setAutoCreateContainerGaps(true);

		JLabel serverPortNumLabel = new JLabel("Enter port number");
		final JTextField serverPortNumText = new JTextField(40);
		JLabel sharedKeyServerLabel = new JLabel("Enter shared key");
		final JTextField sharedKeyServerText = new JTextField(20);
		JButton connectServerBtn = new JButton("Connect");
		// JButton keyServerBtn = new JButton("Set key");
		JButton sendServerMessageBtn = new JButton("Send");
		JButton cancelServerBtn = new JButton("Clear");
		final JLabel stepLabelServer = new JLabel("PROGRAM LOG");
		final JButton stepContinueBtnServer = new JButton("Continue");
		
		
		serverLayout.setHorizontalGroup(serverLayout.createSequentialGroup()
			.addGroup(
					serverLayout
							.createParallelGroup(
									GroupLayout.Alignment.LEADING)
							.addComponent(serverPortNumLabel)
							.addComponent(serverPortNumText)
							.addComponent(sharedKeyServerLabel)
							.addComponent(sharedKeyServerText)
							.addGroup(
									serverLayout
											.createSequentialGroup()
											.addComponent(connectServerBtn)
											// .addComponent(keyServerBtn)
											.addComponent(
													sendServerMessageBtn)
											.addComponent(cancelServerBtn))
							.addComponent(serverMsgPanel)
							.addComponent(stepLabelServer)
							.addComponent(scrollpane_server)
							.addComponent(stepContinueBtnServer)
							)
				);

		serverLayout.setVerticalGroup(serverLayout
				.createSequentialGroup()
				.addComponent(serverPortNumLabel)
				.addComponent(serverPortNumText)
				.addComponent(sharedKeyServerLabel)
				.addComponent(sharedKeyServerText)
				.addGroup(
						serverLayout
								.createParallelGroup(
										GroupLayout.Alignment.LEADING)
								.addComponent(connectServerBtn)
								.addComponent(sendServerMessageBtn)
								.addComponent(cancelServerBtn))
				.addComponent(serverMsgPanel)
				.addComponent(stepLabelServer)
				.addComponent(scrollpane_server)
				.addComponent(stepContinueBtnServer));

		connectServerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearLogMsgServer("");
				serverPortNum = serverPortNumText.getText();
				sharedKeyServer = sharedKeyServerText.getText();
				if (serverPortNum.equals(""))
					displayLogServer.append("Port number is missing.");
				else {
					int portNumber = Integer.parseInt(serverPortNum);

					// Assume that initially the mode is set to authentication.	
					
					// Call VPN package to set up the server
					myVPN.runServerThread(portNumber);
					
					// When done with authentication, set mode to encryption.
					//myVPN.setServerMode(false);

					
					
					/***
					 * might need to change logic of checking if server is
					 * connected later on
					 ***/
					
					isServerConnected = true;
					
					displayLogServer.append("Server socket created at port number: " + serverPortNum);
				}
			}
		});

		/**
		 * Currently, this button does not close the server. (Do we need it,
		 * anyway?)
		 */
		cancelServerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isServerConnected = false;
				myVPN.setClientMode(true);
				myVPN.setServerMode(true);
				serverPortNumText.setText("");
				sharedKeyServerText.setText("");
				serverMsgTextField.setText("");
				displayLogServer.setText("Clear server");
			}
		});

		sendServerMessageBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//serverPortNumText.setText("");
				// sharedKeyServerText.setText("");

				if (isServerConnected  && !myVPN.returnMode()) {
					serverMsg = serverMsgTextField.getText();
					displayLogServer.append("Sending server message: " + serverMsg+"\n");
					
					myVPN.sendServerMessage();
					//myVPN.sendAuthServerMessage(charArrS); // testing
					
					System.out.println("You clicked SEND SERVER button");				
										
				} else {
					serverMsg = "";
					displayLogServer
							.append("Connect to a client before sending a message"
									+ clientMsg+"\n"); // clientMsg is there to check
													// if string is cleared
				}
			}
		});
		
		stepContinueBtnServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (counter_server != -1){
					if (counter_server != logMsg_server.size()) {
						String tmp = "";
						displayLogServer.append(logMsg_server.get(counter_server));
						//System.out.println(counter_server);
						//System.out.println(logMsg_server.size());
						counter_server++;
					}
				}
			}
		});

		// ----------- CLIENT -------------------------------------//

		JPanel clientPanel = new JPanel();
		GroupLayout clientLayout = new GroupLayout(clientPanel);
		clientLayout.setAutoCreateGaps(true);
		clientLayout.setAutoCreateContainerGaps(true);

		JLabel clientPortNumLabel = new JLabel("Enter port number");
		final JTextField clientPortNumTextField = new JTextField(40);
		JLabel ipAddLabel = new JLabel("Enter IP Address");
		final JTextField ipAddTextField = new JTextField(20);
		JLabel sharedKeyClientLabel = new JLabel("Enter shared key");
		final JTextField sharedKeyClientText = new JTextField(20);
		JButton connectClientBtn = new JButton("Connect");
		JButton sendClientMessageBtn = new JButton("Send");
		JButton cancelClientBtn = new JButton("Clear");
		final JLabel stepLabelClient = new JLabel("PROGRAM LOG");
		final JButton stepContinueBtnClient = new JButton("Continue");
		
		displayLogClient = new JTextArea(10, 10);
		displayLogClient.setLineWrap(true);
		JScrollPane scrollpane_client = new JScrollPane(displayLogClient);
		
		clientLayout.setHorizontalGroup(clientLayout.createSequentialGroup()
				.addGroup(
						clientLayout
								.createParallelGroup(
										GroupLayout.Alignment.LEADING)
								.addComponent(clientPortNumLabel)
								.addComponent(clientPortNumTextField)
								.addComponent(ipAddLabel)
								.addComponent(ipAddTextField)
								.addComponent(sharedKeyClientLabel)
								.addComponent(sharedKeyClientText)
								.addGroup(
										clientLayout
												.createSequentialGroup()
												.addComponent(connectClientBtn)
												// .addComponent(keyClientBtn)
												.addComponent(
														sendClientMessageBtn)
												.addComponent(cancelClientBtn))
								.addComponent(clientMsgPanel)
								.addComponent(stepLabelClient)
								.addComponent(scrollpane_client)
								.addComponent(stepContinueBtnClient)));

		clientLayout.setVerticalGroup(clientLayout
				.createSequentialGroup()
				.addComponent(clientPortNumLabel)
				.addComponent(clientPortNumTextField)
				.addComponent(ipAddLabel)
				.addComponent(ipAddTextField)
				.addComponent(sharedKeyClientLabel)
				.addComponent(sharedKeyClientText)
				.addGroup(
						clientLayout
								.createParallelGroup(
										GroupLayout.Alignment.LEADING)
								.addComponent(connectClientBtn)
								// .addComponent(keyClientBtn)
								.addComponent(sendClientMessageBtn)
								.addComponent(cancelClientBtn))
				.addComponent(clientMsgPanel)
				.addComponent(stepLabelClient)
				.addComponent(scrollpane_client)
				.addComponent(stepContinueBtnClient));

		connectClientBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearLogMsgClient("");
				clientPortNum = clientPortNumTextField.getText();
				ipAdd = ipAddTextField.getText();
				sharedKeyClient = sharedKeyClientText.getText();
				if (clientPortNum.equals(""))
					clearLogMsgClient("Port number is missing");
				else if (ipAdd.equals(""))
					clearLogMsgClient("IP address is missing");
				else {
					/***
					 * might need to change logic of checking if client is
					 * connected later on
					 ***/
					isClientConnected = true;

					// Assume that initially the mode is set to authentication.
					
					// Call VPN package to set up the client
					int clientPortNumber = Integer.parseInt(clientPortNum);
					myVPN.runClientThread(clientPortNumber, ipAdd);
					
					// When done with authentication, set mode to encryption.
					//myVPN.setClientMode(false);

					displayLogClient.append("IP Address " + ipAdd
							+ "\nPort number: " + clientPortNum+"\n");
				}
			}
		});

		cancelClientBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isClientConnected = false;
				myVPN.setClientMode(true);
				myVPN.setServerMode(true);
				clientPortNumTextField.setText("");
				ipAddTextField.setText("");
				sharedKeyClientText.setText("");
				clientMsgTextField.setText("");
				displayLogClient.setText("Clear client");
			}
		});

		sendClientMessageBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sharedKeyClient = sharedKeyClientText.getText();

				if (isClientConnected && !myVPN.returnMode()) {
					clientMsg = clientMsgTextField.getText();
					displayLogClient.append("Sending client message: " + clientMsg+"\n");

					myVPN.sendClientMessage();
					
					System.out.println("You clicked SEND CLIENT button");
				} else {
					clientMsg = "";
					clearLogMsgClient("Connect to a client before sending a message:"
							+ serverMsg);

				}
			}
		});
		
		stepContinueBtnClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (counter_client != -1){

					if (counter_client != logMsg_client.size()) {
						String tmp = "";
						displayLogClient.append(logMsg_client.get(counter_client));
						counter_client++;

					}
				}
			}
		});

		serverPanel.setLayout(serverLayout);
		clientPanel.setLayout(clientLayout);
		panel.add("Server", serverPanel);
		panel.add("Client", clientPanel);

		// ---------------- user type selection
		// -------------------------------------//

		final DefaultComboBoxModel panelName = new DefaultComboBoxModel();

		panelName.addElement("Server");
		panelName.addElement("Client");

		final JComboBox listCombo = new JComboBox(panelName);
		listCombo.setSelectedIndex(0);

		JScrollPane listComboScrollPane = new JScrollPane(listCombo);

		JButton selectBtn = new JButton("Select");

		selectBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String data = "";
				if (listCombo.getSelectedIndex() != -1) {
					CardLayout cardLayout = (CardLayout) (panel.getLayout());
					cardLayout.show(panel, (String) listCombo
							.getItemAt(listCombo.getSelectedIndex()));
				}
			}
		});

		userTypePanel.add(listComboScrollPane);
		userTypePanel.add(selectBtn);
		controlPanel.add(panel);
		controlPanel.add(stepPanel);

		mainFrame.setVisible(true);
	}

	public String getClientPortNum() {
		return clientPortNum;
	}

	public String getIpAdd() {
		return ipAdd;
	}

	public String getServerMsg() {
		return serverMsg;
	}

	public String getClientMsg() {
		return clientMsg;
	}

	public void displayMsgOnServer(String input) {
		displayMsgFieldOnServer.setText(input);
	}

	public void displayMsgOnClient(String input) {
		displayMsgFieldOnClient.setText(input);
	}

	public void setLogMsg(ArrayList<String> log,ArrayList<String> log2) {
		counter_client = 0;
		logMsg_client = log;
		
		counter_server = 0;
		logMsg_server = log2;
	}
	
	public void clearLogMsgClient(String str){
		if (str == "")
			displayLogClient.setText(str);
		else{
			displayLogClient.setText("");
			displayLogClient.append(str);
		}
	}
	public void clearLogMsgServer(String str){
		if (str == "")
			displayLogServer.setText(str);
		else{
			displayLogServer.setText("");
			displayLogServer.append(str);
		}
	}
	
	public String getSharedKeyClient() {
		return sharedKeyClient;
	}

	public String getSharedKeyServer() {
		return sharedKeyServer;
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		Gui showGUI = new Gui();
		showGUI.showLayout();

		// Pass the current GUI object to the VPN
		myVPN.setGUI(showGUI);

	}
	
	public static void displayLogAppend(String str){
		displayLogClient.append(str);
	}
}