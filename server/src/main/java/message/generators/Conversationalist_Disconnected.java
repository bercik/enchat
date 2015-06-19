package message.generators;

import com.google.inject.Inject;
import controller.utils.cypher.Encryption;
import message.types.*;
import messages.MessageId;
import rsa.exceptions.EncryptionException;

/**
 * Created by tochur on 01.05.15.
 */
public class Conversationalist_Disconnected {
    private MessageId conversationalistDisconnected = MessageId.CONVERSATIONALIST_DISCONNECTED;
    private Encryption encryption;

    @Inject
    public Conversationalist_Disconnected(Encryption encryption){
        this.encryption = encryption;
    }

    public UEMessage message(Integer receiverID){
        Header header = HeaderGenerator.createHeader(conversationalistDisconnected, 0, 0);
        return new UEMessage(receiverID, new EncryptedMessage(header));
    }

    /*public UEMessage message(Integer receiverID, String disconnectedNick) throws EncryptionException{
        Header header = HeaderGenerator.createHeader(conversationalistDisconnected, 0, 1);
        Message message = new Message(header, disconnectedNick);
        UMessage uMessage = new UMessage(receiverID, message);

        return encryption.encryptMessage(uMessage);
    }

    public UEMessage messageAnonymous(Integer id) {
        try {
            return message(id, "Unknown");
        } catch (EncryptionException e) {
            return null;
        }
    }*/
}
