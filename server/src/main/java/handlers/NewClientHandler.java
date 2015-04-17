package handlers;

import containers.ActiveUsers;
import rsa.PublicKeyInfo;
import rsa.exceptions.GeneratingPublicKeyException;
import user.ActiveUser;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by tochur on 16.04.15.
 *
 * This Handler manages new user, that are going to connect with server.
 * Responsible for creating Active user and adding him to ActiveUsers aggregate.
 */

/*This class generates activeUser (without personal Data), that contains only objects that are necessary to interact with him*/
public class NewClientHandler implements Runnable {
    private Socket socket;
    private DataInputStream inputStream;
    private PublicKeyInfo publicKeyInfo;


    public NewClientHandler(Socket socket) throws GeneratingPublicKeyException {
        this.socket = socket;
        try {
            this.inputStream = new DataInputStream( socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new GeneratingPublicKeyException("Cannot create data stream with this socket");
        }
    }

    /*Jak rzucać wyjątek z Runable*/

    /**
     * Creating new
     */
    @Override
    public void run(){
        scanForKey( inputStream );
        ActiveUsers.getInstance().addUser( new ActiveUser(socket, publicKeyInfo) );
    }

    protected void scanForKey(DataInputStream in) {
        try {
            this.publicKeyInfo = new PublicKeyInfo(in);
        } catch (GeneratingPublicKeyException e) {
            e.printStackTrace();
        }
    }
}
