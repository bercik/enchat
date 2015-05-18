package model.containers.temporary;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import message.generators.Conversationalist_Disconnected;
import model.ChatRoom;
import server.sender.MessageSender;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by tochur on 17.05.15.
 */
public class RoomManager {
    private Map<Integer, ChatRoom> IDRooms;

    @Inject
    public RoomManager(@Named("IDAccounts") Map<Integer, ChatRoom> IDRooms) {
        this.IDRooms = IDRooms;
    }


    public boolean isFree(Integer otherUserID) {
        Boolean inRoom = IDRooms.containsKey(otherUserID);
        if (!inRoom)
            return true;
        return false;
    }

    public void startConversation(Integer authorID, Integer otherUserID) {
        //creating Room
        ChatRoom chatRoom = new ChatRoom(authorID, otherUserID);
        //Creating connection usersToRoom
            //Adding to base
        IDRooms.put(authorID, chatRoom);
        IDRooms.put(otherUserID, chatRoom);
            //
    }

    /**
     * Method, that removes user from room, and returns Collection of ID's of users from room.
     * @param authorID
     * @return
     */
    public Collection<Integer> leaveRoom(Integer authorID) {
        ChatRoom chatRoom = IDRooms.get(authorID);
        IDRooms.remove(authorID);

        return chatRoom.getParticipantsIDs();
    }

    /**
     * Returns all users in room without user with ID passed as parameter
     * @param ID
     * @return
     */
    public Collection<Integer> getConversationalists(Integer ID) {
        ChatRoom chatRoom = IDRooms.get(ID);
        //Not allowed in Java 1.7 Collection<Integer> conversationalists = chatRoom.getParticipantsIDs().stream().filter(id -> id != ID).collect(Collectors.toSet());
        Collection<Integer> conversationalists = new HashSet<>();
        for(Integer i: chatRoom.getParticipantsIDs()){
            if(i != ID)
                conversationalists.add(i);
        }

        return chatRoom.getParticipantsIDs();
    }

    public void leaveRoomAndTryToInform(MessageSender messageSender, Integer authorID, String authorNick,  Conversationalist_Disconnected conversationalist_disconnected) {
        Collection<Integer> toInform = getConversationalists(authorID);
        for(Integer id: toInform){
            try {
                messageSender.send(conversationalist_disconnected.message(id, authorNick));
            } catch (Exception e) {
               //Nice try but nothing can be made.
            }
        }
    }
}
