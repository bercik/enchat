package message.generarators;

import message.types.EncryptedMessage;
import messages.MessageId;
import responders.exceptions.ReactionException;

/**
 * Created by tochur on 01.05.15.
 */
public class Server_error {
    private static MessageId messageId = MessageId.SERVER_ERROR;
    public static EncryptedMessage unableToEncrypt() throws ReactionException {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 1));
    }

    public static EncryptedMessage unableToDecrypt() throws ReactionException {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 2));
    }

    public static EncryptedMessage serverOverloaded() throws ReactionException {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 3));
    }
}
