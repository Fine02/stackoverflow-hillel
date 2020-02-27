package com.ra.course.aws.online.shopping.entity;

public class Payment {

    PaymentStatus status;
    double amount;

    public Payment(PaymentStatus status) {
        this.status = status;
    }
    public double getAmount() {
        return amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }
    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
