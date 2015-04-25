package message;

import messages.IncorrectMessageId;
import messages.MessageId;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tochur on 24.04.15.
 */
public class MessageCreator {
    public static Message createInfoMessage(MessageId messageId, int errorId, String message){
        List<String> strings = new LinkedList<String>();
        strings.add(message);
        return new Message(messageId, errorId, 1, strings);
    }

    public static Message createHeaderMessage(MessageId messageId, int errorId){
        return  new Message(messageId, errorId, 0, null);
    }
}
