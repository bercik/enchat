package model.user;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import messages.MessageId;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Util for verifying user State (weather he can made specified action - requested by the message).
 *
 * @author Created by tochur on 16.05.15.
 */
@Singleton
public class Verifier {
    private Map<MessageId, Set<UserState>> permissions;

    /**
     * Creates an object that defines what is allowed in which state.
     */
    @Inject
    public Verifier(){
        permissions = new HashMap<>();

        permissions.put(MessageId.JUNK, atLeastConnected());
        permissions.put(MessageId.LOG_IN, atLeastConnected());
        permissions.put(MessageId.SIGN_UP, atLeastConnected());
        permissions.put(MessageId.ADD_TO_BLACK_LIST, atLeastLogged());
        permissions.put(MessageId.BLACK_LIST, atLeastLogged());
        permissions.put(MessageId.REMOVE_FROM_BLACK_LIST, atLeastLogged());
        permissions.put(MessageId.CLIENT_MESSAGE, inRoom());
        permissions.put(MessageId.CONVERSATION_REQUEST, atLeastLogged());
        permissions.put(MessageId.DISCONNECT, atLeastConnected());
        permissions.put(MessageId.CLIENTS_LIST, atLeastConnected());
        permissions.put(MessageId.LOGOUT, atLeastLogged());
        permissions.put(MessageId.CONVERSATIONALIST_DISCONNECTED, inRoom());
        //Ryniak patch begin
        permissions.put(MessageId.END_TALK, inRoom());
        //Ryniak patch end
    }

    /**
     * Checks weather userState allows to send the message.
     * @param state UserState, current user State.
     * @param messageId MessageId specifies the type of the message.
     * @return boolean, true when action is allowed, false otherwise.
     */
    public boolean verify(UserState state, MessageId messageId){
        return permissions.get(messageId).contains(state);
    }

    private Set<UserState> atLeastConnected(){
        Set<UserState> atLeastConnected = atLeastLogged();
        atLeastConnected.add(UserState.CONNECTED_TO_SERVER);

        return atLeastConnected;
    }

    private Set<UserState> atLeastLogged(){
        Set<UserState> atLeastLogged = inRoom();
        atLeastLogged.add(UserState.LOGGED);

        return atLeastLogged;
    }

    private Set<UserState> inRoom(){
        Set<UserState> inRoom = new HashSet<UserState>();
        inRoom.add(UserState.IN_ROOM);

        return inRoom;
    }

}



