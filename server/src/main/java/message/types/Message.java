package message.types;

import messages.MessageId;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tochur on 30.04.15.
 */
public class Message extends AbstractMessage {
    List<String> data;

    public Message(MessageId id, MessageId.ErrorId errorId) {
        super(id, errorId, 0);
    }

    public Message(MessageId messageId, MessageId.ErrorId errorId, String info){
        super(messageId, errorId, 1);
        this.data = new LinkedList<String>();
        //Independent string
        this.data.add(new String(info));
    }

    public Message(MessageId messageId, MessageId.ErrorId errorId, int dataAmount, List<String> info){
        super(messageId, errorId, dataAmount);
        this.data = new LinkedList<String>();
        for(String s: info){
            this.data.add(new String(s));
        }
    }

    public Message(Header header) {
        super(header);
    }

    /*Consider to make a deep copy*/
    public List<String> getPackages(){
        return data;
    }
}
