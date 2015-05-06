package responders.implementations.lists;

import containers.BlackList;
import containers.Registered;
import containers.exceptions.AlreadyInCollection;
import containers.exceptions.OverloadedCannotAddNew;
import message.generators.Add_To_Black_List;
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
public class AddToBlackListMessageHandler extends AbstractMessageHandler {
    private String nickToBlock;
    private BlackList blackList;
    /**
     * Constructor of handler
     *
     * @param sender - author of the message
     * @param encrypted  - received message
     */
    public AddToBlackListMessageHandler(User sender, EncryptedMessage encrypted) {
        super(sender, encrypted);
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
            answer = Add_To_Black_List.userNotExists();
        } else {
            try {
                blackList.add(nickToBlock);
                answer = Add_To_Black_List.addedSuccessfully();
            } catch (OverloadedCannotAddNew overloadedCannotAddNew) {
                answer = Add_To_Black_List.toMuchOnList();
            } catch (AlreadyInCollection alreadyInCollection) {
                answer = Add_To_Black_List.alreadyAdded();
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
