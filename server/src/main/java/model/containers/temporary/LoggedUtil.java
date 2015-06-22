package model.containers.temporary;

import com.google.inject.Inject;
import model.Account;
import model.exceptions.ElementNotFoundException;
import model.exceptions.UserWithNickAlreadyLogged;

import java.util.Collection;
import java.util.Map;

/**
 * This it the only one class that can modify collection of logged clients.
 * This collection may be accessed using Logged interface but not modified.
 * Consider, that when user positively goes through the certification process,
 * and another client is already logged using this account. Old client will be
 * destroyed (hid chat will be interrupted and he will be automatically logged out.
 *
 * @author Created by tochur on 17.05.15.
 */
public class LoggedUtil {
    private Logged logged;

    @Inject
    public LoggedUtil(Logged logged) {
        this.logged = logged;
    }

    /**
     * Returns the collection of all logged users nicks.
     * @return String Collection - ReadOnly nick collection of all logged users.
     */
    public Collection<String> getNicks() {
        return logged.getNicks();
    }

    /**
     * Returns the collection of all logged users id's.
     * @return String Collection - ReadOnly id's collection of all logged users.
     */
    public Collection<Integer> getIDs() { return logged.getIDs(); }

    /**
     * Returns the id of the logged user with specified by parameter nick.
     * @param nick String, nick that will be mapped to user id.
     * @return Integer, id of the user associated with nick.
     * @throws ElementNotFoundException when no Account is associated with specified nick.
     */
    public Integer getUserId(String nick) throws ElementNotFoundException {
        Map<Integer, String> loggedMap = logged.getIDNickMap();
        for(Integer id: loggedMap.keySet()){
            if(loggedMap.get(id).equals(nick))
                return id;
        }
        throw new ElementNotFoundException();
    }

    /**
     * Returns the nick of the logged user with specified by parameter id.
     * @param id Integer, id of user that will be mapped his nick.
     * @return String, nick of the user associated with id.
     */
    public String getUserNick(Integer id) throws ElementNotFoundException {
        return logged.getNick(id);
    }

    /**
     * Checks weather user id logged.
     * @param ID Integer, id of user to check.
     * @return true when is logged, false otherwise.
     */
    public boolean isLogged(Integer ID) {
        if (logged.getNick(ID) == null)
            return false;
        return true;
    }

    /**
     * Adds new logged user (after certification).
     * @param authorID Integer, id of the user that will be add to logged.
     * @param account Account, persistent user state.
     * @throws UserWithNickAlreadyLogged - when sb already logged from this account.
     */
    public void add(Integer authorID, Account account)throws UserWithNickAlreadyLogged {
        //Getting the id of the account, that is logged already with specified nick
        Integer loggedBefore = logged.getUserID(account.getNick());
        logged.addNew(authorID, account);
        if(loggedBefore != null){
            throw new UserWithNickAlreadyLogged(loggedBefore);
        }
    }

    /**
     * Removed the user with specified id from logged group.
     * @param id Integer, id of the user to remove.
     */
    public void remove(Integer id){
        try {
            logged.remove(id);
        } catch (ElementNotFoundException e) {
            e.printStackTrace();
        }
    }
}