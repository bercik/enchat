package containers;

import model.exceptions.AlreadyInCollection;
import model.exceptions.ElementNotFoundException;
import model.exceptions.OverloadedCannotAddNew;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tochur on 01.05.15.
 *
 * This class holds nicks of all logged users and
 * associates them with User objects.
 */
public class Logged {
    //Restricts max amount of user logged
    public static final Integer MAX_LOGGED_USER = 1000;
    //Reference to Singleton object
    private static Logged instance;
    /*List of all logged capable to interact with server (CONNECTED_TO_SERVER, LOGGED or CONNECTED_WITH_OTHER,)*/
    private static Map<String, User> logged = new HashMap<>();

    /**
     * Singleton constructor.
     */
    private Logged(){}

    /**
     * Singleton reference getter.
     * @return Reference to singleton object.
     */
    public static Logged getInstance(){
        if (instance == null)
            instance = new Logged();
        return instance;
    }

    /**
     * Adding new unique User to Logged group.
     * @param user - user to add.
     * @throws model.exceptions.OverloadedCannotAddNew - when server is overloaded.
     * @throws model.exceptions.AlreadyInCollection - when tried to add not unique user.
     */
    public void addUser(User user) throws OverloadedCannotAddNew, AlreadyInCollection {
        if ( !( MAX_LOGGED_USER > logged.size() ) ){
            throw new OverloadedCannotAddNew();
        }else if( logged.containsKey(user.getNick()) ) {
            throw new AlreadyInCollection();
        }else {
            logged.put(user.getNick(), user);
        }
    }

    /**
     * Cheeks if account with specified login and password exists.
     * It's used during logging for authorization.
     * @param nick - user login
     * @return - User - object that represents user with nick specified as a parameter.
     * @throws model.exceptions.ElementNotFoundException - when no user with this nick is logged.
     */
    public User getUser(String nick) throws ElementNotFoundException {
        for( String dataName: logged.keySet()){
            if( nick.equals(dataName)){
                User userData;
                if ( (userData = logged.get(dataName)) != null){
                    return userData;
                }
            }
        }
        throw new ElementNotFoundException();
    }

    /**
     * Removes user from ActiveUsers
     * @param nick - user with this nick will be to removed
     * @throws model.exceptions.ElementNotFoundException - when user is not in ActiveUsers
     */
    public void deleteUser (String nick) throws ElementNotFoundException {
        if ( logged.remove(nick) == null )
            throw new ElementNotFoundException();
    }

    /**
     * Removes user from ActiveUsers
     * @param nick - user with this nick will be to removed
     * @return true if user was in logged group.
     */
    public boolean deleteUserIfExists (String nick){
        if ( logged.remove(nick) == null )
            return false;
        return true;
    }

    /**
     *
     * @return UnmodifiableCollection of Users logged
     */
    public Collection<User> getLogged(){
        return Collections.unmodifiableCollection(logged.values());
    }

    /**
     * Cheeks if user with specified nick is currently logged.
     * @param nick - nick o cheek
     * @return - answer
     */
    public boolean isUserLogged(String nick){
        for( String dataName: logged.keySet()){
            if( nick.equals(dataName)){
                return true;
            }
        }
        return false;
    }

}
