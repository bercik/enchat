package responders;

import message.types.EncryptedMessage;
import message.types.Message;
import responders.exceptions.IncorrectUserStateException;
import responders.exceptions.ReactionException;
import rsa.exceptions.DecryptingException;
import user.ActiveUser;

/**
 * Created by tochur on 19.04.15.
 */
public interface IMessageHandler {
    public void handle() throws IncorrectUserStateException, DecryptingException, ReactionException;
}
