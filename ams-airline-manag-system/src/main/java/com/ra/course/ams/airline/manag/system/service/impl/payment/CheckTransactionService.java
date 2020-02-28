package com.ra.course.ams.airline.manag.system.service.impl.payment;

import com.ra.course.ams.airline.manag.system.entity.payment.CheckTransaction;
import com.ra.course.ams.airline.manag.system.service.PaymentService;

public class CheckTransactionService implements PaymentService <CheckTransaction> {

    @Override
    public boolean makeTransaction(CheckTransaction payment) {
        if (payment.getPaimentId() == 0 || payment.getBankName().isBlank()  ||
            payment.getCheckNumber().isBlank() || payment.getAmound() == 0) {

        }
        return false;
    }

    @Override
    public boolean refundPayment(CheckTransaction payment) {
        return false;
    }
}
