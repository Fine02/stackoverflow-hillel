package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.entity.user.Account;

public interface AccountService {

    Long save(Account account);

    boolean update(Account account);

    boolean remove(Long id);

    Account findById(Long id);

}
