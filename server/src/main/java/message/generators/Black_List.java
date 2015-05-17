package message.generators;

import com.google.inject.Inject;
import controller.utils.cypher.Encryption;
import message.types.*;
import messages.MessageId;
import rsa.exceptions.EncryptionException;

import java.util.Arrays;

/**
 * Created by tochur on 03.05.15.
 */
public class Black_List {
    private MessageId requestNick = MessageId.BLACK_LIST;
    private MessageId addToBlackList = MessageId.ADD_TO_BLACK_LIST;
    private MessageId removeFromBlackList = MessageId.REMOVE_FROM_BLACK_LIST;
    private EncryptedMessage encrypted;

    private Encryption encryption;

    @Inject
    public Black_List(Encryption encryption){
        this.encryption = encryption;
    }

    /**
     * Creates message with users from blackList
     * @param receiverID - ID of user that request for his blackList
     * @param nicks - nicks from blackList
     * @return EncryptedMessage - answer, prepared by the system.
     * @throws rsa.exceptions.EncryptionException - When encryption of the message failed
     */
    public UEMessage create(Integer receiverID, String[] nicks) throws EncryptionException {
        Header header = HeaderGenerator.createHeader(requestNick, 0, nicks.length);
        Message message = new Message(header, Arrays.asList(nicks));
        UMessage uMessage = new UMessage(receiverID, message);

        return encryption.encryptMessage(uMessage);
    }

    public UEMessage addedSuccessfully(Integer receiverID){
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(addToBlackList, 0));
        return new UEMessage(receiverID, encrypted);
    }

    public UEMessage userNotExistsCannotAdd(Integer receiverID){
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(addToBlackList, 1));
        return new UEMessage(receiverID, encrypted);
    }

    public UEMessage toMuchOnList(Integer receiverID){
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(addToBlackList, 2));
        return new UEMessage(receiverID, encrypted);
    }

    public UEMessage alreadyAdded(Integer receiverID){
        encrypted =  new EncryptedMessage(HeaderGenerator.createHeader(addToBlackList, 3));
        return new UEMessage(receiverID, encrypted);
    }

    public UEMessage removedSuccessfully(Integer receiverID) {
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(removeFromBlackList, 0));
        return new UEMessage(receiverID, encrypted);
    }

    public UEMessage notOnList(Integer receiverID) {
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(removeFromBlackList, 1));
        return new UEMessage(receiverID, encrypted);
    }

    //When user with this name do not exists.
    public UEMessage userNotExistsCannotRemove(Integer receiverID) {
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(removeFromBlackList, 2));
        return new UEMessage(receiverID, encrypted);
    }
}
