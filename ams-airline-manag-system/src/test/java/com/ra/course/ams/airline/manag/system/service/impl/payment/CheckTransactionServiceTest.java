package com.ra.course.ams.airline.manag.system.service.impl.payment;

import com.ra.course.ams.airline.manag.system.entity.payment.CheckTransaction;
import com.ra.course.ams.airline.manag.system.entity.payment.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckTransactionServiceTest {

    CheckTransactionService checkTransactionService = new CheckTransactionService();

    CheckTransaction confirmedCheckTransaction = new CheckTransaction(1, 1, PaymentStatus.UNPAID,
            "Bank", "chck number");
    CheckTransaction failedPaimentIdEmpty = new CheckTransaction(0, 1, PaymentStatus.UNPAID,
            "Bank", "chck number");
    CheckTransaction failedAmoundEmpty = new CheckTransaction(1, 0, PaymentStatus.UNPAID,
            "Bank", "chck number");
    CheckTransaction failedBankNameEmpty = new CheckTransaction(1, 10, PaymentStatus.UNPAID,
            "", "chck number");
    CheckTransaction failedCheckNumberEmpty = new CheckTransaction(1, 10, PaymentStatus.UNPAID,
            "Bank", "");


    @Test
    public void whenTransactionisCompleaded() {
        assertTrue(checkTransactionService.makeTransaction(confirmedCheckTransaction));
    }

    @Test
    public void whenPaimentIdEmptyThenTransactionIsFalse() {
        assertFalse(checkTransactionService.makeTransaction(failedPaimentIdEmpty));
    }

    @Test
    public void whenAmoundEmptyThenTransactionIsFalse() {
        assertFalse(checkTransactionService.makeTransaction(failedAmoundEmpty));
    }

    @Test
    public void whenBankNameEmptyThenTransactionIsFalse() {
        assertFalse(checkTransactionService.makeTransaction(failedBankNameEmpty));
    }

    @Test
    public void whenCheckNumberEmptyThenTransactionIsFalse() {
        assertFalse(checkTransactionService.makeTransaction(failedCheckNumberEmpty));
    }
}
