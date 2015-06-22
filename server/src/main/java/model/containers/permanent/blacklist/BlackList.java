package model.containers.permanent.blacklist;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import model.exceptions.AlreadyInCollection;
import model.exceptions.ElementNotFoundException;
import model.exceptions.OverloadedCannotAddNew;

import java.util.*;

/**
 *
 * Collects nicks of users that are blocked. Is associated with the client account in 1:1 relationship.
 * Placing the user nick to black list is prompt following consequences:
 * First blocked user won't see you at logged user list even when you will be logged.
 * Second he want be able to start an conversation with you.
 * But that won't restrict your rights. In any moment (without removing him from blacklist) you can
 * start a conversation, and you will see him on logged user list.
 *
 * @author Created by tochur on 01.05.15.
 */
public class BlackList {
    //Holds nick
    private Set<String> nicks;
    //Max nick amount.
    private final int MAX_SIZE;

    /**
     * New blacklist is created with default max user size = 100.
     */
    public BlackList(){
        this(100);
    }

    /**
     * New blacklist is created with max size specified by parameter
     * @param BLACK_LIST_MAX_SIZE Integer max amount of users at blackList
     */
    @Inject
    public BlackList(@Named("BLACK_LIST_MAX_SIZE")Integer BLACK_LIST_MAX_SIZE){
        this.MAX_SIZE = BLACK_LIST_MAX_SIZE;
        nicks = new HashSet<>();
    }

    /**
     * Add new nick to black list.
     * @param nick String nick of the user to add to blackList
     * @throws OverloadedCannotAddNew - when no more users can be added
     * @throws AlreadyInCollection - when user with this nick already is in blackList
     */
    public void add(String nick) throws OverloadedCannotAddNew, AlreadyInCollection {
        if( !(MAX_SIZE > nicks.size()) ){
            throw new OverloadedCannotAddNew();
        } else {
            if ( !nicks.add(nick) )
                throw new AlreadyInCollection();
        }
    }

    /**
     * Removes nick from black list.
     * @param nick - nick to remove from blacklist
     * @throws model.exceptions.ElementNotFoundException - When no user with this nick is on list.
     */
    public void remove(String nick) throws ElementNotFoundException {
        if ( !nicks.remove(nick))
            throw new ElementNotFoundException();
    }

    /**
     * Cheek weather specified nick is on black List
     * @param nick - cheeked nick
     * @return - answer
     */
    public boolean hasNick(String nick){
        return nicks.contains(nick);
    }

    /**
     * @return Independent Collection Of Nicks from blackList
     */
    public Collection<String> getNicks(){ return Collections.unmodifiableCollection(nicks); }
}
