package message.generators;

import message.types.EncryptedMessage;
import message.types.UEMessage;
import messages.MessageId;

/**
 * @author Created by tochur on 01.05.15.
 */
public class Sign_Up {
    public MessageId signUp = MessageId.SIGN_UP;

    public UEMessage ok(Integer receiverID) {
        EncryptedMessage encrypted = new EncryptedMessage(HeaderGenerator.createHeader(signUp, 0));
        return new UEMessage(receiverID, encrypted);
    }

    public UEMessage busyLogin(Integer receiverID) {
        EncryptedMessage encrypted =  new EncryptedMessage(HeaderGenerator.createHeader(signUp, 1));
        return new UEMessage(receiverID, encrypted);
    }

    public UEMessage incorrectLogin(Integer receiverID) {
        EncryptedMessage encrypted =  new EncryptedMessage(HeaderGenerator.createHeader(signUp, 2));
        return new UEMessage(receiverID, encrypted);
    }

    public UEMessage badPasswordLength(Integer receiverID) {
        EncryptedMessage encrypted = new EncryptedMessage(HeaderGenerator.createHeader(signUp, 3));
        return new UEMessage(receiverID, encrypted);
    }

    public UEMessage toManyAccounts(Integer receiverID) {
        EncryptedMessage encrypted = new EncryptedMessage(HeaderGenerator.createHeader(signUp, 4));
        return new UEMessage(receiverID, encrypted);
    }
}
