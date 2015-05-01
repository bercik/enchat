package responders.implementations.lists;

import containers.BlackList;
import containers.Registered;
import containers.exceptions.OverloadedCannotAddNew;
import message.generarators.Add_To_Black_List;
import message.types.EncryptedMessage;
import message.utils.MessageSender;
import responders.AbstractMessageHandler;
import responders.exceptions.ReactionException;
import rsa.exceptions.DecryptingException;
import user.ActiveUser;
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
    public AddToBlackListMessageHandler(ActiveUser sender, EncryptedMessage encrypted) {
        super(sender, encrypted);
    }

    @Override
    protected void processMessage() throws DecryptingException {
        this.decryptMessage();
    }

    @Override
    protected void createAncillaryVariables() {
        this.nickToBlock = message.getPackages().get(0);
        this.blackList = sender.getUserData().getBlackList();
    }

    @Override
    protected void reaction() throws ReactionException {
        EncryptedMessage encrypted;

        if(Registered.getInstance().isLoginFree(nickToBlock)){
            encrypted = Add_To_Black_List.userNotExists();
        } else if ( blackList.hasNick(nickToBlock)){
            encrypted = Add_To_Black_List.alreadyAdded();
        } else {
            try {
                blackList.add(nickToBlock);
                encrypted = Add_To_Black_List.addedSuccessfully();
            } catch (OverloadedCannotAddNew overloadedCannotAddNew) {
                encrypted = Add_To_Black_List.toMuchOnList();
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
