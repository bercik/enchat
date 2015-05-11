package message.generators;

import message.types.EncryptedMessage;
import messages.MessageId;

/**
 * Created by tochur on 02.05.15.
 */
public class Incoming_Conversation {
    private MessageId incomingConversation = MessageId.INCOMING_CONVERSATION;

    public EncryptedMessage connected(){
        return new EncryptedMessage(HeaderGenerator.createHeader(incomingConversation, 0));
    }

    public EncryptedMessage roomOverloaded(){
        return new EncryptedMessage(HeaderGenerator.createHeader(incomingConversation, 1));
    }
}
