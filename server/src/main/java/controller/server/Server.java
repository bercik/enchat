package controller.server;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import view.INewClientListener;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by tochur on 13.05.15.
 */
public class Server {
    private final int PORT;
    private ServerSocket serverSocket;
    private INewClientListener newClientListener;

    @Inject
    Server(@Named("PORT_NUMBER")Integer PORT, INewClientListener newClientListener){
        this.PORT = PORT;
        this.newClientListener = newClientListener;
    }

    public void start() throws ServerStartFailed {
        try {
            this.serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            throw new ServerStartFailed("Unable to start server on PORT: " + PORT +
                    " cheek weather port is not already engaged.");
        }
    }

}
