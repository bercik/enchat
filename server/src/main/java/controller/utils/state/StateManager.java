package controller.utils.state;

import com.google.inject.Inject;
import controller.responders.exceptions.IncorrectUserStateException;
import message.types.UEMessage;
import messages.MessageId;
import model.UserState;
import model.containers.UserStates;

/**
 * Created by tochur on 16.05.15.
 *
 * Let us to verify weather user is allowed to send this message type.
 */
public class StateManager {
    private UserStates userStates;
    private Verifier verifier;

    @Inject
    public StateManager(UserStates userStates, Verifier verifier){
        this.userStates = userStates;
        this.verifier = verifier;
    }

    public void verify(UEMessage euMessage) throws IncorrectUserStateException {
        Integer userId = euMessage.getAuthorID();
        UserState userState =  userStates.getUserState(userId);
        MessageId messageId = euMessage.getId();
        if ( !verifier.verify(userState, messageId))
            throw new IncorrectUserStateException();
    }

    public void update(Integer ID, UserState newUserState){
        userStates.updateState(ID, newUserState);
    }
}
