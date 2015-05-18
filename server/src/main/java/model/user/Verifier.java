package model.user;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import messages.MessageId;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by tochur on 16.05.15.
 */
@Singleton
public class Verifier {
    private Map<MessageId, Set<UserState>> permissions;

    @Inject
    public Verifier(){
        permissions = new HashMap<>();

        permissions.put(MessageId.JUNK, atLeastConnected());
        permissions.put(MessageId.LOG_IN, atLeastConnected());
        permissions.put(MessageId.SIGN_UP, atLeastConnected());
        permissions.put(MessageId.ADD_TO_BLACK_LIST, atLeastLogged());
        permissions.put(MessageId.BLACK_LIST, atLeastLogged());
        permissions.put(MessageId.REMOVE_FROM_BLACK_LIST, atLeastLogged());
        permissions.put(MessageId.CLIENT_MESSAGE, InRoom());
        permissions.put(MessageId.CONVERSATION_REQUEST, atLeastLogged());
        permissions.put(MessageId.DISCONNECT, atLeastConnected());
        permissions.put(MessageId.CLIENTS_LIST, atLeastConnected());
        permissions.put(MessageId.LOGOUT, atLeastLogged());
        permissions.put(MessageId.CONVERSATIONALIST_DISCONNECTED, InRoom());
    }

    public boolean verify(UserState state, MessageId messageId){
        return permissions.get(messageId).contains(state);
    }

    public Set<UserState> atLeastConnected(){
        Set<UserState> atLeastConnected = atLeastLogged();
        atLeastConnected.add(UserState.CONNECTED_TO_SERVER);

        return atLeastConnected;
    }

    public Set<UserState> atLeastLogged(){
        Set<UserState> atLeastLogged = InRoom();
        atLeastLogged.add(UserState.LOGGED);

        return atLeastLogged;
    }

    public Set<UserState> InRoom(){
        Set<UserState> inRoom = new HashSet<UserState>();
        inRoom.add(UserState.IN_ROOM);

        return inRoom;
    }

}



