package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.payment.CreditCardTransaction;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransaction;

public interface PaymentDao {

    void createTransaction(ElectronicBankTransaction bankTransaction);

    void createTransaction(CreditCardTransaction cardTransaction);

    boolean isFoundMemberID(Long id);
}
