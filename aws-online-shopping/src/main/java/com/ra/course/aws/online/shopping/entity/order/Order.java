package com.ra.course.aws.online.shopping.entity.order;

import com.ra.course.aws.online.shopping.entity.enums.OrderStatus;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderLog> getOrderLog() {
        return orderLog;
    }

    public void setOrderLog(List<OrderLog> orderLog) {
        this.orderLog = orderLog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderNumber, order.orderNumber) &&
                status == order.status &&
                Objects.equals(orderDate, order.orderDate) &&
                Objects.equals(orderLog, order.orderLog);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, status, orderDate, orderLog);
    }
}
