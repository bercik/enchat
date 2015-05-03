package message.generators;

import message.types.EncryptedMessage;
import messages.MessageId;

/**
 * Created by tochur on 01.05.15.
 */
public class Server_error {
    private static MessageId messageId = MessageId.SERVER_ERROR;
    public static EncryptedMessage unableToEncrypt() {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 1));
    }

    public static EncryptedMessage unableToDecrypt() {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 2));
    }

    public static EncryptedMessage serverOverloaded() {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 3));
    }
}
