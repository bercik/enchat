package server;

import com.google.inject.Injector;
import server.listeners.ServerListeners;
import server.listeners.ServerStartFailed;

/**
 * ServerStart class
 *
 * @author Created by tochur on 13.05.15.
 */
public class ServerStart {
    /**
     * Main program function
     * @param args String[] args from console
     * @throws ServerStartFailed when any critical problem occurred and server was not able to start.
     */
    public static void main(String[] args) throws ServerStartFailed {
        Injector injector = ServerInjector.get();
        ServerListeners serverListeners = injector.getInstance(ServerListeners.class);
        serverListeners.start();
    }
}
