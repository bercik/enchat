package message.generators;

import message.types.EncryptedMessage;
import message.types.UEMessage;
import messages.MessageId;

/**
 * Creator of the messages.
 *
 * @author Created by tochur on 01.05.15.
 */
public class Sign_Up {
    /**
     * MessageId
     */
    public final MessageId signUp = MessageId.SIGN_UP;

    /**
     * Creates the message with MessageId SIGN_UP and errorState OK
     * @param receiverID Integer, receiver of the message.
     * @return UEMessage - message ready to send.
     */
    public UEMessage ok(Integer receiverID) {
        EncryptedMessage encrypted = new EncryptedMessage(HeaderGenerator.createHeader(signUp, 0));
        return new UEMessage(receiverID, encrypted);
    }

    /**
     * Creates the message with MessageId SIGN_UP and errorState ErrorId.BUSY_LOGIN
     * @param receiverID Integer, receiver of the message.
     * @return UEMessage - message ready to send.
     */
    public UEMessage busyLogin(Integer receiverID) {
        EncryptedMessage encrypted =  new EncryptedMessage(HeaderGenerator.createHeader(signUp, 1));
        return new UEMessage(receiverID, encrypted);
    }

    /**
     * Creates the message with MessageId SIGN_UP and ErrorId.INCORRECT_LOGIN.
     * @param receiverID Integer, receiver of the message.
     * @return UEMessage - message ready to send.
     */
    public UEMessage incorrectLogin(Integer receiverID) {
        EncryptedMessage encrypted =  new EncryptedMessage(HeaderGenerator.createHeader(signUp, 2));
        return new UEMessage(receiverID, encrypted);
    }

    /**
     * Creates the message with MessageId SIGN_UP and ErrorId.BAD_PASSWORD_LENGTH
     * @param receiverID Integer, receiver of the message.
     * @return UEMessage - message ready to send.
     */
    public UEMessage badPasswordLength(Integer receiverID) {
        EncryptedMessage encrypted = new EncryptedMessage(HeaderGenerator.createHeader(signUp, 3));
        return new UEMessage(receiverID, encrypted);
    }

    /**
     * Creates the message with MessageId SIGN_UP and ErrorId.TO_MUCH_REGISTERED.
     * @param receiverID Integer, receiver of the message.
     * @return UEMessage - message ready to send.
     */
    public UEMessage toManyAccounts(Integer receiverID) {
        EncryptedMessage encrypted = new EncryptedMessage(HeaderGenerator.createHeader(signUp, 4));
        return new UEMessage(receiverID, encrypted);
    }
}