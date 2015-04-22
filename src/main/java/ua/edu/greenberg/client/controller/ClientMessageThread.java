package ua.edu.greenberg.client.controller;

import ua.edu.greenberg.client.view.ClientChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class start message thread.
 */
public class ClientMessageThread extends Thread {
	private Socket socket;

	/**
	 * This is the constructor.
	 * @param socket - socket.
	 */
	ClientMessageThread(Socket socket) {
		this.socket = socket;
	}

	/**
	 * This method run thread.
	 */
	public void run() {
		String message = null;
		BufferedReader bufferedReader;
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while ((message = bufferedReader.readLine()) != null) {
				if (getSystemMessage(message)) {
					readSystemMessage(message);
				} else {
					ClientChat.addMessageToList("[" + dateFormat.format(date) + "] " + message);
				}
			}
		} catch (IOException e) {
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
		return (line.equals("#SET_USER_LIST")
				|| line.equals("#ADD_TO_USER_LIST")
				|| line.equals("#DELETE_FROM_USER_LIST"))? true : false;
	}

	/**
	 * This method reads the system messages.
	 * @param message - message.
	 */
	private void readSystemMessage(String message) {
		int titleStart = message.indexOf("#");
		int titleEnd = message.indexOf("[");
		String line = message.substring(titleStart, titleEnd);
		if (line.equals("#SET_USER_LIST")) {
			int titleStartUserName = message.lastIndexOf("[");
			int titleEndUserName = message.lastIndexOf("]");
			String userName = message.substring(titleStartUserName + 1, titleEndUserName);
			if (userName != null) {
				ClientChat.getClientUserToList(userName);
			}
		} else if (line.equals("#ADD_TO_USER_LIST")) {
				int titleStartUserName = message.lastIndexOf("[");
				int titleEndUserName = message.lastIndexOf("]");
				String userName = message.substring(titleStartUserName + 1, titleEndUserName);
				if (userName != null) {
					ClientChat.addClientUserToList(userName);
				}
		} else if (line.equals("#DELETE_FROM_USER_LIST")) {
			int titleStartUserName = message.lastIndexOf("[");
			int titleEndUserName = message.lastIndexOf("]");
			String userName = message.substring(titleStartUserName + 1, titleEndUserName);
			if (userName != null) {
				ClientChat.deleteClientUserToList(userName);
			}
		}
	}
}
