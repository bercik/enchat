package responders.implementations;

import handlers.DisconnectHandler;
import message.generators.Messages;
import message.types.EncryptedMessage;
import responders.AbstractMessageHandler;
import responders.exceptions.ReactionException;
import rsa.exceptions.DecryptingException;
import user.User;
import user.UserState;

/**
 * Created by tochur on 19.04.15.
 */
public class DisconnectMessageHandler extends AbstractMessageHandler {

    /**
     * Constructor of DisconnectRequestHandler
     *
     * @param sender - author of the message
     * @param encrypted  - received message
     */
    public DisconnectMessageHandler(User sender, EncryptedMessage encrypted, Messages messages) {
        super(sender, encrypted, messages);
    }

    @Override
    protected void processMessage() throws DecryptingException {

    }

    @Override
    protected void createAncillaryVariables() {

    }

    @Override
    protected void reaction() throws ReactionException {
        DisconnectHandler.disconnect(sender);
    }

    @Override
    protected UserState[] getPermittedUserStates() {
        return new UserState[] {UserState.IN_ROOM, UserState.LOGGED, UserState.AFTER_KEY_EXCHANGE, UserState.CONNECTED_TO_SERVER };
    }
}
