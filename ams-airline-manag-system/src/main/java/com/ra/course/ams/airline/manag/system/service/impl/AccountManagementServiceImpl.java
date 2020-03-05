package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.person.Account;
import com.ra.course.ams.airline.manag.system.entity.person.AccountStatus;
import com.ra.course.ams.airline.manag.system.entity.person.Admin;
import com.ra.course.ams.airline.manag.system.exception.AccountAlreadyExistException;
import com.ra.course.ams.airline.manag.system.exception.AccountNotExistException;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.AccountManagementService;
import com.ra.course.ams.airline.manag.system.service.AuthenticationService;
import com.ra.course.ams.airline.manag.system.service.AuthorizationService;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class AccountManagementServiceImpl implements AccountManagementService {

    private Repository<Account, String> accountRepository;
    private AuthorizationService authorizationService;
    private AuthenticationService authenticationService;

    @Override
    public Account createAccount(Account account) {
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
        Collection<Account> accounts = accountRepository.getInstances();
        return new LinkedList<>(accounts);
    }

    @Override
    public void deleteAccount(Account account, Admin admin) {
        authorizationService.checkGrantsForDeleteAccountOperation(account, admin);
        Account accountFromRepo = accountRepository.getInstance(account.getId());
        if (accountFromRepo == null) {
            throw new AccountNotExistException();
        }
        accountRepository.removeInstance(account);
    }

    @Override
    public Account updateStatus(Account account, AccountStatus status, Admin admin) {
        authorizationService.checkGrantsForUpdateAccountOperation(account, admin);
        Account accountFromRepo = accountRepository.getInstance(account.getId());
        if (accountFromRepo == null) {
            throw new AccountNotExistException();
        }
        accountFromRepo.setAccountStatus(status);
        accountRepository.updateInstance(accountFromRepo);
        account.setAccountStatus(status);
        return account;
    }

    @Override
    public Account blockAccount(Account account, Admin admin) {
        authorizationService.checkGrantsForBlockAccountOperation(account, admin);
        Account accountFromRepo = accountRepository.getInstance(account.getId());
        if (accountFromRepo == null) {
            throw new AccountNotExistException();
        }
        accountFromRepo.setAccountStatus(AccountStatus.BLOCKED);
        accountRepository.updateInstance(accountFromRepo);
        account.setAccountStatus(AccountStatus.BLOCKED);
        return account;
    }

    @Override
    public void resetPassword(Account account, String oldPassword, String newPassword) {
        Account realAccountFromDb = authenticationService.login(account.getId(), oldPassword);
        realAccountFromDb.setPassword(newPassword);
        accountRepository.updateInstance(realAccountFromDb);
    }

    @Override
    public void resetPassword(Account account, String newPassword, Admin admin) {
        authorizationService.checkGrantsForResetPasswordOperation(account, admin);
        Account accountFromRepo = accountRepository.getInstance(account.getId());
        if (accountFromRepo == null) {
            throw new AccountNotExistException();
        }
        accountFromRepo.setPassword(newPassword);
        accountRepository.updateInstance(accountFromRepo);
    }

    public Repository<Account, String> getAccountRepository() {
        return accountRepository;
    }

    public void setAccountRepository(Repository<Account, String> accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AuthorizationService getAuthorizationService() {
        return authorizationService;
    }

    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
}
