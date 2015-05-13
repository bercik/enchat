package controller.user;

import java.net.Socket;

/**
 * Created by tochur on 13.05.15.
 */
public class NewUser implements IUser{
    private final Integer ID;
    private final Socket socket;

    public NewUser(Socket socket, Integer ID){
        this.socket = socket;
        this.ID = ID;
    }
}
