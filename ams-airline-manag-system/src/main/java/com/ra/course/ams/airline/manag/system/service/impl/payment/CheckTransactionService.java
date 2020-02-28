package com.ra.course.ams.airline.manag.system.service.impl.payment;

import com.ra.course.ams.airline.manag.system.entity.payment.Payment;
import com.ra.course.ams.airline.manag.system.service.PaymentService;

public class CheckTransactionService implements PaymentService {

    @Override
    public boolean makeTransaction(final Payment payment) {
        return false;
    }

    @Override
    public boolean refundPayment(final Payment payment) {
        return false;
    }
}
