package model;


import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Thread safety.
 * This class is used to store (temporary) information about users that
 * are having a chat.
 *
 * In this version only 2 persons may be in room. In future it will be extended.
 *
 *
 * Created by tochur on 30.04.15.
 */
public class ChatRoom {
    private Set<Integer> participantsIDs = new HashSet<>();

    /**
     * Creates new room with 2 users inside.
     * @param userID - ID of first user that will be assigned to this room
     * @param otherUserID - ID of second user that will be assigned to this room
     */
    public ChatRoom(Integer userID, Integer otherUserID){
        participantsIDs.add(userID);
        participantsIDs.add(otherUserID);
    }

    /**
     * Removes the user from room.
     * @param id - id of the user that want to leave the room
     * @return - Unmodifiable usersID collection that are still in room.
     * or null when he was the only one in room, or just user was not assigned to any room.
     */
    public synchronized Collection<Integer> remove(Integer id){
        for(Integer integer : participantsIDs){
            if (integer == id) {
                participantsIDs.remove(id);
                if(participantsIDs.size() > 0)
                    return Collections.unmodifiableCollection(participantsIDs);
            }
        }
        //Not in collection
        return null;
    }

    /**
     * Adds new user to room
     * @param userID - id of the user that will be added to room.
     *
     * !!!This method is not used in this server version, cause max 2 user
     *         may be in room. And when one user leaves the room another
     *     is also automatically removed, but for future version is will be useful !!!
     */
    public synchronized void addUser(Integer userID){
        participantsIDs.add(userID);
    }


    /**
     * Returns an readOnly collection of userIDs that are in this room.
     * @return - readOnly collection of userIDs in Room.
     */
    public synchronized Collection<Integer> getParticipantsIDs(){
        return Collections.unmodifiableCollection(participantsIDs);
    }

    /**
     * How many users take part in conversation (are in ChatRoom)
     * @return - amount of users.
     */
    public synchronized int getParticipantsAmount(){
        return participantsIDs.size();
    }
}
