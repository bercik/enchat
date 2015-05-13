package view;

import controller.user.IUser;
import handlers.NewClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by tochur on 13.05.15.
 */
public class NewClientListener implements Runnable, INewClientListener{
    private ServerSocket serverSocket;

    public NewClientListener(ServerSocket serverSocket, IUser User){
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {

        while (true) {
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
