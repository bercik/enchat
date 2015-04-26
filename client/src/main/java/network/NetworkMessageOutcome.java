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

    /**
     *
     * @param iid jest to id wiadomości jaką wysyłamy
     * @param list jest to lista która przechowuje paczki z wiadomościami
     */
    public NetworkMessageOutcome(int iid, ArrayList<MessageSignPair> list) {
        id = iid;
        messageSignPair = new ArrayList<>();
        for(int i = 0; i < list.size(); ++i)
            messageSignPair.add(list.get(i));
    }


    /**
     * Funkcja służy do wysyłania paczek do serwera
     * @param conn jest to obiekt klasy odpowiedzialny za połączenie się z serwerem
     */
    public void send(Connection conn) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        //wysyłanie id wiadomości
        conn.sendInt(id);

        //żeby każda wiadomość wyglądała tak samo jako drugą wartość wysyłamy 0 i jest to nasze "error"
        conn.sendInt(0);

        //jest to liczba paczek którą wysyłamy. Pomoga ona serwerowi w zorientowaniu się jak długo
        //musi odbierać dane ze strumienia
        conn.sendInt(messageSignPair.size());

        //pętla która iteruje po całej liście przechowującej klase która opakowuje naszą wiadomość
        //przez słowo wiadomość rozumiemy tablice bajtów z naszą wiadomościa i jej podpis
        for(int i = 0; i < messageSignPair.size(); ++i) {
            //System.out.println("Moja wiadomość  w funkcji send = " + new String(messageSignPair.get(i).getMessage()));

            //szyfrowanie tablicy z wiadomoscia
            byte[] encrypt = RSA.encrypt(messageSignPair.get(i).getMessage(), conn.getServerPublicKey().getPublicKey());

            //wysyłanie zaszyfrowanej tablicy do serwera
            conn.sendByteArray(encrypt);

            //wysłanie podpisu do serwera
            conn.sendByteArray(messageSignPair.get(i).getSign());
        }
    }
    
    private int id;
    private ArrayList<MessageSignPair> messageSignPair;


    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeyException, GeneratingPublicKeyException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException, ClassNotFoundException, SignatureException {
        //połączenie z serwerem oraz odebranie jego klucza publicznego i wysłanie swojego klucza publicznego
        Connection conn = new Connection();

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


        NetworkMessageOutcome outcome = new NetworkMessageOutcome(2, list);
        outcome.send(conn);

        NetworkMessageIncome income1 = new NetworkMessageIncome();
        income1.recv(conn);


        NetworkMessageOutcome outcome1 = new NetworkMessageOutcome(1, list);
        outcome1.send(conn);

        //dwie linie odpowiedzialne za odbieranie paczek od serwera
        NetworkMessageIncome income = new NetworkMessageIncome();
        income.recv(conn);
    }

}
