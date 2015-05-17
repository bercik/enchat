package message.generators;

import com.google.inject.Inject;
import controller.utils.cypher.Encryption;
import message.types.*;
import messages.MessageId;
import model.containers.PublicKeysManager;
import rsa.exceptions.EncryptionException;

import java.security.PublicKey;
import java.util.Arrays;

/**
 * Created by tochur on 01.05.15.
 */
public class Conversation_Request {
    private final MessageId conversationRequest = MessageId.CONVERSATION_REQUEST;
    private final Integer packagesAmount = 3;
    private EncryptedMessage encrypted;
    private Encryption encryption;
    private PublicKeysManager publicKeysManager;

    @Inject
    public Conversation_Request(PublicKeysManager publicKeysManager, Encryption encryption){
        this.publicKeysManager = publicKeysManager;
        this.encryption = encryption;
    }

    public UEMessage connected(Integer receiverID, Integer otherID, String newConversationalistNick) throws EncryptionException {
        Header header = HeaderGenerator.createHeader(conversationRequest, 0, packagesAmount);
        String[] toSend = prepareInfo(receiverID, otherID, newConversationalistNick);
        Message message = new Message(header, Arrays.asList(toSend));
        UMessage uMessage = new UMessage(receiverID, message);

        return encryption.encryptMessage(uMessage);
    }

    public UEMessage notLogged(Integer receiverID){
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(conversationRequest, 1));
        return new UEMessage(receiverID, encrypted);
    }

    public UEMessage busyUser(Integer receiverID){
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(conversationRequest, 2));
        return new UEMessage(receiverID, encrypted);
    }

    public UEMessage onBlackList(Integer receiverID){
        encrypted = new EncryptedMessage(HeaderGenerator.createHeader(conversationRequest, 4));
        return new UEMessage(receiverID, encrypted);
    }


    private String[] prepareInfo(Integer receiverID, Integer otherID, String newConversationalistNick) {
        String[] array = new String[3];
        array[0] = newConversationalistNick;

        PublicKey publicKey = publicKeysManager.getKey(otherID);






        return null;
    }
}
