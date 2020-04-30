package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.AccountDao;
import com.ra.course.aws.online.shopping.dao.Dao;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.enums.AccountStatus;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.entity.vo.AccountActionVO;
import com.ra.course.aws.online.shopping.mapper.AccountActionVOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

//DELETE FROM table
//        USING another_table
//        WHERE table.id = another_table.id AND …
@Repository
@PropertySource("account-SQL-requests.yml")
public class AccountDaoImpl implements AccountDao {
    @Value("${GET_ADDRESS_ID}")
    private transient String GET_ADDRESS_ID;
    @Value("${INSERT_ACCOUNT}")
    private transient String INSERT_ACCOUNT;
    @Value("${INSERT_ADDRESS}")
    private transient String INSERT_ADDRESS;
    @Value("${INSERT_CREDIT_CARD}")
    private transient String INSERT_CREDIT_CARD;
    @Value("${INSERT_ELECTRONIC_BANK_TRANSFER}")
    private transient String INSERT_ELECTRONIC_BANK_TRANSFER;
    @Value("${UPDATE_ACCOUNT}")
    private transient String UPDATE_ACCOUNT;
    @Value("${UPDATE_ADDRESS}")
    private transient String UPDATE_ADDRESS;
    @Value("${DELETE_ACCOUNT}")
    private transient String DELETE_ACCOUNT;
    @Value("${DELETE_CREDIT_CARDS}")
    private transient String DELETE_CREDIT_CARDS;
    @Value("${DELETE_TRANSFERS}")
    private transient String DELETE_TRANSFERS;
    @Value("${GET_ACCOUNTS}")
    private transient String GET_ACCOUNTS;

    private transient final JdbcTemplate jdbcTemplate;

