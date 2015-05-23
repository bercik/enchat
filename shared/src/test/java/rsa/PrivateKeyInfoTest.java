package rsa;

import org.junit.Test;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.RSAPrivateCrtKeySpec;

import static org.junit.Assert.*;

/**
 * Created by mateusz on 23.05.15.
 */
public class PrivateKeyInfoTest {

    @Test
    public void testGetModulusAndExponent() throws Exception {
        System.out.println("getModulus() and getExponent() functions test : ");
        //stworzenie obiektu klasy PrivateKeyInfo
        PrivateKeyInfo privateKeyInfo = new PrivateKeyInfo();

        //Jeżeli liczby modulus i exponent w konstuktorze klasy PrivateKeyInfo będą takie same
        //jak liczby otrzymane przez funkcje getModulus() i getExponent() to test poprawny
        System.out.println("Modulus in test function  : " + privateKeyInfo.getModulus().toString());
        System.out.println("Exponent in test function : " + privateKeyInfo.getExponent().toString());
        System.out.println("\n");
    }

    @Test
    public void testGetPrivateKey() throws Exception {
        System.out.println("getPrivateKey() functions test : ");
        //stworzenie obiektu klasy PrivateKeyInfo
        PrivateKeyInfo privateKeyInfo = new PrivateKeyInfo();

        //Sprawdzimy czy po zwrócenia klucza prywatnego przez funkcje getPrivateKey i po wygenerowaniu
        //specyfikacji na podstawie klucza prywatnego, zwróci ona taki sam modulus i exponent jakimi został
        //zainicjalizowany obiekt privateKeyInfo
        PrivateKey privateKey = privateKeyInfo.getPrivateKey();
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateCrtKeySpec rsaPrivateCrtKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateCrtKeySpec.class);

        System.out.println("Modulus in test function  : " + rsaPrivateCrtKeySpec.getModulus().toString());
        System.out.println("Exponent in test function : " + rsaPrivateCrtKeySpec.getPrivateExponent().toString());
        System.out.println("\n");
    }
}