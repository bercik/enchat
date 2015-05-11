package message.types;

import messages.MessageId;

/**
 * Created by tochur on 30.04.15.
 */
public class Header {
    protected MessageId id;
    protected MessageId.ErrorId errorId;
    protected int packsAmount = 0;

    public Header(MessageId id, MessageId.ErrorId errorId, int dataAmount) {
        this(id, errorId);
        this.packsAmount = dataAmount;
    }

    public Header(MessageId id, MessageId.ErrorId errorId) {
        this.id = id;
        this.errorId = errorId;
    }

    public MessageId getId() {
        return id;
    }

    public MessageId.ErrorId getErrorId() {
        return errorId;
    }

    public int getPackageAmount() {
        return packsAmount;
    }
}
