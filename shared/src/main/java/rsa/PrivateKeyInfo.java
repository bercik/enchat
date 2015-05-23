package rsa;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateCrtKeySpec;

/**
 *
 * @author mateusz
 * @version 1.0
 */
final public class PrivateKeyInfo {

    /*Do testów jednostkowych*/
    public PrivateKeyInfo() throws NoSuchAlgorithmException, InvalidKeySpecException {
        //wygenerowanie klucza prywatnego
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        privateKey = keyPair.getPrivate();

        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        RSAPrivateCrtKeySpec rsaPrivateCrtKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateCrtKeySpec.class);
        modulus = rsaPrivateCrtKeySpec.getModulus();
        exponent = rsaPrivateCrtKeySpec.getPrivateExponent();

        System.out.println("Modulus in PrivateKeyInfo constructor  : " + modulus.toString());
        System.out.println("Exponent in PrivateKeyInfo constructor : " + exponent.toString());
    }
    
    public PrivateKeyInfo(PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {

        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        RSAPrivateCrtKeySpec privKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateCrtKeySpec.class);
            
        modulus = privKeySpec.getModulus();
        exponent = privKeySpec.getPrivateExponent();
            
        this.privateKey = keyFactory.generatePrivate(privKeySpec);
    }
    
    
    public BigInteger getModulus() {
        return new BigInteger(modulus.toString());
    }
    
    public BigInteger getExponent() {
        return new BigInteger(exponent.toString());
    }
    
    public PrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        RSAPrivateCrtKeySpec spec = keyFactory.getKeySpec(privateKey, RSAPrivateCrtKeySpec.class);
        return keyFactory.generatePrivate(spec);
    }
    
  /**
    * Są to dwie bardzo duże liczby z których składa się klucz publiczny i prywatny
    * -klucz publiczny składa się z liczby modolus i publicznego exponentu
    * -klucz prywatny składa się z tej samej liczby modulus i prywantego exponentu
    * UWAGA DLA TESTERÓW : jeżeli będziecie chcieli sprawdzić czy klucze zostały
    * wygenerowane poprawnie, wystarczy sprawdzić czy liczby modulus w kluczu
    * publicznym i prywatnym są sobie równe.
    */
    private final BigInteger modulus;
    private final BigInteger exponent;
    
    private final PrivateKey privateKey;
    private final String ALGORITHM = "RSA";

/*
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        
        PrivateKey key = keyPair.getPrivate();
        
        PrivateKeyInfo prvKeyInfo = new PrivateKeyInfo(key);
        
        System.out.println("modulus = " + prvKeyInfo.getModulus());
        System.out.println("exponent = " + prvKeyInfo.getExponent());

    }
*/
}
