package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.dao.AccountDao;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountServiceImplTest {

    private AccountServiceImpl accountService;
    private AccountDao accountDao = mock(AccountDao.class);
    private Account account;

    @BeforeEach
    void before() {

        accountService = new AccountServiceImpl(accountDao);
        account = new Account();
        account.setId(1L);
    }

    @Test
    @DisplayName("Should return id when account saved")
    void shouldReturnSavedId()
    {
        //given
        Long expectedId = 1L;
        when(accountDao.save(account)).thenReturn(expectedId);
        //when
        Long savedAccId = accountService.save(account);
        //then
        assertEquals(expectedId, savedAccId);
    }

    @Test
    @DisplayName("Should return account with following Id")
    void shouldReturnAccountById() {
        //given
        Long id = 1L;
        when(accountDao.findByID(id)).thenReturn(account);
        //when
        Account findedAccount = accountService.findById(id);
        //then
        assertEquals(account, findedAccount);
    }

    @Test
    @DisplayName("Should return true when account updated")
    void shouldUpdate() {
        //given
        Account account = new Account();
        when(accountDao.update(account)).thenReturn(true);
        //when
        boolean result = accountService.update(account);
        //then
        assertTrue(result);
    }

    @Test
    @DisplayName("Should return true when account deleted")
    void shouldRemove() {
        //given
        when(accountDao.delete(account.getId())).thenReturn(true);
        //when
        boolean result = accountService.delete(account.getId());
        //then
        assertTrue(result);
    }
}
