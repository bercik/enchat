package server.listeners;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import controller.responders.NewClientHandler;
import controller.responders.impl.ConversationRequest;
import server.sender.ServerInjectorWrapper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Runnable class,
 * after starting, it persistently waits for clients (passing accept) on ServerSocket.
 * When new clients connects to socket, it captures the newly created Socket, that
 * enables network communication, through http protocol.
 * If you want to build server that listens on few ports, you should create ServerListeners
 * using another new Injector with another PORT number.
 *
 * @author Created by tochur on 13.05.15.
 */
@Singleton
public class NewClientListener implements Runnable{
    private ServerSocket serverSocket;
    private boolean working = false;

    /**
     * To Start listening for new Clients we need Server Instance
     * @param serverSocket instance of ServerSocket.
     * @throws server.listeners.ServerStartFailed ,when server was not able to start on specified Socket.
     */
    @Inject
    public NewClientListener(ServerSocket serverSocket) throws ServerStartFailed {
        if(serverSocket == null){
            throw new ServerStartFailed("Unable to start server on requested port");
        }
        this.serverSocket = serverSocket;
    }

    /**
     * This function listen for new clients (passive open).
     */
    @Override
    public void run() {
        this.working = true;
        while (working) {
            try {
                System.out.println("Waiting for new client");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Got new client");


                //Creating new client handler object
                NewClientHandler newClientHandler = createNewClientHandler();
                handleNewClient(newClientHandler, clientSocket);
            } catch (SocketException e){
                if(!working){
                    System.out.println("New Client Listener ended it's work, correctly.");
                }else{
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * When new client connects to the server, new thread is started to handle the new Client.
     * @param newClientHandler NewClientHandler, object that handle the client (ex. exchange public Key With Him).
     * @param clientSocket Socket, an communication interface between user and server.
     */
    protected void handleNewClient(NewClientHandler newClientHandler, Socket clientSocket){
        newClientHandler.setParameters(clientSocket);
        new Thread(newClientHandler).start();
    }

    /**
     * Creates an object to handle new client - using Guice injector.
     * @return NewClientHandler, new object to serve the new request.
     */
    protected NewClientHandler createNewClientHandler(){
        Injector injector = new ServerInjectorWrapper().getServerInjector();
        return injector.getInstance(NewClientHandler.class);
    }

    /* When server is closing this method release socket resources. */
    private void releaseResources() throws IOException {
        System.out.println("New Client Listener closing...");
        working = false;
        serverSocket.close();
    }

    /**
     * This function is prepared to close the server - stop the clients listening thread.
     * @throws IOException when there was problem with releasing socket.
     */
    public void stopListen() throws IOException {
        releaseResources();
    }
}
