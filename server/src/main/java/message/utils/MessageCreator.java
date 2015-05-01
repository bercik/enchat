package message.utils;

import message.types.EncryptedMessage;
import message.types.Header;
import message.types.Pack;
import messages.IncorrectMessageId;
import messages.MessageId;

import java.util.List;

/**
 * Created by tochur on 24.04.15.
 *
 * Class for creating EncryptedMessages read from stream.
 */
public class MessageCreator {
    //ENCRYPTED//
    public static EncryptedMessage fromStream(int id, int errorId) throws IncorrectMessageId {
        Header header = header(id, errorId, 0);
        return new EncryptedMessage(header);
    }

    public static EncryptedMessage fromStream(int id, int errorId, int amount, List<Pack> packs) throws IncorrectMessageId {
        Header header = header(id, errorId, amount);

        return new EncryptedMessage(header, packs);
    }

    private static Header header(int id, int errorId, int amount) throws IncorrectMessageId {
        MessageId messageId = MessageId.createMessageId(id);
        MessageId.ErrorId erId = messageId.createErrorId(errorId);

        return new Header(messageId, erId, 0);
    }
}

