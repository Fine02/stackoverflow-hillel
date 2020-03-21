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
    private double amount = 777.77;

    private CreditCard creditCard = mockCreditCard("VISA", "7777", 4444, address);
    private List<CreditCard> creditCardList = mockListOfCreditCard();
    private CreditCardTransaction creditCardTransaction = new CreditCardTransaction(PaymentStatus.PENDING, amount);

    private ElectronicBankTransfer bankTransfer = mockBankTransfer("P888", "7717", "1010");
    private List<ElectronicBankTransfer> bankTransfersList = mockListOfBankTransfer();
    private ElectronicBankTransaction electronicBankTransaction = new ElectronicBankTransaction(PaymentStatus.PENDING, amount);


    @BeforeEach
    public void before() {
        paymentService = new PaymentServiceImpl(paymentDao);
        address = new Address("Mira", "Kiyv", "Kyiv", "04114", "Ukraine");
        when(paymentDao.foundMemberById(MEMBER_IN_DB.getMemberID())).thenReturn(MEMBER_IN_DB);
        when(paymentDao.foundListOfBankTransfer(MEMBER_IN_DB.getAccount().getElectronicBankTransferList())).thenReturn(bankTransfersList);
        when(paymentDao.foundListOfCreditCard(MEMBER_IN_DB.getAccount().getCreditCardList())).thenReturn(creditCardList);
    }

    @Test
    public void shouldThrowExceptionIfTransactionIsNotSuccessful() {
        var wrongBankTransfer = mockBankTransfer("P0007", "56554", "7845");
        var wrongCreditCard = mockCreditCard("VISA", "6578", 8545, address);
        Throwable exception = Assertions.assertThrows(PaymentNotProvidedException.class, () -> {
            paymentService.processPaymentByElectronicBankTransaction(MEMBER_IN_DB, wrongBankTransfer, electronicBankTransaction);
            paymentService.processPaymentByCreditCardTransaction(MEMBER_IN_DB, wrongCreditCard, creditCardTransaction);
        });

        assertEquals(exception.getMessage(), "payment process is failed");
        assertEquals(exception.getClass(), PaymentNotProvidedException.class);
    }

    @Test
    public void whenThePaymentByElectronicBankTransactionIsSuccessful() {
        var expectedResult = PaymentStatus.COMPLETED;

        var actualResult = paymentService.processPaymentByElectronicBankTransaction(MEMBER_IN_DB, bankTransfer, electronicBankTransaction);

        assertEquals(expectedResult, actualResult);
        verify(paymentDao).update(electronicBankTransaction);
    }

    @Test
    public void whenThePaymentByCreditCardIsSuccessful() {
        var expectedResult = PaymentStatus.COMPLETED;

        var actualResult = paymentService.processPaymentByCreditCardTransaction(MEMBER_IN_DB, creditCard, creditCardTransaction);

        assertEquals(expectedResult, actualResult);
        verify(paymentDao).update(creditCardTransaction);
    }


    private CreditCard mockCreditCard(String nameOnCard, String cardNumber, int code, Address billingAddress) {
        return new CreditCard(nameOnCard, cardNumber, code, billingAddress);
    }

    private ElectronicBankTransfer mockBankTransfer(String bankName, String routingNumber, String accountNumber) {
        return new ElectronicBankTransfer(bankName, routingNumber, accountNumber);
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
