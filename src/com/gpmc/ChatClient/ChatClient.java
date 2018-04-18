package com.gpmc.ChatClient;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdesktop.application.Application;


import javax.swing.SwingUtilities;

/**
 * 
 * this is chat client GUI class
 *
 */

public class ChatClient extends javax.swing.JFrame {
	private JButton jButtonSendMessage;
	private JLabel jLabel1ServerIP;
	private JButton jButtonClose;
	private JTextField jTextFieldName;
	private JTextArea jTextArea1;
	private JScrollPane jScrollPane1;
	private JButton jButtonConnect;
	private JLabel jLabelName;
	private JTextField jTextFieldServerPort;
	private JLabel jLabelServerPort;
	private JTextField jTextFieldServerIP;
	private JTextField jTextFieldMessage;
	private Socket socket;
	private PrintWriter pw;
	private BufferedReader reader;
	private ClientMessageThread clientThread;
	
	
	private String ServerIP;
	private String ServerPort;
	private String name;

	public ChatClient(String name, String ip,String port) {
		super();
		this.name = name;
		this.ServerIP = ip;
		this.ServerPort = port;
		initGUI();
	}
	
	
	public void initGUI() {
		try {
			getContentPane().setLayout(null);
			{
				jButtonSendMessage = new JButton();
				getContentPane().add(jButtonSendMessage);
				jButtonSendMessage.setBounds(285, 234, 80, 22);
				jButtonSendMessage.setText("SendMessage");
				jButtonSendMessage.addActionListener(l -> sendMessage(jTextFieldMessage.getText()));
			}
			{
				jTextFieldMessage = new JTextField();
				getContentPane().add(jTextFieldMessage);
				
				jTextFieldMessage.setBounds(12, 232, 275, 26);
			}
			{
				jLabel1ServerIP = new JLabel();
				getContentPane().add(jLabel1ServerIP);
				jLabel1ServerIP.setBounds(7, 12, 92, 15);
				jLabel1ServerIP.setText("ServerIP");
			}
			{
				jTextFieldServerIP = new JTextField();
				getContentPane().add(jTextFieldServerIP);
				jTextFieldServerIP.setBounds(80, 9, 74, 22);
				jTextFieldServerIP.setText(ServerIP);
			}
			{
				jLabelServerPort = new JLabel();
				getContentPane().add(jLabelServerPort);
				jLabelServerPort.setBounds(180, 12, 65, 15);
				jLabelServerPort.setText("ServerPort");
			}
			{
				jTextFieldServerPort = new JTextField();
				getContentPane().add(jTextFieldServerPort);
				jTextFieldServerPort.setBounds(250, 9, 73, 22);
				jTextFieldServerPort.setText(ServerPort);
			}
			{
				jLabelName = new JLabel();
				getContentPane().add(jLabelName);
				jLabelName.setBounds(12, 47, 40, 15);
				jLabelName.setText("Name");
			}
			{
				jTextFieldName = new JTextField();
				getContentPane().add(jTextFieldName);
				jTextFieldName.setBounds(64, 44, 62, 22);
				jTextFieldName.setText(name);
			}
			{
				jButtonConnect = new JButton();
				getContentPane().add(jButtonConnect);
				jButtonConnect.setBounds(161, 44, 95, 22);
				jButtonConnect.setText("Connect");
				jButtonConnect.addActionListener(e-> {
					connect();
				});
			}
			{
				jScrollPane1 = new JScrollPane();
				getContentPane().add(jScrollPane1);
				jScrollPane1.setBounds(12, 71, 325, 149);
				{
					jTextArea1 = new JTextArea();
					jScrollPane1.setViewportView(jTextArea1);
					jTextArea1.setName("jTextArea1");
				}
			}
			{
				jButtonClose = new JButton();
				getContentPane().add(jButtonClose);
				jButtonClose.setBounds(275, 44, 45, 22);
				jButtonClose.setText("Close");
				jButtonClose.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if(closeConnection()==true)
							System.out.println("close successful!");
					}
				});
			}
			pack();
			setSize(400, 300);
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * connect chat client with chat server
	 */
	public void connect(){
		int port = Integer.parseInt(jTextFieldServerPort.getText());
		String ip = jTextFieldServerIP.getText();
		String name = jTextFieldName.getText();
		if(port == 0 || ip == null || name == null) {
			JOptionPane.showMessageDialog(this, "pls input information");
		}
		if(socket!=null)
			return;
		try {
			socket = new Socket(ip,port);
			pw = new PrintWriter(socket.getOutputStream());
			System.out.println(name);
			pw.println(name);  //send client name to server;
			pw.flush();
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//start clientMessageThread to receive new message
			clientThread = new ClientMessageThread(reader);
			clientThread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * send message to specific chat group port
	 * @param message
	 */
	public void sendMessage(String message) {
		pw.println(message);
		pw.flush();
		jTextFieldMessage.setText("");
	}
	
	/**
	 * 
	 * ClientMessageThread class, every chat client has a ClientMessageThread to receive message 
	 *
	 */
	public class ClientMessageThread extends Thread{
		private boolean flag = true; //true:run thread, false: stop thread
		private BufferedReader reader;
		public ClientMessageThread(BufferedReader reader) throws IOException {
			this.reader = reader;
		}
		public void stopRunning() throws IOException {
			flag = false;
			if(reader!=null)
				reader.close();
		}
		public void run() {
			while(flag) {
				String message;
				try {
					message = reader.readLine();
					jTextArea1.append(message + "\r\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
		}
	}
	
	/**
	 * close client and server connection 
	 * @return  true: close successful
	 * 			false: close fail
	 */
	public boolean closeConnection(){
		
		try {
			clientThread.stopRunning();
			if(reader!=null)
				reader.close();;
			if(pw!=null)
				pw.close();
			if(socket!=null)
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
		}
		
		return true;
	}
	
	
	

}
