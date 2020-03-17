package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.dao.AccountDao;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountServiceImplTest {

    private AccountServiceImpl accountService;
    private AccountDao accountDao = mock(AccountDao.class);

    @BeforeEach
    void before() {
        accountService = new AccountServiceImpl(accountDao);
    }

    @Test
    void shouldReturnSavedId() {
        //given
        Account account = new Account();
        Long expectedId = 1L;
        when(accountDao.save(account)).thenReturn(expectedId);
        //when
        Long savedAccId = accountService.save(account);
        //then
        assertEquals(expectedId, savedAccId);
    }

    @Test
    void shouldReturnAccountById() {
        //given
        Long id = 1L;
        Account expectedAccount = new Account();
        expectedAccount.setId(id);
        when(accountDao.findByID(id)).thenReturn(expectedAccount);
        //when
        Account findedAccount = accountService.findById(id);
        //then
        assertEquals(expectedAccount, findedAccount);
    }
}
