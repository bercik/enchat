package message.generators;

import message3.types.EncryptedMessage;
import messages.MessageId;

/**
 * Created by tochur on 01.05.15.
 *
 * Let user to safety creating messages.
 */
public class Junk {
    private MessageId junk = MessageId.JUNK;

    public EncryptedMessage ok(){
        return new EncryptedMessage(HeaderGenerator.createHeader(junk, 0));
    }
}
