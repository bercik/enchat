package app_info;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import rsa.PublicKeyInfo;

/**
 *
 * @author mateusz
 * @version 1.0
 */
public class Configuration {
    
    public Configuration() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
        serverPublicKeyInfo = new PublicKeyInfo(keyFactory.generatePublic(spec));
    }
    
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
    
    public PublicKeyInfo getServerPublicKeyInfo() throws NoSuchAlgorithmException, InvalidKeySpecException {
        return new PublicKeyInfo(serverPublicKeyInfo);
    }

    private final static int width = 122;
    private final static int height = 36;
    private final static String serverAddress = "localhost";
    private final static int port = 50000;
    private final PublicKeyInfo serverPublicKeyInfo;
    
    //zmienne pomocnicze
    private static final BigInteger modulus = new BigInteger("10000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
    private static final BigInteger exponent = new BigInteger("10000");
    private static final String ALGORITHM = "RSA";
}
