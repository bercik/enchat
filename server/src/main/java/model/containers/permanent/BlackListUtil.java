package model.containers.permanent;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import controller.responders.exceptions.UserNotExists;
import model.Account;
import model.containers.BlackList;
import model.exceptions.AlreadyInCollection;
import model.exceptions.OverloadedCannotAddNew;
import sun.nio.cs.ext.ISCII91;

import java.util.Map;

/**
 * Created by tochur on 17.05.15.
 */
public class BlackListUtil {
    //To add new nick to User Black List
    private Map<Integer, Account> IDAccounts;
    //To check weather user with specified nick exists
    private Accounts accountsString;

    @Inject
    public BlackListUtil(@Named("IDAccounts")Map<Integer, Account> IDAccounts, Accounts accounts){
        this.IDAccounts = IDAccounts;
        this.accountsString = accounts;
    }

    public void addToBlackList(Integer authorID, String nickToAdd) throws UserNotExists, AlreadyInCollection, OverloadedCannotAddNew {
        if ( !accountsString.containKey(nickToAdd))
            throw new UserNotExists();
        Account account = IDAccounts.get(authorID);
        account.getBlackList().add(nickToAdd);
    }
}
