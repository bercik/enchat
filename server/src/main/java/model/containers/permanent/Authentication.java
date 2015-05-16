package model.containers.permanent;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import model.Account;
import model.exceptions.IncorrectNickOrPassword;

import java.util.Map;

/**
 * Created by tochur on 16.05.15.
 */
public class Authentication {
    private Map<String, Account> accounts;

    @Inject
    public Authentication(@Named("Accounts")Map<String, Account> accounts){
        this.accounts = accounts;
    }


    /** READONLY
     * Cheeks if account with specified login and password exists.
     * It's used during logging for authorization.
     * @param nick - user nick
     * @param password - password associated with login
     * @return - account - object that holds permanent user data.
     * @throws IncorrectNickOrPassword - when authentication failed
     */
    public Account authenticate(String nick, String password) throws IncorrectNickOrPassword {
        for( String key: accounts.keySet()){
            if( nick.equals(key)){
                Account account;
                if ( (account = accounts.get(nick)) != null){
                    if (password.equals(account.getPassword())){
                        return account;
                    }
                }
            }
        }
        throw new IncorrectNickOrPassword();
    }
}
