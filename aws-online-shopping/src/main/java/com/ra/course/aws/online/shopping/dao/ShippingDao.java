package com.ra.course.aws.online.shopping.dao;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.user.Member;

public interface ShippingDao {
    boolean findShippingAddress(Address address);

    boolean isFoundMemberID(Long id);

    void updateShippingAddress(Member address);
}
