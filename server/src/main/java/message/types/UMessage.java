package message.types;

import messages.MessageId;

import java.util.List;

/**
 * This object wraps the row message.
 * Holds unencrypted data. This message may be passed inside the server,
 * It is followed by user id, that identifies the author or receiver of the message.
 *
 * @author Created by tochur on 15.05.15.
 */
public class UMessage {
    private final Integer authorID;
    private Message message;

    /**
     * Creates new UserMessage
     * @param authorID Integer, identifier of the user.
     * @param message Message base message.
     */
    public UMessage(Integer authorID, Message message) {
        this.authorID = authorID;
        this.message = message;
    }

    /**
     * Returns the Id of the message author or receiver.
     * @return Integer, Id of the message author or receiver.
     */
    public Integer getAuthorID() {
        return authorID;
    }

    /**
     * Returns an Message - without user identifier.
     * @return Message, raw message (just Header and List of Stings).
     */
    public Message getMessage() {
        return message;
    }

    /**
     * Returns the Id of the message.
     * @return MessageId, id of the message.
     */
    public MessageId getId() {
        return message.header.getId();
    }

    /**
     * Returns the message ErrorId
     * @return MessageId.ErrorId, message error id.
     */
    public MessageId.ErrorId getErrorId() { return message.header.getErrorId(); }

    /**
     * Returns the amount of packs inside the message.
     * @return Integer, amount of packs inside the message.
     */
    public int getPackageAmount() {
        return message.header.getPackageAmount();
    }

    /**
     * Returns the List of packages that are hold in message.
     * @return List &lt;Pack &gt;, data in message (encrypted and signed).
     */
    public List<String> getPackages() { return message.getPackages(); }
}
