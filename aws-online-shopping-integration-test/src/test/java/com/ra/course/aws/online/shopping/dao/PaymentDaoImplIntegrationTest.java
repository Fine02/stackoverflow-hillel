package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.AwsOnlineShoppingApplication;
import com.ra.course.aws.online.shopping.TestConfig;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.enums.PaymentStatus;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.entity.payment.CreditCardTransaction;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransaction;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;
import com.ra.course.aws.online.shopping.entity.user.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

@SpringBootTest(classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("local")
public class PaymentDaoImplIntegrationTest {

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
    private PaymentDao paymentDao;

    Address address = new Address("Mira, 8", "Kyiv", "Kyiv", "14004", "Ukraine");
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
      //  creditCardList.add(creditCard);
        return creditCardList;
    }

    public ElectronicBankTransfer transfer = makeBankTransfer("GermanBank", "5265", "8542");
    public CreditCard creditCard = makeCreditCard("VISA", "5584", 5662, address);
    private ElectronicBankTransaction electronicBankTransaction = new ElectronicBankTransaction(PaymentStatus.PENDING, amount);
    private CreditCardTransaction creditCardTransaction = new CreditCardTransaction(PaymentStatus.PENDING, amount);

    //work correct
    @Test
    public void getInstanceOfFoundMemberTest() {
        Member member = paymentDao.foundMemberById(2L);
        System.out.println(member);
    }

    //work correct
    @Test
    public void getInstanceOfFindingListOfElectronicBankTransferTest() {
        List<ElectronicBankTransfer> result = paymentDao.foundListOfBankTransfer(makeElectronicBankTransferList());
        System.out.println(result);
    }

    //work correct
    @Test
    public void getInstanceOfFindingListOfCreditCardTest() {
        List<CreditCard> result = paymentDao.foundListOfCreditCard(makeListOfCreditCard());
        System.out.println(result);
    }
    //work correct
    @Test
    public void isListContainsTheObject(){
        List<CreditCard> result = paymentDao.foundListOfCreditCard(makeListOfCreditCard());
        boolean a =result.contains(creditCard);
        System.out.println(a);
    }

    //work correct
    @Test
    public void createElectronicBankTransactionTest() {
        paymentDao.createTransaction(electronicBankTransaction);
    }

    //work correct
    @Test
    public void createCreditCardTransactionTest() {
        paymentDao.createTransaction(creditCardTransaction);
    }


    private CreditCard makeCreditCard(String nameOnCard, String cardNumber, int code, Address billingAddress) {
        return new CreditCard(nameOnCard, cardNumber, code, billingAddress);
    }

    private ElectronicBankTransfer makeBankTransfer(String bankName, String routingNumber, String accountNumber) {
        return new ElectronicBankTransfer(bankName, routingNumber, accountNumber);
    }
}
