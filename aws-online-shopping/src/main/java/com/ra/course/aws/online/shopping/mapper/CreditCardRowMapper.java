package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class CreditCardRowMapper implements RowMapper<CreditCard> {
    @Override
    public CreditCard mapRow(ResultSet rs, int rowNum) throws SQLException {
        Address address = mapAddress(rs, rowNum);
        CreditCard creditCard = new CreditCard();
        creditCard.setNameOnCard(rs.getString("nameOnCard"));
        creditCard.setCardNumber(rs.getString("cardNumber"));
        creditCard.setCode(rs.getInt("code"));
        creditCard.setBillingAddress(address);
        return creditCard;
    }

    public Address mapAddress(ResultSet rs, int rowNum) throws SQLException {
        Address address = new Address();
        address.setStreetAddress(rs.getString("streetAddress"));
        address.setCity(rs.getString("city"));
        address.setState(rs.getString("state"));
        address.setZipCode(rs.getString("zipcode"));
        address.setCountry(rs.getString("country"));
        return address;
    }
}
