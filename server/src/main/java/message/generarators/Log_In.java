package message.generarators;

import message.types.EncryptedMessage;
import messages.MessageId;
import responders.exceptions.ReactionException;

/**
 * Created by tochur on 01.05.15.
 */
public class Log_In {
    private static MessageId messageId = MessageId.LOG_IN;

    public static EncryptedMessage loggedSuccessfully() throws ReactionException {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 0));
    }

    public static EncryptedMessage badLoginOrPassword() throws ReactionException {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 1));
    }

    public static EncryptedMessage toMuchUserLogged() throws ReactionException {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 2));
    }

    public static EncryptedMessage alreadyLogged() throws ReactionException {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 3));
    }
}
