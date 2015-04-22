package ua.edu.greenberg.client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * MyActionListener.
 * @author Greenberg Dima <gdvdima2008@yandex.ru>
 * @version 1.0
 */
public class MyActionListener implements ActionListener {
	private Object frame;
	private int id;
	private ActionListener parentListener;
	
	/**
	 * This class constructor MyActionListenerr.
	 */
	public MyActionListener(Object frame, int id, ActionListener event) {
		this.frame = frame;
		this.id = id;
		this.parentListener = event;
	}

	/**
	 * This method captures actionEvent.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ActionEvent event = new ActionEvent(frame, id, e.getActionCommand());
		parentListener.actionPerformed(event);
	}

	/**
	 * this method gets parentListener.
	 * @return actionListener.
	 */
	public ActionListener getParentListener() {
		return parentListener;
	}
	
	/**
	 * This method set parentListener.
	 * @param listener - listener.
	 * @return actionListener.
	 */
	public ActionListener setParentListener(ActionListener listener) {
		this.parentListener = listener;
		return this;
	}
}