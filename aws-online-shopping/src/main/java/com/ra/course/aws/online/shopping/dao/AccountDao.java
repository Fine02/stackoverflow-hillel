package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.user.Account;

import java.util.List;

public interface AccountDao {

    Long save(Account account);

    boolean update(Account account);

    boolean remove(Long id);

    Account findById(Long id);

    List<Account> getAll();

}
