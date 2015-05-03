package message.generators;

import message.types.EncryptedMessage;
import messages.MessageId;

/**
 * Created by tochur on 01.05.15.
 */
public class Sign_Up {
    public static MessageId messageId = MessageId.SIGN_UP;

    public static EncryptedMessage ok() {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 0));
    }

    public static EncryptedMessage busyLogin() {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 1));
    }

    public static EncryptedMessage incorrectLogin() {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 2));
    }

    public static EncryptedMessage badPasswordLength() {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 3));
    }

    public static EncryptedMessage toManyAccounts() {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 4));
    }
}
