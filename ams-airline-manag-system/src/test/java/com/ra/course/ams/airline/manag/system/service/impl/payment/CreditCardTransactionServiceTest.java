package com.ra.course.ams.airline.manag.system.service.impl.payment;

import com.ra.course.ams.airline.manag.system.entity.payment.CreditCardTransaction;
import com.ra.course.ams.airline.manag.system.entity.payment.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreditCardTransactionServiceTest {

    CreditCardTransactionService creditCardTransactionService = new CreditCardTransactionService();
    CreditCardTransaction confirmedCreditCardTransaction = new CreditCardTransaction(1, 1, PaymentStatus.UNPAID,
            "nameOn card");
    CreditCardTransaction failedPaimentIdEmpty = new CreditCardTransaction(0, 1, PaymentStatus.UNPAID,
            "nameOn card");
    CreditCardTransaction failedAmoundEmpty = new CreditCardTransaction(1, 0, PaymentStatus.UNPAID,
            "nameOn card");
    CreditCardTransaction failedNameOnCardEmpty = new CreditCardTransaction(1, 0, PaymentStatus.UNPAID,
            "nameOn card");

    @Test
    public void whenTransactionisCompleaded() {
        assertTrue(creditCardTransactionService.makeTransaction(confirmedCreditCardTransaction));
    }

    @Test
    public void whenPaimentIdEmptyThenTransactionIsFalse() {
        assertFalse(creditCardTransactionService.makeTransaction(failedPaimentIdEmpty));
    }

    @Test
    public void whenAmoundEmptyThenTransactionIsFalse() {
        assertFalse(creditCardTransactionService.makeTransaction(failedAmoundEmpty));
    }

    @Test
    public void whenNameOnCardEmptyThenTransactionIsFalse() {
        assertFalse(creditCardTransactionService.makeTransaction(failedNameOnCardEmpty));
    }
}
