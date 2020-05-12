package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.dao.AccountDao;
import com.ra.course.aws.online.shopping.dao.AddressDao;
import com.ra.course.aws.online.shopping.dao.CreditCardDao;
import com.ra.course.aws.online.shopping.dao.ElectronicBankTransferDao;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.enums.AccountStatus;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.exceptions.AccountNotFoundException;
import com.ra.course.aws.online.shopping.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    private AccountService accountService;
    private AccountDao accountDao = mock(AccountDao.class);
    private AddressDao addressDao = mock(AddressDao.class);
    private CreditCardDao creditCardDao = mock(CreditCardDao.class);
    private ElectronicBankTransferDao electronicBankTransferDao = mock(ElectronicBankTransferDao.class);

    private Address address;
    private Account account;
    private CreditCard card;
    private ElectronicBankTransfer transfer;

    @BeforeEach
    void setUp() {
        accountService = new AccountServiceImpl(accountDao, addressDao, creditCardDao, electronicBankTransferDao);
        address = new Address("Garmatna", "Kyiv", "Kyiv", "01135", "Ukraine");
        account = new Account("Nick Mason", "123asd", AccountStatus.ACTIVE, "Roger",
                address, "nick@gmail.com", "+380672710102");
        card = new CreditCard("Roger", "5525694123698541", 145, address);
        transfer = new ElectronicBankTransfer("PrivatBank", "1225745123698541", "125647893");
    }

    @Test
    @DisplayName("Should return saved account id when account saved")
    void shouldAddNewAccount() {
        Long expectedNewAccountId = 1L;
        //given
        when(accountDao.save(eq(account))).thenReturn(expectedNewAccountId);
        when(addressDao.saveAccAdd(eq(address), eq(expectedNewAccountId))).thenReturn(true);
        //when
        Long addedAccountID = accountService.create(account);
        //then
        assertEquals(expectedNewAccountId, addedAccountID);
    }

    @Test
    @DisplayName("Should return true when account updated")
    void shouldUpdateExistingAccount() {
        //given
        Address newAddress = new Address(999L, "Kurbasa", "Minsk", "Poltava",
                "333333", "Ukraine3");
        account.setId(1L);
        account.setEmail("nick1971@protonmail.com");
        account.setShippingAddress(newAddress);
        when(accountDao.findById(account.getId())).thenReturn(account);
        //when
        assertTrue(accountService.update(account));
        //then
        verify(addressDao).updateAccAdd(eq(newAddress));
        verify(accountDao).update(eq(account));
    }

    @Test
    @DisplayName("Should throw an exception when account to update does not exist")
    void shouldNotUpdateAccount() {
        //when
        when(accountDao.findById(account.getId())).thenReturn(null);
        //then
        assertThrows(AccountNotFoundException.class, () -> accountService.update(account));
    }

    @Test
    @DisplayName("Should return true when account removed")
    void shouldRemoveAccount() {
        //given
        Long accountToRemoveId = 1L;
        when(accountDao.remove(accountToRemoveId)).thenReturn(true);
        //then
        assertTrue(accountService.delete(accountToRemoveId));
    }

    @Test
    @DisplayName("Finded account should be equal to expected")
    void shouldSearchAccountsByID() {
        //given
        Long accountId = 1L;
        Account expectedAccount = account;
        when(accountDao.findById(accountId)).thenReturn(expectedAccount);
        //when
        Account findedAccount = accountService.findById(accountId);
        //then
        assertEquals(expectedAccount, findedAccount);
    }

    @Test
    @DisplayName("Should return list of all accounts")
    void shouldGetAllAccounts() {
        //given
        when(accountDao.getAll()).thenReturn(new ArrayList<>() {{
            add(new Account());
            add(new Account());
            add(new Account());
        }});
        //when
        List<Account> accounts = accountService.findAll();
        //then
        assertEquals(accounts.size(), 3);
    }

    @Test
    @DisplayName("Should return true when credit card saved")
    void addCreditCardTest() {
        Long expectedCardId = 34L;
        Long accountId = 1L;

        //given
        when(creditCardDao.save(accountId, card)).thenReturn(expectedCardId);
        when(addressDao.saveBillAdd(eq(address), eq(accountId))).thenReturn(true);
        //when
        assertTrue(accountService.addCreditCard(accountId, card));
        //then
        verify(creditCardDao).save(eq(accountId), eq(card));
    }

    @Test
    @DisplayName("Should return true when credit card deleted")
    void deleteCreditCardTest() {
        //given
        when((creditCardDao).remove(eq(card.getCardNumber()))).thenReturn(true);
        //then
        assertTrue(accountService.deleteCreditCard(card.getCardNumber()));
    }

    @Test
    @DisplayName("Should return true when electronic bank saved")
    void addTransferTest() {
        //given
        Long accountId = 1L;
        when(electronicBankTransferDao.save(eq(accountId), eq(transfer))).thenReturn(true);
        //then
        assertTrue(accountService.addElectronicBankTransfer(accountId, transfer));
    }

    @Test
    @DisplayName("Should return true when electronic bank deleted")
    void deleteTransferTest() {
        //given
        when(electronicBankTransferDao.remove(eq(transfer.getRoutingNumber()))).thenReturn(true);
        //then
        assertTrue(accountService.deleteElectronicBankTransfer(transfer.getRoutingNumber()));
    }
}