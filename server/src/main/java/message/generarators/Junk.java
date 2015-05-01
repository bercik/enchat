package message.generarators;

import message.types.EncryptedMessage;
import message.types.Header;
import messages.IncorrectMessageId;
import messages.MessageId;

/**
 * Created by tochur on 01.05.15.
 *
 * Let user to safety creating messages.
 */
public class Junk {
    public static EncryptedMessage ok(){
        MessageId id = MessageId.JUNK;
        Header header = null;
        try {
            header = new Header(id, id.createErrorId(0), 0);
        } catch (IncorrectMessageId incorrectMessageId) {
            System.out.println("Error inside Messages, wrong error number, repair that.");
        }
        return new EncryptedMessage(header);
    }
}
