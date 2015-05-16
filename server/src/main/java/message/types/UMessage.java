package message.types;

import messages.MessageId;

import java.util.List;

/**
 * Created by tochur on 15.05.15.
 */
public class UMessage {
    private final Integer authorID;
    private Message message;

    public UMessage(Integer authorID, Message message) {
        this.authorID = authorID;
        this.message = message;
    }

    public Integer getAuthorID() {
        return authorID;
    }

    public Message getMessage() {
        return message;
    }

    public MessageId getId() {
        return message.header.getId();
    }

    public MessageId.ErrorId getErrorId() { return message.header.getErrorId(); }

    public int getPackageAmount() {
        return message.header.getPackageAmount();
    }

    public List<String> getPackages() { return message.getPackages(); }
}
