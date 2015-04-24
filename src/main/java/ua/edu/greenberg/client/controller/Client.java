package ua.edu.greenberg.client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.apache.log4j.*;
import ua.edu.greenberg.client.model.ClientUser;
import ua.edu.greenberg.client.view.ClientChat;
import ua.edu.greenberg.client.view.ClientLogin;

/**
 * This class starts a chat client.
 */
public class Client {
	private static final Logger log = Logger.getLogger(Client.class);
	private String name;
//	private String url;
	private String message;
//	private int port;
	private Socket socket;
	private static ClientLogin clientName;
	private static ClientUser clientUser;
	private static ClientChat clientChat;

	/**
	 * This is the constructor.
	 */
	public Client() {
		clientName = new ClientLogin();
//		clientName.addActionListener(this);
		clientName.setVisible(true);
		clientChat = new ClientChat();
//		clientChat.addWindowListener(new MyWindowListener(this));
//		clientChat.addActionListener(this);
	}

	/**
	 * This method start client.
	 * @param args
	 */
	public static void main(String[] args)  {
		PropertyConfigurator.configure("./src/resources/log4j.properties");
		new Client();
	}

	/**
	 * This method create a connection.
	 */
	public void createConnection() {
		name = clientUser.getName();
		log.info("ClientUser Name - " + name);
		try {
			socket = new Socket(clientUser.getUrl() , clientUser.getPort());
		} catch (IOException e) {
			e.printStackTrace();
		}
		new ClientMessageThread(socket).start();
		log.info("Connect to URL - " + clientUser.getUrl() + " and PORT - " + clientUser.getPort());
	}


/*
	/**
	 * This method writes in socket a message.
	 * @param message
	 * @throws java.io.IOException
	 */
/*	public void writeInSocket(String message) throws IOException {
		if (!message.isEmpty()) {
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
			printWriter.println(name + ": " + message);
		}
	}
*/

/*
	/**
	 * This method create a user.
	 * @param name - name.
	 * @param url - URL.
	 * @param port - port.
	 */
/*	public void createClientUser(String name, String url, int port) {
		clientUser = new ClientUser(name, url, port);
	}
*/

/*
	@Override
	public void actionPerformed(ActionEvent event) {
		String actionComand = event.getActionCommand();
		ButtonNames buttonName = ButtonNames.getType(actionComand);
			switch (buttonName){
				case BUTTON_LOGIN: {
					if (clientName.validData()) {
						name = clientName.getName();
						url = clientName.getUrl();
						InetAddress addr = null;
						try {
							addr = InetAddress.getByName(url);
						} catch (UnknownHostException e) {
							e.printStackTrace();
						}
						port = clientName.getPort();
						if (pingServer(addr, port, 10)) {
							log.info("Server is running");
							createClientUser(name, url, port);
							createConnection();
							try {
								writeInSocket("#CONNECT_USER" + "[" + clientName.getName() + "]");
							} catch (IOException e) {
								e.printStackTrace();
							}
							clientName.setVisible(false);
							clientChat.setVisible(true);
							try {
								writeInSocket("#GET_USER_LIST" + "[" + clientName.getName() + "]");
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					break;
				}
				case BUTTON_SEND: {
					try {
						writeInSocket(clientChat.getMessage().toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				}
			}
	}
*/


	/**
	 * This method checks the server is started.
	 * @param addr - address server.
	 * @param port - port server.
	 * @param timeout -
	 * @return
	 */
	public static boolean pingServer(InetAddress addr, int port, int timeout) {
		log.info("Ping Server.");
        Socket socket = new Socket();
        Exception exception = null;
        try {
			socket.connect(new InetSocketAddress(addr, port), timeout);
        } catch (IOException e) {
			log.error("IOException.");
            exception = e;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
				log.error("IOException socket.close.");
            }
        }
        return exception == null ? true : false;
    }

	/**
	 * This method closing a client.
	 * @throws java.io.IOException
	 */
	public void closeClient() throws IOException {
//		String closeMessage = "#EXIT_USER" + "[" + clientUser.name + "]";
//		writeInSocket(closeMessage);
		log.info("Close client.");
		System.exit(0);
	}
}