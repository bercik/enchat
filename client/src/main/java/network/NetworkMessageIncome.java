package network;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import messages.MessageId;
import rsa.PublicKeyInfo;

import rsa.RSA;

/**
 *
 * @author mateusz, robert
 * @version 1.0
 */
public class NetworkMessageIncome
{
    public NetworkMessageIncome()
    {
    }

    public List<MessageSignPair> getMessageSignPair()
    {
        return new ArrayList<>(messageSignPair);
    }

    public int getError()
    {
        return error;
    }

    public int getId()
    {
        return id;
    }

    public void recv(Connection conn, PublicKeyInfo interlocutorPublicKeyInfo) 
            throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, 
            InvalidKeyException, IllegalBlockSizeException, 
            BadPaddingException, InvalidKeySpecException, SignatureException
    {
        //odbierane jes id wiadomosci
        id = conn.recvInt();
        
        // klucz publiczny którym sprawdzamy podpis wiadomości
        PublicKeyInfo publicKeyInfoToCheckSign;
        // sprawdzamy czy nie jest to wiadomość konwersacji, jeżeli tak
        // to używamy innego klucza publicznego do sprawdzenia podpisu
        MessageId messageId = MessageId.createMessageId(id);
        if (messageId == MessageId.SERVER_MESSAGE)
        {
            publicKeyInfoToCheckSign = interlocutorPublicKeyInfo;
        }
        else
        {
            publicKeyInfoToCheckSign = conn.getServerPublicKey();
        }

        //odbierana jest  wartosc error która będzie nas iformować o stanie 
        //połączenia
        error = conn.recvInt();

        //jest to rozmiar wysłanych paczek
        int size = conn.recvInt();
        //System.out.println (id + " " + error + " " + size);

        //pętla która wykonuje się tyle razy ile wysłano do nas paczek z 
        //wiadomościami
        for (int i = 0; i < size; ++i)
        {

            //tablica która odszyfrowuje wiadomość uprzednio zaszyfrowaną 
            //przez serwer lub użytkownika z którym rozmawiamy naszym kluczem 
            //publicznym
            byte[] decrypt = RSA.decrypt(conn.recvByteArray(), 
                    conn.getKeyPair().getPrivateKeyInfo().getPrivateKey());

            //dodawanie pojedynczych wiadomosci do listy
            messageSignPair.add(new MessageSignPair(decrypt, 
                    conn.recvByteArray()));
            String m = new String(messageSignPair.get(i).getMessage());

            //sprawdzanie podpisu wiadomości, które upewni nas, że wiadomość 
            //nie została sfałszowana
            messageSignPair.get(i).checkSign(
                    publicKeyInfoToCheckSign.getPublicKey());
        }
    }

    private int error = 0;
    private int id = 0;
    private final ArrayList<MessageSignPair> messageSignPair = new ArrayList<>();
}
