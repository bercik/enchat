package message.types;


import messages.MessageId;

import java.util.List;

/**
 * Represents an encryptedMessage inside server.
 * It is followed by user id - the author or receiver of the message.
 *
 * @author Created by tochur on 15.05.15.
 */
public class UEMessage{
    private final Integer authorID;
    private EncryptedMessage encryptedMessage;

    /**
     * Creates the UEMessage.
     * @param authorID Integer, the message author or receiver.
     * @param encryptedMessage EncryptedMessage, raw encrypted message.
     */
    public UEMessage(Integer authorID, EncryptedMessage encryptedMessage) {
        this.authorID = authorID;
        this.encryptedMessage = encryptedMessage;
    }

    /**
     * Returns the Id of the message author or receiver.
     * @return Integer, Id of the message author or receiver.
     */
    public Integer getAuthorID() {
        return authorID;
    }

    /**
     * Returns an encryptedMessage - without user identifier.
     * @return EncryptedMessage, raw encrypted message.
     */
    public EncryptedMessage getEncryptedMessage() {
        return encryptedMessage;
    }

    /**
     * Returns the Id of the message.
     * @return MessageId, id of the message.
     */
    public MessageId getId() {
        return encryptedMessage.header.getId();
    }

    /**
     * Returns the message ErrorId
     * @return MessageId.ErrorId, message error id.
     */
    public MessageId.ErrorId getErrorId() { return encryptedMessage.header.getErrorId(); }

    /**
     * Returns the amount of packs inside the message.
     * @return Integer, amount of packs inside the message.
     */
    public int getPackageAmount() {
        return encryptedMessage.header.getPackageAmount();
    }

    /**
     * Returns the List of packages that are hold in message.
     * @return List &lt;Pack &gt;, data in message (encrypted and signed).
     */
    public List<Pack> getPackages() { return encryptedMessage.getPackages(); }

}
