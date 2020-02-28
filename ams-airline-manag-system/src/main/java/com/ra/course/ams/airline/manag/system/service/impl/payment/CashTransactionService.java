package com.ra.course.ams.airline.manag.system.service.impl.payment;

import com.ra.course.ams.airline.manag.system.entity.payment.CashTransaction;
import com.ra.course.ams.airline.manag.system.entity.payment.PaymentStatus;
import com.ra.course.ams.airline.manag.system.service.PaymentService;

public class CashTransactionService implements PaymentService <CashTransaction> {

    @Override
    public boolean makeTransaction(CashTransaction payment) {
        if (payment.getPaimentId() == 0 || payment.getAmound() == 0  || payment.getCashTendered() == 0) {
            payment.setStaus(PaymentStatus.FAILED);
            return false;
        } else {
            payment.setStaus(PaymentStatus.COMPLETED);
        }
        return true;
    }
}
