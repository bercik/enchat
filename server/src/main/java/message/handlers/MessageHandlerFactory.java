package message.handlers;

import messages.IncorrectMessageId;
import user.ActiveUser;

import java.io.IOException;

/**
 * Created by tochur on 17.04.15.
 */
public abstract class MessageHandlerFactory{

    /**
     * Creates new MessageHandler.
     * The MessageHandler concrete type depends on the message id - first int value from buffer.
     * Be sure that first value of the message if not encrypted id and, and has correct value.
     * @param activeUser
     * @return Handler, that is capable to handle new message by invoking handle() method.
     */
    public static MessageHandler getMessageHandler(ActiveUser activeUser) throws IOException, IncorrectMessageId {
        /*MessageId messageId = MessageReader.readMessageId(activeUser);

        switch (messageId){
            case JUNK:
                return new JunkMessageHandler(activeUser);
            case CLIENT_MESSAGE:
            case PUBLIC_KEY:
                return new NonDecryptingHandler(activeUser, messageId);
            default:
                return new DecryptingHandler(activeUser, messageId);
        }*/
        return null;
    }
}
