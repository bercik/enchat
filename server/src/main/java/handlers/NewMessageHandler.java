package handlers;

import message3.generators.Messages;
import message3.types.EncryptedMessage;
import messages.MessageId;
import responders.*;
import responders.exceptions.*;
import responders.implementations.*;
import responders.implementations.lists.AddToBlackListMessageHandler;
import responders.implementations.lists.BlackListMessageHandler;
import responders.implementations.lists.ClientListMessageHandler;
import responders.implementations.lists.RemoveFromBlackListMessageHandler;
import rsa.exceptions.DecryptingException;
import rsa.exceptions.EncryptionException;
import user.User;

import java.io.IOException;

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
        } catch (EncryptionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private IMessageHandler getMessageResponder(MessageId messageId) throws NoHandlersFound {
        Messages messages = new Messages();
        switch (messageId){
            case JUNK:
                return new JunkMessageHandler();
            case LOG_IN:
                return new LogInMessageHandler(user, message, messages);
            case SIGN_UP:
                return new SignUpMessageHandler(user, message, messages);
            case CONVERSATION_REQUEST:
                return new ConversationRequestMessageHandler(user, message, messages);
            case CLIENT_MESSAGE:
                return new ClientMessageHandler(user, message, messages);
            case CONVERSATIONALIST_DISCONNECTED:
                return new ConversationalistDisconnectedMessageHandler(user, message, messages);
            case CLIENTS_LIST:
                return new ClientListMessageHandler(user, message, messages);
            case BLACK_LIST:
                return new BlackListMessageHandler(user, message, messages);
            case ADD_TO_BLACK_LIST:
                return new AddToBlackListMessageHandler(user, message, messages);
            case REMOVE_FROM_BLACK_LIST:
                return new RemoveFromBlackListMessageHandler(user, message, messages);
            case DISCONNECT:
                return new DisconnectMessageHandler(user, message, messages);
        }
        throw new NoHandlersFound(messageId);
    }

}
