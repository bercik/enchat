package message.generators;

import com.google.inject.Inject;
import controller.utils.cypher.Encryption;
import message.types.*;
import messages.MessageId;
import rsa.exceptions.EncryptionException;

/**
 * Creator of the messages.
 *
 * @author Created by tochur on 01.05.15.
 */
public class Conversationalist_Disconnected {
    private MessageId conversationalistDisconnected = MessageId.CONVERSATIONALIST_DISCONNECTED;
    private Encryption encryption;

    /**
     * Creates the util user for message creation.
     * @param encryption Encryption - util for encryption the message.
     */
    @Inject
    public Conversationalist_Disconnected(Encryption encryption){
        this.encryption = encryption;
    }

    /**
     * Creates the message with MessageId CONVERSATIONALIST_DISCONNECTED and errorState OK
     * @param receiverID Integer, receiver of the message.
     * @return UEMessage - message ready to send.
     */
    public UEMessage message(Integer receiverID) {
        Header header = HeaderGenerator.createHeader(conversationalistDisconnected, 0, 0);
        return new UEMessage(receiverID, new EncryptedMessage(header));
    }
}
