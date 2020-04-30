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
public class AccountDaoImpl implements AccountDao {
    private static final String GET_ADDRESS_ID = "SELECT address_id FROM account WHERE id=?";
    private static final String INSERT_ACCOUNT =
            "INSERT INTO account (userName, password, account_status, name, address_id, email, phone) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";
    private static final String INSERT_ADDRESS = "INSERT INTO address (streetaddress, city, state, zipcode, country) " +
            "VALUES (?, ?, ?, ?, ?) RETURNING id";
    private static final String INSERT_CREDIT_CARD = "INSERT INTO credit_card (nameOnCard, cardNumber, code, address_id, account_id) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String INSERT_ELECTRONIC_BANK_TRANSFER = "INSERT INTO electronic_bank_transfer (bankName, routingNumber, AccountNumber, account_id) " +
            "VALUES (?, ?, ?, ?)";
    private transient final String UPDATE_ACCOUNT = "UPDATE account " +
            "SET userName=?, password=?, account_status=?, name=?, email=?, phone=? WHERE id=?";
    private transient final String UPDATE_ADDRESS = "UPDATE address " +
            "SET streetaddress=?, city=?, state=?, zipcode=?, country=? WHERE id=?";
    private transient final String DELETE_ACCOUNT = "DELETE FROM address AS add USING account AS acc WHERE add.id = acc.address_id AND acc.id = ?";
    private transient final String DELETE_CREDIT_CARDS = "DELETE FROM credit_card WHERE account_id=?";
    private transient final String DELETE_TRANSFERS = "DELETE FROM electronic_bank_transfer where account_id=?";
    private transient final String GET_ACCOUNT =
//            "SELECT acc.id, acc.userName, acc.password, acc.account_status, acc.name, acc.email, acc.phone," +
//                    "add.streetaddress, add.city, add.state, add.zipcode, add.country," +
//                    "cc.nameoncard, cc.cardnumber, cc.code," +
//                    "cadd.streetaddress, cadd.city, cadd.state, cadd.zipcode, cadd.country," +
//                    "ebt.bankName, ebt.routingNumber, ebt.accountNumber" +
//                    " FROM account acc, address add, credit_card cc, address cadd, electronic_bank_transfer ebt " +
//                    "WHERE acc.id=? AND acc.address_id = add.id AND acc.id = cc.account_id AND cc.address_id = add.id " +
//                    "AND acc.id = ebt.account_id";
            "SELECT acc.id, acc.userName, acc.password, acc.account_status, acc.name, acc.email, acc.phone," +
                    "add.streetaddress, add.city, add.state, add.zipcode, add.country," +
                    "cc.nameoncard, cc.cardnumber, cc.code," +
                    "cadd.streetaddress AS billingStreetAddress, cadd.city AS billingCity, cadd.state AS billingState, cadd.zipcode AS billingZipcode, cadd.country AS billingCountry," +
                    "ebt.bankName, ebt.routingNumber, ebt.accountNumber " +
                    "FROM account acc JOIN address add ON acc.address_id = add.id " +
                    "JOIN credit_card cc ON acc.id = cc.account_id " +
                    "JOIN address cadd ON cc.address_id = cadd.id " +
                    "JOIN electronic_bank_transfer ebt ON acc.id = ebt.account_id " +
                    "WHERE acc.id=? ";
    private transient final String GET_ACCOUNTS = "SELECT acc.id, acc.userName, acc.password, acc.account_status, acc.name, acc.email, acc.phone," +
            "add.streetaddress, add.city, add.state, add.zipcode, add.country," +
            "cc.nameoncard, cc.cardnumber, cc.code," +
            "cadd.streetaddress AS billingStreetAddress, cadd.city AS billingCity, cadd.state AS billingState, cadd.zipcode AS billingZipcode, cadd.country AS billingCountry," +
            "ebt.bankName, ebt.routingNumber, ebt.accountNumber " +
            "FROM account acc JOIN address add ON acc.address_id = add.id " +
            "JOIN credit_card cc ON acc.id = cc.account_id " +
            "JOIN address cadd ON cc.address_id = cadd.id " +
            "JOIN electronic_bank_transfer ebt ON acc.id = ebt.account_id ";
    //      " FROM account acc JOIN address add ON acc.address_id = add.id JOIN credit_card cc ON acc.id = cc.account_id " +
//              "WHERE acc.id=?";
//
//"SELECT um.USER_ID, um.USERNAME, um.PASSWORD,  um.AGENCIA,  um.EMAIL, um.GRABADO_POR,  um.MOBILENUMBER,  um.USER_STATUS,  um.ZONE, " +
//        " um.NAME, um.USER_TYPE,  urmm.USERROLEMAPPING_ID,  r.ROLE_ID,  r.ROLE_NAME,  r.PRIORITY,  rcmm.COMPONENT_ID,  am.ACTION_ID, " +
//        " am.ACTION_NMAE,  cm.COMPONENTID,  cm.COMPONENTNAME,  cm.COMPONENTIDENTIFICATION, cm.COMPONENTSTATE " +
//        "FROM USER_MASTER um, " +
//        " role r, USER_ROLE_MAPPING_MASTER urmm, ACTION_MASTER am, ROLE_COMPONENT_MAPPING_MASTER rcmm, ACTION_COMPONENT_MAPPINGMASTER " +
//        "acm,COMPONENT_MASTER cm WHERE upper(um.USERNAME)=upper(?) AND um.USER_ID          =urmm.USER_ID AND urmm.ROLE_ID      " +
//        "  =r.ROLE_ID AND r.ROLE_ID           =rcmm.ROLE_ID AND urmm.ROLE_ID        =rcmm.ROLE_ID AND acm.ACTION_ID       " +
//        "=am.ACTION_ID AND rcmm.COMPONENT_NAME =acm.COMPONENT_NAME AND acm.COMPONENT_NAME=cm.COMPONENTNAME(+)";
    private transient final JdbcTemplate jdbcTemplate;
    private transient final AccountActionVOMapper accountActionVOMapper;

