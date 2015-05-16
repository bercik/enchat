package model.containers.permanent;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import model.Account;
import model.containers.BlackList;

import java.util.Map;

/**
 * Created by tochur on 17.05.15.
 */
public class BlackListUtil {
    private Map<String, Account> accounts;

    @Inject
    public Authentication(@Named("Accounts")Map<String, Account> accounts){
        this.accounts = accounts;
    }


    public void addToBlackList (String nick, String password) throws IncorrectNickOrPassword {
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
