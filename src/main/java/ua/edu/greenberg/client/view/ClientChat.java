package ua.edu.greenberg.client.view;

import ua.edu.greenberg.client.controller.ButtonNames;
import ua.edu.greenberg.client.controller.MyActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * This class start client chat.
 */
public class ClientChat extends JFrame {
	public static final int jListUsersWidth = 190;
	public static final int jListUsersHeight = 400;
	public static final int jListChatWidth = 570;
	public static final int jListChatHeight = 400;
	public static final int jMessageWidth = 570;
	public static final int jMessageHeight = 80;
	private String message;
	public JList jListUsers;
	public JList jListChat;
	private static DefaultListModel listChatModel = new DefaultListModel();
	private static ArrayList<String> chatArray = new ArrayList<String>();
	private static ArrayList<String> chatArrayView = new ArrayList<String>();
	private static DefaultListModel listUserModel = new DefaultListModel();
	private static ArrayList<String> userArray = new ArrayList<String>();
	private JTextArea jTextMessage;
	private JButton jSendButton;

	/**
	 * This is the constructor.
	 */
	public ClientChat() {
		super("Instant Messenger");
		createClientChat();
		this.setSize(800,600);
		this.setResizable(false);
	}

	/**
	 * This method create a window "Chat room".
	 */
	private void createClientChat() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		JPanel panel = new JPanel();
		this.add(panel, new GridBagConstraints(0, 0, 3, 4, 0, 0, GridBagConstraints.EAST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		panel.setLayout(new GridBagLayout());
		JPanel panelButton = new JPanel();
		panel.add(panelButton, new GridBagConstraints(1, 3, 1, 1, 0, 0, GridBagConstraints.EAST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		panelButton.setLayout(new GridBagLayout());
		createUsresList(panel);
		createButton(panelButton);
	}

	/**
	 * This method created user list.
	 * @param panel - JPanel.
	 */
	private void createUsresList(JPanel panel) {
		Font font = new Font("Calibri", Font.BOLD, 20);
		JLabel textUsers = new JLabel("Users");
		textUsers.setFont(font);
		JLabel textChat = new JLabel("");
		textChat.setFont(font);
		JLabel textMessage = new JLabel("Enter your message");
		textMessage.setFont(new Font("Calibri", Font.ITALIC, 16));
		jListUsers = new JList(listUserModel);
		JScrollPane scroolUsers = new JScrollPane(jListUsers);
		scroolUsers.setPreferredSize(new Dimension(jListUsersWidth, jListUsersHeight));
		jListChat = new JList(listChatModel);
		JScrollPane scroolChat = new JScrollPane(jListChat);
		scroolChat.setPreferredSize(new Dimension(jListChatWidth, jListChatHeight));
		jTextMessage = new JTextArea();
		jTextMessage.setLineWrap(true);
		JScrollPane scrollMessage = new JScrollPane(jTextMessage);
		scrollMessage.setPreferredSize(new Dimension(jMessageWidth, jMessageHeight));
		panel.add(textChat, new GridBagConstraints(0, 0, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				 new Insets(0, 25, 0, 0), 0, 0));
		panel.add(textUsers, new GridBagConstraints(1, 0, 1, 1, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE,
				 new Insets(0, 0, 0, 0), 0, 0));
		panel.add(scroolUsers, new GridBagConstraints(1, 1, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				 new Insets(5, 5, 5, 5), 0, 0));
		panel.add(scroolChat, new GridBagConstraints(0, 1, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				 new Insets(5, 5, 5, 5), 0, 0));
		panel.add(textMessage, new GridBagConstraints(0, 2, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				 new Insets(5, 25, 5, 5), 0, 0));
		panel.add(scrollMessage, new GridBagConstraints(0, 3, 1, 1, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE,
				 new Insets(0, 5, 5, 5), 0, 0));
		jTextMessage.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char vChar = e.getKeyChar();

				if (jTextMessage.getText().length() >= 2000) {
					e.consume();
				}
			}
		});
	}

	/**
	 * This method adds a button.
	 * @param panel - JPanel
	 */
	private void createButton(JPanel panel) {
		jSendButton = new JButton(ButtonNames.BUTTON_SEND.getTypeValue());
		jSendButton.setPreferredSize(new Dimension(180, 75));
		panel.add(jSendButton, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 5, 10, 5), 0, 0));
	}

	/**
	 * This method gets message.
	 * @return messsage.
	 */
	public String getMessage() {
		if (jTextMessage.getText() != null) {
			message = jTextMessage.getText();
			jTextMessage.setText(null);
		}
		return message;
	}

	/**
	 * This is ActionListener.
	 * @param listener  - ActionListener.
	 */
	public void addActionListener(ActionListener listener) {
		jSendButton.addActionListener(new MyActionListener(this, 0 , listener));
	}

	/**
	 * This method adds message.
	 * @param serverMessage - message.
	 */
	public static void addMessageToList(String serverMessage) {
		listChatModel.clear();
		chatArrayView.clear();
		chatArray.add(serverMessage);
		for (int i = chatArray.size() - 1; i >= 0; i--) {
			chatArrayView.add(chatArray.get(i));
		}
		for (int i = 0; i < chatArrayView.size(); i++) {
			listChatModel.add(i, chatArrayView.get(i));
		}
	}

	/**
	 * This method gets list the users.
	 * @param serverMessage - message.
	 */
	public static void getClientUserToList(String serverMessage) {
		listUserModel.clear();
		userArray.add(serverMessage);
		for (int i = 0; i < userArray.size(); i++) {
			listUserModel.add(i, userArray.get(i));
		}
	}

	/**
	 * This method adds the users to list.
	 * @param serverMessage - message.
	 */
	public static void addClientUserToList(String serverMessage) {
		listUserModel.clear();
		userArray.add(serverMessage);
		for (int i = 0; i < userArray.size(); i++) {
			listUserModel.add(i, userArray.get(i));
		}
	}

	/**
	 * This method removes the users from list.
	 * @param serverMessage - message.
	 */
	public static void deleteClientUserToList(String serverMessage) {
		userArray.remove(serverMessage);
		listUserModel.clear();
		for (int i = 0; i < userArray.size(); i++) {
			listUserModel.add(i, userArray.get(i));
		}
	}
}