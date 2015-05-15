package message.exceptions;

import message3.types.EncryptedMessage;
import user.User;

import java.io.IOException;

/**
 * Created by tochur on 30.04.15.
 */
public class UnableToSendMessage extends IOException{
    UnableToSendMessage(User user, EncryptedMessage encryptedMessage){
        super();
    }
}
