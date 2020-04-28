package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.AwsOnlineShoppingApplication;
import com.ra.course.aws.online.shopping.TestConfig;
import com.ra.course.aws.online.shopping.entity.enums.PaymentStatus;
import com.ra.course.aws.online.shopping.entity.payment.CreditCardTransaction;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
//@ActiveProfiles("local")
@ActiveProfiles("test")
//@Sql({"classpath:schema.sql", "classpath:test-data.sql"})
@Sql({"classpath: mysqlschema.sql", "classpath: mysql-test-data.sql"})
public class PaymentDaoImplIntegrationTest {

    @Autowired
    private PaymentDao paymentDao;

    private Double amount = 897.77;
    private ElectronicBankTransaction electronicBankTransaction = new ElectronicBankTransaction(PaymentStatus.PENDING, amount);
    private CreditCardTransaction creditCardTransaction = new CreditCardTransaction(PaymentStatus.PENDING, amount);

    @Test
    public void createElectronicBankTransactionTest() {
        paymentDao.createTransaction(electronicBankTransaction);
    }

    @Test
    public void createCreditCardTransactionTest() {
        paymentDao.createTransaction(creditCardTransaction);
    }

    @Test
    public void ifMemberByIdWasFoundReturnTrueTest() {
        boolean result = paymentDao.isFoundMemberID(3L);

        assertEquals(true, result);
    }

    @Test
    public void ifMemberByIdWasFoundReturnFalseTest() {
        boolean result = paymentDao.isFoundMemberID(8553L);

        assertEquals(false, result);
    }

}
