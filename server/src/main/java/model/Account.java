package model;

import model.containers.permanent.blacklist.BlackList;

/**
 * Holds permanent information about user Account.
 * In next version it will be serializable (and after server restart,
 * clients account will be permanent.
 * Represents the data, characteristic for all registered users.
 *      UserData are unique when pair nick and password are unique.
 * @author Created by tochur on 16.05.15.
 */
public class Account {
    private String nick;
    private String password;
    private BlackList blackList = new BlackList();

    /**
     * Creates new Account object.
     * @param nick String, nick associated with new Account
     * @param password String, password to new Account
     */
    public Account(String nick, String password){
        this.nick = nick;
        this.password = password;
    }

    /**
     * Returns the nick.
     * @return String nick.
     */
    public String getNick() {
        return nick;
    }

    /**
     * Returns the password.
     * @return String, password.
     */
    public String getPassword(){
        return password;
    }

    /**
     * Returns the BlackList associates with this Account.
     * @return BlackList - user BlackList.
     */
    public BlackList getBlackList(){ return blackList; }

    /**
     * Used to compare objects. Two object are equals when they have the same nicks ang passwords.
     * @param other Object, object to compare.
     * @return boolean true when equals, otherwise false.
     */
    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Account))return false;
        Account otherUserData = (Account)other;
        if ( !nick.equals(otherUserData.getNick())) return false;
        if ( !password.equals(otherUserData.getPassword())) return false;
        return true;
    }
}
