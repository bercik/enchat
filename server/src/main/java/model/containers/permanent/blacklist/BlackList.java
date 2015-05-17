package model.containers.permanent.blacklist;

import model.exceptions.AlreadyInCollection;
import model.exceptions.ElementNotFoundException;
import model.exceptions.OverloadedCannotAddNew;

import java.util.*;

/**
 * Created by tochur on 01.05.15.
 *
 * Collects the nicks.
 * User that has black list wont get any message from user with nick from Blacklist
 * As well users with nick from black list wont see weather user is Logged.
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
     * New blacklist is created with default max user specified by parameter
     * @param maxSize - max amount of users at blackList
     */
    public BlackList(int maxSize){
        MAX_SIZE = maxSize;
        nicks = new HashSet<>();
    }

    /**
     * Add new nick to black list.
     * @param nick - nick of the user to add to blackList
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
