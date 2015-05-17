package model.containers;

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
 */
public class LoggedUtil {
    private Map<Integer, Account> IDAccounts;

    @Inject
    public LoggedUtil(@Named("IDAccounts") Map<Integer, Account> IDAccounts) {
        this.IDAccounts = IDAccounts;
    }

    public Collection<String> getNicks() {
        Collection<Account> accounts = IDAccounts.values();
        Set<String> nicks = new HashSet<>();
        for (Account account : accounts) {
            nicks.add(account.getNick());
        }

        return nicks;
    }

    public Integer getUserId(String nick) throws ElementNotFoundException {
        for(Integer id: IDAccounts.keySet()){
            if(IDAccounts.get(id).equals(nick))
                return id;
        }
        throw new ElementNotFoundException();
    }

    public String getUserNick(Integer id) throws ElementNotFoundException {
        Account account;
        if ((account = IDAccounts.get(id)) == null)
            throw new ElementNotFoundException();
        return account.getNick();
    }

    public boolean isLogged(Integer ID) {
        return IDAccounts.containsKey(ID);
    }
}