package message.generarators;

import message.types.EncryptedMessage;
import message.types.Message;
import message.utils.Encryption;
import messages.IncorrectMessageId;
import messages.MessageId;
import rsa.exceptions.EncryptionException;
import user.User;

/**
 * Created by tochur on 01.05.15.
 */
public class Conversationalist_Disconnected {
    public static EncryptedMessage message(User user) throws EncryptionException {
        MessageId id = MessageId.CONVERSATIONALIST_DISCONNECTED;
        Message message = null;
        try {
            message = new Message(id, id.createErrorId(0), user.getNick());
        } catch (IncorrectMessageId incorrectMessageId) {
            System.out.println("Error inside Messages, wrong error number, repair that.");
        }
        return Encryption.encryptMessage(user, message);
    }
}
