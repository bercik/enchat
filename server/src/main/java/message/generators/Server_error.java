package message.generators;

import message.types.EncryptedMessage;
import message.types.UEMessage;
import messages.MessageId;

/**
 * Created by tochur on 01.05.15.
 */
public class Server_error {
    private MessageId serverError = MessageId.SERVER_ERROR;
    private EncryptedMessage encrypted;

    public UEMessage unableToEncrypt(Integer receiverID) {
        encrypted =  new EncryptedMessage(HeaderGenerator.createHeader(serverError, 1));
        return new UEMessage(receiverID, encrypted);
    }

    public UEMessage unableToDecrypt(Integer receiverID) {
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(serverError, 2));
        return new UEMessage(receiverID, encrypted);
    }

    public UEMessage serverOverloaded(Integer receiverID) {
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(serverError, 3));
        return new UEMessage(receiverID, encrypted);
    }
}
