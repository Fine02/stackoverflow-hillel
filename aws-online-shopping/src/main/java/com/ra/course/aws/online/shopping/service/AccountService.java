package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.entity.enums.AccountStatus;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.entity.user.Member;

public interface AccountService {

    Long create(Account account);

    Account findById(Long id);

    boolean update(Account account);

    boolean delete(Account account);

}
