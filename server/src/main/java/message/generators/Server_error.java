package message.generators;

import message.types.EncryptedMessage;
import message.types.UEMessage;
import messages.MessageId;

/**
 * @author Created by tochur on 01.05.15.
 */
public class Server_error {
    private static MessageId serverError = MessageId.SERVER_ERROR;
    private static EncryptedMessage encrypted;

    public static UEMessage unableToEncrypt(Integer receiverID) {
        encrypted =  new EncryptedMessage(HeaderGenerator.createHeader(serverError, 1));
        return new UEMessage(receiverID, encrypted);
    }

    public static UEMessage unableToDecrypt(Integer receiverID) {
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(serverError, 2));
        return new UEMessage(receiverID, encrypted);
    }

    public static UEMessage serverOverloaded(Integer receiverID) {
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(serverError, 3));
        return new UEMessage(receiverID, encrypted);
    }

    public static UEMessage other(Integer receiverID) {
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(serverError, 4));
        return new UEMessage(receiverID, encrypted);
    }
}
