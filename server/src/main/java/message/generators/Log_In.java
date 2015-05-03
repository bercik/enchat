package message.generators;

import message.types.EncryptedMessage;
import messages.MessageId;

/**
 * Created by tochur on 01.05.15.
 */
public class Log_In {
    private static MessageId messageId = MessageId.LOG_IN;

    public static EncryptedMessage loggedSuccessfully(){
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 0));
    }

    public static EncryptedMessage badLoginOrPassword(){
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 1));
    }

    public static EncryptedMessage toMuchUserLogged(){
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 2));
    }

    public static EncryptedMessage alreadyLogged(){
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 3));
    }
}
