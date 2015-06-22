package model.containers.permanent;

import com.google.inject.Inject;
import model.Account;
import model.exceptions.AlreadyInCollection;
import model.exceptions.OverloadedCannotAddNew;

/**
 * Controller class, that supervise the process of adding new users accounts to system.
 *
 * @author Created by tochur on 16.05.15.
 */
public class Registration {
    private Accounts accounts;

    /**
     * <br>
     * Creates controller object to lead modifications on Accounts
     * @param accounts Accounts, object that represents accounts already created.
     */
    @Inject
    public Registration(Accounts accounts){
        this.accounts = accounts;
    }

    /**
     * Creates new Account
     * @param nick String, nick that will identifies the new account
     * @param password String, password to new account
     * @throws OverloadedCannotAddNew when limit of created account was reached.
     * @throws AlreadyInCollection when account with specified nick was already been created.
     */
    public void register(String nick, String password) throws OverloadedCannotAddNew, AlreadyInCollection {
        if ( !(accounts.getAmount() < accounts.getLimit()) ){
            throw new OverloadedCannotAddNew();
        }else if ( isNickFree(nick) ){
            throw new AlreadyInCollection();
        } else {
            Account account = new Account(nick, password);
            accounts.addAccount(nick, account);
        }
    }


    /**
     * Checks weather login is free.
     * @param nick String, nick to check
     * @return true then login is free, false - otherwise
     */
    private boolean isNickFree(String nick){
        return accounts.containKey(nick);
    }
}
