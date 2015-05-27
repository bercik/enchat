package model.containers.permanent;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import model.Account;
import model.exceptions.AlreadyInCollection;
import model.exceptions.OverloadedCannotAddNew;

import java.util.*;

/**
 * Created by tochur on 16.05.15.
 *
 */
@Singleton
public class Accounts {
    // Maps the client ID with it's States
    private Map<String, Account> accounts = new HashMap<>();
    //Maximum account amount
    private final int ACCOUNT_LIMIT;

    @Inject
    public Accounts(@Named("ACCOUNT_LIMIT")Integer ACCOUNT_LIMIT){
        this.ACCOUNT_LIMIT = ACCOUNT_LIMIT;
    }


    void addAccount(String nick, Account account){
        accounts.put(nick, account);
    }

    void deleteAccount (String nick){
        accounts.remove(nick);
    }

    public boolean containKey(String key){
        return accounts.containsKey(key);
    }

    public Map<String, Account> getMap() { return Collections.unmodifiableMap(accounts); }

    public Set<String> getNicks(){ return Collections.unmodifiableSet(accounts.keySet()); }

    public int getAmount(){
        return accounts.size();
    }

    public int getLimit(){
        return ACCOUNT_LIMIT;
    }
}
