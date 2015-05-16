package model.containers;

import com.google.inject.Singleton;
import com.sun.deploy.util.*;
import model.Account;
import model.exceptions.AlreadyInCollection;
import model.exceptions.ElementNotFoundException;
import model.exceptions.OverloadedCannotAddNew;

import java.util.*;

/**
 * Created by tochur on 01.05.15.
 *
 * This class holds nicks of all logged users and
 * associates them with User objects.
 */
@Singleton
public class Logged {

    /*List of all logged capable to interact*/
    private static Map<Integer, Account> logged = new HashMap<>();


    /**
     * Adding new unique User to Logged group.
     * @param ID
     * @param account
     */
    public void addNew(Integer ID, Account account){
        logged.put(ID, account);
    }

    /**
     * Removes user from ActiveUsers
     * @param ID
     * @throws ElementNotFoundException
     */
    public void remove (Integer ID) throws ElementNotFoundException {
        if ( logged.remove(ID) == null )
            throw new ElementNotFoundException();
    }

}
