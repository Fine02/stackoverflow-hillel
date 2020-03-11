package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.order.Order;

public interface OrderDao {
    Order findByOrderNumber(String orderNumber);

    void updateOrder(Order orderNumber);

    boolean isFoundMemberID(Long id);
}
