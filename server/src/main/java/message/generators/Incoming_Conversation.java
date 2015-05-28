package message.generators;

import com.google.inject.Inject;
import controller.utils.cypher.Encryption;
import message.exceptions.UnableToInformReceiver;
import message.types.*;
import messages.MessageId;
import rsa.exceptions.EncryptionException;

import java.util.Arrays;

/**
 * Created by tochur on 02.05.15.
 */
public class Incoming_Conversation {
    private MessageId incomingConversation = MessageId.INCOMING_CONVERSATION;
    private final Integer packagesAmount = 5;
    private Encryption encryption;
    private KeyPackageSupplier keyPackageSupplier;

    @Inject
    public Incoming_Conversation( Encryption encryption, KeyPackageSupplier keyPackageSupplier){
        this.encryption = encryption;
        this.keyPackageSupplier = keyPackageSupplier;
    }

    /**
     * Creates message containing nick and publicKey of the user that was connected (requester)
     * to message receiver.
     * @param requesterID - id of user which id will be send
     * @param receiverID - he will got the public key (in the message).
     * @return - encrypted and tagged with receiver id message.
     */
    public UEMessage connected(Integer requesterID, String requesterNick, Integer receiverID) throws EncryptionException {
        Header header = HeaderGenerator.createHeader(incomingConversation, 0, packagesAmount);
        String[] toSend = keyPackageSupplier.supply(requesterID, requesterNick);
        Message message = new Message(header, Arrays.asList(toSend));
        UMessage uMessage = new UMessage(receiverID, message);

        return encryption.encryptMessage(uMessage);
    }

    /**
     * Creates the message when there was conversation request to user that was in this moment engaged.
     * @param receiverID - id of user who was engaged, and receiver of the message.
     * @param nickThatWantConversation - nick of user that was trying to connect.
     * @return - encrypted and tagged with receiver id message.
     * @throws UnableToInformReceiver
     */
    public UEMessage youWereBusy(Integer receiverID, String nickThatWantConversation) throws UnableToInformReceiver {
        Header header = HeaderGenerator.createHeader(incomingConversation, 1);
        Message message = new Message(header, nickThatWantConversation);
        UMessage uMessage = new UMessage(receiverID, message);
        try {
            UEMessage ueMessage = encryption.encryptMessage(uMessage);
            return ueMessage;
        } catch (EncryptionException e) {
            throw new UnableToInformReceiver();
        }
    }
}
