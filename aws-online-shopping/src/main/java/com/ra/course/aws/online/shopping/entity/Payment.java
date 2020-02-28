package com.ra.course.aws.online.shopping.entity;

public class Payment {
    private PaymentStatus status;
    private double amount;

    public Payment(PaymentStatus status, double amount) {
        this.status = status;
        this.amount = amount;
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

