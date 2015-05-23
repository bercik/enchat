package rsa;

import org.junit.Test;

import javax.sound.midi.SysexMessage;
import java.io.*;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;

import static org.junit.Assert.*;

/**
 * Created by mateusz on 23.05.15.
 */
public class PublicKeyInfoTest {

    @Test
    public void testSend() throws Exception {
        System.out.println("send() function test : ");
        //tworzymy nowy obiekt klasy PublicKeyInfo aby przetestować funkcje send()
        PublicKeyInfo publicKeyInfo = new PublicKeyInfo();

        //Aby przetestować tą funkcje użyjemy zapisywania do pliku modulus i exponent
        //w pliku
        OutputStream outputStream = new FileOutputStream("test.txt");
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        publicKeyInfo.send(dataOutputStream);

        //teraz odczytamy to co zapisaliśmy i sprawdzimy czy wszystko się zgadza
        InputStream inputStream = new FileInputStream("test.txt");
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        //Teraz wykorzystamy kostruktor który jako argument przyjmuje obiekt klasy
        //DataInputStream i sprawdzmimy obiekty klasy PublicKeyInfo porównując modulus i exponent
        PublicKeyInfo publicKeyInfo1 = new PublicKeyInfo(dataInputStream);

        System.out.println("\n");
        System.out.println("Send Modulus    : " + publicKeyInfo.getModulus().toString());
        System.out.println("Send Exponent   : " + publicKeyInfo.getExponent().toString());
        System.out.println("Recv Modulus    : " + publicKeyInfo1.getModulus().toString());
        System.out.println("Recv Exponent   : " + publicKeyInfo1.getExponent().toString());
        System.out.println("\n");

    }

    @Test
    public void testGetPublicKey() throws Exception {
        System.out.println("getPublicKey() function test :");

        final String ALGORITHM = "RSA";

        //tworzymy nowy obiekt klasy PublicKeySpec
        PublicKeyInfo publicKeyInfo = new PublicKeyInfo();

        //Na obiekcie klasy PubliKeyInfo wywołujemy funkcję getPublicKey
        PublicKey publicKey = publicKeyInfo.getPublicKey();

        //tworzymy nowy obiekt specyfikacji klucza publicznego na podstawie klucza publicznego
        //uzyskanego z metody getPublicKey(). Jeżeli liczby modulus i exponent w kostruktorze klasy
        //PublicKeyInfo będę się zgadzały z tymi które uzyskamy w funkcji testującej tzn, że funkcja
        //getPublicKey() działa poprawnie
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        RSAPublicKeySpec rsaPublicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);

        //Wypisujemy modulus i exponent
        System.out.println("Modulus in test function    : " + rsaPublicKeySpec.getModulus().toString());
        System.out.println("Exponent in test function   : " + rsaPublicKeySpec.getPublicExponent().toString());
        System.out.println("\n");
    }

    @Test
    public void testGetModulusAndExponent() throws Exception {
        System.out.println("getModulus() and getExponent() functions test : ");

        //tworzymy nowy obiekt klasy PublicKeySpec
        PublicKeyInfo publicKeyInfo = new PublicKeyInfo();

        //Jeżeli modulus i exponent jaki otrzymamy poprzez wywołanie funkcji getModulus()
        //oraz getExponent() będą zgadzały się z liczbami modulus i exponent tworzonymi
        //w kostruktorze klasy PublicKeyInfo tzn, że obie te funkcje działają poprawnie
        System.out.println("Modulus in test function    : " + publicKeyInfo.getModulus().toString());
        System.out.println("Exponent in test function   : " + publicKeyInfo.getExponent().toString());
    }
}