package handlers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by tochur on 25.04.15.
 *
 * This class is a thread, that all the time waits for new client.
 * When new client will appear, ir runs another (NewClientHandler) thread to interact with the user
 */
public class NewClientListener implements Runnable{
    ServerSocket serverSocket;

    public NewClientListener(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    public void run() {
        while(true){
            try {
                System.out.println("Waiting for new client");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Got new client");
                NewClientHandler newClientHandler = new NewClientHandler(clientSocket);
                new Thread(newClientHandler).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
