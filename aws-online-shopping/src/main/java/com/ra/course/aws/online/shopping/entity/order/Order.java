package com.ra.course.aws.online.shopping.entity.order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private String orderNumber;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private List<OrderLog> orderLog;

    public Order() {
    }

    public Order(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Order(List<OrderLog> orderLog) {
        this.orderLog = orderLog;
    }

    public Order(String orderNumber, OrderStatus status, LocalDateTime orderDate) {
        this.orderNumber = orderNumber;
        this.status = status;
        this.orderDate = orderDate;
    }

    public Order(String orderNumber, OrderStatus status, LocalDateTime orderDate, List<OrderLog> orderLog) {
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

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderLog> getOrderLog() {
        return orderLog;
    }

    public void setOrderLog(List<OrderLog> orderLog) {
        this.orderLog = orderLog;
    }
}
