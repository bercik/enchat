package responders;

import message.types.EncryptedMessage;
import message.types.IMessage;
import user.ActiveUser;

/**
 * Created by tochur on 24.04.15.
 */
public abstract class AbstractMessageHandler implements MessageHandler {
    public abstract void handle(ActiveUser activeUser, EncryptedMessage message);
    //protected abstract boolean isMessageAppropriate(ActiveUser activeUser, IMessage message);
}
