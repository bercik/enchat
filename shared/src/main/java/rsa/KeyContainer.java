package rsa;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 *
 * @author mateusz
 * @version 1.0
 */
final public class KeyContainer {
    
    public KeyContainer(KeyContainer keyContainer) throws NoSuchAlgorithmException, InvalidKeySpecException {
        publicKeyInfo = keyContainer.getPublicKeyInfo();
        privateKeyInfo = keyContainer.getPrivateKeyInfo();
    }
    
    /**
     * Konstruktor który tworzy pusty kontener. W istocie ciało konstruktora mogłoby
     * pozostać puste ponieważ defaultowo składowe typów niewbudowanych mają wartość
     * null. Konstruktor stworzony w celu lepszej przejrzystości kodu
     * @throws java.security.NoSuchAlgorithmException informuje nas że algorytm nie jest wykryty
     * @throws java.security.spec.InvalidKeySpecException
     */
    public KeyContainer() throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.generateKeyPair();
    }
    
    public PublicKeyInfo getPublicKeyInfo() throws NoSuchAlgorithmException, InvalidKeySpecException {
        return new PublicKeyInfo(this.publicKeyInfo.getPublicKey());
    }
    
    public PrivateKeyInfo getPrivateKeyInfo() throws NoSuchAlgorithmException, InvalidKeySpecException {
        return new PrivateKeyInfo(this.privateKeyInfo.getPrivateKey());
    }
    
    
    /**
     * Funkcja generująca klucz publiczny i prywatny który zapisywany jest w obiektach
     * klasy PrivateKeyInfo i PublicKeyInfo
     */
    private void generateKeyPair() throws NoSuchAlgorithmException, InvalidKeySpecException {
        /**Jako że klasa KeyPairGenerator jest abstrakcyjna nie mogliśmy stworzyć obiektu
        tej klasy za pomocą kostruktora tej klasy. Skorzystaliśmy z metody statycznej
        która zwraca obiekt KeyPairGenerator który generuje klucz publiczny/privatny
        przy pomocy odpowiedniego algorytmu*/
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
            
      /**
        * używając kontruktorów klas PublicKeyInfo i PrivateKeyInfo 
        * stworzysliśmy obiekty klas (Private/Public)KeyInfo w celu przechowania
        * klucza publicznego i prywatnego
        */
        publicKeyInfo = new PublicKeyInfo(keyPair.getPublic());
        privateKeyInfo = new PrivateKeyInfo(keyPair.getPrivate());
    }
    
    private static final String ALGORITHM = "RSA";
    private PublicKeyInfo publicKeyInfo;
    private PrivateKeyInfo privateKeyInfo;
    

/*********** DLA TESTERÓW NIEROBÓW :* <3 *********/
    //wystarczy sprawdzić czy liczby modulus są sobie równe
    //ponieważ w kluczu prywatnym i publicznym mają one być 
    //takie same
    /*
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyContainer container = new KeyContainer();
        
        System.out.println("Private Key : modulus = " + container.getPrivateKeyInfo().getModulus() + " exponent = " + container.getPrivateKeyInfo().getExponent());
        System.out.println("Public Key  : modulus = " + container.getPublicKeyInfo().getModulus() + " exponent = " + container.getPublicKeyInfo().getExponent());
    }
    */
}
