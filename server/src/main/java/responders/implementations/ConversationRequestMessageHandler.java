package responders.implementations;

import containers.Logged;
import message.generarators.ConversationRequest;
import message.types.EncryptedMessage;
import responders.AbstractMessageHandler;
import responders.exceptions.ReactionException;
import room.ToMuchUsersInThisRoom;
import rsa.exceptions.DecryptingException;
import user.ActiveUser;

/**
 * Created by tochur on 19.04.15.
 */
public class ConversationRequestMessageHandler extends AbstractMessageHandler {
    //Nick of the person to connect with
    private String nick;
    private ActiveUser userToConnect;
    /**
     * Constructor of handler
     *
     * @param activeUser - author of the message
     * @param encrypted  - received message
     */
    public ConversationRequestMessageHandler(ActiveUser activeUser, EncryptedMessage encrypted) {
        super(activeUser, encrypted);
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

        //If user is not logged
        if( (userToConnect = Logged.getInstance().getUserIfLogged(nick)) == null){
            answer = ConversationRequest.notLogged();
        } else {
            try {
                userToConnect.getRoom().add(sender);
                answer = ConversationRequest.connected();
            } catch (ToMuchUsersInThisRoom toMuchUsersInThisRoom) {
                answer = ConversationRequest.busyUser();
            }
        }
    }
}
