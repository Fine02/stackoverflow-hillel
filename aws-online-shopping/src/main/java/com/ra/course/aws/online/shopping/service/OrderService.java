package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import com.ra.course.aws.online.shopping.entity.user.Member;

import java.util.List;

public interface OrderService {
    Order cancelOrder(Order order, Member member);

    List<OrderLog> getOrderTrack(String orderNumber);

    boolean addOrderLogToOrder(Order order, OrderLog orderLog);

    boolean sendForShipment(Order Order);
}
