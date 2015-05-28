package model.containers.temporary;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import controller.responders.exceptions.ToMuchUsersInThisRoom;
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
    private Rooms rooms;

    @Inject
    public RoomManager(Rooms rooms) {
        this.rooms = rooms;
    }


    /*Checks weather user with ID is free (not in room) */
    public boolean isFree(Integer userID) {
        return rooms.isUserInRoom(userID);
    }

    /**
     * Tries to Start the conversation between users with specified ids.
     * If the person is engaged it throws exception.
     * @param authorID - the user that request the conversation
     * @param otherUserID - user that is demanding by author.
     * @throws ToMuchUsersInThisRoom - when other user is talking currently
     */
    public void startConversation(Integer authorID, Integer otherUserID) throws ToMuchUsersInThisRoom {
        if(rooms.isUserInRoom(otherUserID)) {
            throw new ToMuchUsersInThisRoom();
        }
        //creating Room
        ChatRoom chatRoom = new ChatRoom(authorID, otherUserID);
        //Creating connection usersToRoom - Adding to base
        rooms.addNew(authorID, chatRoom);
        rooms.addNew(otherUserID, chatRoom);
    }

    /**
     * Method, that removes user from room, and returns Collection of ID's of users from room.
     * @param authorID - the user that leaves the room
     * @return - collection of users ID that are still in room
     */
    public Collection<Integer> leaveRoom(Integer authorID) {
        if( rooms.isUserInRoom(authorID)){
            ChatRoom chatRoom = rooms.getUserRoom(authorID);
            rooms.remove(authorID);

            return chatRoom.getParticipantsIDs();
        }
        return null;
    }

    /**
     * Returns all users in room without user with ID passed as parameter
     * @param ID - id of user how won't be int returned collection
     * @return - collection of users ids in room associated with ID(param)
     */
    public Collection<Integer> getConversationalists(Integer ID) {
        ChatRoom chatRoom = rooms.getUserRoom(ID);
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
