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
    
    public static int getWidth() {
        return width;
    }
    
    public static int getHeight() {
        return height;
    }
    
    public static String getServerAddress() {
        return new String(serverAddress.getBytes());
    }
    
    public static int getPort() {
        return port;
    }
    
    public static PublicKeyInfo getServerPublicKeyInfo() throws NoSuchAlgorithmException, InvalidKeySpecException {
        return new PublicKeyInfo(serverPublicKeyInfo);
    }

    public static void loadFromFile(String path) throws IOException, GeneratingPublicKeyException {
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

    private Configuration instance;

    private final static int width = 122;
    private final static int height = 36;

    //zmienne te będą wczytywane z pliku
    private static String serverAddress;
    private static int port;
    private static PublicKeyInfo serverPublicKeyInfo;


    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
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
        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger("100000000000000000000"), new BigInteger("1234"));
        PublicKey publicKey = keyFactory.generatePublic(pubKeySpec);
        PublicKeyInfo publicKeyInfo = new PublicKeyInfo(publicKey);

        publicKeyInfo.send(out);
    }
}
