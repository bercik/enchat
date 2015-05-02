package containers;

import containers.exceptions.AlreadyInCollection;
import containers.exceptions.ElementNotFoundException;
import user.User;

import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tochur on 16.04.15.
 *
 * Is owner of the references to all users in system.
 * They are unique for the sake of Socket (if reference are equals).
 */

public class ActiveUsers {
    //Reference to singleton object
    private static ActiveUsers instance;

    /**
     * Maps the socket references to User references
     */
    private static Map<Socket, User> users;

    /**
     * Singleton constructor
     */
    private ActiveUsers(){
        if (users == null)
            users = new HashMap<>();
    }

    /**
     * Singleton object creator.
     * @return Created object.
     */
    public static ActiveUsers getInstance(){
        if (instance == null)
            instance = new ActiveUsers();
        return instance;
    }

    /**
     * Adding new unique User to ActiveUsers
     * @param user - user to Add
     * @throws AlreadyInCollection - when tried to add not unique user.
     */
    public void addUser(User user) throws AlreadyInCollection {
        if ( users.containsKey(user.getSocket()) ) {
            throw new AlreadyInCollection();
        }else {
            users.put(user.getSocket(), user);
        }
    }

    /**
     * Removes user from ActiveUsers
     * @param user - user to remove
     * @throws ElementNotFoundException - when user is not in ActiveUsers
     */
    public void deleteUser (User user) throws ElementNotFoundException {
        if ( users.remove(user.getSocket()) == null )
            throw new ElementNotFoundException();
    }

    /**
     * Returns the collection to all users in system.
     * @return collection of users.
     */
    public Collection<User> getActiveUsers(){
        return users.values();
    }
}
