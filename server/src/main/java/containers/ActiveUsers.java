package containers;

import user.ActiveUser;
import user.ActiveUserT;

import java.util.ArrayList;

/**
 * Created by tochur on 16.04.15.
 */

/*Holds all users, that can interact with server
*   During work server iterates this collection and listen for new messages from this users, and interact with them.
* */
public class ActiveUsers {
    private static ActiveUsers instance;
    /*List of all users capable to interact with server (CONNECTED_TO_SERVER, LOGGED or CONNECTED_WITH_OTHER,)*/
    private static ArrayList<ActiveUser> activeUsers;

    private ActiveUsers(){
        if (activeUsers == null)
            activeUsers = new ArrayList<ActiveUser>();
    }

    public static ActiveUsers getInstance(){
        if (instance == null)
            instance = new ActiveUsers();
        return instance;
    }

    /*Adds new user to interaction group*/
    public void addUser(ActiveUser activeUser){
        activeUsers.add(activeUser);
    }

    public ArrayList<ActiveUser> getActiveUsers(){
        return activeUsers;
    }
}
