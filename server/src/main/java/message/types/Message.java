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

    public Message(Header header, List<String> info) {
        super(header);
        this.data = new LinkedList<>();
        for(String s: info){
            this.data.add(s);
        }
    }

    public Message(Header header, String nick) {
        super(header);
        this.data = new LinkedList<>();
        this.data.add(nick);
    }

    public Message(MessageId messageId, MessageId.ErrorId errorId, int dataAmount, List<String> info){
        super(messageId, errorId, dataAmount);
        this.data = new LinkedList<>();
        for(String s: info){
            this.data.add(s);
        }
    }

    /*Consider to make a deep copy*/
    public List<String> getPackages(){
        return data;
    }
}
