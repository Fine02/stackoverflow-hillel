package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.user.Account;

public interface AccountDao {

    Long save (Account account);

    Account findByID (Long id);

    boolean update (Account account);

    boolean delete (Long id);
}
