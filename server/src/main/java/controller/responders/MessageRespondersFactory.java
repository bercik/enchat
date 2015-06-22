package controller.responders;

import com.google.inject.Injector;
import com.google.inject.Singleton;
import controller.responders.impl.*;
import controller.responders.impl.lists.AddToBlackList;
import controller.responders.impl.lists.BlackList;
import controller.responders.impl.lists.ClientList;
import controller.responders.impl.lists.RemoveFromBlackList;
import messages.MessageId;

/**
 * Factory of messages responders.
 *
 * @author Created by tochur on 14.05.15.
 */
@Singleton
public class MessageRespondersFactory {

    /**
     * Creates the Responder Object to handle new Message, read from input stream.
     * @param messageId MessageId, id of the incoming message.
     * @param injector Inject, Guice object to create Responder.
     * @return IMessageResponder, suitable Responder object.
     */
    public IMessageResponder create(MessageId messageId, Injector injector){

        switch (messageId){
            case LOG_IN:
                return injector.getInstance(LogIn.class);
            case SIGN_UP:
                return injector.getInstance(SignUp.class);
            case CONVERSATION_REQUEST:
                return injector.getInstance(ConversationRequest.class);
            case CLIENT_MESSAGE:
                return injector.getInstance(UserMessage.class);
            case CONVERSATIONALIST_DISCONNECTED:
                return injector.getInstance(EndConversation.class);
            case CLIENTS_LIST:
                return injector.getInstance(ClientList.class);
            case BLACK_LIST:
                return injector.getInstance(BlackList.class);
            case ADD_TO_BLACK_LIST:
                return injector.getInstance(AddToBlackList.class);
            case REMOVE_FROM_BLACK_LIST:
                return injector.getInstance(RemoveFromBlackList.class);
            case DISCONNECT:
                return injector.getInstance(Disconnect.class);
            case LOGOUT:
                return injector.getInstance(LogOut.class);
            case END_TALK:
                return injector.getInstance(EndConversation.class);
        }
        return null;
    }
}
