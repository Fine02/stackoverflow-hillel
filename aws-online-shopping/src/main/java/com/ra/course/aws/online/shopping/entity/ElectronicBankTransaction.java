package com.ra.course.aws.online.shopping.entity;

public class ElectronicBankTransaction extends Payment {

    public ElectronicBankTransaction(PaymentStatus status, double amount) {
        super(status, amount);
    }
}
