package ua.edu.greenberg.server.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import org.apache.log4j.*;
import ua.edu.greenberg.server.model.User;

public class ServerThread extends Thread {
	private static final Logger log = Logger.getLogger(ServerThread.class);
	User user;
	Socket socket;

	/**
	 * This is the constructor a class.
	 * @param socket - socket.
	 */
	ServerThread(Socket socket) {
		this.socket = socket;
	}

	/**
	 * This method starts a thread.
	 */
	public void run() {

		try {
			String message = null;
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while ((message = bufferedReader.readLine()) != null) {
				if (getSystemMessage(message)) {
					readSystemMessage(message);
				} else {
					log.info("Incoming client message: " + message);
					ChatServer.writeMessageInSocket(message);
				}
			}
			socket.close();
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * This method determines the system messages.
	 * @param message - message.
	 * @return boolean.
	 */
	private boolean getSystemMessage(String message) {
		if (message.indexOf("#") == -1) return false;
		int titleStart = message.indexOf("#");
		int titleEnd = message.indexOf("[");
		String line = message.substring(titleStart, titleEnd);
		return (line.equals("#CONNECT_USER") 
				|| line.equals("#EXIT_USER")
				|| line.equals("#GET_USER_LIST"))? true : false;
	}

	/**
	 * This method reads the system messages.
	 * @param message - message.
	 * @throws java.io.IOException
	 */
	private void readSystemMessage(String message) throws IOException {
		int titleStart = message.indexOf("#");
		int titleEnd = message.indexOf("[");
		String line = message.substring(titleStart, titleEnd);
		if (line.equals("#CONNECT_USER")) {
			int titleStartUserName = message.lastIndexOf("[");
			int titleEndUserName = message.lastIndexOf("]");
			String userName = message.substring(titleStartUserName + 1, titleEndUserName);
			if (userName != null) {
				user = new User(userName);
				ChatServer.addUser(user, this);
				log.info("CONNECT USER: " + user);
			}
		} else if (line.equals("#EXIT_USER")) {
			int titleStartUserName = message.lastIndexOf("[");
			int titleEndUserName = message.lastIndexOf("]");
			String userName = message.substring(titleStartUserName + 1, titleEndUserName);
			if (userName != null) {
				user = new User(userName);
				log.info("EXIT USER: " + user);
				ChatServer.removeUser(user, this);
			}
		} else if (line.equals("#GET_USER_LIST")) {
			int titleStartUserName = message.lastIndexOf("[");
			int titleEndUserName = message.lastIndexOf("]");
			String userName = message.substring(titleStartUserName + 1, titleEndUserName);
			if (userName != null) {
				user = new User(userName);
				ChatServer.getUsersList(user, this);
				log.info("GET USER LIST.");
			}
		}
	}
}
