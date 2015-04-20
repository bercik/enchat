package network;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import rsa.RSA;

/**
 *
 * @author mateusz
 * @version 1.0
 */
public class NetworkMessageOutcome {
    
    public NetworkMessageOutcome() {
        id = 0;
        messageSignPair = new ArrayList<MessageSignPair>();
    }
    
    public void send(Connection conn) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        conn.sendInt(id);
        conn.sendInt(0);
        conn.sendInt(messageSignPair.size());
        
        for(int i = 0; i < messageSignPair.size(); ++i) {
            byte[] encrypt = RSA.encrypt(messageSignPair.get(i).getMessage(), conn.getKeyPair().getPublicKeyInfo().getPublicKey());
            conn.sendByteArray(encrypt);
            conn.sendByteArray(messageSignPair.get(i).getSign());
        }
    }
    
    private int id;
    private ArrayList<MessageSignPair> messageSignPair;
}
