package message.generators;

import message.types.EncryptedMessage;
import messages.MessageId;

/**
 * Created by tochur on 01.05.15.
 */
public class Conversation_Request {
    private static MessageId messageId = MessageId.CONVERSATION_REQUEST;

    public static EncryptedMessage connected(){
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 0));
    }

    public static EncryptedMessage notLogged(){
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 1));
    }

    public static EncryptedMessage busyUser(){
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 2));
    }

    public static EncryptedMessage onBlackList(){
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 4));
    }
}
