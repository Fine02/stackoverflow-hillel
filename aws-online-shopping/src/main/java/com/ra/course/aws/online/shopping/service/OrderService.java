package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.exceptions.MemberNotFoundException;
import com.ra.course.aws.online.shopping.exceptions.OrderNotFoundException;

public interface OrderService {
    Order cancelOrder (Order order, Member member) throws MemberNotFoundException, OrderNotFoundException;
}
