/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_client_communication;

import java.math.BigInteger;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rsa.KeyContainer;
import rsa.PublicKeyInfo;

/**
 *
 * @author robert
 */
public class ConversationPublicKeyExchangeTest
{

    public ConversationPublicKeyExchangeTest()
    {
    }

    @Test
    public void TestExchange() throws Exception
    {
        // generate public key
        rsa.KeyContainer keyContainer = new KeyContainer();
        PublicKeyInfo publicKI = keyContainer.getPublicKeyInfo();

        // get modulus and exponent
        BigInteger expModulusInteger = publicKI.getModulus();
        BigInteger expExponentInteger = publicKI.getExponent();

        // get byte arrays and split into 4
        byte[] modulusByteArray = expModulusInteger.toByteArray();
        byte[] exponentByteArray = expExponentInteger.toByteArray();

        byte[] modFirst = new byte[0];
        byte[] modSecond = new byte[0];
        byte[] expFirst = new byte[0];
        byte[] expSecond = new byte[0];

        int length = modulusByteArray.length;
        if (length > 0)
        {
            modFirst = Arrays.copyOfRange(modulusByteArray, 0, length / 2);
            modSecond = Arrays.copyOfRange(modulusByteArray, length / 2, 
                    length);
        }

        length = exponentByteArray.length;
        if (length > 0)
        {
            expFirst = Arrays.copyOfRange(exponentByteArray, 0, length / 2);
            expSecond = Arrays.copyOfRange(exponentByteArray, length / 2, 
                    length);
        }
        
        String STRING_CODING = "UTF-8";
        
        /* TUTAJ SIĘ SYPIE
        // convert to string arrays
        String[] strArray = new String[4];
        strArray[0] = new String(modFirst, STRING_CODING);
        strArray[1] = new String(modSecond, STRING_CODING);
        strArray[2] = new String(expFirst, STRING_CODING);
        strArray[3] = new String(expSecond, STRING_CODING);
        
        // convert back to byte arrays
        byte[] convModFirst = strArray[0].getBytes(STRING_CODING);
        byte[] convModSecond = strArray[1].getBytes(STRING_CODING);
        byte[] convExpFirst = strArray[2].getBytes(STRING_CODING);
        byte[] convExpSecond = strArray[3].getBytes(STRING_CODING);
        
        // test
        assertEquals(modFirst, convModFirst);
        assertEquals(modSecond, modSecond);
        assertEquals(expFirst, expFirst);
        assertEquals(expSecond, expSecond);
        // KONIEC SYPANIA SIĘ */
        
        // send over TCP
        
        // merge back
        modulusByteArray = concat(modFirst, modSecond);
        exponentByteArray = concat(expFirst, expSecond);
        
        // convert to big ints
        BigInteger modulusInteger = new BigInteger(modulusByteArray);
        BigInteger exponentInteger = new BigInteger(exponentByteArray);
        
        assertEquals(expModulusInteger, modulusInteger);
        assertEquals(expExponentInteger, exponentInteger);
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
    
    @BeforeClass
    public static void setUpClass()
    {
    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
