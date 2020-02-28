package com.ra.course.ams.airline.manag.system.service.impl.payment;

import com.ra.course.ams.airline.manag.system.entity.payment.CheckTransaction;
import com.ra.course.ams.airline.manag.system.entity.payment.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckTransactionServiceTest {
    private CheckTransactionService checkTransactionService;
    private CheckTransaction failedCheckTransaction;
    private CheckTransaction confirmedCheckTransaction;


    @BeforeEach
    public void createTransaction () {
        checkTransactionService = new CheckTransactionService();
        confirmedCheckTransaction = new CheckTransaction(1,1, PaymentStatus.UNPAID,
                "Bank", "chck number");
        failedCheckTransaction = new CheckTransaction(0,1, PaymentStatus.UNPAID,
                "Bank", "chck number");
    }

    @Test
    public void whenTransactionisCompleaded() {
        assertTrue(checkTransactionService.makeTransaction(confirmedCheckTransaction));
    }

    @Test
    public void whenTransactionisFalse() {
        assertFalse(checkTransactionService.makeTransaction(failedCheckTransaction));
    }
}
