package message.generators;

import message.types.EncryptedMessage;
import messages.MessageId;

/**
 * Created by tochur on 01.05.15.
 */
public class Log_In {
    private MessageId logIn = MessageId.LOG_IN;

    public EncryptedMessage loggedSuccessfully(){
        return new EncryptedMessage(HeaderGenerator.createHeader(logIn, 0));
    }

    public EncryptedMessage badLoginOrPassword(){
        return new EncryptedMessage(HeaderGenerator.createHeader(logIn, 1));
    }

    public EncryptedMessage toMuchUserLogged(){
        return new EncryptedMessage(HeaderGenerator.createHeader(logIn, 2));
    }

    public EncryptedMessage alreadyLogged(){
        return new EncryptedMessage(HeaderGenerator.createHeader(logIn, 3));
    }
}
