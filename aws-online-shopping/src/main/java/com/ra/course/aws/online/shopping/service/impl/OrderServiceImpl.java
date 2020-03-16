package com.ra.course.aws.online.shopping.service.impl;

import com.ra.course.aws.online.shopping.dao.OrderDao;
import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import com.ra.course.aws.online.shopping.entity.order.OrderStatus;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.exceptions.MemberDataNotFoundException;
import com.ra.course.aws.online.shopping.exceptions.OrderIsAlreadyShippedException;
import com.ra.course.aws.online.shopping.exceptions.OrderLogIsAlreadyExistException;
import com.ra.course.aws.online.shopping.exceptions.OrderNotFoundException;
import com.ra.course.aws.online.shopping.service.OrderService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private transient final OrderDao orderDao;

    public OrderServiceImpl(final OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public Order cancelOrder(final Order order, final Member member) {
        if (orderDao.isFoundMemberID(member.getMemberID())) {
            final var foundOrder = orderDao.findByOrderNumber(order.getOrderNumber());
            if (foundOrder.getOrderDate().isBefore(LocalDateTime.now())) {
                foundOrder.setStatus(OrderStatus.CANCELED);
                order.setStatus(OrderStatus.CANCELED);
                orderDao.updateOrder(foundOrder);
                return order;
            }
            throw new OrderNotFoundException("There is not found the Order by this number");
        }
        throw new MemberDataNotFoundException("There is not found the Member by this ID");
    }

    @Override
    public List<OrderLog> getOrderTrack(final String orderNumber) {
        final var foundOrder = orderDao.findByOrderNumber(orderNumber);
        if (foundOrder != null) {
            return orderDao.findLogListByOrder(foundOrder.getOrderLog());
        }
        return Collections.emptyList();
    }

    @Override
    public boolean addOrderLogToOrder(final Order order, final OrderLog orderLog) {
        final var foundOrder = orderDao.findByOrderNumber(order.getOrderNumber());
        if (foundOrder.getOrderLog().contains(orderLog)) {
            throw new OrderLogIsAlreadyExistException("This OrderLog is already exist");
        }
        final var foundOrderList = orderDao.findLogListByOrder(order.getOrderLog());
        orderDao.addOrderLog(foundOrderList.add(orderLog));
        foundOrder.setStatus(orderLog.getStatus());
        orderDao.updateOrder(foundOrder);
        return true;
    }

    @Override
    public boolean sendForShipment(final Order order) {
        if (order.getStatus() != OrderStatus.SHIPPED) {
            order.setStatus(OrderStatus.SHIPPED);
            orderDao.updateOrder(order);
            return true;
        }
        throw new OrderIsAlreadyShippedException("This Order is already shipped");
    }
}
