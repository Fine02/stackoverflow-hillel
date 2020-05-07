package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.dao.impl.AddressDaoImpl;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.mapper.AddressRowMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;


public class AddressDaoImplMockTest {

    private AddressDao addressDao;
    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
    private Address address;

    @BeforeEach
    void setUp() {
        addressDao = new AddressDaoImpl(jdbcTemplate);
        address = new Address("Garmatna", "Kyiv", "Kyiv", "01135", "Ukraine");
    }

    @Test
    @DisplayName("Should return true when account address inserted")
    public void saveAccAddTest() {
        //given
        Long accountId = 3L;
        when(jdbcTemplate.update(anyString(), eq("Garmatna"), eq("Kyiv"), eq("Kyiv"),
                eq("01135"), eq("Ukraine"), eq(accountId))).thenReturn(1);
        //then
        assertTrue(addressDao.saveAccAdd(address, accountId));
    }

    @Test
    @DisplayName("Should return true when billing address inserted")
    public void saveBillAddTest() {
        //given
        Long cardId = 3L;
        when(jdbcTemplate.update(anyString(), eq("Garmatna"), eq("Kyiv"), eq("Kyiv"),
                eq("01135"), eq("Ukraine"), eq(cardId))).thenReturn(1);
        //then
        assertTrue(addressDao.saveAccAdd(address, cardId));
    }

    @Test
    @DisplayName("Should return account address by following id")
    public void findAccAddByIDTest() {
        //given
        when(jdbcTemplate.queryForObject(any(), eq(new Object[]{1L}), any(AddressRowMapper.class))).thenReturn(address);
        //when
        Address result = addressDao.findAccAddById(1L);
        //then
        assertEquals(result, address);
    }

    @Test
    @DisplayName("Should return true when account address updated")
    public void updateAccAddTest() {
        //given
        address.setCountry("Russia");
        //when
        assertTrue(addressDao.updateAccAdd(address));
        //then
        verify(jdbcTemplate).update(any(), eq(address.getStreetAddress()), eq(address.getCity()), eq(address.getState()),
                eq(address.getZipCode()), eq(address.getCountry()), eq(address.getId()));
    }
}