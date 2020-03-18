package com.ra.course.aws.online.shopping.entity.payment;

import com.ra.course.aws.online.shopping.entity.enums.PaymentStatus;

public class Payment {
    private Long id;
    private PaymentStatus status;
    private double amount;

    public Payment() {
    }

    public Payment(PaymentStatus status, double amount) {
        this.status = status;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

