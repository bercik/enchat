package package_forwarder;

import network.Connection;
import network.MessageSignPair;
import network.NetworkMessageOutcome;
import rsa.RSA;
import rsa.exceptions.GeneratingPublicKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

/**
 * @author mateusz
 * @version 1.0
 */
public class PackageForwarder implements Runnable{

    public void run() {
        //pętla nieskończona która będzie nasłuchiwała strumień
        while(true) {
            try {
                //sprawdzane jest czy strumień jest pusty
                if(conn.isEmpty() > 0) {

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void connect() throws IOException, NoSuchAlgorithmException, InvalidKeyException, GeneratingPublicKeyException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException, ClassNotFoundException {
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
        conn = new Connection();
    }

    public void send(int id, String[] parameters) throws InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, IOException {
        byte[] message = null;
        byte[] signMessage = null;

        ArrayList<MessageSignPair> messageSignPairs = new ArrayList<>();

        for(int i = 0; i < parameters.length; ++i) {
            message = parameters[i].getBytes();
            signMessage = RSA.sign(message, conn.getKeyPair().getPrivateKeyInfo().getPrivateKey());
            messageSignPairs.add(new MessageSignPair(message, signMessage));
        }

        NetworkMessageOutcome networkMessageOutcome = new NetworkMessageOutcome(id, messageSignPairs);
        networkMessageOutcome.send(conn);
    }

    private Connection conn;
    private Thread thread;
}
