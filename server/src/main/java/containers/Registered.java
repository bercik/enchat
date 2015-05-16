package containers;

import model.exceptions.AlreadyInCollection;
import model.exceptions.OverloadedCannotAddNew;
import user.UserData;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tochur on 24.04.15.
 *
 * This class is responsible for holding all nicks.
 * It's associates them with users Accounts.
 * Only one account with the same login may be in Registered.
 * This class provide the uniqueness of user login.
 */
public class Registered {
    //Holds users nicks associated with accounts // In next version consider lazy initialization of userData.
    private static Map<String, UserData> registered;
    //Reference to Singleton object.
    private static Registered instance;
    //Maximum account amount
    private static final int ACCOUNT_LIMIT = 1000;

    //Accessing singleton reference.
    public static Registered getInstance(){
        if (instance == null){
            instance = new Registered();
        }
        return instance;
    }

    /**
     * Singleton constructor
     */
    private Registered(){
        registered = new HashMap<>();
    }

    /**
     * Adds new account, with unique nick.
     * @param userData - data connected with account.
     * @throws AlreadyInCollection - when account with this login already exists
     */
    public void addNewClient(UserData userData) throws AlreadyInCollection, OverloadedCannotAddNew {
        if ( !(registered.size() < ACCOUNT_LIMIT) ){
            throw new OverloadedCannotAddNew();
        }else if ( registered.containsKey(userData.getNick()) ){
            throw new AlreadyInCollection();
        } else {
            registered.put(userData.getNick(), userData);
        }
    }

    /**
     * Cheeks if account with specified login and password exists.
     * It's used during logging for authorization.
     * @param login - user login
     * @param password - password associated with login
     * @return - true only when both:
     *              - account with login (passed as parameter) exist,
     *              - and password (passed ad parameter) is correct.
     */
    public boolean doesUserExist(String login, String password){
        for( String dataName: registered.keySet()){
            if( login.equals(dataName)){
                UserData userData;
                if ( (userData = registered.get(dataName)) != null){
                    if (password.equals(userData.getPassword())){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * This method is deprecated and will be removed soon.
     * @param login - user login
     * @return answer
     */
    public boolean isLoginFree(String login){
        for(String userData: registered.keySet()){
            if (userData.equals(login) ){
                return false;
            }
        }
        return true;
    }
}
