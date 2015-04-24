package ua.edu.greenberg.client.controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * MyWindowListener.
 * @author Greenberg Dima <gdvdima2008@yandex.ru>
 * @version 1.0
 */
public class MyWindowListener extends WindowAdapter {
	private Client client;
	
	/**
	 * This class constructor MyWindowListener.
	 * @param client - controller.
	 */
	public MyWindowListener(Client client) {
		this.client = client;
	}
	
	/**
	 * This method takes actions to close the window.
	 */
	@Override
	public void windowClosing (WindowEvent event) {
		try {
			client.closeClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
		event.getWindow().dispose();
	}
}