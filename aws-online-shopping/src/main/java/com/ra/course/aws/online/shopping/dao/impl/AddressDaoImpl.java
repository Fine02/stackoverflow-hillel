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
    @Value("${insertAccAddress}")
    private transient String insertAccAddress;
    @Value("${insertBillAddress}")
    private transient String insertBillAddress;
    @Value("${updateAddress}")

    private transient String updateAddress;
    private transient final JdbcTemplate jdbcTemplate;
    private transient final KeyHolderFactory keyHolderFactory;

    public AddressDaoImpl(final JdbcTemplate jdbcTemplate, final KeyHolderFactory keyHolderFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.keyHolderFactory = keyHolderFactory;
    }

    @Override
    public Long saveAccAdd(final Address address, final Long accountId) {
        return saveAddress(address, accountId, insertAccAddress);
    }

    @Override
    public Long saveBillAdd(final Address address, final Long cardId) {
        return saveAddress(address, cardId, insertBillAddress);
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



    private Long saveAddress(Address address, Long id, String sql) {
        final KeyHolder keyHolder = keyHolderFactory.newKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(final Connection con)
                    throws SQLException {
                final PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, address.getStreetAddress());
                ps.setString(2, address.getCity());
                ps.setString(3, address.getState());
                ps.setString(4, address.getZipCode());
                ps.setString(5, address.getCountry());
                ps.setInt(6, id.intValue());
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }
}
