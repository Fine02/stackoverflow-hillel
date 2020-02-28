package com.ra.course.ams.airline.manag.system.service.impl.payment;

import com.ra.course.ams.airline.manag.system.entity.payment.CheckTransaction;
import com.ra.course.ams.airline.manag.system.entity.payment.PaymentStatus;
import com.ra.course.ams.airline.manag.system.service.PaymentService;

public class CheckTransactionService implements PaymentService <CheckTransaction> {

    @Override
    public boolean makeTransaction(CheckTransaction payment) {
        if (payment.getPaimentId() == 0 || payment.getAmound() == 0  ||
            payment.getCheckNumber().isBlank() || payment.getBankName().isBlank() ) {
            payment.setStaus(PaymentStatus.FAILED);
            throw  new IllegalArgumentException("Payment fields can't de empty");
        } else {
            payment.setStaus(PaymentStatus.COMPLETED);
        }
        return true;
    }
}
