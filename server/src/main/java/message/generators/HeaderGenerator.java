package message.generators;

import message.types.Header;
import messages.MessageId;

/**
 * Created by tochur on 03.05.15.
 *
 * Util to create MessageHeaders.
 * It can be accessed only by classes from the same package.
 * So after system testing it will be fully safe.
 * During developing of the application it can
 *      throw IncorrectMessageId (Runtime Exception)
 *  when that happen, cheek weather all function from package
 *  message.generators are correctly formed, and weather class
 *      shared.messages.MessageId defines correct values corresponding
 * to headers.
 */

class HeaderGenerator {

    /**
     * Create Header with specified errorId
     * @param messageId - MessageId - specified in wrapping class
     * @param errorId - messageErrorToCreate
     * @param packageAmount - amount of packages passed in message
     * @return - Header with MessageId.REMOVE_FROM_BLACK_LIST
     */
    protected static Header createHeader(MessageId messageId, int errorId, int packageAmount){
        MessageId.ErrorId error;
        error = messageId.createErrorId(errorId);

        return new Header(messageId, error, packageAmount);
    }

    /**
     * Create Header with specified errorId and 0 packageAmount
     * @param messageId - MessageId - specified in wrapping class
     * @param errorId - messageErrorToCreate
     * @return - Header with MessageId.REMOVE_FROM_BLACK_LIST
     */
    protected static Header createHeader(MessageId messageId, int errorId){
        return createHeader(messageId, errorId, 0);
    }
}
