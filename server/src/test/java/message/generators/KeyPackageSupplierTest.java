package message.generators;

import model.ClientPublicKeyInfo;
import model.containers.temporary.PublicKeysManager;
import model.exceptions.ElementNotFoundException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class KeyPackageSupplierTest {
    private static PublicKeysManager publicKeysManager;
    private static ClientPublicKeyInfo clientPublicKeyInfo;


    @BeforeClass
    public static void init() throws ElementNotFoundException {
        clientPublicKeyInfo = mock(ClientPublicKeyInfo.class);
        when(clientPublicKeyInfo.getModulus()).thenReturn(new BigInteger("12345678901234567890"));
        when(clientPublicKeyInfo.getExponent()).thenReturn(new BigInteger("123456789012345678901"));
        publicKeysManager = mock(PublicKeysManager.class);
        when(publicKeysManager.getClientPublicKeyInfo(anyInt())).thenReturn(clientPublicKeyInfo);
    }

    @Test
    public void testSupply() throws Exception {
        KeyPackageSupplier keyPackageSupplier = new KeyPackageSupplier(publicKeysManager);
        String[] array =  keyPackageSupplier.supply(1, "whatever");


        //BigInteger first_exp = new BigInteger(array[1].getBytes());

       /* assertEquals("whatever", array[0]);
        assertEquals("1234567890", array[1]);
        assertEquals("1234567890", array[2]);
        assertEquals("1234567890", array[3]);
        assertEquals("12345678901", array[4]);*/
    }
}