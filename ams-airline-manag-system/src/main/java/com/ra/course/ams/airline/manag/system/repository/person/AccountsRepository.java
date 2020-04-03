package com.ra.course.ams.airline.manag.system.repository.person;

import com.ra.course.ams.airline.manag.system.entity.person.Account;

import java.util.Collection;
import java.util.Map;

public interface AccountsRepository {

    Account getInstance(final String accountId);

    Collection<Account> getInstances();

    Account addInstance(final Account account);

    void updateInstance(final Account account);

    void removeInstance(final Account account);

    Map<String, Account> getAccounts();

    void setAccounts(final Map<String, Account> accounts);
}
