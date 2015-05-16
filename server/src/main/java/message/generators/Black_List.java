package message.generators;

import controller.utils.cypher.Encryption;
import message.utils.Encryption;
import message3.types.EncryptedMessage;
import message3.types.Header;
import message3.types.Message;
import messages.MessageId;
import rsa.exceptions.EncryptionException;
import user.User;

import java.util.Arrays;

/**
 * Created by tochur on 03.05.15.
 */
public class Black_List {
    private MessageId requestNick = MessageId.BLACK_LIST;
    private MessageId addToBlackList = MessageId.ADD_TO_BLACK_LIST;
    public MessageId removeFromBlackList = MessageId.REMOVE_FROM_BLACK_LIST;

    /**
     * Creates message with users from blackList
     * @param receiver - the ActiveUser that request for his blackList
     * @param nicks - nicks from blackList
     * @return EncryptedMessage - answer, prepared by the system.
     * @throws rsa.exceptions.EncryptionException - When encryption of the message failed
     */
    public EncryptedMessage create(User receiver, String[] nicks) throws EncryptionException {
        Header header = HeaderGenerator.createHeader(requestNick, 0, nicks.length);
        Message message = new Message(header, Arrays.asList(nicks));

        return Encryption.encryptMessage(receiver, message);
    }

    public EncryptedMessage addedSuccessfully(){
        return new EncryptedMessage(HeaderGenerator.createHeader(addToBlackList, 0));
    }

    public EncryptedMessage userNotExistsCannotAdd(){
        return new EncryptedMessage(HeaderGenerator.createHeader(addToBlackList, 1));
    }

    public EncryptedMessage toMuchOnList(){
        return new EncryptedMessage(HeaderGenerator.createHeader(addToBlackList, 2));
    }

    public EncryptedMessage alreadyAdded(){
        return new EncryptedMessage(HeaderGenerator.createHeader(addToBlackList, 3));
    }

    public EncryptedMessage removedSuccessfully() {
        return new EncryptedMessage(HeaderGenerator.createHeader(removeFromBlackList, 0));
    }

    public EncryptedMessage notOnList() {
        return new EncryptedMessage(HeaderGenerator.createHeader(removeFromBlackList, 1));
    }

    //When user with this name do not exists.
    public EncryptedMessage userNotExistsCannotRemove() {
        return new EncryptedMessage(HeaderGenerator.createHeader(removeFromBlackList, 2));
    }
}
