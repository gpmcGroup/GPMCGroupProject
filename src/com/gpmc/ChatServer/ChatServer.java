package com.gpmc.ChatServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

//for server, is quite easy, just need to create a listener for specific port

//when a server receives a new connect client, create a new thread to handle it.
public class ChatServer extends Thread{
	//every team has a teamMessageList, a new login will create a new teamMessageList
	
	//in real situation, every team could have a serverPort
	private int port;  
//	private int[] clientListeningPort = {8001,8002};// one team, one server port, in following version to solve that.
	
	private ServerSocket server;
	
	
	
	//thread list
	private List<ClientThread> ClientThreadList;   //this thread is used to provide service to every user
	
	public ChatServer(int port) {
		this.port = port;
	}
	
	public static void main(String[] args) {
		ChatServer server = new ChatServer(9999);
		server.initialServer();
		server.start();
	}
	
	public void initialServer() {
		//server need to receive message from client, and transfer message between clients
		//so, server needs a serverSocket to listen client message, and has multiple sockets to send message.
		try {
			ClientThreadList = new ArrayList<ClientThread>();
			server = new ServerSocket(port);
			System.out.println(port + ": Server Open");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(true) {
			try {
				//waiting connections from client and create a ClientThread to handle it.
				Socket socket = server.accept();
				ClientThread clientThread = new ClientThread(socket);
				ClientThreadList.add(clientThread);
				clientThread.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public class ClientThread extends Thread{
		private Socket socket;
		private String ClientName;
		private BufferedReader reader;
		private PrintWriter pw;
		public PrintWriter getPrintWriter() {
			return pw;
		}
		
		public ClientThread(Socket socket) {
			this.socket = socket;
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				ClientName = reader.readLine();  // First time connect, the client sends own name.
				pw = new PrintWriter(socket.getOutputStream());
				pw.println(ClientName + " connect successful!");
				pw.flush();
				System.out.println(ClientName + " has connected!");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public void run() {
			String message = null;
			while(true) {
				try {
					message = reader.readLine(); //receive message from client;
					if(message == null) {
						socket.close();
						break;
					}
					
					broadCastMessage(message);  //broadCastMessage to every Client
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//this method is used to broadCast new messages to every client
		public void broadCastMessage(String message) {
			for(int i = 0 ; i < ClientThreadList.size() ; i++) {
				ClientThreadList.get(i).getPrintWriter().println(ClientName + " says: " + message);
				ClientThreadList.get(i).getPrintWriter().flush(); //must flush!
			}
		}
	}
	
	
}