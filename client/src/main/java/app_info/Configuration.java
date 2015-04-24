package app_info;

import java.io.*;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import rsa.PublicKeyInfo;
import rsa.exceptions.GeneratingPublicKeyException;

/**
 *
 * @author mateusz
 * @version 1.0
 */
public class Configuration {

    //konstruktor prywatny potrzebny do wygenerowania i potrzebnych informacji
    private Configuration() throws IOException, GeneratingPublicKeyException {
        width = 122;
        height = 36;
        loadFromFile(path);
    }

    //funkcja którą będziemy wywoływać gdy będziemy potrzbowali informacji o konfiguracji
    public static Configuration getInstance() throws IOException, GeneratingPublicKeyException {
        if(instance == null)
            instance = new Configuration();
        return instance;
    }

    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public String getServerAddress() {
        return serverAddress;
    }
    
    public int getPort() {
        return port;
    }
    
    public PublicKeyInfo getServerPublicKeyInfo() throws NoSuchAlgorithmException, InvalidKeySpecException {
        return new PublicKeyInfo(serverPublicKeyInfo);
    }

    /**
     *
     * @param path zmienna typu String określająca nazwę ścieżki gdzie znajduje się
     *             plik w którym zawierają się informacje o adresie serwera, jego porcie
     *             oraz o liczbach exponent i modulus potrzebnych do wygenerowania klucza
     *             publicznego serwera
     * @throws IOException
     * @throws GeneratingPublicKeyException wyjątek stworzony przez super programistów
     */
    public void loadFromFile(String path) throws IOException, GeneratingPublicKeyException {
        FileInputStream in = new FileInputStream(path);
        DataInputStream input = new DataInputStream(in);

        //wczytywanie adresu serwera z pliku
        byte[] byteArray = new byte[input.readInt()];
        input.readFully(byteArray);
        serverAddress = new String(byteArray);

        //wczytywanie numeru portu z pliku
        port = input.readInt();

        //wczytywanie modulus i exponent z pliku w celu stworzenia klucza publicznego serwera
        serverPublicKeyInfo = new PublicKeyInfo(input);
    }

    //zmienna którą w razie potrzeby będziemy zwracać
    private static Configuration instance = null;

    //rozmiar naszej konsoli
    private int width = 122;
    private int height = 36;

    //zmienne te będą wczytywane z pliku
    private static String serverAddress;
    private static int port;
    private static PublicKeyInfo serverPublicKeyInfo;

    //zmienna pomocnicza przechowująca nazwę pliku w którym przechowujemy
    //adres serwera, numer portu oraz modulus i exponent klucza publicznego serwera(wersja "żeby działało")
    private static final String path = "file.txt";

/*
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, GeneratingPublicKeyException {
        FileOutputStream outFile = new FileOutputStream("file.txt");
        DataOutputStream out = new DataOutputStream(outFile);

        //wysyłanie do pliku
        byte[] byteArray = "123.123.0.1".getBytes();
        out.writeInt(byteArray.length);
        out.write(byteArray);

        //zapisanie portu
        out.writeInt(123);

        //zapisywanie publicKeyInfo
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger("100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"), new BigInteger("1234"));
        PublicKey publicKey = keyFactory.generatePublic(pubKeySpec);
        PublicKeyInfo publicKeyInfo = new PublicKeyInfo(publicKey);

        publicKeyInfo.send(out);


        Configuration.loadFromFile("file.txt");
        System.out.println(Configuration.serverAddress);
        System.out.println(Configuration.getPort());
        System.out.println("Recv modulus = " + Configuration.getServerPublicKeyInfo().getModulus());
        System.out.println("Recv exponent = " + Configuration.getServerPublicKeyInfo().getExponent());
    }
*/
}
