package model.containers.permanent;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import model.Account;
import model.containers.BlackList;

import java.util.Map;

/**
 * Created by tochur on 17.05.15.
 */
public class BlackListAccessor {
    Map<Integer, Account> IDAccounts;

    @Inject
    public BlackListAccessor(@Named("IDAccounts")Map<Integer, Account> IDAccounts){
        this.IDAccounts = IDAccounts;
    }

    public BlackList get(Integer id){
        return IDAccounts.get(id).getBlackList();
    }
}
