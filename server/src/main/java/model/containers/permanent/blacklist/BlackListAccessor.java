package model.containers.permanent.blacklist;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import model.Account;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Util that helps us to access the black list of the specified user.
 * This class is the type of go-between. Now it's trivial and easily may be removed.
 * But in future this class may access the DB to get permanent info connected with the user.
 * (Black list that is associated with client account).
 *
 * @author Created by tochur on 17.05.15.
 */
public class BlackListAccessor {
    private Map<Integer, Account> IDAccounts;

    /**
     * Creates a new BlackList accessor.
     * @param IDAccounts Integer to Account map the collection that is searched to get the user blackList
     */
    @Inject
    public BlackListAccessor(@Named("IDAccounts")Map<Integer, Account> IDAccounts){
        this.IDAccounts = IDAccounts;
    }

    /**
     * Accessing the user blackList
     * @param id Integer id of the user (temporary value, assigned to the every user that is connected with the server)
     * @return BlackList Object that represents the blackList
     * @throws NoSuchElementException when with user is not associated any Account.
     */
    public BlackList get(Integer id) throws NoSuchElementException {
        Account account = IDAccounts.get(id);
        if (account == null)
            throw new NoSuchElementException();
        return account.getBlackList();
    }
}