/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author robert
 */
public class MessageIdTest
{

    public MessageIdTest()
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
     * Test of createErrorId method, of class MessageId.
     */
    @Test
    public void testCreateErrorId() throws Exception
    {
        System.out.println("createErrorId");
        int id = 1;
        int errorId = 1;
        MessageId instance = MessageId.LOG_IN;
        MessageId.ErrorId expResult = MessageId.ErrorId.BAD_LOGIN_OR_PASSWORD;
        MessageId.ErrorId result = instance.createErrorId(id);
        assertThat(expResult, is(result));

        id = 0;
        result = instance.createErrorId(id);
        assertThat(expResult, not(result));
    }

    /**
     * Test of createMessageId method, of class MessageId.
     */
    @Test
    public void testCreateMessageId() throws Exception
    {
        System.out.println("createMessageId");
        int id = 0;
        MessageId expResult = MessageId.JUNK;
        MessageId result = MessageId.createMessageId(id);
        assertEquals(expResult, result);

        id = 1;
        result = MessageId.createMessageId(id);
        assertThat(expResult, not(result));
    }

    /**
     * Test of getIntRepresentation method, of class MessageId.
     */
    @Test
    public void testGetIntRepresentation() throws Exception
    {
        System.out.println("getIntRepresentation");
        MessageId instance = MessageId.JUNK;
        int expResult = 0;
        int result = instance.getIntRepresentation();
        assertEquals(expResult, result);

        result = instance.createErrorId(0).getIntRepresentation();
        assertEquals(expResult, result);
    }

}
