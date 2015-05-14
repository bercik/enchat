package newServer.network;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by tochur on 13.05.15.
 */
@Singleton
public class Server {
    private final int PORT;
    private ServerSocket serverSocket;

    @Inject
    Server(@Named("PORT_NUMBER")Integer PORT) throws ServerStartFailed {
        this.PORT = PORT;
        System.out.print("SERVER PORT:" + PORT);
        try {
            this.serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            throw new ServerStartFailed("Unable to start newServer on PORT: " + PORT +
                    " cheek weather port is not already engaged.");
        }
    }

    public ServerSocket getSocket(){
        return serverSocket;
    }
}
