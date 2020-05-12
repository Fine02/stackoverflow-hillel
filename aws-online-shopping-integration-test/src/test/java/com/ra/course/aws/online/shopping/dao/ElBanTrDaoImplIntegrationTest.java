package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.AwsOnlineShoppingApplication;
import com.ra.course.aws.online.shopping.TestConfig;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("test")
@Sql(scripts = {"classpath:schema.sql", "classpath:test-data-account.sql"})
public class ElBanTrDaoImplIntegrationTest {

    @Autowired
    private ElectronicBankTransferDao electronicBankTransferDao;

    @Test
    @DisplayName("Should return true when EBT inserted")
    @Rollback
    public void saveTest() {
        ElectronicBankTransfer transfer = new ElectronicBankTransfer("PrivatBank123", "1225745123698541",
                "125647893");
        Long accountId = 1L;
        assertTrue(electronicBankTransferDao.save(accountId, transfer));
    }

    @Test
    @DisplayName("Should return true when EBT deleted")
    @Rollback
    public void removeTest() {
        assertTrue(electronicBankTransferDao.remove("444444"));
    }
}