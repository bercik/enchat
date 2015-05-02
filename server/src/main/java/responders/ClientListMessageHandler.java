package responders;

import containers.Logged;
import message.types.EncryptedMessage;
import responders.exceptions.ReactionException;
import rsa.exceptions.DecryptingException;
import user.User;
import user.UserState;

/**
 * Created by tochur on 19.04.15.
 */
public class ClientListMessageHandler extends AbstractMessageHandler {

    /**
     * Constructor of handler
     * Initialize permitted userState by invoking getPermittedStates in AbstractMessageHandler
     *
     * @param sender    - author of the message
     * @param encrypted - received message
     */
    public ClientListMessageHandler(User sender, EncryptedMessage encrypted) {
        super(sender, encrypted);
    }

    @Override
    protected void processMessage() throws DecryptingException {

    }

    @Override
    protected void createAncillaryVariables() {

    }

    @Override
    protected void reaction() throws ReactionException {
        Logged.getInstance().getActiveUsers();
        //EncryptedMessage answer = Lists.
    }

    @Override
    protected UserState[] getPermittedUserStates() {
        return new UserState[]{ UserState.LOGGED, UserState.AFTER_KEY_EXCHANGE };
    }
}
