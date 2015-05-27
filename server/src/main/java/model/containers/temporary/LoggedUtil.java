package model.containers.temporary;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import model.Account;
import model.exceptions.ElementNotFoundException;

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

    public Integer getUserId(String nick) throws ElementNotFoundException {
        Map<Integer, String> loggedMap = logged.getIDNickMap();
        for(Integer id: loggedMap.keySet()){
            if(loggedMap.get(id).equals(nick));
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

    public void add(Integer authorID, Account account) {
        logged.addNew(authorID, account);
    }
}