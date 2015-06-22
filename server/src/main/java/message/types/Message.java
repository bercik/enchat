package message.types;

import messages.MessageId;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents the decrypted message in a system.
 * If packageAmount &lt; 0, data (List&lt;String&gt;) holds the byte[] converted to String.
 * Each string corresponds to info from successive package.
 *      new String( package0.getDataArray() ); &lt;=&gt;  data.get(0);
 *
 * If packageAmount = 0, EncryptedMessage &lt;=&gt; Message.
 * Message format is:
 *      id      - (int32)
 *      errorId  - (int32)
 *      packageAmount - (int32)
 *      data - (List&gt;Strings&gt;);
 *
 * @author Created by tochur on 30.04.15.
 */
public class Message extends AbstractMessage {
    //Data hold in message
    private List<String> data;

    /**
     * Creates message with many data
     * @param header Header, information that identifies the message type and size.
     * @param info List &lt;String &gt; data that will be inside the message.
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
     * @param header Header, information that identifies the message type and size.
     * @param info String, info that will be inside the message.
     */
    public Message(Header header, String info) {
        super(header);
        this.data = new LinkedList<>();
        this.data.add(info);
    }

    /**
     * Creates message with many data
     * @param messageId MessageId, id of the message
     * @param errorId ErrorID, id of the error.
     * @param dataAmount Integer, amount of data to pack
     * @param info List &lt;String &gt; data that will be passed in the message.
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
     * @param id MessageID, id of the message.
     * @param errorId ErrorID, id of the error.
     */
    public Message(MessageId id, MessageId.ErrorId errorId) {
        super(id, errorId, 0);
    }

    /**
     * Access data wrapped by message.
     * @return returns List&lt;String&gt; with data
     */
    public List<String> getPackages(){
        return data;
    }
}
