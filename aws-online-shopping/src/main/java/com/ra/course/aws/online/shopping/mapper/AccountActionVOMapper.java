package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.enums.AccountStatus;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.entity.vo.AccountActionVO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
//        while (rs.next()) {
//            CreditCard card = new CreditCard();
//
//            address.setStreetAddress(rs.getString("streetAddress"));
//            address.setStreetAddress(rs.getString("city"));
//            address.setStreetAddress(rs.getString("state"));
//            address.setStreetAddress(rs.getString("zipcode"));
//            address.setStreetAddress(rs.getString("country"));
//
//            card.setBillingAddress(address);
//            card.setCardNumber(rs.getString("nameOnCard"));
//            card.setCardNumber(rs.getString("cardNumber"));
//            card.setCardNumber(rs.getString("code"));
//
//            creditCards.add(card);
//
//
//            account.setUserName(rs.getString("userName"));
//            account.setPassword(rs.getString("password"));
//            account.setStatus(AccountStatus.valueOf(rs.getString("account_status")));
//            account.setName(rs.getString("name"));
//            account.setShippingAddress(address);
//            account.setEmail(rs.getString("email"));
//            account.setPhone(rs.getString("phone"));
//            account.setCreditCardList(creditCards);
//        }
//        //account.setElectronicBankTransferList(rs.getString());
//        return account;
    }
}
