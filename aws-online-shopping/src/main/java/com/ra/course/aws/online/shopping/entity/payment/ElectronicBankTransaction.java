package com.ra.course.aws.online.shopping.entity.payment;

public class ElectronicBankTransaction extends Payment {

    public ElectronicBankTransaction(PaymentStatus status, double amount) {
        super(status, amount);
    }
}
