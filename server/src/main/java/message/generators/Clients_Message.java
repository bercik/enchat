package message.generators;

import message.types.EncryptedMessage;
import message.types.Header;
import message.types.UEMessage;
import messages.MessageId;
import rsa.exceptions.EncryptionException;

/**
 * Creator of the messages.
 *
 * @author Created by tochur on 01.05.15.
 */
public class Clients_Message {
    private MessageId clientMessage = MessageId.CLIENT_MESSAGE;

    /**
     * Creates the message with MessageId CLIENT_MESSAGE and errorState OK
     * @param receiverID Integer, receiver of the message.
     * @return UEMessage - message ready to send.
     */
    public UEMessage ok(Integer receiverID){
        Header header = HeaderGenerator.createHeader(clientMessage, 0, 0);
        EncryptedMessage encryptedMessage = new EncryptedMessage(header);

        return new UEMessage(receiverID, encryptedMessage);
    }

    /**
     * Creates the message with MessageId CLIENT_MESSAGE and errorState ErrorId.FAILED.
     * @param receiverID Integer, receiver of the message.
     * @return UEMessage - message ready to send.
     */
    public UEMessage failedToDeliver(Integer receiverID){
        Header header = HeaderGenerator.createHeader(clientMessage, 1, 0);
        EncryptedMessage encryptedMessage = new EncryptedMessage(header);

        return new UEMessage(receiverID, encryptedMessage);
    }
}
