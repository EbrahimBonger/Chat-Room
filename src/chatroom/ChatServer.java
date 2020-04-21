package chatroom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * YOUR NAME HERE
 */
public class ChatServer extends ChatWindow {
	private ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
	//private ClientHandler handler;

	public ChatServer(){
		super();
		this.setTitle("Chat Server");
		this.setLocation(80,80);

		try {
			// Create a listening service for connections
			// at the designated port number.
			ServerSocket srv = new ServerSocket(2113);

			while (true) {
				// The method accept() blocks until a client connects.
				printMsg("Waiting for a connection");
				Socket socket = srv.accept();
				ClientHandler handler = new ClientHandler(socket);
				clients.add(handler);
				handler = new ClientHandler(socket);
				//handler.handleConnection();
				handler.connect();
			}

		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/** This innter class handles communication to/from one client. */
	class ClientHandler implements Runnable {
		private PrintWriter writer;
		private BufferedReader reader;

		public ClientHandler(Socket socket) {
			try {
				InetAddress serverIP = socket.getInetAddress();
				printMsg("Connection made to " + serverIP);
				writer = new PrintWriter(socket.getOutputStream(), true);
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			}
			catch (IOException e){
				printMsg("\nERROR:" + e.getLocalizedMessage() + "\n");
			}
		}
		public void handleConnection() {
			try {
				while(true) {
					// read a message from the client
					//readMsg();
					sendMsg(readMsg());
				}
			}
			catch (IOException e){
				printMsg("\nERROR:" + e.getLocalizedMessage() + "\n");
			}

		}

		/** Receive and display a message */
		public String readMsg() throws IOException {
			String s = reader.readLine();
			printMsg(s);
			//sendMsg(s);
			return s;
		}
		/** Send a string */
		public void sendMsg(String s){
			//writer.println(s);
			for(ClientHandler client: clients){
				client.writer.println(s);
			}

		}
		public void connect(){
			Thread th = new Thread(this);
			th.start();
		}

		@Override
		public void run() {
			handleConnection();
		}
	}

	public static void main(String args[]){
		new ChatServer();
	}
}