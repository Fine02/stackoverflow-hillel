package com.ra.course.ams.airline.manag.system.service.impl.payment;

import com.ra.course.ams.airline.manag.system.entity.payment.CheckTransaction;
import com.ra.course.ams.airline.manag.system.entity.payment.CreditCardTransaction;
import com.ra.course.ams.airline.manag.system.entity.payment.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreditCardTransactionServiceTest {

    private CreditCardTransactionService creditCardTransactionService;
    private CreditCardTransaction failedCreditCardTransaction;
    private CreditCardTransaction confirmedCreditCardTransaction;


    @BeforeEach
    public void createTransaction() {
        creditCardTransactionService = new CreditCardTransactionService();
        failedCreditCardTransaction = new CreditCardTransaction(0, 1, PaymentStatus.UNPAID,
                "nameOn card");
        confirmedCreditCardTransaction = new CreditCardTransaction(1, 1, PaymentStatus.UNPAID,
                "nameOn card");
    }

    @Test
    public void whenTransactionisCompleaded() {
        assertTrue(creditCardTransactionService.makeTransaction(confirmedCreditCardTransaction));
    }

    @Test
    public void whenTransactionisFalse() {
        assertFalse(creditCardTransactionService.makeTransaction(failedCreditCardTransaction));
    }
}
