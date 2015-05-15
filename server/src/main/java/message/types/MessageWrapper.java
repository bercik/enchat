package message.types;

/**
 * Created by tochur on 15.05.15.
 */
public class MessageWrapper {
    private final Integer authorID;
    private Message message;

    public MessageWrapper(Integer authorID, Message message) {
        this.authorID = authorID;
        this.message = message;
    }

    public Integer getAuthorID() {
        return authorID;
    }

    public Message getEncryptedMessage() {
        return message;
    }
}
