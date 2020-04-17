package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.AwsOnlineShoppingApplication;
import com.ra.course.aws.online.shopping.TestConfig;
import com.ra.course.aws.online.shopping.dao.AccountDao;
import com.ra.course.aws.online.shopping.dao.ProductDao;
import com.ra.course.aws.online.shopping.dao.ShoppingCartDao;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@SpringBootTest(classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("local")
public class PaymentServiceImplIntegrationTest {
    @Primary
    @Bean
    public AccountDao mockedAccountDao() {
        return mock(AccountDao.class);
    }

    @Primary
    @Bean
    public ProductDao mockedProductDao() {
        return mock(ProductDao.class);
    }

    @Primary
    @Bean
    public ShoppingCartDao mockedShoppingCartDao() {
        return mock(ShoppingCartDao.class);
    }

    @Autowired
    private PaymentService paymentService;

    Address address = new Address("Mira, 8", "Kyiv", "Kyiv", "14004", "Ukraine");
    private Double amount = 777.77;

    public ElectronicBankTransfer transferInDb = new ElectronicBankTransfer("GermanBank", "5265", "8542");
    public CreditCard creditCardInDb = new CreditCard("VISA", "5584",5662, address);
    private ElectronicBankTransaction electronicBankTransaction = new ElectronicBankTransaction(PaymentStatus.PENDING, amount);
    private CreditCardTransaction creditCardTransaction = new CreditCardTransaction(PaymentStatus.PENDING, amount);

    private Account accountWithExistData = makeAccount(makeListOfCreditCard(), makeElectronicBankTransferList());
    private Member memberWithExistData = makeMember(accountWithExistData);

    @Test
    public void shouldThrowExceptionIfTransactionIsNotSuccessful() {
        ElectronicBankTransfer transferNotExistInDb = new ElectronicBankTransfer("VBank", "1122", "5212");
        CreditCard creditCardNotExistInDb = new CreditCard("VISA", "5655",5662, address);

        Throwable exception = Assertions.assertThrows(PaymentNotProvidedException.class, () -> {
            paymentService.processPaymentByElectronicBankTransaction(memberWithExistData, transferNotExistInDb, electronicBankTransaction, amount);
            paymentService.processPaymentByCreditCardTransaction(memberWithExistData, creditCardNotExistInDb, creditCardTransaction, amount);
        });

        assertEquals(exception.getMessage(), "payment process is failed");
        assertEquals(exception.getClass(), PaymentNotProvidedException.class);
    }

    @Test
    public void whenThePaymentByElectronicBankTransactionIsSuccessful() {
        var expectedResult = PaymentStatus.COMPLETED;
        var actualResult = paymentService.processPaymentByElectronicBankTransaction(memberWithExistData, transferInDb, electronicBankTransaction, amount);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void whenThePaymentByCreditCardIsSuccessful() {
        var expectedResult = PaymentStatus.COMPLETED;
        var actualResult = paymentService.processPaymentByCreditCardTransaction(memberWithExistData, creditCardInDb, creditCardTransaction, amount);
        assertEquals(expectedResult, actualResult);
    }

    public List<ElectronicBankTransfer> makeElectronicBankTransferList() {
        List<ElectronicBankTransfer> transferList = new ArrayList<>();
        transferList.add(transferInDb);
        transferList.add(transferInDb);
        transferList.add(transferInDb);
        return transferList;
    }

    private List<CreditCard> makeListOfCreditCard() {
        List<CreditCard> creditCardList = new ArrayList<>();
        creditCardList.add(creditCardInDb);
        return creditCardList;
    }

    private Account makeAccount( List<CreditCard> cardList, List<ElectronicBankTransfer> bankTransfers) {
        Account account = new Account(
                1L,
                "Ivan",
                "1",
                AccountStatus.ACTIVE,
                "Ann",
                address,
                "111j@gmail.com",
                "38012345111",
                cardList,
                bankTransfers
        );
        return account;
    }
    
    private Member makeMember(Account account) {
        Member member = new Member(account);
        member.setMemberID(1L);
        return member;
    }

}
