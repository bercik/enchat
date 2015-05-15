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
public class Conversationalist_Disconnected {
    private MessageId conversationalistDisconnected = MessageId.CONVERSATIONALIST_DISCONNECTED;

    public EncryptedMessage message(User user, String disconnectedNick) throws EncryptionException{
        Header header = HeaderGenerator.createHeader(conversationalistDisconnected, 0, 1);
        Message message = new Message(header, disconnectedNick);

        return Encryption.encryptMessage(user, message);
    }
}
