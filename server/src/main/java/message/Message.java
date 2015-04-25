package message;

import messages.IncorrectMessageId;
import messages.MessageId;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tochur on 24.04.15.
 *
 * Represents the smallest unit passed between system elements.
 * It is prepared to reading (decrypted).
 * You can get data from Massage invoking getPackages().
 */
public class Message implements IMessage{
    protected MessageId id;
    protected int errorId;
    protected int dataAmount;
    protected List<String> data = new LinkedList<String>();

    public Message(int id, int errorId, int dataAmount, List<String> data) throws IncorrectMessageId {
        this.id = MessageId.createMessageId(id);
        this.errorId = errorId;
        this.dataAmount = dataAmount;
        this.data = data;
    }

    public Message(MessageId messageId, int errorId, int dataAmount, List<String> data){
        this.id = messageId;
        this.errorId = errorId;
        this.dataAmount = dataAmount;
        this.data = data;
    }

    public MessageId getId() {
        return id;
    }

    public int getErrorId() {
        return errorId;
    }

    public int getPackageAmount() {
        return dataAmount;
    }

    public List<String> getPackages() {
        return data;
    }
}
