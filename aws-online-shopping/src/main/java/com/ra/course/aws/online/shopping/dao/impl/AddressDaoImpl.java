package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.AddressDao;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.keyholder.KeyHolderFactory;
import com.ra.course.aws.online.shopping.mapper.AddressRowMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
@PropertySource("sql-requests.yml")
public class AddressDaoImpl implements AddressDao {
    @Value("${getAddress}")
    private transient String getAddress;
    @Value("${insertAddress}")
    private transient String insertAddress;
    @Value("${updateAddress}")
    private transient String updateAddress;
    @Value("${deleteAddress}")
    private transient String deleteAddress;

    private transient final JdbcTemplate jdbcTemplate;
    private transient final KeyHolderFactory keyHolderFactory;

    public AddressDaoImpl(final JdbcTemplate jdbcTemplate, final KeyHolderFactory keyHolderFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.keyHolderFactory = keyHolderFactory;
    }

    @Override
    public Long save(final Address address) {
        final KeyHolder keyHolder = keyHolderFactory.newKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(final Connection con)
                    throws SQLException {
            final PreparedStatement ps = con.prepareStatement(insertAddress, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, address.getStreetAddress());
            ps.setString(2, address.getCity());
            ps.setString(3, address.getState());
            ps.setString(4, address.getZipCode());
            ps.setString(5, address.getCountry());
            return ps;
        }}, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Address findById(final Long id) {
        return jdbcTemplate.queryForObject(getAddress, new Object[]{id}, new AddressRowMapper());
    }

    @Override
    public boolean update(final Address address) {
        jdbcTemplate.update(updateAddress, address.getStreetAddress(), address.getCity(), address.getState(),
                address.getZipCode(), address.getCountry(), address.getId());
        return true;
    }

    @Override
    public boolean remove(final Long id) {
        jdbcTemplate.update(deleteAddress, id);
        return true;
    }
}
