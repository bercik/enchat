package controller.utils.state;

import com.google.inject.Singleton;
import messages.MessageId;
import model.user.UserState;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tochur on 16.05.15.
 */
@Singleton
public class AllowedMessages {
    private Map<UserState, MessageId[]> map = new HashMap<>();

    public AllowedMessages(){

    }

    public boolean isOK(UserState current, MessageId messageID){
        return true;
    }
}
