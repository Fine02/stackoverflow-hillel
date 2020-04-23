package com.ra.course.aws.online.shopping.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BooleanOrderLogRowMapperMockTest {
    BooleanOrderLogRowMapper booleanOrderLogRowMapper;

    @BeforeEach
    public void before() {
        booleanOrderLogRowMapper = mock(BooleanOrderLogRowMapper.class);
    }

    @Test
    public void testMapRow() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.getInt("id")).thenReturn(1);
        when((rs.getString("orderNumber"))).thenReturn("3");
        when(rs.getTimestamp("creationDate")).thenReturn(null);
        when(rs.getString("status")).thenReturn(null);

        Boolean result = new BooleanOrderLogRowMapper().mapRow(rs, 0);
        assertEquals(true, result);
    }
}
