package network;

import app_info.Configuration;
import rsa.KeyContainer;
import rsa.PublicKeyInfo;
import rsa.exceptions.GeneratingPublicKeyException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author mateusz
 * @version 1.0
 */
public class Connection
{

    public Connection() throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException, ClassNotFoundException,
            NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException,
            GeneratingPublicKeyException
    {

        //Obiekt klasy Configuration, który przechowuje informacje o adresie serwera
        //oraz jego numerze portu
        Configuration conn = Configuration.getInstance();

        //inicjalizacja zminnej socket która inicjalizuje połączenie między klientem a
        //serwerem
        socket = new Socket(conn.getServerAddress(), conn.getPort());

        //pobranie strumieniu z socketu
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());

        //pobranie klucza publicznego serwera ze strumienia
        serverPublicKey = new PublicKeyInfo(in);

        //wygenerowanie klucza publicznego i prywatnego klienta
        keyPair = new KeyContainer();

        //wysłanie klucza publicznego klienta do serwera
        keyPair.getPublicKeyInfo().send(out);
    }

    /**
     * Funkcja służy do wysyłania tablicy bajtów
     *
     * @param byteArray tablica byte którą chcemy wysłać do serwera
     */
    public void sendByteArray(byte[] byteArray) throws IOException
    {
        out.writeInt(byteArray.length);
        out.write(byteArray);
    }

    /**
     * Funkcja służąca do wysyłania liczby int
     *
     * @param id liczba którą chcemy wysłać do serwera
     */
    public void sendInt(int id) throws IOException
    {
        out.writeInt(id);
    }

    /**
     * Funkcja służąca do odbierania tablicy bajtów
     *
     * @return zwraca ona odebraną tablicę bajtów
     */
    public byte[] recvByteArray() throws IOException
    {
        int lenght = in.readInt();
        byte[] array = new byte[lenght];
        in.readFully(array);
        return array;
    }

    /**
     * Funkcja służąca do odbierania liczby int
     *
     * @return zwraca odebraną liczbę int
     */
    public int recvInt() throws IOException
    {
        return in.readInt();
    }

    /**
     * Funkcja zwracająca kontener kluczy
     *
     * @return jako rezultat funkcja zwraca kontener w którym przehcowywane są
     * nasze klucz (prywatny i publiczny)
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     */
    public KeyContainer getKeyPair() throws NoSuchAlgorithmException, 
            InvalidKeySpecException
    {
        return new KeyContainer(keyPair);
    }

    /**
     * Funkcja zwracająca klase opakowującą klucz publiczny
     *
     * @return jako rezultat funkcja zwraca obiekt klasy PublicKeyInfo, która
     * zawiera informacje o wszystkich parametrach klucza publicznego
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     */
    public PublicKeyInfo getServerPublicKey() throws NoSuchAlgorithmException, 
            InvalidKeySpecException
    {
        return new PublicKeyInfo(serverPublicKey);
    }

    // Funkcja która zamyka port pomiędzy serwerem a klientem
    // Nie rzuca wyjątków!
    public void close()
    {
        try
        {
            in.close();
            out.close();
            socket.close();
        }
        catch (IOException ex)
        {
        
        }
    }

    public boolean isEmpty() throws IOException
    {
        return in.available() <= 0;
    }

    private final PublicKeyInfo serverPublicKey;
    private final KeyContainer keyPair;
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;
}
