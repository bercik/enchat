package message.generarators;

import message.types.EncryptedMessage;
import message.types.Header;
import messages.IncorrectMessageId;
import messages.MessageId;

/**
 * Created by tochur on 02.05.15.
 */
public class Add_To_Black_List {
    public static EncryptedMessage addedSuccessfully() {
        MessageId id = MessageId.ADD_TO_BLACK_LIST;
        Header header = null;
        try {
            MessageId.ErrorId errorId = id.createErrorId(0);
            header = new Header(id, errorId);
        } catch (IncorrectMessageId incorrectMessageId) {
            System.out.println("Error inside Messages, wrong error number, repair that.");
        }

        return new EncryptedMessage(header);
    }

    public static EncryptedMessage userNotExists() {
        MessageId id = MessageId.ADD_TO_BLACK_LIST;
        Header header = null;
        try {
            MessageId.ErrorId errorId = id.createErrorId(1);
            header = new Header(id, errorId);
        } catch (IncorrectMessageId incorrectMessageId) {
            System.out.println("Error inside Messages, wrong error number, repair that.");
        }

        return new EncryptedMessage(header);
    }

    public static EncryptedMessage toMuchOnList() {
        MessageId id = MessageId.ADD_TO_BLACK_LIST;
        Header header = null;
        try {
            MessageId.ErrorId errorId = id.createErrorId(2);
            header = new Header(id, errorId);
        } catch (IncorrectMessageId incorrectMessageId) {
            System.out.println("Error inside Messages, wrong error number, repair that.");
        }

        return new EncryptedMessage(header);
    }

    public static EncryptedMessage alreadyAdded() {
        MessageId id = MessageId.ADD_TO_BLACK_LIST;
        Header header = null;
        try {
            MessageId.ErrorId errorId = id.createErrorId(3);
            header = new Header(id, errorId);
        } catch (IncorrectMessageId incorrectMessageId) {
            System.out.println("Error inside Messages, wrong error number, repair that.");
        }

        return new EncryptedMessage(header);
    }
}
