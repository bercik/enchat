package message.generators;

import message.types.EncryptedMessage;
import messages.MessageId;

/**
 * Created by tochur on 01.05.15.
 */
public class Server_error {
    private MessageId serverError = MessageId.SERVER_ERROR;
    public EncryptedMessage unableToEncrypt() {
        return new EncryptedMessage(HeaderGenerator.createHeader(serverError, 1));
    }

    public EncryptedMessage unableToDecrypt() {
        return new EncryptedMessage(HeaderGenerator.createHeader(serverError, 2));
    }

    public EncryptedMessage serverOverloaded() {
        return new EncryptedMessage(HeaderGenerator.createHeader(serverError, 3));
    }
}
