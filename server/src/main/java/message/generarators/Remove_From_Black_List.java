package message.generarators;

import message.types.EncryptedMessage;
import message.types.Header;
import messages.MessageId;

/**
 * Created by tochur on 02.05.15.
 */
public class Remove_From_Black_List {
    public static EncryptedMessage removedSuccessfully() {
        return new EncryptedMessage(createHeader(0));
    }

    public static EncryptedMessage notOnList() {
        return new EncryptedMessage(createHeader(1));
    }

    public static EncryptedMessage notExists() {
        return new EncryptedMessage(createHeader(2));
    }

    /**
     * Create Header with specified errorId
     * @param errorId - messageErrorToCreate
     * @return - Header with MessageId.REMOVE_FROM_BLACK_LIST
     */
    private static Header createHeader(int errorId){
        MessageId id = MessageId.REMOVE_FROM_BLACK_LIST;
        MessageId.ErrorId error = id.createErrorId(errorId);

        return new Header(id, error);
    }
}
