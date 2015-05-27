package model.containers.temporary;

import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.security.PublicKey;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class PublicKeysManagerTest {
    private PublicKeysManager publicKeysManager;
    private BigInteger modulus;
    private BigInteger exponent;
    private BigInteger modulus2;
    private BigInteger exponent2;
    private PublicKey publicKey1;
    private PublicKey publicKey2;

    @Before
    public void init(){
        modulus = new BigInteger("123456789");
        exponent = new BigInteger("987654321");
        modulus2 = new BigInteger("123456789");
        exponent2 = new BigInteger("987654321");
    }

    @Test
    public void adding_key_works_correctly() throws Exception {
        //given
        publicKeysManager = new PublicKeysManager(new PublicKeys());
        publicKey1 = mock(PublicKey.class);
        publicKey2 = mock(PublicKey.class);

        //when
        publicKeysManager.addKey(0, publicKey1, modulus, exponent);
        publicKeysManager.addKey(1, publicKey2, modulus2, exponent2);

        //then
        assertEquals(publicKey1, publicKeysManager.getKey(0));
        assertEquals(publicKey2, publicKeysManager.getKey(1));

        assertEquals(modulus, publicKeysManager.getClientPublicKeyInfo(0).getModulus());
        assertEquals(modulus2, publicKeysManager.getClientPublicKeyInfo(1).getModulus());

        assertEquals(exponent, publicKeysManager.getClientPublicKeyInfo(0).getExponent());
        assertEquals(exponent2, publicKeysManager.getClientPublicKeyInfo(1).getExponent());

        assertEquals(publicKey1, publicKeysManager.getClientPublicKeyInfo(0).getPublicKey());
        assertEquals(publicKey2, publicKeysManager.getClientPublicKeyInfo(1).getPublicKey());
    }
}