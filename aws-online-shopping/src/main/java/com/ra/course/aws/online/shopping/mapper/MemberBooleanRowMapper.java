package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.enums.AccountStatus;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.entity.user.Member;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberBooleanRowMapper implements RowMapper<Boolean> {

    @Override
    public Boolean mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Member member = extractDataMember(rs);
        if (member != null && member.getMemberID()!=0) {
            return true;
        }
        return false;
    }

    public Member extractDataMember(final ResultSet rs) throws SQLException {
        final Account account = dataFromAccount(rs);
        final Member member = new Member();
        member.setAccount(account);
        member.setMemberID(rs.getInt("member_id"));
        return member;
    }


    public Account dataFromAccount(final ResultSet rs) throws SQLException {
        final Account account = new Account();
        final AccountStatus accountStatus = mapToAccountStatus(rs);
        final Address shippingAddress = mapToAddress(rs);

        final List<CreditCard> creditCardList = mapToCreditCardList(rs);
        final List<ElectronicBankTransfer> bankTransferList = mapToElectronicBankTransfer(rs);

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

    private AccountStatus mapToAccountStatus(final ResultSet rs) throws SQLException {
        final var status = rs.getString("status");
        return status ==null? null: AccountStatus.valueOf(AccountStatus.class, status);
    }

    private Address mapToAddress(final ResultSet rs) throws SQLException {
        final Address address = new Address();
        address.setStreetAddress(rs.getString("streetAddress"));
        address.setCity(rs.getString("city"));
        address.setState(rs.getString("state"));
        address.setZipCode(rs.getString("zipcode"));
        address.setCountry(rs.getString("country"));
        return address;
    }

    private List<CreditCard> mapToCreditCardList(final ResultSet rs) throws SQLException {
        final List<CreditCard> creditCards = new ArrayList<>();
        final CreditCard creditCard = new CreditCard();
        creditCard.setNameOnCard(rs.getString("nameOnCard"));
        creditCard.setCardNumber(rs.getString("cardNumber"));
        creditCard.setCode(rs.getInt("code"));
        creditCards.add(creditCard);
        return creditCards;
    }

    private List<ElectronicBankTransfer> mapToElectronicBankTransfer(final ResultSet rs) throws SQLException {
        final List<ElectronicBankTransfer> bankTransferList = new ArrayList<>();
        final ElectronicBankTransfer bankTransfer = new ElectronicBankTransfer();
        bankTransfer.setBankName(rs.getString("bankName"));
        bankTransfer.setRoutingNumber(rs.getString("routingNumber"));
        bankTransfer.setAccountNumber(rs.getString("accountNumber"));
        bankTransferList.add(bankTransfer);
        return bankTransferList;
    }

}
