package network;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.omg.CORBA.CODESET_INCOMPATIBLE;
import rsa.RSA;
import rsa.exceptions.GeneratingPublicKeyException;

/**
 *
 * @author mateusz
 * @version 1.0
 */
public class NetworkMessageOutcome {
    
    public NetworkMessageOutcome(int iid, ArrayList<MessageSignPair> list) {
        id = iid;
        messageSignPair = new ArrayList<MessageSignPair>();
        for(int i = 0; i < list.size(); ++i)
            messageSignPair.add(list.get(i));
    }
    
    public void send(Connection conn) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        conn.sendInt(id);
        conn.sendInt(0);
        conn.sendInt(messageSignPair.size());
        
        for(int i = 0; i < messageSignPair.size(); ++i) {
            System.out.println("Moja wiadomość  w funkcji send = " + new String(messageSignPair.get(i).getMessage()));
            byte[] encrypt = RSA.encrypt(messageSignPair.get(i).getMessage(), conn.getServerPublicKey().getPublicKey());
            conn.sendByteArray(encrypt);
            conn.sendByteArray(messageSignPair.get(i).getSign());
        }
    }
    
    private int id;
    private ArrayList<MessageSignPair> messageSignPair;

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeyException, GeneratingPublicKeyException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException, ClassNotFoundException, SignatureException {
        //połączenie z serwerem oraz odebranie jego klucza publicznego i wysłanie swojego klucza publicznego
        Connection conn = new Connection();
/*
        //wysyłanie wiadomości
        String login = "Mateusz";
        String password = "Gierczak";
        ArrayList<MessageSignPair> list = new ArrayList<>();

        //dodawanie loginu do paczki
        byte[] mesaggeArray = login.getBytes();
        byte[] signMessage = RSA.sign(mesaggeArray, conn.getKeyPair().getPrivateKeyInfo().getPrivateKey());

        list.add(new MessageSignPair(mesaggeArray, signMessage));

        //dodawanie hasła do paczki
        mesaggeArray = password.getBytes();
        signMessage = RSA.sign(mesaggeArray, conn.getKeyPair().getPrivateKeyInfo().getPrivateKey());

        list.add(new MessageSignPair(mesaggeArray, signMessage));
*/

        //dwie linie odpowiedzialne za odbieranie paczek od serwera
        NetworkMessageIncome income = new NetworkMessageIncome();
        income.recv(conn);

        //NetworkMessageOutcome outcome = new NetworkMessageOutcome(2, list);
        //outcome.send(conn);

    }

}
