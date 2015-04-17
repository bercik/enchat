package message;

import containers.ActiveUsers;
import message.handlers.MessageHandlerFactory;
import user.ActiveUser;

/**
 * Created by tochur on 17.04.15.
 *
 * It is responsible for reading the message from the user.
 * Encrypted message from buffer is changed to Message object.
 * The message is read from the clients buffer, encrypted and Message object is created.
 */
public class MessageReader{
    public static Message readMessage(ActiveUser activeUser){
        return null;
    }
}
