package containers;

import model.exceptions.AlreadyInCollection;
import model.exceptions.ElementNotFoundException;
import model.exceptions.OverloadedCannotAddNew;
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
    // Reference to singleton object
    private static ActiveUsers instance;
    // Maps the socket references to User - the owner of the socket.
    private static Map<Socket, User> users = new HashMap<>();
    // Restricts amount of User that can interact with Server
    private final int MAX_ACTIVE_USER = 1000;

    /**
     * Singleton constructor
     */
    private ActiveUsers(){}

    /**
     * Singleton reference getter.
     * @return Reference to singleton object.
     */
    public static ActiveUsers getInstance(){
        if (instance == null)
            instance = new ActiveUsers();
        return instance;
    }

    /**
     * Adding new unique User to ActiveUsers
     * @param user - user to Add
     * @throws OverloadedCannotAddNew - when newServer is overloaded.
     * @throws AlreadyInCollection - when tried to add not unique user.
     */
    public void addUser(User user) throws AlreadyInCollection, OverloadedCannotAddNew {
        if ( !( MAX_ACTIVE_USER > users.size() ) ){
            throw new OverloadedCannotAddNew();
        }else if( users.containsKey(user.getSocket()) ) {
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
