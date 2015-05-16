package controller.utils.state;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import messages.MessageId;
import model.UserState;

/**
 * Created by tochur on 16.05.15.
 */
@Singleton
public class Verifier {
    AllowedMessages allowedMessages;

    @Inject
    public Verifier(AllowedMessages allowedMessages){
        this.allowedMessages = allowedMessages;
    }

    public boolean verify(UserState state, MessageId messageId){
        return true;
    }
}
