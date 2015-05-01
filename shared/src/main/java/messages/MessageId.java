package messages;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;

/**
 * Created by tochur on 17.04.15. Modified by robert on 29.04.15.
 * Official version verified by (tochur) on 30.04.15
 */
public enum MessageId
{
    JUNK(0),
    LOG_IN(1, ErrorId.BAD_LOGIN_OR_PASSWORD, ErrorId.TOO_MUCH_USERS_LOGGED),
    SIGN_UP(2, ErrorId.BUSY_LOGIN, ErrorId.INCORRECT_LOGIN, 
            ErrorId.BAD_PASSWORD_LENGTH),
    CONVERSATION_REQUEST(3, ErrorId.USER_NOT_LOGGED, ErrorId.BUSY_USER,
            ErrorId.CONVERSATION_WITH_ANOTHER_USER, ErrorId.ON_BLACK_LIST),
    INCOMING_CONVERSATION(4, ErrorId.IM_BUSY),
    CLIENT_MESSAGE(5, ErrorId.FAILED),
    SERVER_MESSAGE(6),
    CONVERSATIONALIST_DISCONNECTED(7),
    CLIENTS_LIST(8),
    BLACK_LIST(9),
    ADD_TO_BLACK_LIST(10, ErrorId.USER_DOESNT_EXIST,
            ErrorId.TOO_MUCH_USERS_ON_BLACKLIST, ErrorId.ALREADY_ADDED),
    REMOVE_FROM_BLACK_LIST(11, ErrorId.USER_NOT_ON_BLACKLIST),
    DISCONNECT(12),
    PUBLIC_KEY(13),
    SERVER_ERROR(14, ErrorId.MESSAGE_DECRYPTING_FAILED, ErrorId.MESSAGE_ENCRYPTING_FAILED);

    private int id;
    private EnumSet<ErrorId> errorIds;

    private MessageId(int id, ErrorId... eerrorIds){
        this.id = id;

        errorIds = EnumSet.noneOf(ErrorId.class); // make an empty enumset
        errorIds.add(ErrorId.OK); // always contains OK error
        errorIds.addAll(Arrays.asList(eerrorIds)); // add varargs to it
    }

    public static enum ErrorId{
        OK(0),
        BAD_LOGIN_OR_PASSWORD(1),
        TOO_MUCH_USERS_LOGGED(2),
        BUSY_LOGIN(1),
        INCORRECT_LOGIN(2),
        BAD_PASSWORD_LENGTH(3),
        USER_NOT_LOGGED(1),
        BUSY_USER(2),
        CONVERSATION_WITH_ANOTHER_USER(3),
        ON_BLACK_LIST(4),
        IM_BUSY(1),
        FAILED(1),
        USER_DOESNT_EXIST(1),
        TOO_MUCH_USERS_ON_BLACKLIST(2),
        ALREADY_ADDED(3),
        USER_NOT_ON_BLACKLIST(1),
        MESSAGE_DECRYPTING_FAILED(1),
        MESSAGE_ENCRYPTING_FAILED(2);

        private int id;

        private ErrorId(int iid)
        {
            id = iid;
        }

        public int getIntRepresentation()
        {
            return id;
        }
    }

    public ErrorId createErrorId(int id) throws IncorrectMessageId{
        int max = Integer.MIN_VALUE;

        for (ErrorId errorId : errorIds){
            if (errorId.getIntRepresentation() > max){
                max = errorId.getIntRepresentation();
            }

            if (errorId.getIntRepresentation() == id){
                return errorId;
            }
        }
        throw new IncorrectMessageId("Incorrect error id for "
                + getClass().getSimpleName() + " !!!\nWas: " + id
                + " expected: [0 - " + Integer.toString(max) + "]");
    }

    public static MessageId createMessageId(int id) throws IncorrectMessageId{
        for (MessageId messageId : MessageId.values()){
            if (messageId.getIntRepresentation() == id){
                return messageId;
            }
        }
        throw new IncorrectMessageId("Incorrect message id !!! Was: " + id
                + " expected: [0 - 13]");
    }

    public int getIntRepresentation()
    {
        return id;
    }
}
