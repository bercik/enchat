package model.containers.temporary;

import com.google.inject.Singleton;
import model.ChatRoom;
import model.exceptions.ElementNotFoundException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tochur on 17.05.15.
 */
@Singleton
public class Rooms {
    /*Maps the users IDs to rooms that they are connected to.*/
    private Map<Integer, ChatRoom> rooms = new HashMap<>();

    /**
     * Adds new association (user - room)
     * @param ID
     * @param room
     */
    public void addNew(Integer ID, ChatRoom room){
        rooms.put(ID, room);
    }

    /**
     * Removes user from ActiveUsers
     * @param ID
     * @throws model.exceptions.ElementNotFoundException
     */
    public void remove (Integer ID) throws ElementNotFoundException {
        if ( rooms.remove(ID) == null )
            throw new ElementNotFoundException();
    }

    /*Checks weather user is in room - during conversation. */
    public boolean isUserInRoom(Integer userID){
        return rooms.containsKey(userID);
    }

    /**
     * Returns the room that is associated with user with specified id
     * @param userID - value that identifies user in system.
     * @return - if user with userID(param) is in room returns room otherwise null.
     */
    public ChatRoom getUserRoom(Integer userID){
        return rooms.get(userID);
    }

    public Map<Integer, ChatRoom> getMap() { return Collections.unmodifiableMap(rooms); }

}
