package newServer.listeners;

import com.google.inject.Inject;
import com.google.inject.Provides;
import controller.connecting.NewClientHandler;
import newServer.network.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by tochur on 13.05.15.
 *
 * This class is runnable.
 * After starting, it persistently waits for clients (passing accept) on ServerSocket.
 * When new clients connects to socket, it captures the newly created Socket, that
 * enables network communication, through http protocol.
 */
public class NewClientListener implements Runnable{
    private ServerSocket serverSocket;
    private NewClientHandler newClientHandler;

    /**
     * To Start listening for new Clients we need Server Instance
     * @param server instance of Server.
     * @param newClientHandler - class responsible for actualization state of users
     *                         that can exchange information with newServer
     */
    @Inject
    public NewClientListener(Server server, NewClientHandler newClientHandler){
        System.out.print("NEW CLIENT LISTENER");
        this.serverSocket = server.getSocket();
        this.newClientHandler = newClientHandler;
    }

    @Override
    public void run() {

        while (true) {
            try {
                System.out.println("Waiting for new client");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Got new client");
                newClientHandler.setParameters(clientSocket);
                //Starting new Thread, that handles new Client
                new Thread(newClientHandler).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Provides
    ServerSocket getServerSocket(Server server){
        return server.getSocket();
    }
}
