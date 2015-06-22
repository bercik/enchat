package server.listeners.message;

import message.exceptions.MessageIdException;
import message.types.*;
import messages.IncorrectMessageId;
import messages.MessageId;

import java.util.List;

/**
 * @author Created by tochur on 24.04.15.
 *
 * Util for creating EncryptedMessages read from row Data.
 * This way of creating message is dangerous, and may cause MessageIdException.
 * This creator should be used only for creating messages received from stream.
 * If you need to create Message you should use prepares function from package
 *      message.generators.*;
 * Which provides safety way to create every message type (with correct Header)
 * that is allowed is system.
 */
public class MessageCreator {
    //ENCRYPTED//
    public static EncryptedMessage fromStream(int id, int errorId) throws MessageIdException {
        try{
            Header header = header(id, errorId, 0);
            return new EncryptedMessage(header);
        }catch (IncorrectMessageId e){
            throw new MessageIdException();
        }
    }

    public static EncryptedMessage fromStream(int id, int errorId, int amount, List<Pack> packs) throws MessageIdException {
        try{
            Header header = header(id, errorId, amount);
            return new EncryptedMessage(header, packs);
        }catch (IncorrectMessageId e){
            throw new MessageIdException();
        }
    }

    private static Header header(int id, int errorId, int amount) throws MessageIdException {
        try{
            MessageId messageId = MessageId.createMessageId(id);
            MessageId.ErrorId erId = messageId.createErrorId(errorId);
            return new Header(messageId, erId, amount);
        }catch (IncorrectMessageId e){
            throw new MessageIdException();
        }
    }
}

