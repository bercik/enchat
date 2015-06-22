package message.generators;

import message.types.EncryptedMessage;
import message.types.Header;
import message.types.UEMessage;
import messages.MessageId;

/**
 * Creator of the messages.
 *
 * @author Created by tochur on 18.05.15.
 *
 * Passing message with header modification.
 */
public class Server_Message {
    private MessageId serverMessage = MessageId.SERVER_MESSAGE;

    /**
     * Creates the message with MessageId SERVER_MESSAGE and errorState OK, with the message.
     * @param ueMessage UEMessage, message to pass.
     * @return UEMessage - message ready to send.
     */
    public EncryptedMessage message(UEMessage ueMessage) {
        Header header = HeaderGenerator.createHeader(serverMessage, 0, ueMessage.getPackageAmount());

        return new EncryptedMessage(header, ueMessage.getPackages());
    }

    /**
     * Creates the message with MessageId SERVER_MESSAGE and errorState OK
     * @param authorID Integer, id of the message author.
     * @return UEMessage - message ready to send.
     */
    public UEMessage confirmation(Integer authorID) {
        Header header = HeaderGenerator.createHeader(serverMessage, 0, 0);

        return new UEMessage(authorID, new EncryptedMessage(header));
    }
}
