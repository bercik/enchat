package responders;

import responders.exceptions.IncorrectUserStateException;
import responders.exceptions.ReactionException;
import rsa.exceptions.DecryptingException;

/**
 * Created by tochur on 19.04.15.
 */
public interface IMessageHandler {
    public void handle() throws IncorrectUserStateException, DecryptingException, ReactionException;
}
