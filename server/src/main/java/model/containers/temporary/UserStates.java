package model.containers.temporary;

import com.google.inject.Singleton;
import model.user.UserState;

import java.util.HashMap;
import java.util.Map;

/**
 * Util for user state management.
 *
 * @author Created by tochur on 16.05.15.
 */

@Singleton
public class UserStates {
    // Maps the client ID with it's States
    private Map<Integer, UserState> userStates = new HashMap<>();

    /**
     * Returns state of the user with specified id.
     * @param ID Integer of the user to check.
     * @return UserState of the user.
     */
    public UserState getUserState(Integer ID){
        return userStates.get(ID);
    }

    /**
     * When record with specified ID exists, userState is changed.
     * Otherwise new record isAdded
     * @param ID - client identifier associated with stream.
     * @param userState - actual userState.
     */
    public void updateState(Integer ID, UserState userState){
        userStates.put(ID, userState);
    }

    /**
     * Delete outStream associated with ID
     * @param ID - id associated with stream.
     */
    public void deleteRecordWithId (Integer ID){
        userStates.remove(ID);
    }

    /**
     * Returns the amount of user connected to the server.
     * @return amount of users connected.
     */
    public int getRecordsAmount(){
        return userStates.size();
    }
}
