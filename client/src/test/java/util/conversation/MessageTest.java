/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.conversation;

import org.joda.time.LocalTime;
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
public class MessageTest
{
    public MessageTest()
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
     * Test of append method, of class Message.
     */
    @Test
    public void testAppend()
    {
        System.out.println("append");
        String message1 = "pierwsza";
        String message2 = "druga";
        String username1 = "user1";
        String username2 = "user2";
        LocalTime dateFrom = LocalTime.now();
        LocalTime dateTo = dateFrom.plusMinutes(1);
        Message instance = new Message(message1, username1, dateFrom);
        instance.append(message2, dateTo);
        
        assertEquals(instance.getDateFrom(), dateFrom);
        assertEquals(instance.getDateTo(), dateTo);
    }
}
