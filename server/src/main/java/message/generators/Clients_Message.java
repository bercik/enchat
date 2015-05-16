package message.generators;

import com.google.inject.Inject;
import controller.utils.cypher.Encryption;
import message.types.*;

import messages.MessageId;
import rsa.exceptions.EncryptionException;

/**
 * Created by tochur on 01.05.15.
 */
public class Clients_Message {
    private MessageId clientMessage = MessageId.CLIENT_MESSAGE;
    private Encryption encryption;

    @Inject
    public Clients_Message(Encryption encryption){
        this.encryption = encryption;
    }

    /**
     *
     * @param receiverID - author of the message
     * @param nick - nick of the user to whom sending message failed
     * @return encrypted message, informing that sending message failed.
     * @throws rsa.exceptions.EncryptionException - when during encrypting answer error happened.
     */
    public UEMessage message(Integer receiverID, String nick) throws EncryptionException{
        Header header = HeaderGenerator.createHeader(clientMessage, 0, 1);
        Message message = new Message(header, nick);
        UMessage uMessage = new UMessage(receiverID, message);

        return encryption.encryptMessage(uMessage);
    }
}
