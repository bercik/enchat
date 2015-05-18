package message.generators;

import com.google.inject.Inject;
import controller.utils.cypher.Encryption;
import message.types.*;
import messages.MessageId;
import rsa.exceptions.EncryptionException;

/**
 * Created by tochur on 18.05.15.
 *
 * Passing message with header modification.
 */
public class Server_Message {
    private MessageId serverMessage = MessageId.SERVER_MESSAGE;

    public UEMessage message(UEMessage ueMessage) {
        Header header = HeaderGenerator.createHeader(serverMessage, 0, ueMessage.getPackageAmount());
        EncryptedMessage encrypted = new EncryptedMessage(header, ueMessage.getPackages());

        return new UEMessage(ueMessage.getAuthorID(), encrypted);
    }
}
