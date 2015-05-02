package handlers;

import message.types.EncryptedMessage;
import messages.MessageId;
import responders.*;
import responders.exceptions.*;
import responders.implementations.*;
import responders.implementations.lists.AddToBlackListMessageHandler;
import responders.implementations.lists.BlackListMessageHandler;
import rsa.exceptions.DecryptingException;
import user.User;

/**
 * Created by tochur on 17.04.15.
 */
/*When this thread is created lock fot stream should by installed.*/
public class NewMessageHandler implements Runnable {
    User user;
    EncryptedMessage message;

    public NewMessageHandler(User user, EncryptedMessage message){
        this.user = user;
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
                return new LogInMessageHandler(user, message);
            case SIGN_UP:
                return new SignUpMessageHandler(user, message);
            case CONVERSATION_REQUEST:
                return new ConversationRequestMessageHandler(user, message);
            case CLIENT_MESSAGE:
                return new ClientMessageHandler(user, message);
            case CONVERSATIONALIST_DISCONNECTED:
                return new ConversationalistDisconectedMessageHandler();
            case CLIENTS_LIST:
                return new ClientListMessageHandler(user, message);
            case BLACK_LIST:
                return new BlackListMessageHandler(user, message);
            case ADD_TO_BLACK_LIST:
                return new AddToBlackListMessageHandler(user, message);
            case REMOVE_FROM_BLACK_LIST:
                return new RemoveFromBlackListMessageHandler(user, message);
            case DISCONNECT:
                return new DisconnectMessageHandler(user, message);
        }
        throw new NoHandlersFound(messageId);
    }

}
