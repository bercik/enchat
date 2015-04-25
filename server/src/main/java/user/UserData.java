package user;

/**
 * Created by tochur on 16.04.15.
 */

/*Holds constant user data, this object will be serializable. And will be used to verify clients log action or new user creating.*/
public class UserData {
    private String nick;
    private String password;

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
}
