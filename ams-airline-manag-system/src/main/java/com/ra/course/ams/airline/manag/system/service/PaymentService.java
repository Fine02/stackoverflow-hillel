package com.ra.course.ams.airline.manag.system.service;

public interface PaymentService {

    int paymentByCreditCard(int amount, String nameOnCard);

    int paymentByCheckTransaction(int amount, String bankName, String checkNumber);

    int paymentByCash(int amount, double cashTendered);

}
