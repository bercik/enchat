package responders.exceptions;

import message.types.EncryptedMessage;
import user.User;

/**
 * Created by tochur on 24.04.15.
 */
public class IncorrectUserStateException extends Exception {
    protected User user;
    protected EncryptedMessage message;
    protected int errorType;
    protected String messageToUser;

    /**
     * Exception that is caused by operation that is not permitted in current userState
     * @param user - user that send message but message caused exception
     * @param message - message received from sender
     * @param messageToUser - message that inform user what happened
     */
    public IncorrectUserStateException(User user, EncryptedMessage message, int errorType, String messageToUser){
        this.user = user;
        this.message = message;
        this.errorType = errorType;
        this.messageToUser = messageToUser;
    }

    public IncorrectUserStateException(){}

    public User getUser(){
        return user;
    }

    public String getMessage(){
        return messageToUser;
    }
}
