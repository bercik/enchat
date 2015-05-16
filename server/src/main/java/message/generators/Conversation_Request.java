package message.generators;

import message.types.EncryptedMessage;
import message.types.UEMessage;
import messages.MessageId;

/**
 * Created by tochur on 01.05.15.
 */
public class Conversation_Request {
    private MessageId conversationRequest = MessageId.CONVERSATION_REQUEST;
    private EncryptedMessage encrypted;

    public UEMessage connected(Integer receiverID){
        encrypted =  new EncryptedMessage(HeaderGenerator.createHeader(conversationRequest, 0));
        return new UEMessage(receiverID, encrypted);
    }

    public UEMessage notLogged(Integer receiverID){
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(conversationRequest, 1));
        return new UEMessage(receiverID, encrypted);
    }

    public UEMessage busyUser(Integer receiverID){
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(conversationRequest, 2));
        return new UEMessage(receiverID, encrypted);
    }

    public UEMessage onBlackList(Integer receiverID){
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(conversationRequest, 4));
        return new UEMessage(receiverID, encrypted);
    }
}
