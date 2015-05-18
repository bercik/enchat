package model.user;

import messages.MessageId;
import org.junit.Test;

import static org.junit.Assert.*;

public class VerifierTest {

    @Test
    public void testVerify() throws Exception {
        Verifier verifier = new Verifier();

        assertTrue(verifier.verify(UserState.IN_ROOM, MessageId.CLIENTS_LIST));
        assertTrue(verifier.verify(UserState.LOGGED, MessageId.CLIENTS_LIST));
        assertTrue(verifier.verify(UserState.CONNECTED_TO_SERVER, MessageId.LOG_IN));
        assertTrue(verifier.verify(UserState.CONNECTED_TO_SERVER, MessageId.CLIENTS_LIST));
        assertTrue(verifier.verify(UserState.CONNECTED_TO_SERVER, MessageId.SIGN_UP));
        assertTrue(verifier.verify(UserState.LOGGED, MessageId.LOGOUT));
        assertTrue(verifier.verify(UserState.IN_ROOM, MessageId.REMOVE_FROM_BLACK_LIST));
        assertTrue(verifier.verify(UserState.IN_ROOM, MessageId.CONVERSATIONALIST_DISCONNECTED));

        assertFalse(verifier.verify(UserState.CONNECTED_TO_SERVER, MessageId.BLACK_LIST));
        assertFalse(verifier.verify(UserState.CONNECTED_TO_SERVER, MessageId.CONVERSATIONALIST_DISCONNECTED));
    }
}