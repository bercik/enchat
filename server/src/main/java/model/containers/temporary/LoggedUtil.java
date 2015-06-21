package model.containers.temporary;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import model.Account;
import model.exceptions.ElementNotFoundException;
import model.exceptions.UserWithNickAlreadyLogged;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by tochur on 17.05.15.
 *
 * This it the only one class that can modify collection of logged clients.
 * This collection may be accessed using Logged interface but not modified.
 */
public class LoggedUtil {
    private Logged logged;

    @Inject
    public LoggedUtil(Logged logged) {
        this.logged = logged;
    }

    public Collection<String> getNicks() {
        return logged.getNicks();
    }

    public Collection<Integer> getIDs() { return logged.getIDs(); }

    public Integer getUserId(String nick) throws ElementNotFoundException {
        Map<Integer, String> loggedMap = logged.getIDNickMap();
        for(Integer id: loggedMap.keySet()){
            if(loggedMap.get(id).equals(nick))
                return id;
        }
        throw new ElementNotFoundException();
    }

    public String getUserNick(Integer id) throws ElementNotFoundException {
        return logged.getNick(id);
    }

    public boolean isLogged(Integer ID) {
        if (logged.getNick(ID) == null)
            return false;
        return true;
    }

    public void add(Integer authorID, Account account)throws UserWithNickAlreadyLogged {
        //Getting the id of the account, that is logged already with specified nick
        Integer loggedBefore = logged.getUserID(account.getNick());
        logged.addNew(authorID, account);
        if(loggedBefore != null){
            throw new UserWithNickAlreadyLogged(loggedBefore);
        }
    }

    public void remove(Integer id){
        try {
            logged.remove(id);
        } catch (ElementNotFoundException e) {
            e.printStackTrace();
        }
    }
}