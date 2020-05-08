package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.Address;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddressRowMapperTest {

    @Test
    public void test() throws SQLException {
        AddressRowMapper rowMapper = new AddressRowMapper();
        ResultSet rs = mock(ResultSet.class);
        Address expected = new Address("Gonchara", "Kyiv", "Kyiv", "01134", "Ukraine");
        expected.setId(1L);
        when(rs.getLong("id")).thenReturn(1L);
        when(rs.getString("streetAddress")).thenReturn("Gonchara");
        when(rs.getString("city")).thenReturn("Kyiv");
        when(rs.getString("state")).thenReturn("Kyiv");
        when(rs.getString("zipcode")).thenReturn("01134");
        when(rs.getString("country")).thenReturn("Ukraine");
        Address result = rowMapper.mapRow(rs, 1);
        assertEquals(expected, result);
    }
}
