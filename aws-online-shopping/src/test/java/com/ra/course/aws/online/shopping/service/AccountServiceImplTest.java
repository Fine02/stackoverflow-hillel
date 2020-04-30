package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.dao.AccountDao;
import com.ra.course.aws.online.shopping.dao.AddressDao;
import com.ra.course.aws.online.shopping.dao.CreditCardDao;
import com.ra.course.aws.online.shopping.dao.ElectronicBankTransferDao;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.enums.AccountStatus;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.exceptions.AccountNotFoundException;
import com.ra.course.aws.online.shopping.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountServiceImplTest {

    private AccountServiceImpl accountService;
    private AccountDao accountDao = mock(AccountDao.class);
    private AddressDao addressDao = mock(AddressDao.class);
    private CreditCardDao creditCardDao = mock(CreditCardDao.class);
    private ElectronicBankTransferDao electronicBankTransferDao = mock(ElectronicBankTransferDao.class);
    private Account someAccount;

    @BeforeEach
    void setUp() {
        accountService = new AccountServiceImpl(accountDao, addressDao, creditCardDao, electronicBankTransferDao);
        someAccount = new Account(1L, "John1971", "123ASD", AccountStatus.ACTIVE, "John",
                new Address(), "john1971@ukr.net", "9379992", new ArrayList<>(), new ArrayList<>());
    }

    @Test
    @DisplayName("Should return saved account id")
    void shouldAddNewAccount() {
        Long expectedNewAccountId = 1L;
        //given
        when(accountDao.save(someAccount)).thenReturn(expectedNewAccountId);
        //when
        Long addedAccountID = accountService.create(someAccount);
        //then
        assertEquals(expectedNewAccountId, addedAccountID);
    }

    @Test
    @DisplayName("Should return true when account updated")
    void shouldUpdateExistingAccount() {
        //given
        Account accountToUpdate = new Account();
        accountToUpdate.setId(someAccount.getId());
        accountToUpdate.setEmail("john1971@protonmail.com");
        when(accountDao.findById(accountToUpdate.getId())).thenReturn(someAccount);
        //when
        boolean result = accountService.update(accountToUpdate);
        //then
        assertTrue(result);
    }

    @Test
    @DisplayName("Should throw an exception when account to update does not exist")
    void shouldNotUpdateAccount() {
        //given
        when(accountDao.findById(someAccount.getId())).thenReturn(null);
        //when
        Exception exception = assertThrows(AccountNotFoundException.class, () -> accountService.update(someAccount));
        //then
        String expectedMessage = "Account with id=" + someAccount.getId() + " not found";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Should return true when account removed")
    void shouldRemoveAccount() {
        Long accountToRemoveId = 1L;
        when(accountDao.findById(accountToRemoveId)).thenReturn(someAccount);
        //when
        boolean result = accountService.delete(accountToRemoveId);
        //then
        assertTrue(result);
    }

    @Test
    @DisplayName("Should throw an exception when account to remove does not exist")
    void shouldNotRemoveAccount() {
        Long accountToRemoveId = 999L;
        //given
        when(accountDao.findById(accountToRemoveId)).thenReturn(null);
        //when
        Exception exception = assertThrows(AccountNotFoundException.class, () -> accountService.delete(accountToRemoveId));
        //then
        String expectedMessage = "Account with id = " + accountToRemoveId + " not found.";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Finded account should be equal to expected")
    void shouldSearchAccountsByID() {
        //given
        Long accountId = 1L;
        Account expectedAccount = someAccount;
        when(accountDao.findById(accountId)).thenReturn(expectedAccount);
        //when
        Account findedAccount = accountService.findById(accountId);
        //then
        assertEquals(expectedAccount, findedAccount);
    }
}
