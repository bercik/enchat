package responders;

import message.types.EncryptedMessage;
import responders.exceptions.ReactionException;
import room.ChatRoom;
import rsa.exceptions.DecryptingException;
import user.ActiveUser;
import user.UserState;

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
    public ClientMessageHandler(ActiveUser sender, EncryptedMessage encrypted) {
        super(sender, encrypted);
    }

    @Override
    protected void processMessage() throws DecryptingException {

    }

    @Override
    protected void createAncillaryVariables() {
        chatRoom = sender.getRoom();
    }

    @Override
    protected void reaction() throws ReactionException {
        chatRoom.sendMessageAs(sender, encrypted);
    }

    @Override
    protected UserState[] getPermittedUserStates() {
        return new UserState[] { UserState.IN_ROOM};
    }
}
