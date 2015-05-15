package responders.implementations.lists;

import containers.BlackList;
import containers.Registered;
import containers.exceptions.AlreadyInCollection;
import containers.exceptions.OverloadedCannotAddNew;
import message3.generators.Messages;
import message3.types.EncryptedMessage;
import message3.utils.MessageSender;
import responders.AbstractMessageHandler;
import responders.exceptions.ReactionException;
import rsa.exceptions.DecryptingException;
import user.User;
import user.UserState;

import java.io.IOException;

/**
 * Created by tochur on 19.04.15.
 */
public class AddToBlackListMessageHandler extends AbstractMessageHandler {
    private String nickToBlock;
    private BlackList blackList;
    /**
     * Constructor of handler
     *
     * @param sender - author of the message
     * @param encrypted  - received message
     */
    public AddToBlackListMessageHandler(User sender, EncryptedMessage encrypted, Messages messages) {
        super(sender, encrypted, messages);
    }

    @Override
    protected void processMessage() throws DecryptingException {
        this.decryptMessage();
    }

    @Override
    protected void createAncillaryVariables() {
        this.nickToBlock = message.getPackages().get(0);
        this.blackList = sender.getData().getBlackList();
    }

    @Override
    protected void reaction() throws ReactionException {
        EncryptedMessage answer;

        if(Registered.getInstance().isLoginFree(nickToBlock)){
            answer = messages.blackList().userNotExistsCannotAdd();
        } else {
            try {
                blackList.add(nickToBlock);
                answer = messages.blackList().addedSuccessfully();
            } catch (OverloadedCannotAddNew overloadedCannotAddNew) {
                answer = messages.blackList().toMuchOnList();
            } catch (AlreadyInCollection alreadyInCollection) {
                answer = messages.blackList().alreadyAdded();
            }
        }

        try {
            MessageSender.sendMessage(sender, encrypted);
        } catch (IOException e) {
            throw new ReactionException();
        }
    }

    @Override
    protected UserState[] getPermittedUserStates() {
        return new UserState[] {UserState.LOGGED, UserState.IN_ROOM };
    }
}
