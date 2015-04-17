package message.handlers;

import messages.MessageId;
import user.ActiveUser;

/**
 * Created by tochur on 18.04.15.
 *
 *
 */
public class NonDecryptingHandler extends MessageHandler {
    public NonDecryptingHandler(ActiveUser activeUser, MessageId messageId) {
        super();
    }

    @Override
    public void handle() {

    }
}
