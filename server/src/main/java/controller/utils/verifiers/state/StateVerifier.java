package controller.utils.verifiers.state;

import com.google.inject.Inject;
import controller.responders.exceptions.IncorrectUserStateException;
import controller.utils.verifiers.state.AllowedMessages;
import message.types.UEMessage;
import messages.MessageId;
import model.UserState;
import model.containers.UserStates;

/**
 * Created by tochur on 16.05.15.
 *
 * Let us to verify weather user is allowed to send this message type.
 */
public class StateVerifier {
    private UserStates userStates;
    private Verifier verifier;

    @Inject
    public StateVerifier(UserStates userStates,Verifier verifier){
        this.userStates = userStates;
        this.verifier = verifier;
    }

    public boolean verify(UEMessage euMessage){
        Integer userId = euMessage.getAuthorID();
        UserState userState =  userStates.getUserState(userId);
        MessageId messageId = euMessage.getId();
        return verifier.verify(userState, messageId);
    }
}
