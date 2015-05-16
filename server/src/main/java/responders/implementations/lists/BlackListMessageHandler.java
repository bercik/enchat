package responders.implementations.lists;

import message3.generators.Messages;
import message3.types.EncryptedMessage;
import message3.utils.MessageSender;
import responders.AbstractMessageHandler;
import responders.exceptions.ReactionException;
import rsa.exceptions.DecryptingException;
import rsa.exceptions.EncryptionException;
import user.User;
import model.UserState;

import java.io.IOException;

/**
 * Created by tochur on 19.04.15.
 */
public class BlackListMessageHandler extends AbstractMessageHandler {
    /**
     * Constructor of handler
     *
     * @param sender - author of the message
     * @param encrypted  - received message
     */
    public BlackListMessageHandler(User sender, EncryptedMessage encrypted, Messages messages) {
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
        String[] nicks = sender.getData().getBlackList().getNicks().toArray(new String[0]);
        try {
            EncryptedMessage answer = messages.blackList().create(sender, nicks);
            MessageSender.sendMessage(sender, answer);
        } catch (IOException e) {
            throw new ReactionException();
        } catch (EncryptionException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected UserState[] getPermittedUserStates() {
        return new UserState[] {UserState.LOGGED};
    }
}
