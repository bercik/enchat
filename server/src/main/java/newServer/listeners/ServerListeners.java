package newServer.listeners;

import com.google.inject.Inject;
import newServer.listeners.message.InputStreamsListener;

/**
 * Created by tochur on 13.05.15.
 */
public class ServerListeners {
    private final INewClientListener newClientListener;
    private final InputStreamsListener newMessageScanner;

    @Inject
    ServerListeners(INewClientListener newClientListener, InputStreamsListener newMessageScanner){
        this.newClientListener = newClientListener;
        this.newMessageScanner = newMessageScanner;
    }

    public void start(){
        new Thread(newClientListener).start();
        new Thread(newMessageScanner).start();
    }

}
