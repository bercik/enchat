package handlers;

import rsa.PublicKeyInfo;
import rsa.exceptions.GeneratingPublicKeyException;
import user.User;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by tochur on 16.04.15.
 *
 * This Handler manages new user, that are going to connect with newServer.
 * Responsible for creating Active user and adding him to ActiveUsers aggregate.
 */

/*This class generates sender (without personal Data), that contains only objects that are necessary to interact with him*/
public class PublicKeyReader{
    private Socket socket;
    private DataInputStream inputStream;
    private PublicKeyInfo publicKeyInfo;


    public PublicKeyReader(User user) throws GeneratingPublicKeyException {

        try {
            this.inputStream = new DataInputStream( socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new GeneratingPublicKeyException("Cannot create data stream with this socket");
        }
    }


    protected void scanForKey(DataInputStream in) {
        try {
            this.publicKeyInfo = new PublicKeyInfo(in);
        } catch (GeneratingPublicKeyException e) {
            e.printStackTrace();
        }
    }
}
