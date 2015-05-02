package containers;

import containers.exceptions.AlreadyInCollection;
import containers.exceptions.ElementNotFoundException;
import containers.exceptions.OverloadedCannotAddNew;
import user.User;

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
     * @throws containers.exceptions.OverloadedCannotAddNew - when server is overloaded.
     * @throws containers.exceptions.AlreadyInCollection - when tried to add not unique user.
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
     * @throws ElementNotFoundException - when no user with this nick is logged.
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
