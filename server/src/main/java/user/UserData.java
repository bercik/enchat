package user;

import containers.BlackList;

import java.util.List;

/**
 * Created by tochur on 16.04.15.
 *
 * Holds user data.
 * These date will serializable and stored.
 * Represents the data, characteristic for all registered users.
 * User must possess unique UserData to log.
 *      UserData are unique when pair nick & password are unique.
 */

/*Holds constant user data, this object will be serializable. And will be used to verify clients log action or new user creating.*/
public class UserData {
    private String nick;
    private String password;
    private BlackList blackList = new BlackList();

    public UserData(String nick, String password){
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
        if (!(other instanceof UserData))return false;
        UserData otherUserData = (UserData)other;
        if ( !nick.equals(otherUserData.getNick())) return false;
        if ( !password.equals(otherUserData.getPassword())) return false;
        return true;
    }
}
