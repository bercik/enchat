package model.containers;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import model.Account;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by tochur on 17.05.15.
 */
public class LoggedManager {
    private Map<Integer, Account> IDAccounts;

    @Inject
    public LoggedManager(@Named("IDAccounts") Map<Integer, Account> IDAccounts) {
        this.IDAccounts = IDAccounts;
    }

    public Collection<String> getNicks() {
        Collection<Account> accounts = IDAccounts.values();
        Set<String> nicks = new HashSet<>();
        for (Account account : accounts) {
            nicks.add(account.getNick());
        }

        return nicks;
    }
}