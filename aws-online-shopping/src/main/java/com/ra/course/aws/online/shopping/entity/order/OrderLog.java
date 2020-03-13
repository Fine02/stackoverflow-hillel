package com.ra.course.aws.online.shopping.entity.order;

import java.time.LocalDateTime;
import java.util.Objects;

public class OrderLog {
    private long id;
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

    public OrderLog(long id, String orderNumber, LocalDateTime creationDate, OrderStatus status) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.creationDate = creationDate;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLog orderLog = (OrderLog) o;
        return Objects.equals(orderNumber, orderLog.orderNumber) &&
                Objects.equals(creationDate, orderLog.creationDate) &&
                status == orderLog.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, creationDate, status);
    }
}
