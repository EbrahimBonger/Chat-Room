package chatroom;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * EBRAHIM BONGER
 */
public class ChatServer extends ChatWindow {
	/** create arrayList to store client(s)**/
	private ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();

	public ChatServer(){
		super();
		this.setTitle("Chat Server");
		this.setLocation(80,80);

		try {
			/** Create a listening service for connections
			at the designated port number.**/
			ServerSocket srv = new ServerSocket(2113);

			while (true) {
				/**The method accept() blocks until a client connects.**/
				printMsg("Waiting for a connection");
				Socket socket = srv.accept();
				ClientHandler handler = new ClientHandler(socket);
				/**add client to the arrayList**/
				clients.add(handler);
				handler = new ClientHandler(socket);
				System.out.println("s: " + socket);
				handler.connect();
			}

		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/** This inner class handles communication to/from one client.
	 * and Implement Runnable interface to the ClientHandler
	 **/
	class ClientHandler implements Runnable {
		private PrintWriter writer;
		private BufferedReader reader;

		public ClientHandler(Socket socket) {
			try {
				InetAddress serverIP = socket.getInetAddress();
				/**confirm the connection**/
				printMsg("Connection made to " + serverIP);

				/**assign new PrintWriter**/
				writer = new PrintWriter(socket.getOutputStream(), true);

				/**assign new BufferedReader**/
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			}
			catch (IOException e){
				printMsg("\nERROR:" + e.getLocalizedMessage() + "\n");
			}
		}
		public void handleConnection() {
			try {
				while(true) {
					/**send the read message out to client(s)**/
					sendMsg(readMsg());
				}
			}
			catch (IOException e){
				printMsg("\nERROR:" + e.getLocalizedMessage() + "\n");
			}

		}

		/** Receive and display a message */
		public String readMsg() throws IOException {
			/**assign the message to a string**/
			String s = reader.readLine();
			/**write the message out to client what has been read**/
			printMsg(s);
			return s;
		}
		/** Send a string */
		public void sendMsg(String s){
			/**iterate through clients and write message**/
			for(ClientHandler client: clients){
				client.writer.println(s);
			}
		}
		/**initialize and activate a new thread  **/
		public void connect(){
			Thread th = new Thread(this);
			th.start();
		}


		/**Override the run method and implement
		 * the multi threading function for the desired class
		 **/
		public void run() {
			handleConnection();
		}
	}

	public static void main(String args[]){
		new ChatServer();
	}
}