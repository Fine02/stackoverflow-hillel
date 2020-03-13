package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.user.Member;

public interface ShippingService {
    Address specifyShippingAddress (Member member, Address address) ;
}
