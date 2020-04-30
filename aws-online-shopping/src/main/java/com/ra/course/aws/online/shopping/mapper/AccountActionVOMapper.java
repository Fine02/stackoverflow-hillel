package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.enums.AccountStatus;
import com.ra.course.aws.online.shopping.entity.vo.AccountActionVO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class AccountActionVOMapper implements RowMapper<AccountActionVO> {
    @Override
    public AccountActionVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        final AccountActionVO accountVO = new AccountActionVO();
        accountVO.setId(rs.getLong("id"));
        accountVO.setUserName(rs.getString("userName"));
        accountVO.setPassword(rs.getString("password"));
        accountVO.setStatus(AccountStatus.valueOf(rs.getString("account_status")));
        accountVO.setName(rs.getString("name"));

        accountVO.setStreetAddress(rs.getString("streetAddress"));
        accountVO.setState(rs.getString("state"));
        accountVO.setZipCode(rs.getString("zipcode"));
        accountVO.setCountry(rs.getString("country"));
        accountVO.setCity(rs.getString("city"));

        accountVO.setEmail(rs.getString("email"));
        accountVO.setPhone(rs.getString("phone"));

        accountVO.setNameOnCard(rs.getString("nameOnCard"));
        accountVO.setCardNumber(rs.getString("cardNumber"));
        accountVO.setCode(rs.getInt("code"));

        accountVO.setBillingStreetAddress(rs.getString("billingStreetAddress"));
        accountVO.setBillingState(rs.getString("billingState"));
        accountVO.setBillingZipCode(rs.getString("billingZipcode"));
        accountVO.setBillingCountry(rs.getString("billingCountry"));
        accountVO.setBillingCity(rs.getString("billingCity"));

        accountVO.setBankName(rs.getString("bankName"));
        accountVO.setRoutingNumber(rs.getString("routingNumber"));
        accountVO.setAccountNumber(rs.getString("accountNumber"));

        return accountVO;
    }
}
