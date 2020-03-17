package com.ra.course.aws.online.shopping.service.impl;

import com.ra.course.aws.online.shopping.dao.AccountDao;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.service.AccountService;

public class AccountServiceImpl implements AccountService {
    private transient final AccountDao accountDao;

    public AccountServiceImpl(final AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Long save(final Account account) {
        return accountDao.save(account);
    }

    @Override
    public Account findById(final Long id) {
        return accountDao.findByID(id);
    }

    @Override
    public boolean update(final Account account) {
        return accountDao.update(account);
    }

    @Override
    public boolean delete(final Long id) {
        return accountDao.delete(id);
    }
}
