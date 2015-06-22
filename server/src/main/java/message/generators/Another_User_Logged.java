package message.generators;

import message.types.EncryptedMessage;
import message.types.UEMessage;
import messages.MessageId;

/**
 * Creator of the messages.
 *
 * @author Created by tochur on 18.05.15.
 */
public class Another_User_Logged {
    private MessageId logOut = MessageId.ANOTHER_USER_LOGGER;
    private EncryptedMessage encrypted;

    /**
     * Creates the message with MessageId SIGN_UP and errorState OK
     * @param receiverID Integer, receiver of the message.
     * @return UEMessage - message ready to send.
     */
    public UEMessage ok(Integer receiverID){
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(logOut, 0));
        return new UEMessage(receiverID, encrypted);
    }
}
