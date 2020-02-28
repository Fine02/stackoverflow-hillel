package com.ra.course.aws.online.shopping.entity;

public class CreditCardTransaction extends Payment{

    public CreditCardTransaction(PaymentStatus status, double amount) {
        super(status, amount);
    }
}
