package com.ra.course.aws.online.shopping.entity;

import java.util.Date;

public class OrderLog {
    private Date creationDate;
    private OrderStatus status;

    public OrderLog(Date creationDate, OrderStatus status) {
        this.creationDate = creationDate;
        this.status = status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
