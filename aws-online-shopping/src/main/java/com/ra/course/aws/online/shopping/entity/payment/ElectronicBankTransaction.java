package com.ra.course.aws.online.shopping.entity.payment;

import com.ra.course.aws.online.shopping.entity.enums.PaymentStatus;

public class ElectronicBankTransaction extends Payment {

    public ElectronicBankTransaction(PaymentStatus status, double amount) {
        super(status, amount);
    }
}
