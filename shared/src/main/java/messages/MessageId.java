package messages;

/**
 * Created by tochur on 17.04.15.
 */
public enum MessageId {
    JUNK(0), LOG_IN(1), SIGN_UP(2), CONVERSATION_REQUEST(3), INCOMING_CONVERSATION(4),
    CLIENT_MESSAGE(5), SERVER_MESSAGE(6), CONVERSATIONALIST_DISCONNECTED(7), CLIENTS_LIST(8),
    BLACK_LIST(9), ADD_TO_BLACK_LIST(10), REMOVE_FROM_BLACK_LIST(11), DISCONNECT(12), PUBLIC_KEY(13)  ;

    private int id;

    private MessageId(int id) {
        this.id = id;
    }

    public static MessageId createMessageId(int id) throws IncorrectMessageId {
        for(MessageId messageId: MessageId.values()){
            if (messageId.getIntRepresentation() == id)
                return messageId;
        }
        throw new IncorrectMessageId("Incorrect message id !!! Was: " + id + " expected: [0 - 12]");
    }

    public int getIntRepresentation(){
        return id;
    }
}
