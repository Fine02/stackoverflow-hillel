package com.ra.course.aws.online.shopping.entity;

import java.time.LocalDate;
import java.util.Map;

public class Order {
    private String orderNumber;
    private OrderStatus status;
    private LocalDate orderDate;

    public Order(String orderNumber, OrderStatus status, LocalDate orderDate) {
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

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
}
