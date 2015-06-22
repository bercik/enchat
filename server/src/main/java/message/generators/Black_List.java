package message.generators;

import com.google.inject.Inject;
import controller.utils.cypher.Encryption;
import message.types.*;
import messages.MessageId;
import rsa.exceptions.EncryptionException;

import java.util.Arrays;

/**
 * Creator of the messages.
 *
 * @author Created by tochur on 03.05.15.
 */
public class Black_List {
    private MessageId requestNick = MessageId.BLACK_LIST;
    private MessageId addToBlackList = MessageId.ADD_TO_BLACK_LIST;
    private MessageId removeFromBlackList = MessageId.REMOVE_FROM_BLACK_LIST;
    private EncryptedMessage encrypted;

    private Encryption encryption;

    /**
     * Creates the util user for message creation.
     * @param encryption Encryption - util for encryption the message.
     */
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

    /**
     * Creates the message with MessageId ADD_TO_BLACK_LIST and error OK - added successfully.
     * @param receiverID Integer, receiver of the message.
     * @return UEMessage - message ready to send.
     */
    public UEMessage addedSuccessfully(Integer receiverID){
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(addToBlackList, 0));
        return new UEMessage(receiverID, encrypted);
    }

    /**
     * Creates the message with MessageId ADD_TO_BLACK_LIST and error ErrorId.TOO_MUCH_USERS_ON_BLACKLIST.
     * @param receiverID Integer, receiver of the message.
     * @return UEMessage - message ready to send.
     */
    public UEMessage userNotExistsCannotAdd(Integer receiverID){
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(addToBlackList, 1));
        return new UEMessage(receiverID, encrypted);
    }

    /**
     * Creates the message with MessageId ADD_TO_BLACK_LIST and error ErrorId.TOO_MUCH_USERS_ON_BLACKLIST.
     * @param receiverID Integer, receiver of the message.
     * @return UEMessage - message ready to send.
     */
    public UEMessage toMuchOnList(Integer receiverID){
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(addToBlackList, 2));
        return new UEMessage(receiverID, encrypted);
    }

    /**
     * Creates the message with MessageId ADD_TO_BLACK_LIST and error ErrorId.ALREADY_ADDED.
     * @param receiverID Integer, receiver of the message.
     * @return UEMessage - message ready to send.
     */
    public UEMessage alreadyAdded(Integer receiverID){
        encrypted =  new EncryptedMessage(HeaderGenerator.createHeader(addToBlackList, 3));
        return new UEMessage(receiverID, encrypted);
    }

    /**
     * Creates the message with MessageId BLACK_LIST and error ok (0)
     * @param receiverID Integer, receiver of the message.
     * @return UEMessage - message ready to send.
     */
    public UEMessage removedSuccessfully(Integer receiverID) {
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(removeFromBlackList, 0));
        return new UEMessage(receiverID, encrypted);
    }

    /**
     * Creates the message with MessageId BLACK_LIST and error ErrorId.USER_NOT_ON_BLACKLIST.
     * @param receiverID Integer, receiver of the message.
     * @return UEMessage - message ready to send.
     */
    public UEMessage notOnList(Integer receiverID) {
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(removeFromBlackList, 1));
        return new UEMessage(receiverID, encrypted);
    }

    /**
     * Creates the message with MessageId BLACK_LIST and error ErrorId.USER_NOT_EXIST.
     * @param receiverID Integer, receiver of the message.
     * @return UEMessage - message ready to send.
     */
    public UEMessage userNotExistsCannotRemove(Integer receiverID) {
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(removeFromBlackList, 2));
        return new UEMessage(receiverID, encrypted);
    }
}