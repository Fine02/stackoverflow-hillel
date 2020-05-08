package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.AwsOnlineShoppingApplication;
import com.ra.course.aws.online.shopping.TestConfig;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.enums.AccountStatus;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.exceptions.AccountNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("test")
@Sql(scripts = {"classpath:schema.sql", "classpath:test-data.sql"})
public class AccountServiceImplIntegrationTest {

    @Autowired
    private AccountService accountService;

    private Address address;
    private Account account;
    private CreditCard card;
    private ElectronicBankTransfer transfer;

    @BeforeEach
    void setUp() {
        address = new Address("Garmatna", "Kyiv", "Kyiv", "01135", "Ukraine");
        account = new Account("Nick Mason", "123asd", AccountStatus.ACTIVE, "Roger",
                address, "nick@gmail.com", "+380672710102");
        card = new CreditCard("Roger", "5525694123698541", 145, address);
        transfer = new ElectronicBankTransfer("PrivatBank", "1225745123698541", "125647893");
    }

    @Test
    @DisplayName("Added account autogenerated id should be equal to expected")
    @Rollback
    void shouldAddNewAccount() {
        Long expectedNewAccountId = 4L;

        Long addedAccountID = accountService.create(account);

        assertEquals(expectedNewAccountId, addedAccountID);
    }

    @Test
    @Rollback
    void shouldUpdateExistingAccount() {
        Long accountId = 1L;
        Account oldAccount = accountService.findById(accountId);

        String newEmail = "nick1971@protonmail.com";
        Address newAddress = new Address(999L, "Kurbasa", "Minsk", "Poltava",
                "333333", "Ukraine3");

        assertAll(() -> {
            assertNotEquals(oldAccount.getEmail(), newEmail);
            assertNotEquals(oldAccount.getShippingAddress(), newAddress);
        });

        oldAccount.setEmail(newEmail);
        oldAccount.setShippingAddress(newAddress);

        accountService.update(oldAccount);
        Account updatedAccount = accountService.findById(accountId);

        assertAll(() -> {
            assertEquals(updatedAccount.getEmail(), newEmail);
            assertEquals(updatedAccount.getShippingAddress(), newAddress);
        });
    }

    @Test
    @DisplayName("Should throw an exception when account to update does not exist")
    @Rollback
    void shouldNotUpdateAccount() {
        account.setId(999L);
        assertThrows(AccountNotFoundException.class, () -> accountService.update(account));
    }

    @Test
    @Rollback
    public void deleteAccountTest() {
        Long accountId = 1L;
        assertNotNull(accountService.findById(accountId));

        accountService.delete(accountId);

        assertNull(accountService.findById(accountId));
    }

    @Test
    @DisplayName("Finded account should be equal to expected")
    @Rollback
    void shouldSearchAccountsByID() {
        Long accountId = 2L;

        Address expectedAddress = new Address(2L, "Zlayoustivska", "Kyiv",
                "Kyiv", "01135", "Ukraine");
        CreditCard expectedCard1 = new CreditCard("Roger Waters", "777777", 777,
                new Address(3L, "KirovaB", "Dnirpo", "Dnipro", "14569", "Ukraine"));
        CreditCard expectedCard2 = new CreditCard("Waters Roger", "888888", 888,
                new Address(3L, "SadovaB", "Dnirpo", "Dnipro", "14568", "Ukraine"));
        ElectronicBankTransfer expectedTransfer = new ElectronicBankTransfer("Alfa", "333333", "333333");

        Account findedAccount = accountService.findById(accountId);

        assertAll(() -> {
            assertEquals(findedAccount.getId(), accountId);
            assertEquals(findedAccount.getUserName(), "roger");
            assertEquals(findedAccount.getPassword(), "123123");
            assertEquals(findedAccount.getStatus(), AccountStatus.COMPROMISED);
            assertEquals(findedAccount.getName(), "Roger Waters");
            assertEquals(findedAccount.getEmail(), "waters@gmail.com");
            assertEquals(findedAccount.getPhone(), "+145878965644");
            assertEquals(findedAccount.getShippingAddress(), expectedAddress);
            assertEquals(2, findedAccount.getCreditCardList().size());
            assertTrue(findedAccount.getCreditCardList().contains(expectedCard1));
            assertTrue(findedAccount.getCreditCardList().contains(expectedCard2));
            assertEquals(1, findedAccount.getElectronicBankTransferList().size());
            assertTrue(findedAccount.getElectronicBankTransferList().contains(expectedTransfer));
        });
    }

