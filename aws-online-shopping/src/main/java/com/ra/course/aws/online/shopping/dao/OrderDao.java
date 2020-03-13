package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;

import java.util.List;

public interface OrderDao {
    //Order findByOrderNumber(String orderNumber);

    void updateOrder(Order orderNumber);

    boolean isFoundMemberID(Long id);

    Order findByOrderNumber(String orderNumber);

    List<OrderLog> findLogListByOrder(List<OrderLog> orderLogList);

    boolean isThisOrderLogExist(OrderLog orderLog);

    void addOrderLog(boolean add);

    OrderLog findOrderLogById(Long orderLogId);

   // void updateOrder(Order order);

}
