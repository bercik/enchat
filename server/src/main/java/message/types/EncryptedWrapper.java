package message.types;


/**
 * Created by tochur on 15.05.15.
 */
public class EncryptedWrapper {
    private final Integer authorID;
    private EncryptedMessage encryptedMessage;

    public EncryptedWrapper(Integer authorID, EncryptedMessage encryptedMessage) {
        this.authorID = authorID;
        this.encryptedMessage = encryptedMessage;
    }

    public Integer getAuthorID() {
        return authorID;
    }

    public EncryptedMessage getEncryptedMessage() {
        return encryptedMessage;
    }
}
