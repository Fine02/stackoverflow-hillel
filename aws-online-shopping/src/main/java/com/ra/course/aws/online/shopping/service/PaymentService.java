package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.entity.enums.PaymentStatus;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.entity.payment.CreditCardTransaction;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransaction;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;
import com.ra.course.aws.online.shopping.entity.user.Member;

public interface PaymentService {
    PaymentStatus processPaymentByElectronicBankTransaction(Member member, ElectronicBankTransfer bankTransfer, ElectronicBankTransaction bankTransaction, Double amount);

    PaymentStatus processPaymentByCreditCardTransaction(Member member, CreditCard creditCard, CreditCardTransaction cardTransaction, Double amount);
}
