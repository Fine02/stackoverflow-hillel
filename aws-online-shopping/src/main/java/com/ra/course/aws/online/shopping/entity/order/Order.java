package com.ra.course.aws.online.shopping.entity.order;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Order {
    private String orderNumber;
    private OrderStatus status;
    private LocalDate orderDate;
    private List<OrderLog> orderLog;

    public Order() {
    }

    public Order(String orderNumber, OrderStatus status, LocalDate orderDate, List<OrderLog> orderLog) {
        this.orderNumber = orderNumber;
        this.status = status;
        this.orderDate = orderDate;
        this.orderLog = orderLog;
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

    public List<OrderLog> getOrderLog() {
        return orderLog;
    }

    public void setOrderLog(List<OrderLog> orderLog) {
        this.orderLog = orderLog;
    }
}
