package controller.responders.impl;

import controller.utils.cypher.Decryption;
import controller.utils.state.StateManager;
import message.generators.Sign_Up;
import message.types.Header;
import message.types.Message;
import message.types.UEMessage;
import message.types.UMessage;
import messages.MessageId;
import model.containers.permanent.Registration;
import server.sender.MessageSender;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

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

    private static SignUp signUP;
    private static UMessage sampleMessage;

    @BeforeClass
    public static void init(){
        decryption = mock(Decryption.class);
        stateManager = mock(StateManager.class);
        registration = mock(Registration.class);
        messageSender = mock(MessageSender.class);
        messages = mock(Sign_Up.class);
        sampleMessage = createSampleUMessage(7, "Tester", "Boarded");

    }

    public static UMessage createSampleUMessage(Integer authorID, String nick, String password){
        Header header = new Header(MessageId.SIGN_UP, MessageId.SIGN_UP.createErrorId(0), 2);
        String[] info = new String[] {nick, password};
        Message message = new Message(header, Arrays.asList(info));
        return new UMessage(authorID, message);
    }

    @Before
    public void initMethod(){
        signUP = new SignUp(decryption, stateManager, messageSender, registration, messages);
    }

    @Test
    public void allMethodsWereCalled() throws Exception {

        when(decryption.decryptMessage(any(UEMessage.class))).thenReturn(sampleMessage);

        signUP.run();

        verify(decryption).decryptMessage(any(UEMessage.class));
        verify(stateManager).verify(any(UEMessage.class));
        verify(registration).register(anyString(), anyString());
        verify(messageSender).send(any(UEMessage.class));
    }
}