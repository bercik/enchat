package message.generators;

import message.types.EncryptedMessage;
import messages.MessageId;

/**
 * Created by tochur on 01.05.15.
 */
public class Sign_Up {
    public MessageId signUp = MessageId.SIGN_UP;

    public EncryptedMessage ok() {
        return new EncryptedMessage(HeaderGenerator.createHeader(signUp, 0));
    }

    public EncryptedMessage busyLogin() {
        return new EncryptedMessage(HeaderGenerator.createHeader(signUp, 1));
    }

    public EncryptedMessage incorrectLogin() {
        return new EncryptedMessage(HeaderGenerator.createHeader(signUp, 2));
    }

    public EncryptedMessage badPasswordLength() {
        return new EncryptedMessage(HeaderGenerator.createHeader(signUp, 3));
    }

    public EncryptedMessage toManyAccounts() {
        return new EncryptedMessage(HeaderGenerator.createHeader(signUp, 4));
    }
}
