package com.ra.course.ams.airline.manag.system.service.impl.payment;

import com.ra.course.ams.airline.manag.system.entity.payment.CashTransaction;
import com.ra.course.ams.airline.manag.system.entity.payment.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CashTransactionServiceTest {

    CashTransactionService cashTransactionService = new CashTransactionService();
    CashTransaction failedCashTransactionEmpty = new CashTransaction(1, 1, PaymentStatus.UNPAID, 0);
    CashTransaction failedAmoundEmpty = new CashTransaction(1, 0, PaymentStatus.UNPAID, 1);
    CashTransaction failedPaimentIdEmpty = new CashTransaction(0, 1, PaymentStatus.UNPAID, 1);
    CashTransaction confirmedCashTransaction = new CashTransaction(1, 1, PaymentStatus.UNPAID, 10);

    @Test
    public void whenAllFieldsFilledThenTransactionisCompleaded() {
        assertTrue(cashTransactionService.makeTransaction(confirmedCashTransaction));
    }

    @Test
    public void whenCashTransactionEmptyThenTransactionIsFalse() {
        assertFalse(cashTransactionService.makeTransaction(failedCashTransactionEmpty));
    }

    @Test
    public void whenAmoundEmptyThenTransactionIsFalse() {
        assertFalse(cashTransactionService.makeTransaction(failedAmoundEmpty));
    }

    @Test
    public void whenPaimentIdEmptyThenTransactionIsFalse() {
        assertFalse(cashTransactionService.makeTransaction(failedPaimentIdEmpty));
    }
}
