package model.containers;

import com.google.inject.Singleton;
import model.Account;
import model.exceptions.ElementNotFoundException;

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
@Singleton
public class Logged {

    /*List of all logged capable to interact*/
    private Map<Integer, Account> logged = new HashMap<>();
    private Map<Integer, String> loggedNicks = new HashMap<>();


    /**
     * Adding new unique User to Logged group.
     * @param ID
     * @param account
     */
    public void addNew(Integer ID, Account account){
        logged.put(ID, account);
    }

    /**
     * Removes user from ActiveUsers
     * @param ID
     * @throws ElementNotFoundException
     */
    public void remove (Integer ID) throws ElementNotFoundException {
        if ( logged.remove(ID) == null )
            throw new ElementNotFoundException();
    }

    public Collection<Account> getAccounts(){
        return Collections.unmodifiableCollection(logged.values());
    }

    public Map<Integer, Account> getMap() { return Collections.unmodifiableMap(logged); }

    public Collection<String> getNicks(){ return  Collections.unmodifiableCollection(loggedNicks.values()); }
}
