package handlers;

import containers.ActiveUsers;
import user.ActiveUser;

import java.net.Socket;

/**
 * Created by tochur on 18.04.15.
 *
 * Responsible for handling new clients that want to user server.
 *
 * This class Creates new Client object and adding it to ActiveUser collection.
 * That means that server will listen for messages from this user.
 */
public class NewClientHandler {
    public static void addNewClient(Socket clientSocket) {
        ActiveUser activeUser = new ActiveUser(clientSocket);
        ActiveUsers.getInstance().addUser(activeUser);
    }
}
