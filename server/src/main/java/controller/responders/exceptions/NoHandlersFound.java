package controller.responders.exceptions;

import messages.MessageId;

/**
 * @author Created by tochur on 19.04.15.
 */
public class NoHandlersFound extends Throwable {
    public NoHandlersFound(MessageId messageId) {
        super("System does not support reaction for message with id: " + messageId);
    }
}
