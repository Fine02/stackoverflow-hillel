package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.entity.enums.PaymentStatus;
import com.ra.course.aws.online.shopping.entity.payment.CreditCardTransaction;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransaction;
import com.ra.course.aws.online.shopping.entity.user.Member;

public interface PaymentService {

    PaymentStatus processPaymentByElectronicBankTransaction(Member member,  ElectronicBankTransaction bankTransaction, Double amount );

    PaymentStatus processPaymentByCreditCardTransaction(Member member,  CreditCardTransaction cardTransaction, Double amount);
}
