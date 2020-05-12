package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.AwsOnlineShoppingApplication;
import com.ra.course.aws.online.shopping.TestConfig;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.enums.AccountStatus;
import com.ra.course.aws.online.shopping.entity.user.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("test")
@Sql(scripts = {"classpath:schema.sql", "classpath:test-data-account.sql"})
public class AccountDaoImplIntegrationTest {

    @Autowired
    private AccountDao accountDao;

    @Test
    @DisplayName("Should insert account into DB an return autogenerated ID")
    @Rollback
    public void saveTest() {
        Account account = new Account("Nick Mason", "123asd", AccountStatus.ACTIVE, "Roger",
                new Address(), "nick@gmail.com", "+380672710102");
        Long expectedId = 4L;
        Long savedAccountId = accountDao.save(account);
        assertEquals(expectedId, savedAccountId);
    }

    @Test
    @DisplayName("Should return true when account updated")
    @Rollback
    public void updateTest() {
        Long accountId = 1L;
        Account accountToUpdate = accountDao.findById(accountId);
        accountToUpdate.setEmail("newmail@yandex.ru");
        Address newAddress = new Address("Bohatyrska", "Lviv", "Lviv", "11111", "Ukraine1");
        accountToUpdate.setShippingAddress(newAddress);
        assertTrue(accountDao.update(accountToUpdate));
    }

    @Test
    @Rollback
    public void removeTest() {
        assertTrue(accountDao.remove(1L));
    }

    @Test
    @DisplayName("Finded account should be equal to expected")
    @Rollback
    public void findByIDTest() {
        Long accountId = 2L;
        Account findedAccount = accountDao.findById(accountId);
        assertAll(() -> {
            assertEquals(findedAccount.getId(), accountId);
            assertEquals(findedAccount.getUserName(), "roger");
            assertEquals(findedAccount.getPassword(), "123123");
            assertEquals(findedAccount.getStatus(), AccountStatus.COMPROMISED);
            assertEquals(findedAccount.getName(), "Roger Waters");
            assertEquals(findedAccount.getEmail(), "waters@gmail.com");
            assertEquals(findedAccount.getPhone(), "+145878965644");
        });
    }

    @Test
    @DisplayName("Should return null when there is no account with following id in DB")
    @Rollback
    public void findByIDNullTest() {
        Long accountId = 999L;
        assertNull(accountDao.findById(accountId));
    }

    @Test
    @DisplayName("Accoutns list size should be equals to actual")
    @Rollback
    public void findAllTest() {
        List<Account> result = accountDao.getAll();
        assertEquals(3, result.size());
    }

    @Test
    @DisplayName("Should return empty list when there are no accounts in DB")
    @Rollback
    public void findAllEmptyTest() {
        accountDao.remove(1L);
        accountDao.remove(2L);
        accountDao.remove(3L);
        assertTrue(accountDao.getAll().isEmpty());
    }
}