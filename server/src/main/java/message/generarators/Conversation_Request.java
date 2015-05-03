package message.generarators;

import message.types.EncryptedMessage;
import messages.MessageId;
import responders.exceptions.ReactionException;

/**
 * Created by tochur on 01.05.15.
 */
public class Conversation_Request {
    private static MessageId messageId = MessageId.CONVERSATION_REQUEST;

    public static EncryptedMessage connected() throws ReactionException {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 0));
    }

    public static EncryptedMessage notLogged() throws ReactionException {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 1));
    }

    public static EncryptedMessage busyUser() throws ReactionException {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 2));
    }

    public static EncryptedMessage onBlackList() throws ReactionException {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 4));
    }
}
