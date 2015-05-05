package message.generators;

import message.types.EncryptedMessage;
import messages.MessageId;

/**
 * Created by tochur on 01.05.15.
 *
 * Let user to safety creating messages.
 */
public class Junk {
    private static MessageId messageId = MessageId.JUNK;

    public static EncryptedMessage ok(){
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 0));
    }
}
