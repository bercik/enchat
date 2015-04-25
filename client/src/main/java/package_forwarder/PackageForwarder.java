package package_forwarder;

import network.Connection;
import rsa.exceptions.GeneratingPublicKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author mateusz
 * @version 1.0
 */
public class PackageForwarder implements Runnable{

    public void connect() throws IOException, NoSuchAlgorithmException, InvalidKeyException, GeneratingPublicKeyException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException, ClassNotFoundException {
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
        conn = new Connection();
    }

    public void run() {

    }

    private Connection conn;
    private Thread thread;
}
