package model;

import model.containers.BlackList;

/**
 * Created by tochur on 16.05.15.
 *
 * Holds permanent information about user Account.
 * In next version it will be serializable (and after server restart,
 * clients account will be permanent.
 * Represents the data, characteristic for all registered users.
 *      UserData are unique when pair nick & password are unique.
 */
public class Account {
    private String nick;
    private String password;
    private BlackList blackList = new BlackList();

    public Account(String nick, String password){
        this.nick = nick;
        this.password = password;
    }

    public String getNick() {
        return nick;
    }

    public String getPassword(){
        return password;
    }

    public BlackList getBlackList(){ return blackList; }

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
