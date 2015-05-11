package responders.implementations.lists;

import containers.Logged;
import message.generators.Logged_List;
import message.types.EncryptedMessage;
import message.utils.MessageSender;
import responders.AbstractMessageHandler;
import responders.exceptions.ReactionException;
import rsa.exceptions.DecryptingException;
import rsa.exceptions.EncryptionException;
import user.User;
import user.UserState;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tochur on 19.04.15.
 */
public class ClientListMessageHandler extends AbstractMessageHandler {
    String senderNick;
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
        senderNick = sender.getNick();
    }

    @Override
    protected void reaction() throws ReactionException {
        Collection<User> logged = Logged.getInstance().getLogged();
        Set<String> available = new HashSet<>();
        for(User user: logged){
            if( !user.getData().getBlackList().hasNick(senderNick) )
                available.add(user.getNick());
        }

        EncryptedMessage answer = null;
        try {
            answer = Logged_List.create(sender, available.toArray(new String[0]));
        } catch (EncryptionException e) {
            throw new ReactionException();
        }

        try {
            MessageSender.sendMessage(sender, answer);
        } catch (IOException e) {
            throw new ReactionException();
        }
    }

    @Override
    protected UserState[] getPermittedUserStates() {
        return new UserState[]{ UserState.LOGGED, UserState.AFTER_KEY_EXCHANGE };
    }
}
