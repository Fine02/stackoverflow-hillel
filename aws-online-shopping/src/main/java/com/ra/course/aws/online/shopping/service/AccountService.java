package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.entity.user.Account;

public interface AccountService {

    Long save(Account account);

    Account findById(Long id);

    boolean update(Account account);

    boolean delete(Long id);

}
