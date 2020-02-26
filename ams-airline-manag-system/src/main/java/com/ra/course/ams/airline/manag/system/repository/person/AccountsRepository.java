package com.ra.course.ams.airline.manag.system.repository.person;

import com.ra.course.ams.airline.manag.system.entity.person.Account;
import com.ra.course.ams.airline.manag.system.exceptions.AccountAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exceptions.AccountNotExistException;
import com.ra.course.ams.airline.manag.system.exceptions.InstanceAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exceptions.InstanceNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AccountsRepository implements Repository<Account, String> {

    private Map<String, Account> accounts = new HashMap<>();

    @Override
    public Account getInstance(String accountId) {
        return accounts.get(accountId);
    }

    @Override
    public Collection<Account> getInstances() {
        return accounts.values();
    }

    @Override
    public Account addInstance(Account account) throws InstanceAlreadyExistException {
        String accountId = account.getId();
        if (accounts.get(accountId) != null) throw new AccountAlreadyExistException();
        return accounts.put(accountId, account);
    }

    @Override
    public void updateInstance(Account account) throws InstanceNotExistException {
        String accountId = account.getId();
        if (accounts.get(accountId) == null) throw new AccountNotExistException();
        accounts.put(accountId, account);
    }

    @Override
    public void remoteInstance(Account account) {
        accounts.remove(account.getId());
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public AccountsRepository setAccounts(Map<String, Account> accounts) {
        this.accounts = accounts;
        return this;
    }
}
