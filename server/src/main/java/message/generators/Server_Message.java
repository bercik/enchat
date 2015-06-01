package message.generators;

import message.types.EncryptedMessage;
import message.types.Header;
import message.types.UEMessage;
import messages.MessageId;

/**
 * Created by tochur on 18.05.15.
 *
 * Passing message with header modification.
 */
public class Server_Message {
    private MessageId serverMessage = MessageId.SERVER_MESSAGE;

    public EncryptedMessage message(UEMessage ueMessage) {
        Header header = HeaderGenerator.createHeader(serverMessage, 0, ueMessage.getPackageAmount());

        return new EncryptedMessage(header, ueMessage.getPackages());
    }

    public UEMessage confirmation(Integer authorID) {
        Header header = HeaderGenerator.createHeader(serverMessage, 0, 0);

        return new UEMessage(authorID, new EncryptedMessage(header));
    }
}
