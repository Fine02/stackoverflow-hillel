package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.Address;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class AddressRowMapper implements RowMapper<Address> {
    @Override
    public Address mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Address address = new Address();
        address.setId(rs.getLong("id"));
        address.setStreetAddress(rs.getString("streetAddress"));
        address.setCity(rs.getString("city"));
        address.setState(rs.getString("state"));
        address.setZipCode(rs.getString("zipcode"));
        address.setCountry(rs.getString("country"));
        return address;
    }
}