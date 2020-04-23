package com.ra.course.aws.online.shopping.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class GetLastIdRowMapperMockTest {
    GetLastIdRowMapper getLastIdRowMapper;

    @BeforeEach
    public void before() {
        getLastIdRowMapper = mock(GetLastIdRowMapper.class);
    }

    @Test
    public void testMapRow() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.getInt("id")).thenReturn(3);

        Integer result = new GetLastIdRowMapper().mapRow(rs, 0);
        assertEquals(3, result);
    }
}
