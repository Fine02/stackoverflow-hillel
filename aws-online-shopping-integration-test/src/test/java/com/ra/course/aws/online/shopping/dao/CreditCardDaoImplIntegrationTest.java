package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.AwsOnlineShoppingApplication;
import com.ra.course.aws.online.shopping.TestConfig;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("local")
@Sql(scripts = {"classpath:schema.sql", "classpath:test-data.sql"})
public class CreditCardDaoImplIntegrationTest {

    @Autowired
    private AddressDao addressDao;
    @Autowired
    private CreditCardDao creditCardDao;

    @Test
    @DisplayName("Should insert account into DB an return autogenerated ID")
    public void saveTest() {
        Address address = new Address("Bohatyrska", "Lviv", "Lviv", "11111", "Ukraine1");
        Long addressId = addressDao.save(address);

        CreditCard card1 = new CreditCard("Roger", "5525694123698541", 145, address);

        Long accountId = 1L;
        assertTrue(creditCardDao.save(accountId, card1, addressId));
    }

    @Test
    public void removeTest() {
        assertTrue(creditCardDao.remove("000000"));
    }
}

