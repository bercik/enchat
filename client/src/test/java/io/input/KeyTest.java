/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.input;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author robert
 */
public class KeyTest
{

    public KeyTest()
    {
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

    /**
     * Test of getKey method, of class Key.
     */
    @Test
    public void testGetKey()
    {
        System.out.println("getKey");
        char[] escChSeq = new char[]
        {
            27, 91, 65
        };
        Key expResult = Key.ARROW_UP;
        Key result = Key.getKey(escChSeq);
        assertEquals(expResult, result);

        escChSeq = new char[]
        {
            27, 91, 66, 27
        };
        expResult = Key.ARROW_DOWN;
        result = Key.getKey(escChSeq);
        assertEquals(expResult, result);
        
        escChSeq = new char[]
        {
            27
        };
        expResult = Key.ESC;
        result = Key.getKey(escChSeq);
        assertEquals(expResult, result);
        
        escChSeq = new char[]
        {
            27, 91, 64, 56
        };
        expResult = Key.UNKNOWN;
        result = Key.getKey(escChSeq);
        assertEquals(expResult, result);
    }
}
