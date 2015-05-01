package message.generarators;

import message.types.EncryptedMessage;
import message.types.Header;
import messages.IncorrectMessageId;
import messages.MessageId;

/**
 * Created by tochur on 01.05.15.
 */
public class Sign_Up {
    public static EncryptedMessage ok(){
        MessageId id = MessageId.SIGN_UP;
        Header header = null;
        try {
            header = new Header(id, id.createErrorId(0), 0);
        } catch (IncorrectMessageId incorrectMessageId) {
            System.out.println("Error inside Messages, wrong error number, repair that.");
        }
        return new EncryptedMessage(header);
    }

    public static EncryptedMessage busyLogin(){
        MessageId id = MessageId.LOG_IN;
        Header header = null;
        try {
            header = new Header(id, id.createErrorId(0), 1);
        } catch (IncorrectMessageId incorrectMessageId) {
            System.out.println("Error inside Messages, wrong error number, repair that.");
        }
        return new EncryptedMessage(header);
    }

    public static EncryptedMessage incorrectLogin(){
        MessageId id = MessageId.LOG_IN;
        Header header = null;
        try {
            header = new Header(id, id.createErrorId(0), 2);
        } catch (IncorrectMessageId incorrectMessageId) {
            System.out.println("Error inside Messages, wrong error number, repair that.");
        }
        return new EncryptedMessage(header);
    }

    public static EncryptedMessage badPasswordLength(){
        MessageId id = MessageId.LOG_IN;
        Header header = null;
        try {
            header = new Header(id, id.createErrorId(0), 3);
        } catch (IncorrectMessageId incorrectMessageId) {
            System.out.println("Error inside Messages, wrong error number, repair that.");
        }
        return new EncryptedMessage(header);
    }
}
