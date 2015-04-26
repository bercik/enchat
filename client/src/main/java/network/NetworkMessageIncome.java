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
import javax.swing.plaf.synth.SynthTextAreaUI;

import rsa.RSA;

/**
 *
 * @author mateusz
 * @version 1.0
 */
public class NetworkMessageIncome {
    
    public void recv(Connection conn) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, SignatureException {

        //odbierane jes id wiadomosci
        id = conn.recvInt();

        //odbierana jest  wartosc error która będzie nas iformować o stanie połączenia
        error = conn.recvInt();

        //jest to rozmiar wysłanych paczek
        int size = conn.recvInt();
        //System.out.println (id + " " + error + " " + size);

        //pętla która wykonuje się tyle razy ile wysłano do nas paczek z wiadomościami
        for(int i = 0; i < size; ++i) {

            //tablica która odszyfrowuje wiadomość uprzednio zaszyfrowaną przez serwer naszym kluczem puplicznym
            byte[] decrypt = RSA.decrypt(conn.recvByteArray(), conn.getKeyPair().getPrivateKeyInfo().getPrivateKey());

            //dodawanie pojedynczych wiadomosci do listy
            messageSignPair.add(new MessageSignPair(decrypt, conn.recvByteArray()));
            String m = new String(messageSignPair.get(i).getMessage());

            //sprawdzanie podpisu wiadomości, któro opewni nas, że wiadomość nie została sfałszowana
            messageSignPair.get(i).checkSign(conn.getServerPublicKey().getPublicKey());
        }
    }
    
    private int error;
    private int id;
    private ArrayList<MessageSignPair> messageSignPair = new ArrayList<>();
}
