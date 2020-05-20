package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.AwsOnlineShoppingApplication;
import com.ra.course.aws.online.shopping.TestConfig;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("test")
@Sql({"classpath:schema.sql", "classpath:data.sql"})
public class PaymentServiceImplIntegrationTest {

    @Autowired
    private PaymentService paymentService;

    Address address = new Address("Mira, 8", "Kyiv", "Kyiv", "14004", "Ukraine");
    private Double amount = 859.77;

    private ElectronicBankTransaction electronicBankTransaction = new ElectronicBankTransaction(PaymentStatus.PENDING, amount);
    private CreditCardTransaction creditCardTransaction = new CreditCardTransaction(PaymentStatus.PENDING, amount);

    private Member memberWithExistData = makeMember(1L);
    private Member memberAbsenceInDb = makeMember(565L);

    @Test
    @Rollback
    public void thenTransactionIsNotSuccessfulBecauseOfIncorrectAmountShouldThrowException() {
        Double amount = 0.0;

        Throwable exception = Assertions.assertThrows(PaymentNotProvidedException.class, () -> {
            paymentService.processPaymentByElectronicBankTransaction(memberWithExistData, electronicBankTransaction, amount);
            paymentService.processPaymentByCreditCardTransaction(memberWithExistData, creditCardTransaction, amount);
        });

        assertEquals(exception.getMessage(), "payment process is failed");
        assertEquals(exception.getClass(), PaymentNotProvidedException.class);
    }

    @Test
    @Rollback
    public void thenTransactionIsNotSuccessfulBecauseOfAbsenceMemberInDbShouldThrowException() {

        Throwable exception = Assertions.assertThrows(PaymentNotProvidedException.class, () -> {
            paymentService.processPaymentByElectronicBankTransaction(memberAbsenceInDb, electronicBankTransaction, amount);
            paymentService.processPaymentByCreditCardTransaction(memberAbsenceInDb, creditCardTransaction, amount);
        });

        assertEquals(exception.getMessage(), "payment process is failed");
        assertEquals(exception.getClass(), PaymentNotProvidedException.class);
    }

    @Test
    @Rollback
    public void whenThePaymentByElectronicBankTransactionIsSuccessful() {
        var expectedResult = PaymentStatus.COMPLETED;
        electronicBankTransaction.setAmount(amount);
        var actualResult = paymentService.processPaymentByElectronicBankTransaction(memberWithExistData, electronicBankTransaction, amount);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @Rollback
    public void whenThePaymentByCreditCardIsSuccessful() {
        var expectedResult = PaymentStatus.COMPLETED;
        var actualResult = paymentService.processPaymentByCreditCardTransaction(memberWithExistData, creditCardTransaction, amount);
        assertEquals(expectedResult, actualResult);
    }

    private Account makeAccount() {
        List<CreditCard> creditCardList = new ArrayList<>();
        creditCardList.add(new CreditCard("VISA", "77", 44, address));
        List<ElectronicBankTransfer> bankTransfers = new ArrayList<>();
        bankTransfers.add(new ElectronicBankTransfer("P8", "77", "10"));
        Account account = new Account(
                "Ivan",
                "1",
                AccountStatus.ACTIVE,
                "Ann",
                address,
                "111j@gmail.com",
                "38012345111",
                creditCardList,
                bankTransfers
        );
        return account;
    }

    private Member makeMember(Long id) {
        Member member = new Member(makeAccount());
        member.setMemberID(id);
        return member;
    }
}
