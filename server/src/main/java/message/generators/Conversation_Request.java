package message.generators;

import message.types.EncryptedMessage;
import messages.MessageId;

/**
 * Created by tochur on 01.05.15.
 */
public class Conversation_Request {
    private MessageId conversationRequest = MessageId.CONVERSATION_REQUEST;

    public EncryptedMessage connected(){
        return new EncryptedMessage(HeaderGenerator.createHeader(conversationRequest, 0));
    }

    public EncryptedMessage notLogged(){
        return new EncryptedMessage(HeaderGenerator.createHeader(conversationRequest, 1));
    }

    public EncryptedMessage busyUser(){
        return new EncryptedMessage(HeaderGenerator.createHeader(conversationRequest, 2));
    }

    public EncryptedMessage onBlackList(){
        return new EncryptedMessage(HeaderGenerator.createHeader(conversationRequest, 4));
    }
}
