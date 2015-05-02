package containers;

import containers.exceptions.ElementNotFoundException;
import containers.exceptions.OverloadedCannotAddNew;

import java.util.*;

/**
 * Created by tochur on 01.05.15.
 */
public class BlackList {
    private Set<String> nicks;
    private final int MAX_SIZE;

    public BlackList(){
        this(100);
    }

    public BlackList(int maxSize){
        MAX_SIZE = maxSize;
        nicks = new HashSet<>();
    }

    /**
     * Add new nick to black list.
     * @param nick no message from user with this nick, wont be delivered
     */
    public void add(String nick) throws OverloadedCannotAddNew {
        if( !(MAX_SIZE > nicks.size()) )
            throw new OverloadedCannotAddNew();
        nicks.add(nick);
    }

    /**
     * Removes nick from black list.
     * @param nick - nick to remove from blacklist
     * @throws containers.exceptions.ElementNotFoundException - When no user with this nick is on list.
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
    public String[] getNicks(){ return nicks.toArray(new String[0]); }
}
