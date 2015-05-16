package message.generators;

import message.types.EncryptedMessage;
import message.types.UEMessage;
import messages.MessageId;

/**
 * Created by tochur on 01.05.15.
 */
public class Log_In {
    private MessageId logIn = MessageId.LOG_IN;
    private EncryptedMessage encrypted;

    public UEMessage loggedSuccessfully(Integer receiverID){
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(logIn, 0));
        return new UEMessage(receiverID, encrypted);
    }

    public UEMessage badLoginOrPassword(Integer receiverID){
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(logIn, 1));
        return new UEMessage(receiverID, encrypted);
    }

    public UEMessage toMuchUserLogged(Integer receiverID){
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(logIn, 2));
        return new UEMessage(receiverID, encrypted);
    }

    public UEMessage alreadyLogged(Integer receiverID){
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(logIn, 3));
        return new UEMessage(receiverID, encrypted);
    }
}
