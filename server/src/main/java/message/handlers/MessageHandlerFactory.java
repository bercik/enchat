package message.handlers;

import message.Message;
import user.ActiveUser;

/**
 * Created by tochur on 17.04.15.
 */
public abstract class MessageHandlerFactory{
    public static MessageHandler getMessageHandler(Message message, ActiveUser activeUser){
        return null;
    }
}
