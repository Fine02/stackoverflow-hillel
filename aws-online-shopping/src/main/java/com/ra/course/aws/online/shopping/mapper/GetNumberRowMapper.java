package com.ra.course.aws.online.shopping.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class GetNumberRowMapper implements RowMapper<Integer> {
    @Override
    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer res = rs.getInt("account_id");
        return res;
    }
}
