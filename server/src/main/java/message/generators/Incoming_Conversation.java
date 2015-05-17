package message.generators;

import message.types.EncryptedMessage;
import message.types.UEMessage;
import messages.MessageId;

/**
 * Created by tochur on 02.05.15.
 */
public class Incoming_Conversation {
    private MessageId incomingConversation = MessageId.INCOMING_CONVERSATION;
    private EncryptedMessage encrypted;

    public UEMessage connected(Integer receiverID){
        encrypted =  new EncryptedMessage(HeaderGenerator.createHeader(incomingConversation, 0));
        return new UEMessage(receiverID, encrypted);
    }

    public UEMessage roomOverloaded(Integer receiverID){
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(incomingConversation, 1));
        return new UEMessage(receiverID, encrypted);
    }
}
