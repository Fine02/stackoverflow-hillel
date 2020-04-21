package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.dao.PaymentDao;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.enums.AccountStatus;
import com.ra.course.aws.online.shopping.entity.enums.PaymentStatus;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.entity.payment.CreditCardTransaction;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransaction;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.exceptions.PaymentNotProvidedException;
import com.ra.course.aws.online.shopping.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentServiceImplTest {
    private PaymentServiceImpl paymentService;
    private PaymentDao paymentDao = mock(PaymentDao.class);
    private Address address;
    private final Member MEMBER_IN_DB = mockMember(mockAccount());
    private Double amount = 777.77;

    private List<CreditCard> creditCardList = mockListOfCreditCard();
    private CreditCardTransaction creditCardTransaction = new CreditCardTransaction(PaymentStatus.PENDING, amount);

    private List<ElectronicBankTransfer> bankTransfersList = mockListOfBankTransfer();
    private ElectronicBankTransaction electronicBankTransaction = new ElectronicBankTransaction(PaymentStatus.PENDING, amount);


    @BeforeEach
    public void before() {
        paymentService = new PaymentServiceImpl(paymentDao);
        address = new Address("Mira", "Kiyv", "Kyiv", "04114", "Ukraine");
        when(paymentDao.isFoundMemberID(MEMBER_IN_DB.getMemberID())).thenReturn(true);
    }

    @Test
    public void thenTransactionIsNotSuccessfulBecauseOfIncorrectAmountShouldThrowException() {
        Double amount = 0.0;

        Throwable exception = Assertions.assertThrows(PaymentNotProvidedException.class, () -> {
            paymentService.processPaymentByElectronicBankTransaction(MEMBER_IN_DB, electronicBankTransaction, amount);
            paymentService.processPaymentByCreditCardTransaction(MEMBER_IN_DB, creditCardTransaction, amount);
        });

        assertEquals(exception.getMessage(), "payment process is failed");
        assertEquals(exception.getClass(), PaymentNotProvidedException.class);
    }

    @Test
    public void thenTransactionIsNotSuccessfulBecauseOfAbsenceMemberInDbShouldThrowException() {
        when(paymentDao.isFoundMemberID(MEMBER_IN_DB.getMemberID())).thenReturn(false);

        Throwable exception = Assertions.assertThrows(PaymentNotProvidedException.class, () -> {
            paymentService.processPaymentByElectronicBankTransaction(MEMBER_IN_DB, electronicBankTransaction, amount);
            paymentService.processPaymentByCreditCardTransaction(MEMBER_IN_DB, creditCardTransaction, amount);
        });

        assertEquals(exception.getMessage(), "payment process is failed");
        assertEquals(exception.getClass(), PaymentNotProvidedException.class);
    }

    @Test
    public void whenThePaymentByElectronicBankTransactionIsSuccessful() {

        var expectedResult = PaymentStatus.COMPLETED;

        var actualResult = paymentService.processPaymentByElectronicBankTransaction(MEMBER_IN_DB, electronicBankTransaction, amount);

        assertEquals(expectedResult, actualResult);
        verify(paymentDao).createTransaction(electronicBankTransaction);
    }

    @Test
    public void whenThePaymentByCreditCardIsSuccessful() {
        var expectedResult = PaymentStatus.COMPLETED;

        var actualResult = paymentService.processPaymentByCreditCardTransaction(MEMBER_IN_DB, creditCardTransaction, amount);

        assertEquals(expectedResult, actualResult);
        verify(paymentDao).createTransaction(creditCardTransaction);
    }


    private List<CreditCard> mockListOfCreditCard() {
        List<CreditCard> creditCardList = new ArrayList<>();
        creditCardList.add(new CreditCard("VISA", "7777", 4444, address));
        creditCardList.add(new CreditCard("VISA", "4444", 1111, address));
        return creditCardList;
    }

    private List<ElectronicBankTransfer> mockListOfBankTransfer() {
        List<ElectronicBankTransfer> bankTransfers = new ArrayList<>();
        bankTransfers.add(new ElectronicBankTransfer("P888", "7717", "1010"));
        bankTransfers.add(new ElectronicBankTransfer("Y777", "9999", "1456"));
        return bankTransfers;
    }

    private Account mockAccount() {
        Account account = new Account(
                "Ivan",
                "1",
                AccountStatus.ACTIVE,
                "Ann",
                address,
                "sbwbw@sbsb.com",
                "555",
                creditCardList,
                bankTransfersList
        );
        return account;
    }

    private Member mockMember(Account account) {
        Member member = new Member();
        member.setAccount(account);
        member.setMemberID(45);
        return member;
    }

}
