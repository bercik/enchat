package message.types;

import messages.MessageId;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Created by tochur on 30.04.15.
 *
 * Represents the decrypted message in a system.
 * If packageAmount > 0, data (List<String>) holds the byte[] converted to String.
 * Each string corresponds to info from successive package.
 *      new String( package0.getDataArray() ); <=>  data.get(0);
 *
 * If packageAmount = 0, EncryptedMessage <=> Message.
 * Message format is:
 *      id      - (int32)
 *      errorId  - (int32)
 *      packageAmount - (int32)
 *      data - (List<Strings>);
 *
 */
public class Message extends AbstractMessage {
    //Data hold in message
    private List<String> data;

    /**
     * Creates message with many data
     * @param header
     * @param info
     */
    public Message(Header header, List<String> info) {
        super(header);
        this.data = new LinkedList<>();
        for(String s: info){
            this.data.add(s);
        }
    }

    /**
     * Creates message with only one string
     * @param header
     * @param info
     */
    public Message(Header header, String info) {
        super(header);
        this.data = new LinkedList<>();
        this.data.add(info);
    }

    /**
     * Creates message with many data
     * @param messageId
     * @param errorId
     * @param dataAmount
     * @param info
     */
    public Message(MessageId messageId, MessageId.ErrorId errorId, int dataAmount, List<String> info){
        super(messageId, errorId, dataAmount);
        this.data = new LinkedList<>();
        for(String s: info){
            this.data.add(s);
        }
    }

    /**
     * Constructs message without data
     * @param id -
     * @param errorId
     */
    public Message(MessageId id, MessageId.ErrorId errorId) {
        super(id, errorId, 0);
    }

    /**
     * Access data wrapped by message.
     * @return returns List<String> with data
     */
    public List<String> getPackages(){
        return data;
    }
}
