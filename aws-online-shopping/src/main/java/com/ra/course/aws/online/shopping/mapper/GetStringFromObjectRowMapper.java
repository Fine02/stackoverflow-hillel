package com.ra.course.aws.online.shopping.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class GetStringFromObjectRowMapper implements RowMapper<String> {
    @Override
    public String mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return rs.getString(1);
    }
}
