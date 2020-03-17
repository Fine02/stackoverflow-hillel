package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.entity.enums.PaymentStatus;
import com.ra.course.aws.online.shopping.entity.user.Account;

public interface PaymentService {
    PaymentStatus makeCreditCardPayment(Account account);
    PaymentStatus makeBankTransferPayment(Account account);
}
