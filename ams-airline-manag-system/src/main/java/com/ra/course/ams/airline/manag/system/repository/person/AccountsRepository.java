package com.ra.course.ams.airline.manag.system.repository.person;

import com.ra.course.ams.airline.manag.system.entity.person.Account;
import com.ra.course.ams.airline.manag.system.exception.AccountAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exception.AccountNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;

import java.util.Collection;
import java.util.Map;

public class AccountsRepository implements Repository<Account, String> {

    private Map<String, Account> accounts;

    @Override
    public Account getInstance(final String accountId) {
        return accounts.get(accountId);
    }

    @Override
    public Collection<Account> getInstances() {
        return accounts.values();
    }

    @Override
    public Account addInstance(final Account account) {
        final String accountId = account.getId();
        if (accounts.get(accountId) != null) {
            throw new AccountAlreadyExistException();
        }
        return accounts.put(accountId, account);
    }

    @Override
    public void updateInstance(final Account account) {
        final String accountId = account.getId();
        if (accounts.get(accountId) == null) {
            throw new AccountNotExistException();
        }
        accounts.put(accountId, account);
    }

    @Override
    public void removeInstance(final Account account) {
        accounts.remove(account.getId());
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(final Map<String, Account> accounts) {
        this.accounts = accounts;
    }
}
