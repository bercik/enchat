package message.utils;

import message.types.EncryptedMessage;
import message.types.Header;
import message.types.Message;
import message.types.Pack;
import messages.IncorrectMessageId;
import messages.MessageId;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tochur on 24.04.15.
 */
public class MessageCreator {
    /*Ma zostać usunięta*/
    public static message.types.Message createInfoMessage(String message){
        List<String> strings = new LinkedList<String>();
        strings.add(message);
        return new message.types.Message(MessageId.SERVER_INFO_MESSAGE, MessageId.ErrorId.OK , 1, strings);
    }

    /*Will be removed*/
    public static message.types.Message createHeaderMessage(MessageId messageId, int errorId){
        return  new message.types.Message(MessageId.SERVER_INFO_MESSAGE, MessageId.ErrorId.OK, 0, null);
    }

    public static Message unableToDeliver(String receiverName){
        try {
            MessageId.ErrorId errorId = MessageId.CLIENT_MESSAGE.createErrorId(1);
            return new Message(MessageId.CLIENT_MESSAGE, errorId, receiverName);
        } catch (IncorrectMessageId incorrectMessageId) {
            incorrectMessageId.printStackTrace();
        }
        return null;
    }

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
