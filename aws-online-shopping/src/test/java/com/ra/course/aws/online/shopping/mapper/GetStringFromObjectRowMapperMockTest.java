package com.ra.course.aws.online.shopping.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetStringFromObjectRowMapperMockTest {
    GetStringFromObjectRowMapper getStringFromObjectRowMapper;

    @BeforeEach
    public void before() {
        getStringFromObjectRowMapper = mock(GetStringFromObjectRowMapper.class);
    }

    @Test
    public void testMapRow() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.getString(1)).thenReturn("test");

        String result = new GetStringFromObjectRowMapper().mapRow(rs,0);
        assertEquals("test", result);
    }
}
