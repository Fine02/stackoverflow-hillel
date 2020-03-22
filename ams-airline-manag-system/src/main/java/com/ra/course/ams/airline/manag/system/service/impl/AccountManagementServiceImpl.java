package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.person.Account;
import com.ra.course.ams.airline.manag.system.entity.person.AccountStatus;
import com.ra.course.ams.airline.manag.system.entity.person.Admin;
import com.ra.course.ams.airline.manag.system.exception.AccountAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exception.AccountNotExistException;
import com.ra.course.ams.airline.manag.system.repository.person.AccountsRepository;
import com.ra.course.ams.airline.manag.system.service.AccountManagementService;
import com.ra.course.ams.airline.manag.system.service.AuthenticationService;
import com.ra.course.ams.airline.manag.system.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Service
public class AccountManagementServiceImpl implements AccountManagementService {

    private final AccountsRepository accountRepository;
    @Autowired
    private AuthorizationService authorizationSvc;
    @Autowired
    private AuthenticationService authenticationSvc;

    public AccountManagementServiceImpl(final AccountsRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createAccount(final Account account) {
        Account accountFromRepo = accountRepository.getInstance(account.getId());
        if (accountFromRepo != null) {
            throw new AccountAlreadyExistException();
        }
        accountFromRepo = new Account(account);
        accountRepository.addInstance(accountFromRepo);
        return account;
    }

    @Override
    public List<Account> getAccounts() {
        final Collection<Account> accounts = accountRepository.getInstances();

        return new LinkedList<>(accounts);
    }

    @Override
    public void deleteAccount(final Account account, final Admin admin) {
        authorizationSvc.checkGrantsForDeleteAccountOperation(account, admin);
        final Account accountFromRepo = accountRepository.getInstance(account.getId());
        if (accountFromRepo == null) {
            throw new AccountNotExistException();
        }
        accountRepository.removeInstance(account);
    }

    @Override
    public Account updateStatus(final Account account, final AccountStatus status, final Admin admin) {
        authorizationSvc.checkGrantsForUpdateAccountOperation(account, admin);
        final Account accountFromRepo = accountRepository.getInstance(account.getId());
        if (accountFromRepo == null) {
            throw new AccountNotExistException();
        }
        accountFromRepo.setAccountStatus(status);
        accountRepository.updateInstance(accountFromRepo);
        account.setAccountStatus(status);
        return account;
    }

    @Override
    public Account blockAccount(final Account account, final Admin admin) {
        authorizationSvc.checkGrantsForBlockAccountOperation(account, admin);
        final Account accountFromRepo = accountRepository.getInstance(account.getId());
        if (accountFromRepo == null) {
            throw new AccountNotExistException();
        }
        accountFromRepo.setAccountStatus(AccountStatus.BLOCKED);
        accountRepository.updateInstance(accountFromRepo);
        account.setAccountStatus(AccountStatus.BLOCKED);
        return account;
    }

    @Override
    public void resetPassword(final Account account, final String oldPassword, final String newPassword) {
        final Account realAccountFromDb = authenticationSvc.login(account.getId(), oldPassword);
        realAccountFromDb.setPassword(newPassword);
        accountRepository.updateInstance(realAccountFromDb);
    }

    @Override
    public void resetPassword(final Account account, final String newPassword, final Admin admin) {
        authorizationSvc.checkGrantsForResetPasswordOperation(account, admin);
        final Account accountFromRepo = accountRepository.getInstance(account.getId());
        if (accountFromRepo == null) {
            throw new AccountNotExistException();
        }
        accountFromRepo.setPassword(newPassword);
        accountRepository.updateInstance(accountFromRepo);
    }

    public AccountsRepository getAccountRepository() {
        return accountRepository;
    }

    public AuthorizationService getAuthorizationSvc() {
        return authorizationSvc;
    }

    public void setAuthorizationSvc(final AuthorizationService authorizationSvc) {
        this.authorizationSvc = authorizationSvc;
    }

    public AuthenticationService getAuthenticationSvc() {
        return authenticationSvc;
    }

    public void setAuthenticationSvc(final AuthenticationService authenticationSvc) {
        this.authenticationSvc = authenticationSvc;
    }
}
