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
 * Util class for managing the blackLists.
 * Should be used as an interface between data and controllers.
 * Provides all needed methods for managing it.
 *
 * @author Created by tochur on 17.05.15.
 */
public class BlackListUtil {
    private BlackListAccessor blackListAccessor;
    //To check weather user with specified nick exists
    private Accounts accountsString;

    /**
     * Creates Util for managing blackList.
     * @param blackListAccessor BlackListAccessor - layer of abstraction in the date assessing
     * @param accounts Accounts - Object that has access to all accounts.
     */
    @Inject
    public BlackListUtil(BlackListAccessor blackListAccessor, Accounts accounts){
        this.blackListAccessor = blackListAccessor;
        this.accountsString = accounts;
    }

    /**
     * Adds specified nick to blackList.<br>
     * After this method call (when no exception was thrown), you have guarantee,
     * that user with specified name is on your black list.
     * @param authorID Integer - id of the user that requested to remove one nick from his blackList.
     * @param nickToAdd String - nick of the user, that will be added to blackList.
     * @throws UserNotExists - when no account with this nick were created
     * @throws AlreadyInCollection When user already added nick to his BlackList
     * @throws OverloadedCannotAddNew When user reached the limit of the users added to his blackList.
     */
    public void addToBlackList(Integer authorID, String nickToAdd) throws UserNotExists, AlreadyInCollection, OverloadedCannotAddNew {
        if ( !accountsString.containKey(nickToAdd))
            throw new UserNotExists();
        BlackList blackList = blackListAccessor.get(authorID);
        blackList.add(nickToAdd);
    }

    /**
     * Removes specified nick from blackList.<br>
     * After this method call (when no exception was thrown), you have guarantee,
     * that user with specified name is not on your black list. Does not matter weather he was there already.
     * @param authorID Integer - id of the user that requested to remove one nick from his blackList.
     * @param nickToRemove String - nick of the user, that will be removed from blackList.
     * @throws UserNotExists - when no account with this nick were created
     * @throws ElementNotFoundException - When no account is associated with user that want to reduce blackList
     */
    public void remove(Integer authorID, String nickToRemove) throws UserNotExists, ElementNotFoundException {
        if ( !accountsString.containKey(nickToRemove))
            throw new UserNotExists();
        BlackList blackList = blackListAccessor.get(authorID);
        blackList.remove(nickToRemove);
    }

    /**
     * Returns the blacklist of the specified client.
     * @param authorID Integer - id of the user whose blackList is requested
     * @return Collection of Strings - nicks of users on blackList
     * @throws NoSuchElementException - when na account is associated with specified user.
     */
    public Collection<String> getBlackList(Integer authorID) throws NoSuchElementException {
        BlackList blackList = blackListAccessor.get(authorID);
        return blackList.getNicks();
    }

    /**
     * Checks weather specified person is on blackList of the user with specified nick.
     * @param ownerOfListId Integer - id of the list owner.
     * @param toCheck String - Nick of the user that is checked
     * @return false - when nick is not on blackList, true - otherwise.
     */
    public boolean isOnBlackList(Integer ownerOfListId, String toCheck){
        return blackListAccessor.get(ownerOfListId).hasNick(toCheck);
    }
}
