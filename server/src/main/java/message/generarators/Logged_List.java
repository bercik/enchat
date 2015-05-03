package message.generarators;

import message.types.EncryptedMessage;
import message.types.Header;
import message.types.Message;
import message.utils.Encryption;
import messages.MessageId;
import responders.exceptions.ReactionException;
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
    private static MessageId messageId = MessageId.CLIENTS_LIST;

    /**
     *
     * @param receiver - the ActiveUser that request for his blackList
     * @param nicks - nicks of logged users
     * @return - encryptedMessage
     * @throws ReactionException - only for developing
     * @throws EncryptionException - When encryption of the message failed
     */
    public static EncryptedMessage loggedUserList(User receiver, String[] nicks) throws ReactionException, EncryptionException {
        Header header = HeaderGenerator.createHeader(messageId, 0, nicks.length);
        Message message = new Message(header, Arrays.asList(nicks));

        return Encryption.encryptMessage(receiver, message);
    }
}
