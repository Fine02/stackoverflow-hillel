package com.ra.course.aws.online.shopping.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MemberBooleanRowMapperMockTest {
    MemberBooleanRowMapper memberBooleanRowMapper;

    @BeforeEach
    public void before() {
        memberBooleanRowMapper = mock(MemberBooleanRowMapper.class);
    }

    @Test
    public void testMapRow() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.getInt("member_id")).thenReturn(1);
        when(rs.getString("userName")).thenReturn("ivan1");
        when(rs.getString("password")).thenReturn("333");
        when(rs.getString("status")).thenReturn(null);
        when((rs.getString("name"))).thenReturn("ivan");
        when((rs.getString("streetAddress"))).thenReturn("3");
        when((rs.getString("city"))).thenReturn("Kyiv");
        when((rs.getString("state"))).thenReturn("Kyiv");
        when((rs.getString("zipcode"))).thenReturn("1114");
        when((rs.getString("country"))).thenReturn("Ukraine");
        when((rs.getString("email"))).thenReturn("ivan@gmail.com");
        when((rs.getString("phone"))).thenReturn("55222");
        when((rs.getString("nameOnCard"))).thenReturn("VISA");
        when((rs.getString("cardNumber"))).thenReturn("7777 7777");
        when(rs.getInt("code")).thenReturn(855);
        when((rs.getString("bankName"))).thenReturn("GermanBank");
        when((rs.getString("routingNumber"))).thenReturn("8555");
        when((rs.getString("accountNumber"))).thenReturn("88855");


        Boolean result = new MemberBooleanRowMapper().mapRow(rs, 0);
        assertEquals(true, result);
    }
}
