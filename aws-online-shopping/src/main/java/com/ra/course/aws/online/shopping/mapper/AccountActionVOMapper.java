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
import java.util.concurrent.ConcurrentHashMap;

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
        final Map<Long, Set<CreditCard>> cards = new ConcurrentHashMap<>();
        final Map<Long, Set<ElectronicBankTransfer>> transfers = new ConcurrentHashMap<>();
        cards.put(-1L, new HashSet<>());
        transfers.put(-1L, new HashSet<>());
        final Iterator<AccountActionVO> itrVO = accountVOs.iterator();
        AccountActionVO accountVO;
        while (itrVO.hasNext()) {
            accountVO = itrVO.next();
            account = mapAccountFromVO(accountVO);
            card = mapCreditCardFromVO(accountVO);
            transfer = mapTransferFromVO(accountVO);
            computeCreditCard(account.getId(), cards, card);
            computeTransfer(account.getId(), transfers, transfer);
            accounts.add(account);
        }
        final Iterator<Account> itrAcc = accounts.iterator();
        while (itrAcc.hasNext()) {
            account = itrAcc.next();
            account.getCreditCardList().addAll(cards.get(account.getId()));
            account.getElectronicBankTransferList().addAll(transfers.get(account.getId()));
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

        final List<CreditCard> cardsList = new ArrayList<>();
        final List<ElectronicBankTransfer> transfersList = new ArrayList<>();
        account.setCreditCardList(cardsList);
        account.setElectronicBankTransferList(transfersList);
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

    private void computeCreditCard(final Long accountId, final Map<Long, Set<CreditCard>> cards, final CreditCard card) {
        cards.computeIfAbsent(accountId, cc -> new HashSet<>()).add(card);
    }

    private void computeTransfer(final Long accountId, final Map<Long, Set<ElectronicBankTransfer>> transfers, final ElectronicBankTransfer transfer) {
        transfers.computeIfAbsent(accountId, cc -> new HashSet<>()).add(transfer);
    }
}