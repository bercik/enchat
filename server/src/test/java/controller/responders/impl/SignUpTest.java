package controller.responders.impl;

import controller.utils.cypher.Decryption;
import controller.utils.state.StateManager;
import message.generators.Sign_Up;
import message.types.UEMessage;
import model.containers.permanent.Registration;
import newServer.sender.MessageSender;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class SignUpTest {
    static Decryption decryption;
    static StateManager stateManager;
    static Registration registration;
    static MessageSender messageSender;
    static Sign_Up messages;

    private SignUp signUP;

    @BeforeClass
    public static void init(){
        decryption = mock(Decryption.class);
        stateManager = mock(StateManager.class);
        registration = mock(Registration.class);
        messageSender = mock(MessageSender.class);
        messages = mock(Sign_Up.class);
    }

    @Before
    public void initMethod(){
        signUP = new SignUp(decryption, stateManager, messageSender, registration, messages);
    }

    @Test
    public void testServeEvent() throws Exception {
        /*UEMessage ueMessage = mock(UEMessage.class);
        if(ueMessage == null){
            System.out.println("Mock object = null");
        }
        signUP.serveEvent(ueMessage);
        assertTrue("Message wasn't assigned", signUP.ueMessage == ueMessage);*/

        //NIE WIEM JAK PRZETESTOWAĆ ODPALENIE WĄTKU
    }

    @Test
    public void allMethodsWereCalled() throws Exception {
        /*Sprawdź, czy trzeba mockować zachowanie mocków.
        * when(decryption.decryptMessage(any(UEMessage.class))).thenReturn(null);
        * */

        verify(decryption).decryptMessage(any(UEMessage.class));
        verify(stateManager).verify(any(UEMessage.class));
        verify(registration).register(anyString(), anyString());
        verify(messageSender).send(any(UEMessage.class));
        verify(messages).ok(anyInt());
    }
}