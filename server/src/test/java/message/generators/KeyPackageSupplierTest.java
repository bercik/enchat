package message.generators;

import model.ClientPublicKeyInfo;
import model.containers.temporary.PublicKeysManager;
import model.exceptions.ElementNotFoundException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigInteger;
import static org.junit.Assert.*;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class KeyPackageSupplierTest
{
    private static PublicKeysManager publicKeysManager;
    private static ClientPublicKeyInfo clientPublicKeyInfo;

    @BeforeClass
    public static void init() throws ElementNotFoundException
    {
        clientPublicKeyInfo = mock(ClientPublicKeyInfo.class);
        when(clientPublicKeyInfo.getModulus()).thenReturn(new BigInteger("12345678901234567890"));
        when(clientPublicKeyInfo.getExponent()).thenReturn(new BigInteger("123456789012345678901"));
        publicKeysManager = mock(PublicKeysManager.class);
        when(publicKeysManager.getClientPublicKeyInfo(anyInt())).thenReturn(clientPublicKeyInfo);
    }

    public byte[] concat(byte[] a, byte[] b)
    {
        int aLen = a.length;
        int bLen = b.length;
        byte[] c = new byte[aLen + bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }

    @Test
    public void testSupply() throws Exception
    {
        BigInteger expModulus = new BigInteger("12345678901234567890");
        BigInteger expExponent = new BigInteger("123456789012345678901");

        KeyPackageSupplier keyPackageSupplier = new KeyPackageSupplier(publicKeysManager);
        String[] array = keyPackageSupplier.supply(1, "whatever");


        // wyciągamy parametry do lokalnych referencji
        String username = array[0];
        // modulus i exponent zostały zamienione na stringa każdy i wysłane
        // znak po znaku (każdy znak to cyfra), a oddzielone są znakiem ;
        String modulusString = "";
        String exponentString = "";

        // zmienna pomocnicza
        boolean isModulus = true;

        for (int i = 1; i < array.length; ++i){
            if (array[i].equals(";")){
                isModulus = false;
                continue;
            }

            if (isModulus){
                modulusString += array[i];
            }
            else{
                exponentString += array[i];
            }
        }

        BigInteger modBigInteger = new BigInteger(modulusString);
        BigInteger expBigInteger = new BigInteger(exponentString);

        assertEquals(expModulus, modBigInteger);
        assertEquals(expExponent, expBigInteger);
    }

   /* @Test
    public void testSupply() throws Exception
    {
        BigInteger expModulus = new BigInteger("12345678901234567890");
        BigInteger expExponent = new BigInteger("123456789012345678901");
        
        KeyPackageSupplier keyPackageSupplier = new KeyPackageSupplier(publicKeysManager);
        String[] array = keyPackageSupplier.supply(1, "whatever");

        byte[] array1 = array[1].getBytes("UTF-8");
        byte[] array2 = array[2].getBytes("UTF-8");
        byte[] array3 = array[3].getBytes("UTF-8");
        byte[] array4 = array[4].getBytes("UTF-8");

        byte[] modulus = concat(array1, array2);
        byte[] exponent = concat(array3, array4);
        
        BigInteger modBigInteger = new BigInteger(modulus);
        BigInteger expBigInteger = new BigInteger(exponent);

//        assertEquals(expModulus, modBigInteger);
//        assertEquals(expExponent, expBigInteger);
    }*/
}
