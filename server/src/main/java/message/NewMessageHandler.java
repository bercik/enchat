package message;

import message.Message;
import message.MessageReader;
import message.handlers.MessageHandler;
import message.handlers.MessageHandlerFactory;
import user.ActiveUser;

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
        /*Getting message*/
        Message message = MessageReader.readMessage(activeUser);

        /*Creating suitable message handler.*/
        MessageHandler messageHandler = MessageHandlerFactory.getMessageHandler(message, activeUser);
        messageHandler.handle();
    }
}
