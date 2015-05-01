package responders.exceptions;

import message.types.EncryptedMessage;
import message.utils.MessageSender;
import user.ActiveUser;

import java.io.IOException;

/**
 * Created by tochur on 24.04.15.
 */
public class IncorrectUserStateException extends Exception {
    protected ActiveUser activeUser;
    protected EncryptedMessage message;
    protected int errorType;
    protected String messageToUser;

    /**
     * Exception that is caused by operation that is not permitted in current userState
     * @param activeUser - user that send message but message caused exception
     * @param message - message received from sender
     * @param messageToUser - message that inform user what happened
     */
    public IncorrectUserStateException(ActiveUser activeUser, EncryptedMessage message, int errorType, String messageToUser){
        this.activeUser = activeUser;
        this.message = message;
        this.errorType = errorType;
        this.messageToUser = messageToUser;
    }

    public IncorrectUserStateException(){}

    public ActiveUser getActiveUser(){
        return activeUser;
    }

    public String getMessage(){
        return messageToUser;
    }
}
