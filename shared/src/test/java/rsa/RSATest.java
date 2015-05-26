package rsa;

import org.junit.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import static org.junit.Assert.*;

/**
 * Created by mateusz on 26.05.15.
 */
public class RSATest {

    @Test
    public void testEncryptAndDecrypt() throws Exception {
        System.out.println("encrypt() and decrypt() functions test");
        //Generujemy klucz publiczny i prywatny
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        //Wiadomość do zaszyfrowanie i odszyfrowania
        String test = "Unit test";
        byte[] toEncrypt = test.getBytes();

        //etap szyfrowania
        byte[] encryptedArray = RSA.encrypt(toEncrypt, publicKey);
        //etap odszywrowywania tablicy
        byte[] decryptedArray = RSA.decrypt(encryptedArray, privateKey);

        assertArrayEquals("Tablice nie są identyczne", decryptedArray, toEncrypt);
        assertEquals("Stringi nie są identyczne", new String(decryptedArray), test);
    }

    @Test
    public void testSignAndCheckSign() throws Exception {
        System.out.println("sign() and checkSign() functions test");
        //Generujemy klucz publiczny i prywatny
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        String test = "Unit test";
        byte[] toSign = test.getBytes();

        //podpisujemy tablice
        byte[] signedArray = RSA.sign(toSign, privateKey);

        //sprawdzamy podpis i jeżeli się nie zgadza to wyrzuca wyjątek
        boolean passsedTest = true;
        try {
            RSA.checkSign(signedArray,toSign ,publicKey);
        } catch (Exception ex) {
            if(RSA.CheckError.class == ex.getClass())
                passsedTest = false;
        }

        assertFalse("Test sprawdzania podpisu niezdany", !passsedTest);
    }
}