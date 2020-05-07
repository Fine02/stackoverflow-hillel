package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.dao.impl.CreditCardDaoImpl;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.keyholder.KeyHolderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class CreditCardDaoImplMockTest {

    private CreditCardDao creditCardDao;
    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private KeyHolderFactory keyHolderFactory = mock(KeyHolderFactory.class);


    @BeforeEach
    void setUp() {
        creditCardDao = new CreditCardDaoImpl(jdbcTemplate, keyHolderFactory);
    }

    @Test
    @DisplayName("Should return true when credit card inserted")
    public void saveTest() {
        //given
        Long accountId = 1L;
        Long expectedId = 7L;
        Address billingAddress = new Address("Bohatyrska", "Lviv", "Lviv", "11111", "Ukraine");
        CreditCard card = new CreditCard("Roger", "5525694123698541", 145, billingAddress);

        KeyHolder keyHolder = mock(GeneratedKeyHolder.class);
        when(keyHolderFactory.newKeyHolder()).thenReturn(keyHolder);

        when(keyHolder.getKey()).thenReturn(expectedId);
        when(jdbcTemplate.update(Mockito.any(PreparedStatementCreator.class), Mockito.any(KeyHolder.class))).thenReturn(1);
        //when
        Long cardId = creditCardDao.save(accountId, card);
        //then
        assertEquals(cardId, expectedId);
    }

    @Test
    @DisplayName("Should return true when credit card deleted")
    public void removeTest() {
        //when
        assertTrue(creditCardDao.remove("000000"));
        //then
        verify(jdbcTemplate).update(any(), eq("000000"));
    }
}