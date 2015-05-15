package message.generators;

import message.utils.Encryption;
import message3.types.EncryptedMessage;
import message3.types.Header;
import message3.types.Message;
import messages.MessageId;
import rsa.exceptions.EncryptionException;
import user.User;

/**
 * Created by tochur on 01.05.15.
 */
public class Clients_Message {
    private MessageId clientMessage = MessageId.CLIENT_MESSAGE;

    /**
     *
     * @param user - author of the message
     * @param nick - nick of the user to whom sending message failed
     * @return encrypted message, informing that sending message failed.
     * @throws rsa.exceptions.EncryptionException - when during encrypting answer error happened.
     */
    public EncryptedMessage message(User user, String nick) throws EncryptionException{
        Header header = HeaderGenerator.createHeader(clientMessage, 0, 1);
        Message message = new Message(header, nick);

        return Encryption.encryptMessage(user, message);
    }
}
