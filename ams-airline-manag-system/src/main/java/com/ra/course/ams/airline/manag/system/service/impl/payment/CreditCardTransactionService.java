package com.ra.course.ams.airline.manag.system.service.impl.payment;

import com.ra.course.ams.airline.manag.system.entity.payment.CreditCardTransaction;
import com.ra.course.ams.airline.manag.system.entity.payment.Payment;
import com.ra.course.ams.airline.manag.system.entity.payment.PaymentStatus;
import com.ra.course.ams.airline.manag.system.service.PaymentService;

public class CreditCardTransactionService implements PaymentService <CreditCardTransaction> {

    @Override
    public boolean makeTransaction(final CreditCardTransaction payment) {
        if (payment.getPaimentId() == 0 || payment.getAmound() == 0  || payment.getNameOnCard().isBlank()) {
            payment.setStaus(PaymentStatus.FAILED);
            throw  new IllegalArgumentException("Payment fields can't de empty");
        } else {
            payment.setStaus(PaymentStatus.COMPLETED);
        }
        return true;
    }
}
