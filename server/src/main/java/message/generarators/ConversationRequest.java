package message.generarators;

import message.types.EncryptedMessage;
import message.types.Header;
import messages.IncorrectMessageId;
import messages.MessageId;

/**
 * Created by tochur on 01.05.15.
 */
public class ConversationRequest {
    public static EncryptedMessage connected(){
        MessageId id = MessageId.CONVERSATION_REQUEST;
        Header header = null;
        try {
            header = new Header(id, id.createErrorId(0));
        } catch (IncorrectMessageId incorrectMessageId) {
            System.out.println("Error inside Messages, wrong error number, repair that.");
        }
        return new EncryptedMessage(header);
    }

    public static EncryptedMessage notLogged(){
        MessageId id = MessageId.CONVERSATION_REQUEST;
        Header header = null;
        try {
            header = new Header(id, id.createErrorId(1));
        } catch (IncorrectMessageId incorrectMessageId) {
            System.out.println("Error inside Messages, wrong error number, repair that.");
        }
        return new EncryptedMessage(header);
    }

    public static EncryptedMessage busyUser(){
        MessageId id = MessageId.CONVERSATION_REQUEST;
        Header header = null;
        try {
            header = new Header(id, id.createErrorId(2));
        } catch (IncorrectMessageId incorrectMessageId) {
            System.out.println("Error inside Messages, wrong error number, repair that.");
        }
        return new EncryptedMessage(header);
    }


    public static EncryptedMessage onBlackList(){
        MessageId id = MessageId.CONVERSATION_REQUEST;
        Header header = null;
        try {
            header = new Header(id, id.createErrorId(4));
        } catch (IncorrectMessageId incorrectMessageId) {
            System.out.println("Error inside Messages, wrong error number, repair that.");
        }
        return new EncryptedMessage(header);
    }
}
