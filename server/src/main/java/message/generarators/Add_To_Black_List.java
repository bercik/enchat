package message.generarators;

import message.types.EncryptedMessage;
import messages.MessageId;
import responders.exceptions.ReactionException;

/**
 * Created by tochur on 02.05.15.
 */
public class Add_To_Black_List {
    private static MessageId messageId = MessageId.ADD_TO_BLACK_LIST;

    public static EncryptedMessage addedSuccessfully() throws ReactionException {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 0));
    }

    public static EncryptedMessage userNotExists() throws ReactionException {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 1));
    }

    public static EncryptedMessage toMuchOnList() throws ReactionException {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 2));
    }

    public static EncryptedMessage alreadyAdded() throws ReactionException {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 3));
    }
}
