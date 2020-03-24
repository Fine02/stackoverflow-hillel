package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.entity.payment.CreditCardTransaction;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransaction;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;
import com.ra.course.aws.online.shopping.entity.user.Member;
import java.util.List;

public interface PaymentDao {
    Member foundMemberById(Long memberId);

    List<ElectronicBankTransfer> foundListOfBankTransfer(List<ElectronicBankTransfer> bankTransfers);

    List<CreditCard> foundListOfCreditCard(List<CreditCard> creditCards);

    void update(ElectronicBankTransaction bankTransaction);

    void update(CreditCardTransaction cardTransaction);
}
