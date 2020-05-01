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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    private AccountServiceImpl accountService;
    private AccountDao accountDao = mock(AccountDao.class);
    private AddressDao addressDao = mock(AddressDao.class);
    private CreditCardDao creditCardDao = mock(CreditCardDao.class);
    private ElectronicBankTransferDao electronicBankTransferDao = mock(ElectronicBankTransferDao.class);
    private Account someAccount;
    private Account account;
    private Address address;
    private Address address1;
    private Address address2;
    private Address address3;
    private CreditCard card1;
    private ElectronicBankTransfer transfer1;

    @BeforeEach
    void setUp() {
        accountService = new AccountServiceImpl(accountDao, addressDao, creditCardDao, electronicBankTransferDao);
        someAccount = new Account("John1971", "123ASD", AccountStatus.ACTIVE, "John",
                new Address(), "john1971@ukr.net", "9379992");
        address = new Address("Garmatna", "Kyiv", "Kyiv", "01135", "Ukraine");
        address1 = new Address("Bohatyrska", "Lviv", "Lviv", "11111", "Ukraine1");
        address2 = new Address("Gonchara", "Dnipro", "Dnipro", "22222", "Ukraine2");
        address3 = new Address("Kurbasa", "Minsk", "Poltava", "333333", "Ukraine3");
        card1 = new CreditCard("Roger", "5525694123698541", 145, address2);
        CreditCard card2 = new CreditCard("Roger", "5525126354798541", 751, address3);
        transfer1 = new ElectronicBankTransfer("PrivatBank", "1225745123698541", "125647893");
        ElectronicBankTransfer transfer2 = new ElectronicBankTransfer("Monobank", "9635745123568789", "335647885");
        List<CreditCard> creditCards = new ArrayList<>() {{
            add(card1);
            add(card2);
        }};
        List<ElectronicBankTransfer> transfers = new ArrayList<>() {{
            add(transfer1);
            add(transfer2);
        }};
        account = new Account(1L, "Nick Mason", "123asd", AccountStatus.ACTIVE, "Roger",
                address, "nick@gmail.com", "+380672710102", creditCards, transfers);
    }

    @Test
    @DisplayName("Should return saved account id")
    void shouldAddNewAccount() {
        Long expectedNewAccountId = 1L;
        //given
        when(accountDao.save(eq(someAccount), any(Long.class))).thenReturn(expectedNewAccountId);
        //when
        Long addedAccountID = accountService.create(someAccount);
        //then
        assertEquals(expectedNewAccountId, addedAccountID);
    }

    @Test
    @DisplayName("Should return true when account updated")
    void shouldUpdateExistingAccount() {
        //given
        Address address = new Address("Garmatna", "Kyiv", "Kyiv", "01135", "Ukraine");
        Account accountToUpdate = new Account(1L, "Nick Mason", "123asd", AccountStatus.ACTIVE, "Roger",
                address, "nick@gmail.com", "+380672710102", new ArrayList<>(), new ArrayList<>());
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
        //when
        when(accountDao.findById(someAccount.getId())).thenReturn(null);
        //then
        Exception exception = assertThrows(AccountNotFoundException.class, () -> accountService.update(someAccount));
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
        //then
        Exception exception = assertThrows(AccountNotFoundException.class, () -> accountService.delete(accountToRemoveId));
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

    @Test
    @DisplayName("Should return list of all accounts")
    void shouldGetAlaccounts() {
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
    @DisplayName("Should update account with new password")
    void resetPasswordTest() {
        //given
        when(accountDao.findById(any(Long.class))).thenReturn(account);
        //when
        assertTrue(accountService.resetPassword(account.getId(), "PasSworD"));
        //then
        verify(accountDao).update(eq(account));
    }

    @Test
    @DisplayName("Should save new credit card and billing address")
    void addCreditCardTest() {
        Long addressId = 34L;
        //given
        when(addressDao.save(any(Address.class))).thenReturn(addressId);
        //when
        assertTrue(accountService.addCreditCard(account.getId(), card1));
        //then
        verify(creditCardDao).save(eq(account.getId()), any(CreditCard.class), eq(addressId));
    }

    @Test
    @DisplayName("Should delete credit card by card number")
    void deleteCreditCardTest() {
        //when
        assertTrue(accountService.deleteCreditCard(card1.getCardNumber()));
        //then
        verify(creditCardDao).remove(eq(card1.getCardNumber()));
    }

    @Test
    @DisplayName("Should save new electronic bank transfer")
    void addTransferTest() {
        //when
        assertTrue(accountService.addElectronicBankTransfer(account.getId(), transfer1));
        //then
        verify(electronicBankTransferDao).save(eq(account.getId()), any(ElectronicBankTransfer.class));
    }

    @Test
    @DisplayName("Should delete electronic bank transfer by routing number")
    void deleteTransferTest() {
        //when
        assertTrue(accountService.deleteElectronicBankTransfer(transfer1.getRoutingNumber()));
        //then
        verify(electronicBankTransferDao).remove(eq(transfer1.getRoutingNumber()));
    }
}
