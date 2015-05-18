package message.generators;

import com.google.inject.Inject;
import controller.utils.cypher.Encryption;
import message.exceptions.UnableToInformReceiver;
import message.types.*;
import messages.MessageId;
import rsa.exceptions.EncryptionException;

/**
 * Created by tochur on 02.05.15.
 */
public class Incoming_Conversation {
    private MessageId incomingConversation = MessageId.INCOMING_CONVERSATION;
    private EncryptedMessage encrypted;
    private Encryption encryption;

    @Inject
    public Incoming_Conversation( Encryption encryption){
        this.encryption = encryption;
    }

    public UEMessage connected(Integer receiverID){
        encrypted =  new EncryptedMessage(HeaderGenerator.createHeader(incomingConversation, 0));
        return new UEMessage(receiverID, encrypted);
    }

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
