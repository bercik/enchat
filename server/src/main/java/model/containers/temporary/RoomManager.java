package model.containers.temporary;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import controller.responders.exceptions.ToMuchUsersInThisRoom;
import message.generators.Conversationalist_Disconnected;
import model.ChatRoom;
import model.exceptions.ElementNotFoundException;
import server.sender.MessageSender;

import java.util.*;

/**
 * Singleton object.
 * Manages Rooms.
 * Interface for chats managing.
 *
 * Created by tochur on 17.05.15.
 */
@Singleton
public class RoomManager {
    private Rooms rooms;

    /**
     * Creates new Room Manager this object should not be created with constructor (from code),
     * since is a Singleton object guaranteed by "Guice" framework.
     * @param rooms - collection of rooms.
     */
    @Inject
    public RoomManager(Rooms rooms) {
        this.rooms = rooms;
    }


    /**
     * Checks weather user with ID is free (not in room)
     * //TODO it's not thread safety. when you use this info outside synchronized
     */
    /*public synchronized boolean isFree(Integer userID) {
        return rooms.isUserInRoom(userID);
    }*/

    /**
     * Tries to Start the conversation between users with specified ids.
     * If the person is engaged it throws exception.
     * @param authorID - the user that request the conversation
     * @param otherUserID - user that is demanded by author.
     * @throws ToMuchUsersInThisRoom - when other user is talking currently
     */
    public void startConversation(Integer authorID, Integer otherUserID) throws ToMuchUsersInThisRoom {
        rooms.createConversation(authorID, otherUserID);
    }

    /**
     * Method, that removes user from room, and returns Collection of ID's of users from room.
     * Removes also the association of user and his room.
     * If in room only one user stayed he is also removed from room
     * @param authorID - the user that leaves the room
     * @return - collection of users ID that are still in room
     * @throws ElementNotFoundException - when no association with ChatRoom was found
     */
    public synchronized Collection<Integer> leaveRoom(Integer authorID) throws ElementNotFoundException{
        ChatRoom chatRoom = rooms.remove(authorID);
        chatRoom.remove(authorID);
        Collection<Integer> others = chatRoom.getParticipantsIDs();
        if(others.size() == 1){
            for(Integer otherId: others){
                Collection<Integer> toInform = Arrays.asList(new Integer[] {otherId});
                chatRoom = rooms.remove(otherId);
                chatRoom.remove(otherId);
                return toInform;
            }
        }
        return others;
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

        return conversationalists;
    }

    public void leaveRoomAndTryToInform(MessageSender messageSender, Integer authorID, String authorNick,  Conversationalist_Disconnected conversationalist_disconnected) {
        Collection<Integer> toInform = getConversationalists(authorID);
        for(Integer id: toInform){
            try {
                messageSender.send(conversationalist_disconnected.message(id));
            } catch (Exception e) {
               //Nice try but nothing can be made.
            }
        }
    }
}
