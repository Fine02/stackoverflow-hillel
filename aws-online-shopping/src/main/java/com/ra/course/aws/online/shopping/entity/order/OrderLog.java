package com.ra.course.aws.online.shopping.entity.order;

import java.time.LocalDateTime;

public class OrderLog {
    private String orderNumber;
    private LocalDateTime creationDate;
    private OrderStatus status;

    public OrderLog() {
    }

    public OrderLog(LocalDateTime creationDate, OrderStatus status) {
        this.creationDate = creationDate;
        this.status = status;
    }

    public OrderLog(String orderNumber, LocalDateTime creationDate, OrderStatus status) {
        this.orderNumber = orderNumber;
        this.creationDate = creationDate;
        this.status = status;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
