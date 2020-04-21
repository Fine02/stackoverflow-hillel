package com.ra.course.aws.online.shopping.service.impl;

import com.ra.course.aws.online.shopping.dao.PaymentDao;
import com.ra.course.aws.online.shopping.entity.enums.PaymentStatus;
import com.ra.course.aws.online.shopping.entity.payment.*;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.exceptions.PaymentNotProvidedException;
import com.ra.course.aws.online.shopping.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    private transient final PaymentDao paymentDao;
    private transient final static String MESSAGE = "payment process is failed";

    public PaymentServiceImpl(final PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }


    @Override
    public PaymentStatus processPaymentByElectronicBankTransaction(final Member member,  final ElectronicBankTransaction bankTransaction, final Double amount ) {
        if (amount>0 & paymentDao.isFoundMemberID(member.getMemberID())) {
            bankTransaction.setAmount(amount);
            bankTransaction.setStatus(PaymentStatus.COMPLETED);
            paymentDao.createTransaction(bankTransaction);
            return bankTransaction.getStatus();
        }
        throw new PaymentNotProvidedException(MESSAGE);
    }

    @Override
    public PaymentStatus processPaymentByCreditCardTransaction(final Member member,  final CreditCardTransaction cardTransaction,  final Double amount) {
        if (amount>0 & paymentDao.isFoundMemberID(member.getMemberID())) {
            cardTransaction.setAmount(amount);
            cardTransaction.setStatus(PaymentStatus.COMPLETED);
            paymentDao.createTransaction(cardTransaction);
            return cardTransaction.getStatus();
        }
        throw new PaymentNotProvidedException(MESSAGE);
    }
}
