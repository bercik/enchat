package handlers;

import message.types.EncryptedMessage;
import messages.MessageId;
import responders.*;
import responders.exceptions.*;
import responders.implementations.*;
import responders.implementations.lists.AddToBlackListMessageHandler;
import responders.implementations.lists.BlackListMessageHandler;
import rsa.exceptions.DecryptingException;
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
        IMessageHandler messageResponder;
        try {
            messageResponder = getMessageResponder(message.getId());
            messageResponder.handle();
        } catch (NoHandlersFound noHandlersFound) {
            noHandlersFound.printStackTrace();
        } catch (IncorrectUserStateException e) {
            e.printStackTrace();
        } catch (ReactionException e) {
            e.printStackTrace();
        } catch (DecryptingException e) {
            e.printStackTrace();
        }

    }

    private IMessageHandler getMessageResponder(MessageId messageId) throws NoHandlersFound {
        switch (messageId){
            case JUNK:
                return new JunkMessageHandler();
            case LOG_IN:
                return new LogInMessageHandler(activeUser, message);
            case SIGN_UP:
                return new SignUpMessageHandler(activeUser, message);
            case CONVERSATION_REQUEST:
                return new ConversationRequestMessageHandler(activeUser, message);
            case CLIENT_MESSAGE:
                return new ClientMessageMessageHandler();
            case SERVER_MESSAGE:
                return new ServerMessageMessageHandler();
            case CONVERSATIONALIST_DISCONNECTED:
                return new ConversationalistDisconectedMessageHandler();
            case CLIENTS_LIST:
                return new ClientListMessageHandler();
            case BLACK_LIST:
                return new BlackListMessageHandler(activeUser, message);
            case ADD_TO_BLACK_LIST:
                return new AddToBlackListMessageHandler(activeUser, message);
            case REMOVE_FROM_BLACK_LIST:
                return new RemoveFromBlackListMessageHandler();
            case DISCONNECT:
                return new DisconnectMessageHandler(activeUser, message);
        }
        throw new NoHandlersFound(messageId);
    }

}
