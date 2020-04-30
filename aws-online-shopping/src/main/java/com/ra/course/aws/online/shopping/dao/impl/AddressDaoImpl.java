package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.AddressDao;
import com.ra.course.aws.online.shopping.entity.Address;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
@PropertySource("sql-requests.yml")
public class AddressDaoImpl implements AddressDao {
    @Value("${INSERT_ADDRESS}")
    private transient String INSERT_ADDRESS;

    private transient final JdbcTemplate jdbcTemplate;

    public AddressDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Address address) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
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
        return null;
    }

    @Override
    public boolean update(Address address) {
        return false;
    }


    @Override
    public boolean remove(Long id) {
        return false;
    }
}
