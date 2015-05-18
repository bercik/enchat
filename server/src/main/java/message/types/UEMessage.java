package message.types;


import messages.MessageId;

import java.util.List;

/**
 * Created by tochur on 15.05.15.
 */
public class UEMessage{
    private final Integer authorID;
    private EncryptedMessage encryptedMessage;

    public UEMessage(Integer authorID, EncryptedMessage encryptedMessage) {
        this.authorID = authorID;
        this.encryptedMessage = encryptedMessage;
    }

    public Integer getAuthorID() {
        return authorID;
    }

    public EncryptedMessage getEncryptedMessage() {
        return encryptedMessage;
    }

    public MessageId getId() {
        return encryptedMessage.header.getId();
    }

    public MessageId.ErrorId getErrorId() { return encryptedMessage.header.getErrorId(); }

    public int getPackageAmount() {
        return encryptedMessage.header.getPackageAmount();
    }

    public List<Pack> getPackages() { return encryptedMessage.getPackages(); }

}
