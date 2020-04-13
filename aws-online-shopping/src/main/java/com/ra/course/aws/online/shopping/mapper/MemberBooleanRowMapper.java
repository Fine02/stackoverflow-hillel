package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.enums.AccountStatus;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.entity.user.Member;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberBooleanRowMapper implements RowMapper<Boolean> {

    @Override
    public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
        Member member = extractDataMember(rs, rowNum);
        if (member != null) {
            return true;
        }
        return false;
    }

    public Member extractDataMember(ResultSet rs, int rowNum) throws SQLException {
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

        List<CreditCard> creditCardList = mapToCreditCardList(rs);
        List<ElectronicBankTransfer> bankTransferList = mapToElectronicBankTransfer(rs);

        account.setUserName(rs.getString("userName"));
        account.setPassword(rs.getString("password"));
        account.setStatus(accountStatus);
        account.setName(rs.getString("name"));
        account.setShippingAddress(shippingAddress);
        account.setEmail(rs.getString("email"));
        account.setPhone(rs.getString("phone"));

        account.setCreditCardList(creditCardList);
        account.setElectronicBankTransferList(bankTransferList);
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

    private List<CreditCard> mapToCreditCardList(ResultSet rs) throws SQLException, DataAccessException {
        List<CreditCard> creditCards = new ArrayList<>();
        CreditCard creditCard = new CreditCard();
        creditCard.setNameOnCard(rs.getString("nameOnCard"));
        creditCard.setCardNumber(rs.getString("cardNumber"));
        creditCard.setCode(rs.getInt("code"));
        creditCards.add(creditCard);
        return creditCards;
    }

    private List<ElectronicBankTransfer> mapToElectronicBankTransfer(ResultSet rs) throws SQLException, DataAccessException {
        List<ElectronicBankTransfer> electronicBankTransferList = new ArrayList<>();
        ElectronicBankTransfer electronicBankTransfer = new ElectronicBankTransfer();
        electronicBankTransfer.setBankName(rs.getString("bankName"));
        electronicBankTransfer.setRoutingNumber(rs.getString("routingNumber"));
        electronicBankTransfer.setAccountNumber(rs.getString("accountNumber"));
        electronicBankTransferList.add(electronicBankTransfer);
        return electronicBankTransferList;
    }


}
