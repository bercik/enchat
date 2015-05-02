package message.generarators;

import message.types.EncryptedMessage;
import message.types.Header;
import messages.IncorrectMessageId;
import messages.MessageId;

/**
 * Created by tochur on 02.05.15.
 */
public class Incoming_Conversation {
    public static EncryptedMessage connected(){
        MessageId id = MessageId.INCOMING_CONVERSATION;
        Header header = null;
        try {
            header = new Header(id, id.createErrorId(0));
        } catch (IncorrectMessageId incorrectMessageId) {
            System.out.println("Error inside Messages, wrong error number, repair that.");
        }
        return new EncryptedMessage(header);
    }

    public static EncryptedMessage roomOverloaded(){
        MessageId id = MessageId.INCOMING_CONVERSATION;
        Header header = null;
        try {
            header = new Header(id, id.createErrorId(1));
        } catch (IncorrectMessageId incorrectMessageId) {
            System.out.println("Error inside Messages, wrong error number, repair that.");
        }
        return new EncryptedMessage(header);
    }
}