    public AccountDaoImpl(JdbcTemplate jdbcTemplate, AccountActionVOMapper accountActionVOMapper) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Account account) {
        Integer addressId = saveAddress(account.getShippingAddress());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(INSERT_ACCOUNT, new String[]{"id"});
            ps.setString(1, account.getUserName());
            ps.setString(2, account.getPassword());
            ps.setString(3, account.getStatus().toString());
            ps.setString(4, account.getName());
            ps.setInt(5, addressId);
            ps.setString(6, account.getEmail());
            ps.setString(7, account.getPhone());
            return ps;
        }, keyHolder);
        Integer savedAccountId = keyHolder.getKey().intValue();
        saveCreditCards(account.getCreditCardList(), savedAccountId, addressId);
        saveElectronicBankTransfers(account.getElectronicBankTransferList(), savedAccountId);
        return savedAccountId.longValue();
    }


    private Integer saveAddress(Address address) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(INSERT_ADDRESS, new String[]{"id"});
            ps.setString(1, address.getStreetAddress());
            ps.setString(2, address.getCity());
            ps.setString(3, address.getState());
            ps.setString(4, address.getZipCode());
            ps.setString(5, address.getCountry());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void saveCreditCards(final List<CreditCard> creditCards, Integer savedAccountId, Integer addressId) {
        final int batchSize = 500;
        for (int j = 0; j < creditCards.size(); j += batchSize) {
            final List<CreditCard> batchList = creditCards.subList(j, Math.min(j + batchSize, creditCards.size()));
            jdbcTemplate.batchUpdate(INSERT_CREDIT_CARD,
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i)
                                throws SQLException {
                            CreditCard creditCard = batchList.get(i);
                            ps.setString(1, creditCard.getNameOnCard());
                            ps.setString(2, creditCard.getCardNumber());
                            ps.setInt(3, creditCard.getCode());
                            ps.setInt(4, addressId);
                            ps.setInt(5, savedAccountId);
                        }

                        @Override
                        public int getBatchSize() {
                            return batchList.size();
                        }
                    });
        }
    }

    public void saveElectronicBankTransfers(final List<ElectronicBankTransfer> transfers, Integer savedAccountId) {
        final int batchSize = 500;
        for (int j = 0; j < transfers.size(); j += batchSize) {
            final List<ElectronicBankTransfer> batchList = transfers.subList(j, Math.min(j + batchSize, transfers.size()));
            jdbcTemplate.batchUpdate(INSERT_ELECTRONIC_BANK_TRANSFER,
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i)
                                throws SQLException {
                            ElectronicBankTransfer transfer = batchList.get(i);
                            ps.setString(1, transfer.getBankName());
                            ps.setString(2, transfer.getRoutingNumber());
                            ps.setString(3, transfer.getAccountNumber());
                            ps.setInt(4, savedAccountId);
                        }

                        @Override
                        public int getBatchSize() {
                            return batchList.size();
                        }
                    });
        }
    }

    @Override
    public boolean update(Account account) {   //
        jdbcTemplate.update(UPDATE_ACCOUNT, account.getUserName(), account.getPassword(), account.getStatus().toString(), account.getName(),
                account.getEmail(), account.getPhone(), account.getId());

        Address newAddress = account.getShippingAddress();
        Integer addressId = jdbcTemplate.queryForObject(GET_ADDRESS_ID, Integer.class, account.getId());
        jdbcTemplate.update(UPDATE_ADDRESS, newAddress.getStreetAddress(),
                newAddress.getCity(), newAddress.getState(), newAddress.getZipCode(), newAddress.getCountry(), addressId);
        return true;
    }

    @Override
    public boolean remove(Long id) {
        jdbcTemplate.update(DELETE_ACCOUNT, id);
        return true;
    }

    @Override
    public Account findById(Long id) {
        final String GET_ACCOUNT = GET_ACCOUNTS + " WHERE acc.id=? ";
        final List<AccountActionVO> accountVOs;
        accountVOs = jdbcTemplate.query(GET_ACCOUNT, new Object[]{id}, new AccountActionVOMapper());
        return getMappedAccountsFromVO(accountVOs).stream().findAny().orElse(null);
    }

    @Override
    public List<Account> getAll() {
        final List<AccountActionVO> accountVOs;
        accountVOs = jdbcTemplate.query(GET_ACCOUNTS, new AccountActionVOMapper());
        return getMappedAccountsFromVO(accountVOs);
    }

    @Override
    public boolean saveCreditCard(CreditCard creditCard, Long accountId) {
        Address address = creditCard.getBillingAddress();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(INSERT_ADDRESS, new String[]{"id"});
            ps.setString(1, address.getStreetAddress());
            ps.setString(2, address.getCity());
            ps.setString(3, address.getState());
            ps.setString(4, address.getZipCode());
            ps.setString(5, address.getCountry());
            return ps;
        }, keyHolder);
        jdbcTemplate.update(INSERT_CREDIT_CARD, creditCard.getNameOnCard(), creditCard.getCardNumber(), creditCard.getCode(),
                keyHolder.getKey().intValue(), accountId.intValue());
        return true;
    }

    @Override
    public boolean saveElectornicBankTransfer(ElectronicBankTransfer transfer, Long accountId) {
        jdbcTemplate.update(INSERT_ELECTRONIC_BANK_TRANSFER, transfer.getBankName(), transfer.getRoutingNumber(),
                transfer.getAccountNumber(), accountId.intValue());
        return true;
    }

    private List<Account> getMappedAccountsFromVO(List<AccountActionVO> accountVOs) {
        Account account;
        CreditCard card;
        ElectronicBankTransfer transfer;

        Set<Account> accounts = new HashSet<>();
        Map<Long, Set<CreditCard>> cards = new HashMap<>();
        Map<Long, Set<ElectronicBankTransfer>> transfers = new HashMap<>();

        for (AccountActionVO accountVO : accountVOs) {
            account = mapAccountFromVO(accountVO);
            card = mapCreditCardFromVO(accountVO);
            transfer = mapTransferFromVO(accountVO);

            cards.computeIfAbsent(account.getId(), cc -> new HashSet<>()).add(card);
            transfers.computeIfAbsent(account.getId(), cc -> new HashSet<>()).add(transfer);
            accounts.add(account);
        }
        for (Account acc : accounts) {
            acc.setCreditCardList(new ArrayList<>(cards.get(acc.getId())));
            acc.setElectronicBankTransferList(new ArrayList<>(transfers.get(acc.getId())));
        }
        return new ArrayList<>(accounts);
    }


    private Account mapAccountFromVO(AccountActionVO accountVO) {
        Account account = new Account();
        account.setId(accountVO.getId());
        account.setUserName(accountVO.getUserName());
        account.setPassword(accountVO.getPassword());
        account.setStatus(accountVO.getStatus());
        account.setName(accountVO.getName());
        account.setEmail(accountVO.getEmail());
        account.setPhone(accountVO.getPhone());

        Address shippingAddress = new Address();
        shippingAddress.setStreetAddress(accountVO.getStreetAddress());
        shippingAddress.setState(accountVO.getState());
        shippingAddress.setZipCode(accountVO.getZipCode());
        shippingAddress.setCountry(accountVO.getCountry());
        shippingAddress.setCity(accountVO.getCity());
        account.setShippingAddress(shippingAddress);
        return account;
    }

    private CreditCard mapCreditCardFromVO(AccountActionVO accountVO) {
        CreditCard card = new CreditCard();
        card.setNameOnCard(accountVO.getNameOnCard());
        card.setCardNumber(accountVO.getCardNumber());
        card.setCode(accountVO.getCode());
        Address billingAddress = new Address();
        billingAddress.setStreetAddress(accountVO.getBillingStreetAddress());
        billingAddress.setState(accountVO.getBillingState());
        billingAddress.setZipCode(accountVO.getBillingZipCode());
        billingAddress.setCountry(accountVO.getBillingCountry());
        billingAddress.setCity(accountVO.getBillingCity());
        card.setBillingAddress(billingAddress);
        return card;
    }

    private ElectronicBankTransfer mapTransferFromVO(AccountActionVO accountVO) {
        ElectronicBankTransfer transfer = new ElectronicBankTransfer();
        transfer.setBankName(accountVO.getBankName());
        transfer.setRoutingNumber(accountVO.getRoutingNumber());
        transfer.setAccountNumber(accountVO.getAccountNumber());
        return transfer;
    }
}
