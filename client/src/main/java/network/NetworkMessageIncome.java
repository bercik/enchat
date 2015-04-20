package network;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
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
public class NetworkMessageIncome {
    
    public void recv(Connection conn) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, SignatureException {
        error = conn.recvInt();
        id = conn.recvInt();
        int size = conn.recvInt();
        
        for(int i = 0; i < size; ++i) {
            byte[] decrypt = RSA.decrypt(conn.recvByteArray(), conn.getKeyPair().getPrivateKeyInfo().getPrivateKey());
            messageSignPair.add(new MessageSignPair(decrypt, conn.recvByteArray()));
            messageSignPair.get(i).checkSign(conn.getKeyPair().getPublicKeyInfo().getPublicKey());
        }
    }
    
    private int error;
    private int id;
    private ArrayList<MessageSignPair> messageSignPair;
}
