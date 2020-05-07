package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.Address;

public interface AddressDao {

    boolean saveAccAdd(Address address, Long accountId);

    boolean saveBillAdd(Address address, Long cardId);

    Address findAccAddById(Long id);

    boolean updateAccAdd(Address address);
}
