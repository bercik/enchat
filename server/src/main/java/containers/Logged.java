package containers;

import user.ActiveUser;

import java.util.ArrayList;

/**
 * Created by tochur on 01.05.15.
 */
public class Logged {
    public static final Integer MAX_LOGGED_USER = 300;
    private static Logged instance;
    /*List of all users capable to interact with server (CONNECTED_TO_SERVER, LOGGED or CONNECTED_WITH_OTHER,)*/
    private static ArrayList<ActiveUser> activeUsers;

    private Logged(){
        if (activeUsers == null)
            activeUsers = new ArrayList<ActiveUser>();
    }

    public static Logged getInstance(){
        if (instance == null)
            instance = new Logged();
        return instance;
    }

    /*Adds new user to interaction group*/
    public void addUser(ActiveUser activeUser){
        activeUsers.add(activeUser);
    }

    public ArrayList<ActiveUser> getActiveUsers(){
        return activeUsers;
    }

    /**
     * Search for user with specified nick amongst logged users.
     * @param nick - nick of user we want to connect
     * @return - sender which was searching
     */
    public ActiveUser getUserIfLogged(String nick){
        for(ActiveUser activeUser: activeUsers){
            if( activeUser.getNick().equals(nick))
                return activeUser;
        }
        return null;
    }

    public boolean canLogNextUser() { return activeUsers.size() < MAX_LOGGED_USER; }
}
