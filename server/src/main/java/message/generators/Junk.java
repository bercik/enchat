package message.generators;

import message.types.EncryptedMessage;
import message.types.UEMessage;
import messages.MessageId;

/**
 * Creator of the messages.
 *
 * @author Created by tochur on 01.05.15.
 *
 * Let user to safety creating messages.
 */
public class Junk {
    private MessageId junk = MessageId.JUNK;

    /**
     * Creates the message with MessageId JUNK and errorState OK
     * @param receiverID Integer, receiver of the message.
     * @return UEMessage - message ready to send.
     */
    public UEMessage ok(Integer receiverID){
        EncryptedMessage encrypted= new EncryptedMessage(HeaderGenerator.createHeader(junk, 0));
        return new UEMessage(receiverID, encrypted);
    }
}
