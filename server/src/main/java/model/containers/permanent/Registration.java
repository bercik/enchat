package model.containers.permanent;

import com.google.inject.Inject;
import model.Account;
import model.exceptions.AlreadyInCollection;
import model.exceptions.OverloadedCannotAddNew;

/**
 * Created by tochur on 16.05.15.
 *
 * Controller class, with supervise the process of adding new users accounts to system.
 */
public class Registration {
    private Accounts accounts;

    @Inject
    public Registration(Accounts accounts){
        this.accounts = accounts;
    }

    /**
     * This method should be atomic operation.
     * @param nick
     * @param password
     * @throws OverloadedCannotAddNew
     * @throws AlreadyInCollection
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


    /** ReadOnly
     * @param nick - user login
     * @return answer
     */
    private boolean isNickFree(String nick){
        return accounts.containKey(nick);
    }
}
