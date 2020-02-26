package com.ra.course.aws.online.shopping.entity;

import java.util.Date;
import java.util.Map;

public class Order {
    private String orderNumber;
    private OrderStatus status;
    private Date orderDate;

    public Order(String orderNumber, OrderStatus status, Date orderDate) {
        this.orderNumber = orderNumber;
        this.status = status;
        this.orderDate = orderDate;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
