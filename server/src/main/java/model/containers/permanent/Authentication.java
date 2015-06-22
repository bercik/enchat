package model.containers.permanent;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import model.Account;
import model.exceptions.IncorrectNickOrPassword;

import java.util.Map;

/**
 * Util for certification.
 *
 * @author Created by tochur on 16.05.15.
 */
public class Authentication {
    private Map<String, Account> accounts;

    /**
     * Creates the util for certification.
     * @param accounts String to Account map, delivers the data necessary for certification.
     */
    @Inject
    public Authentication(@Named("Accounts")Map<String, Account> accounts){
        this.accounts = accounts;
    }


    /**
     * Cheeks if account with specified login and password exists.
     * It's used during logging for certification.
     * @param nick String - user nick
     * @param password String - password associated with login
     * @return Account - object that holds permanent user data.
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
