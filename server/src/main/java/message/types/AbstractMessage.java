package message.types;

import messages.MessageId;

/**
 * Represents the AbstractMessage, the base for EncryptedMessage ant Message.
 *
 * @author Created by tochur on 30.04.15.
 */
public class AbstractMessage implements IMessage {
    Header header;

    /**
     * Creates the message without data.
     * @param id MessageId, id of the message
     * @param errorId ErrorID, id of the error.
     * @param dataAmount Integer, amount of data to pack
     */
    public AbstractMessage(MessageId id, MessageId.ErrorId errorId, int dataAmount) {
        header = new Header(id, errorId, dataAmount);
    }

    /**
     * Creates message only with header.
     * @param header Header, information that identifies the message type and size.
     */
    public AbstractMessage(Header header){
        this.header = header;
    }

    /**
     * Returns the messageId.
     * @return MessageId, id of the message.
     */
    public MessageId getId() {
        return header.getId();
    }

    /**
     * Returns the Error id.
     * @return MessageId.ErrorId, message error id.
     */
    public MessageId.ErrorId getErrorId() { return header.getErrorId(); }

    /**
     * Returns the amount of packages.
     * @return amount of packages.
     */
    public int getPackageAmount() {
        return header.getPackageAmount();
    }
}
