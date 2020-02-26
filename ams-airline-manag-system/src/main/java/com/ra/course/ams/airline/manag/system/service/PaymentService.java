package com.ra.course.ams.airline.manag.system.service;

import com.ra.course.ams.airline.manag.system.entity.payment.Payment;

public interface PaymentService<T extends Payment> {

    boolean makeTransaction(T t);

}
