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
public class Conversationalist_Disconnected {
    private MessageId conversationalistDisconnected = MessageId.CONVERSATIONALIST_DISCONNECTED;

    public EncryptedMessage message(User user, String disconnectedNick) throws EncryptionException{
        Header header = HeaderGenerator.createHeader(conversationalistDisconnected, 0, 1);
        Message message = new Message(header, disconnectedNick);

        return Encryption.encryptMessage(user, message);
    }
}
