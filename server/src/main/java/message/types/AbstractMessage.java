package message.types;

import messages.MessageId;

/**
 * Created by tochur on 30.04.15.
 */
public class AbstractMessage implements IMessage {
    Header header;

    public AbstractMessage(MessageId id, MessageId.ErrorId errorId, int dataAmount) {
        header = new Header(id, errorId, dataAmount);
    }

    public AbstractMessage(Header header){
        this.header = header;
    }

    public MessageId getId() {
        return header.getId();
    }

    public MessageId.ErrorId getErrorId() { return header.getErrorId(); }

    public int getPackageAmount() {
        return header.getPackageAmount();
    }
}