    @Test
    @DisplayName("Should return null when there is no account with following id in DB")
    @Rollback
    public void findByIDNullTest() {
        Long accountId = 999L;
        assertNull(accountService.findById(accountId));
    }

    @Test
    @DisplayName("Accoutns list size should be equals to actual")
    @Rollback
    void shouldGetAllAccounts() {
        int expectedListSize = 3;

        List<Account> accounts = accountService.findAll();
        assertEquals(accounts.size(), expectedListSize);

        Long accountId = accountService.create(account);
        accountService.addCreditCard(accountId, card);
        accountService.addElectronicBankTransfer(accountId, transfer);

        accounts = accountService.findAll();
        assertEquals(accounts.size(), ++expectedListSize);
    }

    @Test
    @DisplayName("Should return empty list when there are no accounts in DB")
    @Rollback
    public void findAllEmptyTest() {
        accountService.delete(1L);
        accountService.delete(2L);
        accountService.delete(3L);
        assertTrue(accountService.findAll().isEmpty());
    }

    @Test
    @Rollback
    void addCreditCardTest() {
        Long accountId = 1L;

        Account oldAccount = accountService.findById(accountId);
        assertEquals(oldAccount.getCreditCardList().size(), 2);

        accountService.addCreditCard(accountId, card);

        Account updCardsAcc = accountService.findById(accountId);
        assertAll(() -> {
            assertEquals(updCardsAcc.getCreditCardList().size(), 3);
            assertTrue(updCardsAcc.getCreditCardList().contains(card));
        });
    }

    @Test
    @Rollback
    public void deleteCreditCardTest() {
        Long accountId = 3L;
        String cardNumber = "000000";
        CreditCard cardToDelete = new CreditCard("Barret Syd", cardNumber, 000,
                new Address(6L, "DerybasivskaB", "Odesa", "Odesa", "25144", "Ukraine"));

        Account oldAccount = accountService.findById(accountId);
        assertAll(() -> {
            assertEquals(oldAccount.getCreditCardList().size(), 2);
            assertTrue(oldAccount.getCreditCardList().contains(cardToDelete));
        });

        accountService.deleteCreditCard(cardNumber);

        Account updCardsAcc = accountService.findById(accountId);
        assertEquals(updCardsAcc.getCreditCardList().size(), 1);
    }

    @Test
    void addTransferTest() {
        Long accountId = 1L;

        Account oldAccount = accountService.findById(accountId);
        assertEquals(oldAccount.getElectronicBankTransferList().size(), 2);

        accountService.addElectronicBankTransfer(accountId, transfer);

        Account updTrsnfAcc = accountService.findById(accountId);
        assertAll(() -> {
            assertEquals(updTrsnfAcc.getElectronicBankTransferList().size(), 3);
            assertTrue(updTrsnfAcc.getElectronicBankTransferList().contains(transfer));
        });
    }

    @Test
    @DisplayName("Should return true when electronic bank deleted")
    void deleteTransferTest() {
        Long accountId = 1L;
        String routingNumber = "111111";
        ElectronicBankTransfer transferToDelete = new ElectronicBankTransfer("Privat",
                routingNumber, "111111");

        Account oldAccount = accountService.findById(accountId);
        assertAll(() -> {
            assertEquals(oldAccount.getElectronicBankTransferList().size(), 2);
            assertTrue(oldAccount.getElectronicBankTransferList().contains(transferToDelete));
        });

        accountService.deleteElectronicBankTransfer(routingNumber);

        Account updTransfAcc = accountService.findById(accountId);
        assertEquals(updTransfAcc.getElectronicBankTransferList().size(), 1);
    }
}