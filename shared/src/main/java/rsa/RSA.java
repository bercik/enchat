package rsa;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author mateusz
 * @version 1.0
 */
public final class RSA
{

    /**
     * Funkcja służy do szyfrowania przesłanej wiadomości w postaci tablicy
     * bajtów
     *
     * @param toEncrypt jest to tablica bajtów które powinniśmy zaszygrować
     * @param key jest to klucz publiczny osoby z którą się komunikujemy
     * @return funkcja zwraca zaszyfrowaną tablicę bajtów
     * @throws java.security.NoSuchAlgorithmException ten wyjątek rzucany jest
     * gdy chcemy wykorzystać konkretny algorytm kryptograficzny lecz nie jest
     * on dostępny w danym środowisku
     * @throws java.security.InvalidKeyException wyjątek rzucany gdy klucz jest
     * niesprawny (np. gdy ma nieodpowiednia długość itp)
     */
    public static byte[] encrypt(byte[] toEncrypt, Key key) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException
    {
        Cipher rsa = Cipher.getInstance(CIPHER_ALGORITHM_NAME);
        rsa.init(Cipher.ENCRYPT_MODE, key);
        return rsa.doFinal(toEncrypt);
    }

    /**
     *
     * @param toDecrypt jest to tablica bajtów która została zaszyfrowana
     * funkcją encrypt
     * @param key jest to klucz prywatny osoby która odebrała wiadomość
     * @return zwraca rozszyfrowaną tablicę bajtów z której możemy np stworzyć
     * Stringa
     * @throws java.security.NoSuchAlgorithmException ten wyjątek rzucany jest
     * gdy chcemy wykorzystać konkretny algorytm kryptograficzny lecz nie jest
     * on dostępny w danym środowisku
     * @throws java.security.InvalidKeyException wyjątek rzucany gdy klucz jest
     * niesprawny (np. gdy ma nieodpowiednia długość itp)
     */
    public static byte[] decrypt(byte[] toDecrypt, Key key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        Cipher rsa = Cipher.getInstance(CIPHER_ALGORITHM_NAME);
        rsa.init(Cipher.DECRYPT_MODE, key);
        return rsa.doFinal(toDecrypt);
    }

    /**
     *
     * @param toSign jest to tablica bajtów, której chcemy nadać podpis cyfrowy
     * @param privateKey jest to klucz prywatny
     * @return funkcja zwraca podpisaną tablicę bajtów
     * @throws java.security.NoSuchAlgorithmException ten wyjątek rzucany jest
     * gdy chcemy wykorzystać konkretny algorytm kryptograficzny lecz nie jest
     * on dostępny w danym środowisku
     * @throws java.security.InvalidKeyException wyjątek rzucany gdy klucz jest
     * niesprawny (np. gdy ma nieodpowiednia długość itp)
     * @throws java.security.SignatureException jest to ogólny wyjątek podpisu
     */
    public static byte[] sign(byte[] toSign, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException
    {
        Signature signature = Signature.getInstance(SIGN_ALGORITHM_NAME);
        signature.initSign(privateKey);
        signature.update(toSign);
        return signature.sign();
    }

    public static void checkSign(byte[] recvSign, byte[] message, PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException
    {
        try
        {
            Signature signature = Signature.getInstance(SIGN_ALGORITHM_NAME);
            signature.initVerify(publicKey);
            signature.update(message);

            if (signature.verify(recvSign) == false)
            {
                throw new CheckError("Check Sign Error");
            }
        }
        catch (CheckError ex)
        {
            ex.printStackTrace();
        }
    }

    public static byte[] hash(byte[] toHash)
    {
        try
        {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM_NAME);
            messageDigest.update(toHash);
            return messageDigest.digest();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Algorytm " + HASH_ALGORITHM_NAME + 
                    " nie istnieje", e);
        }
    }

    private final static String CIPHER_ALGORITHM_NAME = "RSA";
    private final static String SIGN_ALGORITHM_NAME = "SHA256withRSA";
    private final static String HASH_ALGORITHM_NAME = "SHA-256";

    public final static String STRING_CODING = "UTF-8";

    static class CheckError extends Exception
    {
        public CheckError(String message)
        {
            super(message);
        }
    }

    /*
     public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, SignatureException, UnsupportedEncodingException {
     KeyContainer container_client1 = new KeyContainer();
     String message = "Input message";
     byte[] toEncrypt = message.getBytes();
     byte[] encrypted = RSA.encrypt(toEncrypt, container_client1.getPublicKeyInfo().getPublicKey());
     byte[] decrypted = RSA.decrypt(encrypted, container_client1.getPrivateKeyInfo().getPrivateKey());
        
     System.out.println(new String(decrypted));
        
     byte[] sign = RSA.sign(message.getBytes(), container_client1.getPrivateKeyInfo().getPrivateKey());
        
     RSA.checkSign(sign, message.getBytes(), container_client1.getPublicKeyInfo().getPublicKey());
        
     }
     */
}
