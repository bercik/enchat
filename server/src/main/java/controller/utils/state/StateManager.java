package controller.utils.state;

import com.google.inject.Inject;
import controller.responders.exceptions.IncorrectUserStateException;
import message.types.UEMessage;
import messages.MessageId;
import model.user.UserState;
import model.containers.temporary.UserStates;
import model.user.Verifier;

import java.util.Collection;

/**
 * Let us to verify weather user is allowed to send this message type.
 *
 * @author Created by tochur on 16.05.15.
 */
public class StateManager {
    private UserStates userStates;
    private Verifier verifier;

    /**
     * Creates the StateManager object.
     * @param userStates UserStates, holds info about state of every user.
     * @param verifier Verifier, util used to verify user states.
     */
    @Inject
    public StateManager(UserStates userStates, Verifier verifier){
        this.userStates = userStates;
        this.verifier = verifier;
    }

    /**
     * Verifies weather user is allowed to send this kind of message.
     * @param euMessage UEMessage, message send by user.
     * @throws IncorrectUserStateException when user send the message that is not allowed in his state.
     */
    public void verify(UEMessage euMessage) throws IncorrectUserStateException {
        Integer userId = euMessage.getAuthorID();
        UserState userState =  userStates.getUserState(userId);
        MessageId messageId = euMessage.getId();
        if ( !verifier.verify(userState, messageId))
            throw new IncorrectUserStateException();
    }

    /**
     * Changes current userState.
     * @param ID Integer, id of the user whose state will be changed.
     * @param newUserState UserState, new user state.
     */
    public void update(Integer ID, UserState newUserState){
        userStates.updateState(ID, newUserState);
    }

    /**
     * Updated the state of few users with id in collection.
     * @param IDs Connection&lt;Integer&gt; id's collection of users whose State will be changed.
     * @param newUserState UserState, new users state.
     */
    public void update(Collection<Integer> IDs, UserState newUserState){
        for(Integer userID : IDs){
            userStates.updateState(userID, UserState.LOGGED);
        }
    }
}
