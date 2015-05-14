package newServer.listeners;

import newServer.ServerInjector;
import controller.user.NewUser;
import controller.user.NewUserPropagator;

import java.net.Socket;

/**
 * Created by tochur on 13.05.15.
 *
 * This class is responsible for actualization state of users that can exchange information with newServer
 */
public class NewClientHandler implements Runnable {
    private Socket socket;
    private Integer ID;

    public NewClientHandler(Socket socket, Integer ID){
        this.socket = socket;
        this.ID = ID;
    }

    @Override
    public void run() {
        NewUser newUser = new NewUser(socket, ID);
        NewUserPropagator newUserPropagator = ServerInjector.get().getInstance(NewUserPropagator.class);
        newUserPropagator.propagate(newUser);
    }
}
