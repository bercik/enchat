package message.generators;

import message.utils.Encryption;
import message3.types.EncryptedMessage;
import message3.types.Header;
import message3.types.Message;
import messages.MessageId;
import rsa.exceptions.EncryptionException;
import user.User;

import java.util.Arrays;

/**
 * Created by tochur on 03.05.15.
 *
 * Creates message with all strings (user nicks).
 * Each nick is wrapped in own package.
 */
public class Logged_List {
    private MessageId messageId = MessageId.CLIENTS_LIST;

    /**
     *
     * @param receiver - the ActiveUser that request for his blackList
     * @param nicks - nicks of logged users
     * @return - encryptedMessage
     * @throws rsa.exceptions.EncryptionException - When encryption of the message failed
     */
    public EncryptedMessage create(User receiver, String[] nicks) throws EncryptionException {
        Header header = HeaderGenerator.createHeader(messageId, 0, nicks.length);
        Message message = new Message(header, Arrays.asList(nicks));

        return Encryption.encryptMessage(receiver, message);
    }
}
