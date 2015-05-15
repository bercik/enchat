package newServer.listeners;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by tochur on 15.05.15.
 */
public class SimpleClient {

    public void connect(String ip, int port) throws IOException {
        Socket echoSocket = new Socket(ip, port);
    }
}
