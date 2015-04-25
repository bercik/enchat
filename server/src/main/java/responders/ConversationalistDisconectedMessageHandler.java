package responders;

import message.EncryptedMessage;
import user.ActiveUser;

/**
 * Created by tochur on 19.04.15.
 */
public class ConversationalistDisconectedMessageHandler implements MessageHandler {
    @Override
    public void handle(ActiveUser activeUser, EncryptedMessage message) {

    }
}
