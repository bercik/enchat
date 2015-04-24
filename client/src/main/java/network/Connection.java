package network;

import app_info.Configuration;
import rsa.KeyContainer;
import rsa.PublicKeyInfo;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author mateusz
 * @version 1.0
 */
public class Connection {
    
    public Connection() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, ClassNotFoundException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        socket = new Socket(Configuration.getServerAddress(), Configuration.getPort());
        
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
        
        serverPublicKey = Configuration.getServerPublicKeyInfo();
        
        keyPair = new KeyContainer();
    }
    
    public void sendByteArray(byte[] byteArray) throws IOException {
        out.writeInt(byteArray.length);
        out.write(byteArray);
    }
    
    public void sendInt(int id) throws IOException {
        out.writeInt(id);
    }
    
    public byte[] recvByteArray() throws IOException {
        int lenght = in.readInt();
        byte[] array = new byte[lenght];
        in.readFully(array);
        return array;
    }
    
    public int recvInt() throws IOException {
        return in.readInt();
    }
    
    public KeyContainer getKeyPair() throws NoSuchAlgorithmException, InvalidKeySpecException {
        return new KeyContainer(keyPair);
    }
    
    
    public PublicKeyInfo getServerPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        return new PublicKeyInfo(serverPublicKey);
    }
    
    private void close() throws IOException {
        socket.close();
    }
    
    /*
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, ClassNotFoundException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException  {
        Connection conn = new Connection();
    }
    */
    
    private final PublicKeyInfo serverPublicKey;
    private final KeyContainer keyPair;
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;   
}
