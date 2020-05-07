package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.AwsOnlineShoppingApplication;
import com.ra.course.aws.online.shopping.TestConfig;
import com.ra.course.aws.online.shopping.dao.AddressDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("test")
@Sql(scripts = {"classpath:schema.sql", "classpath:test-data.sql"})
//@Sql(scripts= "classpath:test-data.sql")
public class AccountServiceImplIntegrationTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void deleteAccountTest() {
        assertTrue(accountService.delete(1L));
    }

    @Test
    public void deleteCreditCardTest() {
       assertTrue(accountService.deleteCreditCard("000000"));
    }
}
