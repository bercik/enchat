package model.containers.temporary;

import com.google.inject.Singleton;
import controller.responders.exceptions.ToMuchUsersInThisRoom;
import model.ChatRoom;
import model.exceptions.ElementNotFoundException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Thread safety
 *
 * This singleton object holds info about association between users and ChatRooms.
 * Exactly maps all users that are in a ChatRooms to their ChatRooms.
 * This class takes the responsibility for removing the empty ChatRooms.
 * In fact is happens automatically, when all associations between users
 * and specified ChatRoom will be removed.
 *
 * Note that if in one ChatRoom is N users, N mappings <UserId><ChatRoom>
 * will be hold in the collection.
 *
 * This class do not modify the ChatRooms operates only on associations between users and ChatRooms.
 *
 * Created by tochur on 17.05.15.
 */
@Singleton
public class Rooms {
    /*Maps the users IDs to rooms that they are connected to.*/
    private Map<Integer, ChatRoom> rooms = new HashMap<>();

    /**
     * Adds new association (userID ChatRoom)
     * That won't modify the state of the ChatRoom.
     * @param ID - id of the user that will be associated with ChatRoom
     * @param room - with this ChatRoom user will be associated
     */
    public synchronized void addNew(Integer ID, ChatRoom room){
        rooms.put(ID, room);
    }

    /**
     * Removes association between user with specified ID and his ChatRoom.
     * Function wont modify the sate of ChatRoom,
     * but returns the reference to the left ChatRoom.
     * @param ID - ID of the user that want to leave the room
     * @return - ChatRoom that was left
     * @throws ElementNotFoundException - when user is not associated with any ChatRoom.
     */
    public synchronized ChatRoom remove(Integer ID) throws ElementNotFoundException {
        ChatRoom chatRoom = rooms.remove(ID);
        if(chatRoom != null)
            return chatRoom;
        throw new ElementNotFoundException();
    }

    /**
     * Returns the ChatRoom that is associated with user with specified id
     * @param userID - value that identifies user in system.
     * @return - if user with userID(param) is in room returns room otherwise null.
     */
    public synchronized ChatRoom getUserRoom(Integer userID){
        return rooms.get(userID);
    }

    /**
     * //TODO this is not thread safety (we should check weather user didn't logout in the meaning time.
     * Method that lets to create new Conversation it is thread safety.
     * @param authorID - id of the user that requested for chat
     * @param otherUserID - id of the requested conversationalist.
     * @throws ToMuchUsersInThisRoom - when
     */
    public synchronized void createConversation(Integer authorID, Integer otherUserID) throws ToMuchUsersInThisRoom {
        if(isUserInRoom(otherUserID) == true)
            throw new ToMuchUsersInThisRoom();
        //New ChatRoom creation.
        ChatRoom chatRoom = new ChatRoom(authorID, otherUserID);
        //Update of the Rooms collection
        rooms.put(authorID, chatRoom);
        rooms.put(otherUserID, chatRoom);
    }

    /**
     * Checks weather user is in room
     * @param userID - id of user to check
     * @return true when user is assigned to any ChatRoom, otherwise false
     */
    public boolean isUserInRoom(Integer userID){
        return rooms.containsKey(userID);
    }
}
