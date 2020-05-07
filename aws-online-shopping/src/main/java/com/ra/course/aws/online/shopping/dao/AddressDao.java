package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.Address;

public interface AddressDao {

    Long saveAccAdd(Address address, Long accountId);

    Long saveBillAdd(Address address, Long cardId);

    boolean update(Address address);

    Address findById(Long id);
}