    public AccountDaoImpl(JdbcTemplate jdbcTemplate, AccountActionVOMapper accountActionVOMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.accountActionVOMapper = accountActionVOMapper;
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
//        final List<AccountActionVO> accountVOs;
//        accountVOs = jdbcTemplate.query(GET_ACCOUNT, new Object[]{id}, new AccountActionVOMapper());
//        Account account = new Account();
//        CreditCard card = new CreditCard();
//        ElectronicBankTransfer transfer = new ElectronicBankTransfer();
//        Set<Account> accounts = new HashSet<>();
//        Set<CreditCard> cards = new HashSet<>();
//        Set<ElectronicBankTransfer> transfers = new HashSet<>();
//        for (AccountActionVO accountVO : accountVOs) {
//            accountVO.setId(rs.getLong("id"));
//            accountVO.setUserName(rs.getString("userName"));
//            accountVO.setPassword(rs.getString("password"));
//            accountVO.setStatus(AccountStatus.valueOf(rs.getString("account_status")));
//            accountVO.setName(rs.getString("name"));
//
//            accountVO.setStreetAddress(rs.getString("streetAddress"));
//            accountVO.setState(rs.getString("state"));
//            accountVO.setZipCode(rs.getString("zipcode"));
//            accountVO.setCountry(rs.getString("country"));
//            accountVO.setCity(rs.getString("city"));
//
//            accountVO.setEmail(rs.getString("email"));
//            accountVO.setPhone(rs.getString("phone"));
//
//            accountVO.setNameOnCard(rs.getString("nameOnCard"));
//            accountVO.setCardNumber(rs.getString("cardNumber"));
//            accountVO.setCode(rs.getInt("code"));
//
//            accountVO.setBillingStreetAddress(rs.getString("billingStreetAddress"));
//            accountVO.setBillingState(rs.getString("billingState"));
//            accountVO.setBillingZipCode(rs.getString("billingZipcode"));
//            accountVO.setBillingCountry(rs.getString("billingCountry"));
//            accountVO.setBillingCity(rs.getString("billingCity"));
//
//            accountVO.setBankName(rs.getString("bankName"));
//            accountVO.setRoutingNumber(rs.getString("routingNumber"));
//            accountVO.setAccountNumber(rs.getString("accountNumber"));
//        }
        return null;
//        Account account = jdbcTemplate.queryForObject(GET_ACCOUNT, accountActionVOMapper, id);
//        account.setId(id);
//        return account;
    }

    @Override
    public List<Account> getAll() {
        final List<AccountActionVO> accountVOs;
        accountVOs = jdbcTemplate.query(GET_ACCOUNTS, new AccountActionVOMapper());
        Account account;
        CreditCard card = new CreditCard();
        ElectronicBankTransfer transfer = new ElectronicBankTransfer();
        Set<Account> accounts = new HashSet<>();
//        Map<Account, CreditCard> cards = new HashMap<>();
//        Map<Account, ElectronicBankTransfer> transfers = new HashMap<>();
        Map<Long, Set<CreditCard>> cards = new HashMap<>();
        Map<Long, Set<ElectronicBankTransfer>> transfers = new HashMap<>();
//        for (Map.Entry e : phones.entrySet()) {
//            notebook.computeIfAbsent((String) e.getValue(),
//                    ph -> new ArrayList<>()).add((String) e.getKey());
//        }
        for (AccountActionVO accountVO : accountVOs) {
            account = new Account();
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

            card = new CreditCard();
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

            transfer = new ElectronicBankTransfer();
            transfer.setBankName(accountVO.getBankName());
            transfer.setRoutingNumber(accountVO.getRoutingNumber());
            transfer.setAccountNumber(accountVO.getAccountNumber());

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
}
