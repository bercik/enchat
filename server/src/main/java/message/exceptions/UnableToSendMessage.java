package message.exceptions;

import message.types.EncryptedMessage;
import user.ActiveUser;

import java.io.IOException;

/**
 * Created by tochur on 30.04.15.
 */
public class UnableToSendMessage extends IOException{
    UnableToSendMessage(ActiveUser activeUser, EncryptedMessage encryptedMessage){
        super();
    }
}
