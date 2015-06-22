package message.generators;

import message.types.EncryptedMessage;
import message.types.UEMessage;
import messages.MessageId;

/**
 * Creator of the messages.
 *
 * @author Created by tochur on 01.05.15.
 */
public class Log_In {
    private MessageId logIn = MessageId.LOG_IN;
    private EncryptedMessage encrypted;

    /**
     * Creates the message with MessageId LOG_IN and error OK - logged successfully.
     * @param receiverID Integer, receiver of the message.
     * @return UEMessage - message ready to send.
     */
    public UEMessage loggedSuccessfully(Integer receiverID){
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(logIn, 0));
        return new UEMessage(receiverID, encrypted);
    }

    /**
     * Creates the message with MessageId LOG_IN and ErrorId.BAD_LOGIN_OR_PASSWORD.
     * @param receiverID Integer, receiver of the message.
     * @return UEMessage - message ready to send.
     */
    public UEMessage badLoginOrPassword(Integer receiverID){
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(logIn, 1));
        return new UEMessage(receiverID, encrypted);
    }

    /**
     * Creates the message with MessageId LOG_IN and ErrorId.TOO_MUCH_USERS_LOGGED
     * @param receiverID Integer, receiver of the message.
     * @return UEMessage - message ready to send.
     */
    public UEMessage toMuchUserLogged(Integer receiverID){
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(logIn, 2));
        return new UEMessage(receiverID, encrypted);
    }

    /**
     * Creates the message with MessageId LOG_IN and ErrorId.ALREADY_LOGGED.
     * @param receiverID Integer, receiver of the message.
     * @return UEMessage - message ready to send.
     */
    public UEMessage alreadyLogged(Integer receiverID){
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(logIn, 3));
        return new UEMessage(receiverID, encrypted);
    }
}