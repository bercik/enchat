package server.listeners;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by tochur on 15.05.15.
 */
public class SimpleClient {
    Socket socket;

    public void connect(String ip, int port) throws IOException {
        this.socket = new Socket(ip, port);
        socket.getInputStream();
    }
}
