package message.types;

import messages.MessageId;

/**
 * Represents the header of the message.
 *
 * @author Created by tochur on 30.04.15.
 */
public class Header {
    protected MessageId id;
    protected MessageId.ErrorId errorId;
    protected int packsAmount = 0;

    /**
     * Creates message with many data
     * @param id MessageId, id of the message
     * @param errorId ErrorID, id of the error.
     * @param dataAmount Integer, amount of data to pack
     */
    public Header(MessageId id, MessageId.ErrorId errorId, int dataAmount) {
        this(id, errorId);
        this.packsAmount = dataAmount;
    }

    /**
     * Creates message with many data
     * @param id MessageId, id of the message
     * @param errorId ErrorID, id of the error.
     */
    public Header(MessageId id, MessageId.ErrorId errorId) {
        this.id = id;
        this.errorId = errorId;
    }

    /**
     * Returns the messageId
     * @return MessageId, id of the message.
     */
    public MessageId getId() {
        return id;
    }

    /**
     * Returns the MessageErrorId
     * @return MessageId.ErrorId, message error type.
     */
    public MessageId.ErrorId getErrorId() {
        return errorId;
    }

    /**
     * Returns the amount of package in the message
     * @return amount of package in the message.
     */
    public int getPackageAmount() {
        return packsAmount;
    }
}
