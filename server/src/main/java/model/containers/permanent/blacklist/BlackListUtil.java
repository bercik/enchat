package model.containers.permanent.blacklist;

import com.google.inject.Inject;
import controller.responders.exceptions.UserNotExists;
import model.containers.permanent.Accounts;
import model.exceptions.AlreadyInCollection;
import model.exceptions.ElementNotFoundException;
import model.exceptions.OverloadedCannotAddNew;

import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Created by tochur on 17.05.15.
 */
public class BlackListUtil {
    private BlackListAccessor blackListAccessor;
    //To check weather user with specified nick exists
    private Accounts accountsString;

    @Inject
    public BlackListUtil(BlackListAccessor blackListAccessor, Accounts accounts){
        this.blackListAccessor = blackListAccessor;
        this.accountsString = accounts;
    }

    public void addToBlackList(Integer authorID, String nickToAdd) throws UserNotExists, AlreadyInCollection, OverloadedCannotAddNew {
        if ( !accountsString.containKey(nickToAdd))
            throw new UserNotExists();
        BlackList blackList = blackListAccessor.get(authorID);
        blackList.add(nickToAdd);
    }

    public void remove(Integer authorID, String nickToRemove) throws UserNotExists, ElementNotFoundException {
        if ( !accountsString.containKey(nickToRemove))
            throw new UserNotExists();
        BlackList blackList = blackListAccessor.get(authorID);
        blackList.remove(nickToRemove);
    }

    public Collection<String> getBlackList(Integer authorID) throws NoSuchElementException {
        BlackList blackList = blackListAccessor.get(authorID);
        return blackList.getNicks();
    }

    public boolean isOnBlackList(Integer ownerOfListId, String toCheck){
        return blackListAccessor.get(ownerOfListId).hasNick(toCheck);
    }
}
