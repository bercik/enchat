package handlers;

import message.types.EncryptedMessage;
import messages.MessageId;
import responders.*;
import responders.logging.LogInMessageHandler;
import responders.logging.PublicKeyResponser;
import responders.registration.SignUpMessageHandler;
import user.ActiveUser;

/**
 * Created by tochur on 17.04.15.
 */
/*When this thread is created lock fot stream should by installed.*/
public class NewMessageHandler implements Runnable {
    ActiveUser activeUser;
    EncryptedMessage message;

    public NewMessageHandler(ActiveUser activeUser, EncryptedMessage message){
        this.activeUser = activeUser;
        this.message = message;
    }

    public void run() {
        MessageHandler messageResponder = null;
        try {
            messageResponder = getMessageResponder(message.getId());
            messageResponder.handle(activeUser, message);
        } catch (NoHandlersFound noHandlersFound) {
            noHandlersFound.printStackTrace();
        }

    }

    private MessageHandler getMessageResponder(MessageId messageId) throws NoHandlersFound {
        switch (messageId){
            case JUNK:
                return new JunkMessageHandler();
            case LOG_IN:
                return new LogInMessageHandler();
            case SIGN_UP:
                return new SignUpMessageHandler();
            case CONVERSATION_REQUEST:
                return new ConversationRequestMessageHandler();
            case INCOMING_CONVERSATION:
                return new IncomingConversationMessageHandler();
            case CLIENT_MESSAGE:
                return new ClientMessageMessageHandler();
            case SERVER_MESSAGE:
                return new ServerMessageMessageHandler();
            case CONVERSATIONALIST_DISCONNECTED:
                return new ConversationalistDisconectedMessageHandler();
            case CLIENTS_LIST:
                return new ClientListMessageHandler();
            case BLACK_LIST:
                return new BlackListMessageHandler();
            case ADD_TO_BLACK_LIST:
                return new AddToBlackListMessageHandler();
            case REMOVE_FROM_BLACK_LIST:
                return new RemoveFromBlackListMessageHandler();
            case DISCONNECT:
                return new DisconnectMessageHandler();
            case PUBLIC_KEY:
                return new PublicKeyResponser();
        }
        throw new NoHandlersFound(messageId);
    }

}
