package newServer.listeners;

import com.google.inject.Inject;
import newServer.listeners.message.InputStreamsHandler;

/**
 * Created by tochur on 13.05.15.
 *
 * This class opens server inputs.
 * When method start() is called server begin 2 threads:
 *  - first to enable new user to connect with server
 *  - second to enable reading incoming messages from already connected users.
 */
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

    public void start(){
        new Thread(newClientListener).start();
        new Thread(newMessageScanner).start();
    }

}
