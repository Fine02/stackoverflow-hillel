package com.ra.course.aws.online.shopping.service.impl;

import com.ra.course.aws.online.shopping.dao.AccountDao;
import com.ra.course.aws.online.shopping.dao.Dao;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.exceptions.AccountNotFoundException;
import com.ra.course.aws.online.shopping.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private transient final AccountDao accountDao;

    public AccountServiceImpl(final AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Long save(final Account newAccount) {
        return accountDao.save(newAccount);
    }

    @Override
    public boolean update(final Account accountToUpdate) {
        Optional.ofNullable(accountDao.findById(accountToUpdate.getId()))
                .ifPresentOrElse(account -> accountDao.update(accountToUpdate), () -> {
                    throw new AccountNotFoundException("Account with id=" + accountToUpdate.getId() + " not found");
                });
        return true;
    }

    @Override
    public boolean remove(final Long accountId) {
        Optional.ofNullable(accountDao.findById(accountId))
                .ifPresentOrElse(account -> accountDao.remove(accountId), () -> {
                    throw new AccountNotFoundException("Account with id = " + accountId + " not found.");
                });
        return true;
    }

    @Override
    public Account findById(final Long id) {
        return accountDao.findById(id);
    }
}
