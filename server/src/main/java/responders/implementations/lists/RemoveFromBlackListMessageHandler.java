package responders.implementations.lists;

import containers.BlackList;
import containers.Registered;
import containers.exceptions.ElementNotFoundException;
import message.generators.Messages;
import message.types.EncryptedMessage;
import message.utils.MessageSender;
import responders.AbstractMessageHandler;
import responders.exceptions.ReactionException;
import rsa.exceptions.DecryptingException;
import user.User;
import user.UserState;

import java.io.IOException;

/**
 * Created by tochur on 19.04.15.
 */
public class RemoveFromBlackListMessageHandler extends AbstractMessageHandler {
    private String toRemove;
    private BlackList blackList;
    /**
     * Constructor of handler
     * Initialize permitted userState by invoking getPermittedStates in AbstractMessageHandler
     *
     * @param sender    - author of the message
     * @param encrypted - received message
     */
    public RemoveFromBlackListMessageHandler(User sender, EncryptedMessage encrypted, Messages messages) {
        super(sender, encrypted, messages);
    }

    @Override
    protected void processMessage() throws DecryptingException {
        decryptMessage();
    }

    @Override
    protected void createAncillaryVariables() {
        getDataFromMessage();
        blackList = sender.getData().getBlackList();
    }

    @Override
    protected void reaction() throws ReactionException {
        EncryptedMessage answer;

        if (Registered.getInstance().isLoginFree(toRemove)){
            answer = messages.blackList().userNotExistsCannotRemove();
        } else {
            try {
                blackList.remove(toRemove);
                answer = messages.blackList().removedSuccessfully();
            } catch (ElementNotFoundException elementNotFound) {
                answer = messages.blackList().notOnList();
            }
        }

        try {
            MessageSender.sendMessage(sender, answer);
        } catch (IOException e) {
            throw new ReactionException();
        }
    }

    @Override
    protected UserState[] getPermittedUserStates() {
        return new UserState[0];
    }

    @Override
    protected void getDataFromMessage(){
        toRemove = message.getPackages().get(0);
    }
}
