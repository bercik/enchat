package responders.implementations;

import containers.Logged;
import containers.exceptions.ElementNotFoundException;
import message.generarators.ConversationRequest;
import message.types.EncryptedMessage;
import message.utils.MessageSender;
import responders.AbstractMessageHandler;
import responders.exceptions.ReactionException;
import room.ToMuchUsersInThisRoom;
import rsa.exceptions.DecryptingException;
import user.User;
import user.UserState;

import java.io.IOException;

/**
 * Created by tochur on 19.04.15.
 */
public class ConversationRequestMessageHandler extends AbstractMessageHandler {
    //Nick of the person to connect with
    private String nick;
    private User userToConnect;
    /**
     * Constructor of handler
     *
     * @param sender - author of the message
     * @param encrypted  - received message
     */
    public ConversationRequestMessageHandler(User sender, EncryptedMessage encrypted) {
        super(sender, encrypted);
    }

    @Override
    protected void processMessage() throws DecryptingException {
        decryptMessage();
    }

    @Override
    protected void createAncillaryVariables() {
        nick = message.getPackages().get(0);
    }

    @Override
    protected void reaction() throws ReactionException {
        EncryptedMessage answer;

        try {
            userToConnect = Logged.getInstance().getUser(nick);
            if (userToConnect.getData().getBlackList().hasNick(nick)){
                answer = ConversationRequest.notLogged();
            }else {
                try {
                    userToConnect.getRoom().add(sender);
                    answer = ConversationRequest.connected();
                } catch (ToMuchUsersInThisRoom toMuchUsersInThisRoom) {
                    answer = ConversationRequest.busyUser();
                }
            }
        } catch (ElementNotFoundException e) {
            answer = ConversationRequest.notLogged();
        }

        try {
            MessageSender.sendMessage(sender, answer);
        } catch (IOException e) {
            throw new ReactionException();
        }
    }

    @Override
    protected UserState[] getPermittedUserStates() {
        return new UserState[] {UserState.LOGGED};
    }
}
