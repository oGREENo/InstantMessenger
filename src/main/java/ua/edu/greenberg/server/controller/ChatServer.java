package ua.edu.greenberg.server.controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.*;
import ua.edu.greenberg.server.model.User;


/** This is chat server.
 * Created by GREEN on 28.03.2015.
 */
public class ChatServer {
    private static final Logger log = Logger.getLogger(ChatServer.class);
    private static Map<User, ServerThread> userMap = new HashMap<User, ServerThread>();
    public static final int PORT = 12345;

    /**
     * Start a server.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        PropertyConfigurator.configure("./src/resources/log4j.properties");
        new ChatServer().runServer();
    }

    /**
     * this method running a server.
     * @throws IOException
     */
    public void runServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        log.info("Server up & ready for connection...");
        while (true) {
            Socket socket = serverSocket.accept();
            log.info("Connect a new user.");
            new ServerThread(socket).start();
        }
    }


/*
    /**
     * This method removes user from server.
     * @param user - user.
     * @param userThread - user thread.
     * @throws IOException
     */
/*    public static void removeUser(User user, ServerThread userThread) throws IOException {
        userMap.remove(user);
        log.info("User " + user + " has been removed from users list.");
//        System.out.println(userMap);

        writeMessageInSocketExceptThisUser(user, "#DELETE_FROM_USER_LIST" + "[" + user.getName().toString() + "]");
    }
*/

/*
    /**
     * This method writes message in a socket.
     * @param message - message.
     * @throws IOException
     */
/*    public static void writeMessageInSocket(String message) throws IOException {
        User user;
        ServerThread sT;
        for (Map.Entry entry : userMap.entrySet()) {
            user = (User) entry.getKey();
            sT = (ServerThread) entry.getValue();
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(sT.socket.getOutputStream()), true);
            printWriter.println(message);
        }
    }
*/

/*
    /**
     * This method writes message in a socket except this user.
     * @param user - user.
     * @param message - message.
     * @throws IOException
     */
/*    public static void writeMessageInSocketExceptThisUser(User user, String message) throws IOException {
        User users;
        ServerThread sT;
        for (Map.Entry entry : userMap.entrySet()) {
            users = (User) entry.getKey();
            sT = (ServerThread) entry.getValue();
            if (!(users.equals(user))) {
                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(sT.socket.getOutputStream()), true);
                printWriter.println(message);
            }
        }
    }
*/

/*
    /**
     * This method writes message in a socket to this user.
     * @param user - user.
     * @param st - server thread.
     * @param serverMessage - message.
     * @throws IOException
     */
/*    public static void writeMessageInSockeTotUser(User user, ServerThread st, String serverMessage) throws IOException {
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(st.socket.getOutputStream()), true);
        printWriter.println(serverMessage);
    }
*/

    /**
     * This method gets a users list.
     * @param user - user.
     * @param st - server thread.
     * @throws IOException
     */
    public static void getUsersList(User user, ServerThread st) throws IOException {
        User users;
        for (Map.Entry entry : userMap.entrySet()) {
            users = (User) entry.getKey();
//            writeMessageInSockeTotUser(user, st, "#SET_USER_LIST" + "[" + users.getName().toString() + "]");
        }
    }
}