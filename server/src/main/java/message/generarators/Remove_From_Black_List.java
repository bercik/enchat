package message.generarators;

import message.types.EncryptedMessage;
import messages.MessageId;
import responders.exceptions.ReactionException;

/**
 * Created by tochur on 02.05.15.
 */
public class Remove_From_Black_List {
    public static MessageId messageId = MessageId.REMOVE_FROM_BLACK_LIST;

    public static EncryptedMessage removedSuccessfully() throws ReactionException {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 0));
    }

    public static EncryptedMessage notOnList() throws ReactionException {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 1));
    }

    public static EncryptedMessage notExists() throws ReactionException {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 2));
    }
}
