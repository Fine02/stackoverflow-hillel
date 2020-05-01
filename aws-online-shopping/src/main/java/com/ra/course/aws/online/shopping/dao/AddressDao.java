package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.Address;

public interface AddressDao {

    Long save(Address address);

    boolean update(Address address);

    boolean remove(Long id);

    Address findById(Long id);

}
