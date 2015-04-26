package containers;

import user.UserData;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tochur on 24.04.15.
 *
 * This class is responsible for holding all registered users, and give interface to add new users.
 */
public class Registered {
    private static List<UserData> registered = new LinkedList<UserData>();
    private static Registered instance;

    public static Registered getInstance(){
        if (instance == null){
            instance = new Registered();
        }
        return instance;
    }

    private Registered(){
    }

    public void addNewClient(UserData userData){
        registered.add(userData);
    }

    // They wont modify system state
    public boolean isLoginFree(String login){
        for(UserData userData: registered){
            if (userData.getNick().equals(login) ){
                return false;
            }
        }
        return true;
    }
    
    public boolean doesUserExist(String name, String password){
        for( UserData userData: registered){
            if( userData.getNick().equals(name)){
                if (userData.getPassword().equals(password))
                    return true;
            }
        }
        return false;
    }
}
