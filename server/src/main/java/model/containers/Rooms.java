package model.containers;

import model.ChatRoom;
import model.exceptions.ElementNotFoundException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tochur on 17.05.15.
 */
public class Rooms {
    /*List of all logged capable to interact*/
    private Map<Integer, ChatRoom> logged = new HashMap<>();


    /**
     *
     * @param ID
     * @param room
     */
    public void addNew(Integer ID, ChatRoom room){
        logged.put(ID, room);
    }

    /**
     * Removes user from ActiveUsers
     * @param ID
     * @throws model.exceptions.ElementNotFoundException
     */
    public void remove (Integer ID) throws ElementNotFoundException {
        if ( logged.remove(ID) == null )
            throw new ElementNotFoundException();
    }

    public Map<Integer, ChatRoom> getMap() { return Collections.unmodifiableMap(logged); }

}
