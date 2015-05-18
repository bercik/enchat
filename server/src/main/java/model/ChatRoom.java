package model;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tochur on 30.04.15.
 */
public class ChatRoom {
    private Set<Integer> participantsIDs = new HashSet<>();

    public ChatRoom(Integer authorID, Integer otherUserID){
        participantsIDs.add(authorID);
        participantsIDs.add(otherUserID);
    }

    /**
     *
     * @param id
     */
    public void remove(Integer id){
        for(Integer integer : participantsIDs){
            if (integer == id) {
                participantsIDs.remove(id);
                break;
            }
        }
    }

    public Collection<Integer> getParticipantsIDs(){
        return participantsIDs;
    }

    /**
     * How many users take part in conversation (are in ChatRoom)
     * @return - amount of users.
     */
    public int getParticipantsAmount(){
        return participantsIDs.size();
    }
}
