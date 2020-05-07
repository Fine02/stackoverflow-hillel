package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.AddressDao;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.mapper.AddressRowMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("sql-requests.yml")
public class AddressDaoImpl implements AddressDao {

    @Value("${insertAccAddress}")
    private transient String insertAccAddress;
    @Value("${insertBillAddress}")
    private transient String insertBillAddress;
    @Value("${updateAddress}")
    private transient String updateAddress;
    @Value("${getAddress}")
    private transient String getAddress;

    private transient final JdbcTemplate jdbcTemplate;

    public AddressDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean saveAccAdd(final Address address, final Long accountId) {
        return saveAddress(address, accountId, insertAccAddress);
    }

    @Override
    public boolean saveBillAdd(final Address address, final Long cardId) {
        return saveAddress(address, cardId, insertBillAddress);
    }

    @Override
    public Address findAccAddById(final Long id) {
        return jdbcTemplate.queryForObject(getAddress, new Object[]{id}, new AddressRowMapper());
    }

    @Override
    public boolean updateAccAdd(final Address address) {
        jdbcTemplate.update(updateAddress, address.getStreetAddress(), address.getCity(), address.getState(),
                address.getZipCode(), address.getCountry(), address.getId());
        return true;
    }

    private boolean saveAddress(Address address, Long id, String sql) {
        jdbcTemplate.update(sql, address.getStreetAddress(), address.getCity(), address.getState(),
                address.getZipCode(), address.getCountry(), id.intValue());
        return true;
    }
}