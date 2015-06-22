package model.exceptions;

/**
 * When sb already logged, using this account.
 *
 * @author Created by tochur on 21.06.15.
 */
public class UserWithNickAlreadyLogged extends Exception {
    private final Integer id;

    /**
     * When new client want to log. And pass thought the authentication, but
     * sb already logged from this account.
     * @param id - id of the user that already logged with this account
     */
    public UserWithNickAlreadyLogged(int id){
        this.id = id;
    }

    public Integer getUserID() {
        return id;
    }
}
