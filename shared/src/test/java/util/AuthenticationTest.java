/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

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
public class AuthenticationTest
{
    
    public AuthenticationTest()
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
     * Test of isLoginCorrect method, of class Authentication.
     */
    @Test
    public void testIsLoginCorrect()
    {
        System.out.println("isLoginCorrect");
        
        // pusty login
        String login = "";
        boolean expResult = false;
        boolean result = Authentication.isLoginCorrect(login);
        assertEquals(expResult, result);
        
        // login ze spacją na początku
        login = " s_sa_d";
        expResult = false;
        result = Authentication.isLoginCorrect(login);
        assertEquals(expResult, result);
        
        // login z cyfrą na początku
        login = "1robert";
        expResult = false;
        result = Authentication.isLoginCorrect(login);
        assertEquals(expResult, result);
        
        // login z podkreślnikiem na początku
        login = "_robert";
        expResult = false;
        result = Authentication.isLoginCorrect(login);
        assertEquals(expResult, result);
        
        // login z za małą liczbą znaków
        login = "rob";
        expResult = false;
        result = Authentication.isLoginCorrect(login);
        assertEquals(expResult, result);
        
        // login z za dużą liczbą znaków
        login = "to_jest_za_dlugi_login";
        expResult = false;
        result = Authentication.isLoginCorrect(login);
        assertEquals(expResult, result);
        
        // login z niedozwolonymi znakami
        login = "ąęźćśąðœą";
        expResult = false;
        result = Authentication.isLoginCorrect(login);
        assertEquals(expResult, result);
        
        // poprawny login
        login = "rob_log1";
        expResult = true;
        result = Authentication.isLoginCorrect(login);
        assertEquals(expResult, result);
    }
    
}
