package responders.implementations;

import message3.generators.Messages;
import message3.types.EncryptedMessage;
import responders.AbstractMessageHandler;
import responders.exceptions.ReactionException;
import rsa.exceptions.DecryptingException;
import rsa.exceptions.EncryptionException;
import user.User;
import model.user.UserState;

import java.io.IOException;

/**
 * Created by tochur on 19.04.15.
 */
public class ConversationalistDisconnectedMessageHandler extends AbstractMessageHandler {
    /**
     * Constructor of handler
     * Initialize permitted userState by invoking getPermittedStates in AbstractMessageHandler
     *
     * @param sender    - author of the message
     * @param encrypted - received message
     */
    public ConversationalistDisconnectedMessageHandler(User sender, EncryptedMessage encrypted, Messages messages) {
        super(sender, encrypted, messages);
    }

    @Override
    protected void processMessage() throws DecryptingException {

    }

    @Override
    protected void createAncillaryVariables() {

    }

    @Override
    protected void reaction() throws ReactionException, IOException, EncryptionException {
        sender.getRoom().remove(sender);
        sender.setRoom(null);
        sender.setState(UserState.LOGGED);
    }

    @Override
    protected UserState[] getPermittedUserStates() {
        return new UserState[] {UserState.IN_ROOM};
    }
}
