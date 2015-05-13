package controller.server;

import com.google.inject.Injector;
import controller.ServerInjector;

/**
 * Created by tochur on 13.05.15.
 */
public class ServerStart {
    public static void main(String[] args) throws ServerStartFailed {
        Injector injector = ServerInjector.get();
        Server server = injector.getInstance(Server.class);
        server.start();
    }
}
