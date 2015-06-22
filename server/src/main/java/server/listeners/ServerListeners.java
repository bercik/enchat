package server.listeners;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import server.listeners.message.InputStreamsHandler;

/**
 * This class opens server inputs.
 * When method start() is called server begin 2 threads:
 *  - first to enable new user to connect with server
 *  - second to enable reading incoming messages from already connected users.
 *
 *  * @author Created by tochur on 13.05.15.
 */
@Singleton
public class ServerListeners {
    private final NewClientListener newClientListener;
    private final InputStreamsHandler newMessageScanner;

    /**
     * @param newClientListener - Runnable class, that waits for new Clients (passive open)
     * @param newMessageScanner - scan for new messages from input streams od connected clients.
     *                          Then handles the message by, creating and starting new, suitable
     *                          Message Handler.
     */
    @Inject
    ServerListeners(NewClientListener newClientListener, InputStreamsHandler newMessageScanner){
        this.newClientListener = newClientListener;
        this.newMessageScanner = newMessageScanner;
    }

    /**
     * Starts server (message and client) listeners.
     */
    public void start(){
        new Thread(newClientListener).start();
        new Thread(newMessageScanner).start();
    }

}
