package message.generarators;

import message.types.Header;
import messages.IncorrectMessageId;
import messages.MessageId;
import responders.exceptions.ReactionException;

/**
 * Created by tochur on 03.05.15.
 */
public class HeaderGenerator {

    /**
     * Create Header with specified errorId
     * @param errorId - messageErrorToCreate
     * @param packageAmount - amount of packages passed in message
     * @return - Header with MessageId.REMOVE_FROM_BLACK_LIST
     * @throws  ReactionException - when no errorId is associated with int (errorId)
     *          only for developing purpose
     */
    protected static Header createHeader(MessageId messageId, int errorId, int packageAmount) throws ReactionException {
        MessageId.ErrorId error;
        try{
            error = messageId.createErrorId(errorId);
        }catch (IncorrectMessageId e){
            System.out.println("Error inside Messages, wrong error number, REPAIR THAT.");
            throw new ReactionException();
        }

        return new Header(messageId, error, packageAmount);
    }

    /**
     * Create Header with specified errorId and 0 packageAmount
     * @param errorId - messageErrorToCreate
     * @return - Header with MessageId.REMOVE_FROM_BLACK_LIST
     * @throws  ReactionException - when no errorId is associated with int (errorId)
     *          only for developing purpose
     */
    protected static Header createHeader(MessageId messageId, int errorId) throws ReactionException {
        return createHeader(messageId, errorId, 0);
    }
}
