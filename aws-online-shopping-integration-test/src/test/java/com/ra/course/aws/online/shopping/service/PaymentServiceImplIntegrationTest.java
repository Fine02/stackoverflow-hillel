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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("local")
public class PaymentServiceImplIntegrationTest {

    @Autowired
    private PaymentService paymentService;

    Address address = new Address("Mira, 8", "Kiyv", "Kyiv", "14004", "Ukraine");
    private Double amount = 777.77;

    public List<ElectronicBankTransfer> makeElectronicBankTransferList() {
        List<ElectronicBankTransfer> transferList = new ArrayList<>();
        transferList.add(transfer);
        transferList.add(transfer);
        transferList.add(transfer);
        return transferList;
    }

    private List<CreditCard> makeListOfCreditCard() {
        List<CreditCard> creditCardList = new ArrayList<>();
        creditCardList.add(creditCard);
        creditCardList.add(creditCard);
        return creditCardList;
    }

    public ElectronicBankTransfer transfer = makeBankTransfer("GermanBank", "5265", "8542");
    public CreditCard creditCard = makeCreditCard ("VISA", "5584",5662,address);
    private ElectronicBankTransaction electronicBankTransaction = new ElectronicBankTransaction(PaymentStatus.PENDING, amount);
    private CreditCardTransaction creditCardTransaction = new CreditCardTransaction(PaymentStatus.PENDING, amount);


   // private Account accountWithNotExistData = makeAccount(emailNotExist, phoneNotExist);
    //private Member memberWithNotExistData = makeMember(accountWithNotExistData);

    private Account accountExist = makeAccount();
    private Member memberExist = makeMember(accountExist);

//    @Test
//    public void whenThePaymentByElectronicBankTransactionIsSuccessful() {
//        var expectedResult = PaymentStatus.COMPLETED;
//
//        var actualResult = paymentService.processPaymentByElectronicBankTransaction(MEMBER_IN_DB, bankTransfer, electronicBankTransaction, amount);
//
//        assertEquals(expectedResult, actualResult);
//        verify(paymentDao).createTransaction(electronicBankTransaction);
//    }
//
    @Test
    public void whenThePaymentByCreditCardIsSuccessful() {
        var expectedResult = PaymentStatus.COMPLETED;

        var actualResult = paymentService.processPaymentByCreditCardTransaction(memberExist, creditCard, creditCardTransaction, amount);

        assertEquals(expectedResult, actualResult);
        //verify(paymentDao).createTransaction(creditCardTransaction);
    }

    private CreditCard makeCreditCard(String nameOnCard, String cardNumber, int code, Address billingAddress) {
        return new CreditCard(nameOnCard, cardNumber, code, billingAddress);
    }

    private ElectronicBankTransfer makeBankTransfer(String bankName, String routingNumber, String accountNumber) {
        return new ElectronicBankTransfer(bankName, routingNumber, accountNumber);
    }

    private Account makeAccount() {
        List<CreditCard> creditCardList = new ArrayList<>();
        creditCardList.add(new CreditCard("VISA", "77", 44, address));
        List<ElectronicBankTransfer> electronicBankTransfers = new ArrayList<>();
        electronicBankTransfers.add(new ElectronicBankTransfer("P8", "77", "10"));
        Account account = new Account(
                "Ivan",
                "1",
                AccountStatus.ACTIVE,
                "Ann",
                address,
                "111j@gmail.com",
                "38012345111",
                creditCardList,
                electronicBankTransfers
        );
        return account;
    }


    private Member makeMember(Account account) {
        Member member = new Member(account);
        return member;
    }

}
