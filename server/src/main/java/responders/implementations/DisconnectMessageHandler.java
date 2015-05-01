package responders.implementations;

import message.types.EncryptedMessage;
import responders.AbstractMessageHandler;
import responders.exceptions.ReactionException;
import rsa.exceptions.DecryptingException;
import user.ActiveUser;
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
    public DisconnectMessageHandler(ActiveUser sender, EncryptedMessage encrypted) {
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
        sender.getRoom().remove(sender);
        sender.setRoom(null);
        sender.setState(UserState.LOGGED);
    }

    @Override
    protected UserState[] getPermittedUserStates() {
        return new UserState[] {UserState.IN_ROOM};
    }
}
