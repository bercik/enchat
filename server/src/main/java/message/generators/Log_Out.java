package message.generators;

import message.types.EncryptedMessage;
import message.types.UEMessage;
import messages.MessageId;

/**
 * Created by tochur on 18.05.15.
 */
public class Log_Out {
    private MessageId logOut = MessageId.LOGOUT;
    private EncryptedMessage encrypted;

    public UEMessage logoutSuccessful(Integer receiverID){
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(logOut, 0));
        return new UEMessage(receiverID, encrypted);
    }
}
