package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.enums.AccountStatus;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.entity.user.Member;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class MemberWithoutListsRowMapper implements RowMapper <Member> {
    @Override
    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
        Account account = dataFromAccount(rs, rowNum);
        Member member = new Member();
        member.setAccount(account);
        member.setMemberID(rs.getInt("member_id"));
        return member;
    }

    public Account dataFromAccount(ResultSet rs, int rowNum) throws SQLException {
        Account account = new Account();
        AccountStatus accountStatus = mapToAccountStatus(rs);
        Address shippingAddress = mapToAddress(rs);
        account.setUserName(rs.getString("userName"));
        account.setPassword(rs.getString("password"));
        account.setStatus(accountStatus);
        account.setName(rs.getString("name"));
        account.setShippingAddress(shippingAddress);
        account.setEmail(rs.getString("email"));
        account.setPhone(rs.getString("phone"));
        return account;
    }

    private AccountStatus mapToAccountStatus(ResultSet rs) throws SQLException, DataAccessException {
        AccountStatus accountStatus = AccountStatus.valueOf(rs.getString("status"));
        return accountStatus;
    }

    private Address mapToAddress(ResultSet rs) throws SQLException, DataAccessException {
        Address address = new Address();
        address.setStreetAddress(rs.getString("streetAddress"));
        address.setCity(rs.getString("city"));
        address.setState(rs.getString("state"));
        address.setZipCode(rs.getString("zipcode"));
        address.setCountry(rs.getString("country"));
        return address;
    }
}
