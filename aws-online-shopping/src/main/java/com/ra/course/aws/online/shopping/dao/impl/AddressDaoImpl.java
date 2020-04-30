package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.AddressDao;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.keyholder.KeyHolderFactory;
import com.ra.course.aws.online.shopping.mapper.AddressRowMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
@PropertySource("sql-requests.yml")
public class AddressDaoImpl implements AddressDao {
    @Value("${INSERT_ADDRESS}")
    private transient String INSERT_ADDRESS;
    @Value("${UPDATE_ADDRESS}")
    private transient String UPDATE_ADDRESS;
    @Value("${GET_ADDRESS}")
    private transient String GET_ADDRESS;
    @Value("${DELETE_ADDRESS}")
    private transient String DELETE_ADDRESS;

    private transient final JdbcTemplate jdbcTemplate;
    private transient final KeyHolderFactory keyHolderFactory;

    public AddressDaoImpl(JdbcTemplate jdbcTemplate, KeyHolderFactory keyHolderFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.keyHolderFactory = keyHolderFactory;
    }

    @Override
    public Long save(Address address) {
        KeyHolder keyHolder = keyHolderFactory.newKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(INSERT_ADDRESS, new String[]{"id"});
            ps.setString(1, address.getStreetAddress());
            ps.setString(2, address.getCity());
            ps.setString(3, address.getState());
            ps.setString(4, address.getZipCode());
            ps.setString(5, address.getCountry());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Address findById(Long id) {
        return jdbcTemplate.queryForObject(GET_ADDRESS, new Object[]{id}, new AddressRowMapper());
    }

    @Override
    public boolean update(Address address) {
        jdbcTemplate.update(UPDATE_ADDRESS, address.getStreetAddress(), address.getCity(), address.getState(),
                address.getZipCode(), address.getCountry(), address.getId());
        return true;
    }

    @Override
    public boolean remove(Long id) {
        jdbcTemplate.update(DELETE_ADDRESS, id);
        return true;
    }
}
