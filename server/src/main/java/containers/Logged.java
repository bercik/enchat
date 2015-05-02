package containers;

import user.User;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tochur on 01.05.15.
 *
 * This class holds
 */
public class Logged {
    public static final Integer MAX_LOGGED_USER = 300;
    private static Logged instance;
    /*List of all users capable to interact with server (CONNECTED_TO_SERVER, LOGGED or CONNECTED_WITH_OTHER,)*/
    private static Set<User> users;

    private Logged(){
        if (users == null)
            users = new HashSet<>();
    }

    public static Logged getInstance(){
        if (instance == null)
            instance = new Logged();
        return instance;
    }

    /*Adds new user to interaction group*/
    public void addUser(User user){
        users.add(user);
    }

    public Set<User> getActiveUsers(){
        return users;
    }

    /**
     * Search for user with specified nick amongst logged users.
     * @param nick - nick of user we want to connect
     * @return - sender which was searching
     */
    public User getUserIfLogged(String nick){
        for(User user : users){
            if( user.getNick().equals(nick))
                return user;
        }
        return null;
    }

    public boolean canLogNextUser() { return users.size() < MAX_LOGGED_USER; }
}
