package model.containers.temporary;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import model.Account;
import model.exceptions.ElementNotFoundException;

import java.util.*;

/**
 * This class holds nicks of all logged users and
 * associates them with User objects.
 * Exactly maps the temporary identifier to permanent identifier
 * (nick) and consequently account.
 *
 * @author Created by tochur on 01.05.15.
 */
@Singleton
public class Logged {

    /*List of all logged capable to interact*/
    private Map<Integer, Account> logged;
    private Map<Integer, String> loggedNicks;

    /**
     * Creates new object that maps the temporary identifier to permanent identifier (nick).
     */
    @Inject
    public Logged(){
        this.logged = new HashMap<>();
        this.loggedNicks = new HashMap<>();
    }


    /**
     * Adding new unique User to Logged group.
     * @param ID Integer, id of the user - temporary identifier
     * @param account Account, permanent object that holds info about the client on the server.
     */
    void addNew(Integer ID, Account account){
        logged.put(ID, account);
        loggedNicks.put(ID, account.getNick());
    }

    /**
     * Removes user from ActiveUsers.
     * @param ID Integer, id of the user - temporary identifier.
     * @throws ElementNotFoundException when no account is associated with specified id.
     */
    void remove (Integer ID) throws ElementNotFoundException {
        if ( logged.remove(ID) == null )
            throw new ElementNotFoundException();
        loggedNicks.remove(ID);
    }

    /**
     * Returns the map that associates users ID with nicks.
     * @return Integer to String map. Associates users ID with nicks.
     */
    Map<Integer, String> getIDNickMap(){
        return loggedNicks;
    }

    /**
     * Returns the nick of the user with specified id. Maps the temporary id to permanent.
     * @param id Integer, id of user to check.
     * @return String nick of checked user.
     */
    public String getNick(Integer id){
        return loggedNicks.get(id);
    }

    /**
     * Returns the set of users id's (temporary identifiers of all connected clients).
     * @return Integer Set of users id's (temporary identifiers of all connected clients).
     */
    public Set<Integer> getIDs(){
        return loggedNicks.keySet();
    }

    /**
     * Returns the Collection of accounts. Only account of currently logged clients.
     * @return Unmodifiable Account Collection of the currently logged users.
     */
    public Collection<Account> getAccounts(){
        return Collections.unmodifiableCollection(logged.values());
    }

    /**
     * Returns the map that associates the clients id's to accounts.
     * @return Unmodifiable map, user id to account.
     */
    public Map<Integer, Account> getMap() { return Collections.unmodifiableMap(logged); }

    /**
     * Returns the nick collection,of all logged users.
     * @return ReadOnly nick Collection of all logged users.
     */
    public Collection<String> getNicks(){ return  Collections.unmodifiableCollection(loggedNicks.values()); }

    /**
     * Returns the id of the user with specified nick. If no user with specified nick is logged return null
     * @param nick String, login of the user that id is searched
     * @return id of the user with specified nick. If no user with specified nick is logged return null
     */
    public Integer getUserID(String nick){
        for (Map.Entry<Integer, String> pair : loggedNicks.entrySet()){
            if(pair.getValue().equals(nick))
                return pair.getKey();
        }
        return null;
    }
}
