package model.containers.temporary;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import controller.responders.exceptions.ToMuchUsersInThisRoom;
import message.generators.Conversationalist_Disconnected;
import model.ChatRoom;
import server.sender.MessageSender;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

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
     * @param userID
     * @return
     */
    public synchronized boolean isFree(Integer userID) {
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
        rooms.remove(authorID);

        if( rooms.isUserInRoom(authorID)){
            ChatRoom chatRoom = rooms.getUserRoom(authorID);
            rooms.remove(authorID);

            return chatRoom.getParticipantsIDs();
        }
        return null;
    }


    /**
     * Method, that removes user from room, and returns Collection of ID's of users from room.
     * @return - collection of users ID that are still in room
     */
    public void leaveRoom(Collection<Integer> usersIDs) {
        for(Integer userID: usersIDs){
            if( rooms.isUserInRoom(userID)){
                ChatRoom chatRoom = rooms.getUserRoom(userID);
                rooms.remove(userID);
            }
        }
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
                messageSender.send(conversationalist_disconnected.message(id, authorNick));
            } catch (Exception e) {
               //Nice try but nothing can be made.
            }
        }
    }
}
