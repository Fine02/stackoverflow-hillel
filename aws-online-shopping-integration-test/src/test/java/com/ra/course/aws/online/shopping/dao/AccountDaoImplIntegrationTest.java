package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.AwsOnlineShoppingApplication;
import com.ra.course.aws.online.shopping.TestConfig;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.enums.AccountStatus;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;
import com.ra.course.aws.online.shopping.entity.user.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("local")
@Sql(scripts = {"classpath:schema.sql", "classpath:test-data.sql"})
public class AccountDaoImplIntegrationTest {

    @Autowired
    private AccountDao accountDao;
    private Account account;
    private Address address;
    private Address address1;
    private Address address2;
    private Address address3;

    @BeforeEach
    void setUp() {
        address = new Address("Garmatna", "Kyiv", "Kyiv", "01135", "Ukraine");
        address1 = new Address("Bohatyrska", "Lviv", "Lviv", "11111", "Ukraine1");
        address2 = new Address("Gonchara", "Dnipro", "Dnipro", "22222", "Ukraine2");
        address3 = new Address("Kurbasa", "Minsk", "Poltava", "333333", "Ukraine3");
        CreditCard card1 = new CreditCard("Roger", "5525694123698541", 145, address2);
        CreditCard card2 = new CreditCard("Roger", "5525126354798541", 751, address3);
        ElectronicBankTransfer transfer1 = new ElectronicBankTransfer("PrivatBank", "1225745123698541", "125647893");
        ElectronicBankTransfer transfer2 = new ElectronicBankTransfer("Monobank", "9635745123568789", "335647885");
        List<CreditCard> creditCards = new ArrayList<>() {{
            add(card1);
            add(card2);
        }};
        List<ElectronicBankTransfer> transfers = new ArrayList<>() {{
            add(transfer1);
            add(transfer2);
        }};
        account = new Account("Nick Mason", "123asd", AccountStatus.ACTIVE, "Roger",
                address, "nick@gmail.com", "+380672710102", creditCards, transfers);
//        List<CreditCard> newCards = new ArrayList<>() {{
//            add(new CreditCard("Roger", "1111111111111111", 111, address2));
//            add(new CreditCard("Roger", "2222222222222222", 222, address3));
//        }};
//        account.setCreditCardList(newCards);
//
//        List<ElectronicBankTransfer> newTransfers = new ArrayList<>() {{
//            add(new ElectronicBankTransfer("AlfaBank", "3333333333333333", "333333333"));
//            add(new ElectronicBankTransfer("PUMB", "4444444444444444", "444444444"));
//        }};
    }

    @Test
    @DisplayName("Should insert account into DB an return autogenerated ID")
    public void saveTest() {
        Long expectedId = 4L;
        Long savedAccountId = accountDao.save(account);
        assertEquals(expectedId, savedAccountId);
    }

    @Test
    public void updateTest() {
        account.setId(3L);
        account.setEmail("newmail@yandex.ru");
        account.setShippingAddress(address1);
        assertTrue(accountDao.update(account));
    }

    @Test
    public void removeTest() {
        assertTrue(accountDao.remove(1L));
    }

    @Test
    public void findByIDTest() {
        Account result = accountDao.findById(1L);
        System.out.println(result);
    }

    @Test
    public void saveCreditCardTest() {
        CreditCard card1 = new CreditCard("Roger", "5525694123698541", 145, address2);
        CreditCard card2 = new CreditCard("Roger", "5525126354798541", 751, address3);

    }

    @Test
    public void findAllTest() {
        List<Account> result = accountDao.getAll();
        int i = 0;
        System.out.println(result);
    }
}

