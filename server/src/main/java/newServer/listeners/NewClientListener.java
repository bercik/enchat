package newServer.listeners;

import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import controller.connecting.NewClientHandler;

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
 * If you want to build server that listens on few ports, you should create ServerListeners
 * using another new Injector with another PORT number.
 */
@Singleton
public class NewClientListener implements Runnable{
    private ServerSocket serverSocket;
    private NewClientHandler newClientHandler;
    private boolean working;

    /**
     * To Start listening for new Clients we need Server Instance
     * @param serverSocket instance of ServerSocket.
     * @param newClientHandler - class responsible for actualization state of users
     *                         that can exchange information with newServer
     */
    @Inject
    public NewClientListener(ServerSocket serverSocket, NewClientHandler newClientHandler) throws ServerStartFailed {
        System.out.print("NEW CLIENT LISTENER");
        if(serverSocket == null){
            throw new ServerStartFailed("Unable to start server on requested port");
        }
        this.serverSocket = serverSocket;
        this.newClientHandler = newClientHandler;
    }

    @Override
    public void run() {
        working = true;
        while (working) {
            try {
                if(!working){
                    System.out.print("Closing...");
                    serverSocket.close();
                    return;
                }
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

    public void stopListen(){
        this.working = false;
    }
}
