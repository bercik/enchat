package model.containers.permanent;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import model.Account;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Collects all accounts,<br>
 *     exactly maps the clients nick to theirs accounts.
 *     Consider that nick is the unique value. And to each nick one account is fit.
 * @author Created by tochur on 16.05.15.
 */
@Singleton
public class Accounts {
    // Maps the client ID with it's States
    private Map<String, Account> accounts = new HashMap<>();
    //Maximum account amount
    private final int ACCOUNT_LIMIT;

    /**
     * Creates the Util for managing clients accounts.
     * @param ACCOUNT_LIMIT Integer - limit of the account that can be created.
     */
    @Inject
    public Accounts(@Named("ACCOUNT_LIMIT")Integer ACCOUNT_LIMIT){
        this.ACCOUNT_LIMIT = ACCOUNT_LIMIT;
    }


    /**
     * Adds new account to collection. Called after positive registration.
     * @param nick String - permanent client identifier
     * @param account Account - object that represents the permanent client state.
     */
    public void addAccount(String nick, Account account){
        accounts.put(nick, account);
    }

    /**
     * Removes account associated with specified nick
     * @param nick String - identifies the account that will be removed.
     */
    public void deleteAccount (String nick){
        accounts.remove(nick);
    }

    /**
     * Checks weather account with specified nick was already been created
     * @param nick String - nick to check.
     * @return - false, when no account was created with this nick, true otherwise.
     */
    public boolean containKey(String nick){
        return accounts.containsKey(nick);
    }


    public Map<String, Account> getMap() { return Collections.unmodifiableMap(accounts); }

    /**
     * Returns the unmodifiable collection of nicks that are engaged.
     * The account with this nick is already in use.
     * @return set of Strings - unmodifiable collection of nicks.
     */
    public Set<String> getNicks(){ return Collections.unmodifiableSet(accounts.keySet()); }

    /**
     * Returns the current amount of accounts.
     * @return Integer - current accounts amount.
     */
    public int getAmount(){
        return accounts.size();
    }

    /**
     * Returns the limit of accounts that may be created on the server
     * @return Integer - limit of accounts
     */
    public int getLimit(){
        return ACCOUNT_LIMIT;
    }
}
