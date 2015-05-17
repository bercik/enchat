package model.containers.permanent;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import model.Account;
import model.containers.BlackList;
import model.exceptions.ElementNotFoundException;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by tochur on 17.05.15.
 */
public class BlackListAccessor {
    Map<Integer, Account> IDAccounts;

    @Inject
    public BlackListAccessor(@Named("IDAccounts")Map<Integer, Account> IDAccounts){
        this.IDAccounts = IDAccounts;
    }

    public BlackList get(Integer id) throws NoSuchElementException {
        Account account = IDAccounts.get(id);
        if (account == null)
            throw new NoSuchElementException();
        return account.getBlackList();
    }
}
