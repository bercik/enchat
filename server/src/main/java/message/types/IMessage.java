package message.types;

import messages.MessageId;

/**
 * Interface for all messages.
 *
 * @author Created by tochur on 24.04.15.
 */
public interface IMessage {
    /**
     * Returns the MessageId
     * @return messageId
     */
    public MessageId getId();

    /**
     * Returns the MessageErrorId
     * @return errorId.ErrorID, id of the error.
     */
    public MessageId.ErrorId getErrorId();

    /**
     * Returns the amount of package in the message.
     * @return Integer, amount of package in the message.
     */
    public int getPackageAmount();
}
