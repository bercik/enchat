package message.generarators;

import message.types.EncryptedMessage;
import messages.MessageId;
import responders.exceptions.ReactionException;

/**
 * Created by tochur on 01.05.15.
 */
public class Sign_Up {
    public static MessageId messageId = MessageId.SIGN_UP;

    public static EncryptedMessage ok() throws ReactionException {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 0));
    }

    public static EncryptedMessage busyLogin() throws ReactionException {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 1));
    }

    public static EncryptedMessage incorrectLogin() throws ReactionException {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 2));
    }

    public static EncryptedMessage badPasswordLength() throws ReactionException {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 3));
    }

    public static EncryptedMessage toManyAccounts() throws ReactionException {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 4));
    }
}
