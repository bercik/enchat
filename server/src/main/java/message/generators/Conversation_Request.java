package message.generators;

import com.google.inject.Inject;
import controller.utils.cypher.Encryption;
import message.types.*;
import messages.MessageId;
import model.exceptions.ElementNotFoundException;
import rsa.exceptions.EncryptionException;

import java.util.Arrays;

/**
 * Creator of the messages.
 *
 * @author Created by tochur on 01.05.15.
 */
public class Conversation_Request {
    private final MessageId conversationRequest = MessageId.CONVERSATION_REQUEST;
    private EncryptedMessage encrypted;
    private Encryption encryption;
    private KeyPackageSupplier keyPackageSupplier;

    @Inject
    public Conversation_Request(Encryption encryption, KeyPackageSupplier keyPackageSupplier){
        this.encryption = encryption;
        this.keyPackageSupplier = keyPackageSupplier;
    }

    /**
     * Creates the message with MessageId CONVERSATION_REQUEST and errorState OK
     * @param receiverID Integer, receiver of the message.
     * @param keyOwnerID Integer, id of the user, whose PublicKey will be send.
     * @param newConversationalistNick String, nick of the user whose key will be send.
     * @return UEMessage - message ready to send.
     * @throws rsa.exceptions.EncryptionException when sth went wrong with encryption process.
     */
    public UEMessage connected(Integer receiverID, Integer keyOwnerID, String newConversationalistNick) throws EncryptionException, ElementNotFoundException {
        String[] toSend = keyPackageSupplier.supply(keyOwnerID, newConversationalistNick);
        Header header = HeaderGenerator.createHeader(conversationRequest, 0, toSend.length);
        Message message = new Message(header, Arrays.asList(toSend));
        UMessage uMessage = new UMessage(receiverID, message);

        return encryption.encryptMessage(uMessage);
    }

    /**
     * Creates the message with MessageId CONVERSATION_REQUEST and errorState ErrorId.USER_NOT_LOGGED.
     * @param receiverID Integer, receiver of the message.
     * @return UEMessage - message ready to send.
     */
    public UEMessage notLogged(Integer receiverID){
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(conversationRequest, 1));
        return new UEMessage(receiverID, encrypted);
    }

    /**
     * Creates the message with MessageId CONVERSATION_REQUEST and ErrorId.BUSY_USER.
     * @param receiverID Integer, receiver of the message.
     * @return UEMessage - message ready to send.
     */
    public UEMessage busyUser(Integer receiverID){
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(conversationRequest, 2));
        return new UEMessage(receiverID, encrypted);
    }

    /**
     * Creates the message with MessageId CONVERSATION_REQUEST and  ErrorId.ON_BLACK_LIST.
     * @param receiverID Integer, receiver of the message.
     * @return UEMessage - message ready to send.
     */
    public UEMessage onBlackList(Integer receiverID){
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(conversationRequest, 4));
        return new UEMessage(receiverID, encrypted);
    }
}