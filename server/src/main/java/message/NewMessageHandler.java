package message;

import message.Message;
import message.MessageReader;
import message.handlers.MessageHandler;
import message.handlers.MessageHandlerFactory;
import messages.IncorrectMessageId;
import user.ActiveUser;

import java.io.IOException;

/**
 * Created by tochur on 17.04.15.
 *
 * When server receive new message from client this class is going to:
 *  - get this message
 *  - handle this message by creating suitable handler
 */
public class NewMessageHandler implements Runnable {
    /*User that send message*/
    ActiveUser activeUser;

    NewMessageHandler(ActiveUser user){
        this.activeUser = user;
    }

    @Override
    public void run() {
        try {
            MessageHandler messageHandler = MessageHandlerFactory.getMessageHandler(activeUser);
            messageHandler.handle();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IncorrectMessageId incorrectMessageId) {
            incorrectMessageId.printStackTrace();
        }
    }
}
