package message.generators;

import message.types.EncryptedMessage;
import message.types.UEMessage;
import messages.MessageId;

/**
 * Created by tochur on 01.05.15.
 *
 * Let user to safety creating messages.
 */
public class Junk {
    private MessageId junk = MessageId.JUNK;

    public UEMessage ok(Integer receiverID){
        EncryptedMessage encrypted= new EncryptedMessage(HeaderGenerator.createHeader(junk, 0));
        return new UEMessage(receiverID, encrypted);
    }
}
