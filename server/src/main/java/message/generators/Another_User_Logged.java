package message.generators;

import message.types.EncryptedMessage;
import message.types.UEMessage;
import messages.MessageId;

/**
 * Created by tochur on 18.05.15.
 */
public class Another_User_Logged {
    private MessageId logOut = MessageId.ANOTHER_USER_LOGGER;
    private EncryptedMessage encrypted;

    public UEMessage ok(Integer receiverID){
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(logOut, 0));
        return new UEMessage(receiverID, encrypted);
    }
}
