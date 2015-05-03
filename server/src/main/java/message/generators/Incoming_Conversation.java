package message.generators;

import message.types.EncryptedMessage;
import messages.MessageId;

/**
 * Created by tochur on 02.05.15.
 */
public class Incoming_Conversation {
    private static MessageId messageId = MessageId.INCOMING_CONVERSATION;

    public static EncryptedMessage connected(){
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 0));
    }

    public static EncryptedMessage roomOverloaded(){
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 1));
    }
}
