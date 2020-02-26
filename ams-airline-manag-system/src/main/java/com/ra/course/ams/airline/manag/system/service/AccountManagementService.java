package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.person.Account;

import java.util.List;

public interface AccountManagementService {

    Account createAccount(Account account);

    List<Account> getAccounts();

    void deleteAccount(Account account);

    void updateAccount(Account account);

    void blockAccount(Account account);

    boolean resetPassword(Account account, String password);
}
