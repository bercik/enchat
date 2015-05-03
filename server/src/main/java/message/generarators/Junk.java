package message.generarators;

import message.types.EncryptedMessage;
import messages.MessageId;
import responders.exceptions.ReactionException;

/**
 * Created by tochur on 01.05.15.
 *
 * Let user to safety creating messages.
 */
public class Junk {
    private static MessageId messageId = MessageId.JUNK;

    public static EncryptedMessage ok() throws ReactionException {
        return new EncryptedMessage(HeaderGenerator.createHeader(messageId, 0));
    }
}
