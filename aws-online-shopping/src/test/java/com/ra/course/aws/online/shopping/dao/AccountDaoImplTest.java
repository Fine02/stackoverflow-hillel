package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.dao.impl.AccountDaoImpl;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.enums.AccountStatus;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.keyholder.KeyHolderFactory;
import com.ra.course.aws.online.shopping.mapper.AccountActionVOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


public class AccountDaoImplTest {

    private AccountDao accountDao;
    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private AccountActionVOMapper accountActionVOMapper = mock(AccountActionVOMapper.class);
    private KeyHolderFactory keyHolderFactory = mock(KeyHolderFactory.class);

    private Account account;
    private Address address;

    @BeforeEach
    void setUp() {
        accountDao = new AccountDaoImpl(jdbcTemplate, accountActionVOMapper, keyHolderFactory);
        address = new Address("Garmatna", "Kyiv", "Kyiv", "01135", "Ukraine");
        account = new Account("Nick Mason", "123asd", AccountStatus.ACTIVE, "Roger",
                address, "nick@gmail.com", "+380672710102");
    }

    @Test
    @DisplayName("Returned account id should be equal to expected")
    public void saveTest() {
        //given
        Long expectedId = 4L;

        KeyHolder keyHolder = mock(GeneratedKeyHolder.class);
        when(keyHolderFactory.newKeyHolder()).thenReturn(keyHolder);

        when(keyHolder.getKey()).thenReturn(expectedId);
        when(jdbcTemplate.update(Mockito.any(PreparedStatementCreator.class), Mockito.any(KeyHolder.class))).thenReturn(1);
        //when
        Long savedAccountId = accountDao.save(account);
        //then
        assertEquals(expectedId, savedAccountId);
    }

    @Test
    @DisplayName("Should return true when account updated")
    public void updateTest() {
        //given
        account.setId(3L);
        account.setEmail("newmail@yandex.ru");
        //when
        assertTrue(accountDao.update(account));
        //then
        verify(jdbcTemplate).update(any(), eq(account.getUserName()), eq(account.getPassword()), eq(account.getStatus().toString()),
                eq(account.getName()), eq(account.getEmail()), eq(account.getPhone()), eq(account.getId()));
    }

    @Test
    @DisplayName("Should return true when account deleted")
    public void removeTest() {
        //when
        assertTrue(accountDao.remove(1L));
        //then
        verify(jdbcTemplate).update(any(), eq(1L));
    }

    @Test
    @DisplayName("Finded account should be equal to expected")
    public void findByIDTest() {
        //given
        Account account1 = new Account(1L, "Nick Mason", "123asd", AccountStatus.ACTIVE, "Roger",
                address, "nick@gmail.com", "+380672710102", new ArrayList<>(), new ArrayList<>());
        when(jdbcTemplate.query(any(), eq(new Object[]{1L}), any(AccountActionVOMapper.class))).thenReturn(new ArrayList<>());
        when(accountActionVOMapper.getMappedAccountsFromVO(any())).thenReturn(new ArrayList<>() {{
            add(account1);
        }});
        //when
        Account result = accountDao.findById(1L);
        //then
        assertEquals(result, account1);
    }

    @Test
    @DisplayName("Accounts list finded size should be equal to expected")
    public void findAllTest() {
        //given
        when(jdbcTemplate.query(any(), eq(new Object[]{1L}), any(AccountActionVOMapper.class))).thenReturn(new ArrayList<>());
        when(accountActionVOMapper.getMappedAccountsFromVO(any())).thenReturn(new ArrayList<>() {{
            add(new Account());
            add(new Account());
            add(new Account());
        }});
        //when
        List<Account> result = accountDao.getAll();
        //then
        assertEquals(result.size(), 3);
    }
}

