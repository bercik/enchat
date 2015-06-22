package message.generators;

import message.types.EncryptedMessage;
import message.types.Header;
import message.types.UEMessage;
import messages.MessageId;
import rsa.exceptions.EncryptionException;

/**
 * @author Created by tochur on 01.05.15.
 */
public class Clients_Message {
    private MessageId clientMessage = MessageId.CLIENT_MESSAGE;

    public UEMessage ok(Integer receiverID){
        Header header = HeaderGenerator.createHeader(clientMessage, 0, 0);
        EncryptedMessage encryptedMessage = new EncryptedMessage(header);

        return new UEMessage(receiverID, encryptedMessage);
    }

    public UEMessage failedToDeliver(Integer receiverID){
        Header header = HeaderGenerator.createHeader(clientMessage, 1, 0);
        EncryptedMessage encryptedMessage = new EncryptedMessage(header);

        return new UEMessage(receiverID, encryptedMessage);
    }
}
