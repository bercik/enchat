package message.generators;

import message.types.EncryptedMessage;
import message.types.Header;
import message.types.Message;
import message.utils.Encryption;
import messages.MessageId;
import rsa.exceptions.EncryptionException;
import user.User;

/**
 * Created by tochur on 01.05.15.
 */
public class Clients_Message {
    private static MessageId messageId = MessageId.CLIENT_MESSAGE;

    /**
     *
     * @param user - author of the message
     * @param nick - nick of the user to whom sending message failed
     * @return encrypted message, informing that sending message failed.
     * @throws EncryptionException - when during encrypting answer error happened.
     */
    public static EncryptedMessage message(User user, String nick) throws EncryptionException{
        Header header = HeaderGenerator.createHeader(messageId, 0, 1);
        Message message = new Message(header, nick);

        return Encryption.encryptMessage(user, message);
    }
}
