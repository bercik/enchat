package message.generators;

import message.types.EncryptedMessage;
import message.types.Header;
import message.types.Message;
import message.utils.Encryption;
import messages.MessageId;
import rsa.exceptions.EncryptionException;
import user.User;

import java.util.Arrays;

/**
 * Created by tochur on 03.05.15.
 */
public class Black_List {
    private static MessageId messageId = MessageId.BLACK_LIST;

    /**
     * Creates message with users from blackList
     * @param receiver - the ActiveUser that request for his blackList
     * @param nicks - nicks from blackList
     * @return EncryptedMessage - answer, prepared by the system.
     * @throws rsa.exceptions.EncryptionException - When encryption of the message failed
     */
    public static EncryptedMessage blackList(User receiver, String[] nicks) throws EncryptionException {
        Header header = HeaderGenerator.createHeader(messageId, 0, nicks.length);
        Message message = new Message(header, Arrays.asList(nicks));

        return Encryption.encryptMessage(receiver, message);
    }
}
