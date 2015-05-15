package newServer;

import com.google.inject.Injector;
import newServer.listeners.ServerListeners;
import newServer.listeners.ServerStartFailed;

/**
 * Created by tochur on 13.05.15.
 */
public class ServerStart {
    public static void main(String[] args) throws ServerStartFailed {
        Injector injector = ServerInjector.get();
        ServerListeners serverListeners = injector.getInstance(ServerListeners.class);
        serverListeners.start();
    }
}
