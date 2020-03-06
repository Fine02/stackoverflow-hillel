package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.order.OrderStatus;

public interface OrderDao {
     Order addOrder();
     Order findByOrderNumber(String orderNumber);
     OrderStatus removeOrder(String orderNumber);
     Order updateOrder(String orderNumber);

}
