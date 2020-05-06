package com.ra.course.aws.online.shopping.entity.payment;

import com.ra.course.aws.online.shopping.entity.enums.PaymentStatus;

public class CreditCardTransaction extends Payment {

    public CreditCardTransaction(PaymentStatus status, double amount) {
        super(status, amount);
    }

}
