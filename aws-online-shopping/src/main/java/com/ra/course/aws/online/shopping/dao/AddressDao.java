package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.Address;

public interface AddressDao {

    Long save(Address address);

    Address findById(Long id);

    boolean update(Address address);

    boolean remove(Long id);

}
