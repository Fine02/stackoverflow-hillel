package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.exceptions.MemberNotFoundException;
import com.ra.course.aws.online.shopping.exceptions.ShippingAddressNotFoundException;

public interface ShippingService {
    Address specifyShippingAddress (Member member, Address address) throws MemberNotFoundException, ShippingAddressNotFoundException;
}
