package com.ra.course.ams.airline.manag.system.service.impl.payment;

import com.ra.course.ams.airline.manag.system.entity.payment.CashTransaction;
import com.ra.course.ams.airline.manag.system.entity.payment.CheckTransaction;
import com.ra.course.ams.airline.manag.system.entity.payment.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckTransactionServiceTest {
    private CheckTransaction checkTransactionashTransactionService;
    private CashTransaction failedCashTransaction;
    private CashTransaction confirmedCashTransaction;

    @BeforeEach
    public void createTransaction () {
        cashTransactionService = new CashTransactionService();
        failedCashTransaction = new CashTransaction(1,1, PaymentStatus.UNPAID,0);
        confirmedCashTransaction = new CashTransaction(1,1, PaymentStatus.UNPAID,10);
    }

    @Test
    public void whenTransactionisCompleaded() {
        assertTrue(cashTransactionService.makeTransaction(confirmedCashTransaction));
    }

    @Test
    public void whenTransactionisFalse() {
        assertFalse(cashTransactionService.makeTransaction(failedCashTransaction));
    }
}
