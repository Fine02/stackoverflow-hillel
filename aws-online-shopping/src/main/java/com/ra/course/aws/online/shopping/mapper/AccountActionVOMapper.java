package com.ra.course.aws.online.shopping.mapper;

import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.enums.AccountStatus;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.entity.vo.AccountActionVO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
public class AccountActionVOMapper implements RowMapper<AccountActionVO> {
    @Override
    public AccountActionVO mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final AccountActionVO accountVO = new AccountActionVO();
        accountVO.setId(rs.getLong("id"));
        accountVO.setUserName(rs.getString("userName"));
        accountVO.setPassword(rs.getString("password"));
        accountVO.setStatus(AccountStatus.valueOf(rs.getString("account_status")));
        accountVO.setName(rs.getString("name"));

        accountVO.setAddressId(rs.getLong("addressId"));
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

        accountVO.setBillingAddressId(rs.getLong("billingAddressId"));
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

    public List<Account> getMappedAccountsFromVO(final List<AccountActionVO> accountVOs) {
        Account account;
        CreditCard card;
        ElectronicBankTransfer transfer;

        final Set<Account> accounts = new HashSet<>();
        final Map<Long, Set<CreditCard>> cards = new HashMap<>();
        final Map<Long, Set<ElectronicBankTransfer>> transfers = new HashMap<>();

        for (final AccountActionVO accountVO : accountVOs) {
            account = mapAccountFromVO(accountVO);
            card = mapCreditCardFromVO(accountVO);
            transfer = mapTransferFromVO(accountVO);

            cards.computeIfAbsent(account.getId(), cc -> new HashSet<>()).add(card);
            transfers.computeIfAbsent(account.getId(), cc -> new HashSet<>()).add(transfer);
            accounts.add(account);
        }
        for (final Account acc : accounts) {
            acc.setCreditCardList(new ArrayList<>(cards.get(acc.getId())));
            acc.setElectronicBankTransferList(new ArrayList<>(transfers.get(acc.getId())));
        }
        return new ArrayList<>(accounts);
    }


    private Account mapAccountFromVO(final AccountActionVO accountVO) {
        final Account account = new Account();
        account.setId(accountVO.getId());
        account.setUserName(accountVO.getUserName());
        account.setPassword(accountVO.getPassword());
        account.setStatus(accountVO.getStatus());
        account.setName(accountVO.getName());
        account.setEmail(accountVO.getEmail());
        account.setPhone(accountVO.getPhone());

        final Address shippingAddress = new Address();
        shippingAddress.setId(accountVO.getAddressId());//////////////
        shippingAddress.setStreetAddress(accountVO.getStreetAddress());
        shippingAddress.setState(accountVO.getState());
        shippingAddress.setZipCode(accountVO.getZipCode());
        shippingAddress.setCountry(accountVO.getCountry());
        shippingAddress.setCity(accountVO.getCity());
        account.setShippingAddress(shippingAddress);
        return account;
    }

    private CreditCard mapCreditCardFromVO(final AccountActionVO accountVO) {
        final CreditCard card = new CreditCard();
        card.setNameOnCard(accountVO.getNameOnCard());
        card.setCardNumber(accountVO.getCardNumber());
        card.setCode(accountVO.getCode());
        final Address billingAddress = new Address();
        billingAddress.setId(accountVO.getBillingAddressId());///////////////
        billingAddress.setStreetAddress(accountVO.getBillingStreetAddress());
        billingAddress.setState(accountVO.getBillingState());
        billingAddress.setZipCode(accountVO.getBillingZipCode());
        billingAddress.setCountry(accountVO.getBillingCountry());
        billingAddress.setCity(accountVO.getBillingCity());
        card.setBillingAddress(billingAddress);
        return card;
    }

    private ElectronicBankTransfer mapTransferFromVO(final AccountActionVO accountVO) {
        final ElectronicBankTransfer transfer = new ElectronicBankTransfer();
        transfer.setBankName(accountVO.getBankName());
        transfer.setRoutingNumber(accountVO.getRoutingNumber());
        transfer.setAccountNumber(accountVO.getAccountNumber());
        return transfer;
    }
}
