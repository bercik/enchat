package responders.implementations;

import message3.generators.Messages;
import message3.types.EncryptedMessage;
import responders.AbstractMessageHandler;
import responders.exceptions.ReactionException;
import model.ChatRoom;
import rsa.exceptions.DecryptingException;
import user.User;
import model.user.UserState;

import java.io.IOException;

/**
 * Created by tochur on 19.04.15.
 */
public class ClientMessageHandler extends AbstractMessageHandler {
    private ChatRoom chatRoom;

    /**
     * Constructor of handler.
     * Initialize permitted userState by invoking getPermittedStates in base constructor.
     *
     * @param sender    - author of the message
     * @param encrypted - received message
     */
    public ClientMessageHandler(User sender, EncryptedMessage encrypted, Messages messages) {
        super(sender, encrypted, messages);
    }

    @Override
    protected void processMessage() throws DecryptingException {

    }

    @Override
    protected void createAncillaryVariables() {
        chatRoom = sender.getRoom();
    }

    @Override
    protected void reaction() throws ReactionException, IOException {
        chatRoom.sendMessageAs(sender, encrypted);
    }

    @Override
    protected UserState[] getPermittedUserStates() {
        return new UserState[] { UserState.IN_ROOM};
    }
}
